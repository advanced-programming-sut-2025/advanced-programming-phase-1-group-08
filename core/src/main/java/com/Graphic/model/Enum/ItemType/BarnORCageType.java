package com.Graphic.model.Enum.ItemType;

import com.Graphic.model.Animall.BarnOrCage;
import com.Graphic.model.Enum.Direction;
import com.Graphic.model.User;
import com.Graphic.model.collisionRect;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.Graphic.View.GameMenus.GameMenu.camera;
import static com.Graphic.View.GameMenus.GameMenu.gameMenu;

public enum BarnORCageType {

    Barn("Barn",4 , 6 , 3, 6000 , 350 , 150 ,
        1 , "Mohamadreza/Barn.png" , false , "Mohamadreza/Maps/Barn.tmx" ,
        2 ,2,34,6 ,
        List.of(new Point(118,105) , new Point(208,105),new Point(114,67),new Point(210,67)),
        new Circle(176,7,5)),

    BigBarn("Big Barn", 8 , 7 , 4 , 12000 , 450 , 200 ,
        1 , "Mohamadreza/Big Barn.png" , false , "Mohamadreza/Maps/Barn2.tmx",
        2,3,34,6 ,
        List.of(new Point(104,115),new Point(138,116),new Point(175,116),new Point(229,116),
                new Point(229,68) , new Point(188,71),new Point(147,71),new Point(106,27)) ,
        new Circle(177,11,5)),

    DeluxeBarn("Deluxe Barn" , 12 , 8 , 5 , 25000 , 550 , 300 ,
        1 , "Mohamadreza/Deluxe Barn.png" , false , "Mohamadreza/Maps/Barn3.tmx",
        2,4 , 34 , 6 ,
        List.of(new Point(64,119),new Point(103,121),new Point(146,120),new Point(182,120),
                new Point(111,80),new Point(157,80),new Point(189,80),new Point(232,80),
                new Point(232,45),new Point(183,45),new Point(142,45),new Point(97,20)) ,
        new Circle(174,6,5)),

    Coop("Coop" , 4 , 6 , 3 , 4000 , 300 , 100 ,
        1 , "Mohamadreza/Coop.png" , false , "Mohamadreza/Maps/Coop.tmx" ,
        2,2 , 34 , 6 ,
        List.of(new Point(18,62),new Point(51,62),new Point(86,62),new Point(124,62)) ,
        new Circle(32,4,5)),

    BigCoop("Big Coop" , 8 , 7 , 4 , 10000 , 400 , 150 ,
        1 , "Mohamadreza/Big Coop.png" , false , "Mohamadreza/Maps/Coop2.tmx",
        2,3 , 34 , 6 ,
        List.of(new Point(16,80),new Point(48,80),new Point(31,54),new Point(61,54),
                new Point(91,54),new Point(130,54),new Point(66,30),new Point(104,32)) ,
        new Circle(33,4,5)),

    DeluxeCoop("Deluxe Coop" , 12 , 8 , 5 , 20000 , 500 , 200 ,
        1 , "Mohamadreza/Deluxe Coop.png" , false , "Mohamadreza/Maps/Coop3.tmx",
        2,4 , 34 , 6 ,
        List.of(new Point(16,80),new Point(55,83),new Point(17,57),new Point(47,57),
                new Point(78,57),new Point(111,57),new Point(30,30),new Point(54,30) ,
                new Point(83,30),new Point(117,30),new Point(148,30),new Point(154,66)) ,
        new Circle(31,6,5));



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
    private final String entryIconPath;
    private final int doorX;
    private final int doorY;
    private final int initX;
    private final int initY;
    private final List<Point> points = new ArrayList<>();
    private final Circle exit;

    BarnORCageType(String name, int initialCapacity, int width, int height ,int price,int woodNeeded,int stoneNeeded ,
                   int shopLimit , String Path , boolean Waiting , String entryIconPath , int doorX , int doorY , int initX , int initY,
                    List<Point> points , Circle exit ) {
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
        this.entryIconPath = entryIconPath;
        this.doorX = doorX;
        this.doorY = doorY;
        this.initX = initX;
        this.initY = initY;
        this.points.addAll(points);
        this.exit = exit;
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

    public String getEntryIconPath() {
        return entryIconPath;
    }

    public int getDoorX() {
        return doorX;
    }

    public int getDoorY() {
        return doorY;
    }

    public int getInitX() {
        return initX;
    }
    public int getInitY() {
        return initY;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void exitBarnOrCage(User user) {
        if (exit.contains(new Vector2(user.sprite.getX(), user.sprite.getY()))
                && user.getDirection().equals(Direction.Down)) {
            gameMenu.isInFarmExterior = true;
            camera.setToOrtho(false , Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
    }

}
