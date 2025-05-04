package model.Enum.ItemType;

public enum BackPackType {
    primary("primary",12,0 ,0) {

    },

    LargePack("Large Pack" , 24 , 2000 , 1) {

    },

    DeluxePack("Deluxe Pack" , Integer.MAX_VALUE , 10000 , 1) {

    };

    private String name;
    private final int capacity;
    private final int price;
    private final int InitialShopLimit;
    private int remindInShop;

    BackPackType(String name, int capacity, int price, int InitialShopLimit) {
        this.name = name;
        this.capacity = capacity;
        this.price = price;
        this.InitialShopLimit = InitialShopLimit;
    }
    public String getName() {
        return name;
    }
    public int getCapacity() {
        return capacity;
    }
    public int getPrice() {
        return price;
    }
    public int getInitialShopLimit() {
        return InitialShopLimit;
    }

    public int getRemindInShop() {
        return remindInShop;
    }

    public void setRemindInShop() {
        this.remindInShop = InitialShopLimit;
    }

    public void increaseRemindInShop(int amount) {
        this.remindInShop += amount;
    }



 }
