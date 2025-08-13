package com.Graphic.model.ClientServer;

import com.Graphic.model.Enum.WeatherTime.Season;
import com.Graphic.model.Game;
import com.Graphic.model.MapThings.Tile;
import com.Graphic.model.User;
import com.Graphic.model.Weather.DateHour;
import com.badlogic.gdx.utils.TimeUtils;

import java.io.IOException;
import java.util.HashMap;

import static com.Graphic.model.Enum.Commands.CommandType.A_FRIEND_IS_CLOSE;

public class ServerHandler extends Thread {

    private static ServerHandler instance;
    private ClientConnectionController controller;
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
            controller.setTimeAndWeather(game);
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

        for (int i = 0; i < game.getGameState().getPlayers().size(); i++) {
            for (int j = i + 1; j < game.getGameState().getPlayers().size(); j++) {
                if (isClose(game.getGameState().getPlayers().get(i), game.getGameState().getPlayers().get(j))) {
                    HashMap<String , Object> body = new HashMap<>();
                    body.put("Player", game.getGameState().getPlayers().get(j));
                    controller.sendToOnePerson(new Message(A_FRIEND_IS_CLOSE, body), game, game.getGameState().getPlayers().get(i));

                    HashMap<String , Object> body2 = new HashMap<>();
                    body2.put("Player", game.getGameState().getPlayers().get(i));
                    controller.sendToOnePerson(new Message(A_FRIEND_IS_CLOSE, body2), game, game.getGameState().getPlayers().get(j));
                }
            }
        }
    }
    private boolean isClose(User user1, User user2) {
        float firstX = user1.getPositionX();
        float firstY = user1.getPositionY();
        float secondX = user2.getPositionX();
        float secondY = user2.getPositionY();
        return Math.abs(firstX - secondX) < 5f && Math.abs(firstY - secondY) < 5f;
    }

    public DateHour getCurrentDate() {

        return game.getGameState().currentDate;
    }
    public void setGame (Game game) {
        this.game = game;
        game.getGameState().currentDate = new DateHour(Season.Spring, 1, 9, 1950);
    }

}
