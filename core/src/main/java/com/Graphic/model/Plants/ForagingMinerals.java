package com.Graphic.model.Plants;

import com.Graphic.model.Enum.AllPlants.ForagingMineralsType;
import com.Graphic.model.Enum.ItemType.MarketType;
import com.Graphic.model.Items;

public class ForagingMinerals extends Items {

    private final ForagingMineralsType type;


    public ForagingMinerals(ForagingMineralsType type) {
        this.type = type;
    }
    public ForagingMineralsType getType() {
        return type;
    }



    @Override
    public String getIcon () {
        return this.type.getTexturePath();
    }

    @Override
    public String getName() {
        return type.getDisplayName();
    }

    @Override
    public String getInventoryIconPath() {

        return type.getTexturePath();
    }

    @Override
    public int getSellPrice() {
        return type.getPrice();
    }

    @Override
    public int getTakesTime() {
        return 1;
    }

    @Override
    public int getRemindInShop(MarketType marketType) {
        return -1;
    }

    @Override
    public void setRemindInShop(int amount, MarketType marketType) {

    }

    @Override
    public int getMarketPrice(MarketType marketType) {
        return 0;
    }
}
