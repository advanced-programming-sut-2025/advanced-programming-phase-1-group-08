package model.ToolsPackage;

import model.Enum.ToolsType.HoeType;
import model.Enum.WeatherTime.Weather;
import model.Tile;

import java.util.ArrayList;

import static model.App.*;

public class Hoe extends Tools {

    public static ArrayList<Tile> plowedTile = new ArrayList<>();

    private HoeType type;

    public Hoe (HoeType type) {
        super("Hoe", 0);
        this.type = type;
    }

    public void use () {}


    public void kir (){}
    public HoeType getType() {

        return type;
    }
    public void setType(HoeType type) {

        this.type = type;
    }

    public int healthCost() {

        double x = 1;
        if (currentWeather.equals(Weather.Snowy))
            x = 2;
        if (currentWeather.equals(Weather.Rainy) || currentWeather.equals(Weather.Stormy))
            x = 1.5;

        if (currentPlayer.getLevelFarming() == 4)
            return (int) (this.type.getEnergyCost()*x)+1;
        return (int) (this.type.getEnergyCost()*x);
    }

}
