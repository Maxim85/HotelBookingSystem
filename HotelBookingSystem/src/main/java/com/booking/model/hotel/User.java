package com.booking.model.hotel;

/**
 * Created by Анна on 25.06.2015.
 */
public class User {

    private long id;
    private String login;
    private String password;
    private String mail;
    private String name;

    public String getMail() {
        return mail;
    }

    public String getName() {
        return name;
    }

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

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setName(String name) {
        this.name = name;
    }
}
