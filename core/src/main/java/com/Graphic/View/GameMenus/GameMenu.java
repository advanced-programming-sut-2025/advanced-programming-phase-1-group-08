package com.Graphic.View.GameMenus;

import com.Graphic.Controller.MainGame.InputGameController;
import com.Graphic.Main;
import com.Graphic.model.App;
import com.Graphic.model.Enum.GameTexturePath;
import com.Graphic.model.GameAssetManager;
import com.Graphic.model.HelpersClass.TextureManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

import static com.Graphic.model.App.currentGame;


public class GameMenu implements  Screen, InputProcessor {

    public static GameMenu gameMenu;


    private Stage stage;
    public static OrthographicCamera camera;
    private InputGameController controller;

    private Group clockGroup;
    private Label moneyLabel;
    private Label timeLabel;
    private Label dateLabel;
    private Label weekDayLabel;


    private GameMenu() {

    }
    public static GameMenu getInstance() {
        if (gameMenu == null)
            gameMenu = new GameMenu();

        return gameMenu;
    }

    public void show() {

        initialize();

        camera.setToOrtho(false , Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        controller.startNewGame("a");
        Gdx.input.setInputProcessor(this);
        createClock();



    }
    private void initialize () {

        controller = new InputGameController();
        stage = new Stage(new ScreenViewport());
        clockGroup = new Group();
        camera = new OrthographicCamera();


        timeLabel = new Label("", App.skin);
        dateLabel = new Label("", App.skin);
        moneyLabel = new Label("", App.skin);
        weekDayLabel = new Label("", App.skin);

    }
    public void render(float v) {

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Main.getBatch().setProjectionMatrix(camera.combined);
        Main.getBatch().begin();
        controller.print();
        controller.moveCamera(camera);
        Main.getBatch().end();


        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

    }


    private void createClock() {

        Image image = new Image(TextureManager.get(GameTexturePath.Clock.getPath()));

        ArrayList<Label> labels = new ArrayList<>();
        labels.add(timeLabel);
        labels.add(dateLabel);
        labels.add(moneyLabel);
        labels.add(weekDayLabel);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = GameAssetManager.getGameAssetManager().getFont2();

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
        setCenteredPosition(timeLabel, weekDayLabel.getX() + 20, weekDayLabel.getY() - 95);
        moneyLabel.setPosition(timeLabel.getX() + 35 - moneyLabel.getWidth(), timeLabel.getY() - 78);


        float screenWidth = stage.getViewport().getWorldWidth();
        float screenHeight = stage.getViewport().getWorldHeight();


        clockGroup.setSize(image.getWidth(), image.getHeight());

        clockGroup.setPosition(
            screenWidth - clockGroup.getWidth() - 10,
            screenHeight - clockGroup.getHeight() - 10);

        stage.addActor(clockGroup);
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
