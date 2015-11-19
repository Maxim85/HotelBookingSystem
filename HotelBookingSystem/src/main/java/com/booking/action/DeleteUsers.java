package com.booking.action;

import com.booking.command.Command;
import com.booking.dao.BillDaoImpl;
import com.booking.dao.ClientDaoImpl;
import com.booking.model.Client;
import com.booking.model.User;
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
public class DeleteUsers implements Command {
    private static final String PARAM_USER_ID = "id";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page;

        HttpSession session = request.getSession();
        if (session.getAttribute("admin") != null) {
            ClientDaoImpl clientDao = new ClientDaoImpl();
            BillDaoImpl billDao = new BillDaoImpl();

            long idUser = Long.parseLong(request.getParameter(PARAM_USER_ID));
            Client client = clientDao.find(idUser);
            if (billDao.find(client) == null) {
                clientDao.delete(idUser);
            } else {
                billDao.delete(billDao.find(client).getId());
                clientDao.delete(idUser);
            }

            ArrayList<Client> fullListUsers = clientDao.getAll();
            request.setAttribute("fullListUsers", fullListUsers);

            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.EDIT_USERS_PATH);
        } else {
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
            session.invalidate();
        }
        return page;
    }
}
