package com.Graphic.View.GameMenus;

import com.Graphic.Controller.MainGame.GameControllerLogic;
import com.Graphic.Controller.MainGame.InputGameController;
import com.Graphic.Controller.MainGame.Marketing;
import com.Graphic.Main;
import com.Graphic.View.AppMenu;
import com.Graphic.model.*;
import com.Graphic.model.Animall.Animal;
import com.Graphic.model.App;
import com.Graphic.model.Enum.AllPlants.CropsType;
import com.Graphic.model.Enum.AllPlants.ForagingCropsType;
import com.Graphic.model.Enum.AllPlants.ForagingMineralsType;
import com.Graphic.model.Enum.AllPlants.TreeType;
import com.Graphic.model.Enum.*;
import com.Graphic.model.Enum.NPC.NPC;
import com.Graphic.model.Enum.NPC.NPCManager;
import com.Graphic.model.Enum.NPC;
import com.Graphic.model.Enum.Skills;
import com.Graphic.model.HelpersClass.AnimatedImage;
import com.Graphic.model.HelpersClass.Result;
import com.Graphic.model.HelpersClass.SampleAnimation;
import com.Graphic.model.HelpersClass.TextureManager;

import com.Graphic.model.MapThings.Tile;
import com.Graphic.model.MapThings.UnWalkable;
import com.Graphic.model.MapThings.Walkable;
import com.Graphic.model.Plants.*;
import com.Graphic.model.ToolsPackage.Tools;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.Graphic.Controller.MainGame.GameControllerLogic.*;
import static com.Graphic.model.App.*;
import static com.Graphic.model.HelpersClass.TextureManager.EQUIP_THING_SIZE;
import static com.Graphic.model.HelpersClass.TextureManager.TEXTURE_SIZE;

public class GameMenu implements  Screen, InputProcessor , AppMenu {

    public static GameMenu gameMenu; // اگه صفحه ای اینجا قراره باز بشه که وقتی باز شد فرایند بازی متوقف بشه یه بولین برای فعال بودنش بزارین و تو تابع anyMenuIsActivated هم اوکیش کنین
        // TODO مملی ورودی گرفتن برای حرمت مردن رو هم بیار تو تابع اینپوت کنترلر چون مثلا منو باز میشه من میخوام a بنویسم دوربین حرکت میکنه مثلا و وقتی بیاری اونجا اوکی میشه
    public static OrthographicCamera camera;
    private final int hourSecond = 120000;
    private Stage stage;

    private Vector3 mousePos;
    private ArrayList<Animal> shepherdingAnimals;
    private Vector3 vector;
    private InputGameController controller;
    private boolean firstLoad;
    private TiledMap map;
    private BitmapFont animalFont;
    private ArrayList<HeartAnimation> heartAnimations;
    private OrthogonalTiledMapRenderer renderer;
    private InputMultiplexer multiplexer;

    private boolean progressComplete = false;
    private boolean ePressed = false;
    private float holdTime = 0f;
    private final float maxHoldTime = 5f;
    private ShapeRenderer shapeRenderer;


    Texture friendsListTexture;
    TextureRegionDrawable buttonDrawable;
    ImageButton friendButton;
    Dialog friendsListdialog;
    ImageButton tempFriend;
    private Table contextMenu;
    Image bouquetImage;
    Image hugImage;
    Image ringImage;
    private Dialog activeDialog = null;
    private long dialogExpirationTime = 0;

    public static Image helperBackGround;

    public long startTime;
    public long lastTime;

    private int lastHealth;
    public Label energyLabel;
    private Label.LabelStyle energyStyle;

    private Group clockGroup;
    private Image seasonImage;
    private Image weatherImage;
    private Label moneyLabel;
    private Label timeLabel;
    private Label dateLabel;
    private Label weekDayLabel;

    private boolean toolsMenuIsActivated;
    private Window toolsPopup;

    private boolean EscMenuIsActivated;
    private Window EscPopup;

    private boolean inventoryIsActivated;
    private Window inventoryPopup;

    private boolean skillMenuIsActivated;
    private Window skillPopup;

    private boolean socialMenuIsActivated;
    private Window socialPopup;

    private boolean mapIsActivated;
    private Group mapGroup;

    private boolean setEnergyIsActivated;
    private Window setEnergyPopup;

    private boolean informationIsActivated;
    private Window mainInformationPopup;

    private boolean showInformationIsActivated;
    private Window showInformationPopup;

    private boolean subMenuIsActivated;
    private Window subMenuGroup;

    private boolean settingIsActivated;
    private Window settingMenuGroup;

    private TextField energyInputField;
    private TextButton confirmButton;

    private Sprite currentItemSprite;
    private boolean startRotation;
    private float currentRotation = 0f;


    private GameMenu() {

    }
    public static GameMenu getInstance() {
        if (gameMenu == null) {
            gameMenu = new GameMenu();
            gameMenu.initialize();
        }

        return gameMenu;
    }


    public void show() {
        currentMenu = Menu.GameMenu;
        initialize();

        controller.init();
        mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.setToOrtho(false , Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        multiplexer = new InputMultiplexer();

        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.E && Food.itemIsEatable(currentGame.currentPlayer.currentItem)) {
                    ePressed = true;
                    return true;
                }
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                if (keycode == Input.Keys.E) {
                    ePressed = false;
                    holdTime = 0f;
                    return true;
                }
                return false;
            }
        });

        Gdx.input.setInputProcessor(multiplexer);

        createClock();
        firstLoad = true;
        shepherdingAnimals = new ArrayList<>();
        heartAnimations = new ArrayList<>();

    }
    public void render(float v) {

        if (TimeUtils.millis() - lastTime > hourSecond)
            updateClock(1);

        inputController();
        updateEnergyLabel();

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Main.getBatch().setProjectionMatrix(camera.combined);

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            Main.getMain().setScreen(new MarketMenu());
            currentMenu = Menu.MarketMenu;
        }

        if (! currentGame.currentPlayer.isInFarmExterior()) {
            getRenderer().setView(camera);
            getRenderer().render();
        }

        checkFriendDistance();
        if (activeDialog != null && TimeUtils.millis() > dialogExpirationTime) {
            activeDialog.hide();
            activeDialog.remove();
            activeDialog = null;
        }


        Main.getBatch().begin();
        controller.update(camera, v);
        drawCurrentItem();
        moveAnimal();
        NPCManager.NPCWalk(v);
        eatingManagement(v);
        mousePos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos);
        camera.update();
        Main.getBatch().end();


        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

    }

    private void checkFriendDistance() {
        boolean someoneClose = false;

        for (User p : currentGame.players) {
            if (p.getUsername().equalsIgnoreCase(currentGame.currentPlayer.getUsername())) continue;

            float deltaX = Math.abs((float)currentGame.currentPlayer.getPositionX() - p.getPositionX());
            float deltaY = Math.abs((float) currentGame.currentPlayer.getPositionY() - p.getPositionY());
            if (deltaY < 3f && deltaX < 3f) {
                someoneClose = true;

                // تغییر عکس دکمه بر اساس جنسیت
                Texture iconTexture;
                if (p.getGender().equalsIgnoreCase("man"))
                    iconTexture = new Texture("Ariyo/Shane_Icon.png");
                else
                    iconTexture = new Texture("Ariyo/Sandy_Icon.png");

                tempFriend.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(iconTexture));

                tempFriend.setVisible(true);
                break;
            }
        }

        if (!someoneClose) {
            tempFriend.setVisible(true);
        }
    }
    public void eatingManagement(float delta) {
        if (ePressed) {
            holdTime += delta;
            if (holdTime > maxHoldTime) holdTime = maxHoldTime;

            float progress = holdTime / maxHoldTime;

            if (!progressComplete)
                drawProgressBar(progress);

            if (progress >= 1f && !progressComplete) {
                progressComplete = true;
                Result result = eatFood(currentGame.currentPlayer.currentItem.getName());
                showTimedDialog(result.massage(), 2f);
                currentGame.currentPlayer.currentItem = null;
            }

        } else {
            holdTime = 0f;
            progressComplete = false;
        }
    }


    private void drawProgressBar(float progress) {
        float barX = 300;
        float barY = 300;
        float barWidth = 80;
        float barHeight = 20;


        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(barX, barY, barWidth, barHeight);


        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(barX, barY, barWidth * progress, barHeight);
        shapeRenderer.end();
    }

    private Dialog makingInteractionDialog() {
        Dialog interactionDialog = new Dialog("", newSkin);

        User me = currentGame.currentPlayer;
        User other = null;
        for (User p : currentGame.players) {
            if (p.getUsername().equalsIgnoreCase(me.getUsername())) continue;

            float deltaX = Math.abs((float)currentGame.currentPlayer.getPositionX() - p.getPositionX());
            float deltaY = Math.abs((float) currentGame.currentPlayer.getPositionY() - p.getPositionY());
            if (deltaY < 3f && deltaX < 3f) {
                other = p;
                break;
            }
        }

        User finalOther = other;
        interactionDialog.button("Send Flower").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Result result;
                if (getFriendship(me, finalOther) != null) {
                    result = giveFlowers(finalOther.getUsername());
                    // fade in -> wait 1s -> fade out
                    if (result.IsSuccess())
                        bouquetImage.addAction(Actions.sequence(
                        Actions.alpha(0f),
                        Actions.fadeIn(0.3f),
                        Actions.delay(1f),
                        Actions.fadeOut(0.5f)
                        ));
                    else {
                        showTimedDialog(result.massage(), 2f);
                    }
                }
            }
        });
        User finalOther1 = other;
        interactionDialog.button("Hug").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Result result;
                if (getFriendship(me, finalOther1) != null) {
                    result = hug(finalOther1.getUsername());
                    if (result.IsSuccess())
                        hugImage.addAction(Actions.sequence(
                            Actions.alpha(0f),
                            Actions.fadeIn(0.3f),
                            Actions.delay(1f),
                            Actions.fadeOut(0.5f)
                        ));
                    else {
                        showTimedDialog(result.massage(), 2f);
                    }
                }
            }
        });
        User finalOther2 = other;
        interactionDialog.button("Talk").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                talking(finalOther2.getUsername(), result -> {
                        showTimedDialog(result.massage(), 2f);
                });
            }
        });
        User finalOther3 = other;
        interactionDialog.button("Propose").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Result result;
                if (getFriendship(me, finalOther3) != null) {
                    result = propose(finalOther3.getUsername());
                    if (result.IsSuccess()) {
                        ringImage.addAction(Actions.sequence(
                            Actions.alpha(0f),
                            Actions.fadeIn(0.3f),
                            Actions.delay(1.3f),
                            Actions.fadeOut(0.5f)
                        ));
                    }
                    else {
                        showTimedDialog(result.massage(), 2f);
                    }
                }
            }
        });


        return interactionDialog;
    }
    private Dialog makingFriendDialog() {
        friendsListdialog = new Dialog("", newSkin);
        friendsListdialog.setModal(true);
        friendsListdialog.setMovable(true);

        Table friendTable = new Table();
        friendTable.top().left().pad(10);


        String targetName = currentGame.currentPlayer.getUsername();
        User friendUser;

        for (HumanCommunications f : currentGame.friendships) {
            if (f.getPlayer1().getUsername().equals(targetName)) {
                friendUser = f.getPlayer2();
            }
            else if (f.getPlayer2().getUsername().equals(targetName)) {
                friendUser = f.getPlayer1();
            }
            else continue;

            // ستون 1: عکس پروفایل
            Texture avatarTexture;
            if (friendUser.getGender().equalsIgnoreCase("man"))
                avatarTexture = new Texture(Gdx.files.internal("Ariyo/Pierre.png"));
            else avatarTexture = new Texture(Gdx.files.internal("Ariyo/Marnie.png"));
            Image avatar = new Image(new TextureRegionDrawable(new TextureRegion(avatarTexture)));
            avatar.setSize(32, 32);
            avatar.setScaling(Scaling.fit);

            // ستون 2: نام
            Label nameLabel = new Label(friendUser.getNickname(), skin);
            nameLabel.setColor(Color.CYAN);

            // ستون 3: XP و Level
            Label statsLabel = new Label("   Level " + f.getLevel(), skin);
            statsLabel.setFontScale(0.8F);
            statsLabel.setColor(Color.WHITE);

            // ستون 4: دکمه هدیه
            Texture giftTexture = new Texture(Gdx.files.internal("Ariyo/Emojis152.png"));
            Drawable giftDrawable = new TextureRegionDrawable(new TextureRegion(giftTexture));
            ImageButton giftButton = new ImageButton(giftDrawable);
            giftButton.setSize(32, 32);
            giftButton.getImage().setScaling(Scaling.fit);

            // رویداد کلیک روی هدیه
            User finalFriendUser = friendUser;
            giftButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("Sending Gift to " + finalFriendUser.getNickname());
                }
            });

            // اضافه کردن به ردیف جدول
            friendTable.row().pad(5);
            friendTable.add(avatar).size(32, 32).padRight(10);
            friendTable.add(nameLabel).width(100).left().padRight(10);
            friendTable.add(statsLabel).width(150).left().padRight(10);
            friendTable.add(giftButton).width(80).right();
        }

        ScrollPane scrollPane = new ScrollPane(friendTable, skin); // در صورت زیاد بودن
        scrollPane.setFadeScrollBars(false);

        friendsListdialog.getContentTable().add(scrollPane).width(450).height(250).pad(10);
        friendsListdialog.button("CLOSE");

        return friendsListdialog;
    }
    public void showTimedDialog(String message, float durationSeconds) {
        activeDialog = new Dialog("", newSkin);
        activeDialog.text(message);
        activeDialog.pack();
        activeDialog.setPosition(
            (Gdx.graphics.getWidth() - activeDialog.getWidth()) / 2,
            (Gdx.graphics.getHeight() - activeDialog.getHeight()) / 2
        );

        activeDialog.show(stage);

        dialogExpirationTime = TimeUtils.millis() + (long)(durationSeconds * 1000);
    }

                                                                //  //  //  //   Erfan
    private void initialize () {

        currentMenu = Menu.GameMenu;
        startTime = TimeUtils.millis();
        lastTime = TimeUtils.millis();

        controller = InputGameController.getInstance();
        stage = new Stage(new ScreenViewport());
        clockGroup = new Group();
        camera = new OrthographicCamera();

        BitmapFont font = new BitmapFont();
        energyStyle = new Label.LabelStyle();
        energyStyle.font = font;
        energyStyle.fontColor = Color.GREEN;

        Texture iconTexture = new Texture("Ariyo/Shane_Icon.png");
        Drawable iconDrawable = new TextureRegionDrawable(new TextureRegion(iconTexture));
        tempFriend = new ImageButton(iconDrawable);
        tempFriend.setSize(100, 100);
        tempFriend.setVisible(false);
        tempFriend.setPosition((float) ((float) Gdx.graphics.getWidth() *6.8/9), (float) ((float) Gdx.graphics.getHeight() *6/9));
        stage.addActor(tempFriend);

        tempFriend.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                makingInteractionDialog().show(stage);
            }
        });



        energyLabel = new Label("Energy : 100", newSkin);
        lastHealth = -1;
        energyLabel = new Label("Energy : 100", energyStyle);
        energyLabel.setPosition((float) Gdx.graphics.getWidth() - energyLabel.getWidth() - 10, 10);
        stage.addActor(energyLabel);

        shapeRenderer = new ShapeRenderer();



        friendsListTexture = new Texture(Gdx.files.internal("Ariyo/Friendship_101.png"));
        buttonDrawable = new TextureRegionDrawable(new TextureRegion(friendsListTexture));
        friendButton = new ImageButton(buttonDrawable);
        friendButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                makingFriendDialog().show(stage);
            }
        });
        friendButton.setPosition((float) Gdx.graphics.getWidth() *7/9, (float) ((float) Gdx.graphics.getHeight() *6.7/9));
//        friendButton.setPosition(100, 100);
        stage.addActor(friendButton);

        bouquetImage = new Image(new Texture(Gdx.files.internal("Ariyo/Bouquet.png")));
        bouquetImage.setPosition(Gdx.graphics.getWidth() / 2f - bouquetImage.getWidth(),
            Gdx.graphics.getHeight() / 2f - bouquetImage.getHeight() / 2f);
        bouquetImage.getColor().a = 0f;
        bouquetImage.setSize(bouquetImage.getWidth()*3, bouquetImage.getHeight()*3);
        stage.addActor(bouquetImage);

        hugImage = new Image(new Texture(Gdx.files.internal("Ariyo/hug.png")));
        hugImage.setPosition(Gdx.graphics.getWidth() / 2f - hugImage.getWidth(), Gdx.graphics.getHeight() / 2f - hugImage.getHeight() / 2f);
        hugImage.setSize(hugImage.getWidth()*3, hugImage.getHeight()*3);
        hugImage.getColor().a = 0f;
        stage.addActor(hugImage);

        ringImage = new Image(new Texture(Gdx.files.internal("Ariyo/Sturdy_Ring.png")));
        ringImage.setPosition(Gdx.graphics.getWidth() / 2f - ringImage.getWidth(),  Gdx.graphics.getHeight() / 2f - ringImage.getHeight() / 2f);
        ringImage.setSize(ringImage.getWidth()*3, ringImage.getHeight()*3);
        ringImage.getColor().a = 0f;
        stage.addActor(ringImage);



        timeLabel = new Label("", skin);
        dateLabel = new Label("", skin);
        moneyLabel = new Label("", skin);
        weekDayLabel = new Label("", skin);


        showInformationIsActivated = false;
        informationIsActivated = false;
        socialMenuIsActivated = false;
        toolsMenuIsActivated = false;
        inventoryIsActivated = false;
        skillMenuIsActivated = false;
        EscMenuIsActivated = false;
        subMenuIsActivated = false;
        mapIsActivated = false;
        startRotation = false;
    }

    private void inputController () {

        if (!anyMenuIsActivated()) {

            if (Gdx.input.isKeyJustPressed(Keys.ToolsMenu))
                createToolsMenu();
            else if (Gdx.input.isKeyJustPressed(Keys.EscMenu))
                createEscMenu();
            else if (Gdx.input.isKeyJustPressed(Keys.increaseTime))
                updateClock(2);
            else if (Gdx.input.isKeyJustPressed(Keys.lighting))
                createCloud();
            else if (Gdx.input.isKeyJustPressed(Keys.energySet))
                createCheatEnergyMenu();
            else if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) // TODO
                handleLeftClick();
            else if (Gdx.input.isKeyJustPressed(Keys.informationMenu))
                showInformationMenu();
            else if (Gdx.input.isKeyJustPressed(Input.Keys.B))
                showSettingMenu();
            else if (Gdx.input.isKeyJustPressed(Input.Keys.F))
                alaki();
            else if (Gdx.input.isKeyJustPressed(Input.Keys.G))
                plow();


            else if (Gdx.input.isKeyJustPressed(Input.Keys.H)) {
                Main.getMain().setScreen(
                    new TransitionScreen(
                        Main.getMain(),
                        this,
                        new HomeMenu(),
                        1f
                    )
                );
            }

            else if (Gdx.input.isKeyJustPressed(Input.Keys.K)) {
                User temp = currentGame.currentPlayer;
                ArrayList<User> list = currentGame.players;
                if (temp.getUsername().equals(list.get(list.size() - 1).getUsername())) {
                    currentGame.currentPlayer = list.get(0);
                    return;
                }
                boolean found = false;
                for (User user : list) {
                    if (found) {
                        currentGame.currentPlayer = user;
                        return;
                    }
                    if (user.getUsername().equals(temp.getUsername())) {
                        found = true;
                    }
                }
            }


        } else if (inventoryIsActivated) {

            if (Gdx.input.isKeyJustPressed(Keys.EscMenu))
                ExitOfMenu();
            else if (Gdx.input.isKeyJustPressed(Keys.delete) && currentGame.currentPlayer.currentItem != null) {
                GameControllerLogic.advanceItem(
                    currentGame.currentPlayer.currentItem,
                    - currentGame.currentPlayer.getBackPack().inventory.Items.get(currentGame.currentPlayer.currentItem));
                ExitOfMenu();
                createInventory();
            }
        } else
            if (Gdx.input.isKeyJustPressed(Keys.EscMenu))
                ExitOfMenu();
    }
    private void ExitOfMenu() {

        if (toolsMenuIsActivated) {
            helperBackGround.remove();
            toolsPopup.remove();
            toolsMenuIsActivated = false;
        }
        else if (inventoryIsActivated) {
            inventoryPopup.remove();
            inventoryIsActivated = false;
        }
        else if (skillMenuIsActivated) {
            skillPopup.remove();
            skillMenuIsActivated = false;
        }
        else if (socialMenuIsActivated) {
            socialPopup.remove();
            socialMenuIsActivated = false;
        }
        else if (mapIsActivated) {
            mapGroup.remove();
            mapIsActivated = false;
        }
        else if (setEnergyIsActivated) {
            setEnergyPopup.remove();
            setEnergyIsActivated = false;
        }
        else if (EscMenuIsActivated) {
            helperBackGround.remove();
            EscPopup.remove();
            EscMenuIsActivated = false;
        }
        else if (settingIsActivated) {
            settingMenuGroup.remove();
            helperBackGround.remove();
            settingIsActivated = false;
        }

    }
    private void alaki () {

        for (int i = 0; i < 30 ; i++)
            for (int j = 0; j < 30 ; j++) {
                Tile tile = getTileByCoordinates(j, i);
                if (tile.getGameObject() instanceof Walkable)
                    System.out.println(((Walkable) tile.getGameObject()).getGrassOrFiber());
            }


    }

    private void showSettingMenu() {

        createGrayBackGround();
        settingIsActivated = true;

        settingMenuGroup = new Window("", App.newSkin);
        settingMenuGroup.setSize(320, 200);
        settingMenuGroup.setPosition(
            (stage.getViewport().getWorldWidth() - settingMenuGroup.getWidth()) / 2,
            (stage.getViewport().getWorldHeight() - settingMenuGroup.getHeight()) / 2
        );

        TextButton exitButton = new TextButton("Exit Game", App.newSkin);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // TODO
            }
        });

        TextButton voteKickButton = new TextButton(" Vote to Kick Player", App.newSkin);
        voteKickButton.setDisabled(!currentGame.currentPlayer.equals(currentUser));

        voteKickButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!currentGame.currentPlayer.equals(currentUser)) return;

                Window kickWindow = new Window("Select Player to Kick", App.newSkin);
                kickWindow.setSize(300, 250);
                kickWindow.setPosition(
                    (stage.getViewport().getWorldWidth() - kickWindow.getWidth()) / 2,
                    (stage.getViewport().getWorldHeight() - kickWindow.getHeight()) / 2
                );

                VerticalGroup playersList = new VerticalGroup();
                playersList.space(10);
                playersList.pad(10);
                playersList.top();

                for (User player : currentGame.players) {
                    if (player.equals(currentUser)) continue;

                    TextButton playerButton = new TextButton(player.getNickname(), App.newSkin);
                    playerButton.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            // اینجا حذف واقعی رو انجام بده
                            currentGame.players.remove(player);
                            kickWindow.remove(); // یا hide()
                        }
                    });
                    playersList.addActor(playerButton);
                }

                ScrollPane scrollPane = new ScrollPane(playersList, App.newSkin);
                scrollPane.setFadeScrollBars(false);
                kickWindow.add(scrollPane).width(280).height(200).pad(10);

                stage.addActor(kickWindow);
            }
        });

        VerticalGroup menuOptions = new VerticalGroup();
        menuOptions.space(15);
        menuOptions.pad(20);
        menuOptions.addActor(exitButton);
        menuOptions.addActor(voteKickButton);

        settingMenuGroup.add(menuOptions).expand().fill();

        stage.addActor(settingMenuGroup);
    } // TODO

    public void showInformationMenu () {

        Skin skin = App.newSkin;
        createGrayBackGround();

        mainInformationPopup = new Window("", newSkin);

        mainInformationPopup.setSize(400, 450);
        mainInformationPopup.setPosition(
            (stage.getViewport().getWorldWidth() - mainInformationPopup.getWidth()) / 2,
            (stage.getViewport().getWorldHeight() - mainInformationPopup.getHeight()) / 2
        );

        Table menuTable = new Table(skin);
        menuTable.setFillParent(true);
        menuTable.center().pad(10f);

        String[] options = {"Tree", "Mineral", "Crops", "Plant"};
        for (String option : options) {
            TextButton button = new TextButton(option, skin);
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    mainInformationPopup.remove();
                    helperBackGround.remove();
                    informationIsActivated = false;
                    showSubMenu(option);
                }
            });
            menuTable.row().pad(10);
            menuTable.add(button).width(200).height(50);
        }

        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainInformationPopup.remove();
                informationIsActivated = false;
                helperBackGround.remove();
            }
        });

        menuTable.row().pad(10);
        menuTable.add(backButton).width(200).height(50);

        informationIsActivated = true;

        mainInformationPopup.addActor(menuTable);
        stage.addActor(mainInformationPopup);
    }
    public void showSubMenu(String type) {

        Skin skin = App.newSkin;
        createGrayBackGround();


        subMenuGroup = new Window("", newSkin);

        subMenuGroup.setSize(400, 250);
        subMenuGroup.setPosition(
            (stage.getViewport().getWorldWidth() - subMenuGroup.getWidth()) / 2,
            (stage.getViewport().getWorldHeight() - subMenuGroup.getHeight()) / 2
        );

        Table subMenuTable = new Table(skin);
        subMenuTable.setFillParent(true);
        subMenuTable.center().pad(10f);

        Label title = new Label("Enter name for: " + type, skin);
        TextField nameField = new TextField("", skin);

        TextButton submitButton = new TextButton("Submit", skin);
        submitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String enteredName = nameField.getText();
                subMenuGroup.remove();
                subMenuIsActivated = false;
                helperBackGround.remove();
                handleInformationSubmit(type, enteredName);
            }
        });

        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                subMenuGroup.remove();
                subMenuIsActivated = false;
                helperBackGround.remove();
                showInformationMenu();
            }
        });

        subMenuTable.add(title).colspan(2).padBottom(10).row();
        subMenuTable.add(nameField).width(250).colspan(2).padBottom(10).row();
        subMenuTable.add(submitButton).width(120).padRight(10);
        subMenuTable.add(backButton).width(120);

        subMenuIsActivated = true;

        subMenuGroup.addActor(subMenuTable);
        stage.addActor(subMenuGroup);
    }
    private void handleInformationSubmit(String type, String name) {

        createGrayBackGround();

        showInformationPopup = new Window("", newSkin);
        showInformationPopup.setSize(500, 600);
        showInformationPopup.setPosition(
            (stage.getViewport().getWorldWidth() - showInformationPopup.getWidth()) / 2,
            (stage.getViewport().getWorldHeight() - showInformationPopup.getHeight()) / 2
        );

        String description = "No description found.";
        Image image = new Image();

        try {
            switch (type) {
                case "Tree": {
                    TreeType tree = TreeType.fromDisplayName(name);
                    description = TreeType.getInformation(tree);
                    image = new Image(new TextureRegionDrawable(new TextureRegion(TextureManager.get(tree.getPath(5)))));
                    image.setHeight(160);
                    image.setWidth(96);
                    break;
                }
                case "Mineral": {
                    ForagingMineralsType mineral = ForagingMineralsType.fromDisplayName(name);
                    description = mineral.getDescription();
                    image = new Image(new TextureRegionDrawable(new TextureRegion(TextureManager.get(mineral.getTexturePath()))));
                    image.setHeight(70);
                    image.setWidth(70);
                    break;
                }
                case "Crops": {
                    CropsType crop = CropsType.fromDisplayName(name);
                    description = CropsType.getInformation(crop);
                    image = new Image(new TextureRegionDrawable(new TextureRegion(TextureManager.get(crop.getIconPath()))));
                    image.setHeight(70);
                    image.setWidth(70);
                    break;
                }
                case "Plant": {
                    ForagingCropsType plant = ForagingCropsType.fromDisplayName(name);
                    description = ForagingCropsType.getInformation(plant);
                    image = new Image(new TextureRegionDrawable(new TextureRegion(TextureManager.get(plant.getTexturePath()))));
                    image.setHeight(70);
                    image.setWidth(70);
                    break;
                }
            }
        } catch (IllegalArgumentException e) {
            description = "Invalid name: " + name;
        }


        Label descriptionLabel = new Label(description, App.newSkin);
        descriptionLabel.setWrap(true);
        descriptionLabel.setWidth(360);

        image.setPosition(
            (showInformationPopup.getWidth() - image.getWidth()) / 2,
            showInformationPopup.getHeight() - image.getHeight() - 30
        );

        descriptionLabel.setPosition(
            (showInformationPopup.getWidth() - descriptionLabel.getWidth()) / 2,
                image.getY() - descriptionLabel.getHeight()
        );

        showInformationPopup.addActor(image);
        showInformationPopup.addActor(descriptionLabel);


        TextButton backButton = new TextButton("Back", newSkin);
        backButton.setSize(100, 40);
        backButton.setPosition((showInformationPopup.getWidth() - backButton.getWidth()) / 2, 20);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showInformationPopup.remove();
                showInformationIsActivated = false;
                helperBackGround.remove();
            }
        });

        showInformationPopup.addActor(backButton);

        showInformationIsActivated = true;
        stage.addActor(showInformationPopup);
    }


    public void createMap () {

        mapGroup = new Group();

        Texture texture = new Texture(Gdx.files.internal(GameTexturePath.map.getPath()));
        Image image = new Image(new TextureRegionDrawable(new TextureRegion(texture)));

        image.setHeight(image.getHeight() * 3);
        image.setWidth(image.getWidth() * 3);


        image.setPosition(
            stage.getWidth() / 2f - image.getWidth() / 2f,
            stage.getHeight() / 2f - image.getHeight() / 2f + 50
        );

        TextButton backButton = new TextButton("Back", App.newSkin);
        backButton.setSize(100, 40);
        backButton.setPosition(
            stage.getWidth() / 2f - backButton.getWidth() / 2f,
            image.getY() - 60
        );

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mapGroup.remove();
                texture.dispose();
            }
        });

        mapIsActivated = true;

        mapGroup.addActor(image);
        mapGroup.addActor(backButton);

        stage.addActor(mapGroup);
    }

    private void updateEnergyLabel () {

        int currentHealth = currentGame.currentPlayer.getHealth();
        if (currentHealth != lastHealth) {

            energyLabel.setText("Energy : " + currentGame.currentPlayer.getHealth());
            Label.LabelStyle style = energyLabel.getStyle();

            if (currentGame.currentPlayer.getHealth() <= 20)
                style.fontColor = Color.RED;
            else
                style.fontColor = Color.GREEN;

            energyLabel.setStyle(style);
        }
    }

    private void handleLeftClick () {
        useCurrentItem();
        Direction direction = Direction.getDirByCord(
            currentGame.currentPlayer.getSprite().getX(),
            90 - currentGame.currentPlayer.getSprite().getY(),
            getVector().x, 90 - getVector().y
        );
        if (direction != null) {

            int dir = 0;
            Items items = currentGame.currentPlayer.currentItem;
            switch (direction) {

                case Up -> dir = 3;
                case Right -> dir = 1;
                case Down -> dir = 7;
                case Left -> dir = 5;
            }
            if (dir != 0) {
                if (items instanceof Tools) {
                    currentGame.currentPlayer.currentTool = (Tools) items;
                    controller.useTools(dir);
                }
                else if (items instanceof TreeSource)
                    plantTree(((TreeSource) items).getType(), dir);
                else if (items instanceof ForagingSeeds)
                    plantForagingSeed(((ForagingSeeds) items).getType(), dir);
                else if (items instanceof MixedSeeds)
                    plantMixedSeed(dir);
            }
        }
    }

    private void createCheatEnergyMenu () {

        setEnergyPopup = new Window("", App.newSkin);
        setEnergyPopup.setSize(400, 300);
        setEnergyPopup.setPosition(
            (stage.getViewport().getWorldWidth() - setEnergyPopup.getWidth()) / 2,
            (stage.getViewport().getWorldHeight() - setEnergyPopup.getHeight()) / 2
        );
        setEnergyPopup.setMovable(false);

        energyInputField = new TextField("", App.newSkin);
        energyInputField.setMessageText("       Amount");
        energyInputField.setTextFieldFilter(new TextField.TextFieldFilter.DigitsOnlyFilter());


        confirmButton = new TextButton("OK", App.newSkin);
        confirmButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String inputText = energyInputField.getText();
                if (!inputText.isEmpty()) {
                    try {
                        int energy = Integer.parseInt(inputText);

                        currentGame.currentPlayer.setHealth(energy);

                        setEnergyPopup.remove();
                        setEnergyIsActivated = false;

                    } catch (NumberFormatException e) {
                    }
                }
            }
        });

        setEnergyPopup.defaults().pad(10);
        setEnergyPopup.row();
        setEnergyPopup.add(new Label("Input energy amount", App.newSkin)).padTop(20);
        setEnergyPopup.row();
        setEnergyPopup.add(energyInputField).width(200);
        setEnergyPopup.row();
        setEnergyPopup.add(confirmButton).width(100);

        stage.addActor(setEnergyPopup);
        setEnergyIsActivated = true;

    }

    private void createToolsMenu () {

        createGrayBackGround();

        HashMap<String, String> availableTools = controller.availableTools();
        Items currentItem = currentGame.currentPlayer.currentItem;

        int colNumber = availableTools.size() / 2 + 1;

        toolsPopup = new Window("", newSkin);
        toolsPopup.setSize(200 + colNumber * 100, 300);
        toolsPopup.setPosition(
            (stage.getWidth() - toolsPopup.getWidth()) / 2,
            (stage.getHeight() - toolsPopup.getHeight()) / 2);


        Table content = new Table();
        createToolsTable(content, currentItem, availableTools);


        AnimatedImage animatedImage = new AnimatedImage(0.15f, SampleAnimation.Bat, Animation.PlayMode.LOOP);

        toolsPopup.add(content).expand().fill();
        toolsPopup.row();
        toolsPopup.add(animatedImage).size(32, 32).right().padRight(10).padBottom(10);

        stage.addActor(toolsPopup);
        toolsMenuIsActivated = true;
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
                             int i, Label.LabelStyle labelStyle)  {
        Map.Entry<String, String> entry = entries.get(i);
        Label label1 = new Label(entry.getKey(), labelStyle);
        label1.setColor(Color.WHITE);
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

    private void creatSkillMenu () {

        int colNumber = 3;

        skillPopup = new Window("", App.newSkin);
        skillPopup.setSize(200 + colNumber * 100, 320);
        skillPopup.setPosition(
            (stage.getWidth() - skillPopup.getWidth()) / 2,
            (stage.getHeight() - skillPopup.getHeight()) / 2);


        Table content = new Table();
        createSkillsTable(content);

        AnimatedImage animatedImage = new AnimatedImage(0.15f, SampleAnimation.Pillow, Animation.PlayMode.LOOP);

        skillPopup.add(content).expand().fill();
        skillPopup.row();
        skillPopup.add(animatedImage).size(32, 32).right().padRight(10).padBottom(10);

        stage.addActor(skillPopup);
        skillMenuIsActivated = true;
    }
    private void createSkillsTable (Table content) {

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = GameAssetManager.getGameAssetManager().getTinyFont();

        content.setFillParent(true);
        content.top();
        content.defaults().pad(10);

        content.row().padTop(60);

        BitmapFont font = GameAssetManager.getGameAssetManager().getTinyFont();
        font.getData().setScale(0.4f);
        labelStyle.font = font;


        addSkillImage(content, Skills.Fishing);
        addSkillImage(content, Skills.Mining);
        content.row();

        addSkillName(content, labelStyle, Skills.Fishing);
        addSkillName(content, labelStyle, Skills.Mining);

        content.row();

        addSkillImage(content, Skills.Farming);
        addSkillImage(content, Skills.Foraging);

        content.row();
        addSkillName(content, labelStyle, Skills.Farming);
        addSkillName(content, labelStyle, Skills.Foraging);
    }
    private void addSkillName(Table content, Label.LabelStyle labelStyle, Skills skill)  {

        Label label1 = new Label(skill.name(), labelStyle);
        label1.setColor(Color.WHITE);
        label1.setSize(15, 8);
        content.add(label1);

    }
    private void addSkillImage (Table content, Skills skill) {

        Image img = new Image(new TextureRegionDrawable(new TextureRegion(TextureManager.get(skill.getPath()))));
        img.setColor(1f, 1f, 1f, 0.8f);

        Dialog dialog = Marketing.getInstance().createDialogError();
        final Label tooltipLabel = new Label(skill.getDiscription(), App.newSkin);
        tooltipLabel.setVisible(true);
        tooltipLabel.setColor(Color.LIGHT_GRAY);

        Marketing.getInstance().addDialogToTable(dialog, tooltipLabel, this);


        img.addListener(new ClickListener() {

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                dialog.setVisible(true);
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                dialog.setVisible(false);
            }
        });
        content.add(img).size(60, 60);
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


        seasonImage = new Image(TextureManager.get(currentGame.currentDate.getSeason().getIconPath()));
        seasonImage.setSize(200,220);
        seasonImage.setPosition(image.getWidth()- seasonImage.getWidth() + 47, image.getHeight() - seasonImage.getHeight() + 30);
        clockGroup.addActor(seasonImage);

        weatherImage = new Image(TextureManager.get(currentGame.currentWeather.getIconPath()));
        weatherImage.setSize(200,220);
        weatherImage.setPosition(image.getWidth()- weatherImage.getWidth() - 47, image.getHeight() - weatherImage.getHeight() + 30);
        clockGroup.addActor(weatherImage);


        float screenWidth = stage.getViewport().getWorldWidth();
        float screenHeight = stage.getViewport().getWorldHeight();

        clockGroup.setSize(image.getWidth(), image.getHeight());

        clockGroup.setPosition(
            screenWidth - clockGroup.getWidth() - 10,
            screenHeight - clockGroup.getHeight() - 10);

        stage.addActor(clockGroup);
    }
    private void updateClock(int hourPassed) {

        passedOfTime(0, hourPassed);
        timeLabel.setText(currentGame.currentDate.getHour() + ":00");
        dateLabel.setText(currentGame.currentDate.getDate());
        weekDayLabel.setText(currentGame.currentDate.getDayOfTheWeek().name());
        Texture newTexture = TextureManager.get(currentGame.currentDate.getSeason().getIconPath());
        seasonImage.setDrawable(new TextureRegionDrawable(new TextureRegion(newTexture)));
        Texture newTexture1 = TextureManager.get(currentGame.currentWeather.getIconPath());
        weatherImage.setDrawable(new TextureRegionDrawable(new TextureRegion(newTexture1)));
        lastTime = TimeUtils.millis();

    }

    private void createEscMenu () {

        createGrayBackGround();

        EscPopup = new Window("", newSkin);
        EscPopup.setSize(650, 350);
        EscPopup.setPosition(
            (stage.getWidth() - EscPopup.getWidth()) / 2,
            (stage.getHeight() - EscPopup.getHeight()) / 2);


        Table content = new Table();
        createEscMenuButtons(content);

        EscPopup.add(content).expand().fill();

        stage.addActor(EscPopup);
        EscMenuIsActivated = true;
    }
    private void createEscMenuButtons (Table table) {

        table.setFillParent(true);
        table.defaults().align(Align.center).pad(10);
        table.center();

        TextButton inventoryButton = new TextButton("Inventory", newSkin);
        TextButton skillsButton = new TextButton("Skills", newSkin);
        TextButton SocialButton = new TextButton("Social", newSkin);
        TextButton backButton = new TextButton("back", newSkin);
        TextButton mapButton = new TextButton("Map", newSkin);


//        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(backButton.getStyle()); // کپی مستقل
//        Drawable newBackground = new TextureRegionDrawable(new TextureRegion(new Texture("Erfan/Cancel.png")));
//        style.up = newBackground;
//        backButton.setStyle(style);


        table.add(inventoryButton).width(250).center();
        table.add(skillsButton).width(250).center();
        table.row().pad(15, 0, 10, 0);
        table.add(SocialButton).width(250).center();
        table.add(mapButton).width(250).center();
        table.row().pad(30, 0, 10, 0);

        table.add(backButton).width(150).colspan(2).center();


        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                helperBackGround.remove();
                EscPopup.remove();
                EscMenuIsActivated = false;
            }
        });
        inventoryButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                createInventory();
            }
        });
        skillsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                creatSkillMenu();
            }
        });
        SocialButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                createSocialMenu();
            }
        });
        mapButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                createMap();
            }
        });
        stage.addActor(table);
    }

    private void drawCurrentItem() {

        Items currentItem = currentGame.currentPlayer.currentItem;
        if (currentItem == null) return;

        Direction direction = currentGame.currentPlayer.getDirection();
        float x = getXForHands(direction), y = getYForHands(direction);

        if (currentItemSprite == null || !currentItemSprite.getTexture().equals(TextureManager.get(currentItem.getInventoryIconPath()))) {
            currentItemSprite = new Sprite(TextureManager.get(currentItem.getInventoryIconPath()));
        }


        currentItemSprite.flip(Direction.lastDir != null && Direction.lastDir != direction &&
            (direction == Direction.Left || Direction.lastDir == Direction.Left), false);

        if (direction == Direction.Left) {
            currentItemSprite.setOrigin(currentItemSprite.getWidth(), 0);
        } else {
            currentItemSprite.setOrigin(0, 0);
        }

        currentItemSprite.setRotation(currentRotation);

        currentItemSprite.setPosition(x, y);
        currentItemSprite.setSize(EQUIP_THING_SIZE, EQUIP_THING_SIZE);
        Direction.lastDir = direction;
        currentItemSprite.draw(Main.getBatch());
    }
    private void useCurrentItem() {
        Items currentItem = currentGame.currentPlayer.currentItem;

        if (!(currentItem instanceof Tools)) return;

        currentRotation = 0f;

        new Timer().scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                currentRotation = 45f;

                new Timer().scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        currentRotation = 0f;
                    }
                }, 0.3f);
            }
        }, 0.3f);
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

    public void createInventory () {

        inventoryPopup = new Window("", newSkin);
        inventoryPopup.setSize(1300, 700);
        inventoryPopup.setPosition(
            (stage.getWidth() - inventoryPopup.getWidth()) / 2,
            (stage.getHeight() - inventoryPopup.getHeight()) / 2);


        Drawable bg = new TextureRegionDrawable(new TextureRegion(TextureManager.get("Erfan/Inventory/Inventory.png")));
        inventoryPopup.setBackground(bg);

        Table currentItemTable = new Table();
        createCurrentItem(currentItemTable);

        Table content = new Table();
        createItems(content);
        content.padRight(300);
        content.padBottom(293);

        inventoryPopup.add(content);
        inventoryPopup.add(currentItemTable).align(Align.topRight).padRight(215).padBottom(400);

        stage.addActor(inventoryPopup);
        inventoryIsActivated = true;
    }
    private void createItems (Table content) {

        content.defaults().pad(5);
        content.setFillParent(false);
        content.sizeBy(350, 600);
        content.setPosition(300, 300);
        content.padLeft(50);

        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;

        int number = 0;

        for (Map.Entry<Items, Integer> entry : inventory.Items.entrySet()) {

            if (number % 6 == 0)
                content.row();

            Items item = entry.getKey();
            int count = entry.getValue();

            Image itemButton = new Image(new Texture(item.getInventoryIconPath()));

            Items currentItem = currentGame.currentPlayer.currentItem;
            boolean isCurrent = currentItem != null && item.getName().equals(currentItem.getName());

            if (isCurrent) {
                itemButton.setColor(0.4f, 0.8f, 1f, 1f);
                itemButton.setScale(1.3f);
            } else
                itemButton.setColor(1f, 1f, 1f, 0.8f);


            Label countLabel = new Label("", newSkin);

            if (!(item instanceof Tools))
                countLabel.setText(count);

            countLabel.setFontScale(0.9f);
            countLabel.setColor(Color.BLACK);
            countLabel.setAlignment(Align.bottomRight);

            Table labelOverlay = new Table();
            labelOverlay.setFillParent(false);
            labelOverlay.add(countLabel).bottom().right().padLeft(35).padTop(50);

            Stack stack = new Stack();
            stack.add(itemButton);
            stack.add(labelOverlay);

            content.add(stack).width(60).height(60).padLeft(10);

            itemButton.addListener(new ClickListener() {

                @Override
                public void clicked(InputEvent event, float x, float y) {

                    if (currentItem != null && currentItem.getName().equals(item.getName()))
                        currentGame.currentPlayer.currentItem = null;
                    else
                        controller.itemEquip(item.getName());

                    helperBackGround.remove();
                    inventoryPopup.remove();
                    inventoryIsActivated = false;
                }
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    itemButton.setColor(1f, 1f, 1f, 1f);
                    itemButton.setScale(isCurrent ? 1.4f : 1.2f);
                }
                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    if (isCurrent) {
                        itemButton.setColor(0.4f, 0.8f, 1f, 1f);
                        itemButton.setScale(1.3f);
                    } else {
                        itemButton.setColor(1f, 1f, 1f, 0.8f);
                        itemButton.setScale(1f);
                    }
                }
            });
            number++;
        }
    }
    private void createCurrentItem (Table content) {

        Image img;
        if (currentGame.currentPlayer.currentItem != null)
            img = new Image(TextureManager.get(currentGame.currentPlayer.currentItem.getInventoryIconPath()));
        else
            img = new Image(TextureManager.get("Erfan/Cancel2.png"));

        content.add(img).align(Align.topRight).width(150).height(150).right();
        content.row();
    }

    private void createSocialMenu () {

        socialPopup = new Window("", App.newSkin);
        socialPopup.setSize(650, 850);
        socialPopup.setPosition(
            (stage.getWidth() - socialPopup.getWidth()) / 2,
            (stage.getHeight() - socialPopup.getHeight()) / 2);


        Table content = new Table();
        createDetailSocial(content);

        socialPopup.add(content).expand().fill();

        stage.addActor(socialPopup);
        socialMenuIsActivated = true;

    }
    private void createDetailSocial (Table content) {

        content.defaults().pad(5);
        content.setFillParent(false);

        AnimatedImage animatedImage1 = new AnimatedImage(0.18f, SampleAnimation.Heart, Animation.PlayMode.LOOP);
        AnimatedImage animatedImage2 = new AnimatedImage(0.21f, SampleAnimation.Pyramid, Animation.PlayMode.LOOP);

        content.add(animatedImage1).size(40).top().left();
        content.add(animatedImage2).size(40).top().right();

        for (NPC npc : NPC.values()) {

            content.row();
            content.add(new Image(new TextureRegionDrawable(new TextureRegion(TextureManager.get(npc.getIconPath())))));

            Label nameLabel = new Label("Friendship Level with " + npc.getName() +
                " : " + currentGame.currentPlayer.getFriendshipLevel(npc), App.newSkin);

            Label.LabelStyle copiedStyle = new Label.LabelStyle(nameLabel.getStyle());
            copiedStyle.fontColor = Color.GRAY;
            nameLabel.setStyle(copiedStyle);

            content.add(nameLabel);
        }
    }
    private void createGrayBackGround () {
        helperBackGround = new Image(new TextureRegionDrawable(new TextureRegion(TextureManager.get("Erfan/grayPage.jpg"))));
        helperBackGround.setColor(0, 0, 0, 0.5f);
        helperBackGround.setSize(stage.getWidth(), stage.getHeight());
        stage.addActor(helperBackGround);
    }
    public boolean anyMenuIsActivated () {
        return
            inventoryIsActivated || socialMenuIsActivated ||
            informationIsActivated || subMenuIsActivated ||
            toolsMenuIsActivated || EscMenuIsActivated ||
            setEnergyIsActivated || settingIsActivated ||
            skillMenuIsActivated || mapIsActivated;
    }



                /// /// /// /// ///                      Mohammad Reza

    public Vector3 getMousePos() {
        return mousePos;
    }
    public TiledMap getMap() {
        return map;
    }
    public void setTiledMap(TiledMap tiledMap) {
        map = tiledMap;
    }
    public boolean isFirstLoad() {
        return firstLoad;
    }
    public void setFirstLoad(boolean firstLoad) {
        this.firstLoad = firstLoad;
    }
    public OrthogonalTiledMapRenderer getRenderer() {
        return renderer;
    }
    public void setRenderer(OrthogonalTiledMapRenderer renderer) {
        this.renderer = renderer;
    }
    public BitmapFont getAnimalFont() {
        if (animalFont != null) {
            return animalFont;
        }
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Erfan/Fonts/Stardew Valley Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 48;
        parameter.color = Color.RED;

        animalFont = generator.generateFont(parameter);
        generator.dispose();
        return animalFont;
    }
    private void moveAnimal() {
        if (currentGame.currentPlayer.isInMine()) {
            for (Animal animal : shepherdingAnimals) {
                animal.getSprite().setRegion(animal.getAnimation().getKeyFrame(animal.getTimer()));
                if (! animal.getAnimation().isAnimationFinished(animal.getTimer())) {
                    animal.setTimer(animal.getTimer() + Gdx.graphics.getDeltaTime());
                }
                else {
                    animal.setTimer(0);
                }
            }
        }
    }
    public ArrayList<Animal> getShepherdingAnimals() {
        return shepherdingAnimals;
    }
    public ArrayList<HeartAnimation> getHeartAnimations() {
        return heartAnimations;
    }
    public Vector3 getVector() {
        if (vector == null) {
            vector = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        }
        vector.set(Gdx.input.getX() , Gdx.input.getY(), 0);
        if (currentMenu.getMenu().equals(gameMenu)) {
            camera.unproject(vector);
        }
        if (currentMenu.getMenu().equals(MarketMenu.getInstance())) {
            return MarketMenu.getVector();
        }
        return vector;
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
        Vector2 stageCoords = stage.screenToStageCoordinates(new Vector2(i, i1));
        System.out.println("Clicked on stage at: x=" + stageCoords.x + ", y=" + stageCoords.y);
        return true;
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

    public boolean getIsInMine() {
        return true;
    }

    public void setIsInMine(boolean b) {
    }

    public void initializeNPCs() {
        // Abigail's Movement
        NPC.Abigail.setRight(new Animation<>(
            0.8f,
            TextureManager.get("Mohamadreza/NPC/Abigail,Right1.png"),
            TextureManager.get("Mohamadreza/NPC/Abigail,Right2.png")
        ));
        NPC.Abigail.setDown(new Animation<>(
            0.8f,
            TextureManager.get("Mohamadreza/NPC/Abigail,Down1.png"),
            TextureManager.get("Mohamadreza/NPC/Abigail,Down2.png")
        ));
        NPC.Abigail.setUp(new Animation<>(
            0.8f,
            TextureManager.get("Mohamadreza/NPC/Abigail,Up1.png"),
            TextureManager.get("Mohamadreza/NPC/Abigail,Up2.png")
        ));
        NPC.Abigail.setLeft(new Animation<>(
            0.8f,
            TextureManager.get("Mohamadreza/NPC/Abigail,Left1.png"),
            TextureManager.get("Mohamadreza/NPC/Abigail,Left2.png")
        ));

        // Harvey's Movement
        NPC.Harvey.setRight(new Animation<>(
            1f,
            TextureManager.get("Mohamadreza/NPC/Harvey,Right1.png"),
            TextureManager.get("Mohamadreza/NPC/Harvey,Right2.png")
        ));
        NPC.Harvey.setDown(new Animation<>(
            1f,
            TextureManager.get("Mohamadreza/NPC/Harvey,Down1.png"),
            TextureManager.get("Mohamadreza/NPC/Harvey,Down2.png")
        ));
        NPC.Harvey.setUp(new Animation<>(
            1f,
            TextureManager.get("Mohamadreza/NPC/Harvey,Up1.png"),
            TextureManager.get("Mohamadreza/NPC/Harvey,Up2.png")
        ));
        NPC.Harvey.setLeft(new Animation<>(
            1f,
            TextureManager.get("Mohamadreza/NPC/Harvey,Left1.png"),
            TextureManager.get("Mohamadreza/NPC/Harvey,Left2.png")
        ));

        // Leah's Movement
        NPC.Leah.setRight(new Animation<>(
            0.5f,
            TextureManager.get("Mohamadreza/NPC/Leah,Right1.png"),
            TextureManager.get("Mohamadreza/NPC/Leah,Right2.png")
        ));
        NPC.Leah.setDown(new Animation<>(
            0.5f,
            TextureManager.get("Mohamadreza/NPC/Leah,Down1.png"),
            TextureManager.get("Mohamadreza/NPC/Leah,Down2.png")
        ));
        NPC.Leah.setUp(new Animation<>(
            0.5f,
            TextureManager.get("Mohamadreza/NPC/Leah,Up1.png"),
            TextureManager.get("Mohamadreza/NPC/Leah,Up2.png")
        ));
        NPC.Leah.setLeft(new Animation<>(
            0.5f,
            TextureManager.get("Mohamadreza/NPC/Leah,Left1.png"),
            TextureManager.get("Mohamadreza/NPC/Leah,Left2.png")
        ));

        // Robin's Movement
        NPC.Robin.setRight(new Animation<>(
            0.9f,
            TextureManager.get("Mohamadreza/NPC/Robin,Right1.png"),
            TextureManager.get("Mohamadreza/NPC/Robin,Right2.png")
        ));
        NPC.Robin.setDown(new Animation<>(
            0.9f,
            TextureManager.get("Mohamadreza/NPC/Robin,Down1.png"),
            TextureManager.get("Mohamadreza/NPC/Robin,Down2.png")
        ));
        NPC.Robin.setUp(new Animation<>(
            0.9f,
            TextureManager.get("Mohamadreza/NPC/Robin,Up1.png"),
            TextureManager.get("Mohamadreza/NPC/Robin,Up2.png")
        ));
        NPC.Robin.setLeft(new Animation<>(
            0.9f,
            TextureManager.get("Mohamadreza/NPC/Robin,Left1.png"),
            TextureManager.get("Mohamadreza/NPC/Robin,Left2.png")
        ));

        // Sebastian's Movement
        NPC.Sebastian.setRight(new Animation<>(
            0.7f,
            TextureManager.get("Mohamadreza/NPC/Sebastian,Right1.png"),
            TextureManager.get("Mohamadreza/NPC/Sebastian,Right2.png")
        ));
        NPC.Sebastian.setDown(new Animation<>(
            0.7f,
            TextureManager.get("Mohamadreza/NPC/Sebastian,Down1.png"),
            TextureManager.get("Mohamadreza/NPC/Sebastian,Down2.png")
        ));
        NPC.Sebastian.setUp(new Animation<>(
            0.7f,
            TextureManager.get("Mohamadreza/NPC/Sebastian,Up1.png"),
            TextureManager.get("Mohamadreza/NPC/Sebastian,Up2.png")
        ));
        NPC.Sebastian.setLeft(new Animation<>(
            0.7f,
            TextureManager.get("Mohamadreza/NPC/Sebastian,Left1.png"),
            TextureManager.get("Mohamadreza/NPC/Sebastian,Left2.png")
        ));




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
