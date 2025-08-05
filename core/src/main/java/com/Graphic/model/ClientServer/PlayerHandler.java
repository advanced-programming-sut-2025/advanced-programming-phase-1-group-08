package com.Graphic.model.ClientServer;

import com.Graphic.Controller.MainGame.InputGameController;
import com.Graphic.Controller.MainGame.Marketing;
import com.Graphic.model.MapThings.Tile;
import com.Graphic.model.User;

import com.Graphic.model.Game;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import static com.Graphic.Controller.MainGame.GameControllerLogic.*;
import static com.Graphic.model.Enum.Commands.CommandType.GAME_START;

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
        out.writeUTF(JSONUtils.toJson(message));
        out.flush();
    }

    public synchronized void handleMessage(Message message) throws IOException {
        switch (message.getCommandType()) {
            case FARM -> {
                game.getDiffQueue().add(createInitialFarm(message.getIntFromBody("Index") , Player , game.getGameState()));
                game.getGameState().incrementNumberOfMaps();
                if (game.getGameState().getNumberOfMaps() == 4) {
                    game.getDiffQueue().add(build(game.getGameState()));
                }
                break;
            }
            case MOVE_IN_FARM -> {
                game.getDiffQueue().add(controller.checkWalking(message , game));
            }
            case ENTER_THE_MARKET -> {
                AnswerEnterTheMarket(message , game);
            }
            case MOVE_IN_MARKET -> {
                game.getDiffQueue().add(Marketing.getInstance().checkColision(message));
            }
            case BUY -> {
                Marketing.getInstance().payForBuy(message, game);
                Marketing.getInstance().reduceProductInMarket(message, game);
                sendMessage(Marketing.getInstance().addItemToInventory(message));
            }
            case BUY_BACKPACK -> {
                Marketing.getInstance().payForBuy(message, game);
                Marketing.getInstance().reduceProductInMarket(message, game);
                sendMessage(Marketing.getInstance().changeBackPack(message, game));
            }
            case GET_DIFF -> {
                sendMessage(game.getStateFromPlayer(Player));
                break;
            }
            case BUY_BARN_CAGE -> {
                for (Message message1 : Marketing.getInstance().payForBuilding(message , game)) {
                    sendMessage(message1);
                }
                Marketing.getInstance().reduceBarnOrCageInMarket(message , game);
            }
        }
    }

    public synchronized void gameStart() throws IOException {
        HashMap<String , Object> body = new HashMap<>();
        body.put("Players" , game.getGameState().getPlayers());
        sendMessage(new Message(GAME_START , body));
    }
}
