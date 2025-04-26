package model.ToolsPackage;

import model.App;
import model.Enum.ItemType.TrashCanType;
import model.Items;

import java.util.HashMap;

public class TrashCan extends Tools {

    public TrashCanType Type=TrashCanType.primary;
    public TrashCan(){
        super("TrashCan", 0);
    }

    public void setType(TrashCanType type) {
        Type = type;
    }



    public static void removeItem (int money , HashMap<Items,Integer> x, Items items, Integer amount){
        App.currentPlayer.increeaseMoney(money);
        if (amount == null || x.get(items)==amount){
            x.remove(items);
        }
        else {
            x.compute(items, (k, l) -> l - amount);
        }
    } // اسمش باید عوض یشه هم ورودی منابعی که باید برگردونده بشه رو بگیره و درصد برگشت هم که از تو اینام بدست میاد

}
