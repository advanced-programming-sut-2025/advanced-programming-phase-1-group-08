package com.Graphic.View.GameMenus;

import com.Graphic.Controller.MainGame.Marketing;
import com.Graphic.Main;
import com.Graphic.View.AppMenu;
import com.Graphic.model.App;
import com.Graphic.model.App.*;
import com.Graphic.model.Enum.Commands.MarketMenuCommands;
import com.Graphic.model.Enum.ItemType.MarketType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.Scanner;
import java.util.regex.Matcher;

import static com.Graphic.model.HelpersClass.Color_Eraser.RED;
import static com.Graphic.model.HelpersClass.Color_Eraser.RESET;

public class MarketMenu implements Screen , InputProcessor {

    TiledMap map;
    OrthogonalTiledMapRenderer renderer;
    private static OrthographicCamera camera;
    Marketing marketing;
    public static MarketType marketType;
    private static Stage stage;
    private static Skin skin;
    private static Window window;
    private static Image image;
    public static boolean showWindow = true;
    private static Texture coinTexture;
    private static ImageButton closeButton;
    public static boolean choosePlace = false;
    private static Vector3 vector;
    private static Sprite withMouse;

    @Override
    public boolean keyDown(int i) {
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }



    public static Skin getSkin() {
        return skin;
    }

    public static Stage getStage() {
        return stage;
    }

    public static Window getWindow() {
        if (window == null) {
            return window = new Window("Products", skin, "default");
        }
        return window;
    }

    public static Image getImage() {
            Pixmap pixmap =  new Pixmap(1, 1, Pixmap.Format.RGBA8888);
            pixmap.setColor(0, 0, 0, 0.6f);
            pixmap.fill();
            Texture texture = new Texture(pixmap);
            pixmap.dispose();
            return image = new Image(new TextureRegionDrawable(new TextureRegion(texture)));
    }

    public static void removeImage() {
        if (image != null) {
            image.remove();
        }
    }

    public static Texture getCoinTexture() {
        if (coinTexture == null) {
            coinTexture = new Texture(Gdx.files.internal("Mohamadreza/coin.png"));
        }
        return coinTexture;
    }

    public static ImageButton getCloseButton() {
        if (closeButton == null) {
            closeButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Mohamadreza/close.png")))));
        }
        closeButton.setSize(50,50);
        closeButton.getImage().setScaling(Scaling.fit);
        closeButton.getImageCell().size(50, 50);
        return closeButton;
    }

    public static Vector3 getVector() {
        if (vector == null) {
            vector = new Vector3(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY(), 0);
        }
        vector.set(Gdx.input.getX() , Gdx.graphics.getHeight() - Gdx.input.getY(), 0);
        //camera.unproject(vector);
        return vector;
    }

    public static Sprite getWithMouse() {
        if (withMouse == null) {
            withMouse = new Sprite();
        }
        return withMouse;
    }

    public static void setWithMouse(Sprite withMouse) {
        MarketMenu.withMouse = withMouse;
    }

    @Override
    public void show() {
//        App.currentGame.currentPlayer.sprite.setSize(16 , 32);
        marketing = new Marketing();
        marketType = MarketType.JojaMart;
        camera = new OrthographicCamera();
        camera.setToOrtho(false , 300 , 150);
        map = new TmxMapLoader().load("Mohamadreza/Maps/JojaMart.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1f);
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("Mohamadreza/newSkin.json"));

        Main.getBatch().begin();
        marketing.init();
        Main.getBatch().end();


    }

    public static BitmapFont getFont() {
        BitmapFont font = new BitmapFont(Gdx.files.internal("Erfan/Fonts/SmallFont.fnt"));
        return font;
    }
    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Main.getBatch().begin();
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            camera.translate(0, 200 * Gdx.graphics.getDeltaTime());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            camera.translate(0, - 200 * Gdx.graphics.getDeltaTime());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera.translate(- 200 * Gdx.graphics.getDeltaTime(), 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            camera.translate( 200 * Gdx.graphics.getDeltaTime(), 0);
        }
//        if (Gdx.input.isKeyPressed(Input.Keys.T) && !showWindow) {
//            try {
//                image.remove();
//                getWindow().clear();
//                getWindow().remove();
//                showWindow = true;
//            }
//            catch (Exception e) {
//
//            }
//        }
        marketing.move();
        marketing.showCarpenterProducts(1,false);
        marketing.moveTextureWithMouse(getWithMouse());
        camera.update();
        if (! choosePlace) {
            renderer.setView(camera);
            renderer.render();
        }
        Main.getBatch().end();

        stage.act(v);
        stage.draw();

    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
//    Marketing marketing=new Marketing();
//    Matcher matcher;
//
//    @Override
//    public void check(Scanner scanner) {
//        String input = scanner.nextLine();
//
//        if ((matcher= MarketMenuCommands.buyAnimal.getMatcher(input)) != null)
//            System.out.println(marketing.buyAnimal(matcher.group(1).trim() , matcher.group(2).trim()));
//
//        else if ((matcher=MarketMenuCommands.buildBarnOrCage.getMatcher(input)) != null) {
//            Integer x=Integer.parseInt(matcher.group(2).trim());
//            Integer y=Integer.parseInt(matcher.group(3).trim());
//            if (matcher.group(1).trim().equals("Well")) {
//                System.out.println(marketing.createWell(x , y));
//            }
//            else if (matcher.group(1).trim().equals("Shipping Bin")) {
//                System.out.println(marketing.createShippingBin(x , y));
//            }
//            else {
//                System.out.println(marketing.createBarnOrCage(x, y, matcher.group(1).trim()));
//            }
//        }
//
//        else if ((matcher=MarketMenuCommands.showAllProducts.getMatcher(input)) != null)
//            System.out.println(marketing.showAllProducts(1));
//
//        else if ((matcher=MarketMenuCommands.showAvailableProducts.getMatcher(input)) != null)
//            System.out.println(marketing.showAllProducts(2));
//
//        else if ((matcher=MarketMenuCommands.purchase.getMatcher(input)) != null) {
//            Integer amount=null;
//            if(matcher.group(2) != null) {
//                amount=Integer.parseInt(matcher.group(2).trim());
//            }
//            System.out.println(marketing.purchase(matcher.group(1).trim(), amount));
//        }
//
//        else if ((matcher = MarketMenuCommands.toolsUpgrade.getMatcher(input)) != null)
//            System.out.println(marketing.upgradeTool(matcher.group("name").trim()));
//
//        else if (input.equals("exit")) {
//            System.out.println(marketing.goToGameMenu());
//        }
//        else
//            System.out.println(RED + "Invalid Command" + RESET);
//
//
//    }
}
