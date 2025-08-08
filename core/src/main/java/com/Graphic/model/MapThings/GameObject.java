package com.Graphic.model.MapThings;

import com.Graphic.model.Enum.ItemType.MarketType;
import com.Graphic.model.collisionRect;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;

public abstract class GameObject {

    private char Charactor;
    private float textureWidth;
    private float textureHeight;
    //private Texture texture;
    private String Path;

    public GameObject() {
        this.textureHeight = 1;
        this.textureWidth = 1;

    }

    public char getCharactor() {
        return Charactor;
    }
    public void setCharactor(char Charactor) {
        this.Charactor = Charactor;
    }




    public void startDayAutomaticTask() {}
    public void turnByTurnAutomaticTask() {}

    public abstract String getIcon();



    public float getTextureWidth() {
        return textureWidth;
    }

    public void setTextureWidth(float textureWidth) {
        this.textureWidth = textureWidth;
    }

    public float getTextureHeight() {
        return textureHeight;
    }

    public void setTextureHeight(float textureHeight) {
        this.textureHeight = textureHeight;
    }

    public abstract int getRemindInShop(MarketType marketType);

    public abstract void setRemindInShop(int amount , MarketType marketType);

    //public abstract String getInventoryIconPath();

    public void setPath(String path) {
        this.Path = path;
    }

}
