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

import static com.Graphic.model.App.currentGame;

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
                                        // Mamal
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
            case CREATE_LOBBY -> {
                controller.createLobby(message);
            }
            case LIST_GAMES -> {
                sendMessage(controller.AnswerListGames());
            }
            case LEAVE_LOBBY -> {
                sendMessage(controller.AnswerLeaveLobby(message));
            }
            case JOIN_LOBBY -> {
                sendMessage(controller.AnswerJoinLobby(message , connection));
            }
            case FIND_INVISIBLE_LOBBY -> {
                sendMessage(controller.AnswerInvisibleLobby(message));
            }
            case START_LOBBY -> {
                sendMessage(controller.AnswerStartLobby(message));
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
                User player1 = message.getFromBody("Player");

                User player = null;
                for (User user: game.getGameState().getPlayers()) {
                    if (user.getUsername().equals(player1.getUsername())) {
                        player = user;
                    }
                }
                if (player == null) {
                    return;
                }

                String ability = message.getFromBody("Ability");
                int amount = message.getFromBody("amount");


                if (ability.equals("Fishing")) {
                    player.increaseFishingAbility(amount);
                }
                if (ability.equals("Foraging")) {
                    player.increaseForagingAbility(amount);
                }
                if (ability.equals("Farming")) {
                    player.increaseFarmingAbility(amount);
                }
                if (ability.equals("Mining")) {
                    player.increaseMiningAbility(amount);
                }

                HashMap<String, Object> body = new HashMap<>();
                body.put("Fishing", player.getFishingAbility());
                body.put("Foraging", player.getForagingAbility());
                body.put("Mining", player.getMiningAbility());
                body.put("Farming", player.getFarmingAbility());
                controller.sendToOnePerson(new Message(CommandType.CHANGE_ABILITY_LEVEL, body), game, player);
            }
            case CHANGE_INVENTORY -> {
                Message message1 = controller.changeInventory(message, game);
                User user = message1.getFromBody("Player");
                controller.sendToOnePerson(message1, game, user);
            }
            case CHANGE_FRIDGE -> {
                User player = message.getFromBody("Player");
                Items items = message.getFromBody("Item");
                int amount = message.getFromBody("amount");
                for (User p: game.getGameState().getPlayers()) {
                    if (p.getUsername().equals(player.getUsername())) {
                        if (p.getFarm().getHome().getFridge().items.containsKey(items)) {
                            p.getFarm().getHome().getFridge().items.compute(items,(k,v) -> v + amount);
                            if (p.getFarm().getHome().getFridge().items.get(items) == 0) {
                                p.getFarm().getHome().getFridge().items.remove(items);
                            }
                        }
                        else {
                            p.getFarm().getHome().getFridge().items.put(items,amount);
                        }
                    }
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
            case SEND_GIFT -> {
                String player1 = message.getFromBody("Giver");
                String player2 = message.getFromBody("Given");
                User giver = null;
                User given = null;
                for (User user: game.getGameState().getPlayers()) {
                    if (user.getUsername().equals(player1)) {
                        giver = user;
                    }
                    if (user.getUsername().equals(player2)) {
                        given = user;
                    }
                }
                if (giver == null || given == null) {
                    return;
                }

                Items item =  message.getFromBody("Item");


                if (giver.getBackPack().inventory.Items.containsKey(item)) {
                    giver.getBackPack().inventory.Items.compute(item,(k,v) -> v - 1);
                }
                else return;
                if (given.getBackPack().inventory.Items.containsKey(item)) {
                    given.getBackPack().inventory.Items.compute(item,(k,v) -> v + 1);
                }
                else {
                    given.getBackPack().inventory.Items.put(item,1);
                }

                HashMap<String, Object> body = new HashMap<>();
                body.put("Giver", giver);
                body.put("Given", given);
                body.put("Item", item);
                controller.sendToOnePerson(new Message(CommandType.SEND_GIFT, body), game, given);


                HashMap<String, Object> body3 = new HashMap<>();
                body3.put("Player", giver);
                body3.put("Item", item);
                body3.put("amount", -1);
                controller.sendToOnePerson(new Message(CommandType.CHANGE_INVENTORY, body3), game, giver);

                HashMap<String, Object> body4 = new HashMap<>();
                body4.put("Player", given);
                body4.put("Item", item);
                body4.put("amount", 1);
                controller.sendToOnePerson(new Message(CommandType.CHANGE_INVENTORY, body4), game, given);
            }
            case EXIT_MARKET -> {
                System.out.println("Exit");
                controller.ExitTheMarket(message , game);
            }
            case LOADED_GAME -> {}

                                        // Erfan
            case GET_TOMORROW_WEATHER -> {
                controller.sentWeather(game);
            }
            case SET_TIME -> {
                controller.passedOfTime(
                    message.getFromBody("Day"),
                    message.getFromBody("Hour"),
                    game.getGameState().currentDate, game
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
            case CURRENT_ITEM -> {
                User player = message.getFromBody("Player");
                Items items = message.getFromBody("Item");
                controller.sendToOnePerson(controller.changeCurrentItem(player, items, game), game, player);
            }

                                        // Ario
            case FriendshipsInquiry -> {

                if (game.getGameState().friendships.isEmpty()) {
                    for (int i = 0; i < game.getGameState().getPlayers().size(); i++)
                        for (int j = i + 1; j < game.getGameState().getPlayers().size(); j++) {
                            HumanCommunications f = new HumanCommunications(
                                game.getGameState().getPlayers().get(i),
                                game.getGameState().getPlayers().get(j));

                            game.getGameState().friendships.add(f);
                        }
                }
                HashMap<String , Object> body = new HashMap<>();
                body.put("friendships", game.getGameState().friendships);

                if (game.getGameState().friendships.isEmpty()) {
                    for (int i = 0; i < 500; i++)
                        System.out.println("server bega raft");
                }

                controller.sendToAll(new Message(CommandType.UPDATE_FRIENDSHIPS, body), game);
            }
            case ADD_XP_TO_FRIENDSHIP -> {
                String player = message.getFromBody("Player");
                String friend =  message.getFromBody("Friend");
                User p1 = null;
                User p2 = null;
                for (User user: game.getGameState().getPlayers()) {
                    if (user.getUsername().equals(player)) {
                        p1 = user;
                    }
                    if (user.getUsername().equals(friend)) {
                        p2 = user;
                    }
                }
                if (p1 == null || p2 == null) {
                    return;
                }

                int xp = message.getFromBody("XP");

                for (HumanCommunications f : game.getGameState().friendships) {
                    if (f.isBetween(p1, p2)) {
                        f.addXP(xp);
                    }
                }

                HashMap<String, Object> body = new HashMap<>();
                body.put("friendships", game.getGameState().friendships);
                controller.sendToAll(new Message(CommandType.UPDATE_FRIENDSHIPS, body), game);
            }



        }
    }

    public synchronized void sendMessage(Message message) throws IOException {
        connection.sendTCP(message);
        //System.out.println(JSONUtils.toJson(message));
    }
}
