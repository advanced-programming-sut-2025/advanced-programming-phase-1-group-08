package com.Graphic.model.ClientServer;

import com.Graphic.model.Enum.Menu;
import com.Graphic.model.Enum.WeatherTime.Weather;
import com.Graphic.model.MapThings.Tile;
import com.Graphic.model.Places.Farm;
import com.Graphic.model.User;
import com.Graphic.model.Weather.DateHour;

import java.util.ArrayList;
import java.util.Map;

public class GameState {


    private ArrayList<User> players = new ArrayList<>();
    public ArrayList<Tile> bigMap = new ArrayList<>();
    private ArrayList<Farm> farms = new ArrayList<>();

    public Weather tomorrowWeather;
    public Weather currentWeather;
    public DateHour currentDate;
    public Menu currentMenu;

    private int numberOfMaps = 0;







    public ArrayList<User> getPlayers() {
        return players;
    }


    public ArrayList<Farm> getFarms() {
        return farms;
    }

    public synchronized int getNumberOfMaps() {
        return numberOfMaps;
    }

    public void incrementNumberOfMaps() {
        numberOfMaps++;
    }




    public static boolean equals(Object a, Object b) {
         if (a instanceof ArrayList<?> && b instanceof ArrayList<?>) {
             ArrayList<?> aList = (ArrayList<?>) a;
             ArrayList<?> bList = (ArrayList<?>) b;
             if (aList.size() != bList.size()) {
                 return false;
             }
             for (int i = 0; i < aList.size(); i++) {
                 if (!aList.get(i).equals(bList.get(i))) {
                     return false;
                 }
             }
             return true;
         }
         if (a instanceof Map<?, ?> && b instanceof Map<?, ?>) {
             Map<?, ?> aMap = (Map<?, ?>) a;
             Map<?, ?> bMap = (Map<?, ?>) b;
             if (aMap.size() != bMap.size()) {
                 return false;
             }
             for (int i = 0; i < aMap.size(); i++) {
                 if (! bMap.containsValue(aMap.get(i))) {
                     return false;
                 }
             }
         }
         return true;
    }
}
