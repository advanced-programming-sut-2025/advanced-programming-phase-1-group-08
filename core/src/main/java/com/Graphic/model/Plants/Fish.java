package com.Graphic.model.Plants;

import com.Graphic.model.Enum.ItemType.FishType;
import com.Graphic.model.Enum.ItemType.Quantity;
import com.Graphic.model.Items;

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
    public String getInventoryIconPath() {
        return "";
    }

    @Override
    public int getSellPrice() {
        return (int) ( type.getPrice() * quantity.getValue());
    }


}
