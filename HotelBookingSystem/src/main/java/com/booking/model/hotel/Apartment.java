package com.booking.model.hotel;

import java.util.Date;


/**
 * Created by Анна on 24.06.2015.
 */
public class Apartment {

    private long id;

    private Type type;

    private boolean available;
    private Date checkOutDate;

    public Apartment(Type type, boolean available, Date checkOutDate) {
        this.type = type;
        this.available = available;
        this.checkOutDate = checkOutDate;
    }

    public Apartment(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public boolean  isAvailable() {
        return available;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
}
