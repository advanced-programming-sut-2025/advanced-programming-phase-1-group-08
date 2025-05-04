package model.Enum.ItemType;

public enum MarketType {
    Blacksmith("Blacksmith" , 9 , 16),
    MarnieRanch("Marnie's Ranch" , 9 , 16),
    StardropSaloon("The Stardrop Saloon" , 12 , 24),
    CarpenterShop("Carpenter's shop" , 9 , 20),
    JojaMart("JojaMart" , 9 , 23),
    PierreGeneralStore("Pierr's General Store" , 9 , 17),
    FishShop("Fish shop" , 9 , 17);

    private final String name;
    private final int startHour;
    private final int endHour;
    MarketType(String name , int startHour , int endHour) {
        this.name = name;
        this.startHour = startHour;
        this.endHour = endHour;
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
}
