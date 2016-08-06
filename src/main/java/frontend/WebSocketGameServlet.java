package frontend;


import backend.AccountService;
import frontend.WebSocketService.WebSocketGameService;
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
    private WebSocketGameService webSocketGameService;

    public WebSocketGameServlet(AccountService authService,
                                Thread mechanics,
                                WebSocketGameService webSocketGameService) {
        this.accountService = authService;
        this.mechanics = mechanics;
        this.webSocketGameService = webSocketGameService;
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(IDLE_TIME);
        factory.setCreator(new CustomWebSocketCreator(accountService, mechanics, webSocketGameService));
    }
}