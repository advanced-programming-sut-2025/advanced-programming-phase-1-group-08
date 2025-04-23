package model;

import model.Places.Farm;
import model.ToolsPackage.Tools;

public class User {

    private String username;
    private String password;
    private String nickname;
    private final String email;

    private Tile SleepTile;
    private int point;
    private int health;
    private int MAX_HEALTH;

    public Tools currentTool;
    private final Farm farm = new Farm();
    public int topLeftX;
    public int topLeftY;


    public User(String username, String password, String nickname, String email, int point, int health) {

        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.point = point;
        this.MAX_HEALTH = 200;
    }


    public int getMAX_HEALTH() {
        return MAX_HEALTH;
    }
    public void setMAX_HEALTH(int MAX_HEALTH) {
        this.MAX_HEALTH = MAX_HEALTH;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getEmail() {
        return email;
    }
    public int getPoint() {
        return point;
    }
    public void setPoint(int point) {
        this.point = point;
    }
    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
        checkHealth();
    }
    public void increaseHealth(int health) {
        this.health += health;
        checkHealth();
    }
    public Farm getFarm() {
        return farm;
    }

    public void checkHealth() {

        if (this.health > MAX_HEALTH)
            this.health = MAX_HEALTH;
        if (this.health < 0)
            this.health = 0;
    }
}
