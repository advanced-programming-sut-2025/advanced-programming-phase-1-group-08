package com.Graphic.model.ToolsPackage;

import com.Graphic.model.App;
import com.Graphic.model.Enum.ItemType.MarketType;
import com.Graphic.model.Enum.ToolsType.TrashCanType;
import com.Graphic.model.Items;

import java.util.HashMap;

public class TrashCan extends Tools {

    public TrashCanType type;
    public TrashCan(TrashCanType Type) {
        super("TrashCan");
        this.type=Type;
    }

    public void setType(TrashCanType type) {
        this.type = type;
    }



    public static void removeItem (int money , HashMap<Items,Integer> x, Items items, Integer amount){
        App.currentGame.currentPlayer.increaseMoney(money);
        x.compute(items, (k, l) -> l - amount);
        x.entrySet().removeIf(e -> e.getValue() <= 0);
    } // اسمش باید عوض یشه هم ورودی منابعی که باید برگردونده بشه رو بگیره و درصد برگشت هم که از تو اینام بدست میاد


    @Override
    public int healthCost() { // TODO
        return 0;
    }

    @Override
    public String getName() {
        return type.name();
    }

    @Override
    public int getSellPrice() {
        return 0;
    }

    @Override
    public String getInventoryIconPath() {
        return type.getIconPath();
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
        return type.getPrice();
    }
}
