import com.fuepfc.minidriver.ClientMinidriver;

/**
 * Created by IntelliJ IDEA.
 * User: matteoranzi
 * Date: 11/03/19
 * Time: 15.56
 */

public class Main {
    public static void main(String[] args){
        ClientMinidriver clientMinidriver = new ClientMinidriver(4);
        clientMinidriver.debug();
        clientMinidriver.setX(clientMinidriver.getX() + 50);
        clientMinidriver.debug();
    }
}