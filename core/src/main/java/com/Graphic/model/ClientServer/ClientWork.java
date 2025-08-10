package com.Graphic.model.ClientServer;

import com.Graphic.Controller.MainGame.InputGameController;
import com.Graphic.Controller.MainGame.Marketing;
import com.Graphic.Main;
import com.Graphic.View.RegisterMenu;
import com.Graphic.model.Animall.Animal;
import com.Graphic.model.Animall.BarnOrCage;
import com.Graphic.model.Enum.Commands.CommandType;
import com.Graphic.model.Enum.ItemType.AnimalType;
import com.Graphic.model.Enum.ItemType.BackPackType;
import com.Graphic.model.Enum.ItemType.BarnORCageType;
import com.Graphic.model.Enum.ItemType.MarketType;
import com.Graphic.model.Enum.Menu;
import com.Graphic.model.Items;
import com.Graphic.model.MapThings.Tile;
import com.Graphic.model.Places.Farm;
import com.Graphic.model.User;
import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.google.gson.reflect.TypeToken;

import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static com.Graphic.Controller.MainGame.GameControllerLogic.getTileByCoordinates;
import static com.badlogic.gdx.Input.Keys.*;

public class ClientWork  {

    private ClientWorkController controller;

    private GameState localGameState;
    private volatile boolean running = false;
    private boolean exit = false;
    private boolean isWorkingWithOtherClient = false;
    private Client2ServerThread client2ServerThread;
    private BlockingQueue<Message> requests = new LinkedBlockingQueue();
    private User Player;
    private Menu currentMenu;
    private Client client;

    public ClientWork(String serverIp , int tcpPort , int udpPort) throws IOException {
        client = new Client(1024 * 1024 * 10 , 1024 * 1024 * 10);
        client.start();
        Network.register(client);
//        client.setKeepAliveTCP(4000);
//        client.setTimeout(30000);
        client.connect(5000 , serverIp , tcpPort , udpPort);

        client.addListener(new Listener() {

            public void connected(Connection connection) {
                System.out.println("Connected");
            }

            public void received(Connection connection, Object object) {
                if (object instanceof Message) {
                    handleMessage((Message) object);
                }
            }

        });
        controller = ClientWorkController.getInstance();

        new Thread(() -> {
            while (true) {
                try {
                        Message msg = requests.take();
                        sendMessage(msg);
                    }
                catch (InterruptedException e) {
                    //Thread.currentThread().interrupt();
                }
            }
        }).start();

//        new Thread(() -> {
//            try {
//                while (true) {
//                    connection.sendTCP(2);
//                    Thread.sleep(4000);
//                }
//            }
//            catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }).start();

    }

    public void handleMessage(Message message) {
        switch (message.getCommandType()) {
            case LOGIN_SUCCESS -> {
                System.out.println("Login success");
                Main.getClient().setCurrentMenu(Menu.PlayGameMenu);
                User user = message.getFromBody("Player");
                Main.getClient().setPlayer(user);
                break;
            }
            case GENERATE_RANDOM_PASS -> {
                Gdx.app.postRunnable(() -> {
                    RegisterMenu.getInstance().setPasswordField(message);
                });
                break;
            }
            case GAME_START -> {
                ArrayList<User> players = message.getFromBody("Players");
                Main.getClient().getLocalGameState().getPlayers().addAll(players);
                for (User user : players) {
                    if (user.getUsername().trim().equals(Main.getClient().getPlayer().getUsername().trim())) {
                        Main.getClient().setPlayer(user);
                    }
                }
                Main.getClient().setRunning(true);
                Main.getClient().setCurrentMenu(Menu.GameMenu);
                sendMessage(message);
                break;
            }
            case FARM -> {
                Farm farm = message.getFromBody("Farm");
                Main.getClient().getLocalGameState().getFarms().add(farm);
                User user = message.getFromBody("Player");
                int x = message.getFromBody("X");
                int y = message.getFromBody("Y");
                for (User player : Main.getClient().getLocalGameState().getPlayers()) {
                    if (player.getUsername().trim().equals(user.getUsername().trim())) {
                        System.out.println(player.getUsername());
                        player.topLeftX = x;
                        player.topLeftY = y;
//                        if (user.getFarm() == null) {
//                            System.out.println("farm is null");
//                        }
                        player.setFarm(user.getFarm());
                        break;
                    }
                }
            }
            case BIG_MAP -> {
                ArrayList<Tile> bigMap = message.getFromBody("BigMap");
                Main.getClient().getLocalGameState().bigMap.addAll(bigMap);
                Main.getClient().getLocalGameState().setChooseMap(true);
            }
            case ERROR -> {
                Gdx.app.postRunnable(() -> {
                    RegisterMenu.getInstance().showMessage(message);
                });
            }
            case CAN_MOVE -> {
                InputGameController.getInstance().Move(message , Main.getClient().getLocalGameState());
            }
            case CAN_NOT_MOVE -> {
                InputGameController.getInstance().Move(message , Main.getClient().getLocalGameState());
            }
            case ENTER_THE_MARKET -> {
                for (User player : Main.getClient().getLocalGameState().getPlayers()) {
                    if (message.getFromBody("Player").equals(player)) {
                        player.setPositionX(message.getFromBody("X"));
                        player.setPositionY(message.getFromBody("Y"));
                        player.setInFarmExterior(message.getFromBody("Is in farm"));
                        player.setIsInMarket(message.getFromBody("Is in market"));
                        if (Main.getClient().getPlayer().equals(player)) {
                            Main.getClient().getLocalGameState().currentMenu = Menu.MarketMenu;
                        }
                        player.getJoinMarket().add(player);
                    }
                }
            }
            case CHANGE_MONEY -> {
                User user = message.getFromBody("Player");
                if (Main.getClient().getPlayer().getUsername().trim().equals(user.getUsername().trim())) {
                    Main.getClient().getPlayer().increaseMoney(message.getIntFromBody("Money"));
                }
                else if (Main.getClient().getPlayer().getUsername().trim().equals(user.getSpouse().getUsername().trim())) {
                    Main.getClient().getPlayer().increaseMoney(message.getIntFromBody("Money"));
                }
            }
            case CHANGE_INVENTORY -> {
                Items items = message.getFromBody("Item");
                int amount = message.getIntFromBody("amount");
                if (Main.getClient().getPlayer().getBackPack().inventory.Items.containsKey(items)) {
                    Main.getClient().getPlayer().getBackPack().inventory.Items.compute(items,(k,v) -> v + amount);
                    if (Main.getClient().getPlayer().getBackPack().inventory.Items.get(items) == 0) {
                        Main.getClient().getPlayer().getBackPack().inventory.Items.remove(items);
                    }
                }
                else {
                    Main.getClient().getPlayer().getBackPack().inventory.Items.put(items,amount);
                }
            }
            case REDUCE_PRODUCT -> {
                Items items = message.getFromBody("Item");
                MarketType marketType = message.getFromBody("Market");
                items.setRemindInShop(items.getRemindInShop(marketType) - 1 , marketType);
            }
            case BUY_BACKPACK -> {
                BackPackType backPackType = message.getFromBody("BackPack");
                Main.getClient().getPlayer().getBackPack().setType(backPackType);
            }
            case REDUCE_BARN_CAGE -> {
                BarnORCageType barnORCageType = message.getFromBody("BarnORCageType");
                barnORCageType.setShopLimit(0);
            }
            case PLACE_CRAFT_SHIPPING_BIN -> {
                Items items = message.getFromBody("Item");
                int x = message.getIntFromBody("X");
                int y = message.getIntFromBody("Y");
                getTileByCoordinates(x , y , Main.getClient().getLocalGameState()).setGameObject(items);
            }
            case REDUCE_ANIMAL -> {
                AnimalType animalType = message.getFromBody("AnimalType");
                animalType.increaseRemindInShop(-1);
            }
            case BUY_ANIMAL -> {
                Marketing.getInstance().receiveRequestBuyAnimal(message);
            }
            case SELL_ANIMAL -> {
                InputGameController.getInstance().receiveRequestForSellAnimal(message);
            }
            case FEED_HAY -> {
                InputGameController.getInstance().receiveFeedHay(message);
            }
            case SHEPHERD_ANIMAL -> {
                Animal animal = message.getFromBody("Animal");
                animal.setOut(true);
                Main.getClient().getLocalGameState().getAnimals().add(animal);
            }
            case PLACE_BARN_CAGE -> {
                User user = message.getFromBody("Player");
                BarnOrCage barnOrCage = message.getFromBody("BarnOrCage");
                int x = message.getIntFromBody("X");
                int y = message.getIntFromBody("Y");
                InputGameController.getInstance().placeBarnOrCage(x, y, barnOrCage , user);
            }
            case FriendshipsInqResponse -> {
                Main.getClient().getLocalGameState().friendships = message.getFromBody("friendships");
            }
            case PASSED_TIME -> {
                controller.PassedTime(message.getIntFromBody("Hour"), message.getIntFromBody("Day"));
            }
            case CHANGE_GAME_OBJECT -> {

            }
            case FriendshipsInqResponse, UPDATE_FRIENDSHIPS -> {
                Main.getClient().getLocalGameState().friendships = message.getFromBody("friendships");
            }
            case PASSED_TIME -> {
                controller.PassedTime(message.getIntFromBody("Hour"), message.getIntFromBody("Day"));
            }
            case A_FRIEND_IS_CLOSE -> {
                User friend = message.getFromBody("friend");
                Main.getClient().getPlayer().setFriendCloseToMe(friend);
            }
            case  UPDATE_CONVERSATIONS -> {
                Main.getClient().getLocalGameState().conversations =  message.getFromBody("conversations");
                // print unseen messages
                Main.getClient().getPlayer().setShowUnseenChats(true);
            }
        }
        }
    }

    public void sendMessage(Message message) {
        client.sendTCP(message);

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
