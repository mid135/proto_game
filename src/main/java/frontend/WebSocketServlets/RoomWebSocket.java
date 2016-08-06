package frontend.WebSocketServlets;

import backend.AccountService;
import backend.Mechanics.RoomMechanics;
import backend.User;
import frontend.WebSocketService.WebSocketGameService;
import frontend.WebSocketService.WebSocketRoomService;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created by mid-s on 03.08.16.
 */
@WebSocket
public class RoomWebSocket {
    private User user;
    private Session session;
    private RoomMechanics roomMechanics;
    private WebSocketRoomService webSocketRoomService;
    private AccountService accountService;
    public RoomWebSocket(User user, Thread roomMechanics,
                         WebSocketRoomService webSocketRoomService, AccountService accountService) {
        this.user = user;
        this.roomMechanics = (RoomMechanics) roomMechanics;
        this.webSocketRoomService = webSocketRoomService;
        this.accountService = accountService;
    }

    @OnWebSocketMessage
    public void onMessage(String data) throws JSONException {
        JSONObject inputJSON = new JSONObject();
        org.json.simple.parser.JSONParser parser = new JSONParser();
        try {
            inputJSON = (JSONObject) parser.parse(data.toString());

        } catch (ParseException e) {
        }
        try {
            //todo process room message - create delete join quit start
        } catch (Exception e) {

        }

    }

    @OnWebSocketConnect
    public void onOpen(Session session) throws JSONException {
        setSession(session);
        webSocketRoomService.addUser(this);
        //gameMechanics.addUser(user);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {

    }

    public void setSession(Session session) {
        this.session = session;
    }

    public User getUser() {
        return user;
    }
}
