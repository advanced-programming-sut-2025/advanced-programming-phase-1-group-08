package model.Plants;

import model.Enum.AllPlants.ForagingCropsType;
import model.Items;

public class ForagingCrops extends Items {

    private ForagingCropsType type;

    public void setType(ForagingCropsType type) {
        this.type = type;
    }

    public ForagingCropsType getType() {
        return type;
    }
}
