package com.Graphic.View;

import com.Graphic.Controller.MainGame.Marketing;
import com.Graphic.model.Enum.Commands.MarketMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

import static com.Graphic.model.HelpersClass.Color_Eraser.RED;
import static com.Graphic.model.HelpersClass.Color_Eraser.RESET;

public class MarketMenu implements AppMenu {

    Marketing marketing=new Marketing();
    Matcher matcher;

    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();

        if ((matcher= MarketMenuCommands.buyAnimal.getMatcher(input)) != null)
            System.out.println(marketing.buyAnimal(matcher.group(1).trim() , matcher.group(2).trim()));

        else if ((matcher=MarketMenuCommands.buildBarnOrCage.getMatcher(input)) != null) {
            Integer x=Integer.parseInt(matcher.group(2).trim());
            Integer y=Integer.parseInt(matcher.group(3).trim());
            if (matcher.group(1).trim().equals("Well")) {
                System.out.println(marketing.createWell(x , y));
            }
            else if (matcher.group(1).trim().equals("Shipping Bin")) {
                System.out.println(marketing.createShippingBin(x , y));
            }
            else {
                System.out.println(marketing.createBarnOrCage(x, y, matcher.group(1).trim()));
            }
        }

        else if ((matcher=MarketMenuCommands.showAllProducts.getMatcher(input)) != null)
            System.out.println(marketing.showAllProducts(1));

        else if ((matcher=MarketMenuCommands.showAvailableProducts.getMatcher(input)) != null)
            System.out.println(marketing.showAllProducts(2));

        else if ((matcher=MarketMenuCommands.purchase.getMatcher(input)) != null) {
            Integer amount=null;
            if(matcher.group(2) != null) {
                amount=Integer.parseInt(matcher.group(2).trim());
            }
            System.out.println(marketing.purchase(matcher.group(1).trim(), amount));
        }

        else if ((matcher = MarketMenuCommands.toolsUpgrade.getMatcher(input)) != null)
            System.out.println(marketing.upgradeTool(matcher.group("name").trim()));

        else if (input.equals("exit")) {
            System.out.println(marketing.goToGameMenu());
        }
        else
            System.out.println(RED + "Invalid Command" + RESET);


    }
}
