package com.booking.dao;

import com.booking.model.hotel.Bid;

import java.util.ArrayList;

/**
 * @author Maksym.
 */
public interface BidDao {

    /**
     * Add new bid into DB
     *
     * @param bid
     */
    void add(Bid bid);

    /**
     * Update bid into DB
     *
     * @param bid
     */
    void update(Bid bid);

    /**
     * Delete bid from DB by id
     *
     * @param id
     */
    void delete(long id);

    /**
     * Delete all bid from DB
     */
    void deleteAll();

    /**
     * Get Bid from DB by id
     *
     * @param id
     * @return
     */
    Bid find(long id);

    /**
     * Get all bid from DB
     */
    ArrayList<Bid> getAll();


}
