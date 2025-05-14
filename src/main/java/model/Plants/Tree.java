package model.Plants;

import model.DateHour;
import model.Enum.AllPlants.TreeType;
import model.Enum.ItemType.MarketItemType;
import model.Items;
import model.MapThings.Tile;
import model.MapThings.Walkable;

import static model.App.bigMap;
import static model.App.currentDate;
import static model.DateHour.decreaseDay;
import static model.DateHour.getDayDifferent;

public class Tree extends Items {

    private final DateHour birthDay;
    private final TreeType type;
    private boolean isProtected;
    private DateHour lastWater;
    private DateHour lastFruit;
    private boolean haveFruit;
    private boolean fertilize;
    private int numFertilize;
    private int stage;


    public Tree(TreeType type, DateHour currentDate) {

        stage = 1;
        numFertilize = 0;
        this.type = type;
        birthDay = currentDate.clone();
        lastWater = currentDate.clone();
        lastFruit = currentDate.clone();
        isProtected = false;
        haveFruit = false;
        fertilize = false;
    }

    public void setProtected(boolean aProtected) {

        isProtected = aProtected;
    }
    public void setLastWater(DateHour lastWater) {

        this.lastWater = lastWater.clone();
    }
    public void setLastFruit(DateHour lastFruit) {

        this.lastFruit = lastFruit.clone();
    }
    public void setFertilize (MarketItemType item) {

        this.fertilize = true;

        if (item.equals(MarketItemType.QuantityRetainingSoil))
            numFertilize++;
        if (item.equals(MarketItemType.BasicRetainingSoil))
            lastWater = currentDate.clone();

    }
    private void setStage () {

        DateHour dateHour = decreaseDay(numFertilize, currentDate);
        int defDays = getDayDifferent(this.birthDay, currentDate);

        if (defDays > 0 && defDays < 7)
            stage = 1;
        else if (defDays > 7 && defDays < 14)
            stage = 2;
        else if (defDays > 14 && defDays < 21)
            stage = 3;
        else if (defDays > 21 && defDays < 28)
            stage = 4;

    }


    public int getStage() {

        return stage;
    }
    public TreeType getType() {

        return type;
    }
    public String   getIcon () {

        if (stage >= 4 && haveFruit)
            return this.type.getIcon2();
        else
            return this.type.getIcon1();
    }
    public boolean  isFertilize() {

        return fertilize;
    }
    public boolean  isHaveFruit() {

        return haveFruit;
    }
    public boolean  isProtected() {

        return isProtected;
    }
    public DateHour getLastWater() {

        return lastWater;
    }
    public DateHour getLastFruit() {

        return lastFruit;
    }


    private boolean checkForDeath () {

        return getDayDifferent(lastWater, currentDate) > 3;
    }
    private void setHaveFruit () {

        DateHour dateHour = currentDate.clone();
        dateHour.increaseDay(numFertilize);

        this.haveFruit = this.type.getSourceType().getSeason().contains(currentDate.getSeason()) &&
                getDayDifferent(lastFruit, dateHour) > 6;
    }
    public void delete () {

        for (Tile tile : bigMap)
            if (tile.getGameObject().equals(this))
                tile.setGameObject(new Walkable());
    }


    @Override
    public void startDayAutomaticTask () {

        setStage();
        setHaveFruit();
        isProtected = false;
        this.fertilize = false;
        if (checkForDeath())
            delete();

    }

    @Override
    public String getName() {
        return type.getDisplayName();
    }
}