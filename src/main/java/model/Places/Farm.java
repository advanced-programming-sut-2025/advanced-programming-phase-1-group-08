package model.Places;

import model.Tile;

import java.util.ArrayList;

public class Farm {

    private final int width=30;
    private final int height=30;
    private GreenHouse greenHouse;
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
    public void setHome(Home home) {
        this.home = home;
    }
    public void setLake(Lake lake) {
        this.lake = lake;
    }

    public GreenHouse getGreenHouse() {
        return greenHouse;
    }

    public Mine getMine() {
        return mine;
    }
}
