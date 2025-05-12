package View;

import Controller.CraftingController;
import Controller.HomeController;
import model.App;
import model.Enum.Commands.GameMenuCommands;
import model.Enum.Commands.HomeMenuCommands;
import model.Enum.Commands.LoginCommands;
import model.Enum.Menu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class HomeMenu extends AppView implements AppMenu{
//    HomeController controller = new HomeController();
// todo open home menu in game commands and current menu
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

        else if ((matcher= GameMenuCommands.craftingRecipe.getMather(input)) != null)
            System.out.println(craftingController.showCraftingRecipe());

        else if ((matcher=GameMenuCommands.craftingCraft.getMather(input)) != null)
            System.out.println(craftingController.craftingCraft(matcher.group("name").trim()));



        else
            System.out.println("Invalid Command!");
    }
}
