package com.booking.controller;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

/*import by.bsu.famcs.jspservlet.commands.Command;
import by.bsu.famcs.jspservlet.commands.LoginCommand;
import by.bsu.famcs.jspservlet.commands.NoCommand;

/**
 * @author Maksym.
 */
public class RequestHelper {
    private static RequestHelper instance = null;
    HashMap<String, Command> commands = new HashMap<String, Command>();
    private RequestHelper() {
//���������� ������� ���������
        commands.put("login", new LoginCommand());
    }
    public Command getCommand(HttpServletRequest request) {
//���������� ������� �� �������
        String action = request.getParameter("command");
//��������� �������, ���������������� �������
        Command command = commands.get(action);
        if (command == null) {
//���� ������� �� ���������� � ������� �������
            command = new NoCommand();
        }
        return command;
    }
    //�������� ������������� ������� �� ������� Singleton
    public static RequestHelper getInstance() {
        if (instance == null) {
            instance = new RequestHelper();
        }
        return instance;
    }
}
//package by.bsu.famcs.jspservlet;
