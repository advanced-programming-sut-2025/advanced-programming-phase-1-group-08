package Controller;

import model.*;
import model.Enum.AllPlants.ForagingMineralsType;
import model.Enum.AllPlants.TreesSourceType;
import model.Enum.ItemType.CraftType;
import model.Enum.ItemType.MarketItemType;
import model.MapThings.Wood;
import model.Plants.ForagingMinerals;
import model.Plants.TreeSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CraftingController {

    public static Items numberOfIngrediants(String name) {
        Inventory inventory = App.currentPlayer.getBackPack().inventory;

        for (Map.Entry < Items , Integer> entry : inventory.Items.entrySet()) {
            if (entry.getKey() instanceof ForagingMinerals) {
                if (((ForagingMinerals) entry.getKey()).getType().equals(ForagingMineralsType.COAL)) {
                    return entry.getKey();
                }
            }

            if (entry.getKey() instanceof BarsAndOres) {
                if (((BarsAndOres) entry.getKey()).getType().getName().equals(name)) {
                    return entry.getKey();
                }
            }
            if (name.equals("Wood") && entry.getKey() instanceof Wood) {
                return entry.getKey();
            }
            if (name.equals("Stone") && entry.getKey() instanceof BasicRock) {
                return entry.getKey();
            }
            //TODO برای Fiber باید بزنیم


            if (entry.getKey() instanceof TreeSource) {
                if (((TreeSource) entry.getKey()).getType().name().equals(name)) {
                    return entry.getKey();
                }
            }
        }

        return null;
    }

    public Result showCraftingRecipe() {
        Inventory inventory = App.currentPlayer.getBackPack().inventory;
        StringBuilder output = new StringBuilder();

        for (Map.Entry < Items , Integer> entry : inventory.Items.entrySet()) {
            if (entry.getKey() instanceof MarketItem) {
                if (((MarketItem) entry.getKey()).getType().equals(MarketItemType.DehydratorRecipe)) {
                    output.append(((MarketItem) entry.getKey()).getType().getName()).append('\n');
                }
                if (((MarketItem) entry.getKey()).getType().equals(MarketItemType.GrassStarterRecipe)) {
                    output.append(((MarketItem) entry.getKey()).getType().getName()).append('\n');
                }
                if (((MarketItem) entry.getKey()).getType().equals(MarketItemType.FishSmokerRecipe)) {
                    output.append(((MarketItem) entry.getKey()).getType().getName()).append('\n');
                }
            }
        }

        return new Result(true , output.toString());
    }


    public Result craftingCraft(String name) {
        Inventory inventory = App.currentPlayer.getBackPack().inventory;
        CraftType type=null;
        HashMap<Items , Integer> ingrediant = new HashMap();

        for (CraftType craftType : CraftType.values()) {
            if (craftType.getName().equals(name)) {
                type = craftType;
            }
        }

        if (type == null) {
            return new Result(false , "No such Craft Type");
        }
        if (App.currentPlayer.getBackPack().getType().getRemindCapacity() == 0) {
            return new Result(false , "Not enough Capacity in your BackPack");
        }

        for (Map.Entry <String,Integer> entry : type.getIngrediants().entrySet()) {
            Items items =numberOfIngrediants(entry.getKey()) ;
            if (items == null) {
                return new Result(false , "Not enough "+entry.getKey());
            }
            if (inventory.Items.get(items) < entry.getValue()) {
                return new Result(false , "Not enough "+entry.getKey());
            }
            ingrediant.put(items, entry.getValue());
        }

        //TODO برای چک کردن طرح ها
        CraftingItem newCraft=new CraftingItem(type);
        for (Map.Entry <Items , Integer> entry : ingrediant.entrySet()) {
            inventory.Items.compute(entry.getKey(), (k, x) -> x - entry.getValue());
        }

        return new Result(true , "you created " +newCraft.getCraftType().getName() + " successfully");

    }


}
