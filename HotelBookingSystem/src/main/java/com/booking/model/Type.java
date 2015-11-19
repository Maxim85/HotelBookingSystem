package com.booking.model;

/**
 * Created by Анна on 25.06.2015.
 */
public enum Type {
//Http protocol
    // servlet, jsp р html + css

    //Промішленное програм на джава Блинов
//414 page

    STANDARD_1("Standard", 1, 50, 20),//rename all Type
    STANDARD_2("Standard", 2, 75, 10),
    STANDARD_3("Standard", 3, 120, 5),
    LUX_1("Lux", 1, 100, 15),
    LUX_2("Lux", 2, 180, 7),
    LUX_3("Lux", 3, 260, 3);

    private String name;
    private int persons;
    private double price;
    private int numberApartments;

    Type(String name, int persons, double price, int numberApartments) {
        this.name = name;
        this.persons = persons;
        this.price = price;
        this.numberApartments = numberApartments;
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

    public int getNumberApartments() {
        return numberApartments;
    }

    public void setNumberApartments(int numberApartments) {
        this.numberApartments = numberApartments;
    }


}
