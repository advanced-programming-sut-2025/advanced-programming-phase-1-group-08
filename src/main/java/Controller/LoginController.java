package Controller;
import model.Enum.Menu;
import model.Result;
import model.SaveData.PasswordHashUtil;
import model.SaveData.UserBasicInfo;
import model.SaveData.UserDataBase;
import model.User;
import model.App;

import java.util.Scanner;

import static Controller.RegisterController.*;
import static model.App.currentUser;

public class LoginController {

    public Result LoginRes(String username, String password) {
        UserBasicInfo user = null;
        for (UserBasicInfo userBasicInfo: UserDataBase.loadUsers()) {
            if (userBasicInfo.getUsername().equals(username)) {
                if (userBasicInfo.getHashpass().equals(PasswordHashUtil.hashPassword(password))) {
                    return new Result(true, "User Logged in Successfully.You are Now in Main Menu!");
                }
                return new Result(false, "Password is Incorrect!");
            }
        }
        return new Result(false , "Username Doesn't Exist!");
    }

    public Result ForgotPassRes(String username) {
        for (UserBasicInfo userB : UserDataBase.loadUsers())
            if (userB.getUsername().equals(username)) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Answer your Security Question.");
                System.out.println(userB.getSecQ());
                String response = scanner.nextLine();

                if (response.equals(userB.getSecA())) {
                    System.out.println("Great, Now Enter Your New Password.(type random for Random Pass)");
                    String choice = scanner.nextLine();

                    if (choice.equals("random")) {
                        String suggestion = null;
                        while (true) {
                            suggestion = generateRandomPass();
                            System.out.println("Do You Approve This Password?[Y/N/back] Suggested Pass: " + suggestion);
                            String userResponse = scanner.nextLine();
                            if (userResponse.trim().equalsIgnoreCase("y")) {
                                System.out.println("You Are Now in Main Menu");
                                userB.setHashpass(PasswordHashUtil.hashPassword(suggestion));
                                App.currentMenu = Menu.MainMenu;
                                return new Result(true, "Your Password is: " + suggestion);
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

                        userB.setHashpass(PasswordHashUtil.hashPassword(choice));
                        model.SaveData.UserDataBase.updatePassword(username, choice);

                        return new Result(true, "Your Password is: " + choice);
                    }
                }
                else
                    return new Result(false, "Wrong Answer!, You Are Now in LogIn Menu.");
            }

        return new Result(false , "Username Doesn't Exist!");
    }
}