package com.Graphic.model.Plants;

import com.Graphic.Main;
import com.Graphic.model.Enum.FoodTypes;
import com.Graphic.model.Enum.ItemType.MarketType;
import com.Graphic.model.Items;
import com.Graphic.model.Places.MarketItem;

import static com.Graphic.Controller.MainGame.GameControllerLogic.checkAmountProductAvailable;
import static com.Graphic.model.App.currentGame;

public class Food extends Items {

    private FoodTypes type;

    public Food(FoodTypes type) {
        this.type = type;
    }

    public Food() {

    }

    public FoodTypes getType() {
        return type;
    }

    public static boolean checkInventorySpaceForFood(FoodTypes type) {
        return checkAmountProductAvailable(new Food(type), 1) ||
                Main.getClient(null).getPlayer().getBackPack().getType().getRemindCapacity() > 0;
    }
    public static boolean itemIsEatable(Items item) {
        return item instanceof Food || item instanceof Animalproduct
            || item instanceof Fish || (item instanceof MarketItem && ((MarketItem) item).getType().isEatable());
    }

    @Override
    public String getName() {
        return type.getName();
    }

    @Override
    public String getInventoryIconPath() {
        return this.type.getAddress();
    }

    @Override
    public int getSellPrice() {
        return type.getSellPrice();
    }

    @Override
    public String getIcon() {
        return type.getAddress();
    }

    @Override
    public int getRemindInShop(MarketType marketType) {
        return -1;
    }

    @Override
    public void setRemindInShop(int amount, MarketType marketType) {

    }

    @Override
    public int getMarketPrice(MarketType marketType) {
        return 0;
    }
}
