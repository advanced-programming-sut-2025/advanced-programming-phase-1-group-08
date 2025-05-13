package View;

import model.Enum.Menu;
import model.SaveData.SessionManager;
import model.User;

import java.util.Scanner;

import static model.App.currentMenu;
import static model.App.getCurrentMenu;


public class AppView {

    public void run() {
        Scanner scanner = new Scanner(System.in);

//        if (SessionManager.isLoggedIn()) {
//            User user = SessionManager.getLoggedInUser();
//            System.out.println("Welcome back, " + user.getUsername());
//            currentMenu = Menu.MainMenu;
//        } else {
//            currentMenu = Menu.RegisterMenu;
//            System.out.println("\nWelcome To The SignUp Menu!");
//            System.out.println("In This Menu You Can Register, Go to LogInPage or Quit The Program\n");
//        }

        currentMenu = Menu.GameMenu;
        do {
            getCurrentMenu().checkCommand(scanner);
        } while (getCurrentMenu() != Menu.ExitMenu);
    }
}