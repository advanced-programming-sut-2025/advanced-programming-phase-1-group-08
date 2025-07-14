package com.Graphic.model.Enum.ItemType;

import com.Graphic.model.Plants.Fish;
import com.Graphic.model.Plants.Animalproduct;
import com.Graphic.model.App;
import com.Graphic.model.Inventory;
import com.Graphic.model.Items;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public enum BackPackType {
    primary("primary",Integer.MAX_VALUE,0 ,0 , 0 , null) {
      //TODO باید ظرفیتش رو درست کنم بزارم همون 12
    },

    LargePack("Large Pack" , 24 , 2000 , 1 , 1 , "Mohamadreza/Large_Pack.png") {

    },

    DeluxePack("Deluxe Pack" , Integer.MAX_VALUE , 10000 , 1 , 1,"Mohamadreza/Deluxe_Pack.png") {

    };

    private String name;
    private final int capacity;
    private final int price;
    private final int InitialShopLimit;
    private int remindInShop;
    private final String Path;

    BackPackType(String name, int capacity, int price, int InitialShopLimit , int remindInShop , String Path) {
        this.name = name;
        this.capacity = capacity;
        this.price = price;
        this.InitialShopLimit = InitialShopLimit;
        this.remindInShop = remindInShop;
        this.Path = Path;
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
                fishTypes.add(((Fish) entry.getKey()).getType());
            }
            else if (entry.getKey() instanceof Animalproduct) {
                animalProductTypes.add(((Animalproduct) entry.getKey()).getType());
            }
            else {
                x++;
            }
        }

        return App.currentGame.currentPlayer.getBackPack().getType().getCapacity() - x - fishTypes.size() - animalProductTypes.size() ;
    }

    public String getPath() {
        return Path;
    }
}
