package model.MapThings;

import model.Enum.ItemType.WallType;

public class Wall extends GameObject {
    private WallType wallType;

    public void setWallType(WallType wallType) {
        this.wallType = wallType;
    }

    public WallType getWallType() {
        return wallType;
    }

    public int height;

}
