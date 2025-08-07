
package com.Graphic.Controller.MainGame;

import com.Graphic.Main;
import com.Graphic.View.GameMenus.GameMenu;
import com.Graphic.View.GameMenus.MarketMenu;
import com.Graphic.model.*;
import com.Graphic.model.Animall.Animal;
import com.Graphic.model.Animall.BarnOrCage;
import com.Graphic.model.ClientServer.GameState;
import com.Graphic.model.ClientServer.Message;
import com.Graphic.model.Enum.AllPlants.*;
import com.Graphic.model.Enum.Commands.CommandType;
import com.Graphic.model.Enum.Commands.GameMenuCommands;
import com.Graphic.model.Enum.Direction;
import com.Graphic.model.Enum.Door;
import com.Graphic.model.Enum.FoodTypes;
import com.Graphic.model.Enum.ItemType.*;
import com.Graphic.model.Enum.NPC.NPC;
import com.Graphic.model.Enum.NPC.NPCHouse;
import com.Graphic.model.Enum.ToolsType.*;
import com.Graphic.model.Enum.WeatherTime.*;
import com.Graphic.model.HelpersClass.Result;
import com.Graphic.model.HelpersClass.TextureManager;
import com.Graphic.model.MapThings.*;
import com.Graphic.model.OtherItem.*;
import com.Graphic.model.Places.*;
import com.Graphic.model.Plants.*;
import com.Graphic.model.Plants.Tree;
import com.Graphic.model.ToolsPackage.*;
import com.Graphic.model.Weather.Cloud;
import com.Graphic.model.Weather.DateHour;
import com.Graphic.model.Weather.LightningEffect;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;

import static com.Graphic.View.GameMenus.GameMenu.*;
import static com.Graphic.View.GameMenus.MarketMenu.*;
import static com.Graphic.model.App.*;
import static com.Graphic.model.HelpersClass.Color_Eraser.*;
import static com.Graphic.model.HelpersClass.TextureManager.TEXTURE_SIZE;
import static com.Graphic.model.Weather.DateHour.getDayDifferent;
import static com.Graphic.model.Enum.AllPlants.ForagingMineralsType.*;

public class GameControllerLogic {

    public static GameMenu gameMenu = GameMenu.getInstance();

    static int turnCounter = 0;
    static Image helperBackGround;
    static Random rand = new Random();
    static DateHour lastTimeUpdate;


    static Cloud cloud;
    public static LightningEffect lightningEffect;




    public static void init() {
        createScreenOverlay(gameMenu.getStage());
        lastTimeUpdate = currentGame.getGameState().currentDate.clone();
    }

    public static void update(float delta) {
        EnterTheBarnOrCage();
        EnterTheMine();
        EnterTheMarket(Main.getClient(null).getPlayer());
        addPlayerToMarket(Main.getClient(null).getPlayer());
            if ( gameMenu.isFirstLoad() && Main.getClient(null).getPlayer().isInMine()) {
                gameMenu.setFirstLoad(false);
                camera.setToOrtho(false ,Gdx.graphics.getWidth() / 4 , Gdx.graphics.getHeight() / 4);
                gameMenu.setTiledMap(new TmxMapLoader().load("Mohamadreza/Maps/Mines/10.tmx"));
                gameMenu.setRenderer(new OrthogonalTiledMapRenderer(gameMenu.getMap(),1f));

            }
            else if ( gameMenu.isFirstLoad() && Main.getClient(null).getPlayer().isInBarnOrCage()) {
                gameMenu.setFirstLoad(false);
                camera.setToOrtho(false , 300 , 150);
                gameMenu.setTiledMap(new TmxMapLoader().load(Main.getClient(null).getPlayer().getBarnOrCagePath()));
                gameMenu.setRenderer(new OrthogonalTiledMapRenderer(gameMenu.getMap() , 1f));
            }

        handleLightning(delta);


        if (currentGame.getGameState().currentDate.getHour() - lastTimeUpdate.getHour() > 3) {
            AutomaticFunctionAfterAnyAct();
            lastTimeUpdate = currentGame.getGameState().currentDate.clone();
        }

    }

    public static void handleLightning (float delta) {

        if (cloud == null) {
            if ((TimeUtils.millis() / 1000) % 40 == 0)
                if (currentGame.getGameState().currentDate.equals(Weather.Stormy)) {

                    Random random1 = new Random();
                    if (random1.nextInt(2) == 1)
                        createCloud();
                }
        }
        else if (cloud != null) {
            cloud.update(delta);
            cloud.render();

            if (cloud.isFinished())
                cloud = null;
        }
        if (lightningEffect != null) {
            if (lightningEffect.isFinished()) {
                lightningEffect = null;
                return;
            }
            lightningEffect.update(delta);
            lightningEffect.render();
        }
    }
    public static void createCloud() {

        Random random1 = new Random();
        User user = currentGame.getGameState().getPlayers().get(random1.nextInt(currentGame.getGameState().getPlayers().size()));
        Tile tile = selectTileForThor(user.getFarm());

        if (tile != null)
            cloud = new Cloud(tile);
    }

    public static ArrayList<Tile> sortMap(ArrayList<Tile> Map) {
        Map.sort((a, b) -> {
            if (a.getY() != b.getY()) return Integer.compare(a.getY(), b.getY());
            return Integer.compare(a.getX(), b.getX());
        });

        return Map;
    }

    public static boolean isNeighbor(int x1, int y1, int x2, int y2) {
        int [] dirx={0,0,1,1,1,-1,-1,-1};
        int [] diry={1,-1,0,1,-1,0,1,-1};
        for (int i=0 ; i<8 ; i++) {
            if (x1 + dirx[i] == x2 && y1 + diry[i] == y2) {
                return true;
            }
        }
        return false;
    }
    public static boolean isInGreenHouse (Tile tile) {

        for (User user : currentGame.getGameState().getPlayers()) {

            GreenHouse greenHouse = user.getFarm().getGreenHouse();

            if (tile.getX() >= greenHouse.getCoordinateX() &&
                tile.getY() >= greenHouse.getCoordinateY() &&
                tile.getX() < greenHouse.getCoordinateX() + greenHouse.getLength() &&
                tile.getY() < greenHouse.getCoordinateY() + greenHouse.getWidth())
                return true;
        }
        return false;
    }

    public static boolean directionIncorrect(String dir) {

        try {
            int x = Integer.parseInt(dir);
            return x < 1 || x > 8;

        } catch (Exception e) {
            return true;
        }
    }
    public static boolean checkAmountProductAvailable (Items items, int number) {

        Inventory inventory = Main.getClient(null).getPlayer().getBackPack().inventory;

        if (inventory.Items.containsKey(items)) {
            int amount = inventory.Items.get(items);
            return amount >= number;
        }
        return false;
    }
    public static void advanceItem(Items items, int amount) {

        Inventory inventory = Main.getClient(null).getPlayer().getBackPack().inventory;

        if (inventory.Items.containsKey(items)) {
            inventory.Items.compute(items, (k, x) -> x + amount);
        }
        else
            inventory.Items.put(items, amount);

        if (Main.getClient(null).getPlayer().getBackPack().inventory.Items.get(items) == 0) {
            Main.getClient(null).getPlayer().getBackPack().inventory.Items.remove(items);
        }
    }


    public static Tile getTileByDir (int dir) {

        float x = Main.getClient(null).getPlayer().getPositionX();
        float y = Main.getClient(null).getPlayer().getPositionY();

        if (dir == 1)
            return getTileByCoordinates((int) (x+1), (int) y);
        else if (dir == 2)
            return getTileByCoordinates((int)x+1, (int)y+1);
        else if (dir == 3)
            return getTileByCoordinates((int)x, (int)y+1);
        else if (dir == 4)
            return getTileByCoordinates((int)x-1, (int)y+1);
        else if (dir == 5)
            return getTileByCoordinates((int)x-1, (int)y);
        else if (dir == 6)
            return getTileByCoordinates((int)x-1, (int)y-1);
        else if (dir == 7)
            return getTileByCoordinates((int)x, (int)y-1);
        else if (dir == 8)
            return getTileByCoordinates((int)x+1, (int)y-1);
        else
            return null;
    }
    public static Tile getTileByCoordinates(int x , int y) {
        if (x<0 || y<0 || x>=90 || y>=90) {
            return null;
        }
        Tile tarfetTile = currentGame.bigMap.get(90 * y + x);
        return tarfetTile;
    }

    public static Tile getTileByCoordinates(int x, int y , GameState gameState) {
        if (x<0 || y<0 || x>=90 || y>=90) {
            return null;
        }
        Tile targetTile = gameState.bigMap.get(90 * y + x);
        return targetTile;
    }

    // create initial map

    public static void createInitialMine( int x, int y , int topLeftX, int topLeftY, int width , int height , User Player , GameState gameState) {
        Farm farm = Player.getFarm();

        Mine mine=null;

        for (int i = topLeftX + (60 * x) ; i < topLeftX + (60*x) + width; i++) {
            for (int j = topLeftY + (60 * y); j < topLeftY + (60 * y) + height; j++) {
                mine = new Mine(topLeftX + 60 *x , topLeftY + 60 * y ,width , height);
                Tile tile = new Tile(i , j , mine);
                farm.Farm.add(tile);
                gameState.bigMap.add(tile);
            }
        }
        farm.setMine(mine);
    }

    public static void createInitialLake( int x, int y , int topLeftX , int topLeftY , int width , int height , User Player , GameState gameState) {
        Farm farm = Player.getFarm();
        Lake lake = null;
        for (int i = topLeftX + 60 * x; i < topLeftX + 60 * x + width; i++) {
            for (int j = topLeftY + 60 * y; j < topLeftY + 60 * y + height; j++) {
                lake = new Lake(topLeftX + 60 * x, topLeftY + 60 * y, width, height);
                Tile tile = new Tile(i, j, lake);
                farm.Farm.add(tile);
                gameState.bigMap.add(tile);
            }
        }
        farm.setLake(lake);

    }

    public static void createInitialHouse(int x, int y , int topLeftX , int topLeftY , int width , int height , User Player , GameState gameState) {
        Farm farm = Player.getFarm();
        Wall wall = new Wall(3);
        wall.setWallType(WallType.House);
        wall.setCharactor('#');
        door houseDoor = new door();
        houseDoor.setDoor(Door.House);
        houseDoor.setCharactor('D');
        Fridge fridge= new Fridge(topLeftX + 60 * x + width -2 , topLeftY + 60 * y + height - 2);
        fridge.setCharactor('F');


        for (int i = topLeftX + 60 * x; i < topLeftX + 60 * x + width ; i++) {
            for (int j = topLeftY + 60 * y; j < topLeftY + 60 * y + height ; j++) {
                if (i == topLeftX + 60 * x + width -2 && j == topLeftY + 60 * y + height-2) {
                    Tile tile = new Tile(i, j, fridge);
                    farm.Farm.add(tile);
                    gameState.bigMap.add(tile);
                }
                else {
                    Home home = new Home(topLeftX + 60 * x, topLeftY + 60 * y,width,height, fridge);
                    Tile tile = new Tile(i, j, home);
                    farm.Farm.add(tile);
                    gameState.bigMap.add(tile);
                }
            }
        }
        farm.setHome(new Home(topLeftX + 60 * x, topLeftY + 60 * y,width,height, fridge));
    }

    public static void createInitialGreenHouse(int x, int y , int topLeftX , int topLeftY , int width , int height , User Player , GameState gameState) {
        Farm farm = Player.getFarm();
        Wall GreenWall = new Wall(3);
        GreenWall.setWallType(WallType.GreenHouse);
        GreenWall.setCharactor('#');
        door greenHouseDoor=new door();
        greenHouseDoor.setDoor(Door.GreenHouse);
        greenHouseDoor.setCharactor('D');
        GreenHouse greenHouse=new GreenHouse(topLeftX + 60*x,topLeftY + 60*y , width , height);
        greenHouse.setCharactor('G');
        WaterTank waterTank=new WaterTank(100);
        greenHouse.setWaterTank(waterTank);

        for (int i= topLeftX + (60 * x) ; i< topLeftX + 60 * x + width; i++){
            for (int j=topLeftY + 60 * y ; j<topLeftY + 60 * y + height ; j++) {
                if (i==topLeftX + 60 * x + width/2 && j==topLeftY + 60 * y + height-1) {
                    Tile tile = new Tile(i, j, greenHouseDoor);
                    farm.Farm.add(tile);
                    gameState.bigMap.add(tile);
                }
                else if (i== topLeftX + 60*x + width/2 && j==topLeftY + 60*y + 1) {
                    Tile tile = new Tile(i, j, waterTank);
                    farm.Farm.add(tile);
                    gameState.bigMap.add(tile);
                }
                else if (i == topLeftX + 60 * x || i == topLeftX + 60 * x + width -1 || j==topLeftY+60*y || j==topLeftY+60*y + height-1) {
                    Tile tile = new Tile(i, j, GreenWall);
                    farm.Farm.add(tile);
                    currentGame.bigMap.add(tile);
                }

                else {
                    Tile tile = new Tile(i, j, greenHouse);
                    farm.Farm.add(tile);
                    gameState.bigMap.add(tile);
                }
            }
        }
        farm.setGreenHouse(greenHouse);
    }

    public static Message build(GameState gameState) {
        buildHall(gameState);
        buildNpcVillage(gameState);
        HashMap<String , Object> body = new HashMap<>();
        body.put("BigMap" , gameState.bigMap);
        sortMap(gameState.bigMap);
        return new Message(CommandType.BIG_MAP , body);
    }
    public static void buildHall( GameState gameState ) {
        Walkable walkable=new Walkable();

        for (int j = 30 ; j<60 ; j++) {
            Tile tile=new Tile(15 , j , walkable);
            gameState.bigMap.add(tile);
        }
        for (int i=30 ; i<60 ; i++) {
            Tile tile=new Tile(i , 15 , walkable);
            gameState.bigMap.add(tile);
        }
        for (int i = 30 ; i<60 ; i++) {
            Tile tile=new Tile(i , 75 , walkable);
            gameState.bigMap.add(tile);
        }
        for (int j=30 ; j<60 ; j++) {
            Tile tile=new Tile(75 , j , walkable);
            gameState.bigMap.add(tile);
        }
        for (int i=30 ; i<60 ; i++) {
            for (int j=0 ; j<30 ; j++) {
                if (i==45 && j >= 16) {
                    Tile tile=new Tile(i , j , walkable);
                    gameState.bigMap.add(tile);
                }
                else if (j != 15) {
                    UnWalkable unWalkable=new UnWalkable();
                    Tile tile=new Tile(i , j , unWalkable);
                    gameState.bigMap.add(tile);
                }
            }
        }
        for (int i=0 ; i<30 ; i++) {
            for (int j=30 ; j<60 ; j++) {

                if (j==45 && i>=16) {
                    Tile tile=new Tile(i , j , walkable);
                    gameState.bigMap.add(tile);
                }
                else if (i!=15) {
                    UnWalkable unWalkable=new UnWalkable();
                    Tile tile=new Tile(i , j , unWalkable);
                    gameState.bigMap.add(tile);
                }
            }
        }
        for (int i=30 ; i<60 ; i++) {
            for (int j=60 ; j<90 ; j++) {
                if (i==45 && j <= 74) {
                    Tile tile=new Tile(i , j , walkable);
                    gameState.bigMap.add(tile);
                }
                else if (j!=75) {
                    UnWalkable unWalkable=new UnWalkable();
                    Tile tile=new Tile(i , j , unWalkable);
                    gameState.bigMap.add(tile);
                }
            }
        }
        for (int i=60 ; i<90 ; i++) {
            for (int j=30 ; j<60 ; j++) {
                if (j == 45 && i <= 74) {
                    Tile tile=new Tile(i , j , walkable);
                    gameState.bigMap.add(tile);
                }
                else if (i!=75) {
                    UnWalkable unWalkable=new UnWalkable();
                    Tile tile=new Tile(i , j , unWalkable);
                    gameState.bigMap.add(tile);
                }
            }
        }
    }
    public static void buildNpcVillage(GameState gameState) {
        Wall wall=new Wall(3);
        wall.setWallType(WallType.Npc);
        door dor=new door();
        dor.setDoor(Door.Npc);
        Marketing marketing=new Marketing();

        for (int i=30 ; i<60 ; i++) {
            for (int j=30 ; j<60 ; j++) {
                if (i== 30 || i==59 || j==30 || j==59) {
                    if ( (i==30 && j==45 ) || (i==45 && j==30) || (i==45 && j==59) || (i==59 && j==45) ) {
                        Tile tile=new Tile(i , j , dor);
                        gameState.bigMap.add(tile);
                    }
                    else {
                        Tile tile = new Tile(i, j, wall);
                        gameState.bigMap.add(tile);
                    }
                }
                else if (Market.isInMarket(i , j) != null) {
                    MarketType marketType = Market.isInMarket(i, j);
                    Market market = new Market(marketType);
                    Tile tile=new Tile(i , j , market);
                    gameState.bigMap.add(tile);
                }

                else if (NPC.wallOrDoor(i, j) != null) {
                    NPC npc = NPC.wallOrDoor(i, j);

                        NPCHouse house = new NPCHouse(npc);
                        Tile tile = new Tile(i, j, house);
                        gameState.bigMap.add(tile);

//                        if (i == npc.getTopLeftX() + npc.getWidth() -1 && j==npc.getTopLeftY() + 2) {
//                        Tile tile=new Tile(i , j , dor);
//                        currentGame.bigMap.add(tile);
//                    }
//                    else {
//                        Tile tile=new Tile(i , j , wall);
//                        currentGame.bigMap.add(tile);
//                    }
                }
                else if (isInNpc(i , j) != null) {
                    NPC npc = isInNpc(i, j);
                        NPCHouse house = new NPCHouse(npc);
                        Tile tile = new Tile(i, j, house);
                        gameState.bigMap.add(tile);
//                     else {
//                        Walkable walkable = new Walkable();
//                        walkable.setGrassOrFiber(npc.getName());
//                        Tile tile = new Tile(i, j, walkable);
//                        currentGame.bigMap.add(tile);
//                    }
                }
                else {
                    Walkable walkable=new Walkable();
                    Tile tile=new Tile(i , j , walkable);
                    gameState.bigMap.add(tile);
                }
            }
        }
    }

    public static NPC isInNpc(int x , int y) {
        for (NPC npc : NPC.values()) {
            int tlx=npc.getTopLeftX();
            int tly=npc.getTopLeftY();
            if (x > tlx && x < tlx + npc.getWidth() - 1 && y > tly && y < tly + npc.getHeight() - 1 ) {
                return npc;
            }
        }
        return null;
    }
    public static Message createInitialFarm(int id , User Player , GameState gameState) {

        long seed=System.currentTimeMillis();
        Player.setFarm(new Farm());
        Farm farm= Player.getFarm();
        Mine mine=null;
        Lake lake=null;
        Home home=null;
        GreenHouse greenHouse=null;
        Fridge fridge=null;

        if (id ==1 ) {
            mine = new Mine (22 , 2 , 6 , 6);
            lake = new Lake(2 , 21 , 5 , 6);
            home= new Home(12 , 13 , 7 , 7 , fridge);
            greenHouse= new GreenHouse(2 , 2 , 8 , 9);
        }
        if (id ==2 ) {
            mine = new Mine (20 , 5 , 6 , 6);
            lake = new Lake(4 , 11 , 5 , 6);
            home= new Home(16 , 15 , 7 , 7 , fridge);
            greenHouse= new GreenHouse(5 , 20 , 8 , 7);
        }
        boolean createMine=false;
        boolean createLake=false;
        boolean createHome=false;
        boolean createGreenHouse=false;
        int treeNumber=0;

        for (int i=0 ; i<30 ;i++){
            for (int j=0 ; j<30 ; j++) {
                assert mine != null;
                if (i >= mine.getStartX() && i<mine.getStartX() + mine.getWidth() && j>=mine.getStartY() && j< mine.getStartY() + mine.getHeight() ) {
                    if (! createMine) {
                        createInitialMine(Main.getClient(null).getPlayer().topLeftX,
                            Main.getClient(null).getPlayer().topLeftY, mine.getStartX(),
                            mine.getStartY(), mine.getWidth(), mine.getHeight() , Player , gameState);
                        createMine = true;
                    }
                }

                else if(i >= lake.getTopLeftX() && i< lake.getTopLeftX() + lake.getWidth() && j>=lake.getTopLeftY() && j< lake.getTopLeftY() + lake.getHeight()) {
                    if (! createLake) {
                        createInitialLake(Main.getClient(null).getPlayer().topLeftX,
                            Main.getClient(null).getPlayer().topLeftY, lake.getTopLeftX(),
                            lake.getTopLeftY(), lake.getWidth(), lake.getHeight() , Player , gameState);
                        createLake = true;
                    }
                }

                else if(i>=greenHouse.getCoordinateX()  && i < greenHouse.getCoordinateX() + greenHouse.getWidth()
                    && j>=greenHouse.getCoordinateY() && j<greenHouse.getCoordinateY() + greenHouse.getLength() ) {

                    if (! createGreenHouse) {
                        createInitialGreenHouse(Main.getClient(null).getPlayer().topLeftX, Main.getClient(null).getPlayer().topLeftY, greenHouse.getCoordinateX(), greenHouse.getCoordinateY(),
                            greenHouse.getWidth(), greenHouse.getLength() , Player , gameState);

                        createGreenHouse = true;
                    }
                }

                else if (i >= home.getTopLeftX() && i<home.getTopLeftX() + home.getWidth() && j >= home.getTopLeftY() && j<home.getTopLeftY() + home.getLength() ) {
                    if (! createHome) {
                        createInitialHouse(Main.getClient(null).getPlayer().topLeftX, Main.getClient(null).getPlayer().topLeftY,
                            home.getTopLeftX(), home.getTopLeftY(), home.getWidth(), home.getLength() , Player , gameState);
                        createHome = true;
                    }
                }

                else {
                    treeNumber+=MapGenerator(i,j,seed , treeNumber , Player , gameState);
                }
            }
        }

        farm.setX(60 * Player.topLeftX);
        farm.setY(60 * Player.topLeftY);
        farm.setIndex(2 * Player.topLeftY + Player.topLeftX + 1);
        gameState.getFarms().add(farm);
        gameState.bigMap.addAll(farm.Farm);

        HashMap<String , Object> body = new HashMap<>();
        body.put("Farm", farm);
        return new Message(CommandType.FARM , body);
    }

    public static int MapGenerator(int i,int j,long seed , int treeNumber , User Player , GameState gameState){
        if (i == 0 || i == 29 || j == 0 || j == 29) {
            if ((i == 15 && j == 29) || (i==15 && j == 0 ) ){
                door FarmDoor=new door();
                FarmDoor.setDoor(Door.Farm);
                FarmDoor.setCharactor('D');
                Tile tile=new Tile(i + 60 * Player.topLeftX,j + 60 * Player.topLeftY,FarmDoor);
                Player.getFarm().Farm.add(tile);
                return 0;
            }
            else if ((i==29 && j==15) || (i==0 && j==15)){
                door FarmDoor=new door();
                FarmDoor.setDoor(Door.Farm);
                FarmDoor.setCharactor('D');
                Tile tile=new Tile(i + 60 * Player.topLeftX,j + 60 * Player.topLeftY,FarmDoor);
                Player.getFarm().Farm.add(tile);
                return 0;
            }
            else {
                if (i == 0 || i == 29) {
                    Wall wall = new Wall(1);
                    Tile tile = new Tile(i + 60 * Player.topLeftX, j + 60 * Player.topLeftY, wall);
                    Player.getFarm().Farm.add(tile);
                }
                else {
                    Wall wall = new Wall(0);
                    Tile tile = new Tile(i + 60 * Player.topLeftX, j + 60 * Player.topLeftY, wall);
                    Player.getFarm().Farm.add(tile);
                }
                return 0;
            }

        }
        else {
            if (i ==15 && j == 20) {
                Walkable walkable=new Walkable();
                Tile tile = new Tile(i + 60 * Player.topLeftX, j + 60 * Player.topLeftY, walkable);
                Player.getFarm().Farm.add(tile);
                return 0;
            }
            PerlinNoise perlinNoise = new PerlinNoise(seed);

            double noise = perlinNoise.noise(i * 0.1, j * 0.1);
            if (-1.2 < noise && noise < -0.9 && treeNumber <30)
            {
                Tree tree = new Tree(TreeType.OakTree,gameState.currentDate.clone());
                Tile tile = new Tile(i + 60 * Player.topLeftX, j + 60 * Player.topLeftY, tree);
                Player.getFarm().Farm.add(tile);
                gameState.bigMap.add(tile);
                return 1;
            }
            else if(noise > -0.9 && noise <-0.5 && treeNumber <30){
                Tree tree = new Tree(TreeType.MapleTree,gameState.currentDate.clone());
                Tile tile = new Tile(i + 60 * Player.topLeftX, j + 60 * Player.topLeftY, tree);
                Player.getFarm().Farm.add(tile);
                gameState.bigMap.add(tile);
                return 1;
            }
            else if (noise > -0.5 && noise < - 0.2 && treeNumber <30){
                Tree tree = new Tree(TreeType.PineTree, gameState.currentDate.clone());
                Tile tile = new Tile(i + 60 * Player.topLeftX, j + 60 * Player.topLeftY, tree);
                Player.getFarm().Farm.add(tile);
                gameState.bigMap.add(tile);
                return 1;
            }
            else if (-0.1 < noise && noise < 0.0) {
                BasicRock basicRock = new BasicRock();
                basicRock.setCharactor('S');
                Tile tile = new Tile(i + 60 * Player.topLeftX, j + 60 * Player.topLeftY, basicRock);
                Player.getFarm().Farm.add(tile);
                gameState.bigMap.add(tile);
                return 0;
            }
            else {
                Walkable walkable = new Walkable();
                walkable.setCharactor('.');
                Tile tile = new Tile(i + 60 * Player.topLeftX, j + 60 * Player.topLeftY, walkable);
                Player.getFarm().Farm.add(tile);
                gameState.bigMap.add(tile);
                return 0;
            }

        }

    }

    public static User getUserByLocation (int j, int i) {

        for (User user : currentGame.getGameState().getPlayers())
            if (j == user.getPositionX() && i == user.getPositionY())
                return user;
        return null;
    }
    public static boolean checkTile(Tile tile){
        if (tile==null) {
            return false;
        }
        return tile.getGameObject() instanceof Home || tile.getGameObject() instanceof door
            || (tile.getGameObject() instanceof Walkable)
            || tile.getGameObject() instanceof GreenHouse;
    }

    public static void EnterTheMine() {
        if (Main.getClient(null).getPlayer().getDirection().equals(Direction.Up)) {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                if (Main.getClient(null).getPlayer().getFarm().getMine().getDoor().checkCollisionMouse(gameMenu.getMousePos())
                    && Main.getClient(null).getPlayer().getFarm().getMine().getDoor().checkCollision(Main.getClient(null).getPlayer()) ) {

                    Main.getClient(null).getPlayer().setInMine(true);
                    Main.getClient(null).getPlayer().setInFarmExterior(false);


                    Main.getClient(null).getPlayer().sprite.setPosition(140 , 68);
                }
            }
        }
    }

    public static void EnterTheMarket(User Player) {
        if (Player.getDirection().equals(Direction.Up) && Player.isInFarmExterior()) {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                for (MarketType market : MarketType.values()) {
                    if (market.getOutsideDoor().checkCollisionMouse(gameMenu.getMousePos()) &&
                        market.getOutsideDoor().checkCollision(Player)) {

                        HashMap<String , Object> body = new HashMap<>();
                        body.put("Player", Player);
                        body.put("Market", market);
                        Main.getClient(null).getRequests().add(new Message(CommandType.ENTER_THE_MARKET , body));
                        return;
                    }
                }
            }
        }
    }

    public static void AnswerEnterTheMarket(Message message , Game game) {
        for (User user : game.getGameState().getPlayers()) {
            if (message.getFromBody("Player").equals(user)) {
                user.setIsInMarket(true);
                user.setInFarmExterior(false);
                MarketType market = message.getFromBody("Market");
                user.setPositionX(market.getInsideDoor().getX());
                user.setPositionY(market.getInsideDoor().getY());
                HashMap<String , Object> body = new HashMap<>();
                body.put("Player", user);
                body.put("Market", market);
                body.put("X" , market.getInsideDoor().getX());
                body.put("Y" , market.getInsideDoor().getY());
                body.put("Is in farm" , false);
                body.put("Is in Market" , true);
                game.getDiffQueue().add(new Message(CommandType.ENTER_THE_MARKET , body));
            }
        }
    }

    public static void addPlayerToMarket(User Player) {
        while (Player.getJoinMarket().isEmpty()) {
            User P  = Player.getJoinMarket().poll();
            MarketMenu.getInstance().users().add(P);
            MarketMenu.getInstance().addUserRenderer(P);
        }
    }
    public static void showForagingMinerals(Mine mine) {
        if (Main.getClient(null).getPlayer().isInMine()) {
            for (ForagingMinerals foragingMinerals : mine.getForagingMinerals()) {
                foragingMinerals.getSprite().setPosition(foragingMinerals.getPosition().x, foragingMinerals.getPosition().y);
                foragingMinerals.getSprite().setSize(TEXTURE_SIZE / 2 , TEXTURE_SIZE / 2);
                foragingMinerals.getSprite().draw(Main.getBatch());
            }
        }
    }




    public static boolean checkCoordinateForFishing(){
//        int [] x={1,1,1,0,0,-1,-1,-1};
//        int [] y={1,0,-1,1,-1,-1,0,1};
//        for (int i=0;i<8;i++){
//            if (getTileByCoordinates(Main.getClient(null).getPlayer().getPositionX() +x[i],Main.getClient(null).getPlayer().getPositionY() +y[i]).
//                getGameObject() instanceof Lake){
//                return true;
//            }
//        }
        return false;
    }
    public static FishingPole isFishingPoleTypeExist(String name){
        Inventory inventory=Main.getClient(null).getPlayer().getBackPack().inventory;
        FishingPoleType fishingPoleType=null;
        try {
            fishingPoleType=FishingPoleType.getFishingPoleType(name);
        }
        catch (NullPointerException e){
            System.out.println(e.getMessage());
            return null;
        }
        FishingPole fishingPole=new FishingPole();
        fishingPole.type=fishingPoleType;
        for (Map.Entry<Items,Integer> entry: inventory.Items.entrySet()){
            if (entry.getKey().equals(fishingPole)) {
                return fishingPole;
            }
        }
        return null;
    }
    public static Quantity productQuantity(double quantity){

        if (quantity <= 0.5) {
            return Quantity.Normal;
        }
        if (quantity <= 0.7) {
            return Quantity.Silver;
        }
        if (quantity <= 0.9) {
            return Quantity.Golden;
        }

        return Quantity.Iridium;
    }
    public static boolean checkTilesForCreateBarnOrCage(int x, int y, int width, int height) {
        if (x<60 * Main.getClient(null).getPlayer().topLeftX || y< 60 * Main.getClient(null).getPlayer().topLeftY) {
            return false;
        }
        if (x + width > 30 + 60 * Main.getClient(null).getPlayer().topLeftX ||
            y + height > 30 + 60 * Main.getClient(null).getPlayer().topLeftY) {
            return false;
        }
        for (int i = x; i < x+width; i++) {
            for (int j = y; j < y+height; j++) {
                Tile tile = getTileByCoordinates(i , j ,Main.getClient(null).getLocalGameState());
                if (!(tile.getGameObject() instanceof Walkable)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void EnterTheBarnOrCage() {
        if (Main.getClient(null).getPlayer().getDirection().equals(Direction.Up)) {
            if ( Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) ) {
                for (BarnOrCage barnOrCage :Main.getClient(null).getPlayer().BarnOrCages) {
                    if (barnOrCage.getDoor().checkCollisionMouse(gameMenu.getMousePos()) && barnOrCage.getDoor().checkCollision(Main.getClient(null).getPlayer())) {
                        Main.getClient(null).getPlayer().setBarnOrCagePath(barnOrCage.getBarnORCageType().getEntryIconPath());
                        Main.getClient(null).getPlayer().sprite.setPosition(barnOrCage.getBarnORCageType().getInitX() , barnOrCage.getBarnORCageType().getInitY());
                        Main.getClient(null).getPlayer().setCurrentBarnOrCage(barnOrCage);
                        camera.setToOrtho(false , 300,150);
                        Main.getClient(null).getPlayer().setInFarmExterior(false);
                        return;
                    }
                }
            }
        }
    }

    public static void walkInBarnOrCage() {
        float x = Main.getClient(null).getPlayer().sprite.getX();
        float y = Main.getClient(null).getPlayer().sprite.getY();

        if (Gdx.input.isKeyPressed(Input.Keys.U)) {
            System.out.println(Main.getClient(null).getPlayer().sprite.getX() + ", " + Main.getClient(null).getPlayer().sprite.getY());
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            //InputGameController.moveAnimation();
            Main.getClient(null).getPlayer().setDirection(Direction.Up);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            //InputGameController.moveAnimation();
            Main.getClient(null).getPlayer().setDirection(Direction.Down);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            //InputGameController.moveAnimation();
            Main.getClient(null).getPlayer().setDirection(Direction.Left);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            //InputGameController.moveAnimation();
            Main.getClient(null).getPlayer().setDirection(Direction.Right);
        }

        else {
            Main.getClient(null).getPlayer().sprite.draw(Main.getBatch());
            return;
        }

        Main.getClient(null).getPlayer().sprite.setSize(16 , 32);


        Main.getClient(null).getPlayer().sprite.
            setPosition(x + Main.getClient(null).getPlayer().getDirection().getX() * 50 * Gdx.graphics.getDeltaTime(),
                y - Main.getClient(null).getPlayer().getDirection().getY() * 50 * Gdx.graphics.getDeltaTime());

//        if (! checkCollisionInBarnOrCage()) {
//            Main.getClient(null).getPlayer().sprite.setPosition(x , y);
//        }
        Main.getClient(null).getPlayer().sprite.draw(Main.getBatch());
        try {
            Main.getClient(null).getPlayer().getCurrentBarnOrCage().getBarnORCageType().exitBarnOrCage(Main.getClient(null).getPlayer());
        }
        catch (Exception e) {

        }
    }

    public static void showAnimalsInBarnOrCage() {
        for (Animal animal : Main.getClient(null).getPlayer().getCurrentBarnOrCage().animals) {
            if (! animal.isOut()) {
                animal.getSprite().draw(Main.getBatch());
                animal.getSprite().setRegion(animal.getAnimation().getKeyFrame(animal.getTimer()));
                if (! animal.getAnimation().isAnimationFinished(animal.getTimer())) {
                    animal.setTimer(animal.getTimer() + (Gdx.graphics.getDeltaTime()/2));
                }
                else {
                    animal.setTimer(0);
                }

                animal.getSprite().setPosition(
                    Main.getClient(null).getPlayer().getCurrentBarnOrCage().getBarnORCageType().getPoints().get(animal.getIndex()).x,
                    Main.getClient(null).getPlayer().getCurrentBarnOrCage().getBarnORCageType().getPoints().get(animal.getIndex()).y);
                //System.out.println(animal.getSprite().getX()+ ",,"+animal.getSprite().getY());
                animal.getSprite().setSize(animal.getType().getX() , animal.getType().getY());

                animal.getSprite().draw(Main.getBatch());

            }
        }
    }

    public static Animal showAnimalInfo() {
        if (Main.getClient(null).getPlayer().isInBarnOrCage()) {
            for (Animal animal : Main.getClient(null).getPlayer().getCurrentBarnOrCage().animals) {
                if (!animal.isOut()) {
                    if (gameMenu.getMousePos().x >= animal.getSprite().getX() &&
                        gameMenu.getMousePos().x <= animal.getSprite().getX() + animal.getSprite().getWidth() &&
                        gameMenu.getMousePos().y >= animal.getSprite().getY() &&
                        gameMenu.getMousePos().y <= animal.getSprite().getY() + animal.getSprite().getHeight()) {

                        Main.getBatch().draw(TextureManager.get("Mohamadreza/animalInfo.png"),
                            animal.getSprite().getX() + animal.getSprite().getWidth() / 2,
                            animal.getSprite().getY() + animal.getSprite().getHeight() / 2);
                        return animal;
                    }
                }
            }
        }
        else {
            for (BarnOrCage barnOrCage : Main.getClient(null).getPlayer().BarnOrCages) {
                for (Animal animal : barnOrCage.animals) {
                    if (animal.isOut()) {
                        if (gameMenu.getMousePos().x >= animal.getSprite().getX() &&
                            gameMenu.getMousePos().x <= animal.getSprite().getX() + animal.getSprite().getWidth() &&
                            gameMenu.getMousePos().y >= animal.getSprite().getY() &&
                            gameMenu.getMousePos().y <= animal.getSprite().getY() + animal.getSprite().getHeight()) {

                            Main.getBatch().draw(TextureManager.get("Mohamadreza/animalInfo.png"),
                                animal.getSprite().getX() + animal.getSprite().getWidth() / 2,
                                animal.getSprite().getY() + animal.getSprite().getHeight() / 2);
                            return animal;
                        }
                    }
                }
            }
        }
        return null;
    }

    public static void createAnimalInformationWindow(Animal animal) {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && animal!= null) {
            BitmapFont font = gameMenu.getAnimalFont();
            //font.setColor(Color.BROWN);
            Table mainTable = new Table();
            Window window = new Window("Information", getSkin(), "default");
            window.setSize(1000, 750);
            window.setPosition(Gdx.graphics.getWidth() / 2 - 500, Gdx.graphics.getHeight() / 2 - 375);
            Image image = new Image(TextureManager.get(animal.getType().getIcon()));
            Texture background = TextureManager.get("Mohamadreza/AnimalBackground2.png");
            Drawable backgroundDrawable = new TextureRegionDrawable(new TextureRegion(background));

            Table content = new Table();
            content.setBackground(backgroundDrawable);
            content.add(image).size(200, 200).top().row();

            Label.LabelStyle labelStyle = new Label.LabelStyle();
            labelStyle.font = font;
            Table right = new Table();
            Table lowerRight = new Table();
            right.add(createRightUpSideWindow(animal , labelStyle , backgroundDrawable , window , lowerRight)).top().width(500).height(350).row();
            right.add(createRightMidWindow(animal , labelStyle , backgroundDrawable , lowerRight ,window)).width(500).height(200).row();
            lowerRight.setBackground(backgroundDrawable);
            right.add(lowerRight).width(500).height(200);

            mainTable.add(createLeftSideWindow(animal, content, labelStyle, window)).width(500).height(750);
            mainTable.add(right).padLeft(5).width(500).height(750);
            window.add(mainTable).expand().fill();
            gameMenu.getStage().addActor(window);
        }

    }


    private static Table createLeftSideWindow(Animal animal ,Table content ,Label.LabelStyle labelStyle , Window window) {
        Label Name = new Label("Name: "+animal.getName() , labelStyle);
        Label FriendShip = new Label("Friendship: "+animal.getFriendShip() , labelStyle);
        Label isPetToday = new Label("Need To Pet: "+animal.isPetToday() , labelStyle);
        Label isFeedToday = new Label("FeedToday: "+animal.isFeedToday() , labelStyle);

        content.add(Name).center().row();
        content.add(FriendShip).center().row();
        content.add(isPetToday).center().row();
        content.add(isFeedToday).center().row();

        TextButton button = new TextButton("",getSkin());
        button.clearChildren();
        Label back = new Label("Back",labelStyle);
        button.add(back).center();
        content.add(button).center().size(200,100).row();

        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                window.remove();
            }
        });
        return content;
    }

    private static Table createRightUpSideWindow(Animal animal , Label.LabelStyle labelStyle , Drawable drawable , Window window , Table lowerRight) {
        Table right = new Table();
        right.setBackground(drawable);
        TextButton Sell = new TextButton("",getSkin());
        Label sell = new Label("Sell",labelStyle);
        Sell.clearChildren();
        Sell.add(sell).center();

        Sell.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                window.remove();
                InputGameController x = InputGameController.getInstance();
                Marketing marketing = new Marketing();
                Dialog dialog = marketing.createDialogError();
                Label success = new Label(x.sellAnimal(animal).toString(),new Label.LabelStyle(getFont() , Color.BLACK));
                dialog.getContentTable().add(success);
                ImageButton closeButton = marketing.createCloseButtonForDialog(dialog);
                dialog.getTitleTable().add(closeButton).padRight(5).padTop(3).right();
                dialog.show(gameMenu.getStage());
                dialog.setPosition(400,100);
                dialog.setSize(1000,48);
            }
        });


        TextButton Feed = new TextButton("",getSkin());
        Feed.clearChildren();
        Label feed = new Label("Feed",labelStyle);
        Feed.add(feed).center();

        Feed.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                InputGameController x = InputGameController.getInstance();
                Result result = x.feedHay(animal);
                createDialogError(result , lowerRight);
            }
        });

        TextButton Pet = new TextButton("",getSkin());
        Pet.clearChildren();
        Label pet = new Label("Pet",labelStyle);
        Pet.add(pet).center();

        Pet.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                pet(animal);
                window.remove();
                for (int i = 0 ; i < 5 ; i++) {
                    gameMenu.getHeartAnimations().add(new HeartAnimation(
                        TextureManager.get("Mohamadreza/heart.png") ,
                        animal.getSprite().getX() , animal.getSprite().getY()));
                }
            }
        });

        SelectBox<String> collectProduct = getProduct(animal, lowerRight);

        right.add(Pet).center().size(200,66).top().row();
        right.add(Sell).center().size(200,66).row();
        right.add(Feed).center().size(200,66).row();
        right.add(collectProduct).center().row();

        return right;

    }

    private static SelectBox<String> getProduct(Animal animal, Table lowerRight) {
        SelectBox<String> collectProduct = new SelectBox<>(getSkin());
        collectProduct.clear();
        collectProduct.setItems("Collect Products" , "Uncollected Produces");
        collectProduct.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                String selected = collectProduct.getSelected();
                switch (selected) {
                    case "Collect Products": {
                        InputGameController controller = InputGameController.getInstance();
                        Result result = controller.getProductAnimals(animal);
                        createDialogError(result , lowerRight);
                    }
                    case "Uncollected Produces": {
                        InputGameController controller = InputGameController.getInstance();
                        Result result = controller.produces(animal);
                        createDialogError(result , lowerRight);
                    }
                }
            }
        });
        return collectProduct;
    }

    private static Table createRightMidWindow(Animal animal , Label.LabelStyle labelStyle , Drawable drawable , Table lowerRight , Window window) {
        Table mid = new Table();
        mid.clearChildren();
        mid.setBackground(drawable);

        Label shepherd = new Label("Shepherd Animals", labelStyle);
        mid.add(shepherd).center().top().padTop(20).left().padLeft(50).row();

// جدول داخلی برای x و y
        Table coordTable = new Table();
        coordTable.defaults().size(100, 50).pad(5); // فاصله بین فیلدها

        TextField x = new TextField("", getSkin());
        x.setMessageText(" X");

        TextField y = new TextField("", getSkin());
        y.setMessageText(" Y");

        coordTable.add(x);
        coordTable.add(y);

// حالا جدول رو اضافه کن به mid
        mid.add(coordTable).center().padTop(5).row();

// دکمه
        TextButton button = new TextButton("", getSkin());
        Label submit = new Label("Shepherd", labelStyle);
        button.add(submit).center();
        mid.add(button).center().padTop(5).size(200, 50).row();


        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                InputGameController controller = InputGameController.getInstance();
                Result result = controller.checkShepherdAnimals(x.getText() , y.getText() , animal);
                if (! result.IsSuccess()) {
                   createDialogError(result , lowerRight);
                }

                List<Tile> Path = controller.shepherdAnimals(x.getText() , y.getText() , animal);
                if (Path==null) {
                    createDialogError(new Result(false , "There is no way for animal to go to this coordinate") , lowerRight);
                }

                else {
                    animal.setOut(true);
                    gameMenu.getShepherdingAnimals().add(animal);
                    animal.setMoving(true);
                    walkAnimalGradually(Path,animal);
                    window.remove();
                }
            }
        });

        return mid;

    }

    private static void createDialogError(Result result , Table lowerRight) {
        lowerRight.clearChildren();
        Marketing marketing = new Marketing();
        Dialog dialog = marketing.createDialogError();
        dialog.getContentTable().pad(10); // فاصله داخلی دیالوگ
        ImageButton closeButton = marketing.createCloseButtonForDialog(dialog);
        Table closeTable = new Table();
        closeTable.add(closeButton).right().top();
        dialog.getContentTable().add(closeTable).expandX().fillX().row();


        Label message = new Label(result.toString(), new Label.LabelStyle(getFont(), Color.BLACK));
        message.setWrap(true);
        message.setAlignment(Align.left);
        dialog.getContentTable().add(message)
            .width(460) // کمتر از عرض کل دیالوگ
            .padTop(10)
            .padBottom(10)
            .expandX()
            .fillX()
            .row();

        lowerRight.add(dialog).size(500, 200).center();
    }

    private static void walkAnimalGradually(List<Tile> Path , Animal animal) {
        Collections.reverse(Path);
        Timer timer = new Timer();
        for (int i = 0; i < Path.size() - 1; i++) {
            final int finalI = i;
            try {
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (Path.get(finalI+1).getX() - Path.get(finalI).getX() == 0 && Path.get(finalI+1).getY() - Path.get(finalI).getY() == 1) {
                            animal.setDirection(Direction.Down);
                        }
                        if (Path.get(finalI+1).getX() - Path.get(finalI).getX() == 0 && Path.get(finalI+1).getY() - Path.get(finalI).getY() == -1) {
                            animal.setDirection(Direction.Up);
                        }
                        if (Path.get(finalI+1).getX() - Path.get(finalI).getX() == 1 && Path.get(finalI+1).getY() - Path.get(finalI).getY() == 0) {
                            animal.setDirection(Direction.Right);
                        }
                        if (Path.get(finalI+1).getX() - Path.get(finalI).getX() == -1 && Path.get(finalI+1).getY() - Path.get(finalI).getY() == 0) {
                            animal.setDirection(Direction.Left);
                        }
                        animal.setTimer(0);
                        animal.setPositionX(TEXTURE_SIZE * Path.get(finalI+1).getX());
                        animal.setPositionY(TEXTURE_SIZE * (90 - Path.get(finalI+1).getY()));
                        //animal.setPosition(TEXTURE_SIZE * Path.get(finalI+1).getX(), TEXTURE_SIZE * (90 - Path.get(finalI+1).getY()));
                        //animal.getSprite().setSize(TEXTURE_SIZE, TEXTURE_SIZE);

                        if (finalI == Path.size() - 2) {
                            gameMenu.getShepherdingAnimals().remove(animal);
                            animal.setMoving(false);
                        }
                    }
                },500 * i);

            }
            catch (Exception e) {

            }
        }
    }

    public static void pet(Animal animal) {
        animal.increaseFriendShip(15);
        animal.setPetToday(true);
    }

    public static void effectAfterPetAnimal() {
        for (int i = gameMenu.getHeartAnimations().size() - 1 ; i>=0 ; i--) {
            gameMenu.getHeartAnimations().get(i).update(Gdx.graphics.getDeltaTime());
            if (gameMenu.getHeartAnimations().get(i).isFinished()) {
                gameMenu.getHeartAnimations().remove(i);
            }
        }
        for (HeartAnimation heart : gameMenu.getHeartAnimations()) {
            heart.render(Main.getBatch());
        }
    }

    public static boolean checkTileForAnimalWalking(int x, int y) {
        Tile tile = getTileByCoordinates(x , y );
        if (tile == null) {
            return false;
        }
        if (!(tile.getGameObject() instanceof Walkable)) {
            return false;
        }
        return true;
    }

    public static boolean animalIsOnBarnOrCage(Animal animal) {
        BarnOrCage barnOrCage=null;
        for (BarnOrCage barnOrCage1 : Main.getClient(null).getPlayer().BarnOrCages) {
            for (Animal animal1 : barnOrCage1.animals) {
                if (animal1.equals(animal)) {
                    barnOrCage=barnOrCage1;
                    break;
                }
            }
        }
        assert barnOrCage != null;
        int width=barnOrCage.getBarnORCageType().getWidth();
        int height=barnOrCage.getBarnORCageType().getHeight();

        for (int i= barnOrCage.topLeftX ; i< barnOrCage.topLeftX + width ; i++) {
            for (int j=barnOrCage.topLeftY ; j< barnOrCage.topLeftY + height ; j++) {
                Tile tile=getTileByCoordinates(i,j);
                if (tile.getGameObject().equals(animal)) {
                    return true;
                }
            }
        }
        return false;
    }
    public static void calculateAnimalsFriendship() {
        for (User player : currentGame.getGameState().getPlayers())
            for (BarnOrCage barnOrCage : player.BarnOrCages) {
                for (Animal animal : barnOrCage.animals) {
                    if (! animal.isFeedToday()){
                        animal.increaseFriendShip(- 20);
                    }
                    if (animal.isFeedToday()) {
                        animal.increaseFriendShip(8);
                    }
                    if (! animal.isPetToday()) {
                        animal.increaseFriendShip(- 10 * (animal.getFriendShip()/200));
                    }
                    if ( ! animalIsOnBarnOrCage(animal)) {
                        animal.increaseFriendShip(- 20);
                    }
                    animal.setFeedPreviousDay(animal.isFeedToday());
                    System.out.println(animal.isFeedPreviousDay() + "قبل");
                    animal.setFeedToday(false);
                    System.out.println(animal.isFeedPreviousDay() + "بعد");
                    if (currentGame.getGameState().currentDate.getDate() - animal.getLastProduceDay() == animal.getType().getPeriod()) {
                        animal.setLastProduceDay(currentGame.getGameState().currentDate.clone().getDate());
                    }
                    else if (currentGame.getGameState().currentDate.getDate() - animal.getLastProduceDay() == -28 + animal.getType().getPeriod()) {
                        animal.setLastProduceDay(currentGame.getGameState().currentDate.clone().getDate());
                    }
                    animal.setRandomProduction(Math.random() + 0.5);
                    animal.setRandomQuantity(Math.random());
                    animal.setRandomChance(Math.random());
                    animal.setProductCollected(false);
                }
            }
    }
    public static boolean checkBigProduct(Animal animal) {
        double possibility=( animal.getFriendShip() + (150 * animal.getRandomProduction()) ) / 1500;
        return animal.getRandomChance() < possibility;
    }
    public static void checkAnimalProduct() {
        for (User player : currentGame.getGameState().getPlayers()) {
            for (BarnOrCage barnOrCage : player.BarnOrCages) {
                for (Animal animal : barnOrCage.animals) {
                    if (animal.getType().equals(AnimalType.dino)) {
                        animal.setProductType(AnimalProductType.dinosaurEgg);
                    }
                    if (animal.getType().equals(AnimalType.sheep)) {
                        animal.setProductType(AnimalProductType.sheeps_Wool);
                    }
                    //TODO truffle فراموش نشود
                    if (animal.getFriendShip() < 100 || !checkBigProduct(animal)) {
                        switch (animal.getType()) {
                            case hen -> {
                                animal.setProductType(AnimalProductType.Egg);
                            }
                            case duck -> {
                                animal.setProductType(AnimalProductType.duckEgg);
                            }
                            case rabbit -> {
                                animal.setProductType(AnimalProductType.rabbits_Wool);
                            }
                            case cow -> {
                                animal.setProductType(AnimalProductType.milk);
                            }
                            case goat -> {
                                animal.setProductType(AnimalProductType.goatMilk);
                            }
                        }
                    } else if (animal.getFriendShip() >= 100 && checkBigProduct(animal)) {
                        switch (animal.getType()) {
                            case hen -> {
                                animal.setProductType(AnimalProductType.bigEgg);
                            }
                            case duck -> {
                                animal.setProductType(AnimalProductType.duckFeather);
                            }
                            case rabbit -> {
                                animal.setProductType(AnimalProductType.rabbits_Foot);
                            }
                            case cow -> {
                                animal.setProductType(AnimalProductType.bigMilk);
                            }
                            case goat -> {
                                animal.setProductType(AnimalProductType.bigGoatMilk);
                            }
                        }
                    }
                }
            }
        }
    }
    public static boolean checkPeriod(Animal animal) {
        return currentGame.getGameState().currentDate.getDate() - animal.getLastProduceDay() == 0;
    }

    public static void openArtisanMenu() {
        if (Gdx.input.isKeyJustPressed(Keys.ArtisanMenu)) {
            Texture bg = TextureManager.get("Mohamadreza/ArtisanMenu.png");
            ArtisanMenuUI artisanMenuUI = new ArtisanMenuUI(getSkin() , bg , 1);
            artisanMenuUI.setPosition(100 , 100);
            gameMenu.getStage().addActor(artisanMenuUI);
        }
    }





    // Ario


    public static void unloadAndReward() {
        for (User user : currentGame.getGameState().getPlayers()) {
            Farm farm = user.getFarm();
            for (ShippingBin shippingBin : farm.shippingBins) {
                for (Map.Entry<Items,Integer> entry : shippingBin.binContents.entrySet()) {
                    System.out.println(entry.getKey().getName() + " " + entry.getValue());
                    if (entry.getKey() instanceof Fish) {
                        Fish fish = (Fish) entry.getKey();
                        Main.getClient(null).getPlayer().increaseMoney(fish.getSellPrice() * entry.getValue());
                    }
                    if (entry.getKey() instanceof Animalproduct) {
                        Animalproduct animalproduct = (Animalproduct) entry.getKey();
                        Main.getClient(null).getPlayer().increaseMoney(animalproduct.getSellPrice() * entry.getValue());
                    }
                }
                shippingBin.binContents.clear();
            }
        }
    }


    public static String DisplayFriendships () {
        String targetName = Main.getClient(null).getPlayer().getUsername();

        for (HumanCommunications f : currentGame.friendships) {
            if (f.getPlayer1().getUsername().equals(targetName) || f.getPlayer2().getUsername().equals(targetName))
                return f.printInfo();
        }

        return null;
    }
    public static void cheatAddXp (String input) {
        int xp = Integer.parseInt(GameMenuCommands.addXpCheat.getMatcher(input).group("xp"));
        String otherName = GameMenuCommands.addXpCheat.getMatcher(input).group("other");
        User other = findPlayerInGame(otherName);
        HumanCommunications f = getFriendship(Main.getClient(null).getPlayer(), other);
        f.addXP(xp);
    }
    public static void showChatDialog(Stage stage, Skin skin, Consumer<String> onMessageSent) {

        // تعریف متغیرهای اولیه
        TextField messageField = new TextField("", Main.getNewSkin());
        messageField.setMessageText("Type your message...");
        messageField.setMaxLength(200);

        // دیالوگ به صورت subclass تا متد result را override کنیم
        Dialog chatDialog = new Dialog("Send Message", Main.getNewSkin()) {
            @Override
            protected void result(Object obj) {
                if (obj.equals(true)) {
                    String message = messageField.getText().trim();
                    if (!message.isEmpty()) {
                        onMessageSent.accept(message);
                    }
                }
            }
        };

        // ایموجی‌ها
        Table emojiTable = new Table();
        String[] emojis = {"😊", "😂", "❤️", "😢", "💔", "👍", "🎉"};
        for (String emoji : emojis) {
            TextButton emojiButton = new TextButton(emoji, skin);
            emojiButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    messageField.setText(messageField.getText() + emoji);
                }
            });
            emojiTable.add(emojiButton).pad(3);
        }

        // اضافه کردن به دیالوگ
        chatDialog.getContentTable().add(messageField).width(300).pad(10);
        chatDialog.getContentTable().row();
        chatDialog.getContentTable().add(emojiTable).pad(10);

        // دکمه‌ها + مقدار برگردان
        chatDialog.button("Send", true);   // این مقدار به result داده میشه
        chatDialog.button("Cancel", false);
        chatDialog.key(com.badlogic.gdx.Input.Keys.ENTER, true);

        chatDialog.show(stage);
    }


    public static void talking(String destinationUsername, Consumer<Result> onResult) {
        User destinationUser = null;
        boolean found = false;

        for (User player : currentGame.getGameState().getPlayers()) {
            if (player.getUsername().equals(destinationUsername)) {
                found = true;
                destinationUser = player;
                break;
            }
        }

        if (!found) {
            onResult.accept(new Result(false, "Player is Unavailable!"));
            return;
        }

        if (destinationUsername.equals(Main.getClient(null).getPlayer().getUsername())) {
            onResult.accept(new Result(false, "You can't Talk to Yourself!"));
            return;
        }

        HumanCommunications f = getFriendship(Main.getClient(null).getPlayer(), destinationUser);
        if (f == null) {
            onResult.accept(new Result(false, "No Friendship Among These Users!"));
            return;
        }

        // گرفتن پیام از کاربر و فراخوانی تابع talk
        showChatDialog(GameMenu.gameMenu.getStage(), Main.getNewSkin(), message -> {
            Result result = f.talk(message);
            onResult.accept(result);
        });
    }

    public static void DisplayingTalkHistory (String input) {
        String username = GameMenuCommands.talkHistory.getMatcher(input).group("username");
        User destinationUser = null;
        boolean found = false;
        for (User player: currentGame.getGameState().getPlayers()) {
            if (player.getUsername().equals(username)) {
                found = true;
                destinationUser = player;
                break;
            }
        }
        if (!found) {
            System.out.println(RED+"Username is Unavailable!"+RESET);
            return;
        }
        if (username.equals(Main.getClient(null).getPlayer().getUsername())) {
            System.out.println("You can't Talk to " + RED+"Yourself"+RESET + "!");
            return;
        }
        HumanCommunications f = getFriendship(Main.getClient(null).getPlayer(), destinationUser);
        if (f == null) {
            System.out.println("There's " + RED+"no Friendship"+RESET + " Among these Users");
            return;
        }
        Result result = f.talkingHistory();
        System.out.println(result);
    }
    public static Result hug (String input) {
        String username = GameMenuCommands.hug.getMatcher(input).group("username");
        if (!currentGame.getGameState().getPlayers().contains(findPlayerInGame(username))) {
            return new Result(false,"Username is Unavailable!");
        }
        if (username.equals(Main.getClient(null).getPlayer().getUsername())) {
            return new Result(false, "You can't Hug Yourself!");
        }
        HumanCommunications f = getFriendship(Main.getClient(null).getPlayer(), findPlayerInGame(username));
        if (f == null) {
            return new Result(false, "There's no Friendship Among these Users!");
        }
        return f.Hug();
    }

    public static void sendGifts (String input) {
        String username = GameMenuCommands.sendGift.getMatcher(input).group("username");
        String item = GameMenuCommands.sendGift.getMatcher(input).group("item");
        int amount = Integer.parseInt(GameMenuCommands.sendGift.getMatcher(input).group("amount"));
        if (username == null || item == null) {
            System.out.println("Invalid Command!");
            return;
        }
        if (!currentGame.getGameState().getPlayers().contains(findPlayerInGame(username))) {
            System.out.println(RED+"Username '" + username + "' is Unavailable!"+RESET);
            return;
        }
        if (username.equals(Main.getClient(null).getPlayer().getUsername())) {
            System.out.println("You can't Send Gifts to " + RED+"Yourself"+RESET + "!");
            return;
        }
        HumanCommunications f = getFriendship(Main.getClient(null).getPlayer(), findPlayerInGame(username));
        if (f == null) {
            System.out.println("There's " + RED+"no Friendship"+RESET + " Among these Users");
            return;
        }


        Result result = f.sendGifts(username, item, amount);
        System.out.println(result);
        if (result.IsSuccess()) {
            Set<User> key = new HashSet<>(Arrays.asList(Main.getClient(null).getPlayer(), findPlayerInGame(username)));
            currentGame.conversations.putIfAbsent(key, new ArrayList<>());
            currentGame.conversations.get(key).add(new MessageHandling(Main.getClient(null).getPlayer(), findPlayerInGame(username), Main.getClient(null).getPlayer().getNickname() + " Sent you a GIFT. Rate it out of 5!"));
        }
    }
    public static Result giveFlowers (String username) {
        if (!currentGame.getGameState().getPlayers().contains(findPlayerInGame(username))) {

            return new Result(false, "Username is Unavailable!");
        }
        if (username.equals(Main.getClient(null).getPlayer().getUsername())) {
            return new Result(false, "You can't give Flower to Yourself!");
        }
        HumanCommunications f = getFriendship(Main.getClient(null).getPlayer(), findPlayerInGame(username));
        if (f == null) {
            return new Result(false, "There's no Friendship Among these Users!");
        }
        return f.buyFlowers();
    }
    public static Result propose(String username) {
        User wife = findPlayerInGame(username);
        if (wife == null) {
            return new Result(false, "Username is Unavailable!");
        }
        if (username.equals(Main.getClient(null).getPlayer().getUsername())) {
            return new Result(false, "You can't Propose to Yourself!");
        }
        HumanCommunications f = getFriendship(Main.getClient(null).getPlayer(), wife);
        if (f == null) {
            return new Result(false, "There's no Friendship Among these Users");
        }

        return f.propose();
    }
    public static void unlockRecipe(String input) {
        Matcher matcher = GameMenuCommands.recipeUnlock.getMatcher(input);
        if (matcher == null) {
            System.out.println(RED+"Oops!"+RESET);
            return;
        }
        String foodName = matcher.group("food");
        if (foodName == null) {
            System.out.println(RED+"Oops!"+RESET);
            return;
        }
        Recipe recipe = Recipe.findRecipeByName(foodName);
        if (recipe == null) {
            System.out.println(RED+"Food Name Unavailable!"+RESET);
            return;
        }
        recipe.setUsable(true);
        System.out.println(GREEN+"Done!"+RESET);

    }
    public static Result eatFood (String foodName) {

        Recipe recipe = Recipe.findRecipeByName(foodName);

        FoodTypes type = recipe.getType();

        Inventory myInventory = Main.getClient(null).getPlayer().getBackPack().inventory;
        Items i = new Food(type);
        if (checkAmountProductAvailable(i, 1)) {
            myInventory.Items.put(i, myInventory.Items.get(i) - 1);
            myInventory.Items.entrySet().removeIf(entry -> entry.getValue()==null || entry.getValue() <= 0);
        } else {
            return new Result(false, "None of This Item in Inventory!");
        }
        Main.getClient(null).getPlayer().setHealth(Main.getClient(null).getPlayer().getHealth() + recipe.getEnergy());
        recipe.applyEffect(Main.getClient(null).getPlayer());
        return new Result(true, "Eaten, Successfully.");
    }
    public static void exitGame () {
        if (Main.getClient(null).getPlayer() != currentUser) {
            System.out.println(RED+"Access Denied!"+RESET);
            return;
        }
        //TODO بیشترین امتیاز و... سیو بشه
        //TODO currently in game
        //TODO سیو کل بازی
    }
    public static void forceTerminate () {
        Scanner scanner = new Scanner(System.in);
        User terminator = Main.getClient(null).getPlayer();
        for (User user: currentGame.getGameState().getPlayers()) {
            if (user == terminator)
                continue;
            Main.getClient(null).getPlayer() = user;
            System.out.println(Main.getClient(null).getPlayer().getNickname() + ", Do You Agree With the Game Termination?[Y/N]");
            String choice = scanner.next();
            if (!choice.trim().toLowerCase().equals("y")) {
                System.out.println("Vote Failed! The Game won't be Terminated.");
                Main.getClient(null).getPlayer() = terminator;
                return;
            }
        }

        //TODO  کارهای ترمینیت کردن مثل پاک کردن فایل های سیو و ریست کردن همه دیتاهای بازیکنا بجز ماکسیمم امتیاز
        for (User user: currentGame.getGameState().getPlayers()) {
            user.setCurrently_in_game(false);
            user.setMax_point(user.getPoint());
        }
    }


    // Erfan


    public static void setPlayerLocation () {

        for (User user: currentGame.getGameState().getPlayers()) {
            if (user.getHealth() <= 0) {
                user.setPositionX(user.getSleepTile().getX());
                user.setPositionY(user.getSleepTile().getY());
            }
            else {
                Home home = user.getFarm().getHome();
                user.setPositionX(home.getTopLeftX() + home.getWidth() / 2);
                user.setPositionY(home.getTopLeftY() + home.getLength());
            }
        }
    }
    public static void initializePlayer () {

        User user = Main.getClient(null).getPlayer();

        user.increaseHealth(200);

        user.setFriendshipPoint(new HashMap<>(Map.of(
            NPC.Sebastian, 0,
            NPC.Leah, 0,
            NPC.Abigail, 0,
            NPC.Harvey, 0,
            NPC.Robin, 0)));

        for (NPC npc : NPC.values()) {
            user.setTodayTalking(npc, false);
            user.setTodayGifting(npc, false);
            user.setLevel3Date(npc, currentGame.getGameState().getCurrentDate());
        }
        advanceItem(new Scythe(), 1);
        advanceItem(new Hoe(HoeType.primaryHoe), 1);
        advanceItem(new Axe(AxeType.primaryAxe), 1);
        advanceItem(new PickAxe(PickAxeType.primaryPickAxe), 1);
        advanceItem(new WateringCan(WateringCanType.PrimaryWateringCan), 1);
        advanceItem(new TrashCan(TrashCanType.primaryTrashCan), 1);


        Home home = user.getFarm().getHome();
        user.setPositionX(home.getTopLeftX() + home.getWidth() / 2f);
        user.setPositionY(home.getTopLeftY() + home.getLength());
        user.increaseMoney(500 - user.getMoney());
        advanceItem(new Fish(FishType.Salmon, Quantity.Iridium), 1);
        advanceItem(new Fish(FishType.Salmon, Quantity.Golden), 1);
        advanceItem(new Fish(FishType.Sardine, Quantity.Normal), 1);
        advanceItem(new Wood(), 400);
    }

    public static void updateDarknessLevel(int hour) {
        float darkness = getDarknessLevel(hour);
        Color color = helperBackGround.getColor();
        helperBackGround.setColor(color.r, color.g, color.b, darkness);
    }
    public static float getDarknessLevel(int hour) {
        if (hour <= 18)
            return 0f;
        else if (hour >= 22)
            return 0.6f;
        else
            return (hour - 18) / 8f;
    }

    public static void createScreenOverlay(Stage stage) {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, 1);
        pixmap.fill();

        Texture texture = new Texture(pixmap);
        pixmap.dispose();

        helperBackGround = new Image(new TextureRegionDrawable(new TextureRegion(texture)));
        helperBackGround.setSize(stage.getViewport().getWorldWidth(), stage.getViewport().getWorldHeight());
        helperBackGround.getColor().a = 0f;

        helperBackGround.setTouchable(Touchable.disabled);
        stage.addActor(helperBackGround);
    }
    public static void fadeToNextDay() {

        helperBackGround.toFront();

        helperBackGround.addAction(Actions.sequence(
            Actions.fadeIn(1f),
            Actions.run(() -> {
                startDay();
            }),
            Actions.delay(0.3f),
            Actions.fadeOut(1f)
        ));
    }



    public static void passedOfTime (int day, int hour) {

        if (day == 0) {
            if (Main.getClient(null).getPlayer().Buff_maxEnergy_100_hoursLeft > 0) {
                Main.getClient(null).getPlayer().setBuff_maxEnergy_100_hoursLeft(Main.getClient(null).getPlayer().Buff_maxEnergy_100_hoursLeft - hour);
                if (Main.getClient(null).getPlayer().Buff_maxEnergy_100_hoursLeft < 0)
                    Main.getClient(null).getPlayer().setBuff_maxEnergy_100_hoursLeft(0);
            }
            if (Main.getClient(null).getPlayer().Buff_maxEnergy_50_hoursLeft > 0) {
                Main.getClient(null).getPlayer().setBuff_maxEnergy_50_hoursLeft(Main.getClient(null).getPlayer().Buff_maxEnergy_50_hoursLeft - hour);
                if (Main.getClient(null).getPlayer().Buff_maxEnergy_50_hoursLeft < 0)
                    Main.getClient(null).getPlayer().setBuff_maxEnergy_50_hoursLeft(0);
            }
            if (Main.getClient(null).getPlayer().Buff_farming_hoursLeft > 0) {
                Main.getClient(null).getPlayer().setBuff_farming_hoursLeft(Main.getClient(null).getPlayer().Buff_farming_hoursLeft - hour);
                if (Main.getClient(null).getPlayer().Buff_farming_hoursLeft < 0)
                    Main.getClient(null).getPlayer().setBuff_farming_hoursLeft(0);
            }
            if (Main.getClient(null).getPlayer().Buff_foraging_hoursLeft > 0) {
                Main.getClient(null).getPlayer().setBuff_foraging_hoursLeft(Main.getClient(null).getPlayer().Buff_foraging_hoursLeft - hour);
                if (Main.getClient(null).getPlayer().Buff_foraging_hoursLeft < 0)
                    Main.getClient(null).getPlayer().setBuff_foraging_hoursLeft(0);
            }
            if (Main.getClient(null).getPlayer().Buff_fishing_hoursLeft > 0) {
                Main.getClient(null).getPlayer().setBuff_fishing_hoursLeft(Main.getClient(null).getPlayer().Buff_fishing_hoursLeft - hour);
                if (Main.getClient(null).getPlayer().Buff_fishing_hoursLeft < 0)
                    Main.getClient(null).getPlayer().setBuff_fishing_hoursLeft(0);
            }
            if (Main.getClient(null).getPlayer().Buff_mining_hoursLeft > 0) {
                Main.getClient(null).getPlayer()Main.getClient(null).getPlayer().setBuff_mining_hoursLeft(Main.getClient(null).getPlayer().Buff_mining_hoursLeft - hour);
                if (Main.getClient(null).getPlayer().Buff_mining_hoursLeft < 0)
                    Main.getClient(null).getPlayer().setBuff_mining_hoursLeft(0);
            }




            // Buff implementation
            if (Main.getClient(null).getPlayer().Buff_maxEnergy_100_hoursLeft == 0) Main.getClient(null).getPlayer().setMAX_HEALTH(200);
            if (Main.getClient(null).getPlayer().Buff_maxEnergy_50_hoursLeft == 0) Main.getClient(null).getPlayer().setMAX_HEALTH(200);
            if (Main.getClient(null).getPlayer().Buff_maxEnergy_100_hoursLeft > 0) {
                Main.getClient(null).getPlayer().setMAX_HEALTH(Main.getClient(null).getPlayer().getMAX_HEALTH() + 100);
                Main.getClient(null).getPlayer().setHealth(Main.getClient(null).getPlayer().getHealth() + 100);
                Main.getClient(null).getPlayer().setBuff_maxEnergy_100_hoursLeft(Main.getClient(null).getPlayer().Buff_maxEnergy_100_hoursLeft --);
            }
            if (Main.getClient(null).getPlayer().Buff_maxEnergy_50_hoursLeft > 0) {
                Main.getClient(null).getPlayer().setMAX_HEALTH(Main.getClient(null).getPlayer().getMAX_HEALTH() + 50);
                Main.getClient(null).getPlayer().setHealth(Main.getClient(null).getPlayer().getHealth() + 50);
                Main.getClient(null).getPlayer().setBuff_maxEnergy_50_hoursLeft(Main.getClient(null).getPlayer().Buff_maxEnergy_50_hoursLeft --);
            }
            if (Main.getClient(null).getPlayer().Buff_mining_hoursLeft > 0) Main.getClient(null).getPlayer().setBuff_mining_hoursLeft(Main.getClient(null).getPlayer().Buff_mining_hoursLeft --);
            if (Main.getClient(null).getPlayer().Buff_fishing_hoursLeft > 0) Main.getClient(null).getPlayer().setBuff_fishing_hoursLeft(Main.getClient(null).getPlayer().Buff_fishing_hoursLeft --);
            if (Main.getClient(null).getPlayer().Buff_farming_hoursLeft > 0) Main.getClient(null).getPlayer().setBuff_farming_hoursLeft(Main.getClient(null).getPlayer().Buff_farming_hoursLeft --);
            if (Main.getClient(null).getPlayer().Buff_foraging_hoursLeft > 0) Main.getClient(null).getPlayer().setBuff_foraging_hoursLeft(Main.getClient(null).getPlayer().Buff_foraging_hoursLeft --);

        }
        else
            Main.getClient(null).getPlayer().setBuff_maxEnergy_100_hoursLeft(0);

        DateHour dateHour = currentGame.getGameState().currentDate.clone();

        dateHour.increaseHour(hour);
        dateHour.increaseDay(day);

        if (dateHour.getHour() > 22) {
            passedOfTime(getDayDifferent(dateHour, currentGame.getGameState().currentDate), 24 - dateHour.getHour() + 9 + hour);
            return;
        }
        if (dateHour.getHour() < 9) {
            passedOfTime(getDayDifferent(dateHour, currentGame.getGameState().currentDate), 9 - dateHour.getHour() + hour);
            return;
        }
        int number = getDayDifferent(currentGame.getGameState().currentDate, dateHour);

        for (int i = 0 ; i < number ; i++) {
            currentGame.getGameState().currentDate.increaseDay(1);
            fadeToNextDay();
        }
        currentGame.getGameState().currentDate.increaseHour(dateHour.getHour() - currentGame.getGameState().currentDate.getHour());
        updateDarknessLevel(currentGame.getGameState().currentDate.getHour());
    }

    public static void startDay () {



        doSeasonAutomaticTask();
        setPlayerLocation();
        setEnergyInMorning();
        createRandomForaging();
        createRandomMinerals();
        NPAutomateTask();
        unloadAndReward();
        calculateAnimalsFriendship();
        checkAnimalProduct();

        for (Tile tile : currentGame.bigMap)
            tile.getGameObject().startDayAutomaticTask();

        doWeatherTask();
        crowAttack(); // قبل محصول دادن درخت باید باشه



    }

    public static void AutomaticFunctionAfterOneTurn () {

        for (Tile tile : currentGame.bigMap)
            tile.getGameObject().turnByTurnAutomaticTask();
    }
    public static void AutomaticFunctionAfterAnyAct () {

        checkForGiant();
        checkForProtect();

        for (User user : currentGame.getGameState().getPlayers()) {
            user.checkHealth();

            for (NPC npc : NPC.values())
                if (user.getFriendshipLevel(npc) == 3 && user.getLevel3Date(npc) == currentGame.getGameState().currentDate)
                    user.setLevel3Date(npc, currentGame.getGameState().currentDate.clone());
        }


//        if (checkForDeath()) {
//            Main.getClient(null).getPlayer().setSleepTile(
//                getTileByCoordinates(Main.getClient(null).getPlayer().getPositionX(),
//                    Main.getClient(null).getPlayer().getPositionY()));
//            return new Result(false, BRIGHT_RED + "No energy left! It's the next player's turn" + RESET);
//        }

    }

    // energy & Date
    public static void setEnergyInMorning () {
        for (User user : currentGame.getGameState().getPlayers()) {
            if (user.getDaysDepressedLeft() == 0) {
                if (user.getHealth() > 0)
                    user.setHealth(user.getMAX_HEALTH());
                else
                    user.setHealth((user.getMAX_HEALTH() * 3) / 4);
            }
            else {
                user.setDaysDepressedLeft(user.getDaysDepressedLeft() - 1);
                if (user.getHealth() > 0)
                    user.setHealth((user.getMAX_HEALTH()) / 2);
                else
                    user.setHealth((user.getMAX_HEALTH() * 3) / 8);
            }
        }
    }
    public static void setTimeAndWeather () {

        currentGame.getGameState().currentDate = new DateHour(Season.Spring, 1, 9, 1980);
        currentGame.currentWeather = Weather.Sunny;
        currentGame.tomorrowWeather = Weather.Sunny;

    }
    public static void doSeasonAutomaticTask () {

        currentGame.currentWeather = Weather.valueOf(currentGame.tomorrowWeather.toString());
        currentGame.tomorrowWeather = currentGame.getGameState().currentDate.getSeason().getWeather();

    }
    public static void doWeatherTask () {

        if (currentGame.currentWeather.equals(Weather.Rainy) || currentGame.currentWeather.equals(Weather.Stormy))
            for (Tile tile : currentGame.bigMap) {
                GameObject object = tile.getGameObject();

                if (object instanceof Tree && !isInGreenHouse(tile))
                    ((Tree) object).setLastWater(currentGame.getGameState().currentDate);
                if (object instanceof GiantProduct && !isInGreenHouse(tile))
                    ((GiantProduct) object).setLastWater(currentGame.getGameState().currentDate);
                if (object instanceof ForagingSeeds && !isInGreenHouse(tile))
                    ((ForagingSeeds) object).setLastWater(currentGame.getGameState().currentDate);
            }
    }

    // Automatic Plant task
    public static void    crowAttack () {

        for (Farm farm : currentGame.farms) {

            int number = 0;
            for (Tile tile : farm.Farm) {

                GameObject object = tile.getGameObject();

                if (object instanceof Tree ||
                    object instanceof ForagingSeeds ||
                    object instanceof GiantProduct ||
                    object instanceof ForagingCrops) {

                    number++;

                    if (number % 16 == 0) {

                        double x = Math.random();
                        if (x <= 0.25) {

                            if (isInGreenHouse(tile)) {
                                continue;
                            }
                            else if (object instanceof Tree && !((Tree) object).isProtected())
                                ((Tree) object).setLastFruit(currentGame.getGameState().currentDate);

                            else if (object instanceof ForagingCrops && !((ForagingCrops) object).isProtected())
                                ((ForagingCrops) object).delete();

                            else if (object instanceof ForagingSeeds && !((ForagingSeeds) object).isProtected()) {
                                if (((ForagingSeeds) object).getType().isOneTimeUse())
                                    ((ForagingSeeds) object).delete();
                                else
                                    ((ForagingSeeds) object).setLastProduct(currentGame.getGameState().currentDate);
                            } else if (object instanceof GiantProduct && !((GiantProduct) object).isProtected())
                                ((GiantProduct) object).delete();
                        }
                    }
                }
            }
        }
    }
    public static void    checkForGiant () {

        for (Tile tile1 : currentGame.bigMap)
            if (tile1.getGameObject() instanceof ForagingSeeds)
                if (((ForagingSeeds) tile1.getGameObject()).getType().canGrowGiant() && !isInGreenHouse(tile1)) {

                    int i = tile1.getX();
                    int j = tile1.getY();

                    ForagingSeedsType type = ((ForagingSeeds) tile1.getGameObject()).getType();
                    Tile tile2 = getTileByCoordinates(i+1, j);
                    Tile tile3 = getTileByCoordinates(i, j+1);
                    Tile tile4 = getTileByCoordinates(i+1, j+1);

                    if (tile2.getGameObject() instanceof ForagingSeeds &&
                        tile3.getGameObject() instanceof ForagingSeeds &&
                        tile4.getGameObject() instanceof ForagingSeeds)

                        if ((((ForagingSeeds) tile2.getGameObject()).getType() == type) &&
                            (((ForagingSeeds) tile3.getGameObject()).getType() == type) &&
                            (((ForagingSeeds) tile4.getGameObject()).getType() == type)) {

                            GiantProduct giantProduct = new GiantProduct(
                                type, ((ForagingSeeds) tile1.getGameObject()).getBirthDay(),
                                new ArrayList<>(List.of(tile2, tile3, tile4)));

                            tile1.setGameObject(giantProduct);
                            tile2.setGameObject(giantProduct);
                            tile3.setGameObject(giantProduct);
                            tile4.setGameObject(giantProduct);
                        }
                }
    }
    public static void    checkForProtect() {

        for (Tile tile : currentGame.bigMap){

            GameObject object1 = tile.getGameObject();
            if (object1 instanceof Tree)
                ((Tree) object1).setProtected(false);

            if (object1 instanceof ForagingSeeds)
                ((ForagingSeeds) object1).setProtected(false);

            if (object1 instanceof GiantProduct)
                ((GiantProduct) object1).setProtected(false);

            if (object1 instanceof ForagingCrops)
                ((ForagingCrops) object1).setProtected(false);
        }

        for (Tile tile : currentGame.bigMap) {

            GameObject object = tile.getGameObject();

            if (object instanceof CraftingItem &&
                (( ((CraftingItem) object).getType().equals(CraftType.Scarecrow)) ||
                    ((CraftingItem) object).getType().equals(CraftType.DeluxeScarecrow) )) {

                int r = 12;
                if (((CraftingItem) object).getType().equals(CraftType.Scarecrow))
                    r = 8;

                int x = tile.getX();
                int y = tile.getY();

                for (int i = Math.min(x - (r / 2), 1); i < x + r; i++)
                    for (int j = Math.min(y - (r / 2), 1); j < y + r; j++)
                        if ((i - x) * (i - x) + (j - y) * (j - y) <= r * r) {

                            Tile tile2 = getTileByCoordinates(i, j);
                            GameObject object1 = tile2.getGameObject();

                            if (object1 instanceof Tree)
                                ((Tree) object1).setProtected(true);

                            if (object1 instanceof ForagingSeeds)
                                ((ForagingSeeds) object1).setProtected(true);

                            if (object1 instanceof GiantProduct)
                                ((GiantProduct) object1).setProtected(true);

                            if (object1 instanceof ForagingCrops)
                                ((ForagingCrops) object1).setProtected(true);
                        }
            }
        }
    }
    public static boolean checkForDeath () {

        return (Main.getClient(null).getPlayer().getHealth() <= 0 && !Main.getClient(null).getPlayer().isHealthUnlimited());
    }


    public static void    lightningStrike (Tile selected) {

        GameObject object = selected.getGameObject();

        if (object instanceof Tree)
            selected.setGameObject(new ForagingMinerals(COAL));
        else if (object instanceof ForagingSeeds)
            selected.setGameObject(new Walkable());
        else if (object instanceof Animal)
            selected.setGameObject(new Walkable());

    }
    public static Tile    selectTileForThor (Farm farm) {

        List<Tile> matchingTiles = farm.Farm.stream()
            .filter(tile -> (tile.getGameObject() instanceof Tree ||
                tile.getGameObject() instanceof ForagingSeeds) &&
                !farm.isInGreenHouse(tile.getX(), tile.getY())) .toList();

        if (matchingTiles.isEmpty())
            return null;

        Random random = new Random();
        return matchingTiles.get(random.nextInt(matchingTiles.size()));

    }
    public static boolean checkInAllFarm (int x, int y) {

        for (User user : currentGame.getGameState().getPlayers())
            if (user.getFarm().isInFarm(x, y))
                return true;
        return false;
    }
    public static boolean canGrowGrass (Tile tile) {

        int x = tile.getX();
        int y = tile.getY();

        if (!checkInAllFarm(tile.getX(), tile.getY()))
            return false;

        for (User user : currentGame.getGameState().getPlayers())
            if (user.getFarm().isInHome(x, y) || user.getFarm().isInMine(x, y) || user.getFarm().isInGreenHouse(x, y))
                return false;

        return true;
    }
    public static void    createRandomForaging () {

        for (Tile tile : currentGame.bigMap) {

            if (tile.getGameObject() instanceof Walkable &&
                ((Walkable) tile.getGameObject()).getGrassOrFiber().equals("Plowed") && Math.random() <= 0.2) {
                if (Math.random() <= 0.5) {

                    List<ForagingSeedsType> types = Arrays.stream(ForagingSeedsType.values())
                        .filter(d -> d.getSeason().contains(currentGame.getGameState().currentDate.getSeason()))
                        .toList();

                    ForagingSeedsType type = types.get(rand.nextInt(types.size()));

                    tile.setGameObject(new ForagingSeeds(type, currentGame.getGameState().currentDate));
                } else {

                    List<ForagingCropsType> types = new ArrayList<>(Arrays.stream(ForagingCropsType.values())
                        .filter(d -> d.getSeason().contains(currentGame.getGameState().currentDate.getSeason()))
                        .toList());

                    types.remove(ForagingCropsType.Fiber);
                    ForagingCropsType type = types.get(rand.nextInt(types.size()));

                    ForagingCrops crop = new ForagingCrops(type);
                    tile.setGameObject(crop);
                }
            }

            else if (tile.getGameObject() instanceof Walkable &&
                ((Walkable) tile.getGameObject()).getGrassOrFiber().equals("Walk") &&
                canGrowGrass(tile) && Math.random() <= 0.1) {

                if (Math.random() <= 0.5)
                    ((Walkable) tile.getGameObject()).setGrassOrFiber("Fiber");
                else
                    ((Walkable) tile.getGameObject()).setGrassOrFiber("Grass");
            }
        }
    }
    public static void    createRandomMinerals () {
        for (User user : currentGame.getGameState().getPlayers()) {

            List<Integer> positions = new ArrayList<>();
            for (int i = 0 ; i < 16 ; i++) {
                positions.add(i);
            }

            Collections.shuffle(positions);

            List<ForagingMineralsType> minerals = Arrays.asList(
                RUBY, COAL, IRON, TOPAZ, GOLD, JADE, IRIDIUM,
                QUARTZ, EMERALD, COPPER, DIAMOND, AMETHYST,
                AQUAMARINE, FROZEN_TEAR, FIRE_QUARTZ,
                PRISMATIC_SHARD, EARTH_CRYSTAL
            );

            int posIndex = 0;
            for (ForagingMineralsType mineral : minerals) {
                while (posIndex < positions.size()) {
                    Point point = new Point(
                        user.getFarm().getMine().getPositions().get(positions.get(posIndex)));

                    if (user.getFarm().getMine().checkPositionForMineral(point)) {
                        if (Math.random() <= mineral.getProbability()) {
                            if (user.equals(Main.getClient(null).getPlayer())) {
                                System.out.println("jdsfkfhbsk");
                            }
                            ForagingMinerals f = new ForagingMinerals(mineral);
                            f.setPosition(point);
                            user.getFarm().getMine().getForagingMinerals().add(f);
                            user.getFarm().getMine().getTaken().add(point);
                            break;
                        }
                    }

                    posIndex ++;

                }
            }
        }
    }
    public static boolean checkTileForPlant (Tile tile) {

        GameObject object = tile.getGameObject();

        if (object instanceof Tree)
            return true;
        if (object instanceof GiantProduct)
            return true;

        return object instanceof ForagingSeeds;
    } // محصولای خودرو حساب نیستن
    public static void    fertilizePlant (MarketItemType fertilizeType , Tile tile) {

        GameObject gameObject = tile.getGameObject();

        if (gameObject instanceof GiantProduct)
            ((GiantProduct) gameObject).setFertilize(fertilizeType);
        if (gameObject instanceof Tree)
            ((Tree) gameObject).setFertilize(fertilizeType);
        if (gameObject instanceof ForagingSeeds)
            ((ForagingSeeds) gameObject).setFertilize(fertilizeType);

    }

    // other plant task

    public static boolean checkForPlanting (int dir) {

        Tile tile = getTileByDir(dir);

        if ((!Main.getClient(null).getPlayer().getFarm().isInFarm(tile.getX(), tile.getY())) &&
            !Main.getClient(null).getPlayer().getSpouse().getFarm().isInFarm(tile.getX(), tile.getY())) {

            Dialog dialog = Marketing.getInstance().createDialogError();
            final Label tooltipLabel = new Label("You can't planting in this tile", Main.getNewSkin());
            tooltipLabel.setColor(Color.LIGHT_GRAY);

            Marketing.getInstance().addDialogToTable(dialog, tooltipLabel, GameMenu.getInstance());
            com.badlogic.gdx.utils.Timer.schedule(new com.badlogic.gdx.utils.Timer.Task() {
                @Override
                public void run() {
                    dialog.remove();
                }
            }, 3);
            return false;
        }
        return true;
    }
    public static String showTree (Tree tree) {


        return "name : " + tree.getType().getDisplayName() +
            "\nLast Water : " + BLUE + "Date : " + RED + tree.getLastWater().getYear() +
            RESET + " " + tree.getLastWater().getNameSeason() +
            " " + tree.getLastWater().getDate() +
            "\nLast Fruit : " + BLUE + "Date : " + RED + tree.getLastFruit().getYear() +
            RESET + " " + tree.getLastFruit().getNameSeason() +
            " " + tree.getLastFruit().getDate() +
            "\nToday fertilize :" + tree.isFertilize() +
            "\nStage :" + tree.getStage() +
            "\nHave fruit :" + tree.isHaveFruit();
    }
    public static String showForaging (ForagingSeeds foragingSeeds) {

        return "name : " + foragingSeeds.getType().getDisplayName() +
            "\nLast Water : " + BLUE + "Date : " + RED + foragingSeeds.getLastWater().getYear() +
            RESET + " " + foragingSeeds.getLastWater().getNameSeason() +
            " " + foragingSeeds.getLastWater().getDate() +
            "\nToday fertilize :" + foragingSeeds.isTodayFertilize() +
            "\nStage :" + foragingSeeds.getStage() +
            "\nOne Time :" + foragingSeeds.getType().isOneTimeUse() +
            "\nCan grow giant :" + foragingSeeds.getType().canGrowGiant();

    }
    public static String showGiant (GiantProduct giantProduct) {

        return "name : " + giantProduct.getType().getDisplayName() +
            "\nLast Water : " + BLUE + "Date : " + RED + giantProduct.getLastWater().getYear() +
            RESET + " " + giantProduct.getLastWater().getNameSeason() +
            " " + giantProduct.getLastWater().getDate() +
            "\nToday fertilize :" + giantProduct.isTodayFertilize() +
            "\nStage :" + giantProduct.getStage();
    }
    public static void plantTree (TreesSourceType type1, int dir) {

        if (checkForPlanting(dir)) {

            Dialog dialog = Marketing.getInstance().createDialogError();
            Tile tile = getTileByDir(dir);

            if (!isInGreenHouse(tile))
                if (!type1.getSeason().contains(currentGame.getGameState().currentDate.getSeason())) {

                    Label tooltipLabel = new Label("You can't plant this tree in "
                        + currentGame.getGameState().currentDate.getSeason(), Main.getNewSkin());
                    tooltipLabel.setColor(Color.LIGHT_GRAY);
                    Marketing.getInstance().addDialogToTable(dialog, tooltipLabel, GameMenu.getInstance());
                }

            GameObject object = tile.getGameObject();
            if (object instanceof GreenHouse && !((GreenHouse) object).isCreated()) {
                Label tooltipLabel = new Label("First you must create green House", Main.getNewSkin());
                tooltipLabel.setColor(Color.LIGHT_GRAY);
                Marketing.getInstance().addDialogToTable(dialog, tooltipLabel, GameMenu.getInstance());
            }

            if ((tile.getGameObject() instanceof Walkable &&
                ((Walkable) tile.getGameObject()).getGrassOrFiber().equals("Plowed")) ||
                tile.getGameObject() instanceof GreenHouse) {

                Tree tree = new Tree(type1.getTreeType(), currentGame.getGameState().currentDate);
                tile.setGameObject(tree);
                advanceItem(new TreeSource(type1), -1);

                Label tooltipLabel = new Label("The tree begins its journey", Main.getNewSkin());
                tooltipLabel.setColor(Color.LIGHT_GRAY);
                Marketing.getInstance().addDialogToTable(dialog, tooltipLabel, GameMenu.getInstance());

            } else {
                Label tooltipLabel = new Label("First, you must plow the tile", Main.getNewSkin());
                tooltipLabel.setColor(Color.LIGHT_GRAY);
                Marketing.getInstance().addDialogToTable(dialog, tooltipLabel, GameMenu.getInstance());
            }

            com.badlogic.gdx.utils.Timer.schedule(new com.badlogic.gdx.utils.Timer.Task() {
                @Override
                public void run() {
                    dialog.remove();
                }
            }, 3);
        }
    }
    public static void plantMixedSeed (int dir) {

        if (checkForPlanting(dir)) {
            Inventory inventory = Main.getClient(null).getPlayer().getBackPack().inventory;
            MixedSeeds mixedSeeds = new MixedSeeds();

            if (inventory.Items.containsKey(mixedSeeds)) {

                ForagingSeedsType type = mixedSeeds.getSeeds(currentGame.getGameState().currentDate.getSeason());
                advanceItem(mixedSeeds, -1);
                Tile tile = getTileByDir(dir);

                GameObject object = tile.getGameObject();
                Dialog dialog = Marketing.getInstance().createDialogError();

                if (object instanceof GreenHouse && !((GreenHouse) object).isCreated()) {

                    Label tooltipLabel = new Label("First you must create green House", Main.getNewSkin());
                    tooltipLabel.setColor(Color.LIGHT_GRAY);
                    Marketing.getInstance().addDialogToTable(dialog, tooltipLabel, GameMenu.getInstance());
                }

                if ((tile.getGameObject() instanceof Walkable &&
                    ((Walkable) tile.getGameObject()).getGrassOrFiber().equals("Plowed")) ||
                    tile.getGameObject() instanceof GreenHouse) {

                    tile.setGameObject(new ForagingSeeds(type, currentGame.getGameState().currentDate));

                    Label tooltipLabel = new Label("The plant \" + type.getDisplayName() + \" has come to life! \\uD83C\\uDF31✨", Main.getNewSkin());
                    tooltipLabel.setColor(Color.LIGHT_GRAY);
                    Marketing.getInstance().addDialogToTable(dialog, tooltipLabel, GameMenu.getInstance());

                } else {
                    Label tooltipLabel = new Label("First, you must plow the tile.", Main.getNewSkin());
                    tooltipLabel.setColor(Color.LIGHT_GRAY);
                    Marketing.getInstance().addDialogToTable(dialog, tooltipLabel, GameMenu.getInstance());
                }

                com.badlogic.gdx.utils.Timer.schedule(new com.badlogic.gdx.utils.Timer.Task() {
                    @Override
                    public void run() {
                        dialog.remove();
                    }
                }, 3);
            }
        }
    }
    public static void plantForagingSeed (ForagingSeedsType type, int dir) {

        if (checkForPlanting(dir)) {

            Inventory inventory = Main.getClient(null).getPlayer().getBackPack().inventory;
            Dialog dialog = Marketing.getInstance().createDialogError();

            for (Map.Entry<Items, Integer> entry : inventory.Items.entrySet())

                if (entry.getKey() instanceof ForagingSeeds && ((ForagingSeeds) entry.getKey()).getType().equals(type)) {
                    if (entry.getValue() > 0) {

                        Tile tile = getTileByDir(dir);

                        if (!isInGreenHouse(tile))
                            if (!type.getSeason().contains(currentGame.getGameState().currentDate.getSeason())) {
                                Label tooltipLabel = new Label("You can't plant this seed in "
                                    + currentGame.getGameState().currentDate.getSeason(), Main.getNewSkin());
                                tooltipLabel.setColor(Color.LIGHT_GRAY);
                                Marketing.getInstance().addDialogToTable(dialog, tooltipLabel, GameMenu.getInstance());
                            }

                        GameObject object = tile.getGameObject();
                        if (object instanceof GreenHouse && !((GreenHouse) object).isCreated()) {
                            Label tooltipLabel = new Label("First you must create green House", Main.getNewSkin());
                            tooltipLabel.setColor(Color.LIGHT_GRAY);
                            Marketing.getInstance().addDialogToTable(dialog, tooltipLabel, GameMenu.getInstance());
                        }

                        if (tile.getGameObject() instanceof Walkable && (!((Walkable) tile.getGameObject()).getGrassOrFiber().equals("Plowed"))) {
                            Label tooltipLabel = new Label("First, you must plow the tile.", Main.getNewSkin());
                            tooltipLabel.setColor(Color.LIGHT_GRAY);
                            Marketing.getInstance().addDialogToTable(dialog, tooltipLabel, GameMenu.getInstance());
                        }
                        if ((tile.getGameObject() instanceof Walkable &&
                            ((Walkable) tile.getGameObject()).getGrassOrFiber().equals("Plowed")) ||
                            tile.getGameObject() instanceof GreenHouse) {

                            tile.setGameObject(new ForagingSeeds(type, currentGame.getGameState().currentDate));
                            inventory.Items.put(entry.getKey(), entry.getValue() - 1);

                            Label tooltipLabel = new Label("The earth welcomes your seed", Main.getNewSkin());
                            tooltipLabel.setColor(Color.LIGHT_GRAY);
                            Marketing.getInstance().addDialogToTable(dialog, tooltipLabel, GameMenu.getInstance());

                        } else {
                            Label tooltipLabel = new Label("You can't plant in this tile", Main.getNewSkin());
                            tooltipLabel.setColor(Color.LIGHT_GRAY);
                            Marketing.getInstance().addDialogToTable(dialog, tooltipLabel, GameMenu.getInstance());
                        }
                    }
                }
            com.badlogic.gdx.utils.Timer.schedule(new com.badlogic.gdx.utils.Timer.Task() {
                @Override
                public void run() {
                    dialog.remove();
                }
            }, 3);
        }
    }

    // Tools
    public static Result useHoe (int dir) {
        plow();

        Tile tile = getTileByDir(dir);

        if (tile.getGameObject() instanceof Walkable &&
            ((Walkable) tile.getGameObject()).getGrassOrFiber().equals("Plowed"))
            return new Result(false, RED+"This tile is already plowed!"+RESET);
        if (!(tile.getGameObject() instanceof Walkable))
            return new Result(false, RED+"You can't plow this tile!"+RESET);

        ((Walkable) tile.getGameObject()).setGrassOrFiber("Plowed");
        System.out.println("TEST");
        return new Result(true, BLUE+"Tile("+tile.getX()+","+tile.getY()+") Plowed!"+RESET);
    }
    public static Result useWateringCan (int dir) {

        Tile tile = getTileByDir(dir);
        GameObject object = tile.getGameObject();


        if (object instanceof Lake || object instanceof Well) {
            ((WateringCan) Main.getClient(null).getPlayer().currentTool).makeFullWater();
            return new Result(true, BLUE+"The Watering can is now full. Time to water" +
                " those plants!\uD83D\uDEB0"+RESET);
        }
        else if (object instanceof WaterTank) {

            int amount = ((WaterTank) object).getAmount();
            WateringCan wateringCan = (WateringCan) Main.getClient(null).getPlayer().currentTool;

            if (amount > wateringCan.getType().getCapacity() - wateringCan.getReminderCapacity()) {

                int remine = wateringCan.getType().getCapacity() - wateringCan.getReminderCapacity();
                wateringCan.makeFullWater();
                ((WaterTank) object).increaseAmount(-remine);
                return new Result(true, BLUE+"The Watering can is now full. Time to water" +
                    " those plants!\uD83D\uDEB0"+RESET);
            } else {
                wateringCan.increaseReminderCapacity(amount);
                ((WaterTank) object).increaseAmount(-amount);
                return new Result(true, BLUE+"The Watering can amount water +"+amount+". Time to water" +
                    " those plants!\uD83D\uDEB0"+RESET);
            }
        }
        else
            return new Result(false, RED+"This place is bone dry.\uD83C\uDF35"+RESET);
    }
    public static Result useScythe (int dir) {


        Tile tile = getTileByDir(dir);
        GameObject object = tile.getGameObject();

        if (object instanceof Walkable) {
            if (((Walkable) object).getGrassOrFiber().equals("Fiber")) {

                ((Walkable) object).setGrassOrFiber("Walk");
                advanceItem(new ForagingCrops(ForagingCropsType.Fiber), 1);
                return new Result(true, BRIGHT_BLUE + "You got 1 fiber"+RESET);
            }
            if (((Walkable) object).getGrassOrFiber().equals("Grass")) {
                ((Walkable) object).setGrassOrFiber("Walk");
                return new Result(true, BRIGHT_BLUE + "You pulled out the grass" + RESET);
            }
        }
        if (object instanceof Tree) {

            Main.getClient(null).getPlayer().increaseFarmingAbility(10);
            if (((Tree) object).isHaveFruit()) {

                TreeType type = ((Tree) object).getType();

                if (Main.getClient(null).getPlayer().getBackPack().getType().getRemindCapacity() <= 0 &&
                    (!checkAmountProductAvailable(new TreesProdct(type.getProductType()), 0)))

                    return new Result(false, RED+"Inventory is full"+RESET);

                advanceItem(new TreesProdct(type.getProductType()), type.getHarvestYield());

                Main.getClient(null).getPlayer().increaseFarmingAbility(5);
                ((Tree) object).setLastFruit(currentGame.getGameState().currentDate.clone());
                return new Result(true, BLUE + "You got " + type.getHarvestYield()
                    + " " + type.getProductType().getDisplayName() + RESET);
            } else
                return new Result(true, RED + "This tree doesn't have fruit" + RESET);
        }
        if (object instanceof ForagingCrops) {

            Main.getClient(null).getPlayer().increaseFarmingAbility(10);
            ForagingCropsType type = ((ForagingCrops) object).getType();

            if (Main.getClient(null).getPlayer().getBackPack().getType().getRemindCapacity() <= 0 &&
                (!checkAmountProductAvailable(new ForagingCrops(type), 0)))


                return new Result(false, RED+"Inventory is full"+RESET);

            advanceItem(new ForagingCrops(((ForagingCrops) object).getType()), 1);
            ((ForagingCrops) object).delete();
            Main.getClient(null).getPlayer().increaseFarmingAbility(5);

            return new Result(true, BLUE+"You got 1 of "+
                BRIGHT_PURPLE + type.getDisplayName()+RESET);
        }
        if (object instanceof ForagingSeeds) {

            Main.getClient(null).getPlayer().increaseFarmingAbility(10);
            if (((ForagingSeeds) object).isHaveProduct()) {

                ForagingSeedsType type = ((ForagingSeeds) object).getType();

                if (Main.getClient(null).getPlayer().getBackPack().getType().getRemindCapacity() <= 0 &&
                    (!checkAmountProductAvailable(new AllCrops(type.getProductType()), 0)))

                    return new Result(false, RED+"Inventory is full"+RESET);

                advanceItem(new AllCrops(type.getProductType()), 1);
                ((ForagingSeeds) object).harvest();
                Main.getClient(null).getPlayer().increaseFarmingAbility(5);
                return new Result(true, BLUE + "You got 1 " + type.getProductType().getDisplayName() + RESET);
            } else
                return new Result(false, RED + "Still growing..." + RESET);
        }
        if (object instanceof GiantProduct) {

            Main.getClient(null).getPlayer().increaseFarmingAbility(10);
            if (((GiantProduct) object).isHaveProduct()) {

                ForagingSeedsType type = ((GiantProduct) object).getType();

                if (Main.getClient(null).getPlayer().getBackPack().getType().getRemindCapacity() <= 0 &&
                    (!checkAmountProductAvailable(new AllCrops(type.getProductType()), 0)))


                    return new Result(false, RED+"Inventory is full"+RESET);

                advanceItem(new AllCrops(type.getProductType()), 10);
                ((GiantProduct) object).harvest();
                Main.getClient(null).getPlayer().increaseFarmingAbility(5);
                return new Result(true, BLUE + "You got 10 " + type.getProductType().getDisplayName() + RESET);
            } else
                return new Result(false, RED + "Still growing..." + RESET);
        }

        return new Result(false, RED+"There are no plant!"+RESET);
    }
    public static Result useAxe (int dir) {

        Tile tile = getTileByDir(dir);
        GameObject object = tile.getGameObject();

        if (object instanceof Tree) {

            tile.setGameObject(new Walkable());

            if (checkAmountProductAvailable(new Wood(), 1) ||
                Main.getClient(null).getPlayer().getBackPack().getType().getRemindCapacity() > 0) {

                if (((Tree) object).getType().equals(TreeType.MapleTree) ||
                    ((Tree) object).getType().equals(TreeType.MysticTree)) {

                    if (checkAmountProductAvailable(
                        new TreesProdct(((Tree) object).getType().getProductType()), 1) ||
                        Main.getClient(null).getPlayer().getBackPack().getType().getRemindCapacity() > 1) {

                        advanceItem(new Wood(), 5);
                        advanceItem(new TreesProdct(((Tree) object).getType().getProductType()), 1);

                        TreesSourceType sourceType = TreesSourceType.fromDisplayName(((Tree) object).getType().getSourceName());
                        if (checkAmountProductAvailable(new TreeSource(sourceType), 1) ||
                            Main.getClient(null).getPlayer().getBackPack().getType().getRemindCapacity() > 1) {
                            advanceItem(new TreeSource(sourceType), 2);
                            return new Result(true, BRIGHT_BLUE + "+5 wood  +1 " +
                                ((Tree) object).getType().getProductType().getDisplayName() +
                                "  +2 " + sourceType.getDisplayName() + RESET);
                        }
                        return new Result(false, BRIGHT_BLUE + "+5 wood  +1 " +
                            ((Tree) object).getType().getProductType().getDisplayName() + RESET);
                    }

                    advanceItem(new Wood(), 5);
                    return new Result(false, BRIGHT_BLUE + "+5 wood (you destroy " +
                        ((Tree) object).getType().getProductType().getDisplayName() + "!" + RESET);
                }
                else {

                    advanceItem(new Wood(), 5);

                    TreesSourceType sourceType = TreesSourceType.fromDisplayName(((Tree) object).getType().getSourceName());
                    if (checkAmountProductAvailable(new TreeSource(sourceType), 1) ||
                        Main.getClient(null).getPlayer().getBackPack().getType().getRemindCapacity() > 1) {
                        advanceItem(new TreeSource(sourceType), 2);
                        return new Result(true, BRIGHT_BLUE + "+5 wood  +1 " +
                            ((Tree) object).getType().getProductType().getDisplayName() +
                            "  +2 " + sourceType.getDisplayName() + RESET);
                    }
                    return new Result(false, BRIGHT_BLUE + "+5 wood  +1 " +
                        ((Tree) object).getType().getProductType().getDisplayName() + RESET);
                }
            }
            else {
                tile.setGameObject(new Wood());
                return new Result(false, RED+"Inventory is full"+RESET);
            }
        }
        return new Result(false, RED+"There are no Tree!"+RESET);
    }
    public static Result usePickAxe (int dir) {


        Tile tile = getTileByDir(dir);
        GameObject object = tile.getGameObject();

        if (object instanceof ForagingMinerals) {

            if (Main.getClient(null).getPlayer().currentTool.healthCost() > 0 && Main.getClient(null).getPlayer().Buff_mining_hoursLeft > 0)
                Main.getClient(null).getPlayer().increaseHealth(1);

            tile.setGameObject(new Walkable());
            Main.getClient(null).getPlayer().increaseMiningAbility(10);

            if (((ForagingMinerals) object).getType().equals(COPPER)) {

                int x = 1;
                if (Main.getClient(null).getPlayer().getLevelMining() >= 2)
                    x = 2;

                if (checkAmountProductAvailable(new BarsAndOres(BarsAndOreType.CopperOre), x) ||
                    Main.getClient(null).getPlayer().getBackPack().getType().getRemindCapacity() > 0) {

                    advanceItem(new BarsAndOres(BarsAndOreType.CopperOre), x);
                    return new Result(false, BRIGHT_BLUE + x+" Cooper ore added" + RESET);
                }
                else
                    return new Result(false, RED+"Ops!!! you destroy Cooper ore" + RESET);
            }
            if (((ForagingMinerals) object).getType().equals(GOLD)) {

                int x = 1;
                if (Main.getClient(null).getPlayer().getLevelMining() >= 2)
                    x = 2;

                if (checkAmountProductAvailable(new BarsAndOres(BarsAndOreType.GoldOre), x) ||
                    Main.getClient(null).getPlayer().getBackPack().getType().getRemindCapacity() > 0) {
                    advanceItem(new BarsAndOres(BarsAndOreType.GoldOre), x);
                    return new Result(false, BRIGHT_BLUE + x + " Gold ore added" + RESET);
                }
                else
                    return new Result(false, RED+"Ops!!! you destroy Gold ore" + RESET);
            }
            if (((ForagingMinerals) object).getType().equals(IRIDIUM)) {

                int x = 1;
                if (Main.getClient(null).getPlayer().getLevelMining() >= 2)
                    x = 2;

                if (checkAmountProductAvailable(new BarsAndOres(BarsAndOreType.IridiumOre), x) ||
                    Main.getClient(null).getPlayer().getBackPack().getType().getRemindCapacity() > 0) {
                    advanceItem(new BarsAndOres(BarsAndOreType.IridiumOre), x);
                    return new Result(false, BRIGHT_BLUE + x + " Iridium ore added" + RESET);
                }
                else
                    return new Result(false, RED+"Ops!!! you destroy Iridium ore" + RESET);
            }
            if (((ForagingMinerals) object).getType().equals(IRON)) {

                int x = 1;
                if (Main.getClient(null).getPlayer().getLevelMining() >= 2)
                    x = 2;

                if (checkAmountProductAvailable(new BarsAndOres(BarsAndOreType.IronOre), x) ||
                    Main.getClient(null).getPlayer().getBackPack().getType().getRemindCapacity() > 0) {
                    advanceItem(new BarsAndOres(BarsAndOreType.IronOre), x);
                    return new Result(false, BRIGHT_BLUE + x + " Iron ore added" + RESET);
                }
                else
                    return new Result(false, RED+"Ops!!! you destroy Iron ore" + RESET);
            }
            else {

                int x = 1;
                if (Main.getClient(null).getPlayer().getLevelMining() >= 2)
                    x = 2;

                if (checkAmountProductAvailable(new ForagingMinerals(((ForagingMinerals) object).getType()), x) ||
                    Main.getClient(null).getPlayer().getBackPack().getType().getRemindCapacity() > 0) {
                    advanceItem(new ForagingMinerals(((ForagingMinerals) object).getType()), x);
                    return new Result(false, BRIGHT_BLUE + x + " " +
                        ((ForagingMinerals) object).getType().getDisplayName() + " added" + RESET);
                }
                else
                    return new Result(false, RED+"Ops!!! you destroy "+
                        ((ForagingMinerals) object).getType().getDisplayName()+RESET);
            }
        }
        else if (object instanceof Walkable && ((Walkable) object).getGrassOrFiber().equals("Plowed")) {
            tile.setGameObject(new Walkable());
            return new Result(true, BRIGHT_BLUE+"Oops! You accidentally removed a plowed tile"+RESET);
        }
        else if (object instanceof BasicRock) {

            if (Main.getClient(null).getPlayer().currentTool.healthCost() > 0 && Main.getClient(null).getPlayer().Buff_foraging_hoursLeft > 0)
                Main.getClient(null).getPlayer().increaseHealth(1);

            Main.getClient(null).getPlayer().increaseForagingAbility(10);

            int x = 1;
            if (Main.getClient(null).getPlayer().getLevelForaging() >= 2)
                x = 2;
            tile.setGameObject(new Walkable());

            if (checkAmountProductAvailable(new BasicRock(), x) ||
                Main.getClient(null).getPlayer().getBackPack().getType().getRemindCapacity() > 0) {

                advanceItem(new BasicRock(), x);
                return new Result(false, BRIGHT_BLUE + x + " Stone added" + RESET);
            }
            else
                return new Result(false, RED + "Ops!!! you destroy Stone" + RESET);
        }
        else if (object instanceof CraftingItem) {
            Inventory inventory = Main.getClient(null).getPlayer().getBackPack().inventory;
            if (inventory.Items.containsKey(object)) {
                inventory.Items.compute((Items) object, (k, v) -> v+1);
            }
            else {
                inventory.Items.put((Items) object, 1);
            }
            tile.setGameObject(new Walkable());
            return new Result(true , BLUE+ ((CraftingItem) object).getName() + " add to BackPack Successfully"+RESET);
        }
        return new Result(false, RED+"There are no minerals!"+RESET);
    }
    public static Result useShear (int dir) {


        Tile tile = getTileByDir(dir);
        GameObject object = tile.getGameObject();

        return new Result(false, RED+"There are no plant!"+RESET);
    }
    public static Result useMilkPail (int dir) {


        Tile tile = getTileByDir(dir);
        GameObject object = tile.getGameObject();

        return new Result(false, RED+"There are no plant!"+RESET);
    }

    // NPC task
    public static void NPAutomateTask() {

        User saveUser = Main.getClient(null).getPlayer();

        for (User user : currentGame.getGameState().getPlayers()currentGame.getGameState().getPlayers())
            for (NPC npc : NPC.values()) {

                Main.getClient(null).getPlayer() = user;
                user.setTodayTalking(npc, false);
                user.setTodayGifting(npc, false);

                if (user.getFriendshipLevel(npc) == 3 && Math.random() > 0.5)
                    if (user.getBackPack().getType().getRemindCapacity() > 0 ||
                        checkAmountProductAvailable(npc.getGiftItem(), 1))

                        advanceItem(npc.getGiftItem(), 1);
            }
        Main.getClient(null).getPlayer() = saveUser;
    }
    public static String OneNPCQuestsList (NPC npc) {

        StringBuilder sb = new StringBuilder();

        String title = npc.getName();
        String quest2;
        String quest3;

        ArrayList<String> requests = new ArrayList<>();
        ArrayList<Integer> numbers = new ArrayList<>(npc.getRequests().values());

        for (Items item : npc.getRequests().keySet())
            requests.add(item.getName());

        sb.append(title).append("\n\n");

        sb.append("Quest 1:\n");
        sb.append(numbers.getFirst()).append(" ").append(requests.getFirst())
            .append("  --->  ").append(npc.getReward(1)).append("\n\n");

        if (Main.getClient(null).getPlayer().getFriendshipLevel(npc) >= 1)
            quest2 = "Quest 2:";
        else
            quest2 = "Quest 2: (unlock at friendship level 1)";

        sb.append(quest2).append("\n");
        sb.append(numbers.get(1)).append(" ").append(requests.get(1))
            .append("  --->  ").append(npc.getReward(2)).append("\n\n");

        int dif = getDayDifferent(Main.getClient(null).getPlayer().getLevel3Date(npc), currentGame.getGameState().currentDate);

        if (Main.getClient(null).getPlayer().getFriendshipLevel(npc) >= 3) {
            if (dif > npc.getRequest3DayNeeded())
                quest3 = "Quest 3:";
            else
                quest3 = "Quest 3: (unlock in " + (npc.getRequest3DayNeeded() - dif) + " days)";
        } else {
            quest3 = "Quest 3: (unlock at friendship level 3)";
        }

        sb.append(quest3).append("\n");
        sb.append(numbers.get(2)).append(" ").append(requests.get(2))
            .append("  --->  ").append(npc.getReward(3)).append("\n");

        return sb.toString();
    }
    public static String OneNPCFriendshipList (NPC npc) {

        String str = switch (npc) {
            case Sebastian -> "";
            case Abigail -> "  ";
            case Harvey -> "   ";
            case Leah -> "      ";
            default -> "    ";
        };
        int width = 60;

        String result = str + "Level : " + Main.getClient(null).getPlayer().getFriendshipLevel(npc) +
            "       point : " + Main.getClient(null).getPlayer().getFriendshipPoint(npc);

        return npc.getName() + " : " + result;
    }
    public static Result doTask1 (NPC npc) {


        Map.Entry<Items, Integer> entry = new ArrayList<>(npc.getRequests().entrySet()).getFirst();
        Items key = entry.getKey();
        int value = entry.getValue();

        if (!checkAmountProductAvailable(key, value))
            return new Result(false, RED+"You don't have enough source"+RESET);

        switch (npc) {

            case Sebastian -> {

                if (!checkAmountProductAvailable(new ForagingMinerals(DIAMOND), 1) &&
                    Main.getClient(null).getPlayer().getBackPack().getType().getRemindCapacity() <= 0) {

                    int number = 2;
                    if (Main.getClient(null).getPlayer().getFriendshipLevel(npc) > 1)
                        number *= 2;
                    advanceItem(key, -value);
                    advanceItem(new ForagingMinerals(DIAMOND), number);
                    return new Result(true, BRIGHT_BLUE+"You got "+number+" Diamond"+RESET);
                }
                return new Result(true, RED+"Inventory is full"+RESET);
            }
            case Abigail -> {

                Main.getClient(null).getPlayer().increaseFriendshipPoint(NPC.Abigail, 200);
                return new Result(true, BRIGHT_BLUE+"Your friendship level with Abigail increased"+RESET);
            }
            case Harvey -> {

                int number = 750;
                if (Main.getClient(null).getPlayer().getFriendshipLevel(npc) > 1)
                    number *= 2;
                Main.getClient(null).getPlayer().increaseMoney(number);
                return new Result(true, "Your got +"+number+" money");
            }
            case Leah -> {
                int number = 500;
                if (Main.getClient(null).getPlayer().getFriendshipLevel(npc) > 1)
                    number *= 2;
                Main.getClient(null).getPlayer().increaseMoney(number);
                return new Result(true, "Your got +"+number+" money");
            }
            case Robin -> {

                int number = 2000;
                if (Main.getClient(null).getPlayer().getFriendshipLevel(npc) > 1)
                    number *= 2;
                Main.getClient(null).getPlayer().increaseMoney(number);
                return new Result(true, "Your got +"+number+" money");
            }
            default -> {
                return new Result(true, "");
            }
        }
    }
    public static Result doTask2 (NPC npc) {

        if (Main.getClient(null).getPlayer().getFriendshipLevel(npc) < 1)
            return new Result(false, RED+"Your friendship with "+npc.getName()+" needs to grow"+RESET);

        Map.Entry<Items, Integer> entry = new ArrayList<>(npc.getRequests().entrySet()).get(1);
        Items key = entry.getKey();
        int value = entry.getValue();

        if (!checkAmountProductAvailable(key, value))
            return new Result(false, RED+"You don't have enough source"+RESET);

        switch (npc) {

            case Sebastian -> {
                int number = 5000;
                if (Main.getClient(null).getPlayer().getFriendshipLevel(npc) > 1)
                    number *= 2;
                Main.getClient(null).getPlayer().increaseMoney(number);
                return new Result(true, "Your got +"+number+" money");
            }
            case Abigail -> {
                int number = 500;
                if (Main.getClient(null).getPlayer().getFriendshipLevel(npc) > 1)
                    number *= 2;
                // advanceItem(); // TODO ایتما باید کم بشن و تو قسمت نشون دادن اینا همشون داره یه عدد نشون میده
                Main.getClient(null).getPlayer().increaseMoney(number);
                return new Result(true, "Your got +"+number+" money");
            }
            case Harvey -> {
                Main.getClient(null).getPlayer().increaseFriendshipPoint(NPC.Abigail, 200);
                return new Result(true, BRIGHT_BLUE+"Your friendship level with Harvey increased"+RESET);
            }
            case Leah -> {

                if (checkAmountProductAvailable(new MarketItem(MarketItemType.PancakesRecipe), 1) ||
                    Main.getClient(null).getPlayer().getBackPack().getType().getRemindCapacity() > 0) {

                    advanceItem(key, -value);
                    advanceItem(new MarketItem(MarketItemType.PancakesRecipe), 1);
                    Recipe recipe = HomeController.findRecipeByName("Pancakes");
                    assert recipe != null;
                    recipe.setUsable(true);
                    return new Result(true, BRIGHT_BLUE+"You got 1 Pancakes Recipe"+RESET);
                }
                return new Result(true, RED+"Inventory is full"+RESET);
            }
            case Robin -> {
                int number = 1000;
                if (Main.getClient(null).getPlayer().getFriendshipLevel(npc) > 1)
                    number *= 2;
                Main.getClient(null).getPlayer().increaseMoney(number);
                return new Result(true, "Your got +"+number+" money");
            }
            default -> {
                return new Result(true, "");
            }
        }
    }
    public static Result doTask3 (NPC npc) {

        int dif = getDayDifferent(Main.getClient(null).getPlayer().getLevel3Date(npc), currentGame.getGameState().currentDatecurrentGame.getGameState().currentDate);

        if (Main.getClient(null).getPlayer().getFriendshipLevel(npc) >= 3) {
            if (dif < npc.getRequest3DayNeeded())
                return new Result(false, RED+"Quest is lock\n" +
                    "Unlock in " + dif + " days later"+RESET);
        } else
            return new Result(false, RED+"Your friendship with "+npc.getName()+" needs to grow"+RESET);

        Map.Entry<Items, Integer> entry = new ArrayList<>(npc.getRequests().entrySet()).get(2);
        Items key = entry.getKey();
        int value = entry.getValue();

        if (!checkAmountProductAvailable(key, value))
            return new Result(false, RED+"You don't have enough source"+RESET);

        switch (npc) {

            case Sebastian -> {

                if (checkAmountProductAvailable(new ForagingMinerals(QUARTZ), 1) ||
                    Main.getClient(null).getPlayer().getBackPack().getType().getRemindCapacity() > 0) {
                    int number = 50;
                    if (Main.getClient(null).getPlayer().getFriendshipLevel(npc) > 1)
                        number *= 2;
                    advanceItem(key, -value);
                    advanceItem(new ForagingMinerals(QUARTZ), number);
                    return new Result(true, BRIGHT_BLUE+"You got "+number+" Quartz"+RESET);
                }
                return new Result(true, RED+"Inventory is full"+RESET);
            }
            case Abigail -> {
                if (checkAmountProductAvailable(new CraftingItem(CraftType.IridiumSprinkler), 1) ||
                    Main.getClient(null).getPlayer().getBackPack().getType().getRemindCapacity() > 0) {
                    int number = 1;
                    if (Main.getClient(null).getPlayer().getFriendshipLevel(npc) > 1)
                        number *= 2;
                    advanceItem(key, -value);
                    advanceItem(new CraftingItem(CraftType.IridiumSprinkler), number);
                    return new Result(true, BRIGHT_BLUE+"You got "+number+" Iridium sprinkler"+RESET);
                }
                return new Result(true, RED+"Inventory is full"+RESET);
            }
            case Harvey -> {
                if (checkAmountProductAvailable(new MarketItem(MarketItemType.Salad), 1) ||
                    Main.getClient(null).getPlayer().getBackPack().getType().getRemindCapacity() > 0) {
                    int number = 5;
                    if (Main.getClient(null).getPlayer().getFriendshipLevel(npc) > 1)
                        number *= 2;
                    advanceItem(key, -value);
                    advanceItem(new MarketItem(MarketItemType.Salad), number);
                    return new Result(true, BRIGHT_BLUE+"You got "+number+" salad"+RESET);
                }
                return new Result(true, RED+"Inventory is full"+RESET);
            }
            case Leah -> {

                if (checkAmountProductAvailable(new CraftingItem(CraftType.DeluxeScarecrow), 1) ||
                    Main.getClient(null).getPlayer().getBackPack().getType().getRemindCapacity() > 0) {
                    int number = 1;
                    if (Main.getClient(null).getPlayer().getFriendshipLevel(npc) > 1)
                        number *= 2;
                    advanceItem(key, -value);
                    advanceItem(new CraftingItem(CraftType.DeluxeScarecrow), number);
                    return new Result(true, BRIGHT_BLUE+"You got "+number+" ⅾeⅼuxe sⅽareⅽrow"+RESET);
                }
                return new Result(true, RED+"Inventory is full"+RESET);
            }
            case Robin -> {
                int number = 1500;
                if (Main.getClient(null).getPlayer().getFriendshipLevel(npc) > 1)
                    number *= 2;
                Main.getClient(null).getPlayer()Main.getClient(null).getPlayer().increaseMoney(number);
                return new Result(true, "Your got +"+number+" money");
            }
            default -> {
                return new Result(true, "");
            }
        }
    }

    public static void plow () {
        for (int i = 0; i < 30 ; i++) {
            for (int j = 0; j < 30 ; j++) {
                Tile tile = getTileByCoordinates(i,j);
                if (tile.getGameObject() instanceof Walkable) {
                    ((Walkable) tile.getGameObject()).setGrassOrFiber("Plowed");
                }
            }
        }
    }
}
