package model.Enum.AllPlants;

import model.Enum.WeatherTime.Season;

import java.util.ArrayList;

import static model.Color_Eraser.*;

public enum CropsType {

    BlueJazz        ("Blue Jazz",   true,    50, 45, ForagingSeedsType.JazzSeeds),
    Carrot          ("Carrot",      true,    35, 75, ForagingSeedsType.CarrotSeeds),
    Cauliflower     ("Cauliflower", true,    175,75, ForagingSeedsType.CauliflowerSeeds),
    CoffeeBean      ("Coffee Bean", false,   15,  0, ForagingSeedsType.CoffeeBean),
    Garlic          ("Garlic",      true,    60, 20, ForagingSeedsType.GarlicSeeds),
    GreenBean       ("GreenBean",   true,    40, 25, ForagingSeedsType.BeanStarter),
    Kale            ("Kale",        true,    110,50, ForagingSeedsType.KaleSeeds),
    Parsnip         ("Parsnip",     true,    35, 25, ForagingSeedsType.ParsnipSeeds),
    Potato          ("Potato",      true,    80, 25, ForagingSeedsType.PotatoSeeds),
    Rhubarb         ("Rhubarb",     false,   220, 0, ForagingSeedsType.RhubarbSeeds),
    Strawberry      ("Strawberry",  true,    120,50, ForagingSeedsType.StrawberrySeeds),
    Tulip           ("Tulip",       true,    30, 45, ForagingSeedsType.TulipBulb),
    UnmilledRice    ("Unmilled Rice",true,   30,  3, ForagingSeedsType.RiceShoot),
    Blueberry       ("Blueberry",   true,    50, 25, ForagingSeedsType.BlueberrySeeds),
    Corn            ("Corn",        true,    50, 25, ForagingSeedsType.CornSeeds),
    Hops            ("Hops",        true,    25, 45, ForagingSeedsType.HopsStarter),
    HotPepper       ("Hot Pepper",  true,    40, 13, ForagingSeedsType.PepperSeeds),
    Melon           ("Melon",       true,    250,113,ForagingSeedsType.MelonSeeds),
    Poppy           ("Poppy",       true,    140,45, ForagingSeedsType.PoppySeeds),
    Radish          ("Radish",      true,    90, 45, ForagingSeedsType.RadishSeeds),
    RedCabbage      ("Red Cabbage", true,    260,75, ForagingSeedsType.RedCabbageSeeds),
    Starfruit       ("Starfruit",   true,    750,125,ForagingSeedsType.StarfruitSeeds),
    SummerSpangle   ("Summer Spangle",true,  90, 45, ForagingSeedsType.SpangleSeeds),
    SummerSquash    ("Summer Squash",true,   45, 63, ForagingSeedsType.SummerSquashSeeds),
    Sunflower       ("Sunflower",   true,    80, 45, ForagingSeedsType.SunflowerSeeds),
    Tomato          ("Tomato",      true,    60, 20, ForagingSeedsType.TomatoSeeds),
    Wheat           ("Wheat",       false,   25,  0, ForagingSeedsType.WheatSeeds),
    Amaranth        ("Amaranth",    true,    150,50, ForagingSeedsType.AmaranthSeeds),
    Artichoke       ("Artichoke",   true,    160,30, ForagingSeedsType.ArtichokeSeeds),
    Beet            ("Beet",        true,    100,30, ForagingSeedsType.BeetSeeds),
    BokChoy         ("BokChoy",     true,    80, 25, ForagingSeedsType.BokChoySeeds),
    Broccoli        ("Broccoli",    true,    70, 63, ForagingSeedsType.BroccoliSeeds),
    Cranberries     ("Cranberries", true,    75, 38, ForagingSeedsType.CranberrySeeds),
    Eggplant        ("Eggplant",    true,    60, 20, ForagingSeedsType.EggplantSeeds),
    FairyRose       ("Fairy Rose",  true,    290,45, ForagingSeedsType.FairySeeds),
    Grape           ("Grape",       true,    80, 38, ForagingSeedsType.GrapeStarter),
    Pumpkin         ("Pumpkin",     false,   320, 0, ForagingSeedsType.PumpkinSeeds),
    Yam             ("Yam",         true,    160,45, ForagingSeedsType.YamSeeds),
    SweetGemBerry   ("SweetGemBerry",false,  3000,0, ForagingSeedsType.RareSeed),
    Powdermelon     ("Powdermelon",true,     60, 63, ForagingSeedsType.PowdermelonSeeds),
    AncientFruit    ("AncientFruit",false,   550, 0, ForagingSeedsType.AncientSeeds);

    private final String displayName;
    private final boolean isEdible;
    private final int price;
    private final int energy;
    private final ForagingSeedsType seedsType;

    CropsType (String displayName, boolean isEdible, int price, int energy, ForagingSeedsType seedsType) {
        this.displayName = displayName;
        this.isEdible = isEdible;
        this.price = price;
        this.seedsType = seedsType;
        this.energy = energy;
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

        builder.append("Name: ").append(type.displayName).append("\nSource: ").append(type.seedsType.getDisplayName())
                .append("\nStages: ");
        for (int i = 0; i < type.seedsType.getGrowthStages(); i++)
            builder.append(type.seedsType.getStageDate(i)).append("-");

        builder.deleteCharAt(builder.length() - 1);

        builder.append("\nTotal Harvest Time: ").append(type.seedsType.getGrowthStages())
                .append("\nOne Time: ").append(type.seedsType.isOneTimeUse()).append("\nRegrowth Time:")
                .append("\nBase Sell Price: ").append(type.getPrice()).append("\nIs Edible: ").append(type.isEdible)
                .append("\nBase Energy: ").append(type.energy).append("\nSeason: ");

        ArrayList<Season> seasons = type.seedsType.getSeason();
        for (Season season : seasons) builder.append(season.getDisplayName()).append(" ");

        builder.append("\nCan Become Giant: ").append(type.getSeedsType().canGrowGiant());
        return builder.toString();
    }

    public static CropsType fromDisplayName(String displayName) {
        for (CropsType type : CropsType.values())
            if (type.getDisplayName().equals(displayName))
                return type;
        throw new IllegalArgumentException(RED+"wrong name!"+RESET);
    }
}
