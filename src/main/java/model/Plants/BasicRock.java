package model.Plants;

import model.Items;

import static model.Color_Eraser.*;

public class BasicRock extends Items {
    //این کلاس برای سنگ عادی اول بازی زده شده.
    public static final int price=20;

    @Override
    public String getIcon() {
        return GRAY+"s";
    }

    @Override
    public String getName() {
        return "Stone";
    }

    @Override
    public int getSellPrice() {
        return 20;
    }
}
