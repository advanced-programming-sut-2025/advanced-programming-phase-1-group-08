package model;


import model.Enum.AllPlants.CropsType;
import model.Enum.AllPlants.ForagingCropsType;
import model.Enum.AllPlants.TreesProductType;
import model.Enum.ItemType.MarketItemType;
import model.Enum.Menu;
import model.Enum.WeatherTime.Weather;
import model.MapThings.Tile;
import model.OtherItem.MarketItem;
import model.Places.Farm;
import model.Places.Market;
import model.Plants.AllCrops;
import model.Plants.ForagingCrops;
import model.Plants.TreesProdct;
import model.SaveData.PasswordHashUtil;
import model.SaveData.UserBasicInfo;

import java.util.*;

public class App {

    // TODO موقع سیو دیتا اینارو باید همشو سیو کرد و موقع ترمینیت پاک کرد
//    public static Map<Set<User>, List<MessageHandling>> conversations = new HashMap<>();
//    public static Map<Set<User>, List<Trade>> trades = new HashMap<>();
//    public static ArrayList<HumanCommunications> friendships = new ArrayList<>();
    public static ArrayList<Game> games = new ArrayList<>();
    public static Game currentGame;

//    public static ArrayList<User> players = new ArrayList<>();
    public static ArrayList<User> users  = new ArrayList<>();
//    public static ArrayList<Farm> farms  = new ArrayList<>();
//    public static ArrayList<Tile> bigMap = new ArrayList<>();
//    public static ArrayList<Market> markets = new ArrayList<>();

    public static final int mapDimensions = 90;

//    public static Weather tomorrowWeather;
//    public static Weather currentWeather;
//    public static DateHour currentDate;

//    public static User currentPlayer; // TODO
    public static User currentUser; // TODO
    public static Menu currentMenu;


    public static Menu getCurrentMenu() {
        return currentMenu;
    }

    public static HumanCommunications getFriendship(User u1, User u2) {
        for (HumanCommunications f : currentGame.friendships) {
            if (f.isBetween(u1, u2)) {
                return f;
            }
        }
        return null;
    }

    public static void AddNewUser(String username, String pass, String nickname, String email, String gender,
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

        model.SaveData.UserDataBase.addUser(new UserBasicInfo(
                newUser.getUsername(),
                newUser.getHashPass(),
                newUser.getEmail(),
                newUser.getNickname(),
                newUser.getGender(),
                secQ,
                secA
        ));

    }

    public static Items AllFromDisplayNames (String name) {
        Items items = null;
        try {
            items = new AllCrops(CropsType.fromDisplayName(name));
            return items;
        } catch (Exception e) {
            try {
                items = new ForagingCrops(ForagingCropsType.fromDisplayName(name));
                return items;
            } catch (Exception ex) {
                try {
                    items = new TreesProdct(TreesProductType.fromDisplayName(name));
                    return items;
                } catch (Exception exe) {
                    try {
                        items = new MarketItem(MarketItemType.fromDisplayName(name));
                        return items;
                    }
                    catch (Exception exception) {
                        return null;
                    }
                }
            }
        }
    }
}


