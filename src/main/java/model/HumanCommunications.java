package model;

import model.Enum.ItemType.AnimalProductType;
import model.Enum.ItemType.FishType;
import model.Enum.ItemType.MarketItemType;
import model.Enum.ItemType.Quantity;
import model.Animall.Fish;
import model.Animall.Animalproduct;
import model.OtherItem.MarketItem;

import java.util.*;

import static Controller.GameController.isNeighbor;
import static model.App.*;
import static model.Color_Eraser.*;

public class HumanCommunications {
    private User player1;
    private User player2;
    private int XP; // 100 for 1, 200 for 2, 300 for 3, 400 for 4
    private int FriendshipLevel;
    private boolean BOUQUETBought = false;
    private boolean SUCCESSFULPropose = false;

    public HumanCommunications(User player1, User player2) {
        this.player1 = player1;
        this.player2 = player2;
        XP = 0;
        FriendshipLevel = 0;
    }

    public boolean involves(User p) {
        return p.equals(player1) || p.equals(player2);
    }
    public boolean isBetween(User u1, User u2) {
        return (u1.equals(player1) && u2.equals(player2)) || (u1.equals(player2) && u2.equals(player1));
    }

    public int getXP() {
        return XP;
    }
    public void setXP(int XP) {
        this.XP = XP;
    }

    public void addXP(int XP) {
        this.XP += XP;
        updateLevel();
    }
    public void reduceXP(int XP) {
        this.XP -= XP;
        updateLevel();
    }


    public User getPlayer1() {
        return player1;
    }

    public User getPlayer2() {
        return player2;
    }
    //TODO وقتی مکانیزم کاهش لول رو زدی یادت باشه این بولین های اول رو فالس کنی با کم شدن لول
    public void updateLevel() {
        if (FriendshipLevel == 0 && getXP() >= 100) {
            increaseLevel();
            setXP(getXP() - 100);
        }
        if (FriendshipLevel == 1 && getXP() >= 200) {
            increaseLevel();
            setXP(getXP() - 200);
        }
        if (FriendshipLevel == 2 && getXP() >= 300 && BOUQUETBought) {
            increaseLevel();
            setXP(getXP() - 300);
        }
        if (FriendshipLevel == 3 && getXP() >= 400 && SUCCESSFULPropose) {
            marry();
            setXP(getXP() - 400);
        }


        if (FriendshipLevel == 3 && getXP() < 0) {
            decreaseLevel();
            setXP(300 - getXP());
            BOUQUETBought = false;
        }
        if (FriendshipLevel == 2 && getXP() < 0) {
            decreaseLevel();
            setXP(200 - getXP());
        }
        if (FriendshipLevel == 1 && getXP() < 0) {
            decreaseLevel();
            setXP(100 - getXP());
        }
        if (FriendshipLevel == 0 && getXP() < 0)
            setXP(0);


    }
    public int getLevel() {
        updateLevel();
        return FriendshipLevel;
    }
    public void increaseLevel() {
        if (FriendshipLevel < 3)
            FriendshipLevel++;
    }
    public void decreaseLevel() {
        if (FriendshipLevel < 4)
            FriendshipLevel--;
    }
    public void printInfo() {
        updateLevel();
        System.out.println(player1.getNickname() + " <-> " + player2.getNickname() + " | FriendshipLevel: " + getLevel() + " | FriendshipXP: " + XP);
    }

    // LEVEL ZERO TASKS
    public Result talk(String text) {

        User me = currentPlayer;
        User other;
        if (player1.getUsername().equals(currentPlayer.getUsername()))
            other = player2;
        else
            other = player1;

        if (!isNeighbor(player1.getPositionX(), player1.getPositionY(), player2.getPositionX(), player2.getPositionY())) {
            return new Result(false, RED+"You Should " + RED+"Get Closer"+RESET + " in Order to Talk to " + other.getNickname() + "!"+RESET);
        }

        addXP(10);
        updateLevel();

        Set<User> key = new HashSet<>(Arrays.asList(me, other));
        conversations.putIfAbsent(key, new ArrayList<>());
        conversations.get(key).add(new MessageHandling(me, other, text));

        return new Result(true, GREEN+"You Sent a Message to " + other + "."+RESET);
    }
    public Result talkingHistory() {
        Set<User> key = new HashSet<>(Arrays.asList(player1, player2));
        List<MessageHandling> messages = conversations.getOrDefault(key, new ArrayList<>());
        System.out.println("📜 Chat between " + player1.getNickname() + " and " + player2.getNickname() + ":");
        for (MessageHandling m : messages) {
            m.print();
        }
        return new Result(true, GREEN+"Successfully Displayed."+RESET);
    }
    public Result trade(String type, String iGive, int iGiveAmount, String iGet, int iGetAmount) {
        User me = currentPlayer;
        User other;
        if (player1.getUsername().equals(currentPlayer.getUsername()))
            other = player2;
        else
            other = player1;

        if (!isNeighbor(player1.getPositionX(), player1.getPositionY(), player2.getPositionX(), player2.getPositionY())) {
            return new Result(false, RED+"You Should " + RED+"Get Closer"+RESET + " in Order to Send Trade Offer to " + other.getNickname() + "!"+RESET);
        }


    } //TODO اگه بازیکن مورد نظر وجود نداشت پیغام مناسب چاپ بشه و اگه مقدار یا آیتم نامعتبر بودن ارور بده

    // LEVEL ONE TASKS
    public Result rateGifts(){
        Scanner scanner = new Scanner(System.in);
        int rate = scanner.nextInt();
        if (!(rate >= 1 && rate <= 5))
            return new Result(false, RED+"Just Enter a Digit! (1 to 5)"+RESET);

        int x = ((rate - 3) * 30) + 15;
        setXP(getXP() + x);
        updateLevel();

        return new Result(true, GREEN+"Rated Successfully."+RESET);
    }
    public Result sendGifts(String username, String item, int amount) {
        User other;
        if (player1.getUsername().equals(currentPlayer.getUsername()))
            other = player2;
        else
            other = player1;

        Inventory myInventory = currentPlayer.getBackPack().inventory;
        Inventory otherInventory = other.getBackPack().inventory;

        if (!isNeighbor(player1.getPositionX(), player1.getPositionY(), player2.getPositionX(), player2.getPositionY())) {
            return new Result(false, RED+"You Should " + RED+"Get Closer"+RESET + " in Order to Send Gift to " + other.getNickname() + "!"+RESET);
        }
        if (getLevel() < 1) {

            return new Result(false, RED+"You can't Send Gifts in your Current " + RED+"Friendship Level"+RESET + "."+RESET);
        }

        Items items = null;
        String type = switch (item.toLowerCase()) {
            case "egg" -> {
                items = new Animalproduct(AnimalProductType.Egg, Quantity.Normal);
                yield "AnimalProductType";
            }
            case "bigegg" -> {
                items = new Animalproduct(AnimalProductType.bigEgg, Quantity.Normal);
                yield "AnimalProductType";
            }
            case "duckegg" -> {
                items = new Animalproduct(AnimalProductType.duckEgg, Quantity.Normal);
                yield "AnimalProductType";
            }
            case "duckfeather" -> {
                items = new Animalproduct(AnimalProductType.duckFeather, Quantity.Normal);
                yield "AnimalProductType";
            }
            case "rabbits_wool" -> {
                items = new Animalproduct(AnimalProductType.rabbits_Wool, Quantity.Normal);
                yield "AnimalProductType";
            }
            case "rabbits_foot" -> {
                items = new Animalproduct(AnimalProductType.rabbits_Foot, Quantity.Normal);
                yield "AnimalProductType";
            }
            case "dinosauregg" -> {
                items = new Animalproduct(AnimalProductType.dinosaurEgg, Quantity.Normal);
                yield "AnimalProductType";
            }
            case "milk" -> {
                items = new Animalproduct(AnimalProductType.milk, Quantity.Normal);
                yield "AnimalProductType";
            }
            case "bigmilk" -> {
                items = new Animalproduct(AnimalProductType.bigMilk, Quantity.Normal);
                yield "AnimalProductType";
            }
            case "goatmilk" -> {
                items = new Animalproduct(AnimalProductType.goatMilk, Quantity.Normal);
                yield "AnimalProductType";
            }
            case "sheeps_wool" -> {
                items = new Animalproduct(AnimalProductType.sheeps_Wool, Quantity.Normal);
                yield "AnimalProductType";
            }
            case "salmon" -> {
                items = new Fish(FishType.Salmon, Quantity.Normal);
                yield "FishType";
            }
            case "sardine" -> {
                items = new Fish(FishType.Sardine, Quantity.Normal);
                yield "FishType";
            }
            case "shad" -> {
                items = new Fish(FishType.Shad, Quantity.Normal);
                yield "FishType";
            }
            case "blue_discos" -> {
                items = new Fish(FishType.Blue_Discus, Quantity.Normal);
                yield "FishType";
            }
            case "midnight_carp" -> {
                items = new Fish(FishType.Midnight_Carp, Quantity.Normal);
                yield "FishType";
            }
            case "squid" -> {
                items = new Fish(FishType.Squid, Quantity.Normal);
                yield "FishType";
            }
            case "tuna" -> {
                items = new Fish(FishType.Tuna, Quantity.Normal);
                yield "FishType";
            }
            case "perch" -> {
                items = new Fish(FishType.Perch, Quantity.Normal);
                yield "FishType";
            }
            case "flounder" -> {
                items = new Fish(FishType.Flounder, Quantity.Normal);
                yield "FishType";
            }
            case "lionfish" -> {
                items = new Fish(FishType.Lionfish, Quantity.Normal);
                yield "FishType";
            }
            case "herring" -> {
                items = new Fish(FishType.Herring, Quantity.Normal);
                yield "FishType";
            }
            case "tilapia" -> {
                items = new Fish(FishType.Tilapia, Quantity.Normal);
                yield "FishType";
            }
            case "ghostfish" -> {
                items = new Fish(FishType.Ghostfish, Quantity.Normal);
                yield "FishType";
            }
            case "dorado" -> {
                items = new Fish(FishType.Dorado, Quantity.Normal);
                yield "FishType";
            }
            case "sunfish" -> {
                items = new Fish(FishType.Sunfish, Quantity.Normal);
                yield "FishType";
            }
            case "legend" -> {
                items = new Fish(FishType.Legend, Quantity.Normal);
                yield "FishType";
            }
            case "jojacola" -> {
                items = new MarketItem(MarketItemType.JojaCola);
                yield "MarketItemType";
            }
            case "grassstarter" -> {
                items = new MarketItem(MarketItemType.GrassStarter);
                yield "MarketItemType";
            }
            case "sugar" -> {
                items = new MarketItem(MarketItemType.Sugar);
                yield "MarketItemType";
            }
            case "wheatflour" -> {
                items = new MarketItem(MarketItemType.WheatFlour);
                yield "MarketItemType";
            }
            case "rice" -> {
                items = new MarketItem(MarketItemType.Rice);
                yield "MarketItemType";
            }
            case "bouquet" -> {
                items = new MarketItem(MarketItemType.Bouquet);
                yield "MarketItemType";
            }
            case "dehydratorrecipe" -> {
                items = new MarketItem(MarketItemType.DehydratorRecipe);
                yield "MarketItemType";
            }
            case "grassstarterrecipe" -> {
                items = new MarketItem(MarketItemType.GrassStarterRecipe);
                yield "MarketItemType";
            }
            case "oil" -> {
                items = new MarketItem(MarketItemType.Oil);
                yield "MarketItemType";
            }
            case "vinegar" -> {
                items = new MarketItem(MarketItemType.Vinegar);
                yield "MarketItemType";
            }
            case "deluxeretainingsoil" -> {
                items = new MarketItem(MarketItemType.DeluxeRetainingSoil);
                yield "MarketItemType";
            }
            case "speedgro" -> {
                items = new MarketItem(MarketItemType.SpeedGro);
                yield "MarketItemType";
            }
            case "basicretainingsoil" -> {
                items = new MarketItem(MarketItemType.BasicRetainingSoil);
                yield "MarketItemType";
            }
            case "quantityretainingsoil" -> {
                items = new MarketItem(MarketItemType.QuantityRetainingSoil);
                yield "MarketItemType";
            }
            case "hay" -> {
                items = new MarketItem(MarketItemType.Hay);
                yield "MarketItemType";
            }
            case "beer" -> {
                items = new MarketItem(MarketItemType.Beer);
                yield "MarketItemType";
            }
            case "spaghetti" -> {
                items = new MarketItem(MarketItemType.Spaghetti);
                yield "MarketItemType";
            }
            case "salad" -> {
                items = new MarketItem(MarketItemType.Salad);
                yield "MarketItemType";
            }
            case "bread" -> {
                items = new MarketItem(MarketItemType.Bread);
                yield "MarketItemType";
            }
            case "pizza" -> {
                items = new MarketItem(MarketItemType.Pizza);
                yield "MarketItemType";
            }
            case "coffee" -> {
                items = new MarketItem(MarketItemType.Coffee);
                yield "MarketItemType";
            }
            case "hashbrownsrecipe" -> {
                items = new MarketItem(MarketItemType.HashbrownsRecipe);
                yield "MarketItemType";
            }
            case "omeletrecipe" -> {
                items = new MarketItem(MarketItemType.OmeletRecipe);
                yield "MarketItemType";
            }
            case "pancakesrecipe" -> {
                items = new MarketItem(MarketItemType.PancakesRecipe);
                yield "MarketItemType";
            }
            case "breadrecipe" -> {
                items = new MarketItem(MarketItemType.BreadRecipe);
                yield "MarketItemType";
            }
            case "tortillarecipe" -> {
                items = new MarketItem(MarketItemType.TortillaRecipe);
                yield "MarketItemType";
            }
            case "pizzarecipe" -> {
                items = new MarketItem(MarketItemType.PizzaRecipe);
                yield "MarketItemType";
            }
            case "makirollrecipe" -> {
                items = new MarketItem(MarketItemType.MakiRollRecipe);
                yield "MarketItemType";
            }
            case "tripleshotespressorecipe" -> {
                items = new MarketItem(MarketItemType.TripleShotEspressoRecipe);
                yield "MarketItemType";
            }
            case "cookierecipe" -> {
                items = new MarketItem(MarketItemType.CookieRecipe);
                yield "MarketItemType";
            }
            case "wood" -> {
                items = new MarketItem(MarketItemType.Wood);
                yield "MarketItemType";
            }
            case "stone" -> {
                items = new MarketItem(MarketItemType.Stone);
                yield "MarketItemType";
            }
            case "fishsmokerrecipe" -> {
                items = new MarketItem(MarketItemType.FishSmokerRecipe);
                yield "MarketItemType";
            }
            case "troutsoup" -> {
                items = new MarketItem(MarketItemType.TroutSoup);
                yield "MarketItemType";
            }
            default -> null;
        };

        boolean IHaveThat = false;
        for (Map.Entry <Items , Integer> entry : myInventory.Items.entrySet() ) {
            assert type != null;
            if (type.equals("MarketItemType")) {
                if (entry instanceof MarketItem) {
                    if (entry.getKey().toString().equals(items.toString())) {
                        IHaveThat = true;
                        myInventory.Items.put(entry.getKey(), entry.getValue() - amount);
                        if (entry.getValue() == 0) myInventory.Items.remove(entry.getKey());
                        break;
                    }
                }
            }
            else if (type.equals("FishType")) {
                if (entry instanceof Fish) {
                    if (entry.getKey().toString().equals(items.toString())) {
                        IHaveThat = true;
                        myInventory.Items.put(entry.getKey(), entry.getValue() - amount);
                        if (entry.getValue() == 0) myInventory.Items.remove(entry.getKey());
                        break;
                    }
                }
            }
            else if (type.equals("AnimalProductType")) {
                if (entry instanceof Animalproduct) {
                    if (entry.getKey().toString().equals(items.toString())) {
                        IHaveThat = true;
                        myInventory.Items.put(entry.getKey(), entry.getValue() - amount);
                        if (entry.getValue() == 0) myInventory.Items.remove(entry.getKey());
                        break;
                    }
                }
            }
        }

        if (!IHaveThat)
            return new Result(false, RED+"You Don't Have it to Give!"+RESET);



        for (Map.Entry <Items , Integer> entry : myInventory.Items.entrySet() ) {
            if (type.equals("MarketItemType")) {
                if (entry instanceof MarketItem) {
                    if (entry.getKey().toString().equals(items.toString())) {
                        otherInventory.Items.put(entry.getKey(), entry.getValue() + amount);
                        return new Result(true, GREEN+"You Sent it to " + other.getNickname()+RESET);
                    }
                }
            }
            else if (type.equals("FishType")) {
                if (entry instanceof Fish) {
                    if (entry.getKey().toString().equals(items.toString())) {
                        otherInventory.Items.put(entry.getKey(), entry.getValue() + amount);
                        return new Result(true, GREEN+"You Sent it to " + other.getNickname()+RESET);
                    }
                }
            }
            else if (type.equals("AnimalProductType")) {
                if (entry instanceof Animalproduct) {
                    if (entry.getKey().toString().equals(items.toString())) {
                        otherInventory.Items.put(entry.getKey(), entry.getValue() + amount);
                        return new Result(true, GREEN+"You Sent it to " + other.getNickname()+RESET);
                    }
                }
            }
        }


        otherInventory.Items.put(items, amount);
        return new Result(true, GREEN+"You Sent it to " + other.getNickname()+RESET);
    }

    // LEVEL TWO TASKS
    public Result Hug() {

        User other;
        if (player1.getUsername().equals(currentPlayer.getUsername()))
            other = player2;
        else
            other = player1;

        if (!isNeighbor(player1.getPositionX(), player1.getPositionY(), player2.getPositionX(), player2.getPositionY())) {
            return new Result(false, RED+"You Should " + RED+"Get Closer"+RESET + " in Order to Hug " + other.getNickname() + "!"+RESET);
        }
        if (getLevel() < 2) {
            return new Result(false, RED+"You can't Hug in your Current " + RED+"Friendship Level"+RESET + "."+RESET);
        }



        addXP(60);
        updateLevel(); // to get Updated
        return new Result(true, GREEN+"You Hugged " + other.getNickname() + "."+RESET);
    }
    public Result buyFlowers() {
        User other;
        if (player1.getUsername().equals(currentPlayer.getUsername()))
            other = player2;
        else
            other = player1;

        Inventory myInventory = currentPlayer.getBackPack().inventory;
        Inventory otherInventory = other.getBackPack().inventory;

        if (!isNeighbor(player1.getPositionX(), player1.getPositionY(), player2.getPositionX(), player2.getPositionY())) {
            return new Result(false, RED+"You Should Get Closer in Order to Buy Flower for " + other.getNickname() + "!"+RESET);
        }
        if (getLevel() < 2) {
            return new Result(false, RED+"You can't Buy Flower in your Current " + RED+"Friendship Level"+RESET + "."+RESET);
        }

        boolean IHaveBouquet = false;
        for (Map.Entry <Items , Integer> entry : myInventory.Items.entrySet() ) {
            if (entry instanceof MarketItem) {
                if (((MarketItem) entry).getType().equals(MarketItemType.Bouquet)) {
                    IHaveBouquet = true;
                    myInventory.Items.put(entry.getKey(), entry.getValue() - 1);
                    if (entry.getValue() == 0) myInventory.Items.remove(entry.getKey());
                    break;
                }
            }
        }
        if (!IHaveBouquet)
            return new Result(false, RED+"You Don't Have Bouquet to Give!"+RESET);


        for (Map.Entry <Items , Integer> entry : otherInventory.Items.entrySet() ) {
            if (entry instanceof MarketItem) {
                if (((MarketItem)entry).getType().equals(MarketItemType.Bouquet)) {
                    otherInventory.Items.put(entry.getKey(), entry.getValue() + 1);
                    return new Result(true, GREEN+"You Gave Bouquet to " + other.getNickname()+RESET);
                }
            }
        }

        otherInventory.Items.put(new MarketItem(MarketItemType.Bouquet), 1);

        setXP(300);
        BOUQUETBought = true;
        updateLevel();

        return new Result(true, GREEN+"You Gave Bouquet to " + other.getNickname()+RESET);
    }

    // LEVEL THREE TASKS
    public Result propose() {
        User other;
        if (player1.getUsername().equals(currentPlayer.getUsername()))
            other = player2;
        else
            other = player1;

        Inventory myInventory = currentPlayer.getBackPack().inventory;
        Inventory otherInventory = other.getBackPack().inventory;

        if (currentPlayer.getGender().equals(other.getGender()))
            return new Result(false, RED+"No Gay Marriage in Iran!"+RESET);

        if (!isNeighbor(player1.getPositionX(), player1.getPositionY(), player2.getPositionX(), player2.getPositionY())) {
            return new Result(false, PURPLE+"Get Closer Honey!"+RESET);
        }

        if (getLevel() < 3)
            return new Result(false, RED+"You Can't Propose in your Current Friendship Level."+RESET);

        boolean IHaveRing = false;
        for (Map.Entry <Items , Integer> entry : myInventory.Items.entrySet() ) {
            if (entry instanceof MarketItem) {
                if (((MarketItem) entry).getType().equals(MarketItemType.WeddingRing)) {
                    IHaveRing = true;
                    myInventory.Items.put(entry.getKey(), entry.getValue() - 1);
                    if (entry.getValue() == 0) myInventory.Items.remove(entry.getKey());
                    break;
                }
            }
        }
        if (!IHaveRing)
            return new Result(false, RED+"You Don't Have Ring to Propose!"+RESET);


        return new Result(true, GREEN+"You Proposed Successfully!"+RESET);

        //TODO
    }

    // LEVEL FOUR
    public void marry() { //spouse!!!
        if (!SUCCESSFULPropose) {
            System.out.println(RED+"Your Propose Hasn't been Accepted Yet!"+RESET);
            return;
        }

        User other;
        if (player1.getUsername().equals(currentPlayer.getUsername()))
            other = player2;
        else
            other = player1;

        Inventory otherInventory = other.getBackPack().inventory;


        //TODO زمین ها و پولهاشون مال هردو میشه


        for (Map.Entry <Items , Integer> entry : otherInventory.Items.entrySet() ) {
            if (entry instanceof MarketItem) {
                if (((MarketItem)entry).getType().equals(MarketItemType.WeddingRing)) {
                    otherInventory.Items.put(entry.getKey(), entry.getValue() + 1);
                    System.out.println(GREEN+"HAPPY MARRIAGE!"+RESET);
                    return;
                }
            }
        }


        otherInventory.Items.put(new MarketItem(MarketItemType.WeddingRing), 1);
        FriendshipLevel = 4;
        System.out.println(GREEN+"HAPPY MARRIAGE!"+RESET);
    }

    public void divorce() {
        //todo


        updateLevel();
        SUCCESSFULPropose = false;
    }

    // TODO اون پاراگراف بین سطح های دوستی و تعاملات
}
