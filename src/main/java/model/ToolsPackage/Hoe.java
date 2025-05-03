package model.ToolsPackage;

import model.Enum.ToolsType.HoeType;
import model.Enum.WeatherTime.Weather;
import model.MapThings.Tile;

import java.util.ArrayList;

import static model.App.*;

public class Hoe extends Tools {

    private HoeType type;

    public Hoe (HoeType type) {
        super("Hoe", 0);
        this.type = type;
    }

    public HoeType getType() {

        return type;
    }
    public void setType(HoeType type) {

        this.type = type;
    }

    public int healthCost() {

        double x = currentWeather.getEnergyCostCoefficient();

        if (currentPlayer.getLevelFarming() == 4)
            return (int) (this.type.getEnergyCost()*x)+1;
        return (int) (this.type.getEnergyCost()*x);
    }

}
