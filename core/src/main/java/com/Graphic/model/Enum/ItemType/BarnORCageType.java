package com.Graphic.model.Enum.ItemType;

import java.util.HashMap;
import java.util.Map;

public enum BarnORCageType {

    Barn("Barn",4 , 6 , 3, 6000 , 350 , 150 ,
        1 , "Mohamadreza/Barn.png" , false),
    BigBarn("Big Barn", 8 , 7 , 4 , 12000 , 450 , 200 ,
        1 , "Mohamadreza/Big Barn.png" , false),
    DeluxeBarn("Deluxe Barn" , 12 , 8 , 5 , 25000 , 550 , 300 ,
        1 , "Mohamadreza/Deluxe Barn.png" , false),

    Coop("Coop" , 4 , 6 , 3 , 4000 , 300 , 100 ,
        1 , "Mohamadreza/Coop.png" , false),
    BigCoop("Big Coop" , 8 , 7 , 4 , 10000 , 400 , 150 ,
        1 , "Mohamadreza/Big Coop.png" , false),
    DeluxeCoop("Deluxe Coop" , 12 , 8 , 5 , 20000 , 500 , 200 ,
        1 , "Mohamadreza/Deluxe Coop.png" , false),;



    private final String name;
    private int initialCapacity;
    private final int width;
    private final int height;
    private final int price;
    private final int woodNeeded;
    private final int stoneNeeded;
    private int shopLimit;
    private final String Path;
    private boolean Waiting;

    BarnORCageType(String name, int initialCapacity, int width, int height ,int price,int woodNeeded,int stoneNeeded , int shopLimit , String Path , boolean Waiting) {
        this.name = name;
        this.initialCapacity = initialCapacity;
        this.width = width;
        this.height = height;
        this.price = price;
        this.woodNeeded = woodNeeded;
        this.stoneNeeded = stoneNeeded;
        this.shopLimit = shopLimit;
        this.Path = Path;
        this.Waiting = Waiting;
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
    public String getPath() {
        return Path;
    }

    public boolean isWaiting() {
        return Waiting;
    }
    public void setWaiting(boolean Waiting) {
        this.Waiting = Waiting;
    }
}
