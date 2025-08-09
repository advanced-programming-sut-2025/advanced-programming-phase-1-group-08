package com.Graphic.model.ClientServer;

import com.Graphic.model.Game;
import com.Graphic.model.Weather.DateHour;
import com.badlogic.gdx.utils.TimeUtils;

import java.io.IOException;
import java.util.HashMap;

public class ServerHandler extends Thread {

    private static ServerHandler instance;
    private ClientConnectionController controller;

    public Game game;
    public HashMap<String , Object> body = new HashMap<>();

    private final int hourSecond = 120000;

    public long startTime;
    public long lastTime;

    public DateHour currentDateHour;



    private ServerHandler(Game game) {
        initialize(game);
    }
    public void initialize(Game game) {

        startTime = TimeUtils.millis();
        lastTime = TimeUtils.millis();
        this.game = game;
        controller = ClientConnectionController.getInstance();

    }
    public static ServerHandler getInstance(Game game) {
        if (instance == null)
            instance = new ServerHandler(game);
        return instance;
    }

    @Override
    public void run () {

        while (true) {
            try {
                ServerRender();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void ServerRender() throws IOException {
        if (TimeUtils.millis() - lastTime > hourSecond)
            controller.passedOfTime(0, 1, currentDateHour, game);

        controller.CheckFriendDistance(game);
    }



}
