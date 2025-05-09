package model;

import model.Enum.ItemType.MarketItemType;

public class MarketItem extends Items {
    private MarketItemType type;

    public MarketItem(MarketItemType type) {
        this.type = type;
    }
    public MarketItemType getType() {
        return type;
    }
}
