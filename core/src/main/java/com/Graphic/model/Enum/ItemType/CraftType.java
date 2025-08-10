package com.Graphic.model.Enum.ItemType;

import com.Graphic.Main;
import com.Graphic.model.*;
import com.Graphic.model.Places.MarketItem;

import java.util.Map;

public enum CraftType {
    CherryBomb("Cherry Bomb", 50, Map.of("Copper Ore", 4, "Coal", 1),
        "Mohamadreza/CraftIcon/Cherry_Bomb.png", "Ariyo/Craftable_item/Cherry_Bomb.png",false) {
        @Override
        public boolean checkLevel() {
            return Main.getClient().getPlayer().getLevelMining() >= 1;
        }
    },
    Bomb("Bomb", 50, Map.of("Iron Ore", 4, "Coal", 1),
        "Mohamadreza/CraftIcon/Bomb.png", "Ariyo/Craftable_item/Bomb.png",false) {
        @Override
        public boolean checkLevel() {
            return Main.getClient().getPlayer().getLevelMining() >= 2;
        }
    },
    MegaBomb("Mega Bomb", 50, Map.of("Gold Ore", 4, "Coal", 1),
        "Mohamadreza/CraftIcon/Mega_Bomb.png", "Ariyo/Craftable_item/Mega_Bomb.png",false) {
        @Override
        public boolean checkLevel() {
            return Main.getClient().getPlayer().getLevelMining() >= 3;
        }
    },
    Sprinkler("Sprinkler", 0, Map.of("Copper Bar", 1, "Iron Bar", 1),
        "Mohamadreza/CraftIcon/Sprinkler.png", "Ariyo/Craftable_item/Sprinkler.png",false) {
        @Override
        public boolean checkLevel() {
            return Main.getClient().getPlayer().getLevelFarming() >= 1;
        }
    },
    QualitySprinkler("Quality Sprinkler", 0, Map.of("Iron Bar", 1, "Gold Bar", 1),
        "Mohamadreza/CraftIcon/Quality_Sprinkler.png", "Ariyo/Craftable_item/Quality_Sprinkler.png",false) {
        @Override
        public boolean checkLevel() {
            return Main.getClient().getPlayer().getLevelFarming() >= 2;
        }
    },
    IridiumSprinkler("Iridium Sprinkler", 0, Map.of("Gold Bar", 1, "Iridium Bar", 1),
        "Mohamadreza/CraftIcon/Iridium_Sprinkler.png", "Ariyo/Craftable_item/Iridium_Sprinkler.png",false) {
        @Override
        public boolean checkLevel() {
            return Main.getClient().getPlayer().getLevelFarming() >= 3;
        }
    },
    CharcoalKlin("Charcoal Klin", 0, Map.of("Wood", 20, "Copper Bar", 2),
        "Mohamadreza/CraftIcon/Charcoal_Kiln.png", "Ariyo/Craftable_item/Charcoal_Kiln.png",true) {
        @Override
        public boolean checkLevel() {
            return Main.getClient().getPlayer().getLevelForaging() >= 1;
        }
    },
    Furnace("Furnace", 0, Map.of("Copper Ore", 20, "Stone", 25),
        "Mohamadreza/CraftIcon/Furnace.png", "Ariyo/Craftable_item/Furnace.png",true) {
        @Override
        public boolean checkLevel() {
            return true;
        }
    },
    Scarecrow("Scarecrow", 0, Map.of("Wood", 50, "Coal", 1, "Fiber", 20),
        "Mohamadreza/CraftIcon/Scarecrow.png", "Ariyo/Craftable_item/Scarecrow.png",false) {
        @Override
        public boolean checkLevel() {
            return true;
        }
    },
    DeluxeScarecrow("Deluxe Scarecrow", 0, Map.of("Wood", 50, "Coal", 1, "Fiber", 20, "Iridium Ore", 1),
        "Mohamadreza/CraftIcon/Deluxe_Scarecrow.png", "Ariyo/Craftable_item/Deluxe_Scarecrow.png",false) {
        @Override
        public boolean checkLevel() {
            return Main.getClient().getPlayer().getLevelFarming() >= 2;
        }
    },
    BeeHouse("Bee House", 0, Map.of("Wood", 40, "Coal", 8, "Iron Bar", 1),
        "Mohamadreza/CraftIcon/Bee_House.png", "Ariyo/Craftable_item/Bee_House.png",true) {
        @Override
        public boolean checkLevel() {
            return Main.getClient().getPlayer().getLevelFarming() >= 1;
        }
    },
    ChessPress("Chess Press", 0, Map.of("Wood", 45, "Stone", 45, "Copper Bar", 1),
        "Mohamadreza/CraftIcon/Cheese_Press.png", "Ariyo/Craftable_item/Cheese_Press.png",true) {
        @Override
        public boolean checkLevel() {
            return Main.getClient().getPlayer().getLevelFarming() >= 2;
        }
    },
    Keg("Keg", 0, Map.of("Wood", 30, "Copper Bar", 1, "Iron Bar", 1),
        "Mohamadreza/CraftIcon/Keg.png", "Ariyo/Craftable_item/Keg.png",true) {
        @Override
        public boolean checkLevel() {
            return Main.getClient().getPlayer().getLevelFarming() >= 3;
        }
    },
    Loom("Loom", 0, Map.of("Wood", 60, "Fiber", 30),
        "Mohamadreza/CraftIcon/Loom.png", "Ariyo/Craftable_item/Loom.png",true) {
        @Override
        public boolean checkLevel() {
            return Main.getClient().getPlayer().getLevelFarming() >= 3;
        }
    },
    MayonnaiseMachine("Mayonnaise Machine", 0, Map.of("Wood", 15, "Stone", 15, "Copper Bar", 1),
        "Mohamadreza/CraftIcon/Mayonnaise_Machine.png", "Ariyo/Craftable_item/Mayonnaise_Machine.png",true) {
        @Override
        public boolean checkLevel() {
            return true;
        }
    },
    OilMaker("Oil Maker", 0, Map.of("Wood", 100, "Gold Bar", 1, "Iron Bar", 1),
        "Mohamadreza/CraftIcon/Oil_Maker.png", "Ariyo/Craftable_item/Oil_Maker.png",true) {
        @Override
        public boolean checkLevel() {
            return Main.getClient().getPlayer().getLevelFarming() >= 3;
        }
    },
    PreservesJar("Preserves Jar", 0, Map.of("Wood", 50, "Stone", 40, "Coal", 8),
        "Mohamadreza/CraftIcon/Preserves_Jar.png", "Ariyo/Craftable_item/Preserves_Jar.png",true) {
        @Override
        public boolean checkLevel() {
            return Main.getClient().getPlayer().getLevelFarming() >= 2;
        }
    },
    MysticTreeSeed("Mystic Tree Seed", 100, Map.of("Acorns", 5, "Maple Seeds", 5, "Pine Cones", 5, "Mahogany Seeds", 5),
        "Mohamadreza/CraftIcon/Mystic_Tree_Seed.png", "Ariyo/Craftable_item/Ancient_Seeds.png",true) {
        @Override
        public boolean checkLevel() {
            return Main.getClient().getPlayer().getLevelForaging() >= 4;
        }
    },
    Dehydrator("Dehydrator", 0, Map.of("Wood", 30, "Stone", 20, "Fiber", 30),
        "Mohamadreza/Dehydrator.png", "Ariyo/Craftable_item/Dehydrator.png",true) {
        @Override
        public boolean checkLevel() {
            Inventory inventory = Main.getClient().getPlayer().getBackPack().inventory;
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
    GrassStarter("Grass Starter", 0, Map.of("Wood", 1, "Fiber", 1),
        "Mohamadreza/Grass_Starter.png", "Ariyo/Craftable_item/Spring_Seeds.png",true) {
        @Override
        public boolean checkLevel() {
            Inventory inventory = Main.getClient().getPlayer().getBackPack().inventory;
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
    FishSmoker("Fish Smoker", 0, Map.of("Wood", 50, "Iron Bar", 3, "Coal", 10),
        "Mohamadreza/Fish_Smoker.png", "Ariyo/Craftable_item/Fish_smoker.png",true) {
        @Override
        public boolean checkLevel() {
            Inventory inventory = Main.getClient().getPlayer().getBackPack().inventory;
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
    private final String address;
    private final boolean canProduct;

    CraftType(String name, int sellPrice, Map map , String icon, String address , boolean canProduct) {
        this.name = name;
        this.sellPrice = sellPrice;
        this.ingrediants=map;
        this.icon = icon;
        this.address = address;
        this.canProduct=canProduct;
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
    public String getAddress() {
        return address;
    }
    public String getIcon() {
        return icon;
    }

    public boolean isCanProduct() {
        return canProduct;
    }
}
