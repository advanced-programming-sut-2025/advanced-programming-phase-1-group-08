package model.Places;

import model.GameObject;
import model.WaterTank;

public class GreenHouse extends GameObject {

    private final int requiredWood = 500;
    private final int requiredCoins = 1000;
    private final int length = 6; // بدون دیوار
    private final int width = 5; // بدوندیوار
    private int coordinateX; // برا اساس مپ عدد بدیم
    private int coordinateY; // برا اساس مپ عدد بدیم
    public WaterTank waterTank; // مقدار اب مخزن
    public boolean isCreated=false;

    public GreenHouse(int coordinateX, int coordinateY) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }
    // اب پاش فراموش نشه


}
