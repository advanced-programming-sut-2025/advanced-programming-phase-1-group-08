package model;

import model.Enum.SecurityQuestions;
import model.Places.Farm;
import model.ToolsPackage.BackPack;
import model.ToolsPackage.Tools;

public class User {

    private String username;
    private String password;
    private String nickname;
    private String email;
    private final String gender;
    private SecurityQuestions MySecurityQuestion;
    private String MySecurityAnswer;

    private int max_point;
    private int games_played;


    private int point;
    private int health;
    private int MAX_HEALTH;
    private Tile SleepTile;
    private User Married;  // شخصی که باهاش ازدواج کرده
    private boolean healthUnlimited;


    private final BackPack backPack = new BackPack();
    public Tools currentTool;
    private int farmingAbility  = 0; //مهارت کشاورزی که در ابتدا صفر هست.
    private int miningAbility   = 0;
    private int foragingAbility = 0;
    private int fishingAbility  = 0;
    private int money;



    private final Farm farm = new Farm();
    public int topLeftX;
    public int topLeftY;
    private int positionX;
    private int positionY;




    public User(String username, String password, String nickname, String email, String gender,  int point, int health) {

        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
        this.point = point;
        this.MAX_HEALTH = 200;
        this.healthUnlimited = false;
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
    public void setEmail(String email) {this.email = email;}
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
    public int getPositionX() {
        return positionX;
    }
    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }
    public int getPositionY() {
        return positionY;
    }
    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public User getMarried() {
        return Married;
    }
    public void setHealthUnlimited () {this.healthUnlimited = true;}
    public boolean isHealthUnlimited() {
        return healthUnlimited;
    }

    public SecurityQuestions getMySecurityQuestion() {
        return MySecurityQuestion;
    }
    public void setMySecurityQuestion(SecurityQuestions mySecurityQuestion) {
        MySecurityQuestion = mySecurityQuestion;
    }
    public String getMySecurityAnswer() {
        return MySecurityAnswer;
    }
    public void setMySecurityAnswer(String mySecurityAnswer) {
        MySecurityAnswer = mySecurityAnswer;
    }
    public int getMax_point() {
        return max_point;
    }
    public void setMax_point(int max_point) {
        this.max_point = max_point;
    }
    public int getGames_played() {
        return games_played;
    }
    public void setGames_played(int games_played) {
        this.games_played = games_played;
    }


    public BackPack getBackPack() {
        return backPack;
    }

    public int getMoney() {
        return money;
    }

    public void increaseMoney (int amount) {
        this.money += amount;
    }

    public int getLevelFarming() {
        if (this.farmingAbility < 50)
            return 0;
        if (this.farmingAbility < 100)
            return 1;
        if (this.farmingAbility < 250)
            return 2;
        if (this.farmingAbility < 350)
            return 3;
        return 4;
    }
    public int getLevelFishing() {
        if (this.fishingAbility < 50)
            return 0;
        if (this.fishingAbility < 100)
            return 1;
        if (this.fishingAbility < 250)
            return 2;
        if (this.fishingAbility < 350)
            return 3;
        return 4;
    }
    public int getLevelForaging() {
        if (this.foragingAbility < 50)
            return 0;
        if (this.foragingAbility < 100)
            return 1;
        if (this.foragingAbility < 250)
            return 2;
        if (this.foragingAbility < 350)
            return 3;
        return 4;
    }
    public int getLevelMining() {
        if (this.miningAbility < 50)
            return 0;
        if (this.miningAbility < 100)
            return 1;
        if (this.miningAbility < 250)
            return 2;
        if (this.miningAbility < 350)
            return 3;
        return 4;
    }


    public void increaseFarmingAbility(int amount) {
        this.farmingAbility += amount;
    }
    public void increaseFishingAbility(int amount) {
        this.fishingAbility += amount;
    }
    public void increaseForagingAbility(int amount) {
        this.foragingAbility += amount;
    }
    public void increaseMiningAbility(int amount) {
        this.miningAbility += amount;
    }
}
