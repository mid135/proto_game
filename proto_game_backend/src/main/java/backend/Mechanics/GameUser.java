package backend.Mechanics;

import backend.User;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by moskaluk on 03.03.2016.
 */
public class GameUser {
    private String name;
    private GameUser enemy;
    private User user;
    private int score;

    public GameUser(int x,int y) {
        this.score = 0;
    }

    public void setUser(User nam) {
        this.name=nam.getLogin();
        this.user = nam;
    }

    public void incrementScore() {
        this.score = score + 1;
    }

    public int getScore() {
        return score;
    }

    public GameUser getEnemy() {
        return enemy;
    }

    public void setEnemyName(GameUser enemyName) {
        this.enemy = enemyName;
    }

    public JSONObject getState() {
        JSONObject resp = new JSONObject();
        try {
            resp.put("x",0);
            resp.put("y",0);
        } catch(JSONException e) {}

        return resp;
    }

    public User getUser() {
        return user;
    }

    public String getMyName() {
        return name;
    }
}