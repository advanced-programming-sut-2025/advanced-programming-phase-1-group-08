package View;

import model.Enum.Menu;

import java.util.Scanner;

import static model.App.getCurrentMenu;


public class AppView {

    public void run() {
        Scanner scanner = new Scanner(System.in);
        do {
            getCurrentMenu().checkCommand(scanner);
        } while (getCurrentMenu() != Menu.ExitMenu);
    }
}