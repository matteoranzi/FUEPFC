package scenes.home;

import com.fuepfc.models.Game;
import com.fuepfc.models.User;
import com.fuepfc.network.CommandSender;
import com.fuepfc.network.commands.AvailableGamesRequestCommand;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: matteoranzi
 * Date: 18/03/19
 * Time: 12.04
 */
public class HomeController {
    public static final String WINDOW_TITLE = "FUEPFC - Home";


    private Stage stage;

    private CommandSender commandSender;
    private User user;
    private ArrayList<Game> games;

    @FXML
    Label gameNameLabel;

    @FXML
    private Label usernameLbl;

    public void initializeClass(Stage stage, User user){
        commandSender = new CommandSender();

        this.stage = stage;
        this.user = user;

        this.usernameLbl.setText("Traffic Race");

       // games = commandSender.sendAvailableGamesRequestCommand(new AvailableGamesRequestCommand(user.getSessionKey()));
        usernameLbl.setText(user.getUsername());
    }

    @FXML
    private void handleLaunchButtonAction(ActionEvent event){
        String gamePath = (getClass().getResource("/resources/games/TrafficRacer_Game.jar").getPath());
        try {
            Runtime.getRuntime().exec("java -jar " + gamePath + " " + user.getUsername() + " " + user.getSessionKey() + " localhost 6969" );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}