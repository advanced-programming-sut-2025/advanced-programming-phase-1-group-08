package com.Graphic.model.SaveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.Graphic.model.Weather.DateHour;
import com.Graphic.model.Enum.NPC.NPC;
import com.Graphic.model.Items;
import com.Graphic.model.User;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserStorage {
    private static final Gson gson = buildGson();
    private static final Path FILE_PATH = Path.of("users.json");


    private static Gson buildGson() {
        return new GsonBuilder()
                .registerTypeAdapterFactory(new ItemAdapterFactory())
                .registerTypeAdapter(
                        new TypeToken<Map<NPC, Integer>>() {}.getType(),
                        new GenericMapAdapter<>(new Gson(), NPC.class, new TypeToken<Integer>() {})
                )
                .registerTypeAdapter(
                        new TypeToken<Map<NPC, DateHour>>() {}.getType(),
                        new GenericMapAdapter<>(new Gson(), NPC.class, new TypeToken<DateHour>() {})
                )
                .registerTypeAdapter(
                        new TypeToken<Map<Items, DateHour>>() {}.getType(),
                        new GenericMapAdapter<>(new Gson(), Items.class, new TypeToken<DateHour>() {})
                )
                .registerTypeAdapter(
                        new TypeToken<Map<Items, Boolean>>() {}.getType(),
                        new GenericMapAdapter<>(new Gson(), Items.class, new TypeToken<Boolean>() {})
                )
                .registerTypeAdapter(
                        new TypeToken<Map<Items, Integer>>() {}.getType(),
                        new GenericMapAdapter<>(new Gson(), Items.class, new TypeToken<Integer>() {})
                )
                .setPrettyPrinting()
                .create();
    }

    public static void saveUsers(List<User> users) throws IOException {
        String json = gson.toJson(users);
        Files.writeString(FILE_PATH, json);
    }

    public static List<User> loadUsers() throws IOException {
        System.out.println("kir");
        if (!Files.exists(FILE_PATH)) return new ArrayList<>();

        try {
            String json = Files.readString(FILE_PATH);
            Type listType = new TypeToken<List<User>>() {}.getType();

            List<User> users = gson.fromJson(json, listType);
            System.out.println("ki2r");
            if (users == null) {
                System.err.println("JSON parsed to null — check JSON structure.");
                return new ArrayList<>();
            }
            System.out.println("kieer");
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                if (user == null) {
                    System.err.println("Null user object at index: " + i);
                } else if (user.getUsername() == null) {
                    System.err.println("Null 'username' at index: " + i);
                } else if (user.getPassword() == null) {
                    System.err.println("Null 'password' at index: " + i);
                }
                // ادامه بررسی سایر فیلدها...
                System.out.println("kirasdasd");
            }
            System.out.println("ki1111");
            return users;

        } catch (JsonSyntaxException | JsonIOException e) {
            System.err.println("Error parsing JSON: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }

    }
}
