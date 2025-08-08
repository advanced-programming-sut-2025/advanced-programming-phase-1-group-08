package com.Graphic.Controller.Menu;

import com.Graphic.model.ClientServer.Message;
import com.Graphic.model.Enum.Commands.CommandType;
import com.Graphic.model.Enum.SecurityQuestions;
import com.Graphic.model.Game;
import com.Graphic.model.HelpersClass.Result;
import com.Graphic.model.SaveData.UserStorage;
import com.Graphic.model.User;
import com.badlogic.gdx.Gdx;

import java.io.IOException;
import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

import static com.Graphic.model.App.currentUser;

public class RegisterController {

    public Message attemptRegistration(Message message) {
        //String username = usernameField.getText().trim();
        //String password = passwordField.getText();
        //String confirmPassword = confirmPasswordField.getText();
        //String nickname = nicknameField.getText().trim();
        //String email = emailField.getText().trim();
        //String gender = genderBox.getSelected();
        //int questionIndex = securityQuestionBox.getSelectedIndex();
        //String answer = securityAnswerField.getText().trim();
        //String confirmAnswer = confirmAnswerField.getText().trim();

        try {
            Message result = validateUsername(message.getFromBody("Username"));
            if (result != null) {
                return result;
            }

            result = validatePassword(message.getFromBody("Password"), message.getFromBody("Confirm Password"));
            if (result != null) {
                return result;
            }

            result = validateEmail(message.getFromBody("Email"));
            if (result != null) {
                return result;
            }

            result = validateNickname(message.getFromBody("Nickname"));
            if (result != null) {
                return result;
            }

            if (message.getFromBody("Gender").equals("Select Gender")) {
                HashMap<String , Object> body = new HashMap<>();
                body.put("Error","Please select your gender!");
                return new Message(CommandType.ERROR , body);
            }

            if (message.getIntFromBody("Question index") == 0) {
                HashMap<String, Object> body = new HashMap<>();
                body.put("Error", "Please select a security question!");
                return new Message(CommandType.ERROR, body);
            }

            if (message.getFromBody("Answer") == null) {
                HashMap<String, Object> body = new HashMap<>();
                body.put("Error", "Please provide a security answer!");
                return new Message(CommandType.ERROR, body);
            }

            if (! message.getFromBody("Answer").equals(message.getFromBody("Confirm Answer")) ) {
                HashMap<String, Object> body = new HashMap<>();
                body.put("Error", "Security answers don't match!");
                return new Message(CommandType.ERROR, body);
            }


            SecurityQuestions secQuestion = SecurityQuestions.values()[message.getIntFromBody("Question index") - 1];
            result = completeRegistration(
                message.getFromBody("Username"), message.getFromBody("Password"),
                message.getFromBody("Nickname"), message.getFromBody("Email"),
                message.getFromBody("Gender"),
                secQuestion, message.getFromBody("Answer"));

            return result;

        } catch (IOException e) {
            HashMap<String, Object> body = new HashMap<>();
            body.put("Error" , "Registration error");
            return new Message(CommandType.ERROR, body);
        }
    }

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
        System.out.println("TEST");
        return true;
    }

    public static String SuggestUsername(String oldUsername) throws IOException {
        String username = oldUsername;
        do {
            username = username + "-";
        } while (!isUnique(username));
        return username;
    }

    public static Message generateRandomPass() {
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

        HashMap<String , Object> body = new HashMap<>();
        body.put("Password" ,sb.toString() );

        return new Message(CommandType.GENERATE_RANDOM_PASS , body);

    }

    public Message validateUsername(String username) throws IOException {
        HashMap<String , Object> body = new HashMap<>();
        if (!usernameCheck(username)) {
            body.put("Error", "Username is not valid!");
            return new Message(CommandType.ERROR, body);
        }
        if (!isUnique(username)) {
            body.put("Error", "Username is already taken!");
            return new Message(CommandType.ERROR, body);
        }

        return null;
    }

    public Message validatePassword(String password, String confirmPassword) {
        HashMap<String , Object> body = new HashMap<>();
        if (!passCheck(password)) {
            body.put("Error", "Password is not valid!");
            return new Message(CommandType.ERROR , body);
            //return new Result(false, "Password format is invalid!");
        }

        String strengthError = passIsStrong(password);
        if (strengthError != null) {
            body.put("Error", strengthError);
            return new Message(CommandType.ERROR , body);
            //return new Result(false, strengthError);
        }

        if (!password.equals(confirmPassword)) {
            body.put("Error", "Passwords don't match!");
            return new Message(CommandType.ERROR , body);
            //return new Result(false, "Passwords don't match!");
        }
        return null;

        //return new Result(true, "Password is valid");
    }

    public Message validateEmail(String email) {
        HashMap<String , Object> body = new HashMap<>();
        if (!emailCheck(email)) {
            body.put("Error" , "Email is not valid!");
            return new Message(CommandType.ERROR , body);
            //return new Result(false, "Email format is invalid!");
        }
        return null;
        //return new Result(true, "Email is valid");
    }

    public Message validateNickname(String nickname) {
        HashMap<String , Object> body = new HashMap<>();

        if (!CheckName(nickname)) {
            body.put("Error" , "Nickname is not valid!");
            return new Message(CommandType.ERROR , body);
            //return new Result(false, "Nickname format is invalid!");
        }
        //return new Result(true, "Nickname is valid");
        return null;
    }

    public Result validateGender(String gender) {
        if (!(gender.toLowerCase().equals("male") || gender.toLowerCase().equals("female")))
            return new Result(false, "Gender must be Male or Female");
        return new Result(true, "Gender is valid");
    }

    public Message completeRegistration(String username, String password, String nickname,
                                       String email, String gender, SecurityQuestions securityQuestion,
                                       String securityAnswer) throws IOException {

        Game.AddNewUser(username, password, nickname, email, gender, securityQuestion, securityAnswer);

        User newUser = UserStorage.loadUsers().stream()
            .filter(u -> u.getUsername().equals(username))
            .findFirst()
            .orElse(null);

        HashMap<String , Object> body = new HashMap<>();

        if (newUser != null) {
            newUser.setMySecurityQuestion(securityQuestion);
            newUser.setMySecurityAnswer(securityAnswer);
            body.put("Player", newUser);
            return new Message(CommandType.LOGIN_SUCCESS , body);
        }

        body.put("Error" ,"Registration failed!" );
        return new Message(CommandType.ERROR , body);
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
