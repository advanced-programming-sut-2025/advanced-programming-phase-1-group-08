package model.Enum.WeatherTime;

import static model.Color_Eraser.*;
import static model.Color_Eraser.CYAN;
import static model.Color_Eraser.RESET;

public enum Weather {

    Sunny ("☀️Sunny☀️",1.5, 1),
    Rainy ("\uD83C\uDF27Rainy\uD83C\uDF27",1.2, 1.5),
    Stormy("\uD83C\uDF2AStormy\uD83C\uDF2A",0.5, 1.5),
    Snowy ("☃️Snowy☃️",1, 2);

    private final String displayName;
    private final double fishing;
    private final double energyCostCoefficient;

    Weather(String displayName, double fishing, double energyCostCoefficient) {
        this.displayName = displayName;
        this.fishing = fishing;
        this.energyCostCoefficient = energyCostCoefficient;
    }

    public double getFishing() {

        return fishing;
    }
    public String getDisplayName() {

        if (this.equals(Sunny))
            return YELLOW+displayName+RESET;
        if (this.equals(Stormy))
            return PURPLE+displayName+RESET;
        if (this.equals(Snowy))
            return CYAN+displayName+RESET;
        else
            return BLUE+displayName+RESET;
    }
    public double getEnergyCostCoefficient() {

        return energyCostCoefficient;
    }
}