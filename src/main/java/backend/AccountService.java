package backend; /**
 * Created by moskaluk on 24.02.2016. Сервис отвечающий за аккаунтинг - логин/логоф/регистрацию
 */
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import backend.Accounting.AccountEnum;

public interface AccountService {
    Map<String, User> getArraySessionId();
    Map<String, User> getUsers();
    AccountEnum logIn(String login, String password, HttpServletRequest request);
    AccountEnum logOut (HttpServletRequest request) ;
    AccountEnum checkRegistration(String userName) ;
    AccountEnum checkLogIn (HttpServletRequest request) ;
    AccountEnum register(User user);//регистрация пользователя
    AccountEnum editProfile(User user);
}
