package com.Graphic.model.SaveData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.Graphic.model.User;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UserDataBase {

    private static final String FILE_PATH = "users.json";
    private static final Gson gson = new Gson();
    private static List<User> usersCache = null;

    public static synchronized List<User> loadUsers() {
        if (usersCache == null) {
            try (Reader reader = new FileReader(FILE_PATH)) {
                Type listType = new TypeToken<List<User>>(){}.getType();
                usersCache = gson.fromJson(reader, listType);
                if (usersCache == null) {
                    usersCache = new ArrayList<>();
                }
            } catch (IOException e) {
                usersCache = new ArrayList<>();
            }
        }
        return usersCache;
    }

    public static synchronized void saveUsers(List<User> users) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(users, writer);
            usersCache = users;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void addUser(User newUser) {
        List<User> users = loadUsers();
        users.add(newUser);
        saveUsers(users);
    }

    public static User findUserByUsername(String username) {
        List<User> users = loadUsers();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static synchronized void updateUser(String username, User updatedUser) {
        List<User> users = loadUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                users.set(i, updatedUser);
                saveUsers(users);
                return;
            }
        }
    }

    public static synchronized boolean updatePassword(String username, String newPassword) {
        List<User> users = loadUsers();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                user.setPassword(newPassword);
                user.setHashPass(PasswordHashUtil.hashPassword(newPassword));
                saveUsers(users);
                return true;
            }
        }
        return false;
    }

    public static synchronized boolean updateUsername(String oldUsername, String newUsername) {
        if (findUserByUsername(newUsername) != null) {
            return false;
        }

        List<User> users = loadUsers();
        for (User user : users) {
            if (user.getUsername().equals(oldUsername)) {
                user.setUsername(newUsername);
                saveUsers(users);
                return true;
            }
        }
        return false;
    }

    public static synchronized boolean updateNickname(String username, String newNickname) {
        List<User> users = loadUsers();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                user.setNickname(newNickname);
                saveUsers(users);
                return true;
            }
        }
        return false;
    }

    public static synchronized boolean updateEmail(String username, String newEmail) {
        List<User> users = loadUsers();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                user.setEmail(newEmail);
                saveUsers(users);
                return true;
            }
        }
        return false;
    }

    public static synchronized boolean deleteUser(String username) {
        List<User> users = loadUsers();
        boolean removed = users.removeIf(user -> user.getUsername().equals(username));
        if (removed) {
            saveUsers(users);
        }
        return removed;
    }

    public static List<User> getAllUsers() {
        return new ArrayList<>(loadUsers());
    }

    public static int getUserCount() {
        return loadUsers().size();
    }

    public static boolean userExists(String username) {
        return findUserByUsername(username) != null;
    }

    public static synchronized void clearCache() {
        usersCache = null;
    }

    public static void testSerialization(Object obj) {
        Gson gson = new Gson();
        try {
            String json = gson.toJson(obj);
            System.out.println("Serialization OK: " + json);

            Object restored = gson.fromJson(json, obj.getClass());
            System.out.println("Deserialization OK: " + restored);
        } catch (Exception e) {
            System.err.println("FAILED: " + e.getMessage());
        }
    }
}
