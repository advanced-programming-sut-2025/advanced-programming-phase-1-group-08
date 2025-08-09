package com.Graphic.model.ToolsPackage;

import com.Graphic.model.Enum.ItemType.BackPackType;
import com.Graphic.model.Enum.ItemType.MarketType;
import com.Graphic.model.Inventory;
import com.Graphic.model.Items;

public class BackPack {

    private BackPackType Type;
    public Inventory inventory;
    private int capacity = 12;

    public BackPack() {
        this.Type = BackPackType.primary;
        this.inventory = new Inventory();
    }


    public String getIcon() {
        return Type.getPath();
    }

    public BackPackType getType() {
        return Type;
    }
    public void setType(BackPackType type) {
        Type = type;
    }



    public int getSellPrice() {
        return 0;
    }


    public int getRemindInShop(MarketType marketType) {
        return Type.getRemindInShop();
    }


    public String getName() {
        return Type.getName();
    }


    public String getInventoryIconPath() {
        return Type.getPath() ;
    }


    public void setRemindInShop(int amount, MarketType marketType) {

    }

    public int getMarketPrice(MarketType marketType) {
        return Type.getPrice();
    }
}
