package com.Graphic.model.ClientServer;

import com.Graphic.Controller.MainGame.InputGameController;
import com.Graphic.Controller.MainGame.Marketing;
import com.Graphic.Main;
import com.Graphic.View.PlayGameMenu;
import com.Graphic.View.RegisterMenu;
import com.Graphic.model.Animall.Animal;
import com.Graphic.model.Animall.BarnOrCage;
import com.Graphic.model.Enum.ItemType.AnimalType;
import com.Graphic.model.Enum.ItemType.BackPackType;
import com.Graphic.model.Enum.ItemType.BarnORCageType;
import com.Graphic.model.Enum.ItemType.MarketType;
import com.Graphic.model.Enum.Menu;
import com.Graphic.model.Items;
import com.Graphic.model.User;
import com.badlogic.gdx.Gdx;
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
import static com.Graphic.model.Enum.Commands.CommandType.*;

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
        controller = ClientWorkController.getInstance();
    }

    public void handleMessage(Message message) {
        switch (message.getCommandType()) {
            case LOGIN_SUCCESS -> {
                System.out.println("Login success");
                HashMap<String,Object> body = new HashMap<>();
                body.put("null","null");
                sendMessage(new Message(LOGGED_IN , body));
                Main.getMain().setScreen(new PlayGameMenu());
                Main.getClient().setPlayer(message.getFromBody("Player"));
                break;
            }
            case GENERATE_RANDOM_PASS -> {
                Gdx.app.postRunnable(() -> {
                    RegisterMenu.getInstance().setPasswordField(message);
                });
                break;
            }
            case GAME_START -> {
                Main.getClient().getLocalGameState().getPlayers().addAll(message.getFromBody("Players"));
                Main.getClient().setRunning(true);
                sendMessage(message);
                break;
            }
            case FARM -> {
                Main.getClient().getLocalGameState().getFarms().add(message.getFromBody("Farm"));
            }
            case BIG_MAP -> {
                Main.getClient().getLocalGameState().bigMap.addAll(message.getFromBody("BigMap"));
                Main.getClient().getLocalGameState().setChooseMap(true);
            }
            case ERROR -> {
                Gdx.app.postRunnable(() -> {
                    RegisterMenu.getInstance().showMessage(message);
                });
            }
            case CAN_MOVE -> {}
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
            case A_FRIEND_IS_CLOSE -> {
                User friend = message.getFromBody("friend");
                Main.getClient().getPlayer().setFriendCloseToMe(friend);
            }
        }
    }
    public void sendMessage(Message message) {connection.sendTCP(message);}

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
