package model.Enum.ItemType;

public enum Quantity {

    Normal("regular",1),
    Silver("silver",1.25),
    Golden("gold",1.5),
    Iridium("Iridium",2);

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
