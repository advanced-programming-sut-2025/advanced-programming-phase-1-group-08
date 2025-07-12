package com.Graphic.model.Places;

import com.Graphic.Controller.MainGame.GameControllerLogic;
import com.Graphic.model.MapThings.GameObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;
import java.util.Random;

import static com.Graphic.model.HelpersClass.Color_Eraser.*;
import static com.Graphic.model.HelpersClass.TextureManager.TEXTURE_SIZE;

public class Lake extends GameObject {
    private final int topLeftX;
    private final int topLeftY;
    private final int width;
    private final int height;
    private String Path;
    private Animation<Texture> LakeAnimation;
    Sprite first;
    Sprite second;
    private float Timer = 0.0f;

    public Lake(int x, int y, int width, int height) {
        super();
        this.topLeftX = x;
        this.topLeftY = y;
        this.width = width;
        this.height = height;
    }
    public int getTopLeftX() {
        return topLeftX;
    }
    public int getTopLeftY() {
        return topLeftY;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }

    @Override
    public String getIcon() {
        if (Path != null) {
            return Path;
        }


        for (int i = topLeftX ; i < topLeftX + width ; i++) {
            for (int j = topLeftY; j < topLeftY + height; j++) {
                if (GameControllerLogic.getTileByCoordinates(i, j).getGameObject().equals(this)) {
                    int x = width * (j - topLeftY) + i -topLeftX + 1;
                    first = new Sprite(new Texture(Gdx.files.internal("Places/Lake1,"+x+".png")));
                    second = new Sprite(new Texture(Gdx.files.internal("Places/Lake2,"+x+".png")));
                    first.setSize(TEXTURE_SIZE , TEXTURE_SIZE);
                    second.setSize(TEXTURE_SIZE , TEXTURE_SIZE);
                    LakeAnimation = new Animation<>(0.5f , first.getTexture() , second.getTexture());
                    return Path = "Places/Lake1,"+x+".png";
                }
            }
        }

        return Path;
    }

    public Animation<Texture> getLakeAnimation() {
        return LakeAnimation;
    }

    public float getTimer() {
        return Timer;
    }
    public void setTimer(float timer) {
        Timer = timer;
    }
}
