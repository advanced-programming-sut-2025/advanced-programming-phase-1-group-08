package com.Graphic.model.Animall;

import com.Graphic.model.Enum.Direction;
import com.Graphic.model.Enum.ItemType.AnimalProductType;
import com.Graphic.model.Enum.ItemType.AnimalType;
import com.Graphic.model.Enum.ItemType.MarketType;
import com.Graphic.model.HelpersClass.TextureManager;
import com.Graphic.model.MapThings.GameObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;

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
    private boolean isOut;
    private int index;
    private Animation<Texture> Up;
    private Animation<Texture> Left;
    private Animation<Texture> Right;
    private Animation<Texture> Down;
    private float timer = 0.0f;
    private Direction direction;
    private Sprite sprite;
    private boolean isMoving;

    public Animal(AnimalType type,int friendShip, String name, int day ) {
        this.type = type;
        this.FriendShip = friendShip;
        this.name = name;
        this.isFeedToday = false;
        this.isPetToday = false;
        this.spentNightOutside = false;
        this.lastProduceDay=day;
        this.isFeedPreviousDay = false;
        productCollected=false;
        isOut=false;
        isMoving = false;
        direction = Direction.Down;
        sprite = new Sprite(TextureManager.get("Mohamadreza/animal/"+type.getType().toLowerCase()+1+"_down.png"));
        this.Up = new Animation<>(0.1f ,
            new Texture("Mohamadreza/animal/"+type.getType().toLowerCase()+1+"_up.png"),
            new Texture("Mohamadreza/animal/"+type.getType().toLowerCase()+2+"_up.png") ,
            new Texture("Mohamadreza/animal/"+type.getType().toLowerCase()+3+"_up.png"),
            new Texture("Mohamadreza/animal/"+type.getType().toLowerCase()+4+"_up.png"));
        this.Left = new Animation<>(0.1f ,
            new Texture("Mohamadreza/animal/"+type.getType().toLowerCase()+1+"_left.png"),
            new Texture("Mohamadreza/animal/"+type.getType().toLowerCase()+2+"_left.png") ,
            new Texture("Mohamadreza/animal/"+type.getType().toLowerCase()+3+"_left.png"),
            new Texture("Mohamadreza/animal/"+type.getType().toLowerCase()+4+"_left.png"));
        this.Right = new Animation<>(0.1f ,
            new Texture("Mohamadreza/animal/"+type.getType().toLowerCase()+1+"_right.png"),
            new Texture("Mohamadreza/animal/"+type.getType().toLowerCase()+2+"_right.png") ,
            new Texture("Mohamadreza/animal/"+type.getType().toLowerCase()+3+"_right.png"),
            new Texture("Mohamadreza/animal/"+type.getType().toLowerCase()+4+"_right.png"));
        this.Down = new Animation<>(0.1f ,
            new Texture("Mohamadreza/animal/"+type.getType().toLowerCase()+1+"_down.png"),
            new Texture("Mohamadreza/animal/"+type.getType().toLowerCase()+2+"_down.png") ,
            new Texture("Mohamadreza/animal/"+type.getType().toLowerCase()+3+"_down.png"),
            new Texture("Mohamadreza/animal/"+type.getType().toLowerCase()+4+"_down.png"));
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

    @Override
    public int getRemindInShop(MarketType marketType) {
        return type.getRemindInShop();
    }

    @Override
    public void setRemindInShop(int amount, MarketType marketType) {
        type.increaseRemindInShop(amount - type.getRemindInShop());
    }
    public boolean isOut() {
        return isOut;
    }
    public void setOut(boolean out) {
        isOut = out;
    }

    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }

    public Animation<Texture> getUp() {
        return Up;
    }

    public Animation<Texture> getDown() {
        return Down;
    }
    public Animation<Texture> getLeft() {
        return Left;
    }
    public Animation<Texture> getRight() {
        return Right;
    }

    public float getTimer() {
        return timer;
    }

    public Direction getDirection() {
        return direction;
    }
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    public void setTimer(float timer) {
        this.timer = timer;
    }
    public Animation<Texture> getAnimation() {
        switch (direction) {
            case Up: return Up;
            case Down: return Down;
            case Left: return Left;
            case Right: return Right;
        }
        return null;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public boolean isMoving() {
        return isMoving;
    }
    public void setMoving(boolean moving) {
        isMoving = moving;
    }
}
