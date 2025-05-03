package model.ToolsPackage;

import static model.App.currentWeather;

public class Scythe extends Tools {

    public Scythe() {
        super("Scythe", 0);
    }

    @Override
    public String getName() {
        return "Scythe";
    }

    @Override
    public int healthCost() {

        return (int) (2*currentWeather.getEnergyCostCoefficient());
    }
}
