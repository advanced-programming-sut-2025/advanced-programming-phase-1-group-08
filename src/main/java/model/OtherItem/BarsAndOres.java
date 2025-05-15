package model.OtherItem;

import model.Enum.ItemType.BarsAndOreType;
import model.Items;

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
    public int getSellPrice() {
        return type.getPrice();
    }
}
