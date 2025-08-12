package com.Graphic.model.Plants;

import com.Graphic.Main;
import com.Graphic.model.ClientServer.ServerHandler;
import com.Graphic.model.Weather.DateHour;
import com.Graphic.model.Enum.AllPlants.TreeType;
import com.Graphic.model.Enum.ItemType.MarketItemType;
import com.Graphic.model.Enum.ItemType.MarketType;
import com.Graphic.model.MapThings.GameObject;
import com.Graphic.model.MapThings.Tile;
import com.Graphic.model.MapThings.Walkable;

import static com.Graphic.model.Weather.DateHour.getDayDifferent;

public class Tree extends GameObject {

    private DateHour birthDay;
    private TreeType type;
    private boolean isProtected;
    private DateHour lastWater;
    private DateHour lastFruit;
    private boolean haveFruit;
    private boolean fertilize;
    private int numFertilize;
    private int stage;

    public Tree() {}

    public Tree(TreeType type, DateHour currentDate) {

        stage = 1;
        numFertilize = 0;
        this.type = type;
        birthDay = currentDate.clone();
        lastWater = currentDate.clone();
        lastFruit = currentDate.clone();
        isProtected = false;
        haveFruit = false;
        fertilize = false;
        super.setTextureWidth(1);
        super.setTextureHeight(1);
    }

    public void setProtected(boolean aProtected) {

        isProtected = aProtected;
    }
    public void setLastWater(DateHour lastWater) {

        this.lastWater = lastWater.clone();
    }
    public void setLastFruit(DateHour lastFruit) {

        this.lastFruit = lastFruit.clone();
    }
    public void setFertilize (MarketItemType item) {

        this.fertilize = true;

        if (item.equals(MarketItemType.QuantityRetainingSoil))
            numFertilize++;
        if (item.equals(MarketItemType.BasicRetainingSoil))
            lastWater = Main.getClient().getLocalGameState().currentDate.clone();
    }
    private void setStage () {

        DateHour dateHour = Main.getClient().getLocalGameState().currentDate.clone();
        dateHour.increaseDay(numFertilize);

        int defDays = getDayDifferent(this.birthDay, dateHour);

        if (defDays > 0 && defDays < 7)
            stage = 1;
        else if (defDays > 7 && defDays < 14)
            stage = 2;
        else if (defDays > 14 && defDays < 21)
            stage = 3;
        else if (defDays > 21)
            stage = 4;
    }

    public int getStage() {

        return stage;
    }
    public TreeType getType() {

        return this.type;
    }
    public String getIcon () {

        return type.getPath(this.stage);
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
        return getDayDifferent(lastWater, ServerHandler.getInstance().getCurrentDate()) > 3;
    }
    private void setHaveFruit () {

        DateHour dateHour = ServerHandler.getInstance().getCurrentDate().clone();
        dateHour.increaseDay(numFertilize);

        this.haveFruit = this.type.getSeason().contains(ServerHandler.getInstance().getCurrentDate().getSeason()) &&
                getDayDifferent(lastFruit, dateHour) > 6;
    }
    public void delete () {

        for (Tile tile : ServerHandler.getInstance().game.getGameState().bigMap)
            if (tile.getGameObject() == this)
                tile.setGameObject(new Walkable());
    }


    @Override
    public void startDayAutomaticTask () {

        setStage();
        setHaveFruit();
        isProtected = false;
        this.fertilize = false;
        if (checkForDeath())
            delete();

    }

    @Override
    public int getRemindInShop(MarketType marketType) {
        return 0;
    }

    @Override
    public void setRemindInShop(int amount, MarketType marketType) {

    }

    public String getName() {
        return type.getDisplayName();
    }
}
