package com.booking.model;

import java.util.Date;

/**
 * Created by Анна on 24.06.2015.
 */
public class Bid {

    private Date arrival;
    private short term;
    private long id;
    private Type type;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Type getType() {

        return type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getArrival() {
        return arrival;
    }

    public int getTerm() {
        return term;
    }

    public void setArrival(Date arrival) {
        this.arrival = arrival;
    }

    public void setTerm(short term) {
        this.term = term;
    }
}
