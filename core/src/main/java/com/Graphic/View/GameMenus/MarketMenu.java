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
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import java.util.Scanner;
import java.util.regex.Matcher;

import static com.Graphic.model.HelpersClass.Color_Eraser.RED;
import static com.Graphic.model.HelpersClass.Color_Eraser.RESET;

public class MarketMenu implements Screen , InputProcessor {

    TiledMap map;
    OrthogonalTiledMapRenderer renderer;
    OrthographicCamera camera;
    Marketing marketing;
    public static MarketType marketType;

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

    @Override
    public void show() {
        App.currentGame.currentPlayer.sprite.setSize(16 , 32);
        marketing = new Marketing();
        marketType = MarketType.StardropSaloon;
        camera = new OrthographicCamera();
        camera.setToOrtho(false , 300 , 150);
        map = new TmxMapLoader().load("Mohamadreza/Maps/Saloon.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1f);
        Main.getBatch().begin();
        marketing.init();
        Main.getBatch().end();


    }

    @Override
    public void render(float v) {
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
        marketing.move();
        camera.update();
        renderer.setView(camera);
        renderer.render();
        Main.getBatch().end();

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
