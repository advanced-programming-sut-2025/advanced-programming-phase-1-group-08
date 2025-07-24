package com.Graphic.model.ToolsPackage;

import com.Graphic.model.Weather.DateHour;
import com.Graphic.model.Enum.ItemType.CraftType;
import com.Graphic.model.Enum.ItemType.MarketType;
import com.Graphic.model.Items;

import java.util.IdentityHashMap;
import java.util.Map;

public class CraftingItem extends Items {
    private final CraftType type;
    private int x;
    private int y;
    private Map<Items , DateHour> buffer=new IdentityHashMap<>();
    private boolean isWaiting;

    public CraftingItem(CraftType craftType) {
        this.type = craftType;
        isWaiting = false;
    }


    public CraftType getType() {
        return type;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Map<Items, DateHour> getBuffer() {
        return buffer;
    }

    @Override
    public void turnByTurnAutomaticTask() {

        if (this.type.equals(CraftType.Scarecrow))
            this.setProtect(8);
        if (this.type.equals(CraftType.DeluxeScarecrow))
            this.setProtect(12);
    }
    public void setProtect (int r) {


    }

    @Override
    public String getName() {
        return type.getName();
    }

    @Override
    public String getInventoryIconPath() { // TODO
        return "";
    }

    @Override
    public int getSellPrice() {
        return type.getSellPrice();
    }

    @Override
    public String getIcon() {
        return type.getIcon();
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

    public boolean isWaiting() {
        return isWaiting;
    }

    public void setWaiting(boolean waiting) {
        isWaiting = waiting;
    }
}
