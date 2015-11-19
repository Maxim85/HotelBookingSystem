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
public class UpdateBills implements Command {
    private static final String PARAM_BILL_ID = "id";
    private static final String PARAM_NAME_LOGIN = "login";
    private final static String PARAM_NAME_TYPE = "type";
    private final static String PARAM_NAME_DATE = "checkOutDate";
    private final static String PARAM_TERM = "term";
    Logger logger = Logger.getLogger(UpdateBills.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page;

        HttpSession session = request.getSession();
        if (session.getAttribute("admin") != null) {
            BillDaoImpl billDao = new BillDaoImpl();
            long idBill = Long.parseLong(request.getParameter(PARAM_BILL_ID));
            Bill billFromDB = billDao.find(idBill);


            String login = request.getParameter(PARAM_NAME_LOGIN);
            ClientDaoImpl clientDao = new ClientDaoImpl();
            Client client = clientDao.find(login);

            String termFromJSP = request.getParameter(PARAM_TERM);
            short term = Short.parseShort(termFromJSP);

            Apartment apartmentFromBill = billFromDB.getApartment();
            String typeName = request.getParameter(PARAM_NAME_TYPE);
            Type type = Type.getTypeByName(typeName);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String checkOutString = request.getParameter(PARAM_NAME_DATE);
            try {
                Date checkOut = format.parse(checkOutString);
                apartmentFromBill.setCheckOutDate(checkOut);
            } catch (ParseException e) {
                logger.error("Error in UpdateBills when converting String to Date: " + e);
            }

            apartmentFromBill.setAvailable(false);
            apartmentFromBill.setType(type);
            ApartmentDaoImpl apartmentDao = new ApartmentDaoImpl();
            apartmentDao.update(apartmentFromBill);

            double price = type.getPrice() * term;

            Bid bidFromBill = billFromDB.getBid();
            BidDaoImpl bidDao = new BidDaoImpl();
            bidFromBill.setType(type);
            bidFromBill.setTerm(term);
            bidFromBill.setUser(client);
            Calendar checkIn = Calendar.getInstance();
            Date checkOut = null;
            try {
                checkOut = format.parse(checkOutString);
            } catch (ParseException e) {
                logger.error("Error in UpdateBills when converting String to Date: " + e);
            }
            checkIn.setTime(checkOut);
            checkIn.add(Calendar.DATE, -term);
            bidFromBill.setArrival(checkIn.getTime());
            bidDao.update(bidFromBill);

            billFromDB.setBid(bidFromBill);
            billFromDB.setUser(client);
            billFromDB.setApartment(apartmentFromBill);
            billFromDB.setFinalBill(price);
            billDao.update(billFromDB);

            ArrayList<Bill> fullList = billDao.getAll();
            request.setAttribute("fullListBills", fullList);

            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.EDIT_BILLS_PATH);
        } else {
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
            session.invalidate();
        }
        return page;
    }
}


