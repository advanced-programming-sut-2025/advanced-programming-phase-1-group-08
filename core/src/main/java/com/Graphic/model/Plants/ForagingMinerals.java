package com.Graphic.model.Plants;

import com.Graphic.model.Enum.AllPlants.ForagingMineralsType;
import com.Graphic.model.Items;

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
        return this.type.getTexturePath();
    }

    @Override
    public String getName() {
        return type.getDisplayName();
    }

    @Override
    public String getInventoryIconPath() {

        return type.getTexturePath();
    }

    @Override
    public int getSellPrice() {
        return type.getPrice();
    }

    @Override
    public int getTakesTime() {
        return 1;
    }
}
