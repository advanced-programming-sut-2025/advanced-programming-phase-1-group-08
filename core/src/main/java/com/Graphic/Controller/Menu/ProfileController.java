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
        if (Main.getClient().getPlayer() == null) {
            return "No user logged in";
        }

        StringBuilder info = new StringBuilder();
        info.append("Username: ").append(Main.getClient().getPlayer().getUsername()).append("\n");
        info.append("Nickname: ").append(Main.getClient().getPlayer().getNickname()).append("\n");
        info.append("Email: ").append(Main.getClient().getPlayer().getEmail()).append("\n");
        info.append("Most Golds Collected: ").append(Main.getClient().getPlayer().getMax_point()).append("\n");
        info.append("Total Games Played: ").append(Main.getClient().getPlayer().getGames_played());

        return info.toString();
    }


    public String getCurrentUsername() {
        return Main.getClient().getPlayer() != null ? Main.getClient().getPlayer().getUsername() : "";
    }


    public String getCurrentNickname() {
        return Main.getClient().getPlayer() != null ? Main.getClient().getPlayer().getNickname() : "";
    }


    public String getCurrentEmail() {
        return Main.getClient().getPlayer() != null ? Main.getClient().getPlayer().getEmail() : "";
    }


    public int getMaxPoints() {
        return Main.getClient().getPlayer() != null ? Main.getClient().getPlayer().getMax_point() : 0;
    }


    public int getGamesPlayed() {
        return Main.getClient().getPlayer() != null ? Main.getClient().getPlayer().getGames_played() : 0;
    }

    public Result changeUsername(String username) throws IOException {
        if (Main.getClient().getPlayer().getUsername().equals(username))
            return new Result(false, "You Already Have This Username!");
        if (!usernameCheck(username))
            return new Result(false, "Invalid Username Format!");
        if (!isUnique(username))
            return new Result(false, "Username is Already Taken!");

        List<User> users = UserStorage.loadUsers();
        for (User user: users) {
            if (user.getUsername().equals(Main.getClient().getPlayer().getUsername())) {
                user.setUsername(username);
                UserStorage.saveUsers(users);
                SessionManager.saveSession(user.getUsername(), SessionManager.isLoggedIn());
                Main.getClient().getPlayer().setUsername(username);
                return new Result(true, "Username Successfully Changed.");
            }
        }

        return new Result(false, "User Not Found!");
    }

    public Result changeEmail(String email) throws IOException {
        if (Main.getClient().getPlayer().getEmail().equals(email))
            return new Result(false, "You Already Have This Email!");
        if (!emailCheck(email))
            return new Result(false, "Invalid Email Format!");

        List<User> users = UserStorage.loadUsers();
        for (User user: users) {
            if (user.getUsername().equals(Main.getClient().getPlayer().getUsername())) {
                user.setEmail(email);
                UserStorage.saveUsers(users);
                Main.getClient().getPlayer().setEmail(email);
                break;
            }
        }

        return new Result(true, "Email Successfully Changed.");
    }

    public Result changeNickname(String nickname) throws IOException {
        if (Main.getClient().getPlayer().getNickname().equals(nickname))
            return new Result(false, "You Already Have This Nickname!");
        if (!CheckName(nickname))
            return new Result(false, "Invalid Nickname Format!");

        List<User> users = UserStorage.loadUsers();
        for (User user: users) {
            if (user.getUsername().equals(Main.getClient().getPlayer().getUsername())) {
                user.setNickname(nickname);
                UserStorage.saveUsers(users);
                Main.getClient().getPlayer().setNickname(nickname);
                break;
            }
        }

        return new Result(true, "Nickname Successfully Changed.");
    }


    public Result validateOldPassword(String oldPassword) {
        if (!Main.getClient().getPlayer().getHashPass().equals(PasswordHashUtil.hashPassword(oldPassword))) {
            return new Result(false, "Your Old Password is NOT Correct!");
        }
        return new Result(true, "Old password is correct");
    }

    public Result changePassword(String newPassword, String oldPassword) throws IOException {
        Result oldPassResult = validateOldPassword(oldPassword);
        if (!oldPassResult.IsSuccess()) {
            return oldPassResult;
        }

        if (Main.getClient().getPlayer().getHashPass().equals(PasswordHashUtil.hashPassword(newPassword))) {
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
            if (user.getUsername().equals(Main.getClient().getPlayer().getUsername())) {
                user.setHashPass(PasswordHashUtil.hashPassword(newPassword));
                UserStorage.saveUsers(users);
                Main.getClient().getPlayer().setHashPass(PasswordHashUtil.hashPassword(newPassword));
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
