package model.Places;

import model.Enum.ItemType.MarketItemType;
import model.Items;

public class MarketItem extends Items {
    private MarketItemType type;

    public MarketItem(MarketItemType type) {
        this.type = type;
    }
    public MarketItemType getType() {
        return type;
    }

    @Override
    public String getName() {
        return type.getName();
    }

    @Override
    public int getSellPrice() {
        return type.getSellPrice();
    }
}
