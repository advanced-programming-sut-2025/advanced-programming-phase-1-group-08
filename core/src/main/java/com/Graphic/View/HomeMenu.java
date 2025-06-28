package com.Graphic.View;

import com.Graphic.Controller.MainGame.CraftingController;
import com.Graphic.Controller.MainGame.InputGameController;
import com.Graphic.Controller.MainGame.HomeController;
import com.Graphic.model.App;
import com.Graphic.model.Enum.Commands.GameMenuCommands;
import com.Graphic.model.Enum.Commands.HomeMenuCommands;
import com.Graphic.model.Enum.Menu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class HomeMenu extends AppView implements AppMenu{


    CraftingController craftingController=new CraftingController();
    HomeController homeController=new HomeController();
    @Override
    public void check(Scanner scanner) {
        boolean startCrafting = false;
        String input = scanner.nextLine();
        Matcher matcher;
        if (HomeMenuCommands.startCooking.getMatcher(input) != null) {
            HomeController.cook();
        }


        else if (input.trim().toLowerCase().matches("\\s*show\\s+current\\s+menu\\s*"))
            System.out.println("Home Menu");
        else if (input.toLowerCase().matches("\\s*exit\\s*"))
            App.currentMenu = Menu.GameMenu;

        else if ((matcher=HomeMenuCommands.startCrafting.getMatcher(input))!=null)
            System.out.println(homeController.goToCraftingMenu());


        else if((matcher = GameMenuCommands.inventoryShow.getMatcher(input)) != null) {
            InputGameController controller = new InputGameController();
            System.out.println(controller.showInventory());
        }

        else if ((matcher=GameMenuCommands.addItem.getMatcher(input)) != null) {
            InputGameController controller = new InputGameController();
            System.out.println(controller.addItem(matcher.group(1), Integer.parseInt(matcher.group(2).trim())));
        }



        else
            System.out.println("Invalid Command!");
    }
}
