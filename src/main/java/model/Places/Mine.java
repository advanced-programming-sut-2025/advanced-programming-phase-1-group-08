package model.Places;

import model.MapThings.GameObject;

public class Mine extends GameObject {

    private final int startX;
    private final int startY;

    public Mine(int x, int y) {
        this.startX = x;
        this.startY = y;
    }

    public int getStartX() {
        return startX;
    }
    public int getStartY() {
        return startY;
    }
}
