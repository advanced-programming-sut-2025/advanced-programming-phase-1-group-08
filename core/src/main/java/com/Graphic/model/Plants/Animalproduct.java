package com.Graphic.model.Plants;

import com.Graphic.model.Enum.ItemType.AnimalProductType;
import com.Graphic.model.Enum.ItemType.Quantity;
import com.Graphic.model.Items;

public class Animalproduct extends Items {

    private final AnimalProductType type;
    private final Quantity quantity;

    public Animalproduct(AnimalProductType animalProductType, Quantity quantity) {
        this.type = animalProductType;
        this.quantity = quantity;
    }

    public AnimalProductType getType() {
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
        return (int) (type.getInitialPrice() * quantity.getValue() );
    }
}
