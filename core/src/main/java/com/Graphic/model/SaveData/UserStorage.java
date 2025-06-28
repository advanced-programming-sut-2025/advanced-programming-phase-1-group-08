package com.Graphic.model.SaveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.DateHour;
import model.Enum.NPC;
import model.Items;
import model.User;

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
        if (!Files.exists(FILE_PATH)) return new ArrayList<>();
        String json = Files.readString(FILE_PATH);
        Type listType = new TypeToken<List<User>>() {}.getType();
        return gson.fromJson(json, listType);
    }
}
