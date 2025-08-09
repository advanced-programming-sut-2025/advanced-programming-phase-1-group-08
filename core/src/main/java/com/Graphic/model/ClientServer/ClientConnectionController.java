package com.Graphic.model.ClientServer;

import com.Graphic.model.Game;
import com.Graphic.model.User;
import com.esotericsoftware.kryonet.Connection;

import java.io.IOException;
import java.util.Map;

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

}
