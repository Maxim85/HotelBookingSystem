package com.booking.model.hotel;

/**
 * Created by Анна on 25.06.2015.
 */
public enum Type {
//Http protocol
    // servlet, jsp р html + css

    //Промішленное програм на джава Блинов
//414 page

    STANDART_1("STANDART_1", 1, 50),
    STANDART_2("STANDART_2", 2, 75),
    STANDART_3("STANDART_3", 3, 120),
    LUX_1("LUX_1", 1, 100),
    LUX_2("LUX_2", 2, 180),
    LUX_3("LUX_3", 3, 260);

    private String name;
    private int persons;
    private double price;

    Type(String name, int persons, double price) {
        this.name = name;
        this.persons = persons;
        this.price = price;
    }

    public static Type getTypeByName(String typeName) {
        Type type = Type.valueOf(typeName);
        return type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPersons() {
        return persons;
    }

    public void setPersons(int persons) {
        this.persons = persons;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
