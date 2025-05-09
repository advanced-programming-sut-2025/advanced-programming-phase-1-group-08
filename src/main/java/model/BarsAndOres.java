package model;

import model.Enum.ItemType.BarsAndOreType;

public class BarsAndOres extends Items {
    private final BarsAndOreType type;

    public BarsAndOres(BarsAndOreType type) {
        this.type = type;
    }

    public BarsAndOreType getType() {
        return type;
    }
}
