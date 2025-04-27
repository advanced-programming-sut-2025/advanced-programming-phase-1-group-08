package model.Enum.AllPlants;

import static model.Enum.AllPlants.TreesSourceType.*;

public enum TreeType {

    ApricotTree     ("Apricot Tree",  Apricot_Sapling,  TreesProductType.Apricot, 1),
    CherryTree      ("Cherry Tree",   Cherry_Sapling,   TreesProductType.Cherry,  1),
    BananaTree      ("Banana Tree",   Banana_Sapling,   TreesProductType.Banana,  1),
    MangoTree       ("Mango Tree",    Mango_Sapling,    TreesProductType.Mango,   1),
    OrangeTree      ("Orange Tree",   Orange_Sapling,   TreesProductType.Orange,  1),
    PeachTree       ("Peach Tree",    Peach_Sapling,    TreesProductType.Peach,   1),
    AppleTree       ("Apple Tree",    Apple_Sapling,    TreesProductType.Apple,   1),
    OakTree         ("Oak Tree",      Acorns,           TreesProductType.OakResin,7),
    MapleTree       ("Maple Tree",    Maple_Seeds,      TreesProductType.MapleSyrup,9),
    PineTree        ("Pine Tree",     Pine_Cones,       TreesProductType.PineTar, 5),
    MahoganyTree    ("Mahogany Tree", Mahogany_Seeds,   TreesProductType.Sap,     1),
    MysticTree      ("Mystic Tree",   Mystic_Tree_Seeds,TreesProductType.MysticSyrup,7),
    MushroomTree    ("Mushroom Tree", Mushroom_Tree_Seeds,    TreesProductType.CommonMushroom,1),
    PomegranateTree ("Pomegranate Tree", Pomegranate_Sapling, TreesProductType.Pomegranate,   1);

    private final String displayName;
    private final TreesSourceType sourceType;
    private final TreesProductType productType;
    private final int harvestYield;

    TreeType (String displayName, TreesSourceType sourceType, TreesProductType productType, int harvestYield) {
        this.displayName = displayName;
        this.sourceType = sourceType;
        this.productType = productType;
        this.harvestYield = harvestYield;
    }

    public String getDisplayName() {
        return displayName;
    }
    public TreesSourceType getSourceType() {
        return sourceType;
    }
    public TreesProductType getProductType() {
        return productType;
    }
    public int getHarvestYield() {
        return harvestYield;
    }
}
