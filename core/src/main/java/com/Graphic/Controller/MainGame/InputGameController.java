package com.Graphic.Controller.MainGame;

import com.Graphic.Main;
import com.Graphic.View.GameMenus.GameMenu;
import com.Graphic.View.GameMenus.MarketMenu;
import com.Graphic.model.*;
import com.Graphic.model.Animall.Animal;
import com.Graphic.model.Enum.AllPlants.*;
import com.Graphic.model.Enum.Direction;
import com.Graphic.model.Enum.GameTexturePath;
import com.Graphic.model.Enum.Door;
import com.Graphic.model.Enum.ItemType.*;
import com.Graphic.model.Enum.NPC;
import com.Graphic.model.Enum.SecurityQuestions;
import com.Graphic.model.Enum.ToolsType.*;
import com.Graphic.model.HelpersClass.Result;
import com.Graphic.model.HelpersClass.SFX;
import com.Graphic.model.HelpersClass.SFXManager;
import com.Graphic.model.HelpersClass.TextureManager;
import com.Graphic.model.MapThings.*;
import com.Graphic.model.OtherItem.*;
import com.Graphic.model.Places.*;
import com.Graphic.model.Plants.*;
import com.Graphic.model.Animall.BarnOrCage;
import com.Graphic.model.Enum.Commands.GameMenuCommands;
import com.Graphic.model.Enum.WeatherTime.Season;
import com.Graphic.model.Enum.WeatherTime.Weather;
import com.Graphic.model.Plants.Tree;
import com.Graphic.model.SaveData.PasswordHashUtil;
import com.Graphic.model.ToolsPackage.*;
import com.Graphic.model.Weather.DateHour;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;

import java.awt.*;
import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static com.Graphic.Controller.MainGame.GameControllerLogic.*;

import static com.Graphic.View.GameMenus.GameMenu.camera;
import static com.Graphic.View.GameMenus.MarketMenu.*;
import static com.Graphic.model.App.*;
import static com.Graphic.model.HelpersClass.Color_Eraser.*;
import static com.Graphic.model.HelpersClass.TextureManager.TEXTURE_SIZE;
import static com.Graphic.model.SaveData.UserDataBase.findUserByUsername;
import static com.badlogic.gdx.Input.Keys.ENTER;
import static com.badlogic.gdx.Input.Keys.T;


public class InputGameController {

    private static final Logger log = LoggerFactory.getLogger(InputGameController.class);
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
        currentGame.currentPlayer.setInFarmExterior(true);
    }
    public void update(OrthographicCamera camera, float v) {

        if (currentGame.currentPlayer.isInFarmExterior()) {
            updateMove();
            print();
            moveCamera(camera);
            GameControllerLogic.update(v);
            showSelectBoxOnCrafting();
        }
        if (currentGame.currentPlayer.isInBarnOrCage()) {
            walkInBarnOrCage();
            showAnimalsInBarnOrCage();
        }
        if (currentGame.currentPlayer.isInMine()) {
            walkInBarnOrCage();
        }
        createAnimalInformationWindow(showAnimalInfo());
        effectAfterPetAnimal();
        openArtisanMenu();
        placeItem();
        placeBomb(CraftingItem.Bombing);
        useSprinkler(CraftingItem.currentSprinkler);
        showForagingMinerals(currentGame.currentPlayer.getFarm().getMine());
        showSellMenu();

        for (int i = 0  ; i < 90 ; i ++) {
            for (int j = 0; j < 90 ; j ++) {
                if (getTileByCoordinates(i, j).getGameObject() instanceof CraftingItem) {
                    showProgressOnArtisans((CraftingItem) getTileByCoordinates(i,j).getGameObject());
                }
            }
        }
    }

    public void updateMove() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP) ) {
            currentGame.currentPlayer.setDirection(Direction.Up);
            if (checkWalking()) {
                currentGame.currentPlayer.setPositionY(currentGame.currentPlayer.getPositionY() - 5 * Gdx.graphics.getDeltaTime());
            }
            currentGame.currentPlayer.setMoving(true);
            moveAnimation();
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) ) {
            currentGame.currentPlayer.setDirection(Direction.Down);
            if (checkWalking()) {
                currentGame.currentPlayer.setPositionY(currentGame.currentPlayer.getPositionY() + 5 * Gdx.graphics.getDeltaTime());
            }
            currentGame.currentPlayer.setMoving(true);
            moveAnimation();
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) ) {
            currentGame.currentPlayer.setDirection(Direction.Left);
            if (checkWalking()) {
                currentGame.currentPlayer.setPositionX(currentGame.currentPlayer.getPositionX() - 5 * Gdx.graphics.getDeltaTime());
            }
            currentGame.currentPlayer.setMoving(true);
            moveAnimation();
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) ) {
            currentGame.currentPlayer.setDirection(Direction.Right);
            if (checkWalking()) {
                currentGame.currentPlayer.setPositionX(currentGame.currentPlayer.getPositionX() + 5 * Gdx.graphics.getDeltaTime());
            }
            currentGame.currentPlayer.setMoving(true);
            moveAnimation();
        }

        else {
            currentGame.currentPlayer.setMoving(false);
            currentGame.currentPlayer.setTimer(0);
            currentGame.currentPlayer.getSprite().setRegion(currentGame.currentPlayer.getAnimation().getKeyFrame(0));

        }
    }

    public static void moveAnimation() {
        currentGame.currentPlayer.getSprite().setRegion(currentGame.currentPlayer.getAnimation().getKeyFrame(currentGame.currentPlayer.getTimer()));

        if (! currentGame.currentPlayer.getAnimation().isAnimationFinished(currentGame.currentPlayer.getTimer())) {
            currentGame.currentPlayer.setTimer(currentGame.currentPlayer.getTimer() + Gdx.graphics.getDeltaTime());
        }
        else {
            currentGame.currentPlayer.setTimer(0);
        }

        currentGame.currentPlayer.getAnimation().setPlayMode(Animation.PlayMode.LOOP);

    }

    public static boolean checkWalking() {

        try {

            int x = (int) (currentGame.currentPlayer.getPositionX() + currentGame.currentPlayer.getDirection().getX() *
                5 * Gdx.graphics.getDeltaTime());
            int y = (int) (currentGame.currentPlayer.getPositionY() + currentGame.currentPlayer.getDirection().getY() *
                5 * Gdx.graphics.getDeltaTime());

            if (getTileByCoordinates(x, y).getGameObject() instanceof Walkable || getTileByCoordinates(x, y).getGameObject() instanceof door) {
                return true;
            }
        }
        catch (Exception e) {
            return false;
        }

        return false;

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
//    public Result goToMarketMenu () {
//        if (MarketType.isInMarket(currentGame.currentPlayer.getPositionX() , currentGame.currentPlayer.getPositionY()) == null) {
//            return new Result(false , "you are not in market");
//        }
//        currentMenu = com.Graphic.model.Enum.Menu.MarketMenu;
//        return new Result(true , "Welcome to market menu");
//    }
//    public Result goToHomeMenu() {
//        if ( !currentGame.currentPlayer.getFarm().isInHome(currentGame.currentPlayer.getPositionX() , currentGame.currentPlayer.getPositionY())) {
//            return new Result(false , RED+"You're Not in Your Home!"+RESET);
//        }
//        currentMenu = com.Graphic.model.Enum.Menu.HomeMenu;
//        return new Result(true , BLUE+"Welcome to home menu"+RESET);
//    }

//    public Result walk(int goalX, int goalY) {
//        int startX = currentGame.currentPlayer.getPositionX();
//        int startY = currentGame.currentPlayer.getPositionY();
//        Tile endTile = getTileByCoordinates(goalX, goalY);
//
//        if (endTile==null) {
//            return new Result(false,"you can't go to this coordinate");
//        }
//
//        PriorityQueue<State> queue = new PriorityQueue<>(Comparator.comparingInt(s -> s.Energy));
//        Set<State> visited = new HashSet<>();
//
//
//        if (checkConditionsForWalk(goalX, goalY) !=null) {
//            return checkConditionsForWalk(goalX, goalY);
//        }
//
//        for (int dir =1 ; dir<9 ; dir++) {
//            Tile tile = getTileByDir(dir);
//            if (tile == null ) {
//                continue;
//            }
//            if (checkConditionsForWalk(tile.getX() , tile.getY()) ==null) {
//                State state=new State(startX , startY , -1, 0);
//                queue.add(state);
//            }
//        }
//
//        int [] dirx={0,0,1,1,1,-1,-1,-1};
//        int [] diry={1,-1,0,1,-1,0,1,-1};
//
//        while (!queue.isEmpty()) {
//            State current = queue.poll();
//            if (current.x == goalX && current.y == goalY) {
//                if (currentGame.currentPlayer.isHealthUnlimited()){
//                    currentGame.currentPlayer.setPositionX(goalX);
//                    currentGame.currentPlayer.setPositionY(goalY);
//                    return new Result(true,"now you are in "+goalX+","+goalY);
//                }
//                if (currentGame.currentPlayer.getHealth() >= current.Energy ) {
//                    currentGame.currentPlayer.increaseHealth(- current.Energy);
//                    currentGame.currentPlayer.setPositionX(goalX);
//                    currentGame.currentPlayer.setPositionY(goalY);
//                    return new Result(true, "you are now in " + goalX + "," + goalY);
//                }
//                return new Result(false , "your energy is not enough for go to this tile");
//            }
//            if (visited.contains(current)) {
//                continue;
//            }
//            visited.add(current);
//            for (int i=0 ; i<8 ; i++) {
//                int x=dirx[i];
//                int y=diry[i];
//                if (checkConditionsForWalk(x + current.x,y + current.y) !=null) {
//                    continue;
//                }
//                int turnCost= (i + 1 == current.direction || current.direction== - 1) ? 1:10;
//                int totalEnergy=current.Energy + turnCost;
//                queue.add(new State(x+current.x , y+current.y , i+1 , totalEnergy));
//            }
//        }
//
//        return new Result(false , "No way to this coordinate");
//    }
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

                    Tile tile = getTileByCoordinates(i, j);
                    GameObject gameObject = tile.getGameObject();

                    Main.getBatch().draw(TextureManager.get("Places/Walkable.png") ,
                        TEXTURE_SIZE * i , TEXTURE_SIZE * (90 - j) , TEXTURE_SIZE , TEXTURE_SIZE);

                    if (gameObject instanceof UnWalkable) {
                        Main.getBatch().draw(TextureManager.get("Tree/unWalkable6.png"),
                            TEXTURE_SIZE * i , TEXTURE_SIZE * (90 - j) , TEXTURE_SIZE , TEXTURE_SIZE);
                    }

                    Main.getBatch().draw(gameObject.getSprite
                            (TextureManager.get(getTileByCoordinates(i , j).getGameObject().getIcon())) ,
                        TEXTURE_SIZE * i , TEXTURE_SIZE * (90 - j) , TEXTURE_SIZE * gameObject.getTextureWidth(), TEXTURE_SIZE * gameObject.getTextureHeight());

                    if (getTileByCoordinates(i , j).getGameObject() instanceof Lake) {
                        LakeAnimation((Lake) getTileByCoordinates(i , j).getGameObject());
                    }


                }
                catch (Exception e) {

                }
            }

        for (User player : currentGame.players) {

            GreenHouse greenHouse = player.getFarm().getGreenHouse();

            Main.getBatch().draw(TextureManager.get(GameTexturePath.GreenHouse.getPath()),
                (greenHouse.getCoordinateX() + 1) * TEXTURE_SIZE, TEXTURE_SIZE * (92 - greenHouse.getCoordinateY() - greenHouse.getLength()),
                (greenHouse.getWidth() - 2) * TEXTURE_SIZE, (greenHouse.getLength() - 2 )* TEXTURE_SIZE
            );
        }

        for (User player : currentGame.players) {
            player.getSprite().draw(Main.getBatch());
        }

        for (User player : currentGame.players) {
            for (BarnOrCage barnOrCage : player.BarnOrCages) {
                for (Animal animal : barnOrCage.getAnimals()) {
                    if (animal.isOut()) {
                        animal.getSprite().draw(Main.getBatch());
                    }
                }
            }
        }

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



    public List<Tile> shepherdAnimals(String x1, String y1, Animal animal) {

        int goalX=Integer.parseInt(x1);
        int goalY=Integer.parseInt(y1);

        int [] x = {1,0,0,-1};
        int [] y = {0,1,-1,0};
        Queue<Tile> queue = new LinkedList<>();
        Set<Tile> tiles = new HashSet<>();
        for (int i = 0; i < 4; i++) {
            if (checkTileForAnimalWalking(animal.getPositionX() + x[i] , animal.getPositionY() + y[i] )) {
                queue.add(getTileByCoordinates(animal.getPositionX() + x[i] , animal.getPositionY() + y[i]));
            }
        }
        tiles.add(getTileByCoordinates(animal.getPositionX() , animal.getPositionY() ));

        HashMap<Tile , Tile> cameFrom = new HashMap<>();

        while (!queue.isEmpty()) {
            Tile tile=queue.poll();
            tiles.add(tile);
            if (tile.getX() == goalX && tile.getY() == goalY) {
                List<Tile> Path= new ArrayList<>();
                while (tile != null) {
                    Path.add(tile);
                    tile = cameFrom.get(tile);
                }

                return Path;
            }

            for (int i = 0; i < 4; i++) {
                if (! checkTileForAnimalWalking(tile.getX() + x[i] , tile.getY() + y[i] ) ) {
                    continue;
                }
                if (getTileByCoordinates(tile.getX() + x[i] , tile.getY() + y[i]) == null) {
                    continue;
                }
                if (tiles.contains(getTileByCoordinates(tile.getX() + x[i] , tile.getY() + y[i]))) {
                    continue;
                }
                Tile next = getTileByCoordinates(tile.getX() + x[i] , tile.getY() + y[i]);
                cameFrom.put(next, tile);
                queue.add(next);
            }
        }
        return null;
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

    public Result checkShepherdAnimals(String X, String Y , Animal animal) {
        int goalX = 0;
        int goalY = 0;
        try {
            goalX = Integer.parseInt(X);
            goalY = Integer.parseInt(Y);
        }
        catch (NumberFormatException e) {
            return new Result(false , "please enter an integer number");
        }

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
        if (animal.getType().equals(AnimalType.pig) && currentGame.currentDate.getSeason().equals(Season.Winter)) {
            return new Result(false , "Pigs can't go out because we are in winter");
        }
        Point start = new Point(animal.getPositionX() , animal.getPositionY());
        Point end = new Point(goalX , goalY);
        if (start.distance(end) > 5) {
            return new Result(false , "The distance is long and animal can't" +
                " go to this coordinate.\nyou should a place with distance less than 5");
        }

        if (animal == null) {
            return new Result(false , "animal not found!");
        }

        return new Result(true , "1");

    }


    public Result feedHay(Animal animal) {
        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;
        MarketItem marketItem = new MarketItem(MarketItemType.Hay);
        if (inventory.Items.containsKey(marketItem)) {
            inventory.Items.compute(marketItem , (k,v) -> v-1);
            inventory.Items.entrySet().removeIf(entry -> entry.getValue()==0);
            animal.setFeedToday(true);
            return new Result(true, "you fed "+animal.getName()+" successfully!");
        }

        return new Result(false ,"You don't have Hay in your inventiry!");
    }

    public Result getProductAnimals(Animal animal) {

        if (! animal.isFeedPreviousDay()){
            return new Result(false , "No Product because you didn't feed " + animal.getName() + " in previous day");
        }
        if (animal.isProductCollected()) {
            return new Result(false , "Product was collected before");
        }
        if (! checkPeriod(animal)) {
            return new Result(false , "It's not time yet. This animal isn't ready to produce again");
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

    public Result produces(Animal animal) {
        StringBuilder result=new StringBuilder();
        result.append("Remaining Produces:\n");

        if (animal.isFeedPreviousDay() && checkPeriod(animal) && ! animal.isProductCollected()) {
            result.append(animal.getProductType().getName()).append("\n");

            double Quantity=((double) animal.getFriendShip() / 1000) * (0.5 * (1 + animal.getRandomProduction()));
            Quantity quantity=productQuantity(Quantity);
            result.append("Quantity: ").append(quantity.getName());

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
        BarnOrCage current=null;
        for (BarnOrCage barnOrCage : currentGame.currentPlayer.BarnOrCages) {
            for (Animal animal1 : barnOrCage.animals) {
                if (animal1.equals(animal)) {
                    barnOrCage.animals.remove(animal1);
                    current=barnOrCage;
                    break;
                }
            }
        }

        int index=0;
        try {
            for (Animal animal1 : current.animals) {
                animal1.setIndex(index);
                index++;
            }
        }
        catch (Exception e) {

        }

        return new Result(true , animal.getName() +" was sold successfully");
    }
    public Result sellAnimal(Animal animal) {

        double x = animal.getFriendShip()/1000 + 0.3;
        currentGame.currentPlayer.increaseMoney((int) (animal.getType().getPrice() * x) );
        return removeAnimal(animal);

    }


    private void placeBomb(CraftingItem craftingItem) {
        int domain = 0 ;
        try {
            switch (craftingItem.getType()) {
                case CherryBomb -> domain = 3;
                case Bomb -> domain = 5;
                case MegaBomb -> domain = 7;
            }


            for (int i = craftingItem.getX(); i < craftingItem.getX() + domain; i++) {
                for (int j = craftingItem.getY(); j < craftingItem.getY() + domain; j++) {
                    try {
                        Main.getBatch().draw(
                            craftingItem.getBomb().getKeyFrame(craftingItem.getTimer()), TEXTURE_SIZE * i, TEXTURE_SIZE * (90 - j));
                    }
                    catch (Exception e) {}
                }
            }
            if (!craftingItem.getBomb().isAnimationFinished(craftingItem.getTimer())) {
                craftingItem.setTimer(craftingItem.getTimer() + Gdx.graphics.getDeltaTime());
            }

            else {

                craftingItem.setTimer(0);
                CraftingItem.Bombing = null;
                getTileByCoordinates(craftingItem.getX(), craftingItem.getY()).setGameObject(new Walkable());
                for (int i = craftingItem.getX(); i < craftingItem.getX() + domain; i++) {
                    for (int j = craftingItem.getY(); j < craftingItem.getY() + domain; j++) {
                        try {
                            Tile target = getTileByCoordinates(i, j);

                            if (target.getGameObject() instanceof Tree) {
                                target.setGameObject(new Walkable());
                            } else if (target.getGameObject() instanceof ForagingCrops) {
                                target.setGameObject(new Walkable());
                            } else if (target.getGameObject() instanceof GiantProduct) {
                                target.setGameObject(new Walkable());
                            } else if (target.getGameObject() instanceof ForagingSeeds) {
                                target.setGameObject(new Walkable());
                            } else if (target.getGameObject() instanceof BasicRock) {
                                target.setGameObject(new Walkable());
                            }
                        }
                        catch (Exception e) {}
                    }
                }
            }
        }
        catch (Exception e) {

        }
    }

    private void useSprinkler(CraftingItem craftingItem) {
        int domain = 0 ;
        try {
            switch (craftingItem.getType()) {
                case Sprinkler -> domain = 4;
                case QualitySprinkler -> domain = 8;
                case IridiumSprinkler -> domain = 24;
            }

            Main.getBatch().draw(craftingItem.getSprinkler().getKeyFrame(craftingItem.getTimer()),
                TEXTURE_SIZE * (craftingItem.getX() - 2) ,
                TEXTURE_SIZE * (90 - craftingItem.getY() - 2 ) ,
                   TEXTURE_SIZE * 4 , TEXTURE_SIZE * 4);

            if (!craftingItem.getSprinkler().isAnimationFinished(craftingItem.getTimer())) {
                craftingItem.setTimer(craftingItem.getTimer() + Gdx.graphics.getDeltaTime());
            } else {
                craftingItem.setTimer(0);
                CraftingItem.currentSprinkler = null;
                for (int i = -domain / 2; i < domain / 2; i++) {
                    for (int j = -domain / 2; j < domain / 2; j++) {
                        try {
                            Tile tile1 = getTileByCoordinates(i + craftingItem.getX(), j + craftingItem.getY());
                            if (tile1.getGameObject() instanceof Tree) {
                                ((Tree) tile1.getGameObject()).setLastWater(currentGame.currentDate.clone());
                            } else if (tile1.getGameObject() instanceof ForagingSeeds) {
                                ((ForagingSeeds) tile1.getGameObject()).setLastWater(currentGame.currentDate.clone());
                            } else if (tile1.getGameObject() instanceof GiantProduct) {
                                ((GiantProduct) tile1.getGameObject()).setLastWater(currentGame.currentDate.clone());
                            }
                        } catch (Exception e) {
                        }
                    }
                }
            }
        }
        catch (Exception e) {}

    }


    public Result checkPlaceItem(CraftingItem craftingItem) {
        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;
        if (! inventory.Items.containsKey(craftingItem)) {
            return new Result(false , "you don't have this craft in your inventory");
        }
        currentGame.currentPlayer.setIsPlaceArtisanOrShippingBin(true);
        currentGame.currentPlayer.setWithMouse(new Sprite(craftingItem.getSprite(TextureManager.get(craftingItem.getType().getIcon()))));
        currentGame.currentPlayer.getWithMouse().setAlpha(0.5f);
        currentGame.currentPlayer.setDroppedItem(craftingItem);

        return new Result(true , "");
    }

    private boolean bool = false;
    public void placeItem() {
        if (currentGame.currentPlayer.getDroppedItem() != null) {
            if (currentGame.currentPlayer.isPlaceArtisanOrShippingBin() && ! currentGame.currentPlayer.isWaiting()) {
                currentGame.currentPlayer.getWithMouse().setPosition(
                    gameMenu.getVector().x - currentGame.currentPlayer.getWithMouse().getWidth() / 2,
                    gameMenu.getVector().y - currentGame.currentPlayer.getWithMouse().getHeight() / 2);
                Marketing.getInstance().printMapForCreate();
                currentGame.currentPlayer.getWithMouse().draw(Main.getBatch());
            }
            if (currentGame.currentPlayer.isPlaceArtisanOrShippingBin() &&
                !currentGame.currentPlayer.isWaiting() && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                currentGame.currentPlayer.setWaiting(true);
            }
            if (currentGame.currentPlayer.isWaiting()) {
                setCraftInFarm(currentGame.currentPlayer.getWithMouse(), currentGame.currentPlayer.getDroppedItem());
            }
        }
    }

    public void setCraftInFarm(Sprite sprite , Items items) {
        int x = (int) (sprite.getX() / TEXTURE_SIZE) + 60 * currentGame.currentPlayer.topLeftX;
        int y =30 -  (int) (sprite.getY() / TEXTURE_SIZE) + 60 * currentGame.currentPlayer.topLeftY;

        try {
            if (!(getTileByCoordinates(x, y).getGameObject() instanceof Walkable) && !bool) {
                Dialog dialog = Marketing.getInstance().createDialogError();
                Label content = new Label("you can't place craft on this place", new Label.LabelStyle(getFont(), Color.BLACK));
                Marketing.getInstance().addDialogToTable(dialog, content, gameMenu);
                currentGame.currentPlayer.setWaiting(false);
            } else {
                Marketing.getInstance().printMapForCreate();
                bool = true;
                getTileByCoordinates(x, y).setGameObject(items);

                if (Gdx.input.isKeyJustPressed(ENTER)) {
                    TextButton Confirm = Marketing.getInstance().makeConfirmButton(currentMenu.getMenu());
                    TextButton TryAgain = Marketing.getInstance().makeTryAgainButton(currentMenu.getMenu());

                    Confirm.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent changeEvent, Actor actor) {
                            Confirm.remove();
                            TryAgain.remove();
                            currentGame.currentPlayer.setIsPlaceArtisanOrShippingBin(false);
                            currentGame.currentPlayer.setWaiting(false);
                            currentGame.currentPlayer.setDroppedItem(null);
                            advanceItem(items, -1);
                            items.setX(x);
                            items.setY(y);
                            camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                            choosePlace = false;
                            try {
                                System.out.println("buy");
                                currentGame.currentPlayer.getFarm().shippingBins.add((ShippingBin) items);
                            }
                            catch (Exception e) {
                                System.out.println("don't worry");
                            }
                        }
                    });

                    TryAgain.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent changeEvent, Actor actor) {
                            currentGame.currentPlayer.setWaiting(false);
                            Confirm.remove();
                            TryAgain.remove();
                            getTileByCoordinates(x, y).setGameObject(new Walkable());
                            bool = false;
                        }
                    });
                }
            }
        }
        catch (Exception e) {

        }

    }

    public void showSelectBoxOnCrafting() {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {

        int x = (int) (gameMenu.getVector().x/TEXTURE_SIZE);
        int y = (int) (gameMenu.getVector().y/TEXTURE_SIZE);

            if (getTileByCoordinates(x , 90 - y)
                .getGameObject() instanceof CraftingItem ) {

                System.out.println("yes");

                SelectBox selectBox = craftBox((CraftingItem) getTileByCoordinates(x, 90 - y)
                   .getGameObject());
           }

       }
       //System.out.println(gameMenu.getVector().x/TEXTURE_SIZE + "<<" + gameMenu.getVector().y/TEXTURE_SIZE);
    }

    private SelectBox<String> craftBox(CraftingItem craftingItem) {
        SelectBox<String> selectBox = new SelectBox<>(getSkin());
        selectBox.setItems("Use" , "Collect Product" , "cheat");
        Vector2 click = gameMenu.getStage().screenToStageCoordinates(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
        selectBox.setPosition( click.x , click.y);
        gameMenu.getStage().addActor(selectBox);


        selectBox.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                String selected = selectBox.getSelected();
                switch (selected) {
                    case "Use" :{
                        if (craftingItem.getType().equals(CraftType.Bomb)
                            || craftingItem.getType().equals(CraftType.MegaBomb)
                            || craftingItem.getType().equals(CraftType.CherryBomb)) {

                            CraftingItem.Bombing = craftingItem;
                        }
                        else if (craftingItem.getType().equals(CraftType.Sprinkler)
                            || craftingItem.getType().equals(CraftType.IridiumSprinkler)
                            || craftingItem.getType().equals(CraftType.QualitySprinkler)) {
                            CraftingItem.currentSprinkler = craftingItem;
                            selectBox.remove();
                        }
                        else if (craftingItem.getType().isCanProduct()) {
                            Texture bg = TextureManager.get("Mohamadreza/ArtisanMenu.png");
                            ArtisanMenuUI artisanMenuUI = new ArtisanMenuUI(getSkin() , bg , 2);
                            showProductsOfArtisan(craftingItem, artisanMenuUI);
                            selectBox.remove();
                        }
                        break;
                    }
                    case "Collect Product" : {
                        selectBox.remove();
                        ArtisanGetProduct(craftingItem);
                        break;
                    }
                    case "cheat" : {
                        selectBox.remove();
                        break;
                    }
                }
            }
        });


        return selectBox;

    }

    private void showProductsOfArtisan(CraftingItem craftingItem , ArtisanMenuUI artisanMenuUI) {
        Table table = new Table();
        ScrollPane pane = new ScrollPane(table,getSkin());
        pane.setFadeScrollBars(false);
        Window window = new Window("Options",getSkin() , "default");
        Vector2 click = gameMenu.getStage().screenToStageCoordinates(new Vector2(Gdx.input.getX(), Gdx.input.getY()));

        for (ArtisanType artisanType : ArtisanType.values()) {
            if (artisanType.getCraftType().equals(craftingItem.getType())) {
                TextButton button = new TextButton("",getSkin());
                button.clearChildren();
                Label label = new Label(artisanType.getName(),getSkin());
                button.add(label).center();
                button.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        window.remove();
                        artisanMenuUI.setGoingToProduce(new ArtisanProduct(artisanType));
                        artisanMenuUI.setCrafting(craftingItem);
                        artisanMenuUI.setPosition(100 , 100);
                        gameMenu.getStage().addActor(artisanMenuUI);
                    }
                });
                table.add(button).width(100).height(50);
                table.row();
            }
        }
        pane.setWidget(table);
        window.add(pane).width(100).height(50);
        window.pack();
        window.setPosition(click.x , click.y);
        gameMenu.getStage().addActor(window);
    }

    public void showProgressOnArtisans(CraftingItem craftingItem) {
        for (int i = 0 ; i < craftingItem.getItems().size() ; i++) {
            Main.getBatch().draw(TextureManager.get("Mohamadreza/bgProgress.png") ,
                TEXTURE_SIZE * craftingItem.getX() , TEXTURE_SIZE * (90 - craftingItem.getY()) + TEXTURE_SIZE + (TEXTURE_SIZE / 2) * i ,
                TEXTURE_SIZE * 2 , TEXTURE_SIZE / 2);
        }

        Main.getBatch().end();


        for (int i = 0 ; i < craftingItem.getItems().size() ; i++) {
            craftingItem.getShapeRenderers().get(i).setProjectionMatrix(Main.getBatch().getProjectionMatrix());
            craftingItem.getShapeRenderers().get(i).begin(ShapeRenderer.ShapeType.Filled);
            craftingItem.getShapeRenderers().get(i).setColor(0,1,0,1);
            float x = getX(craftingItem, i);
            craftingItem.getShapeRenderers().get(i).rect(TEXTURE_SIZE * craftingItem.getX() + 6 ,
                TEXTURE_SIZE * (90 - craftingItem.getY()) + TEXTURE_SIZE + (TEXTURE_SIZE/2) * i + 3 ,
                (TEXTURE_SIZE * 2) * x - 10 , TEXTURE_SIZE/2 - 7);

            craftingItem.getShapeRenderers().get(i).end();
        }

        Main.getBatch().begin();

    }

    private static float getX(CraftingItem craftingItem, int i) {
        float x =0;
        for (ArtisanType artisanType : ArtisanType.values()) {
            if (craftingItem.getItems().get(i).getName().toLowerCase().equals(artisanType.getName().toLowerCase())) {
                x = (float) (DateHour.getHourDifferent(craftingItem.getDateHours().get(i))) / artisanType.getTakesTime();

                if (x >= 1) {
                    x = 1;
                }
                //System.out.println(x);
            }
        }
        return x;
    }

    public void ArtisanGetProduct(CraftingItem craftingItem) {
        StringBuilder result = new StringBuilder();

        for (int i =craftingItem.getItems().size() - 1 ; i >= 0 ; i--) {
            for (ArtisanType artisanType : ArtisanType.values()) {
                if (artisanType.getName().toLowerCase().equals(craftingItem.getItems().get(i).getName().toLowerCase())) {
                    if (DateHour.getHourDifferent(craftingItem.getDateHours().get(i)) >= artisanType.getTakesTime()) {
                        if (currentGame.currentPlayer.getBackPack().inventory.Items.containsKey(craftingItem.getItems().get(i))) {
                            currentGame.currentPlayer.getBackPack().inventory.Items.compute(craftingItem.getItems().get(i) , (k,v)->v+1);
                        }
                        else {
                            currentGame.currentPlayer.getBackPack().inventory.Items.put(craftingItem.getItems().get(i), 1);
                        }
                        result.append(artisanType.getName()+", ");
                        craftingItem.getDateHours().remove(i);
                        craftingItem.getShapeRenderers().remove(i);
                        craftingItem.getItems().remove(i);
                        break;
                    }
                }
            }
        }


        Label label;
        Dialog dialog = Marketing.getInstance().createDialogError();
        if (!result.isEmpty()) {
            label = new Label(result.toString() + "was added to your inventory", getSkin());
        }
        else {
            label = new Label("please wait more",getSkin());
        }
        Marketing.getInstance().addDialogToTable(dialog,label,gameMenu);
    }

    public void showSellMenu() {
        int x = (int) (gameMenu.getMousePos().x / TEXTURE_SIZE);
        int y = (int) (gameMenu.getMousePos().y / TEXTURE_SIZE);

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && currentGame.currentPlayer.isInFarmExterior()) {
            try {
                Tile tile = getTileByCoordinates(x , 90 - y);
                if (tile.getGameObject() instanceof ShippingBin) {
                    Table table = new Table();
                    Drawable drawable = new TextureRegionDrawable(new TextureRegion(TextureManager.get("Mohamadreza/Sell.png")));
                    table.setBackground(drawable);
                    table.setPosition(Gdx.graphics.getWidth() / 2 - 400 , Gdx.graphics.getHeight() / 2 - 400);
                    table.setSize(800,800);

                    Table buttonTable = new Table();
                    buttonTable.setPosition(648,168);
                    buttonTable.defaults().size(75,62).pad(0);
                    buttonTable.setSize(75 * 6 , 62 * 4);
                    gameMenu.getStage().addActor(table);
                    gameMenu.getStage().addActor(buttonTable);
                    createButtonsForSellMenu(buttonTable , (ShippingBin) tile.getGameObject() , table);
                }
                System.out.println(tile.getGameObject().getIcon());
            }
            catch (Exception e) {

            }
        }
    }

    public void createButtonsForSellMenu(Table table , ShippingBin shippingBin , Table mainTable) {
        int width = 0;
        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;
        Table UpTable = new Table();
        table.top().left();
        for (Map.Entry<Items,Integer> entry : inventory.Items.entrySet()) {
            if (entry.getKey() instanceof Fish || entry.getKey() instanceof Animalproduct) {
                TextButton button = new TextButton("",getSkin());
                button.clearChildren();
                button.add(new Image(TextureManager.get(entry.getKey().getInventoryIconPath()))).size(73,50);
                width = width + 1;
                width = width % 7;
                if (width == 0) {
                    table.add(button).size(75,62);
                    table.row();
                }
                else {
                    table.add(button).size(75,62);
                }
                button.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        makeUpTableForSell(UpTable , entry.getKey() , shippingBin , table , mainTable);
                    }
                });
            }
        }



    }

    public void makeUpTableForSell(Table UpTable , Items items , ShippingBin shippingBin , Table table , Table mainTable) {
        try {
            UpTable.remove();
            UpTable.clear();
        }
        catch (Exception e) {
            System.out.println("dsnkjf");
        }
        UpTable.defaults().size(274,40).pad(10);
        UpTable.setPosition(813, 710);
        UpTable.setSize(300,140);

        TextButton Back = new TextButton("",getSkin());
        Label back = new Label("Back",getSkin());
        Back.clearChildren();
        Back.add(back).center();
        Back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                UpTable.remove();
                UpTable.clear();
                table.clear();
                table.remove();
                mainTable.remove();
                mainTable.clear();
            }
        });

        TextField Field = new TextField("",getSkin());
        Field.setMessageText("Number");

        TextButton Submit = new TextButton("",getSkin());
        Label submit = new Label("Submit",getSkin());
        Submit.clearChildren();
        Submit.add(submit).center();
        Submit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                try {
                    int number = Integer.parseInt(Field.getText());
                    if (number <= 0) {
                        Dialog dialog = Marketing.getInstance().createDialogError();
                        Label label = new Label("please Enter an integer Number greater tan 0",getSkin());
                        Marketing.getInstance().addDialogToTable(dialog,label,gameMenu);
                    }
                    else {
                        if (currentGame.currentPlayer.getBackPack().inventory.Items.get(items) < number) {
                            Dialog dialog = Marketing.getInstance().createDialogError();
                            Label label = new Label("you don't have this number of "+items.getName(),getSkin());
                            Marketing.getInstance().addDialogToTable(dialog,label,gameMenu);
                        }
                        else {
                            if (shippingBin.binContents.containsKey(items)) {
                                shippingBin.binContents.compute(items , (k,v) -> v + number);
                            }
                            else {
                                System.out.println("hello");
                                shippingBin.binContents.put(items, number);
                            }
                            advanceItem(items , - number);
                            Dialog dialog = Marketing.getInstance().createDialogError();
                            Label label = new Label("you sell "+items.getName()+" successfully. tomorrow your money will increase",getSkin());
                            Marketing.getInstance().addDialogToTable(dialog,label,gameMenu);
                            UpTable.remove();
                            UpTable.clear();
                        }
                    }
                }
                catch (Exception e) {
                    Dialog dialog = Marketing.getInstance().createDialogError();
                    Label label = new Label("please Enter an integer Number greater tan 0",getSkin());
                    Marketing.getInstance().addDialogToTable(dialog,label,gameMenu);
                }
            }
        });


        UpTable.top();
        UpTable.add(Field).size(274,33).row();
        UpTable.add(Submit).size(274,33).row();
        UpTable.add(Back).size(274,33).row();

        currentMenu.getMenu().getStage().addActor(UpTable);
    }
    // Ario

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

//        currentGame.players.add(findUserByUsername(user1name));
//        if (user2name != null) currentGame.players.add(findUserByUsername(user2name));
//        if (user3name != null) currentGame.players.add(findUserByUsername(user3name));

        currentGame.players.add(new User("Ario", "Ario", "ario.ebr@gmail.com", "man", 0, 200, PasswordHashUtil.hashPassword("Ebrahim84?"), SecurityQuestions.FavoriteAnimal, "dog"));
        currentGame.players.add(new User("Erfan", "Erfan", "ario.ebr@gmail.com", "man", 0, 200, PasswordHashUtil.hashPassword("Ebrahim84?"), SecurityQuestions.FavoriteAnimal, "dog"));
        currentGame.players.add(new User("Mamali", "Mamali", "ario.ebr@gmail.com", "man", 0, 200, PasswordHashUtil.hashPassword("Ebrahim84?"), SecurityQuestions.FavoriteAnimal, "dog"));
        currentGame.players.add(new User("Ilia", "Ilia", "ario.ebr@gmail.com", "man", 0, 200, PasswordHashUtil.hashPassword("Ebrahim84?"), SecurityQuestions.FavoriteAnimal, "dog"));
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
//
                int choice = 1; // TODO باید پاک بشه

                if (counter == 1) {
                    user.setIcon("all image/Crops/Cactus_Stage_6.png");
                    user.topLeftX = 0;
                    user.topLeftY = 0;
                }
                else if (counter == 2) {
                    user.setIcon("all image/Special_item/Cursed_Mannequin_%28F%29.png");
                    user.topLeftX = 1;
                    user.topLeftY = 0;
                }
                else if (counter == 3) {
                    user.setIcon("all image/Special_item/Deconstructor.png");
                    user.topLeftX = 0;
                    user.topLeftY = 1;
                }
                else if (counter == 4) {
                    user.setIcon("all image/Special_item/Wood_Chipper_On.png");
                    user.topLeftX = 1;
                    user.topLeftY = 1;
                }
                createInitialFarm(choice);
                user.initAnimations();
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
        //fadeToNextDay();
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
        fadeToNextDay();
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

    public void doItemTask (float disX, float disY) {

        // Diraction diraction =
    }
    public Result howMuchWater () {

        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;

        for (Map.Entry <Items,Integer> entry: inventory.Items.entrySet())
            if (entry instanceof WateringCan)
                return new Result(true, BLUE+"Water Remaining : "
                        +RESET+((WateringCan) entry).getReminderCapacity());

        return new Result(false, BLUE+"کدوم سطل سلطان"+RESET);
    }
    public void itemEquip (String name) {

        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;

        for (Map.Entry<Items,Integer> entry: inventory.Items.entrySet())
            if (entry.getKey() != null && entry.getKey().getName().equals(name))
                currentGame.currentPlayer.currentItem = entry.getKey();


    }
    public HashMap<String, String> availableTools() {

        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;
        HashMap<String, String> availableTools = new HashMap<>();

        for (Map.Entry<Items, Integer> entry : inventory.Items.entrySet())
            if (entry.getKey() instanceof Tools)
                availableTools.put(entry.getKey().getName(), entry.getKey().getInventoryIconPath());

        return availableTools;
    }
    public Result upgradeTool (String name) {
//         MarketType marketType=MarketType.wallOrDoor(currentGame.currentPlayer.getPositionX() , currentGame.currentPlayer.getPositionY());
//         if (marketType!=MarketType.Blacksmith) {
//             return new Result(false , "you are not in BlackSmith Market. please go there");
//         }
//         Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;
//
//         if ( name.equals("Axe") ) {
//             for (Map.Entry<Items, Integer> entry : inventory.Items.entrySet()) {
//                 if (entry.getKey() instanceof Axe) {
//                     AxeType axeType = AxeType.getNextType(((Axe) entry.getKey()).getType());
//                     if (axeType == null) {
//                         return new Result(false , name + "is at top level");
//                     }
//                     else if (AxeType.checkIngredient(axeType)) {
//                         ((Axe) entry.getKey()).setType(axeType);
//                         currentGame.currentPlayer.increaseMoney( - axeType.getPrice());
//                         return new Result(true , name + "updated successfully");
//                     }
//                     else {
//                         return new Result(false , "Not enough ingredient or money");
//                     }
//                 }
//             }
//         }
//
//        if ( name.equals("Hoe") ) {
//            for (Map.Entry<Items, Integer> entry : inventory.Items.entrySet()) {
//                if (entry.getKey() instanceof Hoe) {
//                    HoeType hoeType=HoeType.getNextType(((Hoe) entry.getKey()).getType());
//                    if (hoeType == null) {
//                        return new Result(false , name + "is at top level");
//                    }
//                    else if (HoeType.checkIngredient(hoeType)) {
//                        ((Hoe) entry.getKey()).setType(hoeType);
//                        currentGame.currentPlayer.increaseMoney( - hoeType.getPrice());
//                        return new Result(true , name + "updated successfully");
//                    }
//                    else {
//                        return new Result(false , "Not enough ingredient or money");
//                    }
//                }
//            }
//        }
//
//        if ( name.equals("PickAxe") ) {
//            for (Map.Entry<Items, Integer> entry : inventory.Items.entrySet()) {
//                if (entry.getKey() instanceof PickAxe) {
//                    PickAxeType pickAxeType=PickAxeType.getPickAxeType(((PickAxe) entry.getKey()).getType());
//                    if (pickAxeType == null) {
//                        return new Result(false , name + "is at top level");
//                    }
//                    else if (PickAxeType.checkIngredient(pickAxeType)) {
//                        ((PickAxe) entry.getKey()).setType(pickAxeType);
//                        currentGame.currentPlayer.increaseMoney( - pickAxeType.getPrice());
//                        return new Result(true , name + "updated successfully");
//                    }
//                    else {
//                        return new Result(false , "Not enough ingredient or money");
//                    }
//                }
//            }
//        }
//
//        if ( name.equals("WateringCan") ) {
//            for (Map.Entry<Items, Integer> entry : inventory.Items.entrySet()) {
//                if (entry.getKey() instanceof WateringCan) {
//                    WateringCanType wateringCanType=WateringCanType.getWateringCanType(((WateringCan) entry.getKey()).getType());
//                    if (wateringCanType == null) {
//                        return new Result(false , name + "is at top level");
//                    }
//                    else if (WateringCanType.checkIngredient(wateringCanType)) {
//                        ((WateringCan) entry.getKey()).setType(wateringCanType);
//                        currentGame.currentPlayer.increaseMoney( - wateringCanType.getPrice());
//                        return new Result(true , name + "updated successfully");
//                    }
//                    else {
//                        return new Result(false , "Not enough ingredient or money");
//                    }
//                }
//            }
//        }
//
//        if ( name.equals("TrashCan") ) {
//            for (Map.Entry<Items, Integer> entry : inventory.Items.entrySet()) {
//                if (entry.getKey() instanceof TrashCan) {
//                    TrashCanType trashCanType = TrashCanType.nextTrashCanType(((TrashCan) entry.getKey()).type);
//                    if (trashCanType == null) {
//                        return new Result(false , name + "is at top level");
//                    }
//                    else if (TrashCanType.checkIngredient(trashCanType)) {
//                        ((TrashCan) entry.getKey()).setType(trashCanType);
//                        currentGame.currentPlayer.increaseMoney( - trashCanType.getPrice());
//                        return new Result(true , name + "updated successfully");
//                    }
//                    else {
//                        return new Result(false , "Not enough ingredient or money");
//                    }
//                }
//            }
//        }

        return null;
    }
    public Result useTools (int dir) {
        if (!currentGame.currentPlayer.isHealthUnlimited()) {
            if (currentGame.currentPlayer.getHealth() < currentGame.currentPlayer.currentTool.healthCost())
                return new Result(false, RED+"you are not in your hand"+RESET);

            currentGame.currentPlayer.increaseHealth(currentGame.currentPlayer.currentTool.healthCost());
        }

        Tools tools = currentGame.currentPlayer.currentTool;

        if (currentGame.currentPlayer.currentTool == null)
            return new Result(false, RED + "please pick up a tools" + RESET);

        switch (tools) {
            case Axe axe -> {
                return useAxe(dir);
            }
            case Hoe hoe -> {
                if (currentGame.currentPlayer.currentTool.healthCost() > 0 && currentGame.currentPlayer.Buff_farming_hoursLeft > 0)
                    currentGame.currentPlayer.increaseHealth(1);
                return useHoe(dir);
            }
            case MilkPail milkPail -> {
                if (currentGame.currentPlayer.currentTool.healthCost() > 0 && currentGame.currentPlayer.Buff_farming_hoursLeft > 0)
                    currentGame.currentPlayer.increaseHealth(1);
                return useMilkPail(dir);
            }
            case Scythe scythe -> {
                if (currentGame.currentPlayer.currentTool.healthCost() > 0 && currentGame.currentPlayer.Buff_farming_hoursLeft > 0)
                    currentGame.currentPlayer.increaseHealth(1);
                return useScythe(dir);
            }
            case Shear shear -> {
                if (currentGame.currentPlayer.currentTool.healthCost() > 0 && currentGame.currentPlayer.Buff_farming_hoursLeft > 0)
                    currentGame.currentPlayer.increaseHealth(1);
                return useShear(dir);
            }
            case WateringCan wateringCan -> {
                return useWateringCan(dir);
            }
            case PickAxe pickAxe -> {
                return usePickAxe(dir);
            }
            default -> {
            }
        }
        return new Result(false, RED + "please pick up a tools" + RESET);
    }

                                                                    // input NPC command
    public Result meetNPC (String name) {

//        NPC npc;
//        try {
//            npc = NPC.valueOf(name);
//        } catch (Exception e) {
//            return new Result(false, RED+"You're looking for someone who isn't real"+RESET);
//        }
//
//        if (!npc.isInHisHome(currentGame.currentPlayer.getPositionX(), currentGame.currentPlayer.getPositionY()))
//            return new Result(false, RED+"You should go to their place first"+RESET);
//
//        if (!currentGame.currentPlayer.getTodayTalking(npc)) {
//            currentGame.currentPlayer.setTodayTalking(npc,true);
//            currentGame.currentPlayer.increaseFriendshipPoint(npc, 20);
//        }
//
//        return new Result(true, npc.getName() + " : " + BLUE +
//                npc.getDialogue(currentGame.currentPlayer.getFriendshipLevel(npc), currentGame.currentWeather)+RESET);
        return null;
    }
    public Result giftNPC (String name, String itemName) {

        NPC npc;
        try {
            npc = NPC.valueOf(name);
        } catch (Exception e) {
            return new Result(false, RED+"You're looking for someone who isn't real"+RESET);
        }

//        if (!npc.isInHisHome(currentGame.currentPlayer.getPositionX(), currentGame.currentPlayer.getPositionY()))
//            return new Result(false, RED+"You should go to their place first"+RESET);

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
//        if (!npc.isInHisHome(currentGame.currentPlayer.getPositionX(), currentGame.currentPlayer.getPositionY()))
//            return new Result(false, RED+"You should go to their place first"+RESET);

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
