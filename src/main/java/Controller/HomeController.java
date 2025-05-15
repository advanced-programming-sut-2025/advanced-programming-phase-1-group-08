package Controller;

import model.*;
import model.Enum.Commands.HomeMenuCommands;
import model.Enum.FoodTypes;
import model.Enum.ItemType.MarketItemType;
import model.Enum.Menu;
import model.OtherItem.Fridge;
import model.Places.MarketItem;
import model.Plants.Food;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static model.App.*;
import static model.Color_Eraser.*;

public class HomeController {
    public static boolean NotInHome(User user) {
        return !(user.getFarm().isInHome(user.getPositionX(), user.getPositionY()));
    }



    public static Result fridgePick (String input) {
        if (NotInHome(currentPlayer))
            return new Result(false, RED+"You're Not in Your Home!"+RESET);

        Items item = AllFromDisplayNames(HomeMenuCommands.fridgePut.getMatcher(input).group("item"));
        if (item == null)
            return new Result(false, RED+"Item Not Found!"+RESET);


        Fridge fridge = currentPlayer.getFarm().getHome().getFridge();

        boolean foundInFridge = false;
        for (Map.Entry<Items, Integer> e: fridge.items.entrySet()) { // از یخچال بردار
            if (e.getKey().equals(item)) {
                fridge.items.put(e.getKey(), e.getValue() - 1);
                fridge.items.entrySet().removeIf(entry -> entry.getValue()==null || entry.getValue() <= 0);
                foundInFridge = true;
                break;
            }
        }
        if (!foundInFridge)
            return new Result(false, RED+"You don't have it in Fridge!"+RESET);

        boolean alreadyInInventory = false;
        for (Map.Entry<Items, Integer> entry: currentPlayer.getBackPack().inventory.Items.entrySet()) {
            if (entry.getKey().equals(item)) {
                currentPlayer.getBackPack().inventory.Items.put(entry.getKey(), entry.getValue() + 1);
                alreadyInInventory = true;
                break;
            }
        }
        if (!alreadyInInventory)
            currentPlayer.getBackPack().inventory.Items.put(item, 1);

        return new Result(true, GREEN+"Done!"+RESET);

    }
    public static Result fridgePut (String input) {
        if (NotInHome(currentPlayer))
            return new Result(false, RED+"You're Not in Your Home!"+RESET);

        Items item = AllFromDisplayNames(HomeMenuCommands.fridgePut.getMatcher(input).group("item"));
        if (item == null)
            return new Result(false, RED+"Item Not Found!"+RESET);

        boolean foundInInventory = false;
        for (Map.Entry<Items, Integer> e: currentPlayer.getBackPack().inventory.Items.entrySet()) {
            if (e.getKey().equals(item)) {
                currentPlayer.getBackPack().inventory.Items.put(e.getKey(), e.getValue() - 1);
                currentPlayer.getBackPack().inventory.Items.entrySet().removeIf(entry -> entry.getValue()==null || entry.getValue() <= 0);
                foundInInventory = true;
                break;
            }
        }
        if (!foundInInventory)
            return new Result(false, RED+"You Don't Have it in Inventory!"+RESET);


        Fridge fridge = currentPlayer.getFarm().getHome().getFridge();

        boolean alreadyInFridge = false;
        for (Map.Entry<Items, Integer> entry: fridge.items.entrySet()) {
            if (entry.getKey().equals(item)) {
                fridge.items.put(entry.getKey(), entry.getValue() + 1);
                alreadyInFridge = true;
                break;
            }
        }
        if (!alreadyInFridge)
            fridge.items.put(item, 1);

        return new Result(true, GREEN+"Done!"+RESET);
    }
    public static Recipe findRecipeByName (String name) {
        for (Recipe recipe: currentPlayer.getRecipes()) {
            if (recipe.getName().equals(name))
                return recipe;
        }
        return null;
    }
    public static Result foodPrepare (String input) {
        if (NotInHome(currentPlayer))
            return new Result(false, RED+"You're Not in Your Home!"+RESET);

        String foodName = HomeMenuCommands.foodPrepare.getMatcher(input).group("food");
        if (foodName == null)
            return new Result(false, RED+"Wrong Food Name!"+RESET);

        Inventory myInventory = currentPlayer.getBackPack().inventory;

        Recipe recipe = findRecipeByName(foodName);
        if (recipe == null)
            return new Result(false, RED+"Recipe Unavailable!"+RESET);
        if (!recipe.isUsable())
            return new Result(false, RED+"Recipe is Locked!"+RESET);

        // check ingredients
        Fridge fridge = currentPlayer.getFarm().getHome().getFridge();
        boolean r = true;
        HashMap<Items, Integer> ingredients = recipe.getIngredients();
        for (Map.Entry<Items, Integer> e: ingredients.entrySet()) {
            GameController controller = new GameController();
            r = controller.checkAmountProductAvailable(e.getKey(), e.getValue());

            if (fridge.items.containsKey(e.getKey())) {
                int amount = fridge.items.get(e.getKey());
                r = amount >= e.getValue();

            }

            if (!r)
                return new Result(false, RED+"Not Enough Ingredients!"+RESET);
        }

        // check inventory space for food
        FoodTypes t = recipe.getType();
        boolean inventorySpace = Food.checkInventorySpaceForFood(t);
        if (!inventorySpace)
            return new Result(false, RED+"No Space in Inventory!"+RESET);


        // lower default energy
        if (currentPlayer.getHealth() >= 3)
            currentPlayer.setHealth(currentPlayer.getHealth() - 3);
        else return new Result(false, "Not Enough Energy!");



        // add food to inventory
        GameController controller = new GameController();
        Items i = new Food(t);
        if (controller.checkAmountProductAvailable(i, 1)) {
            myInventory.Items.put(i, myInventory.Items.get(i) + 1);
        }
        else myInventory.Items.put(i, 1);


        // decrease ingredients
        for (Map.Entry<Items, Integer> e: ingredients.entrySet()) {
            GameController controller2 = new GameController();
            if (controller2.checkAmountProductAvailable(e.getKey(), e.getValue())) {
                myInventory.Items.put(e.getKey(), myInventory.Items.get(e.getKey()) - e.getValue());
            }
            else {
                fridge.items.put(e.getKey(), fridge.items.get(e.getKey()) - e.getValue());
            }

        }




        return new Result(true, GREEN+"cooked Properly!"+RESET);
    }
    public static Result recipeDisplay () {
        if (NotInHome(currentPlayer))
            return new Result(false, RED+"You're Not in Your Home!"+RESET);

        try {
            System.out.println("Displaying Recipes...");
            for (Recipe recipe: currentPlayer.getRecipes()) {
                if (recipe.isUsable())
                    recipe.print();
            }
            return new Result(true, GREEN+"Done!"+RESET);
        } catch (Exception e) {
            return new Result(false, RED+"Unknown Error!"+RESET);
        }
    }

    public static MarketItemType findSource(String name) {
        return switch (name) {
            case "omelet" -> MarketItemType.OmeletRecipe;
            case "pizza" -> MarketItemType.PizzaRecipe;
            case "tortilla" -> MarketItemType.TortillaRecipe;
            case "maki roll" -> MarketItemType.MakiRollRecipe;
            case "triple shot espresso" -> MarketItemType.TripleShotEspressoRecipe;
            case "cookie" -> MarketItemType.CookieRecipe;
            case "hash browns" -> MarketItemType.HashbrownsRecipe;
            case "pancakes" -> MarketItemType.PancakesRecipe;
            case "bread" -> MarketItemType.BreadRecipe;
            default -> null;
        };
    }


    public static void cook () {
        if (NotInHome(currentPlayer)) {
            System.out.println(RED + "You're Not in Your Home!" + RESET);
            return;
        }




        // رسپی های عرفان
        if (currentPlayer.getLevelMining() >= 1) {
            for (Recipe recipe: currentPlayer.getRecipes()) {
                if (recipe.getName().equals("miner's treat")) {
                    recipe.setUsable(true);
                    break;
                }
            }
        }
        if (currentPlayer.getLevelForaging() >= 2) {
            for (Recipe recipe: currentPlayer.getRecipes()) {
                if (recipe.getName().equals("vegetable medley")) {
                    recipe.setUsable(true);
                    break;
                }
            }
        }
        if (currentPlayer.getLevelForaging() >= 3) {
            for (Recipe recipe: currentPlayer.getRecipes()) {
                if (recipe.getName().equals("survival burger")) {
                    recipe.setUsable(true);
                    break;
                }
            }
        }
        if (currentPlayer.getLevelFishing() >= 3) {
            for (Recipe recipe: currentPlayer.getRecipes()) {
                if (recipe.getName().equals("seaform pudding")) {
                    recipe.setUsable(true);
                    break;
                }
            }
        }

        // رسپی های محمدرضا
        Inventory myInventory = currentPlayer.getBackPack().inventory;
        MarketItem marketItem = new MarketItem(findSource("omelet"));
        if (myInventory.Items.containsKey(marketItem)) {
            for (Recipe recipe: currentPlayer.getRecipes()) {
                if (recipe.getName().equals("omelet")) {
                    recipe.setUsable(true);
                    break;
                }
            }
        }
        MarketItem marketItem4 = new MarketItem(findSource("pizza"));
        if (myInventory.Items.containsKey(marketItem4)) {
            for (Recipe recipe: currentPlayer.getRecipes()) {
                if (recipe.getName().equals("pizza")) {
                    recipe.setUsable(true);
                    break;
                }
            }
        }
        MarketItem marketItem5 = new MarketItem(findSource("tortilla"));
        if (myInventory.Items.containsKey(marketItem5)) {
            for (Recipe recipe: currentPlayer.getRecipes()) {
                if (recipe.getName().equals("tortilla")) {
                    recipe.setUsable(true);
                    break;
                }
            }
        }
        MarketItem marketItem6 = new MarketItem(findSource("maki roll"));
        if (myInventory.Items.containsKey(marketItem6)) {
            for (Recipe recipe: currentPlayer.getRecipes()) {
                if (recipe.getName().equals("maki roll")) {
                    recipe.setUsable(true);
                    break;
                }
            }
        }
        MarketItem marketItem7 = new MarketItem(findSource("triple shot espresso"));
        if (myInventory.Items.containsKey(marketItem7)) {
            for (Recipe recipe: currentPlayer.getRecipes()) {
                if (recipe.getName().equals("triple shot espresso")) {
                    recipe.setUsable(true);
                    break;
                }
            }
        }
        MarketItem marketItem8 = new MarketItem(findSource("cookie"));
        if (myInventory.Items.containsKey(marketItem8)) {
            for (Recipe recipe: currentPlayer.getRecipes()) {
                if (recipe.getName().equals("cookie")) {
                    recipe.setUsable(true);
                    break;
                }
            }
        }
        MarketItem marketItem9 = new MarketItem(findSource("hash browns"));
        if (myInventory.Items.containsKey(marketItem9)) {
            for (Recipe recipe: currentPlayer.getRecipes()) {
                if (recipe.getName().equals("hash browns")) {
                    recipe.setUsable(true);
                    break;
                }
            }
        }
        MarketItem marketItem10 = new MarketItem(findSource("pancakes"));
        if (myInventory.Items.containsKey(marketItem10)) {
            for (Recipe recipe: currentPlayer.getRecipes()) {
                if (recipe.getName().equals("pancakes")) {
                    recipe.setUsable(true);
                    break;
                }
            }
        }
        MarketItem marketItem13 = new MarketItem(findSource("bread"));
        if (myInventory.Items.containsKey(marketItem13)) {
            for (Recipe recipe: currentPlayer.getRecipes()) {
                if (recipe.getName().equals("bread")) {
                    recipe.setUsable(true);
                    break;
                }
            }
        }





        String input;
        Scanner scanner = new Scanner(System.in);
        Result result;
        while (true) {
            input = scanner.nextLine();
            if (HomeMenuCommands.showRecipes.getMatcher(input) != null) {
                result = recipeDisplay();
                System.out.println(result.massage());
            }
            else if (HomeMenuCommands.fridgePick.getMatcher(input) != null) {
                result = fridgePick(input);
                System.out.println(result.massage());
            }
            else if (HomeMenuCommands.fridgePut.getMatcher(input) != null) {
                result = fridgePut(input);
                System.out.println(result);
            }
            else if (HomeMenuCommands.foodPrepare.getMatcher(input) != null) {
                result = foodPrepare(input);
                System.out.println(result);
            }
            else if (input.equalsIgnoreCase("exit"))
                return;
            else System.out.println(RED+"Invalid Command in Cooking Section!(type exit to quit)"+RESET);
        }
    }

    public Result goToCraftingMenu() {
        App.currentMenu = Menu.CraftMenu;
        return new Result(true , BLUE+"Welcome to craft menu" + RESET);
    }
}
