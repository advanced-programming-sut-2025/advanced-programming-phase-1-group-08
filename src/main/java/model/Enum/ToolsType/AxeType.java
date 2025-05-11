package model.Enum.ToolsType;

public enum AxeType {

    primaryAxe  ("Primary Axe", -5 , 1),

    copperyAxe  ("Coppery Axe", -4 , 1),

    ironAxe     ("Iron Axe",    -3 , 1),

    goldenAxe   ("Golden Axe",  -2 , 1),

    iridiumAxe  ("Iridium Axe", -1 , 1);

    private final int energyCost;
    private final String displayName;
    private final int initialLimit;

    AxeType (String displayName, int energyCost , int initialLimit) {
        this.displayName = displayName;
        this.energyCost = energyCost;
        this.initialLimit = initialLimit;
    }

    public int getEnergyCost() {

        return energyCost;
    }
    public String getDisplayName() {
        return displayName;
    }
}
