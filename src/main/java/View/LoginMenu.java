package View;

import Controller.LoginController;
import model.Enum.Commands.LoginCommands;
import model.Result;
import model.User;
import model.App;
import model.Enum.Menu;

import java.util.Scanner;

public class LoginMenu extends AppView implements AppMenu {
    LoginController controller = new LoginController();

    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        if (LoginCommands.Login.getMatcher(input) != null)
        {
            Result result = controller.LoginRes
                    (LoginCommands.Login.getMatcher(input).group("username").trim(),
                            LoginCommands.Login.getMatcher(input).group("password").trim());
            System.out.println(result);
            if (result.IsSuccess()) {

                App.currentMenu = Menu.MainMenu;

                for (User user : App.users)
                    if (user.getUsername().equals(LoginCommands.Login.getMatcher(input).group("username").trim()))
                        App.currentUser = user ;

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
