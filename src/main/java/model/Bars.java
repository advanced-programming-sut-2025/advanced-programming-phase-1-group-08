package model;

import model.Enum.ItemType.BarsType;

public class Bars {
    private final BarsType type;

    public Bars(BarsType type) {
        this.type = type;
    }

    public BarsType getType() {
        return type;
    }
}
