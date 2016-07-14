package frontend;

/**
 * Created by moskaluk on 03.03.2016.
 */

import backend.Mechanics.GameUser;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class WebSocketService {
    private Map<String, GameWebSocket> userSockets = new HashMap<String, GameWebSocket>();

    public void addUser(GameWebSocket user) {
    }

    public void notifyNewState(GameUser user1, GameUser user2) throws JSONException{

    }

    public void notifyStartGame(GameUser user) throws JSONException{
        GameWebSocket gameWebSocket = userSockets.get(user.getMyName());
        gameWebSocket.startGame(user);
    }


    public void notifyGameOver(GameUser user) {
        userSockets.get(user.getMyName()).gameOver( user.getMyName(), user.getScore(), user.getEnemy().getMyName(), user.getEnemy().getScore());
    }
}