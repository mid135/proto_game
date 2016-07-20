package frontend;

import backend.AccountService;
import backend.Mechanics.GameMechanics;
import backend.Mechanics.GameRoom;
import backend.Mechanics.GameUser;
import backend.User;
import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by moskaluk on 03.03.2016.
 */
@WebSocket
public class GameWebSocket {
    private User user;
    private Session session;
    private GameMechanics gameMechanics;
    private WebSocketService webSocketService;
    private AccountService accountService;

    public GameWebSocket(User user, GameMechanics gameMechanics,
                         WebSocketService webSocketService, AccountService accountService) {
        this.user = user;
        this.gameMechanics = gameMechanics;
        this.webSocketService = webSocketService;
        this.accountService = accountService;
    }

    public User getUser() {
        return user;
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name",user.getLogin());
        jsonObject.put("email",user.getEmail());
        return jsonObject;
    }

    public void startGame(GameWebSocket user) {
        try {
            JSONObject jsonStart = new JSONObject();
            jsonStart.put("status", "start");
            session.getRemote().sendString(jsonStart.toString());
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }

    public void gameOver(String myName, int myScore, String oponentName, int oponentScore) {
        try {
            JSONObject jsonStart = new JSONObject();
            jsonStart.put("status", "finish");
            jsonStart.put("win", myScore == oponentScore ? "deadHead" : (myScore > oponentScore ? "win" : "fail"));
            jsonStart.put("user", myName);
            jsonStart.put("myScore", myScore);
            jsonStart.put("oponentName", oponentName);
            jsonStart.put("oponentScore", oponentScore);
            session.getRemote().sendString(jsonStart.toString());
        } catch (Exception e) {
            //System.out.print(e.toString());
        }
    }

    @OnWebSocketMessage
    public void onMessage(String data) throws JSONException {
        JSONObject inputJSON = new JSONObject();
        org.json.simple.parser.JSONParser parser = new JSONParser();
        try {
            inputJSON = (JSONObject) parser.parse(data.toString());

        } catch (ParseException e) {}
        try {
            if (inputJSON.get("urn").equals("room")) {
                if (inputJSON.get("method").equals("post")) {
                    if (inputJSON.get("action").equals("create")) {
                        webSocketService.createRoom(user);
                    } else if (inputJSON.get("action").equals("join")) {
                        String roomName = (String) inputJSON.get("roomName");
                        webSocketService.joinRoom(accountService.getUsers().get(roomName), user);
                    }
                } else if (inputJSON.get("method").equals("get")) {
                    JSONArray rooms = new JSONArray();
                    for (Map.Entry<String, GameRoom> entry : webSocketService.getGameRooms().entrySet()){
                        JSONObject u1 = entry.getValue().getUser1() != null ? entry.getValue().getUser1().toJson(): null;
                        JSONObject u2 = entry.getValue().getUser2() != null ? entry.getValue().getUser2().toJson(): null;
                        JSONObject room = new JSONObject();
                        room.put("user1",u1);
                        room.put("user2",u2);
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put(entry.getKey(),room);
                        rooms.add(room);
                    }
                    session.getRemote().sendString(rooms.toJSONString());
                }
            } else if (inputJSON.get("urn").equals("game")) {
                gameMechanics.changePosition(user, 0);//stub
            }
        } catch (Exception ex) {
            int i = 0;
        }
    }

    public void sendNewState () {
        try {
            JSONObject jsonStart = new JSONObject();
            jsonStart.put("stub", "stub");
            session.getRemote().sendString(jsonStart.toString());
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }

    @OnWebSocketConnect
    public void onOpen(Session session) throws JSONException {
        setSession(session);
        webSocketService.addUser(this);
        //gameMechanics.addUser(user);
    }

    public void setState(GameUser user) throws JSONException {
        JSONObject myJson = new JSONObject();
        myJson.put("status", "game");
        myJson.put("myState", user.getState());
        myJson.put("enemyState", user.getEnemy().getState());
        try {
            session.getRemote().sendString(myJson.toString());
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        //TODO а тут что делать? - перейти на нужную вьюху!!!
        //  this.gameMechanics.gameOver(this.user);
    }
}