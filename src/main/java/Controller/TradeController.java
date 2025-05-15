package Controller;

import model.*;
import model.Enum.AllPlants.CropsType;
import model.Enum.AllPlants.ForagingCropsType;
import model.Enum.AllPlants.TreesProductType;
import model.Enum.Commands.TradeMenuCommands;
import model.Enum.ItemType.MarketItemType;
import model.Plants.AllCrops;
import model.Plants.ForagingCrops;
import model.Plants.TreesProdct;

import java.util.*;

import static model.App.*;
import static model.Color_Eraser.*;
import static model.Trade.checkTradeRequest;


public class TradeController {

    private boolean tempCheck (Result result) {
        if (result.massage().contains("Not Enough Money to Accept!"))
            return true;
        if (result.massage().contains("Item Not Found in Inventory!"))
            return true;
        if (result.massage().contains("Not Enough Item!"))
            return true;
        if (result.massage().contains("OK!"))
            return true;

        return false;
    }

    public void tradeStart () {
        System.out.println("\nDisplaying Trade Requests/Offers...");
        for (List<Trade> tradeList: currentGame.trades.values()) {
            for (Trade t: tradeList) {
                if (t.getReceiver().getUsername().equals(currentGame.currentPlayer.getUsername()) && !t.isResponded()) {
                    t.print();

                    System.out.println("What's Your Response?");
                    Scanner scanner = new Scanner(System.in);
                    String respond;
                    Result result;
                    do {
                        respond = scanner.nextLine();
                        result = Trade.CheckTradeRespond(respond, t.getId());
                        System.out.println(result.massage());
                    } while (!(result.IsSuccess() || tempCheck(result)));
                    if (result.IsSuccess())
                        t.setResponded(true);
                }
            }
        }
        System.out.println(GREEN+"Trades Done!"+RESET);

        System.out.println();

        System.out.println(BLUE+"Players To Trade With: "+RESET);
        for (User p: currentGame.players) {
            if (!p.getUsername().equals(currentGame.currentPlayer.getUsername())) {
                //TODO tradeUsers.add(p);
                System.out.println(p.getNickname() + " (Username: " + p.getUsername() + ")");
            }
        }
        User other = null;
        Scanner scanner = new Scanner(System.in);
        String input;
        Trade trade = null;

        Result result = null;
        while (true) {
            input = scanner.nextLine();

            if (input.matches("\\s*exit\\s*"))
                return;
            if (TradeMenuCommands.tradeP.getMatcher(input) != null) {
                result = checkTradeRequest(input, 'p');
                if (!result.IsSuccess()) {
                    System.out.println(result.massage());
                    continue;
                }
                String pUsername = TradeMenuCommands.tradeP.getMatcher(input).group("username");
                if (pUsername == null) {
                    System.out.println(RED+"User Not Found!"+RESET);
                    return;
                }
                for (User player: currentGame.players) {
                    if (player.getUsername().equals(pUsername)) {
                        other = player;
                    }
                }
                if (other == null) {
                    System.out.println(RED+"Player Not Found!"+RESET);
                    return;
                }

                if (TradeMenuCommands.tradeP.getMatcher(input).group("type").equals("offer"))
                    trade = new Trade(currentGame.currentPlayer, other, 't', 'p', TradeMenuCommands.tradeP.getMatcher(input).group("item"), TradeMenuCommands.tradeP.getMatcher(input).group("price"),
                                        Integer.parseInt(TradeMenuCommands.tradeP.getMatcher(input).group("amount")), Integer.parseInt(TradeMenuCommands.tradeP.getMatcher(input).group("price")));
                else if (TradeMenuCommands.tradeP.getMatcher(input).group("type").equals("request"))
                    trade = new Trade(currentGame.currentPlayer, other, 'p', 't', TradeMenuCommands.tradeP.getMatcher(input).group("price"), TradeMenuCommands.tradeP.getMatcher(input).group("item"),
                                        Integer.parseInt(TradeMenuCommands.tradeP.getMatcher(input).group("price")), Integer.parseInt(TradeMenuCommands.tradeP.getMatcher(input).group("amount")));

            }
            else if (TradeMenuCommands.tradeTI.getMatcher(input) != null) {
                result = checkTradeRequest(input, 't');
                if (!result.IsSuccess()) {
                    System.out.println(result.massage());
                    continue;
                }
                String pUsername = TradeMenuCommands.tradeTI.getMatcher(input).group("username");
                if (pUsername == null) {
                    System.out.println(RED+"User Not Found!"+RESET);
                    return;
                }
                for (User player: currentGame.players) {
                    if (player.getUsername().equals(pUsername)) {
                        other = player;
                    }
                }
                if (other == null) {
                    System.out.println(RED+"Player Not Found!"+RESET);
                    return;
                }

                trade = new Trade(currentGame.currentPlayer, other, 't', 't', TradeMenuCommands.tradeTI.getMatcher(input).group("item"), TradeMenuCommands.tradeTI.getMatcher(input).group("targetItem"),
                                    Integer.parseInt(TradeMenuCommands.tradeTI.getMatcher(input).group("amount")), Integer.parseInt(TradeMenuCommands.tradeTI.getMatcher(input).group("targetAmount")));

            }
            else {
                System.out.println(RED+"Invalid Trade!"+RESET);
                continue;
            }
            break;
        }

        try {
            Set<User> key = new HashSet<>(Arrays.asList(currentGame.currentPlayer, other));
            currentGame.trades.putIfAbsent(key, new ArrayList<>());
            currentGame.trades.get(key).add(trade);
            System.out.println(GREEN + "Trade Offer/Request Sent Successfully." + RESET);
        } catch (Exception e) {
            System.out.println(RED + "Not Correct Format! Try Again." + RESET);
            return;
        }

    }
}