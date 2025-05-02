package model.SaveData;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.User;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    private static final String SESSION_FILE = "session.json";
    private static final Gson gson = new Gson();

    public static void saveSession(User user, boolean isLoggedIn) {
        Map<String, Object> sessionData = new HashMap<>();
        sessionData.put("isLoggedIn", isLoggedIn);
        sessionData.put("user", user);

        try (Writer writer = new FileWriter(SESSION_FILE)) {
            gson.toJson(sessionData, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isLoggedIn() {
        try (Reader reader = new FileReader(SESSION_FILE)) {
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            return json.get("isLoggedIn").getAsBoolean();
        } catch (Exception e) {
            return false;
        }
    }

    public static User getLoggedInUser() {
        try (Reader reader = new FileReader(SESSION_FILE)) {
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            if (json.get("isLoggedIn").getAsBoolean()) {
                return gson.fromJson(json.get("user"), User.class);
            }
        } catch (Exception e) {
            // فایل خراب یا موجود نیست
        }
        return null;
    }

    public static void logout() {
        try (Writer writer = new FileWriter(SESSION_FILE)) {
            writer.write("{}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}