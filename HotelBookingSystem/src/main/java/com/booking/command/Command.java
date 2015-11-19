package com.booking.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author Maksym.
 */
public interface Command {
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
