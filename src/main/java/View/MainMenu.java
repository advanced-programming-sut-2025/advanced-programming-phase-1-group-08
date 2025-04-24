package View;

import Controller.MainController;
import model.App;
import model.Enum.Commands.MainMenuCommands;
import model.Enum.Commands.RegisterCommands;
import model.Enum.Menu;
import model.Result;

import java.util.Scanner;

public class MainMenu implements AppMenu{
    private final MainController controller = new MainController();

    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();

        if (MainMenuCommands.logout.getMatcher(input) != null) {
            Result temp = controller.logoutRes();
            System.out.println(temp);

            if (temp.IsSuccess()) {
                App.currentUser = null;
                App.currentMenu = Menu.RegisterMenu;
            }
        }
        else if (MainMenuCommands.goToGameMenu.getMatcher(input) != null) {
            Result temp = controller.goToGame();
            System.out.println(temp);

            if (temp.IsSuccess()) {
                App.currentMenu = Menu.GameMenu;
            }
        }
        else if (MainMenuCommands.goToProfileMenu.getMatcher(input) != null) {
            Result temp = controller.goToProfile();
            System.out.println(temp);

            if (temp.IsSuccess()) {
                App.currentMenu = Menu.ProfileMenu;
            }
        }
        else if (MainMenuCommands.goToAvatarMenu.getMatcher(input) != null) {
            Result temp = controller.goToAvatar();
            System.out.println(temp);

            if (temp.IsSuccess()) {
                App.currentMenu = Menu.AvatarMenu;
            }
        }
        else if (input.matches("\\s*show\\s*current\\s*menu"))
            System.out.println("Main Menu");
        else
            System.out.println("invalid command!");
    }
}
