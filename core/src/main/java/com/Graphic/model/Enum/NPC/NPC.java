package com.Graphic.model.Enum.NPC;

import com.Graphic.model.*;
import com.Graphic.model.MapThings.Tile;
import com.Graphic.model.Enum.Direction;
import com.Graphic.model.Enum.FoodTypes;
import com.Graphic.model.Plants.*;
import com.Graphic.model.Enum.WeatherTime.Weather;
import com.Graphic.model.Enum.AllPlants.CropsType;
import com.Graphic.model.Enum.ItemType.*;
import com.Graphic.model.OtherItem.ArtisanProduct;
import com.Graphic.model.OtherItem.BarsAndOres;
import com.Graphic.model.Places.MarketItem;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import static com.Graphic.model.HelpersClass.TextureManager.TEXTURE_SIZE;

public enum NPC {

    Sebastian("Sebastian", new LinkedHashMap<>(Map.of(new BarsAndOres(BarsAndOreType.IronOre), 50,
        new Food(FoodTypes.pumpkinPie), 1,
        new BasicRock(), 150 )), 42, 45, 5, 5,
        new MarketItem(MarketItemType.Oil), 10,
        new NPCDirectionSet("Mohamadreza/NPC/Sebastian")) {

        @Override
        public String getDialogue(int friendshipLevel, Weather weather) {

            Map<Integer, Map<Weather, String>> dialogues = new HashMap<>();

            dialogues.put(1, Map.of(
                Weather.Sunny, "Morning'! Perfect day for planting.",
                Weather.Rainy, "Rain's coming'... crops needed that.",
                Weather.Stormy, "Storm's wrecking my fields!",
                Weather.Snowy, "Snow's covered everything..."
            ));

            dialogues.put(0, Map.of(
                Weather.Sunny, "Morning'! Perfect day for planting.",
                Weather.Rainy, "Rain's coming'... crops needed it.",
                Weather.Stormy, "Storm's wrecking my fields!",
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
                ((((Animalproduct) items).getType().equals(AnimalProductType.rabbits_Wool))||
                    ((Animalproduct) items).getType().equals(AnimalProductType.sheeps_Wool))) {
                return true;
            }
//            else if (items instanceof Animalproduct && TODO
//                    ((((Animalproduct) items).getType().equals(AnimalProductType.rabbits_Wool))||
//                            ((Animalproduct) items).getType().equals(AnimalProductType.sheeps_Wool))) {
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
    },

    Abigail("Abigail",  new LinkedHashMap<>(Map.of(new BarsAndOres(BarsAndOreType.GoldBar), 1,
        new AllCrops(CropsType.Pumpkin), 1,
        new AllCrops(CropsType.Wheat),   50)), 51, 45, 5, 5,
        new MarketItem(MarketItemType.Bouquet), 20,
        new NPCDirectionSet("Mohamadreza/NPC/Abigail")) {

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
        new ArtisanProduct(ArtisanType.Wine),   1)), 32, 52, 5, 5,
        new MarketItem(MarketItemType.Bread), 15,
        new NPCDirectionSet("Mohamadreza/NPC/Harvey")) {

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

    Leah("Leah",  new LinkedHashMap<>(Map.of(new Fish(FishType.Salmon, Quantity.Normal), 1,
        new Wood(), 200,
        new BasicRock(),   200)), 42, 52, 5, 5,
        new MarketItem(MarketItemType.Salad), 25,
        new NPCDirectionSet("Mohamadreza/NPC/Leah")) {

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
        new MixedSeeds(),   10)), 51, 52, 5, 5,
        new MarketItem(MarketItemType.Coffee), 18,
        new NPCDirectionSet("Mohamadreza/NPC/Robin")) {

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

    private float positionX;
    private float positionY;

    private long lastConversation;

    private Sprite sprite;
    private Animation<Texture> Left;
    private Animation<Texture> Up;
    private Animation<Texture> Down;
    private Animation<Texture> Right;
    private HashMap<Direction, Texture> DirectionInWalk;
    private boolean isMoving;
    private Direction direction;
    private final NPCDirectionSet directionSet;
    private float Timer = 0.0f;
    private boolean isWaiting = false;

    private float elapsedTime = 0f;
    public float getElapsedTime() {
        return elapsedTime;
    }
    public void setElapsedTime(float elapsedTime) {
        this.elapsedTime = elapsedTime;
    }
    private float waitDuration = 2 + new Random().nextFloat() * 3;
    public float getWaitDuration() {
        return waitDuration;
    }
    public void setWaitDuration(float waitDuration) {
        this.waitDuration = waitDuration;
    }
    private int moveDistance = 8 + new Random().nextInt(4);
    public int getMoveDistance() {
        return moveDistance;
    }
    public void setMoveDistance(int moveDistance) {
        this.moveDistance = moveDistance;
    }
    private boolean isAutoWalking = true;
    public boolean isAutoWalking() {
        return isAutoWalking;
    }
    public void setAutoWalking(boolean autoWalking) {
        isAutoWalking = autoWalking;
    }



    public abstract boolean isItFavorite (Items items);
    public abstract String getDialogue(int level, Weather weather);
    public abstract String getReward (int index);


    NPC (String name, LinkedHashMap<Items, Integer> request, int topLeftX, int topLeftY,
         int width, int height, Items giftItem, int request3DayNeeded, NPCDirectionSet directionSet) {

        Request = request;
        this.name = name;
        Width = width;
        Hight = height;
        this.topLeftX = topLeftX;
        this.topLeftY = topLeftY;
        this.giftItem = giftItem;
        this.request3DayNeeded = request3DayNeeded;

        this.positionX = topLeftX;
        this.positionY = (90 - topLeftY);
        this.directionSet = directionSet;

        this.DirectionInWalk = directionSet.loadAllTextures();
        this.direction = Direction.Down;
        this.sprite = new Sprite(DirectionInWalk.get(direction));
        this.sprite.setPosition(TEXTURE_SIZE * topLeftX, TEXTURE_SIZE * (90 - topLeftY));
    }


    public boolean isTileCloseToNPC (int x, int y) {

        int delX = Math.abs(x - this.getX());
        int delY = Math.abs(y - this.getY());

        return delX < 4 && delY < 4;
    }

    public String getName() {

        return name;
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
    public String getIconPath() {
        return "Erfan/NPC_Avatar/" + this.name + ".png";
    }

    public Items getGiftItem() {
        return giftItem;
    }

    public Sprite getSprite() {
        return sprite;
    }
    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
    public Animation<Texture> getLeft() {
        return Left;
    }
    public void setLeft(Animation<Texture> left) {
        Left = left;
    }
    public Animation<Texture> getUp() {
        return Up;
    }
    public void setUp(Animation<Texture> up) {
        Up = up;
    }
    public Animation<Texture> getDown() {
        return Down;
    }
    public void setDown(Animation<Texture> down) {
        Down = down;
    }
    public Animation<Texture> getRight() {
        return Right;
    }
    public void setRight(Animation<Texture> right) {
        Right = right;
    }
    public NPCDirectionSet getDirectionSet() {
        return directionSet;
    }
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    public Direction getDirection() {
        return direction;
    }
    public float getTimer() {
        return Timer;
    }
    public void setTimer(float timer) {
        Timer = timer;
    }
    public Animation<Texture> getAnimation() {
        switch (direction) {
            case Up -> {
                return Up;
            }
            case Down -> {
                return Down;
            }
            case Left -> {
                return Left;
            }
            case Right -> {
                return Right;
            }
            default -> {
                return null;
            }
        }
    }
    public void setPositionX(float x) {
        positionX = x;
    }
    public float getPositionX() {
        return positionX;
    }
    public void setPositionY(float y) {
        positionY = y;
    }
    public float getPositionY() {
        return positionY;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }
    public boolean isMoving() {
        return isMoving;
    }

    public void setWaiting(boolean waiting) {
        isWaiting = waiting;
    }

    public boolean isWaiting() {
        return isWaiting;
    }


    public static NPC wallOrDoor (int x, int y) {
        for (NPC npc : NPC.values()) {
            if (x == npc.getTopLeftX() && y >= npc.getTopLeftY() && y < npc.getTopLeftY() + npc.getHeight()) {
                return npc;
            }
            if (x==npc.getTopLeftX() + npc.getWidth() -1 && y >= npc.getTopLeftY() && y < npc.getTopLeftY() + npc.getHeight()) {
                return npc;
            }
            if (y == npc.getTopLeftY() && x >= npc.getTopLeftX() && x < npc.getTopLeftX() + npc.getWidth()) {
                return npc;
            }
            if (y== npc.getTopLeftY() + npc.getHeight() -1 && x >= npc.getTopLeftX() && x < npc.getTopLeftX() + npc.getWidth()) {
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

    public int getY() {
        return (int) (90 - (positionY / TEXTURE_SIZE));
    }
    public void setY(int y) {
        positionY = (90 - y) * TEXTURE_SIZE;
    }
    public int getX() {
        return (int) (positionX / TEXTURE_SIZE);
    }
    public void setX(int x) {
        positionX = x * TEXTURE_SIZE;
    }
    public void setPosition(Tile tile) {
        positionX = tile.getX() * TEXTURE_SIZE;
        positionY = tile.getY() * TEXTURE_SIZE;
    }

    public boolean canTalk () {
        return TimeUtils.millis() - this.lastConversation > 10000;
    }
    public void setLastConversation(long lastConversation) {
        this.lastConversation = lastConversation;
    }
}
