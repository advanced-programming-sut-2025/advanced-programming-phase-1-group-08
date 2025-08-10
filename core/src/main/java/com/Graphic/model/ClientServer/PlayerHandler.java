package com.Graphic.model.ClientServer;

import com.Graphic.Controller.MainGame.InputGameController;
import com.Graphic.Controller.MainGame.Marketing;
import com.Graphic.Main;
import com.Graphic.model.Animall.Animal;
import com.Graphic.model.Items;
import com.Graphic.model.MapThings.Tile;
import com.Graphic.model.User;

import com.Graphic.model.Game;
import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

import static com.Graphic.Controller.MainGame.GameControllerLogic.*;
import static com.Graphic.model.Enum.Commands.CommandType.*;

public class PlayerHandler extends Thread {
    //ترد سمت سرور که قراره بازی رو برای کلاینت مورد نظر بچرخونه
    private User Player;
    private Socket clientSocket;
    private Game game;
    DataOutputStream out;
    DataInputStream in;
    HashMap<String , Object> body = new HashMap<>();
    private InputGameController controller;
    private static volatile ArrayList<Tile> tiles = null;
    Gson gson = new Gson();

    public PlayerHandler(User player , Socket clientSocket , Game game , int index) throws IOException {
        this.Player = player;
        this.clientSocket = clientSocket;
        this.game = game;
        out = new DataOutputStream(clientSocket.getOutputStream());
        in = new DataInputStream(clientSocket.getInputStream());
        controller = InputGameController.getInstance();
    }

    public void run() {
        try {
            gameStart();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String line;
        while (true) {
            try {
                line = in.readUTF();
                handleMessage(JSONUtils.fromJson(line));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(Message message) throws IOException {
        if (message.getCommandType() == FARM || message.getCommandType() == BIG_MAP) {
            if (message.getCommandType() == BIG_MAP) {
                System.out.println("resid");
            }
            out.writeBoolean(false);
            byte[] data = JSONUtils.toJson(message).getBytes(StandardCharsets.UTF_8);
            out.writeInt(data.length);
            out.write(data);
            out.flush();
        }
        else {
            out.writeBoolean(true);
            out.writeUTF(JSONUtils.toJson(message));
            out.flush();
        }
    }

    public synchronized void handleMessage(Message message) throws IOException {
        switch (message.getCommandType()) {



        }

    }

    public synchronized void gameStart() throws IOException {
        HashMap<String , Object> body = new HashMap<>();
        body.put("Players" , game.getGameState().getPlayers());
        sendMessage(new Message(GAME_START , body));
    }
}
