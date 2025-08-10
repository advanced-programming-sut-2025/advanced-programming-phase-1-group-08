package com.Graphic.model;

import com.Graphic.Controller.MainGame.InputGameController;
import com.Graphic.Controller.MainGame.Marketing;
import com.Graphic.Main;
import com.Graphic.model.Enum.ItemType.CraftType;
import com.Graphic.model.HelpersClass.Result;
import com.Graphic.model.HelpersClass.TextureManager;
import com.Graphic.model.OtherItem.ArtisanProduct;
import com.Graphic.model.ToolsPackage.CraftingItem;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.Graphic.View.GameMenus.GameMenu.gameMenu;
import static com.Graphic.model.App.currentGame;
//import static com.Graphic.model.App.skin;

public class ArtisanMenuUI extends Group {
    private Table table;
    private Table rightTable;
    private Table lowerRightTable;
    private Texture bgTexture;
    private Image bgImage;
    private ArrayList<Texture> Crafting = new ArrayList<>();
    private ArrayList<Items> inventoryItem = new ArrayList<>();
    private HashMap<Texture , CraftType> mapTexToCraft = new HashMap<>();
    private CraftingItem craftingItem;
    private TextField amount;
    private TextButton submit;
    private HashMap<Items , Integer> ingrediant = new HashMap<>();
    private ArtisanProduct goingToProduce;
    private TextButton create;

    public ArtisanMenuUI(Skin skin, Texture bgTexture , int number ) {

        if (number == 1) {
            for (CraftType craftType : CraftType.values()) {
                Texture texture = TextureManager.get(craftType.getIcon());
                Crafting.add(texture);
                mapTexToCraft.put(texture, craftType);
            }
        }
        if (number == 2) {
            for (Map.Entry<Items, Integer> items : Main.getClient(null).getPlayer().getBackPack().inventory.Items.entrySet()) {
                inventoryItem.add(items.getKey());
            }
        }
        this.bgTexture = bgTexture;
        this.bgImage = new Image(new TextureRegionDrawable(new TextureRegion(bgTexture)));
        bgImage.setSize(bgImage.getWidth() * 4, bgImage.getHeight() * 4);
        this.addActor(bgImage); // پس‌زمینه

        // ایجاد جدول
        table = new Table();
        table.defaults().size(17 * 4,17 * 4).pad(2); // فاصله از گوشه‌ها
        rightTable = new Table();
        rightTable.defaults().pad(20);
        lowerRightTable = new Table();

        TextButton back = new TextButton("",skin);
        Label label = new Label("Back", skin);
        back.clearChildren();
        back.add(label).center();


        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                ArtisanMenuUI.this.remove();
            }
        });


        // ایجاد خانه‌ها
        int index = 0;

        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 6; col++) {
                try {
                    TextButton Button = new TextButton("", skin);
                    if (number == 1) {
                        Button.add(new Image(Crafting.get(index))).size(17 * 4, 17 * 4);
                    }
                    if (number == 2) {
                        try {
                            Button.add(new Image(TextureManager.get(inventoryItem.get(index).getInventoryIconPath()))).size(17 * 4, 17 * 4);
                        }
                        catch (NullPointerException e) {

                        }
                    }

                    table.add(Button).size(17 * 4, 17 * 4);
                    if (number == 1) {
                        final int l = index;
                        Button.addListener(new ChangeListener() {
                            @Override
                            public void changed(ChangeEvent changeEvent, Actor actor) {
                                InputGameController.getInstance().checkPlaceItem(new CraftingItem(mapTexToCraft.get(Crafting.get(l))));
                                ArtisanMenuUI.this.remove();
                            }
                        });
                        index++;
                    }
                    if (number == 2) {
                        final int l = index;
                        Button.addListener(new ChangeListener() {
                            @Override
                            public void changed(ChangeEvent changeEvent, Actor actor) {
                                try {
                                    amount.remove();
                                    submit.remove();
                                    create.remove();
                                }
                                catch (NullPointerException e) {}
                                rightTable.clear();
                                amount = new TextField("", skin);
                                amount.setMessageText("How many "+inventoryItem.get(l).getName()+" do you use?");
                                rightTable.add(amount).size(150,150).row();

                                create = new TextButton("", skin);
                                Label createLabel = new Label("Create", skin);
                                create.clearChildren();
                                create.add(createLabel).center();
                                rightTable.add(create).size(150,150).row();
                                create();

                                submit = new TextButton("", skin);
                                Label sub = new Label("Submit" , skin);
                                submit.clearChildren();
                                submit.add(sub).center();
                                lowerRightTable.clear();
                                lowerRightTable.add(submit).size(410,185).row();

                                submit.addListener(new ChangeListener() {

                                    @Override
                                    public void changed(ChangeEvent changeEvent, Actor actor) {
                                        try {
                                            int numberOfItems = Integer.parseInt(amount.getText());
                                            if (ingrediant.containsKey(inventoryItem.get(l))) {
                                                ingrediant.compute(inventoryItem.get(l) , (k,v) -> v+numberOfItems);
                                            }
                                            else {
                                                ingrediant.put(inventoryItem.get(l), numberOfItems);
                                                System.out.println("dcsdldlj");
                                            }
                                            amount.remove();
                                            submit.remove();
                                        }
                                        catch (Exception e) {
                                            amount.remove();
                                            submit.remove();
                                        }
                                    }
                                });

                            }
                        });
                        index ++;
                    }
                }
                catch (IndexOutOfBoundsException e) {

                }
            }
            table.row();
        }

        table.setPosition(30 * 4 + 3 * 64 + 20,35 * 4 + 4 * 64 + 36);
        rightTable.setPosition(30 * 4 + 3 * 64 + 550 , 35 * 4 + 4 * 64 - 10);
        lowerRightTable.setPosition(30 * 4 + 3 * 64 + 615 , 160);
        rightTable.setSize(150,150);

        back.setPosition(250,90);
        back.setSize(200,50);

        this.addActor(back);
        this.addActor(table);
        this.addActor(rightTable);
        this.addActor(lowerRightTable);
        //table.setPosition(31 * 4 , 35 * 4);

        this.setSize(bgImage.getWidth(), bgImage.getHeight());
    }

    public void setGoingToProduce(ArtisanProduct artisanProduct) {
        this.goingToProduce = artisanProduct;
    }

    public void setCrafting(CraftingItem craftingItem) {
        this.craftingItem = craftingItem;
    }

    public void create() {
        try {
            create.addListener(new ChangeListener() {

                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    Result result = goingToProduce.getType().checkIngredient(ingrediant);
                    if (result.IsSuccess()) {
                        goingToProduce.getType().creatArtesian(null, craftingItem);
                        ArtisanMenuUI.this.remove();
                        //craftingItem.createAnotherShapeRender();
                    }

                    Dialog dialog = Marketing.getInstance().createDialogError();
                    Label success = new Label(result.toString() , Main.getSkin());
                    Marketing.getInstance().addDialogToTable(dialog,success,gameMenu);
                }
            });
        }
        catch (Exception e) {

        }
    }
}
