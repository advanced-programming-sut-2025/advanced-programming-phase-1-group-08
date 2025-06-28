package com.Graphic.model.Places;

import com.Graphic.model.MapThings.GameObject;

import static com.Graphic.model.Color_Eraser.*;

public class Mine extends GameObject {

    private final int startX;
    private final int startY;
    private final int width;
    private final int height;

    public Mine(int x, int y , int width, int height) {
        this.startX = x;
        this.startY = y;
        this.width = width;
        this.height = height;
    }

    public int getStartX() {
        return startX;
    }
    public int getStartY() {
        return startY;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }

    @Override
    public String getIcon() {
        return RED+"M ";
    }
}
