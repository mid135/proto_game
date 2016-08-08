package frontend.WebSocketService;

import Utils.IdSingleton;
import backend.Mechanics.GameRoom;
import backend.User;
import frontend.WebSocketServlets.GameWebSocket;
import frontend.WebSocketServlets.RoomWebSocket;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by mid-s on 06.08.16.
 */
public class WebSocketRoomService implements WebSocketService {
    private Map<User, RoomWebSocket> roomSockets = new ConcurrentHashMap<>();//конекты на получение состояний комнат
    private Map<Integer, GameRoom> gameRooms = new ConcurrentHashMap<>();//карта с комнатами

    public GameRoom createRoom(User creator) {
        List<User> userList = new ArrayList<>();
        userList.add(creator);
        Integer roomId = IdSingleton.getNewId();
        GameRoom room = new GameRoom(userList, roomId);
        gameRooms.put(roomId, room);
        return room;
    }

    public void deleteRoom(Integer id) {
        gameRooms.remove(id);
    }

    public void joinRoom(User user, Integer roomId) {
        gameRooms.get(roomId).getUsers().add(user);
    }

    public void quitRoom(User user, Integer roomId) {
        gameRooms.get(roomId).getUsers().remove(user);
    }

    public JSONObject getRoomsJson() {
        JSONObject rooms = new JSONObject();
        for (Map.Entry<Integer,GameRoom> entry: gameRooms.entrySet()) {
            rooms.put(entry.getKey().toString(), entry.getValue().toJson());
        }
        return rooms;
    }
    /**
     * добавить сокет в список рассылки состояний комнат
     * @param user
     */
    public void addUser(RoomWebSocket user) {
        this.roomSockets.put(user.getUser(), user);
    }

    /**
     * удалить пользователя из списка рассылки состояний комнат
     * @param user
     */
    public void deleteUser(User user) {
        this.roomSockets.remove(user);
    }

    public Map<User, RoomWebSocket> getRoomSockets() {
        return roomSockets;
    }

    public Map<Integer, GameRoom> getGameRooms() {
        return gameRooms;
    }
}
