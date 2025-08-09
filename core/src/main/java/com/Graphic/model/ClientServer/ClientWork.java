package com.Graphic.model.ClientServer;

import com.Graphic.Controller.MainGame.Marketing;
import com.Graphic.Main;
import com.Graphic.model.Animall.Animal;
import com.Graphic.model.Enum.Menu;
import com.Graphic.model.Items;
import com.Graphic.model.User;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static com.Graphic.Controller.MainGame.GameControllerLogic.*;
import static com.Graphic.Controller.MainGame.GameControllerLogic.AnswerShepherding;
import static com.Graphic.model.ClientServer.ClientConnectionController.sendToAll;
import static com.Graphic.model.Enum.Commands.CommandType.CHANGE_INVENTORY;

public class ClientWork  {

    private GameState localGameState;
    private volatile boolean running = false;
    private boolean exit = false;
    private boolean isWorkingWithOtherClient = false;
    private Client2ServerThread client2ServerThread;
    private BlockingQueue<Message> requests = new LinkedBlockingQueue();
    private User Player;
    private Menu currentMenu;
    private com.esotericsoftware.kryonet.Client client;
    private Connection connection;

    public ClientWork(String serverIp , int tcpPort) throws IOException {
        client = new Client();
        Network.register(client);
        client.start();

        client.addListener(new Listener() {

            public void connected(Connection connection) {
                ClientWork.this.connection = connection;
            }

            public void disconnected(Connection connection) {
                ClientWork.this.connection = null;
            }

            public void received(Connection connection, Object object) {
                if (object instanceof Message) {
                    handleMessage((Message) object);
                }
            }
        });

        client.connect(5000 , serverIp , tcpPort);

    }

    public void handleMessage(Message message) {
        switch (message.getCommandType()) {
            case FARM -> {
                game.getDiffQueue().add(createInitialFarm(message.getIntFromBody("Index") , Player , game.getGameState()));
                localGameState.incrementNumberOfMaps();
                if (localGameState.getNumberOfMaps() == 4) {
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
            case PLACE_CRAFT_SHIPPING_BIN -> {
                controller.AnswerPlaceCraft(message, game);
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
                controller.AnswerRequestAnimal(message, game);
            }
            case FEED_HAY -> {
                controller.AnswerFeedHay(message, game);
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

    public void sendMessage(Message message) {

    }



    public boolean isExit() {
        return exit;
    }

    public boolean isWorkingWithOtherClient() {
        return isWorkingWithOtherClient;
    }


    public synchronized BlockingQueue<Message> getRequests() {
        return requests;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public User getPlayer() {
        return Player;
    }

    public void setPlayer(User player) {
        this.Player = player;
    }

    public GameState getLocalGameState() {
        if (localGameState == null) {
            localGameState = new GameState();
        }
        return localGameState;
    }

    public Menu getCurrentMenu() {
        return currentMenu;
    }
    public void setCurrentMenu(Menu currentMenu) {
        this.currentMenu = currentMenu;
    }

}
