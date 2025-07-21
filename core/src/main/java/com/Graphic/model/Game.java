package com.Graphic.model;

import com.Graphic.model.Enum.Menu;
import com.Graphic.model.Enum.SecurityQuestions;
import com.Graphic.model.Enum.WeatherTime.Weather;
import com.Graphic.model.MapThings.Tile;
import com.Graphic.model.Places.Farm;
import com.Graphic.model.Places.Market;
import com.Graphic.model.SaveData.PasswordHashUtil;
import com.Graphic.model.SaveData.UserStorage;
import com.Graphic.model.Weather.DateHour;


import java.io.IOException;
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

    public static void AddNewUser(String username, String pass, String nickname, String email, String gender,
                                  SecurityQuestions secQ, String secA) throws IOException{

        String hashPASS = PasswordHashUtil.hashPassword(pass);

        User newUser = new User(
                username,
                nickname,
                email,
                gender,
                0,
                100,
                hashPASS,
                secQ,
                secA
        );

        App.users.add(newUser);
        App.currentUser = newUser;

        List<User> users = UserStorage.loadUsers();
        users.add(newUser);
        try {
            com.Graphic.model.SaveData.UserStorage.saveUsers(users);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
