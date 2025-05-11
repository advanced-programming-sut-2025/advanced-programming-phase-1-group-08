package model.Enum;

import model.*;
import model.Animall.Animalproduct;
import model.Animall.Fish;
import model.Enum.WeatherTime.Weather;
import model.Enum.AllPlants.CropsType;
import model.Enum.ItemType.*;
import model.MapThings.BasicRock;
import model.MapThings.Wood;
import model.OtherItem.ArtisanProduct;
import model.OtherItem.BarsAndOres;
import model.OtherItem.MarketItem;
import model.Plants.AllCrops;
import model.Plants.MixedSeeds;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public enum NPC {

    Sebastian("Sebastian", new LinkedHashMap<>(Map.of(new BarsAndOres(BarsAndOreType.IronOre), 50,
            new BasicRock(), 150 )), 42, 45, 6, 5,
            new MarketItem(MarketItemType.Oil), 10) {

        @Override
        public String getDialogue(int friendshipLevel, Weather weather) {

            Map<Integer, Map<Weather, String>> dialogues = new HashMap<>();

            dialogues.put(1, Map.of(
                    Weather.Sunny, "Morning'! Perfect day for planting.",
                    Weather.Rainy, "Rain's coming'... crops needed it.",
                    Weather.Stormy, "Storm's wrecking' my fields!",
                    Weather.Snowy, "Snow's covered everything'..."
            ));

            dialogues.put(0, Map.of(
                    Weather.Sunny, "Morning'! Perfect day for planting.",
                    Weather.Rainy, "Rain's coming'... crops needed it.",
                    Weather.Stormy, "Storm's wrecking' my fields!",
                    Weather.Snowy, "Snow's covered everything'..."
            ));

            dialogues.put(2, Map.of(
                    Weather.Sunny, "Wanna help me plant these seeds?",
                    Weather.Rainy, "Take this umbrella, don't get soaked!",
                    Weather.Stormy, "Stay under the tree till it passes.",
                    Weather.Snowy, "Need a shovel? Gotta clear the paths."
            ));

            dialogues.put(3, Map.of(
                    Weather.Sunny, "Secret time: There's a hidden spring underground!",
                    Weather.Rainy, "Come inside, I'll make ya herbal tea.",
                    Weather.Stormy, "We'll ride out this storm together!",
                    Weather.Snowy, "Take these gloves, keep your hands warm."
            ));
            return dialogues.get(friendshipLevel).get(weather);
        }

        @Override
        public boolean isItFavorite (Items items) {

            if (items instanceof Animalproduct &&
                    ((((Animalproduct) items).getAnimalProductType().equals(AnimalProductType.rabbits_Wool))||
                    ((Animalproduct) items).getAnimalProductType().equals(AnimalProductType.sheeps_Wool))) {
                return true;
            }
//            else if (items instanceof Animalproduct && TODO
//                    ((((Animalproduct) items).getAnimalProductType().equals(AnimalProductType.rabbits_Wool))||
//                            ((Animalproduct) items).getAnimalProductType().equals(AnimalProductType.sheeps_Wool))) {
//                return true;
//            }
            else return items instanceof MarketItem && ((MarketItem) items).getType().equals(MarketItemType.Pizza);
        }

        @Override
        public String getReward (int index) {

            return switch (index) {
                case 1 -> "2 Diamond";
                case 2 -> "5000 coin";
                case 3 -> "50 Quartz";
                default -> "";
            };
        }
    }, // TODO

    Abigail("Abigail",  new LinkedHashMap<>(Map.of(new BarsAndOres(BarsAndOreType.GoldBar), 1,
            new AllCrops(CropsType.Pumpkin), 1,
            new AllCrops(CropsType.Wheat),   50)), 51, 45, 6, 5,
            new MarketItem(MarketItemType.Bouquet), 20) {

        @Override
        public String getDialogue(int friendshipLevel, Weather weather) {
            Map<Integer, Map<Weather, String>> dialogues = new HashMap<>();

            dialogues.put(0, Map.of(
                    Weather.Sunny, "Hot out... make it quick.",
                    Weather.Rainy, "Roof's leaking', but I keep working'.",
                    Weather.Stormy, "Storm? Don't care.",
                    Weather.Snowy, "Metal gets brittle in the cold."
            ));

            dialogues.put(1, Map.of(
                    Weather.Sunny, "Hot out... make it quick.",
                    Weather.Rainy, "Roof's leaking', but I keep working'.",
                    Weather.Stormy, "Storm? Don't care.",
                    Weather.Snowy, "Metal gets brittle in the cold."
            ));

            dialogues.put(2, Map.of(
                    Weather.Sunny, "Try swinging this hammer.",
                    Weather.Rainy, "Watch your step, floor's slippery.",
                    Weather.Stormy, "Forged this blade for stormy nights.",
                    Weather.Snowy, "Clear the snow from the doorway, will ya?"
            ));

            dialogues.put(3, Map.of(
                    Weather.Sunny, "Made this sword special for you. Keep it quiet!",
                    Weather.Rainy, "Get in here, spot by the forge's yours.",
                    Weather.Stormy, "Not even lightning stops my work!",
                    Weather.Snowy, "Wear these ironclad gloves. They're warm."
            ));
            return dialogues.get(friendshipLevel).get(weather);
        }

        @Override
        public boolean isItFavorite (Items items) {

            if (items instanceof BasicRock)
                return true;

            else if (items instanceof BarsAndOres && ((BarsAndOres) items).getType().equals(BarsAndOreType.IronOre)) {
                return true;
            }
            else return items instanceof MarketItem && ((MarketItem) items).getType().equals(MarketItemType.Coffee);
        }

        @Override
        public String getReward (int index) {

            return switch (index) {
                case 1 -> "+1 friendship level";
                case 2 -> "500 coin";
                case 3 -> "Iridium Sprinkler";
                default -> "";
            };
        }
    },

    Harvey("Harvey", new LinkedHashMap<>(Map.of(new Fish(FishType.Salmon, Quantity.Normal), 1,
            new AllCrops(CropsType.Kale), 12,
            new ArtisanProduct(ArtisanType.Wine),   1)), 32, 52, 6, 5,
            new MarketItem(MarketItemType.Bread), 15) {

        @Override
        public String getDialogue(int friendshipLevel, Weather weather) {
            Map<Integer, Map<Weather, String>> dialogues = new HashMap<>();

            dialogues.put(1, Map.of(
                    Weather.Sunny, "That book? Not for beginners.",
                    Weather.Rainy, "Rain washes away modern noise.",
                    Weather.Stormy, "Storms shaped ancient civilizations.",
                    Weather.Snowy, "Cold preserves history better..."
            ));

            dialogues.put(0, Map.of(
                    Weather.Sunny, "That book? Not for beginners.",
                    Weather.Rainy, "Rain washes away modern noise.",
                    Weather.Stormy, "Storms shaped ancient civilizations.",
                    Weather.Snowy, "Cold preserves history better..."
            ));

            dialogues.put(2, Map.of(
                    Weather.Sunny, "Help me decipher these runes?",
                    Weather.Rainy, "Ancient ink flows like this rain.",
                    Weather.Stormy, "Legends say storms reveal secrets.",
                    Weather.Snowy, "Snow hides ruins... and truths."
            ));

            dialogues.put(3, Map.of(
                    Weather.Sunny, "The lost library's coordinates... trust me with them.",
                    Weather.Rainy, "Share this fire, I'll share forbidden knowledge.",
                    Weather.Stormy, "The storm's rage matches the old gods' wrath!",
                    Weather.Snowy, "Take this map. It leads to frozen archives."
            ));
            return dialogues.get(friendshipLevel).get(weather);
        }

        @Override
        public boolean isItFavorite (Items items) {

            if (items instanceof ArtisanProduct && ((ArtisanProduct) items).getType().equals(ArtisanType.Pickles))
                return true;

            else if (items instanceof ArtisanProduct && ((ArtisanProduct) items).getType().equals(ArtisanType.Wine))
                return true;

            else return items instanceof MarketItem && ((MarketItem) items).getType().equals(MarketItemType.Coffee);
        }

        @Override
        public String getReward (int index) {

            return switch (index) {
                case 1 -> "750 coin";
                case 2 -> "+1 friendship level";
                case 3 -> "5 salad";
                default -> "";
            };
        }
    },

    Lia("Lia",  new LinkedHashMap<>(Map.of(new Fish(FishType.Salmon, Quantity.Normal), 1,
            new Wood(), 200,
            new BasicRock(),   200)), 42, 52, 6, 5,
            new MarketItem(MarketItemType.Salad), 25) {

        @Override
        public String getDialogue(int friendshipLevel, Weather weather) {
            Map<Integer, Map<Weather, String>> dialogues = new HashMap<>();

            dialogues.put(0, Map.of(
                    Weather.Sunny, "Everything's rare and valuable!",
                    Weather.Rainy, "Rain? Perfect time to buy!",
                    Weather.Stormy, "Storm-proof goods here!",
                    Weather.Snowy, "Furs? Best in the land!"
            ));

            dialogues.put(1, Map.of(
                    Weather.Sunny, "Everything's rare and valuable!",
                    Weather.Rainy, "Rain? Perfect time to buy!",
                    Weather.Stormy, "Storm-proof goods here!",
                    Weather.Snowy, "Furs? Best in the land!"
            ));

            dialogues.put(2, Map.of(
                    Weather.Sunny, "Special discount... for now.",
                    Weather.Rainy, "Buy this waterproof cloak!",
                    Weather.Stormy, "Storm lanterns - half price!",
                    Weather.Snowy, "Snowshoes? Last pair!"
            ));

            dialogues.put(3, Map.of(
                    Weather.Sunny, "Take this master key. Opens any chest.",
                    Weather.Rainy, "My private stash - pick what you want.",
                    Weather.Stormy, "Wealth whispers in the thunder...",
                    Weather.Snowy, "Diamond-grade ice pick. Yours."
            ));
            return dialogues.get(friendshipLevel).get(weather);
        }

        @Override
        public boolean isItFavorite (Items items) {

            if (items instanceof MarketItem && ((MarketItem) items).getType().equals(MarketItemType.Salad))
                return true;

            else if (items instanceof AllCrops && ((AllCrops) items).getType().equals(CropsType.Grape))
                return true;

            else return items instanceof ArtisanProduct && ((ArtisanProduct) items).getType().equals(ArtisanType.Wine);
        }

        @Override
        public String getReward (int index) {

            return switch (index) {
                case 1 -> "500 coin";
                case 2 -> "Pancakes Recipe";
                case 3 -> "ⅾeⅼuxe sⅽareⅽrow";
                default -> "";
            };
        }
    },

    Robin("Robin",  new LinkedHashMap<>(Map.of(new BarsAndOres(BarsAndOreType.IronBar), 10,
            new Wood(), 80,
            new MixedSeeds(),   10)), 51, 52, 6, 5,
            new MarketItem(MarketItemType.Coffee), 18) {

        @Override
        public String getDialogue(int friendshipLevel, Weather weather) {
            Map<Integer, Map<Weather, String>> dialogues = new HashMap<>();

            dialogues.put(0, Map.of(
                    Weather.Sunny, "The valley's quiet. Too quiet.",
                    Weather.Rainy, "Rain hides ambush sounds.",
                    Weather.Stormy, "Storm's fury matches mine!",
                    Weather.Snowy, "Cold sharpens the blade."
            ));

            dialogues.put(1, Map.of(
                    Weather.Sunny, "The valley's quiet. Too quiet.",
                    Weather.Rainy, "Rain hides ambush sounds.",
                    Weather.Stormy, "Storm's fury matches mine!",
                    Weather.Snowy, "Cold sharpens the blade."
            ));

            dialogues.put(2, Map.of(
                    Weather.Sunny, "Spar with me. Don't hold back.",
                    Weather.Rainy, "Wet steel sings a death song.",
                    Weather.Stormy, "Thunder drowns battle cries!",
                    Weather.Snowy, "Snow reveals traitors' tracks."
            ));

            dialogues.put(3, Map.of(
                    Weather.Sunny, "Take my ancestral blade. You're worthy.",
                    Weather.Rainy, "Shelter here. I'll guard your sleep.",
                    Weather.Stormy, "We'll charge through lightning together!",
                    Weather.Snowy, "My war cloak. It's kept me alive."
            ));
            return dialogues.get(friendshipLevel).get(weather);
        }

        @Override
        public boolean isItFavorite (Items items) {

            if (items instanceof MarketItem && ((MarketItem) items).getType().equals(MarketItemType.Spaghetti))
                return true;

            else if (items instanceof Wood)
                return true;

            else return items instanceof BarsAndOres && ((BarsAndOres) items).getType().equals(BarsAndOreType.IronBar);
        }

        @Override
        public String getReward (int index) {

            return switch (index) {
                case 1 -> "2000 coin";
                case 2 -> "1000 coin";
                case 3 -> "1500 coin";
                default -> "";
            };
        }
    };

    private final String name;
    private final int Width;
    private final int Hight;
    private final int topLeftX;
    private final int topLeftY;
    private final Items giftItem;
    private final int request3DayNeeded;
    private final LinkedHashMap<Items, Integer> Request;


    public abstract boolean isItFavorite (Items items);
    public abstract String getDialogue(int level, Weather weather);
    public abstract String getReward (int index);


    NPC (String name, LinkedHashMap<Items, Integer> request, int topLeftX, int topLeftY,
         int width, int height, Items giftItem, int request3DayNeeded) {

        Request = request;
        this.name = name;
        Width = width;
        Hight = height;
        this.topLeftX = topLeftX;
        this.topLeftY = topLeftY;
        this.giftItem = giftItem;
        this.request3DayNeeded = request3DayNeeded;
    }

    public String getName() {

        return name;
    }


    public boolean isInHisHome (int x, int y) {

        return x > this.topLeftX && x < this.topLeftX + this.Width &&
                y > this.topLeftY && y < this.topLeftY + this.Hight;
    }

    public int getWidth() {
        return Width;
    }
    public int getHeight() {
        return Hight;
    }
    public int getTopLeftX() {
        return topLeftX;
    }
    public int getTopLeftY() {
        return topLeftY;
    }
    public Items getGiftItem() {
        return giftItem;
    }

    public static NPC wallOrDoor (int x, int y) {
        for (NPC npc : NPC.values()) {
            if (x == npc.getTopLeftX() && y >= npc.getTopLeftY() && y < npc.getTopLeftY() + npc.getHeight()) {
                return npc;
            }
            if (x==npc.getTopLeftX() + npc.getWidth() -1 && y >= npc.getTopLeftX() && y < npc.getTopLeftY() + npc.getHeight()) {
                return npc;
            }
        }
        return null;
    }

    public int getRequest3DayNeeded() {

        return request3DayNeeded;
    }
    public LinkedHashMap<Items, Integer> getRequests() {

        return Request;
    }

}