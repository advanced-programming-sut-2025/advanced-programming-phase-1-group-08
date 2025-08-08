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

    public LakeRenderer(Lake lake , int x, int y) {
        this.lake = lake;
        lake.getIcon();
        this.x = x;
        this.y = y;

        animation = new Animation<>(0.5f ,
            TextureManager.get(lake.getLakeAnimation().get(0)) , TextureManager.get(lake.getLakeAnimation().get(1)) );
    }

    public void render() {
        Main.getBatch().draw(animation.getKeyFrame(lake.getTimer() , true) ,
            TEXTURE_SIZE * x , TEXTURE_SIZE * y , TEXTURE_SIZE , TEXTURE_SIZE );
        lake.setTimer(lake.getTimer() + Gdx.graphics.getDeltaTime());
        if (lake.getTimer() > 0.3f) {
            lake.setTimer(0);
        }
    }

}
