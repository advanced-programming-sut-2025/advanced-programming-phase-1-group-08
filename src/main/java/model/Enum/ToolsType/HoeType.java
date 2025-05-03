package model.Enum.ToolsType;

public enum HoeType {

    primaryHoe  ("Primary Hoe", -5),

    copperyHoe  ("Coppery Hoe", -4),

    ironHoe     ("Iron Hoe",    -3),

    goldenHoe   ("Golden Hoe",  -2),

    iridiumHoe  ("Iridium Hoe", -1);

    private final int energyCost;
    private final String displayName;

    HoeType(String displayName, int energyCost) {
        this.displayName = displayName;
        this.energyCost = energyCost;
    }

    public int getEnergyCost() {

        return energyCost;
    }
    public String getDisplayName() {

        return displayName;
    }
}
