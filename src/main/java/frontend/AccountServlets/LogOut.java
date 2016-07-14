package frontend.AccountServlets;

import backend.AccountService;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by moskaluk on 24.02.2016.
 */
public class LogOut extends HttpServlet {
    private AccountService pool;

    public LogOut(AccountService pool) {
        this.pool = pool;
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        pool.logOut(request);
        JSONObject resp = new JSONObject();
        try {
            resp.put("status", "1");
            resp.put("message", "logout complete");
        } catch (JSONException e) {
        }
        response.getWriter().println(resp.toString());
        response.setStatus(HttpServletResponse.SC_OK);
    }

}