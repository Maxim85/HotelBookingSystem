package com.booking.filter;

import com.booking.dao.ClientDaoImpl;
import com.booking.model.Client;
import com.booking.service.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author Maksym.
 */


public class UserFilter implements Filter {
    private static Logger logger = Logger.getLogger(Filter.class.getName());
    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest requestHTTP = (HttpServletRequest) request;
        HttpServletResponse responseHTTP = (HttpServletResponse) response;
        HttpSession session = requestHTTP.getSession();
        String page;

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (session.getAttribute("client") == null) {
            try {
                ClientDaoImpl clientDao = new ClientDaoImpl();
                Client client = clientDao.find(login);

                Properties properties = new Properties();
                InputStream inputStream = getClass().getClassLoader().getResourceAsStream("role.properties");
                properties.load(inputStream);
                String adminName = properties.getProperty("ROLE_ADMIN");
                String[] allUser = adminName.split(",");
                ArrayList<String> list = new ArrayList<>(Arrays.asList(allUser));
                session.setAttribute("adminList", list);

                if (list.contains(login) && password != null
                        && password.equals(client.getPassword())) {
                    session.setAttribute("admin", client);
                    request.getRequestDispatcher("mainAdmin.jsp").forward(request, response);
                }
                chain.doFilter(request, response);
            } catch (Exception e) {
                String exception = e.getMessage();
                logger.error("Exception in Filter: "+e);
                request.setAttribute("errorMessage", exception);
                page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
                request.getRequestDispatcher(page).forward(request, response);
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        filterConfig = null;
    }
}
