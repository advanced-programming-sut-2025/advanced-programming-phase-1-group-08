package model.ToolsPackage;

import model.App;
import model.Enum.ItemType.TrashCanType;
import model.Items;

import java.util.HashMap;

public class TrashCan extends Tools {

    public TrashCanType type;
    public TrashCan(TrashCanType Type) {
        super("TrashCan");
        this.type=Type;
    }

    public void setType(TrashCanType type) {
        this.type = type;
    }



    public static void removeItem (int money , HashMap<Items,Integer> x, Items items, Integer amount){
        App.currentPlayer.increaseMoney(money);
        x.compute(items, (k, l) -> l - amount);
        x.entrySet().removeIf(e -> e.getValue() <= 0);
    } // اسمش باید عوض یشه هم ورودی منابعی که باید برگردونده بشه رو بگیره و درصد برگشت هم که از تو اینام بدست میاد


    @Override
    public int healthCost() { // TODO
        return 0;
    }

    @Override
    public String getName() {
        return type.name();
    }
}
