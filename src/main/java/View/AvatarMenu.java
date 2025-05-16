package View;

import model.App;
import model.Enum.Commands.AvatarCommands;
import model.Enum.Menu;

import java.util.Scanner;

public class AvatarMenu implements AppMenu {

    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        if (AvatarCommands.back.getMatcher(input) != null)
            App.currentMenu = Menu.MainMenu;

        else System.out.println("Invalid Command!");
    }
}
