package frontend.WebSocketServlets;

import backend.AccountService;
import backend.Mechanics.GameMechanics;
import backend.User;
import frontend.WebSocketService.WebSocketGameService;
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
 * Created by moskaluk on 03.03.2016.
 */
@WebSocket
public class GameWebSocket {
    private User user;
    private Session session;
    private GameMechanics gameMechanics;
    private WebSocketGameService webSocketGameService;
    private AccountService accountService;

    public GameWebSocket(User user, Thread gameMechanics,
                         WebSocketGameService webSocketGameService, AccountService accountService) {
        this.user = user;
        this.gameMechanics = (GameMechanics) gameMechanics;
        this.webSocketGameService = webSocketGameService;
        this.accountService = accountService;
    }

    public User getUser() {
        return user;
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", user.getLogin());
        jsonObject.put("email", user.getEmail());
        return jsonObject;
    }

    public void startGame(GameWebSocket user) {

    }

    public void gameOver(String myName, int myScore, String oponentName, int oponentScore) {

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
            //todo process game message
        } catch (Exception e) {

        }

    }

    @OnWebSocketConnect
    public void onOpen(Session session) throws JSONException {
        setSession(session);
        webSocketGameService.addUser(this);
        //gameMechanics.addUser(user);
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