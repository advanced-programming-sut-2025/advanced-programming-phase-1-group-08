package model;

import model.Enum.ItemType.BarnORCageType;

import java.util.ArrayList;

public class BarnOrCage extends GameObject {

    private BarnORCageType barnORCageType;
    ArrayList<Animal> animals = new ArrayList<>();
    public final int topLeftX;
    public final int topLeftY;

    public BarnOrCage(BarnORCageType barnORCageType , int topLeftX , int topLeftY) {
        this.barnORCageType = barnORCageType;
        this.topLeftX = topLeftX;
        this.topLeftY = topLeftY;
    }

    public int getReminderCapacity(){
        return this.barnORCageType.getInitialCapacity() - animals.size();
    }


}
