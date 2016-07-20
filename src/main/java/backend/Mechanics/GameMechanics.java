package backend.Mechanics;

import Utils.TimeHelper;
import backend.AccountService;
import backend.User;
import frontend.GameWebSocket;
import frontend.WebSocketService;
import org.json.JSONException;

import java.util.Map;

/**
 * Created by moskaluk on 03.03.2016.
 */
//TODO выделить интерфейс
public class GameMechanics {

    private static final int STEP_TIME = 50;
    WebSocketService webSocketService;
    AccountService accountService;

    public GameMechanics(WebSocketService webSocketService, AccountService p) {
        this.webSocketService = webSocketService;
        this.accountService = p;
    }

    public void run() throws JSONException {
        while (true) {
            gmStep();
            TimeHelper.sleep(STEP_TIME);
        }
    }

    public void changePosition(User u, Integer i) {

    }

    private void gmStep() throws JSONException {
        for (Map.Entry<String, GameRoom> entry : webSocketService.getGameRooms().entrySet()) {
            System.out.println("step");
            for (GameWebSocket user : entry.getValue().getUsers()) {
                if (user != null) {notifyNewState(user);}
            }
        }

    }

    private void startGame(String roomName) throws JSONException {
        webSocketService.notifyStartGame(roomName);
    }

    private void notifyNewState(GameWebSocket user) throws JSONException {
        user.sendNewState();
    }


}
