package model.Plants;

import model.DateHour;
import model.Enum.AllPlants.ForagingSeedsType;
import model.Items;
import model.Tile;
import model.Walkable;

import java.util.ArrayList;

import static model.App.bigMap;
import static model.App.currentDate;
import static model.Color_Eraser.*;
import static model.DateHour.getDayDifferent;

public class GiantProduct extends Items {

    private ArrayList<Tile> neighbors = new ArrayList<>();
    private final ForagingSeedsType type;
    private final DateHour birthDay;
    private boolean isProtected;
    private DateHour lastWater;
    private int stage;

    public GiantProduct (ForagingSeedsType type, DateHour currentDate, ArrayList<Tile> neighbors) {
        this.type = type;
        this.birthDay = currentDate.clone();
        isProtected = false;
        setStage();
        this.neighbors = neighbors;
    }

    public void setLastWater (DateHour dateHour) {

        this.lastWater = dateHour;
    }
    public void setStage  () {

        int days = 0;
        int defDays = getDayDifferent(currentDate, this.birthDay);

        for (int i = 0; i < this.type.getGrowthStages(); i++) {
            if (defDays > days && (days+this.type.getStageDate(i)) > defDays)
                stage = i+1;
            else
                days += this.type.getStageDate(i);
        }
    }
    public void setProtected(boolean aProtected) {

        isProtected = aProtected;
    }

    public boolean checkForDeath () {

        return getDayDifferent(currentDate, lastWater) > 1;
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



    public String   getIcon () {

        return BG_BRIGHT_PURPLE+type.getSymbolByLevel(stage)+RESET;
    }
    public boolean  isProtected() {

        return isProtected;
    }
    public DateHour getBirthDay () {

        return this.birthDay;
    }
    public DateHour getLastWater () {

        return this.lastWater;
    }
    public ForagingSeedsType getType() {

        return type;
    }
    public ArrayList<Tile>   getNeighbors() {

        return neighbors;
    }

}
