package model;


import model.Enum.AllPlants.*;
import model.Enum.ItemType.AnimalProductType;
import model.Enum.ItemType.MarketItemType;
import model.Enum.Menu;
import model.Enum.WeatherTime.Weather;
import model.MapThings.Tile;
import model.Places.MarketItem;
import model.Places.Farm;
import model.Places.Market;
import model.Plants.*;
import model.SaveData.PasswordHashUtil;
import model.SaveData.UserBasicInfo;
import model.SaveData.UserStorage;

import java.io.IOException;
import java.util.*;

public class App {

    // TODO موقع سیو دیتا اینارو باید همشو سیو کرد و موقع ترمینیت پاک کرد
//    public static Map<Set<User>, List<MessageHandling>> conversations = new HashMap<>();
//    public static Map<Set<User>, List<Trade>> trades = new HashMap<>();
//    public static ArrayList<HumanCommunications> friendships = new ArrayList<>();
    public static ArrayList<Game> games = new ArrayList<>();
    public static Game currentGame;

//    public static ArrayList<User> players = new ArrayList<>();
    public static List<User> users  = new ArrayList<>();


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
                        try {
                            items = new ForagingMinerals(ForagingMineralsType.fromDisplayName(name));
                            return items;
                        } catch (Exception e2) {
                            try {
                                items = new ForagingSeeds(ForagingSeedsType.fromDisplayName(name));
                                return items;
                            } catch (Exception e3) {
                                try {
                                    items = new TreeSource(TreesSourceType.fromDisplayName(name));
                                    return items;
                                } catch (Exception e4) {
                                    return null;
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    public static User findPlayerInGame (String name) {
        for (User player: currentGame.players) {
            if (player.getUsername().equalsIgnoreCase(name))
                return player;
        }
        return null;
    }
}


