package View;

import model.Enum.Menu;

import java.util.Scanner;

import static model.App.currentMenu;
import static model.App.getCurrentMenu;


public class AppView {

    public void run() {
        Scanner scanner = new Scanner(System.in);
        currentMenu = Menu.RegisterMenu;
        System.out.println("Welcome To The SignUp Menu!");
        System.out.println("In This Menu You Can Register, Go to LogInPage or Quit The Program");
        do {
            getCurrentMenu().checkCommand(scanner);
        } while (getCurrentMenu() != Menu.ExitMenu);
    }
}