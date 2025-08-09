package com.Graphic.model.MapThings;

import com.Graphic.model.Enum.ItemType.MarketType;

import java.util.Random;

import static com.Graphic.model.HelpersClass.Color_Eraser.*;

public class UnWalkable extends GameObject {

    private transient Random rand = new Random();
    int x;

    public UnWalkable() {
        x = rand.nextInt();
        x = (x % 6) + 1;
        if (x == 4 || x == 5) {
            super.setTextureHeight(1.5f);
            super.setTextureWidth(1.2f);
        }

    }
    @Override
    public String getIcon() {
        return "Tree/unWalkable"+x+".png";
    }

    @Override
    public int getRemindInShop(MarketType marketType) {
        return 0;
    }

    @Override
    public void setRemindInShop(int amount, MarketType marketType) {

    }
}
