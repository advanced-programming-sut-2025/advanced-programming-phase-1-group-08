package com.Graphic.model.Plants;

import com.Graphic.Main;
import com.Graphic.model.Weather.DateHour;
import com.Graphic.model.Enum.AllPlants.ForagingSeedsType;
import com.Graphic.model.Enum.ItemType.MarketItemType;
import com.Graphic.model.Enum.ItemType.MarketType;
import com.Graphic.model.Items;
import com.Graphic.model.MapThings.Tile;
import com.Graphic.model.MapThings.Walkable;

import java.util.ArrayList;


import static com.Graphic.model.App.currentGame;
import static com.Graphic.model.Weather.DateHour.getDayDifferent;

public class GiantProduct extends Items { // TODO ابعاد

    private ArrayList<Tile> neighbors = new ArrayList<>();
    private final ForagingSeedsType type;
    private final DateHour birthDay;
    private boolean todayFertilize;
    private boolean isProtected;
    private DateHour lastWater;
    private boolean haveProduct;
    private int numFertilize;
    private int stage;

    public GiantProduct (ForagingSeedsType type, DateHour currentDate, ArrayList<Tile> neighbors) {

        numFertilize = 0;
        this.type = type;
        isProtected = false;
        this.neighbors = neighbors;
        birthDay = currentDate.clone();
        lastWater = currentDate.clone();
        todayFertilize = false;
        checkHaveProduct();
        setStage();
    }

    public void setProtected(boolean aProtected) {

        isProtected = aProtected;
    }
    public void setLastWater (DateHour dateHour) {

        this.lastWater = dateHour.clone();
    }
    public void setFertilize(MarketItemType item) {

        this.todayFertilize = true;

        if (item.equals(MarketItemType.QuantityRetainingSoil))
            numFertilize++;
        if (item.equals(MarketItemType.BasicRetainingSoil))
            lastWater = Main.getClient().getLocalGameState().currentDate.clone();

    }
    public void setStage  () {

        int days = 0;
        DateHour dateHour = Main.getClient().getLocalGameState().currentDate.clone();
        dateHour.increaseDay(numFertilize);
        int defDays = getDayDifferent(this.birthDay, dateHour);

        for (int i = 0; i < this.type.getGrowthStages(); i++) {
            if (defDays > days && (days+this.type.getStageDate(i)) > defDays) {
                stage = i + 1;
                return;
            } else
                days += this.type.getStageDate(i);
        }
        if (defDays > 8 && stage == 1)
            stage = type.getGrowthStages();
    }


    public void checkHaveProduct () {

        DateHour dateHour = Main.getClient().getLocalGameState().currentDate.clone();
        dateHour.increaseDay(numFertilize);
        this.haveProduct = type.getSeason().contains(Main.getClient().getLocalGameState().currentDate.getSeason()) &&
                getDayDifferent(birthDay, dateHour) > type.getRegrowthTime() &&
                this.stage == this.type.getGrowthStages();
    }
    public boolean checkForDeath () {

        return getDayDifferent(lastWater, Main.getClient().getLocalGameState().currentDate) > 2;
    }
    public void harvest () {

//        for (Tile tile : currentGame.bigMap)
//            if (neighbors.contains(tile))
//                tile.setGameObject(new Walkable());
    }
    public void delete () {

//        for (Tile tile : currentGame.bigMap)
//            if (tile.getGameObject().equals(this))
//                tile.setGameObject(new Walkable());
    }


    @Override
    public void startDayAutomaticTask() {

        setStage();
        this.todayFertilize = false;
        isProtected = false;
        checkHaveProduct();
        if (checkForDeath())
            delete();

    }


    public int getStage() {

        return stage;
    }
    public String   getIcon () {

        return type.getGiantTexturePath();
    }
    public boolean  isProtected() {

        return isProtected;
    }
    public boolean  isHaveProduct() {

        return haveProduct;
    }
    public DateHour getBirthDay () {

        return this.birthDay;
    }
    public DateHour getLastWater () {

        return this.lastWater;
    }
    public boolean  isTodayFertilize() {

        return todayFertilize;
    }
    public ForagingSeedsType getType() {

        return type;
    }
    public ArrayList<Tile>   getNeighbors() {

        return neighbors;
    }

    @Override
    public String getName() {
        return "dl";
    }

    @Override
    public String getInventoryIconPath() {
        return "";
    }

    @Override
    public int getSellPrice() {
        return 0;
    }

    @Override
    public int getRemindInShop(MarketType marketType) {
        return -1;
    }

    @Override
    public void setRemindInShop(int amount, MarketType marketType) {

    }

    @Override
    public int getMarketPrice(MarketType marketType) {
        return 0;
    }
}
