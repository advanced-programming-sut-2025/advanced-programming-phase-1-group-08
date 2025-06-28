//package model.SaveData;
//
//import com.google.gson.Gson;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//import model.User;
//
//import java.io.*;
//import java.util.HashMap;
//import java.util.Map;
//
//public class SessionManager {
//    private static final String SESSION_FILE = "session.json";
//    private static final Gson gson = new Gson();
//
//    public static void saveSession(User user, boolean isLoggedIn) {
//        Map<String, Object> sessionData = new HashMap<>();
//        sessionData.put("isLoggedIn", isLoggedIn);
//        sessionData.put("user", user);
//
//        try (Writer writer = new FileWriter(SESSION_FILE)) {
//            gson.toJson(sessionData, writer);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static boolean isLoggedIn() {
//        try (Reader reader = new FileReader(SESSION_FILE)) {
//            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
//            return json.get("isLoggedIn").getAsBoolean();
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    public static User getLoggedInUser() {
//        try (Reader reader = new FileReader(SESSION_FILE)) {
//            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
//            if (json.get("isLoggedIn").getAsBoolean()) {
//                return gson.fromJson(json.get("user"), User.class);
//            }
//        } catch (Exception e) {
//            // فایل خراب یا موجود نیست
//        }
//        return null;
//    }
//
//    public static void logout() {
//        try (Writer writer = new FileWriter(SESSION_FILE)) {
//            writer.write("{}");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}

//
//import com.google.gson.Gson;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//import model.User;
//
//import java.io.*;
//import java.util.HashMap;
//import java.util.Map;
//
//import static model.Color_Eraser.RED;
//import static model.Color_Eraser.RESET;
//
//public class SessionManager {
//    private static final String SESSION_FILE = "session.json";
//    private static final Gson gson = new Gson();
//
//    public static void saveSession(User user, boolean isLoggedIn) {
//        UserBasicInfo userBasicInfo = null;
//        for (UserBasicInfo u: UserDataBase.loadUsers()) {
//            if (u.getUsername().equals(user.getUsername())) {
//                userBasicInfo = new UserBasicInfo(user.getUsername(), user.getHashPass(), userBasicInfo.getEmail(), user.getNickname(),
//                        user.getGender(), user.getMySecurityQuestion().getQuestionText(), user.getMySecurityAnswer());
//                break;
//            }
//        }
//        if (userBasicInfo == null) {
//            System.out.println(RED+"Unknown Error"+RESET);
//            return;
//        }
//
//        Map<String, Object> sessionData = new HashMap<>();
//        sessionData.put("isLoggedIn", isLoggedIn);
//        sessionData.put("user", userBasicInfo);
//
//        try (Writer writer = new FileWriter(SESSION_FILE)) {
//            gson.toJson(sessionData, writer);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static boolean isLoggedIn() {
//        try (Reader reader = new FileReader(SESSION_FILE)) {
//            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
//            return json.has("isLoggedIn") && json.get("isLoggedIn").getAsBoolean();
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    public static UserBasicInfo getLoggedInUser() {
//        try (Reader reader = new FileReader(SESSION_FILE)) {
//            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
//            if (json.has("isLoggedIn") && json.get("isLoggedIn").getAsBoolean()) {
//                return gson.fromJson(json.get("user"), UserBasicInfo.class);
//            }
//        } catch (Exception e) {
//            // فایل خراب یا موجود نیست
//        }
//        return null;
//    }
//
//    public static void logout() {
//        try (Writer writer = new FileWriter(SESSION_FILE)) {
//            writer.write("{}");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
//package model.SaveData;
//import com.google.gson.Gson;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//import model.User;
//
//import java.io.*;
//import java.util.HashMap;
//import java.util.Map;
//
//import static model.SaveData.UserDataBase.findUserB;
//import static model.SaveData.UserDataBase.findUserByUsername;
//
//public class SessionManager {
//    private static final String SESSION_FILE = "session.json";
//    private static final Gson gson = new Gson();
//
//    public static void saveSession(String username, boolean isLoggedIn) {
//        Map<String, Object> sessionData = new HashMap<>();
//        sessionData.put("isLoggedIn", isLoggedIn);
//        sessionData.put("username", username);
//
//        try (Writer writer = new FileWriter(SESSION_FILE)) {
//            gson.toJson(sessionData, writer);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static boolean isLoggedIn() {
//        try (Reader reader = new FileReader(SESSION_FILE)) {
//            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
//            return json.has("isLoggedIn") && json.get("isLoggedIn").getAsBoolean();
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    public static UserBasicInfo getLoggedInUser() {
//        try (Reader reader = new FileReader(SESSION_FILE)) {
//            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
//            if (json.has("isLoggedIn") && json.get("isLoggedIn").getAsBoolean()) {
//                String username = json.get("username").getAsString();
//                // حالا باید کاربر کامل رو از App.users یا UserDataBase پیدا کنیم
//                return findUserB(username); // فرض بر اینکه این متد وجود داره
//
//            }
//        } catch (Exception e) {
//            // فایل خراب یا مشکل داره
//        }
//        return null;
//    }
//
//    public static void logout() {
//        try (Writer writer = new FileWriter(SESSION_FILE)) {
//            writer.write("{}");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}


package com.Graphic.model.SaveData;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.SaveData.UserBasicInfo;
import model.User;

import java.io.*;

import static model.SaveData.UserDataBase.findUserByUsername;


public class SessionManager {
    private static final String SESSION_FILE = "session.json";
    private static final Gson gson = new Gson();

    // ذخیره‌سازی session (فقط یوزرنیم و فلگ)
    public static void saveSession(String username, boolean isLoggedIn) {
        JsonObject sessionData = new JsonObject();
        sessionData.addProperty("isLoggedIn", isLoggedIn);
        sessionData.addProperty("username", username);

        try (Writer writer = new FileWriter(SESSION_FILE)) {
            gson.toJson(sessionData, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // بررسی اینکه یوزری لاگینه یا نه
    public static boolean isLoggedIn() {
        try (Reader reader = new FileReader(SESSION_FILE)) {
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            boolean isLoggedIn = json.has("isLoggedIn") && json.get("isLoggedIn").getAsBoolean();
            boolean hasUsername = json.has("username") && !json.get("username").getAsString().isEmpty();
            return isLoggedIn && hasUsername;
        } catch (Exception e) {
            return false;
        }
    }

    // گرفتن یوزر لاگین شده (فقط در صورتی که فلگ روشنه)
    public static User getLoggedInUser() {
        if (!isLoggedIn()) return null;

        try (Reader reader = new FileReader(SESSION_FILE)) {
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            String username = json.get("username").getAsString();
            return findUserByUsername(username);
        } catch (Exception e) {
            return null;
        }
    }

    // خروج از حساب
    public static void logout() {
        try (Writer writer = new FileWriter(SESSION_FILE)) {
            writer.write("{}"); // خالی کردن فایل
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
