package model.ToolsPackage;

import model.Enum.ToolsType.WateringCanType;

public class WateringCan extends Tools {

    public WateringCanType wateringCanType = model.Enum.ToolsType.WateringCanType.primary;

    public WateringCan() {
        super("WateringCan", 0);
    }

    private int reminderCapacity;

    public int getReminderCapacity() {
        return reminderCapacity;
    }
    public void setReminderCapacity(int reminderCapacity) {
        this.reminderCapacity = reminderCapacity;
    }
    public void increaseReminderCapacity(int amount) {
        this.reminderCapacity += amount;
    }

    public void use (){}
}
