package com.Graphic.View.GameMenus;

import com.Graphic.Controller.MainGame.CraftingController;
import com.Graphic.Controller.MainGame.HomeController;
import com.Graphic.Main;
import com.Graphic.View.AppMenu;
import com.Graphic.View.AppView;
import com.Graphic.model.*;
import com.Graphic.model.Enum.Fish.FishType;
import com.Graphic.model.Enum.FoodTypes;
import com.Graphic.model.Enum.HouseModes;
import com.Graphic.model.Enum.ItemType.*;
import com.Graphic.model.HelpersClass.Result;
import com.Graphic.model.HelpersClass.TextureManager;
import com.Graphic.model.OtherItem.Fridge;
import com.Graphic.model.Places.MarketItem;
import com.Graphic.model.Plants.Animalproduct;
import com.Graphic.model.Plants.Fish;
import com.Graphic.model.Plants.Food;
import com.Graphic.model.ToolsPackage.Tools;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.utils.StringBuilder;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.HashMap;
import java.util.Map;

import static com.Graphic.Controller.MainGame.HomeController.*;
import static com.Graphic.Main.newSkin;
import static com.Graphic.View.GameMenus.GameMenu.helperBackGround;
import static com.Graphic.model.App.*;
//import static com.Graphic.model.Recipe.createAllRecipes;

public class HomeMenu extends AppView implements AppMenu, Screen {
    private static boolean test = false;

    int startX, startY;
    int areaWidth;
    int areaHeight;

    Stage stage;
    private Dialog activeDialog = null;
    private long dialogExpirationTime = 0;
    TextureRegion borderTexture;
    Texture kitchen;
    Texture livingFloor;
    Texture bedroomFloor;
    Texture bedroomWall;
    Texture craftShop;
    Image fridge;
    Texture chest;
    Texture bench;
    Texture carpet;
    Texture furniture;
    Texture wndw;
    Texture recipePaper;
    Texture bed;
    Texture simpleTable;
    Texture chimney;
    Texture carpet2;
    Texture tree;
    Texture pool;
    Animation<TextureRegion> oldmanAnimation;
    float oldmanStateTime = 0f;
    Texture plant;
    Texture barrel;
    Texture deck;
    Texture lib;
    Texture torch;
    Texture board;
    Texture kitchenTable;
    Texture kitchenChair;
    Texture bedroomCarpet;

    Group cookingGroup;
    Group craftingGroup;


    private boolean fridgeIsActivated;
    private Window fridgePopup;
    private boolean inventoryIsActivated;
    private Window inventoryPopup;


    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;

    TiledMap roomMap;
    OrthogonalTiledMapRenderer roomRenderer;




    private HouseModes mode = HouseModes.home;

    public HouseModes getMode() {
        return mode;
    }
    public void setMode(HouseModes mode) {
        this.mode = mode;
    }

    CraftingController craftingController = new CraftingController();
    HomeController homeController=new HomeController();

    public void showRecipes () {
        TextureRegion recipePaper2 = new TextureRegion(recipePaper, 0, recipePaper.getHeight()/1000, recipePaper.getWidth(), (recipePaper.getHeight()*100)/512);
        Image recipePaperImage = new Image(recipePaper2);
        recipePaperImage.setPosition((float) (startX * 6) / 10, (float) (startY * 3) / 10);
        recipePaperImage.setSize(startX + areaWidth, startY + areaHeight);
        cookingGroup.addActor(recipePaperImage);


        int tileSize = 120;
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        int areaWidth = (int)(screenWidth * 0.75f);
        int areaHeight = (int)(screenHeight * 0.75f);
        areaWidth = (areaWidth / tileSize) * tileSize;
        areaHeight = (areaHeight / tileSize) * tileSize;
        int startX = (screenWidth - areaWidth) / 2;
        int startY = (screenHeight - areaHeight) / 2;

        Label letsCook = new Label("What Are You Cooking?", Main.getSkin());
        letsCook.setPosition((float) (startX + (float) areaWidth*2.2/7), startY + (float) (areaHeight * 11) /10);
        cookingGroup.addActor(letsCook);

        float x = 0, y = 0; // فرض بر اینه که قبلاً تعریف شده
        for (int i = 0; i < FoodTypes.values().length; i++) {
            FoodTypes f = FoodTypes.values()[i];
            Texture t = new Texture(Gdx.files.internal(f.getAddress()));
            Image image = new Image(t);
            Label l = new Label(f.getName(), Main.getSkin());

            boolean usable = Recipe.findRecipeByName(f.getName()).isUsable();

            // موقعیت تصویر
            float imageX = startX + x;
            float imageY = startY + (float) (areaHeight * 9) / 10 - y;
            image.setPosition(imageX, imageY);

            if (!usable) {
                image.setColor(1, 1, 1, 0.3f);
                l.setColor(Color.LIGHT_GRAY);
            } else {
                l.setColor(Color.DARK_GRAY);
            }


            image.addListener(new ClickListener() {
                private boolean longPressTriggered = false;
                private Timer.Task longPressTask;

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    longPressTriggered = false;

                    if (longPressTask != null) {
                        longPressTask.cancel();
                    }

                    longPressTask = Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            if (!longPressTriggered) {
                                longPressTriggered = true;
                                // Long Press
                                showThisOnesRecipe(f);
                            }
                        }
                    }, 0.5f);

                    return super.touchDown(event, x, y, pointer, button);
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    if (longPressTask != null) {
                        longPressTask.cancel();
                        longPressTask = null;
                    }

                    if (!longPressTriggered) {
                        // Simple Click
                        Result result = foodPrepare(f.getName());
                        showTimedDialog(result.massage(), 2f, stage);
                    }
                    super.touchUp(event, x, y, pointer, button);
                }

            });
            image.addListener(new InputListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    if (usable) {
                        image.setColor(0.3f, 0.3f, 0.3f, 0.3f);
                    }
                }
                public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    if (usable) {
                        image.setColor(1, 1, 1, 1f);
                    }
                }
            });

            // موقعیت لیبل
            l.setPosition(imageX, startY + (float) (areaHeight * 8.3) / 10 - y);
            l.setFontScale(0.5f);

            if (!usable) {
                image.setColor(0.5f, 0.5f, 0.5f, 0.6f); // رنگ خاکستری تیره
            }

            cookingGroup.addActor(image);
            cookingGroup.addActor(l);


            if (x < areaWidth * 7 / 10f) {
                x += 2 * tileSize;
            } else {
                x = 0;
                y += tileSize;
            }
        }

        stage.addActor(cookingGroup);
    }

    private void showThisOnesRecipe(FoodTypes f) {
        Recipe recipe = null;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(f.getName()).append("\n\n");
        java.util.List<Recipe> recipeList = null; //createAllRecipes();

        for (Recipe r : recipeList) {
            if (r.getName().equals(f.getName())) {
                recipe = r;
                break;
            }
        }

        if (recipe == null) return;

        HashMap<Items, Integer> ingredients = recipe.getIngredients();
        for (Map.Entry<Items, Integer> entry : ingredients.entrySet()) {
            Items item = entry.getKey();
            int count = entry.getValue();

            String itemName = item.getName();

            stringBuilder.append(itemName)
                .append(" : ")
                .append(count)
                .append("\n");
        }

        showTimedDialog(stringBuilder.toString(), 4f, stage);
    }

    private void showCraftings () {
        TextureRegion recipePaper2 = new TextureRegion(recipePaper, 0, recipePaper.getHeight()/1000, recipePaper.getWidth(), (recipePaper.getHeight()*100)/512);
        Image recipePaperImage = new Image(recipePaper2);
        recipePaperImage.setPosition((float) (startX * 6) / 10, (float) (startY * 3) / 10);
        recipePaperImage.setSize(startX + areaWidth, startY + areaHeight);
        craftingGroup.addActor(recipePaperImage);


        int tileSize = 120;
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        int areaWidth = (int)(screenWidth * 0.75f);
        int areaHeight = (int)(screenHeight * 0.75f);
        areaWidth = (areaWidth / tileSize) * tileSize;
        areaHeight = (areaHeight / tileSize) * tileSize;
        int startX = (screenWidth - areaWidth) / 2;
        int startY = (screenHeight - areaHeight) / 2;

        Label craftMenu = new Label("Craft Menu", Main.getSkin());
        craftMenu.setPosition((float) (startX + (float) areaWidth*2.5/7), startY + (float) (areaHeight * 11) /10);
        craftingGroup.addActor(craftMenu);

        float x = 0, y = 0;
        for (int i = 0; i < CraftType.values().length; i++) {
            CraftType c = CraftType.values()[i];
            Texture t = new Texture(Gdx.files.internal(c.getAddress()));
            Image image = new Image(t);
            Label l = new Label(c.getName(), Main.getSkin());

            CraftType type=null;
            for (CraftType craftType : CraftType.values()) {
                if (craftType.getName().equals(c.getName())) {
                    type = craftType;
                }
            }
            boolean usable;
            if (type == null) {
                continue;
            }
            if (!type.checkLevel()) {
                usable = false;
            } else {
                usable = true;
            }

            float imageX = startX + x;
            float imageY = startY + (float) (areaHeight * 9) / 10 - y;
            image.setPosition(imageX, imageY);

            if (!usable) {
                image.setColor(1, 1, 1, 0.3f);
                l.setColor(Color.LIGHT_GRAY);
            } else {
                l.setColor(Color.DARK_GRAY);
            }



            image.addListener(new ClickListener() {
                private boolean longPressTriggered = false;
                private Timer.Task longPressTask;

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    longPressTriggered = false;

                    if (longPressTask != null) {
                        longPressTask.cancel();
                    }

                    longPressTask = Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            if (!longPressTriggered) {
                                longPressTriggered = true;
                                // Long Press
                                Result result = craftingController.showCraftingRecipe(c);
                                showTimedDialog(result.massage(), 4f, stage);
                            }
                        }
                    }, 0.5f);

                    return super.touchDown(event, x, y, pointer, button);
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    if (longPressTask != null) {
                        longPressTask.cancel();
                        longPressTask = null;
                    }

                    if (!longPressTriggered) {
                        // Simple Click
                        Result result = craftingController.craftingCraft(c.getName());
                        showTimedDialog(result.massage(), 2f,  stage);
                    }
                    super.touchUp(event, x, y, pointer, button);
                }

            });
            image.addListener(new InputListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    if (usable) {
                        image.setColor(0.3f, 0.3f, 0.3f, 0.3f);
                    }
                }
                public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    if (usable) {
                        image.setColor(1, 1, 1, 1f);
                    }
                }
            });






            l.setPosition(imageX, startY + (float) (areaHeight * 8.3) / 10 - y);
            l.setFontScale(0.5f);
            image.setSize(image.getWidth(), image.getHeight()/1.2f);

            if (!usable) {
                image.setColor(0.5f, 0.5f, 0.5f, 0.6f);
            }


            craftingGroup.addActor(image);
            craftingGroup.addActor(l);

            if (x < areaWidth * 7 / 10f) {
                x += 2 * tileSize;
            } else {
                x = 0;
                y += tileSize;
            }
        }
        stage.addActor(craftingGroup);
    }


    public void showTimedDialog(String message, float durationSeconds, Stage stage) {
        if (activeDialog != null) {
            activeDialog.remove();
        }

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

        dialogExpirationTime = TimeUtils.millis() + (long)(durationSeconds * 1000);
    }



    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);


        int tileSize = 120;

        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

// سه چهارم صفحه
        areaWidth = (int)(screenWidth * 0.75f);
        areaHeight = (int)(screenHeight * 0.75f);

// نزدیک‌ترین مقدار که قابل تقسیم بر tileSize باشه
        areaWidth = (areaWidth / tileSize) * tileSize;
        areaHeight = (areaHeight / tileSize) * tileSize;

// محاسبه‌ی موقعیت ناحیه مرکزی
        startX = (screenWidth - areaWidth) / 2;
        startY = (screenHeight - areaHeight) / 2;


        cookingGroup = new Group();
        craftingGroup = new Group();


        kitchen =  new Texture(Gdx.files.internal("Ariyo/Flooring/Flooring_23.png"));
        chest = new Texture(Gdx.files.internal("Ariyo/Chest.png"));
        bench = new Texture(Gdx.files.internal("Ariyo/Workbench.png"));
        livingFloor = new Texture(Gdx.files.internal("Ariyo/Flooring/Flooring_89.png"));
        bedroomFloor = new Texture(Gdx.files.internal("Ariyo/Flooring/Flooring_02.png"));
        bedroomWall = new Texture(Gdx.files.internal("Ariyo/bedroomWall.png"));
        craftShop = new Texture(Gdx.files.internal("Ariyo/Flooring/Flooring_52.png"));
        carpet = new Texture(Gdx.files.internal("Ariyo/carpet.jpg"));
        furniture = new Texture(Gdx.files.internal("Ariyo/furniture-removebg-preview.png"));
        bed = new Texture(Gdx.files.internal("Ariyo/bedd2.png"));
        wndw = new Texture(Gdx.files.internal("Ariyo/window.png"));
        simpleTable = new Texture(Gdx.files.internal("Ariyo/simpleTable.png"));
        chimney = new Texture(Gdx.files.internal("Ariyo/chimney.png"));
        carpet2 =  new Texture(Gdx.files.internal("Ariyo/carpet.png"));
        tree = new Texture(Gdx.files.internal("Ariyo/tree.png"));
        pool = new Texture(Gdx.files.internal("Ariyo/pool.png"));
        TextureRegion[] frames = {new TextureRegion(new Texture(Gdx.files.internal("Ariyo/oldman1.png"))), new TextureRegion(new Texture(Gdx.files.internal("Ariyo/oldman2.png")))};
        oldmanAnimation = new Animation<TextureRegion>(0.7f,
            frames
            );
        plant = new Texture(Gdx.files.internal("Ariyo/plant.png"));
        barrel = new Texture(Gdx.files.internal("Ariyo/barrel.png"));
        deck = new Texture(Gdx.files.internal("Ariyo/deck.png"));
        lib = new Texture(Gdx.files.internal("Ariyo/lib.png"));
        torch = new Texture(Gdx.files.internal("Ariyo/torch.png"));
        board = new Texture(Gdx.files.internal("Ariyo/board.png"));
        kitchenTable = new Texture(Gdx.files.internal("Ariyo/kitchenTable.png"));
        kitchenChair = new Texture(Gdx.files.internal("Ariyo/kitchenChair.png"));
        fridge = new Image(new Texture(Gdx.files.internal("Ariyo/fridge.png")));
        fridge.setColor(1f, 1f, 1f, 0.3f);
        fridge.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
//                fridge.setVisible(true);
                fridge.setColor(0.5f, 0.5f, 0.5f, 1f);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
//                fridge.setVisible(false);
                fridge.setColor(1f, 1f, 1f, 0.3f);
            }

        });
        fridge.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                createFridge();
            }
        });
        fridge.setSize(fridge.getWidth() * 2.5f, fridge.getHeight() * 2.9f);
        fridge.setPosition(startX + areaWidth/8.2f, startY + areaHeight/1.74f);
        stage.addActor(fridge);

        recipePaper = new Texture(Gdx.files.internal("Ariyo/mail.png"));
        bedroomCarpet =  new Texture(Gdx.files.internal("Ariyo/bedroomCarpet.png"));



        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.zoom = 0.35f;


        tiledMap = new TmxMapLoader().load("Ariyo/Maps/FarmHouse1.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);


        float mapPixelWidth = tiledMap.getProperties().get("width", Integer.class) *
            tiledMap.getProperties().get("tilewidth", Integer.class);
        float mapPixelHeight = tiledMap.getProperties().get("height", Integer.class) *
            tiledMap.getProperties().get("tileheight", Integer.class);
        camera.position.set(mapPixelWidth / 2f, mapPixelHeight / 2f, 0);
        camera.update();

        Gdx.input.setInputProcessor(new InputMultiplexer(stage, new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (mode == HouseModes.home && keycode == Input.Keys.ESCAPE) {
                    Main.getMain().setScreen(GameMenu.getInstance());
                }
                else {
                    mode = HouseModes.home;
                }
                return false;
            }
        }));



    }

    private void createFridge() {
//        if (!test) {
//            test = true;
//            Fridge f = currentGame.currentPlayer.getFarm().getHome().getFridge();
//            f.items.put(new Animalproduct(AnimalProductType.Egg, Quantity.Normal), 1);
//            f.items.put(new Fish(FishType.Salmon, Quantity.Normal), 1);
//            f.items.put(new Fish(FishType.Sardine, Quantity.Normal), 1);
//            f.items.put(new Fish(FishType.Tilapia, Quantity.Normal), 1);
//            f.items.put(new Animalproduct(AnimalProductType.sheeps_Wool, Quantity.Normal), 1);
//            f.items.put(new MarketItem(MarketItemType.Coffee), 1);
//            f.items.put(new MarketItem(MarketItemType.Beer), 1);
//            f.items.put(new MarketItem(MarketItemType.Bread), 1);
//            f.items.put(new MarketItem(MarketItemType.BasicRetainingSoil), 1);
//            f.items.put(new Fish(FishType.Blue_Discus, Quantity.Normal), 1);
//            f.items.put(new Fish(FishType.Legend, Quantity.Normal), 1);
//            f.items.put(new Fish(FishType.Crimsonfish, Quantity.Normal), 1);
//            f.items.put(new Fish(FishType.Herring, Quantity.Normal), 1);
//        }



        fridgePopup = new Window("", newSkin);
        fridgePopup.setSize(1300, 700);
        fridgePopup.setPosition(
            (stage.getWidth() - fridgePopup.getWidth()) / 2,
            (stage.getHeight() - fridgePopup.getHeight()) / 2);

        Drawable bg = new TextureRegionDrawable(new TextureRegion(TextureManager.get("Erfan/Inventory/Inventory.png")));
        fridgePopup.setBackground(bg);


        Table content = new Table();
        content.top().left();
        content.setOrigin(Align.topLeft);
        content.setPosition(0, stage.getHeight());
        createItems(content);


        content.setPosition(fridgePopup.getWidth() - content.getWidth(), fridgePopup.getHeight() - content.getHeight(), Align.topLeft);
        fridgePopup.add(content).expand().top().left().padTop(areaHeight/4.4f).padLeft(areaWidth/16f);

        Label details = new Label("Choose an item to pick\nfrom the fridge,\nor select the '+' to add\nan item to fridge.", newSkin);
        details.setPosition(startX + areaWidth/1.8f, startY - areaHeight/17f); // دقیقاً 50 پیکسل از چپ و 100 از پایین
        fridgePopup.addActor(details);   // به جای add() از addActor استفاده کن

        Image img = new Image(new Texture(Gdx.files.internal("Ariyo/Energy.png")));
        img.setPosition(startX + areaWidth/1.56f, startY + areaHeight/1.57f);
        img.setSize(img.getWidth()*6, img.getHeight()*6);
        img.setColor(0.5f, 0.5f, 0.5f, 1f);
        img.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                img.setColor(0.5f, 0.5f, 0.5f, 1f);
                img.setSize(img.getWidth()*2/3f, img.getHeight()*2/3f);
                img.setPosition(startX + areaWidth/1.56f, startY + areaHeight/1.57f);
                addToFridge();
            }
        });
        img.addListener(new InputListener() {
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                img.setColor(1f, 1f, 1f, 1f);
                img.setSize(img.getWidth()*1.5f, img.getHeight()*1.5f);
                img.setPosition(startX + areaWidth/1.62f, startY + areaHeight/1.7f);
            }
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                img.setColor(0.5f, 0.5f, 0.5f, 1f);
                img.setSize(img.getWidth()*2/3f, img.getHeight()*2/3f);
                img.setPosition(startX + areaWidth/1.56f, startY + areaHeight/1.57f);
            }
        });
        fridgePopup.addActor(img);

        stage.addActor(fridgePopup);
        fridgePopup.getColor().a = 0f;
        fridgePopup.addAction(Actions.fadeIn(1f));
        fridgeIsActivated = true;
    }

    private void addToFridge() {
        fridgePopup.remove();
        createInventory();
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
        createInventoryItems(content);
        content.padRight(300);
        content.padBottom(293);

        inventoryPopup.add(content);
        inventoryPopup.add(currentItemTable).align(Align.topRight).padRight(215).padBottom(400);

        stage.addActor(inventoryPopup);
        inventoryIsActivated = true;
    }

    private void createInventoryItems(Table content) {
//        content.defaults().pad(5);
//        content.setFillParent(false);
//        content.sizeBy(350, 600);
//        content.setPosition(300, 300);
//        content.padLeft(50);
//
//        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;
//
//        int number = 0;
//
//        for (Map.Entry<Items, Integer> entry : inventory.Items.entrySet()) {
//
//            if (number % 6 == 0)
//                content.row();
//
//            Items item = entry.getKey();
//            int count = entry.getValue();
//
//            Image itemButton = new Image(new Texture(item.getInventoryIconPath()));
//
//            Items currentItem = currentGame.currentPlayer.currentItem;
//            boolean isCurrent = currentItem != null && item.getName().equals(currentItem.getName());
//
//            if (isCurrent) {
//                itemButton.setColor(0.4f, 0.8f, 1f, 1f);
//                itemButton.setScale(1.3f);
//            } else
//                itemButton.setColor(1f, 1f, 1f, 0.8f);
//
//
//            Label countLabel = new Label("", newSkin);
//
//            if (!(item instanceof Tools))
//                countLabel.setText(count);
//
//            countLabel.setFontScale(0.9f);
//            countLabel.setColor(Color.BLACK);
//            countLabel.setAlignment(Align.bottomRight);
//
//            Table labelOverlay = new Table();
//            labelOverlay.setFillParent(false);
//            labelOverlay.add(countLabel).bottom().right().padLeft(35).padTop(50);
//
//            Stack stack = new Stack();
//            stack.add(itemButton);
//            stack.add(labelOverlay);
//
//            content.add(stack).width(60).height(60).padLeft(10);
//
//            itemButton.addListener(new ClickListener() {
//
//                @Override
//                public void clicked(InputEvent event, float x, float y) {
//
//                    if (!(item instanceof Food || item instanceof Animalproduct ||
//                        item instanceof Fish || (item instanceof MarketItem && ((MarketItem) item).getType().isEatable()))) {
//
//                        showTimedDialog("Choose an eatable Item!", 2f, stage);
//                        if (helperBackGround == null) {
//                            helperBackGround = new Image(new TextureRegionDrawable(new TextureRegion(TextureManager.get("Erfan/grayPage.jpg"))));
//                            helperBackGround.setColor(0, 0, 0, 0.5f);
//                            helperBackGround.setSize(stage.getWidth(), stage.getHeight());
//                            stage.addActor(helperBackGround);
//                        }
//                        helperBackGround.remove();
//                        inventoryPopup.remove();
//                        inventoryIsActivated = false;
//                        return;
//                    }
//
//
//                    Result result = fridgePut(item);
//
//                    if (helperBackGround == null) {
//                        helperBackGround = new Image(new TextureRegionDrawable(new TextureRegion(TextureManager.get("Erfan/grayPage.jpg"))));
//                        helperBackGround.setColor(0, 0, 0, 0.5f);
//                        helperBackGround.setSize(stage.getWidth(), stage.getHeight());
//                        stage.addActor(helperBackGround);
//                    }
//                    helperBackGround.remove();
//                    inventoryPopup.remove();
//                    inventoryIsActivated = false;
//
//                    showTimedDialog(result.massage(), 2f, stage);
//                }
//                @Override
//                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
//                    itemButton.setColor(1f, 1f, 1f, 1f);
//                    itemButton.setScale(isCurrent ? 1.4f : 1.2f);
//                }
//                @Override
//                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
//                    if (isCurrent) {
//                        itemButton.setColor(0.4f, 0.8f, 1f, 1f);
//                        itemButton.setScale(1.3f);
//                    } else {
//                        itemButton.setColor(1f, 1f, 1f, 0.8f);
//                        itemButton.setScale(1f);
//                    }
//                }
//            });
//            number++;
//        }
    }

    private void createCurrentItem (Table content) {

//        Image img;
//        if (currentGame.currentPlayer.currentItem != null)
//            img = new Image(TextureManager.get(currentGame.currentPlayer.currentItem.getInventoryIconPath()));
//        else
//            img = new Image(TextureManager.get("Erfan/Cancel2.png"));
//
//        content.add(img).align(Align.topRight).width(150).height(150).right();
//        content.row();

    }

    private void createItems (Table content) {

//        content.defaults().pad(5);
//        content.setFillParent(false);
//        content.sizeBy(350, 600);
//        content.setPosition(300, 300);
//        content.padLeft(50);
//
//        Fridge f = currentGame.currentPlayer.getFarm().getHome().getFridge();
////        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;
//
//        int number = 0;
//
//
//        for (Map.Entry<Items, Integer> entry : f.items.entrySet()) {
//
//            if (number % 6 == 0)
//                content.row();
//
//            Items item = entry.getKey();
//            int count = entry.getValue();
//
//            Image itemButton = new Image(new Texture(item.getInventoryIconPath()));
//
//            Items currentItem = currentGame.currentPlayer.currentItem;
//            boolean isCurrent = currentItem != null && item.getName().equals(currentItem.getName());
//
//            if (isCurrent) {
//                itemButton.setColor(0.4f, 0.8f, 1f, 1f);
//                itemButton.setScale(1.3f);
//            } else
//                itemButton.setColor(1f, 1f, 1f, 0.8f);
//
//
//            Label countLabel = new Label("", newSkin);
//
//            if (!(item instanceof Tools))
//                countLabel.setText(count);
//
//            countLabel.setFontScale(0.9f);
//            countLabel.setColor(Color.BLACK);
//            countLabel.setAlignment(Align.bottomRight);
//
//            Table labelOverlay = new Table();
//            labelOverlay.setFillParent(false);
//            labelOverlay.add(countLabel).bottom().right().padLeft(35).padTop(50);
//
//            Stack stack = new Stack();
//            stack.add(itemButton);
//            stack.add(labelOverlay);
//
//            content.add(stack).width(60).height(60).padLeft(10);
//
//            itemButton.addListener(new ClickListener() {
//
//                @Override
//                public void clicked(InputEvent event, float x, float y) {
//
//                    Result result = fridgePick(item);
//                    showTimedDialog(result.massage(), 1f, stage);
//                    if (helperBackGround == null) {
//                        helperBackGround = new Image(new TextureRegionDrawable(new TextureRegion(TextureManager.get("Erfan/grayPage.jpg"))));
//                        helperBackGround.setColor(0, 0, 0, 0.5f);
//                        helperBackGround.setSize(stage.getWidth(), stage.getHeight());
//                        stage.addActor(helperBackGround);
//                    }
//                    helperBackGround.remove();
//                    fridgePopup.addAction(Actions.sequence(
//                        Actions.fadeOut(1f),
//                        Actions.run(() -> {
//                            fridgePopup.remove(); // حذف کامل از stage
//                            fridgePopup = null;
//                        })
//                    ));
//
//                    fridgeIsActivated = false;
//                }
//                @Override
//                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
//                    itemButton.setColor(1f, 1f, 1f, 1f);
//                    itemButton.setScale(isCurrent ? 1.4f : 1.2f);
//                }
//                @Override
//                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
//                    if (isCurrent) {
//                        itemButton.setColor(0.4f, 0.8f, 1f, 1f);
//                        itemButton.setScale(1.3f);
//                    } else {
//                        itemButton.setColor(1f, 1f, 1f, 0.8f);
//                        itemButton.setScale(1f);
//                    }
//                }
//            });
//            number++;
//        }
    }

    @Override
    public void render(float v) {

        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();


        if (activeDialog != null && TimeUtils.millis() > dialogExpirationTime) {
            activeDialog.remove();
            activeDialog = null;
        }


        // decorating
        if (mode == HouseModes.home) {
            camera.update();
            mapRenderer.setView(camera);
            mapRenderer.render();
            fridge.setVisible(true);
            if (cookingGroup != null) {
                cookingGroup.remove();
            }
            if (craftingGroup != null) {
                craftingGroup.remove();
            }

            //bedroom walls
            Main.getBatch().draw(bedroomWall, startX + areaWidth/8.67f, startY + areaHeight/28.5f, bedroomWall.getWidth(), bedroomWall.getHeight()*100/95f);
            Main.getBatch().draw(bedroomWall, startX + areaWidth/5.7f, startY + areaHeight/28.5f, bedroomWall.getWidth(), bedroomWall.getHeight()*100/95f);
            Main.getBatch().draw(bedroomWall, startX + areaWidth/5f, startY + areaHeight/28.5f, bedroomWall.getWidth(), bedroomWall.getHeight()*100/95f);
            Main.getBatch().draw(bedroomWall, startX + areaWidth/6.5f, startY + areaHeight/28.5f, bedroomWall.getWidth(), bedroomWall.getHeight()*100/95f);
            Main.getBatch().draw(bedroomWall, startX + areaWidth/7.5f, startY + areaHeight/28.5f, bedroomWall.getWidth(), bedroomWall.getHeight()*100/95f);
            Main.getBatch().draw(bedroomWall, startX + areaWidth/4f - bedroomWall.getWidth(), startY + areaHeight/28.5f, bedroomWall.getWidth(), bedroomWall.getHeight()*100/95f);

            Main.getBatch().draw(tree, startX + areaWidth/4.9f, startY - areaHeight/7.7f);
            Main.getBatch().draw(plant, startX + areaWidth/5.8f, startY - areaHeight/5.7f);
            Main.getBatch().draw(carpet2, startX, startY - areaHeight/9.2f);
            Main.getBatch().draw(chimney, startX - areaWidth/28.5f, startY + areaHeight/40f);
            Main.getBatch().draw(simpleTable, startX + areaWidth/60f, startY - areaHeight/28.2f);
            Main.getBatch().draw(bed, startX + areaWidth/8.5f, startY - areaHeight/30f);
            Main.getBatch().draw(torch,  startX + areaWidth/10.4f, startY +  areaHeight/60f);
            Main.getBatch().draw(wndw, startX + areaWidth/60f, startY + areaHeight/90f);
            Main.getBatch().draw(pool, startX - areaWidth/21f, startY - areaHeight/13f,  pool.getWidth()*8/10f, pool.getHeight()*8/10f);
            oldmanStateTime += Gdx.graphics.getDeltaTime();
            TextureRegion currentFrame = oldmanAnimation.getKeyFrame(oldmanStateTime, true);
            Main.getBatch().draw(currentFrame, startX + areaWidth/30f, startY - areaHeight/9f);
            Main.getBatch().draw(barrel, startX + areaWidth/28.7f, startY - areaHeight/28.5f);
            Main.getBatch().draw(lib, startX + areaWidth/5.5f, startY - areaHeight/80f);
            Main.getBatch().draw(deck, startX + areaWidth/6f, startY - areaHeight/24f, deck.getWidth()*8/10f, deck.getHeight()*8/10f);
            Main.getBatch().draw(torch, startX - areaWidth/90f, startY + areaHeight/60f);
            Main.getBatch().draw(torch, startX - areaWidth/15f, startY + areaHeight/60f);
            Main.getBatch().draw(torch, startX - areaWidth/9f, startY + areaHeight/60f);
            Main.getBatch().draw(torch, startX - areaWidth/6f, startY + areaHeight/60f);
            Main.getBatch().draw(board, startX + areaWidth/7.7f, startY + areaHeight/80f);
            Main.getBatch().draw(kitchenChair, startX - areaWidth/6f, startY - areaHeight/7f);
            Main.getBatch().draw(kitchenChair, startX - areaWidth/7.6f, startY - areaHeight/7f);
            Main.getBatch().draw(kitchenChair, startX - areaWidth/6.7f, startY - areaHeight/9.4f);
            Main.getBatch().draw(kitchenTable, startX - areaWidth/6.7f, startY - areaHeight/8f);
            Main.getBatch().draw(bedroomCarpet, startX + areaWidth/8f, startY - areaHeight/8f, bedroomCarpet.getWidth()*9/10f, bedroomCarpet.getHeight()*9/10f);
        }
        if (mode != HouseModes.home) {
            fridge.setVisible(false);
        }

        if (mode == HouseModes.cook) {
            ScreenUtils.clear(Color.BLACK);
            showRecipes();
        }

        if (mode == HouseModes.craft) {
            ScreenUtils.clear(Color.BLACK);
            showCraftings();
        }

        if (activeDialog != null && TimeUtils.millis() > dialogExpirationTime) {
            activeDialog.remove();
            activeDialog = null;
        }


        if (mode == HouseModes.home)
            drawPlayer();

        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        mode = HomeController.handleHomeInputs(mode, this);
    }

    private void drawPlayer() {
//        Sprite player = currentGame.currentPlayer.getSprite();
//        Main.getBatch().draw(player, startX - areaWidth/22f , startY - areaHeight/7f);


    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public Stage getStage() {
        return stage;
    }
}
