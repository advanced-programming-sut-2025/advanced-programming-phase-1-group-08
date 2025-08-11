package com.Graphic.model.Places;

import com.Graphic.Controller.MainGame.GameControllerLogic;
import com.Graphic.Main;
import com.Graphic.model.Enum.ItemType.MarketType;
import com.Graphic.model.MapThings.GameObject;

public class Market extends GameObject {
    private MarketType marketType;


    public Market(MarketType marketType) {
        this.marketType = marketType;
    }

    public Market() {

    }

    public MarketType getMarketType() {
        return marketType;
    }

    @Override
    public String getIcon() {
        if (Path != null) {
            return Path;
        }
        for (int i = marketType.getTopleftx();  i < marketType.getTopleftx() + marketType.getWidth() ; i++) {
            for (int j = marketType.getToplefty();  j < marketType.getToplefty() + marketType.getHeight() ; j++) {
                if (GameControllerLogic.getTileByCoordinates(i, j , Main.getClient().getLocalGameState()).getGameObject().equals(this)) {
                    int x = marketType.getWidth() * (j - marketType.getToplefty()) + i - marketType.getTopleftx() + 1;
                    return Path = "Places/"+marketType.getName()+x+".png";

                }
            }
        }
        return null;
    }

    public static MarketType isInMarket(int x , int y) {
        for (MarketType marketType : MarketType.values()) {
            int tlx=marketType.getTopleftx();
            int tly=marketType.getToplefty();
            if(x >= tlx && x <= tlx + marketType.getWidth() -1 && y >= tly && y <= tly + marketType.getHeight() - 1) {
                return marketType;
            }
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
