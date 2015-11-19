package com.booking.dao;

import com.booking.model.Client;

import java.util.ArrayList;

/**
 * @author Maksym.
 */
public interface ClientDao {
    /**
     * Add new client into DB
     *
     * @param client
     */
    void add(Client client);

    /**
     * Update client into DB
     *
     * @param client
     */
    void update(Client client);

    /**
     * Get client by name
     *
     * @param login
     * @return
     */
    Client find(String login);

    /**
     * Get client by id
     *
     * @param id
     * @return
     */
    Client find(long id);

    /**
     * Delete client by id
     *
     * @param id
     */
    void delete(long id);

    /**
     * Delete all client from DB
     */
    void deleteAll();

    /**
     * Get all client
     *
     * @return
     */
    ArrayList<Client> getAll();
}
