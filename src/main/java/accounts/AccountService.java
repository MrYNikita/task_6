package accounts;

import db.DBService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AccountService {
    private static final DBService db;
    private static final Map<String,UserProfile> loginToProfile;
    private static final Map<String,UserProfile> sessionIdToProfile;

    static {
        db = new DBService();
        loginToProfile = new HashMap<>();
        sessionIdToProfile = new HashMap<>();

        try {
            ResultSet rs = db.getStatement().executeQuery("select * from users");
            while (rs.next()) {
                loginToProfile.put(
                        rs.getString(1),
                        new UserProfile(
                                rs.getString(1),
                                rs.getString(2),
                                rs.getString(3)
                        )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addNewUser(UserProfile userProfile) throws SQLException {
        loginToProfile.put(userProfile.getLogin(), userProfile);
        db.getStatement().executeUpdate(
                String.format(
                    "insert into users (login,email,password) values ('%s','%s','%s');",
                        userProfile.getLogin(),
                        userProfile.getEmail(),
                        userProfile.getPassword()));
    }
    public static void addSession(String sessionId, UserProfile userProfile) { sessionIdToProfile.put(sessionId,userProfile); }
    public static void deleteSession(String sessionId) { sessionIdToProfile.remove(sessionId); }

    public static UserProfile getUserByLogin(String login) { return loginToProfile.get(login); }
    public static UserProfile getUserBySessionId(String sessionId) { return sessionIdToProfile.get(sessionId); }
}
