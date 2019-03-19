package com.fuepfc.database;

/**
 * Created by IntelliJ IDEA.
 * User: matteoranzi
 * Date: 23/02/19
 * Time: 16.21
 */
public class FuepfcDbSchema {
    public static final class UsersDataTable {
        public static final String NAME = "users_data";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String FIRST_NAME = "first_name";
            public static final String LAST_NAME = "last_name";
            public static final String EMAIL = "email";
            public static final String USERNAME = "username";
            public static final String CRYPTED_PASSWORD = "crypted_password";
            public static final String PASSWORD_SALT = "password_salt";
            public static final String REGISTRATION_DATE = "registration_date";
            public static final String SESSION_KEY = "session_key";
            public static final String SESSION_EXPIRATION_DATE = "session_expiration_date";
        }
    }

    public static final class GamesDataTable {
        public static final String NAME = "games_data";

        public static final class Cols {
            public static final String VERSION_UUID = "version_uuid";
            public static final String GAME_UUID = "game_uuid";
            public static final String GAME_NAME = "game_name";
            public static final String VERSION = "version";
        }
    }

    public static final class UsersScores {
        public static final String NAME = "users_scores";

        public static final class Cols {
            public static final String USER_UUID = "user_uuid";
            public static final String GAME_UUID = "game_uuid";
            public static final String TOTAL_SCORES = "total_scores";
            public static final String BEST_SCORE = "best_score";
            public static final String TOTAL_WINNING = "total_winning";
        }
    }

    public static final class UserSession {
        public static final String NAME = "users_session";

        public static final class Cols {
            public static final String UUID_USER = "uuid_user";
            public static final String UUID_GAME = "uuid_game";
            public static final String UUID_SESSION_KEY = "uuid_session_key";
        }

    }
}