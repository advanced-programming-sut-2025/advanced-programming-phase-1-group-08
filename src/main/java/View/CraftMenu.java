package View;

import Controller.MainGame.CraftingController;
import model.Enum.Commands.CraftMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

import static model.Color_Eraser.RED;
import static model.Color_Eraser.RESET;

public class CraftMenu implements AppMenu {
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher;

        CraftingController controller = new CraftingController();

        if ((matcher = CraftMenuCommands.craftingRecipe.getMatcher(input)) != null) {
            System.out.println(controller.showCraftingRecipe());
        }
        else if ((matcher = CraftMenuCommands.craftingCraft.getMatcher(input)) != null) {
            System.out.println(controller.craftingCraft(matcher.group(1).trim()));
        }
        else if ((matcher=CraftMenuCommands.exit.getMatcher(input)) != null) {
            System.out.println(controller.goToGameMenu());
        }
        else
            System.out.println(RED+"Invalid command"+RESET);
    }
}
