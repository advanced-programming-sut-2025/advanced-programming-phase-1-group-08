package com.Graphic.model.ClientServer;

import com.Graphic.model.Animall.Animal;
import com.Graphic.model.Animall.AnimalRenderer;
import com.Graphic.model.Enum.Menu;
import com.Graphic.model.Enum.WeatherTime.Weather;
import com.Graphic.model.MapThings.Tile;
import com.Graphic.model.Places.Farm;
import com.Graphic.model.User;
import com.Graphic.model.Weather.DateHour;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class GameState {


    private ArrayList<User> players = new ArrayList<>();
    public ArrayList<Tile> bigMap = new ArrayList<>();
    private ArrayList<Farm> farms = new ArrayList<>();
    private Queue<Animal> animals = new LinkedList<>();
    private boolean chooseMap = false;

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

    public Queue<Animal> getAnimals() {
        return animals;
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

    public boolean getChooseMap() {
        return chooseMap;
    }
    public void setChooseMap(boolean chooseMap) {
        this.chooseMap = chooseMap;
    }

    public Weather getTomorrowWeather() {
        return tomorrowWeather;
    }
    public Weather getCurrentWeather() {
        return currentWeather;
    }
    public DateHour getCurrentDate() {
        return currentDate;
    }
    public Menu getCurrentMenu() {
        return currentMenu;
    }
    public void addUser (User user) {
        players.add(user);
    }
}
