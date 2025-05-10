package model.Enum.AllPlants;

import model.Enum.WeatherTime.Season;

import java.util.ArrayList;

import static model.Color_Eraser.*;

public enum CropsType {

    BlueJazz        ("Blue Jazz",   true,    50, 45, ForagingSeedsType.JazzSeeds , false),
    Carrot          ("Carrot",      true,    35, 75, ForagingSeedsType.CarrotSeeds, true),
    Cauliflower     ("Cauliflower", true,    175,75, ForagingSeedsType.CauliflowerSeeds, true),
    CoffeeBean      ("Coffee Bean", false,   15,  0, ForagingSeedsType.CoffeeBean, false),
    Garlic          ("Garlic",      true,    60, 20, ForagingSeedsType.GarlicSeeds, true),
    GreenBean       ("Green Bean",   true,   40, 25, ForagingSeedsType.BeanStarter , true),
    Kale            ("Kale",        true,    110,50, ForagingSeedsType.KaleSeeds , true),
    Parsnip         ("Parsnip",     true,    35, 25, ForagingSeedsType.ParsnipSeeds , true),
    Potato          ("Potato",      true,    80, 25, ForagingSeedsType.PotatoSeeds , true),
    Rhubarb         ("Rhubarb",     false,   220, 0, ForagingSeedsType.RhubarbSeeds , false),
    Strawberry      ("Strawberry",  true,    120,50, ForagingSeedsType.StrawberrySeeds , false),
    Tulip           ("Tulip",       true,    30, 45, ForagingSeedsType.TulipBulb , false),
    UnmilledRice    ("Unmilled Rice",true,   30,  3, ForagingSeedsType.RiceShoot ,false),
    Blueberry       ("Blueberry",   true,    50, 25, ForagingSeedsType.BlueberrySeeds , false),
    Corn            ("Corn",        true,    50, 25, ForagingSeedsType.CornSeeds , true),
    Hops            ("Hops",        true,    25, 45, ForagingSeedsType.HopsStarter , true),
    HotPepper       ("Hot Pepper",  true,    40, 13, ForagingSeedsType.PepperSeeds , false),
    Melon           ("Melon",       true,    250,113,ForagingSeedsType.MelonSeeds , false),
    Poppy           ("Poppy",       true,    140,45, ForagingSeedsType.PoppySeeds , false),
    Radish          ("Radish",      true,    90, 45, ForagingSeedsType.RadishSeeds , true),
    RedCabbage      ("Red Cabbage", true,    260,75, ForagingSeedsType.RedCabbageSeeds , true),
    Starfruit       ("Starfruit",   true,    750,125,ForagingSeedsType.StarfruitSeeds , false),
    SummerSpangle   ("Summer Spangle",true,  90, 45, ForagingSeedsType.SpangleSeeds , false),
    SummerSquash    ("Summer Squash",true,   45, 63, ForagingSeedsType.SummerSquashSeeds , true),
    Sunflower       ("Sunflower",   true,    80, 45, ForagingSeedsType.SunflowerSeeds , false),
    Tomato          ("Tomato",      true,    60, 20, ForagingSeedsType.TomatoSeeds , true),
    Wheat           ("Wheat",       false,   25,  0, ForagingSeedsType.WheatSeeds , true),
    Amaranth        ("Amaranth",    true,    150,50, ForagingSeedsType.AmaranthSeeds , true),
    Artichoke       ("Artichoke",   true,    160,30, ForagingSeedsType.ArtichokeSeeds , true),
    Beet            ("Beet",        true,    100,30, ForagingSeedsType.BeetSeeds , true),
    BokChoy         ("Bok Choy",     true,    80, 25, ForagingSeedsType.BokChoySeeds , true),
    Broccoli        ("Broccoli",    true,    70, 63, ForagingSeedsType.BroccoliSeeds , true),
    Cranberries     ("Cranberries", true,    75, 38, ForagingSeedsType.CranberrySeeds, false),
    Eggplant        ("Eggplant",    true,    60, 20, ForagingSeedsType.EggplantSeeds , true),
    FairyRose       ("Fairy Rose",  true,    290,45, ForagingSeedsType.FairySeeds, false),
    Grape           ("Grape",       true,    80, 38, ForagingSeedsType.GrapeStarter , false),
    Pumpkin         ("Pumpkin",     false,   320, 0, ForagingSeedsType.PumpkinSeeds , true),
    Yam             ("Yam",         true,    160,45, ForagingSeedsType.YamSeeds , true),
    SweetGemBerry   ("Sweet Gem Berry",false,  3000,0, ForagingSeedsType.RareSeed , false),
    Powdermelon     ("Powdermelon",true,     60, 63, ForagingSeedsType.PowdermelonSeeds , false),
    AncientFruit    ("Ancient Fruit",false,   550, 0, ForagingSeedsType.AncientSeeds , false);

    private final String displayName;
    private final boolean isEdible;
    private final int price;
    private final int energy;
    private final ForagingSeedsType seedsType;
    private final boolean isVegetable;

    CropsType (String displayName, boolean isEdible, int price, int energy, ForagingSeedsType seedsType , boolean isVegetable) {
        this.displayName = displayName;
        this.isEdible = isEdible;
        this.price = price;
        this.seedsType = seedsType;
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
    public ForagingSeedsType getSeedsType() {
        return seedsType;
    }
    public static String getInformation (CropsType type) {

        StringBuilder builder = new StringBuilder();

        builder.append(BLUE+"Name: "+RESET).append(type.displayName)
                .append(BLUE+"\nSource: "+RESET).append(type.seedsType.getDisplayName())
                .append(BLUE+"\nStages: "+RESET);
        for (int i = 0; i < type.seedsType.getGrowthStages(); i++)
            builder.append(type.seedsType.getStageDate(i)).append("-");

        builder.deleteCharAt(builder.length() - 1);

        int total = 0;
        for (Integer integer : type.seedsType.getStageDays())
            total += integer;


        builder.append(BLUE+"\nTotal Harvest Time: "+RESET).append(total)
                .append(BLUE+"\nOne Time: "+RESET).append(type.seedsType.isOneTimeUse())
                .append(BLUE+"\nRegrowth Time: "+RESET);

        if (!type.seedsType.isOneTimeUse())
            builder.append(type.seedsType.getRegrowthTime());

        builder.append(BLUE+"\nBase Sell Price: "+RESET).append(type.getPrice())
                .append(BLUE+"\nIs Edible: "+RESET).append(type.isEdible)
                .append(BLUE+"\nBase Energy: "+RESET).append(type.energy)
                .append(BLUE+"\nSeason: "+RESET);

        ArrayList<Season> seasons = type.seedsType.getSeason();
        for (Season season : seasons) builder.append(season.getDisplayName()).append(" ");

        builder.append(BLUE+"\nCan Become Giant: "+RESET).append(type.getSeedsType().canGrowGiant());
        return builder.toString();
    }

    public static CropsType fromDisplayName(String displayName) {
        for (CropsType type : CropsType.values())
            if (type.getDisplayName().equalsIgnoreCase(displayName))
                return type;
        throw new IllegalArgumentException(RED+"wrong name!"+RESET);
    }
}
