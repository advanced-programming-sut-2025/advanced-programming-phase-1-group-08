package com.Graphic.model.Places;

import com.Graphic.model.Enum.ItemType.MarketItemType;
import com.Graphic.model.Enum.ItemType.MarketType;
import com.Graphic.model.Items;

public class MarketItem extends Items {
    private MarketItemType type;

    public MarketItem(MarketItemType type) {
        this.type = type;
    }
    public MarketItemType getType() {
        return type;
    }

    public MarketItem() {

    }

    @Override
    public String getName() {
        return type.getName();
    }

    @Override
    public String getInventoryIconPath() {
        return type.getPath();
    }

    @Override
    public int getSellPrice() {
        return type.getSellPrice();
    }

    @Override
    public String getIcon() {
        return type.getPath();
    }

    @Override
    public int getRemindInShop(MarketType marketType) {
        if (marketType.equals(MarketType.PierreGeneralStore)) {
            return type.getInitialPierreShopsLimit();
        }
        return type.getOtherShopsLimit();
    }

    @Override
    public void setRemindInShop(int amount, MarketType marketType) {
        if (marketType.equals(MarketType.PierreGeneralStore)) {
            type.increasePierreShopsLimit(amount - type.getPierreShopsLimit());
        }
        else {
            type.increaseOtherShopsLimit(amount - type.getOtherShopsLimit());
            System.out.println(getRemindInShop(marketType));
        }
    }

    @Override
    public int getMarketPrice(MarketType marketType) {
        if (marketType.equals(MarketType.JojaMart)) {
            return type.getPrice(1);
        }
        return type.getPrice(0);
    }
}
