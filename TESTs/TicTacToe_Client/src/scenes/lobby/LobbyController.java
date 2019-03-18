package scenes.lobby;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import tictactoe.TicTacToeClientManager;

import java.io.IOException;
import java.net.Socket;

import static tictactoe.GameParameters.TCP_SERVER_PORT;

/**
 * Created by IntelliJ IDEA.
 * User: matteoranzi
 * Date: 04/03/19
 * Time: 14.57
 */
public class LobbyController {
    public static final String WINDOW_TITLE = "TicTacToe - Lobby";

    public static final String SERVER_CONNECTION_MESSAGE = "Connecting to the server";
    public static final String WAITING_NEW_OPPONENT_MESSAGE = "Waiting for an opponent";

    private LoadingIndicatorManager loadingIndicatorManager;

    @FXML
    Label loadingIndicatorLabel;
    @FXML
    Label loadingMessageLabel;

    @FXML
    void initialize() {
        loadingIndicatorManager = new LoadingIndicatorManager(loadingIndicatorLabel, loadingMessageLabel, SERVER_CONNECTION_MESSAGE);
        loadingIndicatorManager.start();

        TicTacToeClientManager ticTacToeClientManager = new TicTacToeClientManager(this );
        ticTacToeClientManager.start();
    }

    public void setLoadingText(String text){
        Platform.runLater(()->{
            loadingIndicatorManager.setMessage(text);
        });
    }


    private class LoadingIndicatorManager implements Runnable {
        private static final int INDICATOR_SPEED = 600;//milliseconds
        private static final int INDICATOR_LENGTH = 3;//number of dots
        private static final String INDICATOR_ENTITY = ".";

        private Label loadingMessageLabel;
        private Label loadingIndicatorLabel;

        private boolean stop;

        public LoadingIndicatorManager(Label loadingIndicatorLabel, Label loadingMessageLabel, String message) {
            this.loadingIndicatorLabel = loadingIndicatorLabel;
            this.loadingMessageLabel = loadingMessageLabel;

            stop = false;

            loadingMessageLabel.setText(message);
        }

        @Override
        public void run() {
            while (!stop) {
                Platform.runLater(() -> {
                    loadingIndicatorLabel.setText("");
                });


                StringBuilder s = new StringBuilder();
                for (int i = 0; i <= INDICATOR_LENGTH; i++) {
                    String finalS = s.toString();
                    Platform.runLater(() -> {
                        loadingIndicatorLabel.setText(finalS);
                    });

                    s.append(INDICATOR_ENTITY);

                    try {
                        Thread.sleep(INDICATOR_SPEED);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        public void setMessage(String message) {
            Platform.runLater(() -> {
                loadingMessageLabel.setText(message);
            });
        }

        public void stop() {
            stop = true;
        }

        public void start() {
            Thread t = new Thread(this);
            t.setDaemon(true);
            t.start();
        }
    }
}