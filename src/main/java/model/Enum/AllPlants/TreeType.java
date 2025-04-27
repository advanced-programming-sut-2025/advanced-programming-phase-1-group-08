package model.Enum.AllPlants;

import static model.Color_Eraser.*;
import static model.Enum.AllPlants.TreesSourceType.*;

public enum TreeType {

    ApricotTree     ("Apricot Tree",  Apricot_Sapling,  TreesProductType.Apricot,
            1,BG_BRIGHT_WHITE+BLACK+BOLD+"A", BG_BRIGHT_WHITE+GREEN+BOLD+"A"),
    CherryTree      ("Cherry Tree",   Cherry_Sapling,   TreesProductType.Cherry,
            1,BG_BRIGHT_WHITE+BLACK+BOLD+"C", BG_BRIGHT_WHITE+GREEN+BOLD+"C"),
    BananaTree      ("Banana Tree",   Banana_Sapling,   TreesProductType.Banana,
            1,BG_BRIGHT_WHITE+BLACK+BOLD+"B", BG_BRIGHT_WHITE+GREEN+BOLD+"B"),
    MangoTree       ("Mango Tree",    Mango_Sapling,    TreesProductType.Mango,
            1,BG_BRIGHT_WHITE+BLACK+BOLD+"M", BG_BRIGHT_WHITE+GREEN+BOLD+"M"),
    OrangeTree      ("Orange Tree",   Orange_Sapling,   TreesProductType.Orange,
            1,BG_BRIGHT_WHITE+BLACK+BOLD+"O", BG_BRIGHT_WHITE+GREEN+BOLD+"O"),
    PeachTree       ("Peach Tree",    Peach_Sapling,    TreesProductType.Peach,
            1,BG_BRIGHT_WHITE+BLACK+BOLD+"P", BG_BRIGHT_WHITE+GREEN+BOLD+"P"),
    AppleTree       ("Apple Tree",    Apple_Sapling,    TreesProductType.Apple,
            1,BG_WHITE+BLACK+BOLD+"A", BG_WHITE+GREEN+BOLD+"A"),
    OakTree         ("Oak Tree",      Acorns,           TreesProductType.OakResin,
            7,BG_WHITE+BLACK+BOLD+"O", BG_WHITE+GREEN+BOLD+"O"),
    MapleTree       ("Maple Tree",    Maple_Seeds,      TreesProductType.MapleSyrup,
            9,BG_WHITE+BLACK+BOLD+"M", BG_WHITE+GREEN+BOLD+"M"),
    PineTree        ("Pine Tree",     Pine_Cones,       TreesProductType.PineTar,
            5,BG_WHITE+BLACK+BOLD+"P", BG_WHITE+GREEN+BOLD+"P"),
    MahoganyTree    ("Mahogany Tree", Mahogany_Seeds,   TreesProductType.Sap,
            1,BG_WHITE+BRIGHT_WHITE+BOLD+"M", BG_WHITE+BROWN+BOLD+"M"),
    MysticTree      ("Mystic Tree",   Mystic_Tree_Seeds,TreesProductType.MysticSyrup,
            7,BG_BRIGHT_WHITE+PURPLE+BOLD+"M", BG_BRIGHT_WHITE+BROWN+BOLD+"M"),
    MushroomTree    ("Mushroom Tree", Mushroom_Tree_Seeds,    TreesProductType.CommonMushroom,
            1,BG_BLUE+BLACK+BOLD+"M", BG_BLUE+GREEN+BOLD+"M"),
    PomegranateTree ("Pomegranate Tree", Pomegranate_Sapling, TreesProductType.Pomegranate,
            1,BG_WHITE+BROWN+BOLD+"P", BG_WHITE+RED+BOLD+"P");

    private final String displayName;
    private final TreesSourceType sourceType;
    private final TreesProductType productType;
    private final int harvestYield;
    private final String icon1;
    private final String icon2;

    TreeType (String displayName, TreesSourceType sourceType, TreesProductType productType,
              int harvestYield, String icon1, String icon2) {

        this.displayName = displayName;
        this.sourceType = sourceType;
        this.productType = productType;
        this.harvestYield = harvestYield;
        this.icon1 = icon1;
        this.icon2 = icon2;
    }

    public String getDisplayName() {return displayName;}
    public TreesSourceType getSourceType() {return sourceType;}
    public TreesProductType getProductType() {return productType;}
    public int getHarvestYield() {return harvestYield;}
}
