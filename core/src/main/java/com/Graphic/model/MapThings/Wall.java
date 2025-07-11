package com.Graphic.model.MapThings;

import com.Graphic.model.Enum.ItemType.WallType;
import static com.Graphic.model.HelpersClass.Color_Eraser.*;

public class Wall extends GameObject {
    private WallType wallType;
    int index;
    private String Path;

    public Wall(int index){
        this.index=index;
    }

    public void setWallType(WallType wallType) {
        this.wallType = wallType;
    }

    public WallType getWallType() {
        return wallType;
    }

    public int height;

    @Override
    public String getIcon() {
        if (Path != null) {
            return Path;
        }
        if (index == 0) {
            return Path ="Places/wall1.png";
        }
        else if (index == 1) {
            super.setTextureWidth(1);
            super.setTextureHeight(2);
            return Path ="Places/wall2.png";
        }
        return null;
    }
}
