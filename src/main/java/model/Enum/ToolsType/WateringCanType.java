package model.Enum.ToolsType;

public enum WateringCanType {

    PrimaryWateringCan  (5, 40),

    CopperyWateringCan  (4, 55),

    IronWateringCan     (3, 70),

    GoldenWateringCan   (2, 85),

    IridiumWateringCan  (1, 100);


    private final int energyCost;
    private final int capacity;

    WateringCanType(int energyCost, int capacity) {
        this.energyCost = energyCost;
        this.capacity = capacity;
    }

    public int getEnergyCost() {

        return energyCost;
    }
    public int getCapacity  () {

        return capacity;
    }
}
