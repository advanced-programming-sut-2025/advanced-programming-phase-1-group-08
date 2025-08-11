package com.Graphic.model.ClientServer;

import com.Graphic.model.Enum.WeatherTime.Season;
import com.Graphic.model.Game;
import com.Graphic.model.MapThings.Tile;
import com.Graphic.model.Weather.DateHour;
import com.badlogic.gdx.utils.TimeUtils;

import java.io.IOException;
import java.util.HashMap;

public class ServerHandler extends Thread {

    private ClientConnectionController controller;
    private static ServerHandler instance;
    public Game game;

    public long startTime;
    public long lastTime;


    private ServerHandler() {
        initialize();
    }
    public void initialize() {

        startTime = TimeUtils.millis();
        lastTime = TimeUtils.millis();
        controller = ClientConnectionController.getInstance();
    }
    public static ServerHandler getInstance() {
        if (instance == null)
            instance = new ServerHandler();
        return instance;
    }

    @Override
    public void run () {

        try {
            while (true) {
                ServerRender();
                Thread.sleep(10);
            }
        }
        catch (Exception e) {e.printStackTrace();}
    }
    private void ServerRender() throws IOException {

        if (TimeUtils.millis() - lastTime > 120000) {
            controller.passedOfTime(0, 1, game.getGameState().currentDate, game);
            lastTime = TimeUtils.millis();
        }
        for (Tile tile : game.getGameState().bigMap )
            tile.getGameObject().startDayAutomaticTask();
    }

    public DateHour getCurrentDate() {

        return game.getGameState().currentDate;
    }
    public void setGame (Game game) {
        this.game = game;
        game.getGameState().currentDate = new DateHour(Season.Spring, 1, 9, 1950);
    }

}
