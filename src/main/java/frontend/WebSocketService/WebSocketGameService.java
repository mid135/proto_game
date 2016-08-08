package frontend.WebSocketService;

/**
 * Created by moskaluk on 03.03.2016.
 */

import backend.Mechanics.GameRoom;
import backend.User;
import frontend.WebSocketServlets.GameWebSocket;
import frontend.WebSocketServlets.RoomWebSocket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketGameService implements WebSocketService {
    private Map<User, GameWebSocket> userSockets = new ConcurrentHashMap<>();
    private Map<String, GameRoom> gameRooms = new ConcurrentHashMap<>();

    public Map<User, GameWebSocket> getSockets() {
        return userSockets;
    }


    public Map<String, GameRoom> getGameRooms() {
        return gameRooms;
    }

    public void addUser(GameWebSocket user) {
        this.userSockets.put(user.getUser(), user);
    }

    public void deleteUser() {

    }


    public void startGame(String roomName) {
//        GameRoom room = gameRooms.get(roomName);
//        for (GameWebSocket user : room.getUsers()) {
//            //todo start game
//        }
    }

}