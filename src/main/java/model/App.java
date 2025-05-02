package model;


import model.Enum.Menu;
import model.Enum.WeatherTime.Weather;
import model.Places.Farm;

import java.util.ArrayList;

public class App {

    public static ArrayList<User> players = new ArrayList<>();
    public static ArrayList<User> users  = new ArrayList<>();
    public static ArrayList<Farm> farms  = new ArrayList<>();
    public static ArrayList<Tile> bigMap = new ArrayList<>();
    public static ArrayList<Tile> plowedTile = new ArrayList<>();

    public static final int mapDimensions = 90;

    public static Weather tomorrowWeather;
    public static Weather currentWeather;
    public static DateHour currentDate;

    public static User currentPlayer; // TODO
    public static User currentUser; // TODO
    public static Menu currentMenu;


    public static Menu getCurrentMenu() {
        return currentMenu;
    }

    public static void AddNewUser (String username, String pass, String nickname, String email, String gender) {
        User newUser = new User(
                username,
                pass,
                nickname,
                email,
                gender,
                0,
                100
        );
        App.users.add(newUser);
        App.currentUser = newUser;
    }


}
