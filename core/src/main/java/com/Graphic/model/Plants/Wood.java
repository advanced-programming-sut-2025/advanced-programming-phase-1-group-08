package com.Graphic.model.Plants;


import com.Graphic.model.Enum.ItemType.MarketType;
import com.Graphic.model.Items;
import static com.Graphic.model.HelpersClass.Color_Eraser.*;

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
    public String getInventoryIconPath() {
        return "Erfan/Wood.png";
    }

    @Override
    public int getSellPrice() {
        return 10;
    }

    @Override
    public int getRemindInShop(MarketType marketType) {
        return Integer.MAX_VALUE;
    }

    @Override
    public void setRemindInShop(int amount, MarketType marketType) {

    }

    @Override
    public int getMarketPrice(MarketType marketType) {
        return 10;
    }
}
