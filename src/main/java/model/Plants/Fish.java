package model.Plants;

import model.Enum.ItemType.FishType;
import model.Enum.ItemType.Quantity;
import model.Items;

public class Fish extends Items {

    private FishType fishType;
    private Quantity quantity;

    public Fish(FishType fishType, Quantity quantity) {
        this.fishType = fishType;
        this.quantity = quantity;
    }

    public FishType getFishType() {
        return fishType;
    }
    public Quantity getQuantity() {
        return quantity;
    }

    @Override
    public String getName() {
        return fishType.getName();
    }


}
