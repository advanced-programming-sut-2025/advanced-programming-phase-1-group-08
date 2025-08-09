package com.Graphic.model.ClientServer;

import com.Graphic.Controller.MainGame.GameControllerLogic;
import com.Graphic.Controller.MainGame.InputGameController;
import com.Graphic.Main;
import com.Graphic.model.Enum.Commands.CommandType;
import com.Graphic.model.HumanCommunications;
import com.Graphic.model.User;

import java.util.HashMap;

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


    public HumanCommunications getFriendship(User u1, User u2) {
        // controller has to have it
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
