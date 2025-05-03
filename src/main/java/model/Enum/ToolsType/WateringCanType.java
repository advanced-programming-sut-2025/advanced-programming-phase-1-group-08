package model.Enum.ToolsType;

public enum WateringCanType {

    PrimaryWateringCan  ("Primary Watering Can",-5, 40),

    CopperyWateringCan  ("Coppery Watering Can",-4, 55),

    IronWateringCan     ("Iron Watering Can",   -3, 70),

    GoldenWateringCan   ("Golden Watering Can", -2, 85),

    IridiumWateringCan  ("Iridium Watering Can",-1, 100);

    private final String displayName;
    private final int energyCost;
    private final int capacity;

    WateringCanType(String displayName, int energyCost, int capacity) {
        this.displayName = displayName;
        this.energyCost = energyCost;
        this.capacity = capacity;
    }

    public int getEnergyCost() {

        return energyCost;
    }
    public int getCapacity  () {

        return capacity;
    }
    public String getDisplayName () {

        return this.displayName;
    }
}
