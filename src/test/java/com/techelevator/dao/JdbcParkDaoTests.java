package com.techelevator.dao;

import com.techelevator.model.Park;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

public class JdbcParkDaoTests extends BaseDaoTests {

    private static final Park PARK_1 = new Park(1, "Park 1", "Pennsylvania", LocalDate.parse("1970-01-01"), 1024, 512, "Test description 1");
    private static final Park PARK_2 = new Park(2, "Park 2", "Ohio", LocalDate.parse("1970-01-01"), 2048, 1024, "Test description 2");
    private ParkDao dao;

    @Before
    public void setup() {
        dao = new JdbcParkDao(dataSource);
    }

    @Test
    public void getParks_Should_Return_All_Parks() {
        List<Park> parks = dao.getParks();
        Assert.assertEquals("Incorrect number of Parks",2, parks.size());
        assertAllParksMatch(PARK_1, parks.get(0));
        assertAllParksMatch(PARK_2, parks.get(1));
    }

    private void assertAllParksMatch(Park expected, Park actual) {
        Assert.assertEquals(expected.getParkId(), actual.getParkId());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getLocation(), actual.getLocation());
        Assert.assertEquals(expected.getEstablishDate(), actual.getEstablishDate());
        Assert.assertEquals(expected.getArea(), actual.getArea());
        Assert.assertEquals(expected.getVisitors(), actual.getVisitors());
        Assert.assertEquals(expected.getDescription(), actual.getDescription());
    }

}
