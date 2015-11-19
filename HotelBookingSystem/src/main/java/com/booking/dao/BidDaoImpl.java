package com.booking.dao;

import com.booking.model.Bid;
import com.booking.model.Type;
import com.booking.model.User;
import com.mysql.jdbc.Statement;
import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

        if (bid.getType() == null) {
            throw new IllegalArgumentException("Enter correct type. Type shouldn't be empty.");
        }
        if (bid.getTerm() < 1) {
            throw new IllegalArgumentException("Enter correct term. Term can not be less than one day.");
        }
        if ((bid.getArrival() == null) || (bid.getArrival().compareTo(currentDay) < 0)) {
            throw new IllegalArgumentException("Enter the correct date. Date can not be a blank or may not be early than today.");
        }
        //todo: 1. check if user with specific id exists in db
        //todo: 2. if not create user and persist him to user table with specific id  or assign him new id, if there are no id in object at all

        String query = "INSERT INTO bid(id, term, arrival, type, userId) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = ConnectionFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, bid.getId());
            statement.setShort(2, (short) bid.getTerm());
            statement.setDate(3, new java.sql.Date(bid.getArrival().getTime()));
            statement.setString(4, bid.getType().name());
            statement.setLong(5, bid.getUser().getId());
            statement.execute();
            logger.info("Method of add new bid successfully completed. Bid term set: " + bid.getTerm() + "\nArrival date set: " + bid.getArrival() + "\nType set: " + bid.getType().name());
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    bid.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating bid failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            logger.error("Exception in method add new bid: ", e);
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

        if (bid.getType() == null) {
            throw new IllegalArgumentException("Enter correct type. Type shouldn't be empty.");
        }
        if (bid.getTerm() < 1) {
            throw new IllegalArgumentException("Enter correct term. Term can not be less than one day.");
        }
        if ((bid.getArrival() == null) || (bid.getArrival().compareTo(currentDay) < 0)) {
            throw new IllegalArgumentException("Enter the correct date. Date can not be a blank or may not be early than today.");
        }
        String query = "UPDATE bid SET term = ?, arrival = ?, type = ?, userId = ? WHERE id = ?";
        try (Connection connection = ConnectionFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setShort(1, (short) bid.getTerm());
            statement.setDate(2, new java.sql.Date(bid.getArrival().getTime()));
            statement.setString(3, bid.getType().name());
            statement.setLong(4, bid.getUser().getId());
            statement.setLong(5, bid.getId());
            statement.executeUpdate();
            logger.info("Method update bid successfully completed. Bid term set: " + bid.getTerm() + "\nArrival date set: " + bid.getArrival() + "\nType set: " + bid.getType().name());
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception in method update the bid: ", e);
        }
    }

    public void delete(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id can not be less than zero");
        }
        String query = "DELETE FROM bid where id=?";
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
        String query = "SELECT * FROM bid INNER JOIN user ON userId=user.id WHERE bid.id = ?;";
        Bid bid = new Bid();
        try (Connection connection = ConnectionFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long idFromDB = resultSet.getLong(1);
                Date arrival = new java.util.Date(resultSet.getDate(2).getTime());
                short term = resultSet.getShort(3);
                String typeName = resultSet.getString(4);
                long userId = resultSet.getLong(5);
                String login = resultSet.getString(7);
                String password = resultSet.getString(8);
                String name = resultSet.getString(9);
                String mail = resultSet.getString(10);

                User user = new User();
                user.setId(userId);
                user.setLogin(login);
                user.setPassword(password);
                user.setName(name);
                user.setMail(mail);
                bid.setUser(user);

                Type type = Type.getTypeByName(typeName);
                bid.setId(idFromDB);
                bid.setTerm(term);
                bid.setArrival(arrival);
                bid.setType(type);
                //TODO: its a mistake, use JOINs
                logger.info("Method of finding the bid by id successfully completed.");
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception in method find bid by id: ", e);
        }
        return bid;
    }

    public ArrayList<Bid> getAll() {
        ArrayList<Bid> list = new ArrayList<>();
        String query = "SELECT * FROM bid INNER JOIN user ON bid.userId=user.id;";
        try (Connection connection = ConnectionFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Bid bid = new Bid();
                long id = resultSet.getLong(1);
                Date arrival = new java.util.Date(resultSet.getDate(2).getTime());
                short term = resultSet.getShort(3);
                String typeName = resultSet.getString(4);
                long userId = resultSet.getLong(5);
                String login = resultSet.getString(7);
                String password = resultSet.getString(8);
                String name = resultSet.getString(9);
                String mail = resultSet.getString(10);

                User user = new User();
                user.setId(userId);
                user.setLogin(login);
                user.setPassword(password);
                user.setName(name);
                user.setMail(mail);
                bid.setUser(user);

                Type type = Type.getTypeByName(typeName);
                bid.setId(id);
                bid.setArrival(arrival);
                bid.setTerm(term);
                bid.setType(type);
                list.add(bid);
            }
            logger.info("Method of obtaining all data of bid successfully completed.\n" +
                    "There is " + list.size() + " bid in database");
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception in method get all bid: ", e);
        }
        return list;
    }

    public ArrayList<Bid> getUserBids(User user) {
        ArrayList<Bid> list = new ArrayList<>();
        String query = "SELECT * FROM bid INNER JOIN user ON bid.userId=user.id WHERE userId = ?;";
        long userId = user.getId();
        try (Connection connection = ConnectionFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Bid bid = new Bid();
                long id = resultSet.getLong(1);
                Date arrival = new java.util.Date(resultSet.getDate(2).getTime());
                short term = resultSet.getShort(3);
                String typeName = resultSet.getString(4);
                long userIdFromDB = resultSet.getLong(5);
                String login = resultSet.getString(7);
                String password = resultSet.getString(8);
                String name = resultSet.getString(9);
                String mail = resultSet.getString(10);
                user.setId(userIdFromDB);
                user.setLogin(login);
                user.setPassword(password);
                user.setName(name);
                user.setMail(mail);
                bid.setUser(user);
                Type type = Type.getTypeByName(typeName);
                bid.setId(id);
                bid.setArrival(arrival);
                bid.setTerm(term);
                bid.setType(type);
                list.add(bid);
            }
            logger.info("Method of obtaining all data of users bid successfully completed.\n" +
                    "There is " + list.size() + " bid in database");
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception in method get all data of users bid: ", e);
        }
        return list;
    }
}
