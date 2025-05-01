package model;

import model.Enum.ItemType.AnimalType;

public class Animal extends GameObject {

    private AnimalType type;
    private int FriendShip;
    private String name;
    boolean isFeed;
    boolean isPetToday;
    boolean spentNightOutside;

    public Animal(AnimalType type,int friendShip, String name, boolean isFeed, boolean isPetToday, boolean spentNightOutside ) {
        this.type = type;
        this.FriendShip = friendShip;
        this.name = name;
        this.isFeed = isFeed;
        this.isPetToday = isPetToday;
        this.spentNightOutside = spentNightOutside;
    }


}
