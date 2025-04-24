package View;

import Controller.ProfileController;
import model.App;
import model.Enum.Commands.ProfileCommands;
import model.User;

import java.util.Scanner;

public class ProfileMenu implements AppMenu{
    private final ProfileController controller = new ProfileController();

    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();

        if (ProfileCommands.userInfo.getMatcher(input) != null) {
            System.out.println("Username: " + App.currentUser.getUsername());
            System.out.println("Nickname: " + App.currentUser.getNickname());
            System.out.println("Most Points Collected: " + App.currentUser.getMax_point());
            System.out.println("Total Games Played: " + App.currentUser.getGames_played());
        }
        else if (ProfileCommands.changeUsername.getMatcher(input) != null) {

        }
        else if (ProfileCommands.changeEmail.getMatcher(input) != null) {

        }
        else if (ProfileCommands.changeNickname.getMatcher(input) != null) {

        }
        else if (ProfileCommands.changePass.getMatcher(input) != null) {

        }
    }
}
