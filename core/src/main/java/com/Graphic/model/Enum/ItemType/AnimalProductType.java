package com.Graphic.model.Enum.ItemType;

import static com.Graphic.model.HelpersClass.Color_Eraser.RED;
import static com.Graphic.model.HelpersClass.Color_Eraser.RESET;

public enum AnimalProductType {

    Egg("Egg" , 50, "Ariyo/Animal_product/Egg.png"),
    bigEgg("Large Egg",95, "Ariyo/Animal_product/Large_Egg.png"),

    duckEgg("Duck Egg", 95, "Ariyo/Animal_product/Duck_Egg.png"),
    duckFeather("Duck Feather", 250, "Ariyo/Animal_product/Duck_Feather.png"),

    rabbits_Wool("Rabbit's Wool" , 340, "Ariyo/Animal_product/Wool.png"),
    rabbits_Foot("Rabbit's Foot", 565, "Ariyo/Animal_product/Rabbit%27s_Foot.png"),

    dinosaurEgg("Dinosaur Egg" , 350, "Ariyo/Animal_product/Dinosaur_Egg.png"),

    milk("Milk" , 125, "Ariyo/Animal_product/Milk.png"),
    bigMilk("Large Milk", 190, "Ariyo/Animal_product/Large_Milk.png"),

    goatMilk("Goat Milk" , 225, "Ariyo/Animal_product/Goat_Milk.png"),
    bigGoatMilk("Large Goat Milk" , 345, "Ariyo/Animal_product/Large_Goat_Milk.png"),

    sheeps_Wool("Sheep's Wool" , 340, "Ariyo/Animal_product/Wool.png"),

    Truffle("Truffle" , 625, "Ariyo/Animal_product/Truffle.png"),;

    private final String name;
    private final int initialPrice;
    private final String iconPath;

    AnimalProductType(String name, int initialPrice, String iconPath) {
        this.name = name;
        this.initialPrice = initialPrice;
        this.iconPath = iconPath;
    }

    public int getInitialPrice() {
        return initialPrice;
    }

    public String getName() {
        return name;
    }

    public String getIconPath() {
        return iconPath;
    }

    public static AnimalProductType fromDisplayName(String displayName) {
        for (AnimalProductType type : AnimalProductType.values())
            if (type.getName().equals(displayName))
                return type;
        throw new IllegalArgumentException(RED+"wrong name!"+RESET);
    }
}
