package model.Enum.ItemType;

public enum AnimalProductType {

    Egg("Egg" , 50),
    bigEgg("Big Egg",95),

    duckEgg("Duck Egg", 95),
    duckFeather("Duck Feather", 250),

    rabbits_Wool("Rabbit's Wool" , 340),
    rabbits_Foot("Rabbit's Foot", 565),

    dinosaurEgg("Dinosaur Egg" , 350),

    milk("Milk" , 125),
    bigMilk("Big Milk", 190),

    goatMilk("Cow Milk" , 225),
    bigGoatMilk("Big Cow Milk" , 345),

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
}
