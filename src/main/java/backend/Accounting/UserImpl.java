package backend.Accounting;

import Utils.IdSingleton;
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
    private Integer userId;
    private String login;
    private String password;
    private String email;
    private Integer roomId;//current room id

    public UserImpl(String login, String password) {
        this.login = login;
        this.password = getHash(password);
        this.userId = IdSingleton.getNewId();
    }

    private String getHash(String param) {//для паролей
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(this.password.getBytes("UTF-8"));
            byte[] digest = md.digest();
            return new String(digest, "UTF-8");
        } catch (Exception e) {
            return "";
        }
    }

    public Integer getRoomId() {
        return roomId;
    }

    public Integer getUserId() {
        return userId;
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
        resp.put("id", this.getUserId());
        resp.put("name", this.getLogin());
        resp.put("email", this.getEmail());
        return resp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserImpl user = (UserImpl) o;

        if (!userId.equals(user.userId)) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        return roomId != null ? roomId.equals(user.roomId) : user.roomId == null;

    }

    @Override
    public int hashCode() {
        int result = userId.hashCode();
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (roomId != null ? roomId.hashCode() : 0);
        return result;
    }
}
