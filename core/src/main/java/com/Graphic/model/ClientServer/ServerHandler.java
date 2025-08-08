package com.Graphic.model.ClientServer;

import com.Graphic.Controller.MainGame.InputGameController;
import com.Graphic.model.Game;
import com.Graphic.model.MapThings.Tile;
import com.Graphic.model.User;
import com.Graphic.model.Weather.DateHour;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class ServerHandler extends Thread {

    public static ServerHandler instance;

    public ArrayList<User> Players;
    public ArrayList<Tile> bigMap;
    public DataOutputStream out;
    public DataInputStream in;
    public Game game;

    public HashMap<String , Object> body = new HashMap<>();
    public InputGameController controller;

    public DateHour currentDateHour;


    private ServerHandler() {
        game = new Game();
        Players = new ArrayList<>();
        bigMap = new ArrayList<>();
    }
    public static ServerHandler getInstance() {
        if (instance == null)
            instance = new ServerHandler();
        return instance;
    }

    public void render () {

    }
}
