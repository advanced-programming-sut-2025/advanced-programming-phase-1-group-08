package model.Enum.ItemType;

public enum BarnORCageType {

    Barn("Barn",4 , 7 , 4),
    BigBarn("Big Barn", 8 , 7 , 4),
    DeluxeBarn("Deluxe Barn" , 12 , 7 , 4),

    Coop("Coop" , 4 , 6 , 3 ),
    BigCoop("Big Coop" , 8 , 6 , 3),
    DeluxeCoop("Deluxe Coop" , 12 , 6 , 3);

    private final String name;
    private int initialCapacity;
    private final int width;
    private final int height;

    BarnORCageType(String name, int initialCapacity, int width, int height) {
        this.name = name;
        this.initialCapacity = initialCapacity;
        this.width = width;
        this.height = height;
    }

    public String getName() {
        return name;
    }
    public int getInitialCapacity() {
        return initialCapacity;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
}
