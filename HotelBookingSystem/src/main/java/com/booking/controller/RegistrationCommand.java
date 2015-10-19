package com.booking.controller;

import com.booking.dao.AdminDaoImpl;
import com.booking.model.hotel.Admin;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Maksym.
 */
public class RegistrationCommand implements Command {
    private static final String PARAM_NAME_NAME = "name";
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String PARAM_NAME_MAIL = "mail";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = null;
        String name = request.getParameter(PARAM_NAME_NAME);
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String password = request.getParameter(PARAM_NAME_PASSWORD);
        String mail = request.getParameter(PARAM_NAME_MAIL);
        Admin admin = new Admin();
        admin.setName(name);
        admin.setLogin(login);
        admin.setPassword(password);
        admin.setMail(mail);
        AdminDaoImpl dao = new AdminDaoImpl();
        dao.add(admin);
        return page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.LOGIN_PAGE_PATH);
    }
}
