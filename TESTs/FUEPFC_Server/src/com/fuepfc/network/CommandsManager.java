package com.fuepfc.network;

import com.fuepfc.network.commands.*;
import com.fuepfc.network.commands.RegistrationResponseCommand.Registration;
import com.fuepfc.database.users_data.UsersTableHelper;
import com.fuepfc.models.User;
import com.fuepfc.utils.AppParameters;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by IntelliJ IDEA.
 * User: matteoranzi
 * Date: 11/03/19
 * Time: 21.30
 */
public class CommandsManager implements Runnable {
    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(AppParameters.TCP_SERVER_PORT);
            System.out.println("Listening to port " + AppParameters.TCP_SERVER_PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Receiving a new connection");
                CommandHandler commandHandler = new CommandHandler(socket);
                commandHandler.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        Thread t = new Thread(this);
        //t.setDaemon(true);
        t.start();
    }

    private class CommandHandler implements Runnable {
        private Socket socket;

        public CommandHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

                Command command = (Command) ois.readObject();
                if (command instanceof LoginCommand) {
                    System.out.println("Command type: login");
                    handleLoginCommand((LoginCommand) command);

                }else if (command instanceof RegistrationCommand){
                    System.out.println("Command type: registration");
                    handleRegistrationCommand((RegistrationCommand) command);

                }else if(command instanceof MatchWinnerCommand){
                    System.out.println("Command type: match winner");
                    handleMatchWinnerCommand((MatchWinnerCommand) command);

                }else if(command instanceof AvailableGamesRequestCommand){
                    System.out.println("Command type: available games request");
                    handleAvailableGamesRequestCommand((AvailableGamesRequestCommand) command);

                }else if(command instanceof MatchScoresCommand){
                    System.out.println("Command type: available games request");
                    handleMatchScoresCommand((MatchScoresCommand) command);

                }

                socket.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        private void handleMatchScoresCommand(MatchScoresCommand matchScoresCommand){
            if(matchScoresCommand != null){
                System.out.println("User: " + matchScoresCommand.getUser().getUsername() + " -> point: " + matchScoresCommand.getScore());
            }
        }

        private void handleMatchWinnerCommand(MatchWinnerCommand matchWinnerCommand){
            if(matchWinnerCommand != null){
                //check if the user sessionKey is valid

            }
        }

        private void handleAvailableGamesRequestCommand(AvailableGamesRequestCommand command){

        }

        private void handleRegistrationCommand(RegistrationCommand registrationCommand) {
            //// TODO: 12/03/19 implement registration confirmation with email (GMail API)
            try {
                if (registrationCommand == null) {
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    System.out.println("No data received, no registration  allowed");
                    oos.writeObject(null);
                    System.out.println("User data sent");
                    oos.flush();
                } else {
                    UsersTableHelper usersTableHelper = new UsersTableHelper();

                    String name = registrationCommand.getName();
                    String lastName = registrationCommand.getLastName();
                    String username = registrationCommand.getUsername();
                    String email = registrationCommand.getEmail();
                    String password = registrationCommand.getPassword();

                    Registration registration = usersTableHelper.registerUser(name, lastName, username, email, password);
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(new RegistrationResponseCommand(registration));
                    System.out.println("registration data sent");
                    oos.flush();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        private void handleLoginCommand(LoginCommand loginCommand) {
            try {
                if (loginCommand == null) {
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    System.out.println("No data received, no login  allowed");
                    oos.writeObject(null);
                    System.out.println("User data sent");
                    oos.flush();
                } else {
                    UsersTableHelper usersTableHelper = new UsersTableHelper();
                    User user = usersTableHelper.authenticateUser(loginCommand.getUsername(), loginCommand.getPassword());
                    System.out.println("Obtaining user data");
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    System.out.println("Sending user data...");
                    oos.writeObject(user);
                    System.out.println("User data sent");
                    oos.flush();
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void start() {
            Thread t = new Thread(this);
            t.setDaemon(true);
            t.start();
        }
    }
}