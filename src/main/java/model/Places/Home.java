package model.Places;

import model.Fridge;
import model.MapThings.GameObject;
import model.MapThings.door;

import java.util.ArrayList;

public class Home extends GameObject {
    private final int width=5;
    private final int length=5;
    private int topLeftX;
    private int topLeftY;
    private Fridge fridge;
    public door houseDoor;

    public Home(int topLeftX, int topLeftY, Fridge fridge) {
        this.topLeftX = topLeftX;
        this.topLeftY = topLeftY;
        this.fridge = fridge;
    }
    ArrayList<String> machines = new ArrayList<>(); // دستگاه هایی که کرفت کردیم و میخواهیم با آنها فراوری یا کارهای دیگر بکنیم
}
