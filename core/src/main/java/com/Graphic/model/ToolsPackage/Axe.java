package com.Graphic.model.ToolsPackage;

import com.Graphic.model.Enum.ToolsType.AxeType;

import static com.Graphic.model.App.*;

public class Axe extends Tools {

    private AxeType type;
    public Axe (AxeType type) {
        super("Axe");
        this.type = type;
    }

    public AxeType getType() {

        return type;
    }
    public void setType(AxeType type) {

        this.type = type;
    }


    @Override
    public int healthCost() {

        double x = currentGame.currentWeather.getEnergyCostCoefficient();

        if (currentGame.currentPlayer.getLevelMining() == 4)
            return Math.min((int) (this.type.getEnergyCost()*x)+1, 0);

        return Math.min((int) (this.type.getEnergyCost()*x), 0);
    }

    public String getName() {

        return type.getDisplayName();
    }
    public int getSellPrice() {
        return 0;
    }
    public String getInventoryIconPath () {
        return this.type.getIconPath();
    }


}
