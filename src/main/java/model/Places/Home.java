package model.Places;

import model.OtherItem.Fridge;
import model.MapThings.GameObject;
import model.MapThings.door;

import java.util.ArrayList;

public class Home extends GameObject {
    private final int width;
    private final int length;
    private int topLeftX;
    private int topLeftY;
    private Fridge fridge;
    public door houseDoor;

    public Home(int topLeftX, int topLeftY, int width , int length, Fridge fridge) {
        this.topLeftX = topLeftX;
        this.topLeftY = topLeftY;
        this.width = width;
        this.length = length;
        this.fridge = fridge;
    }
    ArrayList<String> machines = new ArrayList<>(); // دستگاه هایی که کرفت کردیم و میخواهیم با آنها فراوری یا کارهای دیگر بکنیم

    public int getTopLeftY() {
        return topLeftY;
    }

    public int getLength() {
        return length;
    }

    public int getTopLeftX() {
        return topLeftX;
    }

    public int getWidth() {
        return width;
    }
}
