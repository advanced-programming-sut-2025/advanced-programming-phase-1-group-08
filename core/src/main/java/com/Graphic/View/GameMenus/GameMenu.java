package com.Graphic.View.GameMenus;


import com.Graphic.Controller.MainGame.GameControllerLogic;
import com.Graphic.Controller.MainGame.InputGameController;
import com.Graphic.Controller.MainGame.Marketing;
import com.Graphic.Main;
import com.Graphic.View.AppMenu;
import com.Graphic.model.*;
import com.Graphic.model.Animall.Animal;
import com.Graphic.model.Animall.AnimalRenderer;
import com.Graphic.model.ClientServer.ClientWorkController;
import com.Graphic.model.ClientServer.GameState;
import com.Graphic.model.ClientServer.Message;
import com.Graphic.model.ClientServer.ServerHandler;
import com.Graphic.model.Enum.AllPlants.CropsType;
import com.Graphic.model.Enum.AllPlants.ForagingCropsType;
import com.Graphic.model.Enum.AllPlants.ForagingMineralsType;
import com.Graphic.model.Enum.AllPlants.TreeType;
import com.Graphic.model.Enum.*;
import com.Graphic.model.Enum.Commands.CommandType;
import com.Graphic.model.Enum.Fish.FishAIController;
import com.Graphic.model.Enum.Fish.FishMovementType;
import com.Graphic.model.Enum.Fish.FishType;
import com.Graphic.model.Enum.ItemType.Quantity;
import com.Graphic.model.Enum.NPC.NPC;
import com.Graphic.model.Enum.NPC.NPCManager;

import com.Graphic.model.Enum.Skills;
import com.Graphic.model.Enum.WeatherTime.Season;
import com.Graphic.model.HelpersClass.AnimatedImage;
import com.Graphic.model.HelpersClass.Result;
import com.Graphic.model.HelpersClass.SampleAnimation;
import com.Graphic.model.HelpersClass.TextureManager;

import com.Graphic.model.MapThings.Tile;
import com.Graphic.model.Places.Lake;
import com.Graphic.model.Plants.*;
import com.Graphic.model.ToolsPackage.FishingPole;
import com.Graphic.model.ToolsPackage.Tools;
import com.Graphic.model.Weather.DateHour;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.utils.StringBuilder;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.*;
import java.util.List;

import static com.Graphic.Controller.MainGame.GameControllerLogic.*;
import static com.Graphic.Main.newSkin;
import static com.Graphic.model.App.*;
import static com.Graphic.model.HelpersClass.TextureManager.EQUIP_THING_SIZE;
import static com.Graphic.model.HelpersClass.TextureManager.TEXTURE_SIZE;


public class GameMenu implements  Screen, InputProcessor , AppMenu {

    public static GameMenu gameMenu; // اگه صفحه ای اینجا قراره باز بشه که وقتی باز شد فرایند بازی متوقف بشه یه بولین برای فعال بودنش بزارین و تو تابع anyMenuIsActivated هم اوکیش کنین
    // TODO مملی ورودی گرفتن برای حرمت مردن رو هم بیار تو تابع اینپوت کنترلر چون مثلا منو باز میشه من میخوام a بنویسم دوربین حرکت میکنه مثلا و وقتی بیاری اونجا اوکی میشه
    public static OrthographicCamera camera;
    private Stage stage;
    public static boolean gameMenuInitialized = false;

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
    private ArrayList<UserRenderer> userRenderers;
    public GameState gameState;
    private ArrayList<CraftingRenderer> craftingRenderers;
    private Sprite withMouse;
    private ArrayList<AnimalRenderer> animalRenderers = new ArrayList<>();
    private ArrayList<LakeRenderer> lakeRenderers = new ArrayList<>();
    private boolean initialLake = false;
    private ArrayList<AnimalRenderer> currentBarnOrCageAnimals = new ArrayList<>();

    public boolean showFriendDialog = false;

    private boolean progressComplete = false;
    private boolean ePressed = false;
    private float holdTime = 0f;
    private final float maxHoldTime = 5f;
    private ShapeRenderer shapeRenderer;
    int shapeRendererLevel = 1;
    Sprite fishToCatch;
    Vector2 fishVelocity;
    Sprite minigame;
    private final Rectangle minigameArea = new Rectangle();
    Sprite fishCrown;
    FishAIController fishAI;
    FishType fishToCatchType;
    private float fishingProgress = 0f;
    Image bobble;
    boolean lostPerfectFishing = false;
    boolean showFishLight = false;
    float showFishLightTimer = 0;
    HumanCommunications giftingFriendship = null;


    Texture friendsListTexture;
    TextureRegionDrawable buttonDrawable;
    ImageButton friendButton;
    Dialog friendsListdialog;
    Label sixtyPlusLabel;
    Label onePlusLabel;
    ImageButton tempFriend;
    ImageButton tempFishing;
    float fishingTimer;
    private Table contextMenu;
    Image bouquetImage;
    Image hugImage;
    Image ringImage;
    private Dialog activeDialog = null;
    private long dialogExpirationTime = 0;

    public static Image helperBackGround;

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
    private DateHour lastDateHour;

    private boolean toolsMenuIsActivated;
    private Window toolsPopup;

    private boolean EscMenuIsActivated;
    private Window EscPopup;

    private boolean inventoryIsActivated;
    private boolean dialogActivated;
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

    private boolean NPCMenuIsActivated;
    private Window NPCMenuPopup;

    private boolean showInformationIsActivated;
    private Window showInformationPopup;

    private boolean subMenuIsActivated;
    private Window subMenuGroup;

    private boolean questsIsActivated;
    private Window questsMenuGroup;

    private boolean settingIsActivated;
    private Window settingMenuGroup;

    private TextField energyInputField;
    private TextButton confirmButton;

    private boolean waitingForGiftItemSelection = false;
    private NPC npc;


    private Sprite currentItemSprite;
    private boolean startRotation;
    private float currentRotation = 0f;


    private GameMenu() {}

    public static GameMenu getInstance() {
        if (gameMenu == null) {
            gameMenu = new GameMenu();
            //gameMenu.initialize();
        }
        return gameMenu;
    }


    public void show() {
        if (! Main.getClient().getLocalGameState().getChooseMap()) {
            try {
                controller = InputGameController.getInstance();
                currentMenu = Menu.GameMenu;
                initialize();
                controller.init();
                controller.chooseMap();

                mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
                camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

                multiplexer = new InputMultiplexer();

                multiplexer.addProcessor(stage);
                multiplexer.addProcessor(new InputAdapter() {
                    @Override
                    public boolean keyDown(int keycode) {
                        if (keycode == Input.Keys.E && Food.itemIsEatable(Main.getClient().getPlayer().currentItem)) {
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

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void render(float v) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        inputController();

        if (lastDateHour == null || !Main.getClient().getLocalGameState().currentDate.equals(lastDateHour))
            updateClock();

        initialLake();
        updateEnergyLabel();
        giftNPCMenu();
        Main.getBatch().setProjectionMatrix(camera.combined);
        gameState = Main.getClient().getLocalGameState();
        if (!Main.getClient().getPlayer().isInFarmExterior()) {
            getRenderer().setView(camera);
            getRenderer().render();
        }
        User x = null;
        if ((x = Main.getClient().getPlayer().getFriendCloseToMe()) != null) {
            printTempFriend(x);
        }
        else {
            tempFriend.setVisible(false);
        }
        if (activeDialog != null && TimeUtils.millis() > dialogExpirationTime) {
            activeDialog.hide();
            activeDialog.remove();
            activeDialog = null;
        }
        showUnseenMessages();
        showHugged();
        showGivenFlower();

        if (Main.getClient().getLocalGameState().getChooseMap()) {
            createUserRenderes();
            Main.getBatch().begin();
            controller.update(camera, v, anyMenuIsActivated());
            drawCurrentItem();
            updateAnimals(Main.getClient().getLocalGameState().getAnimals());
            NPCManager.NPCWalk(v);
            eatingManagement(v);
            checkLakeDistance(v);
            lightBeforeFishing(v);
            mousePos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(mousePos);
            camera.update();
            changeMenu();
            if (Main.getClient().getPlayer().isInMine() || Main.getClient().getPlayer().isInHome()) {
                getRenderer().setView(camera);
                getRenderer().render();
            }
            if (activeDialog != null && TimeUtils.millis() < dialogExpirationTime) {
                System.out.println("hi");
                stage.addActor(activeDialog);
            } else {
                activeDialog = null;
            }

            startFishing(v);
            Main.getBatch().end();
        }
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

                                                                // Ario

    private void lightBeforeFishing(float v) {
        if (!showFishLight)
            return;

        showFishLightTimer += v;
        if (showFishLightTimer > 2.5f) {
            showFishLightTimer = 0;
            showFishLight = false;
        }


        Sprite s = new Sprite(new Texture(Gdx.files.internal("Ariyo/light.png")));
        s.setRegion(TextureManager.get("Ariyo/light.png"));
        s.setPosition(Main.getClient().getPlayer().getPositionX() - 70, Main.getClient().getPlayer().getPositionY() - 150);
        s.setSize(s.getWidth()*2, s.getHeight()*2);
        s.draw(Main.getBatch());
    }
    private void checkLakeDistance(float v) {
//        Lake lake = Main.getClient().getPlayer().getFarm().getLake();
//        float lakeY = lake.getTopLeftY();
//        float lakeX = lake.getTopLeftX() + lake.getWidth() / 2f;
//        Sprite fisherman = new Sprite(TextureManager.get("Ariyo/fisherman.png"));
//
//        if (Main.getClient().getPlayer().doingMinigame) {
//            fisherman.setPosition(lakeX * TEXTURE_SIZE, (90 - lakeY) * TEXTURE_SIZE);
//            if (Main.getClient().getPlayer().getGender().equalsIgnoreCase("woman"))
//                fisherman.setRegion(TextureManager.get("Ariyo/fisherwoman.png"));
//            else
//                fisherman.setRegion(TextureManager.get("Ariyo/fisherman.png"));
//            fisherman.draw(Main.getBatch());
//
//            return;
//        }
//
//        fishingTimer += v;
//
//        if (!Main.getClient().getPlayer().isFishing)
//            fishingTimer = 0;
//
//        if (Main.getClient().getPlayer().isFishing && fishingTimer >= 10f) {
//            Main.getClient().getPlayer().doingMinigame = true;
//            shapeRenderer = new ShapeRenderer();
//            createGrayBackGround();
//            fishingTimer = 0;
//            minigame = new Sprite(new Texture(Gdx.files.internal("Ariyo/minigame.png")));
//            minigame.setSize(minigame.getWidth() * 2.5f, minigame.getHeight() * 2.5f);
//            minigame.setRegion(TextureManager.get("Ariyo/minigame.png"));
//
//            Random random = new Random();
//            fishToCatchType = FishType.values()[random.nextInt(FishType.values().length)];
//            fishToCatch = new Sprite(TextureManager.get(fishToCatchType.getIconPath()));
//            fishToCatch.setRegion(TextureManager.get(fishToCatchType.getIconPath()));
//            fishToCatch.setPosition(Main.getClient().getPlayer().getPositionX() + minigame.getWidth() / 2.3f, Main.getClient().getPlayer().getPositionY() + 180);
//            fishToCatch.setSize(fishToCatch.getWidth() * 0.6f, fishToCatch.getHeight() * 0.7f);
//            float verticalSpeed = 50f;
//            float horizontalWiggle = 10f;
//            float dx = (float) (Math.random() * horizontalWiggle * 2 - horizontalWiggle); // بین -10 تا +10
//            float dy = verticalSpeed;
//            fishVelocity = new Vector2(dx, dy);
//
//            if (fishToCatchType.isLegendary()) {
//                fishCrown = new Sprite(TextureManager.get("Ariyo/star.png"));
//                fishCrown.setRegion(TextureManager.get("Ariyo/star.png"));
//                fishCrown.setPosition(Main.getClient().getPlayer().getPositionX() + minigame.getWidth() / 2.3f, Main.getClient().getPlayer().getPositionY() + 190);
//            }
//            else
//                fishCrown = null;
//        }
//
//
//        float myX = Main.getClient().getPlayer().getPositionX();
//        float myY = Main.getClient().getPlayer().getPositionY();
//        float deltaX = Math.abs(myX - lakeX);
//        float deltaY = Math.abs(myY - lakeY);
//        if (!(deltaX < 3f && deltaY < 3f)) {
//            fishingTimer = 0;
//            tempFishing.setVisible(false);
//            return;
//        }
//
//        if (!Main.getClient().getPlayer().isFishing) {
//            tempFishing.setPosition(Main.getClient().getPlayer().getPositionX(), Main.getClient().getPlayer().getPositionY() + 40);
//            tempFishing.draw(Main.getBatch(), 1f);
//        }
//
//
//        if (!Main.getClient().getPlayer().isFishing && Gdx.input.isKeyJustPressed(Input.Keys.F)) {
//            boolean havePole = false;
//            Inventory inventory = Main.getClient().getPlayer().getBackPack().inventory;
//            for (Map.Entry<Items, Integer> entry: inventory.Items.entrySet()) {
//                if (entry.getKey() instanceof FishingPole) {
//                    havePole = true;
//                    break;
//                }
//            }
//            if (!havePole) {
//                showTimedDialog("You Don't Have a Fishing Pole!", 2f);
//                return;
//            }
//
//            showFishLight = true;
//
//            Main.getClient().getPlayer().isFishing = true;
//        }
//        else if (Main.getClient().getPlayer().isFishing && Gdx.input.isKeyJustPressed(Input.Keys.G)) {
//            Main.getClient().getPlayer().isFishing = false;
//        }
//
//        Texture iconTexture = new Texture(Gdx.files.internal("all image/Farming/Fishing.png"));
//        tempFishing.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(iconTexture));
//
//        tempFishing.setVisible(true);
//
//        if (Main.getClient().getPlayer().isFishing) {
//            fisherman.setPosition(lakeX * TEXTURE_SIZE, (90 - lakeY) * TEXTURE_SIZE);
//            if (Main.getClient().getPlayer().getGender().equalsIgnoreCase("woman"))
//                fisherman.setRegion(TextureManager.get("Ariyo/fisherwoman.png"));
//            else
//                fisherman.setRegion(TextureManager.get("Ariyo/fisherman.png"));
//            fisherman.draw(Main.getBatch());
//        }
//

    }

    private void startFishing(float v) {
        /// THE MINI GAME

        if (!Main.getClient().getPlayer().doingMinigame)
            return;

        float delta = Gdx.graphics.getDeltaTime();

        minigame.setPosition(Main.getClient().getPlayer().getPositionX(), Main.getClient().getPlayer().getPositionY() - minigame.getHeight()/2f);
        minigame.draw(Main.getBatch());

        minigameArea.set(
            Main.getClient().getPlayer().getPositionX() + minigame.getWidth() / 2.3f,
            Main.getClient().getPlayer().getPositionY() - 180,
            30,
            360
        );

        Main.getBatch().end();
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 1, 0, 0.5f);
        if (shapeRendererLevel == 0)
            shapeRenderer.rect(Main.getClient().getPlayer().getPositionX() + minigame.getWidth()/2.3f, Main.getClient().getPlayer().getPositionY() - 180, 30, 60);
        else if (shapeRendererLevel == 1)
            shapeRenderer.rect(Main.getClient().getPlayer().getPositionX() + minigame.getWidth()/2.3f, Main.getClient().getPlayer().getPositionY() - 120, 30, 60);
        else if (shapeRendererLevel == 2)
            shapeRenderer.rect(Main.getClient().getPlayer().getPositionX() + minigame.getWidth()/2.3f, Main.getClient().getPlayer().getPositionY() - 60, 30, 60);
        else if (shapeRendererLevel == 3)
            shapeRenderer.rect(Main.getClient().getPlayer().getPositionX() + minigame.getWidth()/2.3f, Main.getClient().getPlayer().getPositionY(), 30, 60);
        else if (shapeRendererLevel == 4)
            shapeRenderer.rect(Main.getClient().getPlayer().getPositionX() + minigame.getWidth()/2.3f, Main.getClient().getPlayer().getPositionY() + 60, 30, 60);
        else if (shapeRendererLevel == 5)
            shapeRenderer.rect(Main.getClient().getPlayer().getPositionX() + minigame.getWidth()/2.3f, Main.getClient().getPlayer().getPositionY() + 120, 30, 60);
        shapeRenderer.end();


        Rectangle greenBarBounds = new Rectangle(
            Main.getClient().getPlayer().getPositionX() + minigame.getWidth() / 2.3f,
            Main.getClient().getPlayer().getPositionY() - 180 + 60 * shapeRendererLevel,
            30,
            60
        );


        Rectangle fishBounds = new Rectangle(
            fishToCatch.getX(),
            fishToCatch.getY(),
            fishToCatch.getWidth(),
            fishToCatch.getHeight()
        );

        if (greenBarBounds.overlaps(fishBounds)) {
            fishingProgress += 0.2f * delta;
            if (fishingProgress > 1f) fishingProgress = 1f;
        } else {
            fishingProgress -= 0.5f * delta * 0.2f;
            lostPerfectFishing = true;
            if (fishingProgress < 0f) fishingProgress = 0f;
        }


        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0.6f, 0, 1f);

        float barX = Main.getClient().getPlayer().getPositionX() + minigameArea.width*3.3f;
        float barY = Main.getClient().getPlayer().getPositionY() - minigameArea.height / 2f;

        float verticalHeight = 365;
        shapeRenderer.rect(barX, barY, 10, verticalHeight * fishingProgress);

        shapeRenderer.end();

        Main.getBatch().begin();

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && shapeRendererLevel < 5) {
            shapeRendererLevel++;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && shapeRendererLevel > 0) {
            shapeRendererLevel--;
        }


        if (fishAI == null) {
            Random random = new Random();
            fishAI = new FishAIController(
                FishMovementType.values()[random.nextInt(FishMovementType.values().length)],
                new Vector2(
                    minigameArea.x + minigameArea.width / 2f,
                    minigameArea.y + minigameArea.height / 2f
                ),
                minigameArea.y,
                minigameArea.y + minigameArea.height - 60
            );





            // به فیش ای آی کار ندارم چون یه بار اجرا میشن اینجا مینویسم
            bobble = new Image(new TextureRegionDrawable(new TextureRegion(TextureManager.get("Ariyo/QM.png"))));
            bobble.setPosition(Main.getClient().getPlayer().getPositionX() + minigame.getWidth(), Main.getClient().getPlayer().getPositionY() + minigameArea.height / 2f);
            bobble.setSize(bobble.getWidth()*2, bobble.getHeight()*2);
            bobble.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    showTimedDialog("Fish Kind: " + fishToCatchType.getName() + "\n" + "Fish Movement Type: " + fishAI.getMovementType().name(), 2f);
                }
            });
            bobble.addListener(new InputListener() {
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    bobble.setColor(1, 1, 1, 0.5f);
                }
                public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    bobble.setColor(1, 1, 1, 1f);
                }
            });
            stage.addActor(bobble);
        }

        fishAI.update(delta);

        fishToCatch.setPosition(fishAI.getPosition().x - 13f, fishAI.getPosition().y);
        fishToCatch.draw(Main.getBatch());


        if (fishCrown != null) {
            fishCrown.setPosition(fishToCatch.getX() + 8,  fishToCatch.getY() + 35);
            fishCrown.draw(Main.getBatch());
        }


        if (Gdx.input.isKeyJustPressed(Input.Keys.Q))
            endFishing(false, false);
        if (fishingProgress == 1 && lostPerfectFishing)
            endFishing(true, false);
        else if (fishingProgress == 1) // perfect (because of else if)
            endFishing(true, true);

    }
    private void endFishing(boolean done, boolean perfect) {
        // for both
        lostPerfectFishing = false;
        fishAI = null;
        fishToCatch = null;
        fishingProgress = 0;
        shapeRendererLevel = 1;
        Main.getClient().getPlayer().doingMinigame = false;
        helperBackGround.remove();
        bobble.remove();

        if (!done)
            return;


        //only if you got the fish
        Random random = new Random();
        Quantity quantity = Quantity.values()[random.nextInt(Quantity.values().length)];
        if (!perfect) {
            HashMap<String , Object> body = new HashMap<>();
            body.put("Item" , new Fish(fishToCatchType, quantity));
            body.put("amount" , 1);
            body.put("Player" , Main.getClient().getPlayer());
            Main.getClient().getRequests().add(new Message(CommandType.CHANGE_INVENTORY, body));
            return;
        }

        //only if perfect
        HashMap<String , Object> body = new HashMap<>();
        if (quantity.equals(Quantity.Normal)) {
            body.put("Item" , new Fish(fishToCatchType, Quantity.Normal));
        }
        else {
            if (quantity == Quantity.Silver) {
                body.put("Item" , new Fish(fishToCatchType, Quantity.Silver));
            } else {
                body.put("Item" , new Fish(fishToCatchType, Quantity.Iridium));
            }
        }
        body.put("amount" , 1);
        body.put("Player" , Main.getClient().getPlayer());
        Main.getClient().getRequests().add(new Message(CommandType.CHANGE_INVENTORY, body));

        HashMap<String , Object> body2 = new HashMap<>();
        body2.put("Ability", "Fishing");
        body2.put("times", 2.4);
        Main.getClient().getRequests().add(new Message(CommandType.CHANGE_ABILITY_LEVEL, body));
    }


    private void printTempFriend(User p) {

        Texture iconTexture;
        if (p.getGender().equalsIgnoreCase("man"))
            iconTexture = new Texture("Ariyo/Shane_Icon.png");
        else
            iconTexture = new Texture("Ariyo/Sandy_Icon.png");

        tempFriend.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(iconTexture));

        tempFriend.setVisible(true);
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
                Result result = eatFood(Main.getClient().getPlayer().currentItem.getName());
                showTimedDialog(result.massage(), 2f);
                Main.getClient().getPlayer().currentItem = null;
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

    private void showUnseenMessages() {
        if (!Main.getClient().getPlayer().isShowUnseenChats())
            return;

        StringBuilder message = new StringBuilder();
        message.append("YOU HAVE UNSEEN MESSAGES\n\n");

        List<MessageHandling> list = new ArrayList<>();

        for (Map.Entry<Set<User>, List<MessageHandling>> c: Main.getClient().getLocalGameState().conversations.entrySet()) {
            for (MessageHandling m: c.getValue()) {
                if (m.contains(Main.getClient().getPlayer()) && !m.isSeen()) {
                    m.setSeen(true);

                    list.add(m);

                    message.append(m.getSender().getUsername()).append(" -> ").append(m.getReceiver().getUsername()).append(" :\n").append(m.getText()).append("\n\n");
                }
            }
        }

        if (list.isEmpty()) {
            return;
        }

        String result = message.toString();


        final Dialog dialog = new Dialog("", newSkin);
        dialog.setModal(true);
        dialog.setMovable(true);
        Label title = new Label("", newSkin);
        dialog.getContentTable().add(title).padBottom(8).row();
        final TextArea textArea = new TextArea(result, newSkin);
        textArea.setPrefRows(6);
        dialog.getContentTable().add(textArea).width(400).height(150).padBottom(8).row();
        TextButton okButton = new TextButton("OK", newSkin);
        okButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                HashMap<String, Object> body = new HashMap<>();
                body.put("Player", Main.getClient().getPlayer());
                body.put("List", list);
                Main.getClient().getRequests().add(new Message(CommandType.SET_SEEN_MESSAGE, body));

                dialog.remove();
            }
        });
        dialog.key(com.badlogic.gdx.Input.Keys.ESCAPE, true);
        dialog.show(stage);
        dialog.pack();
        dialog.setPosition(
            (Gdx.graphics.getWidth() - dialog.getWidth()) / 2f,
            (Gdx.graphics.getHeight() - dialog.getHeight()) / 2f
        );


        Main.getClient().getPlayer().setShowUnseenChats(false);
    }
    private void showHugged() {
        if (!Main.getClient().getPlayer().isShowHugged())
            return;

        hugImage.addAction(Actions.sequence(
            Actions.alpha(0f),
            Actions.fadeIn(0.3f),
            Actions.delay(1f),
            Actions.fadeOut(0.5f)
        ));
        sixtyPlusLabel.addAction(Actions.sequence(
            Actions.alpha(0f),
            Actions.fadeIn(0.3f),
            Actions.delay(1f),
            Actions.fadeOut(0.5f)
        ));

        Main.getClient().getPlayer().setShowHugged(false);
    }

    private void showGivenFlower() {
        if (!Main.getClient().getPlayer().isShowFlowered())
            return;

        bouquetImage.addAction(Actions.sequence(
            Actions.alpha(0f),
            Actions.fadeIn(0.3f),
            Actions.delay(1f),
            Actions.fadeOut(0.5f)
        ));


        onePlusLabel.addAction(Actions.sequence(
            Actions.alpha(0f),
            Actions.fadeIn(0.3f),
            Actions.delay(1f),
            Actions.fadeOut(0.5f)
        ));

        Main.getClient().getPlayer().setShowFlowered(false);
    }

    private Dialog makingInteractionDialog() {
        Dialog interactionDialog = new Dialog("", newSkin);
        dialogActivated = true;
        User me = Main.getClient().getPlayer();

        User p = Main.getClient().getPlayer().getFriendCloseToMe();
        if (p == null) return interactionDialog;


        // ساختن دکمه‌ها دستی
        TextButton flowerButton = new TextButton("Send Flower", newSkin);
        flowerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Result result;
                if (ClientWorkController.getInstance().getFriendship(me, p) != null) {
                    interactionDialog.remove();
                    dialogActivated = false;

                    result = giveFlowers(p.getUsername());
                    if (result.IsSuccess()) {
                        bouquetImage.addAction(Actions.sequence(
                            Actions.alpha(0f),
                            Actions.fadeIn(0.3f),
                            Actions.delay(1f),
                            Actions.fadeOut(0.5f)
                        ));


                        if (ClientWorkController.getInstance().getFriendship(me, p) != null && ClientWorkController.getInstance().getFriendship(me, p).getLevel() < 3)
                            onePlusLabel.addAction(Actions.sequence(
                                Actions.alpha(0f),
                                Actions.fadeIn(0.3f),
                                Actions.delay(1f),
                                Actions.fadeOut(0.5f)
                            ));
                    } else {
                        showTimedDialog(result.massage(), 2f);
                    }
                }
            }
        });

        TextButton hugButton = new TextButton("Hug", newSkin);
        hugButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                HashMap<String, Object> body = new HashMap<>();
                Main.getClient().getRequests().add(new Message(CommandType.FriendshipsInquiry, body));

                Result result;
                if (ClientWorkController.getInstance().getFriendship(me, p) != null) {
                    interactionDialog.remove();
                    dialogActivated = false;

                    result = hug(p.getUsername());
                    if (result.IsSuccess()) {
                        hugImage.addAction(Actions.sequence(
                            Actions.alpha(0f),
                            Actions.fadeIn(0.3f),
                            Actions.delay(1f),
                            Actions.fadeOut(0.5f)
                        ));
                        sixtyPlusLabel.addAction(Actions.sequence(
                            Actions.alpha(0f),
                            Actions.fadeIn(0.3f),
                            Actions.delay(1f),
                            Actions.fadeOut(0.5f)
                        ));

                    } else {
                        showTimedDialog(result.massage(), 2f);
                    }
                }
            }
        });

        TextButton talkButton = new TextButton("Talk", newSkin);
        talkButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                HashMap<String, Object> body = new HashMap<>();
                Main.getClient().getRequests().add(new Message(CommandType.FriendshipsInquiry, body));

                talking(p.getUsername(), result -> {
                    showTimedDialog(result.massage(), 2f);
                });
            }
        });

        TextButton proposeButton = new TextButton("Propose", newSkin);
        proposeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Result result;
                if (ClientWorkController.getInstance().getFriendship(me, p) != null) {
                    result = propose(p.getUsername());
                    if (result.IsSuccess()) {
                        ringImage.addAction(Actions.sequence(
                            Actions.alpha(0f),
                            Actions.fadeIn(0.3f),
                            Actions.delay(1.3f),
                            Actions.fadeOut(0.5f)
                        ));
                    } else {
                        showTimedDialog(result.massage(), 2f);
                    }
                }
            }
        });

        TextButton back = new TextButton("Back", newSkin);
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                interactionDialog.remove();
                dialogActivated = false;
            }
        });


        // اضافه کردن دکمه‌ها به دیالوگ
        Table buttonTable = interactionDialog.getButtonTable();
        buttonTable.pad(10);
        buttonTable.add(flowerButton).pad(5);
        buttonTable.row();
        buttonTable.add(hugButton).pad(5);
        buttonTable.row();
        buttonTable.add(talkButton).pad(5);
        buttonTable.row();
        buttonTable.add(proposeButton).pad(5);
        buttonTable.row();
        buttonTable.row();
        buttonTable.add(back).pad(20);

        return interactionDialog;
    }

    private void giftingInventory() {
        if (giftingFriendship == null)
            return;

        if (giftingFriendship.getPlayer1().getUsername().equals(Main.getClient().getPlayer().getUsername())) {
            sendGifts(giftingFriendship, giftingFriendship.getPlayer1().getUsername());
        }
        else if (giftingFriendship.getPlayer2().getUsername().equals(Main.getClient().getPlayer().getUsername())) {
            sendGifts(giftingFriendship, giftingFriendship.getPlayer2().getUsername());
        }
        Main.getClient().getPlayer().currentItem = null;
        giftingFriendship = null;
    }
    public Dialog makingFriendDialog() {
        friendsListdialog = new Dialog("", newSkin);
        friendsListdialog.setModal(true);
        friendsListdialog.setMovable(true);
        dialogActivated = true;
        Table friendTable = new Table();
        friendTable.top().left().pad(10);


        String targetName = Main.getClient().getPlayer().getUsername();
        User friendUser;

        for (HumanCommunications f : Main.getClient().getLocalGameState().friendships) {
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
            Label nameLabel = new Label(friendUser.getNickname(), Main.getSkin());
            nameLabel.setColor(Color.CYAN);

            // ستون 3: XP و Level
            Label statsLabel = new Label("   Level " + f.getLevel(), Main.getSkin());
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
                    giftingFriendship = f;
                    createInventory();
                    dialogActivated = false;
                    giftingInventory();
                }
            });
            giftButton.addListener(new InputListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    giftButton.setColor(1, 1, 1, 0.5f);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    giftButton.setColor(1, 1, 1, 1f);
                }
            });

            System.out.println(f.printInfo());

            // اضافه کردن به ردیف جدول
            friendTable.row().pad(5);
            friendTable.add(avatar).size(32, 32).padRight(10);
            friendTable.add(nameLabel).width(100).left().padRight(10);
            friendTable.add(statsLabel).width(150).left().padRight(10);
            friendTable.add(giftButton).width(80).right();
        }

        ScrollPane scrollPane = new ScrollPane(friendTable, Main.getSkin()); // در صورت زیاد بودن
        scrollPane.setFadeScrollBars(false);

        friendsListdialog.getContentTable().add(scrollPane).width(450).height(250).pad(10);
        friendsListdialog.button("CLOSE").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialogActivated = false;
            }
        });

        return friendsListdialog;
    }

    public void showTimedDialog(String message, float durationSeconds) {
        activeDialog = new Dialog("", newSkin);
        Label.LabelStyle whiteStyle = new Label.LabelStyle(newSkin.get(Label.LabelStyle.class));
        whiteStyle.fontColor = Color.WHITE;

        Label label = new Label(message, whiteStyle);
        activeDialog.getContentTable().add(label).pad(10);
        activeDialog.pack();
        activeDialog.setPosition(
            (Gdx.graphics.getWidth() - activeDialog.getWidth()) / 2,
            (Gdx.graphics.getHeight() - activeDialog.getHeight()) / 2
        );

        activeDialog.show(stage);

        dialogExpirationTime = TimeUtils.millis() + (long) (durationSeconds * 1000);
    }


        //  //  //  //   Erfan
    private void initialize() {
        if (gameMenuInitialized)
            return;
        gameMenuInitialized = true;

        currentMenu = Menu.GameMenu;

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
        tempFriend.setVisible(false);
        tempFriend.setPosition(70F, (float) ((float) Gdx.graphics.getHeight() *7.5/9));
        stage.addActor(tempFriend);

        tempFriend.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                makingInteractionDialog().show(stage);
            }
        });
        tempFriend.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                tempFriend.setColor(1, 1, 1, 0.5f);
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                tempFriend.setColor(1, 1, 1, 1f);
            }
        });


        Texture iconT = new Texture("all image/Farming/Fishing.png");
        Drawable iconDrawableT = new TextureRegionDrawable(new TextureRegion(iconT));
        tempFishing = new ImageButton(iconDrawableT);
        tempFishing.setVisible(false);
        tempFishing.setPosition(1100, 500);


        energyLabel = new Label("Energy : 100", newSkin);
        lastHealth = -1;
        energyLabel = new Label("Energy : 100", energyStyle);
        energyLabel.setPosition((float) Gdx.graphics.getWidth() - energyLabel.getWidth() - 10, 10);
        stage.addActor(energyLabel);

        shapeRenderer = new ShapeRenderer();


        friendsListTexture = new Texture(Gdx.files.internal("Ariyo/Friendship_101.png"));
        buttonDrawable = new TextureRegionDrawable(new TextureRegion(friendsListTexture));
        if (friendButton != null) {
            friendButton.remove();
        }
        friendButton = new ImageButton(buttonDrawable);
        friendButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                HashMap<String, Object> body = new HashMap<>();
                Main.getClient().getRequests().add(new Message(CommandType.FriendshipsInquiry, body));
                makingFriendDialog().show(stage);
            }
        });
        friendButton.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                friendButton.setColor(1, 1, 1, 0.5f);
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                friendButton.setColor(1, 1, 1, 1f);
            }
        });
        float screenHeight = Gdx.graphics.getHeight();
        friendButton.setPosition(
            Gdx.graphics.getWidth() / 2f - friendButton.getWidth() / 2f,
            Gdx.graphics.getHeight() / 2f - friendButton.getHeight() / 2f
        );
        friendButton.setPosition(50,screenHeight * 8f / 9f);
        friendButton.pack();

        stage.addActor(friendButton);

        bouquetImage = new Image(new Texture(Gdx.files.internal("Ariyo/Bouquet.png")));
        bouquetImage.setPosition(Gdx.graphics.getWidth() / 2f - bouquetImage.getWidth(),
            Gdx.graphics.getHeight() / 2f - bouquetImage.getHeight() / 2f);
        bouquetImage.getColor().a = 0f;
        bouquetImage.setSize(bouquetImage.getWidth() * 3, bouquetImage.getHeight() * 3);
        stage.addActor(bouquetImage);

        hugImage = new Image(new Texture(Gdx.files.internal("Ariyo/hug.png")));
        hugImage.setPosition(Gdx.graphics.getWidth() / 2f - hugImage.getWidth(), Gdx.graphics.getHeight() / 2f - hugImage.getHeight() / 2f);
        hugImage.setSize(hugImage.getWidth(), hugImage.getHeight());
        hugImage.getColor().a = 0f;
        stage.addActor(hugImage);


        Label.LabelStyle whiteStyle = new Label.LabelStyle(newSkin.get(Label.LabelStyle.class));
        whiteStyle.fontColor = Color.GREEN;
        sixtyPlusLabel = new Label("+60 XP", whiteStyle);
        sixtyPlusLabel.setPosition(Gdx.graphics.getWidth() / 2f - hugImage.getWidth()/2, Gdx.graphics.getHeight() / 2f);
        sixtyPlusLabel.setSize(sixtyPlusLabel.getWidth()*2, sixtyPlusLabel.getHeight()*2);
        sixtyPlusLabel.getColor().a = 0f;
        stage.addActor(sixtyPlusLabel);

        onePlusLabel = new Label("+1 Level", whiteStyle);
        onePlusLabel.setPosition(Gdx.graphics.getWidth()/2f -  onePlusLabel.getWidth()/2, Gdx.graphics.getHeight() / 2f + bouquetImage.getHeight()/2f);
        onePlusLabel.setSize(onePlusLabel.getWidth()*2, onePlusLabel.getHeight()*2);
        onePlusLabel.getColor().a = 0f;
        stage.addActor(onePlusLabel);

        ringImage = new Image(new Texture(Gdx.files.internal("Ariyo/Sturdy_Ring.png")));
        ringImage.setPosition(Gdx.graphics.getWidth() / 2f - ringImage.getWidth(), Gdx.graphics.getHeight() / 2f - ringImage.getHeight() / 2f);
        ringImage.setSize(ringImage.getWidth() * 3, ringImage.getHeight() * 3);
        ringImage.getColor().a = 0f;
        stage.addActor(ringImage);


        timeLabel = new Label("", Main.getSkin());
        dateLabel = new Label("", Main.getSkin());
        moneyLabel = new Label("", Main.getSkin());
        weekDayLabel = new Label("", Main.getSkin());


        showInformationIsActivated = false;
        informationIsActivated = false;
        socialMenuIsActivated = false;
        toolsMenuIsActivated = false;
        inventoryIsActivated = false;
        skillMenuIsActivated = false;
        EscMenuIsActivated = false;
        subMenuIsActivated = false;
        questsIsActivated = false;
        mapIsActivated = false;
        startRotation = false;

        //lastDateHour = Main.getClient().getLocalGameState().currentDate.clone();

        //Mohamadreza
        initializePlayer();
        gameState = Main.getClient().getLocalGameState();

        lastDateHour = new DateHour(Season.Spring, 1, 9, 1950);
        gameState.currentDate = new DateHour(Season.Spring, 1, 9, 1950);
    }

    private void inputController() {

        if (!anyMenuIsActivated()) {

            if (Gdx.input.isKeyJustPressed(Keys.ToolsMenu))
                createToolsMenu();
            else if (Gdx.input.isKeyJustPressed(Keys.EscMenu))
                createEscMenu();
            else if (Gdx.input.isKeyJustPressed(Keys.increaseTime))
                controller.sendPassedTimeMessage(0 , 1);
            else if (Gdx.input.isKeyJustPressed(Keys.lighting))
                createCloud();
            else if (Gdx.input.isKeyJustPressed(Keys.energySet))
                createCheatEnergyMenu();
            else if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) // TODO
                handleLeftClick();
            else if (Gdx.input.isKeyJustPressed(Keys.informationMenu))
                showInformationMenu();
            else if (Gdx.input.isKeyJustPressed(Keys.settingMenu))
                showSettingMenu();
            else if (Gdx.input.isKeyJustPressed(Keys.NPCMenu))
                showNPCMenu();


            else if (Gdx.input.isKeyJustPressed(Input.Keys.H)) {
                Main.getMain().setScreen(
                    new TransitionScreen(
                        Main.getMain(),
                        this,
                        new HomeMenu(),
                        1f
                    )
                );
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.K)) {
//                User temp = Main.getClient().getPlayer();
//                ArrayList<User> list = currentGame.getGameState().getPlayers();
//                if (temp.getUsername().equals(list.get(list.size() - 1).getUsername())) {
//                    Main.getClient().setPlayer(list.get(0));
//                    return;
//                }
//                boolean found = false;
//                for (User user : list) {
//                    if (found) {
//                        Main.getClient().setPlayer(user);
//                        return;
//                    }
//                    if (user.getUsername().equals(temp.getUsername())) {
//                        found = true;
//                    }
//                }
            }


        } else if (inventoryIsActivated) {

            if (Gdx.input.isKeyJustPressed(Keys.EscMenu))
                ExitOfMenu();
            else if (Gdx.input.isKeyJustPressed(Keys.delete) && Main.getClient().getPlayer().currentItem != null) {
                GameControllerLogic.advanceItem(
                    Main.getClient().getPlayer().currentItem,
                    -Main.getClient().getPlayer().getBackPack().inventory.Items.get(Main.getClient().getPlayer().currentItem));
                ExitOfMenu();
                createInventory();
            }
        } else if (Gdx.input.isKeyJustPressed(Keys.EscMenu))
            ExitOfMenu();
    }

    private void ExitOfMenu() {

        if (toolsMenuIsActivated) {
            helperBackGround.remove();
            toolsPopup.remove();
            toolsMenuIsActivated = false;
        } else if (inventoryIsActivated) {
            inventoryPopup.remove();
            inventoryIsActivated = false;
        } else if (skillMenuIsActivated) {
            skillPopup.remove();
            skillMenuIsActivated = false;
        } else if (socialMenuIsActivated) {
            socialPopup.remove();
            socialMenuIsActivated = false;
        } else if (mapIsActivated) {
            mapGroup.remove();
            mapIsActivated = false;
        } else if (setEnergyIsActivated) {
            setEnergyPopup.remove();
            setEnergyIsActivated = false;
        } else if (EscMenuIsActivated) {
            helperBackGround.remove();
            EscPopup.remove();
            EscMenuIsActivated = false;
        } else if (settingIsActivated) {
            settingMenuGroup.remove();
            helperBackGround.remove();
            settingIsActivated = false;
        } else if (NPCMenuIsActivated) {
            NPCMenuPopup.remove();
            helperBackGround.remove();
            NPCMenuIsActivated = false;
        }

    }

    private void giftNPCMenu() {

        if (waitingForGiftItemSelection && !inventoryIsActivated) {

            if (Main.getClient().getPlayer().currentItem != null)
                controller.giftNPC(Main.getClient().getPlayer().currentItem, stage, npc);
            else {
                Dialog dialog = Marketing.getInstance().createDialogError();
                final Label tooltipLabel = new Label("Firs you must select Item", newSkin);
                tooltipLabel.setColor(Color.LIGHT_GRAY);

                Marketing.getInstance().addDialogToTable(dialog, tooltipLabel, GameMenu.getInstance());
                com.badlogic.gdx.utils.Timer.schedule(new com.badlogic.gdx.utils.Timer.Task() {
                    @Override
                    public void run() {
                        dialog.remove();
                    }
                }, 3);
            }
            waitingForGiftItemSelection = false;
        }
    }
    private void showNPCMenu() {

        if (NPCMenuIsActivated) {
            NPCMenuIsActivated = false;
            NPCMenuPopup.remove();
            helperBackGround.remove();
        } else {

            npc = controller.findNPC();

            if (npc == null)
                return;

            createGrayBackGround();
            NPCMenuIsActivated = true;

            NPCMenuPopup = new Window("", newSkin);
            NPCMenuPopup.setMovable(false);
            NPCMenuPopup.pad(20);
            NPCMenuPopup.defaults().width(200).pad(10);

            TextButton talkButton = new TextButton("Meet", newSkin);
            TextButton giftButton = new TextButton("Gift", newSkin);
            TextButton backButton = new TextButton("Back", newSkin);
            TextButton questsButton = new TextButton("Quests", newSkin);
            TextButton friendshipButton = new TextButton("Friend Level", newSkin);

            NPCMenuPopup.add(talkButton);
            NPCMenuPopup.add(giftButton).row();
            NPCMenuPopup.add(friendshipButton);
            NPCMenuPopup.add(questsButton).row();
            NPCMenuPopup.add(backButton).width(150).colspan(2).row();

            NPCMenuPopup.pack();
            NPCMenuPopup.setPosition(
                (stage.getWidth() - NPCMenuPopup.getWidth()) / 2,
                (stage.getHeight() - NPCMenuPopup.getHeight()) / 2
            );

            stage.addActor(NPCMenuPopup);

            backButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    NPCMenuPopup.remove();
                    NPCMenuIsActivated = false;
                    helperBackGround.remove();
                }
            });

            talkButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (controller.checkForNPC())
                        controller.meetNPC(stage, npc);
                }
            });

            giftButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (controller.checkForNPC()) {
                        createInventory();
                        waitingForGiftItemSelection = true;
                    }
                }
            });

            friendshipButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    createSocialMenu();
                }
            });

            questsButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    createQuestMenu();
                }
            });
        }
    }

    private void createQuestMenu () {
        socialPopup = new Window("", newSkin);
        socialPopup.setSize(650, 900);
        socialPopup.setPosition(
            (stage.getWidth() - socialPopup.getWidth()) / 2,
            (stage.getHeight() - socialPopup.getHeight()) / 2);

        Table content = new Table();
        createDetailQuest(content);

        ScrollPane scrollPane = new ScrollPane(content, newSkin);
        scrollPane.setFadeScrollBars(false);

        socialPopup.add(scrollPane).expand().fill();

        stage.addActor(socialPopup);
        socialMenuIsActivated = true;
    }

    private void createDetailQuest (Table content) {

        content.defaults().pad(5);
        content.setFillParent(false);
        content.row();

        Label questLabel = new Label(controller.questsNPCList(), newSkin);

        content.add(questLabel);
        content.row();
    }


    private void showSettingMenu() {

        createGrayBackGround();
        settingIsActivated = true;

        settingMenuGroup = new Window("", newSkin);
        settingMenuGroup.setSize(320, 200);
        settingMenuGroup.setPosition(
            (stage.getViewport().getWorldWidth() - settingMenuGroup.getWidth()) / 2,
            (stage.getViewport().getWorldHeight() - settingMenuGroup.getHeight()) / 2
        );

        TextButton exitButton = new TextButton("Exit Game", newSkin);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // TODO
            }
        });

        TextButton voteKickButton = new TextButton(" Vote to Kick Player", newSkin);
//        voteKickButton.setDisabled(!Main.getClient().getPlayer().equals(Main.getClient().getPlayer()));

        voteKickButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (true/*!Main.getClient().getPlayer().equals(currentUser)*/) return;

                Window kickWindow = new Window("Select Player to Kick", newSkin);
                kickWindow.setSize(300, 250);
                kickWindow.setPosition(
                    (stage.getViewport().getWorldWidth() - kickWindow.getWidth()) / 2,
                    (stage.getViewport().getWorldHeight() - kickWindow.getHeight()) / 2
                );

                VerticalGroup playersList = new VerticalGroup();
                playersList.space(10);
                playersList.pad(10);
                playersList.top();

//                for (User player : currentGame.getGameState().getPlayers()) {
//                    if (player.equals(Main.getClient().getPlayer())) continue;
//
//                    TextButton playerButton = new TextButton(player.getNickname(), newSkin);
//                    playerButton.addListener(new ClickListener() {
//                        @Override
//                        public void clicked(InputEvent event, float x, float y) {
//                            // اینجا حذف واقعی رو انجام بده
//                            currentGame.getGameState().getPlayers().remove(player);
//                            kickWindow.remove(); // یا hide()
//                        }
//                    });
//                    playersList.addActor(playerButton);
//                }

                ScrollPane scrollPane = new ScrollPane(playersList, newSkin);
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

        Skin skin = newSkin;
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

        Skin skin = newSkin;
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


        Label descriptionLabel = new Label(description, newSkin);
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

        TextButton backButton = new TextButton("Back", newSkin);
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

        int currentHealth = Main.getClient().getPlayer().getHealth();
        if (currentHealth != lastHealth) {

            energyLabel.setText("Energy : " + Main.getClient().getPlayer().getHealth());
            Label.LabelStyle style = energyLabel.getStyle();

            if (Main.getClient().getPlayer().getHealth() <= 20)
                style.fontColor = Color.RED;
            else
                style.fontColor = Color.GREEN;

            energyLabel.setStyle(style);
        }
    }

    private void handleLeftClick () {
        useCurrentItem();
        Direction direction = Direction.getDirByCord(
            Main.getClient().getPlayer().getPositionX(),
            90 - Main.getClient().getPlayer().getPositionY(),
            getVector().x, 90 - getVector().y
        );
        if (direction != null) {

            int dir = 0;
            Items items = Main.getClient().getPlayer().currentItem;
            switch (direction) {

                case Up -> dir = 3;
                case Right -> dir = 1;
                case Down -> dir = 7;
                case Left -> dir = 5;
            }
            if (dir != 0) {
                if (items instanceof Tools) {
                    Main.getClient().getPlayer().currentTool = (Tools) items;
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

        setEnergyPopup = new Window("", newSkin);
        setEnergyPopup.setSize(400, 300);
        setEnergyPopup.setPosition(
            (stage.getViewport().getWorldWidth() - setEnergyPopup.getWidth()) / 2,
            (stage.getViewport().getWorldHeight() - setEnergyPopup.getHeight()) / 2
        );
        setEnergyPopup.setMovable(false);

        energyInputField = new TextField("", newSkin);
        energyInputField.setMessageText("       Amount");
        energyInputField.setTextFieldFilter(new TextField.TextFieldFilter.DigitsOnlyFilter());


        confirmButton = new TextButton("OK", newSkin);
        confirmButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String inputText = energyInputField.getText();
                if (!inputText.isEmpty()) {
                    try {
                        int energy = Integer.parseInt(inputText);

                        Main.getClient().getPlayer().setHealth(energy);

                        setEnergyPopup.remove();
                        setEnergyIsActivated = false;

                    } catch (NumberFormatException e) {
                    }
                }
            }
        });

        setEnergyPopup.defaults().pad(10);
        setEnergyPopup.row();
        setEnergyPopup.add(new Label("Input energy amount", newSkin)).padTop(20);
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
        Items currentItem = Main.getClient().getPlayer().currentItem;

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
                    Main.getClient().getPlayer().currentItem = null;
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

        skillPopup = new Window("", newSkin);
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
        final Label tooltipLabel = new Label(skill.getDiscription(), newSkin);
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



        timeLabel.setText(Main.getClient().getLocalGameState().currentDate.getHour() + ":00");
        dateLabel.setText(Main.getClient().getLocalGameState().currentDate.getDate());
        weekDayLabel.setText(Main.getClient().getLocalGameState().currentDate.getDayOfTheWeek().name());

        String moneyStr = String.valueOf(Main.getClient().getPlayer().getMoney());
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


        seasonImage = new Image(TextureManager.get(Main.getClient().getLocalGameState().currentDate.getSeason().getIconPath()));
        seasonImage.setSize(200,220);
        seasonImage.setPosition(image.getWidth()- seasonImage.getWidth() + 47, image.getHeight() - seasonImage.getHeight() + 30);
        clockGroup.addActor(seasonImage);

        weatherImage = new Image(TextureManager.get(Main.getClient().getLocalGameState().currentWeather.getIconPath()));
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
    private void updateClock() {

        timeLabel.setText(Main.getClient().getLocalGameState().currentDate.getHour() + ":00");
        dateLabel.setText(Main.getClient().getLocalGameState().currentDate.getDate());
        weekDayLabel.setText(Main.getClient().getLocalGameState().currentDate.getDayOfTheWeek().name());
        Texture newTexture = TextureManager.get(Main.getClient().getLocalGameState().currentDate.getSeason().getIconPath());
        seasonImage.setDrawable(new TextureRegionDrawable(new TextureRegion(newTexture)));
        Texture newTexture1 = TextureManager.get(Main.getClient().getLocalGameState().currentWeather.getIconPath());
        weatherImage.setDrawable(new TextureRegionDrawable(new TextureRegion(newTexture1)));

        lastDateHour = Main.getClient().getLocalGameState().currentDate.clone();
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

        Items currentItem = Main.getClient().getPlayer().currentItem;
        if (currentItem == null) return;

        Direction direction = Main.getClient().getPlayer().getDirection();
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
        Items currentItem = Main.getClient().getPlayer().currentItem;

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
            return (90 - Main.getClient().getPlayer().getPositionY()) * TEXTURE_SIZE + 12;

        return (90 - Main.getClient().getPlayer().getPositionY()) * TEXTURE_SIZE + 8;
    }
    private float getXForHands(Direction direction) {

        return switch (direction) {
            case Right -> Main.getClient().getPlayer().getPositionX() * TEXTURE_SIZE + 20;
            case Left -> Main.getClient().getPlayer().getPositionX() * TEXTURE_SIZE - 10;
            case Up -> Main.getClient().getPlayer().getPositionX() * TEXTURE_SIZE + 25;
            case Down -> Main.getClient().getPlayer().getPositionX() * TEXTURE_SIZE + 23;
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

        Inventory inventory = Main.getClient().getPlayer().getBackPack().inventory;

        int number = 0;

        for (Map.Entry<Items, Integer> entry : inventory.Items.entrySet()) {

            if (number % 6 == 0)
                content.row();

            Items item = entry.getKey();
            int count = entry.getValue();

            Image itemButton = new Image(new Texture(item.getInventoryIconPath()));

            Items currentItem = Main.getClient().getPlayer().currentItem;
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
                        Main.getClient().getPlayer().currentItem = null;
                    else
                        controller.itemEquip(item.getName());

                    if (helperBackGround == null) {
                        helperBackGround = new Image(new TextureRegionDrawable(new TextureRegion(TextureManager.get("Erfan/grayPage.jpg"))));
                        helperBackGround.setColor(0, 0, 0, 0.5f);
                        helperBackGround.setSize(stage.getWidth(), stage.getHeight());
                        stage.addActor(helperBackGround);
                    }

                    helperBackGround.remove();
                    inventoryPopup.remove();
                    inventoryIsActivated = false;


                    giftingInventory();
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
        if (Main.getClient().getPlayer().currentItem != null)
            img = new Image(TextureManager.get(Main.getClient().getPlayer().currentItem.getInventoryIconPath()));
        else
            img = new Image(TextureManager.get("Erfan/Cancel2.png"));

        content.add(img).align(Align.topRight).width(150).height(150).right();
        content.row();
    }

    private void createSocialMenu () {

        socialPopup = new Window("", newSkin);
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

        for (NPC npc1 : NPC.values()) {

            content.row();
            content.add(new Image(new TextureRegionDrawable(new TextureRegion(TextureManager.get(npc1.getIconPath())))));

            Label nameLabel = new Label("Friendship Level with " + npc1.getName() +
                " : " + Main.getClient().getPlayer().getFriendshipLevel(npc1), newSkin);

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
            skillMenuIsActivated || mapIsActivated ||
            NPCMenuIsActivated || questsIsActivated ||
            skillMenuIsActivated || mapIsActivated ||
            dialogActivated ||
            Main.getClient().getPlayer().isFishing || Main.getClient().getPlayer().doingMinigame;
    }



     // // // // // // // // // // // // // // // // // // // // // // // Mohammad Reza




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
        if (Main.getClient().getCurrentMenu().getMenu() instanceof GameMenu) {
            camera.unproject(vector);
        }
        if (Main.getClient().getCurrentMenu().getMenu() instanceof MarketMenu) {
            return MarketMenu.getVector();
        }
        return vector;
    }
    public ArrayList<UserRenderer> getUserRenderer() {
        return userRenderers;
    }
    public ArrayList<AnimalRenderer> getAnimalRenderers() {
        return animalRenderers;
    }
    public void changeMenu() {
        if (Main.getClient().getCurrentMenu() != Menu.GameMenu) {
            System.out.println("yes");
            Main.getMain().setScreen(Main.getClient().getCurrentMenu().getScreen());
        }
    }
    public ArrayList<CraftingRenderer> getCraftingRenderers() {
        if (craftingRenderers == null) {
            craftingRenderers = new ArrayList<>();
        }
        return craftingRenderers;
    }
    public Sprite getWithMouse() {
        if (withMouse == null) {
            withMouse = new Sprite();
        }
        return withMouse;
    }
    public void setWithMouse(Sprite withMouse) {
        this.withMouse = withMouse;
    }
    public void initialLake() {
        if (Main.getClient().getLocalGameState().getChooseMap()) {
            if (! initialLake) {
                for (Tile tile : Main.getClient().getLocalGameState().bigMap) {
                    if (tile.getGameObject() instanceof Lake) {
                        lakeRenderers.add(new LakeRenderer((Lake) tile.getGameObject() , tile.getX() , tile.getY()));
                    }
                }
                initialLake = true;
            }
        }
    }
    public ArrayList<LakeRenderer> getLakeRenderers () {
        return lakeRenderers;
    }
    public ArrayList<AnimalRenderer> getCurrentBarnOrCageAnimals() {
        return currentBarnOrCageAnimals;
    }
    public void createUserRenderes() {
        if (userRenderers == null) {
            userRenderers = new ArrayList<UserRenderer>();
            for (User player : Main.getClient().getLocalGameState().getPlayers()) {
                UserRenderer userRenderer = new UserRenderer();
                userRenderer.addToAnimations(Direction.Up , player.getUp());
                userRenderer.addToAnimations(Direction.Down , player.getDown());
                userRenderer.addToAnimations(Direction.Left , player.getLeft());
                userRenderer.addToAnimations(Direction.Right , player.getRight());
                player.setInFarmExterior(true);
                userRenderers.add(userRenderer);
            }

            controller.setPlayerPosition();
        }
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
        if (stage == null) {
            stage = new Stage(new ScreenViewport());
        }
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
//            Main.getClient().getPlayer().increaseMoney(10000);
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
