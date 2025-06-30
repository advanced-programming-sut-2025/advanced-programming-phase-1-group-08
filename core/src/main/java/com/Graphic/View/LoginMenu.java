package com.Graphic.View;

import com.Graphic.Controller.Menu.LoginController;
import com.Graphic.model.Enum.Commands.LoginCommands;
import com.Graphic.model.HelpersClass.Result;
import com.Graphic.model.SaveData.SessionManager;
import com.Graphic.model.SaveData.UserDataBase;
import com.Graphic.model.User;
import com.Graphic.model.App;

import java.io.IOException;
import java.util.Scanner;

public class LoginMenu extends AppView implements AppMenu {
    LoginController controller = new LoginController();

    @Override
    public void check(Scanner scanner)throws IOException {
        String input = scanner.nextLine();
        if (LoginCommands.Login.getMatcher(input) != null)
        {
            Result result = controller.LoginRes
                    (LoginCommands.Login.getMatcher(input).group("username").trim(),
                            LoginCommands.Login.getMatcher(input).group("password").trim());
            System.out.println(result);
            if (result.IsSuccess()) {

                App.currentMenu = Menu.MainMenu;

                if (UserDataBase.findUserByUsername(LoginCommands.Login.getMatcher(input).group("username").trim()) != null) {
                    User user = UserDataBase.findUserByUsername(LoginCommands.Login.getMatcher(input).group("username").trim());
                    App.currentUser = user;
                    assert user != null;
                    SessionManager.saveSession(user.getUsername(), LoginCommands.Login.getMatcher(input).group(3) != null && LoginCommands.Login.getMatcher(input).group(3).equals(" --stay-logged-in"));
                }
            }
        }
        else if (LoginCommands.ForgotPass.getMatcher(input) != null ) {

            Result result = controller.ForgotPassRes(
                    LoginCommands.ForgotPass.getMatcher(input).group("username").trim()
            );
            System.out.println(result);

        }
        else if (LoginCommands.GoToSignUp.getMatcher(input) != null ) {
            App.currentMenu = Menu.RegisterMenu;
            System.out.println("You are Now in SignUp Menu!");
        }
        else if (input.trim().toLowerCase().matches("\\s*show\\s+current\\s+menu\\s*"))
            System.out.println("LogIn Menu");
        else if (input.toLowerCase().matches("\\s*exit\\s*"))
            App.currentMenu = Menu.ExitMenu;
        else
            System.out.println("Invalid Command!");
    }
}
