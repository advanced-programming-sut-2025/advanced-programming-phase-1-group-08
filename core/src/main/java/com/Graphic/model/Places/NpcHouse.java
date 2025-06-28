package com.Graphic.model.Places;

import model.Enum.NPC;

public class NpcHouse {
    private final NPC owner;

    NpcHouse(NPC owner) {
        this.owner = owner;
    }
    public NPC getOwner() {
        return owner;
    }
}
