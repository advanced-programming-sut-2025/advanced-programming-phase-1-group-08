package com.Graphic.model.ClientServer;

import com.Graphic.Controller.MainGame.GameControllerLogic;
import com.Graphic.Controller.MainGame.InputGameController;
import com.Graphic.Main;
import com.Graphic.model.Enum.Commands.CommandType;
import com.Graphic.model.Enum.WeatherTime.Weather;
import com.Graphic.model.HumanCommunications;
import com.Graphic.model.MapThings.GameObject;
import com.Graphic.model.MapThings.Tile;
import com.Graphic.model.Plants.ForagingMinerals;
import com.Graphic.model.User;
import com.Graphic.model.Weather.DateHour;

import java.awt.*;
import java.util.HashMap;

public class ClientWorkController {

    private static ClientWorkController instance;

    private InputGameController inputController;

    private ClientWorkController() {

        inputController = InputGameController.getInstance();
    }
    public static ClientWorkController getInstance() {

        if (instance == null)
            instance = new ClientWorkController();
        return instance;
    }

                                    // Erfan
    public void PassedTime (int hour, int day) {

        GameControllerLogic.passedOfTime(hour, day);
    }
    public void setTime(int hour, int day) {

        if (Main.getClient().getLocalGameState().currentDate != null) {
            Main.getClient().getLocalGameState().currentDate.setHour(hour);
            Main.getClient().getLocalGameState().currentDate.setDate(day);
        }
        else {
            DateHour dateHour = new DateHour();
            dateHour.setHour(hour);
            dateHour.setDate(day);
            Main.getClient().getLocalGameState().currentDate = dateHour;
        }
    }
    public void changeGameObject(int x, int y, GameObject gameObject) {
        Tile tile = GameControllerLogic.getTileByCoordinates(x, y, Main.getClient().getLocalGameState());
        tile.setGameObject(gameObject);
    }
    public void setTomorrowWeather (Weather weather) {

        Main.getClient().getLocalGameState().tomorrowWeather = weather;
    }
    public void addMineral (ForagingMinerals mineral, Point point, User user) {

        mineral.setPosition(point);
        user.getFarm().getMine().getForagingMinerals().add(mineral);
        user.getFarm().getMine().getTaken().add(point);
    }

                                    // Ario

    public HumanCommunications getFriendship(User u1, User u2) {
        HashMap<String, Object> body = new HashMap<>();
        Main.getClient().getRequests().add(new Message(CommandType.FriendshipsInquiry, body));


        for (HumanCommunications f : Main.getClient().getLocalGameState().friendships) {
            if (f.isBetween(u1, u2)) {
                return f;
            }
        }
        return null;
    }
}
