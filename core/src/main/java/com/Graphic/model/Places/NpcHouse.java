package com.Graphic.model.Places;

import com.Graphic.model.Enum.NPC.NPC;

public class NpcHouse {
    private final NPC owner;

    NpcHouse(NPC owner) {
        this.owner = owner;
    }
    public NPC getOwner() {
        return owner;
    }
}
