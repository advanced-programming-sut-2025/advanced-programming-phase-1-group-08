package model.Enum.ItemType;

public enum BarsType {

    CopperBar("Copper Bar"),

    IronBar("Iron Bar"),

    GoldBar("Gold Bar"),

    IridiumBar("Iridium Bar");

    private final String name;

    BarsType(String name) {
        this.name = name;
    }
}
