package com.booking.dao;


import com.booking.model.hotel.Admin;
import com.sun.istack.internal.Nullable;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Maksym.
 */
public class AdminDaoImpl implements AdminDao {
    //    TODO: Refactor all methods
    private static Logger logger = Logger.getLogger(AdminDaoImpl.class.getName());

    public void add(Admin admin) {
        if (admin.getPassword() == null || admin.getPassword().equals("")) {
            throw new IllegalArgumentException("Enter correct password. Password shouldn't be empty");
        }
        if (admin.getLogin() == null || admin.getLogin().equals("")) {
            throw new IllegalArgumentException("Enter correct login. Login shouldn't be empty");
        }
        String query = "INSERT INTO user (id, login, password) VALUES (?, ?, ?)";
        try (Connection connection = ConnectionFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, admin.getId());
            statement.setString(2, admin.getLogin());
            statement.setString(3, admin.getPassword());
            statement.execute();
            logger.info("Method add a new admin successful done.\n" +
                    "It created a new admin. Login of new admin set: " + admin.getLogin() + "\nPassword of new admin set: " + admin.getPassword());
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception in method add a new admin: ", e);
        }
    }

    public void update(Admin admin) {
        if (admin.getPassword() == null || admin.getPassword().equals("")) {
            throw new IllegalArgumentException("Enter correct password. Password shouldn't be empty");
        }
        if (admin.getLogin() == null || admin.getLogin().equals("")) {
            throw new IllegalArgumentException("Enter correct login. Login shouldn't be empty");
        }
        String query = "UPDATE user SET login = ?, password = ? Where id = ?";
        try (Connection connection = ConnectionFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, admin.getLogin());
            statement.setString(2, admin.getPassword());
            statement.setLong(3, admin.getId());
            statement.executeUpdate();
            //todo: write correct messages
            logger.info("Method update admin successful done.\n" +
                    "Login set: " + admin.getLogin() + "\nPassword set: " + admin.getPassword());
        } catch (SQLException e) {
            logger.error("Exception in method update the admin: ", e);
        }
    }

    @Nullable
    public Admin find(String login) {
        if (login == null || login.equals("")) {
            throw new IllegalArgumentException("Login shouldn't be empty");
        }
        String query = "SELECT * FROM user WHERE login=?";
        Admin admin = new Admin();
        try (Connection connection = ConnectionFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong(1);
                String loginFromDB = resultSet.getString(2);
                String password = resultSet.getString(3);
                admin.setId(id);
                admin.setLogin(loginFromDB);
                admin.setPassword(password);
                logger.info("Method of finding the administrator by name successfully completed.");
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception in method find admin by login: ", e);
        }
        return admin;
    }

    @Nullable
    public Admin find(long id) {
        /*if (id <= 0) {
            throw new IllegalArgumentException("Id can not be less than zero");
        }*/
        Admin admin = null;
        String query = "SELECT * FROM user WHERE id = ?;";
        try (Connection connection = ConnectionFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            //todo: wrap with while
            while (resultSet.next()) {
                admin = new Admin();
                long idFromDB = resultSet.getLong(1);//idFromDB
                String login = resultSet.getString(2);
                String password = resultSet.getString(3);
                admin.setId(idFromDB);
                admin.setLogin(login);
                admin.setPassword(password);
                logger.info("Method of finding the administrator by id successfully completed.");
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception in method find admin by id: ", e);
        }
        return admin;
    }

    public void delete(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id can not be less than zero");
        }
        String query = "DELETE FROM user WHERE id = ?";
        try (Connection connection = ConnectionFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.execute();
            logger.info("Method of deleting the administrator by id successfully completed.");
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception in method delete admin by id: ", e);
        }
    }

    public void deleteAll() {
        String query = "DELETE FROM user";
        try (Connection connection = ConnectionFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.execute();
            logger.info("Method of deleting all admin successfully completed.");
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception in method delete all admin: ", e);
        }
    }

    public ArrayList<Admin> getAll() {
        ArrayList<Admin> list = new ArrayList<Admin>();
        String query = "SELECT * FROM USER;";
        try (Connection connection = ConnectionFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Admin admin = new Admin();
                long id = resultSet.getLong(1);
                String name = resultSet.getString(2);
                String password = resultSet.getString(3);
                admin.setId(id);
                admin.setLogin(name);
                admin.setPassword(password);
                list.add(admin);
            }
            logger.info("Method of obtaining all data of administrators successfully completed.\n" +
                    "There is " + list.size() + " admin in database");
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception in method get all admin: ", e);
        }
        return list;
    }
}
