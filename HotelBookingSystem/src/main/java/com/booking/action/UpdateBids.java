package com.booking.action;

import com.booking.command.Command;
import com.booking.dao.BidDaoImpl;
import com.booking.dao.BillDaoImpl;
import com.booking.dao.ClientDaoImpl;
import com.booking.model.Bid;
import com.booking.model.Bill;
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
public class UpdateBids implements Command {
    private static final String PARAM_BID_ID = "id";
    private static final String PARAM_NAME_LOGIN = "login";
    private final static String PARAM_NAME_TYPE = "type";
    private final static String PARAM_NAME_DATE = "arrival";
    private final static String PARAM_TERM = "term";
    Logger logger = Logger.getLogger(UpdateBids.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page;
        HttpSession session = request.getSession();
        if (session.getAttribute("admin") != null) {
            BidDaoImpl bidDao = new BidDaoImpl();
            BillDaoImpl billDao = new BillDaoImpl();
            long idBid = Long.parseLong(request.getParameter(PARAM_BID_ID));
            Bid bidFromDB = bidDao.find(idBid);

            String login = request.getParameter(PARAM_NAME_LOGIN);
            ClientDaoImpl clientDao = new ClientDaoImpl();
            Client client = clientDao.find(login);
            bidFromDB.setUser(client);

            String termFromJSP = request.getParameter(PARAM_TERM);
            short term = Short.parseShort(termFromJSP);
            bidFromDB.setTerm(term);

            String typeName = request.getParameter(PARAM_NAME_TYPE);
            Type type = Type.getTypeByName(typeName);
            bidFromDB.setType(type);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String arrivalString = request.getParameter(PARAM_NAME_DATE);
            try {
                Date arrival = format.parse(arrivalString);
                bidFromDB.setArrival(arrival);
            } catch (ParseException e) {

                logger.error("Error in AddBids when converting String to Date: " + e);
            }
            bidDao.update(bidFromDB);
            Bill bill = billDao.find(bidFromDB);
            if (bill != null) {
                bill.setBid(bidFromDB);
                billDao.update(bill);
            }

            ArrayList<Bid> fullList = bidDao.getAll();
            session.setAttribute("fullListBids", fullList);

            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.EDIT_BIDS_PATH);
        } else {
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
            session.invalidate();
        }
        return page;
    }
}

