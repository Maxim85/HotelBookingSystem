package com.booking.command;

import com.booking.service.ConfigurationManager;
import com.booking.model.Client;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Maksym.
 */
public class MyBidsCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = null;
        HttpSession session = request.getSession();
        Client clientOriginal = (Client) session.getAttribute("user");
        if (clientOriginal == null) {
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.CLOSE_PAGE_PATH);
            session.invalidate();
        }
        return page;
    }
}
