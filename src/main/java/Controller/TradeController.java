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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static model.App.*;
import static model.Color_Eraser.*;
import static model.Trade.checkTradeRequest;
import static model.Trade.tradeUsers;


public class TradeController {

    public void tradeStart () {
        System.out.println("Players To Trade With: ");
        for (User p: players) {
            if (!p.getUsername().equals(currentPlayer.getUsername())) {
                tradeUsers.add(p);
                System.out.println(p.getNickname());
            }
        }

        Scanner scanner = new Scanner(System.in);
        String input;
        while (true) {
            input = scanner.nextLine();
            Result result = null;
            if (TradeMenuCommands.tradeP.getMatcher(input) != null) {
                result = checkTradeRequest(input, 'p');
            }
            else if (TradeMenuCommands.tradeTI.getMatcher(input) != null) {
                result = checkTradeRequest(input, 't');
            }
            else {
                System.out.println(RED+"Invalid Trade!"+RESET);
            }
            assert result != null;
            if (!result.IsSuccess()) {
                System.out.println(result.massage());
            }
            if (result.IsSuccess())
                break;
        }


    }
}