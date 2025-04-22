package View;

import model.Enum.Menu;

import java.util.Scanner;

import static model.App.currentMenu;
import static model.App.getCurrentMenu;


public class AppView {

    public void run() {
        Scanner scanner = new Scanner(System.in);
        currentMenu = Menu.GameMenu;
        do {
            getCurrentMenu().checkCommand(scanner);
        } while (getCurrentMenu() != Menu.ExitMenu);
    }
}