package View;

import Controller.CraftingController;
import Controller.GameController;
import Controller.HomeController;
import model.App;
import model.Enum.Commands.GameMenuCommands;
import model.Enum.Commands.HomeMenuCommands;
import model.Enum.Menu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class HomeMenu extends AppView implements AppMenu{


    CraftingController craftingController=new CraftingController();
    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher;
        if (HomeMenuCommands.startCooking.getMatcher(input) != null) {
            HomeController.cook();
        }


        else if (input.trim().toLowerCase().matches("\\s*show\\s+current\\s+menu\\s*"))
            System.out.println("Home Menu");
        else if (input.toLowerCase().matches("\\s*exit\\s*"))
            App.currentMenu = Menu.GameMenu;

        else if ((matcher = GameMenuCommands.craftingRecipe.getMatcher(input)) != null)
            System.out.println(craftingController.showCraftingRecipe());

        else if ((matcher = GameMenuCommands.craftingCraft.getMatcher(input)) != null)
            System.out.println(craftingController.craftingCraft(matcher.group("name").trim()));

        else if((matcher = GameMenuCommands.inventoryShow.getMatcher(input)) != null) {
            GameController controller = new GameController();
            System.out.println(controller.showInventory());
        }

        else if ((matcher=GameMenuCommands.addItem.getMatcher(input)) != null) {
            GameController controller = new GameController();
            System.out.println(controller.addItem(matcher.group(1), Integer.parseInt(matcher.group(2).trim())));
        }



        else
            System.out.println("Invalid Command!");
    }
}
