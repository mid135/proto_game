package frontend;


import backend.AccountService;
import frontend.WebSocketService.WebSocketGameService;
import frontend.WebSocketService.WebSocketService;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import javax.servlet.annotation.WebServlet;

/**
 * This class represents a servlet starting a webSocket application
 */
@WebServlet(name = "WebSocketGameServlet")
public class WebSocketGameServlet extends WebSocketServlet {
    private final static int IDLE_TIME = 60 * 1000;//60 sec
    private AccountService accountService;
    private Thread mechanics;
    private WebSocketService webSocketService;

    public WebSocketGameServlet(AccountService authService,
                                Thread mechanics,
                                WebSocketService webSocketService) {
        this.accountService = authService;
        this.mechanics = mechanics;
        this.webSocketService = webSocketService;
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(IDLE_TIME);
        factory.setCreator(new CustomWebSocketCreator(accountService, mechanics, webSocketService));
    }
}