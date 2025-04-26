package model.ToolsPackage;

import model.Enum.ToolsType.HoeType;

public class Hoe extends Tools {

    public Hoe(){
        super("Hoe", 0);
    }

    public HoeType hoeType=HoeType.primary;

    public void use (){}

}
