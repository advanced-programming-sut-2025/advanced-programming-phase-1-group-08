package com.Graphic;

import com.Graphic.View.GameMenus.GameMenu;
import com.Graphic.View.GameMenus.HomeMenu;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

    private static Main main;
    private static SpriteBatch batch;

    @Override
    public void create() {

        main = this;
        batch = new SpriteBatch();
        main.setScreen(GameMenu.getInstance());

    }

    public void render() {
        super.
            render();
    }
    public void dispose() {
        batch.dispose();
    }
    public static SpriteBatch getBatch() {
        return batch;
    }
    public static Main getMain() {
        return main;
    }
    public static void setMain(Main main) {
        Main.main = main;
    }
}
