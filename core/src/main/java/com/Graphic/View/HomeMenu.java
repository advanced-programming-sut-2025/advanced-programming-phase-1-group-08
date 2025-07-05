package com.Graphic.View;

import com.Graphic.Controller.MainGame.CraftingController;
import com.Graphic.Controller.MainGame.InputGameController;
import com.Graphic.Controller.MainGame.HomeController;
import com.Graphic.Main;
import com.Graphic.model.Enum.Commands.GameMenuCommands;
import com.Graphic.model.Enum.Commands.HomeMenuCommands;
import com.Graphic.model.Enum.FoodTypes;
import com.Graphic.model.Enum.HouseModes;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.Scanner;
import java.util.regex.Matcher;

import static com.Graphic.model.App.skin;

public class HomeMenu extends AppView implements AppMenu, Screen {

    Stage stage;
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
    Texture wall;
    Texture wallSeparator;
    Texture recipePaper;
    Texture bed;


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

        int x = 0;
        int y = 0;
        for (int i = 0; i < FoodTypes.values().length; i++) {
            FoodTypes f = FoodTypes.values()[i];
            Texture t = new Texture(Gdx.files.internal(f.getAddress()));
            Label l = new Label(f.getName(), skin);
            Main.getBatch().draw(t, startX + (float) (areaWidth * 9) /10 - x, startY + (float) (areaHeight * 9) /10 - y);
            l.setPosition(startX + (float) (areaWidth * 9) /10 - x, startY + (float) (areaHeight * 8) /10 - y);
            stage.addActor(l);
            if (x < areaWidth*7/10) {
                x += 2*tileSize;
            }
            else  {
                x = 0;
                y += tileSize;
            }
        }
    }
    @Override
    public void check(Scanner scanner) {
        boolean startCrafting = false;
        String input = scanner.nextLine();
        Matcher matcher;
        if (HomeMenuCommands.startCooking.getMatcher(input) != null) {
//            HomeController.cook();
        }


        else if (input.trim().toLowerCase().matches("\\s*show\\s+current\\s+menu\\s*"))
            System.out.println("Home Menu");
        // else if (input.toLowerCase().matches("\\s*exit\\s*"))
            // App.currentMenu = Menu.GameMenu;

        else if ((matcher=HomeMenuCommands.startCrafting.getMatcher(input))!=null)
            System.out.println(homeController.goToCraftingMenu());


        else if((matcher = GameMenuCommands.inventoryShow.getMatcher(input)) != null) {
            InputGameController controller = new InputGameController();
            System.out.println(controller.showInventory());
        }

        else if ((matcher=GameMenuCommands.addItem.getMatcher(input)) != null) {
            InputGameController controller = new InputGameController();
            System.out.println(controller.addItem(matcher.group(1), Integer.parseInt(matcher.group(2).trim())));
        }



        else
            System.out.println("Invalid Command!");
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);


        background = new Texture(Gdx.files.internal("Ariyo/Flooring/Flooring_22.png"));
        borderFullTexture = new Texture(Gdx.files.internal("Ariyo/Gravel_Path_Tile.png"));
        borderTexture = new TextureRegion(borderFullTexture, 0, 30, 30, 80);
        horizontalBorder = new Sprite(borderTexture);
        horizontalBorder.setOriginCenter();  // چرخش از مرکز انجام بشه
        horizontalBorder.setRotation(90);   // چرخش ۹۰ درجه ساعت‌گرد
        corners = new Texture(Gdx.files.internal("Ariyo/Stepping_Stone_Path.png"));
        wall = new Texture(Gdx.files.internal("Ariyo/Flooring/Flooring_11.png"));
        wallSeparator = new Texture(Gdx.files.internal("Ariyo/WallSeperator.jpg"));

        kitchen =  new Texture(Gdx.files.internal("Ariyo/Flooring/Flooring_22.png"));
        fridge = new Texture(Gdx.files.internal("Ariyo/Mini-Fridge.png"));
        chest = new Texture(Gdx.files.internal("Ariyo/Chest.png"));
        bench = new Texture(Gdx.files.internal("Ariyo/Workbench.png"));
        livingFloor = new Texture(Gdx.files.internal("Ariyo/Flooring/Flooring_89.png"));
        bedroomFloor = new Texture(Gdx.files.internal("Ariyo/Flooring/Flooring_02.png"));
        craftShop = new Texture(Gdx.files.internal("Ariyo/Flooring/Flooring_52.png"));
        carpet = new Texture(Gdx.files.internal("Ariyo/carpet.jpg"));
        furniture = new Texture(Gdx.files.internal("Ariyo/furniture-removebg-preview.png"));
        bed = new Texture(Gdx.files.internal("Ariyo/bed-removebg-preview.png"));

        recipePaper = new Texture(Gdx.files.internal("Ariyo/mail.png"));

//// تنظیم input برای بستن با ESC
        Gdx.input.setInputProcessor(new InputMultiplexer(stage, new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (mode == HouseModes.home && keycode == Input.Keys.ESCAPE) {
                    Main.getMain().setScreen((new GameMenu()));
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
        for (int y = startY; y < startY + areaHeight; y += tileSize) {
            for (int x = startX; x < startX + areaWidth; x += tileSize) {
                Main.getBatch().draw(background, x, y, tileSize, tileSize);
            }
        }

        // draw kitchen
        for (int y = (startY + areaHeight)*2/3; y < (startY + areaHeight); y += kitchen.getHeight()) {
            for (int x = (startX + areaWidth - kitchen.getWidth()); x > (startX + areaWidth)*3/5; x -= kitchen.getWidth()) {
//            for (int x = startX; x < (startX + areaWidth)/2; x += kitchen.getWidth()) {
                Main.getBatch().draw(kitchen, x, y, kitchen.getWidth(), kitchen.getHeight());
            }
        }
        // draw LivingRoom
        for (int y = startY; y < (startY + areaHeight)*3/7; y += livingFloor.getHeight()) {
            for (int x = (startX + areaWidth - livingFloor.getWidth()); x > (startX + areaWidth)*4/7; x -= livingFloor.getWidth()) {
                Main.getBatch().draw(livingFloor, x, y, livingFloor.getWidth(), livingFloor.getHeight());
            }
        }
        // draw bedroom
        for (int y = (startY + areaHeight - bedroomFloor.getHeight()); y > (startY + areaHeight)*2/5; y -= bedroomFloor.getHeight()) {
            for (int x = startX; x < (startX + areaWidth)*3/7; x += bedroomFloor.getWidth()) {
                Main.getBatch().draw(bedroomFloor, x, y, bedroomFloor.getWidth(), bedroomFloor.getHeight());
            }
        }
        // draw craftPlace
        for (int y = startY; y < (startY + areaHeight)*2/5; y += craftShop.getHeight()) {
            for (int x = (startX); x < (startX + areaWidth)*3/7; x += craftShop.getWidth()) {
                Main.getBatch().draw(craftShop, x, y, craftShop.getWidth(), craftShop.getHeight());
            }
        }

//        // draw walls
//        for (int y = (startY + areaHeight - wall.getHeight()); y > startY + (areaHeight*3/5); y -= wall.getHeight()) {
//            for (int x = (startX + areaWidth - wall.getWidth()); x > startX - wall.getWidth(); x -= wall.getWidth()) {
//                Main.getBatch().draw(wall, x, y + areaHeight/10, wall.getWidth(), (float) (wall.getHeight() * 2) /3);
//            }
//        }
//        for (int x = (startX + areaWidth - wallSeparator.getWidth()); x > startX - wallSeparator.getWidth(); x -= wallSeparator.getWidth()) {
//            Main.getBatch().draw(wallSeparator, x, startY + areaHeight*9/10, wallSeparator.getWidth(), (float) (wallSeparator.getHeight())*5);
//        }



        for (int x = startX; x < startX + areaWidth; x += horizontalBorder.getRegionHeight()) {
            // بالا
            horizontalBorder.setPosition(x, startY - horizontalBorder.getRegionWidth() + areaHeight);
            horizontalBorder.draw(Main.getBatch());

            // پایین
            horizontalBorder.setPosition(x, startY - horizontalBorder.getRegionWidth());
            horizontalBorder.draw(Main.getBatch());
        }


// کشیدن ضلع چپ و راست با تایل عمودی اصلی
        for (int y = startY; y < startY + areaHeight; y += borderTexture.getRegionHeight()) {
            Main.getBatch().draw(borderTexture, startX - borderTexture.getRegionWidth() + 20, y); // چپ
            Main.getBatch().draw(borderTexture, startX + areaWidth - 20, y); // راست
        }
        Main.getBatch().draw(corners, startX - 30, startY - 20);
        Main.getBatch().draw(corners, startX + areaWidth, startY - 20);
        Main.getBatch().draw(corners, startX - 40, startY + areaHeight);
        Main.getBatch().draw(corners, startX + areaWidth - 20, startY + areaHeight);


        // decorating
        Main.getBatch().draw(fridge, startX + (float) (areaWidth * 8) /10, startY + (float) (areaHeight * 7.5) /10, areaWidth/15, areaHeight/5);
        Main.getBatch().draw(chest, startX + (float) (areaWidth) /15, startY + (float) areaHeight/5);
        Main.getBatch().draw(furniture, startX + (float) (areaWidth*10) /15, (float) (startY + (float) areaHeight*(1.8)/7), areaWidth/8, areaHeight/5);
        Main.getBatch().draw(carpet, startX + (float) (areaWidth*10) /15, (float) (startY + (float) areaHeight*1.8/10), areaWidth/9, areaHeight/13);
        Main.getBatch().draw(bed, (float) (startX*11/10), (float) (startY + areaHeight*7.4/10), areaWidth/12, areaHeight/4);



        if (mode == HouseModes.cook) {
            TextureRegion recipePaper2 = new TextureRegion(recipePaper, 0, recipePaper.getHeight()/1000, recipePaper.getWidth(), (recipePaper.getHeight()*100)/512);
            Main.getBatch().draw(recipePaper2, (float) (startX * 6) /10, (float) (startY * 6) /10, startX + areaWidth, startY + areaHeight);
            showRecipes();
            }

            Main.getBatch().end();
            stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
            stage.draw();
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
}
