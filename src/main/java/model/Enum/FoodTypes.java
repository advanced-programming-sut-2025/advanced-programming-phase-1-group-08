package model.Enum;

public enum FoodTypes {
    friedEgg(35 , "Fried Egg"),
    bakedFish(100 , "Baked Fish"),
    salad(110 , "Salad"),
    omelet(125,"Omelet"),
    pumpkinPie(385 , "Pumpkin Pie"),
    spaghetti(120 , "Spaghetti"),
    pizza(300 , "Pizza"),
    tortilla(50 , "Tortilla"),
    makiRoll(220 , "Maki Roll"),
    tripleShotEspresso(450 , "Triple Shot Espresso"),
    cookie(140 , "Cookie"),
    hashBrowns(120, "Hash Browns"),
    pancakes(80 , "Pancakes"),
    fruitSalad(450 , "Fruit Salad"),
    redPlate(400 , "Red Plate"),
    bread(60 , "Bread"),
    salmonDinner(300 , "Salmon Dinner"),
    vegetableMedley(120 , "Vegetable Medley"),
    survivalBurger(180 , "Survival Burger"),
    seaformPudding(300 , "Seaform Pudding"),
    minersTreat(200 , "Miner's Treat");

    private final int sellPrice;
    private final String name;

    FoodTypes(int sellPrice, String name) {
        this.sellPrice = sellPrice;
        this.name = name;
    }


    public int getSellPrice() {
        return sellPrice;
    }

    public String getName() {
        return name;
    }
}