package com.Graphic.model.ClientServer;

import com.Graphic.Controller.MainGame.InputGameController;
import com.Graphic.Main;
import com.Graphic.model.Enum.Commands.CommandType;
import com.Graphic.model.Game;
import com.Graphic.model.MapThings.Tile;
import com.Graphic.model.User;
import com.Graphic.model.Weather.DateHour;
import com.badlogic.gdx.utils.TimeUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static com.Graphic.model.ClientServer.ClientConnectionController.sendToAll;
import static com.Graphic.model.Weather.DateHour.getDayDifferent;

public class ServerHandler extends Thread {

    private static ServerHandler instance;

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
            passedOfTime(0, 1);


    }

    private void sendPassedTimeMessage (int hour, int day) throws IOException {

        HashMap<String , Object> PassedTime = new HashMap<>();
        PassedTime.put("Hour", hour);
        PassedTime.put("Day", day);
        sendToAll(new Message(CommandType.PASSED_TIME , PassedTime), game);

    }
    private void passedOfTime (int day, int hour) throws IOException {

        DateHour dateHour = currentDateHour.clone();

        dateHour.increaseHour(hour);
        dateHour.increaseDay(day);

        if (dateHour.getHour() > 22) {
            passedOfTime(getDayDifferent(dateHour, Main.getClient().getLocalGameState().currentDate), 24 - dateHour.getHour() + 9 + hour);
            return;
        }
        if (dateHour.getHour() < 9) {
            passedOfTime(getDayDifferent(dateHour, Main.getClient().getLocalGameState().currentDate), 9 - dateHour.getHour() + hour);
            return;
        }

        int number = getDayDifferent(currentDateHour, dateHour);

        for (int i = 0 ; i < number ; i++)
            currentDateHour.increaseDay(1);

        currentDateHour.increaseHour(dateHour.getHour() - currentDateHour.getHour());
        sendPassedTimeMessage(dateHour.getHour() - currentDateHour.getHour(), number);
    }

}
