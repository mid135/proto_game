package backend.Mechanics;

import backend.User;
import frontend.WebSocketServlets.GameWebSocket;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mid-s on 15.07.16.
 */
public class GameRoom {
    List<GameWebSocket> gameUserList = new ArrayList<>();

    public List<GameWebSocket> getUsers() {
        return gameUserList;
    }

    public void start() {

    }

    public String toJsonString() {
        JSONObject jsonObject = new JSONObject();
        Integer i = 0;
        for (GameWebSocket user: gameUserList) {
            jsonObject.put(user.getUser().getLogin(), user.getUser());
            i++;
        }
        return jsonObject.toJSONString();
    }

    public GameRoom(List<GameWebSocket> gameUserList) {
        this.gameUserList = gameUserList;

    }

}
