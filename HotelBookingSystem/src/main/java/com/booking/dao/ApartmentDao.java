package com.booking.dao;

import com.booking.model.hotel.Apartment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maksym.
 */
public interface ApartmentDao {

    /**
     * Add apartment into DB
     *
     * @param apartment
     */
    void add(Apartment apartment);

    /**
     * Remove apartment from DB
     *
     * @param id
     */
    void delete(long id);

    /**
     * remove all apartment from DB
     */

    void deleteAll();

    /**
     * Update apartment
     *
     * @param apartment
     */
    void update(Apartment apartment);

    /**
     * Get apartment by name
     *
     * @return apartment
     */
    Apartment find(String name);

    /**
     * Get apartment by id
     *
     * @return apartment
     */
    Apartment find(long id);

    /**
     * Get all apartment
     *
     * @return
     */
    ArrayList<Apartment> getAll();

}
