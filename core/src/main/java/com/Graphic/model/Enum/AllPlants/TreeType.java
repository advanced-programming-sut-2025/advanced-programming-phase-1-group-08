package com.Graphic.model.Enum.AllPlants;

import com.Graphic.model.Enum.WeatherTime.Season;

import java.util.ArrayList;
import java.util.List;

import static com.Graphic.model.HelpersClass.Color_Eraser.*;

public enum TreeType {

    ApricotTree     ("Apricot Tree", TreesProductType.Apricot, "Apricot Sapling", 1) {
        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Spring));
        }

        @Override
        public String getPath(int stage) {
            return "Erfan/plants/Trees/apricot/Apricot_Stage_" + stage + ".png";
        }
    },
    CherryTree      ("Cherry Tree", TreesProductType.Cherry, "Cherry Sapling", 1) {
        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of( Season.Spring));
        }
        @Override
        public String getPath(int stage) {
            return "Erfan/plants/Trees/cherry/Cherry_Stage_" + stage + ".png";
        }
    },
    BananaTree      ("Banana Tree", TreesProductType.Banana, "Banana Sapling", 1) {
        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Summer));
        }
        @Override
        public String getPath(int stage) {
            return "Erfan/plants/Trees/banana/Banana_Stage_" + stage + ".png";
        }
    },
    MangoTree       ("Mango Tree", TreesProductType.Mango, "Mango Sapling", 1) {
        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Summer));
        }
        @Override
        public String getPath(int stage) {
            return "Erfan/plants/Trees/mango/Mango_Stage_" + stage + ".png";
        }
    },
    OrangeTree      ("Orange Tree", TreesProductType.Orange, "Orange Sapling", 1) {
        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Summer));
        }
        @Override
        public String getPath(int stage) {
            return "Erfan/plants/Trees/mango/Mango_Stage_" + stage + ".png";
        }

    },
    PeachTree       ("Peach Tree", TreesProductType.Peach, "Peach Sapling", 1) {
        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Summer));
        }
        @Override
        public String getPath(int stage) {
            return "Erfan/plants/Trees/peach/Peach_Stage_" + stage + ".png";
        }

    },
    AppleTree       ("Apple Tree", TreesProductType.Apple, "Apple Sapling", 1) {
        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Fall));
        }
        @Override
        public String getPath(int stage) {
            return "Erfan/plants/Trees/apple/Apple_Stage_" + stage + ".png";
        }

    },
    OakTree         ("Oak Tree", TreesProductType.OakResin, "Acorns", 7) {
        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of( Season.Spring, Season.Summer, Season.Fall, Season.Winter));
        }
        @Override
        public String getPath(int stage) {
            return "Erfan/plants/Trees/oak/Oak_Stage_" + stage + ".png";
        }

    }, // TODO stage 5
    MapleTree       ("Maple Tree", TreesProductType.MapleSyrup, "Maple Seeds", 9) {
        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of( Season.Spring, Season.Summer, Season.Fall, Season.Winter));
        }
        @Override
        public String getPath(int stage) {
            return "Erfan/plants/Trees/maple/Maple_Stage_" + stage + ".png";
        }

    },// TODO stage 5
    PineTree        ("Pine Tree", TreesProductType.PineTar, "Pine Cones", 5) {
        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Fall));
        }
        @Override
        public String getPath(int stage) {
            return "Erfan/plants/Trees/pine/Pine_Stage_" + stage + ".png";
        }

    },// TODO stage 5
    MahoganyTree    ("Mahogany Tree", TreesProductType.Sap, "Mahogany Seeds", 1) {
        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of( Season.Spring, Season.Summer, Season.Fall, Season.Winter));
        }
        @Override
        public String getPath(int stage) {
            return "Erfan/plants/Trees/mahogany/Mahogany_Stage_" + stage + ".png";
        }

    }, // TODO stage 5
    MysticTree      ("Mystic Tree", TreesProductType.MysticSyrup, "Mystic Tree Seeds", 7) {
        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of( Season.Spring, Season.Summer, Season.Fall, Season.Winter));
        }
        @Override
        public String getPath(int stage) {
            return "Erfan/plants/Trees/mystic/Mystic_Tree_Stage_" + stage + ".png";
        }

    },
    MushroomTree    ("Mushroom Tree", TreesProductType.CommonMushroom, "Mushroom Tree Seeds", 1) {
        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of( Season.Spring, Season.Summer, Season.Fall, Season.Winter));
        }
        @Override
        public String getPath(int stage) {
            return "Erfan/plants/Trees/mushroom/MushroomTree_Stage_" + stage + ".png";
        }

    },
    PomegranateTree ("Pomegranate Tree", TreesProductType.Pomegranate, "Pomegranate Sapling", 1) {
        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of( Season.Spring, Season.Summer, Season.Fall, Season.Winter));
        }
        @Override
        public String getPath(int stage) {
            return "Erfan/plants/Trees/pomegranate/Pomegranate_Stage_" + stage + ".png";
        }

    };

    private final String displayName;
    private final TreesProductType productType;
    private final String sourceName;
    private final int harvestYield;

    TreeType (String displayName, TreesProductType productType, String sourceName,
              int harvestYield) {

        this.displayName = displayName;
        this.productType = productType;
        this.sourceName = sourceName;
        this.harvestYield = harvestYield;
    }


    public String getDisplayName() {return displayName;}
    public String getSourceName() {

        return sourceName;
    }
    public TreesProductType getProductType() {return productType;}
    public int getHarvestYield() {return harvestYield;}
    public static String getInformation (TreeType type) {

        return BLUE+"Name : "+BRIGHT_PURPLE + type.displayName +
                BLUE+"\nFruit : "+BRIGHT_PURPLE + type.productType.getDisplayName() +
                BLUE+"\nFruit Harvest Cycle : "+BRIGHT_PURPLE + type.harvestYield;
    }

    public static TreeType fromDisplayName(String displayName) {
        for (TreeType type : TreeType.values())
            if (type.getDisplayName().equalsIgnoreCase(displayName))
                return type;
        throw new IllegalArgumentException(RED+"wrong name!"+RESET);
    }
    public abstract ArrayList<Season> getSeason();
    public abstract String getPath(int stage);

}
