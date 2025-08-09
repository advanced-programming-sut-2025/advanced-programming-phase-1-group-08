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
import com.Graphic.model.Places.MarketItem;
import com.Graphic.model.Places.ShippingBin;
import com.Graphic.model.Plants.BasicRock;
import com.Graphic.model.Plants.Wood;
import com.Graphic.model.ToolsPackage.CraftingItem;
import com.esotericsoftware.kryonet.Connection;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.Graphic.Controller.MainGame.GameControllerLogic.*;
import static com.Graphic.model.Enum.Commands.CommandType.CHANGE_INVENTORY;
import static com.Graphic.model.Enum.Commands.CommandType.REDUCE_BARN_CAGE;
import static com.Graphic.model.HelpersClass.TextureManager.TEXTURE_SIZE;

public class ClientConnectionController {


    public static void createFarm(Message message , Game game) throws IOException {
        int index = message.getIntFromBody("Index");
        User user = message.getFromBody("Player");
        game.getGameState().incrementNumberOfMaps();
        int topLeftX = 0;
        int topLeftY = 0;
        if (game.getGameState().getNumberOfMaps() % 2 == 0) {
            topLeftX = 1;
        }
        if (game.getGameState().getNumberOfMaps() >= 3) {
            topLeftY = 1;
        }
        user.topLeftX = topLeftX;
        user.topLeftY = topLeftY;
        Message result = createInitialFarm(index , user , game.getGameState());
        sendToAll(result , game);
        if (game.getGameState().getNumberOfMaps() == 4) {
            sendToAll(build(game.getGameState()) , game);
        }
    }

    public static Game newGame(Message message , Connection connection) throws IOException {
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

    public static Game joinGame(Message message , Connection connection) throws IOException {
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

    public static void moveInFarm(Message message , Game game) throws IOException {
        Message body = InputGameController.getInstance().checkWalking(message, game);
        sendToAll(body , game);
    }

    public static void answerEnterTheMarket(Message message , Game game) throws IOException {
        User player = message.getFromBody("Player");
        for (User user : game.getGameState().getPlayers()) {
            if (user.getUsername().trim().equals(player.getUsername().trim())) {
                user.setIsInMarket(true);
                user.setInFarmExterior(false);
                MarketType market = message.getFromBody("Market");
                user.setPositionX(market.getInsideDoor().getX());
                user.setPositionY(market.getInsideDoor().getY());
                HashMap<String , Object> body = new HashMap<>();
                body.put("Player", user);
                body.put("Market", market);
                body.put("X" , market.getInsideDoor().getX());
                body.put("Y" , market.getInsideDoor().getY());
                body.put("Is in farm" , false);
                body.put("Is in Market" , true);
                sendToAll(new Message(CommandType.ENTER_THE_MARKET , body) , game);
            }
        }
    }

    public static void moveInMarket(Message message , Game game) throws IOException {
        HashMap<String , Object> body = new HashMap<>();
        for (collisionRect rect : MarketMenu.getInstance().marketType.getRects()) {
            if (! rect.checkCollision(message.getFromBody("X") , message.getFromBody("Y"))) {
                body.put("Player" , message.getFromBody("Player") );
                body.put("Timer" , message.getFromBody("Timer"));
                body.put("Direction" , message.getFromBody("Direction"));
                sendToAll(message , game);
                return;
            }
        }
        body.put("Player" , message.getFromBody("Player") );
        body.put("Timer" , message.getFromBody("Timer"));
        body.put("Direction" , message.getFromBody("Direction"));
        body.put("X" , message.getFromBody("X"));
        body.put("Y" , message.getFromBody("Y"));
        sendToAll(message , game);
        return;
    }

    public static Message Buy(Message message , Game game) throws IOException {
        payForBuy(message , game);
        reduceProductInMarket(message , game);

        return addItemToInventory(message);
    }

    private static void payForBuy(Message message , Game game) throws IOException {
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

    private static void reduceProductInMarket(Message message , Game game) throws IOException {
        User Player = message.getFromBody("Player");
        MarketType market = message.getFromBody("Market");

        for (User user : game.getGameState().getPlayers()) {
            if (user.getUsername().trim().equals(Player.getUsername().trim()))  {
                Items items = message.getFromBody("Item");
                items.setRemindInShop(items.getRemindInShop(market) - 1 , market);

                HashMap<String ,Object> body = new HashMap<>();
                body.put("Item" , items);
                sendToAll(message , game);
            }
        }
    }

    private static Message addItemToInventory(Message message) throws IOException {
        HashMap<String ,Object> body = new HashMap<>();
        body.put("Player" , Main.getClient(null).getPlayer());
        body.put("Item" , message.getFromBody("Item"));
        body.put("amount" , message.getFromBody("amount"));
        return new Message(CommandType.
            CHANGE_INVENTORY, body);
    }

    public static void buyBackPack(Message message , Game game) throws IOException {
        payForBuy(message , game);
        reduceProductInMarket(message , game);
        //TODO
    }

    public static void placeCraftOrShippingBin(Message message , Game game) throws IOException {
        Items items = message.getFromBody("Item");
        int x = message.getIntFromBody("X");
        int y = message.getIntFromBody("Y");
        getTileByCoordinates(x , y , game.getGameState()).setGameObject(items);
        User player = message.getFromBody("Player");

        for (User user : game.getGameState().getPlayers()) {
            if (user.getUsername().trim().equals(player.getUsername().trim())) {
                if (items instanceof ShippingBin) {
                    user.getFarm().shippingBins.add((ShippingBin) items);
                }
                user.getBackPack().inventory.Items.compute(items , (k,v) -> v - 1);
                if (user.getBackPack().inventory.Items.get(items ) == 0) {
                    user.getBackPack().inventory.Items.remove(items);
                }
            }
        }
        // در خط پایین برای همه ارسال میکنم که در اون مختصات این آیتم را ست کنن
        HashMap<String , Object> body = new HashMap<>();
        body.put("X", x);
        body.put("Y", y);
        body.put("Item" , items);
        sendToAll(message , game);
    }

    public static ArrayList<Message> BuyBarnCage(Message message , Game game) throws IOException {
        reduceBarnOrCageInMarket(message , game);

        return payForBuilding(message , game);
    }

    private static ArrayList<Message> payForBuilding(Message message , Game game) throws IOException {
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
                body.put("Money" , barnORCageType.getPrice());
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
        body3.put("X" , p.getX());
        body3.put("Y" , p.getY());
        body.put("BarnOrCage" , getTileByCoordinates(p.x , p.y , game.getGameState()).getGameObject());
        body3.put("Player" , message.getFromBody("Player"));
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(new Message(CommandType.CHANGE_INVENTORY, body));
        messages.add(new Message(CommandType.CHANGE_INVENTORY, body2));
        messages.add(new Message(CommandType.PLACE_BARN_CAGE, body3));

        return messages;
    }

    public static void reduceBarnOrCageInMarket(Message message , Game game) throws IOException {
        BarnOrCage barnOrCage = new BarnOrCage(message.getFromBody("BarnOrCageType") , 0 , 0);
        barnOrCage.setRemindInShop(0 , null);
        HashMap<String , Object> body = new HashMap<>();
        body.put("BarnOrCageType" , message.getFromBody("BarnOrCageType"));
        sendToAll(new Message(REDUCE_BARN_CAGE , body) , game);
    }

    public static void buyAnimal(Message message , Game game) throws IOException {
        User player = message.getFromBody("Player");
        Animal animal = message.getFromBody("Animal");
        BarnOrCage barnOrCage = message.getFromBody("BarnOrCage");

        for (User user : game.getGameState().getPlayers()) {
            if (user.getUsername().trim().equals(player.getUsername().trim())) {
                for (BarnOrCage barnOrCage1 : user.BarnOrCages) {
                    if (barnOrCage1.equals(barnOrCage)) {
                        barnOrCage.animals.add(animal);
                        barnOrCage.animals.getLast().setIndex(barnOrCage.animals.size() - 1);
                        barnOrCage.animals.getLast().setPositionX(barnOrCage.topLeftX + barnOrCage.getBarnORCageType().getDoorX());
                        barnOrCage.animals.getLast().setPositionY(barnOrCage.topLeftY + barnOrCage.getBarnORCageType().getDoorY());
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

    public static void sellAnimal(Message message , Game game) throws IOException {
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
                        if (animal1.equals(animal)) {
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

    public static Message AnswerFeedHay(Message message , Game game) {
        User Player = message.getFromBody("Player");
        Animal Animal = message.getFromBody("Animal");
        MarketItem marketItem = message.getFromBody("Hay");
        for (User user : game.getGameState().getPlayers()) {
            if (user.getUsername().trim().equals(Player.getUsername().trim())) {
                user.getBackPack().inventory.Items.compute(marketItem , (k,v) -> v-1);
                user.getBackPack().inventory.Items.entrySet().removeIf(entry -> entry.getValue() == 0);
            }
        }
        Animal.setFeedToday(true);
        HashMap<String , Object> body = new HashMap<>();
        body.put("Animal", Animal);
        body.put("Hay" , marketItem);
        return new Message(CommandType.FEED_HAY , body);
    }

    public static void answerShepherding(Message message , Game game) throws IOException {
        Animal animal = message.getFromBody("Animal");
        int x = message.getIntFromBody("X");
        int y = message.getIntFromBody("Y");
        animal.setOut(true);
        animal.setPositionX(x * TEXTURE_SIZE);
        animal.setPositionY((90 - y) * TEXTURE_SIZE);
        sendToAll(message , game);
    }

    public static void Pet(Message message) {
        Animal animal = message.getFromBody("Animal");
        animal.increaseFriendShip(15);
        animal.setPetToday(true);
    }

    public static Message collectProduct(Message message) {
        Animal animal = message.getFromBody("Animal");
        animal.setProductCollected(true);
        HashMap<String , Object> body = new HashMap<>();
        body.put("Item" , message.getIntFromBody("Product"));
        body.put("amount" , 1);
        return new Message(CommandType.CHANGE_INVENTORY , body);
    }

    public static Message changeInventory(Message message , Game game) {
        User player = message.getFromBody("Player");
        for (User user : game.getGameState().getPlayers()) {
            if (user.getUsername().trim().equals(player.getUsername().trim())) {
                Items items = message.getFromBody("Item");
                int amount = message.getIntFromBody("amount");
                if (user.getBackPack().inventory.Items.containsKey(items)) {
                    user.getBackPack().inventory.Items.compute(items,(k,v) -> v + amount);
                    if (user.getBackPack().inventory.Items.get(items) == 0) {
                        Main.getClient(null).getPlayer().getBackPack().inventory.Items.remove(items);
                    }
                }
                else {
                    user.getBackPack().inventory.Items.put(items,amount);
                }
                HashMap<String , Object> body = new HashMap<>();
                body.put("Player", player);
                body.put("Item" , message.getFromBody("Item"));
                body.put("amount" , message.getIntFromBody("amount"));
                return new Message(CommandType.CHANGE_INVENTORY , body);
            }
        }
        return null;
    }

    public static void sendToAll(Message message , Game game) throws IOException {
        for (Map.Entry<User , Connection> entry : game.connections.entrySet()) {
            entry.getValue().sendTCP(message);
        }
    }

    private static void sendToOnePerson(Message message , Game game , User user) throws IOException {
        for (Map.Entry<User , Connection> entry : game.connections.entrySet()) {
            if (entry.getKey().getUsername().trim().equals(user.getUsername().trim())) {
                entry.getValue().sendTCP(message);
            }
        }
    }
}
