package backend;

import org.json.simple.JSONObject;

/**
 * Created by moskaluk on 24.02.2016. интерфейс юзера
 */

public interface User {
    JSONObject toJson();

    boolean checkPassword(String password);

    String getLogin();

    String getEmail();

    Integer getRoomId();

    void setEmail(String email);

    void setRoomId(Integer roomId);

}