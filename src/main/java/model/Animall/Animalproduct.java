package model.Animall;

import model.Enum.ItemType.AnimalProductType;
import model.Enum.ItemType.Quantity;
import model.Items;

public class Animalproduct extends Items {

    private final AnimalProductType AnimalProductType;
    private final Quantity quantity;

    public Animalproduct(AnimalProductType animalProductType, Quantity quantity) {
        this.AnimalProductType = animalProductType;
        this.quantity = quantity;
    }

    public AnimalProductType getAnimalProductType() {
        return AnimalProductType;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    @Override
    public String getName() {
        return AnimalProductType.getName();
    }
}
