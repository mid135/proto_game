package backend.Mechanics;

import backend.User;
import frontend.WebSocketServlets.GameWebSocket;
import frontend.WebSocketServlets.RoomWebSocket;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mid-s on 15.07.16.
 */
public class GameRoom {
    List<User> gameUserList = new ArrayList<>();
    private Integer roomId;

    public List<User> getUsers() {
        return gameUserList;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        Integer i = 0;
        for (User user: gameUserList) {
            jsonObject.put(user.getLogin(), user.toJson());
            i++;
        }
        return jsonObject;
    }

    public GameRoom(List<User> gameUserList, Integer roomId) {
        this.roomId = roomId;
        this.gameUserList = gameUserList;

    }

}
