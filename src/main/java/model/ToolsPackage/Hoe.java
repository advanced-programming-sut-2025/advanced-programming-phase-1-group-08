package model.ToolsPackage;

import model.Enum.ToolsType.HoeType;

import static model.App.*;

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

        double x = currentWeather.getEnergyCostCoefficient();

        if (currentPlayer.getLevelFarming() == 4)
            return Math.max((int) (this.type.getEnergyCost()*x)+1, 0);

        return Math.max((int) (this.type.getEnergyCost()*x), 0);
    }

    @Override
    public String getName() {
        return type.name();
    }
}
