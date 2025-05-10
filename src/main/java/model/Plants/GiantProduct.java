package model.Plants;

import model.DateHour;
import model.Enum.AllPlants.ForagingSeedsType;
import model.Enum.ItemType.MarketItemType;
import model.Items;
import model.MapThings.Tile;
import model.MapThings.Walkable;

import java.util.ArrayList;

import static model.App.bigMap;
import static model.App.currentDate;
import static model.Color_Eraser.*;
import static model.DateHour.decreaseDay;
import static model.DateHour.getDayDifferent;

public class GiantProduct extends Items {

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

        setStage();
        numFertilize = 0;
        this.type = type;
        isProtected = false;
        this.neighbors = neighbors;
        birthDay = currentDate.clone();
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
            lastWater = currentDate.clone();

    }
    public void setStage  () {

        int days = 0;
        DateHour dateHour = currentDate.clone();
        dateHour.increaseDay(numFertilize);
        int defDays = getDayDifferent(this.birthDay, dateHour);

        for (int i = 0; i < this.type.getGrowthStages(); i++) {
            if (defDays > days && (days+this.type.getStageDate(i)) > defDays)
                stage = i+1;
            else
                days += this.type.getStageDate(i);
        }
    }


    public void checkHaveProduct () {



        DateHour dateHour = currentDate.clone();
        dateHour.increaseDay(numFertilize);
        this.haveProduct = type.getSeason().contains(currentDate.getSeason()) &&
                getDayDifferent(birthDay, dateHour) > type.getRegrowthTime() &&
                this.stage == this.type.getGrowthStages();
    }
    public boolean checkForDeath () {

        return getDayDifferent(lastWater, currentDate) > 1;
    }
    public void harvest () {

        for (Tile tile : bigMap)
            if (neighbors.contains(tile))
                tile.setGameObject(new Walkable());
    }
    public void delete () {

        for (Tile tile : bigMap)
            if (tile.getGameObject().equals(this))
                tile.setGameObject(new Walkable());
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

        return BG_BRIGHT_PURPLE+type.getSymbolByLevel(stage)+RESET;
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
    public DateHour getLastWater () {

        return this.lastWater;
    }
    public boolean isTodayFertilize() {

        return todayFertilize;
    }
    public ForagingSeedsType getType() {

        return type;
    }
    public ArrayList<Tile>   getNeighbors() {

        return neighbors;
    }

}
