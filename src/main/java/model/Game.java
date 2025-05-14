package model;

import model.Enum.Menu;
import model.Enum.WeatherTime.Weather;
import model.MapThings.Tile;
import model.Places.Farm;
import model.Places.Market;
import model.SaveData.PasswordHashUtil;
import model.SaveData.UserBasicInfo;

import java.util.*;

public class Game {
    public Map<Set<User>, List<MessageHandling>> conversations = new HashMap<>();
    public Map<Set<User>, List<Trade>> trades = new HashMap<>();
    public ArrayList<HumanCommunications> friendships = new ArrayList<>();

    public ArrayList<User> players = new ArrayList<>();
    public ArrayList<Farm> farms  = new ArrayList<>();
    public ArrayList<Tile> bigMap = new ArrayList<>();
    public ArrayList<Market> markets = new ArrayList<>();
    public final int mapDimensions = 90;

    public Weather tomorrowWeather;
    public Weather currentWeather;
    public DateHour currentDate;

    public User currentPlayer;
    public User currentUser;
    public Menu currentMenu;

    public Menu getCurrentMenu() {
        return this.currentMenu;
    }

    public HumanCommunications getFriendship(User u1, User u2) {
        for (HumanCommunications f : friendships) {
            if (f.isBetween(u1, u2)) {
                return f;
            }
        }
        return null;
    }

    public void AddNewUser(String username, String pass, String nickname, String email, String gender,
                                  String secQ, String secA){

        String hashPASS = PasswordHashUtil.hashPassword(pass);

        User newUser = new User(
                username,
                nickname,
                email,
                gender,
                0,
                100,
                hashPASS
        );

        App.users.add(newUser);
        App.currentUser = newUser;
        this.currentUser = newUser;

    }
}
