/**
 * Created by moskaluk on 20.02.2016. main file
 */
import backend.AccountService;
import backend.Accounting.AccountServiceImpl;
import backend.Mechanics.MyWebSocketHandler;
import frontend.AccountServlets.Auth;
import frontend.AccountServlets.Registration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

public class Main {
    public static void main(String[] args) throws Exception {

        AccountService pool = new AccountServiceImpl();

        Auth auth = new Auth(pool);
        Registration register = new Registration(pool);

        Server server = new Server(10000);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        server.setHandler(context);
        context.addServlet(new ServletHolder(auth), "/auth");
        context.addServlet(new ServletHolder(register),"/register");
        server.setHandler(context);
        server.start();
        server.join();
    }
}
