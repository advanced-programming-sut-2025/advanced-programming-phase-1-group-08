package com.Graphic.View.GameMenus;

import com.Graphic.Controller.MainGame.CraftingController;
import com.Graphic.Controller.MainGame.InputGameController;
import com.Graphic.Controller.MainGame.HomeController;
import com.Graphic.Main;
import com.Graphic.View.AppMenu;
import com.Graphic.View.AppView;
import com.Graphic.model.Enum.Commands.GameMenuCommands;
import com.Graphic.model.Enum.Commands.HomeMenuCommands;
import com.Graphic.model.Enum.FoodTypes;
import com.Graphic.model.Enum.HouseModes;
import com.Graphic.model.Enum.ItemType.CraftType;
import com.Graphic.model.Enum.SecurityQuestions;
import com.Graphic.model.Game;
import com.Graphic.model.HelpersClass.Result;
import com.Graphic.model.Recipe;
import com.Graphic.model.User;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.Scanner;
import java.util.regex.Matcher;

import static com.Graphic.Controller.MainGame.HomeController.foodPrepare;

import static com.Graphic.model.App.*;

public class HomeMenu extends AppView implements AppMenu, Screen {

    Stage stage;
    private Dialog activeDialog = null;
    private long dialogExpirationTime = 0;
    Texture background;
    Texture borderFullTexture;
    TextureRegion borderTexture;
    Sprite horizontalBorder;
    Texture corners;
    Texture kitchen;
    Texture livingFloor;
    Texture bedroomFloor;
    Texture craftShop;
    Texture fridge;
    Texture chest;
    Texture bench;
    Texture carpet;
    Texture furniture;
    Texture wndw;
    Texture wall;
    Texture houseWall;
    Texture wallSeparator;
    Texture recipePaper;
    Texture bed;
    Texture homeBG;

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
        int tileSize = 120;
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        int areaWidth = (int)(screenWidth * 0.75f);
        int areaHeight = (int)(screenHeight * 0.75f);
        areaWidth = (areaWidth / tileSize) * tileSize;
        areaHeight = (areaHeight / tileSize) * tileSize;
        int startX = (screenWidth - areaWidth) / 2;
        int startY = (screenHeight - areaHeight) / 2;

        Label letsCook = new Label("What Are You Cooking?", skin);
        letsCook.setPosition((float) (startX + (float) areaWidth*2.2/7), startY + (float) (areaHeight * 11) /10);
        stage.addActor(letsCook);

        float x = 0, y = 0; // فرض بر اینه که قبلاً تعریف شده
        for (int i = 0; i < FoodTypes.values().length; i++) {
            FoodTypes f = FoodTypes.values()[i];
            Texture t = new Texture(Gdx.files.internal(f.getAddress()));
            Image image = new Image(t);
            Label l = new Label(f.getName(), skin);

            boolean usable = Recipe.findRecipeByName(f.getName()).isUsable();

            // موقعیت تصویر
            float imageX = startX + x;
            float imageY = startY + (float) (areaHeight * 9) / 10 - y;
            image.setPosition(imageX, imageY);

            if (!usable) {
                image.setColor(1, 1, 1, 0.3f); // شفافیت پایین برای غیرقابل استفاده
                l.setColor(Color.LIGHT_GRAY);
            } else {
                l.setColor(Color.GRAY);
            }

            // لیسنر کلیک
            image.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Result result = foodPrepare(f.getName());
                    showTimedDialog(result.massage(), 2f);
                }
            });

            // موقعیت لیبل
            l.setPosition(imageX, startY + (float) (areaHeight * 8.3) / 10 - y);
            l.setFontScale(0.5f);

            // اضافه کردن به استیج
            stage.addActor(image);
            stage.addActor(l);

            // مدیریت موقعیت‌ها
            if (x < areaWidth * 7 / 10f) {
                x += 2 * tileSize;
            } else {
                x = 0;
                y += tileSize;
            }
        }


    }

    private void showCraftings () {
        int tileSize = 120;
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        int areaWidth = (int)(screenWidth * 0.75f);
        int areaHeight = (int)(screenHeight * 0.75f);
        areaWidth = (areaWidth / tileSize) * tileSize;
        areaHeight = (areaHeight / tileSize) * tileSize;
        int startX = (screenWidth - areaWidth) / 2;
        int startY = (screenHeight - areaHeight) / 2;

        Label craftMenu = new Label("Craft Menu", skin);
        craftMenu.setPosition((float) (startX + (float) areaWidth*2.5/7), startY + (float) (areaHeight * 11) /10);
        stage.addActor(craftMenu);

        float x = 0, y = 0; // فرض بر اینه که قبلاً تعریف شده
        for (int i = 0; i < CraftType.values().length; i++) {
            CraftType c = CraftType.values()[i];
            Texture t = new Texture(Gdx.files.internal(c.getAddress()));
            Image image = new Image(t);
            Label l = new Label(c.getName(), skin);

            CraftType type=null;
            for (CraftType craftType : CraftType.values()) {
                if (craftType.getName().equals(c.getName())) {
                    type = craftType;
                }
            }
            boolean usable = true;
            if (type == null) {
                continue;
            }
            if (!type.checkLevel()) {
                usable = false;
            }

            // موقعیت تصویر
            float imageX = startX + x;
            float imageY = startY + (float) (areaHeight * 9) / 10 - y;
            image.setPosition(imageX, imageY);

            if (!usable) {
                image.setColor(1, 1, 1, 0.3f); // شفافیت پایین برای غیرقابل استفاده
                l.setColor(Color.LIGHT_GRAY);
            } else {
                l.setColor(Color.GRAY);
            }

            // لیسنر کلیک
            image.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Result result = craftingController.craftingCraft(c.getName());
                    showTimedDialog(result.massage(), 2f);
                }
            });

            // موقعیت لیبل
            l.setPosition(imageX, startY + (float) (areaHeight * 8.5) / 10 - y);
            l.setFontScale(0.5f);

            // اضافه کردن به استیج
            stage.addActor(image);
            stage.addActor(l);

            // مدیریت موقعیت‌ها
            if (x < areaWidth * 7 / 10f) {
                x += 2 * tileSize;
            } else {
                x = 0;
                y += tileSize;
            }
        }
    }


    public void showTimedDialog(String message, float durationSeconds) {
        activeDialog = new Dialog("", skin);
        activeDialog.text(message);
        activeDialog.pack();
        activeDialog.setPosition(
            (Gdx.graphics.getWidth() - activeDialog.getWidth()) / 2,
            (Gdx.graphics.getHeight() - activeDialog.getHeight()) / 2
        );

        dialogExpirationTime = TimeUtils.millis() + (long)(durationSeconds * 1000);
    }


//    @Override
//    public void check(Scanner scanner) {
//        boolean startCrafting = false;
//        String input = scanner.nextLine();
//        Matcher matcher;
//        if (HomeMenuCommands.startCooking.getMatcher(input) != null) {
////            HomeController.cook();
//        }
//
//
//        else if (input.trim().toLowerCase().matches("\\s*show\\s+current\\s+menu\\s*"))
//            System.out.println("Home Menu");
//        // else if (input.toLowerCase().matches("\\s*exit\\s*"))
//            // App.currentMenu = Menu.GameMenu;
//
//        else if ((matcher=HomeMenuCommands.startCrafting.getMatcher(input))!=null)
//            System.out.println(homeController.goToCraftingMenu());
//
//
//        else if((matcher = GameMenuCommands.inventoryShow.getMatcher(input)) != null) {
//            InputGameController controller = InputGameController.getInstance();
//            System.out.println(controller.showInventory());
//        }
//
//        else if ((matcher=GameMenuCommands.addItem.getMatcher(input)) != null) {
//            InputGameController controller = InputGameController.getInstance();
//            System.out.println(controller.addItem(matcher.group(1), Integer.parseInt(matcher.group(2).trim())));
//        }
//
//
//
//        else
//            System.out.println("Invalid Command!");
//    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);


        kitchen =  new Texture(Gdx.files.internal("Ariyo/Flooring/Flooring_23.png"));
        fridge = new Texture(Gdx.files.internal("Ariyo/Mini-Fridge.png"));
        chest = new Texture(Gdx.files.internal("Ariyo/Chest.png"));
        bench = new Texture(Gdx.files.internal("Ariyo/Workbench.png"));
        livingFloor = new Texture(Gdx.files.internal("Ariyo/Flooring/Flooring_89.png"));
        bedroomFloor = new Texture(Gdx.files.internal("Ariyo/Flooring/Flooring_02.png"));
        craftShop = new Texture(Gdx.files.internal("Ariyo/Flooring/Flooring_52.png"));
        carpet = new Texture(Gdx.files.internal("Ariyo/carpet.jpg"));
        furniture = new Texture(Gdx.files.internal("Ariyo/furniture-removebg-preview.png"));
        bed = new Texture(Gdx.files.internal("Ariyo/bedd.png"));
        wndw = new Texture(Gdx.files.internal("Ariyo/window.png"));

        recipePaper = new Texture(Gdx.files.internal("Ariyo/mail.png"));



        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.zoom = 0.35f;


        tiledMap = new TmxMapLoader().load("Ariyo/Maps/FarmHouse1.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);


        float mapPixelWidth = tiledMap.getProperties().get("width", Integer.class) *
            tiledMap.getProperties().get("tilewidth", Integer.class);
        float mapPixelHeight = tiledMap.getProperties().get("height", Integer.class) *
            tiledMap.getProperties().get("tileheight", Integer.class);
// دوربین روی وسط نقشه
        camera.position.set(mapPixelWidth / 2f, mapPixelHeight / 2f, 0);
        camera.update();

//// تنظیم input برای بستن با ESC
        Gdx.input.setInputProcessor(new InputMultiplexer(stage, new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                System.out.println("oh yos");
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

    @Override
    public void render(float v) {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();


        int tileSize = 120;

        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

// سه چهارم صفحه
        int areaWidth = (int)(screenWidth * 0.75f);
        int areaHeight = (int)(screenHeight * 0.75f);

// نزدیک‌ترین مقدار که قابل تقسیم بر tileSize باشه
        areaWidth = (areaWidth / tileSize) * tileSize;
        areaHeight = (areaHeight / tileSize) * tileSize;

// محاسبه‌ی موقعیت ناحیه مرکزی
        int startX = (screenWidth - areaWidth) / 2;
        int startY = (screenHeight - areaHeight) / 2;


        // decorating
        if (mode == HouseModes.home) {
            camera.update();
            mapRenderer.setView(camera);
            mapRenderer.render();
            Main.getBatch().draw(bed, startX + areaWidth/8.5f, startY - areaHeight/30f);
            Main.getBatch().draw(wndw, startX + areaWidth/20f, startY + areaHeight/24f);
        }

        if (mode == HouseModes.cook) {
            ScreenUtils.clear(Color.BLACK);
            TextureRegion recipePaper2 = new TextureRegion(recipePaper, 0, recipePaper.getHeight()/1000, recipePaper.getWidth(), (recipePaper.getHeight()*100)/512);
            Main.getBatch().draw(recipePaper2, (float) (startX * 6) /10, (float) (startY * 3) /10, startX + areaWidth, startY + areaHeight);
            showRecipes();
        }

        if (mode == HouseModes.craft) {
            ScreenUtils.clear(Color.BLACK);
            TextureRegion recipePaper2 = new TextureRegion(recipePaper, 0, recipePaper.getHeight()/1000, recipePaper.getWidth(), (recipePaper.getHeight()*100)/512);
            Main.getBatch().draw(recipePaper2, (float) (startX * 6) /10, (float) (startY * 3) /10, startX + areaWidth, startY + areaHeight);
            showCraftings();
        }

        if (activeDialog != null && TimeUtils.millis() < dialogExpirationTime) {
            System.out.println("hi");
            stage.addActor(activeDialog);
        } else {
            activeDialog = null; // اگر زمانش تموم شده، دیالوگ رو حذف کن
        }

        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
//        Main.getBatch().begin();
//        if (mode == HouseModes.home) {
//            Main.getBatch().draw(fridge, startX + (float) (areaWidth * 8) /10, startY + (float) (areaHeight * 7.5) /10, areaWidth/15, areaHeight/5);
//            Main.getBatch().draw(chest, startX + (float) (areaWidth) /15, startY + (float) areaHeight/5);
//            Main.getBatch().draw(furniture, startX + (float) (areaWidth*10) /15, (float) (startY + (float) areaHeight*(1.8)/7), areaWidth/8, areaHeight/5);
//            Main.getBatch().draw(carpet, startX + (float) (areaWidth*10) /15, (float) (startY + (float) areaHeight*1.8/10), areaWidth/9, areaHeight/13);
//            Main.getBatch().draw(bed, (float) (startX*11/10), (float) (startY + areaHeight*7.4/10), areaWidth/12, areaHeight/4);
//
//        }
//        Main.getBatch().end();
        mode = HomeController.handleHomeInputs(mode);
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
