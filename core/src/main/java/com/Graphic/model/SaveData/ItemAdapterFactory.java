package com.Graphic.model.SaveData;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.Graphic.model.Items;
import com.Graphic.model.OtherItem.ArtisanProduct;
import com.Graphic.model.OtherItem.BarsAndOres;
import com.Graphic.model.Places.MarketItem;
import com.Graphic.model.Plants.*;
import com.Graphic.model.ToolsPackage.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ItemAdapterFactory implements TypeAdapterFactory {


    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if (!Items.class.isAssignableFrom(type.getRawType())) {
            return null;
        }

        Map<String, Class<? extends Items>> itemTypeMap = new HashMap<>();
        itemTypeMap.put("Food", Food.class);
        itemTypeMap.put("BarsAndOres", BarsAndOres.class);
        itemTypeMap.put("BasicRock", BasicRock.class);
        itemTypeMap.put("MarketItem", MarketItem.class);
        itemTypeMap.put("AllCrops", AllCrops.class);
        itemTypeMap.put("Wood", Wood.class);
        itemTypeMap.put("Fish", Fish.class);
        itemTypeMap.put("MixedSeeds", MixedSeeds.class);
        itemTypeMap.put("ArtisanProduct", ArtisanProduct.class);
        itemTypeMap.put("Animalproduct", Animalproduct.class);
        itemTypeMap.put("Axe", Axe.class);
        itemTypeMap.put("CraftingItem", CraftingItem.class);
        itemTypeMap.put("FishingPole", FishingPole.class);
        itemTypeMap.put("ForagingCrops", ForagingCrops.class);
        itemTypeMap.put("ForagingMinerals", ForagingMinerals.class);
        itemTypeMap.put("ForagingSeeds", ForagingSeeds.class);
        itemTypeMap.put("GiantProduct", GiantProduct.class);
        itemTypeMap.put("Hoe", Hoe.class);
        itemTypeMap.put("MilkPail", MilkPail.class);
        itemTypeMap.put("PickAxe", PickAxe.class);
        itemTypeMap.put("Scythe", Scythe.class);
        itemTypeMap.put("Shear", Shear.class);
        itemTypeMap.put("Tools", Tools.class);
        itemTypeMap.put("TrashCan", TrashCan.class);
        itemTypeMap.put("TreeSource", TreeSource.class);
        itemTypeMap.put("TreesProduct", TreesProdct.class);
        itemTypeMap.put("WateringCan", WateringCan.class);

        return new TypeAdapter<T>() {
            @Override
            public void write(JsonWriter out, T value) throws IOException {
                if (value == null) {
                    out.nullValue(); // اگه مقدار null بود، همون null رو ذخیره کن
                    return;
                }

                System.out.println(out.toString() + " ****");
                System.out.println(value + "^^^^^");

                out.beginObject();
                out.name("type").value(value.getClass().getSimpleName());
                out.name("data");
                gson.toJson(value, value.getClass(), out);
                out.endObject();
            }

            @Override
            @SuppressWarnings("unchecked")
            public T read(JsonReader in) throws IOException {
                JsonObject obj = JsonParser.parseReader(in).getAsJsonObject();
                System.out.println(obj.toString() + " 00000 ");
                String typeName = obj.get("Type").getAsString();
                Class<? extends Items> actualClass = itemTypeMap.get(typeName);
                if (actualClass == null) {
                    throw new RuntimeException("Unknown item type: " + typeName);
                }
                // 👇 این خط باعث ارور می‌شد و حالا درست شد:
                return (T) gson.fromJson(obj.get("data"), actualClass);
            }
        };
    }
}
