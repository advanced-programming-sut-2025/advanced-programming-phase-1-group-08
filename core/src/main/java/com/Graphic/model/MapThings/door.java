package com.Graphic.model.MapThings;


import com.Graphic.model.Enum.Door;
import static com.Graphic.model.HelpersClass.Color_Eraser.*;

public class door extends GameObject {
    private Door door;

    public void setDoor(Door door) {
        this.door = door;
    }

    public Door getDoor() {
        return door;
    }

    @Override
    public String getIcon() {
        return BRIGHT_BROWN+"\uD83D\uDEAA";
    }
}
