package model.Places;

import model.MapThings.Tile;

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

    public ArrayList<Tile> Farm = new ArrayList<>();


    public void setGreenHouse(GreenHouse greenHouse) {
        this.greenHouse = greenHouse;
    }
    public void setMine(Mine mine) {
        this.mine = mine;
    }
    public void setHome(Home home) {this.home = home;}
    public void setLake(Lake lake) {
        this.lake = lake;
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
        return (x >= this.x && x < this.x+width &&
                y >= this.y && y < this.y+height);
    }
    public boolean isInHome(int x, int y) {
        return (x>= home.getTopLeftX() && x < home.getTopLeftX() + home.getWidth()
                && y>=home.getTopLeftY() && y < home.getTopLeftY() + home.getLength());
    }
}
