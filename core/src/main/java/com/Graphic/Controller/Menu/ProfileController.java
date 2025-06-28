package com.Graphic.Controller.Menu;

import com.Graphic.model.App;
import com.Graphic.model.Result;
import com.Graphic.model.SaveData.PasswordHashUtil;
import com.Graphic.model.SaveData.SessionManager;
import com.Graphic.model.SaveData.UserStorage;
import com.Graphic.model.User;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import static com.Graphic.Controller.Menu.RegisterController.*;
import static com.Graphic.model.Color_Eraser.RED;
import static com.Graphic.model.Color_Eraser.RESET;


public class ProfileController {

    public Result changeUsername(String username) throws IOException {
        if (App.currentUser.getUsername().equals(username))
            return new Result(false, "You Already Have This Username!");
        if (!usernameCheck(username))
            return new Result(false, "Invalid Username Format!");
        if (!isUnique(username))
            return new Result(false, "Username is Already Taken!");



        List<User> users = UserStorage.loadUsers();
        for (User user: users) {
            if (user.getUsername().equals(App.currentUser.getUsername())) {
                user.setUsername(username);
                UserStorage.saveUsers(users);
                SessionManager.saveSession(user.getUsername(), SessionManager.isLoggedIn());
                App.currentUser.setUsername(username);
                return new Result(true, "Username Successfully Changed.");
            }
        }

        return new Result(false, RED+"User Not Found!"+RESET);
    }

    public Result changeEmail(String email) throws IOException{
        if (App.currentUser.getEmail().equals(email))
            return new Result(false, "You Already Have This Email!");
        if (!emailCheck(email))
            return new Result(false, "Invalid Email Format!");

        List<User> users = UserStorage.loadUsers();
        for (User user: users) {
            if (user.getUsername().equals(App.currentUser.getUsername())) {
                user.setEmail(email);
                UserStorage.saveUsers(users);
                App.currentUser.setEmail(email);
                break;
            }
        }

        return new Result(true, "Email Successfully Changed.");
    }

    public Result changeNickname(String nickname) throws IOException{
        if (App.currentUser.getNickname().equals(nickname))
            return new Result(false, "You Already Have This Nickname!");
        if (!CheckName(nickname))
            return new Result(false, "Invalid Nickname Format!");

        List<User> users = UserStorage.loadUsers();
        for (User user: users) {
            if (user.getUsername().equals(App.currentUser.getUsername())) {
                user.setNickname(nickname);
                UserStorage.saveUsers(users);
                App.currentUser.setNickname(nickname);
                break;
            }
        }

        return new Result(true, "Nickname Successfully Changed.");
    }

    public Result changePass(String password, String oldPass) throws IOException{

        if (!App.currentUser.getHashPass().equals(PasswordHashUtil.hashPassword(oldPass)))
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


        if (App.currentUser.getHashPass().equals(PasswordHashUtil.hashPassword(password)))
            return new Result(false, "You Already Have This Password!");
        if (!passCheck(password))
            return new Result(false, "Invalid Password Format!");
        if (passIsStrong(password) != null)
            return new Result(false, passIsStrong(password));


        List<User> users = UserStorage.loadUsers();
        for (User user: users) {
            if (user.getUsername().equals(App.currentUser.getUsername())) {
                user.setHashPass(PasswordHashUtil.hashPassword(password));
                UserStorage.saveUsers(users);
                App.currentUser.setHashPass(PasswordHashUtil.hashPassword(password));
                break;
            }
        }
        return new Result(true, "Password Changed Successfully.");
    }
}
