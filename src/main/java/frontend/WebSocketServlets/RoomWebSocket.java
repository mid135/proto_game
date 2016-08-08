package frontend.WebSocketServlets;

import Utils.IdSingleton;
import backend.AccountService;
import backend.Mechanics.RoomMechanics;
import backend.User;
import frontend.WebSocketService.WebSocketGameService;
import frontend.WebSocketService.WebSocketRoomService;
import frontend.WebSocketService.WebSocketService;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * Created by mid-s on 03.08.16.
 */
@WebSocket
public class RoomWebSocket {
    private Integer id;
    private User user;
    private Session session;
    private RoomMechanics roomMechanics;
    private WebSocketRoomService webSocketRoomService;
    private AccountService accountService;

    public RoomWebSocket(User user, Thread roomMechanics,
                         WebSocketService webSocketService, AccountService accountService) {
        this.user = user;
        this.roomMechanics = (RoomMechanics) roomMechanics;
        this.webSocketRoomService = (WebSocketRoomService) webSocketService;
        this.accountService = accountService;
        this.id = IdSingleton.getNewId();
    }

    @OnWebSocketMessage
    public void onMessage(String data) throws JSONException {
        JSONObject inputJSON = new JSONObject();
        org.json.simple.parser.JSONParser parser = new JSONParser();
        try {
            inputJSON = (JSONObject) parser.parse(data.toString());

        } catch (ParseException e) {
            //TODO parse error
        }

        if (inputJSON.get("action").equals("create")) {
            webSocketRoomService.createRoom(user);
        } else if (inputJSON.get("action").equals("delete")) {
            webSocketRoomService.deleteRoom(Integer.valueOf(inputJSON.get("roomId").toString()));
        } else if (inputJSON.get("action").equals("join")) {
            webSocketRoomService.joinRoom(user, Integer.valueOf(inputJSON.get("roomId").toString()));
        } else if (inputJSON.get("action").equals("quit")) {
            webSocketRoomService.quitRoom(user, Integer.valueOf(inputJSON.get("roomId").toString()));
        } else {
            //TODO log fail action
        }

    }

    @OnWebSocketConnect
    public void onOpen(Session session) throws JSONException {
        setSession(session);
        webSocketRoomService.addUser(this);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        //TODO quit all rooms
        
        //TODO remove from service
    }

    public void sendMessage(String message) {
        try {
            this.session.getRemote().sendString(message);
        } catch (Exception e) {
            //TODO pring connection error
            System.out.print(message + e.toString());
        }
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public User getUser() {
        return user;
    }
}
