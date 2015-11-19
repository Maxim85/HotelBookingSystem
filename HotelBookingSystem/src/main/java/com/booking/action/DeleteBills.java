package com.booking.action;

import com.booking.command.Command;
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
public class DeleteBills implements Command {
    private static final String PARAM_BILL_ID = "id";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String page;
        if (session.getAttribute("admin") != null) {
            BillDaoImpl billDao = new BillDaoImpl();//
            long idBill = Long.parseLong(request.getParameter(PARAM_BILL_ID));
            billDao.delete(idBill);

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
