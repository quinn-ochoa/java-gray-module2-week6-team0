package com.techelevator.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.techelevator.model.Reservation;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;



public class JdbcReservationDaoTests extends BaseDaoTests {

    private static final Reservation RESERVATION_1 = new Reservation(1, 1, "Amy Adams", LocalDate.now().minusDays(18),
                                                    LocalDate.now().minusDays(11), LocalDate.now().minusDays(23));
    private static final Reservation RESERVATION_2 = new Reservation(2, 1, "Bob Billings", LocalDate.now().minusDays(2),
                                                    LocalDate.now().minusDays(1), LocalDate.now().minusDays(3));

    private ReservationDao dao;

    @Before
    public void setup() {
        dao = new JdbcReservationDao(dataSource);
    }

    @Test
    public void getReservationById_Should_Return_Specific_Reservation() {
        Reservation reservation = dao.getReservationById(1);
        assertReservationsMatch(RESERVATION_1, reservation);

        reservation = dao.getReservationById(2);
        assertReservationsMatch(RESERVATION_2, reservation);
    }

    @Test
    public void createReservation_Should_Return_Reservation_With_New_Id() {
        Reservation reservation = new Reservation(
            0,
            1,
            "TEST NAME",
            LocalDate.now().plusDays(1),
            LocalDate.now().plusDays(3),
            LocalDate.now());

        Reservation reservationCreated = dao.createReservation(reservation);

        assertEquals("Incorrect ID of new reservation", 13, reservationCreated.getReservationId());

    }

    private void assertReservationsMatch (Reservation expected, Reservation actual) {

        Assert.assertEquals(expected.getReservationId(), actual.getReservationId());
        Assert.assertEquals(expected.getSiteId(), actual.getSiteId());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getFromDate(), actual.getFromDate());
        Assert.assertEquals(expected.getToDate(), actual.getToDate());
        Assert.assertEquals(expected.getCreateDate(), actual.getCreateDate());

    }

}
