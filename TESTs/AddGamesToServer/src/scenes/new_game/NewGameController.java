package scenes.new_game;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: matteoranzi
 * Date: 19/03/19
 * Time: 2.12
 */
public class NewGameController {
    @FXML
    TextField gameNameTextField;
    @FXML
    TextField gameVersionTextField;
    @FXML
    Label generatedGameUuidLabel;

    private File gameServerFile;
    private File gameClientFile;


    @FXML
    private void handleSelectGameServerButtonAction(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        gameServerFile = fileChooser.showOpenDialog(((Node)event.getSource()).getScene().getWindow());
    }

    @FXML
    private void handleSelectGameClientButtonAction(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        gameClientFile = fileChooser.showOpenDialog(((Node)event.getSource()).getScene().getWindow());
    }

    @FXML
    private void handleAddGameButtonAction(ActionEvent event){
        if(!gameNameTextField.getText().equals("") && !gameVersionTextField.getText().equals("") && gameServerFile != null && gameClientFile != null){

        }
    }
}