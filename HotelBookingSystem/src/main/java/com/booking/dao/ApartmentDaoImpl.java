package com.booking.dao;

import com.booking.model.Apartment;
import com.booking.model.Type;
import com.mysql.jdbc.Statement;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Maksym.
 */
public class ApartmentDaoImpl implements ApartmentDao {

    Logger logger = Logger.getLogger(ApartmentDaoImpl.class.getName());

    public void add(Apartment apartment) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.AM_PM, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date currentDay = calendar.getTime();

        if (apartment.getType() == null) {
            throw new IllegalArgumentException("Enter correct type. Type shouldn't be empty.");
        }
        if ((apartment.getCheckOutDate() == null) || (apartment.getCheckOutDate().compareTo(currentDay) < 0)) {
            throw new IllegalArgumentException("Enter the correct date. Date can not be a blank or may not be early than today.");
        }

        String query = "INSERT INTO apartment(id, type, available, checkOutDate) VALUES (?, ?, ?, ?)";

        try (Connection connection = ConnectionFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, apartment.getId());
            statement.setString(2, apartment.getType().name());
            statement.setBoolean(3, apartment.isAvailable());
            statement.setDate(4, new java.sql.Date(apartment.getCheckOutDate().getTime()));
            statement.execute();
            logger.info("Method of add new apartment successfully completed. Id set: " + apartment.getId() + "\nApartment type set: " + apartment.getType().name() + "\nAvailable set: " + apartment.isAvailable() + "\nCheck out of date set: " + apartment.getCheckOutDate());
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    apartment.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating apartment failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception in method add new apartment: ", e);
        }
    }

    public void delete(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id can not be less than zero");
        }

        String query = "DELETE FROM apartment WHERE id=?";

        try (Connection connection = ConnectionFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.execute();
            logger.info("Method of deleting the apartment by id " + id + " successfully completed.");
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception in method delete apartment by id: ", e);
        }
    }

    public void deleteAll() {
        String query = "DELETE FROM apartment";
        try (Connection connection = ConnectionFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.execute();
            logger.info("Method of deleting all apartment successfully completed.");
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception in method delete all apartment: ", e);
        }
    }

    public void update(Apartment apartment) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.AM_PM, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date currentDay = calendar.getTime();

        if ("".equals(apartment.getType().name())) {
            throw new IllegalArgumentException("Enter correct type. Type shouldn't be empty.");
        }
        if ((apartment.getCheckOutDate() == null) || (apartment.getCheckOutDate().compareTo(currentDay) < 0)) {
            throw new IllegalArgumentException("Enter the correct date. Date can not be a blank or may not be early than today.");
        }

        String query = "UPDATE apartment SET type = ?, available = ?, checkOutDate = ? WHERE id = ?;";
        try (Connection connection = ConnectionFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(4, apartment.getId());
            statement.setString(1, apartment.getType().name());
            statement.setBoolean(2, apartment.isAvailable());
            statement.setDate(3, new java.sql.Date(apartment.getCheckOutDate().getTime()));
            statement.executeUpdate();
            logger.info("Method of update apartment successfully completed. Apartment type set: " + apartment.getType().name() + "\nAvailable set: " + apartment.isAvailable() + "\nCheck out of date set: " + apartment.getCheckOutDate());
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception in method update the apartment: ", e);
        }
    }

    //todo : refactor all
    public Apartment find(String name) {
        if (name == null || name.equals("")) {
            throw new IllegalArgumentException("Name shouldn't be empty");
        }
        String query = "SELECT * FROM apartment WHERE TYPE=?;";
        Apartment apartment = null;
        try (Connection connection = ConnectionFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String typeName = resultSet.getString(2);
                boolean available = resultSet.getBoolean(3);
                Date checkOutDate = resultSet.getDate(4);
                Type type = Type.getTypeByName(typeName);
                //todo : refactor all
                apartment = new Apartment();
                apartment.setId(id);
                apartment.setType(type);
                apartment.setAvailable(available);
                apartment.setCheckOutDate(checkOutDate);
                logger.info("Method of finding the apartment by name successfully completed.");
            }
        } catch (SQLException e) {
            logger.error("Exception in method find apartment by name: ", e);
        }
        return apartment;
    }

    public Apartment find(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id can not be less than zero");
        }
        String query = "SELECT * FROM apartment WHERE id=?;";
        Apartment apartment = new Apartment();
        try (Connection connection = ConnectionFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long idFromDB = resultSet.getLong(1);
                String typeName = resultSet.getString(2);
                boolean available = resultSet.getBoolean(3);
                Date checkOutDate = resultSet.getDate(4);
                Type type = Type.getTypeByName(typeName);
                apartment.setId(idFromDB);
                apartment.setType(type);
                apartment.setAvailable(available);
                apartment.setCheckOutDate(checkOutDate);
                logger.info("Method of finding the apartment by id successfully completed.");
            }
        } catch (SQLException e) {
            logger.error("Exception in method find apartment by id: ", e);
        }
        return apartment;
    }

    public ArrayList<Apartment> getAll() {
        String query = "SELECT * FROM apartment;";
        ArrayList<Apartment> list = new ArrayList<>();
        try (Connection connection = ConnectionFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Apartment apartment = new Apartment();
                int id = resultSet.getInt(1);
                String typeName = resultSet.getString(2);
                boolean available = resultSet.getBoolean(3);
                Date checkOutDate = resultSet.getDate(4);
                Type type = Type.getTypeByName(typeName);
                apartment.setId(id);
                apartment.setType(type);
                apartment.setAvailable(available);
                apartment.setCheckOutDate(checkOutDate);
                list.add(apartment);
            }
            logger.info("Method of obtaining all data of apartment successfully completed.\n" +
                    "There is " + list.size() + " apartment in database");
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception in method get all apartment: ", e);
        }
        return list;
    }
}
