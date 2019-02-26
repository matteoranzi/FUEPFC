import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: matteoranzi
 * Date: 26/02/19
 * Time: 23.21
 */
public class Main {
    public static void main(String[] args){
        try {
            Runtime.getRuntime().exec("java -jar /home/matteoranzi/Documenti/Projects/FUEPFC/TESTs/TicTacToe/out/artifacts/TicTacToe_jar/TicTacToe.jar");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}