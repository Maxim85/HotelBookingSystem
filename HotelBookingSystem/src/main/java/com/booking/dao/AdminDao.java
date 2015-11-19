package com.booking.dao;

import com.booking.model.Admin;


import java.util.ArrayList;

/**
 * @author Maksym.
 */
public interface AdminDao {
    /**
     * Add new administrator into DB
     *
     * @param admin
     */
    void add(Admin admin);

    /**
     * Update administrator into DB
     *
     * @param admin
     */
    void update(Admin admin);

    /**
     * Get administrator by name
     *
     * @param login
     * @return
     */
    Admin find(String login);

    /**
     * Get administrator by id
     *
     * @param id
     * @return
     */
    Admin find(long id);

    /**
     * Delete administrator by id
     *
     * @param id
     */
    void delete(long id);

    /**
     * Delete all administrator
     *
     * @return
     */
    void deleteAll();

    /**
     * Get all administrator
     *
     * @return
     */
    ArrayList<Admin> getAll();
}

