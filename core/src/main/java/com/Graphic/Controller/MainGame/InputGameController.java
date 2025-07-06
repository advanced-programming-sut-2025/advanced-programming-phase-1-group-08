package com.Graphic.Controller.MainGame;

import com.Graphic.Main;
import com.Graphic.model.*;
import com.Graphic.model.Animall.Animal;
import com.Graphic.model.Enum.AllPlants.*;
import com.Graphic.model.Enum.ItemType.*;
import com.Graphic.model.Enum.NPC;
import com.Graphic.model.Enum.SecurityQuestions;
import com.Graphic.model.Enum.ToolsType.*;
import com.Graphic.model.HelpersClass.Result;
import com.Graphic.model.HelpersClass.TextureManager;
import com.Graphic.model.MapThings.*;
import com.Graphic.model.OtherItem.*;
import com.Graphic.model.Places.*;
import com.Graphic.model.Plants.*;
import com.Graphic.model.Animall.BarnOrCage;
import com.Graphic.model.Enum.Commands.GameMenuCommands;
import com.Graphic.model.Enum.WeatherTime.Season;
import com.Graphic.model.Enum.WeatherTime.Weather;
import com.Graphic.model.SaveData.PasswordHashUtil;
import com.Graphic.model.ToolsPackage.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Actor;


import java.io.IOException;
import java.util.*;

import static com.Graphic.Controller.MainGame.GameControllerLogic.*;

import static com.Graphic.model.App.*;
import static com.Graphic.model.HelpersClass.Color_Eraser.*;
import static com.Graphic.model.HelpersClass.TextureManager.TEXTURE_SIZE;
import static com.Graphic.model.SaveData.UserDataBase.findUserByUsername;


public class InputGameController {

    public static InputGameController inputGameController;

    private InputGameController() {

    }
    public static InputGameController getInstance() {
        if (inputGameController == null)
            inputGameController = new InputGameController();
        return inputGameController;
    }

    public void init () {
        GameControllerLogic.init();
    }
    public void update(OrthographicCamera camera, float v) {
        print();
        moveCamera(camera);
        GameControllerLogic.update();
    }

    public void setCenteredPosition(Actor actor, float centerX, float centerY) {
        actor.setPosition(centerX - actor.getWidth() / 2f, centerY - actor.getHeight() / 2f);
    }


    public Result addDollar(int amount) {
        currentGame.currentPlayer.increaseMoney(amount);
        return new Result(true , "your money cheated successfully");
    }
    public Result setDollar(int amount) {
        currentGame.currentPlayer.increaseMoney(amount - currentGame.currentPlayer.getMoney());
        return new Result(true , "your money cheated successfully");
    }
    public Result goToMarketMenu () {
        if (MarketType.isInMarket(currentGame.currentPlayer.getPositionX() , currentGame.currentPlayer.getPositionY()) == null) {
            return new Result(false , "you are not in market");
        }
        currentMenu = com.Graphic.model.Enum.Menu.MarketMenu;
        return new Result(true , "Welcome to market menu");
    }
    public Result goToHomeMenu() {
        if ( !currentGame.currentPlayer.getFarm().isInHome(currentGame.currentPlayer.getPositionX() , currentGame.currentPlayer.getPositionY())) {
            return new Result(false , RED+"You're Not in Your Home!"+RESET);
        }
        currentMenu = com.Graphic.model.Enum.Menu.HomeMenu;
        return new Result(true , BLUE+"Welcome to home menu"+RESET);
    }

    public Result walk(int goalX, int goalY) {
        int startX = currentGame.currentPlayer.getPositionX();
        int startY = currentGame.currentPlayer.getPositionY();
        Tile endTile = getTileByCoordinates(goalX, goalY);

        if (endTile==null) {
            return new Result(false,"you can't go to this coordinate");
        }

        PriorityQueue<State> queue = new PriorityQueue<>(Comparator.comparingInt(s -> s.Energy));
        Set<State> visited = new HashSet<>();


        if (checkConditionsForWalk(goalX, goalY) !=null) {
            return checkConditionsForWalk(goalX, goalY);
        }

        for (int dir =1 ; dir<9 ; dir++) {
            Tile tile = getTileByDir(dir);
            if (tile == null ) {
                continue;
            }
            if (checkConditionsForWalk(tile.getX() , tile.getY()) ==null) {
                State state=new State(startX , startY , -1, 0);
                queue.add(state);
            }
        }

        int [] dirx={0,0,1,1,1,-1,-1,-1};
        int [] diry={1,-1,0,1,-1,0,1,-1};

        while (!queue.isEmpty()) {
            State current = queue.poll();
            if (current.x == goalX && current.y == goalY) {
                if (currentGame.currentPlayer.isHealthUnlimited()){
                    currentGame.currentPlayer.setPositionX(goalX);
                    currentGame.currentPlayer.setPositionY(goalY);
                    return new Result(true,"now you are in "+goalX+","+goalY);
                }
                if (currentGame.currentPlayer.getHealth() >= current.Energy ) {
                    currentGame.currentPlayer.increaseHealth(- current.Energy);
                    currentGame.currentPlayer.setPositionX(goalX);
                    currentGame.currentPlayer.setPositionY(goalY);
                    return new Result(true, "you are now in " + goalX + "," + goalY);
                }
                return new Result(false , "your energy is not enough for go to this tile");
            }
            if (visited.contains(current)) {
                continue;
            }
            visited.add(current);
            for (int i=0 ; i<8 ; i++) {
                int x=dirx[i];
                int y=diry[i];
                if (checkConditionsForWalk(x + current.x,y + current.y) !=null) {
                    continue;
                }
                int turnCost= (i + 1 == current.direction || current.direction== - 1) ? 1:10;
                int totalEnergy=current.Energy + turnCost;
                queue.add(new State(x+current.x , y+current.y , i+1 , totalEnergy));
            }
        }

        return new Result(false , "No way to this coordinate");
    }
    public Result cheatFishingAbility(int amount) {
        currentGame.currentPlayer.increaseFishingAbility(amount);
        return new Result(true , "you cheated fishing successfully");
    }
    public Result addItem(String name ,int amount) {

        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;
        ItemRegistry itemRegistry = new ItemRegistry();
        Items items = null;


        itemRegistry.scanItems("model.Plants");
        if ((items = itemRegistry.nameToItemMap.get(name)) != null) {
            if (inventory.Items.containsKey(items)) {
                inventory.Items.compute(items , (k,v) -> v+amount);
            }
            else if (currentGame.currentPlayer.getBackPack().getType().getRemindCapacity() == 0) {
                return new Result(false , "Not Enough Capacity!");
            }
            else {
                inventory.Items.put(items, amount);
            }
            return new Result(true , name + " Added Successfully");
        }

        itemRegistry.scanItems("model.Places");
        if ((items=itemRegistry.nameToItemMap.get(name)) != null) {
            if (inventory.Items.containsKey(items)) {
                inventory.Items.compute(items , (k,v) -> v+amount);
            }
            else if (currentGame.currentPlayer.getBackPack().getType().getRemindCapacity() == 0) {
                return new Result(false , "Not Enough Capacity!");
            }
            else {
                inventory.Items.put(items, amount);
            }
            return new Result(true , name + " Added Successfully");
        }

        itemRegistry.scanItems("model.ToolsPackage");
        if ((items=itemRegistry.nameToItemMap.get(name)) != null) {
            if (inventory.Items.containsKey(items)) {
                inventory.Items.compute(items , (k,v) -> v+amount);
            }
            else if (currentGame.currentPlayer.getBackPack().getType().getRemindCapacity() == 0) {
                return new Result(false , "Not Enough Capacity!");
            }
            else {
                inventory.Items.put(items, amount);
            }
            return new Result(true , name + " Added Successfully");
        }

        itemRegistry.scanItems("model.OtherItem");
        if ((items=itemRegistry.nameToItemMap.get(name)) != null) {
            if (inventory.Items.containsKey(items)) {
                inventory.Items.compute(items , (k,v) -> v+amount);
            }
            else if (currentGame.currentPlayer.getBackPack().getType().getRemindCapacity() == 0) {
                return new Result(false , "Not Enough Capacity!");
            }
            else {
                inventory.Items.put(items, amount);
            }
            return new Result(true , name + " Added Successfully");
        }

        return new Result(false , name + " not found!");
    }



    public Result print(){

        for (int i =0 ; i< 90 ; i++)
            for (int j =0 ; j< 90 ; j++) {
                try {
                    Main.getBatch().draw(TextureManager.get("Places/Walkable.png") ,
                        TEXTURE_SIZE * i , TEXTURE_SIZE * (90 - j) , TEXTURE_SIZE , TEXTURE_SIZE);

                    if (getTileByCoordinates(i,j).getGameObject() instanceof UnWalkable) {
                        Main.getBatch().draw(TextureManager.get("Tree/unWalkable6.png"),
                            TEXTURE_SIZE * i , TEXTURE_SIZE * (90 - j) , TEXTURE_SIZE , TEXTURE_SIZE);
                    }

                    Main.getBatch().draw(getTileByCoordinates(i , j)
                       .getGameObject()
                       .getSprite(TextureManager.get(getTileByCoordinates(i , j).getGameObject().getIcon())) ,
                                     TEXTURE_SIZE * i , TEXTURE_SIZE * (90 - j) , TEXTURE_SIZE , TEXTURE_SIZE);

                    if (getTileByCoordinates(i , j).getGameObject() instanceof Lake) {
                        LakeAnimation((Lake) getTileByCoordinates(i , j).getGameObject());
                    }

                }
                catch (Exception e) {

                }
            }

        for (User player : currentGame.players)
            Main.getBatch().draw(
                TextureManager.get(player.getIcon()),
                player.getPositionX()*TEXTURE_SIZE,
                player.getPositionY()*TEXTURE_SIZE,
                64, 64);

        return null;
    }

    public void LakeAnimation(Lake lake) {
        lake.getSprite(null).setRegion(lake.getLakeAnimation().getKeyFrame(lake.getTimer()));
        if (! lake.getLakeAnimation().isAnimationFinished(lake.getTimer())) {
            lake.setTimer(lake.getTimer() + Gdx.graphics.getDeltaTime());
        }
        else {
            lake.setTimer(0);
        }
        lake.getLakeAnimation().setPlayMode(Animation.PlayMode.LOOP);
    }
    public Result checkConditionsForWalk(int goalX, int goalY){
        if (goalX <0 || goalX >=90 || goalY <0 || goalY >=90) {
            return new Result(false,"you can't walk out of bounds");
        }

        Tile tile = getTileByCoordinates(goalX, goalY);
        Farm farm = null;


        for (Farm farms : currentGame.farms) {
            if (farms.Farm.contains(tile)) {
                farm = farms;
                break;
            }
        }
        for (User user : currentGame.players) {
            if (user.getFarm().equals(farm)){
                if (user.getSpouse() != null) {
                    if (!user.getSpouse().equals(currentGame.currentPlayer) && !user.equals(currentGame.currentPlayer)) {
                        return new Result(false, "you can't go to this farm");
                    }
                }
                else {
                    if (! user.equals(currentGame.currentPlayer)) {
                        return new Result(false, "you can't go to this farm");
                    }
                }
            }
        }

        if (tile.getGameObject() instanceof GreenHouse) {
            if (!((GreenHouse) tile.getGameObject()).isCreated()){
                return new Result(false,"GreenHouse is not created yet");
            }
        }

        for (User user : currentGame.players) {
            if (user.getPositionX()==goalX && user.getPositionY()==goalY){
                return new Result(true,"you can't go to this coordinate");
            }
        }
        if (!checkTile(tile)){
            return new Result(false,"you can't go to this coordinate");
        }

//        for (Market market : currentGame.markets) {
//            if (goalX > market.getTopLeftX() && goalY > market.getTopLeftY()){
//                if (goalX < market.getTopLeftX() + market.getWidth() && goalY < market.getTopLeftY() + market.getHeight()){
//                    if (market.getMarketType().getStartHour() > currentGame.currentDate.getHour() || market.getMarketType().getEndHour() < currentGame.currentDate.getHour()){
//                        return new Result(false , "you can't go to Market because it is not open");
//                    }
//                }
//            }
//        }
        return null;

    }

    public void moveCamera(OrthographicCamera camera) {
        float cameraSpeed = 200 * Gdx.graphics.getDeltaTime(); // سرعت حرکت بر اساس زمان فریم

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            camera.translate(0, cameraSpeed);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            camera.translate(0, -cameraSpeed);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera.translate(-cameraSpeed, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            camera.translate(cameraSpeed, 0);
        }

        camera.update();
    }


    public Result showInventory() {

        Inventory inventory= currentGame.currentPlayer.getBackPack().inventory;
        StringBuilder output = new StringBuilder();
        output.append(BLUE+"\nItems"+RESET + " :").append("\n");

        for (Map.Entry <Items,Integer> entry: inventory.Items.entrySet()){
            if (entry.getKey() instanceof Food) {
                output.append("\t-> ").append(((Food) entry.getKey()).getType().getName()).append(": ").append(entry.getValue()).append('\n');
            }
            else if (entry.getKey() instanceof BasicRock){
                output.append("\t-> ").append("Stone: ").append(entry.getValue()).append('\n');
            }
            else if (entry.getKey() instanceof Wood){
                output.append("\t-> ").append("Wood: ").append(entry.getValue()).append('\n');
            }
            else if (entry.getKey() instanceof ForagingMinerals){
                output.append("\t-> ").append(((ForagingMinerals) entry.getKey()).getType().getDisplayName()).append(": ").append(entry.getValue()).append('\n');
            }
            else if (entry.getKey() instanceof ForagingSeeds){
                output.append("\t-> ").append(((ForagingSeeds) entry.getKey()).getType().getDisplayName()).append(": ").append(entry.getValue()).append('\n');
            }
            else if (entry.getKey() instanceof AllCrops){
                output.append("\t-> ").append(((AllCrops) entry.getKey()).getType().getDisplayName()).append(": ").append(entry.getValue()).append('\n');
            }
            else if (entry.getKey() instanceof ForagingCrops) {
                output.append("\t-> ").append(((ForagingCrops) entry.getKey()).getType().getDisplayName()).append(": ").append(entry.getValue()).append('\n');
            }
            else if (entry.getKey() instanceof TreeSource){
                output.append("\t-> ").append(((TreeSource) entry.getKey()).getType().getDisplayName()).append(": ").append(entry.getValue()).append('\n');
            }
            else if (entry.getKey() instanceof TreesProdct) {
                output.append("\t-> ").append(entry.getKey().getName()).append(": ").append(entry.getValue()).append('\n');
            }
            else if (entry.getKey() instanceof Axe ){
                output.append("\t-> ").append(((Axe) entry.getKey()).getType().getDisplayName()).append('\n');
            }
            else if (entry.getKey() instanceof FishingPole){
                output.append("\t-> ").append(((FishingPole) entry.getKey()).type.name()).append('\n');
            }
            else if (entry.getKey() instanceof Hoe){
                output.append("\t-> ").append(((Hoe) entry.getKey()).getType().getDisplayName()).append('\n');
            }
            else if (entry.getKey() instanceof PickAxe){
                output.append("\t-> ").append(((PickAxe) entry.getKey()).getType().getDisplayName()).append('\n');
            }
            else if (entry.getKey() instanceof WateringCan){
                output.append("\t-> ").append(((WateringCan) entry.getKey()).getType().getDisplayName()).append('\n');
            }
            else if (entry.getKey() instanceof TrashCan){
                output.append("\t-> ").append(((TrashCan) entry.getKey()).type.getDisplayName()).append('\n');
            }
            else if (entry.getKey() instanceof Tools){
                output.append("\t-> ").append(entry.getKey().getName()).append('\n');
            }
            else if (entry.getKey() instanceof MarketItem) {
                output.append("\t-> ").append(((MarketItem) entry.getKey()).getType().getName()).append(": ").append(entry.getValue()).append('\n');
            }
            else if (entry.getKey() instanceof Fish) {
                output.append("\t-> ").append(((Fish) entry.getKey()).getType().getName()).append(": ") .append(((Fish) entry.getKey()).getQuantity().getName()).append(" ").append(entry.getValue()).append('\n');
            }
            else if (entry.getKey() instanceof Animalproduct) {
                output.append("\t-> ").append(((Animalproduct) entry.getKey()).getType().getName()).append("(").append(((Animalproduct) entry.getKey()).getQuantity().getName()).append("): ").append(entry.getValue()).append('\n');
            }
            else if (entry.getKey() instanceof CraftingItem) {
                output.append("\t-> ").append(((CraftingItem) entry.getKey()).getType().getName()).append(": ").append(entry.getValue()).append('\n');
            }
            else if (entry.getKey() instanceof ArtisanProduct) {
                output.append("\t-> ").append(((ArtisanProduct) entry.getKey()).getType().getName()).append(": ").append(entry.getValue()).append('\n');
            }
            else if (entry.getKey() instanceof BarsAndOres) {
                output.append("\t-> ").append(((BarsAndOres) entry.getKey()).getType().getName()).append(": ").append(entry.getValue()).append('\n');
            }
            else if (entry.getKey() instanceof MixedSeeds) {
                output.append("Mixed Seeds : ").append(entry.getValue()).append("\n");
            }
        }

        return new Result(true,output.toString());
    }
    private Result increaseMoney(Integer amount , int price , Items items,String name , Integer reminder) {
        int percent=0;
        for (Map.Entry<Items,Integer> entry: currentGame.currentPlayer.getBackPack().inventory.Items.entrySet()) {
            if (entry.getKey() instanceof TrashCan){
                percent= ((TrashCan) entry.getKey()).type.getPercent();
                break;
            }
        }

        if (amount ==null || amount.equals(reminder)) {
            int increase=(reminder * percent *price)/100;
            TrashCan.removeItem(increase,currentGame.currentPlayer.getBackPack().inventory.Items, items, reminder);
            return new Result(true,name + " completely removed from your inventory");
        }
        if (amount > reminder) {
            return new Result(false,"not enough "+name+" "+"in your inventory for remove");
        }
        int increase = (reminder * percent * price) / 100;
        TrashCan.removeItem(increase,currentGame.currentPlayer.getBackPack().inventory.Items, items, reminder);
        return new Result(true , amount + " "+name+" "+"removed from your inventory");

    }
    public Result removeItemToTrashcan (String name, String amount){
        Integer number=null;
        if (amount != null) {
            number = Integer.parseInt(amount.trim());
        }

        Inventory inventory=currentGame.currentPlayer.getBackPack().inventory;
        for (Map.Entry<Items,Integer> entry: inventory.Items.entrySet()){

//            if (entry.getKey() instanceof Wood){
//                if (name.equals(Wood.name)) {
//                    return increaseMoney(number, Wood.price, (Wood) entry.getKey(), name, entry.getValue());
//                }
//            }
//            if (entry.getKey() instanceof BasicRock){
//                if (name.equals("Stone")) {
//                    return increaseMoney(number, BasicRock.price, (BasicRock) entry.getKey(), name, entry.getValue());
//                }
//            }
//
//            if (entry.getKey() instanceof ForagingMinerals){
//                if (((ForagingMinerals) entry.getKey()).getType().getDisplayName().equals(name)){
//                    return increaseMoney(number,((ForagingMinerals) entry.getKey()).getType().getPrice(),entry.getKey(), name,entry.getValue());
//                }
//            }
//
//
//            if (entry.getKey() instanceof AllCrops){
//                if (((AllCrops) entry.getKey()).getType().getDisplayName().equals(name)){
//                    return increaseMoney(number, ((AllCrops) entry.getKey()).getType().getPrice(), entry.getKey(), name,entry.getValue());
//                }
//            }
//
//            if (entry.getKey() instanceof ForagingCrops){
//                if (((ForagingCrops) entry.getKey()).getType().getDisplayName().equals(name)){
//                    return increaseMoney(entry.getValue(),((ForagingCrops) entry.getKey()).getType().getPrice(), entry.getKey(), name,entry.getValue());
//                }
//            }
//            if (entry.getKey() instanceof Tools){
//                return new Result(false,"you can't remove "+name+"because it is a tool");
//            }

            if (entry.getKey().getName().equals(name)) {
                if (entry.getKey().getSellPrice() == 0) {
                    continue;
                }
                return increaseMoney(number , entry.getKey().getSellPrice() , entry.getKey() , name , entry.getValue()) ;
            }

        }
        return new Result(false , name + " not found");
    }

                                                                // Fish


    public Result addFishToInventory(FishingPole fishingPole) {
        Inventory inventory=currentGame.currentPlayer.getBackPack().inventory;
        double random = Math.random() + 0.3;
        int x = (int) (random * currentGame.currentWeather.getFishing() * (currentGame.currentPlayer.getLevelFishing() + 3));
        int numberOfFish = Math.min(6, x);
        StringBuilder result = new StringBuilder("number of Fishes: " + numberOfFish + "\n");
        ArrayList<Fish> fishes = new ArrayList<>();

        for (int i = 0; i < numberOfFish; i++) {

            double rand = Math.random() + 0.4;
            double quantity = (rand * (currentGame.currentPlayer.getLevelFishing() + 2) *
                    fishingPole.type.getCoefficient()) / (7 - 2*currentGame.currentWeather.getFishing());
            Quantity fishQuantity = productQuantity(quantity);

            if (fishingPole.type.equals(FishingPoleType.TrainingRod)) {

                switch (currentGame.currentDate.getSeason()) {
                    case Spring:
                        Fish springFish = new Fish(FishType.Herring, fishQuantity);
                        fishes.add(springFish);
                        result.append(springFish.getType().getName()).append(" ").append(springFish.getQuantity().getName()).append("\n");
                        break;
                    case Summer:
                        Fish summerFish = new Fish(FishType.Sunfish, fishQuantity);
                        fishes.add(summerFish);
                        result.append(summerFish.getType().getName()).append(" ").append(summerFish.getQuantity().getName()).append("\n");
                        break;
                    case Fall:
                        Fish fallFish = new Fish(FishType.Sardine, fishQuantity);
                        fishes.add(fallFish);
                        result.append(fallFish.getType().getName()).append(" ").append(fallFish.getQuantity().getName()).append("\n");
                        break;
                    case Winter:
                        Fish winterFish = new Fish(FishType.Perch, fishQuantity);
                        fishes.add(winterFish);
                        result.append(winterFish.getType().getName()).append(" ").append(winterFish.getQuantity().getName()).append("\n");
                        break;
                    default:
                        break;
                }

            }

            else if (rand <= 0.2 || ( rand > 0.8 && rand <= 0.85 && currentGame.currentPlayer.getLevelFishing()!=4) ){

                switch (currentGame.currentDate.getSeason()) {
                    case Spring:
                        Fish springFish = new Fish(FishType.Flounder, fishQuantity);
                        fishes.add(springFish);
                        result.append(springFish.getType().getName()).append(" ").append(springFish.getQuantity().getName()).append("\n");
                        break;
                    case Summer:
                        Fish summerFish = new Fish(FishType.Tilapia, fishQuantity);
                        fishes.add(summerFish);
                        result.append(summerFish.getType().getName()).append(" ").append(summerFish.getQuantity().getName()).append("\n");
                        break;
                    case Fall:
                        Fish fallFish = new Fish(FishType.Salmon, fishQuantity);
                        fishes.add(fallFish);
                        result.append(fallFish.getType().getName()).append(" ").append(fallFish.getQuantity().getName()).append("\n");
                        break;
                    case Winter:
                        Fish winterFish = new Fish(FishType.Midnight_Carp, fishQuantity);
                        fishes.add(winterFish);
                        result.append(winterFish.getType().getName()).append(" ").append(winterFish.getQuantity().getName()).append("\n");
                    default:
                        break;
                }
            }
            else if (rand <= 0.4 || (rand > 0.85 && rand <= 0.9 && currentGame.currentPlayer.getLevelFishing() != 4)) {

                switch (currentGame.currentDate.getSeason()) {
                    case Spring:
                        Fish springFish = new Fish(FishType.Lionfish, fishQuantity);
                        fishes.add(springFish);
                        result.append(springFish.getType().getName()).append(" ").append(springFish.getQuantity().getName()).append("\n");
                        break;
                    case Summer:
                        Fish summerFish = new Fish(FishType.Dorado, fishQuantity);
                        fishes.add(summerFish);
                        result.append(summerFish.getType().getName()).append(" ").append(summerFish.getQuantity().getName()).append("\n");
                        break;
                    case Fall:
                        Fish fallFish = new Fish(FishType.Sardine, fishQuantity);
                        fishes.add(fallFish);
                        result.append(fallFish.getType().getName()).append(" ").append(fallFish.getQuantity().getName()).append("\n");
                        break;
                    case Winter:
                        Fish winterFish = new Fish(FishType.Squid, fishQuantity);
                        fishes.add(winterFish);
                        result.append(winterFish.getType().getName()).append(" ").append(winterFish.getQuantity().getName()).append("\n");
                        break;
                    default:
                        break;
                }
            }
            else if (rand <= 0.6 || (rand > 0.9 && rand <= 0.95 && currentGame.currentPlayer.getLevelFishing() != 4)) {

                switch (currentGame.currentDate.getSeason()) {
                    case Spring:
                        Fish springFish = new Fish(FishType.Herring, fishQuantity);
                        fishes.add(springFish);
                        result.append(springFish.getType().getName()).append(" ").append(springFish.getQuantity().getName()).append("\n");
                        break;
                    case Summer:
                        Fish summerFish = new Fish(FishType.Sunfish, fishQuantity);
                        fishes.add(summerFish);
                        result.append(summerFish.getType().getName()).append(" ").append(summerFish.getQuantity().getName()).append("\n");
                        break;
                    case Fall:
                        Fish fallFish = new Fish(FishType.Shad, fishQuantity);
                        fishes.add(fallFish);
                        result.append(fallFish.getType().getName()).append(" ").append(fallFish.getQuantity().getName()).append("\n");
                        break;
                    case Winter:
                        Fish winterFish = new Fish(FishType.Tuna, fishQuantity);
                        fishes.add(winterFish);
                        result.append(winterFish.getType().getName()).append(" ").append(winterFish.getQuantity().getName()).append("\n");
                        break;
                    default:
                        break;
                }

            }
            else if (rand <= 0.8 || (rand > 0.95 && currentGame.currentPlayer.getLevelFishing() != 4)) {

                switch (currentGame.currentDate.getSeason()) {
                    case Spring:
                        Fish springFish = new Fish(FishType.Ghostfish, fishQuantity);
                        fishes.add(springFish);
                        result.append(springFish.getType().getName()).append(" ").append(springFish.getQuantity().getName()).append("\n");
                        break;
                    case Summer:
                        Fish summerFish = new Fish(FishType.Rainbow_Trout, fishQuantity);
                        fishes.add(summerFish);
                        result.append(summerFish.getType().getName()).append(" ").append(summerFish.getQuantity().getName()).append("\n");
                        break;
                    case Fall:
                        Fish fallFish = new Fish(FishType.Blue_Discus, fishQuantity);
                        fishes.add(fallFish);
                        result.append(fallFish.getType().getName()).append(" ").append(fallFish.getQuantity().getName()).append("\n");
                        break;
                    case Winter:
                        Fish winterFish = new Fish(FishType.Perch, fishQuantity);
                        fishes.add(winterFish);
                        result.append(winterFish.getType().getName()).append(" ").append(winterFish.getQuantity().getName()).append("\n");
                        break;
                    default:
                        break;
                }

            }
            else {
                if (currentGame.currentPlayer.getLevelFishing() == 4){

                    switch (currentGame.currentDate.getSeason()){
                        case Spring:
                            Fish springFish= new Fish(FishType.Legend,fishQuantity);
                            fishes.add(springFish);
                            result.append(springFish.getType().getName()).append(" ").append(springFish.getQuantity().getName()).append("\n");
                            break;
                        case Summer:
                            Fish summerFish= new Fish(FishType.Dorado,fishQuantity);
                            fishes.add(summerFish);
                            result.append(summerFish.getType().getName()).append(" ").append(summerFish.getQuantity().getName()).append("\n");
                            break;
                        case Fall:
                            Fish fallFish= new Fish(FishType.Squid,fishQuantity);
                            fishes.add(fallFish);
                            result.append(fallFish.getType().getName()).append(" ").append(fallFish.getQuantity().getName()).append("\n");
                            break;
                        case Winter:
                            Fish winterFish= new Fish(FishType.Tuna,fishQuantity);
                            fishes.add(winterFish);
                            result.append(winterFish.getType().getName()).append(" ").append(winterFish.getQuantity().getName()).append("\n");
                            break;
                    }

                }
            }
        }

        boolean top=currentGame.currentPlayer.getLevelFishing() == 4;
        currentGame.currentPlayer.increaseHealth(-Math.min ( ((FishingPole) currentGame.currentPlayer.currentTool).type.costEnergy(top) , currentGame.currentPlayer.getHealth()) ) ;
        currentGame.currentPlayer.increaseFishingAbility(5);
        for (Fish fish : fishes) {
            if (inventory.Items.containsKey(fish)) {
                inventory.Items.compute(fish , (k,v) -> v+1);
            }
            else if (currentGame.currentPlayer.getBackPack().getType().getRemindCapacity() !=0) {
                inventory.Items.put(fish , 1);
            }
        }

        return new Result(true, result.toString());
    }
    public Result Fishing(String fishingPoleType) {
        if (!(currentGame.currentPlayer.currentTool instanceof FishingPole)) {
            return new Result(false, "your current tool is not a FishingPole!");
        }

        if (!checkCoordinateForFishing()) {
            boolean top=currentGame.currentPlayer.getLevelFishing() == 4;
            currentGame.currentPlayer.increaseHealth(-Math.min ( ((FishingPole) currentGame.currentPlayer.currentTool).type.costEnergy(top) , currentGame.currentPlayer.getHealth()) ) ;
            return new Result(false, "you can't fishing because lake is not around you");
        }
        if (isFishingPoleTypeExist(fishingPoleType) == null) {
            return new Result(false, "No such fishing pole exist!");
        }

        return addFishToInventory(isFishingPoleTypeExist(fishingPoleType));
    }


    public Result pet(String petName) {
        int [] x={1,1,1,0,0,-1,-1,-1};
        int [] y={1,0,-1,1,-1,-1,0,1};

        for (int i = 0; i < 8; i++) {
            Tile tile = getTileByCoordinates(currentGame.currentPlayer.getPositionX() + x[i], currentGame.currentPlayer.getPositionY() + y[i]);
            if (tile.getGameObject() instanceof Animal) {
                Animal animal = (Animal) tile.getGameObject();
                if (animal.getName().equals(petName)) {
                    animal.increaseFriendShip(15);
                    animal.setPetToday(true);
                    return new Result(true, petName + " petted successfully!");
                }
            }
        }
        return new Result(false,petName+"  doesn't exist!");
    }
    public Result animals() {
        StringBuilder result= new StringBuilder();
        for (BarnOrCage barnOrCage : currentGame.currentPlayer.BarnOrCages) {
            for (Animal animal : barnOrCage.animals){
                result.append(animal.getName())
                        .append(" Friendship: ").append(animal.getFriendShip()).append('\n')
                        .append(" petToday: ").append(animal.isPetToday()).append('\n')
                        .append("feedToday: ").append(animal.isFeedToday()).append("\n");
            }
        }
        return new Result(true, result.toString());
    }
    public Result shepherdAnimals(String x1, String y1, String name) {

        int goalX=Integer.parseInt(x1);
        int goalY=Integer.parseInt(y1);

        if (checkShepherdAnimals(goalX , goalY , name) != null) {
            return checkShepherdAnimals(goalX , goalY , name);
        }


        Animal animal = getAnimalByName(name);
        if (animal.getType().equals(AnimalType.pig) && currentGame.currentDate.getSeason().equals(Season.Winter)) {
            return new Result(false , "Pigs can't go out because we are in winter");
        }
        Walkable walkable=new Walkable();

        int [] x = {1,1,1,0,0,-1,-1,-1};
        int [] y = {1,0,-1,1,-1,-1,0,1};
        Queue<Tile> queue = new LinkedList<>();
        Set<Tile> tiles = new HashSet<>();
        for (int i = 0; i < 8; i++) {
            if (checkTileForAnimalWalking(animal.getPositionX() + x[i] , animal.getPositionY() + y[i] )) {
                queue.add(getTileByCoordinates(animal.getPositionX() + x[i] , animal.getPositionY() + y[i]));
            }
        }
        tiles.add(getTileByCoordinates(animal.getPositionX() , animal.getPositionY() ));

        while (!queue.isEmpty()) {
            Tile tile=queue.poll();
            tiles.add(tile);
            if (tile.getX() == goalX && tile.getY() == goalY) {
                tile.setGameObject(animal);
                getTileByCoordinates(animal.getPositionX(), animal.getPositionY() ).setGameObject(walkable);
                animal.setPositionX(goalX);
                animal.setPositionY(goalY);
                return eatFiberByAnimal(animal);
            }

            for (int i = 0; i < 8; i++) {
                if (! checkTileForAnimalWalking(tile.getX() + x[i] , tile.getY() + y[i] ) ) {
                    continue;
                }
                if (getTileByCoordinates(tile.getX() + x[i] , tile.getY() + y[i]) == null) {
                    continue;
                }
                if (tiles.contains(getTileByCoordinates(tile.getX() + x[i] , tile.getY() + y[i]))) {
                    continue;
                }
                queue.add(getTileByCoordinates(tile.getX() + x[i], tile.getY() + y[i]));
            }
        }
        return new Result(false , "there is no way for animal to go to this coordinate!");
    }
    public Result eatFiberByAnimal(Animal animal) {
        int [] x = {1,1,1,0,0,-1,-1,-1};
        int [] y = {1,0,-1,1,-1,-1,0,1};

        for (int i = 0; i < 8; i++) {
            Tile tile = getTileByCoordinates(animal.getPositionX() + x[i] , animal.getPositionY() + y[i]);
            if (tile == null) {
                continue;
            }
            if (tile.getGameObject() instanceof Walkable) {
                if (((Walkable) tile.getGameObject()).getGrassOrFiber().equals("Fiber") ||
                    ((Walkable) tile.getGameObject()).getGrassOrFiber().equals("Grass")) {
                    tile.setGameObject(new Walkable());
                    animal.setFeedToday(true);
                }
            }
        }
        if (animal.isFeedToday()) {
            return new Result(true , BLUE+animal.getName() + " is fed successfully"+RESET);
        }
        return new Result(true , RED+animal.getName() + " Shepherd successfully"+RESET);
    }
    private Result checkShepherdAnimals(int goalX, int goalY, String name) {
        if (goalX < 0 || goalX >90 || goalY < 0 || goalY >90) {
            return new Result(false , "you can't shepherd animals out of bounds!");
        }
        Tile tile = getTileByCoordinates(goalX , goalY );
        if (!(tile.getGameObject() instanceof Walkable)) {
            return new Result(false , "yot can't shepherd animals on this coordinate!");
        }
        if (currentGame.currentWeather.equals(Weather.Snowy) || currentGame.currentWeather.equals(Weather.Rainy) || currentGame.currentWeather.equals(Weather.Stormy) ) {
            return new Result(false , "The weather conditions isn't suitable");
        }

        Animal animal=null;

        for (BarnOrCage barnOrCage : currentGame.currentPlayer.BarnOrCages) {
            for (Animal animal1 : barnOrCage.animals) {
                if (animal1.getName().equals(name)) {
                    animal = animal1;
                    break;
                }
            }
        }

        if (animal == null) {
            return new Result(false , "animal not found!");
        }

        return null;

    }


    public Result feedHay(String name) {
        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;
        Animal animal=getAnimalByName(name);
        if (animal==null) {
            return new Result(false , "animal not found!");
        }
        MarketItem marketItem = new MarketItem(MarketItemType.Hay);
        if (inventory.Items.containsKey(marketItem)) {
            inventory.Items.compute(marketItem , (k,v) -> v-1);
            inventory.Items.entrySet().removeIf(entry -> entry.getValue()==0);
            animal.setFeedToday(true);
            return new Result(true, BLUE+"you fed "+name+" successfully!" + RESET);
        }

        return new Result(false , RED + "You don't have Hay in your inventiry!" + RESET);
    }
    public Result cheatSetFriendship( String name , Integer amount ) {
        Animal animal=getAnimalByName(name);
        if (animal==null) {
            return new Result(false , "animal not found!");
        }
        if (amount > 1000 || amount <0) {
            return new Result(false , RED+ "You should say a number between 1 and 1000" + RESET);
        }
        animal.increaseFriendShip(amount - animal.getFriendShip());
        return new Result(true, "friendship cheated successfully!");
    }
    public Result getProductAnimals(String name) {
        Animal animal=getAnimalByName(name);

        if (! animal.isFeedPreviousDay()){
            return new Result(false , "No Product because you didn't feed " + animal.getName() + " in previous day");
        }
        if (animal.isProductCollected()) {
            return new Result(false , "Product was collected before");
        }
        if (! checkPeriod(animal)) {
            return new Result(false , "It's not time yet. This animal isn't ready to produce again");
        }
        if ( ! isNeighbor(currentGame.currentPlayer.getPositionX() , currentGame.currentPlayer.getPositionY() , animal.getPositionX() , animal.getPositionY())) {
            return new Result(false , "The animal is not in Neighbor Tile");
        }

        if (animal.getType().equals(AnimalType.cow) || animal.getType().equals(AnimalType.goat) || animal.getType().equals(AnimalType.sheep)) {
            if (sheepOrGoatOrCow(animal) == null) {
                return sheepOrGoatOrCow(animal);
            }
        }


        double Quantity=((double) animal.getFriendShip() / 1000) * (0.5 * (1 + animal.getRandomProduction()));
        Quantity quantity=productQuantity(Quantity);

        Animalproduct animalproduct = new Animalproduct(animal.getProductType(), quantity);
        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;
        if (inventory.Items.containsKey(animalproduct)) {
            inventory.Items.compute(animalproduct , (k,v) -> v+1);
        }
        else {
            inventory.Items.put(animalproduct, 1);
        }

        animal.setProductCollected(true);

        return new Result(true , "product "+ animal.getProductType().getName() + "collected successfully");
    }
    public Result produces() {
        StringBuilder result=new StringBuilder();
        result.append("Produces :\n");
        for (BarnOrCage barnOrCage : currentGame.currentPlayer.BarnOrCages) {
            for (Animal animal : barnOrCage.animals) {
                result.append(animal.getName()).append(",  Remaining Produces: ");
                if (animal.isFeedPreviousDay() && checkPeriod(animal) && ! animal.isProductCollected()) {
                    result.append(animal.getProductType().getName()).append(", ");

                    double Quantity=((double) animal.getFriendShip() / 1000) * (0.5 * (1 + animal.getRandomProduction()));
                    Quantity quantity=productQuantity(Quantity);

                    result.append("Quantity: ").append(quantity.getName());

                }
                result.append("\n");
            }
        }
        return new Result(true , result.toString());
    }
    public Result sheepOrGoatOrCow(Animal animal) {
        if (animal.getType().equals(AnimalType.sheep) ) {
            if (!(currentGame.currentPlayer.currentTool instanceof Shear)) {
                return new Result(false , "for collect wool you should use shear");
            }
            currentGame.currentPlayer.increaseMoney( (int) (4 * currentGame.currentWeather.getEnergyCostCoefficient()));
            animal.increaseFriendShip(5);
        }
        if (animal.getType().equals(AnimalType.goat)) {
            if (!(currentGame.currentPlayer.currentTool instanceof MilkPail)) {
                return new Result(false , "for collect milk you should use MilkPail");
            }
            currentGame.currentPlayer.increaseMoney((int) (4 * currentGame.currentWeather.getEnergyCostCoefficient()));
            animal.increaseFriendShip(5);
        }
        if (animal.getType().equals(AnimalType.cow)) {
            if (!(currentGame.currentPlayer.currentTool instanceof MilkPail)) {
                return new Result(false , "for collect milk you should use MilkPail");
            }
            currentGame.currentPlayer.increaseMoney((int) (4 * currentGame.currentWeather.getEnergyCostCoefficient()));
            animal.increaseFriendShip(5);
        }

        return null;
    }

    public Result removeAnimal(Animal animal) {
        for (BarnOrCage barnOrCage : currentGame.currentPlayer.BarnOrCages) {
            for (Animal animal2 : barnOrCage.animals) {
                if (animal.equals(animal2)) {
                    barnOrCage.getAnimals().remove(animal2);
                    return new Result(true , animal.getName() + " was sold successfully");
                }
            }
        }
        return null;
    }
    public Result sellAnimal(String name) {
        Animal animal=getAnimalByName(name);

        if (animal == null) {
            return new Result(false , "Animal not found");
        }
        for (Tile tile : currentGame.bigMap) {
            if (tile.getGameObject().equals(animal)) {
                tile.setGameObject(new Walkable());
                break;
            }
        }
        double x = animal.getFriendShip()/1000 + 0.3;
        currentGame.currentPlayer.increaseMoney((int) (animal.getType().getPrice() * x) );
        return removeAnimal(animal);

    }


    private Result placeBomb(Tile tile , String name , Items items) {
        if (currentGame.currentPlayer.getFarm().isInHome(tile.getX(), tile.getY())) {
            return new Result(false , "you can't place Bomb in your Home because it is dangerous");
        }

        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;
        int domain=0;
        CraftType Bomb=null;

        for (CraftType craftType:CraftType.values()) {
            if (craftType.name().equals(name)) {
                Bomb=craftType;
                break;
            }
        }

        assert Bomb != null;
        if (Bomb.equals(CraftType.CherryBomb)) {
            domain=3;
        }
        if (Bomb.equals(CraftType.Bomb)) {
            domain=5;
        }
        if (Bomb.equals(CraftType.MegaBomb)) {
            domain=7;
        }

        int x= tile.getX();
        int y= tile.getY();

        for (int i=x ; i < x + domain ; i++) {
            for (int j=y ; j < y + domain ; j++) {
                Tile target=getTileByCoordinates(i,j);
                if (target == null) {
                    continue;
                }
                if (target.getGameObject() instanceof Tree) {
                    target.setGameObject(new Walkable());
                }
                else if (target.getGameObject() instanceof ForagingCrops) {
                    target.setGameObject(new Walkable());
                }
                else if (target.getGameObject() instanceof GiantProduct) {
                    target.setGameObject(new Walkable());
                }
                else if (target.getGameObject() instanceof ForagingSeeds) {
                    target.setGameObject(new Walkable());
                }
            }
        }
        inventory.Items.compute(items , (k,v) -> v-1);
        inventory.Items.entrySet().removeIf(entry -> entry.getValue() == null || entry.getValue() ==0);

        return new Result(true , "Bomb successfully replaced and everything destroyed");
    }
    private Result placeScarecrow(Tile tile , String name, Items items) {

        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;
        if (currentGame.currentPlayer.getFarm().isInHome(currentGame.currentPlayer.getPositionX(), currentGame.currentPlayer.getPositionY())) {
            return new Result(false , "you can't place "+name+" in your Home because it is not for this place");
        }

        if (name.equals("Scarecrow")) {
            tile.setGameObject(items);
            inventory.Items.compute(items , (k,v) -> v-1);
            inventory.Items.entrySet().removeIf(entry -> entry.getValue() == null || entry.getValue() == 0);
            return new Result(true , "Scarecrow successfully placed");
        }
        else  {
            tile.setGameObject(items);
            inventory.Items.compute(items , (k,v) -> v-1);
            inventory.Items.entrySet().removeIf(entry -> entry.getValue() == null || entry.getValue() == 0);
            return new Result(true , "Deluxe Scarecrow successfully placed");
        }
    }
    private Result placeOther(Items items , Tile tile) {
        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;

        if (items instanceof CraftingItem) {
            if (((CraftingItem) items).getType().equals(CraftType.BeeHouse)) {

                if (currentGame.currentPlayer.getFarm().isInHome(tile.getX(), tile.getY())) {
                    return new Result(false , "you should place Bee House in Farm!");
                }
                tile.setGameObject(items);
                inventory.Items.compute(items , (k,v) -> v-1);
                if (inventory.Items.get(items) == 0) {
                    inventory.Items.remove(items);
                }
                return new Result(true , "Item placed Successfully");
            }

            tile.setGameObject(items);
            inventory.Items.compute(items , (k,v) -> v-1);
            if (inventory.Items.get(items) == 0) {
                inventory.Items.remove(items);
            }
            return new Result(true , "Item placed Successfully");

        }
        return new Result(false, RED + "Item not found" + RESET);
    }
    public Result placeItem(String name, String dir) {
        int direction=Integer.parseInt(dir);
        if (direction < 1 || direction >8) {
            return new Result(false , "invalid direction");
        }

        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;
        Tile tile=getTileByDir(direction);
        if (tile == null) {
            return new Result(false , "you can't place Item on this Tile");
        }

        Farm farm=null;
        for (Farm farms : currentGame.farms) {
            if (farms.Farm.contains(tile)) {
                farm = farms;
            }
        }

        for (User user: currentGame.players) {
            if (user.getFarm().equals(farm)) {
                if (!user.equals(currentGame.currentPlayer) && ! user.getSpouse().equals(currentGame.currentPlayer) ) {
                    return new Result(false , "you can't place Item on this Tile");
                }
            }
        }

        if (farm == null) {
            return new Result(false , "you can't place Item on this Tile");
        }

        if (!(tile.getGameObject() instanceof Walkable) ){
            return new Result(false , "You can't place Item on this Tile");
        }

        if (tile.getGameObject() instanceof Walkable && !((Walkable) tile.getGameObject()).getGrassOrFiber().equals("Walk")) {
            return new Result(false , "You can't place Item on this Tile");
        }


        if (name.equals("Mystic Tree Seed")) {
            //TODO
        }

        Items items= CraftingController.numberOfIngrediants(name);
        if (items == null) {
            return new Result(false , name + "not found!");
        }
        switch (name) {
            case "Grass Starter" -> {
                ((Walkable) tile.getGameObject()).setGrassOrFiber("Grass");
                return new Result(true, "Grass Starter placed successfully");
            }
            case "Chery Bomb", "Bomb", "Mega Bomb" -> {
                return placeBomb(tile, name, items);
            }
            case "Quality Sprinkler", "Sprinkler", "Iridium Sprinkler" -> {
                return placeOther(items, tile);
            }
            case "Scarecrow","Deluxe Scarecrow" -> {
                return placeScarecrow(tile, name, items);
            }
            case "Furnace","Charcoal Klin","Bee House","Cheese Press","Keg","Loom","Mayonnaise Machine","Oil Maker","Preserves Jar","Dehydrator" ->{
                return placeOther(items, tile);
            }

        }
        return new Result(false , "Something went wrong");
    }
    public Result ArtisanUse(String artisanName , String first , String second) {
        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;
        String newArtistName=artisanName.replace('_',' ');

        boolean found=false;

        for (ArtisanType artisan : ArtisanType.values()) {
            if (artisan.getCraftType().getName().equals(newArtistName)) {
                found=true;
                break;
            }
        }
        if (! found) {
            return new Result(false , "No such type Of Crafting!");
        }
        CraftingItem craftingItem=isNeighborWithCrafting(newArtistName.trim());

        if (craftingItem==null) {
            return new Result(false , "you can't use this Crafting because you are not close to it");
        }


        if (second !=null) {
            second=second.trim();
        }

        for (ArtisanType artisanType : ArtisanType.values()) {
            if (artisanType.getCraftType().equals(craftingItem.getType())) {

                if (artisanType.checkIngredient(first.trim(), second)) {
                    artisanType.creatArtesian(first.trim(), craftingItem);
                    return new Result(true , "you use "+newArtistName+" successfully");
                }
            }
        }
        return new Result(false , "Not enough ingredient for use");

    }


                                                                    // Ario
    public Result ArtisanGetProduct(String name) {
        int [] dirx={0,0,1,1,1,-1,-1,-1};
        int [] diry={1,-1,0,1,-1,0,1,-1};

        Inventory inventory= currentGame.currentPlayer.getBackPack().inventory;
        if (currentGame.currentPlayer.getBackPack().getType().getRemindCapacity() ==0) {
            return new Result(false , "you can't get product because your backpack is full");
        }
        Items items=null;
        StringBuilder result=new StringBuilder();
        result.append("Produces:").append('\n');

        for (int x = 0 ; x<dirx.length ; x++) {
            Tile tile=getTileByCoordinates(currentGame.currentPlayer.getPositionX() + dirx[x],currentGame.currentPlayer.getPositionY() + diry[x]);
                if (tile.getGameObject() instanceof CraftingItem) {
                    if (((CraftingItem) tile.getGameObject()).getName().equals(name)) {
                        Map <Items , DateHour> map=((CraftingItem) tile.getGameObject()).getBuffer();
                        for (Map.Entry <Items , DateHour> entry : map.entrySet()) {
                            for (ArtisanType artisan : ArtisanType.values()) {
                                if (artisan.getName().equals(entry.getKey().getName()) ) {
                                    if (DateHour.getHourDifferent(entry.getValue()) >= artisan.getTakesTime()){
                                        entry.setValue(null);
                                        if (inventory.Items.containsKey(entry.getKey())) {
                                            inventory.Items.compute(entry.getKey(), (k, v) -> v + 1);
                                        }
                                        else {
                                            inventory.Items.put(entry.getKey(), 1);
                                        }
                                        result.append(BLUE + entry.getKey().getName() +RESET);
                                    }
                                }
                            }
                        }

                        map.entrySet().removeIf(entry -> entry.getValue() == null);
                    }
                }
            }

        return new Result(false , result.toString());
    }
    public Result sell(String name , Integer amount) {
        ShippingBin shippingBin=ShippingBin.isNearShippingBin();
        if (shippingBin == null ) {
            return new Result(false , "you are not near shipping bin");
        }

        Inventory inventory=currentGame.currentPlayer.getBackPack().inventory;
        ArrayList<Fish> fishes=new ArrayList<>();
        ArrayList<Animalproduct> animalproducts=new ArrayList<>();

        int cursor=0;

        for (Map.Entry <Items,Integer> entry : inventory.Items.entrySet() ) {
            if (entry.getKey() instanceof Fish) {
                if (((Fish) entry.getKey()).getType().getName().equals(name)) {
                    Fish fish=new Fish(((Fish) entry.getKey()).getType() , ((Fish) entry.getKey()).getQuantity());
                    fishes.add((Fish) entry.getKey());
                    if (amount == -1) {
                        shippingBin.binContents.put(entry.getKey(), entry.getValue());
                        inventory.Items.compute(fish, (k, v) -> 0);
                        continue;
                    }
                    if (cursor >= amount) {
                        break;
                    }
                    else if (cursor + entry.getValue() <= amount) {
                        shippingBin.binContents.put(entry.getKey(), entry.getValue());
                        cursor += entry.getValue();
                        inventory.Items.compute(fish, (k, v) -> 0);
                    }
                    else if (cursor + entry.getValue() > amount) {
                        shippingBin.binContents.put(entry.getKey(), entry.getValue() + cursor - amount);
                        int finalCursor = cursor;
                        inventory.Items.compute(fish, (k, v) -> v - amount + finalCursor);
                        cursor=amount;
                    }
                }
            }
            if (entry.getKey() instanceof Animalproduct) {
                if (((Animalproduct) entry.getKey()).getType().getName().equals(name)) {
                    animalproducts.add((Animalproduct) entry.getKey());
                    Animalproduct animalproduct = new Animalproduct(((Animalproduct) entry.getKey()).getType() , ((Animalproduct) entry.getKey()).getQuantity());
                    if (amount == -1) {
                        shippingBin.binContents.put(entry.getKey(), entry.getValue());
                        inventory.Items.compute(animalproduct, (k, v) -> 0);
                        continue;
                    }
                    if (cursor >= amount) {
                        break;
                    }
                    else if (cursor + entry.getValue() <= amount) {
                        inventory.Items.compute(animalproduct, (k, v) -> 0);
                        shippingBin.binContents.put(entry.getKey(), entry.getValue());
                        cursor += entry.getValue();
                    }
                    else if (cursor + entry.getValue() > amount) {
                        shippingBin.binContents.put(entry.getKey(), entry.getValue() + cursor - amount);
                        int finalCursor1 = cursor;
                        inventory.Items.compute(animalproduct , (k, v) -> v - amount + finalCursor1);
                        cursor=amount;
                    }
                }
            }
        }
        inventory.Items.entrySet().removeIf(entry -> entry.getValue() == 0);

        if (fishes.isEmpty() && animalproducts.isEmpty()) {
            return new Result(false , name + " not found!");
        }

        return new Result(true , BLUE +"your money will increase tomorrow" + RESET);

    }
    public Result backToMainMenu () {
        if (App.currentUser.isCurrently_in_game())
            return new Result(false, RED+"You Are Currently in a Game!"+RESET);
        else {
            currentMenu = com.Graphic.model.Enum.Menu.MainMenu;
            return new Result(true, GREEN+"Returned to Main Menu."+RESET);
        }
    }


    public void startNewGame (String input) {

        currentGame = new Game();
        //currentGame.currentMenu = currentMenu;


//        String user1name = GameMenuCommands.makeNewGame.getMatcher(input).group("username1");
//        String user2name = GameMenuCommands.makeNewGame.getMatcher(input).group("username2"); // could be null
//        String user3name = GameMenuCommands.makeNewGame.getMatcher(input).group("username3");// could be null
//
//        User user1 = findUserByUsername(user1name);
//        User user2 = findUserByUsername(user2name);
//        User user3 = findUserByUsername(user3name);
//
//        if (user1 == null){
//            System.out.println("User1 Not Found!");
//            return;
//        }
//        if (user2name != null) {
//            if (user2 == null) {
//                System.out.println("User2 Not Found! Try Again.");
//                return;
//            }
//        }
//        if (user3name != null) {
//            if (user3 == null) {
//                System.out.println("User3 Not Found! Try Again.");
//                return;
//            }
//        }
//        if (user1.isCurrently_in_game()){
//            System.out.println("User1 Currently in Game! Try Again.");
//            return;
//        }
//        else user1.setCurrently_in_game(true);
//
//        if (user2name != null) {
//            if (findUserByUsername(user2name).isCurrently_in_game()) {
//                System.out.println("User2 Currently in Game! Try Again.");
//                return;
//            }
//            else user2.setCurrently_in_game(true);
//        }
//        if (user3name != null) {
//            if (findUserByUsername(user3name).isCurrently_in_game()) {
//                System.out.println("User3 Currently in Game! Try Again.");
//                return;
//            }
//            else user3.setCurrently_in_game(true);
//        }
//
//        if (user1.getUsername().equals(currentUser.getUsername())) {
//            System.out.println(RED+"Invite Users Other than Yourself! Try Again."+RESET);
//            return;
//        }
//        if (user2 != null) {
//            if (user2.getUsername().equals(currentUser.getUsername())) {
//                System.out.println(RED+"Invite Users Other than Yourself! Try Again."+RESET);
//                return;
//            }
//        }
//        if (user3 != null) {
//            if (user3.getUsername().equals(currentUser.getUsername())) {
//                System.out.println(RED+"Invite Users Other than Yourself! Try Again."+RESET);
//                return;
//            }
//        }
//        currentGame.players.add(currentUser);
//        currentGame.currentPlayer = currentUser;
//        System.out.println(RED+"player selected"+RESET);
//        setTimeAndWeather();

//        currentGame.players.add(findUserByUsername(user1name));
//        if (user2name != null) currentGame.players.add(findUserByUsername(user2name));
//        if (user3name != null) currentGame.players.add(findUserByUsername(user3name));
        currentGame.players.add(new User("Ario", "Ario", "ario.ebr@gmail.com", "male", 0, 200, PasswordHashUtil.hashPassword("Ebrahim84?"), SecurityQuestions.FavoriteAnimal, "dog"));
        currentGame.players.add(new User("Erfan", "Erfan", "ario.ebr@gmail.com", "female", 0, 200, PasswordHashUtil.hashPassword("Ebrahim84?"), SecurityQuestions.FavoriteAnimal, "dog"));
        currentGame.players.add(new User("Mamali", "Mamali", "ario.ebr@gmail.com", "male", 0, 200, PasswordHashUtil.hashPassword("Ebrahim84?"), SecurityQuestions.FavoriteAnimal, "dog"));
        currentGame.players.add(new User("Ilia", "Ilia", "ario.ebr@gmail.com", "male", 0, 200, PasswordHashUtil.hashPassword("Ebrahim84?"), SecurityQuestions.FavoriteAnimal, "dog"));
        setTimeAndWeather();
        currentGame.currentPlayer = currentGame.players.getFirst();
        currentUser = currentGame.players.getFirst();


        // done



        int counter = 1;
        for (User user: currentGame.players) {

            currentGame.currentPlayer = user;
            while (true) {

//                System.out.println(currentPlayer.getUsername() + "'s turn to choose map(1 or 2)");
//                String choiceString = scanner.nextLine();
//                String[] splitChoice = choiceString.trim().split("\\s+");
//
//                int choice;
//                try {
//                    choice = Integer.parseInt(splitChoice[2]);
//                } catch (Exception e) {
//                    System.out.println("Please put a integer between 1 and 2!");
//                    continue;
//                }
//                if (choice != 1 && choice != 2) {
//                    System.out.println("Choose between 1 and 2!");
//                    continue;
//                }

                int choice = 1; // TODO باید پاک بشه

                if (counter == 1) {
                    user.setIcon("all image/Crops/Cactus_Stage_6.png");
//                    user.setIcon(BRIGHT_CYAN + "∆ " + RESET);
                    user.topLeftX = 0;
                    user.topLeftY = 0;
                }
                else if (counter == 2) {
//                    user.setIcon(BRIGHT_PURPLE + "∆ " + RESET);
                    user.setIcon("all image/Special_item/Cursed_Mannequin_%28F%29.png");
                    user.topLeftX = 1;
                    user.topLeftY = 0;
                }
                else if (counter == 3) {
                    user.setIcon("all image/Special_item/Deconstructor.png");
//                    user.setIcon(BRIGHT_RED + "∆ " + RESET);
                    user.topLeftX = 0;
                    user.topLeftY = 1;
                }
                else if (counter == 4) {
                    user.setIcon("all image/Special_item/Wood_Chipper_On.png");
//                    user.setIcon(BRIGHT_YELLOW + "∆ " + RESET);
                    user.topLeftX = 1;
                    user.topLeftY = 1;
                }
                createInitialFarm(choice);
                counter++;
                break;
            }
        }
        currentGame.currentPlayer = currentGame.players.getFirst();



        // Form Friendships
        for (int i = 0; i < currentGame.players.size(); i++) {
            for (int j = i + 1; j < currentGame.players.size(); j++) {
                HumanCommunications f = new HumanCommunications(currentGame.players.get(i), currentGame.players.get(j));
                currentGame.friendships.add(f);
            }
        }
        // set initial Cooking Recipes from beginning
        for (User player: currentGame.players) {
            player.setRecipes(Recipe.createAllRecipes());
        }
        buildHall();
        buildNpcVillage();
        sortMap(currentGame.bigMap);
        initializePlayer();
        startDay();
    }
    public void startNewGame1 (String input) throws IOException {

        currentGame = new Game();
        currentGame.currentPlayer = currentUser;
        currentGame.currentMenu = currentMenu;


        String user1name = GameMenuCommands.makeNewGame.getMatcher(input).group("username1");
        String user2name = GameMenuCommands.makeNewGame.getMatcher(input).group("username2"); // could be null
        String user3name = GameMenuCommands.makeNewGame.getMatcher(input).group("username3");// could be null

        User user1 = findUserByUsername(user1name);
        User user2 = findUserByUsername(user2name);
        User user3 = findUserByUsername(user3name);

        if (user1 == null){
            System.out.println("User1 Not Found!");
            return;
        }
        if (user2name != null) {
            if (user2 == null) {
                System.out.println("User2 Not Found!");
                return;
            }
        }
        if (user3name != null) {
            if (user3 == null) {
                System.out.println("User3 Not Found!");
                return;
            }
        }
        if (user1.isCurrently_in_game()){
            System.out.println("User1 Currently in Game!");
            return;
        }
        else user1.setCurrently_in_game(true);

        if (user2name != null) {
            if (findUserByUsername(user2name).isCurrently_in_game()) {
                System.out.println("User2 Currently in Game!");
                return;
            }
            else user2.setCurrently_in_game(true);
        }
        if (user3name != null) {
            if (findUserByUsername(user3name).isCurrently_in_game()) {
                System.out.println("User3 Currently in Game!");
                return;
            }
            else user3.setCurrently_in_game(true);
        }

        if (user1.getUsername().equals(currentUser.getUsername())) {
            System.out.println(RED+"Invite Users Other than Yourself!"+RESET);
            return;
        }
        if (user2 != null) {
            if (user2.getUsername().equals(currentUser.getUsername())) {
                System.out.println(RED+"Invite Users Other than Yourself!"+RESET);
                return;
            }
        }
        if (user3 != null) {
            if (user3.getUsername().equals(currentUser.getUsername())) {
                System.out.println(RED+"Invite Users Other than Yourself!"+RESET);
                return;
            }
        }


        currentGame.players.add(currentUser);
        currentGame.players.add(user1);
        if (user2 != null) currentGame.players.add(user2);
        if (user3 != null) currentGame.players.add(user2);
        currentGame.currentPlayer = currentUser;
        setTimeAndWeather();

        Scanner scanner = new Scanner(System.in);

        int counter = 1;
        for (User user: currentGame.players) {

            currentGame.currentPlayer = user;
            while (true) {

                System.out.println(currentGame.currentPlayer.getNickname() + "'s turn to choose map(1 or 2)");
                String choiceString = scanner.nextLine();
                String[] splitChoice = choiceString.trim().split("\\s+");

                int choice;
                try {
                    choice = Integer.parseInt(splitChoice[2]);
                } catch (Exception e) {
                    System.out.println("Please Use an Integer between 1 and 2!");
                    continue;
                }
                if (choice != 1 && choice != 2) {
                    System.out.println("Choose between 1 and 2!");
                    continue;
                }


                if (counter == 1) {
                    user.setIcon(BRIGHT_CYAN + "∆ " + RESET);
                    user.topLeftX = 0;
                    user.topLeftY = 0;
                }
                else if (counter == 2) {
                    user.setIcon(BRIGHT_PURPLE + "∆ " + RESET);
                    user.topLeftX = 1;
                    user.topLeftY = 0;
                }
                else if (counter == 3) {
                    user.setIcon(BRIGHT_RED + "∆ " + RESET);
                    user.topLeftX = 0;
                    user.topLeftY = 1;
                }
                else if (counter == 4) {
                    user.setIcon(BRIGHT_YELLOW + "∆ " + RESET);
                    user.topLeftX = 1;
                    user.topLeftY = 1;
                }
                createInitialFarm(choice);
                counter++;
                break;
            }
        }
        currentGame.currentPlayer = currentGame.players.getFirst();

        // Form Friendships
        for (int i = 0; i < currentGame.players.size(); i++) {
            for (int j = i + 1; j < currentGame.players.size(); j++) {
                HumanCommunications f = new HumanCommunications(currentGame.players.get(i), currentGame.players.get(j));
                currentGame.friendships.add(f);
            }
        }
        // set initial Cooking Recipes from beginning
        for (User player: currentGame.players) {
            player.setRecipes(Recipe.createAllRecipes());
        }
        buildHall();
        buildNpcVillage();
        sortMap(currentGame.bigMap);
        initializePlayer();
        startDay();
    }


                                                                    // Erfan

                                                                  // input command Date
    public Result showTime () {
        return new Result(true, BLUE +"Time : "+RESET
                + currentGame.currentDate.getHour()+ ":00");
    }
    public Result showDate () {
        return new Result(true, BLUE+"Date : "+RED+currentGame.currentDate.getYear()+RESET+" "+currentGame.currentDate.getNameSeason()+" "+currentGame.currentDate.getDate());
    }
    public Result showSeason   () {

        return new Result(true, currentGame.currentDate.getNameSeason());
    }
    public Result showWeather  (boolean isToday) {

        if (isToday)
            return new Result(true, currentGame.currentWeather.getDisplayName());
        else
            return new Result(true, currentGame.tomorrowWeather.getDisplayName());
    }
    public Result setWeather   (String type) {

        Weather weather;
        try {
            weather = Weather.valueOf(type);
        } catch (Exception e) {
            return new Result(false, RED+"Weather type is incorrect!"+RESET);
        }
        currentGame.tomorrowWeather = weather;
        return new Result(true, BLUE+"Tomorrow weather change to "+RESET+currentGame.tomorrowWeather.getDisplayName());
    }
    public Result setEnergy    (String amount) {

        if (currentGame.currentPlayer.isHealthUnlimited())
            return new Result(false, BLUE+"You're unstoppable! Energy level: ∞"+RESET);

        if (amount.charAt(0) == '-')
            return new Result(false, RED+"Energy must be a positive number!"+RESET);
        int amount2;
        try {
            amount2 = Integer.parseInt(amount);
        } catch (Exception e) {
            return new Result(false, RED+"Number is incorrect!"+RESET);
        }

        if (currentGame.currentPlayer.getHealth() > amount2) {
            currentGame.currentPlayer.setHealth(amount2);
            return new Result(true, BLUE+"Your Energy decreased to : " +RESET+amount2);
        }
        else if (currentGame.currentPlayer.getHealth() < amount2) {
            currentGame.currentPlayer.setHealth(amount2);
            return new Result(true, BLUE+"Your Energy increased to : " +RESET+amount2);
        } else
            return new Result(false, "Your energy level at this moment is this amount.");
    }
    public Result showDateTime () {
        return new Result(true, BLUE+"Time : "+RED+ currentGame.currentDate.getHour()+ ":00" +
                BLUE+"\nData : "+RED+currentGame.currentDate.getYear()+RESET+" "+currentGame.currentDate.getNameSeason()+" "+currentGame.currentDate.getDate());
    }
    public Result showDayOfWeek() {
        return new Result(true, BLUE+"Day of Week : "+RESET
                + currentGame.currentDate.getDayOfTheWeek());
    }
    public Result increaseHour (String hour) {

        if (hour.charAt(0) == '-')
            return new Result(false, RED+"The time must be a positive number!"+RESET);
        int amount;
        try {
            amount = Integer.parseInt(hour);
        } catch (Exception e) {
            return new Result(false, RED+"Time is incorrect!"+RESET);
        }
        passedOfTime(0, amount);
        return new Result(true, BLUE+"Time change to : "+GREEN+ currentGame.currentDate.getHour()+":00"+RESET);
    }
    public Result increaseDate (String date) {

        if (date.charAt(0) == '-')
            return new Result(false, RED+"The time must be a positive number!"+RESET);
        int amount;
        try {
            amount = Integer.parseInt(date);
        } catch (Exception e) {
            return new Result(false, RED+"Time is incorrect!"+RESET);
        }
        passedOfTime(amount, 0);
        return new Result(true, BLUE+"Date change to : "+RED+currentGame.currentDate.getYear()+RESET+" "+currentGame.currentDate.getNameSeason()+" "+currentGame.currentDate.getDate());
    }
    public Result showEnergy () {

        if (currentGame.currentPlayer.isHealthUnlimited())
            return new Result(false, BRIGHT_BLUE + "Your energy is unlimited" + RESET);
        return new Result(true,BRIGHT_BLUE+"Your energy is : "+currentGame.currentPlayer.getHealth()+RESET);
    }
    public Result EnergyUnlimited () {

        currentGame.currentPlayer.setHealthUnlimited();
        return new Result(true, BLUE+"Whoa! Infinite energy mode activated!"+RESET);
    }

                                                                   // input command plant
    public Result planting (String name, String direction) {

        if (directionIncorrect(direction))
            return new Result(false, RED+"Direction is invalid"+RESET);

        int dir = Integer.parseInt(direction);

        Tile tile = getTileByDir(dir);

        if ((!currentGame.currentPlayer.getFarm().isInFarm(tile.getX(), tile.getY())) &&
                !currentGame.currentPlayer.getSpouse().getFarm().isInFarm(tile.getX(), tile.getY()))
            return new Result(false, RED+"You must select your tile"+RESET);

        if (name.matches("\\s*(?i)Mixed\\s*seed(s)?\\s*"))
            return plantMixedSeed(dir);

        try {
            ForagingSeedsType type = ForagingSeedsType.fromDisplayName(name.trim());
            return plantForagingSeed(type, dir);
        } catch (Exception e) {
            try {
                TreesSourceType type2 = TreesSourceType.fromDisplayName(name.trim());
                return plantTree(type2, dir);
            } catch (Exception e2) {
                return new Result(false, RED+"Hmm... that seed name doesn’t seem right!"+RESET);
            }
        }
    }
    public Result WateringPlant (String direction) {

        if (directionIncorrect(direction))
            return new Result(false, RED+"Direction is invalid"+RESET);

        int dir = Integer.parseInt(direction);

        if (!(currentGame.currentPlayer.currentTool instanceof WateringCan))
            return new Result(false, RED+"سطل اب رو بردار دوست من"+RESET);

        if (!currentGame.currentPlayer.isHealthUnlimited())
            currentGame.currentPlayer.increaseHealth(currentGame.currentPlayer.currentTool.healthCost());

        Tile tile = getTileByDir(dir);
        GameObject object = tile.getGameObject();

        if ((!currentGame.currentPlayer.getFarm().isInFarm(tile.getX(), tile.getY())) &&
                !currentGame.currentPlayer.getSpouse().getFarm().isInFarm(tile.getX(), tile.getY()))
            return new Result(false, RED+"You must select your tile"+RESET);

        if (((WateringCan) currentGame.currentPlayer.currentTool).getReminderCapacity() < 1)
            return new Result(false, RED+"ظرفت خالیه مشتی"+RESET);

        if (object instanceof ForagingSeeds) {

            ((WateringCan) currentGame.currentPlayer.currentTool).decreaseWater(1);
            ((ForagingSeeds) object).setLastWater(currentGame.currentDate);
            return new Result(true, BLUE+"The plant has been watered!\uD83C\uDF31"+RESET);

        }
        else if (object instanceof Tree) {

            ((WateringCan) currentGame.currentPlayer.currentTool).decreaseWater(1);
            ((Tree) object).setLastWater(currentGame.currentDate);
            return new Result(true, BLUE+"The plant has been watered!\uD83C\uDF31"+RESET);

        }
        else if (object instanceof GiantProduct) {

            ((WateringCan) currentGame.currentPlayer.currentTool).decreaseWater(1);
            ((GiantProduct) object).setLastWater(currentGame.currentDate);
            return new Result(true, BLUE+"The plant has been watered!\uD83C\uDF31"+RESET);
        }

        return new Result(false, RED+"No plant in here!"+RESET);
    }
    public Result showFruitInfo (String name) {

        TreesProductType type;

        try {
            type = TreesProductType.fromDisplayName(name);
            return new Result(true, TreesProductType.getInformation(type));

        } catch (Exception e) {

            CropsType cropType;
            try {
                cropType = CropsType.fromDisplayName(name);
                return new Result(true, CropsType.getInformation(cropType));

            } catch (Exception e1) {
                return new Result(false, RED+"sorry, name is invalid!"+RESET);
            }
        }
    }
    public Result thor (String x, String y) {

        int x1 = Integer.parseInt(x);
        int y1 = Integer.parseInt(y);

        if ((!currentGame.currentPlayer.getFarm().isInFarm(x1, y1)) && currentGame.currentPlayer.getSpouse() != null
                && !currentGame.currentPlayer.getSpouse().getFarm().isInFarm(x1, y1))
            return new Result(false, RED+"You must select your tile"+RESET);

        if (isInGreenHouse(getTileByCoordinates(x1, y1)))
            return new Result(false, RED+"Lightning can’t hit the greenhouse"+RESET);

        lightningStrike(getTileByCoordinates(x1, y1));
        return new Result(true, BLUE+"A lightning bolt hits!"+RESET);
    }
    public Result buildGreenHouse () {

        if (currentGame.currentPlayer.getFarm().getGreenHouse().isCreated())
            return new Result(false, BRIGHT_BLUE+"The greenhouse has been build!"+RESET);

        if (!checkAmountProductAvailable(new Wood(), GreenHouse.requiredWood))
            return new Result(false, RED+"You don't have enough wood!"+RESET);

        if (currentGame.currentPlayer.getMoney() < GreenHouse.requiredCoins )
            return new Result(false, RED+"You don't have enough Coin!"+RESET);

        currentGame.currentPlayer.increaseMoney(-GreenHouse.requiredCoins);
        advanceItem(new Wood(), -GreenHouse.requiredWood);

        currentGame.currentPlayer.getFarm().getGreenHouse().setCreated(true);

        return new Result(true, RED + "-500 wood  -1000 Coin"+BLUE+"\nThe greenhouse has been built! \uD83C\uDF31"+RESET);
    }
    public Result info (String name) {

        TreeType treeType;
        try {
            treeType = TreeType.fromDisplayName(name);
            return new Result(true, TreeType.getInformation(treeType));

        } catch (Exception e1) {

            ForagingCropsType foragingCropsType;
            try {
                foragingCropsType = ForagingCropsType.fromDisplayName(name);
                return new Result(true, ForagingCropsType.getInformation(foragingCropsType));
            } catch (Exception e2) {
                return new Result(false, RED+"Name is incorrect!"+RESET);
            }
        }
    }
    public Result showPlant (String xNumber, String yNumber) {

        int x = Integer.parseInt(xNumber);
        int y = Integer.parseInt(yNumber);

        if (!currentGame.currentPlayer.getFarm().isInFarm(x, y))
            return new Result(false, RED+"Pick from your own farm!"+RESET);

        Tile tile = getTileByCoordinates(x, y);

        if (tile.getGameObject() instanceof Tree)
            return new Result(true, showTree((Tree) tile.getGameObject()));
        if (tile.getGameObject() instanceof ForagingSeeds)
            return new Result(true, showForaging((ForagingSeeds) tile.getGameObject()));
        if (tile.getGameObject() instanceof GiantProduct)
            return new Result(true, showGiant((GiantProduct) tile.getGameObject()));

        if ((!currentGame.currentPlayer.getFarm().isInFarm(tile.getX(), tile.getY())) &&
                !currentGame.currentPlayer.getSpouse().getFarm().isInFarm(tile.getX(), tile.getY()))
            return new Result(false, RED+"You must select your tile"+RESET);

        return new Result(false, RED+"That tile don't have plant!"+RESET);

    }
    public Result fertilize (String fertilizeType, String direction) {

        if (directionIncorrect(direction))
            return new Result(false, RED+"Direction is invalid"+RESET);

        int dir = Integer.parseInt(direction);
        Tile tile = getTileByDir(dir);

        if (!checkTileForPlant(tile))
            return new Result(false, RED+"This tile has no plants!"+RESET);

        MarketItemType type;

        try {
            type = MarketItemType.fromDisplayName(fertilizeType);
        } catch (Exception e) {
            return new Result(false, RED+"Fertilize type is invalid"+RESET);
        }
        if (!checkAmountProductAvailable(new MarketItem(type), 1))
            return new Result(false, RED+"You don't have enough "+type.getName()+RESET);

        if ((!currentGame.currentPlayer.getFarm().isInFarm(tile.getX(), tile.getY())) &&
                !currentGame.currentPlayer.getSpouse().getFarm().isInFarm(tile.getX(), tile.getY()))
            return new Result(false, RED+"You must select your tile"+RESET);

        advanceItem(new MarketItem(type), -1);
        fertilizePlant(type, tile);
        return new Result(true, BLUE+"The plant has been fertilized! ✨"+RESET);
    }

                                                                   // input tools command
    public Result howMuchWater () {

        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;

        for (Map.Entry <Items,Integer> entry: inventory.Items.entrySet())
            if (entry instanceof WateringCan)
                return new Result(true, BLUE+"Water Remaining : "
                        +RESET+((WateringCan) entry).getReminderCapacity());

        return new Result(false, BLUE+"کدوم سطل سلطان"+RESET);
    }
    public Result toolsEquip (String name) {

        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;

        for (Map.Entry<Items,Integer> entry: inventory.Items.entrySet()) {

            if (entry.getKey() instanceof Axe && name.equals("Axe")) {
                currentGame.currentPlayer.currentTool = (Tools) entry.getKey();
                return new Result(true, BRIGHT_BLUE+"Axe successfully picked up"+RESET);
            }
            else if (entry.getKey() instanceof FishingPole && name.equals("FishingPole")){
                currentGame.currentPlayer.currentTool = (Tools) entry.getKey();
                return new Result(true, BRIGHT_BLUE+"FishingPole successfully picked up"+RESET);
            }
            else if (entry.getKey() instanceof Hoe && name.equals("Hoe")){
                currentGame.currentPlayer.currentTool = (Tools) entry.getKey();
                return new Result(true, BRIGHT_BLUE+"Hoe successfully picked up"+RESET);
            }
            else if (entry.getKey() instanceof PickAxe && name.equals("PickAxe")){
                currentGame.currentPlayer.currentTool = (Tools) entry.getKey();
                return new Result(true, BRIGHT_BLUE+"PickAxe successfully picked up"+RESET);
            }
            else if (entry.getKey() instanceof WateringCan && name.equals("WateringCan")){
                currentGame.currentPlayer.currentTool = (Tools) entry.getKey();
                return new Result(true, BRIGHT_BLUE+"WateringCan successfully picked up"+RESET);
            }
            else if (entry.getKey() instanceof MilkPail && name.equals("MilkPail")){
                currentGame.currentPlayer.currentTool = (Tools) entry.getKey();
                return new Result(true, BRIGHT_BLUE+"MilkPail successfully picked up"+RESET);
            }
            else if (entry.getKey() instanceof Scythe && name.equals("Scythe")){
                currentGame.currentPlayer.currentTool = (Tools) entry.getKey();
                return new Result(true, BRIGHT_BLUE+"Scythe successfully picked up"+RESET);
            }
            else if (entry.getKey() instanceof Shear && name.equals("Shear")){
                currentGame.currentPlayer.currentTool = (Tools) entry.getKey();
                return new Result(true, BRIGHT_BLUE+"Shear successfully picked up"+RESET);
            }
        }
        return new Result(false,"there is no such tool");
    }
    public Result showCurrentTool() {

        Tools currentTool = currentGame.currentPlayer.currentTool;

        return switch (currentTool) {

            case Axe axe -> new Result(true, "current tool: " + axe.getType().getDisplayName());
            case null    -> new Result(false, "there is no current tool in your hands");
            case Hoe hoe -> new Result(true, "current tool: " + hoe.getType().getDisplayName());
            case MilkPail milkPail -> new Result(true, "current tool: " + milkPail.getName());
            case Scythe scythe -> new Result(true, "current tool: " + scythe.getName());
            case Shear shear   -> new Result(true, "current tool: " + shear.getName());
            case PickAxe pickAxe -> new Result(true, "current tool: " +
                    pickAxe.getType().getDisplayName());
            case FishingPole fishingPole -> new Result(true, "current tool: " +
                    fishingPole.type.getName());
            case WateringCan wateringCan -> new Result(true, "current tool: " +
                    wateringCan.getType().getDisplayName());
            default -> new Result(true, "current tool: " + currentTool.getName());
        };
    }
    public Result availableTools() {

        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;
        StringBuilder result = new StringBuilder();

        for (Map.Entry<Items, Integer> entry : inventory.Items.entrySet()) {

            if (entry.getKey() instanceof Axe)
                 result.append(BRIGHT_BLUE).append(((Axe) entry.getKey()).getType().getDisplayName()).append(RESET).append("\n");

            else if (entry.getKey() instanceof FishingPole)
                result.append(BRIGHT_RED).append(((FishingPole) entry.getKey()).type).append(RESET).append("\n");

            else if (entry.getKey() instanceof Hoe)
                result.append(BRIGHT_PURPLE).append(((Hoe) entry.getKey()).getType().getDisplayName()).append(RESET).append("\n");

            else if (entry.getKey() instanceof WateringCan)
                result.append(BRIGHT_GREEN).append(((WateringCan) entry.getKey()).getType().getDisplayName()).append(RESET).append("\n");

            else if (entry.getKey() instanceof PickAxe)
                result.append(BRIGHT_CYAN).append(((PickAxe) entry.getKey()).getType().getDisplayName()).append(RESET).append("\n");

            else if (entry.getKey() instanceof Tools)
                result.append(BRIGHT_BROWN).append(((Tools) entry.getKey()).getName()).append(RESET).append("\n");
        }
        if (result.isEmpty())
            return new Result(false, RED + "You don't have any tools" + RESET);
        return new Result(true, result.toString());
    }
    public Result upgradeTool (String name) {
         MarketType marketType=MarketType.wallOrDoor(currentGame.currentPlayer.getPositionX() , currentGame.currentPlayer.getPositionY());
         if (marketType!=MarketType.Blacksmith) {
             return new Result(false , "you are not in BlackSmith Market. please go there");
         }
         Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;

         if ( name.equals("Axe") ) {
             for (Map.Entry<Items, Integer> entry : inventory.Items.entrySet()) {
                 if (entry.getKey() instanceof Axe) {
                     AxeType axeType = AxeType.getNextType(((Axe) entry.getKey()).getType());
                     if (axeType == null) {
                         return new Result(false , name + "is at top level");
                     }
                     else if (AxeType.checkIngredient(axeType)) {
                         ((Axe) entry.getKey()).setType(axeType);
                         currentGame.currentPlayer.increaseMoney( - axeType.getPrice());
                         return new Result(true , name + "updated successfully");
                     }
                     else {
                         return new Result(false , "Not enough ingredient or money");
                     }
                 }
             }
         }

        if ( name.equals("Hoe") ) {
            for (Map.Entry<Items, Integer> entry : inventory.Items.entrySet()) {
                if (entry.getKey() instanceof Hoe) {
                    HoeType hoeType=HoeType.getNextType(((Hoe) entry.getKey()).getType());
                    if (hoeType == null) {
                        return new Result(false , name + "is at top level");
                    }
                    else if (HoeType.checkIngredient(hoeType)) {
                        ((Hoe) entry.getKey()).setType(hoeType);
                        currentGame.currentPlayer.increaseMoney( - hoeType.getPrice());
                        return new Result(true , name + "updated successfully");
                    }
                    else {
                        return new Result(false , "Not enough ingredient or money");
                    }
                }
            }
        }

        if ( name.equals("PickAxe") ) {
            for (Map.Entry<Items, Integer> entry : inventory.Items.entrySet()) {
                if (entry.getKey() instanceof PickAxe) {
                    PickAxeType pickAxeType=PickAxeType.getPickAxeType(((PickAxe) entry.getKey()).getType());
                    if (pickAxeType == null) {
                        return new Result(false , name + "is at top level");
                    }
                    else if (PickAxeType.checkIngredient(pickAxeType)) {
                        ((PickAxe) entry.getKey()).setType(pickAxeType);
                        currentGame.currentPlayer.increaseMoney( - pickAxeType.getPrice());
                        return new Result(true , name + "updated successfully");
                    }
                    else {
                        return new Result(false , "Not enough ingredient or money");
                    }
                }
            }
        }

        if ( name.equals("WateringCan") ) {
            for (Map.Entry<Items, Integer> entry : inventory.Items.entrySet()) {
                if (entry.getKey() instanceof WateringCan) {
                    WateringCanType wateringCanType=WateringCanType.getWateringCanType(((WateringCan) entry.getKey()).getType());
                    if (wateringCanType == null) {
                        return new Result(false , name + "is at top level");
                    }
                    else if (WateringCanType.checkIngredient(wateringCanType)) {
                        ((WateringCan) entry.getKey()).setType(wateringCanType);
                        currentGame.currentPlayer.increaseMoney( - wateringCanType.getPrice());
                        return new Result(true , name + "updated successfully");
                    }
                    else {
                        return new Result(false , "Not enough ingredient or money");
                    }
                }
            }
        }

        if ( name.equals("TrashCan") ) {
            for (Map.Entry<Items, Integer> entry : inventory.Items.entrySet()) {
                if (entry.getKey() instanceof TrashCan) {
                    TrashCanType trashCanType = TrashCanType.nextTrashCanType(((TrashCan) entry.getKey()).type);
                    if (trashCanType == null) {
                        return new Result(false , name + "is at top level");
                    }
                    else if (TrashCanType.checkIngredient(trashCanType)) {
                        ((TrashCan) entry.getKey()).setType(trashCanType);
                        currentGame.currentPlayer.increaseMoney( - trashCanType.getPrice());
                        return new Result(true , name + "updated successfully");
                    }
                    else {
                        return new Result(false , "Not enough ingredient or money");
                    }
                }
            }
        }

        return null;
    }
    public Result useTools (String direction) {

        if (!currentGame.currentPlayer.isHealthUnlimited()) {
            if (currentGame.currentPlayer.getHealth() < currentGame.currentPlayer.currentTool.healthCost())
                return new Result(false, RED+"you are not in your hand"+RESET);

            currentGame.currentPlayer.increaseHealth(currentGame.currentPlayer.currentTool.healthCost());
        }

        System.out.println("Energy : "+currentGame.currentPlayer.getHealth());

        if (directionIncorrect(direction))
            return new Result(false, RED+"Direction is invalid"+RESET);

        int dir = Integer.parseInt(direction);

        Tools tools = currentGame.currentPlayer.currentTool;

        if (currentGame.currentPlayer.currentTool == null)
            return new Result(false, RED + "please pick up a tools" + RESET);

        if (tools instanceof Axe)
            return useAxe(dir);
        else if (tools instanceof Hoe) {
            if (currentGame.currentPlayer.currentTool.healthCost() > 0 && currentGame.currentPlayer.Buff_farming_hoursLeft > 0) currentGame.currentPlayer.increaseHealth(1);
            return useHoe(dir);
        } else if (tools instanceof MilkPail) {
            if (currentGame.currentPlayer.currentTool.healthCost() > 0 && currentGame.currentPlayer.Buff_farming_hoursLeft > 0) currentGame.currentPlayer.increaseHealth(1);
            return useMilkPail(dir);
        } else if (tools instanceof Scythe) {
            if (currentGame.currentPlayer.currentTool.healthCost() > 0 && currentGame.currentPlayer.Buff_farming_hoursLeft > 0) currentGame.currentPlayer.increaseHealth(1);
            return useScythe(dir);
        }
        else if (tools instanceof Shear) {
            if (currentGame.currentPlayer.currentTool.healthCost() > 0 && currentGame.currentPlayer.Buff_farming_hoursLeft > 0) currentGame.currentPlayer.increaseHealth(1);
            return useShear(dir);
        }
        else if (tools instanceof WateringCan)
            return useWateringCan(dir);
        else if (tools instanceof PickAxe)
            return usePickAxe(dir);

        return new Result(false, RED + "please pick up a tools" + RESET);
    }

                                                                    // input NPC command
    public Result meetNPC (String name) {

        NPC npc;
        try {
            npc = NPC.valueOf(name);
        } catch (Exception e) {
            return new Result(false, RED+"You're looking for someone who isn't real"+RESET);
        }

        if (!npc.isInHisHome(currentGame.currentPlayer.getPositionX(), currentGame.currentPlayer.getPositionY()))
            return new Result(false, RED+"You should go to their place first"+RESET);

        if (!currentGame.currentPlayer.getTodayTalking(npc)) {
            currentGame.currentPlayer.setTodayTalking(npc,true);
            currentGame.currentPlayer.increaseFriendshipPoint(npc, 20);
        }

        return new Result(true, npc.getName() + " : " + BLUE +
                npc.getDialogue(currentGame.currentPlayer.getFriendshipLevel(npc), currentGame.currentWeather)+RESET);
    }
    public Result giftNPC (String name, String itemName) {

        NPC npc;
        try {
            npc = NPC.valueOf(name);
        } catch (Exception e) {
            return new Result(false, RED+"You're looking for someone who isn't real"+RESET);
        }

        if (!npc.isInHisHome(currentGame.currentPlayer.getPositionX(), currentGame.currentPlayer.getPositionY()))
            return new Result(false, RED+"You should go to their place first"+RESET);

        Items item = AllFromDisplayNames(itemName);

        if (item == null)
            return new Result(false, RED+"You can only gift items from the market, crops and fruit"+RESET);

        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;

        if (!inventory.Items.containsKey(item))
            return new Result(false, RED+"You don't have this item"+RESET);

        if (item instanceof Tools)
            return new Result(false, RED + "you can't gift tools" + RESET);
        advanceItem(item, -1);

        if (!currentGame.currentPlayer.getTodayGifting(npc)) {

            if (npc.isItFavorite(item))
                currentGame.currentPlayer.increaseFriendshipPoint(npc, 200);
            else
                currentGame.currentPlayer.increaseFriendshipPoint(npc, 50);
        } else {

            if (npc.isItFavorite(item))
                currentGame.currentPlayer.increaseFriendshipPoint(npc, 50);
            else
                currentGame.currentPlayer.increaseFriendshipPoint(npc, 15);
        }
        currentGame.currentPlayer.setTodayGifting(npc, true);
        return new Result(false, BRIGHT_BLUE+"Your gift successfully sent to "
                + BRIGHT_GREEN + npc.getName() + RESET);
    }
    public Result questsNPCList () {

        StringBuilder sb = new StringBuilder();

        sb.append("+").append(BRIGHT_PURPLE).append("-".repeat(100 - 2)).append(RESET).append("+\n");

        for (NPC npc : NPC.values())
            sb.append(OneNPCQuestsList(npc));

        sb.append("+").append(BRIGHT_PURPLE).append("-".repeat(100 - 2)).append(RESET).append("+");

        return new Result(true, sb.toString());
    }
    public Result friendshipNPCList () {

        StringBuilder sb = new StringBuilder();

        sb.append("+").append(RED).append("-".repeat(60 - 2)).append(RESET).append("+\n");

        for (NPC npc : NPC.values())
            sb.append(OneNPCFriendshipList(npc));

        sb.append(RED + "|").append(" ".repeat(60 - 2)).append("|\n").
                append(RESET).append("+").append(RED).append("-".repeat(60 - 2)).append(RESET).append("+");

        return new Result(true, sb.toString());
    }
    public Result doQuest (String name, String index) {

        int ID;
        try {
            ID = Integer.parseInt(index);
        } catch (Exception e) {
            return new Result(false, RED+"Index is invalid"+RESET);
        }

        NPC npc;
        try {
            npc = NPC.valueOf(name);
        } catch (Exception e) {
            return new Result(false, RED+"You're looking for someone who isn't real"+RESET);
        }
        if (!npc.isInHisHome(currentGame.currentPlayer.getPositionX(), currentGame.currentPlayer.getPositionY()))
            return new Result(false, RED+"You should go to their place first"+RESET);

        return switch (ID) {
            case 1 -> doTask1(npc);
            case 2 -> doTask2(npc);
            case 3 -> doTask3(npc);
            default -> new Result(false, RED + "Index is invalid" + RESET);
        };
    }


                                                                    // cheat command

    public Result getObject (String direction) {

        return new Result(true, PURPLE + getTileByDir(Integer.parseInt(direction)).getGameObject().toString() + RESET);
    }
    public Result getObject2 (String x, String y) {

        return new Result(true, PURPLE + getTileByCoordinates(Integer.parseInt(x), Integer.parseInt(y)).getGameObject().toString() + RESET);
    }
    public void remove (int x) {

        getTileByDir(x).setGameObject(new Walkable());
    }
    public void plow () {

        for (User user : currentGame.players)
            for (Tile tile : user.getFarm().Farm)
                if (tile.getGameObject() instanceof Walkable ) {
                    Walkable walkable = new Walkable();
                    walkable.setGrassOrFiber("Plowed");
                    tile.setGameObject(walkable);
                }
    }
    public void clear () {

        for (User user : currentGame.players)
            for (Tile tile : user.getFarm().Farm)
                if (tile.getGameObject() instanceof Walkable ||
                        tile.getGameObject() instanceof Tree || tile.getGameObject() instanceof BasicRock)
                    tile.setGameObject(new Walkable());
        Walkable walkable = new Walkable();
        walkable.setGrassOrFiber("Plowed");
        getTileByCoordinates(7, 10).setGameObject(walkable);
    }
    public void plantCreator () {

        clear();

        for (int i = 25; i < 28; i++)
            for (int j = 22; j < 25; j++)
                getTileByCoordinates(i,j).setGameObject(new Tree(TreeType.OakTree, currentGame.currentDate.clone()));

        for (int i = 21; i < 24; i++)
            for (int j = 22; j < 25; j++)
                getTileByCoordinates(i,j).setGameObject(new ForagingCrops(ForagingCropsType.CrystalFruit));

        for (int i = 17; i < 20; i++)
            for (int j = 22; j < 25; j++)
                getTileByCoordinates(i,j).setGameObject(new ForagingSeeds(ForagingSeedsType.AncientSeeds, currentGame.currentDate.clone()));


    }
}
