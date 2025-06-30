package com.Graphic.model.MapThings;

import com.Graphic.model.Enum.ItemType.WallType;
import static com.Graphic.model.HelpersClass.Color_Eraser.*;

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
        return BG_BRIGHT_WHITE+"# ";
    }
}
