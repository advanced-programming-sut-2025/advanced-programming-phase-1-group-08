package model;


import model.Enum.Menu;
import model.Enum.WeatherTime.Weather;

import java.util.ArrayList;

public class App {

    public static ArrayList<User> players = new ArrayList<>();
    public static ArrayList<User> users = new ArrayList<>();
    public static ArrayList<Farm> farms = new ArrayList<>();
    public static Weather currentWeather;
    public static DateHour currentDate;
    public static User currentPlayer; // TODO
    public static User currentUser; // TODO
    public static Menu currentMenu;


    public static Menu getCurrentMenu() {
        return currentMenu;
    }
}
