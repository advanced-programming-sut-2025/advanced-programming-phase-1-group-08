package model.Enum.ItemType;

public enum MarketType {
    Blacksmith("Blacksmith" , 9 , 16 , 42 , 32 , 6 , 5),
    MarnieRanch("Marnie's Ranch" , 9 , 16 , 51 , 32 , 6 , 5),
    StardropSaloon("The Stardrop Saloon" , 12 , 24 , 32 , 45 , 6 , 5),
    CarpenterShop("Carpenter's shop" , 9 , 20 , 51, 38 , 6 , 5),
    JojaMart("JojaMart" , 9 , 23 , 32 , 38 , 6 , 5),
    PierreGeneralStore("Pierr's General Store" , 9 , 17 , 32 , 32 , 6 , 5),
    FishShop("Fish shop" , 9 , 17 , 42 , 38 , 6 , 5);

    private final String name;
    private final int startHour;
    private final int endHour;
    private final int topleftx;
    private final int toplefty;
    private final int width;
    private final int height;
    MarketType(String name , int startHour , int endHour , int topleftx , int toplefty , int width , int height) {
        this.name = name;
        this.startHour = startHour;
        this.endHour = endHour;
        this.topleftx = topleftx;
        this.toplefty = toplefty;
        this.width = width;
        this.height = height;
    }

    public String getName() {
        return name;
    }
    public int getStartHour() {
        return startHour;
    }
    public int getEndHour() {
        return endHour;
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public int getTopleftx() {
        return topleftx;
    }
    public int getToplefty() {
        return toplefty;
    }

    public static MarketType isInMarket(int x , int y) {
        for (MarketType marketType : MarketType.values()) {
            boolean b = y < marketType.getToplefty() + marketType.getHeight();
            if (x == marketType.topleftx && y >= marketType.getToplefty() && b) {
                return marketType;
            }
            if (x== marketType.topleftx + marketType.getWidth() && y >= marketType.getToplefty() && b) {
                return marketType;
            }
        }
        return null;
    }
}
