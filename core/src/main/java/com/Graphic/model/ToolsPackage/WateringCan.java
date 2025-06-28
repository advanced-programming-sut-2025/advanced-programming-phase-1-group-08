package com.Graphic.model.ToolsPackage;

import model.Enum.ToolsType.WateringCanType;

import static model.App.*;

public class WateringCan extends Tools {

    private WateringCanType type;
    private int reminderCapacity;

    public WateringCan(WateringCanType type) {

        super("WateringCan");
        this.type = type;
        reminderCapacity = this.type.getCapacity();
    }



    public int healthCost() {

        double x = currentGame.currentWeather.getEnergyCostCoefficient();

        if (currentGame.currentPlayer.getLevelFarming() == 4)
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
    public int getSellPrice() {
        return type.getPrice();
    }
}
