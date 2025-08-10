package com.Graphic.model.ClientServer;

import com.Graphic.Controller.MainGame.GameControllerLogic;
import com.Graphic.Controller.MainGame.InputGameController;
import com.Graphic.Main;
import com.Graphic.model.Weather.DateHour;

public class ClientWorkController {

    private static ClientWorkController instance;

    private InputGameController inputGameController;


    private ClientWorkController() {

        inputGameController = InputGameController.getInstance();
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
}
