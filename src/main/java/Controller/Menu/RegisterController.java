package Controller.Menu;

import model.Enum.SecurityQuestions;
import model.Game;
import model.Result;
import model.SaveData.UserStorage;
import model.User;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static model.App.currentUser;


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
    public void PickSecurityQA (String Username, String Password, String NickName, String Email, String Gender) throws IOException{
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
        Matcher matcher = pattern.matcher(inp);
        if (matcher.matches()) {
            int questionNumber = Integer.parseInt(matcher.group("number"));
            String answer = matcher.group("answer");
            String confirm = matcher.group("confirm");
            if (!answer.equals(confirm)) {
                System.out.println("Answer and confirmation do not match! Try Again.");
            } else {


                System.out.println("Security question and answer set successfully!");

                //Add User
                MySecQ = SecurityQuestions.values()[questionNumber - 1];
                Game.AddNewUser(Username, Password, NickName, Email, Gender, MySecQ , answer);
                currentUser.setMySecurityQuestion(SecurityQuestions.values()[questionNumber - 1]);
                currentUser.setMySecurityAnswer(answer);
            }
        }
        else {
            System.out.println("Invalid Command!");
        }
    }


    public Result register (String Username , String Password , String ConfirmPass , String NickName , String Email, String Gender) throws IOException {

        // Handle Username
        if (!usernameCheck(Username))
            return new Result(false ,"username format is invalid!");
        if (!isUnique(Username)) {
            System.out.println("Username Is Already Taken!");
            String suggestion = SuggestUsername(Username);
            System.out.println("Do You Want to Use '" + suggestion + "' as Your Username?[Y/N]");
            Scanner scanner = new Scanner(System.in);
            String response = scanner.nextLine().trim().toLowerCase();
            if (!response.toLowerCase().trim().equals("y"))
                return new Result(false ,"Then Try Again.");
            Username = suggestion;
        }

        // Handle Pass
        if (!(Password).trim().toLowerCase().equals("random")) {
            if (!passCheck(Password))
                return new Result(false, "password format is invalid!");
            if (passIsStrong(Password) != null)
                return new Result(false , passIsStrong(Password));
            if (!Password.equals(ConfirmPass)) {
                System.out.println("Password Doesn't Match the Confirmation!");
                System.out.println("ReEnter Password and Password Confirmation in a Line Or type 'back' to Return.");
                Scanner scanner = new Scanner(System.in);
                while (true) {
                    String PassRetry = scanner.nextLine();
                    if (PassRetry.trim().toLowerCase().equals("back"))
                        return new Result(false, "Then Try Again.");
                    String[] words = PassRetry.trim().split("\\s+");
                    if (words.length != 2) {
                        System.out.println("Wrong Format!");
                        System.out.println("ReEnter Password and Password Confirmation in a Line Or type 'back' to Return.");
                        continue;
                    }

                    else if (words[0].equals(words[1]) && passIsStrong(words[0]) == null && passCheck(words[0])) {
                        System.out.println("You Made It!");
                        Password = words[0];
                        break;
                    }
                    // if didn't match again
                    System.out.println("Password Doesn't Match");
                    System.out.println("ReEnter Password and Password Confirmation in a Line Or type 'back' to Return.");
                }
            }
        }
        else if (Password.equals("random")) {
            Scanner scanner = new Scanner(System.in);
            String suggestion = null;
            while (true) {
               suggestion = generateRandomPass();
                System.out.println("Do You Approve This Password?[Y/N/back] Suggested Pass: " + suggestion);
                String response = scanner.nextLine();
                if (response.trim().toLowerCase().equals("y")) {
                    System.out.println("Then Your Pass is " + suggestion);
                    Password = suggestion;
                    break;
                }
                else if (response.toLowerCase().trim().equals("back"))
                    return new Result(false, "Returned to SignUp Menu");

                System.out.println("Generating New Password...");
            }
        }

        // Handle Email
        if (!emailCheck(Email))
            return new Result(false ,"email format is invalid!");

        // Handle Nickname
        if (!CheckName(NickName))
            return new Result(false ,"nickname format is invalid!");

        // Handle Gender
        if (!(Gender.toLowerCase().equals("male") || Gender.toLowerCase().equals("female")))
            return new Result(false, "Gender Has to be Male or Female");


        do {
            PickSecurityQA(Username, Password, NickName, Email, Gender); // it adds the user automatically
        } while (currentUser.getMySecurityAnswer() == null);


        return new Result(true ,"User Registered Successfully.You are now in Main Menu!");
    }

}
