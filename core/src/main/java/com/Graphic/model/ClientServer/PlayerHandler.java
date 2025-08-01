package com.Graphic.model.ClientServer;

import com.Graphic.model.User;

import com.Graphic.model.Game;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class PlayerHandler extends Thread {

    private User Player;
    private Socket clientSocket;
    private Game game;
    PrintWriter out;

    public PlayerHandler(User player , Socket clientSocket , Game game) {
        this.Player = player;
        this.clientSocket = clientSocket;
        this.game = game;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            String line;
            while ((line = in.readLine()) != null) {
                switch (line) {
                    case "GET_DIFF"-> {
                        out.println(game.getStateFromPlayer(Player));
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
}
