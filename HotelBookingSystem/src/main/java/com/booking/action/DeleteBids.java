package com.booking.action;

import com.booking.command.Command;
import com.booking.dao.BidDaoImpl;
import com.booking.dao.BillDaoImpl;
import com.booking.model.Bid;
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
public class DeleteBids implements Command {
    private static final String PARAM_BID_ID = "id";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String page;
        if (session.getAttribute("admin") != null) {
            BidDaoImpl bidDao = new BidDaoImpl();
            BillDaoImpl billDao = new BillDaoImpl();
            long idBid = Long.parseLong(request.getParameter(PARAM_BID_ID));
            Bid bid = bidDao.find(idBid);
            if (billDao.find(bid)== null) {
                bidDao.delete(idBid);
            } else {
                billDao.delete(billDao.find(bid).getId());
            }
            ArrayList<Bid> fullListBids = bidDao.getAll();
            request.setAttribute("fullListBids", fullListBids);
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.EDIT_BIDS_PATH);
        } else {
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
            session.invalidate();
        }
        return page;
    }
}
