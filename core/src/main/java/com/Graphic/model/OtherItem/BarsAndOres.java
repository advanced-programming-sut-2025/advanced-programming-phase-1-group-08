package com.Graphic.model.OtherItem;

import com.Graphic.model.Enum.ItemType.BarsAndOreType;
import com.Graphic.model.Enum.ItemType.MarketType;
import com.Graphic.model.Items;

public class BarsAndOres extends Items {
    private final BarsAndOreType type;

    public BarsAndOres(BarsAndOreType type) {
        this.type = type;
    }

    public BarsAndOreType getType() {
        return type;
    }

    @Override
    public String getName() {
        return type.getName();
    }

    @Override
    public String getInventoryIconPath() {
        return type.getAddress();
    }

    @Override
    public int getSellPrice() {
        return type.getPrice();
    }

    @Override
    public int getTakesTime() {
        return 4;
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
        return type.getPrice();
    }
}
