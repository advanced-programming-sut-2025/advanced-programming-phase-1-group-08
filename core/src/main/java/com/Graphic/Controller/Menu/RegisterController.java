package com.Graphic.Controller.Menu;

import com.Graphic.model.Enum.SecurityQuestions;
import com.Graphic.model.Game;
import com.Graphic.model.HelpersClass.Result;
import com.Graphic.model.SaveData.UserStorage;
import com.Graphic.model.User;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

import static com.Graphic.model.App.currentUser;

public class RegisterController {

    public static boolean CheckName(String name) {
        return name != null && name.matches("^[a-zA-Z0-9-]+$");
    }

    public static boolean emailCheck(String email) {
        return email != null &&
            email.matches("^(?=[a-zA-Z0-9][a-zA-Z0-9._-]*[a-zA-Z0-9])(?:(?!\\.\\.)[a-zA-Z0-9._-])" +
                "+@(?=[a-zA-Z0-9][a-zA-Z0-9.-]*[a-zA-Z0-9])(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z0-9-]{2,}$");
    }

    public static boolean passCheck(String password) {
        String correctPattern = "^[a-zA-Z0-9!#$%^\\&*\\(\\)=+\\{\\}\\[\\]\\|\\/\\\\:;'\"<>,\\?\\-]+$";
        return password != null && Pattern.compile(correctPattern).matcher(password).matches();
    }

    public static String passIsStrong(String pass) {
        if (pass.length() < 8)
            return "Password Must Have at Least 8 Characters!";
        if (!Pattern.compile("\\d").matcher(pass).find())
            return "Password Must Have at Least One Digit!";
        if (!Pattern.compile("[a-z]").matcher(pass).find())
            return "Password Must Have at Least One Lowercase!";
        if (!Pattern.compile("[A-Z]").matcher(pass).find())
            return "Password Must Have at Least One Uppercase!";
        if (!Pattern.compile("[!#$%^&*()=+{}\\[\\]|\\/\\\\:;'\"<>,\\?]").matcher(pass).find())
            return "Password Must Have at Least One Special Character!";
        return null;
    }

    public static boolean usernameCheck(String username) {
        String correctPattern = "\\s*[a-zA-Z][a-zA-Z0-9-]{3,9}\\s*";
        return username != null && Pattern.compile(correctPattern).matcher(username).matches();
    }

    public static boolean isUnique(String username) throws IOException {
        for (User user: UserStorage.loadUsers()) {
            if (user.getUsername().equals(username))
                return false;
        }
        return true;
    }

    public static String SuggestUsername(String oldUsername) throws IOException {
        String username = oldUsername;
        do {
            username = username + "-";
        } while (!isUnique(username));
        return username;
    }

    public static String generateRandomPass() {
        String possibleDigits = "0123456789";
        String possibleUppers = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String possibleLowers = possibleUppers.toLowerCase();
        String possibleSpecialCharacters = "^&*!#?()|[]{}/:;',<>";
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 3; i++) {
            int index = rand.nextInt(possibleUppers.length());
            char randomChar = possibleUppers.charAt(index);
            sb.append(randomChar);
        }
        for (int i = 0; i < 3; i++) {
            int index = rand.nextInt(possibleDigits.length());
            char randomChar = possibleDigits.charAt(index);
            sb.append(randomChar);
        }
        for (int i = 0; i < 3; i++) {
            int index = rand.nextInt(possibleLowers.length());
            char randomChar = possibleLowers.charAt(index);
            sb.append(randomChar);
        }
        for (int i = 0; i < 2; i++) {
            int index = rand.nextInt(possibleSpecialCharacters.length());
            char randomChar = possibleSpecialCharacters.charAt(index);
            sb.append(randomChar);
        }

        return sb.toString();
    }

    public Result validateUsername(String username) throws IOException {
        if (!usernameCheck(username))
            return new Result(false, "Username format is invalid!");
        if (!isUnique(username))
            return new Result(false, "Username is already taken!");
        return new Result(true, "Username is valid");
    }

    public Result validatePassword(String password, String confirmPassword) {
        if (!passCheck(password))
            return new Result(false, "Password format is invalid!");

        String strengthError = passIsStrong(password);
        if (strengthError != null)
            return new Result(false, strengthError);

        if (!password.equals(confirmPassword))
            return new Result(false, "Passwords don't match!");

        return new Result(true, "Password is valid");
    }

    public Result validateEmail(String email) {
        if (!emailCheck(email))
            return new Result(false, "Email format is invalid!");
        return new Result(true, "Email is valid");
    }

    public Result validateNickname(String nickname) {
        if (!CheckName(nickname))
            return new Result(false, "Nickname format is invalid!");
        return new Result(true, "Nickname is valid");
    }

    public Result validateGender(String gender) {
        if (!(gender.toLowerCase().equals("male") || gender.toLowerCase().equals("female")))
            return new Result(false, "Gender must be Male or Female");
        return new Result(true, "Gender is valid");
    }

    public Result completeRegistration(String username, String password, String nickname,
                                       String email, String gender, SecurityQuestions securityQuestion,
                                       String securityAnswer) throws IOException {
        Game.AddNewUser(username, password, nickname, email, gender, securityQuestion, securityAnswer);

        User newUser = UserStorage.loadUsers().stream()
            .filter(u -> u.getUsername().equals(username))
            .findFirst()
            .orElse(null);

        if (newUser != null) {
            currentUser = newUser;
            currentUser.setMySecurityQuestion(securityQuestion);
            currentUser.setMySecurityAnswer(securityAnswer);
            return new Result(true, "User registered successfully! Welcome to the game!");
        }

        return new Result(false, "Registration failed!");
    }

    @Deprecated
    public void PickSecurityQA(String Username, String Password, String NickName, String Email, String Gender) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Now Choose a Security Question and Answer It.");
        System.out.println("1: What's My Favorite Animal?");
        System.out.println("2: What's My Favorite Food?");
        System.out.println("3: What's My Favorite Movie?");
        System.out.println("4: What's My Favorite Book?");
        System.out.println("5: What's My Favorite Game?");
        String inp = scanner.nextLine();
        SecurityQuestions MySecQ;
        Pattern pattern = Pattern.compile("\\s*pick\\s+question\\s+" +
            "-q\\s+(?<number>\\d+)\\s+" +
            "-a\\s+(?<answer>\\S+)\\s+" +
            "-c\\s+(?<confirm>\\S+)\\s*");
        var matcher = pattern.matcher(inp);
        if (matcher.matches()) {
            int questionNumber = Integer.parseInt(matcher.group("number"));
            String answer = matcher.group("answer");
            String confirm = matcher.group("confirm");
            if (!answer.equals(confirm)) {
                System.out.println("Answer and confirmation do not match! Try Again.");
            } else {
                System.out.println("Security question and answer set successfully!");
                MySecQ = SecurityQuestions.values()[questionNumber - 1];
                Game.AddNewUser(Username, Password, NickName, Email, Gender, MySecQ, answer);
                currentUser.setMySecurityQuestion(SecurityQuestions.values()[questionNumber - 1]);
                currentUser.setMySecurityAnswer(answer);
            }
        } else {
            System.out.println("Invalid Command!");
        }
    }

    @Deprecated
    public Result register(String Username, String Password, String ConfirmPass, String NickName, String Email, String Gender) throws IOException {
        return new Result(false, "Please use the new GUI-based registration");
    }
}
