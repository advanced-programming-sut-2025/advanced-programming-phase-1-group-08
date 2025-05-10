package model;

import model.Enum.ItemType.ArtisanType;

import java.util.ArrayList;

public class ArtisanProduct {
    private ArtisanType type;
    private DateHour dateHour;
    public ArtisanProduct(ArtisanType type , DateHour dateHour) {
        this.type = type;
        this.dateHour = dateHour;
    }
    public ArtisanType getType() {
        return type;
    }
    public DateHour getDateHour() {
        return dateHour;
    }

}
