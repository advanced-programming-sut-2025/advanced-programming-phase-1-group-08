package com.Graphic.model.ClientServer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

import java.util.HashMap;
import java.util.ArrayList;

public class Network {
    public static final int TCP_PORT = 54555; // example, جایگزین 8080 کن اگر خواستی
    public static final int UDP_PORT = 54777;

    // این متد باید در هر دو سمت (server/client) صدا زده بشه تا Kryo کلاس‌ها رو ثبت کنه
    public static void register(EndPoint endpoint) {
        Kryo kryo = endpoint.getKryo();
        kryo.register(Message.class);
        kryo.register(java.util.HashMap.class);
        kryo.register(java.util.ArrayList.class);
        kryo.register(java.util.LinkedList.class);
        kryo.register(String.class);
        kryo.register(Integer.class);
        kryo.register(int[].class);

        // --- ثبت کلاس‌های پروژه‌ات که توی Message.body استفاده می‌شن ---
        kryo.register(com.Graphic.model.User.class);
        kryo.register(com.Graphic.model.Items.class);
        kryo.register(com.Graphic.model.MapThings.Tile.class);
        kryo.register(com.Graphic.model.Animall.Animal.class);
        kryo.register(com.Graphic.model.Game.class);
        kryo.register(com.Graphic.model.ClientServer.GameState.class);
        kryo.register(com.Graphic.model.Recipe.class);
        kryo.register(com.Graphic.model.Animall.Animal.class);
        kryo.register(com.Graphic.model.Animall.BarnOrCage.class);
        kryo.register(com.Graphic.model.MapThings.Walkable.class);
        kryo.register(com.Graphic.model.MapThings.GameObject.class);
        kryo.register(com.Graphic.model.MapThings.UnWalkable.class);
        kryo.register(com.Graphic.model.MapThings.door.class);
        kryo.register(com.Graphic.model.MapThings.Wall.class);
        kryo.register(com.Graphic.model.MapThings.WaterTank.class);
        kryo.register(com.Graphic.model.OtherItem.BarsAndOres.class);
        kryo.register(com.Graphic.model.OtherItem.ArtisanProduct.class);
        kryo.register(com.Graphic.model.Places.MarketItem.class);
        kryo.register(com.Graphic.model.Places.GreenHouse.class);
        kryo.register(com.Graphic.model.Places.Market.class);
        kryo.register(com.Graphic.model.Places.Home.class);
        kryo.register(com.Graphic.model.Places.Farm.class);
        kryo.register(com.Graphic.model.Places.Lake.class);
        kryo.register(com.Graphic.model.Places.Mine.class);
        kryo.register(com.Graphic.model.Places.ShippingBin.class);
        kryo.register(com.Graphic.model.Places.Well.class);
        kryo.register(com.Graphic.model.Weather.DateHour.class);
        kryo.register(com.Graphic.model.Weather.Cloud.class);
        kryo.register(com.Graphic.model.ToolsPackage.Tools.class);
        kryo.register(com.Graphic.model.ToolsPackage.Axe.class);
        kryo.register(com.Graphic.model.ToolsPackage.BackPack.class);
        kryo.register(com.Graphic.model.ToolsPackage.Hoe.class);
        kryo.register(com.Graphic.model.ToolsPackage.CraftingItem.class);
        kryo.register(com.Graphic.model.ToolsPackage.FishingPole.class);
        kryo.register(com.Graphic.model.ToolsPackage.MilkPail.class);
        kryo.register(com.Graphic.model.ToolsPackage.PickAxe.class);
        kryo.register(com.Graphic.model.ToolsPackage.Scythe.class);
        kryo.register(com.Graphic.model.ToolsPackage.Axe.class);
        kryo.register(com.Graphic.model.ToolsPackage.Shear.class);
        kryo.register(com.Graphic.model.ToolsPackage.TrashCan.class);
        kryo.register(com.Graphic.model.ToolsPackage.WateringCan.class);
        kryo.register(com.Graphic.model.Plants.MixedSeeds.class);
        kryo.register(com.Graphic.model.Plants.ForagingMinerals.class);
        kryo.register(com.Graphic.model.Plants.ForagingCrops.class);
        kryo.register(com.Graphic.model.Plants.ForagingSeeds.class);
        kryo.register(com.Graphic.model.Plants.TreeSource.class);
        kryo.register(com.Graphic.model.Plants.Tree.class);
        kryo.register(com.Graphic.model.Plants.AllCrops.class);
        kryo.register(com.Graphic.model.Plants.Animalproduct.class);
        kryo.register(com.Graphic.model.Plants.BasicRock.class);
        kryo.register(com.Graphic.model.Plants.Fish.class);
        kryo.register(com.Graphic.model.Plants.Food.class);
        kryo.register(com.Graphic.model.Plants.TreesProdct.class);
        kryo.register(com.Graphic.model.Plants.GiantProduct.class);


        // ... هر کلاسی که تو body پیام‌ها قرار می‌گیره
    }
}
