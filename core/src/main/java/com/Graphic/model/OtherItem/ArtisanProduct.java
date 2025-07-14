package com.Graphic.model.OtherItem;

import com.Graphic.model.Enum.ItemType.ArtisanType;
import com.Graphic.model.Enum.ItemType.MarketType;
import com.Graphic.model.Items;

public class ArtisanProduct extends Items {

    private ArtisanType type;
    public ArtisanProduct(ArtisanType type ) {
        this.type = type;
    }
    public ArtisanType getType() {
        return type;
    }

    @Override
    public String getName() {
        return type.getName();
    }

    @Override
    public String getInventoryIconPath() {
        return "";
    }

    @Override
    public int getSellPrice() {
        return type.getPrice();
    }

    @Override
    public int getTakesTime() {
        return type.getTakesTime();
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
