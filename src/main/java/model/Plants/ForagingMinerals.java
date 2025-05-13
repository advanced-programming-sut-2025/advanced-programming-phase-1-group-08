package model.Plants;

import model.Enum.AllPlants.ForagingMineralsType;
import model.Items;

public class ForagingMinerals extends Items {

    private final ForagingMineralsType type;


    public ForagingMinerals(ForagingMineralsType type) {
        this.type = type;
    }
    public ForagingMineralsType getType() {
        return type;
    }

    @Override
    public String getIcon () {
        return this.type.getCharacter();
    }

    @Override
    public String getName() {
        return type.getDisplayName();
    }
}