package tictactoe.messages;

/**
 * Created by IntelliJ IDEA.
 * User: matteoranzi
 * Date: 14/03/19
 * Time: 21.49
 */
public class MoveMessage extends Message {
    private final int i, j;

    public MoveMessage(int i, int j){
        this.i = i;
        this.j = j;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }
}