package com.Graphic.model.ClientServer;

import com.Graphic.Controller.MainGame.InputGameController;
import com.Graphic.View.GameMenus.MarketMenu;
import com.Graphic.model.*;
import com.Graphic.model.Animall.Animal;
import com.Graphic.model.Animall.BarnOrCage;
import com.Graphic.model.Enum.AllPlants.ForagingCropsType;
import com.Graphic.model.Enum.AllPlants.ForagingMineralsType;
import com.Graphic.model.Enum.AllPlants.ForagingSeedsType;
import com.Graphic.model.Enum.Commands.CommandType;
import com.Graphic.model.Enum.ItemType.BarnORCageType;
import com.Graphic.model.Enum.ItemType.MarketType;
import com.Graphic.model.Enum.WeatherTime.Season;
import com.Graphic.model.Enum.WeatherTime.Weather;
import com.Graphic.model.MapThings.GameObject;
import com.Graphic.model.MapThings.Tile;
import com.Graphic.model.MapThings.Walkable;
import com.Graphic.model.Places.MarketItem;
import com.Graphic.model.Places.ShippingBin;
import com.Graphic.model.Plants.*;
import com.Graphic.model.Weather.DateHour;
import com.esotericsoftware.kryonet.Connection;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

import static com.Graphic.Controller.MainGame.GameControllerLogic.*;
import static com.Graphic.model.Enum.AllPlants.ForagingMineralsType.*;
import static com.Graphic.model.Enum.Commands.CommandType.REDUCE_BARN_CAGE;
import static com.Graphic.model.Enum.Commands.CommandType.*;
import static com.Graphic.model.HelpersClass.TextureManager.TEXTURE_SIZE;
import static com.Graphic.model.Weather.DateHour.getDayDifferent;

public class ClientConnectionController {

    private static ClientConnectionController instance;
    private Random rand = new Random();

    public void createFarm(Message message , Game game) throws IOException {
        int index = message.getFromBody("Index");
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
                    if (barnOrCage1.equals(barnOrCage)) {
                        System.out.println("Buy Animal");
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

    public Message AnswerFeedHay(Message message , Game game) {
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
//                    if (user.getBackPack().inventory.Items.get(items) == 0) {
//                        Main.getClient().getPlayer().getBackPack().inventory.Items.remove(items);
//                    }
                }
                else {
                    user.getBackPack().inventory.Items.put(items,amount);
                }
                HashMap<String , Object> body = new HashMap<>();
                body.put("Player", player);
                body.put("Item" , items);
                body.put("amount" , amount);
                return new Message(CommandType.CHANGE_INVENTORY , body);
            }
        }
        return null;
    }

    public void ExitTheMarket(Message message , Game game) throws IOException {
        //MarketType marketType = message.getFromBody("Market");
        int x = message.getFromBody("X");
        int y = message.getFromBody("Y");
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
            passedOfTime(getDayDifferent(dateHour, currentDateHour),
                24 - dateHour.getHour() + 9 + hour, currentDateHour, game);
            return;
        }
        if (dateHour.getHour() < 9) {
            passedOfTime(getDayDifferent(dateHour, currentDateHour),
                9 - dateHour.getHour() + hour, currentDateHour, game);
            return;
        }

        int number = getDayDifferent(currentDateHour, dateHour);

        for (int i = 0 ; i < number ; i++) {
            currentDateHour.increaseDay(1);
            this.startDay(game);
        }

        currentDateHour.increaseHour(dateHour.getHour() - currentDateHour.getHour());
        sendSetTimeMessage(currentDateHour.getHour(), currentDateHour.getDate(), game);
    }
    public void setTime (int day, int hour, Game game) throws IOException {

        DateHour currentDateHour = game.getGameState().currentDate;

        currentDateHour.setDate(day);
        currentDateHour.increaseHour(hour - currentDateHour.getHour());

        sendSetTimeMessage(currentDateHour.getHour(), currentDateHour.getDate(), game);
    }
    public void sendSetGameObjectMessage (int x, int y, GameObject object, Game game) throws IOException {

        Tile tile = getTileByCoordinates(x, y, game.getGameState());
        tile.setGameObject(object);

        HashMap<String , Object> body = new HashMap<>();
        body.put("X", x);
        body.put("Y", y);
        body.put("Object", object);
        sendToAll(new Message(CHANGE_GAME_OBJECT, body), game);
    }
    public void sendSetGameObjectMessage (Tile tile,  GameObject object, Game game) throws IOException {

        tile.setGameObject(object);

        HashMap<String , Object> body = new HashMap<>();
        body.put("X", tile.getX());
        body.put("Y", tile.getY());
        body.put("Object", object);
        sendToAll(new Message(CHANGE_GAME_OBJECT, body), game);
    }
    public void sentWeather(Game game) throws IOException {

        HashMap<String , Object> body = new HashMap<>();
        body.put("Weather", game.getGameState().tomorrowWeather);
        sendToAll(new Message(GET_TOMORROW_WEATHER, body), game);
    }
    public void setTimeAndWeather (Game game) throws IOException {

        game.getGameState().currentDate = new DateHour(Season.Spring, 1, 9, 1980);
        game.getGameState().currentWeather = Weather.Sunny;
        game.getGameState().tomorrowWeather = Weather.Sunny;
    }
    public void sendAddMineralMessage (User user, ForagingMinerals minerals, Point point, Game game) throws IOException {

        HashMap<String , Object> body = new HashMap<>();
        body.put("User", user);
        body.put("Point", point);
        body.put("Mineral", minerals);
        sendToAll(new Message(ADD_MINERAL, body), game);
    }
    public void startDay (Game game) throws IOException {
        createRandomForaging(game);
        createRandomMinerals(game);
        for (Tile tile : game.getGameState().bigMap)
            tile.getGameObject().startDayAutomaticTask();
    }
    public void createRandomForaging (Game game) throws IOException {

        for (Tile tile : game.getGameState().bigMap) {

            if (tile.getGameObject() instanceof Walkable &&
                ((Walkable) tile.getGameObject()).getGrassOrFiber().equals("Plowed") && Math.random() <= 0.2) {
                if (Math.random() <= 0.5) {

                    java.util.List<ForagingSeedsType> types = Arrays.stream(ForagingSeedsType.values())
                        .filter(d -> d.getSeason().contains(game.getGameState().currentDate.getSeason()))
                        .toList();

                    ForagingSeedsType type = types.get(rand.nextInt(types.size()));
                    this.sendSetGameObjectMessage(tile, new ForagingSeeds(type, game.getGameState().currentDate), game);
                } else {

                    List<ForagingCropsType> types = new ArrayList<>(Arrays.stream(ForagingCropsType.values())
                        .filter(d -> d.getSeason().contains(game.getGameState().currentDate.getSeason()))
                        .toList());

                    types.remove(ForagingCropsType.Fiber);
                    ForagingCropsType type = types.get(rand.nextInt(types.size()));

                    ForagingCrops crop = new ForagingCrops(type);
                    this.sendSetGameObjectMessage(tile, crop, game);
                }
            }

            else if (tile.getGameObject() instanceof Walkable &&
                ((Walkable) tile.getGameObject()).getGrassOrFiber().equals("Walk") &&
                canGrowGrass(tile) && Math.random() <= 0.1) {

                Walkable walkable = (Walkable) tile.getGameObject();

                if (Math.random() <= 0.5)
                    walkable.setGrassOrFiber("Fiber");
                else
                    walkable.setGrassOrFiber("Grass");

                sendSetGameObjectMessage(tile, walkable, game);
            }
        }
    }
    public void createRandomMinerals (Game game) throws IOException {

//        for (User user : game.getGameState().getPlayers()) {
//
//            List<Integer> positions = new ArrayList<>();
//            for (int i = 0 ; i < 16 ; i++)
//                positions.add(i);
//
//            Collections.shuffle(positions);
//
//            List<ForagingMineralsType> minerals = Arrays.asList(
//                RUBY, COAL, IRON, TOPAZ, GOLD, JADE, IRIDIUM,
//                QUARTZ, EMERALD, COPPER, DIAMOND, AMETHYST,
//                AQUAMARINE, FROZEN_TEAR, FIRE_QUARTZ,
//                PRISMATIC_SHARD, EARTH_CRYSTAL
//            );
//
//            int posIndex = 0;
//            for (ForagingMineralsType mineral : minerals) {
//                while (posIndex < positions.size()) {
//                    Point point = new Point(
//                        user.getFarm().getMine().getPositions().get(positions.get(posIndex)));
//
//                    if (user.getFarm().getMine().checkPositionForMineral(point)) {
//                        if (Math.random() <= mineral.getProbability()) {
//                            ForagingMinerals f = new ForagingMinerals(mineral);
//                            f.setPosition(point);
//                            user.getFarm().getMine().getForagingMinerals().add(f);
//                            user.getFarm().getMine().getTaken().add(point);
//                            sendAddMineralMessage(user, f, point, game);
//                            break;
//                        }
//                    }
//                    posIndex ++;
//                }
//            }
//        }
    }

    public Message changeCurrentItem(User player, Items item, Game game) throws IOException {
        for (User p: game.getGameState().getPlayers()) {
            if (p.getUsername().equals(player.getUsername())) {
                p.currentItem = item;
            }
        }

        HashMap<String, Object> body = new HashMap<>();
        body.put("Player", player);
        body.put("Item", item);
        return new Message(CURRENT_ITEM, body);
    }
}
