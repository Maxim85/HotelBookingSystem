package com.booking.command;

import com.booking.dao.ApartmentDaoImpl;
import com.booking.model.Apartment;
import com.booking.service.ConfigurationManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Maksym.
 */
public class EditDBApartmentsCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page;
        HttpSession session = request.getSession();

        if (session.getAttribute("admin") != null) {
            ApartmentDaoImpl apartmentDao = new ApartmentDaoImpl();
            ArrayList<Apartment> fullListApartments = apartmentDao.getAll();

            request.setAttribute("fullListApartments", fullListApartments);
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.EDIT_APARTMENTS_PATH);
        } else {
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
            session.invalidate();
        }
        return page;
    }
}
