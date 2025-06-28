package com.Graphic.model.Plants;

import model.Enum.ItemType.FishType;
import model.Enum.ItemType.Quantity;
import model.Items;

public class Fish extends Items {

    private FishType type;
    private Quantity quantity;

    public Fish(FishType fishType, Quantity quantity) {
        this.type = fishType;
        this.quantity = quantity;
    }

    public FishType getType() {
        return type;
    }
    public Quantity getQuantity() {
        return quantity;
    }

    @Override
    public String getName() {
        return type.getName();
    }

    @Override
    public int getSellPrice() {
        return (int) ( type.getPrice() * quantity.getValue());
    }


}
