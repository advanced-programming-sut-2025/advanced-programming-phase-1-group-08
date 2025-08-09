package com.Graphic.model.Enum.ItemType;

import com.Graphic.Main;
import com.Graphic.model.*;
import com.Graphic.model.HelpersClass.Result;
import com.Graphic.model.Plants.Fish;
import com.Graphic.model.Enum.AllPlants.CropsType;
import com.Graphic.model.Enum.AllPlants.ForagingMineralsType;
import com.Graphic.model.Enum.AllPlants.ForagingSeedsType;
import com.Graphic.model.Enum.AllPlants.TreesProductType;
import com.Graphic.model.Plants.Wood;
import com.Graphic.model.OtherItem.ArtisanProduct;
import com.Graphic.model.OtherItem.BarsAndOres;
import com.Graphic.model.ToolsPackage.CraftingItem;
import com.Graphic.model.Places.MarketItem;
import com.Graphic.model.Plants.*;

import java.util.HashMap;
import java.util.Map;

import static com.Graphic.model.App.currentGame;

public enum ArtisanType {

    Honey       ("Honey" , CraftType.BeeHouse , 350 , 96) {
        @Override
        public int getEnergy(String name) {
            return 75;
        }

        @Override
        public void creatArtesian(String name, CraftingItem craftingItem) {
            ArtisanProduct artisanProduct=new ArtisanProduct(Honey);
            craftingItem.getItems().add(artisanProduct);
            craftingItem.getDateHours().add(Main.getClient().getLocalGameState().currentDate.clone());
        }

        @Override
        public Result checkIngredient(HashMap<Items,Integer> ingredient) {
            return new Result(true , "you create Honey Successfully");
        }
    },
    Cheese      ("Cheese" , CraftType.ChessPress , 345 , 3) {
        @Override
        public int getEnergy(String name) {
            return 100;
        }

        @Override
        public void creatArtesian(String name, CraftingItem craftingItem) {
            ArtisanProduct cheese=new ArtisanProduct(Cheese);
            craftingItem.getItems().add(cheese);
            craftingItem.getDateHours().add(Main.getClient().getLocalGameState().currentDate.clone());
        }

        @Override
        public Result checkIngredient(HashMap<Items,Integer> ingredient) {

            for (Map.Entry<Items,Integer> entry : ingredient.entrySet()) {
                if (entry.getKey() instanceof Animalproduct) {
                    if (((Animalproduct) entry.getKey()).getType().equals(AnimalProductType.milk)
                        || ((Animalproduct) entry.getKey()).getType().equals(AnimalProductType.bigMilk)) {

                        Main.getClient().getPlayer().getBackPack().inventory.Items.remove(entry.getKey());
                        return new Result(true , "you create Cheese Successfully");
                    }
                }
            }
            return new Result(false , "you didn't choose suitable ingredients for Cheese");
        }
    },
    Goat_Cheese ("Goat Cheese" , CraftType.ChessPress , 600 , 3) {
        @Override
        public int getEnergy(String name) {
            return 100;
        }

        @Override
        public void creatArtesian(String name, CraftingItem craftingItem) {
            ArtisanProduct GoatCheese=new ArtisanProduct(Goat_Cheese);
            craftingItem.getItems().add(GoatCheese);
            craftingItem.getDateHours().add(Main.getClient().getLocalGameState().currentDate.clone());
        }

        @Override
        public Result checkIngredient(HashMap<Items,Integer> ingredient) {

            for (Map.Entry<Items,Integer> entry : ingredient.entrySet()) {
                if (entry.getKey() instanceof Animalproduct) {
                    if (((Animalproduct) entry.getKey()).getType().equals(AnimalProductType.goatMilk)
                        || ((Animalproduct) entry.getKey()).getType().equals(AnimalProductType.bigGoatMilk)) {

                        Main.getClient().getPlayer().getBackPack().inventory.Items.remove(entry.getKey());
                        return new Result(true , "you create Goat Cheese Successfully");
                    }
                }
            }
            return new Result(false , "you didn't choose suitable ingredients for Goat Cheese");
        }
    },
    Beer        ("Beer" , CraftType.Keg , 200 , 24) {
        @Override
        public int getEnergy(String name) {
            return 50;
        }

        @Override
        public void creatArtesian(String name, CraftingItem craftingItem) {
            MarketItem beer=new MarketItem(MarketItemType.Beer);
            craftingItem.getItems().add(beer);
            craftingItem.getDateHours().add(Main.getClient().getLocalGameState().currentDate.clone());
        }

        @Override
        public Result checkIngredient(HashMap<Items,Integer> ingredient) {
            Items wheat = new AllCrops(CropsType.Wheat);
            if (ingredient.containsKey(wheat)) {
                Main.getClient().getPlayer().getBackPack().inventory.Items.compute(wheat,(k,v)-> v-1);
                return new Result(true , "you create Wheat Successfully");
            }
            return new Result(false , "you didn't choose suitable ingredients for Wheat");
        }
    },
    Vinegar     ("Vinegar" , CraftType.Keg , 100 , 10) {
        @Override
        public int getEnergy(String name) {
            return 13;
        }


        @Override
        public void creatArtesian(String name, CraftingItem craftingItem) {
            MarketItem vinegar=new MarketItem(MarketItemType.Vinegar);
            craftingItem.getItems().add(vinegar);
            craftingItem.getDateHours().add(Main.getClient().getLocalGameState().currentDate.clone());
        }

        @Override
        public Result checkIngredient(HashMap<Items,Integer> ingredient) {
            Items Rice = new MarketItem(MarketItemType.Rice);
            if (ingredient.containsKey(Rice)) {
                Main.getClient().getPlayer().getBackPack().inventory.Items.compute(Rice,(k,v)-> v-1);
                return new Result(true , "you create Vinegar Successfully");
            }
            return new Result(false,"you didn't choose suitable ingredients for Vinegar");
        }
    },
    Coffee      ("Coffee" , CraftType.Keg , 150 , 2) {
        @Override
        public int getEnergy(String name) {
            return 75;
        }


        @Override
        public void creatArtesian(String name, CraftingItem craftingItem) {
            MarketItem coffee=new MarketItem(MarketItemType.Coffee);
            craftingItem.getItems().add(coffee);
            craftingItem.getDateHours().add(Main.getClient().getLocalGameState().currentDate.clone());
        }

        @Override
        public Result checkIngredient(HashMap<Items,Integer> ingredient) {
            Items items = new AllCrops(CropsType.CoffeeBean);
            try {
                int x = ingredient.get(items);
                if (x < 5) {
                    return new Result(false , "you should choose more Coffee Bean");
                }
                if (Main.getClient().getPlayer().getBackPack().inventory.Items.get(items) < 5) {
                    return new Result(false , "Not enough Coffee Bean in your inventory");
                }
                Main.getClient().getPlayer().getBackPack().inventory.Items.compute(items,(k,v)-> v-5);
                return new Result(true , "you create Coffee Successfully");
            }
            catch (Exception e) {
                return new Result(false , "you didn't choose suitable ingredients for Coffee");
            }
        }
    },
    Juice       ("Juice" , CraftType.Keg , 300 , 96) {
        @Override
        public int getEnergy(String name) {
            CropsType cropsType = CropsType.valueOf(name);
            return 2 * cropsType.getEnergy();
        }

        @Override
        public void creatArtesian(String name, CraftingItem craftingItem) {
            ArtisanProduct juice=new ArtisanProduct(Juice);
            craftingItem.getItems().add(juice);
            craftingItem.getDateHours().add(Main.getClient().getLocalGameState().currentDate.clone());
        }

        @Override
        public Result checkIngredient(HashMap<Items,Integer> ingredient) {
            for (CropsType cropsType :CropsType.values()) {
                Items items = new AllCrops(cropsType);
                if (ingredient.containsKey(items)) {
                    Main.getClient().getPlayer().getBackPack().inventory.Items.compute(items,(k,v)-> v-1);
                    return new Result(true , "you create Juice Successfully");
                }
            }
            return new Result(false , "you didn't choose suitable ingredients for Juice");
        }
    },
    Mead        ("Mead",CraftType.Keg , 300 , 10) {
        @Override
        public int getEnergy(String name) {
            return 100;
        }


        @Override
        public void creatArtesian(String name, CraftingItem craftingItem) {
            ArtisanProduct mead=new ArtisanProduct(Mead);
            craftingItem.getItems().add(mead);
            craftingItem.getDateHours().add(Main.getClient().getLocalGameState().currentDate.clone());
        }

        @Override
        public Result checkIngredient(HashMap<Items,Integer> ingredient) {
            Items honey = new ArtisanProduct(Honey);
            if (ingredient.containsKey(honey)) {
                Main.getClient().getPlayer().getBackPack().inventory.Items.compute(honey,(k,v)-> v-1);
                return new Result(true , "you create Mead Successfully");
            }
            return new Result(false,"you didn't choose suitable ingredients for Mead");
        }
    },
    Pale_Ale    ("Pale Ale",CraftType.Keg , 300 , 72) {
        @Override
        public int getEnergy(String name) {
            return 50;
        }

        @Override
        public void creatArtesian(String name, CraftingItem craftingItem) {
            ArtisanProduct pale=new ArtisanProduct(Pale_Ale);
            craftingItem.getItems().add(pale);
            craftingItem.getDateHours().add(Main.getClient().getLocalGameState().currentDate.clone());
        }

        @Override
        public Result checkIngredient(HashMap<Items,Integer> ingredient) {
            Items hops = new AllCrops(CropsType.Hops);
            if (ingredient.containsKey(hops)) {
                Main.getClient().getPlayer().getBackPack().inventory.Items.compute(hops,(k,v)-> v-1);
                return new Result(true , "you create Hops Successfully");
            }
            return new Result(false,"you didn't choose suitable ingredients for Hops");
        }
    },
    Wine        ("Wine" , CraftType.Keg , 400 , 168) {
        @Override
        public int getEnergy(String name) {
            TreesProductType type = TreesProductType.valueOf(name);
            return (7 * type.getEnergy() ) / 4;
        }

        @Override
        public void creatArtesian(String name, CraftingItem craftingItem) {
            ArtisanProduct wine=new ArtisanProduct(Wine);
            craftingItem.getItems().add(wine);
            craftingItem.getDateHours().add(Main.getClient().getLocalGameState().currentDate.clone());
        }

        @Override
        public Result checkIngredient(HashMap<Items,Integer> ingredient) {
            return checkTreeProduct(ingredient , "Wine" , 1);
        }
    },
    Dried_Fruit ("Dried Fruit" , CraftType.Dehydrator , 425 , 12) {
        @Override
        public int getEnergy(String name) {
            return 75;
        }

        @Override
        public void creatArtesian(String name, CraftingItem craftingItem) {
            ArtisanProduct DriedFruit=new ArtisanProduct(Dried_Fruit);
            craftingItem.getItems().add(DriedFruit);
            craftingItem.getDateHours().add(Main.getClient().getLocalGameState().currentDate.clone());
        }

        @Override
        public Result checkIngredient(HashMap<Items,Integer> ingredient) {
            return checkTreeProduct(ingredient , "Dried Fruit" , 5);
        }
    },
    Raisins     ("Raisins" , CraftType.Dehydrator , 600 , 12) {
        @Override
        public int getEnergy(String name) {
            return 125;
        }

        @Override
        public void creatArtesian(String name, CraftingItem craftingItem) {
            ArtisanProduct raisins=new ArtisanProduct(Raisins);
            craftingItem.getItems().add(raisins);
            craftingItem.getDateHours().add(Main.getClient().getLocalGameState().currentDate.clone());
        }

        @Override
        public Result checkIngredient(HashMap<Items,Integer> ingredient) {
            Items Grape = new AllCrops(CropsType.Grape);
            try {
                int x = ingredient.get(Grape);
                if (x >= 5) {
                    if (Main.getClient().getPlayer().getBackPack().inventory.Items.get(Grape) >= 5) {
                        Main.getClient().getPlayer().getBackPack().inventory.Items.compute(Grape,(k,v)-> v-5);
                        return new Result(true , "you create Raisins Successfully");
                    }
                    return new Result(false , "you don't have enough Grape in your inventory");
                }
                return new Result(false,"you should add more Grape for product of Raisins");
            }
            catch (Exception e) {
                return new Result(false,"you should add more Grape for product of Raisins");
            }
        }
    },
    Coal        ("Coal",CraftType.CharcoalKlin , 50 , 1) {
        @Override
        public int getEnergy(String name) {
            return 0;
        }

        @Override
        public void creatArtesian(String name, CraftingItem craftingItem) {
            ForagingMinerals coal = new ForagingMinerals(ForagingMineralsType.COAL);
            craftingItem.getItems().add(coal);
            craftingItem.getDateHours().add(Main.getClient().getLocalGameState().currentDate.clone());
        }

        @Override
        public Result checkIngredient(HashMap<Items,Integer> ingredient) {
            Items wood = new Wood();
            try {
                int x = ingredient.get(wood);
                if (x >= 10) {
                    if (Main.getClient().getPlayer().getBackPack().inventory.Items.get(wood) >= 10) {
                        Main.getClient().getPlayer().getBackPack().inventory.Items.compute(wood,(k,v)-> v-10);
                        return new Result(true , "you create Wood Successfully");
                    }
                    return new Result(false,"you don't have enough Wood in your inventory");
                }
                return new Result(false , "you should add more Wood for product of Coal");
            }
            catch (Exception e) {
                return new Result(false , "you should add more Wood for product of Coal");
            }
        }
    },
    Cloth       ("Cloth",CraftType.Loom , 470 , 4) {
        @Override
        public int getEnergy(String name) {
            return 0;
        }

        @Override
        public void creatArtesian(String name, CraftingItem craftingItem) {
            ArtisanProduct cloth=new ArtisanProduct(Cloth);
            craftingItem.getItems().add(cloth);
            craftingItem.getDateHours().add(Main.getClient().getLocalGameState().currentDate.clone());
        }

        @Override
        public Result checkIngredient(HashMap<Items,Integer> ingredient) {
            for (Map.Entry<Items,Integer> entry : ingredient.entrySet()) {
                if (entry.getKey() instanceof Animalproduct) {
                    if (((Animalproduct) entry.getKey()).getType().equals(AnimalProductType.rabbits_Wool)
                        || ((Animalproduct) entry.getKey()).getType().equals(AnimalProductType.sheeps_Wool)) {
                        Main.getClient().getPlayer().getBackPack().inventory.Items.compute(entry.getKey(),(k,v)-> v-1);

                        return new Result(true , "you create Cloth Successfully");
                    }
                }
            }
            return new Result(false , "you didn't choose suitable ingredient for Cloth");
        }
    },
    Mayonnaise  ("Mayonnaise" , CraftType.MayonnaiseMachine , 237 , 3) {
        @Override
        public int getEnergy(String name) {
            return 50;
        }

        @Override
        public void creatArtesian(String name, CraftingItem craftingItem) {
            ArtisanProduct mayonnaise=new ArtisanProduct(Mayonnaise);
            craftingItem.getItems().add(mayonnaise);
            craftingItem.getDateHours().add(Main.getClient().getLocalGameState().currentDate.clone());
        }

        @Override
        public Result checkIngredient(HashMap<Items,Integer> ingredient) {
            for (Map.Entry<Items,Integer> entry : ingredient.entrySet()) {
                if (entry.getKey() instanceof Animalproduct) {
                    if (((Animalproduct) entry.getKey()).getType().equals(AnimalProductType.Egg)
                        || ((Animalproduct) entry.getKey()).getType().equals(AnimalProductType.bigEgg)) {
                        Main.getClient().getPlayer().getBackPack().inventory.Items.compute(entry.getKey(),(k,v)-> v-1);

                        return new Result(true , "you create Mayonnaise Successfully");
                    }
                }
            }
            return new Result(false , "you didn't choose suitable ingredient for Mayonnaise");
        }
    },
    Truffle_Oil ("Truffle Oil" , CraftType.OilMaker , 1065 , 6) {
        @Override
        public int getEnergy(String name) {
            return 6;
        }

        @Override
        public void creatArtesian(String name, CraftingItem craftingItem) {
            ArtisanProduct TruffleOil=new ArtisanProduct(Truffle_Oil);
            craftingItem.getItems().add(TruffleOil);
            craftingItem.getDateHours().add(Main.getClient().getLocalGameState().currentDate.clone());
        }

        @Override
        public Result checkIngredient(HashMap<Items,Integer> ingredient) {
            for (Map.Entry<Items,Integer> entry : ingredient.entrySet()) {
                if (entry.getKey() instanceof Animalproduct) {
                    if (((Animalproduct) entry.getKey()).getType().equals(AnimalProductType.Truffle)) {
                        Main.getClient().getPlayer().getBackPack().inventory.Items.compute(entry.getKey(),(k,v)-> v-1);

                        return new Result(true , "you create Truffle Successfully");
                    }
                }
            }
            return new Result(false , "you didn't choose suitable ingredient for Truffle");
        }
    },
    Oil         ("Oil", CraftType.OilMaker , 100 , 6) {
        @Override
        public int getEnergy(String name) {
            return 13;
        }

        @Override
        public void creatArtesian(String name, CraftingItem craftingItem) {
            MarketItem oil=new MarketItem(MarketItemType.Oil);
            craftingItem.getItems().add(oil);
            craftingItem.getDateHours().add(Main.getClient().getLocalGameState().currentDate.clone());
        }

        @Override
        public Result checkIngredient(HashMap<Items,Integer> ingredient) {
            Items corn = new AllCrops(CropsType.Corn);
            Items sunFlower = new AllCrops(CropsType.Sunflower);
            Items sunFlowerSeed = new ForagingSeeds(ForagingSeedsType.SunflowerSeeds);

            if (ingredient.containsKey(corn)) {
                Main.getClient().getPlayer().getBackPack().inventory.Items.compute(corn,(k,v)-> v-1);
                return new Result(true , "you create Oil Successfully");
            }
            if (ingredient.containsKey(sunFlower)) {
                Main.getClient().getPlayer().getBackPack().inventory.Items.compute(sunFlower,(k,v)-> v-1);
                return new Result(true , "you create Oil Successfully");
            }
            if (ingredient.containsKey(sunFlowerSeed)) {
                Main.getClient().getPlayer().getBackPack().inventory.Items.compute(sunFlowerSeed,(k,v)-> v-1);
                return new Result(true , "you create Oil Successfully");
            }
            return new Result(false , "you didn't choose suitable ingredient for Oil");
        }
    },
    Pickles     ("Pickles" , CraftType.PreservesJar , 200 , 6) {
        @Override
        public int getEnergy(String name) {
            CropsType cropsType = CropsType.valueOf(name);
            return (7 * cropsType.getEnergy() ) / 4;
        }

        @Override
        public void creatArtesian(String name, CraftingItem craftingItem) {
            ArtisanProduct pickles=new ArtisanProduct(Pickles);
            craftingItem.getItems().add(pickles);
            craftingItem.getDateHours().add(Main.getClient().getLocalGameState().currentDate.clone());
        }

        @Override
        public Result checkIngredient(HashMap<Items,Integer> ingredient) {
            for (Map.Entry<Items,Integer> entry : ingredient.entrySet()) {
                if (entry.getKey() instanceof AllCrops) {
                    if (((AllCrops) entry.getKey()).getType().isVegetable()) {
                        Main.getClient().getPlayer().getBackPack().inventory.Items.compute(entry.getKey(),(k,v)-> v-1);
                        return new Result(true , "you create Pickles Successfully");
                    }
                }
            }
            return new Result(false , "you didn't choose suitable ingredient for Pickles");
        }
    },
    Jelly       ("Jelly" , CraftType.PreservesJar , 200 , 72) {
        @Override
        public int getEnergy(String name) {
            TreesProductType type = TreesProductType.valueOf(name);
            return 2 * type.getEnergy();
        }

        @Override
        public void creatArtesian(String name, CraftingItem craftingItem) {
            ArtisanProduct jelly=new ArtisanProduct(Jelly);
            craftingItem.getItems().add(jelly);
            craftingItem.getDateHours().add(Main.getClient().getLocalGameState().currentDate.clone());
        }

        @Override
        public Result checkIngredient(HashMap<Items,Integer> ingredient) {
            return checkTreeProduct(ingredient , "Jelly",1);
        }
    },
    Smoked_Fish ("Smoked Fish" , CraftType.FishSmoker , 300 , 1)  {
        @Override
        public int getEnergy(String name) {
            return 15;
        }

        public void creatArtesian(String name, CraftingItem craftingItem) {
            ArtisanProduct smokedFish=new ArtisanProduct(Smoked_Fish);
            craftingItem.getItems().add(smokedFish);
            craftingItem.getDateHours().add(Main.getClient().getLocalGameState().currentDate.clone());
        }

        @Override
        public Result checkIngredient(HashMap<Items,Integer> ingredient) {
            Items coal = new ForagingMinerals(ForagingMineralsType.COAL);
            if (! ingredient.containsKey(coal)) {
                return new Result(false , "you should add more Coal");
            }
            for (Map.Entry<Items,Integer> entry : ingredient.entrySet()) {
                if (entry.getKey() instanceof Fish) {
                    Main.getClient().getPlayer().getBackPack().inventory.Items.compute(entry.getKey(),(k,v)-> v-1);
                    Main.getClient().getPlayer().getBackPack().inventory.Items.compute(coal,(k,v)-> v-1);
                    return new Result(true , "you create Smoked Fish Successfully");
                }
            }
            return new Result(false , "you should add a Fish");
        }
    },
    Dried_Mushrooms("Dried Mushrooms" , CraftType.Dehydrator , 400 , 12) {
        @Override
        public int getEnergy(String name) {
            return 50;
        }

        @Override
        public void creatArtesian(String name, CraftingItem craftingItem) {
            ArtisanProduct DriedMushrooms=new ArtisanProduct(Dried_Mushrooms);
            craftingItem.getItems().add(DriedMushrooms);
            craftingItem.getDateHours().add(Main.getClient().getLocalGameState().currentDate.clone());
        }

        @Override
        public Result checkIngredient(HashMap<Items,Integer> ingredient) {
            for (Map.Entry<Items,Integer> entry : ingredient.entrySet()) {
                if (entry.getKey() instanceof ForagingCrops) {
                    if (((ForagingCrops) entry.getKey()).getType().isMushroom()) {
                        try {
                            int x = ingredient.get(entry.getKey());
                            if (x >= 5) {
                                if (Main.getClient().getPlayer().getBackPack().inventory.Items.get(entry.getKey()) >= 5) {
                                    Main.getClient().getPlayer().getBackPack().inventory.Items.compute(entry.getKey(),(k,v)-> v-5);
                                    return new Result(true , "you create Dried Mushrooms Successfully");
                                }
                            }
                        }
                        catch (Exception e) {

                        }
                    }
                }
            }
            return new Result(false , "you didn't choose suitable ingredients for Dried Mushrooms");
        }
    },
    Duck_Mayonnaise("Duck Mayonnaise" , CraftType.MayonnaiseMachine , 37 , 3) {
        @Override
        public int getEnergy(String name) {
            return 75;
        }

        @Override
        public void creatArtesian(String name, CraftingItem craftingItem) {
            ArtisanProduct DuckMayonnaise=new ArtisanProduct(Duck_Mayonnaise);
            craftingItem.getItems().add(DuckMayonnaise);
            craftingItem.getDateHours().add(Main.getClient().getLocalGameState().currentDate.clone());
        }


        @Override
        public Result checkIngredient(HashMap<Items,Integer> ingredient) {
            for (Map.Entry<Items,Integer> entry : ingredient.entrySet()) {
                if (entry.getKey() instanceof Animalproduct) {
                    if (((Animalproduct) entry.getKey()).getType().equals(AnimalProductType.duckEgg)) {
                        Main.getClient().getPlayer().getBackPack().inventory.Items.compute(entry.getKey(),(k,v)-> v-1);

                        return new Result(true , "you create Duck Mayonnaise Successfully");
                    }
                }
            }
            return new Result(false , "you didn't choose suitable ingredient for Duck Mayonnaise");
        }
    },
    Dinosaur_Mayonnaise("Dinosaur Mayonnaise" , CraftType.MayonnaiseMachine , 800 , 3) {
        @Override
        public int getEnergy(String name) {
            return 125;
        }

        @Override
        public void creatArtesian(String name, CraftingItem craftingItem) {
            ArtisanProduct DinosaurMayonnaise=new ArtisanProduct(Dinosaur_Mayonnaise);
            craftingItem.getItems().add(DinosaurMayonnaise);
            craftingItem.getDateHours().add(Main.getClient().getLocalGameState().currentDate.clone());
        }

        @Override
        public Result checkIngredient(HashMap<Items,Integer> ingredient) {
            for (Map.Entry<Items,Integer> entry : ingredient.entrySet()) {
                if (entry.getKey() instanceof Animalproduct) {
                    if (((Animalproduct) entry.getKey()).getType().equals(AnimalProductType.dinosaurEgg)) {
                        Main.getClient().getPlayer().getBackPack().inventory.Items.compute(entry.getKey(),(k,v)-> v-1);

                        return new Result(true , "you create Dinosaur Mayonnaise Successfully");
                    }
                }
            }
            return new Result(false , "you didn't choose suitable ingredient for Dinosaur Mayonnaise");
        }
    };

//    AnyMetalBar("Any_Metal_Bar" , CraftType.Furnace , 100 , 4) {
//        @Override
//        public int getEnergy(String name) {
//            return 0;
//        }
//
//        @Override
//        public void creatArtesian(String name, CraftingItem craftingItem) {
//            if (name.equals("Copper_Ore")) {
//                BarsAndOres copperBar=new BarsAndOres(BarsAndOreType.CopperBar);
//                craftingItem.getBuffer().put(copperBar , currentGame.currentDate.clone());
//            }
//            if (name.equals("Iron_Ore")) {
//                BarsAndOres ironBar =new BarsAndOres(BarsAndOreType.IronBar);
//                craftingItem.getBuffer().put(ironBar , currentGame.currentDate.clone());
//            }
//            if (name.equals("Gold_Ore")) {
//                BarsAndOres goldBar=new BarsAndOres(BarsAndOreType.GoldBar);
//                craftingItem.getBuffer().put(goldBar , currentGame.currentDate.clone());
//            }
//            if (name.equals("Iridium_Ore")) {
//                BarsAndOres iridiumBar=new BarsAndOres(BarsAndOreType.IridiumBar);
//                craftingItem.getBuffer().put(iridiumBar , currentGame.currentDate.clone());
//            }
//        }
//
//        @Override
//        public boolean checkIngredient(String first, String second) {
//            String newFirst=first.replace('_' , ' ');
//            String newSecond=second.replace('_' , ' ');
//
//            if (! newSecond.equals(ForagingMineralsType.COAL.getDisplayName())) {
//                return false;
//            }
//            Inventory inventory= currentGame.currentPlayer.getBackPack().inventory;
//            for (Map.Entry <Items , Integer> entry : inventory.Items.entrySet()) {
//                if (entry.getKey() instanceof BarsAndOres) {
//
//                    if (((BarsAndOres) entry.getKey()).getType().getName().equals(newFirst)) {
//
//                        if (entry.getValue() >= 5) {
//                            inventory.Items.put(entry.getKey() , entry.getValue() - 5);
//                            if (entry.getValue() == 0) {
//                                inventory.Items.remove(entry.getKey());
//                            }
//                            return true;
//                        }
//                    }
//                }
//            }
//            return false;
//        }
//    };

    private static Result checkTreeProduct(HashMap<Items,Integer> ingredient , String name , int number) {
        for (Map.Entry <Items, Integer> entry : ingredient.entrySet()) {
            if (entry.getKey() instanceof TreesProdct) {
                if (entry.getValue() >= number) {
                    if (Main.getClient().getPlayer().getBackPack().inventory.Items.get(entry.getKey()) >= number) {
                        Main.getClient().getPlayer().getBackPack().inventory.Items.compute(entry.getKey() , (key,v) -> v-number);
                        return new Result(true , "you create "+name+" Successfully");
                    }
                }

            }
        }
        return new Result(false , "you didn't choose suitable ingredient for "+name);
    }

//    private static void check(String newName) {
//        Inventory inventory= currentGame.currentPlayer.getBackPack().inventory;
//        for (Map.Entry <Items, Integer> entry : inventory.Items.entrySet()) {
//            if (entry.getKey() instanceof Animalproduct) {
//                if (((Animalproduct) entry.getKey()).getType().getName().equals(newName)) {
//                    inventory.Items.remove(entry.getKey());
//                    //return true;
//                }
//            }
//        }
//        //return false;
//    }

    private final String name;
    private final CraftType craftType;
    public abstract int getEnergy(String name);
    public abstract void creatArtesian(String name , CraftingItem craftingItem);
    public abstract Result checkIngredient(HashMap<Items,Integer> ingrediant);
    private final int price;
    private final int takesTime;

    ArtisanType(String name, CraftType craftType , int price ,int takesTime) {
        this.name = name;
        this.price = price;
        this.craftType = craftType;
        this.takesTime = takesTime;
    }

    public String getName() {
        return name;
    }
    public CraftType getCraftType() {
        return craftType;
    }
    public int getPrice() {
        return price;
    }
    public int getTakesTime() {
        return takesTime;
    }
    public String getPath() {
        return "Erfan/Artisan/" + this.name + ".png";
    }
}
