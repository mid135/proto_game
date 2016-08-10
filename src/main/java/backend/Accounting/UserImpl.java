package backend.Accounting;

import backend.User;
import org.json.simple.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by moskaluk on 24.02.2016.
 */
//TODO add unique Id
public class UserImpl implements User {
    private String login;
    private String password;
    private String email;
    private Integer roomId;//current room id

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserImpl user = (UserImpl) o;

        if (!login.equals(user.login)) return false;
        if (!password.equals(user.password)) return false;
        return email != null ? email.equals(user.email) : user.email == null;

    }

    @Override
    public int hashCode() {
        int result = login.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    public UserImpl(String login, String password) {
        this.login = login;
        this.password = getHash(password);
    }

    private String getHash(String param) {//для паролей
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(this.password.getBytes("UTF-8"));
            byte[] digest = md.digest();
            return new String(digest,"UTF-8");
        } catch (Exception e) {
            return "";
        }
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
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

    public JSONObject toJson() {
        JSONObject resp = new JSONObject();
        resp.put("name", this.getLogin());
        resp.put("email", this.getEmail());
        return resp;
    }
}
