package accounts;

import java.io.File;

public class UserProfile {
    private final String login;
    private final String email;
    private final String password;

    private final String mainDirectory = "D:\\Y_Projects\\Y_Java\\task_3\\filesUsers";

    public UserProfile(String l, String e, String p) {
        login = l;
        email = e;
        password = p;

        createHomeDirectory();
    }
    public UserProfile(String l) {
        login = l;
        email = l;
        password = l;

        createHomeDirectory();
    }

    public String getLogin() { return login; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }

    private void createHomeDirectory() { new File(mainDirectory + "\\" + login).mkdir(); }
}
