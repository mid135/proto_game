package frontend;

import backend.AccountService;
import backend.Mechanics.GameMechanics;
import backend.Mechanics.RoomMechanics;
import backend.User;
import frontend.WebSocketServlets.GameWebSocket;
import frontend.WebSocketServlets.RoomWebSocket;
import frontend.WebSocketService.WebSocketGameService;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

/**
 * Created by moskaluk on 03.03.2016.
 */
public class CustomWebSocketCreator implements WebSocketCreator {
    private AccountService authService;
    private Thread mechanics;
    private WebSocketGameService webSocketGameService;

    public CustomWebSocketCreator(AccountService authService,
                                  Thread mechanics,
                                  WebSocketGameService webSocketGameService) {
        this.authService = authService;
        this.mechanics = mechanics;
        this.webSocketGameService = webSocketGameService;
    }

    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        String sessionId = req.getHttpServletRequest().getSession().getId();
        User user;
        try {
            user = authService.getArraySessionId().get(sessionId);
        } catch (Exception e) {
            return null;//никаких анонимов
        }
        if (mechanics instanceof GameMechanics) {
            System.out.print("game_socket");
            return new GameWebSocket(user, mechanics, webSocketGameService, authService);
        } else if (mechanics instanceof RoomMechanics) {
            System.out.print("room_socket");
            return new RoomWebSocket(user, mechanics, webSocketGameService, authService);
        }
        return null;
    }
}