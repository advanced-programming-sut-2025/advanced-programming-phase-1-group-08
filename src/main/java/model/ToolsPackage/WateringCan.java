package model.ToolsPackage;

import model.Enum.ToolsType.WateringCanType;
import model.Enum.WeatherTime.Weather;

import static model.App.currentPlayer;
import static model.App.currentWeather;

public class WateringCan extends Tools {

    public WateringCanType wateringCanType;
    private int reminderCapacity;

    public WateringCan(WateringCanType type) {

        super("WateringCan");
        this.wateringCanType = type;
        reminderCapacity = this.wateringCanType.getCapacity();
    }



    public void use () {}
    public int healthCost() {

        double x = currentWeather.getEnergyCostCoefficient();

        if (currentPlayer.getLevelFarming() == 4)
            return (int) (this.wateringCanType.getEnergyCost()*x)+1;
        return (int) (this.wateringCanType.getEnergyCost()*x);
    }

    public WateringCanType getWateringCanType() {

        return wateringCanType;
    }
    public void setWateringCanType(WateringCanType wateringCanType) {

        this.wateringCanType = wateringCanType;
    }

    public int getReminderCapacity() {

        return reminderCapacity;
    }
    public void decreaseWater(int amount) {
        this.reminderCapacity -= amount;
        checkForWater();
    }
    public void makeFullWater () {

        this.reminderCapacity = this.wateringCanType.getCapacity();
    }
    public void checkForWater () {

        if (this.reminderCapacity < 0)
            this.reminderCapacity = 0;
        if (this.reminderCapacity > this.wateringCanType.getCapacity())
            this.reminderCapacity = this.wateringCanType.getCapacity();
    }
}
