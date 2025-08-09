package com.Graphic.model.ClientServer;

import com.Graphic.Main;
import com.Graphic.model.Enum.Commands.CommandType;
import com.Graphic.model.Game;
import com.Graphic.model.User;
import com.Graphic.model.Weather.DateHour;
import com.esotericsoftware.kryonet.Connection;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.Graphic.model.Weather.DateHour.getDayDifferent;

public class ClientConnectionController {

    private static ClientConnectionController instance;

    public void sendToAll(Message message, Game game) throws IOException {
        for (Map.Entry<User, Connection> entry : game.connections.entrySet()) {
            entry.getValue().sendTCP(message);
        }
    }
    public void sendToOnePerson(Message message , Game game , User user) throws IOException {
        for (Map.Entry<User , Connection> entry : game.connections.entrySet()) {
            if (entry.getKey().getUsername().trim().equals(user.getUsername().trim())) {
                entry.getValue().sendTCP(message);
            }
        }
    }

    private ClientConnectionController() {}

    public static ClientConnectionController getInstance() {
        if (instance == null)
            instance = new ClientConnectionController();
        return instance;
    }
    public void CheckFriendDistance() {

    }



    public void sendPassedTimeMessage (int hour, int day, Game game) throws IOException {

        HashMap<String , Object> PassedTime = new HashMap<>();
        PassedTime.put("Hour", hour);
        PassedTime.put("Day", day);
        sendToAll(new Message(CommandType.PASSED_TIME , PassedTime), game);
    }
    public void passedOfTime (int day, int hour, DateHour currentDateHour, Game game) throws IOException {

        DateHour dateHour = currentDateHour.clone();

        dateHour.increaseHour(hour);
        dateHour.increaseDay(day);

        if (dateHour.getHour() > 22) {
            passedOfTime(getDayDifferent(dateHour, Main.getClient().getLocalGameState().currentDate),
                24 - dateHour.getHour() + 9 + hour, currentDateHour, game);
            return;
        }
        if (dateHour.getHour() < 9) {
            passedOfTime(getDayDifferent(dateHour, Main.getClient().getLocalGameState().currentDate),
                9 - dateHour.getHour() + hour, currentDateHour, game);
            return;
        }

        int number = getDayDifferent(currentDateHour, dateHour);

        for (int i = 0 ; i < number ; i++)
            currentDateHour.increaseDay(1);

        currentDateHour.increaseHour(dateHour.getHour() - currentDateHour.getHour());
        sendPassedTimeMessage(dateHour.getHour() - currentDateHour.getHour(), number, game);
    }
}
