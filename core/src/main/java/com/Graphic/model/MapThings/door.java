package com.Graphic.model.MapThings;


import com.Graphic.model.Enum.Door;
import com.Graphic.model.Enum.ItemType.MarketType;

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

    @Override
    public int getRemindInShop(MarketType marketType) {
        return 0;
    }

    @Override
    public void setRemindInShop(int amount, MarketType marketType) {

    }
}
