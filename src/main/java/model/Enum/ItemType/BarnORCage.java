package model.Enum.ItemType;

import model.Animal;

import java.util.ArrayList;

public enum BarnORCage {

    cageLevel2,
    cageLevel1,
    cageLevel3,
    barn1,
    barn2,
    barn3;


    private int capacity;
    private ArrayList<Animal> animals = new ArrayList<>();

    public ArrayList<Animal> getAnimals() {
        return animals;
    }
    public void setAnimals(ArrayList<Animal> animals) {
        this.animals = animals;
    }
    public void addAnimal (Animal animal) {
        animals.add(animal);
    }
    public Animal getAnimal (int index) {
        return animals.get(index);
    }
}
