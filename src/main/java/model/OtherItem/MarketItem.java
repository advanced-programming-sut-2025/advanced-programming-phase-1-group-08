package model.OtherItem;

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
}
