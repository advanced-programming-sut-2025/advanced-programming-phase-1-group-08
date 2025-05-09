package model.Enum.ItemType;

public enum BarsAndOreType {

    CopperBar("Copper Bar"),

    IronBar("Iron Bar"),

    GoldBar("Gold Bar"),

    IridiumBar("Iridium Bar"),

    CopperOre("Copper Ore"),

    IronOre("Iron Ore"),

    GoldOre("Gold Ore"),

    IridiumOre("Iridium Ore");

    private final String name;

    BarsAndOreType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
