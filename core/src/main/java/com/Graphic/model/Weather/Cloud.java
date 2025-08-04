package com.Graphic.model.Weather;

import com.Graphic.Main;
import com.Graphic.model.Enum.GameTexturePath;
import com.Graphic.model.HelpersClass.TextureManager;
import com.Graphic.model.MapThings.Tile;
import com.badlogic.gdx.graphics.Texture;

import static com.Graphic.Controller.MainGame.GameControllerLogic.lightningEffect;
import static com.Graphic.Controller.MainGame.GameControllerLogic.lightningStrike;
import static com.Graphic.model.HelpersClass.TextureManager.TEXTURE_SIZE;

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

    public Cloud (Tile targetTile) {
        this.cloudTexture = TextureManager.get(GameTexturePath.Cloud.getPath());
        this.shadowTexture = TextureManager.get(GameTexturePath.CloudShadow.getPath());
        this.targetTile = targetTile;

        this.cloudX = 90 * TEXTURE_SIZE;
        this.cloudY = (targetTile.getY() + 1) * TEXTURE_SIZE;
        this.shadowY = targetTile.getY() * TEXTURE_SIZE;
        this.targetX = targetTile.getX() * TEXTURE_SIZE;
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
            lightningEffect = new LightningEffect(targetTile, cloudY);
            lightningStrike(targetTile);
            lightningTriggered = true;
        }
    }
    public boolean isFinished() {
        return lightningTriggered;
    }
}

