package model;

import model.Places.Farm;
import model.ToolsPackage.Tools;

public class User {

    private String username;
    private String password;
    private String nickname;
    private final String email;
    private int point;
    public static int health;
    public Tools currentTool;
    private Farm farm=new Farm();
    public int topLeftX;
    public int topLeftY;


    public User(String username, String password, String nickname, String email, int point, int health) {

        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.point = point;
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
    }
    public void increaseHealth(int health) {
        this.health += health;
    }
    public Farm getFarm() {
        return farm;
    }
}
