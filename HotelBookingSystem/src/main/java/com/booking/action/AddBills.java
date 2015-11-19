package com.booking.action;

import com.booking.command.Command;
import com.booking.dao.ApartmentDaoImpl;
import com.booking.dao.BidDaoImpl;
import com.booking.dao.BillDaoImpl;
import com.booking.dao.ClientDaoImpl;
import com.booking.model.*;
import com.booking.service.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Maksym.
 */
public class AddBills implements Command {
    private static final String PARAM_NAME_LOGIN = "login";
    private final static String PARAM_NAME_TYPE = "type";
    private final static String PARAM_NAME_DATE = "checkOutDate";
    private final static String PARAM_TERM = "term";
    Logger logger = Logger.getLogger(AddBills.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page;

        HttpSession session = request.getSession();
        if (session.getAttribute("admin") != null) {
            String login = request.getParameter(PARAM_NAME_LOGIN);
            ClientDaoImpl clientDao = new ClientDaoImpl();
            Client client = clientDao.find(login);

            String termFromJSP = request.getParameter(PARAM_TERM);
            short term = Short.parseShort(termFromJSP);

            Apartment apartment = new Apartment();
            String typeName = request.getParameter(PARAM_NAME_TYPE);
            Type type = Type.getTypeByName(typeName);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String checkOutString = request.getParameter(PARAM_NAME_DATE);
            try {
                Date checkOut = format.parse(checkOutString);
                apartment.setCheckOutDate(checkOut);
            } catch (ParseException e) {
                logger.error("Error in AddBills when converting String to Date: " + e);
            }

            apartment.setAvailable(false);
            apartment.setType(type);
            ApartmentDaoImpl apartmentDao = new ApartmentDaoImpl();
            apartmentDao.add(apartment);


            double price = type.getPrice() * term;

            Bid bid = new Bid();
            BidDaoImpl bidDao = new BidDaoImpl();
            bid.setType(type);
            bid.setTerm(term);
            bid.setUser(client);
            Calendar checkIn = Calendar.getInstance();
            Date checkOut = null;
            try {
                checkOut = format.parse(checkOutString);
            } catch (ParseException e) {
                logger.error("Error in AddBills when converting String to Date: " + e);
            }
            checkIn.setTime(checkOut);
            checkIn.add(Calendar.DATE, -term);
            bid.setArrival(checkIn.getTime());
            bidDao.add(bid);

            Bill bill = new Bill();
            BillDaoImpl billDao = new BillDaoImpl();
            bill.setBid(bid);
            bill.setUser(client);
            bill.setApartment(apartment);
            bill.setFinalBill(price);
            billDao.add(bill);

            ArrayList<Bill> fullList = billDao.getAll();
            session.setAttribute("fullListBills", fullList);

            response.setStatus(301);
            response.addHeader("Location", "editDBBills.jsp");

            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.EDIT_BILLS_PATH);
        } else {
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
            session.invalidate();
        }
        return page;
    }
}
