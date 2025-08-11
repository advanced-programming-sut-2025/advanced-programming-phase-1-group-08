package com.Graphic.model.ClientServer;

import com.Graphic.model.Enum.AllPlants.TreeType;
import com.Graphic.model.Enum.Commands.CommandType;
import com.Graphic.model.Enum.Direction;
import com.Graphic.model.Enum.Door;
import com.Graphic.model.Enum.Fish.FishType;
import com.Graphic.model.Enum.ItemType.*;
import com.Graphic.model.Enum.NPC.NPC;
import com.Graphic.model.Enum.NPC.NPCHouse;
import com.Graphic.model.Enum.SecurityQuestions;
import com.Graphic.model.Enum.ToolsType.*;
import com.Graphic.model.Inventory;
import com.Graphic.model.OtherItem.Fridge;
import com.Graphic.model.Plants.Wood;
import com.Graphic.model.ToolsPackage.MilkPail;
import com.Graphic.model.collisionRect;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.util.DefaultInstantiatorStrategy;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;
import org.objenesis.strategy.StdInstantiatorStrategy;

import java.awt.*;

public class Network {

    public static final int TCP_PORT = 54555;
    public static final int UDP_PORT = 54777;

    public static void register(Server server) {
        Kryo kryo = server.getKryo();
        // دور زدن نیاز به constructor
        kryo.setInstantiatorStrategy(
            new DefaultInstantiatorStrategy(new StdInstantiatorStrategy())
        );

        registerClasses(kryo);
    }

    public static void register(Client client) {
        Kryo kryo = client.getKryo();
        kryo.setInstantiatorStrategy(
            new DefaultInstantiatorStrategy(new StdInstantiatorStrategy())
        );

        registerClasses(kryo);
    }

    public static void registerClasses(Kryo kryo) {
        kryo.setRegistrationRequired(true);
        kryo.setReferences(true);

        kryo.register(CommandType.class);
        kryo.register(SecurityQuestions.class);
        kryo.register(Message.class);
        kryo.register(java.util.HashMap.class);
        kryo.register(java.util.ArrayList.class);
        kryo.register(java.util.LinkedList.class);
        kryo.register(String.class);
        kryo.register(Integer.class);
        kryo.register(int[].class);

        // --- ثبت کلاس‌های پروژه‌ات که توی Message.body استفاده می‌شن ---
        kryo.register(com.Graphic.model.User.class);
        kryo.register(BackPackType.class);
        kryo.register(Inventory.class);
        kryo.register(com.Graphic.model.Items.class);
        kryo.register(com.Graphic.model.MapThings.Tile.class);
        kryo.register(com.Graphic.model.Animall.Animal.class);
        kryo.register(com.Graphic.model.Game.class);
        kryo.register(com.Graphic.model.ClientServer.GameState.class);
        kryo.register(com.Graphic.model.Recipe.class);
        kryo.register(com.Graphic.model.Animall.Animal.class);
        kryo.register(com.Graphic.model.Animall.BarnOrCage.class);
        kryo.register(BarnORCageType.class);
        kryo.register(NPCHouse.class);
        kryo.register(NPC.class);
        kryo.register(com.Graphic.model.Enum.ItemType.MarketType.class);
        kryo.register(Direction.class);
        kryo.register(WallType.class);
        kryo.register(Door.class);
        kryo.register(TreeType.class);
        kryo.register(Fridge.class);
        kryo.register(collisionRect.class);
        kryo.register(AnimalType.class);
        kryo.register(Point.class);
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
        kryo.register(Quantity.class);
        kryo.register(AxeType.class);
        kryo.register(HoeType.class);
        kryo.register(FishingPoleType.class);
        kryo.register(MilkPail.class);
        kryo.register(PickAxeType.class);
        kryo.register(TrashCanType.class);
        kryo.register(FishType.class);
        kryo.register(Wood.class);
        kryo.register(MarketItemType.class);
        kryo.register(FishingPoleType.class);
        kryo.register(AnimalProductType.class);
        kryo.register(WateringCanType.class);
        kryo.register(com.Graphic.model.Plants.BasicRock.class);
        kryo.register(com.Graphic.model.Plants.Fish.class);
        kryo.register(com.Graphic.model.Plants.Food.class);
        kryo.register(com.Graphic.model.Plants.TreesProdct.class);
        kryo.register(com.Graphic.model.Plants.GiantProduct.class);

    }
}
