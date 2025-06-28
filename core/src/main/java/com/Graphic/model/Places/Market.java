package com.Graphic.model.Places;

import com.Graphic.model.Enum.ItemType.MarketType;
import com.Graphic.model.MapThings.GameObject;

public class Market extends GameObject {
    private final int topLeftX;
    private final int topLeftY;
    private final int width;
    private final int height;
    private final MarketType marketType;

    public Market(int topLeftX, int topLeftY, int width, int height, MarketType marketType) {
        this.topLeftX = topLeftX;
        this.topLeftY = topLeftY;
        this.width = width;
        this.height = height;
        this.marketType = marketType;
    }
    public int getTopLeftX() {
        return topLeftX;
    }
    public int getTopLeftY() {
        return topLeftY;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public MarketType getMarketType() {
        return marketType;
    }

    @Override
    public String getIcon() {
        return super.getIcon();
    }

    public static MarketType isInMarket(int x , int y) {
        for (MarketType marketType : MarketType.values()) {
            int tlx=marketType.getTopleftx();
            int tly=marketType.getToplefty();
            if(x > tlx && x < tlx + marketType.getWidth() -1 && y > tly && y < tly + marketType.getHeight() - 1) {
                return marketType;
            }
        }
        return null;

    }
}
