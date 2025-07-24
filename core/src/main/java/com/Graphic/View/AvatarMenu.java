package com.Graphic.View;

import com.Graphic.model.App;
import com.Graphic.model.Enum.Commands.AvatarCommands;
import com.Graphic.model.Enum.Menu;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.Scanner;

public class AvatarMenu implements AppMenu{

//    public void check(Scanner scanner) {
//        String input = scanner.nextLine();
//        if (AvatarCommands.back.getMatcher(input) != null)
//            App.currentMenu = Menu.MainMenu;
//
//        else System.out.println("Invalid Command!");
//    }

    @Override
    public Stage getStage() {
        return null;
    }
}
