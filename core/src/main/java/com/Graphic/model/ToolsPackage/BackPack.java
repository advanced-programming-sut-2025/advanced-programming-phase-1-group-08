package com.Graphic.model.ToolsPackage;

import com.Graphic.model.Enum.ItemType.BackPackType;
import com.Graphic.model.Enum.ItemType.MarketType;
import com.Graphic.model.Inventory;
import com.Graphic.model.Items;

public class BackPack extends Items {

    private BackPackType Type;
    public Inventory inventory;
    private int capacity = 12;
    public final String name = "BackPack";

    public BackPack() {
        this.Type = BackPackType.primary;
        this.inventory = new Inventory();
    }

    @Override
    public String getIcon() {
        return Type.getPath();
    }

    public BackPackType getType() {
        return Type;
    }
    public void setType(BackPackType type) {
        Type = type;
    }


    @Override
    public int getSellPrice() {
        return 0;
    }

    @Override
    public int getRemindInShop(MarketType marketType) {
        return Type.getRemindInShop();
    }

    @Override
    public String getName() {
        return Type.getName();
    }

    @Override
    public String getInventoryIconPath() {
        return Type.getPath() ;
    }

    @Override
    public void setRemindInShop(int amount, MarketType marketType) {

    }

    @Override
    public int getMarketPrice(MarketType marketType) {
        return Type.getPrice();
    }
}
