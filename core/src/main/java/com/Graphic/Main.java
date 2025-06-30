package com.Graphic;

import com.Graphic.View.HomeMenu;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.sun.jdi.request.StepRequest;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

    private static Main main;
    private static SpriteBatch batch;

    @Override
    public void create() {

        main = this;
        batch = new SpriteBatch();
        main.setScreen((new HomeMenu()));

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
