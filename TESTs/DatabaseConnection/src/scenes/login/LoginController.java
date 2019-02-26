package scenes.login;

import database.users.UsersTableHelper;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import scenes.registration.RegistrationController;

import java.io.IOException;

public class LoginController {
    public static final String WINDOW_TITLE = "Login";

    @FXML
    TextField usernameTextField;
    @FXML
    PasswordField passwordPasswordField;
    @FXML
    Button loginButton;
    @FXML
    Button goToRegistrationButton;
    @FXML
    Label connectionStatusLabel;

    private UsersTableHelper usersTableHelper;

    @FXML
    void initialize() {
        usersTableHelper = new UsersTableHelper();
    }

    @FXML
    void handleLoginButtonAction(ActionEvent event) {
        String username = usernameTextField.getText();
        String password = passwordPasswordField.getText();

        if (usersTableHelper.authenticateUser(username, password) == UsersTableHelper.AUTHENTICATED) {
            Platform.runLater(() -> {
                connectionStatusLabel.setText("Logged successfully!");
                connectionStatusLabel.setTextFill(Color.GREEN);

                usernameTextField.setText("");
                passwordPasswordField.setText("");
                usernameTextField.requestFocus();
            });
        } else {
            Platform.runLater(() -> {
                connectionStatusLabel.setText("Login gone wrong!");
                connectionStatusLabel.setTextFill(Color.RED);

                passwordPasswordField.setText("");
                usernameTextField.requestFocus();
            });
        }
    }

    @FXML
    void handleGoToRegistrationButtonAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/scenes/registration/registrationView.fxml"));

                Stage registrationControllerStage = new Stage();
                registrationControllerStage.setScene(new Scene(root));
                registrationControllerStage.setTitle(RegistrationController.WINDOW_TITLE);
                registrationControllerStage.setResizable(false);
                registrationControllerStage.show();

                //Close current window
                ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
