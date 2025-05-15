package model.Enum;

public enum FoodTypes {
    friedEgg(35 , "Fried egg"),
    bakedFish(100 , "Baked Fish"),
    salad(110 , "Salad"),
    omelet(125,"Omelet"),
    pumpkinPie(385 , "pumpkine pie"),
    spaghetti(120 , "spaghetti"),
    pizza(300 , "pizza"),
    tortilla(50 , "Tortilla"),
    makiRoll(220 , "Maki Roll"),
    tripleShotEspresso(450 , "Triple Shot Espresso"),
    cookie(140 , "Cookie"),
    hashBrowns(120, "hash browns"),
    pancakes(80 , "pancakes"),
    fruitSalad(450 , "fruit salad"),
    redPlate(400 , "red plate"),
    bread(60 , "bread"),
    salmonDinner(300 , "salmon dinner"),
    vegetableMedley(120 , "vegetable medley"),
    survivalBurger(180 , "survival burger"),
    seaformPudding(300 , "seaform pudding"),
    minersTreat(200 , "miners treat");

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
