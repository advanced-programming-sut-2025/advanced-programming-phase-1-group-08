package com.Graphic.Controller.MainGame;

import com.Graphic.Main;
import com.Graphic.View.GameMenus.GameMenu;
import com.Graphic.model.*;
import com.Graphic.model.Animall.Animal;
import com.Graphic.model.Animall.AnimalRenderer;
import com.Graphic.model.ClientServer.GameState;
import com.Graphic.model.ClientServer.Message;
import com.Graphic.model.Enum.AllPlants.*;
import com.Graphic.model.Enum.Commands.CommandType;
import com.Graphic.model.Enum.Direction;
import com.Graphic.model.Enum.GameTexturePath;
import com.Graphic.model.Enum.ItemType.*;
import com.Graphic.model.Enum.NPC.NPC;
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
import com.Graphic.model.Plants.Tree;
import com.Graphic.model.SaveData.PasswordHashUtil;
import com.Graphic.model.ToolsPackage.*;
import com.Graphic.model.Weather.DateHour;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.google.gson.Gson;


import static com.Graphic.Controller.MainGame.GameControllerLogic.*;

import static com.Graphic.View.GameMenus.GameMenu.camera;
import static com.Graphic.View.GameMenus.MarketMenu.*;
import static com.Graphic.model.App.*;
import static com.Graphic.model.HelpersClass.Color_Eraser.*;
import static com.Graphic.model.HelpersClass.TextureManager.TEXTURE_SIZE;
import static com.badlogic.gdx.Input.Keys.ENTER;


public class InputGameController {

    public static InputGameController inputGameController;
    Gson gson = new Gson();

    private InputGameController() {}
    public static InputGameController getInstance() {
        if (inputGameController == null)
            inputGameController = new InputGameController();
        return inputGameController;
    }

    public void init () {
        Main.getClient().getPlayer().setInFarmExterior(true);
        setTimeAndWeather();
        GameControllerLogic.init();
    }
    public void update(OrthographicCamera camera, float v, Boolean menuActivated) {

        if (!menuActivated) {
            if (Main.getClient().getPlayer().isInFarmExterior()) {
                updateMove();
                print();
                moveCamera(camera);
                GameControllerLogic.update(v);
                showSelectBoxOnCrafting();
            }
            if (Main.getClient().getPlayer().isInBarnOrCage()) {
                walkInBarnOrCage();
                showAnimalsInBarnOrCage();
            }
            if (Main.getClient().getPlayer().isInMine()) {
                walkInBarnOrCage();
            }
            createAnimalInformationWindow(showAnimalInfo());
            effectAfterPetAnimal();
            openArtisanMenu();
            placeItem();
            placeBomb(CraftingItem.Bombing);
            useSprinkler(CraftingItem.currentSprinkler);
            showForagingMinerals(Main.getClient().getPlayer().getFarm().getMine());
            showSellMenu();
            showProgressOnArtisans();

//            for (int i = 0; i < 90; i++) {
//                for (int j = 0; j < 90; j++) {
//                    if (getTileByCoordinates(i, j).getGameObject() instanceof CraftingItem) {
//                        showProgressOnArtisans((CraftingItem) getTileByCoordinates(i, j).getGameObject());
//                    }
//                }
//            }
        } else {
            if (Main.getClient().getPlayer().isInFarmExterior())
                print();
        }
    }

    public void updateMove() {
        float x;
        float y;
        float copy = Main.getClient().getPlayer().getTimer();
        float time = Main.getClient().getPlayer().getTimer() + Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.UP) ) {
            x = Main.getClient().getPlayer().getPositionX();
            y = Main.getClient().getPlayer().getPositionY() + 100 * Gdx.graphics.getDeltaTime();
            HashMap<String , Object> body = new HashMap<>();
            body.put("Player" , Main.getClient().getPlayer().getUsername());
            body.put("Direction" , Direction.Up);
            body.put("X" , x);
            body.put("Y" , y);
            body.put("Time" , time);
            Main.getClient().getRequests().add(new Message(CommandType.MOVE_IN_FARM, body));
            return;
//            Main.getClient().getPlayer().setDirection(Direction.Up);
//            if (checkWalking()) {
//                Main.getClient().getPlayer().setPositionY(Main.getClient().getPlayer().getPositionY() - 5 * Gdx.graphics.getDeltaTime());
//            }
//            Main.getClient().getPlayer().setMoving(true);
//            moveAnimation();
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) ) {
            x = Main.getClient().getPlayer().getPositionX();
            y = Main.getClient().getPlayer().getPositionY() - 100 * Gdx.graphics.getDeltaTime();
            HashMap<String , Object> body = new HashMap<>();
            body.put("Player" , Main.getClient().getPlayer().getUsername());
            body.put("Direction" , Direction.Down);
            body.put("X" , x);
            body.put("Y" , y);
            body.put("Time" , time);
            Main.getClient().getRequests().add(new Message(CommandType.MOVE_IN_FARM, body));
            return;
//            Main.getClient().getPlayer().setDirection(Direction.Down);
//            if (checkWalking()) {
//                Main.getClient().getPlayer()Main.getClient().getPlayer().setPositionY(Main.getClient().getPlayer().getPositionY() + 5 * Gdx.graphics.getDeltaTime());
//            }
//            Main.getClient().getPlayer().setMoving(true);
//            moveAnimation();
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) ) {
            x = Main.getClient().getPlayer().getPositionX() - 100 * Gdx.graphics.getDeltaTime();
            y = Main.getClient().getPlayer().getPositionY();
            HashMap<String , Object> body = new HashMap<>();
            body.put("Player" , Main.getClient().getPlayer().getUsername());
            body.put("Direction" , Direction.Left);
            body.put("X" , x);
            body.put("Y" , y);
            body.put("Time" , time);
            Main.getClient().getRequests().add(new Message(CommandType.MOVE_IN_FARM, body));
            return;
//            Main.getClient().getPlayer().setDirection(Direction.Left);
//            if (checkWalking()) {
//                Main.getClient().getPlayer().setPositionX(Main.getClient().getPlayer().getPositionX() - 5 * Gdx.graphics.getDeltaTime());
//            }
//            Main.getClient().getPlayer().setMoving(true);
//            moveAnimation();
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) ) {
            x = Main.getClient().getPlayer().getPositionX() + 100 * Gdx.graphics.getDeltaTime();
            y = Main.getClient().getPlayer().getPositionY();
            HashMap<String , Object> body = new HashMap<>();
            body.put("Player" , Main.getClient().getPlayer().getUsername());
            body.put("Direction" , Direction.Right);
            body.put("X" , x);
            body.put("Y" , y);
            body.put("Time" , time);
            Main.getClient().getRequests().add(new Message(CommandType.MOVE_IN_FARM, body));
            return;
//            Main.getClient().getPlayer().setDirection(Direction.Right);
//            if (checkWalking()) {
//                Main.getClient().getPlayer().setPositionX(Main.getClient().getPlayer().getPositionX() + 5 * Gdx.graphics.getDeltaTime());
//            }
//            Main.getClient().getPlayer().setMoving(true);
//            moveAnimation();
        }

        Main.getClient().getPlayer().setTimer(copy);

//        else {
//            Main.getClient().getPlayer().setMoving(false);
//            Main.getClient().getPlayer().setTimer(0);
//            Main.getClient().getPlayer().getSprite().setRegion(Main.getClient().getPlayer().getAnimation().getKeyFrame(0));
//
//        }
    }

    public void setPlayerPosition() {
        for (User user : Main.getClient().getLocalGameState().getPlayers()) {
            user.setPositionX(TEXTURE_SIZE * (user.getFarm().getHome().getTopLeftX() + user.getFarm().getHome().getWidth() / 2));
            user.setPositionY(TEXTURE_SIZE * (90 - user.getFarm().getHome().getTopLeftY() - user.getFarm().getHome().getLength()));
        }
    }


    public Message checkWalking(Message message , Game game) {
        String Player = message.getFromBody("Player");
        Direction Direction = message.getFromBody("Direction");
        float x = message.getFromBody("X");
        float y = message.getFromBody("Y");
        float Time = message.getFromBody("Time");
        HashMap<String , Object> body = new HashMap<>();

        try {

            if (getTileByCoordinates((int) (x / TEXTURE_SIZE), 90 - (int)(y / TEXTURE_SIZE) , game.getGameState()).getGameObject() instanceof Walkable ||
                getTileByCoordinates((int) (x / TEXTURE_SIZE), 90 - (int)(y / TEXTURE_SIZE) , game.getGameState()).getGameObject() instanceof door) {

                body.put("Player" , Player);
                body.put("Direction" , Direction);
                body.put("X" , x);
                body.put("Y" , y);
                body.put("Time" , Time);
                return new Message(CommandType.CAN_MOVE , body);
            }

        }
        catch (Exception e) {
            body.put("Player" , Player);
            body.put("Direction" , Direction);
            body.put("Time" , Time);
            return new Message(CommandType.CAN_NOT_MOVE , body);
        }

        body.put("Player" , Player);
        body.put("Direction" , Direction);
        body.put("Time" , Time);
        return new Message(CommandType.CAN_NOT_MOVE , body);

    }

    public void Move(Message message , GameState gameState) {
        if (message.getCommandType() == CommandType.CAN_MOVE) {
            String user = message.getFromBody("Player");
            Direction Direction = message.getFromBody("Direction");
            float x = message.getFromBody("X");
            float y = message.getFromBody("Y");
            float Time = message.getFromBody("Time");
            for (User Player : gameState.getPlayers()) {
                if (user.trim().equals(Player.getUsername().trim())) {
                    Player.setPositionX(x);
                    Player.setPositionY(y);
                    Player.setDirection(Direction);
//                    if (Time >= 0.1f) {
//                        Player.setTimer(0.0f);
//                    }
//                    else {
//                        Player.setTimer(Time);
//                    }
                }
            }
        }
        if (message.getCommandType() == CommandType.CAN_NOT_MOVE) {
            String user = gson.fromJson(gson.toJson(message.getBody().get("Player")) , String.class);
            Direction Direction = gson.fromJson(gson.toJson(message.getBody().get("Direction")) , com.Graphic.model.Enum.Direction.class);
            float Time = gson.fromJson(gson.toJson(message.getBody().get("Time")) , float.class);
            for (User Player : gameState.getPlayers()) {
                if (Player.getUsername().trim().equals(user.trim())) {
                    Player.setDirection(Direction);
                    if (Time >= 0.1f) {
                        Player.setTimer(0.0f);
                    }
                    else {
                        Player.setTimer(Time);
                    }
                }
            }
        }
    }

    public void chooseMap() {
        Skin skin = new Skin(Gdx.files.internal("Skin/craftacular-ui.json"));
        TextField choose = new TextField("",skin);
        choose.setMessageText("Please select a number between 1 and 2");
        Table table = new Table();
        table.setFillParent(true);
        Texture backGround = TextureManager.get("Skin/back.png");
        Drawable backGroundDrawable = new TextureRegionDrawable(new TextureRegion(backGround));
        table.setBackground(backGroundDrawable);
        table.add(choose).center().size(200 , 48).row();
        table.row();
        TextButton button = new TextButton("",skin);
        Label label = new Label("Submit" , skin);
        button.add(label).center();
        table.add(button);
        GameMenu.getInstance().getStage().addActor(table);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                String result = choose.getText();
                if (result != null) {
                    try {
                        int index = Integer.parseInt(result);
                        if (index == 1 || index == 2) {
                            HashMap<String , Object> body = new HashMap<>();
                            body.put("Player" , Main.getClient().getPlayer());
                            body.put("Index" , index);
                            Main.getClient().getRequests().add(new Message(CommandType.FARM , body));
                            table.remove();
                        }
                        result = null;

                    } catch (Exception e) {

                    }
                }
            }
        });


    }

    public void setCenteredPosition(Actor actor, float centerX, float centerY) {
        actor.setPosition(centerX - actor.getWidth() / 2f, centerY - actor.getHeight() / 2f);
    }



    public Result addItem(String name ,int amount) {

//        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;
//        ItemRegistry itemRegistry = new ItemRegistry();
//        Items items = null;
//
//
//        itemRegistry.scanItems("model.Plants");
//        if ((items = itemRegistry.nameToItemMap.get(name)) != null) {
//            if (inventory.Items.containsKey(items)) {
//                inventory.Items.compute(items , (k,v) -> v+amount);
//            }
//            else if (currentGame.currentPlayer.getBackPack().getType().getRemindCapacity() == 0) {
//                return new Result(false , "Not Enough Capacity!");
//            }
//            else {
//                inventory.Items.put(items, amount);
//            }
//            return new Result(true , name + " Added Successfully");
//        }
//
//        itemRegistry.scanItems("model.Places");
//        if ((items=itemRegistry.nameToItemMap.get(name)) != null) {
//            if (inventory.Items.containsKey(items)) {
//                inventory.Items.compute(items , (k,v) -> v+amount);
//            }
//            else if (currentGame.currentPlayer.getBackPack().getType().getRemindCapacity() == 0) {
//                return new Result(false , "Not Enough Capacity!");
//            }
//            else {
//                inventory.Items.put(items, amount);
//            }
//            return new Result(true , name + " Added Successfully");
//        }
//
//        itemRegistry.scanItems("model.ToolsPackage");
//        if ((items=itemRegistry.nameToItemMap.get(name)) != null) {
//            if (inventory.Items.containsKey(items)) {
//                inventory.Items.compute(items , (k,v) -> v+amount);
//            }
//            else if (currentGame.currentPlayer.getBackPack().getType().getRemindCapacity() == 0) {
//                return new Result(false , "Not Enough Capacity!");
//            }
//            else {
//                inventory.Items.put(items, amount);
//            }
//            return new Result(true , name + " Added Successfully");
//        }
//
//        itemRegistry.scanItems("model.OtherItem");
//        if ((items=itemRegistry.nameToItemMap.get(name)) != null) {
//            if (inventory.Items.containsKey(items)) {
//                inventory.Items.compute(items , (k,v) -> v+amount);
//            }
//            else if (currentGame.currentPlayer.getBackPack().getType().getRemindCapacity() == 0) {
//                return new Result(false , "Not Enough Capacity!");
//            }
//            else {
//                inventory.Items.put(items, amount);
//            }
//            return new Result(true , name + " Added Successfully");
//        }

        return new Result(false , name + " not found!");
    }

    public void placeBarnOrCage(int x ,  int y , BarnOrCage barnOrCage , User user) {
        for (int i = x ; i < x + barnOrCage.getBarnORCageType().getWidth() ; i++) {
            for (int j = y ; j < y + barnOrCage.getBarnORCageType().getHeight() ; j++) {
                getTileByCoordinates(i , j , Main.getClient().getLocalGameState())
                    .setGameObject(new BarnOrCage(barnOrCage.getBarnORCageType() , x , y));
            }
        }
        for (User player : Main.getClient().getLocalGameState().getPlayers()) {
            if (player.getUsername().trim().equals(user.getUsername().trim())) {
                player.BarnOrCages.add(barnOrCage);
            }
        }
    }



    public Result print(){


        camera.position.set(Main.getClient().getPlayer().getPositionX() , Main.getClient().getPlayer().getPositionY() , 0f);
        //camera.update();
        //camera.unproject(camera.position);
        int x = (int) (camera.position.x - camera.viewportWidth * camera.zoom / 2) / TEXTURE_SIZE;
        int y = (int) (camera.position.y - camera.viewportHeight * camera.zoom / 2) / TEXTURE_SIZE;


        for (int i =x ; i< x + (camera.viewportWidth * camera.zoom )/TEXTURE_SIZE; i++) {
            for (int j = y; j < y + (camera.viewportHeight * camera.zoom) /TEXTURE_SIZE; j++) {
                //System.out.println("i: " + i + " j: " + j);
                try {

                    if (i >= 0 && i <= 90 && j >= 0 && j <= 90) {
                        Main.getBatch().draw(TextureManager.get("Places/Walkable.png"),
                            TEXTURE_SIZE * i, TEXTURE_SIZE * j, TEXTURE_SIZE, TEXTURE_SIZE);
                    }

                    if (getTileByCoordinates(i,90 -  j, gameMenu.gameState).getGameObject() instanceof UnWalkable) {
                        Main.getBatch().draw(TextureManager.get("Tree/unWalkable6.png"),
                            TEXTURE_SIZE * i, TEXTURE_SIZE *  j, TEXTURE_SIZE, TEXTURE_SIZE);
                    }

                    Main.getBatch().draw(
                        (TextureManager.get(getTileByCoordinates(i, 90 - j, gameMenu.gameState).getGameObject().getIcon())),
                        TEXTURE_SIZE * i, TEXTURE_SIZE *  j,
                        TEXTURE_SIZE * getTileByCoordinates(i, 90 - j, gameMenu.gameState).getGameObject().getTextureWidth(),
                        TEXTURE_SIZE * getTileByCoordinates(i, 90 - j, gameMenu.gameState).getGameObject().getTextureHeight());


                } catch (Exception e) {
                    //System.out.println(getTileByCoordinates(i , j , gameMenu.gameState).getGameObject().getIcon());
                }
            }
        }


        for (User player : gameMenu.gameState.getPlayers()) {

            GreenHouse greenHouse = player.getFarm().getGreenHouse();

            Main.getBatch().draw(TextureManager.get(GameTexturePath.GreenHouse.getPath()),
                (greenHouse.getCoordinateX() + 1) * TEXTURE_SIZE, TEXTURE_SIZE * (92 - greenHouse.getCoordinateY() - greenHouse.getLength()),
                (greenHouse.getWidth() - 2) * TEXTURE_SIZE, (greenHouse.getLength() - 2 )* TEXTURE_SIZE
            );
        }

        for (int i = 0 ; i < gameMenu.gameState.getPlayers().size() ; i ++) {
            if (gameMenu.gameState.getPlayers().get(i).isInFarmExterior()) {
                gameMenu.getUserRenderer().get(i).render(gameMenu.gameState.getPlayers().get(i));
            }
        }


        for (LakeRenderer lakeRenderer : gameMenu.getLakeRenderers()) {
            lakeRenderer.render();
        }
//        for (User player : Main.getClient().getLocalGameState().getPlayers()) {
//            for (BarnOrCage barnOrCage : player.BarnOrCages) {
//                for (Animal animal : barnOrCage.getAnimals()) {
//                    if (animal.isOut()) {
//                        animal.getSprite().draw(Main.getBatch());
//                    }
//                }
//            }
//        }
        for (AnimalRenderer animalRenderer : gameMenu.getAnimalRenderers()) {
            animalRenderer.render();
        }

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

        Inventory inventory= Main.getClient().getPlayer().getBackPack().inventory;
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
        for (Map.Entry<Items,Integer> entry: Main.getClient().getPlayer().getBackPack().inventory.Items.entrySet()) {
            if (entry.getKey() instanceof TrashCan){
                percent= ((TrashCan) entry.getKey()).type.getPercent();
                break;
            }
        }

        if (amount ==null || amount.equals(reminder)) {
            int increase=(reminder * percent *price)/100;
            TrashCan.removeItem(increase,Main.getClient().getPlayer().getBackPack().inventory.Items, items, reminder);
            return new Result(true,name + " completely removed from your inventory");
        }
        if (amount > reminder) {
            return new Result(false,"not enough "+name+" "+"in your inventory for remove");
        }
        int increase = (reminder * percent * price) / 100;
        TrashCan.removeItem(increase,Main.getClient().getPlayer().getBackPack().inventory.Items, items, reminder);
        return new Result(true , amount + " "+name+" "+"removed from your inventory");

    }

    public Result removeItemToTrashcan (String name, String amount){
        Integer number=null;
        if (amount != null) {
            number = Integer.parseInt(amount.trim());
        }

//        Inventory inventory=Main.getClient().getPlayer().getBackPack().inventory;
//        for (Map.Entry<Items,Integer> entry: inventory.Items.entrySet()){

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
//            if (entry.getKey().getName().equals(name)) {
//                if (entry.getKey().getSellPrice() == 0) {
//                    continue;
//                }
//                return increaseMoney(number , entry.getKey().getSellPrice() , entry.getKey() , name , entry.getValue()) ;
//            }
//
//        }
        return new Result(false , name + " not found");
    }

                                                                // Fish


    public Result addFishToInventory(FishingPole fishingPole) {
//        Inventory inventory=currentGame.currentPlayer.getBackPack().inventory;
        double random = Math.random() + 0.3;
//        int x = (int) (random * currentGame.currentWeather.getFishing() * (currentGame.currentPlayer.getLevelFishing() + 3));
//        int numberOfFish = Math.min(6, x);
//        StringBuilder result = new StringBuilder("number of Fishes: " + numberOfFish + "\n");
//        ArrayList<Fish> fishes = new ArrayList<>();
//
//        for (int i = 0; i < numberOfFish; i++) {
//
//            double rand = Math.random() + 0.4;
//            double quantity = (rand * (currentGame.currentPlayer.getLevelFishing() + 2) *
//                    fishingPole.type.getCoefficient()) / (7 - 2*currentGame.currentWeather.getFishing());
//            Quantity fishQuantity = productQuantity(quantity);
//
//            if (fishingPole.type.equals(FishingPoleType.TrainingRod)) {
//
//                switch (currentGame.currentDate.getSeason()) {
//                    case Spring:
//                        Fish springFish = new Fish(FishType.Herring, fishQuantity);
//                        fishes.add(springFish);
//                        result.append(springFish.getType().getName()).append(" ").append(springFish.getQuantity().getName()).append("\n");
//                        break;
//                    case Summer:
//                        Fish summerFish = new Fish(FishType.Sunfish, fishQuantity);
//                        fishes.add(summerFish);
//                        result.append(summerFish.getType().getName()).append(" ").append(summerFish.getQuantity().getName()).append("\n");
//                        break;
//                    case Fall:
//                        Fish fallFish = new Fish(FishType.Sardine, fishQuantity);
//                        fishes.add(fallFish);
//                        result.append(fallFish.getType().getName()).append(" ").append(fallFish.getQuantity().getName()).append("\n");
//                        break;
//                    case Winter:
//                        Fish winterFish = new Fish(FishType.Perch, fishQuantity);
//                        fishes.add(winterFish);
//                        result.append(winterFish.getType().getName()).append(" ").append(winterFish.getQuantity().getName()).append("\n");
//                        break;
//                    default:
//                        break;
//                }
//
//            }
//
//            else if (rand <= 0.2 || ( rand > 0.8 && rand <= 0.85 && currentGame.currentPlayer.getLevelFishing()!=4) ){
//
//                switch (currentGame.currentDate.getSeason()) {
//                    case Spring:
//                        Fish springFish = new Fish(FishType.Flounder, fishQuantity);
//                        fishes.add(springFish);
//                        result.append(springFish.getType().getName()).append(" ").append(springFish.getQuantity().getName()).append("\n");
//                        break;
//                    case Summer:
//                        Fish summerFish = new Fish(FishType.Tilapia, fishQuantity);
//                        fishes.add(summerFish);
//                        result.append(summerFish.getType().getName()).append(" ").append(summerFish.getQuantity().getName()).append("\n");
//                        break;
//                    case Fall:
//                        Fish fallFish = new Fish(FishType.Salmon, fishQuantity);
//                        fishes.add(fallFish);
//                        result.append(fallFish.getType().getName()).append(" ").append(fallFish.getQuantity().getName()).append("\n");
//                        break;
//                    case Winter:
//                        Fish winterFish = new Fish(FishType.Midnight_Carp, fishQuantity);
//                        fishes.add(winterFish);
//                        result.append(winterFish.getType().getName()).append(" ").append(winterFish.getQuantity().getName()).append("\n");
//                    default:
//                        break;
//                }
//            }
//            else if (rand <= 0.4 || (rand > 0.85 && rand <= 0.9 && currentGame.currentPlayer.getLevelFishing() != 4)) {
//
//                switch (currentGame.currentDate.getSeason()) {
//                    case Spring:
//                        Fish springFish = new Fish(FishType.Lionfish, fishQuantity);
//                        fishes.add(springFish);
//                        result.append(springFish.getType().getName()).append(" ").append(springFish.getQuantity().getName()).append("\n");
//                        break;
//                    case Summer:
//                        Fish summerFish = new Fish(FishType.Dorado, fishQuantity);
//                        fishes.add(summerFish);
//                        result.append(summerFish.getType().getName()).append(" ").append(summerFish.getQuantity().getName()).append("\n");
//                        break;
//                    case Fall:
//                        Fish fallFish = new Fish(FishType.Sardine, fishQuantity);
//                        fishes.add(fallFish);
//                        result.append(fallFish.getType().getName()).append(" ").append(fallFish.getQuantity().getName()).append("\n");
//                        break;
//                    case Winter:
//                        Fish winterFish = new Fish(FishType.Squid, fishQuantity);
//                        fishes.add(winterFish);
//                        result.append(winterFish.getType().getName()).append(" ").append(winterFish.getQuantity().getName()).append("\n");
//                        break;
//                    default:
//                        break;
//                }
//            }
//            else if (rand <= 0.6 || (rand > 0.9 && rand <= 0.95 && currentGame.currentPlayer.getLevelFishing() != 4)) {
//
//                switch (currentGame.currentDate.getSeason()) {
//                    case Spring:
//                        Fish springFish = new Fish(FishType.Herring, fishQuantity);
//                        fishes.add(springFish);
//                        result.append(springFish.getType().getName()).append(" ").append(springFish.getQuantity().getName()).append("\n");
//                        break;
//                    case Summer:
//                        Fish summerFish = new Fish(FishType.Sunfish, fishQuantity);
//                        fishes.add(summerFish);
//                        result.append(summerFish.getType().getName()).append(" ").append(summerFish.getQuantity().getName()).append("\n");
//                        break;
//                    case Fall:
//                        Fish fallFish = new Fish(FishType.Shad, fishQuantity);
//                        fishes.add(fallFish);
//                        result.append(fallFish.getType().getName()).append(" ").append(fallFish.getQuantity().getName()).append("\n");
//                        break;
//                    case Winter:
//                        Fish winterFish = new Fish(FishType.Tuna, fishQuantity);
//                        fishes.add(winterFish);
//                        result.append(winterFish.getType().getName()).append(" ").append(winterFish.getQuantity().getName()).append("\n");
//                        break;
//                    default:
//                        break;
//                }
//
//            }
//            else if (rand <= 0.8 || (rand > 0.95 && currentGame.currentPlayer.getLevelFishing() != 4)) {
//
//                switch (currentGame.currentDate.getSeason()) {
//                    case Spring:
//                        Fish springFish = new Fish(FishType.Ghostfish, fishQuantity);
//                        fishes.add(springFish);
//                        result.append(springFish.getType().getName()).append(" ").append(springFish.getQuantity().getName()).append("\n");
//                        break;
//                    case Summer:
//                        Fish summerFish = new Fish(FishType.Rainbow_Trout, fishQuantity);
//                        fishes.add(summerFish);
//                        result.append(summerFish.getType().getName()).append(" ").append(summerFish.getQuantity().getName()).append("\n");
//                        break;
//                    case Fall:
//                        Fish fallFish = new Fish(FishType.Blue_Discus, fishQuantity);
//                        fishes.add(fallFish);
//                        result.append(fallFish.getType().getName()).append(" ").append(fallFish.getQuantity().getName()).append("\n");
//                        break;
//                    case Winter:
//                        Fish winterFish = new Fish(FishType.Perch, fishQuantity);
//                        fishes.add(winterFish);
//                        result.append(winterFish.getType().getName()).append(" ").append(winterFish.getQuantity().getName()).append("\n");
//                        break;
//                    default:
//                        break;
//                }
//
//            }
//            else {
//                if (currentGame.currentPlayer.getLevelFishing() == 4){
//
//                    switch (currentGame.currentDate.getSeason()){
//                        case Spring:
//                            Fish springFish= new Fish(FishType.Legend,fishQuantity);
//                            fishes.add(springFish);
//                            result.append(springFish.getType().getName()).append(" ").append(springFish.getQuantity().getName()).append("\n");
//                            break;
//                        case Summer:
//                            Fish summerFish= new Fish(FishType.Dorado,fishQuantity);
//                            fishes.add(summerFish);
//                            result.append(summerFish.getType().getName()).append(" ").append(summerFish.getQuantity().getName()).append("\n");
//                            break;
//                        case Fall:
//                            Fish fallFish= new Fish(FishType.Squid,fishQuantity);
//                            fishes.add(fallFish);
//                            result.append(fallFish.getType().getName()).append(" ").append(fallFish.getQuantity().getName()).append("\n");
//                            break;
//                        case Winter:
//                            Fish winterFish= new Fish(FishType.Tuna,fishQuantity);
//                            fishes.add(winterFish);
//                            result.append(winterFish.getType().getName()).append(" ").append(winterFish.getQuantity().getName()).append("\n");
//                            break;
//                    }
//
//                }
//            }
//        }
//
//        boolean top=currentGame.currentPlayer.getLevelFishing() == 4;
//        currentGame.currentPlayer.increaseHealth(-Math.min ( ((FishingPole) currentGame.currentPlayer.currentTool).type.costEnergy(top) , currentGame.currentPlayer.getHealth()) ) ;
//        currentGame.currentPlayer.increaseFishingAbility(5);
//        for (Fish fish : fishes) {
//            if (inventory.Items.containsKey(fish)) {
//                inventory.Items.compute(fish , (k,v) -> v+1);
//            }
//            else if (currentGame.currentPlayer.getBackPack().getType().getRemindCapacity() !=0) {
//                inventory.Items.put(fish , 1);
//            }
//        }

        //return new Result(true, result.toString());
        return null;
    }
    public Result Fishing(String fishingPoleType) {
//        if (!(currentGame.currentPlayer.currentTool instanceof FishingPole)) {
//            return new Result(false, "your current tool is not a FishingPole!");
//        }
//
//        if (!checkCoordinateForFishing()) {
//            boolean top=currentGame.currentPlayer.getLevelFishing() == 4;
//            currentGame.currentPlayer.increaseHealth(-Math.min ( ((FishingPole) currentGame.currentPlayer.currentTool).type.costEnergy(top) , currentGame.currentPlayer.getHealth()) ) ;
//            return new Result(false, "you can't fishing because lake is not around you");
//        }
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
                queue.add(getTileByCoordinates(animal.getPositionX() + x[i] , animal.getPositionY() + y[i] ,
                    Main.getClient().getLocalGameState()));
            }
        }
        tiles.add(getTileByCoordinates(animal.getPositionX() , animal.getPositionY() , Main.getClient().getLocalGameState() ));

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
                if (getTileByCoordinates(tile.getX() + x[i] , tile.getY() + y[i] , Main.getClient().getLocalGameState()) == null) {
                    continue;
                }
                if (tiles.contains(getTileByCoordinates(tile.getX() + x[i] , tile.getY() + y[i] , Main.getClient().getLocalGameState()))) {
                    continue;
                }
                Tile next = getTileByCoordinates(tile.getX() + x[i] , tile.getY() + y[i] , Main.getClient().getLocalGameState());
                cameFrom.put(next, tile);
                queue.add(next);
            }
        }
        return null;
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
        Tile tile = getTileByCoordinates(goalX , goalY , Main.getClient().getLocalGameState() );
        if (!(tile.getGameObject() instanceof Walkable)) {
            return new Result(false , "yot can't shepherd animals on this coordinate!");
        }
        if (Main.getClient().getLocalGameState().currentWeather.equals(Weather.Snowy) ||
            Main.getClient().getLocalGameState().currentWeather.equals(Weather.Rainy) ||
            Main.getClient().getLocalGameState().currentWeather.equals(Weather.Stormy) ) {
            return new Result(false , "The weather conditions isn't suitable");
        }
        if (animal.getType().equals(AnimalType.pig) && Main.getClient().getLocalGameState().currentDate.getSeason().equals(Season.Winter)) {
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
        Inventory inventory = Main.getClient().getPlayer().getBackPack().inventory;
        MarketItem marketItem = new MarketItem(MarketItemType.Hay);
        if (inventory.Items.containsKey(marketItem)) {
            requestForFeedHay(animal);
//            inventory.Items.compute(marketItem , (k,v) -> v-1);
//            inventory.Items.entrySet().removeIf(entry -> entry.getValue()==0);
//            animal.setFeedToday(true);
            return new Result(true, "you fed "+animal.getName()+" successfully!");
        }

        return new Result(false ,"You don't have Hay in your inventory!");
    }



    public void receiveFeedHay(Message message) {
        Animal animal = message.getFromBody("Animal");
        MarketItem marketItem = message.getFromBody("Hay");
        animal.setFeedToday(true);
        Main.getClient().getPlayer().getBackPack().inventory.Items.compute(marketItem , (k,v) -> v-1);
        Main.getClient().getPlayer().getBackPack().inventory.Items.entrySet().removeIf(entry -> entry.getValue() == 0);
    }

    public void requestForFeedHay(Animal animal) {
        HashMap<String , Object> feedHay = new HashMap<>();
        feedHay.put("Animal", animal);
        feedHay.put("Hay" , new MarketItem(MarketItemType.Hay));
        feedHay.put("Player" , Main.getClient().getPlayer());
        Main.getClient().getRequests().add(new Message(CommandType.FEED_HAY , feedHay ));
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
        HashMap<String , Object> body = new HashMap<>();
        body.put("Animal", animal);
        body.put("Product" , animalproduct);
        Main.getClient().getRequests().add(new Message(CommandType.COLLECT_PRODUCT , body));
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
        HashMap<String , Object> body = new HashMap<>();
        if (animal.getType().equals(AnimalType.sheep) ) {
            if (!(Main.getClient().getPlayer().currentTool instanceof Shear)) {
                return new Result(false , "for collect wool you should use shear");
            }
            body.put("Player" , Main.getClient().getPlayer());
            body.put("Money" , (int) (4 * Main.getClient().getLocalGameState().currentWeather.getEnergyCostCoefficient() ));
            Main.getClient().getRequests().add(new Message(CommandType.CHANGE_MONEY , body));
            //animal.increaseFriendShip(5);
        }
        if (animal.getType().equals(AnimalType.goat)) {
            if (!(Main.getClient().getPlayer().currentTool instanceof MilkPail)) {
                return new Result(false , "for collect milk you should use MilkPail");
            }
            body.put("Player" , Main.getClient().getPlayer());
            body.put("Money" , (int) (4 * Main.getClient().getLocalGameState().currentWeather.getEnergyCostCoefficient() ));
            Main.getClient().getRequests().add(new Message(CommandType.CHANGE_MONEY , body));
            animal.increaseFriendShip(5);
        }
        if (animal.getType().equals(AnimalType.cow)) {
            if (!(Main.getClient().getPlayer().currentTool instanceof MilkPail)) {
                return new Result(false , "for collect milk you should use MilkPail");
            }
            body.put("Player" , Main.getClient().getPlayer());
            body.put("Money" , (int) (4 * Main.getClient().getLocalGameState().currentWeather.getEnergyCostCoefficient() ));
            Main.getClient().getRequests().add(new Message(CommandType.CHANGE_MONEY , body));
            animal.increaseFriendShip(5);
        }

        return null;
    }

    public Result sellAnimal(Animal animal) {
        requestForSellAnimal(animal);
        //double x = animal.getFriendShip()/1000 + 0.3;
        //currentGame.currentPlayer.increaseMoney((int) (animal.getType().getPrice() * x) );
        return new Result(true , animal.getName() +" was sold successfully");

    }

    public void requestForSellAnimal(Animal animal) {
        HashMap<String , Object> sellAnimals = new HashMap<>();
        sellAnimals.put("Player" , Main.getClient().getPlayer());
        sellAnimals.put("Animal" , animal);
        Main.getClient().getRequests().add(new Message(CommandType.SELL_ANIMAL , sellAnimals));
    }


    public void receiveRequestForSellAnimal(Message message) {
        User player = message.getFromBody("Player");
        Animal animal = message.getFromBody("Animal");

        BarnOrCage current=null;
        for (User user : gameMenu.gameState.getPlayers()) {
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
                            CraftingRenderer.bombAnimation.getKeyFrame(craftingItem.getTimer()), TEXTURE_SIZE * i, TEXTURE_SIZE * (90 - j));
                    }
                    catch (Exception e) {}
                }
            }
            if (!CraftingRenderer.bombAnimation.isAnimationFinished(craftingItem.getTimer())) {
                craftingItem.setTimer(craftingItem.getTimer() + Gdx.graphics.getDeltaTime());
            }

            else {

                craftingItem.setTimer(0);
                CraftingItem.Bombing = null;
                getTileByCoordinates(craftingItem.getX(), craftingItem.getY() , gameMenu.gameState).setGameObject(new Walkable());
                for (int i = craftingItem.getX(); i < craftingItem.getX() + domain; i++) {
                    for (int j = craftingItem.getY(); j < craftingItem.getY() + domain; j++) {
                        try {
                            Tile target = getTileByCoordinates(i, j , gameMenu.gameState);

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

            Main.getBatch().draw(CraftingRenderer.sprinklerAnimation.getKeyFrame(craftingItem.getTimer()),
                TEXTURE_SIZE * (craftingItem.getX() - 2) ,
                TEXTURE_SIZE * (90 - craftingItem.getY() - 2 ) ,
                   TEXTURE_SIZE * 4 , TEXTURE_SIZE * 4);

            if (!CraftingRenderer.sprinklerAnimation.isAnimationFinished(craftingItem.getTimer())) {
                craftingItem.setTimer(craftingItem.getTimer() + Gdx.graphics.getDeltaTime());
            } else {
                craftingItem.setTimer(0);
                CraftingItem.currentSprinkler = null;
                for (int i = -domain / 2; i < domain / 2; i++) {
                    for (int j = -domain / 2; j < domain / 2; j++) {
                        try {
                            Tile tile1 = getTileByCoordinates(i + craftingItem.getX(), j + craftingItem.getY() , gameMenu.gameState);
                            if (tile1.getGameObject() instanceof Tree) {
                                ((Tree) tile1.getGameObject()).setLastWater(gameMenu.gameState.currentDate.clone());
                            } else if (tile1.getGameObject() instanceof ForagingSeeds) {
                                ((ForagingSeeds) tile1.getGameObject()).setLastWater(gameMenu.gameState.currentDate.clone());
                            } else if (tile1.getGameObject() instanceof GiantProduct) {
                                ((GiantProduct) tile1.getGameObject()).setLastWater(gameMenu.gameState.currentDate.clone());
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
        Inventory inventory = Main.getClient().getPlayer().getBackPack().inventory;
        if (! inventory.Items.containsKey(craftingItem)) {
            return new Result(false , "you don't have this craft in your inventory");
        }
        Main.getClient().getPlayer().setIsPlaceArtisanOrShippingBin(true);
        gameMenu.setWithMouse(new Sprite(TextureManager.get(craftingItem.getType().getIcon())));
        gameMenu.getWithMouse().setAlpha(0.5f);
        Main.getClient().getPlayer().setDroppedItem(craftingItem);

        return new Result(true , "");
    }

    private boolean bool = false;

    public void placeItem() {
        if (Main.getClient().getPlayer().getDroppedItem() != null) {
            camera.setToOrtho(false , Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
            if (Main.getClient().getPlayer().isPlaceArtisanOrShippingBin() &&
                ! Main.getClient().getPlayer().isWaiting()) {
                gameMenu.getWithMouse().setPosition(
                    gameMenu.getVector().x - gameMenu.getWithMouse().getWidth() / 2,
                    gameMenu.getVector().y - gameMenu.getWithMouse().getHeight() / 2);
                Marketing.getInstance().printMapForCreate();
                gameMenu.getWithMouse().draw(Main.getBatch());
            }
            if (Main.getClient().getPlayer().isPlaceArtisanOrShippingBin() &&
                !Main.getClient().getPlayer().isWaiting() && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                Main.getClient().getPlayer().setWaiting(true);
            }
            if (Main.getClient().getPlayer().isWaiting()) {
                setCraftInFarm(gameMenu.getWithMouse(), Main.getClient().getPlayer().getDroppedItem());
            }
        }
    }

    public void setCraftInFarm(Sprite sprite , Items items) {
        int x = (int) (sprite.getX() / TEXTURE_SIZE) + 60 * Main.getClient().getPlayer().topLeftX;
        int y =30 -  (int) (sprite.getY() / TEXTURE_SIZE) + 60 * Main.getClient().getPlayer().topLeftY;

        try {
            if (!(getTileByCoordinates(x, y , Main.getClient().getLocalGameState()).getGameObject() instanceof Walkable) && !bool) {
                Dialog dialog = Marketing.getInstance().createDialogError();
                Label content = new Label("you can't place craft on this place", new Label.LabelStyle(getFont(), Color.BLACK));
                Marketing.getInstance().addDialogToTable(dialog, content, gameMenu);
                Main.getClient().getPlayer().setWaiting(false);
            } else {
                Marketing.getInstance().printMapForCreate();
                bool = true;
                getTileByCoordinates(x, y , Main.getClient().getLocalGameState()).setGameObject(items);

                if (Gdx.input.isKeyJustPressed(ENTER)) {
                    TextButton Confirm = Marketing.getInstance().makeConfirmButton(currentMenu.getMenu());
                    TextButton TryAgain = Marketing.getInstance().makeTryAgainButton(currentMenu.getMenu());

                    Confirm.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent changeEvent, Actor actor) {
                            Confirm.remove();
                            TryAgain.remove();
                            Main.getClient().getPlayer().setIsPlaceArtisanOrShippingBin(false);
                            Main.getClient().getPlayer().setWaiting(false);
                            Main.getClient().getPlayer().setDroppedItem(null);
                            requestForPlaceCraft(items , x , y);
                            advanceItem(items, -1);
                            items.setX(x);
                            items.setY(y);
                            camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                            choosePlace = false;
                            try {
                                System.out.println("buy");
                                Main.getClient().getPlayer().getFarm().shippingBins.add((ShippingBin) items);
                            }
                            catch (Exception e) {
                                gameMenu.getCraftingRenderers().add(new CraftingRenderer((CraftingItem) items));
                                System.out.println("don't worry");
                            }
                        }
                    });

                    TryAgain.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent changeEvent, Actor actor) {
                            Main.getClient().getPlayer().setWaiting(false);
                            Confirm.remove();
                            TryAgain.remove();
                            getTileByCoordinates(x, y , Main.getClient().getLocalGameState()).setGameObject(new Walkable());
                            bool = false;
                        }
                    });
                }
            }
        }
        catch (Exception e) {

        }

    }

    public void requestForPlaceCraft(Items items , int x , int y) {
        HashMap<String , Object> body = new HashMap<>();
        body.put("X", x);
        body.put("Y", y);
        body.put("Item" , items);
        body.put("Player" , Main.getClient().getPlayer());
        Main.getClient().getRequests().add(new Message(CommandType.PLACE_CRAFT_SHIPPING_BIN , body));
    }

    public void AnswerPlaceCraft(Message message , Game game) {
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
                if (items instanceof CraftingItem) {
                    //gameMenu.getOnFarm().add((CraftingItem) items);
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
        game.getDiffQueue().add(new Message(CommandType.PLACE_CRAFT_SHIPPING_BIN , body));
    }

    public void showSelectBoxOnCrafting() {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {

        int x = (int) (gameMenu.getVector().x/TEXTURE_SIZE);
        int y = (int) (gameMenu.getVector().y/TEXTURE_SIZE);

            if (getTileByCoordinates(x , 90 - y , Main.getClient().getLocalGameState())
                .getGameObject() instanceof CraftingItem ) {

                System.out.println("yes");

                SelectBox selectBox = craftBox((CraftingItem) getTileByCoordinates(x, 90 - y , Main.getClient().getLocalGameState())
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

    public void showProgressOnArtisans() {
//        for (int i = 0 ; i < craftingItem.getItems().size() ; i++) {
//            Main.getBatch().draw(TextureManager.get("Mohamadreza/bgProgress.png") ,
//                TEXTURE_SIZE * craftingItem.getX() , TEXTURE_SIZE * (90 - craftingItem.getY()) + TEXTURE_SIZE + (TEXTURE_SIZE / 2) * i ,
//                TEXTURE_SIZE * 2 , TEXTURE_SIZE / 2);
//        }
        for (CraftingRenderer craftingRenderer : gameMenu.getCraftingRenderers()) {
            craftingRenderer.renderBg();
        }


        Main.getBatch().end();

        for (CraftingRenderer craftingRenderer : gameMenu.getCraftingRenderers()) {
            craftingRenderer.render();
        }

//        for (int i = 0 ; i < gameMenu.getCraftingItems().size() ; i++) {
//            for (int j = 0 ; j < gameMenu.getCraftingItems().get(i).getItems().size() ; j++) {
//                int size = gameMenu.getCraftingItems().get(i).getItems().size();
//                gameMenu.getShapeRenderers().get(i * size + j).setProjectionMatrix(Main.getBatch().getProjectionMatrix());
//                gameMenu.getShapeRenderers().get(i * size + j).begin(ShapeRenderer.ShapeType.Line);
//                gameMenu.getShapeRenderers().get(i * size + j).setColor(0,1,0,1);
//                float x = getX(gameMenu.getCraftingItems().get(i) , j);
//                gameMenu.getShapeRenderers().get(i * size + j).rect(
//                    TEXTURE_SIZE * gameMenu.getCraftingItems().get(i).getX() + 6 ,
//                       TEXTURE_SIZE * (90 - gameMenu.getCraftingItems().get(i).getY()) +
//                           TEXTURE_SIZE + (TEXTURE_SIZE/2) * i + 3 ,
//                    (TEXTURE_SIZE * 2) * x - 10 , TEXTURE_SIZE / 2 - 7);
//
//                gameMenu.getShapeRenderers().get(i * size + j).end();
//            }
//        }
//        for (int i = 0 ; i < craftingItem.getItems().size() ; i++) {
//            craftingItem.getShapeRenderers().get(i).setProjectionMatrix(Main.getBatch().getProjectionMatrix());
//            craftingItem.getShapeRenderers().get(i).begin(ShapeRenderer.ShapeType.Filled);
//            craftingItem.getShapeRenderers().get(i).setColor(0,1,0,1);
//            float x = getX(craftingItem, i);
//            craftingItem.getShapeRenderers().get(i).rect(TEXTURE_SIZE * craftingItem.getX() + 6 ,
//                TEXTURE_SIZE * (90 - craftingItem.getY()) + TEXTURE_SIZE + (TEXTURE_SIZE/2) * i + 3 ,
//                (TEXTURE_SIZE * 2) * x - 10 , TEXTURE_SIZE/2 - 7);
//
//            craftingItem.getShapeRenderers().get(i).end();
//        }
        Main.getBatch().begin();

    }

    public static float getX(CraftingItem craftingItem, int i) {
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
                        HashMap<String , Object> body = new HashMap<>();
                        body.put("Item" , craftingItem.getItems().get(i));
                        body.put("amount" , 1);
                        body.put("Player" , Main.getClient().getPlayer());
                        Main.getClient().getRequests().add(new Message(CommandType.CHANGE_INVENTORY , body));
                        result.append(artisanType.getName()+", ");
                        craftingItem.getDateHours().remove(i);
//                        gameMenu.getShapeRenderers().remove()
//                        craftingItem.getShapeRenderers().remove(i);
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

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && Main.getClient().getPlayer().isInFarmExterior()) {
            try {
                Tile tile = getTileByCoordinates(x , 90 - y , Main.getClient().getLocalGameState());
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
        Inventory inventory = Main.getClient().getPlayer().getBackPack().inventory;
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
                        if (Main.getClient().getPlayer().getBackPack().inventory.Items.get(items) < number) {
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
                            HashMap<String , Object> body = new HashMap<>();
                            body.put("Item" , items);
                            body.put("amount" , -number);
                            body.put("Player" , Main.getClient().getPlayer());
                            Main.getClient().getRequests().add(new Message(CommandType.CHANGE_INVENTORY , body));
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

    public Result backToMainMenu () {
//        if (App.currentUser.isCurrently_in_game())
//            return new Result(false, RED+"You Are Currently in a Game!"+RESET);
//        else {
//            currentMenu = com.Graphic.model.Enum.Menu.MainMenu;
//            return new Result(true, GREEN+"Returned to Main Menu."+RESET);
//        }
        return null;
    }

    public void startGamePlayer () {

        int playerNumber = currentGame.getGameState().getPlayers().size();

        User user = Main.getClient().getPlayer();

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
            if (playerNumber == 1) {
                user.setIcon("all image/Crops/Cactus_Stage_6.png");
                user.topLeftX = 0;
                user.topLeftY = 0;
            } else if (playerNumber == 2) {
                user.setIcon("all image/Special_item/Cursed_Mannequin_%28F%29.png");
                user.topLeftX = 1;
                user.topLeftY = 0;
            } else if (playerNumber == 3) {
                user.setIcon("all image/Special_item/Deconstructor.png");
                user.topLeftX = 0;
                user.topLeftY = 1;
            } else if (playerNumber == 4) {
                user.setIcon("all image/Special_item/Wood_Chipper_On.png");
                user.topLeftX = 1;
                user.topLeftY = 1;
            }
//            createInitialFarm(choice);
            GameMenu.getInstance().initializeNPCs();
            break;
        }

    }
    public void startNewGameServer (String input) {

//        currentGame = new Game();
//        //currentGame.currentMenu = currentMenu;
//
//
////        String user1name = GameMenuCommands.makeNewGame.getMatcher(input).group("username1");
////        String user2name = GameMenuCommands.makeNewGame.getMatcher(input).group("username2"); // could be null
////        String user3name = GameMenuCommands.makeNewGame.getMatcher(input).group("username3");// could be null
////
////        User user1 = findUserByUsername(user1name);
////        User user2 = findUserByUsername(user2name);
////        User user3 = findUserByUsername(user3name);
////
////        if (user1 == null){
////            System.out.println("User1 Not Found!");
////            return;
////        }
////        if (user2name != null) {
////            if (user2 == null) {
////                System.out.println("User2 Not Found! Try Again.");
////                return;
////            }
////        }
////        if (user3name != null) {
////            if (user3 == null) {
////                System.out.println("User3 Not Found! Try Again.");
////                return;
////            }
////        }
////        if (user1.isCurrently_in_game()){
////            System.out.println("User1 Currently in Game! Try Again.");
////            return;
////        }
////        else user1.setCurrently_in_game(true);
////
////        if (user2name != null) {
////            if (findUserByUsername(user2name).isCurrently_in_game()) {
////                System.out.println("User2 Currently in Game! Try Again.");
////                return;
////            }
////            else user2.setCurrently_in_game(true);
////        }
////        if (user3name != null) {
////            if (findUserByUsername(user3name).isCurrently_in_game()) {
////                System.out.println("User3 Currently in Game! Try Again.");
////                return;
////            }
////            else user3.setCurrently_in_game(true);
////        }
////
////        if (user1.getUsername().equals(currentUser.getUsername())) {
////            System.out.println(RED+"Invite Users Other than Yourself! Try Again."+RESET);
////            return;
////        }
////        if (user2 != null) {
////            if (user2.getUsername().equals(currentUser.getUsername())) {
////                System.out.println(RED+"Invite Users Other than Yourself! Try Again."+RESET);
////                return;
////            }
////        }
////        if (user3 != null) {
////            if (user3.getUsername().equals(currentUser.getUsername())) {
////                System.out.println(RED+"Invite Users Other than Yourself! Try Again."+RESET);
////                return;
////            }
////        }
////        currentGame.players.add(currentUser);
////        currentGame.currentPlayer = currentUser;
////        System.out.println(RED+"player selected"+RESET);
//
////        currentGame.players.add(findUserByUsername(user1name));
////        if (user2name != null) currentGame.players.add(findUserByUsername(user2name));
////        if (user3name != null) currentGame.players.add(findUserByUsername(user3name));
//
//        currentGame.players.add(new User("Ario", "Ario", "ario.ebr@gmail.com", "man", 0, 200, PasswordHashUtil.hashPassword("Ebrahim84?"), SecurityQuestions.FavoriteAnimal, "dog"));
//        currentGame.players.add(new User("Erfan", "Erfan", "ario.ebr@gmail.com", "man", 0, 200, PasswordHashUtil.hashPassword("Ebrahim84?"), SecurityQuestions.FavoriteAnimal, "dog"));
//        currentGame.players.add(new User("Mamali", "Mamali", "ario.ebr@gmail.com", "man", 0, 200, PasswordHashUtil.hashPassword("Ebrahim84?"), SecurityQuestions.FavoriteAnimal, "dog"));
//        currentGame.players.add(new User("Ilia", "Ilia", "ario.ebr@gmail.com", "man", 0, 200, PasswordHashUtil.hashPassword("Ebrahim84?"), SecurityQuestions.FavoriteAnimal, "dog"));
//        setTimeAndWeather();
//        currentGame.currentPlayer = currentGame.players.getFirst();
//        currentUser = currentGame.players.getFirst();
//
//
//        // done
//
//
//
//        int counter = 1;
//        for (User user: currentGame.players) {
//
//            currentGame.currentPlayer = user;
//            while (true) {
//
////                System.out.println(currentPlayer.getUsername() + "'s turn to choose map(1 or 2)");
////                String choiceString = scanner.nextLine();
////                String[] splitChoice = choiceString.trim().split("\\s+");
////
////                int choice;
////                try {
////                    choice = Integer.parseInt(splitChoice[2]);
////                } catch (Exception e) {
////                    System.out.println("Please put a integer between 1 and 2!");
////                    continue;
////                }
////                if (choice != 1 && choice != 2) {
////                    System.out.println("Choose between 1 and 2!");
////                    continue;
////
//                int choice = 1; // TODO باید پاک بشه
//
//                if (counter == 1) {
//                    user.setIcon("all image/Crops/Cactus_Stage_6.png");
//                    user.topLeftX = 0;
//                    user.topLeftY = 0;
//                }
//                else if (counter == 2) {
//                    user.setIcon("all image/Special_item/Cursed_Mannequin_%28F%29.png");
//                    user.topLeftX = 1;
//                    user.topLeftY = 0;
//                }
//                else if (counter == 3) {
//                    user.setIcon("all image/Special_item/Deconstructor.png");
//                    user.topLeftX = 0;
//                    user.topLeftY = 1;
//                }
//                else if (counter == 4) {
//                    user.setIcon("all image/Special_item/Wood_Chipper_On.png");
//                    user.topLeftX = 1;
//                    user.topLeftY = 1;
//                }
//                //createInitialFarm(choice);
//                user.initAnimations();
//                GameMenu.getInstance().initializeNPCs();
//                counter++;
//                break;
//            }
//        }
//        currentGame.currentPlayer = currentGame.players.getFirst();
//
//
//
//        // Form Friendships
//        for (int i = 0; i < currentGame.players.size(); i++) {
//            for (int j = i + 1; j < currentGame.players.size(); j++) {
//                HumanCommunications f = new HumanCommunications(currentGame.players.get(i), currentGame.players.get(j));
//                currentGame.friendships.add(f);
//            }
//        }
//        // set initial Cooking Recipes from beginning
//        for (User player: currentGame.players) {
//            player.setRecipes(Recipe.createAllRecipes());
//        }
//        //buildHall();
//        //buildNpcVillage();
//        sortMap(currentGame.bigMap);
//        initializePlayer();
//        //fadeToNextDay();
    }
    public void startNewGame1 (String input) throws IOException {

//        currentGame = new Game();
//        currentGame.currentPlayer = currentUser;
//        currentGame.currentMenu = currentMenu;
//
//
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
//                System.out.println("User2 Not Found!");
//                return;
//            }
//        }
//        if (user3name != null) {
//            if (user3 == null) {
//                System.out.println("User3 Not Found!");
//                return;
//            }
//        }
//        if (user1.isCurrently_in_game()){
//            System.out.println("User1 Currently in Game!");
//            return;
//        }
//        else user1.setCurrently_in_game(true);
//
//        if (user2name != null) {
//            if (findUserByUsername(user2name).isCurrently_in_game()) {
//                System.out.println("User2 Currently in Game!");
//                return;
//            }
//            else user2.setCurrently_in_game(true);
//        }
//        if (user3name != null) {
//            if (findUserByUsername(user3name).isCurrently_in_game()) {
//                System.out.println("User3 Currently in Game!");
//                return;
//            }
//            else user3.setCurrently_in_game(true);
//        }
//
//        if (user1.getUsername().equals(currentUser.getUsername())) {
//            System.out.println(RED+"Invite Users Other than Yourself!"+RESET);
//            return;
//        }
//        if (user2 != null) {
//            if (user2.getUsername().equals(currentUser.getUsername())) {
//                System.out.println(RED+"Invite Users Other than Yourself!"+RESET);
//                return;
//            }
//        }
//        if (user3 != null) {
//            if (user3.getUsername().equals(currentUser.getUsername())) {
//                System.out.println(RED+"Invite Users Other than Yourself!"+RESET);
//                return;
//            }
//        }
//
//
//        currentGame.players.add(currentUser);
//        Main.getClient().getPlayer() = currentUser;
//        System.out.println(RED+"player selected"+RESET);

//        currentGame.players.add(findUserByUsername(user1name));
//        if (user2name != null) currentGame.players.add(findUserByUsername(user2name));
//        if (user3name != null) currentGame.players.add(findUserByUsername(user3name));

        //buildHall();
        //buildNpcVillage();
        //sortMap(currentGame.bigMap);
        //fadeToNextDay();
    }


                                                                    // Erfan


                                                                  // input command Date
                                                                   // input command plant


    public Result WateringPlant (String direction) {

        if (directionIncorrect(direction))
            return new Result(false, RED+"Direction is invalid"+RESET);

        int dir = Integer.parseInt(direction);

        if (!(Main.getClient().getPlayer().currentTool instanceof WateringCan))
            return new Result(false, RED+"سطل اب رو بردار دوست من"+RESET);

        if (!Main.getClient().getPlayer().isHealthUnlimited())
            Main.getClient().getPlayer().increaseHealth(Main.getClient().getPlayer().currentTool.healthCost());

        Tile tile = getTileByDir(dir);
        GameObject object = tile.getGameObject();

        if ((!Main.getClient().getPlayer().getFarm().isInFarm(tile.getX(), tile.getY())) &&
                !Main.getClient().getPlayer().getSpouse().getFarm().isInFarm(tile.getX(), tile.getY()))
            return new Result(false, RED+"You must select your tile"+RESET);

        if (((WateringCan) Main.getClient().getPlayer().currentTool).getReminderCapacity() < 1)
            return new Result(false, RED+"ظرفت خالیه مشتی"+RESET);

        if (object instanceof ForagingSeeds) {

            ((WateringCan) Main.getClient().getPlayer().currentTool).decreaseWater(1);
            ((ForagingSeeds) object).setLastWater(currentGame.getGameState().getCurrentDate());
            return new Result(true, BLUE+"The plant has been watered!\uD83C\uDF31"+RESET);

        }
        else if (object instanceof Tree) {

            ((WateringCan) Main.getClient().getPlayer().currentTool).decreaseWater(1);
            ((Tree) object).setLastWater(currentGame.getGameState().getCurrentDate());
            return new Result(true, BLUE+"The plant has been watered!\uD83C\uDF31"+RESET);

        }
        else if (object instanceof GiantProduct) {

            ((WateringCan) Main.getClient().getPlayer().currentTool).decreaseWater(1);
            ((GiantProduct) object).setLastWater(currentGame.getGameState().getCurrentDate());
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
    public Result buildGreenHouse () {

        if (Main.getClient().getPlayer().getFarm().getGreenHouse().isCreated())
            return new Result(false, BRIGHT_BLUE+"The greenhouse has been build!"+RESET);

        if (!checkAmountProductAvailable(new Wood(), GreenHouse.requiredWood))
            return new Result(false, RED+"You don't have enough wood!"+RESET);

        if (Main.getClient().getPlayer().getMoney() < GreenHouse.requiredCoins )
            return new Result(false, RED+"You don't have enough Coin!"+RESET);

        Main.getClient().getPlayer().increaseMoney(-GreenHouse.requiredCoins);
        advanceItem(new Wood(), -GreenHouse.requiredWood);

        Main.getClient().getPlayer().getFarm().getGreenHouse().setCreated(true);

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

        if (!Main.getClient().getPlayer().getFarm().isInFarm(x, y))
            return new Result(false, RED+"Pick from your own farm!"+RESET);

        Tile tile = getTileByCoordinates(x, y , Main.getClient().getLocalGameState());

        if (tile.getGameObject() instanceof Tree)
            return new Result(true, showTree((Tree) tile.getGameObject()));
        if (tile.getGameObject() instanceof ForagingSeeds)
            return new Result(true, showForaging((ForagingSeeds) tile.getGameObject()));
        if (tile.getGameObject() instanceof GiantProduct)
            return new Result(true, showGiant((GiantProduct) tile.getGameObject()));

        if ((!Main.getClient().getPlayer().getFarm().isInFarm(tile.getX(), tile.getY())) &&
                !Main.getClient().getPlayer().getSpouse().getFarm().isInFarm(tile.getX(), tile.getY()))
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

        if ((!Main.getClient().getPlayer().getFarm().isInFarm(tile.getX(), tile.getY())) &&
                !Main.getClient().getPlayer().getSpouse().getFarm().isInFarm(tile.getX(), tile.getY()))
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

        Inventory inventory = Main.getClient().getPlayer().getBackPack().inventory;

        for (Map.Entry <Items,Integer> entry: inventory.Items.entrySet())
            if (entry instanceof WateringCan)
                return new Result(true, BLUE+"Water Remaining : "
                        +RESET+((WateringCan) entry).getReminderCapacity());

        return new Result(false, BLUE+"کدوم سطل سلطان"+RESET);
    }
    public void itemEquip (String name) {

        Inventory inventory = Main.getClient().getPlayer().getBackPack().inventory;

        for (Map.Entry<Items,Integer> entry: inventory.Items.entrySet())
            if (entry.getKey() != null && entry.getKey().getName().equals(name))
                Main.getClient().getPlayer().currentItem = entry.getKey();


    }
    public HashMap<String, String> availableTools() {

        Inventory inventory = Main.getClient().getPlayer().getBackPack().inventory;
        HashMap<String, String> availableTools = new HashMap<>();

        for (Map.Entry<Items, Integer> entry : inventory.Items.entrySet())
            if (entry.getKey() instanceof Tools)
                availableTools.put(entry.getKey().getName(), entry.getKey().getInventoryIconPath());

        return availableTools;
    }
    public Result upgradeTool (String name) {
//         MarketType marketType=MarketType.wallOrDoor(Main.getClient().getPlayer().getPositionX() , Main.getClient().getPlayer().getPositionY());
//         if (marketType!=MarketType.Blacksmith) {
//             return new Result(false , "you are not in BlackSmith Market. please go there");
//         }
//         Inventory inventory = Main.getClient().getPlayer().getBackPack().inventory;
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
//                         Main.getClient().getPlayer().increaseMoney( - axeType.getPrice());
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
//                        Main.getClient().getPlayer().increaseMoney( - hoeType.getPrice());
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
//                        Main.getClient().getPlayer().increaseMoney( - pickAxeType.getPrice());
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
//                        Main.getClient().getPlayer().increaseMoney( - wateringCanType.getPrice());
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
//                        Main.getClient().getPlayer().increaseMoney( - trashCanType.getPrice());
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
        if (!Main.getClient().getPlayer().isHealthUnlimited()) {
            if (Main.getClient().getPlayer().getHealth() < Main.getClient().getPlayer().currentTool.healthCost())
                return new Result(false, RED+"you are not in your hand"+RESET);

            Main.getClient().getPlayer().increaseHealth(Main.getClient().getPlayer().currentTool.healthCost());
        }

        Tools tools = Main.getClient().getPlayer().currentTool;

        if (Main.getClient().getPlayer().currentTool == null)
            return new Result(false, RED + "please pick up a tools" + RESET);

        switch (tools) {
            case Axe axe -> {
                return useAxe(dir);
            }
            case Hoe hoe -> {
                if (Main.getClient().getPlayer().currentTool.healthCost() > 0 && Main.getClient().getPlayer().Buff_farming_hoursLeft > 0)
                    Main.getClient().getPlayer().increaseHealth(1);
                return useHoe(dir);
            }
            case MilkPail milkPail -> {
                if (Main.getClient().getPlayer().currentTool.healthCost() > 0 && Main.getClient().getPlayer().Buff_farming_hoursLeft > 0)
                    Main.getClient().getPlayer().increaseHealth(1);
                return useMilkPail(dir);
            }
            case Scythe scythe -> {
                if (Main.getClient().getPlayer().currentTool.healthCost() > 0 && Main.getClient().getPlayer().Buff_farming_hoursLeft > 0)
                    Main.getClient().getPlayer().increaseHealth(1);
                return useScythe(dir);
            }
            case Shear shear -> {
                if (Main.getClient().getPlayer().currentTool.healthCost() > 0 && Main.getClient().getPlayer().Buff_farming_hoursLeft > 0)
                    Main.getClient().getPlayer().increaseHealth(1);
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
    public NPC findNPC () {

        User player = Main.getClient().getPlayer();
        NPC npc = null;

        for (NPC npc1 : NPC.values())
            if (npc1.isTileCloseToNPC((int) player.getPositionX() / TEXTURE_SIZE, (int) player.getPositionY() / TEXTURE_SIZE))
                npc = npc1;

        return npc;
    }
    public boolean checkForNPC () {

        User player = Main.getClient().getPlayer();
        NPC npc = null;

        for (NPC npc1 : NPC.values())
            if (npc1.isTileCloseToNPC((int) player.getPositionX() / TEXTURE_SIZE, (int) player.getPositionY() / TEXTURE_SIZE))
                npc = npc1;

        return npc != null;
    }
    public void meetNPC (Stage stage, NPC npc) {

        User player = Main.getClient().getPlayer();

        if (!player.getTodayTalking(npc)) {
            player.setTodayTalking(npc,true);
            player.increaseFriendshipPoint(npc, 20);
        }
        int level = player.getFriendshipLevel(npc);
        drawNPCDialogue(stage, npc, level, currentGame.getGameState().currentWeather);
        npc.setLastConversation(System.currentTimeMillis());

    }
    private void drawNPCDialogue(Stage stage, NPC npc, int friendshipLevel, Weather weather) {

        String iconPath = npc.getIconPath();
        Texture npcTexture = TextureManager.get(iconPath);
        Image npcImage = new Image(new TextureRegionDrawable(new TextureRegion(npcTexture)));
        npcImage.setSize(64, 64);

        Label.LabelStyle style = new Label.LabelStyle(Main.getNewSkin().get(Label.LabelStyle.class));
        style.fontColor = Color.WHITE;

        Label nameLabel = new Label(npc.getName(), style);

        Label.LabelStyle style1 = new Label.LabelStyle(Main.getNewSkin().get(Label.LabelStyle.class));
        style1.fontColor = Color.GRAY;

        String dialogue = npc.getDialogue(friendshipLevel, weather);
        Label dialogueLabel = new Label(dialogue, style1);
        dialogueLabel.setWrap(true);
        dialogueLabel.setWidth(300);


        Window dialogueWindow = new Window("", Main.getNewSkin());
        dialogueWindow.setMovable(false);
        dialogueWindow.pad(10);

        Table contentTable = new Table();
        contentTable.top().left();
        contentTable.defaults().left().pad(5);

        contentTable.add(npcImage).size(64, 64);
        contentTable.add(nameLabel).left().top().padLeft(10);
        contentTable.row();

        contentTable.add(dialogueLabel).colspan(2).width(300).left().top();
        contentTable.row();

        dialogueWindow.add(contentTable).left().top();
        dialogueWindow.pack();
        dialogueWindow.setPosition(10, stage.getHeight() - dialogueWindow.getHeight() - 7);

        dialogueWindow.addAction(
            Actions.sequence(
                Actions.delay(3f),
                Actions.fadeOut(0.5f),
                Actions.removeActor()
            )
        );

        stage.addActor(dialogueWindow);
    }
    public void giftNPC (Items item, Stage stage, NPC npc) {

        User player = Main.getClient().getPlayer();

        if (npc == null) {return;}

        if (item == null) {
            createGiftMenu(new Result(false, "You can only gift items from the market, crops and fruit"), stage, npc);
            return;
        }

        Inventory inventory = player.getBackPack().inventory;


        if (!inventory.Items.containsKey(item)) {
            createGiftMenu(new Result(false, "You don't have this item"), stage, npc);
            return;
        }

        if (item instanceof Tools) {
            createGiftMenu(new Result(false, "you can't gift tools"), stage, npc);
            return;
        }

        advanceItem(item, -1);

        if (!player.getTodayGifting(npc)) {

            if (npc.isItFavorite(item))
                player.increaseFriendshipPoint(npc, 200);
            else
                player.increaseFriendshipPoint(npc, 50);
        } else {

            if (npc.isItFavorite(item))
                player.increaseFriendshipPoint(npc, 50);
            else
                player.increaseFriendshipPoint(npc, 15);
        }
        player.setTodayGifting(npc, true);
        createGiftMenu (new Result(true, "Your gift successfully sent to " + npc.getName()), stage, npc);
    }
    private void createGiftMenu (Result message, Stage stage, NPC npc) {

        String iconPath = npc.getIconPath();
        Texture npcTexture = TextureManager.get(iconPath);
        Image npcImage = new Image(new TextureRegionDrawable(new TextureRegion(npcTexture)));
        npcImage.setSize(64, 64);

        Label.LabelStyle style = new Label.LabelStyle(Main.getNewSkin().get(Label.LabelStyle.class));
        style.fontColor = Color.WHITE;

        Label nameLabel = new Label(npc.getName(), style);

        Label.LabelStyle style1 = new Label.LabelStyle(Main.getNewSkin().get(Label.LabelStyle.class));

        if (message.IsSuccess())
            style1.fontColor = Color.GREEN;
        else
            style1.fontColor = Color.RED;

        Label dialogueLabel = new Label(message.massage(), style1);
        dialogueLabel.setWrap(true);
        dialogueLabel.setWidth(400);


        Window dialogueWindow = new Window("", Main.getNewSkin());
        dialogueWindow.setMovable(false);
        dialogueWindow.pad(10);

        Table contentTable = new Table();
        contentTable.top().left();
        contentTable.defaults().left().pad(5);

        contentTable.add(npcImage).size(64, 64);
        contentTable.add(nameLabel).left().top().padLeft(10);
        contentTable.row();

        contentTable.add(dialogueLabel).colspan(2).width(300).left().top();
        contentTable.row();

        dialogueWindow.add(contentTable).left().top();
        dialogueWindow.pack();
        dialogueWindow.setPosition(10, stage.getHeight() - dialogueWindow.getHeight() - 7);

        dialogueWindow.addAction(
            Actions.sequence(
                Actions.delay(3f),
                Actions.fadeOut(0.5f),
                Actions.removeActor()
            )
        );

        stage.addActor(dialogueWindow);
    }
    public String questsNPCList () {

        StringBuilder sb = new StringBuilder();

        for (NPC npc : NPC.values())
            sb.append(OneNPCQuestsList(npc));

        return sb.toString();
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
//        if (!npc.isInHisHome(Main.getClient().getPlayer().getPositionX(), Main.getClient().getPlayer().getPositionY()))
//            return new Result(false, RED+"You should go to their place first"+RESET);

        return switch (ID) {
            case 1 -> doTask1(npc);
            case 2 -> doTask2(npc);
            case 3 -> doTask3(npc);
            default -> new Result(false, RED + "Index is invalid" + RESET);
        };
    }
                            // OTHERd

    public void sendPassedTimeMessage (int day, int hour) {

        HashMap<String , Object> PassedTime = new HashMap<>();
        PassedTime.put("Hour", hour);
        PassedTime.put("Day", day);
        Main.getClient().getRequests().add(new Message(CommandType.SET_TIME , PassedTime));

    }
    public void sendChangeGameObjectMessage (int x, int y, GameObject gameObject) {
        HashMap<String , Object> PassedTime = new HashMap<>();
        PassedTime.put("X", x);
        PassedTime.put("Y", y);
        PassedTime.put("Object", gameObject);
        Main.getClient().getRequests().add(new Message(CommandType.CHANGE_GAME_OBJECT, PassedTime));
    }
    public void sendChangeGameObjectMessage (Tile tile, GameObject gameObject) {
        HashMap<String , Object> PassedTime = new HashMap<>();
        PassedTime.put("X", tile.getX());
        PassedTime.put("Y", tile.getY());
        PassedTime.put("Object", gameObject);
        Main.getClient().getRequests().add(new Message(CommandType.CHANGE_GAME_OBJECT, PassedTime));
    }
}
