package frontend.AccountServlets;

import backend.AccountService;
import backend.Accounting.AccountEnum;
import backend.Accounting.UserImpl;
import backend.User;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by moskaluk on 24.02.2016. сервлет регистрации
 */
public class Registration extends HttpServlet {
    AccountService pool;

    public Registration(AccountService users) {
        this.pool = users;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {//обработчик кнопки отлогинивания
        response.setContentType("text/html;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        JSONObject json = new JSONObject();
        User user = new UserImpl(request.getParameter("login"),request.getParameter("password"));
        try {
            if (pool.checkRegistration(user.getLogin()) == AccountEnum.UserRegistered) {
                json.put("status", "0");
                json.put("message","userExist");
                response.setStatus(HttpServletResponse.SC_CONFLICT);
            } else {
                if (pool.register(user) == AccountEnum.RegisterSuccess) {
                    json.put("status", "1");
                    json.put("message","success");
                    response.setStatus(HttpServletResponse.SC_OK);
                } else {
                    json.put("status", "0");
                    json.put("message","userExist");
                    response.setStatus(HttpServletResponse.SC_CONFLICT);
                }
            }
        } catch (JSONException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        response.getWriter().println(json.toString());
    }

}
