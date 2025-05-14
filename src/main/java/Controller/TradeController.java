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
import static model.SaveData.UserDataBase.findUserByUsername;
import static model.Trade.checkTradeRequest;


public class TradeController {

    public void tradeStart () {
        System.out.println("Players To Trade With: ");
        for (User p: currentGame.players) {
            if (!p.getUsername().equals(currentGame.currentPlayer.getUsername())) {
                //todo tradeUsers.add(p);
                System.out.println(p.getNickname());
            }
        }
        User other;
        Scanner scanner = new Scanner(System.in);
        String input;
        Trade trade = null;
        while (true) {
            input = scanner.nextLine();
            Result result = null;
            if (TradeMenuCommands.tradeP.getMatcher(input) != null) {
                result = checkTradeRequest(input, 'p');
                other = findUserByUsername(TradeMenuCommands.tradeP.getMatcher(input).group("username"));

                if (TradeMenuCommands.tradeP.getMatcher(input).group("type").equals("offer"))
                    trade = new Trade(currentGame.currentPlayer, other, 't', 'p', TradeMenuCommands.tradeP.getMatcher(input).group("item"), TradeMenuCommands.tradeP.getMatcher(input).group("price"),
                                        Integer.parseInt(TradeMenuCommands.tradeP.getMatcher(input).group("amount")), Integer.parseInt(TradeMenuCommands.tradeP.getMatcher(input).group("price")));
                else if (TradeMenuCommands.tradeP.getMatcher(input).group("type").equals("request"))
                    trade = new Trade(currentGame.currentPlayer, other, 'p', 't', TradeMenuCommands.tradeP.getMatcher(input).group("price"), TradeMenuCommands.tradeP.getMatcher(input).group("item"),
                                        Integer.parseInt(TradeMenuCommands.tradeP.getMatcher(input).group("price")), Integer.parseInt(TradeMenuCommands.tradeP.getMatcher(input).group("amount")));

            }
            else if (TradeMenuCommands.tradeTI.getMatcher(input) != null) {
                result = checkTradeRequest(input, 't');
                other = findUserByUsername(TradeMenuCommands.tradeTI.getMatcher(input).group("username"));

                trade = new Trade(currentGame.currentPlayer, other, 't', 't', TradeMenuCommands.tradeP.getMatcher(input).group("item"), TradeMenuCommands.tradeP.getMatcher(input).group("targetItem"),
                                    Integer.parseInt(TradeMenuCommands.tradeTI.getMatcher(input).group("amount")), Integer.parseInt(TradeMenuCommands.tradeTI.getMatcher(input).group("targetAmount")));

            }
            else {
                System.out.println(RED+"Invalid Trade!"+RESET);
                continue;
            }
            if (!result.IsSuccess()) {
                System.out.println(result.massage());
            }
            if (result.IsSuccess())
                break;
        }

        try {
            Set<User> key = new HashSet<>(Arrays.asList(currentGame.currentPlayer, other));
            currentGame.trades.putIfAbsent(key, new ArrayList<>());
            currentGame.trades.get(key).add(trade);
        } catch (Exception e) {
            System.out.println(RED+"Not Correct Format! Try Again."+RESET);
            return;
        }

    }
}