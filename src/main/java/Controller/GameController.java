package Controller;

import model.*;
import model.Enum.Color_Eraser;
import model.Enum.Door;
import model.Enum.WeatherTime.Season;
import model.Enum.ItemType.WallType;
import model.Enum.WeatherTime.Weather;
import model.Places.Farm;
import model.Places.GreenHouse;
import model.Places.Home;
import model.Places.Lake;
import model.Plants.ForagingMinerals;
import model.Plants.Tree;

import static model.App.*;
import static model.Color_Eraser.*;

public class GameController {

    public Tile getTileByCoordinates(int x, int y,Farm farm) {
        for (Tile tile:farm.Farm){
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
            for (int i=24;i<30;i++){
                for (int j=24;j<30;j++) {
                    Tile tile = new Tile(i + 60*x, j + 60*y, mine);
                    farm.Farm.add(tile);
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
            for (int i=0;i<5;i++){
                for (int j=25;j<30;j++) {
                    Tile tile = new Tile(i +60*x, j+60*y, lake);
                    farm.Farm.add(tile);
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
                }
                else if (i==15 && j==6){
                    Tile tile = new Tile(i + 60*x, j + 60*y, houseDoor);
                    farm.Farm.add(tile);
                }
                else {
                    if (j==0 || j==6){
                        Tile tile = new Tile(i + 60*x, j + 60*y, wall);
                        farm.Farm.add(tile);
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
                }
                else {
                    Tile tile = new Tile(i + 60*x, j + 60*y, home);
                    farm.Farm.add(tile);
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
                    if (j==4){
                        Tile tile = new Tile(i +60*x, j +60*y, greenHouseDoor);
                        farm.Farm.add(tile);
                    }
                    else {
                        Tile tile = new Tile(i +60*x, j +60*y, GreenWall);
                        farm.Farm.add(tile);
                    }
                }
                else {
                    if (j==0 || j==8){
                        Tile tile = new Tile(i +60*x, j+60*y, GreenWall);
                        farm.Farm.add(tile);
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
                }
                else {
                    Tile tile=new Tile(i + 60*x,j + 60*y,greenHouse);
                    farm.Farm.add(tile);
                }
            }
        }
    }

    public Farm creatInitialFarm(int id){
        long seed=System.currentTimeMillis();
        Farm farm= currentPlayer.getFarm();

        for (int i=0 ; i<30 ;i++){
            for (int j=0 ; j<30 ; j++) {
                if (i>=24 && j>=24){
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

        return farm;

    }

    public void MapGenerator(int i,int j,long seed){
        if (i==0 || i==29 || j==0 || j==29){
            Walkable walkable=new Walkable();
            Tile tile=new Tile(i + 60* currentPlayer.topLeftX,j + 60*currentPlayer.topLeftY,walkable);
            currentPlayer.getFarm().Farm.add(tile);
        }
        else {

            PerlinNoise perlinNoise = new PerlinNoise(seed);

            double noise = perlinNoise.noise(i * 0.1, j * 0.1);
            if (-1.2 < noise && noise < -0.2) {
                Tree tree = new Tree();
                tree.setCharactor('T');
                Tile tile = new Tile(i + 60 * App.currentPlayer.topLeftX, j + 60 * App.currentPlayer.topLeftY, tree);
                App.currentPlayer.getFarm().Farm.add(tile);
            } else if (-0.1 < noise && noise < 0.0) {
                BasicRock basicRock = new BasicRock();
                basicRock.setCharactor('S');
                Tile tile = new Tile(i + 60 * App.currentPlayer.topLeftX, j + 60 * App.currentPlayer.topLeftY, basicRock);
                App.currentPlayer.getFarm().Farm.add(tile);
            } else {
                Walkable walkable = new Walkable();
                walkable.setCharactor('.');
                Tile tile = new Tile(i + 60 * currentPlayer.topLeftX, j + 60 * currentPlayer.topLeftY, walkable);
                App.currentPlayer.getFarm().Farm.add(tile);
            }

        }

    }

    public void print(Farm farm){
        int x=currentPlayer.topLeftX;
        int y=currentPlayer.topLeftY;

        for (int i=60 * x ; i<60 * x +30 ; i++){
            for (int j=60 * y ; j<60 * y +30 ; j++) {
                Tile tile=getTileByCoordinates(i,j,farm);

                if (tile.getGameObject() instanceof Walkable){
                    System.out.print(Color_Eraser.WHITE+tile.getGameObject().getCharactor()+Color_Eraser.RESET);
                }
                else if(tile.getGameObject() instanceof BasicRock){
                    System.out.print(Color_Eraser.GRAY+tile.getGameObject().getCharactor()+Color_Eraser.RESET);
                }
                else if (tile.getGameObject() instanceof Tree){
                    System.out.print(Color_Eraser.GREEN+tile.getGameObject().getCharactor()+Color_Eraser.RESET);
                }
                else if (tile.getGameObject() instanceof Lake){
                    System.out.print(Color_Eraser.BLUE+tile.getGameObject().getCharactor()+Color_Eraser.RESET);
                }
                else if (tile.getGameObject() instanceof Mine){
                    System.out.print(Color_Eraser.RED+tile.getGameObject().getCharactor()+Color_Eraser.RESET);
                }
                else if (tile.getGameObject() instanceof Wall){
                    System.out.print(Color_Eraser.WHITE+tile.getGameObject().getCharactor()+Color_Eraser.RESET);
                }
                else if (tile.getGameObject() instanceof Home){
                    System.out.print(Color_Eraser.YELLOW+tile.getGameObject().getCharactor()+Color_Eraser.RESET);
                }
                else if (tile.getGameObject() instanceof door){
                    System.out.print(Color_Eraser.Brown+tile.getGameObject().getCharactor()+Color_Eraser.RESET);
                }
                else if (tile.getGameObject() instanceof GreenHouse){
                    System.out.print(Color_Eraser.GREEN+tile.getGameObject().getCharactor()+Color_Eraser.RESET);
                }
                else if (tile.getGameObject() instanceof Fridge){
                    System.out.print(Color_Eraser.WHITE+tile.getGameObject().getCharactor()+Color_Eraser.RESET);
                }
                else if (tile.getGameObject() instanceof WaterTank){
                    System.out.print(Color_Eraser.BLUE+tile.getGameObject().getCharactor()+Color_Eraser.RESET);
                }

            }
            System.out.println();
        }
    }



    public void setTime (boolean gameIsNew) {

        if (gameIsNew)
            currentDate = new DateHour(Season.Spring, 1, 9, 1980);
        else {
            // TODO
        }
    }
    public void setWeather (boolean gameIsNew) {

        if (gameIsNew)
            currentWeather = Weather.Sunny;

        else {
            // TODO
        }
    }
    public void doSeasonAutomaticTask () {

    }




    public void passedOfTime (int day, int hour) {

        currentDate.increaseHour(hour);
        currentDate.increaseDay(day);
    } // TODO   باید کارایی که بعد افزایش زمان انجام میشن رو انجام بدی


    public void startNewGame () {

        setTime(true);
        setWeather(true);
    }
    public void loadGame () {
        setTime(false);
        setWeather(false);
    }
    public void startDay () {

    }
    public void AutomaticFunction () {


        if (currentUser == currentPlayer)
            passedOfTime(0, 1);

        startDay();
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