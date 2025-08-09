package com.Graphic.model.ClientServer;

import com.Graphic.Controller.MainGame.InputGameController;
import com.Graphic.model.Enum.Commands.CommandType;
import com.Graphic.model.Game;
import com.Graphic.model.MapThings.Tile;
import com.Graphic.model.User;
import com.Graphic.model.Weather.DateHour;
import com.badlogic.gdx.utils.TimeUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class ServerHandler extends Thread {

    public static ServerHandler instance;

    public ArrayList<User> Players;
    public ArrayList<Tile> bigMap;
    public DataOutputStream out;
    public DataInputStream in;
    public Game game;

    public HashMap<String , Object> body = new HashMap<>();
    public InputGameController controller;

    private final int hourSecond = 120000;

    public long startTime;
    public static long lastTime;

    public DateHour currentDateHour;



    private ServerHandler() {
        initialize();
    }
    public void initialize() {

        startTime = TimeUtils.millis();
        lastTime = TimeUtils.millis();
        game = new Game();
        Players = new ArrayList<>();
        bigMap = new ArrayList<>();
        controller = InputGameController.getInstance();

    }
    public static ServerHandler getInstance() {
        if (instance == null)
            instance = new ServerHandler();
        return instance;
    }

    public void render () {
        while (true) {
            if (TimeUtils.millis() - lastTime > hourSecond)
                sendPassedTimeMessage(1, 0);
        }
    }

    private void sendPassedTimeMessage (int hour, int day) {

        HashMap<String , Object> PassedTime = new HashMap<>();
        PassedTime.put("Hour", hour);
        PassedTime.put("Day", day);
        game.getDiffQueue().add(new Message(CommandType.PASSED_TIME , PassedTime));
    }
}
