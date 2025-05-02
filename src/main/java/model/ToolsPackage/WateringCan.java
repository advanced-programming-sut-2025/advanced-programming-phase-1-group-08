package model.ToolsPackage;

import model.Enum.ToolsType.WateringCanType;

import static model.App.currentPlayer;

public class WateringCan extends Tools {

    public WateringCanType wateringCanType;
    private int reminderCapacity;

    public WateringCan(WateringCanType type) {

        super("WateringCan", 0);
        this.wateringCanType = type;
        reminderCapacity = this.wateringCanType.getCapacity();
    }


    public int getReminderCapacity() {
        return reminderCapacity;
    }
    public void setReminderCapacity(int reminderCapacity) {
        this.reminderCapacity = reminderCapacity;
    }
    public void increaseReminderCapacity(int amount) {
        this.reminderCapacity += amount;
    }



    public void use () {}

    @Override
    public int healthCost() {

        if (currentPlayer.getLevelFarming() == 4)
            return this.wateringCanType.getEnergyCost()-1;
        return this.wateringCanType.getEnergyCost();
    }
}
