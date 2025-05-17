package View;

import model.App;
import model.Enum.Menu;
import model.SaveData.PasswordHashUtil;
import model.SaveData.SessionManager;
import model.User;

import java.io.IOException;
import java.util.Scanner;

import static model.App.currentMenu;
import static model.Color_Eraser.*;


public class AppView {

    public void run() throws IOException {
        Scanner scanner = new Scanner(System.in);

//        if (SessionManager.isLoggedIn()) {
//            User user = SessionManager.getLoggedInUser();
//            assert user != null;
//            System.out.println();
//            System.out.println();
//            System.out.println(BLUE+"Welcome back, " + user.getNickname()+RESET);
//            App.currentUser = user;
//            App.currentMenu = Menu.MainMenu;
//
//        } else {
//            App.currentMenu = Menu.RegisterMenu;
//            System.out.println(CYAN+"\nWelcome To The SignUp Menu!"+RESET);
//            System.out.println("In This Menu You Can Register, Go to LogInPage or Quit The Program\n");
//        }
        currentMenu = Menu.GameMenu;
        do {
            App.getCurrentMenu().checkCommand(scanner);
        } while (App.getCurrentMenu() != Menu.ExitMenu);
    }
}