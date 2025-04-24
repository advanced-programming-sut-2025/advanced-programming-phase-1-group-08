package Controller;

import View.RegisterMenu;
import model.App;
import model.Result;
import model.User;

import java.util.Scanner;

import static Controller.RegisterController.*;

public class ProfileController {

    public Result changeUsername(String username) {
        if (App.currentUser.getUsername().equals(username))
            return new Result(false, "You Already Have This Username!");
        if (!usernameCheck(username))
            return new Result(false, "Invalid Username Format!");
        if (!isUnique(username))
            return new Result(false, "Username is Already Taken!");


        App.currentUser.setUsername(username);
        return new Result(true, "Username Successfully Changed.");
    }

    public Result changeEmail(String email) {
        if (App.currentUser.getEmail().equals(email))
            return new Result(false, "You Already Have This Email!");
        if (!emailCheck(email))
            return new Result(false, "Invalid Email Format!");


        App.currentUser.setEmail(email);
        return new Result(true, "Email Successfully Changed.");
    }

    public Result changeNickname(String nickname) {
        if (App.currentUser.getNickname().equals(nickname))
            return new Result(false, "You Already Have This Nickname!");
        if (!CheckName(nickname))
            return new Result(false, "Invalid Nickname Format!");


        App.currentUser.setNickname(nickname);
        return new Result(true, "Nickname Successfully Changed.");
    }

    public Result changePass(String password, String oldPass) {
        if (!App.currentUser.getPassword().equals(oldPass))
            return new Result(false, "Your Old Password is NOT Correct!");

        if (password.equals("random")) {
            Scanner scanner = new Scanner(System.in);
            String suggestion = null;
            while (true) {
                suggestion = generateRandomPass();

                System.out.println("Do You Approve This Password?[Y/N/back] Suggested Pass: " + suggestion);

                String response = scanner.nextLine();

                if (response.trim().toLowerCase().equals("y")) {
                    System.out.println("Then Your Pass is " + suggestion);
                    password = suggestion;
                    break;
                }
                else if (response.toLowerCase().trim().equals("back"))
                    return new Result(false, "Returned to Profile Menu");

                System.out.println("Generating New Password...");
            }
        }


        if (App.currentUser.getPassword().equals(password))
            return new Result(false, "You Already Have This Password!");
        if (!passCheck(password))
            return new Result(false, "Invalid Password Format!");
        if (passIsStrong(password) != null)
            return new Result(false, passIsStrong(password));

        App.currentUser.setPassword(password);
        return new Result(true, "Password Changed Successfully.");
    }
}
