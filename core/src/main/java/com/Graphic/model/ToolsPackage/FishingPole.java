package com.Graphic.model.ToolsPackage;

import com.Graphic.model.Enum.ToolsType.FishingPoleType;

public class FishingPole extends Tools {

    public FishingPole() {
        super("FishingPole");
    }

    public FishingPoleType type = null;

    public void use (){}

    @Override
    public int healthCost() { // TODO
        return 0;
    }
    @Override
    public String getName() {
        return type.getName();
    }

    @Override
    public int getSellPrice() {
        return 0;
    }
    @Override
    public String getInventoryIconPath() {
        return type.getIconPath();
    }
}
