package model.Animall;

import model.Enum.ItemType.BarnORCageType;
import model.MapThings.GameObject;

import java.util.ArrayList;

import static model.Color_Eraser.BROWN;

public class BarnOrCage extends GameObject {

    private BarnORCageType barnORCageType;
    public ArrayList<Animal> animals = new ArrayList<>();
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

    public BarnORCageType getBarnORCageType() {
        return barnORCageType;
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    @Override
    public String getIcon() {
        if (barnORCageType.equals(BarnORCageType.Barn) || barnORCageType.equals(BarnORCageType.BigBarn) || barnORCageType.equals(BarnORCageType.DeluxeBarn)) {
            return BROWN +"B";
        }
        else {
            return BROWN +"C";
        }
    }
}
