package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Site;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcSiteDao implements SiteDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcSiteDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Site> getSitesWithRVAccessByParkId(int parkId) {
        List<Site> sites = new ArrayList<>();
        String sql = "SELECT * FROM site JOIN campground ON site.campground_id = campground.campground_id WHERE site.max_rv_length > 0 AND campground.park_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, parkId);
            while (results.next()) {
                sites.add(mapRowToSite(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }

        return sites;
    }

    @Override
    public List<Site> getSitesAvailableTodayByParkId(int parkId) {
        List<Site> sites = new ArrayList<>();
        String sql = "SELECT DISTINCT site.site_id, site.campground_id, site_number, max_occupancy, accessible, max_rv_length, utilities FROM site " +
                "JOIN campground ON site.campground_id = campground.campground_id " +
                "JOIN park ON campground.park_id = park.park_id " +
                "JOIN reservation ON site.site_id = reservation.site_id " +
                "WHERE park.park_id = ? AND CURRENT_DATE NOT BETWEEN from_date AND to_date";

//        IN (from_date, to_date)
//        +
//                "(SELECT COUNT(reservation_id) AS count_res " +
//                "FROM reservation " +
//                "WHERE CURRENT_DATE BETWEEN from_date AND to_date) < max_occupancy;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, parkId);
            while (results.next()) {
                sites.add(mapRowToSite(results));
            }
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }

        return sites;
    }

    private Site mapRowToSite(SqlRowSet results) {
        Site site = new Site();
        site.setSiteId(results.getInt("site_id"));
        site.setCampgroundId(results.getInt("campground_id"));
        site.setSiteNumber(results.getInt("site_number"));
        site.setMaxOccupancy(results.getInt("max_occupancy"));
        site.setAccessible(results.getBoolean("accessible"));
        site.setMaxRvLength(results.getInt("max_rv_length"));
        site.setUtilities(results.getBoolean("utilities"));
        return site;
    }
}
