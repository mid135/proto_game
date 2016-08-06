package frontend.WebSocketService;

import backend.User;
import frontend.WebSocketServlets.GameWebSocket;
import frontend.WebSocketServlets.RoomWebSocket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by mid-s on 06.08.16.
 */
public class WebSocketRoomService {
    private Map<User, RoomWebSocket> roomSockets = new ConcurrentHashMap<>();

    public void addUser(RoomWebSocket user) {
        this.roomSockets.put(user.getUser(), user);
    }
}
