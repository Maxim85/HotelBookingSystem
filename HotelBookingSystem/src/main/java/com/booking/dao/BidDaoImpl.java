package com.booking.dao;

import com.booking.model.hotel.Bid;
import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Maksym.
 */
public class BidDaoImpl implements BidDao {
    //SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
    private static Logger logger = Logger.getLogger(BidDaoImpl.class.getName());

    public void add(Bid bid) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.AM_PM, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date currentDay = calendar.getTime();
        if (bid.getTerm() < 1) {
            throw new IllegalArgumentException("Enter correct term. Term can not be less than one day.");
        }
        if ((bid.getArrival() == null) || (bid.getArrival().compareTo(currentDay) < 0)) {
            throw new IllegalArgumentException("Enter the correct date. Date can not be a blank or may not be early than today.");
        }
        String query = "INSERT INTO bid(id, term, arrival) VALUES (?, ?, ?)";
        try (Connection connection = ConnectionFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, bid.getId());
            statement.setShort(2, (short) bid.getTerm());
            statement.setDate(3, new java.sql.Date(bid.getArrival().getTime()));
            statement.execute();
            logger.info("Method of add new bid successfully completed. Bid term set: " + bid.getTerm() + "\nArrival date set: " + bid.getArrival());
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception in method add new bid: ", e);
        }
    }

    public void update(Bid bid) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.AM_PM, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date currentDay = calendar.getTime();
        if (bid.getTerm() < 1) {
            throw new IllegalArgumentException("Enter correct term. Term can not be less than one day.");
        }
        if ((bid.getArrival() == null) || (bid.getArrival().compareTo(currentDay) < 0)) {
            throw new IllegalArgumentException("Enter the correct date. Date can not be a blank or may not be early than today.");
        }
        String query = "UPDATE bid SET term = ?, arrival = ? WHERE id = ?";
        try (Connection connection = ConnectionFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setShort(1, (short) bid.getTerm());
            statement.setDate(2, new java.sql.Date(bid.getArrival().getTime()));
            statement.setLong(3, bid.getId());
            statement.executeUpdate();
            logger.info("Method update bid successfully completed. Bid term set: " + bid.getTerm() + "\nArrival date set: " + bid.getArrival());
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception in method update the bid: ", e);
        }
    }

    public void delete(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id can not be less than zero");
        }
        String query = "DELETE FROM bid WHERE id = ?;";
        try (Connection connection = ConnectionFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.execute();
            logger.info("Method of deleting the bid by id successfully completed.");
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception in method delete bid by id: ", e);
        }
    }

    public void deleteAll() {
        String query = "DELETE FROM bid";
        try (Connection connection = ConnectionFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.execute();
            logger.info("Method of deleting all bid successfully completed.");
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception in method delete all bid: ", e);
        }
    }

    public Bid find(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id can not be less than zero");
        }
        String query = "SELECT * FROM bid WHERE id = ?;";
        Bid bid = new Bid();
        try (Connection connection = ConnectionFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long idFromDB = resultSet.getLong(1);
                Date arrival = new java.util.Date(resultSet.getDate(2).getTime());
                short term = resultSet.getShort(3);
                bid.setId(idFromDB);
                bid.setTerm(term);
                bid.setArrival(arrival);
                logger.info("Method of finding the bid by id successfully completed.");
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception in method find bid by id: ", e);
        }
        return bid;
    }

    public ArrayList<Bid> getAll() {
        ArrayList<Bid> list = new ArrayList<>();
        String query = "SELECT * FROM bid;";
        try (Connection connection = ConnectionFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Bid bid = new Bid();
                long id = resultSet.getLong(1);
                Date arrival = new java.util.Date(resultSet.getDate(2).getTime());
                short term = resultSet.getShort(3);
                bid.setId(id);
                bid.setArrival(arrival);
                bid.setTerm(term);
                list.add(bid);
            }
            logger.info("Method of obtaining all data of bid successfully completed.\n" +
                    "There is " + list.size() + " bid in database");
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception in method get all bid: ", e);
        }
        return list;
    }
}
