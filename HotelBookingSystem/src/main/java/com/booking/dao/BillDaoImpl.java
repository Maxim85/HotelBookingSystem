package com.booking.dao;

import com.booking.model.*;
import com.mysql.jdbc.Statement;
import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;


/**
 * @author Maksym.
 */
public class BillDaoImpl implements BillDao {
    Logger logger = Logger.getLogger(BillDaoImpl.class.getName());

    public void add(Bill bill) {
        if (bill.getFinalBill() <= 0) {
            throw new IllegalArgumentException("Final bill can not be less than zero");
        }
        String query = "INSERT INTO bill(id, finalBill, userId, apartmentId, bidId) VALUES (?,?,?,?,?)";
        try (Connection connection = ConnectionFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, bill.getId());
            statement.setDouble(2, bill.getFinalBill());
            statement.setLong(3, bill.getUser().getId());
            statement.setLong(4, bill.getApartment().getId());
            statement.setLong(5, bill.getBid().getId());
            statement.execute();
            logger.info("Method of add new bill successfully completed. Final bill set: " + bill.getFinalBill());
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    bill.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating bill failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception in method add new bill: ", e);
        }
    }

    public void update(Bill bill) {
        if (bill.getFinalBill() <= 0 || bill.getId() <= 0) {
            throw new IllegalArgumentException("Final bill or id can not be less than zero");
        }
        String query = "UPDATE bill SET finalBill = ?, userId = ?, apartmentId = ?, bidId = ? WHERE id = ?;";
        try (Connection connection = ConnectionFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDouble(1, bill.getFinalBill());
            statement.setLong(2, bill.getUser().getId());
            statement.setLong(3, bill.getApartment().getId());
            statement.setLong(4, bill.getBid().getId());
            statement.setLong(5, bill.getId());
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
        String query = "DELETE bill, apartment, bid from bill join apartment on bill.apartmentId = apartment.Id join bid on bill.bidId = bid.id WHERE bill.id = ?;";
        try (Connection connection = ConnectionFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
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
        String query = "SELECT * from ((bill INNER JOIN user ON bill.userId = user.id) INNER Join apartment ON bill.apartmentId = apartment.id)INNER JOIN bid ON bill.bidId=bid.id WHERE bill.id = ?";
        Bill bill = new Bill();
        try (Connection connection = ConnectionFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                Apartment apartment = new Apartment();
                Bid bid = new Bid();

                long idFromDB = resultSet.getLong(1);
                double finallBill = resultSet.getDouble(2);
                long userId = resultSet.getLong(3);
                long apartmentId = resultSet.getLong(4);
                long bidId = resultSet.getLong(5);

                String login = resultSet.getString(7);
                String password = resultSet.getString(8);
                String name = resultSet.getString(9);
                String mail = resultSet.getString(10);

                String typeNameApartment = resultSet.getString(12);
                boolean available = resultSet.getBoolean(13);
                Date checkOutDate = new java.util.Date(resultSet.getDate(14).getTime());

                Date arrival = new java.util.Date(resultSet.getDate(16).getTime());
                short term = (short) resultSet.getInt(17);
                String typeNameBid = resultSet.getString(18);

                user.setId(userId);
                user.setLogin(login);
                user.setPassword(password);
                user.setName(name);
                user.setMail(mail);
                bill.setUser(user);

                Type typeApartment = Type.getTypeByName(typeNameApartment);
                apartment.setId(apartmentId);
                apartment.setType(typeApartment);
                apartment.setAvailable(available);
                apartment.setCheckOutDate(checkOutDate);
                bill.setApartment(apartment);

                Type typeBid = Type.getTypeByName(typeNameBid);
                bid.setId(bidId);
                bid.setArrival(arrival);
                bid.setTerm(term);
                bid.setType(typeBid);
                bid.setUser(user);
                bill.setBid(bid);

                bill.setId(idFromDB);
                bill.setFinalBill(finallBill);
                logger.info("Method of finding the bill by id successfully completed.");
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception in method find bill by id: ", e);
        }
        return bill;
    }

    public Bill find(Bid bid) {
        String query = "SELECT * from ((bill INNER JOIN user ON bill.userId = user.id) INNER Join apartment ON bill.apartmentId = apartment.id)INNER JOIN bid ON bill.bidId=bid.id WHERE bill.bidId = ?";
        long bidId = bid.getId();
        Bill bill = null;
        try (Connection connection = ConnectionFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, bidId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                Apartment apartment = new Apartment();

                long id = resultSet.getLong(1);
                double finallBill = resultSet.getDouble(2);
                long userId = resultSet.getLong(3);
                long apartmentId = resultSet.getLong(4);
                long bidIdFromDB = resultSet.getLong(5);

                String login = resultSet.getString(7);
                String password = resultSet.getString(8);
                String name = resultSet.getString(9);
                String mail = resultSet.getString(10);

                String typeNameApartment = resultSet.getString(12);
                boolean available = resultSet.getBoolean(13);
                Date checkOutDate = new java.util.Date(resultSet.getDate(14).getTime());

                Date arrival = new java.util.Date(resultSet.getDate(16).getTime());
                short term = (short) resultSet.getInt(17);
                String typeNameBid = resultSet.getString(18);

                user.setId(userId);
                user.setLogin(login);
                user.setPassword(password);
                user.setName(name);
                user.setMail(mail);

                Type typeApartment = Type.getTypeByName(typeNameApartment);
                apartment.setId(apartmentId);
                apartment.setType(typeApartment);
                apartment.setAvailable(available);
                apartment.setCheckOutDate(checkOutDate);

                Type typeBid = Type.getTypeByName(typeNameBid);
                bid.setId(bidIdFromDB);
                bid.setArrival(arrival);
                bid.setTerm(term);
                bid.setType(typeBid);
                bid.setUser(user);

                bill = new Bill();
                bill.setUser(user);
                bill.setApartment(apartment);
                bill.setBid(bid);
                bill.setId(id);
                bill.setFinalBill(finallBill);
                logger.info("Method of finding the bill by bid successfully completed.");
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception in method find bill by id: ", e);
        }
        return bill;
    }

    public Bill find(Apartment apartment) {
        String query = "SELECT * from ((bill INNER JOIN user ON bill.userId = user.id) INNER Join apartment ON bill.apartmentId = apartment.id)INNER JOIN bid ON bill.bidId=bid.id WHERE bill.apartmentId = ?";
        long apartmentId = apartment.getId();
        Bill bill = null;
        try (Connection connection = ConnectionFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, apartmentId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                Bid bid = new Bid();

                long id = resultSet.getLong(1);
                double finallBill = resultSet.getDouble(2);
                long userId = resultSet.getLong(3);
                long apartmentIdFromDB = resultSet.getLong(4);
                long bidId = resultSet.getLong(5);

                String login = resultSet.getString(7);
                String password = resultSet.getString(8);
                String name = resultSet.getString(9);
                String mail = resultSet.getString(10);

                String typeNameApartment = resultSet.getString(12);
                boolean available = resultSet.getBoolean(13);
                Date checkOutDate = new java.util.Date(resultSet.getDate(14).getTime());

                Date arrival = new java.util.Date(resultSet.getDate(16).getTime());
                short term = (short) resultSet.getInt(17);
                String typeNameBid = resultSet.getString(18);

                user.setId(userId);
                user.setLogin(login);
                user.setPassword(password);
                user.setName(name);
                user.setMail(mail);

                Type typeApartment = Type.getTypeByName(typeNameApartment);
                apartment.setId(apartmentIdFromDB);
                apartment.setType(typeApartment);
                apartment.setAvailable(available);
                apartment.setCheckOutDate(checkOutDate);

                Type typeBid = Type.getTypeByName(typeNameBid);
                bid.setId(bidId);
                bid.setArrival(arrival);
                bid.setTerm(term);
                bid.setType(typeBid);
                bid.setUser(user);

                bill = new Bill();
                bill.setUser(user);
                bill.setApartment(apartment);
                bill.setBid(bid);
                bill.setId(id);
                bill.setFinalBill(finallBill);
                logger.info("Method of finding the bill by apartment successfully completed.");
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception in method find bill by id: ", e);
        }
        return bill;
    }

    public Bill find(User user) {
        String query = "SELECT * from ((bill INNER JOIN user ON bill.userId = user.id) INNER Join apartment ON bill.apartmentId = apartment.id)INNER JOIN bid ON bill.bidId=bid.id WHERE bill.userId = ?";
        long userId = user.getId();
        Bill bill = null;
        try (Connection connection = ConnectionFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Apartment apartment = new Apartment();
                Bid bid = new Bid();

                long id = resultSet.getLong(1);
                double finallBill = resultSet.getDouble(2);
                long userIdFromDB = resultSet.getLong(3);
                long apartmentId = resultSet.getLong(4);
                long bidId = resultSet.getLong(5);

                String login = resultSet.getString(7);
                String password = resultSet.getString(8);
                String name = resultSet.getString(9);
                String mail = resultSet.getString(10);

                String typeNameApartment = resultSet.getString(12);
                boolean available = resultSet.getBoolean(13);
                Date checkOutDate = new java.util.Date(resultSet.getDate(14).getTime());

                Date arrival = new java.util.Date(resultSet.getDate(16).getTime());
                short term = (short) resultSet.getInt(17);
                String typeNameBid = resultSet.getString(18);

                user.setId(userIdFromDB);
                user.setLogin(login);
                user.setPassword(password);
                user.setName(name);
                user.setMail(mail);

                Type typeApartment = Type.getTypeByName(typeNameApartment);
                apartment.setId(apartmentId);
                apartment.setType(typeApartment);
                apartment.setAvailable(available);
                apartment.setCheckOutDate(checkOutDate);

                Type typeBid = Type.getTypeByName(typeNameBid);
                bid.setId(bidId);
                bid.setArrival(arrival);
                bid.setTerm(term);
                bid.setType(typeBid);
                bid.setUser(user);

                bill = new Bill();
                bill.setUser(user);
                bill.setApartment(apartment);
                bill.setBid(bid);
                bill.setId(id);
                bill.setFinalBill(finallBill);
                logger.info("Method of finding the bill by user successfully completed.");
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception in method find bill by id: ", e);
        }
        return bill;
    }

    public ArrayList<Bill> getAll() {
        ArrayList<Bill> list = new ArrayList<>();
        String query = "SELECT * from ((bill INNER JOIN user ON bill.userId = user.id) INNER Join apartment ON bill.apartmentId = apartment.id)INNER JOIN bid ON bill.bidId=bid.id;";
//        String query = "SELECT * from bill;";
        try (Connection connection = ConnectionFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Bid bid = new Bid();
                Bill bill = new Bill();
                User user = new User();
                Apartment apartment = new Apartment();

                long idFromDB = resultSet.getLong(1);
                double finallBill = resultSet.getDouble(2);
                long userId = resultSet.getLong(3);
                long apartmentId = resultSet.getLong(4);
                long bidId = resultSet.getLong(5);

                String login = resultSet.getString(7);
                String password = resultSet.getString(8);
                String name = resultSet.getString(9);
                String mail = resultSet.getString(10);

                String typeNameApartment = resultSet.getString(12);
                boolean available = resultSet.getBoolean(13);
                Date checkOutDate = new java.util.Date(resultSet.getDate(14).getTime());

                Date arrival = new java.util.Date(resultSet.getDate(16).getTime());
                short term = (short) resultSet.getInt(17);
                String typeNameBid = resultSet.getString(18);
                //long userIdBid = resultSet.getLong(19);

                user.setId(userId);
                user.setLogin(login);
                user.setPassword(password);
                user.setName(name);
                user.setMail(mail);
                bill.setUser(user);

                Type typeApartment = Type.getTypeByName(typeNameApartment);
                apartment.setId(apartmentId);
                apartment.setType(typeApartment);
                apartment.setAvailable(available);
                apartment.setCheckOutDate(checkOutDate);
                bill.setApartment(apartment);

                Type typeBid = Type.getTypeByName(typeNameBid);
                bid.setId(bidId);
                bid.setArrival(arrival);
                bid.setTerm(term);
                bid.setType(typeBid);
                bid.setUser(user);
                bill.setBid(bid);

                bill.setId(idFromDB);
                bill.setFinalBill(finallBill);
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
