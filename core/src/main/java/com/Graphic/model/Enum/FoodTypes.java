package com.Graphic.model.Enum;

public enum FoodTypes {
    friedEgg(35 , "Fried Egg", "Ariyo/Recipe/Fried_Egg.png"),
    bakedFish(100 , "Baked Fish", "Ariyo/Recipe/Baked_Fish.png"),
    salad(110 , "Salad", "Ariyo/Recipe/Salad.png"),
    omelet(125,"Omelet", "Ariyo/Recipe/Omelet.png"),
    pumpkinPie(385 , "Pumpkin Pie", "Ariyo/Recipe/Pumpkin_Pie.png"),
    spaghetti(120 , "Spaghetti", "Ariyo/Recipe/Spaghetti.png"),
    pizza(300 , "Pizza", "Ariyo/Recipe/Pizza.png"),
    tortilla(50 , "Tortilla", "Ariyo/Recipe/Tortilla.png"),
    makiRoll(220 , "Maki Roll", "Ariyo/Recipe/Maki_Roll.png"),
    tripleShotEspresso(450 , "Triple Shot Espresso", "Ariyo/Recipe/Triple_Shot_Espresso.png"),
    cookie(140 , "Cookie", "Ariyo/Recipe/Cookie.png"),
    hashBrowns(120, "Hash Browns", "Ariyo/Recipe/Hashbrowns.png"),
    pancakes(80 , "Pancakes", "Ariyo/Recipe/Pancakes.png"),
    fruitSalad(450 , "Fruit Salad", "Ariyo/Recipe/Fruit_Salad.png"),
    redPlate(400 , "Red Plate", "Ariyo/Recipe/Red_Plate.png"),
    bread(60 , "Bread", "Ariyo/Recipe/Bread.png"),
    salmonDinner(300 , "Salmon Dinner", "Ariyo/Recipe/Salmon_Dinner.png"),
    vegetableMedley(120 , "Vegetable Medley", "Ariyo/Recipe/Vegetable_Medley.png"),
    survivalBurger(180 , "Survival Burger", "Ariyo/Recipe/Survival_Burger.png"),
    seaformPudding(300 , "Seaform Pudding", "Ariyo/Recipe/Seafoam_Pudding.png"),
    minersTreat(200 , "Miner's Treat", "Ariyo/Recipe/Miner%27s_Treat.png");

    private final int sellPrice;
    private final String name;
    private final String address;

    FoodTypes(int sellPrice, String name, String address) {
        this.sellPrice = sellPrice;
        this.name = name;
        this.address = address;
    }


    public int getSellPrice() {
        return sellPrice;
    }

    public String getName() {
        return name;
    }
    public String getAddress() {
        return address;
    }
}
