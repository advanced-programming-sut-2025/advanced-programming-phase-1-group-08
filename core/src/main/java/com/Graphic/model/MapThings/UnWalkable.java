package com.Graphic.model.MapThings;

import java.util.Random;

import static com.Graphic.model.HelpersClass.Color_Eraser.*;

public class UnWalkable extends GameObject {
    Random rand = new Random();
    int x = rand.nextInt();

    @Override
    public String getIcon() {
        x = (x % 6) + 1;
        return "Tree/unWalkable"+x+".png";
    }
}
