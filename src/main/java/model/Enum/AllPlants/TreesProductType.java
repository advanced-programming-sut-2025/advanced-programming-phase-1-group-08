package model.Enum.AllPlants;

import model.Enum.WeatherTime.Season;

import java.util.ArrayList;

import static model.Color_Eraser.*;

public enum TreesProductType {

    Apricot     ("Apricot",   59,  true,  38,  TreeType.ApricotTree),
    Cherry      ("Cherry",    80,  true,  38,  TreeType.CherryTree),
    Banana      ("Banana",    150, true,  75,  TreeType.BananaTree),
    Mango       ("Mango",     130, true,  100, TreeType.MangoTree),
    Orange      ("Orange",    100, true,  38,  TreeType.OrangeTree),
    Peach       ("Peach",     140, true,  38,  TreeType.PeachTree),
    Apple       ("Apple",     100, true,  38,  TreeType.AppleTree),
    OakResin    ("Oak Resin", 150, false, 0,   TreeType.OakTree),
    MapleSyrup  ("Maple Syrup",200, false, 0,   TreeType.MapleTree),
    PineTar     ("Pine Tar",  100, false, 0,   TreeType.PineTree),
    Sap         ("Sap",       2,   false, -2,  TreeType.MahoganyTree),
    MysticSyrup ("Mystic Syrup",1000,false, 500, TreeType.MysticTree),
    Pomegranate ("Pomegranate",140, true,  38,  TreeType.PomegranateTree),
    CommonMushroom("Common Mushroom",40,true,  38,  TreeType.MushroomTree);

    private final String displayName;
    private final int price;
    private final boolean edible;
    private final int energy;
    private final TreeType treeType;

    TreesProductType (String displayName, int price, boolean edible, int energy, TreeType treeType) {
        this.price = price;
        this.edible = edible;
        this.energy = energy;
        this.treeType = treeType;
        this.displayName = displayName;
    }

    public int getPrice() {
        return price;
    }
    public boolean isEdible() {
        return edible;
    }
    public int getEnergy() {
        return energy;
    }
    public TreeType getTreeType() {
        return treeType;
    }
    public String getDisplayName() {
        return displayName;
    }

    public static String getInformation (TreesProductType type) {

        StringBuilder builder = new StringBuilder();

        builder.append(BLUE+"Name: "+RESET).append(type.displayName)
                .append(BLUE+"\nSource: "+RESET).append(type.treeType.getSourceType().getDisplayName())
                .append(BLUE+"\nStages: "+RESET).append("7-7-7-7")
                .append(BLUE+"\nTotal Harvest Time: "+RESET).append("28")
                .append(BLUE+"\nOne Time: "+RESET).append("False")
                .append(BLUE+"\nRegrowth Time: "+RESET+"28")
                .append(BLUE+"\nBase Sell Price: "+RESET).append(type.getPrice())
                .append(BLUE+"\nIs Edible: "+RESET).append(type.isEdible())
                .append(BLUE+"\nBase Energy: "+RESET).append(type.energy)
                .append(BLUE+"\nSeason: "+RESET);

        ArrayList<Season> seasons = type.treeType.getSourceType().getSeason();
        for (Season season : seasons) builder.append(season.getDisplayName()).append(" ");

        builder.append(BLUE+"\nCan Become Giant: "+RESET).append("No");
        return builder.toString();
    }
    public static TreesProductType fromDisplayName(String displayName) {
        for (TreesProductType type : TreesProductType.values())
            if (type.getDisplayName().equals(displayName))
                return type;
        throw new IllegalArgumentException(RED+"wrong name!"+RESET);
    }
}

