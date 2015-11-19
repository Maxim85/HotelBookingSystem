package com.booking.command;

import com.booking.service.ConfigurationManager;
import com.booking.dao.BidDaoImpl;
import com.booking.dao.ClientDaoImpl;
import com.booking.model.Bid;
import com.booking.model.Client;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;


/**
 * @author Maksym.
 */
public class LoginCommand implements Command {
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";

    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page;
        HttpSession session = request.getSession();
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
//        Client client = (Client) session.getAttribute("client");
//        String login = (String) session.getAttribute(PARAM_NAME_LOGIN);
//        String pass = (String) session.getAttribute(PARAM_NAME_PASSWORD);
        ClientDaoImpl clientDao = new ClientDaoImpl();
        Client client = clientDao.find(login);
        BidDaoImpl bidDao = new BidDaoImpl();
        if (client != null
                && pass != null
                && pass.equals(client.getPassword())) {
            Calendar calendar = Calendar.getInstance();
            Date currentDay = calendar.getTime();
            ArrayList<Bid> listFull = bidDao.getUserBids(client);
            ArrayList<Bid> historyList = new ArrayList<>();
            ArrayList<Bid> actualList = new ArrayList<>();
            for (Bid bids : listFull) {
                calendar.setTime(bids.getArrival());
                calendar.add(Calendar.DATE, bids.getTerm());
                if (currentDay.compareTo(calendar.getTime()) > 0) {
                    historyList.add(bids);
                } else actualList.add(bids);
            }

            ArrayList<Client> fullListUser = clientDao.getAll();
            ArrayList<String> fullListAdmin = (ArrayList)session.getAttribute("adminList");
            ArrayList<Client> fullListClient = new ArrayList<>();
            for(Client clients : fullListUser){
                for (String admin : fullListAdmin) {
                    if (!(clients.getLogin()).equals(admin)) {
                        fullListClient.add(clients);
                    }
                }
            }

            session.setAttribute("fullListClients", fullListClient);
            session.setAttribute("client", client);
            session.setAttribute("actualList", actualList);
            session.setAttribute("historyList", historyList);
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.MAIN_PAGE_PATH);
        } else {
            request.setAttribute("errorMessage", "Enter correct password. Password shouldn't be empty");//MessageManager.getInstance().getProperty(MessageManager.LOGIN_ERROR_MESSAGE
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);//LOGIN_PAGE_PATH
            session.invalidate();
        }
        return page;
    }
}
