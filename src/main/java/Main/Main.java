package Main; /**
 * Created by moskaluk on 20.02.2016. main file
 */

import backend.AccountService;
import backend.Accounting.AccountServiceImpl;
import backend.Mechanics.GameMechanics;
import frontend.AccountServlets.Auth;
import frontend.AccountServlets.LogOut;
import frontend.AccountServlets.Registration;
import frontend.WebSocketGameServlet;
import frontend.WebSocketService;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {
    public static void main(String[] args) throws Exception {

        AccountService pool = new AccountServiceImpl();

        Auth auth = new Auth(pool);
        Registration register = new Registration(pool);
        LogOut logOut = new LogOut(pool);

        Server server = new Server(9000);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        server.setHandler(context);
        context.addServlet(new ServletHolder(auth), "/auth");
        context.addServlet(new ServletHolder(register),"/register");
        context.addServlet(new ServletHolder(logOut),"/logout");

        WebSocketService webSocketService = new WebSocketService();
        GameMechanics gameMechanics = new GameMechanics(webSocketService,pool);
        context.addServlet(new ServletHolder(new WebSocketGameServlet(pool, gameMechanics, webSocketService)), "/gameplay");


        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setResourceBase("/home/mid-s/java project/proto_game_front");
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});

        server.setHandler(handlers);

        server.start();
        gameMechanics.run();
        server.join();
    }
}
