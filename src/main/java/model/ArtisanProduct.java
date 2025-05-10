package model;

import model.Enum.ItemType.ArtisanType;

import java.util.ArrayList;

public class ArtisanProduct extends Items {
    private ArtisanType type;
    public ArtisanProduct(ArtisanType type ) {
        this.type = type;
    }
    public ArtisanType getType() {
        return type;
    }


}
