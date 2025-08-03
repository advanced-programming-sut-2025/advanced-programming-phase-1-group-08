package com.Graphic.model.Plants;

import com.Graphic.model.Enum.ItemType.MarketType;
import com.Graphic.model.Items;

public class BasicRock extends Items {

    public static final int price=20;


    public BasicRock() {
        super.setTextureHeight(0.7f);
        super.setTextureWidth(0.7f);
    }

    @Override
    public String getIcon() {
        return "Stones/Basic_Rock.png";
    }

    @Override
    public String getName() {
        return "Stone";
    }

    @Override
    public String getInventoryIconPath() {
        return "Erfan/Stone.png";
    }

    @Override
    public int getSellPrice() {
        return 20;
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
        return price;
    }
}
