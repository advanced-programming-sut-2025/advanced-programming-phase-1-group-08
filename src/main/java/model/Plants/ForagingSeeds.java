package model.Plants;

import model.*;
import model.Enum.AllPlants.ForagingSeedsType;


import static model.App.bigMap;
import static model.App.currentDate;
import static model.DateHour.getDayDifferent;

public class ForagingSeeds extends Items {

    private final ForagingSeedsType type;
    private final DateHour birthDay;
    private DateHour lastWater;
    private int stage;


    public ForagingSeeds(ForagingSeedsType type, DateHour Date) {
        this.type = type;
        birthDay = Date.clone();
        stage = 1;
    }

    public ForagingSeedsType getType() {

        return type;
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
    public String getIcon () {

        return type.getSymbolByLevel(stage);
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
    public DateHour getBirthDay () {

        return this.birthDay;
    }
    public void setLastWater(DateHour lastWater) {

        this.lastWater = lastWater;
    }
}
