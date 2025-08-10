package com.Graphic.Controller.MainGame;

import com.Graphic.Main;
import com.Graphic.View.AppMenu;
import com.Graphic.View.GameMenus.MarketMenu;
import com.Graphic.model.*;
import com.Graphic.model.Animall.Animal;
import com.Graphic.model.Animall.BarnOrCage;
import com.Graphic.model.ClientServer.Message;
import com.Graphic.model.ClientServer.PlayerHandler;
import com.Graphic.model.Enum.AllPlants.ForagingMineralsType;
import com.Graphic.model.Enum.AllPlants.ForagingSeedsType;
import com.Graphic.model.Enum.AllPlants.TreesSourceType;
import com.Graphic.model.Enum.Commands.CommandType;
import com.Graphic.model.Enum.Direction;
import com.Graphic.model.Enum.ItemType.*;
import com.Graphic.model.Enum.Menu;
import com.Graphic.model.Enum.ToolsType.*;
import com.Graphic.model.HelpersClass.TextureManager;
import com.Graphic.model.MapThings.GameObject;
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
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.Graphic.Controller.MainGame.GameControllerLogic.*;
import static com.Graphic.View.GameMenus.MarketMenu.*;
//import static com.Graphic.View.GameMenus.MarketMenu.marketType;
import static com.Graphic.model.App.currentGame;
import static com.Graphic.model.App.currentMenu;
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

    public void openProductsMenu(MarketType marketType) {
        switch (marketType) {
            case JojaMart ->{
                showJojaProducts(1 , false );
                break;
            }
            case PierreGeneralStore -> {
                showPierreProducts(1 , false );
                break;
            }
            case StardropSaloon -> {
                showStardropProducts(1 , false );
                break;
            }
            case FishShop -> {
                showFishProducts(1 , false );
                break;
            }
            case CarpenterShop -> {
                showCarpenterProducts(1 , false );
                break;
            }
            case MarnieRanch -> {
                showMarnieProducts(1 , false );
                break;
            }
        }
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
        User Player = Main.getClient(null).getPlayer();
//        if (item instanceof BackPack) {
//            if (Player.getBackPack().getType().equals(BackPackType.DeluxePack) ||
//                Player.getBackPack().getType().equals( ((BackPack) item).getType()) ) {
//                return new Result(false, "4");
//            }
//        }
        if (item instanceof FishingPole) {
            if ( ! ((FishingPole) item).type.checkAbility(Player.getLevelFishing())) {
                return new Result(false , "5");
            }
        }
        if (item instanceof ShippingBin) {
            try {
                if (Player.getBackPack().inventory.Items.get(new Wood()) < 150) {
                    return new Result(false, "6");
                }
            }
            catch (Exception e) {
                return new Result(false, "6");
            }
        }
        if (item.getRemindInShop(marketType) == 0) {
            return new Result(false,"1");
        }
        if (item.getMarketPrice(marketType) > Player.getMoney() ) {
            return new Result(false,"2");
        }
        if (! (item instanceof ShippingBin) /*&& ! (item instanceof BackPack)*/ ) {
            if (Player.getBackPack().getType().getRemindCapacity() == 0) {
                if (!Player.getBackPack().inventory.Items.containsKey(item)) {
                    return new Result(false, "3");
                }
            }
        }
        return new Result(true,"0");

    }

    public Result checkBuyBarnOrCage(BarnORCageType barnORCageType) {
        User Player = Main.getClient(null).getPlayer();
        if (Player.getMoney() < barnORCageType.getPrice()) {
            return new Result(false , "2");
        }
        Inventory inventory = Player.getBackPack().inventory;

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

    public void init(User player) {
        player.setDirection(Direction.Up);
        //currentGame.currentPlayer.sprite.setSize(16 , 16);
        //currentGame.currentPlayer.sprite.draw(Main.getBatch());
        closeWindow();
    }

    public void printPlayers() {
        for (int i = 0 ; i < MarketMenu.getInstance().users().size() ; i++) {
            MarketMenu.getInstance().getUserRenderers().get(i).render(MarketMenu.getInstance().users().get(i));
        }
    }

    public void move(User Player) {
        float x = Player.getPositionX();
        float y = Player.getPositionY();
        float Timer = Player.getTimer();
        HashMap<String , Object> body = new HashMap<>();

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            //InputGameController.moveAnimation();
            y = y + 50 * Gdx.graphics.getDeltaTime();
            Timer = Timer + Gdx.graphics.getDeltaTime();
            body.put("Player", Player);
            body.put("Timer", Timer);
            body.put("X", x);
            body.put("Y", y);
            body.put("Direction", Direction.Up);
            Main.getClient(null).getRequests().add(new Message(CommandType.MOVE_IN_MARKET , body));
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            y = y - 50 * Gdx.graphics.getDeltaTime();
            Timer = Timer + Gdx.graphics.getDeltaTime();
            body.put("Player", Player);
            body.put("Timer", Timer);
            body.put("X", x);
            body.put("Y", y);
            body.put("Direction", Direction.Down);
            Main.getClient(null).getRequests().add(new Message(CommandType.MOVE_IN_MARKET , body));
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            x = x - 50 * Gdx.graphics.getDeltaTime();
            Timer = Timer + Gdx.graphics.getDeltaTime();
            body.put("Player", Player);
            body.put("Timer", Timer);
            body.put("X", x);
            body.put("Y", y);
            body.put("Direction", Direction.Left);
            Main.getClient(null).getRequests().add(new Message(CommandType.MOVE_IN_MARKET , body));
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            x = x + 50 * Gdx.graphics.getDeltaTime();
            Timer = Timer + Gdx.graphics.getDeltaTime();
            body.put("Player", Player);
            body.put("Timer", Timer);
            body.put("X", x);
            body.put("Y", y);
            body.put("Direction", Direction.Right);
            Main.getClient(null).getRequests().add(new Message(CommandType.MOVE_IN_MARKET , body));
        }

        else {
            return;
        }

        //currentGame.currentPlayer.sprite.setSize(16 , 32);


//        currentGame.currentPlayer.sprite.
//            setPosition(x + currentGame.currentPlayer.getDirection().getX() * 50 * Gdx.graphics.getDeltaTime(),
//                y - currentGame.currentPlayer.getDirection().getY() * 50 * Gdx.graphics.getDeltaTime());

//        if (! checkColision()) {
//            currentGame.currentPlayer.sprite.setPosition(x , y);
//        }

        //currentGame.currentPlayer.sprite.draw(Main.getBatch());
    }




    public Message changeBackPack(Message message , Game game) {
        User Player = message.getFromBody("Player");
        BackPack backPack = message.getFromBody("Item");
        for (User user : game.getGameState().getPlayers()) {
            if (user.getUsername().trim().equals(Player.getUsername().trim()))  {
                user.getBackPack().setType(backPack.getType());
                HashMap<String ,Object> body = new HashMap<>();
                body.put("BackPack", backPack.getType());
                return new Message(CommandType.BUY_BACKPACK , body);
            }
        }
        System.out.println("bia be changeBackPack dar Marketing");
        return null;
    }

    public void buy(TextButton button , Items item , MarketType marketType) {
        User Player = Main.getClient(null).getPlayer();
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if (! checkBuy(item, marketType).IsSuccess()) {
                    Dialog dialog=createDialogError();
                    Label content = new Label(MarketType.endLimit(checkBuy(item,marketType).massage()), new Label.LabelStyle(getFont() , Color.BLACK));
                    addDialogToTable(dialog, content , MarketMenu.getInstance());
                }
//                else if (item instanceof ShippingBin) {
//                    Main.getClient(null).getPlayer().setDroppedItem(new ShippingBin());
//                    Main.getClient(null).getPlayer().setIsPlaceArtisanOrShippingBin(true);
//                    Main.getClient(null).getPlayer().setWithMouse(new Sprite(TextureManager.get("Mohamadreza/Shipping Bin.png")));
//                    Main.getClient(null).getPlayer().getWithMouse().setAlpha(0.5f);
//                    choosePlace = true;
//                    getWindow().remove();
//                    removeImage();
//                }
                else {
                    HashMap<String , Object> body = new HashMap<>();
                    body.put("Player" , Player);
                    body.put("Item" , item);
                    body.put("amount" , 1);
                    body.put("Market" , marketType);
//                    if (item instanceof BackPack) {
//                        Main.getClient(null).getRequests().add(new Message(CommandType.BUY_BACKPACK, body));
//                    }
                    //else {
                        Main.getClient(null).getRequests().add(new Message(CommandType.BUY, body));
                    //}

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
        for (BarnOrCage barnOrCage : Main.getClient(null).getPlayer().BarnOrCages) {
            for (Animal animal : barnOrCage.getAnimals()) {
                if (animal.getName().trim().equals(name.trim())) {
                    return new Result(false , "9");
                }
            }
        }

        if (Main.getClient(null).getPlayer().getMoney() < animalType.getPrice()) {
            return new Result(false , "2");
        }
        if (animalType.getRemindInShop() == 0) {
            return new Result(false , "1");
        }

        for (BarnOrCage barnOrCage : Main.getClient(null).getPlayer().BarnOrCages) {
            if (animalType.getBarnorcages().contains(barnOrCage.getBarnORCageType())) {
                if (barnOrCage.getBarnORCageType().getInitialCapacity() > barnOrCage.animals.size()) {
                    requestForBuyAnimal(animalType , field.getText() , barnOrCage);
//                    currentGame.currentPlayer.increaseMoney( - animalType.getPrice());
//                    barnOrCage.animals.add(new Animal(animalType , 0 , field.getText() ,
//                        currentGame.currentDate.clone().getDate()));
//
//                    barnOrCage.animals.getLast().setIndex(barnOrCage.animals.size() - 1);
//                    barnOrCage.animals.getLast().setPositionX(barnOrCage.topLeftX + barnOrCage.getBarnORCageType().getDoorX());
//                    barnOrCage.animals.getLast().setPositionY(barnOrCage.topLeftY + barnOrCage.getBarnORCageType().getDoorY());
//
//                    System.out.println(barnOrCage.animals.getLast().getPositionX() +"<<"+barnOrCage.animals.getLast().getPositionY());
//                    animalType.increaseRemindInShop(-1);

                    return new Result(true , "0");
                }
            }
        }
        return new Result(false , "10");
    }

    public void requestForBuyAnimal(AnimalType animalType , String name , BarnOrCage barnOrCage) {
        HashMap<String , Object> body = new HashMap<>();
        Animal animal = new Animal(animalType , 0 , name , gameMenu.gameState.currentDate.clone().getDate());
        body.put("Animal" , animal);
        body.put("Player" , Main.getClient(null).getPlayer());
        body.put("BarnOrCage" , barnOrCage);
        Main.getClient(null).getRequests().add(new Message(CommandType.BUY_ANIMAL , body));
    }


    public void receiveRequestBuyAnimal(Message message) {
        User player = message.getFromBody("Player");
        BarnOrCage barnOrCage = message.getFromBody("BarnOrCage");
        Animal animal = message.getFromBody("Animal");
        for (User user : Main.getClient(null).getLocalGameState().getPlayers()) {
            if (user.getUsername().trim().equals(player.getUsername().trim())) {
                for (BarnOrCage barnOrCage1 : user.BarnOrCages) {
                    if (barnOrCage1.equals(barnOrCage)) {
                        barnOrCage.animals.add(animal);
                        barnOrCage.animals.getLast().setIndex(barnOrCage.animals.size() - 1);
                        barnOrCage.animals.getLast().setPositionX(barnOrCage.topLeftX + barnOrCage.getBarnORCageType().getDoorX());
                        barnOrCage.animals.getLast().setPositionY(barnOrCage.topLeftY + barnOrCage.getBarnORCageType().getDoorY());
                    }
                }
            }
        }
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
        int x = (int) (sprite.getX() / TEXTURE_SIZE) + 60 * Main.getClient(null).getPlayer().topLeftX;
        int y =30 -  (int) (sprite.getY() / TEXTURE_SIZE) + 60 * Main.getClient(null).getPlayer().topLeftY;

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
                    if (!(getTileByCoordinates(i , j , Main.getClient(null).getLocalGameState()).getGameObject() instanceof BarnOrCage )) {
                        getTileByCoordinates(i , j , Main.getClient(null).getLocalGameState()).setGameObject(new BarnOrCage(barnORCageType , x , y));
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
                        choosePlace = false;
                        barnORCageType.setWaiting(false);
                        confirm.remove();
                        TryAgain.remove();
                        showWindow = true;
                        setWithMouse(null);
                        requestForCreateBarnOrCage(x , y , barnORCageType);
                    }
                });

                TryAgain.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        barnORCageType.setWaiting(false);
                        isPlaced = false;
                        for (int i = x ; i < x + barnORCageType.getWidth() ; i++) {
                            for (int j = y ; j < y + barnORCageType.getHeight() ; j++) {
                                getTileByCoordinates(i , j , Main.getClient(null).getLocalGameState()).setGameObject(new Walkable());
                            }
                        }
                        confirm.remove();
                        TryAgain.remove();
                    }
                });
            }
        }
    }

    public Message requestForCreateBarnOrCage(int x , int y , BarnORCageType barnORCageType) {
        Point p = new Point(x, y);
        HashMap<String , Object> body = new HashMap<>();
        body.put("Point" , p);
        body.put("BarnOrCageType" , barnORCageType);
        body.put("Player" , Main.getClient(null).getPlayer());
        return new Message(CommandType.BUY_BARN_CAGE, body);
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


    public void printMapForCreate() {
        GameObject gameObject = null;
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                try {
                    Main.getBatch().draw(TextureManager.get("Places/Walkable.png"),
                        TEXTURE_SIZE  * (i),
                        TEXTURE_SIZE  * (30 -j) ,
                        TEXTURE_SIZE  , TEXTURE_SIZE );

//                    Main.getBatch().draw(getTileByCoordinates(i + 60 * currentGame.currentPlayer.topLeftX ,
//                            j + 60 * currentGame.currentPlayer.topLeftY , Main.getClient(null).getLocalGameState())
//                            .getGameObject()
//                            .getSprite(TextureManager.get(getTileByCoordinates(i , j).getGameObject().getIcon())) ,
//                        TEXTURE_SIZE * (i) ,
//                        TEXTURE_SIZE  * (30 - j) ,
//                        TEXTURE_SIZE , TEXTURE_SIZE);
                    gameObject = getTileByCoordinates(i + 60 * Main.getClient(null).getPlayer().topLeftX
                        , j + 60 * Main.getClient(null).getPlayer().topLeftY , Main.getClient(null).getLocalGameState()).getGameObject();

                    Main.getBatch().draw(
                        (TextureManager.get(gameObject.getIcon())),
                        TEXTURE_SIZE * i, TEXTURE_SIZE * (90 - j), TEXTURE_SIZE* gameObject.getTextureWidth(),
                        TEXTURE_SIZE * gameObject.getTextureHeight());

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
        if (! currentMenu.getMenu().getStage().getActors().contains(image , true)) {
            currentMenu.getMenu().getStage().addActor(image);
        }
        if (! currentMenu.getMenu().getStage().getActors().contains(getWindow() , true)) {
            currentMenu.getMenu().getStage().addActor(getWindow());
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
        if ( showWindow || Filter) {
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
        if ( showWindow || Filter) {
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
                    if (id == 1 || backPack.getRemindInShop(null) > 0) {
                        TextButton coinButton = new TextButton("",getSkin());
                        coinButton.clearChildren();
                        //buy(coinButton , backPack , PierreGeneralStore);

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
        if ( showWindow || Filter) {
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
            //Shipping Bin
            TextButton coinButton = new TextButton("",getSkin());
            coinButton.clearChildren();
            buy(coinButton , new ShippingBin() , CarpenterShop);
            Table innerTable = new Table();
            innerTable.add(new Image(new Texture(Gdx.files.internal(new ShippingBin().getIcon())))).left().padLeft(60).size(24, 24);
            makeCoinButton(table,coinButton,innerTable,"Shipping bin",250,new ShippingBin().getRemindInShop(CarpenterShop));
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




    public Result BlackSmithProducts() {
        StringBuilder result=new StringBuilder();
        result.append("Copper Ore, ").append("Price: ").append("75\n");
        result.append("Iron Ore, ").append("Price: ").append("150\n");
        result.append("Gold Ore, ").append("Price: ").append("400\n");
        result.append("Coal, ").append("Price: ").append("150\n");
        return new Result(true , result.toString());
    }




//    public Result purchaseFromBlackSmith(String name , Integer amount) {
//        if (currentGame.currentDate.getHour() < MarketType.Blacksmith.getStartHour() || currentGame.currentDate.getHour() > MarketType.Blacksmith.getEndHour()) {
//            return new Result(false , RED + "Sorry. Store is close at this time");
//        }
//
//        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;
//        if (amount == null) {
//            amount = 1;
//        }
//        if (name.equals("Coal") ) {
//            ForagingMinerals Coal = new ForagingMinerals(ForagingMineralsType.COAL);
//            if (currentGame.currentPlayer.getMoney() < amount * 150) {
//                return new Result(false , RED + "Not enough money!" + RESET);
//            }
//            if (inventory.Items.containsKey(Coal)) {
//                Integer finalAmount = amount;
//                inventory.Items.compute(Coal , (key, value) -> value + finalAmount);
//            }
//            else {
//                if (currentGame.currentPlayer.getBackPack().getType().getRemindCapacity() == 0) {
//                    return new Result(false , RED + "Not enough remind capacity!" + RESET);
//                }
//                inventory.Items.put(Coal, amount);
//            }
//
//            currentGame.currentPlayer.increaseMoney(- amount * 150);
//            return new Result(true , BLUE + "You purchased "+name+ " successfully!");
//        }
//        BarsAndOres b = null;
//
//        for (BarsAndOreType barsAndOreType : BarsAndOreType.values()) {
//            if (barsAndOreType.name().equals(name) && barsAndOreType.getMarketType() != null) {
//                b = new BarsAndOres(barsAndOreType);
//                break;
//            }
//        }
//        if (b == null) {
//            return new Result(false , "Products Not found");
//        }
//
//        if (currentGame.currentPlayer.getMoney() < amount * b.getType().getPrice()) {
//            return new Result(false , RED + "Not enough money!" + RESET);
//        }
//        if (inventory.Items.containsKey(b)) {
//            Integer finalAmount1 = amount;
//            inventory.Items.compute(b , (k, v) -> v + finalAmount1);
//        }
//        else {
//            if (currentGame.currentPlayer.getBackPack().getType().getRemindCapacity() == 0) {
//                return new Result(false , RED + "Not enough capacity in your backpack!" + RESET);
//            }
//            inventory.Items.put(b, amount);
//        }
//
//        currentGame.currentPlayer.increaseMoney( - amount * b.getType().getPrice());
//        return new Result(true , BLUE + "You purchased "+name+ " successfully!");
//
//    }


//    public Result upgradeTool (String name) {
//        if (currentGame.currentDate.getHour() < MarketType.FishShop.getStartHour() || currentGame.currentDate.getHour() > MarketType.FishShop.getEndHour()) {
//            return new Result(false , RED + "Sorry. Store is close at this time"+RESET);
//        }
//        MarketType marketType=null;//MarketType.isInMarket(currentGame.currentPlayer.getPositionX() , currentGame.currentPlayer.getPositionY());
//        if (marketType!=MarketType.Blacksmith) {
//            return new Result(false , "you are not in BlackSmith Market. please go there");
//        }
//        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;
//
//        if ( name.equals("Axe") ) {
//            for (Map.Entry<Items, Integer> entry : inventory.Items.entrySet()) {
//                if (entry.getKey() instanceof Axe) {
//                    AxeType axeType = AxeType.getNextType(((Axe) entry.getKey()).getType());
//                    if (axeType == null) {
//                        return new Result(false , name + "is at top level");
//                    }
//                    else if (AxeType.checkIngredient(axeType)) {
//                        ((Axe) entry.getKey()).setType(axeType);
//                        currentGame.currentPlayer.increaseMoney( - axeType.getPrice());
//                        return new Result(true , name + "updated successfully");
//                    }
//                    else {
//                        return new Result(false , "Not enough ingredient or money");
//                    }
//                }
//            }
//        }
//
//        if ( name.equals("Hoe") ) {
//            for (Map.Entry<Items, Integer> entry : inventory.Items.entrySet()) {
//                if (entry.getKey() instanceof Hoe) {
//                    HoeType hoeType= HoeType.getNextType(((Hoe) entry.getKey()).getType());
//                    if (hoeType == null) {
//                        return new Result(false , name + "is at top level");
//                    }
//                    else if (HoeType.checkIngredient(hoeType)) {
//                        ((Hoe) entry.getKey()).setType(hoeType);
//                        currentGame.currentPlayer.increaseMoney( - hoeType.getPrice());
//                        return new Result(true , name + "updated successfully");
//                    }
//                    else {
//                        return new Result(false , "Not enough ingredient or money");
//                    }
//                }
//            }
//        }
//
//        if ( name.equals("PickAxe") ) {
//            for (Map.Entry<Items, Integer> entry : inventory.Items.entrySet()) {
//                if (entry.getKey() instanceof PickAxe) {
//                    PickAxeType pickAxeType=PickAxeType.getPickAxeType(((PickAxe) entry.getKey()).getType());
//                    if (pickAxeType == null) {
//                        return new Result(false , name + "is at top level");
//                    }
//                    else if (PickAxeType.checkIngredient(pickAxeType)) {
//                        ((PickAxe) entry.getKey()).setType(pickAxeType);
//                        currentGame.currentPlayer.increaseMoney( - pickAxeType.getPrice());
//                        return new Result(true , name + "updated successfully");
//                    }
//                    else {
//                        return new Result(false , "Not enough ingredient or money");
//                    }
//                }
//            }
//        }
//
//        if ( name.equals("WateringCan") ) {
//            for (Map.Entry<Items, Integer> entry : inventory.Items.entrySet()) {
//                if (entry.getKey() instanceof WateringCan) {
//                    WateringCanType wateringCanType=WateringCanType.getWateringCanType(((WateringCan) entry.getKey()).getType());
//                    if (wateringCanType == null) {
//                        return new Result(false , name + "is at top level");
//                    }
//                    else if (WateringCanType.checkIngredient(wateringCanType)) {
//                        ((WateringCan) entry.getKey()).setType(wateringCanType);
//                        currentGame.currentPlayer.increaseMoney( - wateringCanType.getPrice());
//                        return new Result(true , name + "updated successfully");
//                    }
//                    else {
//                        return new Result(false , "Not enough ingredient or money");
//                    }
//                }
//            }
//        }
//
//        if ( name.equals("TrashCan") ) {
//            for (Map.Entry<Items, Integer> entry : inventory.Items.entrySet()) {
//                if (entry.getKey() instanceof TrashCan) {
//                    TrashCanType trashCanType = TrashCanType.nextTrashCanType(((TrashCan) entry.getKey()).type);
//                    if (trashCanType == null) {
//                        return new Result(false , name + "is at top level");
//                    }
//                    else if (TrashCanType.checkIngredient(trashCanType)) {
//                        ((TrashCan) entry.getKey()).setType(trashCanType);
//                        currentGame.currentPlayer.increaseMoney( - trashCanType.getPrice());
//                        return new Result(true , name + " updated successfully");
//                    }
//                    else {
//                        return new Result(false , "Not enough ingredient or money");
//                    }
//                }
//            }
//        }
//
//        return new Result(false , name + " not found");
//    }


}
