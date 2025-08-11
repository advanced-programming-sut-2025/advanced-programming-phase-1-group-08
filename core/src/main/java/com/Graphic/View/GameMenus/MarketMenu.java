package com.Graphic.View.GameMenus;

import com.Graphic.Controller.MainGame.InputGameController;
import com.Graphic.Controller.MainGame.Marketing;
import com.Graphic.Main;
import com.Graphic.View.AppMenu;
import com.Graphic.model.App;
import com.Graphic.model.App.*;
import com.Graphic.model.Enum.Commands.MarketMenuCommands;
import com.Graphic.model.Enum.Direction;
import com.Graphic.model.Enum.ItemType.BarnORCageType;
import com.Graphic.model.Enum.ItemType.MarketType;
import com.Graphic.model.Enum.Menu;
import com.Graphic.model.User;
import com.Graphic.model.UserRenderer;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;

import static com.Graphic.View.GameMenus.GameMenu.gameMenu;
import static com.Graphic.model.App.currentMenu;
import static com.Graphic.model.HelpersClass.Color_Eraser.RED;
import static com.Graphic.model.HelpersClass.Color_Eraser.RESET;

public class MarketMenu implements Screen , InputProcessor , AppMenu{

    private static MarketMenu marketMenu;

    TiledMap map;
    OrthogonalTiledMapRenderer renderer;
    public static  OrthographicCamera camera;
    Marketing marketing;
    public MarketType marketType;
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
    private static HashMap <Sprite , BarnORCageType> mapSpriteToBarnOrCage;
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<UserRenderer> userRenderers = new ArrayList<>();


    public MarketMenu() {

    }

    public static MarketMenu getInstance() {
        if (marketMenu == null) {
            marketMenu = new MarketMenu();
        }
        return marketMenu;
    }

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
        if (skin == null) {
            return skin = new Skin(Gdx.files.internal("Mohamadreza/newSkin.json"));
        }
        return skin;
    }

    public Stage getStage() {
        if (stage == null) {
            stage = new Stage(new ScreenViewport());
        }
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
            vector = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        }
        vector.set(Gdx.input.getX() , Gdx.input.getY(), 0);
        camera.unproject(vector);
        return vector;
    }

    public static Sprite getWithMouse() {
        if (withMouse == null) {
            withMouse = new Sprite();
        }
        return withMouse;
    }

    public static HashMap<Sprite , BarnORCageType> mapSpriteToBarnOrCage() {
        if (mapSpriteToBarnOrCage == null) {
            mapSpriteToBarnOrCage = new HashMap();
        }
        return mapSpriteToBarnOrCage;
    }

    public static void setWithMouse(Sprite withMouse) {
        MarketMenu.withMouse = withMouse;
    }

    public ArrayList<User> users() {
        return users;
    }
    public void addUserRenderer(User Player) {
        UserRenderer userRenderer = new UserRenderer();
        userRenderer.addToAnimations(Direction.Up , Player.getUp());
        userRenderer.addToAnimations(Direction.Down , Player.getDown());
        userRenderer.addToAnimations(Direction.Left , Player.getLeft());
        userRenderer.addToAnimations(Direction.Right , Player.getRight());
        userRenderers.add(userRenderer);
        users.add(Player);
    }

    public void removeUserRenderer(User Player) {
        try {
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getUsername().equals(Player.getUsername())) {
                    users.remove(i);
                    userRenderers.remove(i);
                    break;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<UserRenderer> getUserRenderers() {
        return userRenderers;
    }

    @Override
    public void show() {
        marketing = new Marketing();
        marketType = Main.getClient().getPlayer().getMarketType();
        camera = new OrthographicCamera();
        camera.setToOrtho(false , 300 , 150);
        map = new TmxMapLoader().load("Mohamadreza/Maps/" + marketType.getName() + ".tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1f);
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("Mohamadreza/newSkin.json"));
        addUserRenderer(Main.getClient().getPlayer());
        Main.getBatch().begin();
        marketing.init(Main.getClient().getPlayer());
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
        camera.update();
        marketing.addPlayerToMarket();
        marketing.removePlayerFromMarket();
        //removeUserRenderer(Main.getClient().getPlayer());
        marketing.exitTheMarket();
        Main.getBatch().setProjectionMatrix( camera.combined);

        Main.getBatch().begin();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Marketing.getInstance().openProductsMenu(marketType);
        }

        if (! choosePlace) {
            renderer.setView(camera);
            renderer.render();
            camera.position.set(Main.getClient().getPlayer().getPositionX() , Main.getClient().getPlayer().getPositionY() , 0f);
            marketing.move(Main.getClient().getPlayer());
            marketing.printPlayers();
        }
        else {
            try {
                marketing.moveTextureWithMouse(getWithMouse());
            }
            catch (Exception e) {
                InputGameController.getInstance().placeItem();
            }
            camera.setToOrtho(false, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
            camera.zoom = 2f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.U)) {
            System.out.println(getVector().x + " " + getVector().y);
        }
//        if (Gdx.input.isKeyJustPressed(Input.Keys.J)) {
//            marketType = null;
//            Main.getMain().setScreen(GameMenu.getInstance());
//            currentMenu = Menu.GameMenu;
//        }
        camera.update();
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
