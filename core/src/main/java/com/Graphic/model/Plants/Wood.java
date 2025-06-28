package com.Graphic.model.Plants;


import com.Graphic.model.Items;
import static com.Graphic.model.Color_Eraser.*;

public class Wood extends Items {

    public static final int price = 10;
    public static final String name = "Wood";

    @Override
    public String getIcon() {
        return BRIGHT_BROWN + "| ";
    }

    @Override
    public String getName() {
        return "Wood";
    }

    @Override
    public int getSellPrice() {
        return 10;
    }
}
