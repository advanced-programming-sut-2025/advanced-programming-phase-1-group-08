package model.Enum.ItemType;

public enum Quantity {

    Silver("silver",1.25),
    Golden("gold",1.5),
    Iridium("Iridium",2),
    Normal("regular",1);

    private final String name;
    private final double value;
    Quantity(final String name, final double value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }
    public double getValue() {
        return value;
    }
}
