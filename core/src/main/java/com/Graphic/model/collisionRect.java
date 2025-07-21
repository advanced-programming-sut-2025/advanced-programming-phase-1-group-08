package com.Graphic.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import static com.Graphic.model.HelpersClass.TextureManager.TEXTURE_SIZE;

public class collisionRect {
    private int x;
    private int y;
    private int width;
    private int height;

    public collisionRect(int x, int width, int y, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean checkCollision(User user) {
        if (user.sprite.getX() >= x && user.sprite.getX() <= x + width
            && user.sprite.getY() >= y && user.sprite.getY() <= y + height) {
            return false;
        }
        return true;
    }

    public boolean checkCollisionMouse(Vector3 mouse) {
        if (mouse.x >= x && mouse.x <= (x + width) &&
            mouse.y >= y  && mouse.y <= (y + height ) ) {
            return true;
        }
        return false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
}
