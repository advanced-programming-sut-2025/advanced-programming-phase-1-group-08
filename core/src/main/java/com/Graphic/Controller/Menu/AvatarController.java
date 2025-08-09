package com.Graphic.Controller.Menu;

import com.Graphic.Main;
import com.Graphic.model.App;
import com.Graphic.model.SaveData.UserStorage;
import com.Graphic.model.User;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.io.IOException;
import java.util.List;

public class AvatarController {

    private static final String PREFS_NAME = "AvatarSettings";
    private static final String AVATAR_INDEX_KEY = "avatarIndex_";
    private static final String COLOR_R_KEY = "colorR_";
    private static final String COLOR_G_KEY = "colorG_";
    private static final String COLOR_B_KEY = "colorB_";

    private Preferences prefs;

    public AvatarController() {
        prefs = Gdx.app.getPreferences(PREFS_NAME);
    }


    public int getCurrentAvatarIndex() {
        if (Main.getClient().getPlayer() == null) {
            return 0;
        }

        String key = AVATAR_INDEX_KEY + Main.getClient().getPlayer().getUsername();
        return prefs.getInteger(key, 0);
    }


    public float[] getCurrentColor() {
        if (Main.getClient().getPlayer() == null) {
            return new float[]{1, 1, 1};
        }

        String username = Main.getClient().getPlayer().getUsername();
        float r = prefs.getFloat(COLOR_R_KEY + username, 1.0f);
        float g = prefs.getFloat(COLOR_G_KEY + username, 1.0f);
        float b = prefs.getFloat(COLOR_B_KEY + username, 1.0f);

        return new float[]{r, g, b};
    }


    public boolean saveAvatarSettings(int avatarIndex, float r, float g, float b) {
        if (Main.getClient().getPlayer() == null) {
            return false;
        }

        try {
            String username = Main.getClient().getPlayer().getUsername();

            prefs.putInteger(AVATAR_INDEX_KEY + username, avatarIndex);
            prefs.putFloat(COLOR_R_KEY + username, r);
            prefs.putFloat(COLOR_G_KEY + username, g);
            prefs.putFloat(COLOR_B_KEY + username, b);
            prefs.flush();

            updateUserAvatarInfo(avatarIndex, r, g, b);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public void resetToDefaults() {
        if (Main.getClient().getPlayer() == null) {
            return;
        }

        String username = Main.getClient().getPlayer().getUsername();
        prefs.putInteger(AVATAR_INDEX_KEY + username, 0);
        prefs.putFloat(COLOR_R_KEY + username, 1.0f);
        prefs.putFloat(COLOR_G_KEY + username, 1.0f);
        prefs.putFloat(COLOR_B_KEY + username, 1.0f);
        prefs.flush();
    }


    public String getAvatarPath(int avatarIndex) {
        return String.format("avatars/avatar_%d.png", avatarIndex + 1);
    }


    public boolean isAvatarUnlocked(int avatarIndex) {
        return true;
    }


    public int getAvailableAvatarsCount() {
        return 6;
    }


    private void updateUserAvatarInfo(int avatarIndex, float r, float g, float b) throws IOException {
        List<User> users = UserStorage.loadUsers();

        for (User user : users) {
            if (user.getUsername().equals(Main.getClient().getPlayer().getUsername())) {
                // ذخیره اطلاعات آواتار در یوزر
                // اگر فیلدهای مربوط به آواتار در کلاس User ندارید، می‌تونید اضافه کنید
                // user.setAvatarIndex(avatarIndex);
                // user.setAvatarColorR(r);
                // user.setAvatarColorG(g);
                // user.setAvatarColorB(b);

                UserStorage.saveUsers(users);
                break;
            }
        }
    }


    public String rgbToHex(float r, float g, float b) {
        int red = (int)(r * 255);
        int green = (int)(g * 255);
        int blue = (int)(b * 255);
        return String.format("#%02x%02x%02x", red, green, blue);
    }


    public String getAvatarName(int index) {
        String[] names = {
            "Classic Farmer",
            "Adventure Seeker",
            "Nature Lover",
            "Tech Savvy",
            "Artist Soul",
            "Mystery Person"
        };

        if (index >= 0 && index < names.length) {
            return names[index];
        }
        return "Avatar " + (index + 1);
    }
}
