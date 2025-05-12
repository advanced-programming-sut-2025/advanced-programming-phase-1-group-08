package View;

import Controller.HomeController;
import model.App;
import model.Enum.Commands.HomeMenuCommands;
import model.Enum.Commands.LoginCommands;
import model.Enum.Menu;

import java.util.Scanner;

public class HomeMenu extends AppView implements AppMenu{
//    HomeController controller = new HomeController();
// todo open home menu in game commands and current menu

    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        if (HomeMenuCommands.startCooking.getMatcher(input) != null) {
            HomeController.cook();
        }


        else if (input.trim().toLowerCase().matches("\\s*show\\s+current\\s+menu\\s*"))
            System.out.println("Home Menu");
        else if (input.toLowerCase().matches("\\s*exit\\s*"))
            App.currentMenu = Menu.GameMenu;
        else
            System.out.println("Invalid Command!");
    }
}
