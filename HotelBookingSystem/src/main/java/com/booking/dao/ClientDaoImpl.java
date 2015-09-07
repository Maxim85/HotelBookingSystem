package com.booking.dao;

import com.booking.model.hotel.Client;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Maksym.
 */
public class ClientDaoImpl implements ClientDao {
    private static Logger logger = Logger.getLogger(ClientDaoImpl.class.getName());

    public void add(Client client) {
        if (client.getPassword() == null || client.getPassword().equals("")) {
            throw new IllegalArgumentException("Enter correct password. Password shouldn't be empty");
        }
        if (client.getLogin() == null || client.getLogin().equals("")) {
            throw new IllegalArgumentException("Enter correct login. Login shouldn't be empty");
        }
        String query = "INSERT INTO user (id, login, password) VALUES (?, ?, ?)";
        try (Connection connection = ConnectionFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, client.getId());
            statement.setString(2, client.getLogin());
            statement.setString(3, client.getPassword());
            statement.execute();
            logger.info("Method add a new client successful done.\n" +
                    "It created a new client. Login of new client set: " + client.getLogin() + "\nPassword of new client set: " + client.getPassword());
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception in method add a new client: ", e);
        }
    }

    public void update(Client client) {
        if (client.getPassword() == null || client.getPassword().equals("")) {
            throw new IllegalArgumentException("Enter correct password. Password shouldn't be empty");
        }
        if (client.getLogin() == null || client.getLogin().equals("")) {
            throw new IllegalArgumentException("Enter correct login. Login shouldn't be empty");
        }
        String query = "UPDATE user SET login = ?, password = ? Where id = ?";
        try (Connection connection = ConnectionFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, client.getLogin());
            statement.setString(2, client.getPassword());
            statement.setLong(3, client.getId());
            statement.executeUpdate();
            //todo: write correct messages
            logger.info("Method update the client successful done.\n" +
                    "Login set: " + client.getLogin() + "\nPassword set: " + client.getPassword());
        } catch (SQLException e) {
            logger.error("Exception in method update the client: ", e);
        }
    }

    public Client find(String login) {
        if (login == null || login.equals("")) {
            throw new IllegalArgumentException("Login shouldn't be empty");
        }
        Client client = new Client();
        String query = "SELECT * FROM user WHERE login=?";
        try (Connection connection = ConnectionFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong(1);
                String loginFromDB = resultSet.getString(2);
                String password = resultSet.getString(3);
                client.setId(id);
                client.setLogin(loginFromDB);
                client.setPassword(password);
                logger.info("Method of finding the client by name successfully completed.");
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception in method find client by login: ", e);
        }
        return client;
    }

    public Client find(long id) {
        /*if (id <= 0) {
            throw new IllegalArgumentException("Id can not be less than zero");
        }*/
        Client client = null;
        String query = "SELECT * FROM user WHERE id = ?;";
        try (Connection connection = ConnectionFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            //todo: wrap with while
            while (resultSet.next()) {
                client = new Client();
                long idFromDB = resultSet.getLong(1);
                String login = resultSet.getString(2);
                String password = resultSet.getString(3);
                client.setId(idFromDB);
                client.setLogin(login);
                client.setPassword(password);
                logger.info("Method of finding the client by id successfully completed.");
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception in method find client by id: ", e);
        }
        return client;
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
            logger.info("Method of deleting the client by id successfully completed.");
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception in method delete the client by id: ", e);
        }
    }

    public void deleteAll() {
        String query = "DELETE FROM user";
        try (Connection connection = ConnectionFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.execute();
            logger.info("Method of deleting all client successfully completed.");
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception in method delete all client: ", e);
        }
    }

    public ArrayList<Client> getAll() {
        ArrayList<Client> list = new ArrayList<>();
        String query = "SELECT * FROM USER;";
        try (Connection connection = ConnectionFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Client client = new Client();
                long id = resultSet.getLong(1);
                String name = resultSet.getString(2);
                String password = resultSet.getString(3);
                client.setId(id);
                client.setLogin(name);
                client.setPassword(password);
                list.add(client);
            }
            logger.info("Method of obtaining all data of client successfully completed.\n" +
                    "There is " + list.size() + " client in database");
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception in method get all client: ", e);
        }
        return list;
    }
}
