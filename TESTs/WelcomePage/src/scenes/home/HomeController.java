package scenes.home;

import com.fuepfc.models.Game;
import com.fuepfc.models.User;
import com.fuepfc.network.CommandSender;
import com.fuepfc.network.commands.AvailableGamesRequestCommand;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

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
    private Label usernameLbl;

    public void initializeClass(Stage stage, User user){
        commandSender = new CommandSender();

        this.stage = stage;
        this.user = user;

        games = commandSender.sendAvailableGamesRequestCommand(new AvailableGamesRequestCommand(user.getSessionKey()));
        usernameLbl.setText(user.getUsername());
    }
}