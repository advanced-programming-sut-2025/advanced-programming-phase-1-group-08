package com.Graphic.model.ClientServer;

import com.Graphic.Controller.MainGame.GameControllerLogic;
import com.Graphic.Controller.MainGame.InputGameController;

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

    public void PassedTime (int hour, int day) {
        GameControllerLogic.passedOfTime(hour, day);
    }
}
