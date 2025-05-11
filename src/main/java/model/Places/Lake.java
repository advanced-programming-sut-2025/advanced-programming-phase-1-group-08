package model.Places;

import model.MapThings.GameObject;

public class Lake extends GameObject {
    private final int topLeftX;
    private final int topLeftY;
    private final int width;
    private final int height;

    public Lake(int x, int y, int width, int height) {
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
}
