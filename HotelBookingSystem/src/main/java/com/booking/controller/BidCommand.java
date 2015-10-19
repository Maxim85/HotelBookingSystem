package com.booking.controller;

import com.booking.dao.BidDaoImpl;
import com.booking.model.hotel.Bid;
import com.booking.model.hotel.Client;
import com.booking.model.hotel.Type;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

/**
 * @author Maksym.
 */
public class BidCommand implements Command {
    private final static String PARAM_NAME_TERM = "term";
    private final static String PARAM_NAME_TYPE = "type";
    private final static String PARAM_NAME_DATE = "arrivalDate";
    Logger logger = Logger.getLogger(BidCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page;
        HttpSession session = request.getSession();
        Bid bid = new Bid();
        BidDaoImpl dao = new BidDaoImpl();
        String type = request.getParameter(PARAM_NAME_TYPE);
        String term = request.getParameter(PARAM_NAME_TERM);
        String arrival = request.getParameter(PARAM_NAME_DATE);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        bid.setTerm(Short.parseShort(term));
        try {
            bid.setArrival(format.parse(arrival));
        } catch (ParseException e) {
            logger.error("Error in BidCommand when converting String to Date: " + e);
        }
        bid.setType(Type.valueOf(type));
        Client client = (Client) session.getAttribute("user");
        bid.setUser(client);
        dao.add(bid);
        ArrayList list = dao.getUserBids(client);
        session.setAttribute("list", list);
        response.setStatus(301);
        response.addHeader("Location","myBids.jsp");
        page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.MY_BID_PATH);
        return page;
    }
}
