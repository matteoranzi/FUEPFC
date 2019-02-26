package scenes.game;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class GameController {
    private static final String X_ICON = "images/x_icon.png";
    private static final String O_ICON = "images/o_icon.png";

    private static final int TABLE_SIZE = 3;
    private static final int MAX_MOVES = 9;

    private static final String WINNER = "You win!";
    private static final String LOOSER = "You lose!";
    private static final String DRAW = "DRAW";

    private static int player1Score = 0;
    private static int player2Score = 0;

    private enum CellStatus {
        PLAYER_1,
        PLAYER_2,
        BLANK
    }

    private boolean isPlayer1Turn;
    private CellStatus[][] tableStatus;
    private int movesCounter;

    @FXML
    ImageView firstCell;
    @FXML
    ImageView secondCell;
    @FXML
    ImageView thirdCell;
    @FXML
    ImageView fourthCell;
    @FXML
    ImageView fifthCell;
    @FXML
    ImageView sixthCell;
    @FXML
    ImageView seventhCell;
    @FXML
    ImageView eighthCell;
    @FXML
    ImageView ninthCell;

    @FXML
    ImageView player1TurnIcon;
    @FXML
    ImageView player2TurnIcon;

    @FXML
    AnchorPane matchResultAnchorPane;
    @FXML
    AnchorPane gameAnchorPane;

    @FXML
    Label drawResultLabel;
    @FXML
    Label player1ResultLabel;
    @FXML
    Label player2ResultLabel;

    @FXML
    Label player1ScoreLabel;
    @FXML
    Label player2ScoreLabel;


    public GameController() {
        restart();
    }

    @FXML
    void initialize() {
        restartUI();
    }

    @FXML
    void handleFirstCellMouseClicked() {
        if (isPlayer1Turn) {
            tableStatus[0][0] = CellStatus.PLAYER_1;
        } else {
            tableStatus[0][0] = CellStatus.PLAYER_2;
        }

        playerMove(firstCell);
    }

    @FXML
    void handleSecondCellMouseClicked() {
        if (isPlayer1Turn) {
            tableStatus[0][1] = CellStatus.PLAYER_1;
        } else {
            tableStatus[0][1] = CellStatus.PLAYER_2;
        }

        playerMove(secondCell);
    }

    @FXML
    void handleThirdCellMouseClicked() {
        if (isPlayer1Turn) {
            tableStatus[0][2] = CellStatus.PLAYER_1;
        } else {
            tableStatus[0][2] = CellStatus.PLAYER_2;
        }

        playerMove(thirdCell);
    }

    @FXML
    void handleFourthCellMouseClicked() {
        if (isPlayer1Turn) {
            tableStatus[1][0] = CellStatus.PLAYER_1;
        } else {
            tableStatus[1][0] = CellStatus.PLAYER_2;
        }

        playerMove(fourthCell);
    }

    @FXML
    void handleFifthCellMouseClicked() {
        if (isPlayer1Turn) {
            tableStatus[1][1] = CellStatus.PLAYER_1;
        } else {
            tableStatus[1][1] = CellStatus.PLAYER_2;
        }

        playerMove(fifthCell);
    }

    @FXML
    void handleSixthCellMouseClicked() {
        if (isPlayer1Turn) {
            tableStatus[1][2] = CellStatus.PLAYER_1;
        } else {
            tableStatus[1][2] = CellStatus.PLAYER_2;
        }

        playerMove(sixthCell);
    }

    @FXML
    void handleSeventhCellMouseClicked() {
        if (isPlayer1Turn) {
            tableStatus[2][0] = CellStatus.PLAYER_1;
        } else {
            tableStatus[2][0] = CellStatus.PLAYER_2;
        }

        playerMove(seventhCell);
    }

    @FXML
    void handleEighthCellMouseClicked() {
        if (isPlayer1Turn) {
            tableStatus[2][1] = CellStatus.PLAYER_1;
        } else {
            tableStatus[2][1] = CellStatus.PLAYER_2;
        }

        playerMove(eighthCell);
    }

    @FXML
    void handleNinthCellMouseClicked() {
        if (isPlayer1Turn) {
            tableStatus[2][2] = CellStatus.PLAYER_1;
        } else {
            tableStatus[2][2] = CellStatus.PLAYER_2;
        }

        playerMove(ninthCell);
    }

    @FXML
    void handleRestartButtonAction(ActionEvent event){
        restart();
        restartUI();
    }

    private void restart(){
        isPlayer1Turn = true;
        movesCounter = 0;
        tableStatus = new CellStatus[TABLE_SIZE][TABLE_SIZE];

        for (int i = 0; i < TABLE_SIZE; i++) {
            for (int j = 0; j < TABLE_SIZE; j++) {
                tableStatus[i][j] = CellStatus.BLANK;
            }
        }
    }

    private void restartUI(){
        gameAnchorPane.setDisable(false);
        matchResultAnchorPane.setVisible(false);

        player1ScoreLabel.setText(Integer.toString(player1Score));
        player2ScoreLabel.setText(Integer.toString(player2Score));

        if (isPlayer1Turn) {
            Image image = new Image(getClass().getClassLoader().getResource(X_ICON).toString());
            player1TurnIcon.setImage(image);
            player2TurnIcon.setImage(null);
        } else {
            Image image = new Image(getClass().getClassLoader().getResource(O_ICON).toString());
            player2TurnIcon.setImage(image);
            player1TurnIcon.setImage(null);
        }

        firstCell.setImage(null);
        secondCell.setImage(null);
        thirdCell.setImage(null);
        fourthCell.setImage(null);
        fifthCell.setImage(null);
        sixthCell.setImage(null);
        seventhCell.setImage(null);
        eighthCell.setImage(null);
        ninthCell.setImage(null);
    }

    private void playerMove(ImageView imageView) {
        if (imageView.getImage() == null) {

            Platform.runLater(() -> {
                Image image;

                if (isPlayer1Turn) {
                    player1TurnIcon.setImage(null);
                    image = new Image(getClass().getClassLoader().getResource(O_ICON).toString());
                    player2TurnIcon.setImage(image);

                    image = new Image(getClass().getClassLoader().getResource(X_ICON).toString());
                } else {
                    player2TurnIcon.setImage(null);
                    image = new Image(getClass().getClassLoader().getResource(X_ICON).toString());
                    player1TurnIcon.setImage(image);

                    image = new Image(getClass().getClassLoader().getResource(O_ICON).toString());
                }

                imageView.setImage(image);

                isPlayer1Turn = !isPlayer1Turn;
            });

            movesCounter++;
            checkMatchStatus();
        }
    }

    private void checkMatchStatus() {
        CellStatus winner = checkWinning();
        boolean endMatch = false;

        switch (winner) {
            case PLAYER_1:
                player1ResultLabel.setText(WINNER);
                player2ResultLabel.setText(LOOSER);
                drawResultLabel.setText("");
                endMatch = true;

                player1Score++;

                break;
            case PLAYER_2:
                player2ResultLabel.setText(WINNER);
                player1ResultLabel.setText(LOOSER);
                drawResultLabel.setText("");
                endMatch = true;

                player2Score++;

                break;

            case BLANK:
                if (movesCounter >= MAX_MOVES) {
                    endMatch = true;
                    player1ResultLabel.setText("");
                    player2ResultLabel.setText("");
                    drawResultLabel.setText(DRAW);
                }
        }

        if (endMatch) {
            Platform.runLater(() -> {
                player1TurnIcon.setImage(null);
                player2TurnIcon.setImage(null);
                gameAnchorPane.setDisable(true);
                matchResultAnchorPane.setVisible(true);
            });
        }
    }

    private CellStatus checkWinning() {
        for (int i = 0; i < TABLE_SIZE; i++) {
            if (tableStatus[i][0] == tableStatus[i][1] && tableStatus[i][1] == tableStatus[i][2]) {
                return tableStatus[i][0];
            }
        }

        for (int i = 0; i < TABLE_SIZE; i++) {
            if (tableStatus[0][i] == tableStatus[1][i] && tableStatus[1][i] == tableStatus[2][i]) {
                return tableStatus[0][i];
            }
        }

        if (tableStatus[0][0] == tableStatus[1][1] && tableStatus[1][1] == tableStatus[2][2]) {
            return tableStatus[0][0];
        }

        if (tableStatus[2][0] == tableStatus[1][1] && tableStatus[1][1] == tableStatus[0][2]) {
            return tableStatus[2][0];
        }

        return CellStatus.BLANK;
    }

    private void debugTable() {
        System.out.println("\nStatus:");
        for (CellStatus[] rowStatus : tableStatus) {
            for (CellStatus cellStatus : rowStatus) {
                System.out.print("|" + cellStatus + "|");
            }
            System.out.println();
        }
    }
}
