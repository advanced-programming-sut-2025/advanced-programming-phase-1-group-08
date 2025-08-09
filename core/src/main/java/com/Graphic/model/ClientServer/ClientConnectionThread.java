package com.Graphic.model.ClientServer;

import com.Graphic.Controller.MainGame.Marketing;
import com.Graphic.Controller.Menu.LoginController;
import com.Graphic.Controller.Menu.RegisterController;
import com.Graphic.Main;
import com.Graphic.model.Animall.Animal;
import com.Graphic.model.App;
import com.Graphic.model.Enum.Commands.CommandType;
import com.Graphic.model.Game;
import com.Graphic.model.Items;
import com.Graphic.model.User;
import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static com.Graphic.Controller.MainGame.GameControllerLogic.*;
import static com.Graphic.Controller.MainGame.GameControllerLogic.AnswerShepherding;
import static com.Graphic.model.ClientServer.ClientConnectionController.sendToAll;
import static com.Graphic.model.Enum.Commands.CommandType.CHANGE_INVENTORY;

public class ClientConnectionThread extends Thread {
    //این کلاس برای ارتباط بین سرور و کلاینت قبل از شروع بازی است
    //این ترد در طرف سرور هست
    //private Socket clientSocket;
    //private DataInputStream in;
    //private DataOutputStream out;
    private LoginController controller;
    private RegisterController registerController;
    private Connection connection;
    private KryoNetServer server;
    private final BlockingQueue<Message> messageQueue = new LinkedBlockingQueue<>();
    private Game game;


    public ClientConnectionThread(Connection connection) throws IOException {
//        this.clientSocket = clientSocket;
//        in = new DataInputStream(clientSocket.getInputStream());
//        out = new DataOutputStream(clientSocket.getOutputStream());
        this.connection = connection;
        controller = new LoginController();
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
            case LOGIN -> {
                sendMessage(controller.LoginRes(message));
            }
            case SIGN_UP -> {
                System.out.println("SERVER");
                sendMessage(registerController.attemptRegistration(message));
            }
            case GENERATE_RANDOM_PASS -> {
                sendMessage(RegisterController.generateRandomPass());
            }
            case NEW_GAME -> {
                String id = message.getFromBody("id");
                User player = message.getFromBody("Player");
                for (Map.Entry<String , Game> entry : App.games.entrySet()) {
                    if (id.equals(entry.getKey())) {
                        //خطا بده بهش
                        System.out.println("Repeat");
                    }
                }
                System.out.println("No Repeat");
                App.games.put(id, new Game());
                App.games.get(id).addPlayer(player , connection );
            }
            case JOIN_GAME -> {
                String id = message.getFromBody("id");
                User player = message.getFromBody("Player");
                for (Map.Entry<String , Game> entry : App.games.entrySet()) {
                    if (id.equals(entry.getKey())) {
                        if (entry.getValue().getGameState().getPlayers().size() != 4) {
                            this.game = entry.getValue();
                            entry.getValue().addPlayer(player , connection);
                        }
                    }
                }
            }
            case LOADED_GAME -> {}
            case FARM -> {

//                sendToAll(createInitialFarm(message.getIntFromBody("Index") , Player , game.getGameState()), game);
//                game.getDiffQueue().add(createInitialFarm(message.getIntFromBody("Index") , Player , game.getGameState()));
                Main.getClient().getLocalGameState().incrementNumberOfMaps();
                if (Main.getClient().getLocalGameState().getNumberOfMaps() == 4) {
                    game.getDiffQueue().add(build(game.getGameState()));
                }
                break;
            }
            case MOVE_IN_FARM -> {
//                game.getDiffQueue().add(controller.checkWalking(message , game));
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
            case PLACE_CRAFT_SHIPPING_BIN -> {
//                controller.AnswerPlaceCraft(message, game);
            }
            case BUY_BARN_CAGE -> {
                for (Message message1 : Marketing.getInstance().payForBuilding(message , game)) {
                    sendMessage(message1);
                }
                Marketing.getInstance().reduceBarnOrCageInMarket(message , game);
            }
            case CHANGE_INVENTORY -> {
                User player = message.getFromBody("Player");
                for (User user : game.getGameState().getPlayers()) {
                    if (user.getUsername().trim().equals(player.getUsername().trim())) {
                        Items items = message.getFromBody("Item");
                        int amount = message.getIntFromBody("amount");
                        if (user.getBackPack().inventory.Items.containsKey(items)) {
                            user.getBackPack().inventory.Items.compute(items,(k,v) -> v + amount);
                            if (user.getBackPack().inventory.Items.get(items) == 0) {
                                user.getBackPack().inventory.Items.remove(items);
                            }
                        }
                        else {
                            user.getBackPack().inventory.Items.put(items,amount);
                        }
                        HashMap<String , Object> body = new HashMap<>();
                        body.put("Item" , message.getFromBody("Item"));
                        body.put("amount" , message.getIntFromBody("amount"));
                        sendMessage(new Message(CHANGE_INVENTORY , body));
                    }
                }
            }
            case BUY_ANIMAL -> {
                Marketing.getInstance().AnswerRequestForBuyAnimal(message , game);
            }
            case SELL_ANIMAL -> {
//                controller.AnswerRequestAnimal(message, game);
            }
            case FEED_HAY -> {
//                controller.AnswerFeedHay(message, game);
            }
            case SHEPHERD_ANIMAL -> {
                AnswerShepherding(message, game);
            }
            case PET -> {
                Animal animal = message.getFromBody("Animal");
                animal.increaseFriendShip(15);
                animal.setPetToday(true);
            }
            case COLLECT_PRODUCT -> {
                Animal animal = message.getFromBody("Animal");
                animal.setProductCollected(true);
                HashMap<String , Object> body = new HashMap<>();
                body.put("Item" , message.getIntFromBody("Product"));
                body.put("amount" , 1);
                sendMessage(new Message(CHANGE_INVENTORY , body));
            }
            case CHANGE_ABILITY_LEVEL ->  {
                int xp = Main.getClient().getPlayer().getFishingAbility();
                Main.getClient().getPlayer().increaseFishingAbility((int) (xp * 1.4));
            }
            case CHANGE_FRIDGE -> {
                Items items = message.getFromBody("Item");
                int amount = message.getIntFromBody("amount");
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
            case PASSED_TIME -> {
                // controller.
            }
        }
    }

    public synchronized void sendMessage(Message message) throws IOException {
        connection.sendTCP(message);
        System.out.println(JSONUtils.toJson(message));
    }
}
