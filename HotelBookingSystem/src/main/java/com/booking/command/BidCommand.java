package com.booking.command;

import com.booking.service.ConfigurationManager;
import com.booking.dao.BidDaoImpl;
import com.booking.model.Bid;
import com.booking.model.Client;
import com.booking.model.Type;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.log4j.Logger;

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
        Client clientOriginal = (Client) session.getAttribute("client");
        if (clientOriginal==null) {
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.CLOSE_PAGE_PATH);
            session.invalidate();
        } else {
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
            Client client = (Client) session.getAttribute("client");
            bid.setUser(client);
            //TODO: Handle error messages
            dao.add(bid);
            ArrayList<Bid> actualList = (ArrayList<Bid>) session.getAttribute("actualList");
            actualList.add(bid);
            response.setStatus(301);
            response.addHeader("Location", "myBids.jsp");
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.MY_BID_PATH);
        }
        return page;
    }
}
