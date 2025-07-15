package com.Graphic.Controller.MainGame;

import com.Graphic.Main;
import com.Graphic.View.GameMenus.MarketMenu;
import com.Graphic.model.Animall.Animal;
import com.Graphic.model.Animall.BarnOrCage;
import com.Graphic.model.Enum.AllPlants.ForagingMineralsType;
import com.Graphic.model.Enum.AllPlants.ForagingSeedsType;
import com.Graphic.model.Enum.AllPlants.TreesSourceType;
import com.Graphic.model.Enum.Direction;
import com.Graphic.model.Enum.ItemType.*;
import com.Graphic.model.Enum.ToolsType.*;
import com.Graphic.model.Enum.WeatherTime.Season;
import com.Graphic.model.HelpersClass.TextureManager;
import com.Graphic.model.Inventory;
import com.Graphic.model.Items;
import com.Graphic.model.Plants.BasicRock;
import com.Graphic.model.MapThings.Tile;
import com.Graphic.model.MapThings.Walkable;
import com.Graphic.model.Plants.Wood;
import com.Graphic.model.OtherItem.BarsAndOres;
import com.Graphic.model.Places.MarketItem;
import com.Graphic.model.Places.ShippingBin;
import com.Graphic.model.Places.Well;
import com.Graphic.model.Plants.ForagingMinerals;
import com.Graphic.model.Plants.ForagingSeeds;
import com.Graphic.model.Plants.TreeSource;
import com.Graphic.model.HelpersClass.Result;
import com.Graphic.model.ToolsPackage.*;
import com.Graphic.model.collisionRect;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.Map;

import static com.Graphic.Controller.MainGame.GameControllerLogic.checkTilesForCreateBarnOrCage;
import static com.Graphic.Controller.MainGame.GameControllerLogic.getTileByCoordinates;
import static com.Graphic.View.GameMenus.MarketMenu.*;
import static com.Graphic.View.GameMenus.MarketMenu.marketType;
import static com.Graphic.model.App.currentGame;
import static com.Graphic.model.Enum.ItemType.MarketType.*;
import static com.Graphic.model.HelpersClass.Color_Eraser.*;
import static com.Graphic.model.HelpersClass.TextureManager.TEXTURE_SIZE;

public class Marketing {

    public void closeWindow() {
        getCloseButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                removeImage();
                getWindow().clear();
                getWindow().remove();
                showWindow = true;
            }
        });
    }

    public ImageButton createCloseButtonForDialog(Dialog dialog) {
        Texture closeTexture = new Texture(Gdx.files.internal("Mohamadreza/close.png"));
        Drawable closeDrawable = new TextureRegionDrawable(new TextureRegion(closeTexture));
        ImageButton closeButton = new ImageButton(closeDrawable);
        closeButton.setSize(50,50);
        closeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                dialog.remove();
            }
        });
        return closeButton;
    }

    public Result checkBuy(Items item , MarketType marketType) {
        if (item instanceof BackPack) {
            if (currentGame.currentPlayer.getBackPack().getType().equals(BackPackType.DeluxePack)
                || currentGame.currentPlayer.getBackPack().getType().equals( ((BackPack) item).getType()) ) {
                return new Result(false, "4");
            }
        }
        if (item instanceof FishingPole) {
            if ( ! ((FishingPole) item).type.checkAbility(currentGame.currentPlayer.getLevelFishing())) {
                return new Result(false , "5");
            }
        }
        if (item.getRemindInShop(marketType) == 0) {
            return new Result(false,"1");
        }
        if (item.getMarketPrice(marketType) > currentGame.currentPlayer.getMoney() ) {
            return new Result(false,"2");
        }
        if (currentGame.currentPlayer.getBackPack().getType().getRemindCapacity() == 0) {
            if (! currentGame.currentPlayer.getBackPack().inventory.Items.containsKey(item)) {
                return new Result(false,"3");
            }
        }
        return new Result(true,"0");

    }

    private void makeCoinButton(Table table, TextButton coinButton, Table innerTable, String name, int price, int remindInShop) {
        Label label;
        innerTable.add().expandX();
        coinButton.add(innerTable).grow();

        table.add(coinButton).size(900,48);
        label = new Label(name, getSkin());
        coinButton.add(label).left().padLeft(50);
        coinButton.add().expandX();
        label = new Label(""+ price, getSkin());
        coinButton.add(label).padRight(60);
        coinButton.add(new Image(getCoinTexture())).size(20,20).padRight(50);
        if (remindInShop == 0) {
            coinButton.setColor(Color.DARK_GRAY);
        }
        table.row();
    }

    public void init() {
        currentGame.currentPlayer.setDirection(Direction.Up);
        currentGame.currentPlayer.sprite.setPosition(79 ,24 );
        currentGame.currentPlayer.sprite.setSize(16 , 16);
        currentGame.currentPlayer.sprite.draw(Main.getBatch());
        closeWindow();
    }

    public void move() {
        float x = currentGame.currentPlayer.sprite.getX();
        float y = currentGame.currentPlayer.sprite.getY();

        if (Gdx.input.isKeyPressed(Input.Keys.U)) {
            System.out.println(currentGame.currentPlayer.sprite.getX() + ", " + currentGame.currentPlayer.sprite.getY());
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            InputGameController.moveAnimation();
            currentGame.currentPlayer.setDirection(Direction.Up);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            InputGameController.moveAnimation();
            currentGame.currentPlayer.setDirection(Direction.Down);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            InputGameController.moveAnimation();
            currentGame.currentPlayer.setDirection(Direction.Left);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            InputGameController.moveAnimation();
            currentGame.currentPlayer.setDirection(Direction.Right);
        }

        else {
            currentGame.currentPlayer.sprite.draw(Main.getBatch());
            return;
        }

        currentGame.currentPlayer.sprite.setSize(16 , 32);


        currentGame.currentPlayer.sprite.
            setPosition(x + currentGame.currentPlayer.getDirection().getX() * 50 * Gdx.graphics.getDeltaTime(),
                y - currentGame.currentPlayer.getDirection().getY() * 50 * Gdx.graphics.getDeltaTime());

        if (! checkColision()) {
            currentGame.currentPlayer.sprite.setPosition(x , y);
        }

        currentGame.currentPlayer.sprite.draw(Main.getBatch());
    }

    public void buy(TextButton button , Items item , MarketType marketType) {
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if (! checkBuy(item, marketType).IsSuccess()) {
                    Texture tex = new Texture(Gdx.files.internal("Mohamadreza/Error.png"));
                    NinePatch patch = new NinePatch(tex,10,10,3,3);
                    Drawable background = new NinePatchDrawable(patch);
                    Window.WindowStyle style = new Window.WindowStyle();
                    style.background = background;
                    style.titleFont = getFont();
                    Dialog dialog = new Dialog("Error",style);
                    Label content = new Label(MarketType.endLimit(checkBuy(item,marketType).massage()), new Label.LabelStyle(getFont() , Color.BLACK));
                    dialog.getContentTable().add(content);
                    ImageButton closeButton = createCloseButtonForDialog(dialog);
                    dialog.getTitleTable().add(closeButton).padRight(5).padTop(3).right();
                    dialog.show(getStage());
                    dialog.setPosition(400,100);
                    dialog.setSize(1000,48);
                }
                else if (item instanceof BackPack) {
                    currentGame.currentPlayer.getBackPack().setType(((BackPack) item).getType());
                }
                else {

                    item.setRemindInShop(item.getRemindInShop(marketType) -1 , marketType);
                    currentGame.currentPlayer.increaseMoney(- item.getMarketPrice(marketType));

                    if (currentGame.currentPlayer.getBackPack().inventory.Items.containsKey(item)) {
                        currentGame.currentPlayer.getBackPack().inventory.Items.compute(item,(k,v) -> v + 1);
                    }
                    else {
                        currentGame.currentPlayer.getBackPack().inventory.Items.put(item, 1);
                    }
                    if (item.getRemindInShop(marketType) == 0) {
                        button.setColor(Color.DARK_GRAY);
                    }
                }
            }
        });
    }

    public void buyBarnOrCage(TextButton button , BarnOrCage barnOrCage) {

        button.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                removeImage();
                getWindow().clear();
                getWindow().remove();
                Texture texture = TextureManager.get(barnOrCage.getBarnORCageType().getPath());
                Sprite sprite = new Sprite(texture);
                sprite.setAlpha(0.5f);
                setWithMouse(sprite);
                choosePlace = true;
            }
        });
    }

    public void moveTextureWithMouse(Sprite sprite) {
        if (choosePlace) {
            printMapForCreate();
            sprite.setPosition(getVector().x - sprite.getWidth() / 2, getVector().y - sprite.getHeight() / 2);
            sprite.draw(Main.getBatch());
        }
    }

    public void printMapForCreate() {
        if (Gdx.input.isKeyPressed(Input.Keys.T)) {
            System.out.println(currentGame.currentPlayer.topLeftX + " " + currentGame.currentPlayer.topLeftY);
        }
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                try {
                    Main.getBatch().draw(TextureManager.get("Places/Walkable.png"),
                        TEXTURE_SIZE * 2 * (i),
                        TEXTURE_SIZE * 2 * (30 -j) ,
                        TEXTURE_SIZE * 2 , TEXTURE_SIZE * 2);

                    Main.getBatch().draw(getTileByCoordinates(i + 60 * currentGame.currentPlayer.topLeftX , j + 60 * currentGame.currentPlayer.topLeftY)
                            .getGameObject()
                            .getSprite(TextureManager.get(getTileByCoordinates(i , j).getGameObject().getIcon())) ,
                        TEXTURE_SIZE * 2 * (i) ,
                        TEXTURE_SIZE * 2 * (30 - j) ,
                        TEXTURE_SIZE * 2 , TEXTURE_SIZE * 2);

                } catch (Exception e) {

                }
            }
        }
    }


    public void createSelectBox(SelectBox selectBox , MarketType marketType) {
        selectBox.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                String selected = selectBox.getSelected().toString();
                    if (selected.equals("All Products")) {
                        removeImage();
                        getWindow().clear();
                        getWindow().remove();
                        switch (marketType) {
                            case JojaMart -> showJojaProducts(1,true);
                            case PierreGeneralStore -> showPierreProducts(1,true);
                            case StardropSaloon -> showStardropProducts(1,true);
                            case FishShop -> showFishProducts(1,true);
                            case CarpenterShop -> showCarpenterProducts(1,true);
                        }
                    }
                    if (selected.equals("Available Products")) {
                        removeImage();
                        getWindow().clear();
                        getWindow().remove();
                        switch (marketType) {
                            case JojaMart -> showJojaProducts(2,true);
                            case PierreGeneralStore -> showPierreProducts(2,true);
                            case StardropSaloon -> showStardropProducts(2,true);
                            case FishShop -> showFishProducts(2,true);
                            case CarpenterShop -> showCarpenterProducts(2,true);
                        }
                    }
            }
        });
    }

    public void createWindow(ScrollPane pane , Image image ) {
        getWindow().add(pane).width(1000).height(700);
        getWindow().pack();
        getWindow().setPosition(Gdx.graphics.getWidth()/2 - 500, Gdx.graphics.getHeight()/2 - 350);
        getWindow().setVisible(true);
        if (! MarketMenu.getStage().getActors().contains(image , true)) {
            MarketMenu.getStage().addActor(image);
        }
        if (! MarketMenu.getStage().getActors().contains(getWindow() , true)) {
            MarketMenu.getStage().addActor(getWindow());
        }
    }

    public Table createTable(MarketType marketType) {
        Table table = new Table();
        SelectBox<String> filter = new SelectBox<>(getSkin());
        filter.setItems("All Products","Available Products");
        createSelectBox(filter , marketType);
        table.add(filter);
        table.add().expandX();
        table.add(getCloseButton()).size(50,50).pad(5);

        return table;
    }

    public void listForagingSeeds(int id ,Table table , MarketType marketType , Label label) {
        for (ForagingSeedsType f : ForagingSeedsType.values()) {
            if (f.getMarketTypes().contains(marketType) && (id == 1 || new ForagingSeeds(f).getRemindInShop(marketType) > 0) ) {
                TextButton coinButton = new TextButton("",getSkin());
                coinButton.clearChildren();
                buy(coinButton , new ForagingSeeds(f) , marketType);

                Table innerTable = new Table();
                innerTable.add(new Image(new Texture(Gdx.files.internal(f.getInventoryIconPath())))).left().padLeft(60).size(24, 24);

                makeCoinButton(table , coinButton , innerTable, f.getDisplayName(),
                    f.getPrice(marketType),new ForagingSeeds(f).getRemindInShop(marketType) );
            }

        }
    }

    public void listMarketItems(int id , Table table , MarketType marketType , Label label) {

        for (MarketItemType f : MarketItemType.values()) {
            if (f.getMarketTypes().contains(marketType) && (id ==1 || new MarketItem(f).getRemindInShop(marketType) > 0)) {
                TextButton coinButton = new TextButton("",getSkin());
                coinButton.clearChildren();
                if (f.equals(MarketItemType.Wood)) {
                    buy(coinButton , new Wood() , marketType);
                }
                else if (f.equals(MarketItemType.Stone)) {
                    buy(coinButton , new BasicRock() , marketType);
                }
                else {
                    buy(coinButton, new MarketItem(f), marketType);
                }

                Table innerTable = new Table();
                innerTable.add(new Image(new Texture(Gdx.files.internal(f.getPath())))).left().padLeft(60).size(24, 24);

                makeCoinButton(table,coinButton,innerTable,f.getName(),
                    new MarketItem(f).getMarketPrice(marketType),
                    new MarketItem(f).getRemindInShop(marketType) );
            }
        }
    }

    public void listBarnOrCage(int id , Table table , MarketType marketType , Label label) {
        for (BarnORCageType f : BarnORCageType.values()) {
            if (id == 1 || new BarnOrCage(f , 0 , 0).getRemindInShop(marketType) > 0) {
                TextButton coinButton = new TextButton("",getSkin());
                coinButton.clearChildren();
                buyBarnOrCage(coinButton , new BarnOrCage(f , 0 , 0));
                Table innerTable = new Table();
                innerTable.add(new Image(new Texture(Gdx.files.internal(f.getPath())))).left().padLeft(60).size(24, 24);

                makeCoinButton(table , coinButton , innerTable , f.getName() , f.getPrice(),
                    new BarnOrCage(f,0,0).getRemindInShop(marketType));
            }
        }
    }


    public void showJojaProducts(int id , boolean Filter) {
        if ((Gdx.input.isKeyPressed(Input.Keys.SPACE) && showWindow) || Filter) {
            showWindow = false;

            Table table = createTable(JojaMart);
            ScrollPane pane = new ScrollPane(table,getSkin());
            Image image = getImage();
            Label label = null;
            pane.setFadeScrollBars(false);
            image.setSize(MarketMenu.getStage().getWidth(), MarketMenu.getStage().getHeight());
            listForagingSeeds(id , table , JojaMart, label);
            listMarketItems(id,table,JojaMart,label);
            pane.setWidget(table);
            createWindow(pane , image);

        }

    }

    public void showPierreProducts(int id , boolean Filter) {
        if ((Gdx.input.isKeyPressed(Input.Keys.SPACE) && showWindow) || Filter) {
            showWindow = false;

            Table table = createTable(PierreGeneralStore);
            ScrollPane pane = new ScrollPane(table,getSkin());
            Image image = getImage();
            Label label = null;
            pane.setFadeScrollBars(false);

            image.setSize(MarketMenu.getStage().getWidth(), MarketMenu.getStage().getHeight());
            listForagingSeeds(id , table , PierreGeneralStore, label);
            listMarketItems(id,table,PierreGeneralStore,label);

            for (BackPackType f : BackPackType.values()) {
                if (f.getInitialShopLimit() == 1) {
                    BackPack backPack = new BackPack();
                    backPack.setType(f);
                    if (id == 1 || backPack.getRemindInShop(marketType) > 0) {
                        TextButton coinButton = new TextButton("",getSkin());
                        coinButton.clearChildren();
                        buy(coinButton , backPack , PierreGeneralStore);

                        Table innerTable = new Table();
                        innerTable.add(new Image(new Texture(Gdx.files.internal(f.getPath())))).left().padLeft(60).size(24, 24);
                        makeCoinButton(table, coinButton, innerTable, f.getName(),
                            f.getPrice(), backPack.getRemindInShop(PierreGeneralStore));
                    }
                }
            }

            pane.setWidget(table);
            createWindow(pane , image);

        }
    }

    public void showStardropProducts(int id , boolean Filter) {
        if ((Gdx.input.isKeyPressed(Input.Keys.SPACE) && showWindow) || Filter) {
            showWindow = false;

            Table table = createTable(StardropSaloon);
            ScrollPane pane = new ScrollPane(table,getSkin());
            Image image = getImage();
            Label label = null;
            pane.setFadeScrollBars(false);
            image.setSize(MarketMenu.getStage().getWidth(), MarketMenu.getStage().getHeight());
            listMarketItems(id,table,StardropSaloon,label);
            pane.setWidget(table);
            createWindow(pane , image);
        }
    }

    public void showFishProducts(int id , boolean Filter) {
        if ((Gdx.input.isKeyPressed(Input.Keys.SPACE) && showWindow) || Filter) {
            showWindow = false;

            Table table = createTable(FishShop);
            ScrollPane pane = new ScrollPane(table,getSkin());
            Image image = getImage();
            Label label = null;
            pane.setFadeScrollBars(false);
            image.setSize(MarketMenu.getStage().getWidth(), MarketMenu.getStage().getHeight());
            listMarketItems(id,table,FishShop,label);

            for (FishingPoleType f : FishingPoleType.values()) {
                if (id == 1 || f.shopLimit > 0) {
                    TextButton coinButton = new TextButton("",getSkin());
                    coinButton.clearChildren();
                    FishingPole fishingPole = new FishingPole();
                    fishingPole.type = f;
                    buy(coinButton , fishingPole , FishShop);

                    Table innerTable = new Table();
                    innerTable.add(new Image(new Texture(Gdx.files.internal(f.getIconPath())))).left().padLeft(60).size(24, 24);
                    makeCoinButton(table, coinButton, innerTable, f.getName(), f.getPrice(), fishingPole.getRemindInShop(FishShop));
                }
            }
            pane.setWidget(table);
            createWindow(pane , image);

        }
    }

    public void showCarpenterProducts(int id , boolean Filter) {
        if ((Gdx.input.isKeyPressed(Input.Keys.SPACE) && showWindow) || Filter) {
            showWindow = false;

            Table table = createTable(CarpenterShop);
            ScrollPane pane = new ScrollPane(table,getSkin());
            Image image = getImage();
            Label label = null;
            pane.setFadeScrollBars(false);
            image.setSize(MarketMenu.getStage().getWidth(), MarketMenu.getStage().getHeight());
            listMarketItems(id,table,CarpenterShop,label);
            listBarnOrCage(id, table,CarpenterShop,label);
            pane.setWidget(table);
            createWindow(pane , image);
        }
    }


    public boolean checkColision() {
        for (collisionRect rect : MarketMenu.marketType.getRects()) {
            if (! rect.checkCollision(currentGame.currentPlayer)) {
                return false;
            }
        }
        return true;
    }

    public MarketType findEnteredShopType() {
        for (MarketType market : MarketType.values()) {
            if (currentGame.currentPlayer.getPositionX() >= market.getTopleftx() && currentGame.currentPlayer.getPositionY() >= market.getToplefty()) {
                if (currentGame.currentPlayer.getPositionX() < market.getTopleftx() + market.getWidth() && currentGame.currentPlayer.getPositionY() < market.getToplefty() + market.getHeight() ) {
                    return market;
                }
            }
        }
        return null;
    }


    public Result MarnieProducts(int id) {
        StringBuilder result=new StringBuilder();
        if (id == 1) {
            result.append("Marnie's Ranch Products :\n");
        }
        else if (id == 2) {
            result.append("Marnie's Ranch available Products :\n");
        }
        for (AnimalType animalType : AnimalType.values()) {
            int remind=animalType.getRemindInShop();
            if (id == 1 || (id ==2 && remind > 0)) {
                result.append(animalType.name()).append(", Price: ").append(animalType.getPrice()).append("\n");
            }
        }
        result.append(MarketItemType.Hay.getName()).append(", Price: ").append(MarketItemType.Hay.getPrice(0)).append("\n");
        if (id ==1 || new MilkPail().getRemindInShop(null) > 0) {
            result.append("Milk Pail").append(", Price: ").append(MilkPail.coinNeeded).append("\n");
        }
        if (id == 1 || Shear.getRemindInshop() > 0) {
            result.append("Shear").append(", Price: ").append(Shear.getCoinNeeded()).append("\n");
        }

        return new Result(true , result.toString());
    }

    public Result BlackSmithProducts() {
        StringBuilder result=new StringBuilder();
        result.append("Copper Ore, ").append("Price: ").append("75\n");
        result.append("Iron Ore, ").append("Price: ").append("150\n");
        result.append("Gold Ore, ").append("Price: ").append("400\n");
        result.append("Coal, ").append("Price: ").append("150\n");
        return new Result(true , result.toString());
    }

    public Result StardropSaloonProduct( int id) {
        StringBuilder result=new StringBuilder();
        if (id == 1) {
            result.append(MarketType.StardropSaloon.name()).append(" Products:").append("\n");
        }
        else if (id == 2) {
            result.append(MarketType.StardropSaloon.name()).append(" available Products:").append("\n");
        }
        for (MarketItemType marketItem : MarketItemType.values()) {
            if (marketItem.getMarketTypes().contains(MarketType.StardropSaloon)) {
                int remind= marketItem.getOtherShopsLimit();
                if (id == 1 || remind > 0 ) {
                    result.append(marketItem.getName()).append(", Price: ").append(marketItem.getPrice(0)).append("\n");
                }
            }

        }
        return new Result(true , result.toString());
    }

    public Result CarpenterProducts(int id) {
        StringBuilder result=new StringBuilder();
        if (id == 1) {
            result.append(MarketType.CarpenterShop.name()).append(" Products:").append("\n");
        }
        else if (id == 2) {
            result.append(MarketType.CarpenterShop.name()).append(" available Products:").append("\n");
        }
        for (BarnORCageType barnORCageType : BarnORCageType.values()) {
            int remind = barnORCageType.getShopLimit();
            if (id == 1 || remind > 0 ) {
                result.append(barnORCageType.name()).append(", Price: ").append(barnORCageType.getPrice()).append(", Wood: ")
                        .append(barnORCageType.getWoodNeeded()).append(", Stone: ").append(barnORCageType.getStoneNeeded()).append("\n");
            }
        }

        if (id == 1 || Well.getRemindInShop() > 0) {
            result.append("Well, Price: ").append(Well.getNeededCoin()).append(", Stone: ").append(Well.getNeededStone()).append("\n");
        }
        if (id ==1 || ShippingBin.getRemindInShop() > 0) {
            result.append("Shipping Bin, Price: ").append(ShippingBin.getCoinNeeded()).append(", Wood: ").append(ShippingBin.getWoodNeeded()).append("\n");
        }

        for (MarketItemType marketItem : MarketItemType.values()) {
            if (marketItem.getMarketTypes().contains(MarketType.CarpenterShop)) {
                int remind= marketItem.getOtherShopsLimit();
                if (id == 1 || remind > 0 ) {
                    result.append(marketItem.getName()).append(", Price: ").append(marketItem.getPrice(0)).append("\n");
                }
            }
        }

        return new Result(true , result.toString());
    }

    public Result JojaMartProducts(int id) {
        StringBuilder result=new StringBuilder();
        if (id == 1) {
            result.append(JojaMart.name()).append(" Products:").append("\n");
        }
        else if (id == 2) {
            result.append(JojaMart.name()).append(" available Products:").append("\n");
        }

        for (ForagingSeedsType foragingSeedsType : ForagingSeedsType.values()) {
            if (foragingSeedsType.getMarketTypes().contains(JojaMart)) {
                int remind = foragingSeedsType.JojaMartLimit;
                if (id == 1 || (remind > 0 && checkSeason(foragingSeedsType.getDisplayName()) ) ) {
                    int price=foragingSeedsType.getPrice(JojaMart);
                    result.append(foragingSeedsType.getDisplayName()).append(", Price: ").append(price).append("\n");
                }
            }
        }
        for (MarketItemType marketItem : MarketItemType.values()) {
            if (marketItem.getMarketTypes().contains(JojaMart)) {
                int remind = marketItem.getOtherShopsLimit();
                if (id == 1 || (remind > 0 && checkSeason(marketItem.getName()) ) ) {
                    result.append(marketItem.getName()).append(", Price: ").append(marketItem.getPrice(0)).append("\n");
                }
            }
        }
        return new Result(true , result.toString());
    }

    public Result PierreProducts(int id) {
        StringBuilder result=new StringBuilder();
        if (id == 1) {
            result.append(MarketType.PierreGeneralStore.name()).append(" Products:").append("\n");
        }
        else if (id == 2) {
            result.append(MarketType.PierreGeneralStore.name()).append(" available Products:").append("\n");
        }
        for (ForagingSeedsType foragingSeedsType : ForagingSeedsType.values()) {
            if (foragingSeedsType.getMarketTypes().contains(MarketType.PierreGeneralStore)) {
                int remind = foragingSeedsType.PierrGeneralLimit;
                if (id == 1 || remind > 0 ) {
                    int price=foragingSeedsType.getPrice(MarketType.PierreGeneralStore);
                    result.append(foragingSeedsType.name()).append(", Price: ").append(price).append("\n");
                }
            }
        }
        for (MarketItemType marketItem : MarketItemType.values()) {
            if (marketItem.getMarketTypes().contains(MarketType.PierreGeneralStore)) {
                int remind = marketItem.getPierreShopsLimit();
                if (id == 1 || remind > 0 ) {
                    result.append(marketItem.getName()).append(", Price: ").append(marketItem.getPrice(1)).append("\n");
                }
            }
        }
        for (BackPackType backPackType : BackPackType.values()) {
            if (backPackType.getInitialShopLimit() == 1) {
                int remind = backPackType.getRemindInShop();
                if (id == 1 || remind > 0 ) {
                    result.append(backPackType.getName()).append(", Price: ").append(backPackType.getPrice()).append("\n");
                }
            }
        }
        return new Result(true , result.toString());
    }

    public Result FishSoupProducts(int id) {
        StringBuilder result=new StringBuilder();
        if (id == 1) {
            result.append(MarketType.FishShop.name()).append(" Products:").append("\n");
        }
        else if (id == 2) {
            result.append(MarketType.FishShop.name()).append(" available Products:").append("\n");
        }
        for (MarketItemType marketItem : MarketItemType.values()) {
            if (marketItem.getMarketTypes().contains(MarketType.FishShop)) {
                int remind = marketItem.getOtherShopsLimit();
                if (id == 1 || remind > 0 ) {
                    result.append(marketItem.getName()).append(", Price: ").append(marketItem.getPrice(0)).append("\n");
                }
            }
        }
        for (FishingPoleType fishingPoleType : FishingPoleType.values()) {
            int remind = fishingPoleType.shopLimit;
            if (id == 1 || remind > 0 ) {
                result.append(fishingPoleType.getName()).append(", Price: ").append(fishingPoleType.getPrice()).append("\n");
            }
        }
        return new Result(true , result.toString());
    }

    public Result showAllProducts(int id) {
        MarketType marketType = findEnteredShopType();

        if (marketType == null) {
            return new Result(false , "you are not in any shop");
        }
        switch (marketType) {
            case MarnieRanch -> {return MarnieProducts(id) ; }
            case StardropSaloon -> {return StardropSaloonProduct(id) ; }
            case CarpenterShop -> {return CarpenterProducts(id) ; }
            case JojaMart -> {return JojaMartProducts(id) ; }
            case PierreGeneralStore -> {return PierreProducts(id) ; }
            case FishShop -> {return FishSoupProducts(id) ; }
            case Blacksmith -> {return  BlackSmithProducts() ;}
        }

        return null;

    }


    public void takeAnimalToBarnOrCage(Animal animal , BarnOrCage barnOrCage ) {
        for (int x=barnOrCage.topLeftX + 1 ; x < barnOrCage.topLeftX+ barnOrCage.getBarnORCageType().getWidth() - 1 ; x++) {
            for (int y=barnOrCage.topLeftY + 1 ; y< barnOrCage.topLeftY + barnOrCage.getBarnORCageType().getHeight() - 1 ; y++) {

                Tile tile = getTileByCoordinates(x,y);

                if (tile.getGameObject() instanceof Walkable) {
                    tile.setGameObject(animal);
                    animal.setPositionX(x);
                    animal.setPositionY(y);
                    return;
                }
            }
        }
    }


    public Result buyAnimal(String animal,String name) {
        AnimalType animalType=null;
        for (AnimalType animalType1 : AnimalType.values()) {
            if (animalType1.getType().equals(animal)) {
                animalType=animalType1;
            }
        }
        if (animalType==null) {
            return new Result(false , "No such type of Animal!");
        }
        for (BarnOrCage barnOrCage : currentGame.currentPlayer.BarnOrCages) {
            for (Animal animals : barnOrCage.getAnimals()) {
                if (animals.getName().equals(name)) {
                    return new Result(false , "You already have Animal with name "+name);
                }
            }
        }
        if (currentGame.currentPlayer.getMoney() < animalType.getPrice()) {
            return new Result(false , "You don't have enough money to buy this animal");
        }
        if (animalType.getRemindInShop() == 0) {
            return new Result(false , "The purchase limit for this product has been reached");
        }
        boolean canBuyAnimal = false;
        for (BarnOrCage barnOrCage : currentGame.currentPlayer.BarnOrCages) {
            if (animalType.getBarnorcages().contains(barnOrCage.getBarnORCageType())) {
                if (barnOrCage.getReminderCapacity() > 0) {
                    canBuyAnimal = true;
                    Animal newAnimal = new Animal(animalType , 0 , name , false , false , false , false , currentGame.currentDate.clone().getDate());
                    takeAnimalToBarnOrCage(newAnimal , barnOrCage);
                    barnOrCage.animals.add(newAnimal);
                    animalType.increaseRemindInShop(-1);
                    break;
                }
            }
        }
        if (! canBuyAnimal) {
            return new Result(false , "you don't have enough capacity or suitable place for this animal");
        }

        currentGame.currentPlayer.increaseMoney(- animalType.getPrice());

        return new Result(true , "you bought this animal successfully");

    }

    public Result createBarnOrCage(int topLeftX, int topLeftY, String name) {
        BarnORCageType barnORCageType=null;
        for (BarnORCageType x : BarnORCageType.values()) {
            if (x.getName().equals(name)) {
                barnORCageType=x;
            }
        }

        if (barnORCageType==null) {
            return new Result(false , "No such type of BarnORCage!");
        }

        if (barnORCageType.getShopLimit() == 0) {
            return new Result(false , RED+"The purchase limit for this product has been reached" + RESET);
        }

        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;
        InputGameController gameController = InputGameController.getInstance();

        if (findEnteredShopType() != MarketType.MarnieRanch) {
            return new Result(false , "you can't create makeCoinButton barn or cage because you are not in Marnie's Ranch Market");
        }

        if (!checkTilesForCreateBarnOrCage(topLeftX, topLeftY, barnORCageType.getWidth(), barnORCageType.getHeight())) {
            return new Result(false, "you can't create barn or cage on this coordinate!");
        }

        int Wood = 0;
        int Stone= 0;
        for (Map.Entry <Items, Integer> entry:inventory.Items.entrySet()) {
            if (entry.getKey() instanceof Wood) {
                Wood=entry.getValue();
            }
            if (entry.getKey() instanceof BasicRock) {
                Stone=entry.getValue();
            }
        }

        if (barnORCageType.getWoodNeeded() > Wood) {
            return new Result(false , "you can't create barn or cage because you don't have enough wood!");
        }
        if (barnORCageType.getStoneNeeded() > Stone) {
            return new Result(false , "you can't create barn or cage because you don't have enough stone!");
        }
        if (barnORCageType.getPrice() > currentGame.currentPlayer.getMoney() ) {
            return new Result(false , "you can't create barn or cage because you don't have enough money!");
        }


        BarnOrCage barnOrCage = new BarnOrCage(barnORCageType, topLeftX, topLeftY);

        for (Map.Entry <Items , Integer> entry:inventory.Items.entrySet()) {
            if (entry.getKey() instanceof Wood) {
                entry.setValue(entry.getValue() - barnORCageType.getWoodNeeded());
            }
            if (entry.getKey() instanceof BasicRock) {
                entry.setValue(entry.getValue() - barnORCageType.getStoneNeeded());
            }
        }

        inventory.Items.entrySet().removeIf(entry -> entry.getValue() == 0);


        currentGame.currentPlayer.increaseMoney(- barnORCageType.getPrice());

        if (barnORCageType.equals(BarnORCageType.Barn) || barnORCageType.equals(BarnORCageType.BigBarn)
                || barnORCageType.equals(BarnORCageType.DeluxeBarn)) {
            barnOrCage.setCharactor('b');
        }
        else {
            barnOrCage.setCharactor('c');
        }

        for (int i = topLeftX; i < topLeftX + barnORCageType.getWidth(); i++) {
            for (int j = topLeftY; j < topLeftY + barnORCageType.getHeight(); j++) {

                if (i == topLeftX || i == topLeftX + barnORCageType.getWidth() -1 || j == topLeftY || j == topLeftY + barnORCageType.getHeight() -1) {
                    Tile tile = getTileByCoordinates(i , j );
                    tile.setGameObject(barnOrCage);
                }
            }
        }

        currentGame.currentPlayer.BarnOrCages.add(barnOrCage);
        barnORCageType.setShopLimit(0);

        return new Result(true, barnORCageType.getName() + " created successfully!");

    }

    public Result createWell(int topLeftX , int topLeftY) {
        InputGameController gameController = InputGameController.getInstance();
        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;
        Marketing marketing = new Marketing();

        if (marketing.findEnteredShopType() != MarketType.MarnieRanch) {
            return new Result(false , "you can't create makeCoinButton Well because you are not in Marnie's Ranch Market");
        }

        if (Well.getNeededStone() == 0) {
            return new Result(false , RED+"The purchase limit for this product has been reached" + RESET);
        }

        if (!checkTilesForCreateBarnOrCage(topLeftX, topLeftY, Well.getWidth(), Well.getHeight())) {
            return new Result(false, "you can't create makeCoinButton Well on this coordinate!");
        }

        int Stone= 0;
        for (Map.Entry <Items , Integer> entry:inventory.Items.entrySet()) {
            if (entry.getKey() instanceof BasicRock) {
                Stone=entry.getValue();
            }
        }

        if (Well.getNeededStone() > Stone) {
            return new Result(false , "you can't create well because you don't have enough stone!");
        }

        if (Well.getNeededCoin() > currentGame.currentPlayer.getMoney() ) {
            return new Result(false , "you can't create well because you don't have enough money!");
        }

        for (Map.Entry <Items , Integer> entry:inventory.Items.entrySet()) {
            if (entry.getKey() instanceof BasicRock) {
                entry.setValue(entry.getValue() - Well.getNeededStone());
                break;
            }
        }
        currentGame.currentPlayer.increaseMoney(- Well.getNeededCoin());

        Well well = new Well(topLeftX, topLeftY);
        well.setCharactor('w');

        for (int i = topLeftX; i < topLeftX + Well.getWidth(); i++) {
            for (int j = topLeftY; j < topLeftY + Well.getHeight(); j++) {

                if (i == topLeftX || i == topLeftX + Well.getWidth() -1 || j == topLeftY || j == topLeftY + Well.getHeight() -1) {
                    Tile tile = getTileByCoordinates(i , j );
                    tile.setGameObject(well);
                }
            }
        }
        Well.setRemindInShop(0);
        return new Result(true, "Well Created Successfully");

    }

    public Result createShippingBin(int topLeftX , int topLeftY) {
        InputGameController gameController = InputGameController.getInstance();
        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;
        Marketing marketing = new Marketing();

        if (marketing.findEnteredShopType() != MarketType.CarpenterShop) {
            return new Result(false , "you can't create makeCoinButton Shipping Bin because you are not in Carpenter Market");
        }

        if (!checkTilesForCreateBarnOrCage(topLeftX, topLeftY, ShippingBin.getWidth(), ShippingBin.getHeight())) {
            return new Result(false, "you can't create makeCoinButton Shipping Bin on this coordinate!");
        }

        int Wood= 0;
        for (Map.Entry <Items , Integer> entry:inventory.Items.entrySet()) {
            if (entry.getKey() instanceof Wood) {
                Wood=entry.getValue();
            }
        }

        if (ShippingBin.getWoodNeeded() > Wood) {
            return new Result(false , "you can't create Shipping Bin because you don't have enough Wood!");
        }

        if (ShippingBin.getCoinNeeded() > currentGame.currentPlayer.getMoney() ) {
            return new Result(false , "you can't create Shipping Bin because you don't have enough money!");
        }



        ShippingBin shippingBin = new ShippingBin(topLeftX, topLeftY);
        shippingBin.setCharactor('s');

        Tile tile = getTileByCoordinates(topLeftX , topLeftY);
        if (tile.getGameObject() instanceof Walkable) {
            tile.setGameObject(shippingBin);
        }
        else {
            return new Result(false , RED + "you can't create shipping been on this coordinate!" +RESET);
        }

        for (Map.Entry <Items , Integer> entry:inventory.Items.entrySet()) {
            if (entry.getKey() instanceof Wood) {
                entry.setValue(entry.getValue() - ShippingBin.getWoodNeeded());
            }
        }
        inventory.Items.entrySet().removeIf(entry -> entry.getValue() == 0);
        currentGame.currentPlayer.increaseMoney(- ShippingBin.getCoinNeeded());

        currentGame.currentPlayer.getFarm().shippingBins.add(shippingBin);
        return new Result(true, "Shipping Bin Created Successfully");
    }

    public Result purchaseFromBlackSmith(String name , Integer amount) {
        if (currentGame.currentDate.getHour() < MarketType.Blacksmith.getStartHour() || currentGame.currentDate.getHour() > MarketType.Blacksmith.getEndHour()) {
            return new Result(false , RED + "Sorry. Store is close at this time");
        }

        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;
        if (amount == null) {
            amount = 1;
        }
        if (name.equals("Coal") ) {
            ForagingMinerals Coal = new ForagingMinerals(ForagingMineralsType.COAL);
            if (currentGame.currentPlayer.getMoney() < amount * 150) {
                return new Result(false , RED + "Not enough money!" + RESET);
            }
            if (inventory.Items.containsKey(Coal)) {
                Integer finalAmount = amount;
                inventory.Items.compute(Coal , (key, value) -> value + finalAmount);
            }
            else {
                if (currentGame.currentPlayer.getBackPack().getType().getRemindCapacity() == 0) {
                    return new Result(false , RED + "Not enough remind capacity!" + RESET);
                }
                inventory.Items.put(Coal, amount);
            }

            currentGame.currentPlayer.increaseMoney(- amount * 150);
            return new Result(true , BLUE + "You purchased "+name+ " successfully!");
        }
        BarsAndOres b = null;

        for (BarsAndOreType barsAndOreType : BarsAndOreType.values()) {
            if (barsAndOreType.name().equals(name) && barsAndOreType.getMarketType() != null) {
                b = new BarsAndOres(barsAndOreType);
                break;
            }
        }
        if (b == null) {
            return new Result(false , "Products Not found");
        }

        if (currentGame.currentPlayer.getMoney() < amount * b.getType().getPrice()) {
            return new Result(false , RED + "Not enough money!" + RESET);
        }
        if (inventory.Items.containsKey(b)) {
            Integer finalAmount1 = amount;
            inventory.Items.compute(b , (k, v) -> v + finalAmount1);
        }
        else {
            if (currentGame.currentPlayer.getBackPack().getType().getRemindCapacity() == 0) {
                return new Result(false , RED + "Not enough capacity in your backpack!" + RESET);
            }
            inventory.Items.put(b, amount);
        }

        currentGame.currentPlayer.increaseMoney( - amount * b.getType().getPrice());
        return new Result(true , BLUE + "You purchased "+name+ " successfully!");

    }


    public Result purchaseFromMarnieRanch(String name , Integer amount) {

        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;

        if (currentGame.currentDate.getHour() < MarketType.MarnieRanch.getStartHour() || currentGame.currentDate.getHour() > MarketType.MarnieRanch.getEndHour()) {
            return new Result(false , RED + "Sorry. Store is close at this time" + RESET);
        }

        if (name.equals("Hay")) {
            if (amount == null) {
                amount = 1;
            }
            if (amount > MarketItemType.Hay.getOtherShopsLimit()) {
                return new Result(false , "The purchase limit for this product has been reached");
            }
            if (MarketItemType.Hay.getPrice(0) * amount > currentGame.currentPlayer.getMoney()){
                return new Result(false , "not enough money to purchase this product");
            }
            MarketItem Hay = new MarketItem(MarketItemType.Hay);
            if (inventory.Items.containsKey(Hay)) {
                Integer finalAmount = amount;
                inventory.Items.compute(Hay , (key, value) -> value + finalAmount);
            }
            else {
                if (currentGame.currentPlayer.getBackPack().getType().getRemindCapacity() == 0) {
                    return new Result(false , RED + "Not enough capacity in your backpack!" + RESET);
                }
                inventory.Items.put(Hay, amount);
            }
            currentGame.currentPlayer.increaseMoney(- amount * MarketItemType.Hay.getPrice(0));
            MarketItemType.Hay.increaseOtherShopsLimit(-amount);
            return new Result(false , "you bought this product successfully");
        }
        if (name.equals("Milk Pail")) {
            if (amount == null) {
                amount = 1;
            }
            if (amount > new MilkPail().getRemindInShop(null)) {
                return new Result(false , "The purchase limit for this product has been reached");
            }
            if (MilkPail.coinNeeded * amount > currentGame.currentPlayer.getMoney()){
                return new Result(false , "not enough money to purchase this product");
            }
            if (amount > currentGame.currentPlayer.getBackPack().getType().getRemindCapacity()) {
                return new Result(false , "Not enough capacity in your backpack");
            }
            for (Map.Entry <Items , Integer> entry : inventory.Items.entrySet()) {
                if (entry.getKey() instanceof MilkPail) {
                    return new Result(false , "you already have Milk Pail in your inventory");
                }
            }
            MilkPail milkPail = new MilkPail();
            inventory.Items.put(milkPail , amount);
            MilkPail.increaseRemindInShop(- amount);
            currentGame.currentPlayer.increaseMoney( - amount * MilkPail.coinNeeded);
            return new Result(false , "you bought this product successfully");
        }

        if (name.equals("Shears")) {
            if (amount == null) {
                amount = 1;
            }
            if (amount > new Shear().getRemindInShop(null)) {
                return new Result(false , "The purchase limit for this product has been reached");
            }
            if (Shear.coinNeeded * amount > currentGame.currentPlayer.getMoney()){
                return new Result(false , "not enough money to purchase this product");
            }
            if (amount > currentGame.currentPlayer.getBackPack().getType().getCapacity()) {
                return new Result(false , "Not enough capacity in your backpack");
            }
            for (Map.Entry <Items , Integer> entry : inventory.Items.entrySet()) {
                if (entry.getKey() instanceof Shear) {
                    return new Result(false , "you already have Milk Pail in your inventory");
                }
            }
            Shear shear = new Shear();
            inventory.Items.put(shear , amount);
            Shear.increaseRemindInShop(- amount);
            currentGame.currentPlayer.increaseMoney(- amount * Shear.coinNeeded);
            return new Result(false , "you bought this product successfully");
        }

        return new Result(false , "No such product found");
    }

    public Result purchaseFromStardrop(String name , Integer amount) {
        if (currentGame.currentDate.getHour() < MarketType.StardropSaloon.getStartHour() || currentGame.currentDate.getHour() > MarketType.StardropSaloon.getEndHour()) {
            return new Result(false , RED + "Sorry. Store is close at this time" + RESET);
        }
        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;
        MarketItemType Product = null;
        for (MarketItemType type : MarketItemType.values()) {
            if (type.getMarketTypes().contains(MarketType.StardropSaloon)) {
                if (type.getName().equals(name)) {
                    Product = type;
                }
            }
        }
        if (Product == null) {
            return new Result(false , "No such product found");
        }
        if (amount == null) {
            amount = 1;
        }
        if (amount > Product.getOtherShopsLimit()) {
            return new Result(false , "The purchase limit for this product is reached");
        }
        if (currentGame.currentPlayer.getMoney() < amount * Product.getPrice(0)) {
            return new Result(false , "Not enough money to purchase this product");
        }
        if (currentGame.currentPlayer.getBackPack().getType().getRemindCapacity() == 0) {
            return new Result(false , "Not enough capacity in your backpack");
        }
        for (Map.Entry <Items , Integer> entry : inventory.Items.entrySet()) {
            if (entry.getKey() instanceof MarketItem) {
                if (((MarketItem) entry.getKey()).getType() .equals(Product) ) {
                    entry.setValue(entry.getValue() + amount);
                    Product.increaseOtherShopsLimit(-amount);
                    currentGame.currentPlayer.increaseMoney( - amount * Product.getPrice(0));
                    return new Result(false , "You bought this product successfully");
                }
            }
        }
        MarketItem newItem = new MarketItem(Product);
        inventory.Items.put(newItem , amount);
        Product.increaseOtherShopsLimit(-amount);
        currentGame.currentPlayer.increaseMoney( - amount * Product.getPrice(0));

        return new Result(false , "You bought this product successfully");
    }

    public Result purchaseFromCarpenter(String name , Integer amount) {
        if (currentGame.currentDate.getHour() < MarketType.CarpenterShop.getStartHour() || currentGame.currentDate.getHour() > MarketType.CarpenterShop.getEndHour()) {
            return new Result(false , RED + "Sorry. Store is close at this time");
        }

        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;

        if (amount == null) {
            amount = 1;
        }
        MarketItemType Product = null;

        for (MarketItemType type : MarketItemType.values()) {
            if (type.getName().equals(name) && type.getMarketTypes().contains(MarketType.CarpenterShop)) {
                    Product = type;
            }
        }

        if (Product == null) {
            return new Result(false , "No such product found");
        }
        if (currentGame.currentPlayer.getMoney() < amount * Product.getPrice(0)) {
            return new Result(false , "Not enough money to purchase this product");
        }
        if (Product.getOtherShopsLimit() < amount) {
            return new Result(false , "The purchase limit for this product is reached");
        }

        if (Product.getName().equals("Stone") ) {
            BasicRock rock = new BasicRock();
            if (inventory.Items.containsKey(rock)) {
                Integer finalAmount = amount;
                inventory.Items.compute(rock , (k, v) -> v + finalAmount);
            }
            else {
                if (currentGame.currentPlayer.getBackPack().getType().getRemindCapacity() == 0) {
                    return new Result(false , RED+"Not enough capacity in your backpack"+RESET);
                }
                inventory.Items.put(rock , amount);
            }
            currentGame.currentPlayer.increaseMoney(- amount * Product.getPrice(0));
            return new Result(false , "You bought this product successfully");
        }

        if (Product.getName().equals("Wood") ) {
            Wood wood = new Wood();
            if (inventory.Items.containsKey(wood)) {
                Integer finalAmount = amount;
                inventory.Items.compute(wood , (k, v) -> v + finalAmount);
            }
            else {
                if (currentGame.currentPlayer.getBackPack().getType().getRemindCapacity() == 0) {
                    return new Result(false , RED+"Not enough capacity in your backpack"+RESET);
                }
                inventory.Items.put(wood , amount);
            }
            currentGame.currentPlayer.increaseMoney(- amount * Product.getPrice(0));
            return new Result(false , "You bought this product successfully");
        }
        return null;
    }

    private Result buySeedsOrMarketItem(MarketItemType Product , ForagingSeedsType foragingSeed , Integer amount , MarketType marketType) {
        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;

        int id=0;
        if (marketType.equals(MarketType.PierreGeneralStore)) {
            id = 1;
        }

        if (Product == null && foragingSeed == null) {
            return new Result(false , "No such product found");
        }

        if (currentGame.currentPlayer.getBackPack().getType().getRemindCapacity() == 0) {
            return new Result(false , "Not enough capacity in your backpack");
        }

        if (Product != null) {
            if (currentGame.currentPlayer.getMoney() < amount * Product.getPrice(id)) {
                return new Result(false , "Not enough money to purchase this product");
            }

            if (id == 1 && Product.getPierreShopsLimit() < amount) {
                return new Result(false , "The purchase limit for this product is reached");
            }
            if (Product.getOtherShopsLimit() < amount && id!=1) {
                return new Result(false , "The purchase limit for this product is reached");
            }

            if (id == 1) {
                Product.increaseOtherShopsLimit(-amount);
            }
            else {
                Product.increaseOtherShopsLimit(-amount);
            }
            currentGame.currentPlayer.increaseMoney(- amount * Product.getPrice(id));
            MarketItem marketItem = new MarketItem(Product);

            if (inventory.Items.containsKey(marketItem)) {
                inventory.Items.compute(marketItem , (k,v) -> v+amount);
            }
            else {
                inventory.Items.put(marketItem , amount);
            }
            return new Result(true , "You bought this product successfully");
        }

        else {
            if (foragingSeed.JojaMartLimit < amount && id!=1) {
                return new Result(false , "The purchase limit for this product is reached");
            }
            if (foragingSeed.PierrGeneralLimit < amount && id==1) {
                return new Result(false , "The purchase limit for this product is reached");
            }

            if (id == 1) {
                foragingSeed.PierrGeneralLimit -= amount;
                currentGame.currentPlayer.increaseMoney(-  amount * foragingSeed.getPrice(MarketType.PierreGeneralStore));
            }
            else {
                foragingSeed.JojaMartLimit -= amount;
                currentGame.currentPlayer.increaseMoney(- amount * foragingSeed.getPrice(JojaMart));
            }
            ForagingSeeds foragingSeeds = new ForagingSeeds(foragingSeed) ;
            if (inventory.Items.containsKey(foragingSeeds)) {
                inventory.Items.compute(foragingSeeds , (k,v) -> v + amount );
            }
            else {
            inventory.Items.put(foragingSeeds, amount);
            }
            return new Result(false , "You bought this product successfully");
        }
    }

    public Result purchaseFromJojaMart(String name , Integer amount) {
        if (currentGame.currentDate.getHour() < JojaMart.getStartHour() || currentGame.currentDate.getHour() > JojaMart.getEndHour()) {
            return new Result(false , RED + "Sorry. Store is close at this time");
        }
        if (! checkSeason(name) ) {
            return new Result(false , "This product is not for this Season");
        }

        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;
        if (amount == null) {
            amount = 1;
        }
        MarketItemType Product = null;
        ForagingSeedsType foragingSeed = null;

        for (MarketItemType type : MarketItemType.values()) {
            if (type.getName().equals(name) && type.getMarketTypes().contains(JojaMart)) {
                Product = type;
            }
        }
        for (ForagingSeedsType foragingSeeds : ForagingSeedsType.values()) {
            if (foragingSeeds.getMarketTypes().contains(JojaMart) && foragingSeeds.getDisplayName().equals(name)) {
                foragingSeed = foragingSeeds;
            }
        }

        return buySeedsOrMarketItem(Product , foragingSeed , amount , JojaMart);

    }

    private boolean isExistInArray(String [] array , String item) {
        for (String string : array) {
            if (string.equals(item)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkSeason(String name) {
        String [] Spring={"Parsnip Seeds" , "Bean Starter" , "Cauliflower Seeds" , "Potato Seeds" , "Strawberry Seeds" ,
                "Tulip Bulb" , "Kale Seeds" , "Coffee Beans" , "Carrot Seeds" , "Rhubarb Seeds" , "Jazz Seeds"};
        String [] Summer={"Tomato Seeds" , "Pepper Seeds" , "Wheat Seeds" , "Summer Squash Seeds" , "Radish Seeds",
                "Melon Seeds" , "Hops Starter" , "Poppy Seeds" , "Spangle Seeds" , "Starfruit Seeds" , "Coffee Beans" , "Sunflower Seeds"};
        String [] Full={"Corn Seeds" , "Eggplant Seeds" , "Pumpkin Seeds" , "Broccoli Seeds" , "Amaranth Seeds" ,
                "Grape Starter" , "Beet Seeds" , "Yam Seeds" , "Bok Choy Seeds" , "Cranberry Seeds" , "Sunflower Seeds" , "Fairy Seeds" , "Rare Seed" , "Wheat Seeds"};
        String [] Winter={"Powdermelon Seeds"};

        String [] AllSeason={"Joja Cola" , "Ancient Seed" , "Grass Starter" , "Sugar" , "Wheat Flour" , "Rice"};

        if (isExistInArray(AllSeason , name)) {
            return true;
        }

        if (currentGame.currentDate.getSeason().equals(Season.Spring) && ! isExistInArray(Spring , name)) {
            return false;
        }
        if (currentGame.currentDate.getSeason().equals(Season.Summer) && ! isExistInArray(Summer, name)) {
            return false;
        }
        if (currentGame.currentDate.getSeason().equals(Season.Fall) && ! isExistInArray(Full , name)) {
            return false;
        }
        if (currentGame.currentDate.getSeason().equals(Season.Winter) && ! isExistInArray(Winter , name)) {
            return false;
        }
        return true;
    }

    private Result buyBackpack(BackPackType backPackType , Integer amount) {
        if (backPackType.getRemindInShop() < amount) {
            return new Result(false , "The purchase limit for this product is reached");
        }

        if (currentGame.currentPlayer.getMoney() < amount * backPackType.getPrice()) {
            return new Result(false , "Not enough money to purchase this product");
        }

        currentGame.currentPlayer.getBackPack().setType(backPackType);
        currentGame.currentPlayer.increaseMoney( -amount * backPackType.getPrice());
        backPackType.increaseRemindInShop(-amount);
        return new Result(false , "you bought" + backPackType.getName() + " successfully");
    }


    private Result buyTreeSource(TreesSourceType treesSourceType , Integer amount) {
        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;
        if (currentGame.currentPlayer.getMoney() < amount * treesSourceType.getPrice()) {
            return new Result(false , "Not enough money to purchase this product");
        }
        if (amount > currentGame.currentPlayer.getBackPack().getType().getRemindCapacity()) {
            return new Result(false , "Not enough capacity in your backpack");
        }
        for (Map.Entry <Items , Integer> entry : inventory.Items.entrySet()) {
            if (entry.getKey() instanceof TreeSource) {
                if (((TreeSource) entry.getKey()).getType() .equals(treesSourceType)) {
                    entry.setValue(entry.getValue() + amount);
                    currentGame.currentPlayer.increaseMoney(- amount * treesSourceType.getPrice());
                    return new Result(true , "You bought " + treesSourceType.name() +" successfully");
                }
            }
        }

        TreeSource treeSource = new TreeSource(treesSourceType);
        inventory.Items.put(treeSource, amount);
        currentGame.currentPlayer.increaseMoney(-amount * treesSourceType.getPrice());
        return new Result(true , "You bought " + treesSourceType.name() +" successfully");
    }


    public Result purchaseFromPierre(String name , Integer amount) {
        if (currentGame.currentDate.getHour() < MarketType.PierreGeneralStore.getStartHour() || currentGame.currentDate.getHour() > MarketType.PierreGeneralStore.getEndHour()) {
            return new Result(false , RED + "Sorry. Store is close at this time"+RESET);
        }

        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;
        if (amount == null) {
            amount = 1;
        }
        MarketItemType Product = null;
        ForagingSeedsType foragingSeed = null;
        TreesSourceType treesSource = null;
        BackPackType backPack = null;

        for (MarketItemType type : MarketItemType.values()) {
            if (type.getName().equals(name) && type.getMarketTypes().contains(MarketType.PierreGeneralStore)) {
                Product = type;
            }
        }
        for (ForagingSeedsType foragingSeeds : ForagingSeedsType.values()) {
            if (foragingSeeds.getMarketTypes().contains(MarketType.PierreGeneralStore) && foragingSeeds.getDisplayName().equals(name)) {
                foragingSeed = foragingSeeds;
            }
        }
        for (TreesSourceType type : TreesSourceType.values()) {
            if (type.getDisplayName().equals(name) && type.getPrice() !=0 ) {
                treesSource = type;
            }
        }
        for (BackPackType backPackType : BackPackType.values()) {
            if (backPackType.getName().equals(name) ) {
                backPack = backPackType;
            }
        }


        if (backPack != null) {
            return buyBackpack(backPack , amount);
        }
        if (treesSource != null) {
            return buyTreeSource(treesSource , amount);
        }
        return buySeedsOrMarketItem(Product , foragingSeed , amount , MarketType.PierreGeneralStore);

    }

    private Result buyFishingPole(FishingPoleType poleType , Integer amount) {
        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;
        if (amount > poleType.shopLimit) {
            return new Result(false ,"The purchase limit for this product is reached");
        }
        if (currentGame.currentPlayer.getMoney() < amount * poleType.getPrice()) {
            return new Result(false , "Not enough money to purchase this product");
        }
        if (currentGame.currentPlayer.getLevelFishing() < poleType.getLevel()) {
            return new Result(false ,"for buy this fishing pole you should be at least in level " + poleType.getLevel());
        }

        if (amount > currentGame.currentPlayer.getBackPack().getType().getRemindCapacity()) {
            return new Result(false , "Not enough capacity in your backpack");
        }

        for (Map.Entry <Items , Integer> entry : inventory.Items.entrySet()) {
            if (entry.getKey() instanceof FishingPole) {
                inventory.Items.remove(entry.getKey());
                break;
            }
        }

        if (amount > currentGame.currentPlayer.getBackPack().getType().getRemindCapacity()) {
            return new Result(false , "Not enough capacity in your backpack");
        }

        FishingPole fishingPole = new FishingPole();
        fishingPole.type = poleType;
        inventory.Items.put(fishingPole, amount);
        poleType.incrementShopLimit(-amount);
        currentGame.currentPlayer.increaseMoney(- amount * poleType.getPrice());

        return new Result(true , "You bought " + poleType.name() +" successfully");

    }

    public Result purchaseFromFishShop(String name , Integer amount) {
          if (currentGame.currentDate.getHour() < MarketType.FishShop.getStartHour() || currentGame.currentDate.getHour() > MarketType.FishShop.getEndHour()) {
              return new Result(false , RED + "Sorry. Store is close at this time"+RESET);
          }

          Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;
          if (amount == null) {
              amount = 1;
          }

          FishingPoleType fishingPoleType = null;
          MarketItemType Product = null;

          for (FishingPoleType type : FishingPoleType.values()) {
              if (type.getName().equals(name) ) {
                  fishingPoleType = type;
                  break;
              }
          }
          for (MarketItemType type : MarketItemType.values()) {
              if (type.getName().equals(name) && type.getMarketTypes().contains(MarketType.FishShop)) {
                  Product = type;
                  break;
              }
          }

          if (Product == null && fishingPoleType == null) {
              return new Result(false , "No such product found");
          }

          if (fishingPoleType != null) {
              return buyFishingPole(fishingPoleType , amount);
          }

          if (Product.getOtherShopsLimit() < amount) {
              return new Result(false , "The purchase limit for this product is reached");
          }
          if (currentGame.currentPlayer.getMoney() < amount * Product.getPrice(0)) {
              return new Result(false , "Not enough money to purchase this product");
          }
          if (amount > currentGame.currentPlayer.getBackPack().getType().getRemindCapacity()) {
              return new Result(false , "Not enough capacity in your backpack");
          }

          for (Map.Entry <Items , Integer> entry : inventory.Items.entrySet()) {
              if (entry.getKey() instanceof MarketItem) {
                  if (((MarketItem) entry.getKey()).getType() .equals(Product)) {
                      entry.setValue(entry.getValue() + amount);
                      Product.increaseOtherShopsLimit(-amount);
                      currentGame.currentPlayer.increaseMoney(-amount * Product.getPrice(0));
                      return new Result(true , "You bought " + Product.getName() +" successfully");
                  }
              }
          }

          MarketItem marketItem = new MarketItem(Product);
          inventory.Items.put(marketItem, amount);
        currentGame.currentPlayer.increaseMoney(-amount * Product.getPrice(0));
          Product.increaseOtherShopsLimit(-amount);
          return new Result(true , "You bought " + Product.getName() +" successfully");

    }

    public Result upgradeTool (String name) {
        if (currentGame.currentDate.getHour() < MarketType.FishShop.getStartHour() || currentGame.currentDate.getHour() > MarketType.FishShop.getEndHour()) {
            return new Result(false , RED + "Sorry. Store is close at this time"+RESET);
        }
        MarketType marketType=null;//MarketType.isInMarket(currentGame.currentPlayer.getPositionX() , currentGame.currentPlayer.getPositionY());
        if (marketType!=MarketType.Blacksmith) {
            return new Result(false , "you are not in BlackSmith Market. please go there");
        }
        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;

        if ( name.equals("Axe") ) {
            for (Map.Entry<Items, Integer> entry : inventory.Items.entrySet()) {
                if (entry.getKey() instanceof Axe) {
                    AxeType axeType = AxeType.getNextType(((Axe) entry.getKey()).getType());
                    if (axeType == null) {
                        return new Result(false , name + "is at top level");
                    }
                    else if (AxeType.checkIngredient(axeType)) {
                        ((Axe) entry.getKey()).setType(axeType);
                        currentGame.currentPlayer.increaseMoney( - axeType.getPrice());
                        return new Result(true , name + "updated successfully");
                    }
                    else {
                        return new Result(false , "Not enough ingredient or money");
                    }
                }
            }
        }

        if ( name.equals("Hoe") ) {
            for (Map.Entry<Items, Integer> entry : inventory.Items.entrySet()) {
                if (entry.getKey() instanceof Hoe) {
                    HoeType hoeType= HoeType.getNextType(((Hoe) entry.getKey()).getType());
                    if (hoeType == null) {
                        return new Result(false , name + "is at top level");
                    }
                    else if (HoeType.checkIngredient(hoeType)) {
                        ((Hoe) entry.getKey()).setType(hoeType);
                        currentGame.currentPlayer.increaseMoney( - hoeType.getPrice());
                        return new Result(true , name + "updated successfully");
                    }
                    else {
                        return new Result(false , "Not enough ingredient or money");
                    }
                }
            }
        }

        if ( name.equals("PickAxe") ) {
            for (Map.Entry<Items, Integer> entry : inventory.Items.entrySet()) {
                if (entry.getKey() instanceof PickAxe) {
                    PickAxeType pickAxeType=PickAxeType.getPickAxeType(((PickAxe) entry.getKey()).getType());
                    if (pickAxeType == null) {
                        return new Result(false , name + "is at top level");
                    }
                    else if (PickAxeType.checkIngredient(pickAxeType)) {
                        ((PickAxe) entry.getKey()).setType(pickAxeType);
                        currentGame.currentPlayer.increaseMoney( - pickAxeType.getPrice());
                        return new Result(true , name + "updated successfully");
                    }
                    else {
                        return new Result(false , "Not enough ingredient or money");
                    }
                }
            }
        }

        if ( name.equals("WateringCan") ) {
            for (Map.Entry<Items, Integer> entry : inventory.Items.entrySet()) {
                if (entry.getKey() instanceof WateringCan) {
                    WateringCanType wateringCanType=WateringCanType.getWateringCanType(((WateringCan) entry.getKey()).getType());
                    if (wateringCanType == null) {
                        return new Result(false , name + "is at top level");
                    }
                    else if (WateringCanType.checkIngredient(wateringCanType)) {
                        ((WateringCan) entry.getKey()).setType(wateringCanType);
                        currentGame.currentPlayer.increaseMoney( - wateringCanType.getPrice());
                        return new Result(true , name + "updated successfully");
                    }
                    else {
                        return new Result(false , "Not enough ingredient or money");
                    }
                }
            }
        }

        if ( name.equals("TrashCan") ) {
            for (Map.Entry<Items, Integer> entry : inventory.Items.entrySet()) {
                if (entry.getKey() instanceof TrashCan) {
                    TrashCanType trashCanType = TrashCanType.nextTrashCanType(((TrashCan) entry.getKey()).type);
                    if (trashCanType == null) {
                        return new Result(false , name + "is at top level");
                    }
                    else if (TrashCanType.checkIngredient(trashCanType)) {
                        ((TrashCan) entry.getKey()).setType(trashCanType);
                        currentGame.currentPlayer.increaseMoney( - trashCanType.getPrice());
                        return new Result(true , name + " updated successfully");
                    }
                    else {
                        return new Result(false , "Not enough ingredient or money");
                    }
                }
            }
        }

        return new Result(false , name + " not found");
    }


    public Result purchase(String name , Integer amount) {
        MarketType marketType = findEnteredShopType();

        if (marketType == null) {
            return new Result(false , "you are not in any shop");
        }
        //System.out.println(marketType.getName());
        switch (marketType) {
            case MarnieRanch -> {
                return purchaseFromMarnieRanch(name, amount);
            }
            case StardropSaloon -> { return purchaseFromStardrop(name, amount);}
            case CarpenterShop -> { return purchaseFromCarpenter(name, amount);}
            case JojaMart -> {return purchaseFromJojaMart(name, amount);}
            case PierreGeneralStore -> { return purchaseFromPierre(name, amount);}
            case FishShop ->{return purchaseFromFishShop(name, amount);}
            case Blacksmith -> {return purchaseFromBlackSmith(name, amount);}
            default -> {return new Result(false , name + " not found"); }
        }
    }

    public Result goToGameMenu() {
        // currentMenu = Menu.GameMenu;
        return new Result(true , BLUE+"Back to game menu" + RESET);
    }
}
