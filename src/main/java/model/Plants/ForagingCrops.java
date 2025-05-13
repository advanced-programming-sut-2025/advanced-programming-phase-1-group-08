package model.Plants;

import model.Enum.AllPlants.ForagingCropsType;
import model.Items;
import model.MapThings.Tile;
import model.MapThings.Walkable;

import static model.App.bigMap;

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

        for (Tile tile : bigMap)
            if (tile.getGameObject().equals(this))
                tile.setGameObject(new Walkable());
    }

    @Override
    public String getName() {
        return type.getDisplayName();
    }
}
