package com.Graphic.model.Plants;

import model.Enum.AllPlants.TreesProductType;
import model.Items;

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
    public int getSellPrice() {
        return type.getPrice();
    }
}
