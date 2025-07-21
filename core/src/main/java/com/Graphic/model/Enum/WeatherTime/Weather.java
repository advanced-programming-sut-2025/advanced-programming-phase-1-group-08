package com.Graphic.model.Enum.WeatherTime;

import static com.Graphic.model.HelpersClass.Color_Eraser.*;
import static com.Graphic.model.HelpersClass.Color_Eraser.CYAN;
import static com.Graphic.model.HelpersClass.Color_Eraser.RESET;

public enum Weather {

    Sunny ("☀️ Sunny☀️",1.5, 1),
    Rainy ("\uD83C\uDF27 Rainy\uD83C\uDF27",1.2, 1.5),
    Stormy("\uD83C\uDF2A Stormy\uD83C\uDF2A",0.5, 1.5),
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
    public String getIconPath() {
        return "Erfan/Clock/Weather Icon/" + this.name() + ".png";
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
