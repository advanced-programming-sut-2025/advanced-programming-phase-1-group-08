package com.Graphic.Controller.Menu;

import com.Graphic.Main;
import com.Graphic.model.App;
import com.Graphic.model.HelpersClass.Result;
import com.Graphic.model.SaveData.PasswordHashUtil;
import com.Graphic.model.SaveData.SessionManager;
import com.Graphic.model.SaveData.UserStorage;
import com.Graphic.model.User;

import java.io.IOException;
import java.util.List;

import static com.Graphic.Controller.Menu.RegisterController.*;
import static com.Graphic.model.HelpersClass.Color_Eraser.RED;
import static com.Graphic.model.HelpersClass.Color_Eraser.RESET;

public class ProfileController {


    public String getUserInfo() {
        if (Main.getClient(null).getPlayer() == null) {
            return "No user logged in";
        }

        StringBuilder info = new StringBuilder();
        info.append("Username: ").append(Main.getClient(null).getPlayer().getUsername()).append("\n");
        info.append("Nickname: ").append(Main.getClient(null).getPlayer().getNickname()).append("\n");
        info.append("Email: ").append(Main.getClient(null).getPlayer().getEmail()).append("\n");
        info.append("Most Golds Collected: ").append(Main.getClient(null).getPlayer().getMax_point()).append("\n");
        info.append("Total Games Played: ").append(Main.getClient(null).getPlayer().getGames_played());

        return info.toString();
    }


    public String getCurrentUsername() {
        return App.currentUser != null ? App.currentUser.getUsername() : "";
    }


    public String getCurrentNickname() {
        return App.currentUser != null ? App.currentUser.getNickname() : "";
    }


    public String getCurrentEmail() {
        return App.currentUser != null ? App.currentUser.getEmail() : "";
    }


    public int getMaxPoints() {
        return App.currentUser != null ? App.currentUser.getMax_point() : 0;
    }


    public int getGamesPlayed() {
        return App.currentUser != null ? App.currentUser.getGames_played() : 0;
    }

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

        return new Result(false, "User Not Found!");
    }

    public Result changeEmail(String email) throws IOException {
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

    public Result changeNickname(String nickname) throws IOException {
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


    public Result validateOldPassword(String oldPassword) {
        if (!App.currentUser.getHashPass().equals(PasswordHashUtil.hashPassword(oldPassword))) {
            return new Result(false, "Your Old Password is NOT Correct!");
        }
        return new Result(true, "Old password is correct");
    }

    public Result changePassword(String newPassword, String oldPassword) throws IOException {
        Result oldPassResult = validateOldPassword(oldPassword);
        if (!oldPassResult.IsSuccess()) {
            return oldPassResult;
        }

        if (App.currentUser.getHashPass().equals(PasswordHashUtil.hashPassword(newPassword))) {
            return new Result(false, "You Already Have This Password!");
        }

        if (!passCheck(newPassword)) {
            return new Result(false, "Invalid Password Format!");
        }

        String strengthError = passIsStrong(newPassword);
        if (strengthError != null) {
            return new Result(false, strengthError);
        }

        List<User> users = UserStorage.loadUsers();
        for (User user: users) {
            if (user.getUsername().equals(App.currentUser.getUsername())) {
                user.setHashPass(PasswordHashUtil.hashPassword(newPassword));
                UserStorage.saveUsers(users);
                App.currentUser.setHashPass(PasswordHashUtil.hashPassword(newPassword));
                break;
            }
        }

        return new Result(true, "Password Changed Successfully.");
    }


    public String generatePassword() {
        //TODO return generateRandomPass();
        return null;
    }

    @Deprecated
    public Result changePass(String password, String oldPass) throws IOException {
        return changePassword(password, oldPass);
    }
}
