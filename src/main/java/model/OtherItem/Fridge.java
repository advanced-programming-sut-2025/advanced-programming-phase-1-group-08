package model.OtherItem;

import model.Items;
import model.MapThings.GameObject;

import java.util.HashMap;

import static model.Color_Eraser.YELLOW;

public class Fridge extends GameObject {
    //ArrayList<GameObject>
    private int x;
    private int y;
    public HashMap<Items, Integer> items = new HashMap<>();

    public Fridge(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String getIcon() {
        return YELLOW +"F ";
    }
}
