package model;

import model.Enum.ItemType.AnimalType;

public class Animal extends GameObject {

    private AnimalType type;
    private int FriendShip;
    private final String name;
    private int positionX;
    private int positionY;
    private int lastProduceDay;
    boolean isFeed;
    boolean isPetToday;
    boolean spentNightOutside;

    public Animal(AnimalType type,int friendShip, String name, boolean isFeed, boolean isPetToday, boolean spentNightOutside, int day ) {
        this.type = type;
        this.FriendShip = friendShip;
        this.name = name;
        this.isFeed = isFeed;
        this.isPetToday = isPetToday;
        this.spentNightOutside = spentNightOutside;
        this.lastProduceDay=day;
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

    public void setFeed(boolean feed) {
        isFeed = feed;
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

    public int getPositionX() {
        return positionX;
    }
    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }
    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public int getLastProduceDay() {
        return lastProduceDay;
    }

    public void setLastProduceDay(int lastProduceDay) {
        this.lastProduceDay = lastProduceDay;
    }
}
