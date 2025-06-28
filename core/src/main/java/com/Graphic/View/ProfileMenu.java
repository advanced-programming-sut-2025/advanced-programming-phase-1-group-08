package com.Graphic.View;

import Controller.Menu.ProfileController;
import com.Graphic.model.App;
import com.Graphic.model.Enum.Commands.ProfileCommands;
import com.Graphic.model.Enum.Menu;
import com.Graphic.model.Result;

import java.io.IOException;
import java.util.Scanner;

public class ProfileMenu implements AppMenu{
    private final ProfileController controller = new ProfileController();

    @Override
    public void check(Scanner scanner) throws IOException {
        String input = scanner.nextLine();

        if (ProfileCommands.userInfo.getMatcher(input) != null) {
            System.out.println("Username: " + App.currentUser.getUsername());
            System.out.println("Nickname: " + App.currentUser.getNickname());
            System.out.println("Most Golds Collected: " + App.currentUser.getMax_point());
            System.out.println("Total Games Played: " + App.currentUser.getGames_played());
        }
        else if (ProfileCommands.changeUsername.getMatcher(input) != null) {
            Result result = controller.changeUsername(ProfileCommands.changeUsername.getMatcher(input).group("username").trim());
            System.out.println(result);
        }
        else if (ProfileCommands.changeEmail.getMatcher(input) != null) {
            Result result = controller.changeEmail(ProfileCommands.changeEmail.getMatcher(input).group("email").trim());
            System.out.println(result);
        }
        else if (ProfileCommands.changeNickname.getMatcher(input) != null) {
            Result result = controller.changeNickname(ProfileCommands.changeNickname.getMatcher(input).group("nickname").trim());
            System.out.println(result);
        }
        else if (ProfileCommands.changePass.getMatcher(input) != null) {
            Result result = controller.changePass(ProfileCommands.changePass.getMatcher(input).group("password").trim(),
                                                    ProfileCommands.changePass.getMatcher(input).group("oldPassword"));
            System.out.println(result);
        }
        else if (ProfileCommands.back.getMatcher(input) != null) {
            App.currentMenu = Menu.MainMenu;
        }
        else if (input.matches("\\s*show\\s*current\\s*menu"))
            System.out.println("Profile Menu");
        else
            System.out.println("invalid command!");
    }
}
