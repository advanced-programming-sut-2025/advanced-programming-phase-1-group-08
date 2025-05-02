package model;

import model.Enum.ItemType.AnimalType;

public class Animal extends GameObject {

    private AnimalType type;
    private int FriendShip;
    private final String name;
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

    public String getName() {
        return name;
    }
    public AnimalType getType() {
        return type;
    }
    public int getFriendShip() {
        return FriendShip;
    }
    public void increaseFriendShip( int amount ) {
        FriendShip += amount;
    }
    public boolean isFeed() {
        return isFeed;
    }
    public boolean isPetToday() {
        return isPetToday;
    }

    public void setPetToday(boolean petToday) {
        isPetToday = petToday;
    }

    public boolean isSpentNightOutside() {
        return spentNightOutside;
    }
}
