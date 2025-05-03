package model.Plants;

import model.DateHour;
import model.Enum.AllPlants.TreeType;
import model.Enum.AllPlants.TreesProductType;
import model.GameObject;
import model.Tile;
import model.Walkable;

import java.util.Date;

import static model.App.bigMap;
import static model.App.currentDate;
import static model.DateHour.getDayDifferent;

public class Tree extends GameObject {

    private final DateHour birthDay;
    private final TreeType type;
    private boolean isProtected;
    private DateHour lastWater;
    private DateHour lastFruit;
    private int stage;


    public Tree(TreeType type, DateHour currentDate) {
        this.type = type;
        birthDay = currentDate.clone();
        stage = 1;
        this.isProtected = false;
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
    public void setStage () {

        int defDays = getDayDifferent(currentDate, this.birthDay);

        if (defDays > 0 && defDays < 7)
            stage = 1;
        else if (defDays > 7 && defDays < 14)
            stage = 2;
        else if (defDays > 14 && defDays < 21)
            stage = 3;
        else if (defDays > 21 && defDays < 28)
            stage = 4;

    }

    public String   getIcon () {

        return this.type.getIcon(stage);
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

    public boolean checkForDeath () {

        return getDayDifferent(currentDate, lastWater) > 3;
    }
    public void delete () {

        for (Tile tile : bigMap)
            if (tile.getGameObject().equals(this))
                tile.setGameObject(new Walkable());
    }


    @Override
    public void turnByTurnAutomaticTask() {
        setStage();
        if (checkForDeath())
            delete();
    }
}