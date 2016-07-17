package backend.Accounting;

import backend.AccountService;
import backend.User;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by moskaluk on 24.02.2016. in-memmory реализация
 */
public class AccountServiceImpl implements AccountService {
    private Map<String, User> arraySessionId = new HashMap<String, User>();//все сессии пользователей - sessionId/UserImplMemory

    public Map<String, User> getUsers() {
        return users;
    }

    private Map<String, User> users = new HashMap<String, User>();//все зарегистрированые юзеры - логин/UserImplMemory

    public Map<String, User> getArraySessionId() {
        return this.arraySessionId;
    }

    public AccountEnum logIn(String login, String password, HttpServletRequest request) {
        if (checkLogIn(request) == AccountEnum.UserNotLoggedIn) {
            if ((this.checkRegistration(login) == AccountEnum.UserRegistered)) {
                if (users.get(login).checkPassword(password)) {
                    arraySessionId.put(request.getSession().getId(), users.get(login));
                    return AccountEnum.LogInSuccess;
                } else {
                    return AccountEnum.LogInFail;
                }
            } else {
                return AccountEnum.LogInFail;
            }
        } else {
            return AccountEnum.UserLoggedIn;
        }
    }

    public AccountEnum logOut(HttpServletRequest request) {
        if (arraySessionId.containsKey(request.getSession().getId())) {
            User cur = arraySessionId.get(request.getSession().getId());
            if ((this.checkRegistration(cur.getLogin()) == AccountEnum.UserRegistered)
                    && (this.checkLogIn(request) == AccountEnum.UserLoggedIn)) {
                arraySessionId.remove(request.getSession().getId());
                return AccountEnum.LogOffSuccess;
            } else {
                return AccountEnum.LogOffFail;
            }
        } else {
            return AccountEnum.LogOffFail;
        }
    }

    public AccountEnum checkRegistration(String userName) {
        if (users.containsKey(userName)) {
            return AccountEnum.UserRegistered;
        } else {
            return AccountEnum.UserNotRegistered;
        }
    }

    public AccountEnum checkLogIn(HttpServletRequest request) {
        if (arraySessionId.containsKey(request.getSession().getId())) {
            return AccountEnum.UserLoggedIn;
        } else {
            return AccountEnum.UserNotLoggedIn;
        }
    }

    public AccountEnum register(User user) {
            if (users.containsKey(user.getLogin())) {
            return AccountEnum.UserAlreadyExists;
        } else {
            users.put(user.getLogin(), user);
            return AccountEnum.RegisterSuccess;
        }
    }

    public AccountEnum editProfile(User user) {
        return null;
    }
}
