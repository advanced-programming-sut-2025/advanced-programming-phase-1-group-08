package model.ToolsPackage;

public class Shear extends Tools {

    public static final int coinNeeded=1000;
    private static int remindInShop=1;

    public Shear(){
        super("Shear", 0);
    }

    @Override
    public String getName() {
        return "Shear";
    }

    public void use (){}

    public static int getRemindInShop() {
        return remindInShop;
    }
    public static void setRemindInShop() {
        remindInShop = 1;
    }
    public static void increaseRemindInShop(int amount) {
        remindInShop += amount;
    }

    public static int getCoinNeeded() {
        return coinNeeded;
    }
    public static int getRemindInshop() {
        return remindInShop;
    }

    @Override
    public int healthCost() { // TODO
        return 0;
    }
}
