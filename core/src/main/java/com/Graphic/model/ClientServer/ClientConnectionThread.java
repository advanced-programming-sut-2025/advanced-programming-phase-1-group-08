package com.Graphic.model.ClientServer;

import com.Graphic.Controller.Menu.LoginController;
import com.Graphic.Controller.Menu.RegisterController;
import com.Graphic.Main;
import com.Graphic.model.App;
import com.Graphic.model.Enum.Commands.CommandType;
import com.Graphic.model.Game;
import com.Graphic.model.User;
import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import com.Graphic.model.ClientServer.ClientConnectionController.*;

import com.esotericsoftware.kryonet.Connection.*;

import static com.Graphic.model.ClientServer.ClientConnectionController.*;

public class ClientConnectionThread extends Thread {
    //این کلاس برای ارتباط بین سرور و کلاینت قبل از شروع بازی است
    //این ترد در طرف سرور هست
    //private Socket clientSocket;
    //private DataInputStream in;
    //private DataOutputStream out;
    private ClientConnectionController controller;
    private LoginController LoginController;
    private RegisterController registerController;
    private Connection connection;
    private kryoNetServer server;
    private final BlockingQueue<Message> messageQueue = new LinkedBlockingQueue<>();
    private Game game;


    public ClientConnectionThread(Connection connection) throws IOException {
//        this.clientSocket = clientSocket;
//        in = new DataInputStream(clientSocket.getInputStream());
//        out = new DataOutputStream(clientSocket.getOutputStream());
        this.controller = ClientConnectionController.getInstance();
        this.connection = connection;
        LoginController = new LoginController();
        registerController = new RegisterController();
    }


    @Override
    public void run() {
        System.out.println("Client connected");
        while (true) {
            try {
                Message message = messageQueue.take();
                handleMessage(message);
            }
            catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
        System.out.println("Client disconnected");
    }

    public void enqueueMessage(Message message) {
        messageQueue.offer(message);
    }

    public synchronized void handleMessage(Message message) throws IOException {
        switch (message.getCommandType()) {
            case FARM -> {
                controller.createFarm(message , game);
            }
            case LOGIN -> {
                sendMessage(LoginController.LoginRes(message));
            }
            case SIGN_UP -> {
                sendMessage(registerController.attemptRegistration(message));
            }
            case GENERATE_RANDOM_PASS -> {
                sendMessage(RegisterController.generateRandomPass());
            }
            case NEW_GAME -> {
                Game result = controller.newGame(message , connection);
                if (result != null) {
                    game = result;
                }
            }
            case JOIN_GAME -> {
                Game result = controller.joinGame(message , connection);
                if (result != null) {
                    game = result;
                }
            }
            case MOVE_IN_FARM -> {
                controller.moveInFarm(message, game);
            }
            case ENTER_THE_MARKET -> {
                controller.answerEnterTheMarket(message , game);
            }
            case MOVE_IN_MARKET -> {
                controller.moveInMarket(message, game);
            }
            case BUY -> {
                sendMessage(controller.Buy(message , game));
            }
            case BUY_BACKPACK -> {

            }
            case PLACE_CRAFT_SHIPPING_BIN -> {
                controller.placeCraftOrShippingBin(message , game);
            }
            case BUY_BARN_CAGE -> {
                for (Message message1 : controller.BuyBarnCage(message , game)) {
                    sendMessage(message1);
                }
            }
            case BUY_ANIMAL -> {
                controller.buyAnimal(message , game);
            }
            case SELL_ANIMAL -> {
                controller.sellAnimal(message , game);
            }
            case FEED_HAY -> {
                sendMessage(controller.AnswerFeedHay(message , game));
            }
            case SHEPHERD_ANIMAL -> {
                controller.answerShepherding(message , game);
            }
            case PET -> {
                controller.Pet(message);
            }
            case COLLECT_PRODUCT -> {
                sendMessage(controller.collectProduct(message));
            }
            case CHANGE_INVENTORY -> {
                sendMessage(controller.changeInventory(message , game));
            }
            case LOADED_GAME -> {}

            case PASSED_TIME -> {
                controller.passedOfTime(
                    message.getIntFromBody("Day"),
                    message.getIntFromBody("Hour"),
                    ServerHandler.getInstance(game).currentDateHour, game
                );
            }
            case FriendshipsInquiry -> {
                HashMap<String , Object> body = new HashMap<>();
                body.put("friendships", game.getGameState().friendships);
                sendMessage(new Message(CommandType.FriendshipsInqResponse, body));
            }


        }
    }

    public synchronized void sendMessage(Message message) throws IOException {
        connection.sendTCP(message);
        System.out.println(JSONUtils.toJson(message));
    }
}
