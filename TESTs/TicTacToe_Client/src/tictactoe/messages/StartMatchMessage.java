package tictactoe.messages;

/**
 * Created by IntelliJ IDEA.
 * User: matteoranzi
 * Date: 14/03/19
 * Time: 20.58
 */
public class StartMatchMessage extends Message {
    public enum Player {
        PLAYER_1,
        PLAYER_2
    }

    private final Player player;

    public StartMatchMessage(Player p){
        this.player = p;
    }

    public Player getPlayer() {
        return player;
    }
}