package model;

import model.Enum.ItemType.CraftType;

public class CraftingItem extends Items {
    private CraftType craftType;
    public CraftingItem(CraftType craftType) {
        this.craftType = craftType;
    }
    public CraftType getCraftType() {
        return craftType;
    }

}
