package model.ToolsPackage;

public class MilkPail extends Tools {

    public static final int coinNeeded=1000;
    public static int remindInShop=1;

    public MilkPail(){
        super("MilkPail");
    }

    @Override
    public String getName() {
        return "MilkPail";
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


    @Override
    public int healthCost() { // TODO
        return 0;
    }

    @Override
    public String getIcon() {
        return "MilkPail";
    }
}
