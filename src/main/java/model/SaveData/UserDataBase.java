package model.SaveData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.User;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class UserDataBase {
    private static final String FILE_PATH = "users.json";
    private static final Gson gson = new Gson();

    public static List<User> loadUsers() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<List<User>>(){}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public static void saveUsers(List<User> users) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addUser(User newUser) {
        List<User> userss = loadUsers();
        userss.add(newUser);
        saveUsers(userss);
    }

    public static User findUserByUsername(String username) {
        for (User user : loadUsers()) {
            if (user.getUsername().equals(username)) return user;
        }
        return null;
    }

    public static boolean updatePassword(String username, String newPassword) {
        List<User> users = loadUsers();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                user.setHashPass(PasswordHashUtil.hashPassword(newPassword));
                user.setPassword(newPassword);
                saveUsers(users);
                return true;
            }
        }
        return false;
    }
}
