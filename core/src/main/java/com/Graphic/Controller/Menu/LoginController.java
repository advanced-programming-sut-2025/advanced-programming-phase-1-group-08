package com.Graphic.Controller.Menu;

import com.Graphic.model.Enum.Menu;
import com.Graphic.model.Enum.SecurityQuestions;
import com.Graphic.model.HelpersClass.Result;
import com.Graphic.model.SaveData.PasswordHashUtil;
import com.Graphic.model.SaveData.UserDataBase;
import com.Graphic.model.SaveData.UserStorage;
import com.Graphic.model.User;
import com.Graphic.model.App;

import java.io.IOException;
import java.util.Scanner;

import static com.Graphic.Controller.Menu.RegisterController.*;

public class LoginController {

    private User forgotPasswordUser;

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

    public Result checkUsernameAndGetQuestion(String username) throws IOException {
        for (User user : UserStorage.loadUsers()) {
            if (user.getUsername().equals(username)) {
                forgotPasswordUser = user;
                SecurityQuestions question = user.getMySecurityQuestion();
                return new Result(true, question.getQuestionText());
            }
        }
        forgotPasswordUser = null;
        return new Result(false, "Username Doesn't Exist!");
    }

    public Result verifySecurityAnswer(String answer) {
        if (forgotPasswordUser == null) {
            return new Result(false, "No active password reset process!");
        }

        if (answer.equals(forgotPasswordUser.getMySecurityAnswer())) {
            return new Result(true, "Answer correct! You can now set a new password.");
        } else {
            forgotPasswordUser = null;
            return new Result(false, "Wrong Answer! Password reset cancelled.");
        }
    }

    public Result setNewPassword(String newPassword) throws IOException {
        if (forgotPasswordUser == null) {
            return new Result(false, "No active password reset process!");
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

        App.currentUser = forgotPasswordUser;
        App.currentMenu = Menu.MainMenu;

        String username = forgotPasswordUser.getUsername();
        forgotPasswordUser = null;

        return new Result(true, "Password successfully reset for user: " + username);
    }

    public String generatePasswordSuggestion() {
        return generateRandomPass();
    }

    public void cancelForgotPassword() {
        forgotPasswordUser = null;
    }

    @Deprecated
    public Result ForgotPassRes(String username) throws IOException {
        Scanner scanner = new Scanner(System.in);

        for (User user : UserStorage.loadUsers())
            if (user.getUsername().equals(username)) {
                System.out.println("Answer your Security Question.");
                System.out.println(user.getMySecurityQuestion().getQuestionText());
                String response = scanner.nextLine();

                if (response.equals(user.getMySecurityAnswer())) {
                    System.out.println("Great, Now Enter Your New Password.(type random for Random Pass)");
                    String choice = scanner.nextLine();

                    if (choice.equals("random")) {
                        String suggestion = null;
                        while (true) {
                            suggestion = generateRandomPass();
                            System.out.println("Do You Approve This Password?[Y/N/back] Suggested Pass: " + suggestion);
                            String userResponse = scanner.nextLine();
                            if (userResponse.trim().equalsIgnoreCase("y")) {
                                System.out.println("You Will Be Directed to Main Menu");
                                user.setHashPass(PasswordHashUtil.hashPassword(suggestion));
                                UserDataBase.updatePassword(username, PasswordHashUtil.hashPassword(suggestion));
                                App.currentMenu = Menu.MainMenu;
                                return new Result(true, "Your Password is: " + suggestion);
                            } else if (userResponse.toLowerCase().trim().equals("back"))
                                return new Result(false, "Returned to LogIn Menu");

                            System.out.println("Generating New Password...");
                        }
                    } else if (!choice.trim().toLowerCase().equals("random")) {
                        if (!passCheck(choice))
                            return new Result(false, "password format is invalid!");
                        if (passIsStrong(choice) != null)
                            return new Result(false, passIsStrong(choice));

                        String hashed = PasswordHashUtil.hashPassword(choice);
                        user.setHashPass(hashed);
                        UserDataBase.updatePassword(username, hashed);
                        System.out.println("You Will Be Directed to Main Menu");
                        App.currentMenu = Menu.MainMenu;
                        return new Result(true, "Your Password is: " + choice);
                    }
                } else
                    return new Result(false, "Wrong Answer!, You Are Now in LogIn Menu.");
            }

        return new Result(false, "Username Doesn't Exist!");
    }
}
