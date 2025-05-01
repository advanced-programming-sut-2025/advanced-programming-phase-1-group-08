package model.Enum.ItemType;

public enum Quantity {

    Normal("Normal",1),
    Silver("Silver",1.25),
    Golden("Golden",1.5),
    Iridium("Iridium",2);

    private final String name;
    private final double value;
    Quantity(final String name, final double value) {
        this.name = name;
        this.value = value;
    }
}
