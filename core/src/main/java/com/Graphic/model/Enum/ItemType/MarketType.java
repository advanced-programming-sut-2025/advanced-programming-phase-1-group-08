package com.Graphic.model.Enum.ItemType;

import com.Graphic.model.collisionRect;

import java.util.ArrayList;
import java.util.List;

public enum MarketType {
    Blacksmith("BlackSmith" , 9 , 16 , 42 , 32 , 6 , 5 ,
                    List.of(new collisionRect(131,56,32,35) , new collisionRect(24,51,75,44) ,
                            new collisionRect(198,25,39,45) , new collisionRect(186,12,27,30) ,
                            new collisionRect(20,200,135,52) , new collisionRect(122,66,123,12),
                            new collisionRect(179,37,94,23) ,new collisionRect(25,25,124,12) ,
                            new collisionRect(2,22,5,189) , new collisionRect(211 , 22 ,7,71) ,
                            new collisionRect(210,22,84,31) , new collisionRect(91 ,119,7,15) ,
                            new collisionRect(23,47,7,17) ) , new collisionRect(0,0,0,0) , new collisionRect(0,0,0,0) ),

    MarnieRanch("Marnie's Ranch" , 9 , 16 , 51 , 32 , 6 , 5,List.of(new collisionRect(0,0,0,0)),
        new collisionRect(0,0,0,0) , new collisionRect(0,0,0,0)),


    StardropSaloon("The Stardrop Saloon" , 12 , 24 , 32 , 45 , 6 , 5,
                    List.of(new collisionRect(145,45,34,20)   , new collisionRect(34,40,58,30),
                            new collisionRect(5,40,16,29)     , new collisionRect(113,204,70,128) ,
                            new collisionRect(256,45,12,33)   , new collisionRect(423,41,20,164) ,
                            new collisionRect(10,35,104,71)   , new collisionRect(71,43,122,62) ,
                            new collisionRect(45,27,127,35)   , new collisionRect(317,108,120,65) ,
                            new collisionRect(341,41,107,13)  , new collisionRect(389,35,107,13) ,
                            new collisionRect(229,218,-11,32) , new collisionRect(1,217,4,20) ,
                            new collisionRect(10,2,45,60)) , new collisionRect(0,0,0,0) , new collisionRect(0,0,0,0)),

    CarpenterShop("Carpenter's shop" , 9 , 20 , 51, 38 , 6 , 5 ,
        List.of(new collisionRect(0,0,0,0)) , new collisionRect(0,0,0,0) , new collisionRect(0,0,0,0)),

    JojaMart("JojaMart" , 9 , 23 , 32 , 38 , 6 , 5 ,
                    List.of(new collisionRect(0 ,45,40,82)    , new collisionRect(70,38,40,72),
                            new collisionRect(54,16,56,56)    , new collisionRect(134,42,40,72),
                            new collisionRect(118,16,56,56)   , new collisionRect(36,44,155,180),
                            new collisionRect(100,44,123,212) , new collisionRect(164,44,155,180),
                            new collisionRect(228,44,123,212) , new collisionRect(292,44,155,180),
                            new collisionRect(356,44,155,180) , new collisionRect(400,16,219,24),
                            new collisionRect(292,104,61,56)  , new collisionRect(436,64,133,367),
                            new collisionRect(420,80,90,43)   , new collisionRect(420,80,0,70),
                            new collisionRect(0,500,390,110)  , new collisionRect(84,91,348,40) ,
                            new collisionRect(20,16,171,24)   ) , new collisionRect(0,0,0,0) , new collisionRect(0,0,0,0)),

    PierreGeneralStore("Pierr's General Store" , 9 , 17 , 32 , 32 , 6 , 5 ,
                    List.of(new collisionRect(16,27,48,45)    , new collisionRect(40,40,48,42),
                            new collisionRect(7,22,92,200)    , new collisionRect(36,40,138,40),
                            new collisionRect(36,100,199,55)  , new collisionRect(147,40,25,103),
                            new collisionRect(148,104,135,45) , new collisionRect(148,104,183,46),
                            new collisionRect(212,42,23,107)  , new collisionRect(260,139,106,203),
                            new collisionRect(136,134,237,56) , new collisionRect(260,81,56,55) ,
                            new collisionRect(276,43,47,10)   , new collisionRect(337,41,7,98),
                            new collisionRect(0,134,20,55)    , new collisionRect(139,197,16,3)) ,
                            new collisionRect(0,0,0,0) , new collisionRect(0,0,0,0)),

    FishShop("Fish shop" , 9 , 17 , 42 , 38 , 6 , 5 ,
                    List.of(new collisionRect(36,25,28,61)    , new collisionRect(61,48,62,27),
                            new collisionRect(100,27,26,78)   , new collisionRect(2,172,93,61),
                            new collisionRect(127,100,78,100) , new collisionRect(85,95,-7,29),
                            new collisionRect(0,75,-1,22)     , new collisionRect(-2,25,20,71),
                            new collisionRect(161,24,22,55))  , new collisionRect(0,0,0,0) , new collisionRect(0,0,0,0)),;

    private final String name;
    private final int startHour;
    private final int endHour;
    private final int topleftx;
    private final int toplefty;
    private final int width;
    private final int height;
    private final List<collisionRect> rects = new ArrayList<collisionRect>();
    private final collisionRect insideDoor;
    private final collisionRect outsideDoor;

    MarketType(String name , int startHour , int endHour , int topleftx ,
               int toplefty , int width , int height , List<collisionRect> rects , collisionRect insideDoor , collisionRect outsideDoor ) {
        this.name = name;
        this.startHour = startHour;
        this.endHour = endHour;
        this.topleftx = topleftx;
        this.toplefty = toplefty;
        this.width = width;
        this.height = height;
        this.rects.addAll(rects);
        this.insideDoor = insideDoor;
        this.outsideDoor = outsideDoor;
    }

    public String getName() {
        return name;
    }
    public int getStartHour() {
        return startHour;
    }
    public int getEndHour() {
        return endHour;
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public int getTopleftx() {
        return topleftx;
    }
    public int getToplefty() {
        return toplefty;
    }
    public List<collisionRect> getRects() {
        return rects;
    }
    public collisionRect getInsideDoor() {
        return insideDoor;
    }
    public collisionRect getOutsideDoor() {
        return outsideDoor;
    }



    public static MarketType wallOrDoor(int x , int y) {
        for (MarketType marketType : MarketType.values()) {
            boolean b = y < marketType.getToplefty() + marketType.getHeight();
            boolean a= x < marketType.getTopleftx() + marketType.getWidth();
            if (x == marketType.topleftx && y >= marketType.getToplefty() && b) {
                return marketType;
            }
            if (x== marketType.topleftx + marketType.getWidth() - 1 && y >= marketType.getToplefty() && b) {
                return marketType;
            }
            if (y == marketType.toplefty && x >= marketType.getTopleftx() && a) {
                return marketType;
            }
            if (y== marketType.toplefty + marketType.getHeight() - 1 && x >= marketType.getTopleftx() && a) {
                return marketType;
            }
        }
        return null;
    }

    public static MarketType isInMarket(int x , int y) {
        for (MarketType marketType : MarketType.values()) {
            boolean b = y < marketType.getToplefty() + marketType.getHeight();
            boolean a= x < marketType.getTopleftx() + marketType.getWidth();

            if (x > marketType.getTopleftx() && y > marketType.getToplefty() && a && b) {
                return marketType;
            }
        }
        return null;
    }

    public static String endLimit(String id) {
        if (id.equals("1")) {
            return "The purchase limit for this product is reached";
        }
        if (id.equals("2")) {
            return "Your Money is not enough to purchase this product";
        }
        if (id.equals("3")) {
            return "your BackPack does not have enough capacity for this product";
        }
        if (id.equals("4")) {
            return "Your BackPack does not changeable";
        }
        if (id.equals("5")) {
            return "Your ability is not enough for purchase this Fishing Pole";
        }
        if (id.equals("6")) {
            return "You don't have enough Wood to make this Building";
        }
        if (id.equals("7")) {
            return "You don't have enough Stone to make this Building";
        }
        if (id.equals("8")) {
            return "you can't create Barn Or Cage on this coordinate";
        }
        if (id.equals("9")) {
            return "You have an animal with this name";
        }
        if (id.equals("10")) {
            return "you don't have enough capacity or suitable place for this animal";
        }
        return null;
    }

}
