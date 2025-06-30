package com.Graphic.View;

import com.Graphic.model.App;
import com.Graphic.model.Enum.Commands.AvatarCommands;

import java.util.Scanner;

public class AvatarMenu {

    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        if (AvatarCommands.back.getMatcher(input) != null)
            App.currentMenu = Menu.MainMenu;

        else System.out.println("Invalid Command!");
    }
}
