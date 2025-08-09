package com.Graphic.Controller.MainGame;

import com.Graphic.Main;
import com.Graphic.model.App;
import com.Graphic.model.ClientServer.Message;
import com.Graphic.model.Enum.Commands.CommandType;
import com.Graphic.model.Enum.Menu;
import com.Graphic.model.Inventory;
import com.Graphic.model.Items;
import com.Graphic.model.HelpersClass.Result;
import com.Graphic.model.Enum.AllPlants.TreesSourceType;
import com.Graphic.model.Enum.ItemType.CraftType;
import com.Graphic.model.Enum.ItemType.MarketItemType;
import com.Graphic.model.ToolsPackage.CraftingItem;
import com.Graphic.model.Places.MarketItem;
import com.Graphic.model.Plants.TreeSource;

import java.util.HashMap;
import java.util.Map;

import static com.Graphic.model.HelpersClass.Color_Eraser.BLUE;
import static com.Graphic.model.HelpersClass.Color_Eraser.RESET;

public class CraftingController {

    public static Items numberOfIngrediants(String name) {
        Inventory inventory = Main.getClient().getPlayer().getBackPack().inventory;

        for (Map.Entry < Items , Integer> entry : inventory.Items.entrySet()) {
            if (entry.getKey().getName().equals(name)) {
                return entry.getKey();
            }
        }

        return null;
    }

    public Result showCraftingRecipe(CraftType craftType) {
        StringBuilder output = new StringBuilder();
        output.append(craftType.getName()).append("\n\n");

        Map<String , Integer> ingredients = craftType.getIngrediants();
        for (Map.Entry < String , Integer> entry : ingredients.entrySet()) {
            output.append(entry.getKey()).append(" : ").append(entry.getValue()).append("\n");
        }

        return new Result(true , output.toString());
    }

    public Result craftingCraft(String name) {
        Inventory inventory = Main.getClient().getPlayer().getBackPack().inventory;
        CraftType type=null;
        HashMap<Items , Integer> ingrediant = new HashMap();

        for (CraftType craftType : CraftType.values()) {
            if (craftType.getName().equals(name)) {
                type = craftType;
                break;
            }
        }

        HashMap<String , Object> body = new HashMap();

        if (type == null) {
            return new Result(false , "No such Craft type");
        }
        if (Main.getClient().getPlayer().getBackPack().getType().getRemindCapacity() == 0) {
            return new Result(false , "Not enough Capacity in your BackPack");
        }
        if (!type.checkLevel()) {
            return new Result(false , "Recipe is Locked");
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


        for (Map.Entry <Items , Integer> entry : ingrediant.entrySet()) {
            HashMap<String , Object> ingBody = new HashMap();
            ingBody.put("Player", Main.getClient().getPlayer());
            ingBody.put("Item", entry.getKey());
            ingBody.put("amount", -entry.getValue());
            Main.getClient().getRequests().add(new Message(CommandType.CHANGE_INVENTORY, ingBody));
        }

        if (name.equals("Grass Starter")) {
            for (Map.Entry <Items , Integer> entry : inventory.Items.entrySet()) {
                if (entry.getKey() instanceof MarketItem && ((MarketItem) entry.getKey()).getType().equals(MarketItemType.GrassStarter)) {
                    body.put("Player", Main.getClient().getPlayer());
                    body.put("Item", entry.getKey());
                    body.put("amount", 1);
                    Main.getClient().getRequests().add(new Message(CommandType.CHANGE_INVENTORY, body));
                    return new Result(true , "you created Grass Starter Successfully!");
                }
            }
            MarketItem grassStarter = new MarketItem(MarketItemType.GrassStarter);
            body.put("Player", Main.getClient().getPlayer());
            body.put("Item", grassStarter);
            body.put("amount", 1);
            Main.getClient().getRequests().add(new Message(CommandType.CHANGE_INVENTORY, body));
            return new Result(true , "You created Grass Starter Successfully!");
        }
        if (name.equals("Mystic Tree Seed")) {
            for (Map.Entry <Items , Integer> entry : inventory.Items.entrySet()) {
                if (entry.getKey() instanceof TreeSource && ((TreeSource) entry.getKey()).getType().equals(TreesSourceType.Mystic_Tree_Seeds)) {
                    body.put("Player", Main.getClient().getPlayer());
                    body.put("Item", entry.getKey());
                    body.put("amount", 1);
                    Main.getClient().getRequests().add(new Message(CommandType.CHANGE_INVENTORY, body));
                    return new Result(true , "you created Grass Starter Successfully!");
                }
            }
            TreeSource MysticTreeSeed =new TreeSource(TreesSourceType.Mystic_Tree_Seeds);
            body.put("Player", Main.getClient().getPlayer());
            body.put("Item", MysticTreeSeed);
            body.put("amount", 1);
            Main.getClient().getRequests().add(new Message(CommandType.CHANGE_INVENTORY, body));
            return new Result(true , "You created Mystic Tree Seed Successfully!");
        }

        for (Map.Entry <Items , Integer> entry : inventory.Items.entrySet()) {
            if (entry.getKey() instanceof CraftingItem && ((CraftingItem) entry.getKey()).getType().equals(type)) {
                body.put("Player", Main.getClient().getPlayer());
                body.put("Item", entry.getKey());
                body.put("amount", 1);
                Main.getClient().getRequests().add(new Message(CommandType.CHANGE_INVENTORY, body));
                return new Result(true , "You created "+type.getName()+" Successfully!");
            }
        }


        CraftingItem newCraft=new CraftingItem(type);
        body.put("Player", Main.getClient().getPlayer());
        body.put("Item", newCraft);
        body.put("amount", 1);
        Main.getClient().getRequests().add(new Message(CommandType.CHANGE_INVENTORY, body));


        //TODO App.currentGame.currentPlayer.increaseHealth(-2);

        return new Result(true , "you created " +newCraft.getType().getName() + " successfully");

    }
    public Result goToGameMenu() {

        // App.currentMenu= Menu.GameMenu;
        return new Result(true , BLUE + "Back to Game Menu" + RESET);
    }

}
