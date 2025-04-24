package model.Enum.AllPlants;

import model.Enum.WeatherTime.Season;

import java.util.ArrayList;
import java.util.List;

public enum TreesSourceType {

    Apricot_Sapling     (TreeType.ApricotTree,    0.0, "Apricot Sapling"){

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of( Season.Spring));
        }
    },
    Cherry_Sapling      (TreeType.CherryTree,     0.0, "Cherry Sapling") {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of( Season.Spring));
        }
    },
    Banana_Sapling      (TreeType.BananaTree,     0.0, "Banana Sapling") {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Summer));
        }
    },
    Mango_Sapling       (TreeType.MangoTree,      0.0, "Mango Sapling")  {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Summer));
        }
    },
    Orange_Sapling      (TreeType.OrangeTree,     0.0, "Orange Sapling") {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Summer));
        }
    },
    Peach_Sapling       (TreeType.PeachTree,      0.0, "Peach Sapling")  {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Summer));
        }
    },
    Apple_Sapling       (TreeType.AppleTree,      0.0, "Apple Sapling")  {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Fall));
        }
    },
    Acorns              (TreeType.OakTree,        0.0, "Acorns")         {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of( Season.Spring, Season.Summer, Season.Fall, Season.Winter));
        }
    },
    Maple_Seeds         (TreeType.MapleTree,      0.0, "Maple Seeds")         {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of( Season.Spring, Season.Summer, Season.Fall, Season.Winter));
        }
    },
    Pomegranate_Sapling (TreeType.PomegranateTree,0.0, "Pomegranate Sapling") {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Fall));
        }
    },
    Pine_Cones          (TreeType.PineTree,       0.0, "Pine Cones")          {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of( Season.Spring, Season.Summer, Season.Fall, Season.Winter));
        }
    },
    Mahogany_Seeds      (TreeType.MahoganyTree,   0.0, "Mahogany Seeds")      {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of( Season.Spring, Season.Summer, Season.Fall, Season.Winter));
        }
    },
    Mushroom_Tree_Seeds (TreeType.MushroomTree,   0.0, "Mushroom Tree Seeds") {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of( Season.Spring, Season.Summer, Season.Fall, Season.Winter));
        }
    },
    Mystic_Tree_Seeds   (TreeType.MysticTree,     0.0, "Mystic Tree Seeds")   {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of( Season.Spring, Season.Summer, Season.Fall, Season.Winter));
        }
    };

    private final String displayName;
    private final TreeType treeType;
    private final double probability;

    TreesSourceType(TreeType treeType,double probability, String displayName) {
        this.treeType = treeType;
        this.probability = probability;
        this.displayName = displayName;
    }

    public TreeType getTreeType() {
        return treeType;
    }
    public double getProbability() {
        return probability;
    }
    public String getDisplayName() {
        return displayName;
    }
    public abstract ArrayList<Season> getSeason();
}