package com.Graphic.model.Weather;

import com.Graphic.Main;
import com.Graphic.model.Enum.GameTexturePath;
import com.Graphic.model.HelpersClass.TextureManager;
import com.Graphic.model.MapThings.Tile;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import static com.Graphic.Controller.MainGame.GameControllerLogic.lightningStrike;
import static com.Graphic.model.HelpersClass.TextureManager.TEXTURE_SIZE;

public class LightningEffect {

    private final Texture lightningTexture;
    private final Tile targetTile;
    private final float tileSize;

    private float x, y;
    private float startY;
    private float alpha = 0f;
    private float flashAlpha = 0f;

    private boolean finished = false;
    private boolean strikeDone = false;

    private float time = 0f;

    public LightningEffect(Tile tile, float cloudY) {
        this.targetTile = tile;
        this.tileSize = TEXTURE_SIZE;
        this.lightningTexture = TextureManager.get(GameTexturePath.Lightning.getPath());

        this.x = tile.getX() * tileSize;
        this.y = tile.getY() * tileSize;
        this.startY = cloudY;
    }

    public void update(float delta) {
        if (finished) return;

        time += delta;

        if (time < 0.1f) {
            alpha = time / 0.1f;
        } else if (time < 0.3f) {
            if (!strikeDone) {
                lightningStrike(targetTile);
                flashAlpha = 1f;
                strikeDone = true;
            }
        } else if (time < 0.8f) {
            flashAlpha = 1f - ((time - 0.3f) / 0.5f);
            alpha = 1f - ((time - 0.3f) / 0.5f);
        } else
            finished = true;
    }

    public void render() {
        if (finished) return;

        if (flashAlpha > 0f) {
            Color prevColor = Main.getBatch().getColor();
            Main.getBatch().setColor(1f, 1f, 1f, flashAlpha);
            Main.getBatch().draw(getWhiteTexture(),0,0,
                (float) (Gdx.graphics.getWidth() * 2.5), (float) (Gdx.graphics.getHeight() * 3.2));
            Main.getBatch().setColor(prevColor);
        }

        if (alpha > 0f) {
            Color prevColor = Main.getBatch().getColor();
            Main.getBatch().setColor(1f, 1f, 1f, alpha);
            Main.getBatch().draw(lightningTexture, x, startY - (startY - y) * Math.min(1f, time / 0.1f),
                tileSize * 2, tileSize * 5);
            Main.getBatch().setColor(prevColor);
        }
        Main.getBatch().setColor(Color.WHITE);
    }

    public boolean isFinished() {
        return finished;
    }

    private static Texture whitePixel;
    private static Texture getWhiteTexture() {
        if (whitePixel == null) {
            Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
            pixmap.setColor(Color.WHITE);
            pixmap.fill();
            whitePixel = new Texture(pixmap);
            pixmap.dispose();
        }
        return whitePixel;
    }
}

