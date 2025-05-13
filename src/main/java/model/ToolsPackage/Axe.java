package model.ToolsPackage;

import model.Enum.ToolsType.AxeType;

import static model.App.currentPlayer;
import static model.App.currentWeather;

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

        double x = currentWeather.getEnergyCostCoefficient();

        if (currentPlayer.getLevelMining() == 4)
            return Math.max((int) (this.type.getEnergyCost()*x)+1, 0);

        return Math.max((int) (this.type.getEnergyCost()*x), 0);
    }

    @Override
    public String getName() {
        return type.getDisplayName();
    }
}
