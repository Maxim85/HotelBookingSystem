package com.booking.action;

import com.booking.command.Command;
import com.booking.dao.ApartmentDaoImpl;
import com.booking.dao.BillDaoImpl;
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
public class DeleteApartments implements Command {
    private static final String PARAM_APARTMENTS_ID = "id";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String page;

        if (session.getAttribute("admin") != null) {
            ApartmentDaoImpl apartmentDao = new ApartmentDaoImpl();
            BillDaoImpl billDao = new BillDaoImpl();
            long idApartments = Long.parseLong(request.getParameter(PARAM_APARTMENTS_ID));
            Apartment apartment = apartmentDao.find(idApartments);
            billDao.delete(billDao.find(apartment).getId());

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

