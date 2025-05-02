package model.Places;

import model.GameObject;
import model.User;
import model.WaterTank;

import static model.Color_Eraser.*;

public class GreenHouse extends GameObject {

    public static final int requiredWood = 500;
    public static final int requiredCoins = 1000;

    private final int length = 6; // بدون دیوار
    private final int width = 5; // بدوندیوار
    private int coordinateX; // برا اساس مپ عدد بدیم
    private int coordinateY; // برا اساس مپ عدد بدیم

    private User Owner;
    private WaterTank waterTank; // مقدار اب مخزن
    private boolean isCreated=false;

    public GreenHouse(int coordinateX, int coordinateY) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }
    // اب پاش فراموش نشه


    public void setWaterTank(WaterTank waterTank) {
        this.waterTank = waterTank;
    }

    public void setCreated(boolean created) {
        isCreated = created;
    }

    public WaterTank getWaterTank() {
        return waterTank;
    }

    public boolean isCreated() {
        return isCreated;
    }

    public String getIcon () {
        if (isCreated)
            return GREEN+"G"+ RESET;
        else
            return GRAY+"G"+ RESET;
    }


}
