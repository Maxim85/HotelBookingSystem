package com.booking.command;

import com.booking.dao.BidDaoImpl;
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
public class EditDBBidsCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page;
        HttpSession session = request.getSession();

        if (session.getAttribute("admin") != null) {
            BidDaoImpl bidDao = new BidDaoImpl();
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
