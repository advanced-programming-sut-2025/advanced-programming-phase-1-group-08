package model;

import model.Enum.ItemType.CraftType;

import java.util.HashMap;

public class CraftingItem extends Items {
    private CraftType craftType;
    private int x;
    private int y;
    private HashMap<Items , DateHour> buffer=new HashMap<>();

    public CraftingItem(CraftType craftType) {
        this.craftType = craftType;
    }


    public CraftType getCraftType() {
        return craftType;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public HashMap<Items, DateHour> getBuffer() {
        return buffer;
    }
}
