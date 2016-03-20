package frontend.AccountServlets;

/**
 * Created by moskaluk on 24.02.2016.
 */

import backend.AccountService;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Auth extends HttpServlet {
    public AccountService pool;

    public Auth(AccountService p) {
        pool = p;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");

        JSONObject json = new JSONObject();
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        try {
            switch (this.pool.logIn(login, password, request)) {
                case LogInSuccess: {
                    json.put("user", pool.getArraySessionId().get(request.getSession().getId()).getLogin());
                    json.put("email", pool.getArraySessionId().get(request.getSession().getId()).getEmail());
                    json.put("status", "1");
                    response.setStatus(HttpServletResponse.SC_OK);
                    break;
                }
                case UserLoggedIn: {
                    json.put("user", pool.getArraySessionId().get(request.getSession().getId()).getLogin());
                    json.put("email", pool.getArraySessionId().get(request.getSession().getId()).getEmail());
                    json.put("status", "1");
                    response.setStatus(HttpServletResponse.SC_OK);
                    break;
                }
                case LogInFail: {
                    json.put("status", "unable to login");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    break;
                }
                default: {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    break;
                }
            }
        } catch (JSONException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        response.getWriter().println(json.toString());
    }
}
