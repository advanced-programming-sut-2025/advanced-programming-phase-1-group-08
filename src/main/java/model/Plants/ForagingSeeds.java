package model.Plants;

import model.DateHour;
import model.Enum.AllPlants.ForagingSeedsType;
import model.GameObject;
import model.Items;


import static model.App.currentDate;
import static model.DateHour.getDayDifferent;

public class ForagingSeeds extends Items {

    private final ForagingSeedsType type;
    private final DateHour birthDay;
    private int stage;


    public ForagingSeeds(ForagingSeedsType type, DateHour currentDate) {
        this.type = type;
        birthDay = currentDate.clone();
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

    @Override
    public void turnByTurnAutomaticTask() {
        setStage();
    }

}
