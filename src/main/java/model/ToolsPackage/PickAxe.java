package model.ToolsPackage;

import model.Enum.ToolsType.PickAxeType;

public class PickAxe extends Tools {

    public PickAxe(){
        super("PickAxe", 0);
    }

    public PickAxeType pickAxeType=PickAxeType.PrimaryPickAxe;

    public void use (){}

}
