package model.Enum.WeatherTime;

import static model.Color_Eraser.*;
import static model.Color_Eraser.CYAN;
import static model.Color_Eraser.RED;
import static model.Color_Eraser.RESET;

public enum Weather {

    Sunny ("☀\uFE0FSunny☀\uFE0F"),
    Rainy ("\uD83C\uDF27Rainy\uD83C\uDF27"),
    Stormy ("\uD83C\uDF2AStormy\uD83C\uDF2A"),
    Snowy ("☃\uFE0FSnowy☃\uFE0F");

    private final String displayName;

    Weather(String displayName) {
        this.displayName = displayName;
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

}
