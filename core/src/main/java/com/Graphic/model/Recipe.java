package com.Graphic.model;

import com.Graphic.model.Plants.Animalproduct;
import com.Graphic.model.Plants.Fish;
import com.Graphic.model.Enum.AllPlants.CropsType;
import com.Graphic.model.Enum.AllPlants.ForagingCropsType;
import com.Graphic.model.Enum.AllPlants.TreesProductType;
import com.Graphic.model.Enum.FoodTypes;
import com.Graphic.model.Enum.ItemType.*;
import com.Graphic.model.OtherItem.ArtisanProduct;
import com.Graphic.model.Places.MarketItem;
import com.Graphic.model.Plants.AllCrops;
import com.Graphic.model.Plants.ForagingCrops;
import com.Graphic.model.Plants.TreesProdct;

import java.util.HashMap;
import java.util.List;

import static com.Graphic.model.App.currentGame;


public class Recipe {

    private String name;
    private boolean usable = false;
    private FoodTypes type;
    private HashMap<Items, Integer> ingredients = new HashMap<>(); // مقدار لازم از هر آیتم
    private int energyNeeded;
    private int sellPrice_golds;
    private Effect effect;

    public Recipe(){}

    public Recipe(String name, boolean usable, FoodTypes type, HashMap<Items, Integer> ingredients, int energyNeeded, int price,
                  Effect effect) {
        this.name = name;
        this.usable = usable;
        this.type = type;
        this.ingredients = ingredients;
        this.energyNeeded = energyNeeded;
        this.sellPrice_golds = price;
        this.effect = effect;
    }
    public void applyEffect(User currentPlayer) {
        if (effect != null) {
            effect.apply(currentPlayer);
        }
    }

    public String getName() {
        return name;
    }

    public boolean isUsable() { return usable; }
    public void setUsable(boolean usable) { this.usable = usable; }

    public HashMap<Items, Integer> getIngredients() { return ingredients; }
    public void setIngredients(HashMap<Items, Integer> ingredients) { this.ingredients = ingredients; }

    public int getEnergy() { return energyNeeded; }
    public void setEnergy(int energy) { this.energyNeeded = energy; }

    public double getPrice() { return sellPrice_golds; }
    public void setPrice(int price) { this.sellPrice_golds = price; }

    public void print() {
        System.out.println("Recipe: " + name.toUpperCase());
    }
    public static List<Recipe> createAllRecipes() {

        HashMap<Items, Integer> FriedEggIngredients = new HashMap<>();
        FriedEggIngredients.put(new Animalproduct(AnimalProductType.Egg, Quantity.Normal), 1);
        Recipe FriedEgg = new Recipe(
                "Fried Egg",
                true,
                FoodTypes.friedEgg,
                FriedEggIngredients,
                50,
                35,
                null
        );

        HashMap<Items, Integer> bakedFishIngredients = new HashMap<>();
        bakedFishIngredients.put(new Fish(FishType.Sardine, Quantity.Normal), 1);
        bakedFishIngredients.put(new Fish(FishType.Salmon, Quantity.Normal), 1);
        bakedFishIngredients.put(new MarketItem(MarketItemType.WheatFlour), 1);
        Recipe bakedFish = new Recipe(
                "Baked Fish",
                true,
                FoodTypes.bakedFish,
                bakedFishIngredients,
                75,
                100,
                null
        );

        HashMap<Items, Integer> saladIngredients = new HashMap<>();
        saladIngredients.put(new ForagingCrops(ForagingCropsType.Leek), 1);
        saladIngredients.put(new ForagingCrops(ForagingCropsType.Dandelion), 1);
        Recipe salad = new Recipe(
                "Salad",
                true,
                FoodTypes.salad,
                saladIngredients,
                113,
                110,
                null
        );

        HashMap<Items, Integer> omeletIngredients = new HashMap<>();
        omeletIngredients.put(new Animalproduct(AnimalProductType.Egg, Quantity.Normal), 1);
        omeletIngredients.put(new Animalproduct(AnimalProductType.milk, Quantity.Normal), 1);
        Recipe omelet = new Recipe(
                "Omelet",
                false,
                FoodTypes.omelet,
                omeletIngredients,
                100,
                125,
                null
        );

        HashMap<Items, Integer> pumpkinPieIng = new HashMap<>();
        pumpkinPieIng.put(new AllCrops(CropsType.Pumpkin), 1);
        pumpkinPieIng.put(new Animalproduct(AnimalProductType.milk, Quantity.Normal), 1);
        pumpkinPieIng.put(new MarketItem(MarketItemType.WheatFlour), 1);
        pumpkinPieIng.put(new MarketItem(MarketItemType.Sugar), 1);
        Recipe pumpkinPie = new Recipe(
                "Pumpkin Pie",
                true,
                FoodTypes.pumpkinPie,
                pumpkinPieIng,
                225,
                385,
                null
        );

        HashMap<Items, Integer> spaghettiIng = new HashMap<>();
        spaghettiIng.put(new AllCrops(CropsType.Tomato), 1);
        spaghettiIng.put(new MarketItem(MarketItemType.WheatFlour), 1);
        Recipe spaghetti = new Recipe(
                "Spaghetti",
                true,
                FoodTypes.spaghetti,
                spaghettiIng,
                75,
                120,
                null
        );

        HashMap<Items, Integer> pizzaIng = new HashMap<>();
        pizzaIng.put(new AllCrops(CropsType.Tomato), 1);
        pizzaIng.put(new MarketItem(MarketItemType.WheatFlour), 1);
        pizzaIng.put(new ArtisanProduct(ArtisanType.Cheese), 1);
        Recipe pizza = new Recipe(
                "Pizza",
                false,
                FoodTypes.pizza,
                pizzaIng,
                150,
                300,
                null
        );

        HashMap<Items, Integer> tortillaIng = new HashMap<>();
        tortillaIng.put(new AllCrops(CropsType.Corn), 1);
        Recipe tortilla = new Recipe(
                "Tortilla",
                false,
                FoodTypes.tortilla,
                tortillaIng,
                50,
                50,
                null
        );

        HashMap<Items, Integer> makiRollIng = new HashMap<>();
        makiRollIng.put(new MarketItem(MarketItemType.Rice), 1);
        makiRollIng.put(new ForagingCrops(ForagingCropsType.Fiber), 1);
        makiRollIng.put(new Fish(FishType.Tilapia, Quantity.Normal), 1);
        Recipe makiRoll = new Recipe(
                "Maki Roll",
                false,
                FoodTypes.makiRoll,
                makiRollIng,
                100,
                220,
                null
        );

        HashMap<Items, Integer> coffeeIng = new HashMap<>();
        coffeeIng.put(new MarketItem(MarketItemType.Coffee), 3);
        Recipe tripleShotEspresso = new Recipe(
                "Triple Shot Espresso",
                false,
                FoodTypes.tripleShotEspresso,
                coffeeIng,
                200,
                450,
                User -> currentGame.currentPlayer.setBuff_maxEnergy_100_hoursLeft(5)
        );

        HashMap<Items, Integer> cookieIng = new HashMap<>();
        cookieIng.put(new MarketItem(MarketItemType.WheatFlour), 1);
        cookieIng.put(new MarketItem(MarketItemType.Sugar), 1);
        cookieIng.put(new Animalproduct(AnimalProductType.Egg, Quantity.Normal), 1);
        Recipe cookie = new Recipe(
                "Cookie",
                false,
                FoodTypes.cookie,
                cookieIng,
                90,
                140,
                null
        );

        HashMap<Items, Integer> hashBrownsIng = new HashMap<>();
        hashBrownsIng.put(new MarketItem(MarketItemType.Oil), 1);
        hashBrownsIng.put(new AllCrops(CropsType.Potato), 1);
        Recipe hashBrowns = new Recipe(
                "Hash Browns",
                false,
                FoodTypes.hashBrowns,
                hashBrownsIng,
                90,
                120,
                User -> currentGame.currentPlayer.setBuff_farming_hoursLeft(5)
        );

        HashMap<Items, Integer> pancakesIng = new HashMap<>();
        pancakesIng.put(new MarketItem(MarketItemType.WheatFlour), 1);
        pancakesIng.put(new Animalproduct(AnimalProductType.Egg, Quantity.Normal), 1);
        Recipe pancakes = new Recipe(
                "Pancakes",
                false,
                FoodTypes.pancakes,
                pancakesIng,
                90,
                80,
                User -> currentGame.currentPlayer.setBuff_foraging_hoursLeft(11)
        );

        HashMap<Items, Integer> fruitSaladIng = new HashMap<>();
        fruitSaladIng.put(new AllCrops(CropsType.Blueberry), 1);
        fruitSaladIng.put(new AllCrops(CropsType.Melon), 1);
        fruitSaladIng.put(new TreesProdct(TreesProductType.Apricot), 1);
        Recipe fruitSalad = new Recipe(
                "Fruit Salad",
                true,
                FoodTypes.fruitSalad,
                fruitSaladIng,
                263,
                450,
                null
        );

        HashMap<Items, Integer> redPlateIng = new HashMap<>();
        redPlateIng.put(new AllCrops(CropsType.RedCabbage), 1);
        redPlateIng.put(new AllCrops(CropsType.Radish), 1);
        Recipe redPlate = new Recipe(
                "Red Plate",
                true,
                FoodTypes.redPlate,
                redPlateIng,
                240,
                400,
                User -> currentGame.currentPlayer.setBuff_maxEnergy_50_hoursLeft(3)
        );

        HashMap<Items, Integer> breadIng = new HashMap<>();
        breadIng.put(new MarketItem(MarketItemType.WheatFlour), 1);
        Recipe bread = new Recipe(
                "Bread",
                false,
                FoodTypes.bread,
                breadIng,
                50,
                60,
                null
        );

        HashMap<Items, Integer> salmonDinnerIng = new HashMap<>();
        salmonDinnerIng.put(new Fish(FishType.Salmon, Quantity.Normal), 1);
        salmonDinnerIng.put(new AllCrops(CropsType.Amaranth), 1);
        salmonDinnerIng.put(new AllCrops(CropsType.Kale), 1);
        Recipe salmonDinner = new Recipe(
                "Salmon Dinner",
                false,
                FoodTypes.salmonDinner,
                salmonDinnerIng,
                125,
                300,
                null
        );

        HashMap<Items, Integer> vegetableMedleyIng  = new HashMap<>();
        vegetableMedleyIng.put(new AllCrops(CropsType.Tomato), 1);
        vegetableMedleyIng.put(new AllCrops(CropsType.Beet), 1);
        Recipe vegetableMedley  = new Recipe(
                "Vegetable Medley",
                false,
                FoodTypes.vegetableMedley,
                vegetableMedleyIng,
                165,
                120,
                null
        );

        HashMap<Items, Integer> survivalBurgerIng  = new HashMap<>();
        survivalBurgerIng.put(new MarketItem(MarketItemType.Bread), 1);
        survivalBurgerIng.put(new AllCrops(CropsType.Carrot), 1);
        survivalBurgerIng.put(new AllCrops(CropsType.Eggplant), 1);
        Recipe survivalBurger  = new Recipe(
                "Survival Burger",
                false,
                FoodTypes.survivalBurger,
                survivalBurgerIng,
                125,
                180,
                User -> currentGame.currentPlayer.setBuff_foraging_hoursLeft(5)
        );

        HashMap<Items, Integer> seaFormPuddingIng  = new HashMap<>();
        survivalBurgerIng.put(new Fish(FishType.Flounder, Quantity.Normal), 1);
        survivalBurgerIng.put(new Fish(FishType.Midnight_Carp, Quantity.Normal), 1);
        Recipe seaFormPudding  = new Recipe(
                "Seaform Pudding",
                false,
                FoodTypes.seaformPudding,
                seaFormPuddingIng,
                175,
                300,
                User -> currentGame.currentPlayer.setBuff_fishing_hoursLeft(10)
        );

        HashMap<Items, Integer> minersTreatIng  = new HashMap<>();
        minersTreatIng.put(new AllCrops(CropsType.Carrot), 2);
        minersTreatIng.put(new Animalproduct(AnimalProductType.milk, Quantity.Normal), 1);
        minersTreatIng.put(new MarketItem(MarketItemType.Sugar), 1);
        Recipe minersTreat  = new Recipe(
                "Miner's Treat",
                false,
                FoodTypes.minersTreat,
                minersTreatIng,
                125,
                200,
                User -> currentGame.currentPlayer.setBuff_mining_hoursLeft(5)
        );

        return List.of(FriedEgg, bakedFish, salad, omelet, pumpkinPie, spaghetti, pizza, tortilla, makiRoll
                , tripleShotEspresso, cookie, hashBrowns, pancakes, fruitSalad, redPlate, bread
                , salmonDinner, vegetableMedley, survivalBurger, seaFormPudding, minersTreat);
    }

    public FoodTypes getType() {
        return type;
    }

    public static Recipe findRecipeByName(String name) {

        for (Recipe recipe: currentGame.currentPlayer.getRecipes()) {
            if (recipe.getName().equals(name) || recipe.getName().replace(" ", "").equals(name.toLowerCase()))
                return recipe;
        }

        return null;
    }
}

