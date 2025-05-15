package model.Enum.ItemType;

import model.*;
import model.Places.MarketItem;

import java.util.Map;

public enum CraftType {
    CherryBomb("Cherry Bomb", 50, Map.of("Copper Ore", 4, "Coal", 1), "💣") {
        @Override
        public boolean checkLevel() {
            return App.currentGame.currentPlayer.getLevelMining() >= 1;
        }
    },
    Bomb("Bomb", 50, Map.of("Iron Ore", 4, "Coal", 1), "🧨") {
        @Override
        public boolean checkLevel() {
            return App.currentGame.currentPlayer.getLevelMining() >= 2;
        }
    },
    MegaBomb("Mega Bomb", 50, Map.of("Gold Ore", 4, "Coal", 1), "💥") {
        @Override
        public boolean checkLevel() {
            return App.currentGame.currentPlayer.getLevelMining() >= 3;
        }
    },
    Sprinkler("Sprinkler", 0, Map.of("Copper Bar", 1, "Iron Bar", 1), "💧") {
        @Override
        public boolean checkLevel() {
            return App.currentGame.currentPlayer.getLevelFarming() >= 1;
        }
    },
    QualitySprinkler("Quality Sprinkler", 0, Map.of("Iron Bar", 1, "Gold Bar", 1), "💦") {
        @Override
        public boolean checkLevel() {
            return App.currentGame.currentPlayer.getLevelFarming() >= 2;
        }
    },
    IridiumSprinkler("Iridium Sprinkler", 0, Map.of("Gold Bar", 1, "Iridium Bar", 1), "🌊") {
        @Override
        public boolean checkLevel() {
            return App.currentGame.currentPlayer.getLevelFarming() >= 3;
        }
    },
    CharcoalKlin("Charcoal Klin", 0, Map.of("Wood", 20, "Copper Bar", 2), "🔥") {
        @Override
        public boolean checkLevel() {
            return App.currentGame.currentPlayer.getLevelForaging() >= 1;
        }
    },
    Furnace("Furnace", 0, Map.of("Copper Ore", 20, "Stone", 25), "♨️") {
        @Override
        public boolean checkLevel() {
            return true;
        }
    },
    Scarecrow("Scarecrow", 0, Map.of("Wood", 50, "Coal", 1, "Fiber", 20), "🪆") {
        @Override
        public boolean checkLevel() {
            return true;
        }
    },
    DeluxeScarecrow("Deluxe Scarecrow", 0, Map.of("Wood", 50, "Coal", 1, "Fiber", 20, "Iridium Ore", 1), "🎃") {
        @Override
        public boolean checkLevel() {
            return App.currentGame.currentPlayer.getLevelFarming() >= 2;
        }
    },
    BeeHouse("Bee House", 0, Map.of("Wood", 40, "Coal", 8, "Iron Bar", 1), "🐝") {
        @Override
        public boolean checkLevel() {
            return App.currentGame.currentPlayer.getLevelFarming() >= 1;
        }
    },
    ChessPress("Chess Press", 0, Map.of("Wood", 45, "Stone", 45, "Copper Bar", 1), "🪵") {
        @Override
        public boolean checkLevel() {
            return App.currentGame.currentPlayer.getLevelFarming() >= 2;
        }
    },
    Keg("Keg", 0, Map.of("Wood", 30, "Copper Bar", 1, "Iron Bar", 1), "🍺") {
        @Override
        public boolean checkLevel() {
            return App.currentGame.currentPlayer.getLevelFarming() >= 3;
        }
    },
    Loom("Loom", 0, Map.of("Wood", 60, "Fiber", 30), "🧵") {
        @Override
        public boolean checkLevel() {
            return App.currentGame.currentPlayer.getLevelFarming() >= 3;
        }
    },
    MayonnaiseMachine("Mayonnaise Machine", 0, Map.of("Wood", 15, "Stone", 15, "Copper Bar", 1), "🥚") {
        @Override
        public boolean checkLevel() {
            return true;
        }
    },
    OilMaker("Oil Maker", 0, Map.of("Wood", 100, "Gold Bar", 1, "Iron Bar", 1), "🛢️") {
        @Override
        public boolean checkLevel() {
            return App.currentGame.currentPlayer.getLevelFarming() >= 3;
        }
    },
    PreservesJar("Preserves Jar", 0, Map.of("Wood", 50, "Stone", 40, "Coal", 8), "🥫") {
        @Override
        public boolean checkLevel() {
            return App.currentGame.currentPlayer.getLevelFarming() >= 2;
        }
    },
    MysticTreeSeed("Mystic Tree Seed", 100, Map.of("Acorns", 5, "Maple Seeds", 5, "Pine Cones", 5, "Mahogany Seeds", 5), "🌱") {
        @Override
        public boolean checkLevel() {
            return App.currentGame.currentPlayer.getLevelForaging() >= 4;
        }
    },
    Dehydrator("Dehydrator", 0, Map.of("Wood", 30, "Stone", 20, "Fiber", 30), "🌞") {
        @Override
        public boolean checkLevel() {
            Inventory inventory = App.currentGame.currentPlayer.getBackPack().inventory;
            for (Map.Entry<Items, Integer> entry : inventory.Items.entrySet()) {
                if (entry.getKey() instanceof MarketItem) {
                    if (((MarketItem) entry.getKey()).getType().equals(MarketItemType.DehydratorRecipe)) {
                        return true;
                    }
                }
            }
            return false;
        }
    },
    GrassStarter("Grass Starter", 0, Map.of("Wood", 1, "Fiber", 1), "🌾") {
        @Override
        public boolean checkLevel() {
            Inventory inventory = App.currentGame.currentPlayer.getBackPack().inventory;
            for (Map.Entry<Items, Integer> entry : inventory.Items.entrySet()) {
                if (entry.getKey() instanceof MarketItem) {
                    if (((MarketItem) entry.getKey()).getType().equals(MarketItemType.GrassStarterRecipe)) {
                        return true;
                    }
                }
            }
            return false;
        }
    },
    FishSmoker("Fish Smoker", 0, Map.of("Wood", 50, "Iron Bar", 3, "Coal", 10), "🐟") {
        @Override
        public boolean checkLevel() {
            Inventory inventory = App.currentGame.currentPlayer.getBackPack().inventory;
            for (Map.Entry<Items, Integer> entry : inventory.Items.entrySet()) {
                if (entry.getKey() instanceof MarketItem) {
                    if (((MarketItem) entry.getKey()).getType().equals(MarketItemType.FishSmokerRecipe)) {
                        return true;
                    }
                }
            }
            return false;
        }
    };


    private final String name;
    private final int sellPrice;
    private Map<String , Integer> ingrediants;
    private final String icon;

    CraftType(String name, int sellPrice, Map map , String icon) {
        this.name = name;
        this.sellPrice = sellPrice;
        this.ingrediants=map;
        this.icon = icon;
    }

    public abstract boolean checkLevel();

    public String getName() {
        return name;
    }
    public int getSellPrice() {
        return sellPrice;
    }
    public Map<String , Integer> getIngrediants() {
        return ingrediants;
    }

    public String getIcon() {
        return icon;
    }
}
