package Controller;

import model.*;
import model.Color_Eraser;
import model.Enum.Door;
import model.Enum.WeatherTime.Season;
import model.Enum.ItemType.WallType;
import model.Enum.WeatherTime.Weather;
import model.Places.Farm;
import model.Places.GreenHouse;
import model.Places.Home;
import model.Places.Lake;
import model.Plants.Tree;
import model.App;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import static model.App.*;
import static model.App.tomorrowWeather;
import static model.Color_Eraser.*;

public class GameController {

    public Tile getTileByCoordinates(int x, int y) {
        for (Tile tile:bigMap){
            if (tile.getX() == x && tile.getY() == y){
                return tile;
            }
        }
        return null;
    }

    public void creatInitialMine(int id , int x , int y){
        Farm farm= currentPlayer.getFarm();
        if (id==1){
            Mine mine = new Mine();
            mine.setCharactor('M');
            for (int i=23;i<29;i++){
                for (int j=2;j<8;j++) {
                    Tile tile = new Tile(i + 60*x, j + 60*y, mine);
                    farm.Farm.add(tile);
                    bigMap.add(tile);
                }
            }
        }
        else if (id==2){

        }
    }

    public void creatInitialLake(int id , int x , int y){
        Farm farm= currentPlayer.getFarm();
        if (id==1){
            Lake lake = new Lake();
            lake.setCharactor('L');
            for (int i=2;i<7;i++){
                for (int j=23;j<29;j++) {
                    Tile tile = new Tile(i +60*x, j+60*y, lake);
                    farm.Farm.add(tile);
                    bigMap.add(tile);
                }
            }
        }
        else if (id==2){

        }
    }

    public void creatInitialHouse(int id , int x , int y){
        Farm farm= currentPlayer.getFarm();
        Wall wall = new Wall();
        wall.setWallType(WallType.House);
        wall.setCharactor('#');
        door houseDoor=new door();
        houseDoor.setDoor(Door.House);
        houseDoor.setCharactor('D');

        for (int i=12;i<19;i++){
            for (int j=0;j<7;j++) {
                if (i==12 || i==18){
                    Tile tile = new Tile(i + 60*x, j + 60*y, wall);
                    farm.Farm.add(tile);
                    bigMap.add(tile);
                }
                else if (i==15 && j==6){
                    Tile tile = new Tile(i + 60*x, j + 60*y, houseDoor);
                    farm.Farm.add(tile);
                    bigMap.add(tile);
                }
                else {
                    if (j==0 || j==6){
                        Tile tile = new Tile(i + 60*x, j + 60*y, wall);
                        farm.Farm.add(tile);
                        bigMap.add(tile);
                    }
                }
            }
        }


        Fridge fridge=new Fridge(18 + 60*x,6 + 60*y);
        fridge.setCharactor('F');
        Home home=new Home(13 +60*x,1 + 60*y,fridge);
        home.setCharactor('H');
        home.houseDoor=houseDoor;

        for (int i=13 ; i<18 ; i++){
            for (int j=1 ; j<6 ; j++) {
                if (i==18 && j==5){
                    Tile tile = new Tile(i + 60*x, j +60*y, fridge);
                    farm.Farm.add(tile);
                    bigMap.add(tile);
                }
                else {
                    Tile tile = new Tile(i + 60*x, j + 60*y, home);
                    farm.Farm.add(tile);
                    bigMap.add(tile);
                }
            }
        }
    }

    public void creatInitialGreenHouse(int id , int x , int y){
        Farm farm= currentPlayer.getFarm();
        Wall GreenWall = new Wall();
        GreenWall.setWallType(WallType.GreenHouse);
        GreenWall.setCharactor('#');
        door greenHouseDoor=new door();
        greenHouseDoor.setDoor(Door.GreenHouse);
        greenHouseDoor.setCharactor('D');

        for (int i=0;i<8;i++){
            for (int j=0;j<9;j++) {
                if (i==0 || i==7){
                    if (j==4 && i==7){
                        Tile tile = new Tile(i +60*x, j +60*y, greenHouseDoor);
                        farm.Farm.add(tile);
                        bigMap.add(tile);
                    }
                    else {
                        Tile tile = new Tile(i +60*x, j +60*y, GreenWall);
                        farm.Farm.add(tile);
                        bigMap.add(tile);
                    }
                }
                else {
                    if (j==0 || j==8){
                        Tile tile = new Tile(i +60*x, j+60*y, GreenWall);
                        farm.Farm.add(tile);
                        bigMap.add(tile);
                    }
                }
            }
        }

        GreenHouse greenHouse=new GreenHouse(1 + 60*x,1 + 60*y);
        greenHouse.setCharactor('G');
        WaterTank waterTank=new WaterTank(100);
        waterTank.setWaterTank('W');
        greenHouse.waterTank=waterTank;
        for (int i=1 ; i<7 ; i++){
            for (int j=1 ; j<8 ; j++) {
                if (i==1 && j==3){
                    Tile tile=new Tile(i + 60*x,j + 60*y,waterTank);
                    farm.Farm.add(tile);
                    bigMap.add(tile);
                }
                else {
                    Tile tile=new Tile(i + 60*x,j + 60*y,greenHouse);
                    farm.Farm.add(tile);
                    bigMap.add(tile);
                }
            }
        }
    }

    public Farm creatInitialFarm(int id){
        long seed=System.currentTimeMillis();
        Farm farm= currentPlayer.getFarm();

        for (int i=0 ; i<30 ;i++){
            for (int j=0 ; j<30 ; j++) {
                if (i>=23 && i<29 && j>=2 && j<8){
                    creatInitialMine(id,currentPlayer.topLeftX,currentPlayer.topLeftY);
                }
                else if(1<i && i<7 && j>22 && j<29){
                    creatInitialLake(id,currentPlayer.topLeftX,currentPlayer.topLeftY);
                }
                else if(i>=12 && i<19 && j<7){
                    creatInitialHouse(id,currentPlayer.topLeftX,currentPlayer.topLeftY);
                }
                else if (i<8 && j<9){
                    creatInitialGreenHouse(id,currentPlayer.topLeftX,currentPlayer.topLeftY);
                }
                else {
                    MapGenerator(i,j,seed);
                }
            }
        }
        farms.add(farm);

        return farm;

    }

    public void MapGenerator(int i,int j,long seed){
        if (i==0 || i==29 || j==0 || j==29){
            if (i==15 && j==29){
                door FarmDoor=new door();
                FarmDoor.setDoor(Door.Farm);
                FarmDoor.setCharactor('D');
                Tile tile=new Tile(i + 60*currentPlayer.topLeftX,j + 30*currentPlayer.topLeftY,FarmDoor);
                currentPlayer.getFarm().Farm.add(tile);
                bigMap.add(tile);
            }
            else if (i==29 && j==15){
                door FarmDoor=new door();
                FarmDoor.setDoor(Door.Farm);
                FarmDoor.setCharactor('D');
                Tile tile=new Tile(i + 30*currentPlayer.topLeftX,j + 60*currentPlayer.topLeftY,FarmDoor);
                currentPlayer.getFarm().Farm.add(tile);
                bigMap.add(tile);
            }
            else {
                Walkable walkable = new Walkable();
                walkable.setCharactor('.');
                Tile tile = new Tile(i + 60 * currentPlayer.topLeftX, j + 60 * currentPlayer.topLeftY, walkable);
                currentPlayer.getFarm().Farm.add(tile);
                bigMap.add(tile);
            }

        }
        else {

            PerlinNoise perlinNoise = new PerlinNoise(seed);

            double noise = perlinNoise.noise(i * 0.1, j * 0.1);
            if (-1.2 < noise && noise < -0.2) {
                Tree tree = new Tree();
                tree.setCharactor('T');
                Tile tile = new Tile(i + 60 * currentPlayer.topLeftX, j + 60 * currentPlayer.topLeftY, tree);
                currentPlayer.getFarm().Farm.add(tile);
                bigMap.add(tile);
            } else if (-0.1 < noise && noise < 0.0) {
                BasicRock basicRock = new BasicRock();
                basicRock.setCharactor('S');
                Tile tile = new Tile(i + 60 * currentPlayer.topLeftX, j + 60 * currentPlayer.topLeftY, basicRock);
                currentPlayer.getFarm().Farm.add(tile);
                bigMap.add(tile);
            } else {
                Walkable walkable = new Walkable();
                walkable.setCharactor('.');
                Tile tile = new Tile(i + 60 * currentPlayer.topLeftX, j + 60 * currentPlayer.topLeftY, walkable);
                currentPlayer.getFarm().Farm.add(tile);
                bigMap.add(tile);
            }

        }

    }

    public void print(Farm farm){
        int x=currentPlayer.topLeftX;
        int y=currentPlayer.topLeftY;

        for (int i=60 * x ; i<60 * x +30 ; i++) {
            for (int j = 60 * y; j < 60 * y + 30; j++) {

                Tile tile = getTileByCoordinates(j, i);

                if (tile.getGameObject() instanceof Walkable) {
                    System.out.print(WHITE + tile.getGameObject().getCharactor() + RESET+" ");
                } else if (tile.getGameObject() instanceof BasicRock) {
                    System.out.print(GRAY + tile.getGameObject().getCharactor() + RESET+" ");
                } else if (tile.getGameObject() instanceof Tree) {
                    System.out.print(GREEN + tile.getGameObject().getCharactor() + RESET+" ");
                } else if (tile.getGameObject() instanceof Lake) {
                    System.out.print(BLUE + tile.getGameObject().getCharactor() + RESET+" ");
                } else if (tile.getGameObject() instanceof Mine) {
                    System.out.print(RED + tile.getGameObject().getCharactor() + RESET+" ");
                } else if (tile.getGameObject() instanceof Wall) {
                    System.out.print(WHITE + tile.getGameObject().getCharactor() + RESET+" ");
                } else if (tile.getGameObject() instanceof Home) {
                    System.out.print(YELLOW + tile.getGameObject().getCharactor() + RESET+" ");
                } else if (tile.getGameObject() instanceof door) {
                    System.out.print(Brown + tile.getGameObject().getCharactor() + RESET+" ");
                } else if (tile.getGameObject() instanceof GreenHouse) {
                    System.out.print(GREEN + tile.getGameObject().getCharactor() + RESET+" ");
                } else if (tile.getGameObject() instanceof Fridge) {
                    System.out.print(WHITE + tile.getGameObject().getCharactor() + RESET+" ");
                } else if (tile.getGameObject() instanceof WaterTank) {
                    System.out.print(BLUE + tile.getGameObject().getCharactor() + RESET+" ");
                }
            }
            System.out.println();

        }

    }

    public Result walk (int goalX, int goalY){
         int startX=currentPlayer.getPositionX();
         int startY=currentPlayer.getPositionY();
         Tile endTile=getTileByCoordinates(goalX, goalY);

         if (!checkConditionsForWalk(goalX, goalY)) {
             return new Result(false,"you can't go to this coordinate");
         }

         int [] dirx={0,0,0,1,1,1,-1,-1,-1};
         int [] diry={0,1,-1,0,1,-1,0,1,-1};
         HashMap<Tile,Integer> costEnergy=new HashMap<>();
         Queue<int []> queue=new LinkedList<>();

         for (int i=0 ; i<8 ; i++) {
             queue.add(new int[]{startX,startY,i,0,0});
         }

         return null;

    }

    private boolean checkTile(Tile tile){
        if (tile.getGameObject() instanceof Home || tile.getGameObject() instanceof door
                || tile.getGameObject() instanceof Walkable || tile.getGameObject() instanceof GreenHouse) {

            return true;
        }
        return false;
    }

    public boolean checkConditionsForWalk(int goalX, int goalY){
        Tile tile = getTileByCoordinates(goalX, goalY);
        Farm farm = null;
        for (Farm farms : farms) {
            if (farms.Farm.contains(tile)) {
                farm = farms;
                break;
            }
        }
        for (User user : players) {
            if (user.getFarm().equals(farm)){
                if (!user.getMarried().equals(currentPlayer) && !user.equals(currentPlayer)){
                    return false;
                }
            }
        }

        if (tile.getGameObject() instanceof GreenHouse) {
            if (!((GreenHouse) tile.getGameObject()).isCreated){
                return false;
            }
        }

        for (User user : users) {
            if (user.getPositionX()==goalX && user.getPositionY()==goalY){
                return false;
            }
        }
        if (!checkTile(tile)){
            return false;
        }
        //TODO اگر NPC در اون مختصات باشه نمیتونیم اونجا بریم
        //TODO جاهایی که دونه کاشتیم
        return true;

    }



    private void setEnergyInMorning () {
        for (User user : players) {

            if (user.getHealth() > 0)
                user.setHealth(user.getMAX_HEALTH());
            else
                user.setHealth((user.getMAX_HEALTH()*3)/4);
        }
    }
    private void setTime (boolean gameIsNew) {

        if (gameIsNew)
            currentDate = new DateHour(Season.Spring, 1, 9, 1980);
        else {
            // TODO
        }
    }
    private void setWeather (boolean gameIsNew) {

        if (gameIsNew)
            currentWeather = Weather.Sunny;

        else {
            // TODO
        }
    }
    private void doSeasonAutomaticTask () {

        currentWeather = tomorrowWeather;
        tomorrowWeather = currentDate.getSeason().getWeather();
    }
    private void doWeatherTask () {

    }
    private boolean checkForDeath () {

        return currentPlayer.getHealth() <= 0;
    }


    public void startNewGame () {

        setTime(true);
        setWeather(true);
    }
    public void loadGame () {
        setTime(false);
        setWeather(false);
    }



    public void passedOfTime (int day, int hour) {

        currentDate.increaseHour(hour);
        currentDate.increaseDay(day);
    } // TODO   باید کارایی که بعد افزایش زمان انجام میشن رو انجام بدی


    public void startDay () {

        doSeasonAutomaticTask();
        passedOfTime(0,(24 -currentDate.getHour()) + 9);
        setEnergyInMorning();

        // TODO بازیکنا برن خونشون , غش کردن
        // TODO محصول کاشته بشه و رشد محصولا یه روز بره بالاتر
        // TODO کانی تولید بشه شاپینگ بین خالی بشه و.  پول بیاد تو حساب فرد
    }
    public void AutomaticFunctionAfterOneTurn () {


        if (currentUser == currentPlayer)
            passedOfTime(0, 1);

        if (currentDate.getHour() > 22)
            startDay();
    }
    public void AutomaticFunctionAfterAnyAct () {

        for (User user : players)
            user.checkHealth();

    }


    public Result showTime () {
        return new Result(true, BLUE +"Time : "+RESET
                + currentDate.getHour()+ ":00");
    }
    public Result showDate () {
        return new Result(true, BLUE+"Date : "+RED+currentDate.getYear()+RESET+" "+currentDate.getNameSeason()+" "+currentDate.getDate());
    }
    public Result showSeason   () {

        return new Result(true, currentDate.getNameSeason());
    }
    public Result showWeather  (boolean isToday) {

        if (isToday)
            return new Result(true, currentWeather.getDisplayName());
        else
            return new Result(true, tomorrowWeather.getDisplayName());
    }
    public Result setWeather   (String type) {

        Weather weather;
        try {
            weather = Weather.valueOf(type);
        } catch (Exception e) {
            return new Result(false, RED+"Weather type is incorrect!"+RESET);
        }
        tomorrowWeather = weather;
        return new Result(true, BLUE+"Tomorrow weather change to "+RESET+currentWeather.getDisplayName());
    }
    public Result setEnergy    (String amount) {

        if (amount.charAt(0) == '-')
            return new Result(false, RED+"Energy must be a positive number!"+RESET);
        int amount2;
        try {
            amount2 = Integer.parseInt(amount);
        } catch (Exception e) {
            return new Result(false, RED+"Number is incorrect!"+RESET);
        }

        if (currentPlayer.getHealth() > amount2) {
            currentPlayer.setHealth(amount2);
            return new Result(true, BLUE+"Your Energy");

        }
        else if (currentPlayer.getHealth() < amount2) {

            return new Result(true, "");
        } else
            return new Result(false, "Your energy level at this moment is this amount.");

    }
    public Result showDateTime () {
        return new Result(true, BLUE+"Time : "+RED+ currentDate.getHour()+ ":00" +
                BLUE+"\nData : "+RED+currentDate.getYear()+RESET+" "+currentDate.getNameSeason()+" "+currentDate.getDate());
    }
    public Result showDayOfWeek () {
        return new Result(true, BLUE+"Day of Week : "+RESET
                + currentDate.getDayOfTheWeek());
    }
    public Result increaseHour (String hour) {

        if (hour.charAt(0) == '-')
            return new Result(false, RED+"The time must be a positive number!"+RESET);
        int amount;
        try {
            amount = Integer.parseInt(hour);
        } catch (Exception e) {
            return new Result(false, RED+"Time is incorrect!"+RESET);
        }
        passedOfTime(0, amount);
        return new Result(true, BLUE+"Time change to : "+GREEN+ currentDate.getHour()+":00"+RESET);
    }
    public Result increaseDate (String date) {

        if (date.charAt(0) == '-')
            return new Result(false, RED+"The time must be a positive number!"+RESET);
        int amount;
        try {
            amount = Integer.parseInt(date);
        } catch (Exception e) {
            return new Result(false, RED+"Time is incorrect!"+RESET);
        }
        passedOfTime(amount, 0);
        return new Result(true, BLUE+"Date change to : "+RED+currentDate.getYear()+RESET+" "+currentDate.getNameSeason()+" "+currentDate.getDate());
    }
}