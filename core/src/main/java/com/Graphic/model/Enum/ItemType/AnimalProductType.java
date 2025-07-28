package com.Graphic.model.Enum.ItemType;

import static com.Graphic.model.HelpersClass.Color_Eraser.RED;
import static com.Graphic.model.HelpersClass.Color_Eraser.RESET;

public enum AnimalProductType {

    Egg("Egg" , 50 , "Mohamadreza/Animal Product/Egg.png"),
    bigEgg("Large Egg",95 , "Mohamadreza/Animal Product/Large_Egg.png"),

    duckEgg("Duck Egg", 95 , "Mohamadreza/Animal Product/Duck_Egg.png"),
    duckFeather("Duck Feather", 250 , "Mohamadreza/Animal Product/Duck_Feather.png"),

    rabbits_Wool("Rabbit's Wool" , 340 , "Mohamadreza/Animal Product/Wool.png"),
    rabbits_Foot("Rabbit's Foot", 565 , "Mohamadreza/Animal Product/Foot.png"),

    dinosaurEgg("Dinosaur Egg" , 350 , "Mohamadreza/Animal Product/Dinosaur_Egg.png"),

    milk("Milk" , 125 , "Mohamadreza/Animal Product/Milk.png"),
    bigMilk("Large Milk", 190 , "Mohamadreza/Animal Product/Large_Milk.png"),

    goatMilk("Goat Milk" , 225 , "Mohamadreza/Animal Product/Goat_Milk.png"),
    bigGoatMilk("Large Goat Milk" , 345 , "Mohamadreza/Animal Product/Large_Goat_Milk.png"),

    sheeps_Wool("Sheep's Wool" , 340 , "Mohamadreza/Animal Product/Wool.png"),

    Truffle("Truffle" , 625 , "Mohamadreza/Animal Product/Truffle.png");

    private final String name;
    private final int initialPrice;
    private final String Path;

    AnimalProductType(String name, int initialPrice , String Path) {
        this.name = name;
        this.initialPrice = initialPrice;
        this.Path = Path;
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

    public String getPath() {
        return Path;
    }
}
