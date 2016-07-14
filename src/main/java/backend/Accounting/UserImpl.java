package backend.Accounting;

import backend.User;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by moskaluk on 24.02.2016.
 */
public class UserImpl implements User {
    private String login;
    private String password;
    private String email;

    public UserImpl(String login, String password) {
        this.login = login;
        this.password = getHash(password);
    }

    private String getHash(String param) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(this.password.getBytes("UTF-8"));
            byte[] digest = md.digest();
            return new String(digest,"UTF-8");
        } catch (Exception e) {
            return "";
        }
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean checkPassword(String password) {
        if (getHash(password).equals(getHash(this.password))) {
            return true;
        } else {
            return false;
        }
    }
}
