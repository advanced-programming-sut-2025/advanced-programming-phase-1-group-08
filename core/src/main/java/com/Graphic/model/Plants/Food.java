package com.Graphic.model.Plants;

import com.Graphic.model.Enum.FoodTypes;
import com.Graphic.model.Enum.ItemType.MarketType;
import com.Graphic.model.Items;

import static com.Graphic.Controller.MainGame.GameControllerLogic.checkAmountProductAvailable;
import static com.Graphic.model.App.currentGame;

public class Food extends Items {

    private final FoodTypes type;

    public Food(FoodTypes type) {
        this.type = type;
    }

    public FoodTypes getType() {
        return type;
    }

    public static boolean checkInventorySpaceForFood(FoodTypes type) {
        return checkAmountProductAvailable(new Food(type), 1) ||
                currentGame.currentPlayer.getBackPack().getType().getRemindCapacity() > 0;
    }

    @Override
    public String getName() {
        return type.name();
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
