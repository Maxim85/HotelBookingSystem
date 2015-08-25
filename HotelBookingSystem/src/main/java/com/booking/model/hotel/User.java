package com.booking.model.hotel;

/**
 * Created by Анна on 25.06.2015.
 */
public class User {

    private long id;
    private String login;
    private String password;

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(long id) {
        this.id = id;
    }
}
