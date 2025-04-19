package model;


import model.Enum.Menu;
import model.Enum.Weather;

import java.util.ArrayList;
import java.util.Date;

public class App {

    public static ArrayList<User> players = new ArrayList<>();
    public static ArrayList<User> users = new ArrayList<>();
    public static ArrayList<Farm> farms = new ArrayList<>();
    public static Weather currentWeather;
    public static Date currentDate;
    public static User currentUser;
    public static Menu currentMenu;


    public static Menu getCurrentMenu() {
        return currentMenu;
    }
}
