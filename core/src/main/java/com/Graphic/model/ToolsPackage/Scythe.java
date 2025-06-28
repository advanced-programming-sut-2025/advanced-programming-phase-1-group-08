package com.Graphic.model.ToolsPackage;

import static com.Graphic.model.App.currentGame;
public class Scythe extends Tools {

    public Scythe() {
        super("Scythe");
    }


    @Override
    public int healthCost() {

        return Math.min((int) (-2*currentGame.currentWeather.getEnergyCostCoefficient()), 0);
    }

    @Override
    public String getName() {
        return "Scythe";
    }

    @Override
    public int getSellPrice() {
        return 0;
    }
}
