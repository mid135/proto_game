package backend.Mechanics;

import backend.User;
import frontend.WebSocketServlets.GameWebSocket;
import frontend.WebSocketServlets.RoomWebSocket;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mid-s on 15.07.16.
 */
public class GameRoom {
    private Integer roomId;
    private User creator;
    private String roomName;

    List<User> gameUserList = new ArrayList<>();
    public GameRoom(List<User> gameUserList, Integer roomId, User creator, String roomName) {
        this.roomId = roomId;
        this.gameUserList = gameUserList;
        this.creator = creator;
        this.roomName = roomName;
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("roomId",roomId.toString());
        jsonObject.put("roomName",roomName);
        JSONObject players = new JSONObject();
        for (User user: gameUserList) {
            players.put(user.getLogin(), user.toJson());
        }
        jsonObject.put("players",players);
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

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

}
