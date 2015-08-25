package com.booking.dao;

import com.booking.model.hotel.Bill;

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
     *
     */
    ArrayList<Bill> getAll();
}
