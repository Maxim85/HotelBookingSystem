package com.booking.command;

import com.booking.dao.*;
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
import java.util.Collections;
import java.util.Date;

/**
 * @author Maksym.
 */
public class BillCommand implements Command {
    Logger logger = Logger.getLogger(BillCommand.class.getName());
    private static final String PARAM_BID_ID = "id";
    private static final String PARAM_NAME_LOGIN = "login";
    private final static String PARAM_NAME_TYPE = "type";
    private final static String PARAM_NAME_DATE = "arrivalDate";
    private final static String PARAM_TERM = "term";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page;
        HttpSession session = request.getSession();

        if (session.getAttribute("admin") != null) {
            BidDaoImpl bidDao = new BidDaoImpl();
            String id = request.getParameter(PARAM_BID_ID);
            long bidId = Long.parseLong(id);
            Bid bid = bidDao.find(bidId);

            ClientDaoImpl clientDao = new ClientDaoImpl();
            String login = request.getParameter(PARAM_NAME_LOGIN);
            Client client = clientDao.find(login);

            String termFromJSP = request.getParameter(PARAM_TERM);
            short term = Short.parseShort(termFromJSP);
            String typeName = request.getParameter(PARAM_NAME_TYPE);
            Type type = Type.getTypeByName(typeName);
            String arrival = request.getParameter(PARAM_NAME_DATE);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            try {
                Date arrivalDate = format.parse(arrival);
                calendar.setTime(arrivalDate);
                calendar.add(Calendar.DATE, term);
            } catch (ParseException e) {
                logger.error("Error in BillCommand when converting String to Date: " + e);
            }
            Apartment apartment = new Apartment();
            apartment.setAvailable(false);
            apartment.setCheckOutDate(calendar.getTime());
            apartment.setType(type);
            ApartmentDaoImpl apartmentDao = new ApartmentDaoImpl();
            apartmentDao.add(apartment);

            double price = type.getPrice() * term;

            Bill bill = new Bill();
            BillDaoImpl billDao = new BillDaoImpl();
            bill.setBid(bid);
            bill.setUser(client);
            bill.setApartment(apartment);
            bill.setFinalBill(price);
            billDao.add(bill);

            int numberApartments = bill.getApartment().getType().getNumberApartments();
            if (numberApartments > 0) {
                bill.getApartment().getType().setNumberApartments(numberApartments - 1);

                ArrayList<Bid> listBids = (ArrayList) session.getAttribute("actualBids");
                ArrayList<Client> onlyClient = (ArrayList) session.getAttribute("onlyClient");
                ArrayList<Bill> listBills = billDao.getAll();

                for (Bill bills : listBills) {
                    for (Bid bids : listBids) {
                        if ((bids.getId() == bills.getBid().getId()))
                            listBids.remove(bids);
                        break;
                    }
                }
                if (onlyClient != null) {
                    ArrayList<Client> fullListClients = new ArrayList<>();
                    for (Bid bids : listBids) {
                        long idClientFromBids = bids.getUser().getId();
                        Client clientFromBids = clientDao.find(idClientFromBids);
                        fullListClients.add(clientFromBids);
                    }
                    int count = Collections.frequency(fullListClients, client);
                    if ((count < 1)) {
                        onlyClient.remove(client);
                    }
                session.setAttribute("onlyClient", onlyClient);
            } else {
                ArrayList<Client> Client = new ArrayList<>();
                session.setAttribute("onlyClient", Client);
            }
            session.setAttribute("actualBids", listBids);
            response.setStatus(301);
            response.addHeader("Location", "formingBills.jsp");
        } else {
            request.setAttribute("errorMessage", "You don't have free apartments, number of " + bill.getApartment().getType() + " is " + bill.getApartment().getType().getNumberApartments() + " change your choice.");
            // page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
        }
        page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.FORMING_PAGE_PATH);
    }

    else

    {
        page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
        session.invalidate();
    }

    return page;
}
}
