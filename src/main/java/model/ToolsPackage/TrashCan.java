package model.ToolsPackage;

import model.Enum.ItemType.TrashCanType;

public class TrashCan extends Tools {

    private TrashCanType Type=TrashCanType.primary;
    public TrashCan(){
        super("TrashCan", 0);
    }


    public void removeItem (){} // اسمش باید عوض یشه هم ورودی منابعی که باید برگردونده بشه رو بگیره و درصد برگشت هم که از تو اینام بدست میاد

}
