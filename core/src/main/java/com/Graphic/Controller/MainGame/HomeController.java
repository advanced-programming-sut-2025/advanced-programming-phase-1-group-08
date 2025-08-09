package com.Graphic.Controller.MainGame;

import com.Graphic.Main;
import com.Graphic.View.GameMenus.GameMenu;
import com.Graphic.View.GameMenus.HomeMenu;
import com.Graphic.View.GameMenus.TransitionScreen;
import com.Graphic.model.*;
import com.Graphic.model.ClientServer.Message;
import com.Graphic.model.Enum.Commands.CommandType;
import com.Graphic.model.Enum.Commands.HomeMenuCommands;
import com.Graphic.model.Enum.FoodTypes;
import com.Graphic.model.Enum.HouseModes;
import com.Graphic.model.Enum.ItemType.MarketItemType;
import com.Graphic.model.Enum.Menu;
import com.Graphic.model.HelpersClass.Result;
import com.Graphic.model.OtherItem.Fridge;
import com.Graphic.model.Places.MarketItem;
import com.Graphic.model.Plants.Food;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import java.util.HashMap;
import java.util.Map;

import static com.Graphic.Controller.MainGame.GameControllerLogic.checkAmountProductAvailable;
import static com.Graphic.model.App.AllFromDisplayNames;
import static com.Graphic.model.App.currentGame;
import static com.Graphic.model.HelpersClass.Color_Eraser.*;
import static com.Graphic.model.Plants.Food.checkInventorySpaceForFood;

public class HomeController {
    public static boolean NotInHome(User user) {
        //return !(user.getFarm().isInHome(user.getPositionX(), user.getPositionY()));
        return true;
    }

    private static HomeMenu homeMenu = null;

    public static Result fridgePick (Items item) {

//        Main.getClient().getPlayer().getFarm().getHome().getFridge().items.compute(item, (k, x) -> x - 1);
//        if (Main.getClient().getPlayer().getFarm().getHome().getFridge().items.get(item) == 0) {
//            Main.getClient().getPlayer().getFarm().getHome().getFridge().items.remove(item);
//        }
        HashMap<String, Object> body = new HashMap<>();
        body.put("Item", item);
        body.put("amount", 1);
        Main.getClient().getRequests().add(new Message(CommandType.CHANGE_FRIDGE, body));

        boolean freeSpace = checkAmountProductAvailable(item, 1) ||
            Main.getClient().getPlayer().getBackPack().getType().getRemindCapacity() > 0;
        if (!freeSpace) {
            return new Result(false, "Free some space in your inventory!");
        }

        Inventory inventory = Main.getClient().getPlayer().getBackPack().inventory;

//        if (inventory.Items.containsKey(item)) {
//            inventory.Items.compute(item, (k, x) -> x + 1);
//        }
//        else
//            inventory.Items.put(item, 1);
        HashMap<String, Object> body2 = new HashMap<>();
        body.put("Item", item);
        body.put("amount", 1);
        Main.getClient().getRequests().add(new Message(CommandType.CHANGE_INVENTORY, body2));


        return new Result(true, "Added to inventory.");

    }
    public static Result fridgePut (Items item) {

//        Inventory inventory = Main.getClient().getPlayer().getBackPack().inventory;
//        inventory.Items.compute(item, (k, x) -> x - 1);
//        if (inventory.Items.get(item) == 0) {
//            inventory.Items.remove(item);
//        }
        HashMap<String, Object> body = new HashMap<>();
        body.put("Item", item);
        body.put("amount", 1);
        Main.getClient().getRequests().add(new Message(CommandType.CHANGE_INVENTORY, body));


//        Fridge fridge = Main.getClient().getPlayer().getFarm().getHome().getFridge();
//
//        if (fridge.items.containsKey(item)) {
//            fridge.items.compute(item, (k, x) -> x + 1);
//        }
//        else
//            fridge.items.put(item, 1);
        HashMap<String, Object> body2 = new HashMap<>();
        body.put("Item", item);
        body.put("amount", 1);
        Main.getClient().getRequests().add(new Message(CommandType.CHANGE_FRIDGE, body2));

        return new Result(true, "Added to fridge.");
    }
    public static Recipe findRecipeByName (String name) {
        for (Recipe recipe: Main.getClient().getPlayer().getRecipes()) {
            if (recipe.getName().equals(name))
                return recipe;
        }
        return null;
    }
    public static Result foodPrepare (String foodName) {

        if (foodName == null)
            return new Result(false, "Wrong Food Name!");

        Inventory myInventory = Main.getClient().getPlayer().getBackPack().inventory;

        Recipe recipe = findRecipeByName(foodName);
        if (recipe == null)
            return new Result(false, "Recipe Unavailable!");
        if (!recipe.isUsable())
            return new Result(false, "Recipe is Locked!");

        // check ingredients
        Fridge fridge = Main.getClient().getPlayer().getFarm().getHome().getFridge();
        boolean r = true;
        HashMap<Items, Integer> ingredients = recipe.getIngredients();
        for (Map.Entry<Items, Integer> e: ingredients.entrySet()) {
            r = checkAmountProductAvailable(e.getKey(), e.getValue());

            if (fridge.items.containsKey(e.getKey())) {
                int amount = fridge.items.get(e.getKey());
                r = amount >= e.getValue();

            }

            if (!r)
                return new Result(false, "Not Enough Ingredients!");
        }

        // check inventory space for food
        FoodTypes t = recipe.getType();
        boolean inventorySpace = checkInventorySpaceForFood(t);
        if (!inventorySpace)
            return new Result(false, "No Space in Inventory!");


        // lower default energy
        if (Main.getClient().getPlayer().getHealth() >= 3)
            Main.getClient().getPlayer().setHealth(Main.getClient().getPlayer().getHealth() - 3);
        else return new Result(false, "Not Enough Energy!");



        // add food to inventory
        Items i = new Food(t);
        HashMap<String, Object> body = new HashMap<>();
        body.put("Item", i);
        body.put("amount", 1);
        Main.getClient().getRequests().add(new Message(CommandType.CHANGE_INVENTORY, body));


        // decrease ingredients
        for (Map.Entry<Items, Integer> e: ingredients.entrySet()) {
            HashMap<String, Object> body2 = new HashMap<>();
            body2.put("Item", e.getKey());
            body2.put("amount", -e.getValue());
            Main.getClient().getRequests().add(new Message(CommandType.CHANGE_INVENTORY, body2));
        }

        return new Result(true, "Cooked Properly!");
    }
    public static Result recipeDisplay () {
        if (NotInHome(Main.getClient().getPlayer()))
            return new Result(false, RED+"You're Not in Your Home!"+RESET);

        try {
            System.out.println("Displaying Recipes...");
            for (Recipe recipe: Main.getClient().getPlayer().getRecipes()) {
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


    public static HouseModes handleHomeInputs (HouseModes mode, HomeMenu hM) {
        if (homeMenu == null) {
            homeMenu = hM;
        }


        if (mode == HouseModes.home && Gdx.input.isKeyJustPressed(Input.Keys.C))
            return cook(mode);
        if (mode == HouseModes.home && Gdx.input.isKeyJustPressed(Input.Keys.B))
            return HouseModes.craft;
        if (mode == HouseModes.home && Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            Main.getMain().setScreen(
                new TransitionScreen(
                    Main.getMain(),
                    homeMenu,
                    GameMenu.getInstance(),
                    1f
                )
            );
        }

        return mode;
    }
    public static HouseModes cook (HouseModes mode) {
//         رسپی های عرفان
        if (Main.getClient().getPlayer().getLevelMining() >= 1) {
            for (Recipe recipe: Main.getClient().getPlayer().getRecipes()) {
                if (recipe.getName().equals("miner's treat")) {
                    recipe.setUsable(true);
                    break;
                }
            }
        }
        if (Main.getClient().getPlayer().getLevelForaging() >= 2) {
            for (Recipe recipe: Main.getClient().getPlayer().getRecipes()) {
                if (recipe.getName().equals("vegetable medley")) {
                    recipe.setUsable(true);
                    break;
                }
            }
        }
        if (Main.getClient().getPlayer().getLevelForaging() >= 3) {
            for (Recipe recipe: Main.getClient().getPlayer().getRecipes()) {
                if (recipe.getName().equals("survival burger")) {
                    recipe.setUsable(true);
                    break;
                }
            }
        }
        if (Main.getClient().getPlayer().getLevelFishing() >= 3) {
            for (Recipe recipe: Main.getClient().getPlayer().getRecipes()) {
                if (recipe.getName().equals("seaform pudding")) {
                    recipe.setUsable(true);
                    break;
                }
            }
        }

        // رسپی های محمدرضا
        Inventory myInventory = Main.getClient().getPlayer().getBackPack().inventory;
        MarketItem marketItem = new MarketItem(findSource("omelet"));
        if (myInventory.Items.containsKey(marketItem)) {
            for (Recipe recipe: Main.getClient().getPlayer().getRecipes()) {
                if (recipe.getName().equals("omelet")) {
                    recipe.setUsable(true);
                    break;
                }
            }
        }
        MarketItem marketItem4 = new MarketItem(findSource("pizza"));
        if (myInventory.Items.containsKey(marketItem4)) {
            for (Recipe recipe: Main.getClient().getPlayer().getRecipes()) {
                if (recipe.getName().equals("pizza")) {
                    recipe.setUsable(true);
                    break;
                }
            }
        }
        MarketItem marketItem5 = new MarketItem(findSource("tortilla"));
        if (myInventory.Items.containsKey(marketItem5)) {
            for (Recipe recipe: Main.getClient().getPlayer().getRecipes()) {
                if (recipe.getName().equals("tortilla")) {
                    recipe.setUsable(true);
                    break;
                }
            }
        }
        MarketItem marketItem6 = new MarketItem(findSource("maki roll"));
        if (myInventory.Items.containsKey(marketItem6)) {
            for (Recipe recipe: Main.getClient().getPlayer().getRecipes()) {
                if (recipe.getName().equals("maki roll")) {
                    recipe.setUsable(true);
                    break;
                }
            }
        }
        MarketItem marketItem7 = new MarketItem(findSource("triple shot espresso"));
        if (myInventory.Items.containsKey(marketItem7)) {
            for (Recipe recipe: Main.getClient().getPlayer().getRecipes()) {
                if (recipe.getName().equals("triple shot espresso")) {
                    recipe.setUsable(true);
                    break;
                }
            }
        }
        MarketItem marketItem8 = new MarketItem(findSource("cookie"));
        if (myInventory.Items.containsKey(marketItem8)) {
            for (Recipe recipe: Main.getClient().getPlayer().getRecipes()) {
                if (recipe.getName().equals("cookie")) {
                    recipe.setUsable(true);
                    break;
                }
            }
        }
        MarketItem marketItem9 = new MarketItem(findSource("hash browns"));
        if (myInventory.Items.containsKey(marketItem9)) {
            for (Recipe recipe: Main.getClient().getPlayer().getRecipes()) {
                if (recipe.getName().equals("hash browns")) {
                    recipe.setUsable(true);
                    break;
                }
            }
        }
        MarketItem marketItem10 = new MarketItem(findSource("pancakes"));
        if (myInventory.Items.containsKey(marketItem10)) {
            for (Recipe recipe: Main.getClient().getPlayer().getRecipes()) {
                if (recipe.getName().equals("pancakes")) {
                    recipe.setUsable(true);
                    break;
                }
            }
        }
        MarketItem marketItem13 = new MarketItem(findSource("bread"));
        if (myInventory.Items.containsKey(marketItem13)) {
            for (Recipe recipe: Main.getClient().getPlayer().getRecipes()) {
                if (recipe.getName().equals("bread")) {
                    recipe.setUsable(true);
                    break;
                }
            }
        }


        return HouseModes.cook;
    }

    public Result goToCraftingMenu() {
        App.currentMenu = Menu.CraftMenu;
        return new Result(true , "Welcome to craft menu");
    }
}
