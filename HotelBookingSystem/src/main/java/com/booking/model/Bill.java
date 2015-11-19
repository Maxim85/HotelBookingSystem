package com.booking.model;//переименование, bill
//клиент


/**
 * Created by Анна on 24.06.2015.
 */
public class Bill {

    private double finalBill;
    private long id;
    private User user;
    private Apartment apartment;
    private Bid bid;

    public void setBid(Bid bid) {
        this.bid = bid;
    }

    public Bid getBid() {
        return bid;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public User getUser() {

        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getFinalBill() {
        return finalBill;
    }

    public void setFinalBill(double finalBill) {
        this.finalBill = finalBill;
    }
}
