package com.Graphic.model.Enum.AllPlants;

import com.Graphic.model.Enum.WeatherTime.Season;

import java.util.ArrayList;

import static com.Graphic.model.Color_Eraser.*;

public enum CropsType {

    BlueJazz        ("Blue Jazz",   true,    50, 45, "JazzSeeds",       false),
    Carrot          ("Carrot",      true,    35, 75, "CarrotSeeds",     true),
    Cauliflower     ("Cauliflower", true,    175,75, "CauliflowerSeeds",true),
    CoffeeBean      ("Coffee Bean", false,   15,  0, "CoffeeBean",      false),
    Garlic          ("Garlic",      true,    60, 20, "GarlicSeeds",     true),
    GreenBean       ("Green Bean",   true,   40, 25, "BeanStarter",     true),
    Kale            ("Kale",        true,    110,50, "KaleSeeds",       true),
    Parsnip         ("Parsnip",     true,    35, 25, "ParsnipSeeds",    true),
    Potato          ("Potato",      true,    80, 25, "PotatoSeeds",     true),
    Rhubarb         ("Rhubarb",     false,   220, 0, "RhubarbSeeds",    false),
    Strawberry      ("Strawberry",  true,    120,50, "StrawberrySeeds", false),
    Tulip           ("Tulip",       true,    30, 45, "TulipBulb",       false),
    UnmilledRice    ("Unmilled Rice",true,   30,  3, "RiceShoot",       false),
    Blueberry       ("Blueberry",   true,    50, 25, "BlueberrySeeds",  false),
    Corn            ("Corn",        true,    50, 25, "CornSeeds",       true),
    Hops            ("Hops",        true,    25, 45, "HopsStarter",     true),
    HotPepper       ("Hot Pepper",  true,    40, 13, "PepperSeeds",     false),
    Melon           ("Melon",       true,    250,113,"MelonSeeds",      false),
    Poppy           ("Poppy",       true,    140,45, "PoppySeeds",      false),
    Radish          ("Radish",      true,    90, 45, "RadishSeeds",     true),
    RedCabbage      ("Red Cabbage", true,    260,75, "RedCabbageSeeds", true),
    Starfruit       ("Starfruit",   true,    750,125,"StarfruitSeeds",  false),
    SummerSpangle   ("Summer Spangle",true,  90, 45, "SpangleSeeds",    false),
    SummerSquash    ("Summer Squash",true,   45, 63, "SummerSquashSeeds",true),
    Sunflower       ("Sunflower",   true,    80, 45, "SunflowerSeeds",  false),
    Tomato          ("Tomato",      true,    60, 20, "TomatoSeeds",     true),
    Wheat           ("Wheat",       false,   25,  0, "WheatSeeds",      true),
    Amaranth        ("Amaranth",    true,    150,50, "AmaranthSeeds",   true),
    Artichoke       ("Artichoke",   true,    160,30, "ArtichokeSeeds",  true),
    Beet            ("Beet",        true,    100,30, "BeetSeeds",       true),
    BokChoy         ("Bok Choy",     true,    80, 25, "BokChoySeeds",   true),
    Broccoli        ("Broccoli",    true,    70, 63, "BroccoliSeeds",   true),
    Cranberries     ("Cranberries", true,    75, 38, "CranberrySeeds",  false),
    Eggplant        ("Eggplant",    true,    60, 20, "EggplantSeeds",   true),
    FairyRose       ("Fairy Rose",  true,    290,45, "FairySeeds",      false),
    Grape           ("Grape",       true,    80, 38, "GrapeStarter",    false),
    Pumpkin         ("Pumpkin",     false,   320, 0, "PumpkinSeeds",    true),
    Yam             ("Yam",         true,    160,45, "YamSeeds",        true),
    SweetGemBerry   ("Sweet Gem Berry",false,  3000,0, "RareSeed",      false),
    Powdermelon     ("Powdermelon",true,     60, 63, "PowdermelonSeeds",false),
    AncientFruit    ("Ancient Fruit",false,   550, 0, "AncientSeeds",   false);

    private final String displayName;
    private final String sourceName;
    private final boolean isEdible;
    private final int price;
    private final int energy;
    private final boolean isVegetable;

    CropsType (String displayName, boolean isEdible, int price, int energy, String sourceName , boolean isVegetable) {
        this.displayName = displayName;
        this.isEdible = isEdible;
        this.price = price;
        this.sourceName = sourceName;
        this.energy = energy;
        this.isVegetable = isVegetable;
    }

    public String getDisplayName () {
        return this.displayName;
    }
    public int getEnergy () {
        return this.energy;
    }
    public boolean isEdible() {
        return isEdible;
    }
    public int getPrice() {
        return price;
    }

    public static String getInformation (CropsType type) {

        StringBuilder builder = new StringBuilder();

        ForagingSeedsType seedsType = ForagingSeedsType.valueOf(type.sourceName);

        builder.append(BLUE+"Name: "+RESET).append(type.displayName)
                .append(BLUE+"\nSource: "+RESET).append(seedsType.getDisplayName())
                .append(BLUE+"\nStages: "+RESET);
        for (int i = 0; i < seedsType.getGrowthStages(); i++)
            builder.append(seedsType.getStageDate(i)).append("-");

        builder.deleteCharAt(builder.length() - 1);

        int total = 0;
        for (Integer integer : seedsType.getStageDays())
            total += integer;


        builder.append(BLUE+"\nTotal Harvest Time: "+RESET).append(total)
                .append(BLUE+"\nOne Time: "+RESET).append(seedsType.isOneTimeUse())
                .append(BLUE+"\nRegrowth Time: "+RESET);

        if (!seedsType.isOneTimeUse())
            builder.append(seedsType.getRegrowthTime());

        builder.append(BLUE+"\nBase Sell Price: "+RESET).append(type.getPrice())
                .append(BLUE+"\nIs Edible: "+RESET).append(type.isEdible)
                .append(BLUE+"\nBase Energy: "+RESET).append(type.energy)
                .append(BLUE+"\nSeason: "+RESET);

        ArrayList<Season> seasons = seedsType.getSeason();
        for (Season season : seasons) builder.append(season.getDisplayName()).append(" ");

        builder.append(BLUE+"\nCan Become Giant: "+RESET).append(seedsType.canGrowGiant());
        return builder.toString();
    }

    public static CropsType fromDisplayName(String displayName) {
        for (CropsType type : CropsType.values())
            if (type.getDisplayName().equalsIgnoreCase(displayName))
                return type;
        throw new IllegalArgumentException(RED+"wrong name!"+RESET);
    }

    public boolean isVegetable () {
        return isVegetable;
    }
}
