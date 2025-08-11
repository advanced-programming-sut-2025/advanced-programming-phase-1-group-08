package com.Graphic.model.ClientServer;

import com.Graphic.Controller.Menu.LoginController;
import com.Graphic.Controller.Menu.RegisterController;
import com.Graphic.Main;
import com.Graphic.model.*;
import com.Graphic.model.Enum.Commands.CommandType;
import com.Graphic.model.Game;
import com.Graphic.model.Items;
import com.Graphic.model.User;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.*;
import java.util.ArrayList;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientConnectionThread extends Thread {
    //این کلاس برای ارتباط بین سرور و کلاینت قبل از شروع بازی است
    //این ترد در طرف سرور هست
    //private Socket clientSocket;
    //private DataInputStream in;
    //private DataOutputStream out;
    private ServerHandler server;
    private ClientConnectionController controller;
    private LoginController LoginController;
    private RegisterController registerController;
    private Connection connection;
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
        connection.addListener(new Listener(){
            public void received(Connection connection, Object object) {
                if (object instanceof Message) {
                    try {
                        handleMessage((Message) object);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

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
            case BUY_BACKPACK -> {}
            case PLACE_CRAFT_SHIPPING_BIN -> {
                controller.placeCraftOrShippingBin(message , game);
            }
            case BUY_BARN_CAGE -> {
                ArrayList<Message> messages = controller.BuyBarnCage(message , game);
                for (Message message1 : messages) {
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
            case CHANGE_ABILITY_LEVEL ->  {
                int xp = Main.getClient().getPlayer().getFishingAbility();
                Main.getClient().getPlayer().increaseFishingAbility((int) (xp * 1.4));
            }
            case CHANGE_INVENTORY -> {
                sendMessage(controller.changeInventory(message , game));
            }
            case CHANGE_FRIDGE -> {
                Items items = message.getFromBody("Item");
                int amount = message.getFromBody("amount");
                if (Main.getClient().getPlayer().getFarm().getHome().getFridge().items.containsKey(items)) {
                    Main.getClient().getPlayer().getFarm().getHome().getFridge().items.compute(items,(k,v) -> v + amount);
                    if (Main.getClient().getPlayer().getFarm().getHome().getFridge().items.get(items) == 0) {
                        Main.getClient().getPlayer().getFarm().getHome().getFridge().items.remove(items);
                    }
                }
                else {
                    Main.getClient().getPlayer().getFarm().getHome().getFridge().items.put(items,amount);
                }
            }
            case TALK_TO_FRIEND -> {
                MessageHandling messageHandling = message.getFromBody("MessageHandling");

                // add to server conversations
                Set<User> key = new HashSet<>(Arrays.asList(messageHandling.getSender(), messageHandling.getReceiver()));
                game.getGameState().conversations.putIfAbsent(key, new ArrayList<>());
                game.getGameState().conversations.get(key).add(messageHandling);

                // send to both players to update local conversations
                HashMap<String , Object> body = new HashMap<>();
                body.put("conversations", game.getGameState().conversations);
                ClientConnectionController.getInstance().sendToOnePerson(new Message(CommandType.UPDATE_CONVERSATIONS, body), game, messageHandling.getSender());
                ClientConnectionController.getInstance().sendToOnePerson(new Message(CommandType.UPDATE_CONVERSATIONS, body), game, messageHandling.getReceiver());

                // add xp
                for (HumanCommunications f: game.getGameState().friendships) {
                    if (f.isBetween(messageHandling.getSender(), messageHandling.getReceiver())) {
                        f.addXP(10);
                    }
                }
                HashMap<String, Object> body2 = new HashMap<>();
                body2.put("friendships", game.getGameState().friendships);
                ClientConnectionController.getInstance().sendToAll(new Message(CommandType.UPDATE_FRIENDSHIPS, body2), game);
            }
            case EXIT_MARKET -> {
                System.out.println("Exit");
                controller.ExitTheMarket(message , game);
            }
            case LOADED_GAME -> {}

            case SET_TIME -> {
                controller.setTime(
                    message.getFromBody("Day"),
                    message.getFromBody("Hour"), game
                );
            }
            case GET_TIME -> {
                controller.sendSetTimeMessage(
                    game.getGameState().currentDate.getHour(),
                    game.getGameState().currentDate.getDate(), game);
            }
            case CHANGE_GAME_OBJECT -> {
                controller.sendSetGameObjectMessage(
                    message.getFromBody("X"),
                    message.getFromBody("Y"),
                    message.getFromBody("Object"), game
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
