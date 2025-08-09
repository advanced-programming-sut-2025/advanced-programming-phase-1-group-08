package com.Graphic.model.ClientServer;

import com.Graphic.model.Animall.Animal;
import com.Graphic.model.Animall.AnimalRenderer;
import com.Graphic.model.Enum.Menu;
import com.Graphic.model.Enum.WeatherTime.Weather;
import com.Graphic.model.MapThings.Tile;
import com.Graphic.model.Places.Farm;
import com.Graphic.model.ToolsPackage.CraftingItem;
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
    private ArrayList<CraftingItem> OnFarm = new ArrayList<>();
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
        numberOfMaps = numberOfMaps + 1;
    }

    public Queue<Animal> getAnimals() {
        return animals;
    }
    public ArrayList<CraftingItem> getOnFarm() {
        return OnFarm;
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
