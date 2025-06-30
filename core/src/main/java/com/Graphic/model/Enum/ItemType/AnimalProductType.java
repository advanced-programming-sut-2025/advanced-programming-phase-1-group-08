package com.Graphic.model.Enum.ItemType;

import static com.Graphic.model.HelpersClass.Color_Eraser.RED;
import static com.Graphic.model.HelpersClass.Color_Eraser.RESET;

public enum AnimalProductType {

    Egg("Egg" , 50),
    bigEgg("Large Egg",95),

    duckEgg("Duck Egg", 95),
    duckFeather("Duck Feather", 250),

    rabbits_Wool("Rabbit's Wool" , 340),
    rabbits_Foot("Rabbit's Foot", 565),

    dinosaurEgg("Dinosaur Egg" , 350),

    milk("Milk" , 125),
    bigMilk("Large Milk", 190),

    goatMilk("Goat Milk" , 225),
    bigGoatMilk("Large Goat Milk" , 345),

    sheeps_Wool("Sheep's Wool" , 340),

    Truffle("Truffle" , 625);

    private final String name;
    private final int initialPrice;

    AnimalProductType(String name, int initialPrice) {
        this.name = name;
        this.initialPrice = initialPrice;
    }

    public int getInitialPrice() {
        return initialPrice;
    }

    public String getName() {
        return name;
    }

    public static AnimalProductType fromDisplayName(String displayName) {
        for (AnimalProductType type : AnimalProductType.values())
            if (type.getName().equals(displayName))
                return type;
        throw new IllegalArgumentException(RED+"wrong name!"+RESET);
    }
}
