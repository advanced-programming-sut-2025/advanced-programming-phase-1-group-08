package Controller;

import model.*;
import model.Enum.WeatherTime.Season;
import model.Enum.ItemType.WallType;
import model.Enum.WeatherTime.Weather;
import model.Places.Farm;
import model.Places.Home;
import model.Places.Lake;

import static model.App.*;
import static model.Color_Eraser.*;

public class GameController {

    public Farm creatInitialFarm (int id) {

        Farm farm= currentUser.getFarm();
        if ( id == 1 ) {

            Lake lake = new Lake();
            lake.setCharactor('L');
            for (int i=0;i<5;i++){
                for (int j=25;j<30;j++) {
                    Tile tile = new Tile(i, j, lake);
                    farm.Farm.add(tile);
                }
            }

            Mine mine = new Mine();
            mine.setCharactor('M');
            for (int i=24;i<30;i++){
                for (int j=24;j<30;j++) {
                    Tile tile = new Tile(i, j, mine);
                    farm.Farm.add(tile);
                }
            }

            Wall wall = new Wall();
            wall.setWallType(WallType.House);
            wall.setCharactor('#');
            for (int i = 12 ; i < 19 ; i++) {
                for (int j = 0 ; j < 7 ; j++) {
                    if (i==12 || i==18) {
                        Tile tile = new Tile(i, j, wall);
                        farm.Farm.add(tile);
                    }
                    else {
                        if (j==0 || j==6){
                            Tile tile = new Tile(i, j, wall);
                            farm.Farm.add(tile);
                        }
                    }
                }
            }

            Wall GreenWall = new Wall();
            GreenWall.setWallType(WallType.GreenHouse);
            GreenWall.setCharactor('#');

            for (int i = 0 ; i < 7 ; i++)
                for (int j = 0 ; j < 9 ; j++) {
                    if (i==0){
                        Tile tile = new Tile(i, j, GreenWall);
                        farm.Farm.add(tile);
                    }
                    else {
                        if (j==0 || j==8){
                            Tile tile = new Tile(i, j, GreenWall);
                            farm.Farm.add(tile);
                        }
                    }
                }

            Fridge fridge=new Fridge(18,6);
            Home home=new Home(13,1,fridge);



        }
        return null;
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