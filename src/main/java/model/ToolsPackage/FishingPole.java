package model.ToolsPackage;

import model.Enum.ToolsType.FishingPoleType;

public class FishingPole extends Tools {

    public FishingPole() {
        super("FishingPole");
    }

    public FishingPoleType fishingPoleType = null;

    public void use (){}

    @Override
    public int healthCost() { // TODO
        return 0;
    }
}
