package model.Animall;

import model.Enum.ItemType.AnimalProductType;
import model.Enum.ItemType.AnimalType;
import model.MapThings.GameObject;

public class Animal extends GameObject {

    private AnimalType type;
    private AnimalProductType productType;
    private int FriendShip;
    private final String name;
    private int positionX;
    private int positionY;
    private int lastProduceDay;
    private double randomProduction;
    private double randomQuantity;
    private double randomChance;
    private boolean isFeedToday;
    private boolean isFeedPreviousDay;
    private boolean isPetToday;
    private boolean spentNightOutside;
    private boolean productCollected;

    public Animal(AnimalType type,int friendShip, String name, boolean isFeed, boolean isFeedPreviousDay, boolean isPetToday, boolean spentNightOutside, int day ) {
        this.type = type;
        this.FriendShip = friendShip;
        this.name = name;
        this.isFeedToday = isFeed;
        this.isPetToday = isPetToday;
        this.spentNightOutside = spentNightOutside;
        this.lastProduceDay=day;
        this.isFeedPreviousDay = isFeedPreviousDay;
        productCollected=false;
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
    public boolean isFeedToday() {
        return isFeedToday;
    }

    public void setFeedToday(boolean feedToday) {
        isFeedToday = feedToday;
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

    public boolean isFeedPreviousDay() {
        return isFeedPreviousDay;
    }

    public void setFeedPreviousDay(boolean feedPreviousDay) {
        isFeedPreviousDay = feedPreviousDay;
    }

    public void setProductCollected(boolean productCollected) {
        this.productCollected = productCollected;
    }
    public boolean isProductCollected() {
        return productCollected;
    }

    public double getRandomProduction() {
        return randomProduction;
    }
    public void setRandomProduction(double randomProduction) {
        this.randomProduction = randomProduction;
    }
    public double getRandomQuantity() {
        return randomQuantity;
    }
    public void setRandomQuantity(double randomQuantity) {
        this.randomQuantity = randomQuantity;
    }

    public double getRandomChance() {
        return randomChance;
    }
    public void setRandomChance(double randomChance) {
        this.randomChance = randomChance;
    }

    public AnimalProductType getProductType() {
        return productType;
    }

    public void setProductType(AnimalProductType productType) {
        this.productType = productType;
    }

    @Override
    public String getIcon() {
        return type.getIcon();
    }
}
