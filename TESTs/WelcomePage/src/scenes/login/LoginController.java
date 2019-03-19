package scenes.login;

import com.fuepfc.models.User;
import com.fuepfc.network.CommandSender;
import com.fuepfc.network.commands.LoginCommand;
import com.fuepfc.network.commands.RegistrationCommand;
import com.fuepfc.network.commands.RegistrationResponseCommand.Registration;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import scenes.home.HomeController;

import java.io.IOException;
import java.util.concurrent.Semaphore;

/**
 * Created by IntelliJ IDEA.
 * User: matteoranzi
 * Date: 15/02/19
 * Time: 17.25
 */

public class LoginController {
    private static final boolean REGISTRATION_FIRST = false;

    private CommandSender commandSender;

    private Semaphore signInSemaphore;
    private boolean tryingToLogin;

    private Semaphore signUpSemaphore;
    private boolean tryingToSignUp;

    //________________________________DYNAMIC_GUI_________________________________
    @FXML
    AnchorPane mainAnchorPane;
    @FXML
    AnchorPane signInAnchorPane;
    @FXML
    AnchorPane signUpAnchorPane;
    @FXML
    Hyperlink newAccountHyperLink;
    @FXML
    Hyperlink signInHyperLink;

    //________________________________SING-IN_FORM________________________________
    @FXML
    JFXTextField signInUsernameTxtFld;
    @FXML
    JFXPasswordField signInPasswordPswFld;
    @FXML
    JFXButton signInBtn;
    @FXML
    RequiredFieldValidator usernameValidator;

    //________________________________SING-UP_FORM________________________________
    @FXML
    JFXTextField signUpNameTxtFld;
    @FXML
    JFXTextField signUpLastNameTxtFld;
    @FXML
    JFXTextField signUpUsernameTxtFld;
    @FXML
    JFXTextField signUpEmailTxtFld;
    @FXML
    JFXPasswordField signUpPasswordPswFld;
    @FXML
    JFXPasswordField signUpConfirmPasswordPswFld;


    public LoginController() {
        commandSender = new CommandSender();

        signInSemaphore = new Semaphore(1);
        tryingToLogin = false;

        signUpSemaphore = new Semaphore(1);
        tryingToSignUp = false;
    }

    @FXML
    private void initialize() {
        signInAnchorPane.setVisible(!REGISTRATION_FIRST);
        signUpAnchorPane.setVisible(REGISTRATION_FIRST);

        addListeners();
    }

    @FXML
    private void handleSignInBtnAction(Event event) {
        //// TODO: 11/03/19 Show something else during authorization request
        //// FIXME: 12/03/19 if the user presses 'login' multiple times (quickly) the program crashes
        try {
            signInSemaphore.acquire();//        | avoid multiple login requests
            if (!tryingToLogin) {//             |
                tryingToLogin = true;//         |
                signInSemaphore.release();//    |

                Thread t = new Thread(() -> {

                    String username = signInUsernameTxtFld.getText();
                    String password = signInPasswordPswFld.getText();

                    User user = commandSender.sendLoginCommand(new LoginCommand(username, password));

                    try {
                        signInSemaphore.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    tryingToLogin = false;
                    signInSemaphore.release();

                    if (user != null) {
                        System.out.println("You are logged in as: " + user.getUsername() + "\t|\t -> {" + user.getSessionKey() + "}");

                        Platform.runLater(() -> {
                            try {
                                FXMLLoader homeControllerLoader = new FXMLLoader((getClass().getResource("/scenes/home/homeView.fxml")));
                                Parent homeControllerRoot = homeControllerLoader.load();

                                HomeController homeController = homeControllerLoader.getController();

                                Stage homeControllerStage = new Stage();
                                homeController.initializeClass(homeControllerStage, user);
                                homeControllerStage.setScene(new Scene(homeControllerRoot));
                                homeControllerStage.setTitle(HomeController.WINDOW_TITLE);
                                homeControllerStage.setResizable(false);
                                homeControllerStage.show();

                                ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();


                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });


                    } else {
                        System.out.println("Wrong username or password");
                    }

                });
                t.setDaemon(true);
                t.start();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSignUpBtnAction(Event event) {
        //// TODO: 11/03/19 Show something else during authorization request
        //// FIXME: 12/03/19 if the user presses 'login' multiple times (quickly) the program crashes
        try {
            signUpSemaphore.acquire();//        | avoid multiple login requests
            if (!tryingToSignUp) {//             |
                tryingToSignUp = true;//         |
                signUpSemaphore.release();//    |

                Thread t = new Thread(() -> {

                    String name = signUpNameTxtFld.getText();
                    String lastName = signUpLastNameTxtFld.getText();
                    String username = signUpUsernameTxtFld.getText();
                    String email = signUpEmailTxtFld.getText();
                    String password = signUpPasswordPswFld.getText();
                    String confirmPassword = signUpConfirmPasswordPswFld.getText();

                    if (password.equals(confirmPassword)) {

                        Registration registration = (Registration) commandSender.sendRegistrationCommand(new RegistrationCommand(name, lastName, username, email, password));

                        switch (registration) {

                            case ERROR:
                                System.out.println("Error");
                                break;
                            case SUCCESSFUL:
                                System.out.println("Successful");
                                handleSignInHyperLinkAction(event);
                                break;
                            case DUPLICATED_ENTRY:
                                System.out.println("Username already exists");
                                break;
                            case EMPTY_FIELDS:
                                System.out.println("All fields are required");
                                break;
                            case STRING_TOO_LONG:
                                System.out.println("Strings too long");
                                break;
                        }

                        try {
                            signUpSemaphore.acquire();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        tryingToSignUp = false;
                        signUpSemaphore.release();

                    } else {
                        System.out.println("Passwords don't match");
                    }

                });
                t.setDaemon(true);
                t.start();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    private void addListeners() {
        //Sign in
        signInUsernameTxtFld.setOnKeyPressed(this::handleSignInEnterAction);
        signInPasswordPswFld.setOnKeyPressed(this::handleSignInEnterAction);

        //Sign up
        signUpNameTxtFld.setOnKeyPressed(this::handleSignUpEnterAction);
        signUpLastNameTxtFld.setOnKeyPressed(this::handleSignUpEnterAction);
        signUpUsernameTxtFld.setOnKeyPressed(this::handleSignUpEnterAction);
        signUpEmailTxtFld.setOnKeyPressed(this::handleSignUpEnterAction);
        signUpPasswordPswFld.setOnKeyPressed(this::handleSignUpEnterAction);
        signUpConfirmPasswordPswFld.setOnKeyPressed(this::handleSignUpEnterAction);


    }

    private void handleSignInEnterAction(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            keyEvent.consume();
            handleSignInBtnAction(keyEvent);
        }
    }

    private void handleSignUpEnterAction(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            keyEvent.consume();
            handleSignUpBtnAction(keyEvent);
        }
    }
}