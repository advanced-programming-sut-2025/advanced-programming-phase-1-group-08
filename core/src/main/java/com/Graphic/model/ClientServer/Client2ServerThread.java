package com.Graphic.model.ClientServer;

import com.Graphic.Controller.MainGame.GameControllerLogic;
import com.Graphic.Controller.MainGame.InputGameController;
import com.Graphic.Controller.MainGame.Marketing;
import com.Graphic.Main;
import com.Graphic.View.GameMenus.MarketMenu;
import com.Graphic.View.LoginMenu;
import com.Graphic.View.MainMenu;
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
import com.Graphic.model.MapThings.Tile;
import com.Graphic.model.Places.Farm;
import com.Graphic.model.User;
import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

//import static com.Graphic.Controller.MainGame.GameControllerLogic.getTileByCoordinates;
import static com.Graphic.Controller.MainGame.GameControllerLogic.getTileByCoordinates;
import static com.Graphic.model.Enum.Commands.CommandType.*;

public class Client2ServerThread extends Thread{

//    private Socket socket;
//    private DataOutputStream out;
//    private DataInputStream in;
//    private String update;
//    Gson gson = new Gson();
//    boolean isSmall = false;
//
//    public Client2ServerThread(Socket socket) throws IOException {
//        this.socket = socket;
//        out = new DataOutputStream(socket.getOutputStream());
//        in = new DataInputStream(socket.getInputStream());
//    }
//
//
//    @Override
//    public void run() {
//        String line = null;
//        Message message = null;
//        HashMap<String,Object> body = new HashMap<>();
//        body.put("null","null");
//        Message diff = new Message(GET_DIFF, body);
//        update = JSONUtils.toJson(diff);
//        while (true) {
//            try {
//                if (! Main.getClient().getRequests().isEmpty()) {
//                    message = Main.getClient().getRequests().poll();
//                    sendMessage(message);
//                    if (! isPublic(message) || message.getCommandType() == NEW_GAME || message.getCommandType() == JOIN_GAME) {
//                        line = Read(line);
//                    }
//                }
//                if (Main.getClient().isRunning()) {
//                    read(diff , line);
//                }
//
//                Thread.sleep(100);
//            }
//            catch (Exception e) {
//                e.printStackTrace();
//                break;
//            }
//        }
//    }
//
//    private String Read(String line) throws Exception {
//        isSmall = in.readBoolean();
//        if (!isSmall) {
//            int length = in.readInt();
//            byte[] data = new byte[length];
//            in.readFully(data);
//            String json = new String(data, StandardCharsets.UTF_8);
//            handleMessage(JSONUtils.fromJson(json));
//        } else {
//            line = in.readUTF();
//            handleMessage(JSONUtils.fromJson(line));
//        }
//        return line;
//    }
//
//    private void read(Message diff , String line) throws Exception {
//        sendMessage(diff);
//        Read(line);
//    }
//
//    public void handleMessage(Message message) throws Exception {
//        switch (message.getCommandType()) {
//            case LOGIN_SUCCESS -> {
//                System.out.println("Login success");
//                Main.getClient().setCurrentMenu(Menu.PlayGameMenu);
//                User user = gson.fromJson(gson.toJson(message.getBody().get("Player")), User.class);
//                Main.getClient().setPlayer(user);
//                break;
//            }
//            case GENERATE_RANDOM_PASS -> {
//                Gdx.app.postRunnable(() -> {
//                    RegisterMenu.getInstance().setPasswordField(message);
//                });
//                break;
//            }
//            case GAME_START -> {
//                Type userListType = new TypeToken<ArrayList<User>>() {}.getType();
//
//                ArrayList<User> players = gson.fromJson(
//                    gson.toJson(message.getBody().get("Players")),
//                    userListType
//                );
//                Main.getClient().getLocalGameState().getPlayers().addAll(players);
//                for (User user : players) {
//                    if (user.getUsername().trim().equals(Main.getClient().getPlayer().getUsername().trim())) {
//                        Main.getClient().setPlayer(user);
//                    }
//                }
//                Main.getClient().setRunning(true);
//                Main.getClient().setCurrentMenu(Menu.GameMenu);
//                sendMessage(message);
//                break;
//            }
//            case FARM -> {
//                System.out.println("1- "+Main.getClient().getLocalGameState().getPlayers().size());
//                Farm farm = gson.fromJson(gson.toJson(message.getBody().get("Farm")), Farm.class);
//                Main.getClient().getLocalGameState().getFarms().add(farm);
//                User user = gson.fromJson(gson.toJson(message.getBody().get("Player")), User.class);
//                int x = gson.fromJson(gson.toJson(message.getBody().get("X")), int.class);
//                int y = gson.fromJson(gson.toJson(message.getBody().get("Y")), int.class);
//                System.out.println("2- "+Main.getClient().getLocalGameState().getPlayers().size());
//                for (User player : Main.getClient().getLocalGameState().getPlayers()) {
//                    if (player.getUsername().trim().equals(user.getUsername().trim())) {
//                        System.out.println(player.getUsername());
//                        player.topLeftX = x;
//                        player.topLeftY = y;
////                        if (user.getFarm() == null) {
////                            System.out.println("farm is null");
////                        }
//                        player.setFarm(user.getFarm());
//                        break;
//                    }
//                }
//                System.out.println(Main.getClient().getLocalGameState().getPlayers().size());
//            }
//            case BIG_MAP -> {
//                System.out.println("bigmap1");
//                Type tileListType = new TypeToken<ArrayList<Tile>>() {}.getType();
//
//                ArrayList<Tile> bigMap = gson.fromJson(
//                    gson.toJson(message.getBody().get("BigMap")),
//                    tileListType
//                );
//                System.out.println("bigmap2");
//                Main.getClient().getLocalGameState().bigMap.addAll(bigMap);
//                Main.getClient().getLocalGameState().setChooseMap(true);
//                System.out.println("bigmap3");
//            }
//            case ERROR -> {
//                Gdx.app.postRunnable(() -> {
//                    RegisterMenu.getInstance().showMessage(message);
//                });
//            }
//            case CAN_MOVE -> {
//                InputGameController.getInstance().Move(message , Main.getClient().getLocalGameState());
//            }
//            case CAN_NOT_MOVE -> {
//                InputGameController.getInstance().Move(message , Main.getClient().getLocalGameState());
//            }
//            case ENTER_THE_MARKET -> {
//                for (User player : Main.getClient().getLocalGameState().getPlayers()) {
//                    if (message.getFromBody("Player").equals(player)) {
//                        player.setPositionX(message.getFromBody("X"));
//                        player.setPositionY(message.getFromBody("Y"));
//                        player.setInFarmExterior(message.getFromBody("Is in farm"));
//                        player.setIsInMarket(message.getFromBody("Is in market"));
//                        if (Main.getClient().getPlayer().equals(player)) {
//                            Main.getClient().getLocalGameState().currentMenu = Menu.MarketMenu;
//                        }
//                        player.getJoinMarket().add(player);
//                    }
//                }
//            }
//            case CHANGE_MONEY -> {
//                User user = message.getFromBody("Player");
//                if (Main.getClient().getPlayer().getUsername().trim().equals(user.getUsername().trim())) {
//                    Main.getClient().getPlayer().increaseMoney(message.getIntFromBody("Money"));
//                }
//                else if (Main.getClient().getPlayer().getUsername().trim().equals(user.getSpouse().getUsername().trim())) {
//                    Main.getClient().getPlayer().increaseMoney(message.getIntFromBody("Money"));
//                }
//            }
//            case CHANGE_INVENTORY -> {
//                Items items = message.getFromBody("Item");
//                int amount = message.getIntFromBody("amount");
//                if (Main.getClient().getPlayer().getBackPack().inventory.Items.containsKey(items)) {
//                    Main.getClient().getPlayer().getBackPack().inventory.Items.compute(items,(k,v) -> v + amount);
//                    if (Main.getClient().getPlayer().getBackPack().inventory.Items.get(items) == 0) {
//                        Main.getClient().getPlayer().getBackPack().inventory.Items.remove(items);
//                    }
//                }
//                else {
//                    Main.getClient().getPlayer().getBackPack().inventory.Items.put(items,amount);
//                }
//            }
//            case REDUCE_PRODUCT -> {
//                Items items = message.getFromBody("Item");
//                MarketType marketType = message.getFromBody("Market");
//                items.setRemindInShop(items.getRemindInShop(marketType) - 1 , marketType);
//            }
//            case BUY_BACKPACK -> {
//                BackPackType backPackType = message.getFromBody("BackPack");
//                Main.getClient().getPlayer().getBackPack().setType(backPackType);
//            }
//            case REDUCE_BARN_CAGE -> {
//                BarnORCageType barnORCageType = message.getFromBody("BarnORCageType");
//                barnORCageType.setShopLimit(0);
//            }
//            case PLACE_CRAFT_SHIPPING_BIN -> {
//                Items items = message.getFromBody("Item");
//                int x = message.getIntFromBody("X");
//                int y = message.getIntFromBody("Y");
//                getTileByCoordinates(x , y , Main.getClient().getLocalGameState()).setGameObject(items);
//            }
//            case REDUCE_ANIMAL -> {
//                AnimalType animalType = message.getFromBody("AnimalType");
//                animalType.increaseRemindInShop(-1);
//            }
//            case BUY_ANIMAL -> {
//                Marketing.getInstance().receiveRequestBuyAnimal(message);
//            }
//            case SELL_ANIMAL -> {
//                InputGameController.getInstance().receiveRequestForSellAnimal(message);
//            }
//            case FEED_HAY -> {
//                InputGameController.getInstance().receiveFeedHay(message);
//            }
//            case SHEPHERD_ANIMAL -> {
//                Animal animal = message.getFromBody("Animal");
//                animal.setOut(true);
//                Main.getClient().getLocalGameState().getAnimals().add(animal);
//            }
//            case PLACE_BARN_CAGE -> {
//                User user = message.getFromBody("Player");
//                BarnOrCage barnOrCage = message.getFromBody("BarnOrCage");
//                int x = message.getIntFromBody("X");
//                int y = message.getIntFromBody("Y");
//                InputGameController.getInstance().placeBarnOrCage(x, y, barnOrCage , user);
//            }
//        }
//    }
//
//    public void sendMessage(Message message) throws Exception {
//        if (message.getCommandType() == GET_DIFF) {
//            out.writeUTF(update);
//            out.flush();
//        }
//        else {
//            out.writeUTF(JSONUtils.toJson(message));
//            out.flush();
//        }
//    }
//
//    public boolean isPublic(Message message) {
//        if (message.getCommandType() == FARM || message.getCommandType() == BIG_MAP) {
//            return true;
//        }
//
//        if (message.getCommandType() == MOVE_IN_FARM) {
//            return true;
//        }
//        return false;
//    }
}
