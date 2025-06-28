package com.Graphic.model.Plants;

import com.Graphic.model.Enum.AllPlants.ForagingCropsType;
import com.Graphic.model.Items;
import com.Graphic.model.MapThings.Tile;
import com.Graphic.model.MapThings.Walkable;

import static com.Graphic.model.App.currentGame;


public class ForagingCrops extends Items {

    private final ForagingCropsType type;
    private boolean isProtected;


    public ForagingCrops(ForagingCropsType type) {
        this.type = type;
    }

    public ForagingCropsType getType() {
        return type;
    }

    public void setProtected(boolean aProtected) {
        isProtected = aProtected;
    }

    public boolean isProtected() {
        return isProtected;
    }

    public void delete () {

        for (Tile tile : currentGame.bigMap)
            if (tile.getGameObject() == this)
                tile.setGameObject(new Walkable());
    }

    @Override
    public String getName () {
        return type.getDisplayName();
    }

    @Override
    public int getSellPrice() {
        return type.getPrice();
    }

    @Override
    public String getIcon () {
        return this.type.getIcon();
    }
}
