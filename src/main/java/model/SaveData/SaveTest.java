package model.SaveData;//package model.SaveData;
//import com.google.gson.Gson;
//import model.App;
//
//import java.io.FileWriter;
//import java.io.IOException;
//
//import static model.App.currentMenu;
//import static model.App.users;
//
//public class SaveTest {
//    public static void test() {
//        AppTest app = new AppTest(App.currentUser, users, currentMenu);
//
//        Gson gson = new Gson();
//
//        try (FileWriter writer = new FileWriter("person.json")) {
//            gson.toJson(app, writer);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//
//}

import com.google.gson.Gson;
import model.App;
import model.Enum.Menu;
import model.SaveData.AppTest;

import java.io.*;

public class SaveTest {
    private static final String FILE_PATH = "appstate.json";
    private static final Gson gson = new Gson();

    public static void saveAppState() {
        AppTest state = new AppTest(App.currentUser, App.users, App.currentMenu);

        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(state, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadAppState() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            AppTest state = gson.fromJson(reader, AppTest.class);
            App.currentUser = state.getCurrentUser();
            App.users = state.getUsers();
            App.currentMenu = state.getCurrentMenu();
        } catch (IOException e) {
            // فایل وجود نداره، مشکلی نیست
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}