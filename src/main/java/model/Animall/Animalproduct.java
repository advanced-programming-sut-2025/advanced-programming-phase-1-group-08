package model.Animall;

import model.Enum.ItemType.AnimalProductType;
import model.Enum.ItemType.Quantity;
import model.Items;

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
    public int getSellPrice() {
        return 0;
    }
}
