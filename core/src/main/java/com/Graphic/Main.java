package com.Graphic;

import com.Graphic.Controller.MainGame.InputGameController;
import com.Graphic.View.GameMenus.GameMenu;
import com.Graphic.View.GameMenus.MarketMenu;
import com.Graphic.View.LoginMenu;
import com.Graphic.View.MainMenu;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

    private static Main main;
    private static SpriteBatch batch;
    private static Skin skin;
    private static InputGameController x;

    @Override
    public void create() {

        main = this;
        batch = new SpriteBatch();
//        x = InputGameController.getInstance();
//        x.startNewGame("a");
//        main.setScreen(new MarketMenu());

        skin = new Skin(Gdx.files.internal("Skin/craftacular-ui.json"));
        main.setScreen(new LoginMenu());

    }

    public void render() {
        super.
            render();
    }
    public void dispose() {
        batch.dispose();
        skin.dispose();
    }
    public static SpriteBatch getBatch() {
        return batch;
    }
    public static Skin getSkin() {return skin;}
    public static Main getMain() {
        return main;
    }
    public static void setMain(Main main) {
        Main.main = main;
    }
}
