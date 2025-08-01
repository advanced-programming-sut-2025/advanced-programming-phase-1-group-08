package com.Graphic.model.Enum.AllPlants;

import com.Graphic.model.Enum.WeatherTime.Season;

import java.util.ArrayList;

import static com.Graphic.model.HelpersClass.Color_Eraser.*;

public enum CropsType {

    BlueJazz        ("Blue Jazz",   true,    50, 45,
        "JazzSeeds",       false, "Erfan/plants/crops/blueJazz/Blue_Jazz.png"),
    Carrot          ("Carrot",      true,    35, 75,
        "CarrotSeeds",     true, "Erfan/plants/crops/carrot/Carrot.png"),
    Cauliflower     ("Cauliflower", true,    175,75,
        "CauliflowerSeeds",true, "Erfan/plants/crops/Cauliflower/Cauliflower.png"),
    CoffeeBean      ("Coffee Bean", false,   15,  0,
        "CoffeeBean",      false, "Erfan/plants/crops/coffeeBean/Coffee_Bean.png"),
    Garlic          ("Garlic",      true,    60, 20,
        "GarlicSeeds",     true, "Erfan/plants/crops/Garlic/Garlic.png"),
    GreenBean       ("Green Bean",   true,   40, 25,
        "BeanStarter",     true, "Erfan/plants/crops/GreenBean/Green_Bean.png"),
    Kale            ("Kale",        true,    110,50,
        "KaleSeeds",       true, "Erfan/plants/crops/kale/Kale.png"),
    Parsnip         ("Parsnip",     true,    35, 25,
        "ParsnipSeeds",    true, "Erfan/plants/crops/parsnip/Parsnip.png"),
    Potato          ("Potato",      true,    80, 25,
        "PotatoSeeds",     true, "Erfan/plants/crops/potato/Potato.png"),
    Rhubarb         ("Rhubarb",     false,   220, 0,
        "RhubarbSeeds",    false, "Erfan/plants/crops/rhubarb/Rhubarb.png"),
    Strawberry      ("Strawberry",  true,    120,50,
        "StrawberrySeeds", false, "Erfan/plants/crops/strawberry/Strawberry.png"),
    Tulip           ("Tulip",       true,    30, 45,
        "TulipBulb",       false, "Erfan/plants/crops/tulip/Tulip.png"),
    UnmilledRice    ("Unmilled Rice",true,   30,  3,
        "RiceShoot",       false, "Erfan/plants/crops/unmilledRice/Unmilled_Rice.png"),
    Blueberry       ("Blueberry",   true,    50, 25,
        "BlueberrySeeds",  false, "Erfan/plants/crops/blueberry/Blueberry.png"),
    Corn            ("Corn",        true,    50, 25,
        "CornSeeds",       true, "Erfan/plants/crops/corn/Corn.png"),
    Hops            ("Hops",        true,    25, 45,
        "HopsStarter",     true, "Erfan/plants/crops/hops/Hops.png"),
    HotPepper       ("Hot Pepper",  true,    40, 13,
        "PepperSeeds",     false, "Erfan/plants/crops/hotPepper/Hot_Pepper.png"),
    Melon           ("Melon",       true,    250,113,
        "MelonSeeds",      false, "Erfan/plants/crops/melon/Melon.png"),
    Poppy           ("Poppy",       true,    140,45,
        "PoppySeeds",      false, "Erfan/plants/crops/poppy/Poppy.png"),
    Radish          ("Radish",      true,    90, 45,
        "RadishSeeds",     true, "Erfan/plants/crops/radish/Radish.png"),
    RedCabbage      ("Red Cabbage", true,    260,75,
        "RedCabbageSeeds", true, "Erfan/plants/crops/redCabbage/Red_Cabbage.png"),
    Starfruit       ("Starfruit",   true,    750,125,
        "StarfruitSeeds",  false, "Erfan/plants/crops/starfruit/Starfruit.png"),
    SummerSpangle   ("Summer Spangle",true,  90, 45,
        "SpangleSeeds",    false, "Erfan/plants/crops/summerSpangle/Summer_Spangle.png"),
    SummerSquash    ("Summer Squash",true,   45, 63,
        "SummerSquashSeeds",true, "Erfan/plants/crops/summerSquash/Summer_Squash.png"),
    Sunflower       ("Sunflower",   true,    80, 45,
        "SunflowerSeeds",  false, "Erfan/plants/crops/sunflower/Sunflower.png"),
    Tomato          ("Tomato",      true,    60, 20,
        "TomatoSeeds",     true, "Erfan/plants/crops/tomato/Tomato.png"),
    Wheat           ("Wheat",       false,   25,  0,
        "WheatSeeds",      true, "Erfan/plants/crops/wheat/Wheat.png"),
    Amaranth        ("Amaranth",    true,    150,50,
        "AmaranthSeeds",   true, "Erfan/plants/crops/amaranth/Amaranth.png"),
    Artichoke       ("Artichoke",   true,    160,30,
        "ArtichokeSeeds",  true, "Erfan/plants/crops/artichoke/Artichoke.png"),
    Beet            ("Beet",        true,    100,30,
        "BeetSeeds",       true, "Erfan/plants/crops/beet/Beet.png"),
    BokChoy         ("Bok Choy",     true,    80, 25,
        "BokChoySeeds",   true, "Erfan/plants/crops/bokChoy/Bok_Choy.png"),
    Broccoli        ("Broccoli",    true,    70, 63,
        "BroccoliSeeds",   true, "Erfan/plants/crops/Broccoli/Broccoli.png"),
    Cranberries     ("Cranberries", true,    75, 38,
        "CranberrySeeds",  false, "Erfan/plants/crops/cranberries/Cranberries.png"),
    Eggplant        ("Eggplant",    true,    60, 20,
        "EggplantSeeds",   true, "Erfan/plants/crops/eggplant/Eggplant.png"),
    FairyRose       ("Fairy Rose",  true,    290,45,
        "FairySeeds",      false, "Erfan/plants/crops/fairyRose/Fairy_Rose.png"),
    Grape           ("Grape",       true,    80, 38,
        "GrapeStarter",    false, "Erfan/plants/crops/grape/Grape.png"),
    Pumpkin         ("Pumpkin",     false,   320, 0,
        "PumpkinSeeds",    true, "Erfan/plants/crops/pumpkin/Pumpkin.png"),
    Yam             ("Yam",         true,    160,45,
        "YamSeeds",        true, "Erfan/plants/crops/yam/Yam.png"),
    SweetGemBerry   ("Sweet Gem Berry",false,  3000,0,
        "RareSeed",      false, "Erfan/plants/crops/sweetGemBerry/Sweet_Gem_Berry.png"),
    Powdermelon     ("Powdermelon",true,     60, 63,
        "PowdermelonSeeds",false, "Erfan/plants/crops/Powdermelon/Powdermelon.png"),
    AncientFruit    ("Ancient Fruit",false,   550, 0,
        "AncientSeeds",   false, "Erfan/plants/crops/ancientFruit/Ancient_Fruit.png");

    private final String displayName;
    private final String sourceName;
    private final boolean isEdible;
    private final String iconPath;
    private final int price;
    private final int energy;
    private final boolean isVegetable;

    CropsType (String displayName, boolean isEdible, int price, int energy,
               String sourceName , boolean isVegetable, String iconPath) {
        this.displayName = displayName;
        this.iconPath = iconPath;
        this.isEdible = isEdible;
        this.price = price;
        this.sourceName = sourceName;
        this.energy = energy;
        this.isVegetable = isVegetable;
    }

    public String getDisplayName () {
        return this.displayName;
    }
    public String getIconPath() {
        return iconPath;
    }
    public int getEnergy () {
        return this.energy;
    }
    public boolean isEdible() {
        return isEdible;
    }
    public boolean isVegetable () {
        return isVegetable;
    }
    public int getPrice() {
        return price;
    }



    public static String getInformation (CropsType type) {

        StringBuilder builder = new StringBuilder();

        ForagingSeedsType seedsType = ForagingSeedsType.valueOf(type.sourceName);

        builder.append("Name: ").append(type.displayName)
                .append("\nSource: ").append(seedsType.getDisplayName())
                .append("\nStages: ");
        for (int i = 0; i < seedsType.getGrowthStages(); i++)
            builder.append(seedsType.getStageDate(i)).append("-");

        builder.deleteCharAt(builder.length() - 1);

        int total = 0;
        for (Integer integer : seedsType.getStageDays())
            total += integer;


        builder.append("\nTotal Harvest Time: ").append(total)
                .append("\nOne Time: ").append(seedsType.isOneTimeUse())
                .append("\nRegrowth Time: ");

        if (!seedsType.isOneTimeUse())
            builder.append(seedsType.getRegrowthTime());

        builder.append("\nBase Sell Price: ").append(type.getPrice())
                .append("\nIs Edible: ").append(type.isEdible)
                .append("\nBase Energy: ").append(type.energy)
                .append("\nSeason: ");

        ArrayList<Season> seasons = seedsType.getSeason();
        for (Season season : seasons) builder.append(season.getDisplayName()).append(" ");

        builder.append("\nCan Become Giant: ").append(seedsType.canGrowGiant());
        return builder.toString();
    }
    public static CropsType fromDisplayName(String displayName) {
        for (CropsType type : CropsType.values())
            if (type.getDisplayName().equalsIgnoreCase(displayName))
                return type;
        throw new IllegalArgumentException("wrong name!");
    }
}
