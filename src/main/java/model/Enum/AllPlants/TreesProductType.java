package model.Enum.AllPlants;

import model.Enum.WeatherTime.Season;

public enum TreesProductType {

    Apricot     ("Apricot",   59,  true,  38,  TreeType.ApricotTree),
    Cherry      ("Cherry",    80,  true,  38,  TreeType.CherryTree),
    Banana      ("Banana",    150, true,  75,  TreeType.BananaTree),
    Mango       ("Mango",     130, true,  100, TreeType.MangoTree),
    Orange      ("Orange",    100, true,  38,  TreeType.OrangeTree),
    Peach       ("Peach",     140, true,  38,  TreeType.PeachTree),
    Apple       ("Apple",     100, true,  38,  TreeType.AppleTree),
    OakResin    ("OakResin",  150, false, 0,   TreeType.OakTree),
    MapleSyrup  ("MapleSyrup",200, false, 0,   TreeType.MapleTree),
    PineTar     ("PineTar",   100, false, 0,   TreeType.PineTree),
    Sap         ("Sap",       2,   false, -2,  TreeType.MahoganyTree),
    MysticSyrup ("MysticSyrup",1000,false, 500, TreeType.MysticTree),
    Pomegranate ("Pomegranate",140, true,  38,  TreeType.PomegranateTree),
    CommonMushroom("CommonMushroom",40,true,  38,  TreeType.MushroomTree);

    private String displayName;
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
}
