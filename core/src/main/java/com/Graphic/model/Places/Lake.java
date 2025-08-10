package com.Graphic.model.Places;

import com.Graphic.Controller.MainGame.GameControllerLogic;
import com.Graphic.Main;
import com.Graphic.model.Enum.ItemType.MarketType;
import com.Graphic.model.HelpersClass.TextureManager;
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
    private  int topLeftX;
    private  int topLeftY;
    private  int width;
    private  int height;
    private ArrayList<String> LakeAnimation = new ArrayList<>();
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
    public Lake() {

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
                if (GameControllerLogic.getTileByCoordinates(i, j , Main.getClient(null).getLocalGameState()).getGameObject().equals(this)) {
                    int x = width * (j - topLeftY) + i -topLeftX + 1;
                    LakeAnimation.add("Places/Lake1,"+x+".png");
                    LakeAnimation.add("Places/Lake2,"+x+".png");
                    //LakeAnimation = new Animation<>(0.5f , first.getTexture() , second.getTexture());
                    return Path = "Places/Lake1,"+x+".png";
                }
            }
        }

        return Path;
    }

    public ArrayList<String> getLakeAnimation() {
        return LakeAnimation;
    }

    public float getTimer() {
        return Timer;
    }
    public void setTimer(float timer) {
        Timer = timer;
    }

    @Override
    public int getRemindInShop(MarketType marketType) {
        return 0;
    }

    @Override
    public void setRemindInShop(int amount, MarketType marketType) {

    }

}
