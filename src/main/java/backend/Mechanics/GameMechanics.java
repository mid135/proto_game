package backend.Mechanics;

import Utils.TimeHelper;
import backend.AccountService;
import backend.User;
import frontend.WebSocketServlets.GameWebSocket;
import frontend.WebSocketService.WebSocketGameService;
import org.json.JSONException;

import java.util.Map;

/**
 * Created by moskaluk on 03.03.2016.
 */
//TODO выделить интерфейс
public class GameMechanics extends Thread {

    private static final int STEP_TIME = 50;
    WebSocketGameService webSocketGameService;
    AccountService accountService;

    public GameMechanics(WebSocketGameService webSocketGameService, AccountService p) {
        this.webSocketGameService = webSocketGameService;
        this.accountService = p;
    }

    @Override
    public void run() {
        while (true) {
            gmStep();
            TimeHelper.sleep(STEP_TIME);
        }
    }

    public void changePosition(User u, Integer i) {

    }

    private void gmStep() {
        for (Map.Entry<String, GameRoom> entry : webSocketGameService.getGameRooms().entrySet()) {

        }
    }


}
