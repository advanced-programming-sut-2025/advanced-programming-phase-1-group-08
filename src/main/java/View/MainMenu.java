package View;

import Controller.MainController;
import model.App;
import model.Enum.Commands.MainMenuCommands;
import model.Enum.Menu;
import model.Result;
import model.SaveData.UserStorage;

import java.io.IOException;
import java.util.Scanner;

import static model.Color_Eraser.CYAN;
import static model.Color_Eraser.RESET;

public class MainMenu implements AppMenu{
    private final MainController controller = new MainController();

    @Override
    public void check(Scanner scanner) throws IOException {

        if (App.users.isEmpty())
            App.users = UserStorage.loadUsers();

        System.out.println();
        System.out.println(CYAN+"MAIN MENU"+RESET);
        System.out.println("\t-> Game Menu");
        System.out.println("\t-> Profile Menu");
        System.out.println("\t-> Avatar Menu");
        System.out.println();


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
        else if (input.matches("\\s*show\\s*current\\s*menu\\s*"))
            System.out.println("Main Menu");
        else
            System.out.println("Invalid Command!");
    }
}
