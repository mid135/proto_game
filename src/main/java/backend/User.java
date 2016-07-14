package backend;

/**
 * Created by moskaluk on 24.02.2016. интерфейс юзера
 */

public interface User {
    String getLogin();

    String getEmail();

    void setEmail(String email);

    boolean checkPassword(String password);

}