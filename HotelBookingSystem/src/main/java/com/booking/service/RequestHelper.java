package com.booking.service;

import com.booking.action.*;
import com.booking.command.*;

import java.util.HashMap;

/**
 * @author Maksym.
 */
public class RequestHelper {
    private static RequestHelper instance = null;
    HashMap<String, Command> commands = new HashMap<String, Command>();

    private RequestHelper() {
        commands.put("login", new LoginCommand());
        commands.put("registration", new RegistrationCommand());
        commands.put("bid", new BidCommand());
        commands.put("exit", new MyBidsCommand());
        commands.put("forming", new FormingBillsCommand());
        commands.put("sendBills", new BillCommand());
        commands.put("editDBBills", new EditDBBillCommand());
        commands.put("deleteBills", new DeleteBills());
        commands.put("addBills", new AddBills());
        commands.put("updateBills", new UpdateBills());
        commands.put("editDBBids", new EditDBBidsCommand());
        commands.put("deleteBids", new DeleteBids());
        commands.put("addBids", new AddBids());
        commands.put("updateBids", new UpdateBids());
        commands.put("editDBApartments", new EditDBApartmentsCommand());
        commands.put("deleteApartments", new DeleteApartments());
        commands.put("addApartments", new AddApartments());
        commands.put("updateApartments", new UpdateApartments());
        commands.put("editDBUsers", new EditDBUsersCommand());
        commands.put("deleteUsers", new DeleteUsers());
        commands.put("addUsers", new AddUsers());
        commands.put("updateUsers", new UpdateUsers());
    }

    public Command getCommand(String action) {
        Command command = commands.get(action);
        if (command == null) {
            command = new NoCommand();
        }
        return command;
    }

    public static RequestHelper getInstance() {
        if (instance == null) {
            instance = new RequestHelper();
        }
        return instance;
    }
}
