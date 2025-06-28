package com.Graphic.model.ToolsPackage;

import com.Graphic.model.Enum.ToolsType.HoeType;

import static com.Graphic.model.App.*;

public class Hoe extends Tools {

    private HoeType type;

    public Hoe (HoeType type) {
        super("Hoe");
        this.type = type;
    }

    public HoeType getType() {

        return type;
    }
    public void setType(HoeType type) {

        this.type = type;
    }

    public int healthCost() {

        double x = currentGame.currentWeather.getEnergyCostCoefficient();

        if (currentGame.currentPlayer.getLevelFarming() == 4)
            return Math.min((int) (this.type.getEnergyCost()*x)+1, 0);

        return Math.min((int) (this.type.getEnergyCost()*x), 0);
    }

    @Override
    public String getName() {
        return type.name();
    }

    @Override
    public int getSellPrice() {
        return 0;
    }
}
