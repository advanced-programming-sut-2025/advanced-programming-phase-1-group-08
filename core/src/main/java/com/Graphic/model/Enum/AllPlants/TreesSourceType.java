package com.Graphic.model.Enum.AllPlants;

import com.Graphic.model.Enum.WeatherTime.Season;

import java.util.ArrayList;
import java.util.List;

import static com.Graphic.model.HelpersClass.Color_Eraser.RED;
import static com.Graphic.model.HelpersClass.Color_Eraser.RESET;
import static com.Graphic.model.Enum.AllPlants.TreeType.*;

public enum TreesSourceType {

    Apricot_Sapling     (ApricotTree,    0.0, "Apricot Sapling"){

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of( Season.Spring));
        }

        @Override
        public int getPrice() {
            return 2000;
        }
    },
    Cherry_Sapling      (CherryTree,     0.0, "Cherry Sapling") {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of( Season.Spring));
        }

        @Override
        public int getPrice() {
            return 3400;
        }
    },
    Banana_Sapling      (BananaTree,     0.0, "Banana Sapling") {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Summer));
        }

        @Override
        public int getPrice() {
            return 0;
        }
    },
    Mango_Sapling       (MangoTree,      0.0, "Mango Sapling")  {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Summer));
        }

        @Override
        public int getPrice() {
            return 0;
        }
    },
    Orange_Sapling      (OrangeTree,     0.0, "Orange Sapling") {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Summer));
        }

        @Override
        public int getPrice() {
            return 4000;
        }
    },
    Peach_Sapling       (PeachTree,      0.0, "Peach Sapling")  {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Summer));
        }

        @Override
        public int getPrice() {
            return 6000;
        }
    },
    Apple_Sapling       (AppleTree,      0.0, "Apple Sapling")  {
        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Fall));
        }

        @Override
        public int getPrice() {
            return 4000;
        }
    },
    Acorns              (OakTree,        0.0, "Acorns")         {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of( Season.Spring, Season.Summer, Season.Fall, Season.Winter));
        }

        @Override
        public int getPrice() {
            return 0;
        }
    },
    Maple_Seeds         (MapleTree,      0.0, "Maple Seeds")         {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of( Season.Spring, Season.Summer, Season.Fall, Season.Winter));
        }

        @Override
        public int getPrice() {
            return 0;
        }
    },
    Pomegranate_Sapling (PomegranateTree,0.0, "Pomegranate Sapling") {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Fall));
        }

        @Override
        public int getPrice() {
            return 6000;
        }
    },
    Pine_Cones          (PineTree,       0.0, "Pine Cones")          {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of( Season.Spring, Season.Summer, Season.Fall, Season.Winter));
        }

        @Override
        public int getPrice() {
            return 0;
        }
    },
    Mahogany_Seeds      (MahoganyTree,   0.0, "Mahogany Seeds")      {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of( Season.Spring, Season.Summer, Season.Fall, Season.Winter));
        }

        @Override
        public int getPrice() {
            return 0;
        }
    },
    Mushroom_Tree_Seeds (MushroomTree,   0.0, "Mushroom Tree Seeds") {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of( Season.Spring, Season.Summer, Season.Fall, Season.Winter));
        }

        @Override
        public int getPrice() {
            return 0;
        }
    },
    Mystic_Tree_Seeds   (MysticTree,     0.0, "Mystic Tree Seeds")   {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of( Season.Spring, Season.Summer, Season.Fall, Season.Winter));
        }

        @Override
        public int getPrice() {
            return 0;
        }
    };

    private final String displayName;
    private final TreeType treeType;
    private final double probability;
    public abstract int getPrice();

    TreesSourceType(TreeType treeType,double probability, String displayName) {
        this.treeType = treeType;
        this.probability = probability;
        this.displayName = displayName;
    }

    public TreeType getTreeType() {
        return this.treeType;
    }
    public double getProbability() {
        return probability;
    }
    public String getDisplayName() {
        return displayName;
    }
    public abstract ArrayList<Season> getSeason();
    public static TreesSourceType fromDisplayName(String displayName) {
        for (TreesSourceType type : TreesSourceType.values())
            if (type.getDisplayName().equals(displayName))
                return type;
        throw new IllegalArgumentException(RED+"wrong name!"+RESET);
    }
}
