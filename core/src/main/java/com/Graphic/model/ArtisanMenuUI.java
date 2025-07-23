package com.Graphic.model;

import com.Graphic.Controller.MainGame.InputGameController;
import com.Graphic.View.GameMenus.GameMenu;
import com.Graphic.model.Enum.ItemType.CraftType;
import com.Graphic.model.HelpersClass.TextureManager;
import com.Graphic.model.ToolsPackage.CraftingItem;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;
import java.util.HashMap;

import static com.Graphic.View.GameMenus.GameMenu.gameMenu;
import static com.Graphic.model.App.currentGame;

public class ArtisanMenuUI extends Group {
    private Table table;
    private Texture bgTexture;
    private Image bgImage;
    private ArrayList<Texture> Crafting = new ArrayList<>();
    private HashMap<Texture , CraftType> mapTexToCraft = new HashMap<>();
    private CraftingItem craftingItem;

    public ArtisanMenuUI(Skin skin, Texture bgTexture ) {

        for (CraftType craftType : CraftType.values()) {
            Texture texture = TextureManager.get(craftType.getIcon());
            Crafting.add(texture);
            mapTexToCraft.put(texture, craftType);
        }

        this.bgTexture = bgTexture;
        this.bgImage = new Image(new TextureRegionDrawable(new TextureRegion(bgTexture)));
        bgImage.setSize(bgImage.getWidth() * 4, bgImage.getHeight() * 4);
        this.addActor(bgImage); // پس‌زمینه

        // ایجاد جدول
        table = new Table();
        table.defaults().size(17 * 4,17 * 4).pad(2); // فاصله از گوشه‌ها

        // ایجاد خانه‌ها
        int index = 0;

        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 6; col++) {
                try {
                    TextButton craftingButton = new TextButton("", skin);
                    craftingButton.add(new Image(Crafting.get(index))).size(17 * 4,17 * 4);
                    table.add(craftingButton).size(17 * 4, 17 * 4);
                    final int l = index;
                    craftingButton.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent changeEvent, Actor actor) {
                            InputGameController.getInstance().checkPlaceItem(new CraftingItem(mapTexToCraft.get(Crafting.get(l))));
                            ArtisanMenuUI.this.remove();
                        }
                    });
                    index++;
                }
                catch (IndexOutOfBoundsException e) {

                }
            }
            table.row();
        }

        table.setPosition(30 * 4 + 3 * 64 + 20,35 * 4 + 4 * 64 + 36);

        this.addActor(table);
        //table.setPosition(31 * 4 , 35 * 4);

        this.setSize(bgImage.getWidth(), bgImage.getHeight());
    }
}
