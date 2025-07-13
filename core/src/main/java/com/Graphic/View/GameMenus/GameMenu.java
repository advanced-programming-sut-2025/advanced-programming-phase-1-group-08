package com.Graphic.View.GameMenus;

import com.Graphic.Controller.MainGame.InputGameController;
import com.Graphic.Main;
import com.Graphic.model.App;
import com.Graphic.model.Enum.Direction;
import com.Graphic.model.Enum.GameTexturePath;
import com.Graphic.model.GameAssetManager;
import com.Graphic.model.HelpersClass.TextureManager;

import com.Graphic.model.Items;
import com.Graphic.model.Keys;
import com.Graphic.model.ToolsPackage.Tools;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.awt.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.Graphic.Controller.MainGame.GameControllerLogic.passedOfTime;
import static com.Graphic.model.App.currentGame;
import static com.Graphic.model.HelpersClass.TextureManager.EQUIP_THING_SIZE;
import static com.Graphic.model.HelpersClass.TextureManager.TEXTURE_SIZE;


public class GameMenu implements  Screen, InputProcessor {

    public static GameMenu gameMenu;

    public static OrthographicCamera camera;
    private InputGameController controller;
    private final int hourSecond = 120000;
    private Stage stage;

    private Image helperBackGround;

    public long startTime;
    public long lastTime;

    private Group clockGroup;
    private Label moneyLabel;
    private Label timeLabel;
    private Label dateLabel;
    private Label weekDayLabel;

    private boolean toolsMenuIsActivated;
    private Window toolsPopup;


    private GameMenu() {

    }
    public static GameMenu getInstance() {
        if (gameMenu == null)
            gameMenu = new GameMenu();

        return gameMenu;
    }

    public void show() {

        initialize();
        controller.init();

        camera.setToOrtho(false , Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        controller.startNewGame("a");
        Gdx.input.setInputProcessor(stage);
        createClock();

    }
    public void render(float v) {

        if (TimeUtils.millis() - lastTime > hourSecond)
            updateClock();

        inputController();
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Main.getBatch().setProjectionMatrix(camera.combined);
        Main.getBatch().begin();
        controller.update(camera, v);
        drawCurrentItem();
        Main.getBatch().end();


        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

    }


    private void createToolsTable (Table content, Items currentItem, HashMap<String,String> tools) {

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = GameAssetManager.getGameAssetManager().getTinyFont();


        content.setFillParent(true);
        content.top();
        content.defaults().pad(10);

        Label label = new Label("", labelStyle);
        label.setColor(Color.BLACK);
        content.add(label).padTop(30).padLeft(20).center().row();

        BitmapFont font = GameAssetManager.getGameAssetManager().getTinyFont();
        font.getData().setScale(0.4f);
        labelStyle.font = font;

        int total = tools.size();
        int half = (int) Math.ceil(total / 2.0);
        Array<Map.Entry<String, String>> entries = new Array<>(tools.entrySet().toArray(new Map.Entry[0]));


        for (int i = 0; i < half; i++)
            addToolImage(content, entries, i, currentItem);
        content.row();

        for (int i = 0; i < half; i++)
            addToolName(content, entries, i, labelStyle);

        content.row();

        for (int i = half; i < total; i++)
            addToolImage(content, entries, i, currentItem);

        content.row();

        for (int i = half; i < total; i++)
            addToolName(content, entries, i, labelStyle);
    }
    private void addToolName(Table content, Array<Map.Entry<String, String>> entries,
                             int i, Label.LabelStyle labelStyle) {
        Map.Entry<String, String> entry = entries.get(i);
        Label label1 = new Label(entry.getKey(), labelStyle);
        label1.setColor(Color.BLACK);
        label1.setSize(15, 8);
        content.add(label1);
    }
    private void addToolImage(Table content, Array<Map.Entry<String, String>> entries,
                              int i, Items currentItem) {
        Map.Entry<String, String> entry = entries.get(i);
        Image img = new Image(new TextureRegionDrawable(new TextureRegion(TextureManager.get(entry.getValue()))));
        img.setSize(30, 30);

        final String toolName = entry.getKey();

        boolean isCurrent = currentItem != null && toolName.equals(currentItem.getName());
        if (isCurrent) {
            img.setColor(0.4f, 0.8f, 1f, 1f);
            img.setScale(1.3f);
        } else
            img.setColor(1f, 1f, 1f, 0.8f);

        img.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if (currentItem != null && currentItem.getName().equals(toolName))
                    currentGame.currentPlayer.currentItem = null;
                else
                    controller.itemEquip(toolName);

                helperBackGround.remove();
                toolsPopup.remove();
                toolsMenuIsActivated = false;
            }
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                img.setColor(1f, 1f, 1f, 1f);
                img.setScale(isCurrent ? 1.4f : 1.2f);
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (isCurrent) {
                    img.setColor(0.4f, 0.8f, 1f, 1f); // برگرد به رنگ خاص خودش
                    img.setScale(1.3f);
                } else {
                    img.setColor(1f, 1f, 1f, 0.8f); // حالت عادی
                    img.setScale(1f);
                }
            }
        });

        content.add(img).size(30, 30);
    }



    private void initialize () {

        startTime = TimeUtils.millis();
        lastTime = TimeUtils.millis();

        controller = InputGameController.getInstance();
        stage = new Stage(new ScreenViewport());
        clockGroup = new Group();
        camera = new OrthographicCamera();


        timeLabel = new Label("", App.skin);
        dateLabel = new Label("", App.skin);
        moneyLabel = new Label("", App.skin);
        weekDayLabel = new Label("", App.skin);

        toolsMenuIsActivated = false;

    }

    private void createClock() {

        Image image = new Image(TextureManager.get(GameTexturePath.Clock.getPath()));

        ArrayList<Label> labels = new ArrayList<>();
        labels.add(timeLabel);
        labels.add(dateLabel);
        labels.add(moneyLabel);
        labels.add(weekDayLabel);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = GameAssetManager.getGameAssetManager().getSmallFont();

        Label.LabelStyle labelStyle2 = new Label.LabelStyle();
        labelStyle2.font = GameAssetManager.getGameAssetManager().getFont3();



        timeLabel.setText(currentGame.currentDate.getHour() + ":00");
        dateLabel.setText(currentGame.currentDate.getDate());
        weekDayLabel.setText(currentGame.currentDate.getDayOfTheWeek().name());

        String moneyStr = String.valueOf(currentGame.currentPlayer.getMoney());
        String spaced = String.join(" ", moneyStr.split(""));
        moneyLabel.setText(spaced);


        for (Label l : labels) {
            l.setColor(Color.BLACK);
            l.setStyle(labelStyle2);
        }
        moneyLabel.setStyle(labelStyle);

        clockGroup.addActor(image);

        for (Label l : labels)
            clockGroup.addActor(l);


        dateLabel.setPosition(image.getWidth() - dateLabel.getWidth() - 70, image.getHeight() - dateLabel.getHeight() - 48);
        weekDayLabel.setPosition(dateLabel.getX() - weekDayLabel.getWidth() - 80, dateLabel.getY() + 3);
        setCenteredPosition(timeLabel, weekDayLabel.getX() + 10, weekDayLabel.getY() - 95);
        moneyLabel.setPosition(timeLabel.getX() + 45 - moneyLabel.getWidth(), timeLabel.getY() - 78);


        float screenWidth = stage.getViewport().getWorldWidth();
        float screenHeight = stage.getViewport().getWorldHeight();


        clockGroup.setSize(image.getWidth(), image.getHeight());

        clockGroup.setPosition(
            screenWidth - clockGroup.getWidth() - 10,
            screenHeight - clockGroup.getHeight() - 10);

        stage.addActor(clockGroup);
    }
    private void updateClock() {

        passedOfTime(0, 1);
        timeLabel.setText(currentGame.currentDate.getHour() + ":00");
        dateLabel.setText(currentGame.currentDate.getDate());
        weekDayLabel.setText(currentGame.currentDate.getDayOfTheWeek().name());
        lastTime = TimeUtils.millis();
    }
    private void createToolsMenu () {

        if (!toolsMenuIsActivated) {
            helperBackGround = new Image(new TextureRegionDrawable(new TextureRegion(TextureManager.get("Erfan/grayPage.jpg"))));
            helperBackGround.setColor(0, 0, 0, 0.5f);
            helperBackGround.setSize(stage.getWidth(), stage.getHeight());


            HashMap<String, String> availableTools = controller.availableTools();
            Items currentItem = currentGame.currentPlayer.currentItem;

            int colNumber = availableTools.size() / 2 + 1;

            toolsPopup = new Window("", App.skin);
            toolsPopup.setSize(200 + colNumber * 100, 300);
            toolsPopup.setPosition(
                (stage.getWidth() - toolsPopup.getWidth()) / 2,
                (stage.getHeight() - toolsPopup.getHeight()) / 2);


            Table content = new Table();
            createToolsTable(content, currentItem, availableTools);

            toolsPopup.add(content).expand().fill();

            stage.addActor(helperBackGround);
            stage.addActor(toolsPopup);
            toolsMenuIsActivated = true;
        }
        else {
            helperBackGround.remove();
            toolsPopup.remove();
            toolsMenuIsActivated = false;
        }
    }
    private void drawCurrentItem() {
        if (currentGame.currentPlayer.currentItem != null) {

            Items currentItem = currentGame.currentPlayer.currentItem;
            Direction direction = currentGame.currentPlayer.getDirection();

            float x = getXForHands(direction), y = getYForHands(direction);
            Sprite toolSprite = currentItem.getSprite(TextureManager.get(currentItem.getInventoryIconPath()));

            toolSprite.flip(Direction.lastDir != null && Direction.lastDir != direction &&
                (direction == Direction.Left || Direction.lastDir == Direction.Left), false);

            Main.getBatch().draw(toolSprite, x, y, EQUIP_THING_SIZE, EQUIP_THING_SIZE);
            Direction.lastDir = direction;
        }
    }

    private float getYForHands(Direction direction) {

        if (direction == Direction.Up)
            return (90 - currentGame.currentPlayer.getPositionY()) * TEXTURE_SIZE + 12;

        return (90 - currentGame.currentPlayer.getPositionY()) * TEXTURE_SIZE + 8;
    }
    private float getXForHands(Direction direction) {

        return switch (direction) {
            case Right -> currentGame.currentPlayer.getPositionX() * TEXTURE_SIZE + 20;
            case Left -> currentGame.currentPlayer.getPositionX() * TEXTURE_SIZE - 10;
            case Up -> currentGame.currentPlayer.getPositionX() * TEXTURE_SIZE + 25;
            case Down -> currentGame.currentPlayer.getPositionX() * TEXTURE_SIZE + 23;
        };
    }

    private void inputController () {

         if (Gdx.input.isKeyJustPressed(Keys.ToolsMenu))
             createToolsMenu();
    }

    public void resize(int i, int i1) {

    }
    public void pause() {

    }
    public void resume() {

    }
    public void hide() {

    }
    public void dispose() {

    }


    public boolean keyUp(int i) {
        return false;
    }
    public boolean keyDown(int i) {

        return false;
    }
    public boolean keyTyped(char c) {
        return false;
    }
    public boolean mouseMoved(int i, int i1) {
        return false;
    }
    public boolean scrolled(float v, float v1) {
        return false;
    }
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }
    public void setCenteredPosition(Actor actor, float centerX, float centerY) {
        actor.setPosition(centerX - actor.getWidth() / 2f, centerY - actor.getHeight() / 2f);
    }

    public Stage getStage() {
        return stage;
    }


    //    public void check(Scanner scanner) throws IOException {
//
//        String input = scanner.nextLine();
//
//
//        if (GameMenuCommands.makeNewGame.getMatcher(input) != null)
//            controller.startNewGame(input);
//
//        else if (GameMenuCommands.back.getMatcher(input) != null)
//            System.out.println(controller.backToMainMenu().massage());
//
//        else if ((matcher = GameMenuCommands.printMap.getMatcher(input)) != null) {
//            System.out.println(controller.print(
//                    Integer.parseInt(matcher.group(1).trim()),
//                    Integer.parseInt(matcher.group(2).trim()),
//                    Integer.parseInt(matcher.group(3).trim())));
//        }
//
//        else if (GameMenuCommands.nextTurn.getMatcher(input) != null)
//            GameControllerLogic.nextTurn();
//
//        else if (GameMenuCommands.openHomeMenu.getMatcher(input) != null)
//            System.out.println(controller.goToHomeMenu() );
//
//        else if (GameMenuCommands.eatFood.getMatcher(input) != null)
//            GameControllerLogic.eatFood(input);
//
//        else if (GameMenuCommands.recipeUnlock.getMatcher(input) != null)
//            GameControllerLogic.unlockRecipe(input);
//
//        else if (GameMenuCommands.friendships.getMatcher(input) != null)
//            GameControllerLogic.DisplayFriendships();
//
//        else if (GameMenuCommands.addXpCheat.getMatcher(input) != null)
//            GameControllerLogic.cheatAddXp(input);
//
//        else if (GameMenuCommands.talking.getMatcher(input) != null)
//            GameControllerLogic.talking(input);
//
//        else if (GameMenuCommands.hug.getMatcher(input) != null)
//            GameControllerLogic.hug(input);
//
//        else if (GameMenuCommands.sendGift.getMatcher(input) != null)
//            GameControllerLogic.sendGifts(input);
//
////        else if (GameMenuCommands.giftList.getMatcher(input) != null)  پاکش نکنین!
////            System.out.println(controller.giftList().massage());
//
//        else if (GameMenuCommands.trade.getMatcher(input) != null) {
//            TradeController tradeController = new TradeController();
//            tradeController.tradeStart();
//        }
//        else if (GameMenuCommands.propose.getMatcher(input) != null)
//            GameControllerLogic.propose(input);
//
//        else if (GameMenuCommands.giveFlower.getMatcher(input) != null)
//            GameControllerLogic.giveFlowers(input);
//
//        else if (GameMenuCommands.talkHistory.getMatcher(input) != null)
//            GameControllerLogic.DisplayingTalkHistory(input);
//
//        else if (GameMenuCommands.showTime.getMatcher(input) != null)
//            System.out.println(controller.showTime());
//
//        else if (GameMenuCommands.showDate.getMatcher(input) != null)
//            System.out.println(controller.showDate());
//
//        else if (GameMenuCommands.showDateTime.getMatcher(input) != null)
//            System.out.println(controller.showDateTime());
//
//        else if (GameMenuCommands.showDayOfWeek.getMatcher(input) != null)
//            System.out.println(controller.showDayOfWeek());
//
//        else if (GameMenuCommands.showSeason.getMatcher(input) != null)
//            System.out.println(controller.showSeason());
//
//        else if ((matcher = GameMenuCommands.advanceTime.getMatcher(input)) != null)
//            System.out.println(controller.increaseHour(matcher.group("hour").trim()));
//
//        else if ((matcher = GameMenuCommands.advanceDate.getMatcher(input)) != null)
//            System.out.println(controller.increaseDate(matcher.group("date").trim()));
//
//        else if (GameMenuCommands.showWeather.getMatcher(input) != null)
//            System.out.println(controller.showWeather(true));
//
//        else if (GameMenuCommands.showTomorrowWeather.getMatcher(input) != null)
//            System.out.println(controller.showWeather(false));
//
//        else if ((matcher = GameMenuCommands.setWeather.getMatcher(input)) != null)
//            System.out.println(controller.setWeather(matcher.group("Weather").trim()));
//
//        else if (GameMenuCommands.showEnergy.getMatcher(input) != null)
//            System.out.println(controller.showEnergy());
//
//        else if ((matcher = GameMenuCommands.setEnergy.getMatcher(input)) != null)
//            System.out.println(controller.setEnergy(matcher.group("amount").trim()));
//
//        else if (GameMenuCommands.energyUnlimit.getMatcher(input) != null)
//            System.out.println(controller.EnergyUnlimited());
//
//        else if ((matcher = GameMenuCommands.showFruitInfo.getMatcher(input)) != null)
//            System.out.println(controller.showFruitInfo(matcher.group("name").trim()));
//
//        else if (GameMenuCommands.buildGreenHouse.getMatcher(input) != null)
//            System.out.println(controller.buildGreenHouse());
//
//        else if ((matcher = GameMenuCommands.planting.getMatcher(input)) != null)
//            System.out.println(controller.planting(matcher.group("seed").trim(),
//                    matcher.group("direction").trim()));
//
//        else if (GameMenuCommands.howMuchWater.getMatcher(input) != null)
//            System.out.println(controller.howMuchWater());
//
//        else if ((matcher = GameMenuCommands.createThor.getMatcher(input)) != null)
//            System.out.println(controller.thor(matcher.group("x").trim(),
//                    matcher.group("y").trim()));
//
//        else if ((matcher = GameMenuCommands.showPlant.getMatcher(input)) != null)
//            System.out.println(controller.showPlant(matcher.group("x").trim(),
//                    matcher.group("y").trim()));
//
//        else if ((matcher = GameMenuCommands.fertilize.getMatcher(input)) != null)
//            System.out.println(controller.fertilize(matcher.group("fertilizer")
//                    .trim(), matcher.group("direction").trim()));
//
//        else if ((matcher = GameMenuCommands.showTreeInfo.getMatcher(input)) != null)
//            System.out.println(controller.info(matcher.group("name").trim()));
//
//        else if (GameMenuCommands.questsList.getMatcher(input) != null)
//            System.out.println(controller.questsNPCList());
//
//        else if (GameMenuCommands.friendshipNPCList.getMatcher(input) != null)
//            System.out.println(controller.friendshipNPCList());
//
//        else if ((matcher = GameMenuCommands.meetNPC.getMatcher(input)) != null)
//            System.out.println(controller.meetNPC(matcher.group("name").trim()));
//
//        else if ((matcher = GameMenuCommands.questsFinish.getMatcher(input)) != null)
//            System.out.println(controller.doQuest(matcher.group("name").trim(),
//                    matcher.group("index").trim()));
//
//        else if ((matcher = GameMenuCommands.giftNPC.getMatcher(input)) != null)
//            System.out.println(controller.giftNPC(matcher.group("name").trim(),
//                    matcher.group("item").trim()));
//
//        else if (GameMenuCommands.showTool.getMatcher(input) != null)
//            System.out.println(controller.showCurrentTool());
//
//        else if (GameMenuCommands.toolsAvailable.getMatcher(input) != null)
//            System.out.println(controller.availableTools());
//
//        else if ((matcher = GameMenuCommands.toolsEquip.getMatcher(input)) != null)
//            System.out.println(controller.toolsEquip(matcher.group("name").trim()));
//
//
//        else if ((matcher = GameMenuCommands.toolsUse.getMatcher(input)) != null)
//            System.out.println(controller.useTools(matcher.group("direction").trim()));
//
//        else if ((matcher = GameMenuCommands.wateringPlant.getMatcher(input)) != null)
//            System.out.println(controller.WateringPlant(matcher.group("direction").trim()));
//
//
//        else if (input.matches("\\s*show\\s*current\\s*menu\\s*"))
//            System.out.println("Game Menu");
//
//        else if (input.matches("\\s*exit\\s*game\\s*"))
//            GameControllerLogic.exitGame();
//        else if (input.matches("\\s*force\\s*terminate\\s*"))
//            GameControllerLogic.forceTerminate();
//
//
//        else if((matcher=GameMenuCommands.walk.getMatcher(input)) != null)
//            System.out.println(controller.walk(Integer.parseInt(matcher.group(1).trim())
//                    , Integer.parseInt(matcher.group(2).trim()) ));
//
//        else if((matcher=GameMenuCommands.inventoryShow.getMatcher(input)) != null)
//            System.out.println(controller.showInventory());
//
//        else if((matcher=GameMenuCommands.removeItem.getMatcher(input)) != null)
//            System.out.println(controller.removeItemToTrashcan(matcher.group(1).trim(), null));
//
//        else if ((matcher=GameMenuCommands.removeItemFlags.getMatcher(input)) != null)
//            System.out.println(controller.removeItemToTrashcan(matcher.group(1).trim(), matcher.group(2).trim()) );
//
//        else if ((matcher=GameMenuCommands.fishing.getMatcher(input)) != null)
//            System.out.println(controller.Fishing(matcher.group(1).trim()));
//
//        else if ((matcher=GameMenuCommands.pet.getMatcher(input)) != null)
//            System.out.println(controller.pet(matcher.group(1).trim()));
//
//        else if ((matcher=GameMenuCommands.animals.getMatcher(input)) != null)
//            System.out.println(controller.animals());
//
//        else if ((matcher=GameMenuCommands.shepherdAnimals.getMatcher(input)) != null) {
//            System.out.println(controller.shepherdAnimals(matcher.group(2).trim()
//                    , matcher.group(3).trim() , matcher.group(1).trim()));
//        }
//
//        else if ((matcher=GameMenuCommands.feedHay.getMatcher(input)) != null)
//            System.out.println(controller.feedHay(matcher.group("name").trim()));
//
//        else if ((matcher=GameMenuCommands.produces.getMatcher(input)) != null)
//            System.out.println(controller.produces());
//
//        else if ((matcher=GameMenuCommands.collectProduct.getMatcher(input)) != null)
//            System.out.println(controller.getProductAnimals(matcher.group("name").trim()));
//
//        else if ((matcher=GameMenuCommands.sellAnimal.getMatcher(input)) != null)
//            System.out.println(controller.sellAnimal(matcher.group("name").trim()));
//
//        else if ((matcher=GameMenuCommands.placeItem.getMatcher(input)) != null)
//            System.out.println(controller.placeItem(matcher.group("name").trim() , matcher.group(2).trim()));
//
//        else if ((matcher=GameMenuCommands.artisan.getMatcher(input)) != null)
//            System.out.println(controller.ArtisanUse(matcher.group(1).trim() , matcher.group(2).trim() , null));
//
//        else if ((matcher=GameMenuCommands.artisanUse.getMatcher(input)) != null)
//            System.out.println(controller.ArtisanUse(matcher.group(1).trim() , matcher.group(2).trim() , matcher.group(3).trim() ));
//
//        else if ((matcher=GameMenuCommands.artisanGet.getMatcher(input)) != null)
//            System.out.println(controller.ArtisanGetProduct(matcher.group(1).trim()));
//
//        else if ((matcher=GameMenuCommands.sellByCount.getMatcher(input)) != null)
//            System.out.println(controller.sell(matcher.group("name").trim() , Integer.parseInt(matcher.group(2).trim()) ));
//
//        else if ((matcher=GameMenuCommands.sell.getMatcher(input)) != null)
//            System.out.println(controller.sell(matcher.group("name").trim() , -1));
//
//        else if ((matcher=GameMenuCommands.cheatSetFriendship.getMatcher(input)) != null)
//            System.out.println(controller.cheatSetFriendship(matcher.group(1).trim() , Integer.parseInt(matcher.group(2).trim()) ));
//
//        else if ((matcher=GameMenuCommands.addDollar.getMatcher(input)) != null)
//            System.out.println(controller.addDollar(Integer.parseInt(matcher.group(1).trim())));
//
//        else if ((matcher=GameMenuCommands.setDollar.getMatcher(input)) != null)
//            System.out.println(controller.setDollar(Integer.parseInt(matcher.group(1).trim())));
//
//        else if ((matcher=GameMenuCommands.addItem.getMatcher(input)) != null)
//            System.out.println(controller.addItem(matcher.group(1) , Integer.parseInt(matcher.group(2).trim())));
//
//        else if ((matcher=GameMenuCommands.MarketMenu.getMatcher(input)) != null)
//            System.out.println(controller.goToMarketMenu());
//
//        else if (input.matches("\\s*plant\\s*"))
//            controller.plantCreator();
//
//
//        else if ((matcher = GameMenuCommands.getGameObject.getMatcher(input)) != null)
//            System.out.println(controller.getObject(matcher.group("dir").trim()));
//        else if ((matcher = GameMenuCommands.getGameObject2.getMatcher(input)) != null)
//            System.out.println(controller.getObject2(matcher.group("x").trim(), matcher.group("y").trim()));
//
//        else if (input.matches("(i?)\\s*print\\s*"))
//            System.out.println(controller.print(0, 0, 30));
//
//        else if (input.matches("(i?)\\s*print\\s*all\\s*"))
//            System.out.println(controller.print(0, 0, 90));
//
//        else if ((matcher = GameMenuCommands.remove.getMatcher(input)) != null)
//            controller.remove(Integer.parseInt(matcher.group(1)));
//
//        else if (input.matches("(?i)\\s*add\\s*money\\s*"))
//            currentGame.currentPlayer.increaseMoney(10000);
//
//        else if (input.matches("\\s*clear\\s*"))
//            controller.clear();
//
//        else if (input.matches("\\s*plow\\s*"))
//            controller.plow();
//
//        else
//            System.out.println(RED+"Invalid Command, Try Again"+RESET);
//
//    }
}
