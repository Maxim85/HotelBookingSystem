package com.booking.model.hotel;//переименование, bill
//клиент


/**
 * Created by Анна on 24.06.2015.
 */
public class Bill {

    private double finalBill;
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getFinalBill(){
        return finalBill;
    }

    public void setFinalBill(double finalBill){
        this.finalBill=finalBill;
    }
}
