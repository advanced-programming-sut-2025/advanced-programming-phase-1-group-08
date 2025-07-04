package com.Graphic.View.GameMenus;

import com.Graphic.Controller.MainGame.CraftingController;
import com.Graphic.Controller.MainGame.InputGameController;
import com.Graphic.Controller.MainGame.HomeController;
import com.Graphic.Main;
import com.Graphic.View.AppMenu;
import com.Graphic.View.AppView;
import com.Graphic.model.Enum.Commands.GameMenuCommands;
import com.Graphic.model.Enum.Commands.HomeMenuCommands;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

public class HomeMenu extends AppView implements AppMenu, Screen {

    Stage stage;

    CraftingController craftingController = new CraftingController();
    HomeController homeController=new HomeController();
    @Override
    public void check(Scanner scanner) {
        boolean startCrafting = false;
        String input = scanner.nextLine();
        Matcher matcher;
        if (HomeMenuCommands.startCooking.getMatcher(input) != null) {
            HomeController.cook();
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

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.RED);
        pixmap.fill();

        Texture texture = new Texture(pixmap);
        Drawable backgroundDrawable = new TextureRegionDrawable(new TextureRegion(texture));

// ساخت Style برای Window
        Window.WindowStyle windowStyle = new Window.WindowStyle();
        windowStyle.titleFont = new BitmapFont(); // یا فونت دلخواه شما
        windowStyle.background = backgroundDrawable;

        Window inventoryWindow = new Window("HI", windowStyle);
        inventoryWindow.setSize(400, 300);
        inventoryWindow.setPosition(
            (Gdx.graphics.getWidth() - inventoryWindow.getWidth()) / 2,
            (Gdx.graphics.getHeight() - inventoryWindow.getHeight()) / 2
        );

// اضافه کردن آیتم‌ها
        inventoryWindow.add(new Label("Item 1", new Label.LabelStyle(new BitmapFont(), Color.WHITE)));
        inventoryWindow.row();
        inventoryWindow.add(new Label("Item 2", new Label.LabelStyle(new BitmapFont(), Color.WHITE)));

// اضافه به Stage
        stage.addActor(inventoryWindow);

// تنظیم input برای بستن با ESC
        Gdx.input.setInputProcessor(new InputMultiplexer(stage, new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    inventoryWindow.remove();
                }
                return false;
            }
        }));



    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        HomeController.handleHomeInputs();
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
