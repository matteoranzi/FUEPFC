package scenes.home;

import com.fuepfc.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Created by IntelliJ IDEA.
 * User: matteoranzi
 * Date: 18/03/19
 * Time: 12.04
 */
public class HomeController {
    public static final String WINDOW_TITLE = "FUEPFC - Home";


    private Stage stage;
    private User user;

    @FXML
    private Label usernameLbl;

    public void initializeClass(Stage stage, User user){
        this.stage = stage;
        this.user = user;

        usernameLbl.setText(user.getUsername());
    }
}