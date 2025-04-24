package Controller;

import View.RegisterMenu;
import model.App;
import model.Result;

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


        App.currentUser.getEmail(email);
        return new Result(true, "Username Successfully Changed.");
    }
}
