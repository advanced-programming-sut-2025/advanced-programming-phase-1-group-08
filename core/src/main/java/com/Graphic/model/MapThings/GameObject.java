package com.Graphic.model.MapThings;

import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class GameObject {

    private char Charactor;
    private float width;
    private float height;

    public GameObject() {
        this.width = 1;
        this.height = 1;
    }

    public char getCharactor() {
        return Charactor;
    }
    public void setCharactor(char Charactor) {
        this.Charactor = Charactor;
    }

    private Sprite sprite;

    public Sprite getSprite(Texture texture) {
        if (sprite == null) {
            sprite = new Sprite(texture);
        }

        return sprite;
    }
    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void startDayAutomaticTask() {}
    public void turnByTurnAutomaticTask() {}

    public String getIcon() {
        return null;
    }


    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
