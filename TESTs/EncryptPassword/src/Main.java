import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * Created by IntelliJ IDEA.
 * User: matteoranzi
 * Date: 25/02/19
 * Time: 19.22
 */

public class Main {
    public static void main(String[] args){
        String ps_hash = BCrypt.hashpw("matteo", BCrypt.gensalt());
        System.out.println(ps_hash);
    }
}