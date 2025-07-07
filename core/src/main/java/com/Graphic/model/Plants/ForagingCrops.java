package com.Graphic.model.Plants;

import com.Graphic.model.Enum.AllPlants.ForagingCropsType;
import com.Graphic.model.Items;
import com.Graphic.model.MapThings.Tile;
import com.Graphic.model.MapThings.Walkable;
import com.badlogic.gdx.graphics.Texture;

import static com.Graphic.model.App.currentGame;


public class ForagingCrops extends Items {

    private final ForagingCropsType type;
    private boolean isProtected;


    public ForagingCrops(ForagingCropsType type) {
        this.type = type;
    }



    public void delete () {

        for (Tile tile : currentGame.bigMap)
            if (tile.getGameObject() == this)
                tile.setGameObject(new Walkable());
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

}
