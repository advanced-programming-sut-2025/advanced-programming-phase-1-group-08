package model.Plants;

import model.Enum.AllPlants.ForagingCropsType;
import model.Items;

public class ForagingCrops extends Items {

    private final ForagingCropsType type;

    public ForagingCrops(ForagingCropsType type) {
        this.type = type;
    }

    public ForagingCropsType getType() {
        return type;
    }
}
