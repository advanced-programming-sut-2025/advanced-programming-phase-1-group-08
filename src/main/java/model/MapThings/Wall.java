package model.MapThings;

import model.Enum.ItemType.WallType;
import static model.Color_Eraser.*;

public class Wall extends GameObject {
    private WallType wallType;

    public void setWallType(WallType wallType) {
        this.wallType = wallType;
    }

    public WallType getWallType() {
        return wallType;
    }

    public int height;

    @Override
    public String getIcon() {
        return BG_BRIGHT_BLACK+"# ";
    }
}
