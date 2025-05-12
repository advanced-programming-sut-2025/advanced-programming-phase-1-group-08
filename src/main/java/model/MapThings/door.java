package model.MapThings;


import model.Enum.Door;
import static model.Color_Eraser.*;

public class door extends GameObject {
    private Door door;

    public void setDoor(Door door) {
        this.door = door;
    }

    public Door getDoor() {
        return door;
    }

    @Override
    public String getIcon() {
        return BRIGHT_BROWN+"D";
    }
}
