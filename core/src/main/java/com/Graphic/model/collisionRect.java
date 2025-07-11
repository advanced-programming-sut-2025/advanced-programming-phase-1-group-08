package com.Graphic.model;

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
}
