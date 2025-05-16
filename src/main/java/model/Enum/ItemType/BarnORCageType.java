package model.Enum.ItemType;

import java.util.HashMap;
import java.util.Map;

public enum BarnORCageType {

    Barn("Barn",4 , 6 , 3, 6000 , 350 , 150 , 1),
    BigBarn("Big Barn", 8 , 7 , 4 , 12000 , 450 , 200 , 1),
    DeluxeBarn("Deluxe Barn" , 12 , 8 , 5 , 25000 , 550 , 300 , 1),

    Coop("Coop" , 4 , 6 , 3 , 4000 , 300 , 100 , 1),
    BigCoop("Big Coop" , 8 , 7 , 4 , 10000 , 400 , 150 , 1),
    DeluxeCoop("Deluxe Coop" , 12 , 8 , 5 , 20000 , 500 , 200 , 1);



    private final String name;
    private int initialCapacity;
    private final int width;
    private final int height;
    private final int price;
    private final int woodNeeded;
    private final int stoneNeeded;
    private int shopLimit;

    BarnORCageType(String name, int initialCapacity, int width, int height ,int price,int woodNeeded,int stoneNeeded , int shopLimit) {
        this.name = name;
        this.initialCapacity = initialCapacity;
        this.width = width;
        this.height = height;
        this.price = price;
        this.woodNeeded = woodNeeded;
        this.stoneNeeded = stoneNeeded;
        this.shopLimit = shopLimit;
    }

    public String getName() {
        return name;
    }
    public int getInitialCapacity() {
        return initialCapacity;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public int getPrice() {
        return price;
    }
    public int getWoodNeeded() {
        return woodNeeded;
    }
    public int getStoneNeeded() {
        return stoneNeeded;
    }
    public int getShopLimit() {
        return shopLimit;
    }
    public void setShopLimit(int ShopLimit) {
        this.shopLimit = ShopLimit;
    }
}
