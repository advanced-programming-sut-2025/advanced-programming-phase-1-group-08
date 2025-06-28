package com.Graphic.model.Enum.ItemType;

public enum BarsAndOreType {

    CopperBar("Copper Bar" , null , 750),

    IronBar("Iron Bar" , null , 1500),

    GoldBar("Gold Bar" , null , 400),

    IridiumBar("Iridium Bar" , null , 0),

    CopperOre("Copper Ore" , MarketType.Blacksmith , 75),

    IronOre("Iron Ore" , MarketType.Blacksmith , 150),

    GoldOre("Gold Ore" , MarketType.Blacksmith , 400),

    IridiumOre("Iridium Ore" , null , 0);

    private final String name;
    private final MarketType marketType;
    private final int Price;

    BarsAndOreType(String name , MarketType marketType , int Price) {
        this.name = name;
        this.marketType = marketType;
        this.Price = Price;
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

}
