package model.Plants;

import model.DateHour;
import model.Enum.AllPlants.TreeType;
import model.MapThings.GameObject;
import model.MapThings.Tile;
import model.MapThings.Walkable;

import static model.App.bigMap;
import static model.App.currentDate;
import static model.DateHour.getDayDifferent;

public class Tree extends GameObject {

    private final DateHour birthDay;
    private final TreeType type;
    private boolean isProtected;
    private DateHour lastWater;
    private DateHour lastFruit;
    private boolean haveFruit;
    private boolean fertilize;
    private int stage;


    public Tree(TreeType type, DateHour currentDate) {
        stage = 1;
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

        this.lastWater = lastWater;
    }
    public void setLastFruit(DateHour lastFruit) {

        this.lastFruit = lastFruit;
    }
    public void setFertilize() {

        this.fertilize = true;

        if (stage == 4) // TODO  بر اساس نوع کود
            lastFruit.decreaseDay(1);
        else
            birthDay.decreaseDay(1);

    }
    private void setStage () {

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

        this.haveFruit = this.type.getSourceType().getSeason().contains(currentDate.getSeason()) &&
                getDayDifferent(lastFruit, currentDate) > 6;
    }
    private void delete () {

        for (Tile tile : bigMap)
            if (tile.getGameObject().equals(this))
                tile.setGameObject(new Walkable());
    }


    @Override
    public void startDayAutomaticTask () {

        setStage();
        setHaveFruit();
        this.fertilize = false;
        if (checkForDeath())
            delete();

    }
}