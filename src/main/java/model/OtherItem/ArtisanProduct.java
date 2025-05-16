package model.OtherItem;

import model.Enum.ItemType.ArtisanType;
import model.Items;

public class ArtisanProduct extends Items {
    private ArtisanType type;
    public ArtisanProduct(ArtisanType type ) {
        this.type = type;
    }
    public ArtisanType getType() {
        return type;
    }

    @Override
    public String getName() {
        return type.getName();
    }

    @Override
    public int getSellPrice() {
        return type.getPrice();
    }

    @Override
    public int getTakesTime() {
        return type.getTakesTime();
    }
}
