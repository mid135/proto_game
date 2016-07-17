package frontend;

import backend.AccountService;
import backend.Mechanics.GameMechanics;
import backend.User;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

/**
 * Created by moskaluk on 03.03.2016.
 */
public class CustomWebSocketCreator implements WebSocketCreator {
    private AccountService authService;
    private GameMechanics gameMechanics;
    private WebSocketService webSocketService;

    public CustomWebSocketCreator(AccountService authService,
                                  GameMechanics gameMechanics,
                                  WebSocketService webSocketService) {
        this.authService = authService;
        this.gameMechanics = gameMechanics;
        this.webSocketService = webSocketService;
    }

    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        String sessionId = req.getHttpServletRequest().getSession().getId();
        User user;
        try {
            user = authService.getArraySessionId().get(sessionId);
        } catch (Exception e) {
            return null;//никаких анонимов
        }
        return new GameWebSocket(user, gameMechanics, webSocketService, authService);
    }
}