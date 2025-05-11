package model.Enum.ToolsType;

public enum AxeType {

    primaryAxe  ("Primary Axe", -5),

    copperyAxe  ("Coppery Axe", -4),

    ironAxe     ("Iron Axe",    -3),

    goldenAxe   ("Golden Axe",  -2),

    iridiumAxe  ("Iridium Axe", -1);

    private final int energyCost;
    private final String displayName;

    AxeType (String displayName, int energyCost) {
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
