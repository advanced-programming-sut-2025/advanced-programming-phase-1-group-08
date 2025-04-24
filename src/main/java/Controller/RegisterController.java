package Controller;

import model.App;
import model.Result;
import model.User;

import java.util.regex.Pattern;

public class RegisterController {
    private boolean CheckName(String name) {
        return name != null && name.matches("^[a-zA-Z0-9-]+$");
    }

    private boolean emailCheck(String email) {
        return email != null &&
                 email.matches("^(?=[a-zA-Z0-9][a-zA-Z0-9._-]*[a-zA-Z0-9])(?:(?!\\.\\.)[a-zA-Z0-9._-])" +
                         "+@(?=[a-zA-Z0-9][a-zA-Z0-9.-]*[a-zA-Z0-9])(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z0-9-]{2,}$");
    }

    private boolean passCheck(String password) {
        String correctPattern = "^[a-zA-Z0-9!#$%^\\&*\\(\\)=+\\{\\}\\[\\]\\|\\/\\\\:;'\"<>,\\?\\-]+$";
        return password != null && Pattern.compile(correctPattern).matcher(password).matches();
    }

    private boolean usernameCheck(String username) {
        String correctPattern = "\\s*[a-zA-Z][a-zA-Z0-9-_.]{3,9}\\s*";
        return username != null && Pattern.compile(correctPattern).matcher(username).matches();
    }

    private boolean isUnique(String username) {
        for (User user : App.users) {
            if (user.getUsername().equals(username))
                return false;
        }
        return true;
    }

    public Result register (String Username , String Password , String Email , String Name) {
        if (!usernameCheck(Username))
            return new Result(false ,"username format is invalid!");
        else if (!isUnique(Username))
            return new Result(false ,"this username is already taken!");
        else if (!passCheck(Password))
            return new Result(false ,"password format is invalid!");
        else if (!emailCheck(Email))
            return new Result(false ,"email format is invalid!");
        else if (!CheckName(Name))
            return new Result(false ,"name format is invalid!");

        return new Result(true ,"user registered successfully.you are now in login menu!");
    }
}
