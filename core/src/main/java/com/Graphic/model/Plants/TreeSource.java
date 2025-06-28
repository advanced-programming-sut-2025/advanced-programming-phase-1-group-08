package com.Graphic.model.Plants;

import com.Graphic.model.Enum.AllPlants.TreesSourceType;
import com.Graphic.model.Items;

public class TreeSource extends Items {

    private TreesSourceType type;

    public TreeSource (TreesSourceType type) {
        this.type = type;
    }

    public TreesSourceType getType() {
        return type;
    }

    @Override
    public String getName() {
        return type.getDisplayName();
    }

    @Override
    public int getSellPrice() {
        return type.getPrice();
    }
}
