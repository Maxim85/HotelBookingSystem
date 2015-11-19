package com.booking.action;

import com.booking.command.Command;
import com.booking.dao.BidDaoImpl;
import com.booking.dao.ClientDaoImpl;
import com.booking.model.Bid;
import com.booking.model.Client;
import com.booking.model.Type;
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
import java.util.Date;

/**
 * @author Maksym.
 */
public class AddBids implements Command {
    private static final String PARAM_NAME_LOGIN = "login";
    private final static String PARAM_NAME_TYPE = "type";
    private final static String PARAM_NAME_DATE = "arrival";
    private final static String PARAM_TERM = "term";
    Logger logger = Logger.getLogger(AddBids.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page;
        HttpSession session = request.getSession();
        if (session.getAttribute("admin") != null) {
            Bid bid = new Bid();
            BidDaoImpl bidDao = new BidDaoImpl();

            String login = request.getParameter(PARAM_NAME_LOGIN);
            ClientDaoImpl clientDao = new ClientDaoImpl();
            Client client = clientDao.find(login);
            bid.setUser(client);

            String termFromJSP = request.getParameter(PARAM_TERM);
            short term = Short.parseShort(termFromJSP);
            bid.setTerm(term);

            String typeName = request.getParameter(PARAM_NAME_TYPE);
            Type type = Type.getTypeByName(typeName);
            bid.setType(type);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String arrivalString = request.getParameter(PARAM_NAME_DATE);
            try {
                Date arrival = format.parse(arrivalString);
                bid.setArrival(arrival);
            } catch (ParseException e) {

                logger.error("Error in AddBids when converting String to Date: " + e);
            }
            bidDao.add(bid);

            ArrayList<Bid> fullList = bidDao.getAll();
            session.setAttribute("fullListBids", fullList);

            response.setStatus(301);
            response.addHeader("Location", "editDBBids.jsp");

            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.EDIT_BILLS_PATH);
        } else {
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
            session.invalidate();
        }
        return page;
    }
}

