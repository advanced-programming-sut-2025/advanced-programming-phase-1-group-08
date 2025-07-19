package com.Graphic.model.Weather;

import com.Graphic.Main;
import com.Graphic.model.MapThings.Tile;
import com.badlogic.gdx.graphics.Texture;

import static com.Graphic.Controller.MainGame.GameControllerLogic.lightningStrike;

public class Cloud {

    private final Texture cloudTexture;
    private final Texture shadowTexture;
    private final Tile targetTile;

    private float cloudX;
    private float cloudY;
    private float shadowY;

    private final float targetX;
    private final float speed = 50f;
    private boolean lightningTriggered = false;

    public Cloud (Texture cloudTexture, Texture shadowTexture, Tile targetTile, float tileSize) {
        this.cloudTexture = cloudTexture;
        this.shadowTexture = shadowTexture;
        this.targetTile = targetTile;

        this.cloudX = 90 * tileSize;
        this.cloudY = (targetTile.getY() + 1) * tileSize;
        this.shadowY = targetTile.getY() * tileSize;
        this.targetX = targetTile.getX() * tileSize;
    }


    public void render() {

        Main.getBatch().draw(shadowTexture, cloudX + 20, shadowY,
            shadowTexture.getWidth() *2, shadowTexture.getHeight() * 2);

        Main.getBatch().draw(cloudTexture, cloudX, cloudY + 20,
            cloudTexture.getWidth() * 2, cloudTexture.getHeight() * 2);
    }
    public void update(float delta) {
        if (cloudX > targetX) {
            cloudX -= speed * delta;
            if (cloudX < targetX) cloudX = targetX;
        } else if (!lightningTriggered) {
            lightningStrike(targetTile);
            lightningTriggered = true;
        }
    }
    public boolean isFinished() {
        return lightningTriggered;
    }
}

