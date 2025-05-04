package Controller;

import model.Enum.AllPlants.ForagingSeedsType;
import model.Enum.ItemType.*;
import model.Enum.ToolsType.FishingPoleType;
import model.Places.Market;
import model.Places.ShippingBin;
import model.Places.Well;
import model.Result;
import model.ToolsPackage.MilkPail;
import model.ToolsPackage.Shear;

import static model.App.currentPlayer;
import static model.App.markets;

public class Marketing {

    public MarketType findEnteredShopType() {
        for (Market market : markets) {
            if (currentPlayer.getPositionX() >= market.getTopLeftX() && currentPlayer.getPositionY() >= market.getTopLeftY()) {
                if (currentPlayer.getPositionX() < market.getTopLeftX() + market.getWidth() && currentPlayer.getPositionY() < market.getTopLeftY()) {
                    return market.getMarketType();
                }
            }
        }
        return null;
    }


    public Result MarnieProducts(int id) {
        StringBuilder result=new StringBuilder();
        if (id == 1) {
            result.append("Marnie's Ranch Products :\n");
        }
        else if (id == 2) {
            result.append("Marnie's Ranch available Products :\n");
        }
        for (AnimalType animalType : AnimalType.values()) {
            int remind=animalType.getRemindInShop();
            if (id == 1 || (id ==2 && remind > 0)) {
                result.append(animalType.name()).append(", Price: ").append(animalType.getPrice()).append("\n");
            }
        }
        result.append(MarketItem.Hay.getName()).append(", Price: ").append(MarketItem.Hay.getPrice(0)).append("\n");
        if (id ==1 || MilkPail.getRemindInShop() > 0) {
            result.append("Milk Pail").append(", Price: ").append(MilkPail.coinNeeded).append("\n");
        }
        if (id == 1 || Shear.getRemindInshop() > 0) {
            result.append("Shear").append(", Price: ").append(Shear.getCoinNeeded()).append("\n");
        }

        return new Result(true , result.toString());
    }

    public Result StardropSaloonProduct( int id) {
        StringBuilder result=new StringBuilder();
        if (id == 1) {
            result.append(MarketType.StardropSaloon.name()).append(" Products:").append("\n");
        }
        else if (id == 2) {
            result.append(MarketType.StardropSaloon.name()).append(" available Products:").append("\n");
        }
        for (MarketItem marketItem : MarketItem.values()) {
            if (marketItem.getMarketTypes().contains(MarketType.StardropSaloon)) {
                int remind= marketItem.getOtherShopsLimit();
                if (id == 1 || remind > 0 ) {
                    result.append(marketItem.getName()).append(", Price: ").append(marketItem.getPrice(0)).append("\n");
                }
            }

        }
        return new Result(true , result.toString());
    }

    public Result CarpenterProducts(int id) {
        StringBuilder result=new StringBuilder();
        if (id == 1) {
            result.append(MarketType.CarpenterShop.name()).append(" Products:").append("\n");
        }
        else if (id == 2) {
            result.append(MarketType.CarpenterShop.name()).append(" available Products:").append("\n");
        }
        for (BarnORCageType barnORCageType : BarnORCageType.values()) {
            int remind = barnORCageType.getShopLimit();
            if (id == 1 || remind > 0 ) {
                result.append(barnORCageType.name()).append(", Price: ").append(barnORCageType.getPrice()).append(", Wood: ")
                        .append(barnORCageType.getWoodNeeded()).append(", Stone: ").append(barnORCageType.getStoneNeeded()).append("\n");
            }
        }

        if (id == 1 || Well.getRemindInShop() > 0) {
            result.append("Well, Price: ").append(Well.getNeededCoin()).append(", Stone: ").append(Well.getNeededStone()).append("\n");
        }
        if (id ==1 || ShippingBin.getRemindInShop() > 0) {
            result.append("Shipping Bin, Price: ").append(ShippingBin.getCoinNeeded()).append(", Wood: ").append(ShippingBin.getWoodNeeded()).append("\n");
        }

        for (MarketItem marketItem : MarketItem.values()) {
            if (marketItem.getMarketTypes().contains(MarketType.CarpenterShop)) {
                int remind= marketItem.getOtherShopsLimit();
                if (id == 1 || remind > 0 ) {
                    result.append(marketItem.getName()).append(", Price: ").append(marketItem.getPrice(0)).append("\n");
                }
            }
        }

        return new Result(true , result.toString());
    }

    public Result JojaMartProducts(int id) {
        StringBuilder result=new StringBuilder();
        if (id == 1) {
            result.append(MarketType.JojaMart.name()).append(" Products:").append("\n");
        }
        else if (id == 2) {
            result.append(MarketType.JojaMart.name()).append(" available Products:").append("\n");
        }
        for (ForagingSeedsType foragingSeedsType : ForagingSeedsType.values()) {
            if (foragingSeedsType.getMarketTypes().contains(MarketType.JojaMart)) {
                int remind = foragingSeedsType.JojaMartLimit;
                if (id == 1 || remind > 0 ) {
                    int price=foragingSeedsType.getPrice(MarketType.JojaMart);
                    result.append(foragingSeedsType.name()).append(", Price: ").append(price).append("\n");
                }
            }
        }
        for (MarketItem marketItem : MarketItem.values()) {
            if (marketItem.getMarketTypes().contains(MarketType.JojaMart)) {
                int remind = marketItem.getOtherShopsLimit();
                if (id == 1 || remind > 0 ) {
                    result.append(marketItem.getName()).append(", Price: ").append(marketItem.getPrice(0)).append("\n");
                }
            }
        }
        return new Result(true , result.toString());
    }

    public Result PierreProducts(int id) {
        StringBuilder result=new StringBuilder();
        if (id == 1) {
            result.append(MarketType.PierreGeneralStore.name()).append(" Products:").append("\n");
        }
        else if (id == 2) {
            result.append(MarketType.PierreGeneralStore.name()).append(" available Products:").append("\n");
        }
        for (ForagingSeedsType foragingSeedsType : ForagingSeedsType.values()) {
            if (foragingSeedsType.getMarketTypes().contains(MarketType.PierreGeneralStore)) {
                int remind = foragingSeedsType.PierrGeneralLimit;
                if (id == 1 || remind > 0 ) {
                    int price=foragingSeedsType.getPrice(MarketType.PierreGeneralStore);
                    result.append(foragingSeedsType.name()).append(", Price: ").append(price).append("\n");
                }
            }
        }
        for (MarketItem marketItem : MarketItem.values()) {
            if (marketItem.getMarketTypes().contains(MarketType.PierreGeneralStore)) {
                int remind = marketItem.getPierreShopsLimit();
                if (id == 1 || remind > 0 ) {
                    result.append(marketItem.getName()).append(", Price: ").append(marketItem.getPrice(1)).append("\n");
                }
            }
        }
        for (BackPackType backPackType : BackPackType.values()) {
            if (backPackType.getInitialShopLimit() == 1) {
                int remind = backPackType.getRemindInShop();
                if (id == 1 || remind > 0 ) {
                    result.append(backPackType.getName()).append(", Price: ").append(backPackType.getPrice()).append("\n");
                }
            }
        }
        return new Result(true , result.toString());
    }

    public Result FishSoupProducts(int id) {
        StringBuilder result=new StringBuilder();
        if (id == 1) {
            result.append(MarketType.FishShop.name()).append(" Products:").append("\n");
        }
        else if (id == 2) {
            result.append(MarketType.FishShop.name()).append(" available Products:").append("\n");
        }
        for (MarketItem marketItem : MarketItem.values()) {
            if (marketItem.getMarketTypes().contains(MarketType.FishShop)) {
                int remind = marketItem.getOtherShopsLimit();
                if (id == 1 || remind > 0 ) {
                    result.append(marketItem.getName()).append(", Price: ").append(marketItem.getPrice(0)).append("\n");
                }
            }
        }
        for (FishingPoleType fishingPoleType : FishingPoleType.values()) {
            int remind = fishingPoleType.getshopLimit();
            if (id == 1 || remind > 0 ) {
                result.append(fishingPoleType.getName()).append(", Price: ").append(fishingPoleType.getPrice()).append("\n");
            }
        }
        return new Result(true , result.toString());
    }

    public Result showAllProducts(int id) {
        MarketType marketType = findEnteredShopType();

        if (marketType == null) {
            return new Result(false , "you are not in any shop");
        }
        switch (marketType) {
            case MarnieRanch -> {return MarnieProducts(id) ; }
            case StardropSaloon -> {return StardropSaloonProduct(id) ; }
            case CarpenterShop -> {return CarpenterProducts(id) ; }
            case JojaMart -> {return JojaMartProducts(id) ; }
            case PierreGeneralStore -> {return PierreProducts(id) ; }
            case FishShop -> {return FishSoupProducts(id) ; }
        }

    }
}
