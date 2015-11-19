package com.booking.action;

import com.booking.command.Command;
import com.booking.dao.BidDaoImpl;
import com.booking.dao.BillDaoImpl;
import com.booking.dao.ClientDaoImpl;
import com.booking.model.Bid;
import com.booking.model.Bill;
import com.booking.model.Client;
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
public class AddUsers implements Command {

    private static final String PARAM_NAME_NAME = "name";
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String PARAM_NAME_MAIL = "mail";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page;
        HttpSession session = request.getSession();

        if (session.getAttribute("admin") != null) {
            Client client = new Client();
            ClientDaoImpl clientDao = new ClientDaoImpl();

            String name = request.getParameter(PARAM_NAME_NAME);
            String login = request.getParameter(PARAM_NAME_LOGIN);
            String password = request.getParameter(PARAM_NAME_PASSWORD);
            String mail = request.getParameter(PARAM_NAME_MAIL);

            client.setName(name);
            client.setLogin(login);
            client.setPassword(password);
            client.setMail(mail);
            clientDao.add(client);

            ArrayList<Client> fullListUsers = clientDao.getAll();
            session.setAttribute("fullListUsers", fullListUsers);

            response.setStatus(301);
            response.addHeader("Location", "editDBUsers.jsp");

            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.EDIT_USERS_PATH);
        } else {
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
            session.invalidate();
        }
        return page;
    }
}
