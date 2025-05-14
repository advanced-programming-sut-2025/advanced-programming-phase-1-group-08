package model.Enum.ItemType;

import model.*;
import model.Animall.Animalproduct;
import model.Animall.Fish;
import model.Enum.AllPlants.CropsType;
import model.Enum.AllPlants.ForagingMineralsType;
import model.Enum.AllPlants.ForagingSeedsType;
import model.Enum.AllPlants.TreesProductType;
import model.MapThings.Wood;
import model.OtherItem.ArtisanProduct;
import model.OtherItem.BarsAndOres;
import model.OtherItem.CraftingItem;
import model.OtherItem.MarketItem;
import model.Plants.*;

import java.util.HashMap;
import java.util.Map;

public enum ArtisanType {
    Honey("Honey" , CraftType.BeeHouse) {
        @Override
        public int getEnergy(String name) {
            return 75;
        }

        @Override
        public void creatArtesian(String name, CraftingItem craftingItem) {
            ArtisanProduct artisanProduct=new ArtisanProduct(Honey);
            HashMap<DateHour, Integer> x=new HashMap<>();
            x.put(App.currentGame.currentDate.clone() , 96);
            craftingItem.getBuffer().put(artisanProduct , x);
        }

        @Override
        public boolean checkIngredient(String first, String second) {
            return true;
        }
    },
    Cheese("Cheese" , CraftType.ChessPress) {
        @Override
        public int getEnergy(String name) {
            return 100;
        }

        @Override
        public void creatArtesian(String name, CraftingItem craftingItem) {
            ArtisanProduct cheese=new ArtisanProduct(Cheese);
            HashMap<DateHour, Integer> x=new HashMap<>();
            x.put(App.currentGame.currentDate.clone() , 3);
            craftingItem.getBuffer().put(cheese , x);
        }

        @Override
        public boolean checkIngredient(String first, String second) {
            String newName= first.replace('_',' ');
            if (! newName.equals("Milk") && ! newName.equals("Large Milk")) {
                return false;
            }
            Inventory inventory=App.currentGame.currentPlayer.getBackPack().inventory;
            for (Map.Entry <Items , Integer> entry : inventory.Items.entrySet()) {
                if (entry.getKey() instanceof Animalproduct) {
                    if (((Animalproduct) entry.getKey()).getAnimalProductType().getName().equals(newName)) {
                        inventory.Items.remove(entry.getKey());
                        return true;
                    }
                }
            }
            return false;
        }
    },

    Goat_Cheese("Goat Cheese" , CraftType.ChessPress) {
        @Override
        public int getEnergy(String name) {
            return 100;
        }

        @Override
        public void creatArtesian(String name, CraftingItem craftingItem) {
            ArtisanProduct GoatCheese=new ArtisanProduct(Goat_Cheese);
            HashMap<DateHour, Integer> x=new HashMap<>();
            x.put(App.currentGame.currentDate.clone() , 3);
            craftingItem.getBuffer().put(GoatCheese , x);
        }

        @Override
        public boolean checkIngredient(String first, String second) {
            String newName= first.replace('_',' ');
            if (! newName.equals("Goat Milk") && ! newName.equals("Large Goat Milk")) {
                return false;
            }
            Inventory inventory=App.currentGame.currentPlayer.getBackPack().inventory;

            for (Map.Entry <Items , Integer> entry : inventory.Items.entrySet()) {
                if (entry.getKey() instanceof Animalproduct) {
                    if (((Animalproduct) entry.getKey()).getAnimalProductType().getName().equals(newName)) {
                        inventory.Items.remove(entry.getKey());
                        return true;
                    }
                }
            }

            return false;
        }
    },

    Beer("Beer" , CraftType.Keg) {
        @Override
        public int getEnergy(String name) {
            return 50;
        }

        @Override
        public void creatArtesian(String name, CraftingItem craftingItem) {
            MarketItem beer=new MarketItem(MarketItemType.Beer);
            HashMap<DateHour, Integer> x=new HashMap<>();
            x.put(App.currentGame.currentDate.clone() , 24);
            craftingItem.getBuffer().put(beer , x);
        }

        @Override
        public boolean checkIngredient(String first, String second) {
            if (! first.equals("Wheat") ) {
                return false;
            }
            Inventory inventory=App.currentGame.currentPlayer.getBackPack().inventory;
            for (Map.Entry <Items , Integer> entry : inventory.Items.entrySet()) {
                if (entry.getKey() instanceof AllCrops) {
                    if (((AllCrops) entry.getKey()).getType().equals(CropsType.Wheat)) {
                        inventory.Items.put(entry.getKey() , entry.getValue()-1);
                        if (entry.getValue()==0) {
                            inventory.Items.remove(entry.getKey());
                        }
                        return true;
                    }
                }
            }
            return false;
        }
    },

    Vinegar("Vinegar" , CraftType.Keg) {
        @Override
        public int getEnergy(String name) {
            return 13;
        }


        @Override
        public void creatArtesian(String name, CraftingItem craftingItem) {
            MarketItem vinegar=new MarketItem(MarketItemType.Vinegar);
            HashMap<DateHour, Integer> x=new HashMap<>();
            x.put(App.currentGame.currentDate.clone() , 10);
            craftingItem.getBuffer().put(vinegar , x);
        }

        @Override
        public boolean checkIngredient(String first, String second) {
            if (! first.equals("Rice") ) {
                return false;
            }
            Inventory inventory=App.currentGame.currentPlayer.getBackPack().inventory;
            for (Map.Entry <Items , Integer> entry : inventory.Items.entrySet()) {
                if (entry.getKey() instanceof MarketItem) {
                    if (((MarketItem) entry.getKey()).getType().equals(MarketItemType.Rice)) {
                        inventory.Items.put(entry.getKey() , entry.getValue()-1);
                        if (entry.getValue()==0) {
                            inventory.Items.remove(entry.getKey());
                        }
                        return true;
                    }
                }
            }
            return false;
        }
    },

    Coffee("Coffee" , CraftType.Keg) {
        @Override
        public int getEnergy(String name) {
            return 75;
        }


        @Override
        public void creatArtesian(String name, CraftingItem craftingItem) {
            MarketItem coffee=new MarketItem(MarketItemType.Coffee);
            HashMap<DateHour, Integer> x=new HashMap<>();
            x.put(App.currentGame.currentDate.clone() , 2);
            craftingItem.getBuffer().put(coffee , x);
        }

        @Override
        public boolean checkIngredient(String first, String second) {
            if (! first.equals("Coffee_Bean") ) {
                return false;
            }
            Inventory inventory=App.currentGame.currentPlayer.getBackPack().inventory;
            for (Map.Entry <Items , Integer> entry : inventory.Items.entrySet()) {
                if (entry.getKey() instanceof AllCrops) {
                    if (((AllCrops) entry.getKey()).getType().equals(CropsType.CoffeeBean) && entry.getValue() >= 5) {
                        inventory.Items.put(entry.getKey() , entry.getValue()-5);
                        if (entry.getValue()==0) {
                            inventory.Items.remove(entry.getKey());
                        }
                        return true;
                    }
                }
            }
            return false;
        }
    },

    Juice("Juice" , CraftType.Keg) {
        @Override
        public int getEnergy(String name) {
            CropsType cropsType = CropsType.valueOf(name);
            return 2 * cropsType.getEnergy();
        }

        @Override
        public void creatArtesian(String name, CraftingItem craftingItem) {
            ArtisanProduct juice=new ArtisanProduct(Juice);
            HashMap<DateHour, Integer> x=new HashMap<>();
            x.put(App.currentGame.currentDate.clone() , 96);
            craftingItem.getBuffer().put(juice , x);
        }

        @Override
        public boolean checkIngredient(String first, String second) {
            String newName=name().replace('_',' ' );
            Inventory inventory=App.currentGame.currentPlayer.getBackPack().inventory;
            for (Map.Entry <Items , Integer> entry : inventory.Items.entrySet()) {
                if (entry.getKey() instanceof AllCrops) {

                    if (((AllCrops) entry.getKey()).getType().getDisplayName().equals(newName)) {

                        if (((AllCrops) entry.getKey()).getType().isVegetable()) {

                            inventory.Items.put(entry.getKey() , entry.getValue()-1);
                            if (entry.getValue()==0) {
                                inventory.Items.remove(entry.getKey());
                            }
                            return true;
                        }

                        return false;
                    }
                }
            }
            return false;
        }
    },

    Mead("Mead",CraftType.Keg) {
        @Override
        public int getEnergy(String name) {
            return 100;
        }


        @Override
        public void creatArtesian(String name, CraftingItem craftingItem) {
            ArtisanProduct mead=new ArtisanProduct(Mead);
            HashMap<DateHour, Integer> x=new HashMap<>();
            x.put(App.currentGame.currentDate.clone() , 10);
            craftingItem.getBuffer().put(mead , x);
        }

        @Override
        public boolean checkIngredient(String first, String second) {
            if (! first.equals("Honey") ) {
                return false;
            }
            Inventory inventory=App.currentGame.currentPlayer.getBackPack().inventory;
            for (Map.Entry <Items , Integer> entry : inventory.Items.entrySet()) {
                if (entry.getKey() instanceof ArtisanProduct) {
                    if (((ArtisanProduct) entry.getKey()).getType().equals(Honey)) {
                        inventory.Items.put(entry.getKey() , entry.getValue()-1);
                        if (entry.getValue()==0) {
                            inventory.Items.remove(entry.getKey());
                        }
                        return true;
                    }
                }
            }
            return false;
        }
    },

    Pale_Ale("Pale Ale",CraftType.Keg) {
        @Override
        public int getEnergy(String name) {
            return 50;
        }

        @Override
        public void creatArtesian(String name, CraftingItem craftingItem) {
            ArtisanProduct pale=new ArtisanProduct(Pale_Ale);
            HashMap<DateHour, Integer> x=new HashMap<>();
            x.put(App.currentGame.currentDate.clone() , 72);
            craftingItem.getBuffer().put(pale , x);
        }

        @Override
        public boolean checkIngredient(String first, String second) {
            if (! first.equals("Hops")) {
                return false;
            }
            Inventory inventory=App.currentGame.currentPlayer.getBackPack().inventory;
            for (Map.Entry <Items , Integer> entry : inventory.Items.entrySet()) {
                if (entry.getKey() instanceof AllCrops) {
                    if (((AllCrops) entry.getKey()).getType().equals(CropsType.Hops)) {
                        inventory.Items.put(entry.getKey() , entry.getValue()-1);
                        if (entry.getValue()==0) {
                            inventory.Items.remove(entry.getKey());
                        }
                        return true;
                    }
                }
            }
            return false;
        }
    },

    Wine("Wine" , CraftType.Keg) {
        @Override
        public int getEnergy(String name) {
            TreesProductType type = TreesProductType.valueOf(name);
            return (7 * type.getEnergy() ) / 4;
        }

        @Override
        public void creatArtesian(String name, CraftingItem craftingItem) {
            ArtisanProduct wine=new ArtisanProduct(Wine);
            HashMap<DateHour, Integer> x=new HashMap<>();
            x.put(App.currentGame.currentDate.clone() , 168);
            craftingItem.getBuffer().put(wine , x);
        }

        @Override
        public boolean checkIngredient(String first, String second) {
            String newName= first.replace('_',' ' );
            Inventory inventory=App.currentGame.currentPlayer.getBackPack().inventory;
            for (Map.Entry <Items , Integer> entry : inventory.Items.entrySet()) {
                if (entry.getKey() instanceof TreesProdct) {
                    if (((TreesProdct) entry.getKey()).getType().getDisplayName().equals(newName)) {
                        inventory.Items.put(entry.getKey() , entry.getValue()-1);
                        if (entry.getValue()==0) {
                            inventory.Items.remove(entry.getKey());
                        }
                        return true;
                    }
                }
            }
            return false;
        }
    },

    Dried_Mushrooms("Dried Mushrooms" , CraftType.Dehydrator) {
        @Override
        public int getEnergy(String name) {
            return 50;
        }

        @Override
        public void creatArtesian(String name, CraftingItem craftingItem) {
            ArtisanProduct DriedMushrooms=new ArtisanProduct(Dried_Mushrooms);
            HashMap<DateHour, Integer> x=new HashMap<>();
            x.put(App.currentGame.currentDate.clone() , 12);
            craftingItem.getBuffer().put(DriedMushrooms , x);
        }

        @Override
        public boolean checkIngredient(String first, String second) {
            String newName= first.replace('_',' ' );
            Inventory inventory=App.currentGame.currentPlayer.getBackPack().inventory;
            for (Map.Entry <Items , Integer> entry : inventory.Items.entrySet()) {

                if (entry.getKey() instanceof ForagingCrops) {

                    if (((ForagingCrops) entry.getKey()).getType().getDisplayName().equals(newName)) {

                        if (entry.getValue() >= 5 && ((ForagingCrops) entry.getKey()).getType().isMushroom()) {
                            inventory.Items.put(entry.getKey() , entry.getValue()-5);
                            if (entry.getValue()==0) {
                                inventory.Items.remove(entry.getKey());
                            }
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    },

    Dried_Fruit("Dried Fruit" , CraftType.Dehydrator) {
        @Override
        public int getEnergy(String name) {
            return 75;
        }

        @Override
        public void creatArtesian(String name, CraftingItem craftingItem) {
            ArtisanProduct DriedFruit=new ArtisanProduct(Dried_Fruit);
            HashMap<DateHour, Integer> x=new HashMap<>();
            x.put(App.currentGame.currentDate.clone() , 12);
            craftingItem.getBuffer().put(DriedFruit , x);
        }

        @Override
        public boolean checkIngredient(String first, String second) {
            String newName= first.replace('_',' ' );
            Inventory inventory=App.currentGame.currentPlayer.getBackPack().inventory;
            for (Map.Entry <Items , Integer> entry : inventory.Items.entrySet()) {
                if (entry.getKey() instanceof TreesProdct) {
                    if (((TreesProdct) entry.getKey()).getType().getDisplayName().equals(newName)) {
                        if (entry.getValue() >= 5 ) {
                            inventory.Items.put(entry.getKey() , entry.getValue()-5);
                            if (entry.getValue()==0) {
                                inventory.Items.remove(entry.getKey());
                            }
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    },

    Raisins("Raisins" , CraftType.Dehydrator) {
        @Override
        public int getEnergy(String name) {
            return 125;
        }

        @Override
        public void creatArtesian(String name, CraftingItem craftingItem) {
            ArtisanProduct raisins=new ArtisanProduct(Raisins);
            HashMap<DateHour, Integer> x=new HashMap<>();
            x.put(App.currentGame.currentDate.clone() , 12);
            craftingItem.getBuffer().put(raisins , x);
        }

        @Override
        public boolean checkIngredient(String first, String second) {
            if (! first.equals("Grapes")) {
                return false;
            }
            Inventory inventory=App.currentGame.currentPlayer.getBackPack().inventory;
            for (Map.Entry <Items , Integer> entry : inventory.Items.entrySet()) {
                if (entry.getKey() instanceof AllCrops) {
                    if (((AllCrops) entry.getKey()).getType().equals(CropsType.Grape)) {
                        if (entry.getValue() >= 5 ) {
                            inventory.Items.put(entry.getKey() , entry.getValue()-5);
                            if (entry.getValue()==0) {
                                inventory.Items.remove(entry.getKey());
                            }
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    },

    Coal("Coal",CraftType.CharcoalKlin) {
        @Override
        public int getEnergy(String name) {
            return 0;
        }

        @Override
        public void creatArtesian(String name, CraftingItem craftingItem) {
            ForagingMinerals coal = new ForagingMinerals(ForagingMineralsType.COAL);
            HashMap<DateHour, Integer> x=new HashMap<>();
            x.put(App.currentGame.currentDate.clone() , 1);
            craftingItem.getBuffer().put(coal , x);
        }

        @Override
        public boolean checkIngredient(String first, String second) {
            if (! first.equals("Wood")) {
                return false;
            }
            Inventory inventory=App.currentGame.currentPlayer.getBackPack().inventory;
            for (Map.Entry <Items , Integer> entry : inventory.Items.entrySet()) {
                if (entry.getKey() instanceof Wood) {
                    if (entry.getValue() >= 10 ) {
                        inventory.Items.put(entry.getKey() , entry.getValue()-10);
                        if (entry.getValue()==0) {
                            inventory.Items.remove(entry.getKey());
                        }
                        return true;
                    }
                }
            }
            return false;
        }
    },

    Cloth("Cloth",CraftType.Loom) {
        @Override
        public int getEnergy(String name) {
            return 0;
        }

        @Override
        public void creatArtesian(String name, CraftingItem craftingItem) {
            ArtisanProduct cloth=new ArtisanProduct(Cloth);
            HashMap<DateHour, Integer> x=new HashMap<>();
            x.put(App.currentGame.currentDate.clone() , 4);
            craftingItem.getBuffer().put(cloth , x);
        }

        @Override
        public boolean checkIngredient(String first, String second) {
            if (! first.equals("Wool")) {
                return false;
            }
            Inventory inventory=App.currentGame.currentPlayer.getBackPack().inventory;
            for (Map.Entry <Items , Integer> entry : inventory.Items.entrySet()) {
                if (entry.getKey() instanceof Animalproduct) {
                    if (((Animalproduct) entry.getKey()).getAnimalProductType().equals(AnimalProductType.rabbits_Wool)) {
                        inventory.Items.put(entry.getKey() , entry.getValue()-1);
                        if (entry.getValue()==0) {
                            inventory.Items.remove(entry.getKey());
                        }
                        return true;
                    }
                    if (((Animalproduct) entry.getKey()).getAnimalProductType().equals(AnimalProductType.sheeps_Wool)) {
                        inventory.Items.put(entry.getKey() , entry.getValue()-1);
                        if (entry.getValue()==0) {
                            inventory.Items.remove(entry.getKey());
                        }
                        return true;
                    }
                }
            }
            return false;
        }
    },

    Mayonnaise("Mayonnaise" , CraftType.MayonnaiseMachine) {
        @Override
        public int getEnergy(String name) {
            return 50;
        }

        @Override
        public void creatArtesian(String name, CraftingItem craftingItem) {
            ArtisanProduct mayonnaise=new ArtisanProduct(Mayonnaise);
            HashMap<DateHour, Integer> x=new HashMap<>();
            x.put(App.currentGame.currentDate.clone() , 3);
            craftingItem.getBuffer().put(mayonnaise , x);
        }

        @Override
        public boolean checkIngredient(String first, String second) {
            String newName= first.replace('_' , ' ');
            if (! newName.equals("Egg") && ! newName.equals("Large Egg")) {
                return false;
            }
            Inventory inventory=App.currentGame.currentPlayer.getBackPack().inventory;
            for (Map.Entry <Items , Integer> entry : inventory.Items.entrySet()) {
                if (entry.getKey() instanceof Animalproduct) {
                    if (((Animalproduct) entry.getKey()).getAnimalProductType().getName().equals(newName)) {
                        inventory.Items.put(entry.getKey() , entry.getValue()-1);
                        if (entry.getValue()==0) {
                            inventory.Items.remove(entry.getKey());
                        }
                        return true;
                    }
                }
            }
            return false;
        }
    },

    Duck_Mayonnaise("Duck Mayonnaise" , CraftType.MayonnaiseMachine) {
        @Override
        public int getEnergy(String name) {
            return 75;
        }

        @Override
        public void creatArtesian(String name, CraftingItem craftingItem) {
            ArtisanProduct DuckMayonnaise=new ArtisanProduct(Duck_Mayonnaise);
            HashMap<DateHour, Integer> x=new HashMap<>();
            x.put(App.currentGame.currentDate.clone() , 3);
            craftingItem.getBuffer().put(DuckMayonnaise , x);
        }

        @Override
        public boolean checkIngredient(String first, String second) {
            String newName= first.replace('_' , ' ');
            if (! newName.equals("Duck Egg") ) {
                return false;
            }
            Inventory inventory=App.currentGame.currentPlayer.getBackPack().inventory;
            for (Map.Entry <Items , Integer> entry : inventory.Items.entrySet()) {
                if (entry.getKey() instanceof Animalproduct) {
                    if (((Animalproduct) entry.getKey()).getAnimalProductType().equals(AnimalProductType.duckEgg)) {
                        inventory.Items.put(entry.getKey() , entry.getValue()-1);
                        if (entry.getValue()==0) {
                            inventory.Items.remove(entry.getKey());
                        }
                        return true;
                    }
                }
            }
            return false;
        }
    },

    Dinosaur_Mayonnaise("Dinosaur Mayonnaise" , CraftType.MayonnaiseMachine) {
        @Override
        public int getEnergy(String name) {
            return 125;
        }

        @Override
        public void creatArtesian(String name, CraftingItem craftingItem) {
            ArtisanProduct DinosaurMayonnaise=new ArtisanProduct(Dinosaur_Mayonnaise);
            HashMap<DateHour, Integer> x=new HashMap<>();
            x.put(App.currentGame.currentDate.clone() , 3);
            craftingItem.getBuffer().put(DinosaurMayonnaise , x);
        }

        @Override
        public boolean checkIngredient(String first, String second) {
            String newName= first.replace('_' , ' ');
            if (! newName.equals(AnimalProductType.dinosaurEgg.getName()) ) {
                return false;
            }
            Inventory inventory=App.currentGame.currentPlayer.getBackPack().inventory;
            for (Map.Entry <Items , Integer> entry : inventory.Items.entrySet()) {
                if (entry.getKey() instanceof Animalproduct) {
                    if (((Animalproduct) entry.getKey()).getAnimalProductType().equals(AnimalProductType.dinosaurEgg)) {
                        inventory.Items.put(entry.getKey() , entry.getValue()-1);
                        if (entry.getValue()==0) {
                            inventory.Items.remove(entry.getKey());
                        }
                        return true;
                    }
                }
            }
            return false;
        }
    },

    Truffle_Oil("Truffle Oil" , CraftType.OilMaker) {
        @Override
        public int getEnergy(String name) {
            return 6;
        }

        @Override
        public void creatArtesian(String name, CraftingItem craftingItem) {
            ArtisanProduct TruffleOil=new ArtisanProduct(Truffle_Oil);
            HashMap<DateHour, Integer> x=new HashMap<>();
            x.put(App.currentGame.currentDate.clone() , 6);
            craftingItem.getBuffer().put(TruffleOil , x);
        }

        @Override
        public boolean checkIngredient(String first, String second) {
            if (! first.equals(AnimalProductType.Truffle.getName())) {
                return false;
            }
            Inventory inventory=App.currentGame.currentPlayer.getBackPack().inventory;
            for (Map.Entry <Items , Integer> entry : inventory.Items.entrySet()) {
                if (entry.getKey() instanceof Animalproduct) {
                    if (((Animalproduct) entry.getKey()).getAnimalProductType().equals(AnimalProductType.Truffle)) {
                        inventory.Items.put(entry.getKey() , entry.getValue()-1);
                        if (entry.getValue()==0) {
                            inventory.Items.remove(entry.getKey());
                        }
                        return true;
                    }
                }
            }
            return false;
        }
    },

    Oil("Oil", CraftType.OilMaker) {
        @Override
        public int getEnergy(String name) {
            return 13;
        }

        @Override
        public void creatArtesian(String name, CraftingItem craftingItem) {
            MarketItem oil=new MarketItem(MarketItemType.Oil);
            HashMap<DateHour, Integer> x=new HashMap<>();
            x.put(App.currentGame.currentDate.clone() , 6);
            craftingItem.getBuffer().put(oil , x);
        }

        @Override
        public boolean checkIngredient(String first, String second) {
            String newName= first.replace('_' , ' ');
            if (! newName.equals("Corn") && ! newName.equals("Sunflower") && ! newName.equals("Sunflower Seeds") ) {
                return false;
            }
            Inventory inventory=App.currentGame.currentPlayer.getBackPack().inventory;
            for (Map.Entry <Items , Integer> entry : inventory.Items.entrySet()) {
                if (entry.getKey() instanceof AllCrops) {
                    if (((AllCrops) entry.getKey()).getType().equals(CropsType.Corn) || ((AllCrops) entry.getKey()).getType().equals(CropsType.Sunflower)) {
                        inventory.Items.put(entry.getKey() , entry.getValue()-1);
                        if (entry.getValue()==0) {
                            inventory.Items.remove(entry.getKey());
                        }
                        return true;
                    }
                }
                if (entry.getKey() instanceof ForagingSeeds) {
                    if (((ForagingSeeds) entry.getKey()).getType().equals(ForagingSeedsType.SunflowerSeeds)) {
                        inventory.Items.put(entry.getKey() , entry.getValue()-1);
                        if (entry.getValue()==0) {
                            inventory.Items.remove(entry.getKey());
                        }
                        return true;
                    }
                }
            }
            return false;
        }
    },

    Pickles("Pickles" , CraftType.PreservesJar) {
        @Override
        public int getEnergy(String name) {
            CropsType cropsType = CropsType.valueOf(name);
            return (7 * cropsType.getEnergy() ) / 4;
        }

        @Override
        public void creatArtesian(String name, CraftingItem craftingItem) {
            ArtisanProduct pickles=new ArtisanProduct(Pickles);
            HashMap<DateHour, Integer> x=new HashMap<>();
            x.put(App.currentGame.currentDate.clone() , 6);
            craftingItem.getBuffer().put(pickles , x);
        }

        @Override
        public boolean checkIngredient(String first, String second) {
            String newName= first.replace('_' , ' ');
            Inventory inventory=App.currentGame.currentPlayer.getBackPack().inventory;
            for (Map.Entry <Items , Integer> entry : inventory.Items.entrySet()) {
                if (entry.getKey() instanceof AllCrops) {

                    if (((AllCrops) entry.getKey()).getType().isVegetable()) {

                        if (((AllCrops) entry.getKey()).getType().getDisplayName().equals(newName)) {

                            inventory.Items.put(entry.getKey(), entry.getValue() - 1);
                            if (entry.getValue() == 0) {
                                inventory.Items.remove(entry.getKey());
                            }
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    },

    Jelly("Jelly" , CraftType.PreservesJar) {
        @Override
        public int getEnergy(String name) {
            TreesProductType type = TreesProductType.valueOf(name);
            return 2 * type.getEnergy();
        }

        @Override
        public void creatArtesian(String name, CraftingItem craftingItem) {
            ArtisanProduct jelly=new ArtisanProduct(Jelly);
            HashMap<DateHour, Integer> x=new HashMap<>();
            x.put(App.currentGame.currentDate.clone() , 72);
            craftingItem.getBuffer().put(jelly , x);
        }

        @Override
        public boolean checkIngredient(String first, String second) {
            String newName= first.replace('_' , ' ');
            Inventory inventory=App.currentGame.currentPlayer.getBackPack().inventory;
            for (Map.Entry <Items , Integer> entry : inventory.Items.entrySet()) {
                if (entry.getKey() instanceof TreesProdct) {

                    if (((TreesProdct) entry.getKey()).getType().getDisplayName().equals(newName)) {
                        inventory.Items.put(entry.getKey() , entry.getValue()-1);
                        if (entry.getValue()==0) {
                            inventory.Items.remove(entry.getKey());
                        }
                        return true;
                    }
                }
            }
            return false;
        }
    },
    Smoked_Fish("Smoked Fish" , CraftType.FishSmoker) {
        @Override
        public int getEnergy(String name) {
            return 15;
        }

        public void creatArtesian(String name, CraftingItem craftingItem) {
            ArtisanProduct smokedFish=new ArtisanProduct(Smoked_Fish);
            HashMap<DateHour, Integer> x=new HashMap<>();
            x.put(App.currentGame.currentDate.clone() , 1);
            craftingItem.getBuffer().put(smokedFish , x);
        }

        @Override
        public boolean checkIngredient(String first, String second) {
            if (first == null || second == null) {
                return false;
            }
            String newFirstName= first.replace('_' , ' ');
            String newSecondName= second.replace('_' , ' ');
            Fish fish=null;
            ForagingMinerals coal=null;

            if ( ! newSecondName.equals(ForagingMineralsType.COAL.getDisplayName())) {
                return false;
            }

            Inventory inventory=App.currentGame.currentPlayer.getBackPack().inventory;
            for (Map.Entry <Items , Integer> entry : inventory.Items.entrySet()) {
                if (entry.getKey() instanceof Fish && fish==null) {
                    if (((Fish) entry.getKey()).getFishType().getName().equals(newFirstName)) {
                        fish = (Fish) entry.getKey();
                    }
                }
                if (entry.getKey() instanceof ForagingMinerals && coal==null) {
                    if (((ForagingMinerals) entry.getKey()).getType().equals(ForagingMineralsType.COAL)) {
                        coal = (ForagingMinerals) entry.getKey();
                    }
                }
            }
            if (coal==null || fish==null) {
                return false;
            }
            inventory.Items.compute(fish , (k,v) -> v-1);
            if (inventory.Items.get(fish)==0) {
                inventory.Items.remove(fish);
            }
            inventory.Items.compute(coal , (k,v) -> v-1);
            if (inventory.Items.get(coal)==0) {
                inventory.Items.remove(coal);
            }
            return true;
        }
    },

    AnyMetalBar("Any_Metal_Bar" , CraftType.Furnace) {
        @Override
        public int getEnergy(String name) {
            return 0;
        }

        @Override
        public void creatArtesian(String name, CraftingItem craftingItem) {
            if (name.equals("Copper_Ore")) {
                BarsAndOres copperBar=new BarsAndOres(BarsAndOreType.CopperBar);
                HashMap<DateHour, Integer> x=new HashMap<>();
                x.put(App.currentGame.currentDate.clone() , 4);
                craftingItem.getBuffer().put(copperBar , x);
            }
            if (name.equals("Iron_Ore")) {
                BarsAndOres ironBar =new BarsAndOres(BarsAndOreType.IronBar);
                HashMap<DateHour, Integer> x=new HashMap<>();
                x.put(App.currentGame.currentDate.clone() , 4);
                craftingItem.getBuffer().put(ironBar , x);
            }
            if (name.equals("Gold_Ore")) {
                BarsAndOres goldBar=new BarsAndOres(BarsAndOreType.GoldBar);
                HashMap<DateHour, Integer> x=new HashMap<>();
                x.put(App.currentGame.currentDate.clone() , 4);
                craftingItem.getBuffer().put(goldBar , x);
            }
            if (name.equals("Iridium_Ore")) {
                BarsAndOres iridiumBar=new BarsAndOres(BarsAndOreType.IridiumBar);
                HashMap<DateHour, Integer> x=new HashMap<>();
                x.put(App.currentGame.currentDate.clone() , 4);
                craftingItem.getBuffer().put(iridiumBar , x);
            }
        }

        @Override
        public boolean checkIngredient(String first, String second) {
            String newFirst=first.replace('_' , ' ');
            String newSecond=second.replace('_' , ' ');

            if (! newSecond.equals(ForagingMineralsType.COAL.getDisplayName())) {
                return false;
            }
            Inventory inventory=App.currentGame.currentPlayer.getBackPack().inventory;
            for (Map.Entry <Items , Integer> entry : inventory.Items.entrySet()) {
                if (entry.getKey() instanceof BarsAndOres) {

                    if (((BarsAndOres) entry.getKey()).getType().getName().equals(newFirst)) {

                        if (entry.getValue() >= 5) {
                            inventory.Items.put(entry.getKey() , entry.getValue() - 5);
                            if (entry.getValue() == 0) {
                                inventory.Items.remove(entry.getKey());
                            }
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    };

    private final String name;
    private final CraftType craftType;
    public abstract int getEnergy(String name);
    public abstract void creatArtesian(String name , CraftingItem craftingItem);
    public abstract boolean checkIngredient(String first , String second);

    ArtisanType(String name, CraftType craftType) {
        this.name = name;
        this.craftType = craftType;
    }

    public String getName() {
        return name;
    }

    public CraftType getCraftType() {
        return craftType;
    }
}
