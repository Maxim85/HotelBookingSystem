package com.booking.command;


import com.booking.dao.ClientDaoImpl;
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
public class EditDBUsersCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page;
        HttpSession session = request.getSession();
        Client admin = (Client) session.getAttribute("admin");
        if (admin != null) {
            ClientDaoImpl clientDao = new ClientDaoImpl();
            ArrayList<Client> fullListUsers = clientDao.getAll();
            ArrayList<Client> listWithOutActualAdmin = new ArrayList<>();
            for (Client clients : fullListUsers) {
                if (!clients.equals(admin)) {
                    listWithOutActualAdmin.add(clients);
                }
            }
            session.setAttribute("listWithOutActualAdmin", listWithOutActualAdmin);
            request.setAttribute("fullListUsers", fullListUsers);

            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.EDIT_USERS_PATH);
        } else {
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
            session.invalidate();
        }
        return page;
    }
}
