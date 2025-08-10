package com.Graphic.model;

import com.Graphic.Main;
import com.Graphic.model.HelpersClass.TextureManager;
import com.Graphic.model.Places.Lake;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

import static com.Graphic.model.HelpersClass.TextureManager.TEXTURE_SIZE;

public class LakeRenderer {
    private Lake lake;
    private int x;
    private int y;

    private Animation<Texture> animation;

    public LakeRenderer(Lake l , int x, int y) {
        int m = l.getWidth() * (y - l.getTopLeftY()) + x - l.getTopLeftX() + 1;
        this.lake = l;
        l.getIcon();
        this.x = x;
        this.y = y;
        try {
            animation = new Animation<>(0.5f,
                TextureManager.get("Places/Lake1," + m + ".png"), TextureManager.get("Places/Lake2," + m + ".png"));
        }
        catch (Exception e) {

        }
    }

    public void render() {
        try {
            Main.getBatch().draw(animation.getKeyFrame(lake.getTimer(), true),
                TEXTURE_SIZE * x, TEXTURE_SIZE * (90 - y), TEXTURE_SIZE, TEXTURE_SIZE);
            //lake.setTimer(lake.getTimer() + Gdx.graphics.getDeltaTime());
            if (!animation.isAnimationFinished(lake.getTimer())) {
                lake.setTimer(lake.getTimer() + Gdx.graphics.getDeltaTime());
            }
            else {
                lake.setTimer(0);
            }
        }
        catch (Exception e) {

        }
    }

}
