package com.Graphic.model.Places;

import com.Graphic.model.MapThings.Tile;

import java.util.ArrayList;

public class Farm {

    private final int width=30;
    private final int height=30;
    private GreenHouse greenHouse;
    private int x;
    private int y;
    private Mine mine;
    private Home home;
    private Lake lake;
    private int index;

    public ArrayList<Tile> Farm = new ArrayList<>();
    public ArrayList<ShippingBin> shippingBins = new ArrayList<>();


    public void setGreenHouse(GreenHouse greenHouse) {
        this.greenHouse = greenHouse;
    }
    public void setMine(Mine mine) {
        this.mine = mine;
    }
    public void setHome(Home home) {
        this.home = home;
    }
    public void setLake(Lake lake) {
        this.lake = lake;
    }
    public Lake getLake() {
        return lake;
    }

    public GreenHouse getGreenHouse() {
        return greenHouse;
    }

    public Mine getMine() {
        return mine;
    }
    public boolean isInGreenHouse (int x, int y) {

        return (x >= greenHouse.getCoordinateX() &&
                y >= greenHouse.getCoordinateY() &&
                x < greenHouse.getCoordinateX() + greenHouse.getLength() &&
                y < greenHouse.getCoordinateY() + greenHouse.getWidth());
    }
    public boolean isInFarm (int x, int y) {
        return (x > this.x && x < this.x+width &&
                y > this.y && y < this.y+height);
    }
    public boolean isInMine (int x, int y) {
        return (x >= mine.getStartX() &&
                y >= mine.getStartY() &&
                x < mine.getStartX() + mine.getWidth() &&
                y < mine.getStartY() + mine.getHeight());
    }
    public boolean isInHome (int x, int y) {
        return (x>= home.getTopLeftX() && x < home.getTopLeftX() + home.getWidth()
                && y>=home.getTopLeftY() && y < home.getTopLeftY() + home.getLength());
    }
    public Home getHome () {
        return home;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
}
