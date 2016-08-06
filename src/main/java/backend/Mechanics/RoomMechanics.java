package backend.Mechanics;

import Utils.TimeHelper;
import backend.AccountService;
import frontend.WebSocketServlets.GameWebSocket;
import frontend.WebSocketService.WebSocketGameService;

import java.util.Map;

/**
 * Created by mid-s on 21.07.16.
 */
public class RoomMechanics extends Thread {
    private static Integer STEP_TIME = 500;
    private WebSocketGameService webSocketGameService;
    private AccountService accountService;

    public RoomMechanics(WebSocketGameService webSocketGameService, AccountService accountService) {
        this.webSocketGameService = webSocketGameService;
        this.accountService = accountService;
    }

    @Override
    public void run() {
        while (true) {
            rmStep();
            TimeHelper.sleep(STEP_TIME);
        }
    }

    private void rmStep() {
        for (Map.Entry<String, GameRoom> entry : webSocketGameService.getGameRooms().entrySet()) {
            System.out.println("room step");
            for (GameWebSocket user: entry.getValue().getUsers()) {
                try {
                    //user.sendRooms();
                    System.out.println("room sent to user"+user.getUser().getLogin());
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
            }
        }
    }
}
