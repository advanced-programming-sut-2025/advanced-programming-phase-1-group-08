package model.Enum.ItemType;

import model.Animall.Animalproduct;
import model.Animall.Fish;
import model.App;
import model.Inventory;
import model.Items;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

    public int getRemindCapacity() {
        int x=0;
        Inventory inventory = App.currentGame.currentPlayer.getBackPack().inventory;
        Set<FishType> fishTypes=new HashSet<>();
        Set<AnimalProductType> animalProductTypes=new HashSet<>();
        for (Map.Entry < Items , Integer > entry : inventory.Items.entrySet()) {
            if (entry.getKey() instanceof Fish) {
                fishTypes.add(((Fish) entry.getKey()).getFishType());
            }
            else if (entry.getKey() instanceof Animalproduct) {
                animalProductTypes.add(((Animalproduct) entry.getKey()).getAnimalProductType());
            }
            else {
                x++;
            }
        }

        return App.currentGame.currentPlayer.getBackPack().getType().getCapacity() - x - fishTypes.size() - animalProductTypes.size() ;
    }



 }
