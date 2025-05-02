package model;

import model.Enum.ItemType.AnimalProductType;
import model.Enum.ItemType.Quantity;

public class Animalproduct extends Items{

    private final AnimalProductType animalProductType;
    private final Quantity quantity;

    public Animalproduct(AnimalProductType animalProductType, Quantity quantity) {
        this.animalProductType = animalProductType;
        this.quantity = quantity;
    }

    public AnimalProductType getAnimalProductType() {
        return animalProductType;
    }

    public Quantity getQuantity() {
        return quantity;
    }
}
