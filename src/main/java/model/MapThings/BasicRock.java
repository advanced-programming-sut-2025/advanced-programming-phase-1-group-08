package model.MapThings;

import model.Items;

import static model.Color_Eraser.*;

public class BasicRock extends Items {
    //این کلاس برای سنگ عادی اول بازی زده شده.
    public static final int price=20;

    @Override
    public String getIcon() {
        return GRAY+"s";
    }
}
