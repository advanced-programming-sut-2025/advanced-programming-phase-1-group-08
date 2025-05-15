//package model.SaveData;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import model.User;
//
//import java.io.*;
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class UserDataBase {
//    private static final String FILE_PATH = "users.json";
//    private static final Gson gson = new Gson();
//
//    public static List<User> loadUsers() {
//        try (Reader reader = new FileReader(FILE_PATH)) {
//            Type listType = new TypeToken<List<User>>(){}.getType();
//            return gson.fromJson(reader, listType);
//        } catch (IOException e) {
//            return new ArrayList<>();
//        }
//    }
//
//    public static void saveUsers(List<User> users) {
//        try (Writer writer = new FileWriter(FILE_PATH)) {
//            gson.toJson(users, writer);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void addUser(User newUser) {
//        List<User> userss = loadUsers();
//        userss.add(newUser);
//        saveUsers(userss);
//    }
//
//    public static User findUserByUsername(String username) {
//        for (User user : loadUsers()) {
//            if (user.getUsername().equals(username)) return user;
//        }
//        return null;
//    }
//
//    public static boolean updatePassword(String username, String newPassword) {
//        List<User> users = loadUsers();
//        for (User user : users) {
//            if (user.getUsername().equals(username)) {
//                user.setHashPass(PasswordHashUtil.hashPassword(newPassword));
//                user.setPassword(newPassword);
//                saveUsers(users);
//                return true;
//            }
//        }
//        return false;
//    }
//}


package model.SaveData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.App;
import model.User;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UserDataBase {

    private static final String FILE_PATH = "basic_users.json";
    private static final Gson gson = new Gson();

    public static List<UserBasicInfo> loadUsers() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<List<UserBasicInfo>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public static void saveUsers(List<UserBasicInfo> users) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addUser(UserBasicInfo newUser) {
        List<UserBasicInfo> users = loadUsers();
        users.add(newUser);
        saveUsers(users);
    }

    public static User findUserByUsername(String username) {
        for (UserBasicInfo user : loadUsers()) {
            if (App.currentUser == null) {
                if (user.getUsername().equals(username)) {
                    return new User(user.getUsername(), user.getNickname(), user.getEmail(), user.getGender(),
                            0, 200, user.getHashpass());
                }
            }
            for (User user1 : App.users)
                if (user.getUsername().equals(username) && user1.getUsername().equals(username)) return user1;
        }
        return null;
    }

    public static UserBasicInfo findUserB(String username) {
        for (UserBasicInfo user : loadUsers()) {
                if (user.getUsername().equals(username)) return user;
        }
        return null;
    }

    public static void updatePassword(String username, String newPasswordHashed) {
        List<UserBasicInfo> users = loadUsers();
        for (UserBasicInfo user : users) {
            if (user.getUsername().equals(username)) {
                user.setHashpass(newPasswordHashed);
                saveUsers(users);
                return;
            }
        }
    }
}