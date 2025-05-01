package model.Enum.WeatherTime;

import static model.Color_Eraser.*;
import static model.Color_Eraser.CYAN;
import static model.Color_Eraser.RESET;

public enum Weather {

    Sunny ("☀\uFE0FSunny☀\uFE0F",1.5),
    Rainy ("\uD83C\uDF27Rainy\uD83C\uDF27",1.2),
    Stormy ("\uD83C\uDF2AStormy\uD83C\uDF2A",0.5),
    Snowy ("☃\uFE0FSnowy☃\uFE0F",1);

    private final String displayName;
    private final double fishing;

    Weather(String displayName,double fishing) {
        this.displayName = displayName;
        this.fishing = fishing;
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

    public double getFishing() {
        return fishing;
    }

}