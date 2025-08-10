package com.Graphic.model.Enum.NPC;

import com.Graphic.Controller.MainGame.GameControllerLogic;
import com.Graphic.Main;
import com.Graphic.model.Enum.ItemType.MarketType;
import com.Graphic.model.MapThings.GameObject;

public class NPCHouse extends GameObject {

    private String path;
    private NPC npc;

    public NPC getNpc() {
        return npc;
    }

    public NPCHouse() {

    }

    public NPCHouse(NPC npc) {
        this.npc = npc;
    }

    @Override
    public String getIcon() {

        if (path != null) {
            return path;
        }

        for (int i = npc.getTopLeftX();  i < npc.getTopLeftX() + npc.getWidth() ; i++) {
            for (int j = npc.getTopLeftY();  j < npc.getTopLeftY() + npc.getHeight() ; j++) {

                if (GameControllerLogic.getTileByCoordinates(i, j , Main.getClient(null).getLocalGameState()).getGameObject().equals(this)) {
                    int x = npc.getWidth() * (j - npc.getTopLeftY()) + i - npc.getTopLeftX() + 1;
                    return path = "Places/" + npc.name() + x + ".png";

                }
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
