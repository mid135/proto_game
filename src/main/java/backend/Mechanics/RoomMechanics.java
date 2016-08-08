package backend.Mechanics;

import Utils.TimeHelper;
import backend.AccountService;
import backend.User;
import frontend.WebSocketService.WebSocketRoomService;
import frontend.WebSocketServlets.GameWebSocket;
import frontend.WebSocketService.WebSocketGameService;
import frontend.WebSocketServlets.RoomWebSocket;
import org.eclipse.jetty.websocket.api.SuspendToken;
import org.json.simple.JSONObject;

import java.util.Map;

/**
 * Created by mid-s on 21.07.16.
 */
public class RoomMechanics extends Thread {
    private static Integer STEP_TIME = 500;
    private WebSocketRoomService webSocketRoomService;
    private AccountService accountService;

    public RoomMechanics(WebSocketRoomService webSocketRoomService, AccountService accountService) {
        this.webSocketRoomService = webSocketRoomService;
        this.accountService = accountService;
    }

    @Override
    public void run() {
        int i = 0;
        while (true) {
            System.out.print("gameroom iteration " + i + ": ");
            rmStep();
            TimeHelper.sleep(STEP_TIME);
            i++;
        }
    }

    private void rmStep() {
        //комнаты нужно все один раз преобразовать а потом всем отослать одинаковый результат
        JSONObject rooms = webSocketRoomService.getRoomsJson();
        System.out.println(rooms.toJSONString());
        for (Map.Entry<User, RoomWebSocket> entry : webSocketRoomService.getRoomSockets().entrySet()) {
            entry.getValue().sendMessage(rooms.toJSONString());
        }
    }
}
