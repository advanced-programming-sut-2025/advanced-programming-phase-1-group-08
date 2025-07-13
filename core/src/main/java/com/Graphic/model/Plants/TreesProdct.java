package com.Graphic.model.Plants;

import com.Graphic.model.Enum.AllPlants.TreesProductType;
import com.Graphic.model.Items;

public class TreesProdct extends Items {

    private TreesProductType type;


    public TreesProdct(TreesProductType type) {
        this.type = type;
    }

    public void setType(TreesProductType type) {
        this.type = type;
    }

    public TreesProductType getType() {
        return type;
    }

    @Override
    public String getName() {
        return type.getDisplayName();
    }

    @Override
    public String getInventoryIconPath() {
        return type.getInventoryIconPath();
    }

    @Override
    public int getSellPrice() {
        return type.getPrice();
    }
}
