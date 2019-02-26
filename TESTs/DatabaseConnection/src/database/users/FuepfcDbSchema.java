package database.users;

/**
 * Created by IntelliJ IDEA.
 * User: matteoranzi
 * Date: 23/02/19
 * Time: 16.21
 */
public class FuepfcDbSchema {
    public static final class UsersTable{
        public static final String NAME = "users";

        public static final class Cols{
            public static final String ID = "id";
            public static final String FIRST_NAME = "first_name";
            public static final String LAST_NAME = "last_name";
            public static final String EMAIL = "email";
            public static final String USERNAME = "username";
            public static final String CRYPTED_PASSWORD = "crypted_password";
            public static final String PASSWORD_SALT = "password_salt";
            public static final String REGISTRATION_DATE  = "registration_date";

        }
    }
}