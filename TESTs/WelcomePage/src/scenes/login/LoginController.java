package scenes.login;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;

/**
 * Created by IntelliJ IDEA.
 * User: matteoranzi
 * Date: 15/02/19
 * Time: 17.25
 */

public class LoginController {
    private static final boolean REGISTRATION_FIRST = false;

    @FXML
    AnchorPane signInAnchorPane;
    @FXML
    AnchorPane signUpAnchorPane;
    @FXML
    Hyperlink newAccountHyperLink;
    @FXML
    Hyperlink signInHyperLink;
    @FXML
    JFXTextField usernameJFXTextField;
    @FXML
    JFXPasswordField passwordJFXPasswordField;

    @FXML
    private void initialize() {
        signInAnchorPane.setVisible(!REGISTRATION_FIRST);
        signUpAnchorPane.setVisible(REGISTRATION_FIRST);
    }

    @FXML
    private void handleSignInButtonAction(Event event) {
        System.out.println("Trying to log in");
    }

    @FXML
    private void handleNewAccountHyperLinkAction(Event event) {
        //Show 'sign up' page
        Platform.runLater(() -> {
            signInAnchorPane.setVisible(false);
            signUpAnchorPane.setVisible(true);
        });
    }

    @FXML
    private void handleSignInHyperLinkAction(Event event) {
        //Show 'sign in' page
        Platform.runLater(() -> {
            signUpAnchorPane.setVisible(false);
            signInAnchorPane.setVisible(true);
        });
    }

    @FXML
    private void handleExitButtonImageViewMouseClick(Event event) {
        Platform.exit();
    }
}