package model.Plants;

import Controller.GameController;
import model.Enum.FoodTypes;
import model.Inventory;
import model.Items;
import model.Result;

import static model.App.currentGame;
import static model.Color_Eraser.RED;
import static model.Color_Eraser.RESET;

public class Food extends Items {
    private final FoodTypes type;

    public Food(FoodTypes type) {
        this.type = type;
    }

    public FoodTypes getType() {
        return type;
    }

    public static boolean checkInventorySpaceForFood(FoodTypes type) {
        GameController controller = new GameController();
        return controller.checkAmountProductAvailable(new Food(type), 1) ||
                currentGame.currentPlayer.getBackPack().getType().getRemindCapacity() > 0;
    }

    @Override
    public String getName() {
        return type.name();
    }

    @Override
    public int getSellPrice() {
        return type.getSellPrice();
    }
}
