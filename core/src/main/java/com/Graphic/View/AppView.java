package com.Graphic.View;

import com.Graphic.model.App;
import com.Graphic.model.Enum.Menu;
import com.Graphic.model.SaveData.SessionManager;
import com.Graphic.model.User;

import java.io.IOException;
import java.util.Scanner;

import static com.Graphic.model.App.currentMenu;
import static com.Graphic.model.HelpersClass.Color_Eraser.*;


public class AppView {

    public void run() throws IOException {
        Scanner scanner = new Scanner(System.in);

        if (SessionManager.isLoggedIn()) {
            User user = SessionManager.getLoggedInUser();
            assert user != null;
            System.out.println(BLUE+"Welcome back, " + user.getNickname()+RESET);
            App.currentUser = user;
            App.currentMenu = Menu.MainMenu;

        } else {
            App.currentMenu = Menu.RegisterMenu;
            System.out.println(CYAN+"\nWelcome To The SignUp Menu!"+RESET);
            System.out.println("In This Menu You Can Register, Go to LogInPage or Quit The Program\n");
        }
        // currentMenu = Menu.GameMenu;
        do {
            App.getCurrentMenu().checkCommand(scanner);
        } while (App.getCurrentMenu() != Menu.ExitMenu);
    }
}
