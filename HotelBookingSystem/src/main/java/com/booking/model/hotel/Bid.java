package com.booking.model.hotel;

import java.util.Date;

/**
 * Created by Анна on 24.06.2015.
 */
public class Bid {

    private Date arrival;
    private short term;
    private long id;

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
