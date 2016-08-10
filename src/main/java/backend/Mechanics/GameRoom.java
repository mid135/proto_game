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
    private Integer roomId;
    private User creator;
    List<User> gameUserList = new ArrayList<>();

    public GameRoom(List<User> gameUserList, Integer roomId, User creator) {
        this.roomId = roomId;
        this.gameUserList = gameUserList;
        this.creator = creator;
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

    public List<User> getUsers() {
        return gameUserList;
    }

    public User getCreator() {
        return creator;
    }

    public Integer getRoomId() {
        return roomId;
    }

}
