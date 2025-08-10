package com.Graphic.model.MapThings;

import com.Graphic.model.Enum.ItemType.MarketType;
import com.Graphic.model.Enum.ItemType.WallType;
import static com.Graphic.model.HelpersClass.Color_Eraser.*;

public class Wall extends GameObject {
    private WallType wallType;
    int index;

    public Wall() {

    }
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
            super.setTextureWidth(0.7f);
            super.setTextureHeight(1.5f);
            return Path ="Places/wall2.png";
        }
        return null;
    }

    @Override
    public int getRemindInShop(MarketType marketType) {
        return 0;
    }

    @Override
    public void setRemindInShop(int amount, MarketType marketType) {

    }
}
