package backend.Mechanics;

import backend.User;
import frontend.GameWebSocket;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mid-s on 15.07.16.
 */
public class GameRoom {
    GameWebSocket user1;
    GameWebSocket user2;

    public List<GameWebSocket> getUsers() {
        ArrayList<GameWebSocket> res = new ArrayList<>();
        res.add(user1);
        res.add(user2);
        return res;
    }

    public void start() {
        if (user1 != null && user2 != null && user1 != user2) {
            user1.startGame(user2);
            user2.startGame(user1);
        }
    }

    public String toJsonString() {
        JSONObject jsonObject = new JSONObject();
        if (user1 != null ) {
            jsonObject.put("user1", user1.getUser());
        }
        if (user2 != null) {
            jsonObject.put("user2", user2.getUser());
        }
        return jsonObject.toJSONString();
    }

    public GameWebSocket getUser1() {
        return user1;
    }

    public void setUser1(GameWebSocket user1) {
        this.user1 = user1;
    }

    public GameWebSocket getUser2() {
        return user2;
    }

    public void setUser2(GameWebSocket user2) {
        this.user2 = user2;
    }

    public GameRoom(GameWebSocket user1, GameWebSocket user2) {
        this.user1 = user1;
        this.user2 = user2;
    }

}
