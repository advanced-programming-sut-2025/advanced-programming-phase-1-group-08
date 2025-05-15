package model.ToolsPackage;

import static model.App.currentWeather;

public class Scythe extends Tools {

    public Scythe() {
        super("Scythe");
    }


    @Override
    public int healthCost() {

        return Math.max((int) (2*currentWeather.getEnergyCostCoefficient()), 0);
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
