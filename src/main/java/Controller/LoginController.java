package Controller;
import model.Enum.Menu;
import model.Result;
import model.SaveData.PasswordHashUtil;
import model.SaveData.UserDataBase;
import model.User;
import model.App;

import java.util.Scanner;

import static Controller.RegisterController.*;

public class LoginController {
    public Result LoginRes(String username, String password) {
        if (UserDataBase.findUserByUsername(username) != null) {
            User user = UserDataBase.findUserByUsername(username);
            if (user.getHashPass().equals(PasswordHashUtil.hashPassword(password)))
                    return new Result(true, "User Logged in Successfully.You are Now in Main Menu!");
            else
                    return new Result(false, "Password is Incorrect!");

        }

        return new Result(false , "Username Doesn't Exist!");
    }

    public Result ForgotPassRes(String username) {
        for (User user : App.users)
            if (user.getUsername().equals(username)) {
                Scanner scanner = new Scanner(System.in);
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
                            if (userResponse.trim().toLowerCase().equals("y")) {
                                System.out.println("You Are Now in Main Menu");
                                user.setPassword(suggestion);
                                App.currentMenu = Menu.MainMenu;
                                return new Result(true, "Your Password is: " + user.getPassword());
                            }
                            else if (userResponse.toLowerCase().trim().equals("back"))
                                return new Result(false, "Returned to LogIn Menu");

                            System.out.println("Generating New Password...");
                        }
                    }
                    else if (!choice.trim().toLowerCase().equals("random")) {
                        if (!passCheck(choice))
                            return new Result(false, "password format is invalid!");
                        if (passIsStrong(choice) != null)
                            return new Result(false , passIsStrong(choice));

                        user.setPassword(choice);
                        model.SaveData.UserDataBase.updatePassword(username, choice);

                        return new Result(true, "Your Password is: " + user.getPassword());
                    }
                }
                else
                    return new Result(false, "Wrong Answer!, You Are Now in LogIn Menu.");
            }

        return new Result(false , "Username Doesn't Exist!");
    }
}








