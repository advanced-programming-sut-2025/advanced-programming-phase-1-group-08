package com.Graphic.model.Places;

import com.Graphic.Controller.MainGame.GameControllerLogic;
import com.Graphic.model.Enum.ItemType.MarketType;
import com.Graphic.model.OtherItem.Fridge;
import com.Graphic.model.MapThings.GameObject;
import com.Graphic.model.MapThings.door;

import java.util.ArrayList;

import static com.Graphic.model.HelpersClass.Color_Eraser.*;

public class Home extends GameObject {
    private final int width;
    private final int length;
    private int topLeftX;
    private int topLeftY;
    private Fridge fridge;
    public door houseDoor;
    private String Path;

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

    public Fridge getFridge() {
        return fridge;
    }

    @Override
    public String getIcon() {
        if (Path != null) {
            return Path;
        }

        for (int i = topLeftX; i < topLeftX + width; i++) {
            for (int j = topLeftY; j < topLeftY + length; j++) {
                if (GameControllerLogic.getTileByCoordinates(i , j).getGameObject().equals(this)) {
                    int l = 7 * (j - topLeftY) + i - topLeftX + 1;
                    Path = "Places/home" + l + ".png";
                    return Path;
                }
            }
        }
        return Path;
    }

    @Override
    public int getRemindInShop(MarketType marketType) {
        return 0;
    }

    @Override
    public void setRemindInShop(int amount, MarketType marketType) {

    }
}
