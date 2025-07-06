package com.Graphic.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class GameAssetManager {

    private static GameAssetManager gameAssetManager;

    BitmapFont font = new BitmapFont(Gdx.files.internal("Erfan/Fonts/tinyFont.fnt"));
    BitmapFont font2 = new BitmapFont(Gdx.files.internal("Erfan/Fonts/SmallFont.fnt"));
    BitmapFont font3 = new BitmapFont(Gdx.files.internal("Erfan/Fonts/SpriteFont1.fnt"));



    private GameAssetManager() {

    }

    public static GameAssetManager getGameAssetManager() {
        if (gameAssetManager == null)
            gameAssetManager = new GameAssetManager();
        return gameAssetManager;
    }


    public BitmapFont getFont() {
        return font;
    }
    public BitmapFont getFont2() {
        return font2;
    }
    public BitmapFont getFont3() {
        return font3;
    }
}
