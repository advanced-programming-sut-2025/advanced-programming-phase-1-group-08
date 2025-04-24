package model.ToolsPackage;

import model.Enum.ItemType.WateringCanType;

public class WateringCan extends Tools {

    private WateringCanType WateringCanType= model.Enum.ItemType.WateringCanType.primary;

    public WateringCan() {
        super("WateringCan", 0);
    }

    public void use (){}
}
