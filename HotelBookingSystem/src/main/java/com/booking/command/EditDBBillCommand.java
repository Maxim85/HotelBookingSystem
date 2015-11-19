package com.booking.command;

import com.booking.dao.BillDaoImpl;
import com.booking.model.Bill;
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
public class EditDBBillCommand implements Command {


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String page;
        HttpSession session = request.getSession();

        if (session.getAttribute("admin") != null) {
            BillDaoImpl billDao = new BillDaoImpl();
            ArrayList<Bill> fullList = billDao.getAll();
            request.setAttribute("fullListBills", fullList);

            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.EDIT_BILLS_PATH);
        } else {
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
            session.invalidate();
        }
        return page;
    }
}