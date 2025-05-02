package model.Plants;

import model.DateHour;
import model.Enum.AllPlants.TreeType;
import model.Enum.AllPlants.TreesProductType;
import model.GameObject;

import java.util.Date;

import static model.App.currentDate;
import static model.DateHour.getDayDifferent;

public class Tree extends GameObject {

    private final TreeType type;
    private int stage;
    private final DateHour birthDay;


    public Tree(TreeType type, DateHour currentDate) {
        this.type = type;
        birthDay = currentDate.clone();
        stage = 0;
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
    public String getIcon () {

        return this.type.getIcon(stage);
    }

    @Override
    public void turnByTurnAutomaticTask() {
        setStage();
    }
}