package com.Graphic.Controller.MainGame;

import com.Graphic.Main;
import com.Graphic.View.AppMenu;
import com.Graphic.View.GameMenus.MarketMenu;
import com.Graphic.model.Animall.Animal;
import com.Graphic.model.Animall.BarnOrCage;
import com.Graphic.model.Enum.AllPlants.ForagingMineralsType;
import com.Graphic.model.Enum.AllPlants.ForagingSeedsType;
import com.Graphic.model.Enum.AllPlants.TreesSourceType;
import com.Graphic.model.Enum.Direction;
import com.Graphic.model.Enum.ItemType.*;
import com.Graphic.model.Enum.ToolsType.*;
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

    private static Marketing marketing;

    public static Marketing getInstance() {
        if (marketing == null) {
            return marketing =  new Marketing();
        }
        return marketing;
    }

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

    public Result checkBuyBarnOrCage(BarnORCageType barnORCageType) {
        if (currentGame.currentPlayer.getMoney() < barnORCageType.getPrice()) {
            return new Result(false , "2");
        }
        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;

        if (inventory.Items.containsKey(new Wood())) {
            if (inventory.Items.get(new Wood()) < barnORCageType.getWoodNeeded()) {
                return new Result(false , "6");
            }
        }
        else {
            return new Result(false , "6");
        }

        if (inventory.Items.containsKey(new BasicRock())) {
            if (inventory.Items.get(new BasicRock()) < barnORCageType.getStoneNeeded()) {
                return new Result(false , "7");
            }
        }
        else {
            return new Result(false , "7");
        }
        return new Result(true , "0");

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

    public void addDialogToTable(Dialog dialog, Label content , AppMenu menu) {
        dialog.getContentTable().add(content);
        ImageButton closeButton = createCloseButtonForDialog(dialog);
        dialog.getTitleTable().add(closeButton).padRight(5).padTop(3).right();
        dialog.show(menu.getStage());
        dialog.setPosition(400,100);
        dialog.setSize(1000,48);
    }

    public Dialog createDialogError() {
        Texture tex = TextureManager.get("Mohamadreza/Error.png");
        NinePatch patch = new NinePatch(tex,10,10,3,3);
        Drawable background = new NinePatchDrawable(patch);
        Window.WindowStyle style = new Window.WindowStyle();
        style.background = background;
        style.titleFont = getFont();
        Dialog dialog = new Dialog("Error",style);
        return dialog;
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
                    Dialog dialog=createDialogError();
                    Label content = new Label(MarketType.endLimit(checkBuy(item,marketType).massage()), new Label.LabelStyle(getFont() , Color.BLACK));
                    addDialogToTable(dialog, content , MarketMenu.getInstance());
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
                if (! checkBuyBarnOrCage(barnOrCage.getBarnORCageType()).IsSuccess()) {
                    Dialog dialog=createDialogError();
                    Label content = new Label(MarketType.endLimit(checkBuyBarnOrCage(barnOrCage.getBarnORCageType()).massage()), new Label.LabelStyle(getFont() , Color.BLACK));
                    addDialogToTable(dialog, content , MarketMenu.getInstance());
                }
                else {
                    removeImage();
                    getWindow().clear();
                    getWindow().remove();
                    Texture texture = TextureManager.get(barnOrCage.getBarnORCageType().getPath());
                    Sprite sprite = new Sprite(texture);
                    sprite.setAlpha(0.5f);
                    mapSpriteToBarnOrCage().put(sprite, barnOrCage.getBarnORCageType());
                    setWithMouse(sprite);
                    choosePlace = true;
                }
            }
        });
    }

    public void buyAnimal(TextButton button , AnimalType animalType) {
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                Table mainTable = new Table();
                Window window = new Window("information",getSkin() , "default");
                window.setSize(1000 , 700);
                window.setPosition(Gdx.graphics.getWidth()/2 - 500 , Gdx.graphics.getHeight()/2 - 350);
                getWindow().setVisible(false);

                Image image = new Image(TextureManager.get(animalType.getIcon()));

                Texture background = TextureManager.get("Mohamadreza/AnimalBackground.png");
                Drawable backgroundDrawable = new TextureRegionDrawable(new TextureRegion(background));
                Texture Name = TextureManager.get("Mohamadreza/NameBackground.png");
                Drawable NameDrawable = new TextureRegionDrawable(new TextureRegion(Name));

                TextField nameField = new TextField("" , getSkin());
                nameField.setMessageText(" Enter a name");

                Table content = new Table();
                content.setBackground(backgroundDrawable);
                content.add(image).size(200,200).row();

                TextButton Back = new TextButton("", getSkin());
                Label back = new Label("Back", getSkin());
                Back.add(back).center();

                content.add(Back).size(200 , 100);
                Back.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        window.remove();
                        getWindow().setVisible(true);
                    }
                });

                Table rightSide = new Table();


                Table field = new Table();
                field.setBackground(NameDrawable);
                field.add(nameField).size(300 , 100).row();

                TextButton Buy = new TextButton("",getSkin());
                Buy.clearChildren();
                Label label = new Label("Buy" , getSkin());
                Buy.add(label).center();

                field.add(Buy).size(300 , 100).row();


                Table lowerRight = new Table();
                lowerRight.setBackground(NameDrawable);

                Buy.addListener(new ChangeListener() {

                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        lowerRight.clear();
                        lowerRight.setBackground(NameDrawable);
                        if (! checkBuyAnimal( nameField , animalType).IsSuccess()) {
                            Dialog dialog=createDialogError();
                            Label Error = new Label(MarketType.endLimit(checkBuyAnimal(nameField , animalType).massage()) , getSkin());
                            Error.setWidth(400);
                            dialog.getContentTable().add(Error).width(400);
                            Error.setWrap(true);
                            ImageButton closeButton = createCloseButtonForDialog(dialog);
                            dialog.getTitleTable().add(closeButton).padRight(5).padTop(3).right();
                            lowerRight.add(dialog).size(450 , 200).center();
                        }
                        else {
                            window.remove();
                            getWindow().setVisible(true);
                        }
                    }
                });

                rightSide.add(field).width(500).height(350).row();
                rightSide.add(lowerRight).width(500).height(350);


                mainTable.add(content).width(500).height(700);
                mainTable.add(rightSide).width(500).height(700);

                window.add(mainTable).expand().fill();
                MarketMenu.getInstance().getStage().addActor(window);
            }
        });
    }

    private Result checkBuyAnimal(TextField field , AnimalType animalType) {
        String name = field.getText();
        for (BarnOrCage barnOrCage : currentGame.currentPlayer.BarnOrCages) {
            for (Animal animal : barnOrCage.getAnimals()) {
                if (animal.getName().trim().equals(name.trim())) {
                    return new Result(false , "9");
                }
            }
        }

        if (currentGame.currentPlayer.getMoney() < animalType.getPrice()) {
            return new Result(false , "2");
        }
        if (animalType.getRemindInShop() == 0) {
            return new Result(false , "1");
        }

        for (BarnOrCage barnOrCage : currentGame.currentPlayer.BarnOrCages) {
            if (animalType.getBarnorcages().contains(barnOrCage.getBarnORCageType())) {
                if (barnOrCage.getBarnORCageType().getInitialCapacity() > barnOrCage.animals.size()) {
                    currentGame.currentPlayer.increaseMoney( - animalType.getPrice());
                    barnOrCage.animals.add(new Animal(animalType , 0 , field.getText() ,
                        currentGame.currentDate.clone().getDate()));

                    barnOrCage.animals.getLast().setIndex(barnOrCage.animals.size() - 1);
                    barnOrCage.animals.getLast().setPositionX(barnOrCage.topLeftX + barnOrCage.getBarnORCageType().getDoorX());
                    barnOrCage.animals.getLast().setPositionY(barnOrCage.topLeftY + barnOrCage.getBarnORCageType().getDoorY());

                    System.out.println(barnOrCage.animals.getLast().getPositionX() +"<<"+barnOrCage.animals.getLast().getPositionY());
                    animalType.increaseRemindInShop(-1);

                    return new Result(true , "0");
                }
            }
        }
        return new Result(false , "10");
    }

    public void moveTextureWithMouse(Sprite sprite) {

            if (choosePlace && !mapSpriteToBarnOrCage().get(sprite).isWaiting()) {
                printMapForCreate();
                sprite.setPosition(getVector().x - sprite.getWidth() / 2, getVector().y - sprite.getHeight() / 2);
                sprite.draw(Main.getBatch());
            }
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && choosePlace && ! mapSpriteToBarnOrCage().get(sprite).isWaiting()) {
                mapSpriteToBarnOrCage().get(sprite).setWaiting(true);
            }

            if (mapSpriteToBarnOrCage().get(sprite).isWaiting()) {
                setBarnOrCageOnFarm(sprite, mapSpriteToBarnOrCage().get(sprite));
            }

    }

    private boolean isPlaced = false;
    public void setBarnOrCageOnFarm(Sprite sprite ,BarnORCageType barnORCageType) {
        int x = (int) (sprite.getX() / TEXTURE_SIZE) + 60 * currentGame.currentPlayer.topLeftX;
        int y =30 -  (int) (sprite.getY() / TEXTURE_SIZE) + 60 * currentGame.currentPlayer.topLeftY;

        if (! checkTilesForCreateBarnOrCage(x , y , barnORCageType.getWidth() , barnORCageType.getHeight()) && ! isPlaced  ) {

            Dialog dialog=createDialogError();
            Label content = new Label(MarketType.endLimit("8") , new Label.LabelStyle(getFont() , Color.BLACK));
            addDialogToTable(dialog, content , MarketMenu.getInstance());
            barnORCageType.setWaiting(false);
        }
        else {
            isPlaced = true;
            for (int i = x ; i < x + barnORCageType.getWidth() ; i++) {
                for (int j = y ; j < y + barnORCageType.getHeight() ; j++) {
                    if (!(getTileByCoordinates(i , j).getGameObject() instanceof BarnOrCage )) {
                        getTileByCoordinates(i , j).setGameObject(new BarnOrCage(barnORCageType , x , y));
                    }
                }
            }
            printMapForCreate();
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                TextButton confirm = makeConfirmButton(MarketMenu.getInstance());
                TextButton TryAgain = makeTryAgainButton(MarketMenu.getInstance());

                confirm.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        isPlaced = false;
                        payForBuilding((BarnOrCage) getTileByCoordinates(x,y).getGameObject());
                        choosePlace = false;
                        barnORCageType.setWaiting(false);
                        confirm.remove();
                        TryAgain.remove();
                        showWindow = true;
                    }
                });

                TryAgain.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        barnORCageType.setWaiting(false);
                        isPlaced = false;
                        for (int i = x ; i < x + barnORCageType.getWidth() ; i++) {
                            for (int j = y ; j < y + barnORCageType.getHeight() ; j++) {
                                getTileByCoordinates(i , j).setGameObject(new Walkable());
                            }
                        }
                        confirm.remove();
                        TryAgain.remove();
                    }
                });
            }
        }
    }

    public TextButton makeConfirmButton(AppMenu menu) {
        TextButton confirm = new TextButton("" , getSkin());
        Label con = new Label("Confirm" , getSkin());
        confirm.add(con).left().padLeft(10);
        menu.getStage().addActor(confirm);
        confirm.setPosition(1200,600);
        confirm.setSize(200 , 40);
        return confirm;
    }

    public TextButton makeTryAgainButton(AppMenu menu) {
        TextButton TryAgain = new TextButton("" , getSkin());
        Label Try = new Label("Try Again" , getSkin());
        TryAgain.add(Try).left().padLeft(10);
        menu.getStage().addActor(TryAgain);
        TryAgain.setPosition(1200,480);
        TryAgain.setSize(200 , 40);
        return TryAgain;
    }

    public void payForBuilding(BarnOrCage barnOrCage) {
        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;
        inventory.Items.put(new Wood() , inventory.Items.get(new Wood()) - barnOrCage.getBarnORCageType().getWoodNeeded());
        inventory.Items.put(new BasicRock() , inventory.Items.get(new BasicRock()) - barnOrCage.getBarnORCageType().getStoneNeeded());
        inventory.Items.entrySet().removeIf(entry -> entry.getValue() == 0);
        currentGame.currentPlayer.increaseMoney(- barnOrCage.getBarnORCageType().getPrice());
        barnOrCage.setRemindInShop(barnOrCage.getRemindInShop(null) - 1 , null);
        currentGame.currentPlayer.BarnOrCages.add(barnOrCage);
    }

    public void printMapForCreate() {
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                try {
                    Main.getBatch().draw(TextureManager.get("Places/Walkable.png"),
                        TEXTURE_SIZE  * (i),
                        TEXTURE_SIZE  * (30 -j) ,
                        TEXTURE_SIZE  , TEXTURE_SIZE );

                    Main.getBatch().draw(getTileByCoordinates(i + 60 * currentGame.currentPlayer.topLeftX , j + 60 * currentGame.currentPlayer.topLeftY)
                            .getGameObject()
                            .getSprite(TextureManager.get(getTileByCoordinates(i , j).getGameObject().getIcon())) ,
                        TEXTURE_SIZE * (i) ,
                        TEXTURE_SIZE  * (30 - j) ,
                        TEXTURE_SIZE , TEXTURE_SIZE);

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
        if (! MarketMenu.getInstance().getStage().getActors().contains(image , true)) {
            MarketMenu.getInstance().getStage().addActor(image);
        }
        if (! MarketMenu.getInstance().getStage().getActors().contains(getWindow() , true)) {
            MarketMenu.getInstance().getStage().addActor(getWindow());
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

    public void listAnimals(int id , Table table , MarketType marketType , Label label) {
        for (AnimalType f : AnimalType.values()) {
            if (id == 1 || f.getRemindInShop() > 0) {
                TextButton coinButton = new TextButton("",getSkin());
                coinButton.clearChildren();
                buyAnimal(coinButton , f);
                Table innerTable = new Table();
                innerTable.add(new Image(new Texture(Gdx.files.internal(f.getIcon())))).left().padLeft(60).size(24, 24);

                makeCoinButton(table , coinButton , innerTable , f.getType() , f.getPrice(), f.getRemindInShop());
            }
        }
    }

    public void listTreeSources(int id , Table table , MarketType marketType , Label label) {
        for (TreesSourceType f : TreesSourceType.values()) {
            if (f.getPrice() != 0 && (id == 1 || new TreeSource(f).getRemindInShop(marketType) > 0) ) {
                TextButton coinButton = new TextButton("",getSkin());
                coinButton.clearChildren();
                buy(coinButton , new TreeSource(f) , marketType);

                Table innerTable = new Table();
                innerTable.add(new Image(new Texture(Gdx.files.internal(f.getInventoryIconPath())))).left().padLeft(60).size(24, 24);

                makeCoinButton(table , coinButton , innerTable, f.getDisplayName(),
                    f.getPrice(),new TreeSource(f).getRemindInShop(marketType) );
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
            image.setSize(MarketMenu.getInstance().getStage().getWidth(), MarketMenu.getInstance().getStage().getHeight());
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

            image.setSize(MarketMenu.getInstance().getStage().getWidth(), MarketMenu.getInstance().getStage().getHeight());
            listForagingSeeds(id , table , PierreGeneralStore, label);
            listMarketItems(id,table,PierreGeneralStore,label);
            listTreeSources(id,table,PierreGeneralStore,label);

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
            image.setSize(MarketMenu.getInstance().getStage().getWidth(), MarketMenu.getInstance().getStage().getHeight());
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
            image.setSize(MarketMenu.getInstance().getStage().getWidth(), MarketMenu.getInstance().getStage().getHeight());
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
            image.setSize(MarketMenu.getInstance().getStage().getWidth(), MarketMenu.getInstance().getStage().getHeight());
            listMarketItems(id,table,CarpenterShop,label);
            listBarnOrCage(id, table,CarpenterShop,label);
            pane.setWidget(table);
            createWindow(pane , image);
        }
    }

    public void showMarnieProducts(int id , boolean Filter) {
        if ((Gdx.input.isKeyPressed(Input.Keys.L) && showWindow) || Filter) {
            showWindow = false;

            Table table = createTable(MarnieRanch);
            ScrollPane pane = new ScrollPane(table,getSkin());
            Image image = getImage();
            Label label = null;
            pane.setFadeScrollBars(false);
            image.setSize(MarketMenu.getInstance().getStage().getWidth(), MarketMenu.getInstance().getStage().getHeight());
            listAnimals(id , table,MarnieRanch,label);
            listMarketItems(id , table , MarnieRanch,label);

            TextButton milkPail = new TextButton("",getSkin());
            milkPail.clearChildren();
            buy(milkPail , new MilkPail(),MarnieRanch);
            Table innerTable = new Table();
            innerTable.add(new Image(new Texture(Gdx.files.internal(new MilkPail().getIcon())))).left().padLeft(60).size(24, 24);
            makeCoinButton(table , milkPail , innerTable,new MilkPail().getName() , new MilkPail().getMarketPrice(null) , new MilkPail().getRemindInShop(null));

            TextButton shear = new TextButton("",getSkin());
            shear.clearChildren();
            buy(shear , new Shear(),MarnieRanch);
            Table innerShear = new Table();
            innerShear.add(new Image(new Texture(Gdx.files.internal(new Shear().getIcon())))).left().padLeft(60).size(24, 24);
            makeCoinButton(table , shear , innerShear,new Shear().getName() , new Shear().getMarketPrice(null) , new Shear().getRemindInShop(null));
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

    public Result BlackSmithProducts() {
        StringBuilder result=new StringBuilder();
        result.append("Copper Ore, ").append("Price: ").append("75\n");
        result.append("Iron Ore, ").append("Price: ").append("150\n");
        result.append("Gold Ore, ").append("Price: ").append("400\n");
        result.append("Coal, ").append("Price: ").append("150\n");
        return new Result(true , result.toString());
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


    private boolean isExistInArray(String [] array , String item) {
        for (String string : array) {
            if (string.equals(item)) {
                return true;
            }
        }
        return false;
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


}
