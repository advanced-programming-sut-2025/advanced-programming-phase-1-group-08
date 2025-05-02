package model.Enum.AllPlants;

import static model.Color_Eraser.*;
import static model.Enum.AllPlants.TreesSourceType.*;

public enum TreeType {

    ApricotTree     ("Apricot Tree",  Apricot_Sapling,  TreesProductType.Apricot,
            1,BG_BRIGHT_WHITE+BLACK+BOLD+"A"+RESET, BG_BRIGHT_WHITE+GREEN+BOLD+"A"+RESET),
    CherryTree      ("Cherry Tree",   Cherry_Sapling,   TreesProductType.Cherry,
            1,BG_BRIGHT_WHITE+BLACK+BOLD+"C"+RESET, BG_BRIGHT_WHITE+GREEN+BOLD+"C"+RESET),
    BananaTree      ("Banana Tree",   Banana_Sapling,   TreesProductType.Banana,
            1,BG_BRIGHT_WHITE+BLACK+BOLD+"B"+RESET, BG_BRIGHT_WHITE+GREEN+BOLD+"B"+RESET),
    MangoTree       ("Mango Tree",    Mango_Sapling,    TreesProductType.Mango,
            1,BG_BRIGHT_WHITE+BLACK+BOLD+"M"+RESET, BG_BRIGHT_WHITE+GREEN+BOLD+"M"+RESET),
    OrangeTree      ("Orange Tree",   Orange_Sapling,   TreesProductType.Orange,
            1,BG_BRIGHT_WHITE+BLACK+BOLD+"O"+RESET, BG_BRIGHT_WHITE+GREEN+BOLD+"O"+RESET),
    PeachTree       ("Peach Tree",    Peach_Sapling,    TreesProductType.Peach,
            1,BG_BRIGHT_WHITE+BLACK+BOLD+"P"+RESET, BG_BRIGHT_WHITE+GREEN+BOLD+"P"+RESET),
    AppleTree       ("Apple Tree",    Apple_Sapling,    TreesProductType.Apple,
            1,BG_WHITE+BLACK+BOLD+"A"+RESET, BG_WHITE+GREEN+BOLD+"A"+RESET),
    OakTree         ("Oak Tree",      Acorns,           TreesProductType.OakResin,
            7,BG_WHITE+BLACK+BOLD+"O"+RESET, BG_WHITE+GREEN+BOLD+"O"+RESET),
    MapleTree       ("Maple Tree",    Maple_Seeds,      TreesProductType.MapleSyrup,
            9,BG_WHITE+BLACK+BOLD+"M"+RESET, BG_WHITE+GREEN+BOLD+"M"+RESET),
    PineTree        ("Pine Tree",     Pine_Cones,       TreesProductType.PineTar,
            5,BG_WHITE+BLACK+BOLD+"P"+RESET, BG_WHITE+GREEN+BOLD+"P"+RESET),
    MahoganyTree    ("Mahogany Tree", Mahogany_Seeds,   TreesProductType.Sap,
            1,BG_WHITE+BRIGHT_WHITE+BOLD+"M"+RESET, BG_WHITE+BROWN+BOLD+"M"+RESET),
    MysticTree      ("Mystic Tree",   Mystic_Tree_Seeds,TreesProductType.MysticSyrup,
            7,BG_BRIGHT_WHITE+PURPLE+BOLD+"M"+RESET, BG_BRIGHT_WHITE+BROWN+BOLD+"M"+RESET),
    MushroomTree    ("Mushroom Tree", Mushroom_Tree_Seeds,    TreesProductType.CommonMushroom,
            1,BG_BLUE+BLACK+BOLD+"M"+RESET, BG_BLUE+GREEN+BOLD+"M"+RESET),
    PomegranateTree ("Pomegranate Tree", Pomegranate_Sapling, TreesProductType.Pomegranate,
            1,BG_WHITE+BROWN+BOLD+"P"+RESET, BG_WHITE+RED+BOLD+"P"+RESET);

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

    public String getIcon (int level) {
        if (level < 4)
            return this.icon1;
        else
            return this.icon2;
    }
    public String getDisplayName() {return displayName;}
    public TreesSourceType getSourceType() {return sourceType;}
    public TreesProductType getProductType() {return productType;}
    public int getHarvestYield() {return harvestYield;}
}
