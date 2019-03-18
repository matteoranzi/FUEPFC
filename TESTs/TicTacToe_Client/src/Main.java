import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import scenes.lobby.LobbyController;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.UUID;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("scenes/lobby/lobbyView.fxml"));
        primaryStage.setTitle(LobbyController.WINDOW_TITLE);
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
