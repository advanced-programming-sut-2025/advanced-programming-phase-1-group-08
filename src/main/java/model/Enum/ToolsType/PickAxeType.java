package model.Enum.ToolsType;

public enum PickAxeType {

    primaryPickAxe  ("Primary PickAxe", -5),

    copperyPickAxe  ("Coppery PickAxe", -4),

    ironPickAxe     ("Iron PickAxe",    -3),

    goldenPickAxe   ("Golden PickAxe",  -2),

    iridiumPickAxe  ("Iridium PickAxe", -1);

    private final int energyCost;
    private final String displayName;

    PickAxeType (String displayName, int energyCost) {
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
