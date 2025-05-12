package Controller;

import model.Enum.Commands.HomeMenuCommands;
import model.Items;
import model.OtherItem.Fridge;
import model.Result;
import model.User;

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
//    public static Result foodPrepare (String input) {
//        if (NotInHome(currentPlayer))
//            return new Result(false, RED+"You're Not in Your Home!"+RESET);
//
//    }
//    public static Result recipeDisplay () {
//        if (NotInHome(currentPlayer))
//            return new Result(false, RED+"You're Not in Your Home!"+RESET);
//    }
//    public static void cook () {
//        if (NotInHome(currentPlayer)) {
//            System.out.println(RED + "You're Not in Your Home!" + RESET);
//            return;
//        }
//
//        String input;
//        Scanner scanner = new Scanner(System.in);
//        Result result;
//        while (true) {
//            input = scanner.nextLine();
//            if (HomeMenuCommands.showRecipes.getMatcher(input) != null) {
//                result = recipeDisplay();
//                System.out.println(result.massage());
//            }
//            else if (HomeMenuCommands.fridgePick.getMatcher(input) != null) {
//                result = fridgePick(input);
//                System.out.println(result.massage());
//            }
//            else if (HomeMenuCommands.fridgePut.getMatcher(input) != null) {
//                result = fridgePut(input);
//                System.out.println(result);
//            }
//            else if (HomeMenuCommands.foodPrepare.getMatcher(input) != null) {
//                result = foodPrepare(input);
//                System.out.println(result);
//            }
//            else if (input.equalsIgnoreCase("exit"))
//                return;
//            else System.out.println(RED+"Invalid Command in Cooking Section!(type exit to quit)"+RESET);
//        }
//    }
}
