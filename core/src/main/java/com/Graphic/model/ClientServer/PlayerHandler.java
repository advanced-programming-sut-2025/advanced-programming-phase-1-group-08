package com.Graphic.model.ClientServer;

import com.Graphic.model.User;

import com.Graphic.model.Game;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

import static com.Graphic.model.Enum.Commands.CommandType.INDEX;

public class PlayerHandler extends Thread {

    private User Player;
    private Socket clientSocket;
    private Game game;
    DataOutputStream out;
    HashMap<String , Object> body = new HashMap<>();

    public PlayerHandler(User player , Socket clientSocket , Game game , int index) throws IOException {
        this.Player = player;
        this.clientSocket = clientSocket;
        this.game = game;
        out = new DataOutputStream(clientSocket.getOutputStream());
        body.put("index", index);
        sendMessage(new Message(INDEX , body));
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                switch (line) {
                    case "GET_DIFF"-> {
                        //out.println(game.getStateFromPlayer(Player));
                        break;
                    }
                    default -> {

                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                clientSocket.close();
            }
            catch (Exception e) {

            }
        }
    }

    public void sendMessage(Message message) throws IOException {
        String result = JSONUtils.toJson(message);
        out.writeUTF(result);
    }
}
