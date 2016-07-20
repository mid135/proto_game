package frontend;

/**
 * Created by moskaluk on 03.03.2016.
 */

import backend.Mechanics.GameRoom;
import backend.Mechanics.GameUser;
import backend.User;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class WebSocketService {
    private Map<User, GameWebSocket> userSockets = new HashMap<>();
    private Map<String, GameRoom> gameRooms = new HashMap<>();

    public Map<User, GameWebSocket> getUserSockets() {
        return userSockets;
    }

    //имя комнаты сопадает с логином создателя
    public Map<String, GameRoom> getGameRooms() {
        return gameRooms;
    }

    public void addUser(GameWebSocket user) {
        this.userSockets.put(user.getUser(), user);
    }

    public void createRoom(User user) {
        if (user != null && gameRooms.get(user.getLogin()) == null) {
            gameRooms.put(user.getLogin(), new GameRoom(userSockets.get(user), null));
        }
    }

    public void joinRoom(User roomCreator, User user) {

        if (gameRooms.get(roomCreator.getLogin()) != null) {
            gameRooms.get(roomCreator.getLogin()).setUser2(userSockets.get(user));
        }
        gameRooms.get(roomCreator.getLogin()).start();
    }

    public void notifyStartGame(String roomName) {
        GameRoom room = gameRooms.get(roomName);
        for (GameWebSocket user : room.getUsers()) {

        }
    }

}