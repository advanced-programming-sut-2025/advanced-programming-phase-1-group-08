package com.Graphic.model.Plants;

import com.Graphic.model.*;
import com.Graphic.model.Enum.AllPlants.ForagingSeedsType;
import com.Graphic.model.Enum.ItemType.MarketItemType;
import com.Graphic.model.Enum.ItemType.MarketType;
import com.Graphic.model.MapThings.Tile;
import com.Graphic.model.MapThings.Walkable;
import com.Graphic.model.Weather.DateHour;

import static com.Graphic.model.App.currentGame;
import static com.Graphic.model.Weather.DateHour.getDayDifferent;

public class ForagingSeeds extends Items {

    private final ForagingSeedsType type;
    private DateHour birthDay;
    private boolean todayFertilize;
    private DateHour lastProduct;
    private boolean haveProduct;
    private boolean isProtected;
    private DateHour lastWater;
    private int numFertilize;
    private int stage;


    public ForagingSeeds(ForagingSeedsType type) {
        stage = 1;
        numFertilize = 0;
        this.type = type;
        isProtected = false;
        haveProduct = false;
        todayFertilize = false;
    }
    public ForagingSeeds(ForagingSeedsType type, DateHour Date) { // TODO  باید بالایی کال بشه قبل کاشتن

        stage = 1;
        numFertilize = 0;
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
    public String getIcon () {

        return type.getTexturePath(stage);
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
        DateHour dateHour = currentGame.currentDate.clone();
        dateHour.increaseDay(numFertilize);
        int defDays = getDayDifferent(this.birthDay, dateHour);

        for (int i = 0; i < this.type.getGrowthStages(); i++) {
            if (defDays > days && (days+this.type.getStageDate(i)) >= defDays) {
                stage = i + 1;
                return;
            } else
                days += this.type.getStageDate(i);
        }
        if (defDays > 8 && stage == 1)
            stage = type.getGrowthStages();
    }
    public void setFertilize (MarketItemType item) {

        this.todayFertilize = true;

        if (item.equals(MarketItemType.QuantityRetainingSoil))
            numFertilize++;
        if (item.equals(MarketItemType.BasicRetainingSoil))
            lastWater = currentGame.currentDate.clone();

    }
    public void setLastWater (DateHour lastWater) {

        this.lastWater = lastWater.clone();
    }
    public void setProtected (boolean aProtected) {

        isProtected = aProtected;
    }
    public void harvest () {

        if (type.isOneTimeUse())
            delete();
        else
            lastProduct = currentGame.currentDate.clone();
    }
    public void setLastProduct(DateHour lastProduct) {

        this.lastProduct = lastProduct.clone();
    }



    public boolean checkForDeath () {

        return getDayDifferent( lastWater, currentGame.currentDate) > 2;
    }
    public void checkHaveProduct () {

        DateHour dateHour = currentGame.currentDate.clone();
        dateHour.increaseDay(numFertilize);

        if (type.isOneTimeUse())
            this.haveProduct = type.getSeason().contains(currentGame.currentDate.getSeason()) &&
                    stage == type.getGrowthStages();
        else {
            this.haveProduct = type.getSeason().contains(currentGame.currentDate.getSeason()) &&
                    getDayDifferent(lastProduct, dateHour) > type.getRegrowthTime() &&
                    this.stage == this.type.getGrowthStages();
        }
    }
    public void delete () {

        for (Tile tile : currentGame.bigMap)
            if (tile.getGameObject() == (this))
                tile.setGameObject(new Walkable());
    }

    @Override
    public void startDayAutomaticTask() {

        setStage();
        todayFertilize = false;
        isProtected = false;
        checkHaveProduct();
        if (checkForDeath())
            delete();
    }

    @Override
    public String getName() {
        return type.getDisplayName();
    }

    @Override
    public String getInventoryIconPath() {
        return type.getInventoryIconPath();
    }

    @Override
    public int getSellPrice() {
        return 1;
    }

    @Override
    public int getRemindInShop(MarketType marketType) {
        if (marketType.equals(MarketType.JojaMart)) {
            return type.getJojaMartLimit();
        }
        if (marketType.equals(MarketType.PierreGeneralStore)){
            return type.getPierrGeneralLimit();
        }
        return -1;
    }

    @Override
    public void setRemindInShop(int amount, MarketType marketType) {
        if (marketType.equals(MarketType.JojaMart)) {
            type.increaseJojaMartLimit(amount - type.getJojaMartLimit());
        }
        if (marketType.equals(MarketType.PierreGeneralStore)){
            type.increasePierrGeneralLimit(amount - type.getPierrGeneralLimit());
        }
    }

    @Override
    public int getMarketPrice(MarketType marketType) {
        return type.getPrice(marketType);
    }
}
