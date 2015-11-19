package com.booking.command;

import com.booking.dao.BidDaoImpl;
import com.booking.dao.BillDaoImpl;
import com.booking.dao.ClientDaoImpl;
import com.booking.model.*;
import com.booking.service.ConfigurationManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author Maksym.
 */
public class FormingBillsCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page;
        HttpSession session = request.getSession();

        if (session.getAttribute("admin") != null) {
            ClientDaoImpl clientDao = new ClientDaoImpl();
            ArrayList<Client> onlyClient = new ArrayList<>();

            BidDaoImpl bidDao = new BidDaoImpl();
            ArrayList<Bid> allBids = bidDao.getAll();
            ArrayList<Bid> actualBids = new ArrayList<>();

            BillDaoImpl billDao = new BillDaoImpl();
            ArrayList<Bill> allBills = billDao.getAll();

            ArrayList<Bid> listBids = (ArrayList) session.getAttribute("actualBids");
            ArrayList<Client> listClients = (ArrayList) session.getAttribute("onlyClient");

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR, 0);
            calendar.set(Calendar.AM_PM, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Date currentDay = calendar.getTime();

            if (listBids == null) {
                for (Bid bids : allBids) {
                    if (bids.getArrival().compareTo(currentDay) >= 0) {
                        actualBids.add(bids);
                    }
                }
                for (Bill bills : allBills) {
                    for (Bid bids : actualBids) {
                        if ((bids.getId() == bills.getBid().getId()))
                            actualBids.remove(bids);
                        break;
                    }
                }
                session.setAttribute("actualBids", actualBids);
            } else {
                session.setAttribute("actualBids", listBids);
            }
            if (listClients == null) {
                for (Bid bids : actualBids) {
                    long id = bids.getUser().getId();
                    Client client = clientDao.find(id);
                    if (!onlyClient.contains(client)) {
                        onlyClient.add(client);
                    }
                }
                session.setAttribute("onlyClient", onlyClient);
            } else {
                session.setAttribute("onlyClient", listClients);
            }
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.FORMING_PAGE_PATH);
        } else {
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
            session.invalidate();
        }
        return page;
    }
}
