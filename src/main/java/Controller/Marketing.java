package Controller;

import model.*;
import model.Animall.Animal;
import model.Animall.BarnOrCage;
import model.Enum.AllPlants.ForagingMineralsType;
import model.Enum.AllPlants.ForagingSeedsType;
import model.Enum.AllPlants.TreesSourceType;
import model.Enum.ItemType.*;
import model.Enum.ToolsType.FishingPoleType;
import model.MapThings.BasicRock;
import model.MapThings.Tile;
import model.MapThings.Walkable;
import model.MapThings.Wood;
import model.OtherItem.BarsAndOres;
import model.OtherItem.MarketItem;
import model.Places.ShippingBin;
import model.Places.Well;
import model.Plants.ForagingMinerals;
import model.Plants.ForagingSeeds;
import model.Plants.TreeSource;
import model.ToolsPackage.FishingPole;
import model.ToolsPackage.MilkPail;
import model.ToolsPackage.Shear;

import java.util.Map;

import static model.App.*;

public class Marketing {

    public MarketType findEnteredShopType() {
        for (MarketType market : MarketType.values()) {
            if (currentPlayer.getPositionX() >= market.getTopleftx() && currentPlayer.getPositionY() >= market.getToplefty()) {
                if (currentPlayer.getPositionX() < market.getTopleftx() + market.getWidth() && currentPlayer.getPositionY() < market.getToplefty() + market.getHeight() ) {
                    return market;
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
        result.append(MarketItemType.Hay.getName()).append(", Price: ").append(MarketItemType.Hay.getPrice(0)).append("\n");
        if (id ==1 || MilkPail.getRemindInShop() > 0) {
            result.append("Milk Pail").append(", Price: ").append(MilkPail.coinNeeded).append("\n");
        }
        if (id == 1 || Shear.getRemindInshop() > 0) {
            result.append("Shear").append(", Price: ").append(Shear.getCoinNeeded()).append("\n");
        }

        return new Result(true , result.toString());
    }

    public Result BlackSmithProducts() {
        StringBuilder result=new StringBuilder();
        result.append("Copper Ore, ").append("Price: ").append("75\n");
        result.append("Iron Ore, ").append("Price: ").append("150\n");
        result.append("Gold Ore, ").append("Price: ").append("400\n");
        result.append("Coal, ").append("Price: ").append("150\n");
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
        for (MarketItemType marketItem : MarketItemType.values()) {
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

        for (MarketItemType marketItem : MarketItemType.values()) {
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
        for (MarketItemType marketItem : MarketItemType.values()) {
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
        for (MarketItemType marketItem : MarketItemType.values()) {
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
        for (MarketItemType marketItem : MarketItemType.values()) {
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
            case Blacksmith -> {return  BlackSmithProducts() ;}
        }

        return null;

    }


    public void takeAnimalToBarnOrCage(Animal animal , BarnOrCage barnOrCage ) {
        GameController gameController = new GameController();
        for (int x=barnOrCage.topLeftX ; x < barnOrCage.getBarnORCageType().getWidth() ; x++) {
            for (int y=barnOrCage.topLeftY ; y< barnOrCage.getBarnORCageType().getHeight() ; y++) {

                Tile tile=gameController.getTileByCoordinates(x,y);

                if (tile.getGameObject() instanceof Walkable) {
                    tile.setGameObject(animal);
                    animal.setPositionX(x);
                    animal.setPositionY(y);
                    return;
                }
            }
        }
    }


    public Result buyAnimal(String animal ,String name) {
        AnimalType animalType=null;
        for (AnimalType animalType1 : AnimalType.values()) {
            if (animalType1.name().equals(animal)) {
                animalType=animalType1;
            }
        }
        if (animalType==null) {
            return new Result(false , "No such type of Animal!");
        }
        for (BarnOrCage barnOrCage : currentPlayer.BarnOrCages) {
            for (Animal animals : barnOrCage.getAnimals()) {
                if (animals.getName().equals(animal)) {
                    return new Result(false , "You already have Animal with name "+animal);
                }
            }
        }
        if (currentPlayer.getMoney() < animalType.getPrice()) {
            return new Result(false , "You don't have enough money to buy this animal");
        }
        if (animalType.getRemindInShop() == 0) {
            return new Result(false , "The purchase limit for this product has been reached");
        }
        boolean canBuyAnimal = false;
        for (BarnOrCage barnOrCage : currentPlayer.BarnOrCages) {
            if (animalType.getBarnorcages().contains(barnOrCage.getBarnORCageType())) {
                if (barnOrCage.getReminderCapacity() > 0) {
                    canBuyAnimal = true;
                    Animal newAnimal = new Animal(animalType , 0 , name , false , false , false , false , currentDate.getDate());
                    takeAnimalToBarnOrCage(newAnimal , barnOrCage);
                    barnOrCage.animals.add(newAnimal);
                    animalType.increaseRemindInShop(-1);
                }
            }
        }
        if (! canBuyAnimal) {
            return new Result(false , "you don't have enough capacity or suitable place for this animal");
        }

        return new Result(true , "you bought this animal successfully");

    }

    public Result createBarnOrCage(int topLeftX, int topLeftY, String name) {
        BarnORCageType barnORCageType=null;
        for (BarnORCageType x : BarnORCageType.values()) {
            if (x.name().equals(name)) {
                barnORCageType=x;
            }
        }

        if (barnORCageType==null) {
            return new Result(false , "No such type of BarnORCage!");
        }

        Inventory inventory = currentPlayer.getBackPack().inventory;
        GameController gameController = new GameController();

        if (findEnteredShopType() != MarketType.MarnieRanch) {
            return new Result(false , "you can't create a barn or cage because you are not in Marnie's Ranch Market");
        }

        if (!gameController.checkTilesForCreateBarnOrCage(topLeftX, topLeftY, barnORCageType.getWidth(), barnORCageType.getHeight())) {
            return new Result(false, "you can't create barn or cage on this coordinate!");
        }

        int Wood = 0;
        int Stone= 0;
        for (Map.Entry <Items , Integer> entry:inventory.Items.entrySet()) {
            if (entry.getKey() instanceof Wood) {
                Wood=entry.getValue();
            }
            if (entry.getKey() instanceof BasicRock) {
                Stone=entry.getValue();
            }
        }

        if (barnORCageType.getWoodNeeded() > Wood) {
            return new Result(false , "you can't create barn or cage because you don't have enough wood!");
        }
        if (barnORCageType.getStoneNeeded() > Stone) {
            return new Result(false , "you can't create barn or cage because you don't have enough stone!");
        }
        if (barnORCageType.getPrice() > currentPlayer.getMoney() ) {
            return new Result(false , "you can't create barn or cage because you don't have enough money!");
        }

        BarnOrCage barnOrCage = new BarnOrCage(barnORCageType, topLeftX, topLeftY);

        for (Map.Entry <Items , Integer> entry:inventory.Items.entrySet()) {
            if (entry.getKey() instanceof Wood) {
                entry.setValue(entry.getValue() - Wood);
            }
            if (entry.getKey() instanceof BasicRock) {
                entry.setValue(entry.getValue() - Stone);
            }
        }


        currentPlayer.increaseMoney(- barnORCageType.getPrice());

        if (barnORCageType.equals(BarnORCageType.Barn) || barnORCageType.equals(BarnORCageType.BigBarn)
                || barnORCageType.equals(BarnORCageType.DeluxeBarn)) {
            barnOrCage.setCharactor('b');
        }
        else {
            barnOrCage.setCharactor('c');
        }

        for (int i = topLeftX; i < topLeftX + barnORCageType.getWidth(); i++) {
            for (int j = topLeftY; j < topLeftY + barnORCageType.getHeight(); j++) {

                if (i == topLeftX || i == topLeftX + barnORCageType.getWidth() -1 || j == topLeftY || j == topLeftY + barnORCageType.getHeight() -1) {
                    Tile tile = gameController.getTileByCoordinates(i , j );
                    tile.setGameObject(barnOrCage);
                }
            }
        }

        return new Result(true, barnORCageType.getName() + "created successfully!");

    }

    public Result purchaseFromBlackSmith(String name , Integer amount) {
        Inventory inventory = currentPlayer.getBackPack().inventory;
        if (amount == null) {
            amount = 1;
        }
        if (name.equals("Coal") ) {
            for (Map.Entry <Items , Integer> entry : inventory.Items.entrySet()) {
                if (entry.getKey() instanceof ForagingMinerals) {
                    if (((ForagingMinerals) entry.getKey()).getType().equals(ForagingMineralsType.COAL)) {
                        if (currentPlayer.getMoney() >= 150 * amount) {
                            currentPlayer.increaseMoney( - 150 * amount);
                            inventory.Items.put(entry.getKey(), entry.getValue() + amount);
                            return new Result(true, name + "was purchased successfully");
                        }
                        else {
                            return new Result(false , "Not enough money to purchase "+name);
                        }
                    }
                }
            }
            if (currentPlayer.getBackPack().getType().getRemindCapacity() > 0) {
                if (currentPlayer.getMoney() >= 150 * amount) {
                    currentPlayer.increaseMoney( - 150 * amount);
                    ForagingMinerals Coal = new ForagingMinerals(ForagingMineralsType.COAL);
                    return new Result(true, name + "was purchased successfully");
                }
                else {
                    return new Result(false , "Not enough money to purchase "+name);
                }
            }
            return new Result(false , "not enough capacity in your backpack");
        }
        BarsAndOres b = null;
        for (BarsAndOreType barsAndOreType : BarsAndOreType.values()) {
            if (barsAndOreType.name().equals(name) && barsAndOreType.getMarketType() != null) {
                b = new BarsAndOres(barsAndOreType);
                break;
            }
        }
        if (b == null) {
            return new Result(false , "Products Not found");
        }
        for (Map.Entry <Items , Integer> entry : inventory.Items.entrySet()) {
            if (entry.getKey() instanceof BarsAndOres) {
                if (((BarsAndOres) entry.getKey()).getType().equals(b.getType())) {
                    if (currentPlayer.getMoney() >= ((BarsAndOres) entry.getKey()).getType().getPrice() * amount) {
                        currentPlayer.increaseMoney( - ((BarsAndOres) entry.getKey()).getType().getPrice() * amount);
                        inventory.Items.put(entry.getKey(), entry.getValue() + amount);
                        return new Result(true, name + "was purchased successfully");
                    }
                    else {
                        return new Result(false , "Not enough money to purchase "+name);
                    }
                }
            }
        }
        if (currentPlayer.getBackPack().getType().getRemindInShop() > 0) {
            if (currentPlayer.getMoney() >= b.getType().getPrice() * amount) {
                inventory.Items.put(b, amount);
                return new Result(true, name + "was purchased successfully");
            }
            else {
                return new Result(false , "Not enough money to purchase "+name);
            }
        }
        return new Result(false , "not enough capacity in your backpack");

    }


    public Result purchaseFromMarnieRanch(String name , Integer amount) {

        Inventory inventory = currentPlayer.getBackPack().inventory;

        if (name.equals("Hay")) {
            if (amount == null) {
                amount = 1;
            }
            if (amount > MarketItemType.Hay.getOtherShopsLimit()) {
                return new Result(false , "The purchase limit for this product has been reached");
            }
            if (MarketItemType.Hay.getPrice(0) * amount > currentPlayer.getMoney()){
                return new Result(false , "not enough money to purchase this product");
            }
            if (currentPlayer.getBackPack().getType().getRemindCapacity() == 0) {
                return new Result(false , "Not enough capacity in your backpack");
            }

            MarketItem Hay=new MarketItem(MarketItemType.Hay);
            inventory.Items.put(Hay , amount);
            currentPlayer.increaseMoney(- amount * MarketItemType.Hay.getPrice(0));
            MarketItemType.Hay.increaseOtherShopsLimit(-amount);
            return new Result(false , "you bought this product successfully");
        }
        if (name.equals("Milk Pail")) {
            if (amount == null) {
                amount = 1;
            }
            if (amount > MilkPail.getRemindInShop()) {
                return new Result(false , "The purchase limit for this product has been reached");
            }
            if (MilkPail.coinNeeded * amount > currentPlayer.getMoney()){
                return new Result(false , "not enough money to purchase this product");
            }
            if (amount > currentPlayer.getBackPack().getType().getCapacity()) {
                return new Result(false , "Not enough capacity in your backpack");
            }
            for (Map.Entry <Items , Integer> entry : inventory.Items.entrySet()) {
                if (entry instanceof MilkPail) {
                    return new Result(false , "you already have Milk Pail in your inventory");
                }
            }
            MilkPail milkPail = new MilkPail();
            inventory.Items.put(milkPail , amount);
            MilkPail.increaseRemindInShop(- amount);
            currentPlayer.increaseMoney( - amount * MilkPail.coinNeeded);
            return new Result(false , "you bought this product successfully");
        }

        if (name.equals("Shears")) {
            if (amount == null) {
                amount = 1;
            }
            if (amount > Shear.getRemindInShop()) {
                return new Result(false , "The purchase limit for this product has been reached");
            }
            if (Shear.coinNeeded * amount > currentPlayer.getMoney()){
                return new Result(false , "not enough money to purchase this product");
            }
            if (amount > currentPlayer.getBackPack().getType().getCapacity()) {
                return new Result(false , "Not enough capacity in your backpack");
            }
            for (Map.Entry <Items , Integer> entry : inventory.Items.entrySet()) {
                if (entry instanceof Shear) {
                    return new Result(false , "you already have Milk Pail in your inventory");
                }
            }
            Shear shear = new Shear();
            inventory.Items.put(shear , amount);
            Shear.increaseRemindInShop(- amount);
            currentPlayer.increaseMoney(- amount * Shear.coinNeeded);
            return new Result(false , "you bought this product successfully");
        }

        return new Result(false , "No such product found");
    }

    public Result purchaseFromStardrop(String name , Integer amount) {
        Inventory inventory = currentPlayer.getBackPack().inventory;
        MarketItemType Product = null;
        for (MarketItemType type : MarketItemType.values()) {
            if (type.getMarketTypes().contains(MarketType.StardropSaloon)) {
                if (type.getName().equals(name)) {
                    Product = type;
                }
            }
        }
        if (Product == null) {
            return new Result(false , "No such product found");
        }
        if (amount == null) {
            amount = 1;
        }
        if (amount > Product.getOtherShopsLimit()) {
            return new Result(false , "The purchase limit for this product is reached");
        }
        if (currentPlayer.getMoney() < amount * Product.getPrice(0)) {
            return new Result(false , "Not enough money to purchase this product");
        }
        if (currentPlayer.getBackPack().getType().getRemindCapacity() == 0) {
            return new Result(false , "Not enough capacity in your backpack");
        }
        for (Map.Entry <Items , Integer> entry : inventory.Items.entrySet()) {
            if (entry.getKey() instanceof MarketItem) {
                if (((MarketItem) entry.getKey()).getType() .equals(Product) ) {
                    entry.setValue(entry.getValue() + amount);
                    Product.increaseOtherShopsLimit(-amount);
                    currentPlayer.increaseMoney( - amount * Product.getPrice(0));
                    return new Result(false , "You bought this product successfully");
                }
            }
        }
        MarketItem newItem = new MarketItem(Product);
        inventory.Items.put(newItem , amount);
        Product.increaseOtherShopsLimit(-amount);
        currentPlayer.increaseMoney( - amount * Product.getPrice(0));

        return new Result(false , "You bought this product successfully");
    }

    public Result purchaseFromCarpenter(String name , Integer amount) {
        Inventory inventory = currentPlayer.getBackPack().inventory;

        if (amount == null) {
            amount = 1;
        }
        MarketItemType Product = null;

        for (MarketItemType type : MarketItemType.values()) {
            if (type.getName().equals(name) && type.getMarketTypes().contains(MarketType.CarpenterShop)) {
                    Product = type;
            }
        }

        if (Product == null) {
            return new Result(false , "No such product found");
        }
        if (currentPlayer.getMoney() < amount * Product.getPrice(0)) {
            return new Result(false , "Not enough money to purchase this product");
        }
        if (Product.getOtherShopsLimit() < amount) {
            return new Result(false , "The purchase limit for this product is reached");
        }
        if (currentPlayer.getBackPack().getType().getRemindCapacity() == 0) {
            return new Result(false , "Not enough capacity in your backpack");
        }

        if (Product.getName().equals("Stone") ) {
            for (Map.Entry <Items , Integer> entry : inventory.Items.entrySet()) {
                if (entry.getKey() instanceof BasicRock) {
                    entry.setValue(entry.getValue() + amount);
                    Product.increaseOtherShopsLimit(-amount);
                    currentPlayer.increaseMoney(- amount * Product.getPrice(0));
                    return new Result(false , "You bought this product successfully");
                }
            }
        }

        if (Product.getName().equals("Wood") ) {
            for (Map.Entry <Items , Integer> entry : inventory.Items.entrySet()) {
                if (entry.getKey() instanceof Wood) {
                    entry.setValue(entry.getValue() + amount);
                    Product.increaseOtherShopsLimit(-amount);
                    currentPlayer.increaseMoney(- amount * Product.getPrice(0));
                    return new Result(false , "You bought this product successfully");
                }
            }
        }
        return null;
    }

    private Result buySeedsOrMarketItem(MarketItemType Product , ForagingSeedsType foragingSeed , Integer amount , MarketType marketType) {
        Inventory inventory = currentPlayer.getBackPack().inventory;

        int id=0;
        if (marketType.equals(MarketType.PierreGeneralStore)) {
            id = 1;
        }

        if (Product == null && foragingSeed == null) {
            return new Result(false , "No such product found");
        }
        if (currentPlayer.getMoney() < amount * Product.getPrice(id)) {
            return new Result(false , "Not enough money to purchase this product");
        }
        if (currentPlayer.getBackPack().getType().getRemindCapacity() == 0) {
            return new Result(false , "Not enough capacity in your backpack");
        }

        if (Product != null) {

            if (id == 1 && Product.getPierreShopsLimit() < amount) {
                return new Result(false , "The purchase limit for this product is reached");
            }
            if (Product.getOtherShopsLimit() < amount && id!=1) {
                return new Result(false , "The purchase limit for this product is reached");
            }

            for (Map.Entry <Items , Integer> entry : inventory.Items.entrySet()) {
                if (entry.getKey() instanceof MarketItem) {
                    if (((MarketItem) entry.getKey()).getType() .equals(Product)) {
                        entry.setValue(entry.getValue() + amount);
                        if (id == 1) {
                            Product.increaseOtherShopsLimit(-amount);
                        }
                        else {
                            Product.increaseOtherShopsLimit(-amount);
                        }
                        currentPlayer.increaseMoney(- amount * Product.getPrice(id));
                        return new Result(true , "You bought this product successfully");
                    }
                }
            }

            if (id == 1) {
                Product.increaseOtherShopsLimit(-amount);
            }
            else {
                Product.increaseOtherShopsLimit(-amount);
            }

            MarketItem marketItem = new MarketItem(Product);
            inventory.Items.put(marketItem, amount);
            return new Result(true , "You bought this product successfully");
        }

        else {
            if (foragingSeed.JojaMartLimit < amount && id!=1) {
                return new Result(false , "The purchase limit for this product is reached");
            }
            if (foragingSeed.PierrGeneralLimit < amount && id==1) {
                return new Result(false , "The purchase limit for this product is reached");
            }

            for (Map.Entry <Items , Integer> entry : inventory.Items.entrySet()) {
                if (entry.getKey() instanceof ForagingSeeds) {
                    if (((ForagingSeeds) entry.getKey()).getType() .equals(foragingSeed)) {
                        entry.setValue(entry.getValue() + amount);
                        if (id == 1) {
                            foragingSeed.PierrGeneralLimit -= amount;
                            currentPlayer.increaseMoney(-  amount * foragingSeed.getPrice(MarketType.PierreGeneralStore));
                        }
                        else {
                            foragingSeed.JojaMartLimit -= amount;
                            currentPlayer.increaseMoney(- amount * foragingSeed.getPrice(MarketType.JojaMart));
                        }
                        return new Result(false , "You bought this product successfully");
                    }
                }
            }

            ForagingSeeds foragingSeeds = new ForagingSeeds(foragingSeed , null) ;
            inventory.Items.put(foragingSeeds, amount);
            return new Result(false , "You bought this product successfully");
        }
    }

    public Result purchaseFromJojaMart(String name , Integer amount) {
        Inventory inventory = currentPlayer.getBackPack().inventory;
        if (amount == null) {
            amount = 1;
        }
        MarketItemType Product = null;
        ForagingSeedsType foragingSeed = null;

        for (MarketItemType type : MarketItemType.values()) {
            if (type.getName().equals(name) && type.getMarketTypes().contains(MarketType.JojaMart)) {
                Product = type;
            }
        }
        for (ForagingSeedsType foragingSeeds : ForagingSeedsType.values()) {
            if (foragingSeeds.getMarketTypes().contains(MarketType.JojaMart) && foragingSeeds.getDisplayName().equals(name)) {
                foragingSeed = foragingSeeds;
            }
        }

        return buySeedsOrMarketItem(Product , foragingSeed , amount , MarketType.JojaMart);

    }

    private Result buyBackpack(BackPackType backPackType , Integer amount) {
        if (backPackType.getRemindInShop() < amount) {
            return new Result(false , "The purchase limit for this product is reached");
        }

        if (currentPlayer.getMoney() < amount * backPackType.getPrice()) {
            return new Result(false , "Not enough money to purchase this product");
        }

        currentPlayer.getBackPack().setType(backPackType);
        currentPlayer.increaseMoney( -amount * backPackType.getPrice());
        backPackType.increaseRemindInShop(-amount);
        return new Result(false , "you bought" + backPackType.getName() + " successfully");
    }


    private Result buyTreeSource(TreesSourceType treesSourceType , Integer amount) {
        Inventory inventory = currentPlayer.getBackPack().inventory;
        if (currentPlayer.getMoney() < amount * treesSourceType.getPrice()) {
            return new Result(false , "Not enough money to purchase this product");
        }
        if (amount > currentPlayer.getBackPack().getType().getRemindCapacity()) {
            return new Result(false , "Not enough capacity in your backpack");
        }
        for (Map.Entry <Items , Integer> entry : inventory.Items.entrySet()) {
            if (entry.getKey() instanceof TreeSource) {
                if (((TreeSource) entry.getKey()).getType() .equals(treesSourceType)) {
                    entry.setValue(entry.getValue() + amount);
                    currentPlayer.increaseMoney(- amount * treesSourceType.getPrice());
                    return new Result(true , "You bought " + treesSourceType.name() +" successfully");
                }
            }
        }

        TreeSource treeSource = new TreeSource(treesSourceType);
        inventory.Items.put(treeSource, amount);
        currentPlayer.increaseMoney(-amount * treesSourceType.getPrice());
        return new Result(true , "You bought " + treesSourceType.name() +" successfully");
    }


    public Result purchaseFromPierre(String name , Integer amount) {
        Inventory inventory = currentPlayer.getBackPack().inventory;
        if (amount == null) {
            amount = 1;
        }
        MarketItemType Product = null;
        ForagingSeedsType foragingSeed = null;
        TreesSourceType treesSource = null;
        BackPackType backPack = null;

        for (MarketItemType type : MarketItemType.values()) {
            if (type.getName().equals(name) && type.getMarketTypes().contains(MarketType.PierreGeneralStore)) {
                Product = type;
            }
        }
        for (ForagingSeedsType foragingSeeds : ForagingSeedsType.values()) {
            if (foragingSeeds.getMarketTypes().contains(MarketType.PierreGeneralStore) && foragingSeeds.getDisplayName().equals(name)) {
                foragingSeed = foragingSeeds;
            }
        }
        for (TreesSourceType type : TreesSourceType.values()) {
            if (type.getDisplayName().equals(name) && type.getPrice() !=0 ) {
                treesSource = type;
            }
        }
        for (BackPackType backPackType : BackPackType.values()) {
            if (backPackType.getName().equals(name) ) {
                backPack = backPackType;
            }
        }


        if (backPack != null) {
            return buyBackpack(backPack , amount);
        }
        if (treesSource != null) {
            return buyTreeSource(treesSource , amount);
        }
        return buySeedsOrMarketItem(Product , foragingSeed , amount , MarketType.PierreGeneralStore);

    }

    private Result buyFishingPole(FishingPoleType poleType , Integer amount) {
        Inventory inventory = currentPlayer.getBackPack().inventory;
        if (amount > poleType.getshopLimit()) {
            return new Result(false ,"The purchase limit for this product is reached");
        }
        if (currentPlayer.getMoney() < amount * poleType.getPrice()) {
            return new Result(false , "Not enough money to purchase this product");
        }
        if (currentPlayer.getLevelFishing() < poleType.getLevel()) {
            return new Result(false ,"for buy this fishing pole you should be at least in level " + poleType.getLevel());
        }

        for (Map.Entry <Items , Integer> entry : inventory.Items.entrySet()) {
            if (entry.getKey() instanceof FishingPole) {
                inventory.Items.remove(entry.getKey());
                break;
            }
        }

        if (amount > currentPlayer.getBackPack().getType().getRemindCapacity()) {
            return new Result(false , "Not enough capacity in your backpack");
        }

        FishingPole fishingPole = new FishingPole();
        fishingPole.type = poleType;
        inventory.Items.put(fishingPole, amount);
        poleType.incrementShopLimit(-amount);

        return new Result(true , "You bought " + poleType.name() +" successfully");

    }

    public Result purchaseFromFishShop(String name , Integer amount) {
          Inventory inventory = currentPlayer.getBackPack().inventory;
          if (amount == null) {
              amount = 1;
          }

          FishingPoleType fishingPoleType = null;
          MarketItemType Product = null;

          for (FishingPoleType type : FishingPoleType.values()) {
              if (type.getName().equals(name) ) {
                  fishingPoleType = type;
                  break;
              }
          }
          for (MarketItemType type : MarketItemType.values()) {
              if (type.getName().equals(name) && type.getMarketTypes().contains(MarketType.FishShop)) {
                  Product = type;
                  break;
              }
          }

          if (Product == null && fishingPoleType == null) {
              return new Result(false , "No such product found");
          }

          if (fishingPoleType != null) {
              return buyFishingPole(fishingPoleType , amount);
          }

          if (Product.getOtherShopsLimit() < amount) {
              return new Result(false , "The purchase limit for this product is reached");
          }
          if (currentPlayer.getMoney() < amount * Product.getPrice(0)) {
              return new Result(false , "Not enough money to purchase this product");
          }
          if (amount > currentPlayer.getBackPack().getType().getRemindCapacity()) {
              return new Result(false , "Not enough capacity in your backpack");
          }

          for (Map.Entry <Items , Integer> entry : inventory.Items.entrySet()) {
              if (entry.getKey() instanceof MarketItem) {
                  if (((MarketItem) entry.getKey()).getType() .equals(Product)) {
                      entry.setValue(entry.getValue() + amount);
                      Product.increaseOtherShopsLimit(-amount);
                      currentPlayer.increaseMoney(-amount * Product.getPrice(0));
                      return new Result(true , "You bought " + Product.getName() +" successfully");
                  }
              }
          }

          MarketItem marketItem = new MarketItem(Product);
          inventory.Items.put(marketItem, amount);
          currentPlayer.increaseMoney(-amount * Product.getPrice(0));
          Product.increaseOtherShopsLimit(-amount);
          return new Result(true , "You bought " + Product.getName() +" successfully");

    }


    public Result purchase(String name , Integer amount) {
        MarketType marketType = findEnteredShopType();

        if (marketType == null) {
            return new Result(false , "you are not in any shop");
        }
        switch (marketType) {
            case MarnieRanch -> {purchaseFromMarnieRanch(name, amount);}
            case StardropSaloon -> {purchaseFromStardrop(name, amount);}
            case CarpenterShop -> {purchaseFromCarpenter(name, amount);}
            case JojaMart -> {purchaseFromJojaMart(name, amount);}
            case PierreGeneralStore -> {purchaseFromPierre(name, amount);}
            case FishShop ->{purchaseFromFishShop(name, amount);}
            case Blacksmith -> {purchaseFromBlackSmith(name, amount);}
        }
        return null;
    }
}
