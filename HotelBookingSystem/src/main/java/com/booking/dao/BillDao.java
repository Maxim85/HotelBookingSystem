package com.booking.dao;

import com.booking.model.*;

import java.util.ArrayList;


/**
 * @author Maksym.
 */
public interface BillDao {

    /**
     * Add bill into DB
     *
     * @param bill
     */
    void add(Bill bill);

    /**
     * Update bill into DB
     *
     * @param bill
     */
    void update(Bill bill);

    /**
     * Delete bill from DB by id
     *
     * @param id
     */
    void delete(long id);

    /**
     * Delete all bill from DB
     */
    void deleteAll();

    /**
     * Get bill from DB by id
     *
     * @param id
     * @return
     */
    Bill find(long id);

    /**
     * Get bill from DB by bid
     *
     * @param bid
     * @return
     */
    Bill find(Bid bid);

    /**
     * Get bill from DB by apartment
     *
     * @param apartment
     * @return
     */
    Bill find(Apartment apartment);

    /**
     * Get bill from DB by user
     *
     * @param user
     * @return
     */
    Bill find(User user);

    /**
     *Get all bill from DB
     * @return ArrayList
     */
    ArrayList<Bill> getAll();
}
