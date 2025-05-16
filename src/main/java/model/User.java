package model;

import Controller.GameController;
import model.Animall.BarnOrCage;
import model.Enum.NPC;
import model.Enum.SecurityQuestions;
import model.MapThings.Tile;
import model.Places.Farm;
import model.ToolsPackage.BackPack;
import model.ToolsPackage.Tools;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static model.App.currentGame;


public class User {

    private String username;
    private String password;
    private String hashPass;
    private String nickname;
    private String email;
    private final String gender;
    private SecurityQuestions MySecurityQuestion;
    private String MySecurityAnswer;
    private int daysDepressedLeft = 0;
    private List<Recipe> recipes = Recipe.createAllRecipes();

    // buffs
    public int Buff_maxEnergy_100_hoursLeft = 0;
    public void setBuff_maxEnergy_100_hoursLeft (int x) {
        Buff_maxEnergy_100_hoursLeft = x;
        Buff_maxEnergy_50_hoursLeft = 0;
        Buff_foraging_hoursLeft = 0;
        Buff_farming_hoursLeft = 0;
        Buff_fishing_hoursLeft = 0;
        Buff_mining_hoursLeft = 0;
        // Buff implementation
        if (currentGame.currentPlayer.Buff_maxEnergy_100_hoursLeft > 0) {
            currentGame.currentPlayer.setMAX_HEALTH(currentGame.currentPlayer.getMAX_HEALTH() + 100);
            currentGame.currentPlayer.setHealth(currentGame.currentPlayer.getHealth() + 100);
        }
    }
    public int Buff_maxEnergy_50_hoursLeft = 0;
    public void setBuff_maxEnergy_50_hoursLeft (int x) {
        Buff_maxEnergy_50_hoursLeft = x;
        Buff_maxEnergy_100_hoursLeft = 0;
        Buff_foraging_hoursLeft = 0;
        Buff_farming_hoursLeft = 0;
        Buff_fishing_hoursLeft = 0;
        Buff_mining_hoursLeft = 0;
        // Buff implementation
        if (currentGame.currentPlayer.Buff_maxEnergy_50_hoursLeft > 0) {
            currentGame.currentPlayer.setMAX_HEALTH(currentGame.currentPlayer.getMAX_HEALTH() + 50);
            currentGame.currentPlayer.setHealth(currentGame.currentPlayer.getHealth() + 50);
        }
    }
    public int Buff_foraging_hoursLeft = 0;
    public void setBuff_foraging_hoursLeft (int x) {
        Buff_foraging_hoursLeft = x;
        Buff_maxEnergy_50_hoursLeft = 0;
        Buff_maxEnergy_100_hoursLeft = 0;
        Buff_farming_hoursLeft = 0;
        Buff_fishing_hoursLeft = 0;
        Buff_mining_hoursLeft = 0;
    }
    public int Buff_farming_hoursLeft = 0;
    public void setBuff_farming_hoursLeft (int x) {
        Buff_farming_hoursLeft = x;
        Buff_maxEnergy_50_hoursLeft = 0;
        Buff_foraging_hoursLeft = 0;
        Buff_maxEnergy_100_hoursLeft = 0;
        Buff_fishing_hoursLeft = 0;
        Buff_mining_hoursLeft = 0;
        GameController controller = new GameController();
        controller.passedOfTime(0, 0);
    }
    public int Buff_fishing_hoursLeft = 0;
    public void setBuff_fishing_hoursLeft (int x) {
        Buff_fishing_hoursLeft = x;
        Buff_maxEnergy_50_hoursLeft = 0;
        Buff_foraging_hoursLeft = 0;
        Buff_farming_hoursLeft = 0;
        Buff_maxEnergy_100_hoursLeft = 0;
        Buff_mining_hoursLeft = 0;
        GameController controller = new GameController();
        controller.passedOfTime(0, 0);
    }
    public int Buff_mining_hoursLeft = 0;
    public void setBuff_mining_hoursLeft (int x) {
        Buff_mining_hoursLeft = x;
        Buff_maxEnergy_50_hoursLeft = 0;
        Buff_foraging_hoursLeft = 0;
        Buff_farming_hoursLeft = 0;
        Buff_fishing_hoursLeft = 0;
        Buff_maxEnergy_100_hoursLeft = 0;
        GameController controller = new GameController();
        controller.passedOfTime(0, 0);
    }

    // TODO وقتی بازی تموم میشه این سه تارو ست کنیم
    private int max_point = 0;
    private int games_played = 0;
    private boolean currently_in_game = false;


    private int point;
    private int health;
    private int MAX_HEALTH;
    private Tile SleepTile;
    private User Spouse;  // شخصی که باهاش ازدواج کرده
    private boolean healthUnlimited;
    private String icon;


    private HashMap<NPC, Integer>    friendshipPoint = new HashMap<>();
    private final HashMap<NPC, DateHour>  level3Date = new HashMap<>();
    private final HashMap<NPC, Boolean> todayTalking = new HashMap<>();
    private final HashMap<NPC, Boolean> todayGifting = new HashMap<>();

    public HashMap<Items , DateHour> buffer = new HashMap<>();//برای برداشت محصولات فرآوری شده استفاده میشود

    private final BackPack backPack = new BackPack();
    public Tools currentTool;
    private int farmingAbility  = 0;
    private int miningAbility   = 0;
    private int foragingAbility = 0;
    private int fishingAbility  = 0;
    private int money;



    private final Farm farm = new Farm();
    public int topLeftX;
    public int topLeftY; // اینا باید برن تو فارم
    private int positionX;
    private int positionY;

    public ArrayList<BarnOrCage> BarnOrCages = new ArrayList<>();


    public User(String username, String nickname, String email, String gender,  int point, int health, String hashPass) {

        this.username = username;
        this.hashPass = hashPass;
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
    public void setSleepTile(Tile sleepTile) {

        SleepTile = sleepTile;
    }
    public Tile getSleepTile() {

        return SleepTile;
    }
    public String getIcon() {

        return icon;
    }
    public void setIcon(String icon) {

        this.icon = icon;
    }

    public User getSpouse() {
        return Spouse;
    }
    public void    setHealthUnlimited () {this.healthUnlimited = true;}
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
    public void setMoney (int amount) {
        this.money += amount;
    }
    public void increaseMoney (int amount) {
        if (this.getSpouse() == null) this.money += amount;
        else {
            this.money += (amount / 2);
            this.getSpouse().setMoney(this.getSpouse().getMoney() + (amount / 2));
        }
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
    public void increaseFarmingAbility (int amount) {
        this.farmingAbility += amount;
    }
    public void increaseFishingAbility (int amount) {
        this.fishingAbility += amount;
    }
    public void increaseForagingAbility(int amount) {
        this.foragingAbility += amount;
    }
    public void increaseMiningAbility  (int amount) {
        this.miningAbility += amount;
    }

    public int getFarmingAbility() {
        return farmingAbility;
    }
    public int getMiningAbility() {
        return miningAbility;
    } // TODO اینا باید پاک شن و برای دیباگن
    public int getForagingAbility() {
        return foragingAbility;
    }
    public int getFishingAbility() {
        return fishingAbility;
    }


    public void setFriendshipPoint(HashMap<NPC, Integer> friendshipPoint) {

        this.friendshipPoint = friendshipPoint;
    }
    public void increaseFriendshipPoint(NPC npc, int point) {

        this.friendshipPoint.put(npc, friendshipPoint.get(npc) + point);
    }
    public int put(NPC npc) {

        return Math.min(friendshipPoint.get(npc)%200, 3);
    }
    public int getFriendshipLevel(NPC npc) {

        return Math.min((friendshipPoint.get(npc)/200), 3);
    }
    public int getFriendshipPoint(NPC npc) {

        return Math.min(friendshipPoint.get(npc), 600);
    }
    public boolean getTodayGifting(NPC npc) {

        return todayGifting.get(npc);
    }
    public boolean getTodayTalking(NPC npc) {

        return todayTalking.get(npc);
    }
    public DateHour getLevel3Date(NPC npc) {

        return level3Date.get(npc);
    }
    public void setLevel3Date(NPC npc, DateHour dateHour) {

        level3Date.put(npc, dateHour);
    }
    public void setTodayTalking(NPC npc, boolean isTrue) {

        this.todayTalking.put(npc, isTrue);
    }
    public void setTodayGifting(NPC npc, boolean isTrue) {

        this.todayGifting.put(npc, isTrue);
    }


    public void setHashPass(String hashPass) {
        this.hashPass = hashPass;
    }
    public String getHashPass() {
        return hashPass;
    }

    public boolean isCurrently_in_game() {
        return currently_in_game;
    }

    public void setCurrently_in_game(boolean currently_in_game) {
        this.currently_in_game = currently_in_game;
    }


    public String getGender() {return gender;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return username.equals(user.username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    public int getDaysDepressedLeft() {
        return daysDepressedLeft;
    }

    public void setDaysDepressedLeft(int daysDepressedLeft) {
        this.daysDepressedLeft = daysDepressedLeft;
    }

    public void setSpouse(User spouse) {
        Spouse = spouse;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}