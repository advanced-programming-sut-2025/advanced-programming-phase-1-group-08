package model.Places;

public class Well {
    private static int remindInShop=1;
    private static final int width=3;
    private static final int height=3;
    private static final int neededCoin=1000;
    private static final int neededStone=75;
    private final int topLeftX;
    private final int topLeftY;

    public Well(int topLeftX , int topLeftY) {
        this.topLeftX = topLeftX;
        this.topLeftY = topLeftY;
    }

    public static int getHeight() {
        return height;
    }
    public static int getWidth() {
        return width;
    }

    public static int getNeededCoin() {
        return neededCoin;
    }

    public static int getNeededStone() {
        return neededStone;
    }

    public static int getRemindInShop() {
        return remindInShop;
    }
    public static void setRemindInShop(int x) {
        remindInShop = x;
    }
}
