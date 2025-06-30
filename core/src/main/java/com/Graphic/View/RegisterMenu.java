package com.Graphic.View;

import com.Graphic.Controller.Menu.RegisterController;
import com.Graphic.model.App;
import com.Graphic.model.Enum.Commands.RegisterCommands;
import com.Graphic.model.Enum.Menu;
import com.Graphic.model.HelpersClass.Result;

import java.io.IOException;
import java.util.Scanner;

public class RegisterMenu implements AppMenu {
    private final RegisterController controller = new RegisterController();

    @Override
    public void check(Scanner scanner) throws IOException {
            String input = scanner.nextLine();

            if (RegisterCommands.Register.getMatcher(input) != null) {

                Result temp = controller.register(
                        RegisterCommands.Register.getMatcher(input).group("username").trim(),
                        RegisterCommands.Register.getMatcher(input).group("password").trim(),
                        RegisterCommands.Register.getMatcher(input).group("confirm").trim(),
                        RegisterCommands.Register.getMatcher(input).group("nickname").trim(),
                        RegisterCommands.Register.getMatcher(input).group("email").trim(),
                        RegisterCommands.Register.getMatcher(input).group("gender").trim());

                System.out.println(temp);
                if (temp.IsSuccess()) {

                    App.currentMenu = Menu.MainMenu;
                }
            } else if (RegisterCommands.GoToLogin.getMatcher(input) != null) {
                System.out.println("you are now in login menu!");
                App.currentMenu = Menu.LoginMenu;
            } else if (input.matches("\\s*menu\\s*exit\\s*"))
                App.currentMenu = Menu.ExitMenu;
            else if (input.matches("\\s*show\\s*current\\s*menu"))
            System.out.println("SignUp/Register Menu");
            else
                System.out.println("invalid command!");
        }
    }
