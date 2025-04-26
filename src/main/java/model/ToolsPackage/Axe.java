package model.ToolsPackage;

import model.Enum.ToolsType.AxeType;

public class Axe extends Tools {

    public Axe(){
        super("Axe", 0);
    }
    public AxeType axeType=AxeType.primary;

    public void use (){}
}
