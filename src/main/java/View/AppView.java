package View;

import model.App;
import model.Enum.Menu;
import model.SaveData.AppTest;
import model.SaveData.PasswordHashUtil;
import model.SaveData.SessionManager;
import model.SaveData.UserBasicInfo;
import model.User;

import java.util.Scanner;

import static model.App.*;
import static model.Color_Eraser.BLUE;
import static model.Color_Eraser.RESET;


public class AppView {

    public void run() {
        Scanner scanner = new Scanner(System.in);

//        if (SessionManager.isLoggedIn()) {
//            // TODO initialize currentGame by loading data
//            UserBasicInfo userB = SessionManager.getLoggedInUser();
//            assert userB != null;
//            System.out.println();
//            System.out.println();
//            System.out.println(BLUE+"Welcome back, " + userB.getNickname()+RESET);
//            App.currentUser = new User("Mohammadreza", "Messi", "a@gmail.com", "male", 0, 200, PasswordHashUtil.hashPassword("Mohammadreza"));
//            App.currentMenu = Menu.MainMenu;
//
//        } else {
//            App.currentMenu = Menu.RegisterMenu;
//            System.out.println("\nWelcome To The SignUp Menu!");
//            System.out.println("In This Menu You Can Register, Go to LogInPage or Quit The Program\n");
//        }
        currentMenu = Menu.GameMenu;
        do {
            App.getCurrentMenu().checkCommand(scanner);
        } while (App.getCurrentMenu() != Menu.ExitMenu);
    }
}