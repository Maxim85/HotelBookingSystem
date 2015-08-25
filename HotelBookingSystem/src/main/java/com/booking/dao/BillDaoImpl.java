package com.booking.dao;

import com.booking.model.hotel.Bill;
import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * @author Maksym.
 */
public class BillDaoImpl implements BillDao {
    Logger logger = Logger.getLogger(BillDaoImpl.class.getName());

    public void add(Bill bill) {
        if (bill.getFinalBill() <= 0) {
            throw new IllegalArgumentException("Final bill can not be less than zero");
        }
        String query = "INSERT INTO bill(id, finalBill) VALUES (?,?)";
        try (Connection connection = ConnectionFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, bill.getId());
            statement.setDouble(2, bill.getFinalBill());
            statement.execute();
            logger.info("Method of add new bill successfully completed. Final bill set: " + bill.getFinalBill());
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception in method add new bill: ", e);
        }
    }

    public void update(Bill bill) {
        if (bill.getFinalBill() <= 0 || bill.getId() <= 0) {
            throw new IllegalArgumentException("Final bill or id can not be less than zero");
        }
        String query = "UPDATE bill SET finalBill = ? WHERE id = ?;";
        try (Connection connection = ConnectionFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, bill.getId());
            statement.setDouble(2, bill.getFinalBill());
            statement.executeUpdate();
            logger.info("Method update bill successfully completed. Final bill set: " + bill.getFinalBill());
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception in method update the bill: ", e);
        }
    }

    public void delete(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id can not be less than zero");
        }
        String query = "DELETE FROM bill WHERE id = ?;";
        try (Connection connection = ConnectionFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDouble(1, id);
            statement.execute();
            logger.info("Method of deleting the bill by id successfully completed. Deleted finalbill by id: " + id);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception in method delete bill by id: ", e);
        }
    }

    public void deleteAll() {
        String query = "DELETE FROM bill";
        try (Connection connection = ConnectionFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.execute();
            logger.info("Method of deleting all bill successfully completed.");
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception in method delete all bill: ", e);
        }
    }

    public Bill find(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id can not be less than zero");
        }
        String query = "SELECT * from bill WHERE id = ?";
        Bill bill = new Bill();
        try (Connection connection = ConnectionFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long idFromDB = resultSet.getLong(1);
                double finallBill = resultSet.getDouble(2);
                bill.setId(idFromDB);
                bill.setFinalBill(finallBill);
                logger.info("Method of finding the bill by id successfully completed.");
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception in method find bill by id: ", e);
        }
        return bill;
    }

    public ArrayList<Bill> getAll() {
        ArrayList<Bill> list = new ArrayList<>();
        String query = "SELECT * FROM bill;";
        try (Connection connection = ConnectionFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Bill bill = new Bill();
                long id = resultSet.getLong(1);
                double finalBill = resultSet.getDouble(2);
                bill.setId(id);
                bill.setFinalBill(finalBill);
                list.add(bill);
            }
            logger.info("Method of obtaining all data of bill successfully completed.\n" +
                    "There is " + list.size() + " bill in database");
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception in method get all bill: ", e);
        }
        return list;
    }
}
