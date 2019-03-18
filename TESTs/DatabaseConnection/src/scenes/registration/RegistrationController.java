package scenes.registration;

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
import models.users.User;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: matteoranzi
 * Date: 25/02/19
 * Time: 16.50
 */
public class RegistrationController {
    public static final String WINDOW_TITLE = "Registration";

    @FXML
    TextField firstNameTextField;
    @FXML
    TextField lastNameTextField;
    @FXML
    TextField usernameTextField;
    @FXML
    TextField emailTextField;
    @FXML
    PasswordField passwordPasswordField;
    @FXML
    PasswordField confirmPasswordPasswordField;
    @FXML
    Button registerButton;
    @FXML
    Button goToLoginButton;
    @FXML
    Label registrationStatusLabel;

    private UsersTableHelper usersTableHelper;

    @FXML
    void initialize() {
        usersTableHelper = new UsersTableHelper();
    }

    @FXML
    void handleGoToLoginButtonAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/scenes/login/loginView.fxml"));

                Stage loginControllerStage = new Stage();
                loginControllerStage.setScene(new Scene(root));
                loginControllerStage.setTitle(RegistrationController.WINDOW_TITLE);
                loginControllerStage.setResizable(false);
                loginControllerStage.show();

                //Close current window
                ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void handleRegisterButtonAction(ActionEvent event) {
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String username = usernameTextField.getText();
        String email = emailTextField.getText();
        String password = passwordPasswordField.getText();
        String confirmPassword = confirmPasswordPasswordField.getText();

        if (firstName.equals("") || lastName.equals("") || username.equals("") || email.equals("") || password.equals("") || confirmPassword.equals("")) {
            registrationStatusLabel.setText("All fields are required!");
            registrationStatusLabel.setTextFill(Color.RED);
        } else if (!password.equals(confirmPassword)) {
            registrationStatusLabel.setText("Passwords don't match");
            registrationStatusLabel.setTextFill(Color.RED);
        } else {
            User user = new User(UUID.randomUUID());
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);

            Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
            user.setRegistrationDate(timestamp);

            UsersTableHelper.Registration registration = usersTableHelper.registerUser(user);
            switch (registration) {
                case SUCCESSFUL:
                    Platform.runLater(() -> {
                        registrationStatusLabel.setText("Registered successfully!");
                        registrationStatusLabel.setTextFill(Color.GREEN);
                    });

                    firstNameTextField.requestFocus();
                    firstNameTextField.setText("");
                    lastNameTextField.setText("");
                    usernameTextField.setText("");
                    emailTextField.setText("");
                    passwordPasswordField.setText("");
                    confirmPasswordPasswordField.setText("");

                    break;
                case DUPLICATED_ENTRY:
                    Platform.runLater(() -> {
                        registrationStatusLabel.setText("Username already exists!");
                        registrationStatusLabel.setTextFill(Color.RED);
                    });
                    break;
                case ERROR:
                    Platform.runLater(() -> {
                        registrationStatusLabel.setText("Registration gone wrong");
                        registrationStatusLabel.setTextFill(Color.RED);
                    });
                    break;
                case EMPTY_FIELDS:
                    Platform.runLater(() -> {
                        registrationStatusLabel.setText("Blank field are not admitted!");
                        registrationStatusLabel.setTextFill(Color.RED);
                    });
                    break;

                case STRING_TOO_LONG:
                    Platform.runLater(() -> {
                        registrationStatusLabel.setText("String is too long!");
                        registrationStatusLabel.setTextFill(Color.RED);
                    });
                    break;
                default:
            }
        }
    }
}