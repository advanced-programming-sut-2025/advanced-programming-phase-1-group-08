package com.Graphic.model.ClientServer;

import com.Graphic.Controller.MainGame.InputGameController;
import com.Graphic.Main;
import com.Graphic.View.GameMenus.MarketMenu;
import com.Graphic.model.*;
import com.Graphic.model.Animall.Animal;
import com.Graphic.model.Animall.BarnOrCage;
import com.Graphic.model.Enum.Commands.CommandType;
import com.Graphic.model.Enum.ItemType.BarnORCageType;
import com.Graphic.model.Enum.ItemType.MarketType;
import com.Graphic.model.MapThings.GameObject;
import com.Graphic.model.MapThings.Tile;
import com.Graphic.model.Places.MarketItem;
import com.Graphic.model.Places.ShippingBin;
import com.Graphic.model.Plants.BasicRock;
import com.Graphic.model.Plants.Wood;
import com.Graphic.model.Weather.DateHour;
import com.esotericsoftware.kryonet.Connection;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.Graphic.Controller.MainGame.GameControllerLogic.*;
import static com.Graphic.model.Enum.Commands.CommandType.REDUCE_BARN_CAGE;
import static com.Graphic.model.Enum.Commands.CommandType.*;
import static com.Graphic.model.HelpersClass.TextureManager.TEXTURE_SIZE;
import static com.Graphic.model.Weather.DateHour.getDayDifferent;
import static com.badlogic.gdx.math.MathUtils.random;

public class ClientConnectionController {

    private static ClientConnectionController instance;

    public void createLobby(Message message) {
        String name = message.getFromBody("name");
        String password = message.getFromBody("password");
        boolean visible = message.getFromBody("visible");
        User Creator = message.getFromBody("Creator");
        String Id = createId();
        boolean isPrivate = password != null;

        Game game = new Game(name , Id , password , isPrivate , visible , Creator);
        App.gamesActive.add(game);
        for (int i = App.gamesActive.size() - 1 ; i >= 0 ; i --) {
            System.out.println(App.gamesActive.get(i).getId());
        }
    }

    public String createId() {
        StringBuilder Id = new StringBuilder();
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        while (true) {
            for (int i = 0 ; i < 10 ; i ++) {
                Id.append(chars.charAt(random.nextInt(chars.length())));

            }
            if (checkId(Id.toString())) {
                break;
            }

        }

        System.out.println(Id.toString());

        return Id.toString();
    }

    public boolean checkId(String Id) {
        for (Game game : App.gamesActive) {
            if (game.getId().equals(Id)) {
                return false;
            }
        }
        return true;
    }

    public Message AnswerListGames() {
        ArrayList<Game> games = new ArrayList<>();
        for (int i = App.gamesActive.size() - 1 ; i >= 0 ; i --) {
            if (App.gamesActive.get(i).isVisible()) {
                games.add(App.gamesActive.get(i));
            }
        }
        HashMap<String , Object> gamesActive = new HashMap<>();
        gamesActive.put("games", games);

        return new Message(LIST_GAMES , gamesActive);
    }

    public Message AnswerLeaveLobby(Message message) {
        User Player = message.getFromBody("Player");
        Game game = null;
        boolean exists = false;
        HashMap<String , Object> leave = new HashMap<>();

        for (Game g : App.gamesActive) {
            if (g.getId().equals(message.getFromBody("Game"))) {
                game = g;
            }
        }

        if (game == null) {
            leave.put("Message" , "game is no longer exist. please refresh");
            return new Message(LEAVE_LOBBY , leave);
        }

        for (User user : game.getPlayers()) {
            if (user.getUsername().equals(Player.getUsername())) {
                game.getPlayers().remove(user);
                exists = true;
                break;
            }
        }

        if (! exists) {
            leave.put("Message" , "You Already lived this game");
            return new Message(LEAVE_LOBBY , leave);
        }

        if (game.getPlayers().isEmpty()) {
            App.gamesActive.remove(game);
        }
        else {
            game.setCreator(game.getPlayers().get(0));
        }

        for (Map.Entry<User , Connection> entry : game.connections.entrySet()) {
            if (entry.getKey().getUsername().equals(Player.getUsername())) {
                game.connections.remove(entry.getKey());
                break;
            }
        }

        leave.put("Message" , "You Successfully Leaved the Game");
        return new Message(LEAVE_LOBBY , leave);
    }

    public Message AnswerJoinLobby(Message message , Connection connection) {
        User Player = message.getFromBody("Player");
        Game game = null;
        HashMap<String , Object> join = new HashMap<>();

        for (Game g : App.gamesActive) {
            if (g.getId().equals(message.getFromBody("Game"))) {
                if (g.getPlayers().size() == 4) {
                    join.put("Message" , "Game is full");
                    return new Message(JOIN_LOBBY , join);
                }
                game = g;
            }
        }

        if (game == null) {
            join.put("Message" , "Game is no longer exist. please refresh");
            return new Message(JOIN_LOBBY , join);
        }

        if (game.isPrivate()) {
            String pass = message.getFromBody("Pass");
            if (! pass.equals(game.getPassword())) {
                join.put("Message" , "Wrong password");
                return new Message(JOIN_LOBBY , join);
            }
        }

        for (User user : game.getPlayers()) {
            if (user.getUsername().equals(Player.getUsername())) {
                join.put("Message" , "You are already in a game");
                return new Message(JOIN_LOBBY , join);
            }
        }

        game.getPlayers().add(Player);
        game.connections.put(Player , connection );
        join.put("Message" , "You Successfully Joined the Game");
        return new Message(JOIN_LOBBY , join);
    }

    public Message AnswerStartLobby(Message message) throws IOException {
        User Player = message.getFromBody("Player");
        Game game = null;
        boolean exists = false;
        HashMap<String , Object> startGame = new HashMap<>();

        for (Game g : App.gamesActive) {
            if (g.getId().equals(message.getFromBody("Game"))) {
                game = g;
                for (User user : g.getPlayers()) {
                    if (user.getUsername().equals(Player.getUsername())) {
                        exists = true;
                    }
                }
            }
        }
        if (game == null) {
            startGame.put("Message" , "game is no longer exist. please refresh");
            return new Message(PLAYER_NOT_IN_LOBBY , startGame);
        }

        if (! exists) {
            startGame.put("Message" , "You Already lived this game");
            return new Message(PLAYER_NOT_IN_LOBBY , startGame);
        }
        if (game.getPlayers().size() != 4) {
            startGame.put("Message" , "Game needs more player");
            return new Message(PLAYER_NOT_IN_LOBBY , startGame);
        }

        startGame.put("Players" , game.getPlayers());
        sendToAllExceptOnePerson(new Message(START_LOBBY , startGame) , game , Player);

        ServerHandler serverHandler = ServerHandler.getInstance(game);
        serverHandler.start();

        return new Message(START_LOBBY , startGame);
    }

    public Message AnswerInvisibleLobby(Message message) {
        String id = message.getFromBody("Id");
        for (Game g : App.gamesActive) {
            if (g.getId().equals(id)) {
                HashMap<String , Object> invisible = new HashMap<>();
                invisible.put("Game" , g);
                return new Message(FIND_INVISIBLE_LOBBY , invisible);
            }
        }
        HashMap<String , Object> invisible = new HashMap<>();
        invisible.put("Game" , null);
        invisible.put("Message" , "There is no game with this id");
        return new Message(FIND_INVISIBLE_LOBBY , invisible);
    }

    public void createFarm(Message message , Game game) throws IOException {
        int index = message.getFromBody("Index");
        User user = message.getFromBody("Player");
        game.getGameState().incrementNumberOfMaps();

        for (User player : game.getGameState().getPlayers()) {
            if (player.getUsername().equals(user.getUsername())) {

                int topLeftX = 0;
                int topLeftY = 0;
                if (game.getGameState().getNumberOfMaps() % 2 == 0) {
                    topLeftX = 1;
                }
                if (game.getGameState().getNumberOfMaps() >= 3) {
                    topLeftY = 1;
                }
                player.topLeftX = topLeftX;
                player.topLeftY = topLeftY;
                Message result = createInitialFarm(index, player, game.getGameState());
                sendToAll(result, game);
            }
        }

        if (game.getGameState().getNumberOfMaps() == 4) {
            sendToAll(build(game.getGameState()) , game);
            for (Tile tile : game.getGameState().bigMap) {
                System.out.println("x: " + tile.getX() + "y: " + tile.getY());
            }
        }
    }

    public Game newGame(Message message , Connection connection) throws IOException {
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
        return App.games.get(id);
    }

    public Game joinGame(Message message , Connection connection) throws IOException {
        String id = message.getFromBody("id");
        User player = message.getFromBody("Player");
        for (Map.Entry<String , Game> entry : App.games.entrySet()) {
            if (id.equals(entry.getKey())) {
                if (entry.getValue().getGameState().getPlayers().size() != 4) {
                    entry.getValue().addPlayer(player , connection);
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    public void moveInFarm(Message message , Game game) throws IOException {
        Message body = InputGameController.getInstance().checkWalking(message, game);
        sendToAll(body , game);
    }

    public void answerEnterTheMarket(Message message , Game game) throws IOException {
        User player = message.getFromBody("Player");
        for (User user : game.getGameState().getPlayers()) {
            if (user.getUsername().trim().equals(player.getUsername().trim())) {
                user.setIsInMarket(true);
                user.setInFarmExterior(false);
                MarketType market = message.getFromBody("Market");
                System.out.println(market.getName());
                HashMap<String , Object> body = new HashMap<>();
                body.put("Player", user);
                body.put("Market", market);
                body.put("X" , market.getInsideDoor().getX());
                body.put("Y" , market.getInsideDoor().getY());
                body.put("Is in farm" , false);
                body.put("Is in market" , true);
                sendToAll(new Message(CommandType.ENTER_THE_MARKET , body) , game);
            }
        }
    }

    public void moveInMarket(Message message , Game game) throws IOException {
        HashMap<String , Object> body = new HashMap<>();
        MarketType marketType = message.getFromBody("Market");
        for (collisionRect rect : marketType.getRects()) {
            if (! rect.checkCollision(message.getFromBody("X") , message.getFromBody("Y"))) {
                body.put("Player" , message.getFromBody("Player") );
                body.put("Time" , message.getFromBody("Timer"));
                body.put("Direction" , message.getFromBody("Direction"));
                sendToAll(new Message(CAN_NOT_MOVE , body) , game);
                return;
            }
        }
        body.put("Player" , message.getFromBody("Player") );
        body.put("Time" , message.getFromBody("Timer"));
        body.put("Direction" , message.getFromBody("Direction"));
        body.put("X" , message.getFromBody("X"));
        body.put("Y" , message.getFromBody("Y"));
        sendToAll(new Message(CAN_MOVE , body) , game);
    }

    public Message Buy(Message message , Game game) throws IOException {
        payForBuy(message , game);
        reduceProductInMarket(message , game);

        return addItemToInventory(message);
    }

    private void payForBuy(Message message , Game game) throws IOException {
        User Player = message.getFromBody("Player");
        for (User user : game.getGameState().getPlayers()) {
            if (user.getUsername().trim().equals(Player.getUsername().trim()))  {
                Items items = message.getFromBody("Item");
                user.increaseMoney( - items.getMarketPrice(message.getFromBody("Market")));
                HashMap<String ,Object> body = new HashMap<>();
                body.put("Player", Player);
                body.put("Money" , items.getMarketPrice(message.getFromBody("Market")));
                sendToAll(new Message(CommandType.CHANGE_MONEY , body) , game);
            }
        }
    }

    private void reduceProductInMarket(Message message , Game game) throws IOException {
        MarketType market = message.getFromBody("Market");

                Items items = message.getFromBody("Item");
                items.setRemindInShop(items.getRemindInShop(market) - 1 , market);

                HashMap<String ,Object> body = new HashMap<>();
                body.put("Item" , items);
                body.put("Market" , market);
                sendToAll(new Message(REDUCE_PRODUCT , body) , game);
    }

    private Message addItemToInventory(Message message) throws IOException {
        HashMap<String ,Object> body = new HashMap<>();
        body.put("Player" , message.getFromBody("Player"));
        body.put("Item" , message.getFromBody("Item"));
        body.put("amount" , message.getFromBody("amount"));
        return new Message(CommandType.
            CHANGE_INVENTORY, body);
    }

    public void buyBackPack(Message message , Game game) throws IOException {
        payForBuy(message , game);
        reduceProductInMarket(message , game);
        //TODO
    }

    public void placeCraftOrShippingBin(Message message , Game game) throws IOException {
        Items items = message.getFromBody("Item");
        int x = message.getFromBody("X");
        int y = message.getFromBody("Y");
        getTileByCoordinates(x , y , game.getGameState()).setGameObject(items);
        User player = message.getFromBody("Player");

        for (User user : game.getGameState().getPlayers()) {
            if (user.getUsername().trim().equals(player.getUsername().trim())) {
                if (items instanceof ShippingBin) {
                    user.getFarm().shippingBins.add((ShippingBin) items);
                }
//                user.getBackPack().inventory.Items.compute(items , (k,v) -> v - 1);
//                if (user.getBackPack().inventory.Items.get(items ) == 0) {
//                    user.getBackPack().inventory.Items.remove(items);
//                }
            }
        }
        // در خط پایین برای همه ارسال میکنم که در اون مختصات این آیتم را ست کنن
        HashMap<String , Object> body = new HashMap<>();
        body.put("X", x);
        body.put("Y", y);
        body.put("Item" , items);
        sendToAll(message , game);
    }

    public ArrayList<Message> BuyBarnCage(Message message , Game game) throws IOException {
        reduceBarnOrCageInMarket(message , game);

        return payForBuilding(message , game);
    }

    private ArrayList<Message> payForBuilding(Message message , Game game) throws IOException {
        BarnORCageType barnORCageType = message.getFromBody("BarnOrCageType");
        Point p = message.getFromBody("Point");
        for (int i = p.x ; i < p.x + barnORCageType.getWidth() ; i++) {
            for (int j = p.y ; j < p.y + barnORCageType.getHeight() ; j++) {
                BarnOrCage barnOrCage = new BarnOrCage(barnORCageType , i , j);
                getTileByCoordinates(i , j , game.getGameState()).setGameObject(barnOrCage);
            }
        }
        User Player = message.getFromBody("Player");
        for (User user : game.getGameState().getPlayers()) {
            if (user.getUsername().trim().equals(Player.getUsername().trim()))  {
                user.increaseMoney( - barnORCageType.getPrice());
                HashMap<String ,Object> body = new HashMap<>();
                body.put("Player", Player);
                body.put("Money" , - barnORCageType.getPrice());
                user.BarnOrCages.add((BarnOrCage) getTileByCoordinates(p.x , p.y , game.getGameState()).getGameObject());
                sendToAll(new Message(CommandType.CHANGE_MONEY , body) , game);
            }
        }

        HashMap<String ,Object> body = new HashMap<>();
        body.put("Player" , Player);
        body.put("Item" , new Wood());
        body.put("amount" , - barnORCageType.getWoodNeeded());
        HashMap<String ,Object> body2 = new HashMap<>();
        body.put("Player" , Player);
        body2.put("Item" , new BasicRock());
        body2.put("amount" , - barnORCageType.getStoneNeeded());
        HashMap<String , Object> body3 = new HashMap<>();
        body3.put("X" , p.x);
        body3.put("Y" , p.y);
        body3.put("BarnOrCage" , getTileByCoordinates(p.x , p.y , game.getGameState()).getGameObject());
        body3.put("Player" , message.getFromBody("Player"));
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(new Message(CommandType.CHANGE_INVENTORY, body));
        messages.add(new Message(CommandType.CHANGE_INVENTORY, body2));
        sendToAll(new Message(PLACE_BARN_CAGE , body3) , game);
        //messages.add(new Message(CommandType.PLACE_BARN_CAGE, body3));

        return messages;
    }

    public void reduceBarnOrCageInMarket(Message message , Game game) throws IOException {
        BarnOrCage barnOrCage = new BarnOrCage(message.getFromBody("BarnOrCageType") , 0 , 0);
        barnOrCage.setRemindInShop(0 , null);
        HashMap<String , Object> body = new HashMap<>();
        body.put("BarnOrCageType" , message.getFromBody("BarnOrCageType"));
        System.out.println("reduce barn or cage");
        sendToAll(new Message(REDUCE_BARN_CAGE , body) , game);
    }

    public void buyAnimal(Message message , Game game) throws IOException {
        User player = message.getFromBody("Player");
        Animal animal = message.getFromBody("Animal");
        BarnOrCage barnOrCage = message.getFromBody("BarnOrCage");

        for (User user : game.getGameState().getPlayers()) {
            if (user.getUsername().trim().equals(player.getUsername().trim())) {
                for (BarnOrCage barnOrCage1 : user.BarnOrCages) {
                    if (barnOrCage1.topLeftX == barnOrCage.topLeftX && barnOrCage1.topLeftY == barnOrCage.topLeftY) {
                        System.out.println("Buy Animal");
                        barnOrCage1.animals.add(animal);
                        barnOrCage1.animals.getLast().setIndex(barnOrCage.animals.size() - 1);
                        barnOrCage1.animals.getLast().setPositionX(barnOrCage.topLeftX + barnOrCage.getBarnORCageType().getDoorX());
                        barnOrCage1.animals.getLast().setPositionY(barnOrCage.topLeftY + barnOrCage.getBarnORCageType().getDoorY());
                        animal.getType().increaseRemindInShop(-1);
                    }
                }
            }
        }
        HashMap<String , Object> changeMoney = new HashMap<>();
        changeMoney.put("Player" , player);
        changeMoney.put("Money" , animal.getType().getPrice());
        sendToAll(new Message(CommandType.CHANGE_MONEY , changeMoney), game);

        HashMap<String , Object> reduceAnimal = new HashMap<>();
        reduceAnimal.put("AnimalType" , animal.getType());
        sendToAll(new Message(CommandType.REDUCE_ANIMAL , reduceAnimal), game);

        HashMap<String , Object> buyAnimal = new HashMap<>();
        buyAnimal.put("Animal" , animal);
        buyAnimal.put("BarnOrCage" , barnOrCage);
        buyAnimal.put("Player" , player);
        sendToAll(new Message(CommandType.BUY_ANIMAL, buyAnimal), game);
    }

    public void sellAnimal(Message message , Game game) throws IOException {
        User player = message.getFromBody("Player");
        Animal animal = message.getFromBody("Animal");
        HashMap<String , Object> changeMoney = new HashMap<>();
        double x = animal.getFriendShip()/1000 + 0.3;
        changeMoney.put("Money" , (int) (animal.getType().getPrice() * x));
        sendToAll(new Message(CommandType.CHANGE_MONEY , changeMoney), game);
        BarnOrCage current=null;

        for (User user : game.getGameState().getPlayers()) {
            if (user.getUsername().trim().equals(player.getUsername().trim())) {
                for (BarnOrCage barnOrCage : user.BarnOrCages) {
                    for (Animal animal1 : barnOrCage.animals) {
                        if (animal1.getName().equals(animal.getName())) {
                            barnOrCage.animals.remove(animal1);
                            current = barnOrCage;
                            break;
                        }
                    }
                }
            }
        }
        int index=0;
        for (Animal animal1 : current.animals) {
            animal1.setIndex(index);
            index++;
        }
        HashMap<String , Object> sellAnimals = new HashMap<>();
        sellAnimals.put("Player" , player);
        sellAnimals.put("Animal" , animal);
        sendToAll(new Message(CommandType.SELL_ANIMAL , sellAnimals) , game);
    }

    public Message AnswerFeedHay(Message message , Game game) {
        User Player = message.getFromBody("Player");
        Animal Animal = message.getFromBody("Animal");
        MarketItem marketItem = message.getFromBody("Hay");
        for (User user : game.getGameState().getPlayers()) {
            if (user.getUsername().trim().equals(Player.getUsername().trim())) {
                if (user.getBackPack().inventory.Items.containsKey(marketItem)) {
                    user.getBackPack().inventory.Items.compute(marketItem, (k, v) -> v - 1);
                }
                user.getBackPack().inventory.Items.entrySet().removeIf(entry -> entry.getValue() == 0);
            }
        }
        Animal.setFeedToday(true);
        HashMap<String , Object> body = new HashMap<>();
        body.put("Animal", Animal);
        body.put("Hay" , marketItem);
        return new Message(CommandType.FEED_HAY , body);
    }

    public void answerShepherding(Message message , Game game) throws IOException {
        Animal animal = message.getFromBody("Animal");
        int x = message.getFromBody("X");
        int y = message.getFromBody("Y");
        animal.setOut(true);
        animal.setPositionX(x * TEXTURE_SIZE);
        animal.setPositionY((90 - y) * TEXTURE_SIZE);
        sendToAll(message , game);
    }

    public void Pet(Message message) {
        Animal animal = message.getFromBody("Animal");
        animal.increaseFriendShip(15);
        animal.setPetToday(true);
    }

    public Message collectProduct(Message message) {
        Animal animal = message.getFromBody("Animal");
        animal.setProductCollected(true);
        HashMap<String , Object> body = new HashMap<>();
        body.put("Item" , message.getFromBody("Product"));
        body.put("amount" , 1);
        return new Message(CommandType.CHANGE_INVENTORY , body);
    }

    public Message changeInventory(Message message , Game game) {
        User player = message.getFromBody("Player");
        for (User user : game.getGameState().getPlayers()) {
            if (user.getUsername().trim().equals(player.getUsername().trim())) {
                Items items = message.getFromBody("Item");
                int amount = message.getFromBody("amount");
                if (user.getBackPack().inventory.Items.containsKey(items)) {
                    user.getBackPack().inventory.Items.compute(items,(k,v) -> v + amount);
                    if (user.getBackPack().inventory.Items.get(items) == 0) {
                        Main.getClient().getPlayer().getBackPack().inventory.Items.remove(items);
                    }
                }
                else {
                    user.getBackPack().inventory.Items.put(items,amount);
                }
                HashMap<String , Object> body = new HashMap<>();
                body.put("Player", player);
                body.put("Item" , message.getFromBody("Item"));
                body.put("amount" , message.getFromBody("amount"));
                return new Message(CommandType.CHANGE_INVENTORY , body);
            }
        }
        return null;
    }

    public void ExitTheMarket(Message message , Game game) throws IOException {
        //MarketType marketType = message.getFromBody("Market");
        int x = message.getFromBody("X");
        int y = message.getFromBody("Y");
        System.out.println(x + " " + y);
        for (User user : game.getGameState().getPlayers()) {
            if (user.getUsername().trim().equals(message.getFromBody("Player"))) {
                user.setPositionX((float) x);
                user.setPositionY((float) y);
                user.setIsInMarket(false);
                user.setInFarmExterior(true);
            }
        }
        sendToAll(message , game);
    }

    public void sendToAll(Message message , Game game) throws IOException {
        for (Map.Entry<User , Connection> entry : game.connections.entrySet()) {
            entry.getValue().sendTCP(message);
        }
    }

    void sendToOnePerson(Message message, Game game, User user) throws IOException {
        for (Map.Entry<User , Connection> entry : game.connections.entrySet()) {
            if (entry.getKey().getUsername().trim().equals(user.getUsername().trim())) {
                entry.getValue().sendTCP(message);
            }
        }
    }

    public void sendToAllExceptOnePerson(Message message, Game game, User user) throws IOException {
        for (Map.Entry<User , Connection> entry : game.connections.entrySet()) {
            if (! entry.getKey().getUsername().trim().equals(user.getUsername().trim())) {
                entry.getValue().sendTCP(message);
            }
        }
    }

    private ClientConnectionController() {}

    public static ClientConnectionController getInstance() {
        if (instance == null)
            instance = new ClientConnectionController();
        return instance;
    }
    public void CheckFriendDistance() {

    }


                                        // Erfan
    public void sendSetTimeMessage (int hour, int day, Game game) throws IOException {

        HashMap<String , Object> PassedTime = new HashMap<>();
        PassedTime.put("Hour", hour);
        PassedTime.put("Day", day);
        sendToAll(new Message(CommandType.SET_TIME, PassedTime), game);
    }
    public void passedOfTime (int day, int hour, DateHour currentDateHour, Game game) throws IOException {

        DateHour dateHour = currentDateHour.clone();

        dateHour.increaseHour(hour);
        dateHour.increaseDay(day);

        if (dateHour.getHour() > 22) {
            passedOfTime(getDayDifferent(dateHour, Main.getClient().getLocalGameState().currentDate),
                24 - dateHour.getHour() + 9 + hour, currentDateHour, game);
            return;
        }
        if (dateHour.getHour() < 9) {
            passedOfTime(getDayDifferent(dateHour, Main.getClient().getLocalGameState().currentDate),
                9 - dateHour.getHour() + hour, currentDateHour, game);
            return;
        }

        int number = getDayDifferent(currentDateHour, dateHour);

        for (int i = 0 ; i < number ; i++)
            currentDateHour.increaseDay(1);

        currentDateHour.increaseHour(dateHour.getHour() - currentDateHour.getHour());
        sendSetTimeMessage(currentDateHour.getHour(), currentDateHour.getDate(), game);
    }
    public void setTime (int day, int hour, Game game) throws IOException {

        DateHour currentDateHour = ServerHandler.getInstance(game).currentDateHour;

        ServerHandler.getInstance(game).currentDateHour.setDate(day);
        ServerHandler.getInstance(game).currentDateHour.setHour(hour);

        sendSetTimeMessage(currentDateHour.getHour(), currentDateHour.getDate(), game);
    }
    public void sendSetGameObjectMessage (int x, int y, GameObject object, Game game) throws IOException {
        HashMap<String , Object> PassedTime = new HashMap<>();
        PassedTime.put("X", x);
        PassedTime.put("Y", y);
        PassedTime.put("Object", object);
        sendToAll(new Message(CommandType.SET_TIME, PassedTime), game);
    }
}
