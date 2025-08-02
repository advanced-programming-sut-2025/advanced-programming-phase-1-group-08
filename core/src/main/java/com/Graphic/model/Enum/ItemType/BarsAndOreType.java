package com.Graphic.model.Enum.ItemType;

public enum BarsAndOreType {

    CopperBar("Copper Bar" , null , 750, "all image/Resource/Copper_Bar.png"),

    IronBar("Iron Bar" , null , 1500, "all image/Resource/Iron_Bar.png"),

    GoldBar("Gold Bar" , null , 400, "all image/Resource/Gold_Bar.png"),

    IridiumBar("Iridium Bar" , null , 0, "all image/Crafting/Iridium_Bar.png"),

    CopperOre("Copper Ore" , MarketType.Blacksmith , 75, "all image/Crafting/Copper_Ore.png"),

    IronOre("Iron Ore" , MarketType.Blacksmith , 150, "all image/Crafting/Iron_Ore.png"),

    GoldOre("Gold Ore" , MarketType.Blacksmith , 400, "all image/Crafting/Gold_Ore.png"),

    IridiumOre("Iridium Ore" , null , 0, "all image/Crafting/Iridium_Ore.png"),;

    private final String name;
    private final MarketType marketType;
    private final int Price;
    private final String address;

    BarsAndOreType(String name , MarketType marketType , int Price, String address) {
        this.name = name;
        this.marketType = marketType;
        this.Price = Price;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public MarketType getMarketType() {
        return marketType;
    }
    public int getPrice() {
        return Price;
    }
    public String getAddress() {
        return address;
    }

}
