package com.techelevator.dao;

import com.techelevator.model.Campground;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class JdbcCampgroundDaoTests extends BaseDaoTests {

    private static final Campground CAMPGROUND_1 = new Campground(1, 1, "Test Campground 1", 1, 12, 35);
    private static final Campground CAMPGROUND_2 = new Campground(2, 1, "Test Campground 2", 1, 12, 35);

    private CampgroundDao dao;

    @Before
    public void setup() {
        dao = new JdbcCampgroundDao(dataSource);
    }

    @Test
    public void getCampgroundById_Should_Return_Specific_Campground() {
        Campground campground = dao.getCampgroundById(1);

        assertEquals("Incorrect campground returned for ID 1", 1, campground.getCampgroundId());
    }

    @Test
    public void getCampgroundsByParkId_Should_Return_All_Campgrounds_For_Park() {
        List<Campground> campgrounds = dao.getCampgroundsByParkId(1);
        Assert.assertEquals("Size does not match", 2, campgrounds.size());

        assertCampgroundsMatch(CAMPGROUND_1, campgrounds.get(0));
        assertCampgroundsMatch(CAMPGROUND_2, campgrounds.get(1));
    }

    private void assertCampgroundsMatch(Campground expected, Campground actual) {
        Assert.assertEquals("Campground id does not match", expected.getCampgroundId(), actual.getCampgroundId());
        Assert.assertEquals("Park id does not match", expected.getParkId(), actual.getParkId());
        Assert.assertEquals("Name does not match", expected.getName(), actual.getName());
        Assert.assertEquals("Open from month does not match", expected.getOpenFromMonth(), actual.getOpenFromMonth());
        Assert.assertEquals("Open to month does not match", expected.getOpenToMonth(), actual.getOpenToMonth());
        Assert.assertEquals("Daily fee does not match", expected.getDailyFee(), actual.getDailyFee(), 0.0);
    }


}
