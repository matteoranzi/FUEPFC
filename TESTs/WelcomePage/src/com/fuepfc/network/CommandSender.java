package com.fuepfc.network;

import com.fuepfc.models.User;
import com.fuepfc.network.commands.LoginCommand;
import com.fuepfc.network.commands.RegistrationCommand;
import com.fuepfc.network.commands.RegistrationResponseCommand;
import com.fuepfc.network.commands.RegistrationResponseCommand.Registration;
import com.fuepfc.utils.AppParameters;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by IntelliJ IDEA.
 * User: matteoranzi
 * Date: 11/03/19
 * Time: 23.09
 */
public class CommandSender {
    public User sendLoginCommand(LoginCommand loginCommand) {
        User user = null;

        try {
            Socket socket = new Socket(AppParameters.SERVER_IP, AppParameters.TCP_SERVER_PORT);

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(loginCommand);

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            user = (User) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return user;
    }

    public Registration sendRegistrationCommand(RegistrationCommand registrationCommand){
        Registration registration = Registration.ERROR;

        try{
            Socket socket = new Socket(AppParameters.SERVER_IP, AppParameters.TCP_SERVER_PORT);

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(registrationCommand);

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            RegistrationResponseCommand registrationResponseCommand = (RegistrationResponseCommand) ois.readObject();
            registration = registrationResponseCommand.getRegistration();

        }catch ( IOException | ClassNotFoundException e){
            e.printStackTrace();
        }

        return registration;
    }
}