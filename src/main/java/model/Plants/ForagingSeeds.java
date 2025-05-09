package model.Plants;

import model.*;
import model.Enum.AllPlants.ForagingSeedsType;
import model.MapThings.Tile;
import model.MapThings.Walkable;


import static model.App.bigMap;
import static model.App.currentDate;
import static model.DateHour.getDayDifferent;

public class ForagingSeeds extends Items {

    private final ForagingSeedsType type;
    private DateHour birthDay;
    private boolean todayFertilize;
    private DateHour lastProduct;
    private boolean haveProduct;
    private boolean isProtected;
    private DateHour lastWater;
    private int stage;


    public ForagingSeeds(ForagingSeedsType type, DateHour Date) {
        stage = 1;
        this.type = type;
        birthDay = Date.clone();
        lastProduct = Date.clone();
        lastWater = Date.clone();
        isProtected = false;
        haveProduct = false;
        todayFertilize = false;
    }

    public ForagingSeedsType getType() {

        return type;
    }
    public int getStage() {

        return stage;
    }
    public String   getIcon () {

        return type.getSymbolByLevel(stage);
    }
    public boolean  isProtected() {

        return isProtected;
    }
    public boolean isHaveProduct() {

        return haveProduct;
    }
    public DateHour getBirthDay () {

        return this.birthDay;
    }
    public DateHour getLastWater() {

        return lastWater;
    }
    public DateHour getLastProduct() {

        return lastProduct;
    }
    public boolean isTodayFertilize() {

        return todayFertilize;
    }


    public void setStage  () {

        int days = 0;
        int defDays = getDayDifferent( this.birthDay, currentDate);

        for (int i = 0; i < this.type.getGrowthStages(); i++) {
            if (defDays > days && (days+this.type.getStageDate(i)) > defDays)
                stage = i+1;
            else
                days += this.type.getStageDate(i);
        }
    }
    public void setFertilize() {

        this.todayFertilize = true;

        if (stage == 4) // TODO  بر اساس نوع کود
            lastProduct.decreaseDay(1);
        else
            birthDay.decreaseDay(1);

    }
    public void setBirthDay(DateHour birthDay) {

        this.birthDay = birthDay;
    }
    public void setLastWater(DateHour lastWater) {

        this.lastWater = lastWater;
    }
    public void setProtected(boolean aProtected) {

        isProtected = aProtected;
    }
    public void setLastProduct(DateHour lastProduct) {

        this.lastProduct = lastProduct;
    }
    public void setTodayFertilize(boolean todayFertilize) {

        this.todayFertilize = todayFertilize;
    }


    public boolean checkForDeath () {

        return getDayDifferent( lastWater, currentDate) > 1;
    }
    public void checkHaveProduct () {
        // یمتونی تعداد کود هایی که داده شده رو سیو کنی و یه کپی از کارنت دیت بگیری و روزاشو کم کنی
        // یا حتی میشه اون تایع کم شدن روز رو استاتیک بزاری و حروجی یه تاریخ جدید بده و ازون استفاده کنی
        this.haveProduct = type.getSeason().contains(currentDate.getSeason()) &&
                getDayDifferent(lastProduct, currentDate) > type.getRegrowthTime() &&
                this.stage == this.type.getGrowthStages();
    }
    public void delete () {

        for (Tile tile : bigMap)
            if (tile.getGameObject().equals(this))
                tile.setGameObject(new Walkable());
    }

    @Override
    public void startDayAutomaticTask() {

        setStage();
        todayFertilize = false;
        if (checkForDeath())
            delete();
    }
}
