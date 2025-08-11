package com.Graphic.model;

import com.Graphic.model.Animall.BarnOrCage;
import com.Graphic.model.Enum.Direction;
import com.Graphic.model.Enum.ItemType.MarketType;
import com.Graphic.model.Enum.NPC.NPC;
import com.Graphic.model.Enum.SecurityQuestions;
import com.Graphic.model.MapThings.Tile;
import com.Graphic.model.Places.Farm;
import com.Graphic.model.ToolsPackage.BackPack;
import com.Graphic.model.ToolsPackage.CraftingItem;
import com.Graphic.model.ToolsPackage.Tools;
import com.Graphic.model.Weather.DateHour;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.*;

import static com.Graphic.Controller.MainGame.GameControllerLogic.passedOfTime;
import static com.Graphic.model.App.currentGame;


public class User {

    private String username;
    private String password;
    private String hashPass;
    private String nickname;
    private String email;
    private String gender;
    private SecurityQuestions MySecurityQuestion;
    private String MySecurityAnswer;
    private int daysDepressedLeft = 0;
    private List<Recipe> recipes;

    private ArrayList<String> Right;
    private ArrayList<String> Left;
    private ArrayList<String> Up;
    private ArrayList<String> Down;
    private Direction direction;
    private HashMap<Direction , String> DirectionInWalk;
    private boolean isMoving;
    private float Timer = 0.0f;
    private boolean first = true;
    private boolean isInMine = false;
    private boolean isPlaceArtisanOrShippingBin = false;
    private boolean isInBarnOrCage = false;
    private boolean isInHome = false;
    private boolean isInMarket = false;
    private boolean isInFarmExterior=false;
    private boolean isWaiting = false;
    private String withMouse;
    private Items droppedItem;
    private String BarnOrCagePath;
    private BarnOrCage currentBarnOrCage = new BarnOrCage();
    private MarketType currentMarket;
    private Queue<User> joinMarket = new LinkedList<>();
    private Queue<User> exitMarket = new LinkedList<>();

    public boolean isFishing = false;
    public boolean doingMinigame = false;

    private User FriendCloseToMe = null;
    private boolean showUnseenChats = false;
    private boolean showHugged =  false;
    private boolean showFlowered = false;

    // buffs
    public int Buff_maxEnergy_100_hoursLeft = 0;

    public int Buff_maxEnergy_50_hoursLeft = 0;
    public int Buff_foraging_hoursLeft = 0;
    public int Buff_farming_hoursLeft = 0;
    public int Buff_fishing_hoursLeft = 0;
    public int Buff_mining_hoursLeft = 0;

    // TODO وقتی بازی تموم میشه این سه تارو ست کنیم
    private int max_point = 0;
    private int games_played = 0;
    private boolean currently_in_game = false;


    private int point;
    private int health;
    private int MAX_HEALTH;
    private Tile SleepTile;
    private User Spouse;  // شخصی که باهاش ازدواج کرده
    private String icon;
    private boolean healthUnlimited;


    private HashMap<NPC, Integer>    friendshipPoint = new HashMap<>();
    private final HashMap<NPC, DateHour>  level3Date = new HashMap<>();
    private final HashMap<NPC, Boolean> todayTalking = new HashMap<>();
    private final HashMap<NPC, Boolean> todayGifting = new HashMap<>();

    public HashMap<Items , DateHour> buffer = new HashMap<>();   //برای برداشت محصولات فرآوری شده استفاده میشود

    private BackPack backPack;

    public Items currentItem;
    public Tools currentTool;
    private int farmingAbility  = 0;
    private int miningAbility   = 0;
    private int foragingAbility = 0;
    private int fishingAbility  = 0;
    private int money;

    private  Farm farm;
    public int topLeftX;
    public int topLeftY; // اینا باید برن تو فارم
    private float positionX;
    private float positionY;

    public ArrayList<BarnOrCage> BarnOrCages = new ArrayList<>();

    public User() {
        // اگر چیزی بعد لود دوباره بارگذاری بشه
    }


    public User(String username, String nickname, String email, String gender,
                int point, int health, String hashPass, SecurityQuestions MySecQ, String MySecA) {

        this.username = username;
        this.hashPass = hashPass;
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
        this.point = point;
        this.MAX_HEALTH = 200;
        this.healthUnlimited = false;
        this.MySecurityQuestion = MySecQ;
        this.MySecurityAnswer = MySecA;

        backPack = new BackPack();


        isMoving = false;
    }



    public void setBuff_maxEnergy_100_hoursLeft (int x) {
        Buff_maxEnergy_100_hoursLeft = x;
        Buff_maxEnergy_50_hoursLeft = 0;
        Buff_foraging_hoursLeft = 0;
        Buff_farming_hoursLeft = 0;
        Buff_fishing_hoursLeft = 0;
        Buff_mining_hoursLeft = 0;
        // Buff implementation
//        if (currentGame.currentPlayer.Buff_maxEnergy_100_hoursLeft > 0) {
//            currentGame.currentPlayer.setMAX_HEALTH(currentGame.currentPlayer.getMAX_HEALTH() + 100);
//            currentGame.currentPlayer.setHealth(currentGame.currentPlayer.getHealth() + 100);
//        }
    }
    public void setBuff_maxEnergy_50_hoursLeft (int x) {
        Buff_maxEnergy_50_hoursLeft = x;
        Buff_maxEnergy_100_hoursLeft = 0;
        Buff_foraging_hoursLeft = 0;
        Buff_farming_hoursLeft = 0;
        Buff_fishing_hoursLeft = 0;
        Buff_mining_hoursLeft = 0;
        // Buff implementation
//        if (currentGame.currentPlayer.Buff_maxEnergy_50_hoursLeft > 0) {
//            currentGame.currentPlayer.setMAX_HEALTH(currentGame.currentPlayer.getMAX_HEALTH() + 50);
//            currentGame.currentPlayer.setHealth(currentGame.currentPlayer.getHealth() + 50);
//        }
    }
    public void setBuff_foraging_hoursLeft (int x) {
        Buff_foraging_hoursLeft = x;
        Buff_maxEnergy_50_hoursLeft = 0;
        Buff_maxEnergy_100_hoursLeft = 0;
        Buff_farming_hoursLeft = 0;
        Buff_fishing_hoursLeft = 0;
        Buff_mining_hoursLeft = 0;
    }
    public void setBuff_farming_hoursLeft (int x) {
        Buff_farming_hoursLeft = x;
        Buff_maxEnergy_50_hoursLeft = 0;
        Buff_foraging_hoursLeft = 0;
        Buff_maxEnergy_100_hoursLeft = 0;
        Buff_fishing_hoursLeft = 0;
        Buff_mining_hoursLeft = 0;
        passedOfTime(0, 0);
    }
    public void setBuff_fishing_hoursLeft (int x) {
        Buff_fishing_hoursLeft = x;
        Buff_maxEnergy_50_hoursLeft = 0;
        Buff_foraging_hoursLeft = 0;
        Buff_farming_hoursLeft = 0;
        Buff_maxEnergy_100_hoursLeft = 0;
        Buff_mining_hoursLeft = 0;
        passedOfTime(0, 0);
    }
    public void setBuff_mining_hoursLeft (int x) {
        Buff_mining_hoursLeft = x;
        Buff_maxEnergy_50_hoursLeft = 0;
        Buff_foraging_hoursLeft = 0;
        Buff_farming_hoursLeft = 0;
        Buff_fishing_hoursLeft = 0;
        Buff_maxEnergy_100_hoursLeft = 0;
        passedOfTime(0, 0);
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
    public float getPositionX() {
        return positionX;
    }
    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }
    public float getPositionY() {
        return positionY;
    }
    public void setPositionY(float positionY) {
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
            this.getSpouse().setMoney(amount / 2);
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

    public void initAnimations () {
        this.Right = new ArrayList<>();
        this.Left = new ArrayList<>();
        this.Up = new ArrayList<>();
        this.Down = new ArrayList<>();
        this.Right.add("Mohamadreza/PlayerSpriteIdle/Right" + farm.getIndex() + "," + gender + ".png");
        this.Right.add("Mohamadreza/PlayerSpriteMove/Right" + farm.getIndex() + "," + gender + ".png");
        this.Left.add("Mohamadreza/PlayerSpriteIdle/Left" + farm.getIndex() + "," + gender + ".png");
        this.Left.add("Mohamadreza/PlayerSpriteMove/Left" + farm.getIndex() + "," + gender + ".png");
        this.Up.add("Mohamadreza/PlayerSpriteIdle/Up" + farm.getIndex() + "," + gender + ".png");
        this.Up.add("Mohamadreza/PlayerSpriteMove/Up" + farm.getIndex() + "," + gender + ".png");
        this.Down.add("Mohamadreza/PlayerSpriteIdle/Down" + farm.getIndex() + "," + gender + ".png");
        this.Down.add("Mohamadreza/PlayerSpriteMove/Down" + farm.getIndex() + "," + gender + ".png");



//        this.Right = new Animation<>(0.1f,
//            TextureManager.get("Mohamadreza/PlayerSpriteIdle/Right" + farm.getIndex() + "," + gender + ".png"),
//            TextureManager.get("Mohamadreza/PlayerSpriteMove/Right" + farm.getIndex() + "," + gender + ".png"));
//
//        this.Left = new Animation<>(0.1f,
//            TextureManager.get("Mohamadreza/PlayerSpriteIdle/Left" + farm.getIndex() + "," + gender + ".png"),
//            TextureManager.get("Mohamadreza/PlayerSpriteMove/Left" + farm.getIndex() + "," + gender + ".png"));
//
//        this.Up = new Animation<>(0.1f,
//            TextureManager.get("Mohamadreza/PlayerSpriteIdle/Up" + farm.getIndex() + "," + gender + ".png"),
//            TextureManager.get("Mohamadreza/PlayerSpriteMove/Up" + farm.getIndex() + "," + gender + ".png"));
//
//        this.Down = new Animation<>(0.1f,
//            TextureManager.get("Mohamadreza/PlayerSpriteIdle/Down" + farm.getIndex() + "," + gender + ".png"),
//            TextureManager.get("Mohamadreza/PlayerSpriteMove/Down" + farm.getIndex() + "," + gender + ".png"));

        direction = Direction.Down;
        DirectionInWalk = new HashMap<>();
        DirectionInWalk.put(Direction.Up , Direction.Up.getPath() + farm.getIndex() + "," + gender + ".png");
        DirectionInWalk.put(Direction.Down , Direction.Down.getPath() + farm.getIndex() + "," + gender + ".png");
        DirectionInWalk.put(Direction.Left , Direction.Left.getPath() + farm.getIndex() + "," + gender + ".png");
        DirectionInWalk.put(Direction.Right, Direction.Right.getPath() + farm.getIndex() + "," + gender + ".png");
//        DirectionInWalk.put(Direction.Up , new Texture(Gdx.files.internal(Direction.Up.getPath() + farm.getIndex() + "," + gender + ".png")));
//        DirectionInWalk.put(Direction.Down , new Texture(Gdx.files.internal(Direction.Down.getPath() + farm.getIndex() + "," + gender + ".png")));
//        DirectionInWalk.put(Direction.Right , new Texture(Gdx.files.internal(Direction.Right.getPath() + farm.getIndex() + "," + gender + ".png")));
//        DirectionInWalk.put(Direction.Left , new Texture(Gdx.files.internal(Direction.Left.getPath() + farm.getIndex() + "," + gender + ".png")));


        //sprite = new Sprite(DirectionInWalk.get(direction));
        //sprite.setPosition(TEXTURE_SIZE * (positionX), TEXTURE_SIZE * (90 - positionY));
    }

    //    public Sprite getSprite() {
//        sprite.setPosition(TEXTURE_SIZE * (positionX), TEXTURE_SIZE * (90 - positionY));
//        sprite.setSize(TEXTURE_SIZE, TEXTURE_SIZE);
//
//        return sprite;
//    }
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    public Direction getDirection() {
        if (direction == null) {
            direction = Direction.Down;
        }
        return direction;
    }

    public boolean isMoving() {
        return isMoving;
    }
    public void setMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }
    public ArrayList<String> getAnimation() {
        switch (direction) {
            case Up -> {
                return Up;
            }
            case Down -> {
                return Down;
            }
            case Left -> {
                return Left;
            }
            case Right -> {
                return Right;
            }
            default -> {
                return null;
            }
        }
    }

    public float getTimer() {
        return Timer;
    }
    public void setTimer(float timer) {
        Timer = timer;
    }

    public BarnOrCage getCurrentBarnOrCage() {
        return currentBarnOrCage;
    }

    public String getBarnOrCagePath() {
        return BarnOrCagePath;
    }

    public boolean isInMine() {
        return isInMine;
    }

    public Items getDroppedItem() {
        return droppedItem;
    }

    public boolean isPlaceArtisanOrShippingBin() {
        return isPlaceArtisanOrShippingBin;
    }

    public String getWithMouse() {
        return withMouse;
    }



    public boolean isInBarnOrCage() {
        return isInBarnOrCage;
    }

    public boolean isInHome() {
        return isInHome;
    }

    public boolean isInMarket() {
        return isInMarket;
    }

    public void setCurrentBarnOrCage(BarnOrCage currentBarnOrCage) {
        this.currentBarnOrCage = currentBarnOrCage;
    }

    public void setBarnOrCagePath(String barnOrCagePath) {
        BarnOrCagePath = barnOrCagePath;
    }

    public void setInMine(boolean inMine) {
        isInMine = inMine;
    }

    public void setDroppedItem(Items droppedItem) {
        this.droppedItem = droppedItem;
    }

    public void setIsPlaceArtisanOrShippingBin(boolean placeArtisanOrShippingBin) {
        isPlaceArtisanOrShippingBin = placeArtisanOrShippingBin;
    }

    public void setIsInBarnOrCage(boolean inBarnOrCage) {
        isInBarnOrCage = inBarnOrCage;
    }

    public void setIsInHome(boolean inHome) {
        isInHome = inHome;
    }

    public void setIsInMarket(boolean inMarket) {
        isInMarket = inMarket;
    }

    public boolean isInFarmExterior() {
        return isInFarmExterior;
    }

    public void setInFarmExterior(boolean inFarmExterior) {
        this.isInFarmExterior = inFarmExterior;
    }

    public void setWithMouse(String withMouse) {
        this.withMouse = withMouse;
    }

    public void setWaiting(boolean waiting) {
        isWaiting = waiting;
    }

    public boolean isWaiting() {
        return isWaiting;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }
    public ArrayList<String> getUp() {
        if (Up == null) {
            initAnimations();
        }
        return Up;
    }
    public ArrayList<String> getDown() {
        return Down;
    }
    public ArrayList<String> getLeft() {
        return Left;
    }
    public ArrayList<String> getRight() {
        return Right;
    }
    public MarketType getMarketType() {
        return currentMarket;
    }
    public void setCurrentMarket(MarketType currentMarket) {
        this.currentMarket = currentMarket;
    }
    public Queue<User> getJoinMarket() {
        return joinMarket;
    }
    public Queue<User> getExitMarket() {
        return exitMarket;
    }
    public void setFriendCloseToMe(User FriendCloseToMe) {
        this.FriendCloseToMe = FriendCloseToMe;
    }
    public User getFriendCloseToMe() {
        return FriendCloseToMe;
    }
    public boolean isShowUnseenChats() {
        return showUnseenChats;
    }
    public void setShowUnseenChats(boolean showUnseenChats) {
        this.showUnseenChats = showUnseenChats;
    }

    public boolean isShowHugged() {
        return showHugged;
    }
    public void setShowHugged(boolean showHugged) {
        this.showHugged = showHugged;
    }

    public boolean isShowFlowered() {
        return showFlowered;
    }
    public void setShowFlowered(boolean showFlowered) {
        this.showFlowered = showFlowered;
    }
}

