package View;

import Controller.Marketing;
import model.Enum.Commands.GameMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MarketMenu implements AppMenu {

    Marketing marketing=new Marketing();
    Matcher matcher;

    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();

        if ((matcher= GameMenuCommands.buyAnimal.getMatcher(input)) != null)
            System.out.println(marketing.buyAnimal(matcher.group(0).trim() , matcher.group(1).trim()));

        else if ((matcher=GameMenuCommands.buildBarnOrCage.getMatcher(input)) != null) {
            Integer x=Integer.parseInt(matcher.group(2).trim());
            Integer y=Integer.parseInt(matcher.group(3).trim());
            System.out.println(marketing.createBarnOrCage( x , y , matcher.group(1).trim()));
        }

        else if ((matcher=GameMenuCommands.showAllProducts.getMatcher(input)) != null)
            System.out.println(marketing.showAllProducts(1));

        else if ((matcher=GameMenuCommands.showAvailableProducts.getMatcher(input)) != null)
            System.out.println(marketing.showAllProducts(2));

        else if ((matcher=GameMenuCommands.purchase.getMatcher(input)) != null) {
            Integer amount=null;
            if(matcher.group(2) != null) {
                amount=Integer.parseInt(matcher.group(2).trim());
            }
            System.out.println(marketing.purchase(matcher.group(1).trim(), amount));
        }


    }
}
