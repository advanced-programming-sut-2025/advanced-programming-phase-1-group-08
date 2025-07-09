package com.Graphic.model.OtherItem;

import com.Graphic.model.Items;
import com.Graphic.model.MapThings.GameObject;

import java.util.HashMap;

import static com.Graphic.model.HelpersClass.Color_Eraser.YELLOW;

public class Fridge extends GameObject {
    //ArrayList<GameObject>
    private int x;
    private int y;
    public HashMap<Items, Integer> items = new HashMap<>();

    public Fridge(int x, int y) {
        this.x = x;
        this.y = y;
        super.setHeight(1.5f);
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
