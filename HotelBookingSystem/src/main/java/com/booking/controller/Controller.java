package com.booking.controller;
//package by.bsu.famcs.jspservlet;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import by.bsu.famcs.jspservlet.commands.Command;
//import by.bsu.famcs.jspservlet.manager.MessageManager;
//import by.bsu.famcs.jspservlet.manager.ConfigurationManager;

/**
 * @author Maksym.
 */
public class Controller extends HttpServlet {
    //объект, содержащий список возможных команд
    RequestHelper requestHelper = RequestHelper.getInstance();

    public Controller() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = null;
        try {
//определение команды, пришедшей из JSP
            Command command = requestHelper.getCommand(request);
/*вызов реализованного метода execute() интерфейса Command и передача параметров классу-обработчику конкретной команды*/
            page = command.execute(request, response);
// метод возвращает страницу ответа
        } catch (ServletException e) {
            e.printStackTrace();
//генерация сообщения об ошибке
            request.setAttribute("errorMessage", MessageManager.getInstance().getProperty(MessageManager.SERVLET_EXCEPTION_ERROR_MESSAGE));
//вызов JSP-страницы c cообщением об ошибке
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", MessageManager.getInstance().getProperty(MessageManager.IO_EXCEPTION_ERROR_MESSAGE));
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
        }
//вызов страницы ответа на запрос
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
}


