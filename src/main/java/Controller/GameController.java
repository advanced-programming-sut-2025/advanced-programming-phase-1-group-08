package Controller;

import model.*;
import model.Enum.WeatherTime.Season;
import model.Enum.WallType;
import model.Enum.WeatherTime.Weather;
import model.Enum.WeatherTime.WeekDay;

import static model.App.*;

public class GameController {

    public Farm creatInitialFarm (int id) {

        Farm farm= App.currentUser.getFarm();
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
            currentDate = new DateHour(WeekDay.monday, Season.Spring, 1, 9, 1980);

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


    public void startNewGame () {

    }
    public void loadGame () {

    }
    public void AutomaticFunction () {

    }
}