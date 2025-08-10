package com.Graphic.model.Plants;

import com.Graphic.model.Enum.AllPlants.ForagingCropsType;
import com.Graphic.model.Enum.ItemType.MarketType;
import com.Graphic.model.Items;
import com.Graphic.model.MapThings.Tile;
import com.Graphic.model.MapThings.Walkable;
import com.badlogic.gdx.graphics.Texture;

import static com.Graphic.model.App.currentGame;


public class ForagingCrops extends Items {

    private ForagingCropsType type;
    private boolean isProtected;


    public ForagingCrops(ForagingCropsType type) {
        this.type = type;
    }

    public ForagingCrops() {

    }



    public void delete () {

        /*for (Tile tile : currentGame.bigMap)
            if (tile.getGameObject() == this)
                tile.setGameObject(new Walkable());*/
    }
    public void setProtected(boolean aProtected) {

        isProtected = aProtected;
    }
    public boolean isProtected() {

        return isProtected;
    }
    public ForagingCropsType getType() {

        return type;
    }

    @Override
    public String getName () {

        return type.getDisplayName();
    }

    @Override
    public String getInventoryIconPath() {

        return type.getTexturePath();
    }

    @Override
    public int getSellPrice() {

        return type.getPrice();
    }

    @Override
    public String getIcon() {
        return type.getTexturePath();
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
