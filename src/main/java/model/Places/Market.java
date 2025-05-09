package model.Places;

import model.Enum.ItemType.MarketType;
import model.MapThings.GameObject;

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
}
