package network;

/**
 * Created by IntelliJ IDEA.
 * User: matteoranzi
 * Date: 27/02/19
 * Time: 0.18
 */
public class TicTacToeServer implements Runnable {

    @Override
    public void run() {

    }

    public void start(){
        Thread thread = new Thread(this);
        thread.start();
    }
}