package com.Graphic.model.Places;

import model.MapThings.GameObject;
import model.User;
import model.MapThings.WaterTank;

import static model.Color_Eraser.*;

public class GreenHouse extends GameObject {

    public static final int requiredWood = 500;
    public static final int requiredCoins = 1000;

    private final int length; // بدون دیوار
    private final int width; // بدوندیوار
    private int coordinateX; // برا اساس مپ عدد بدیم
    private int coordinateY; // برا اساس مپ عدد بدیم

    private User Owner;
    private WaterTank waterTank; // مقدار اب مخزن
    private boolean isCreated=false;

    public GreenHouse(int coordinateX, int coordinateY , int width , int height) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.width = width;
        this.length = width;
    }
    // اب پاش فراموش نشه


    public void setWaterTank(WaterTank waterTank) {
        this.waterTank = waterTank;
    }
    public void setCreated(boolean created) {
        isCreated = created;
    }
    public WaterTank getWaterTank() {
        return waterTank;
    }
    public boolean isCreated() {
        return isCreated;
    }

    public String getIcon () {
        if (isCreated)
            return GREEN+"G "+ RESET;
        else
            return GRAY+"G "+ RESET;
    }
    public int getLength() {
        return length;
    }
    public int getWidth() {
        return width;
    }
    public int getCoordinateX() {
        return coordinateX;
    }
    public int getCoordinateY() {
        return coordinateY;
    }

}
