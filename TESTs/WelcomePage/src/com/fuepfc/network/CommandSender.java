package com.fuepfc.network;

import com.fuepfc.models.Game;
import com.fuepfc.models.User;
import com.fuepfc.network.commands.*;
import com.fuepfc.network.commands.RegistrationResponseCommand.Registration;
import com.fuepfc.utils.AppParameters;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: matteoranzi
 * Date: 11/03/19
 * Time: 23.09
 */
public class CommandSender {
    public User sendLoginCommand(LoginCommand loginCommand) {
        return (User) sendCommand(loginCommand);
    }

    public Registration sendRegistrationCommand(RegistrationCommand registrationCommand) {
        return ((RegistrationResponseCommand) sendCommand(registrationCommand)).getRegistration();
    }

    public ArrayList<Game> sendAvailableGamesRequestCommand(AvailableGamesRequestCommand availableGamesRequestCommand) {
        return ((AvailableGamesResponseCommand) sendCommand(availableGamesRequestCommand)).getGames();
    }

    private Object sendCommand(Command command) {
        Object response = null;

        try {
            Socket socket = new Socket(AppParameters.SERVER_IP, AppParameters.TCP_SERVER_PORT);

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(command);

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            response = ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return response;
    }
}