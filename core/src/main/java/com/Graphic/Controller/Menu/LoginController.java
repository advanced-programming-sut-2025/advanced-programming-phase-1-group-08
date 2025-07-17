package com.Graphic.Controller.Menu;

import com.Graphic.model.Enum.Menu;
import com.Graphic.model.HelpersClass.Result;
import com.Graphic.model.SaveData.PasswordHashUtil;
import com.Graphic.model.SaveData.UserDataBase;
import com.Graphic.model.SaveData.UserStorage;
import com.Graphic.model.User;
import com.Graphic.model.App;
import com.Graphic.model.Enum.SecurityQuestions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.Graphic.Controller.Menu.RegisterController.*;

public class LoginController {

    private User forgotPasswordUser;
    private SecurityQuestions currentSecurityQuestion;

    public Result LoginRes(String username, String password) throws IOException {
        for (User user : UserStorage.loadUsers()) {
            if (user.getUsername().equals(username)) {
                if (user.getHashPass().equals(PasswordHashUtil.hashPassword(password))) {
                    return new Result(true, "User Logged in Successfully.You are Now in Main Menu!");
                }
                return new Result(false, "Password is Incorrect!");
            }
        }
        return new Result(false, "Username Doesn't Exist!");
    }


    public Result checkUsernameForPasswordReset(String username) throws IOException {
        for (User user : UserStorage.loadUsers()) {
            if (user.getUsername().equals(username)) {
                forgotPasswordUser = user;
                currentSecurityQuestion = user.getMySecurityQuestion();
                return new Result(true, currentSecurityQuestion.getQuestionText());
            }
        }
        forgotPasswordUser = null;
        currentSecurityQuestion = null;
        return new Result(false, "Username Doesn't Exist!");
    }


    public Result verifySecurityAnswer(String answer) {
        if (forgotPasswordUser == null) {
            return new Result(false, "No password reset in progress!");
        }

        if (answer.equals(forgotPasswordUser.getMySecurityAnswer())) {
            return new Result(true, "Security answer correct! Please enter new password.");
        } else {
            forgotPasswordUser = null;
            currentSecurityQuestion = null;
            return new Result(false, "Wrong Answer! You Are Now in LogIn Menu.");
        }
    }


    public Result setNewPassword(String newPassword) throws IOException {
        if (forgotPasswordUser == null) {
            return new Result(false, "No password reset in progress!");
        }

        if (newPassword.equals("random")) {
            String randomPass = generateRandomPass();
            return new Result(true, "SUGGESTION:" + randomPass);
        }

        if (!passCheck(newPassword)) {
            return new Result(false, "Password format is invalid!");
        }

        String strengthError = passIsStrong(newPassword);
        if (strengthError != null) {
            return new Result(false, strengthError);
        }

        String hashedPassword = PasswordHashUtil.hashPassword(newPassword);
        forgotPasswordUser.setHashPass(hashedPassword);
        UserDataBase.updatePassword(forgotPasswordUser.getUsername(), hashedPassword);

        List<User> users = UserStorage.loadUsers();
        for (User user : users) {
            if (user.getUsername().equals(forgotPasswordUser.getUsername())) {
                user.setHashPass(hashedPassword);
                UserStorage.saveUsers(users);
                break;
            }
        }

        String username = forgotPasswordUser.getUsername();
        forgotPasswordUser = null;
        currentSecurityQuestion = null;

        return new Result(true, "Password changed successfully for user: " + username);
    }


    public List<String> generateRandomPasswordSuggestions(int count) {
        List<String> suggestions = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            suggestions.add(generateRandomPass());
        }
        return suggestions;
    }


    public void cancelPasswordReset() {
        forgotPasswordUser = null;
        currentSecurityQuestion = null;
    }


    public String getCurrentSecurityQuestion() {
        if (currentSecurityQuestion != null) {
            return currentSecurityQuestion.getQuestionText();
        }
        return null;
    }

    public boolean isPasswordResetInProgress() {
        return forgotPasswordUser != null;
    }

    public String getResetUsername() {
        if (forgotPasswordUser != null) {
            return forgotPasswordUser.getUsername();
        }
        return null;
    }
}
