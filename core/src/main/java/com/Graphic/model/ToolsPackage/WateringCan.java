package com.Graphic.model.ToolsPackage;

import com.Graphic.Main;
import com.Graphic.model.Enum.ItemType.MarketType;
import com.Graphic.model.Enum.ToolsType.WateringCanType;

import static com.Graphic.model.App.*;

public class WateringCan extends Tools {

    private WateringCanType type;
    private int reminderCapacity;

    public WateringCan(WateringCanType type) {

        super("WateringCan");
        this.type = type;
        reminderCapacity = this.type.getCapacity();
    }



    public int healthCost() {

        double x = Main.getClient(null).getLocalGameState().currentWeather.getEnergyCostCoefficient();

        if (Main.getClient(null).getPlayer().getLevelFarming() == 4)
            return (int) (this.type.getEnergyCost()*x)+1;
        return (int) (this.type.getEnergyCost()*x);
    }

    public WateringCanType getType() {

        return type;
    }
    public void setType(WateringCanType type) {

        this.type = type;
    }

    public int getReminderCapacity() {

        return reminderCapacity;
    }
    public void decreaseWater(int amount) {
        this.reminderCapacity -= amount;
        checkForWater();
    }
    public void makeFullWater () {

        this.reminderCapacity = this.type.getCapacity();
    }
    public void checkForWater () {

        if (this.reminderCapacity < 0)
            this.reminderCapacity = 0;
        if (this.reminderCapacity > this.type.getCapacity())
            this.reminderCapacity = this.type.getCapacity();
    }
    public void increaseReminderCapacity (int amount) {

        this.reminderCapacity += amount;
    }

    @Override
    public String getName() {
        return type.getDisplayName();
    }

    @Override
    public String getInventoryIconPath() {
        return type.getIconPath();
    }

    @Override
    public int getSellPrice() {
        return type.getPrice();
    }

    @Override
    public String getIcon() {
        return type.getIconPath();
    }

    @Override
    public int getRemindInShop(MarketType marketType) {
        return Integer.MAX_VALUE;
    }

    @Override
    public void setRemindInShop(int amount, MarketType marketType) {

    }

    @Override
    public int getMarketPrice(MarketType marketType) {
        return type.getPrice();
    }
}
