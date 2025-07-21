
package com.Graphic.Controller.MainGame;

import com.Graphic.View.GameMenus.GameMenu;
import com.Graphic.model.*;
import com.Graphic.model.Animall.Animal;
import com.Graphic.model.Animall.BarnOrCage;
import com.Graphic.model.Enum.AllPlants.*;
import com.Graphic.model.Enum.Commands.GameMenuCommands;
import com.Graphic.model.Enum.Door;
import com.Graphic.model.Enum.FoodTypes;
import com.Graphic.model.Enum.GameTexturePath;
import com.Graphic.model.Enum.ItemType.*;
import com.Graphic.model.Enum.NPC;
import com.Graphic.model.Enum.ToolsType.*;
import com.Graphic.model.Enum.WeatherTime.*;
import com.Graphic.model.HelpersClass.Result;
import com.Graphic.model.HelpersClass.TextureManager;
import com.Graphic.model.MapThings.*;
import com.Graphic.model.OtherItem.*;
import com.Graphic.model.Places.*;
import com.Graphic.model.Plants.*;
import com.Graphic.model.ToolsPackage.*;
import com.Graphic.model.Weather.Cloud;
import com.Graphic.model.Weather.DateHour;
import com.Graphic.model.Weather.LightningEffect;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.*;
import java.util.regex.Matcher;

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
    static long lastTimeUpdate = TimeUtils.millis();


    static Cloud cloud;
    public static LightningEffect lightningEffect;




    public static void init() {

        createScreenOverlay(gameMenu.getStage());
    }
    public static void update(float delta) {

        handleLightning(delta);

        if (TimeUtils.millis() - lastTimeUpdate > 1000) {
            AutomaticFunctionAfterAnyAct();
            lastTimeUpdate = TimeUtils.millis();
        }

    }

    public static void handleLightning (float delta) {

        if (cloud == null) {
            if ((TimeUtils.millis() / 1000) % 40 == 0)
                if (currentGame.currentWeather.equals(Weather.Stormy)) {

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
        User user = currentGame.players.get(random1.nextInt(currentGame.players.size()));
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

        for (User user : currentGame.players) {

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

        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;

        if (inventory.Items.containsKey(items)) {
            int amount = inventory.Items.get(items);
            return amount >= number;
        }
        return false;
    }
    public static void advanceItem(Items items, int amount) {

        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;

        if (inventory.Items.containsKey(items)) {
            inventory.Items.compute(items, (k, x) -> x + amount);
        }
        else
            inventory.Items.put(items, amount);
    }


    public static Tile getTileByDir (int dir) {

        float x = currentGame.currentPlayer.getPositionX();
        float y = currentGame.currentPlayer.getPositionY();

//        if (dir == 1)
//            return getTileByCoordinates(x+1, y);
//        else if (dir == 2)
//            return getTileByCoordinates(x+1, y+1);
//        else if (dir == 3)
//            return getTileByCoordinates(x, y+1);
//        else if (dir == 4)
//            return getTileByCoordinates(x-1, y+1);
//        else if (dir == 5)
//            return getTileByCoordinates(x-1, y);
//        else if (dir == 6)
//            return getTileByCoordinates(x-1, y-1);
//        else if (dir == 7)
//            return getTileByCoordinates(x, y-1);
//        else if (dir == 8)
//            return getTileByCoordinates(x+1, y-1);
//        else
            return null;
    }
    public static Tile getTileByCoordinates(int x, int y) {
        if (x<0 || y<0 || x>=90 || y>=90) {
            return null;
        }
        Tile targetTile = currentGame.bigMap.get(90 * y + x);
        return targetTile;
    }

    // create initial map

    public static void createInitialMine( int x, int y , int topLeftX, int topLeftY, int width , int height) {
        Farm farm = currentGame.currentPlayer.getFarm();

        Mine mine=null;

        for (int i = topLeftX + (60 * x) ; i < topLeftX + (60*x) + width; i++) {
            for (int j = topLeftY + (60 * y); j < topLeftY + (60 * y) + height; j++) {
                mine = new Mine(topLeftX + 60 *x , topLeftY + 60 * y ,width , height);
                Tile tile = new Tile(i , j , mine);
                farm.Farm.add(tile);
                currentGame.bigMap.add(tile);
            }
        }
        farm.setMine(mine);
    }
    public static void createInitialLake( int x, int y , int topLeftX , int topLeftY , int width , int height) {
        Farm farm = currentGame.currentPlayer.getFarm();
        Lake lake = null;
        for (int i = topLeftX + 60 * x; i < topLeftX + 60 * x + width; i++) {
            for (int j = topLeftY + 60 * y; j < topLeftY + 60 * y + height; j++) {
                lake = new Lake(topLeftX + 60 * x, topLeftY + 60 * y, width, height);
                Tile tile = new Tile(i, j, lake);
                farm.Farm.add(tile);
                currentGame.bigMap.add(tile);
            }
        }
        farm.setLake(lake);

    }
    public static void createInitialHouse(int x, int y , int topLeftX , int topLeftY , int width , int height) {
        Farm farm = currentGame.currentPlayer.getFarm();
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
//                if (i == topLeftX + 60 * x + width / 2 && j == topLeftY + 60 * y + height-1) {
//                    Tile tile = new Tile(i, j, houseDoor);
//                    farm.Farm.add(tile);
//                    currentGame.bigMap.add(tile);
//                }
                if (i == topLeftX + 60 * x + width -2 && j == topLeftY + 60 * y + height-2) {
                    Tile tile = new Tile(i, j, fridge);
                    farm.Farm.add(tile);
                    currentGame.bigMap.add(tile);
                }
//                else if (i == topLeftX + 60 * x || i == topLeftX + 60 * x + width -1 || j==topLeftY+60*y || j==topLeftY+60*y + height-1) {
//                    Tile tile = new Tile(i, j, wall);
//                    farm.Farm.add(tile);
//                    currentGame.bigMap.add(tile);
//                }
                else {
                    Home home = new Home(topLeftX + 60 * x, topLeftY + 60 * y,width,height, fridge);
                    Tile tile = new Tile(i, j, home);
                    farm.Farm.add(tile);
                    currentGame.bigMap.add(tile);
                }
            }
        }
        farm.setHome(new Home(topLeftX + 60 * x, topLeftY + 60 * y,width,height, fridge));
    }
    public static void createInitialGreenHouse(int x, int y , int topLeftX , int topLeftY , int width , int height) {
        Farm farm = currentGame.currentPlayer.getFarm();
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
                    currentGame.bigMap.add(tile);
                }
                else if (i== topLeftX + 60*x + width/2 && j==topLeftY + 60*y + 1) {
                    Tile tile = new Tile(i, j, waterTank);
                    farm.Farm.add(tile);
                    currentGame.bigMap.add(tile);
                }
                else if (i == topLeftX + 60 * x || i == topLeftX + 60 * x + width -1 || j==topLeftY+60*y || j==topLeftY+60*y + height-1) {
                    Tile tile = new Tile(i, j, GreenWall);
                    farm.Farm.add(tile);
                    currentGame.bigMap.add(tile);
                }

                else {
                    Tile tile = new Tile(i, j, greenHouse);
                    farm.Farm.add(tile);
                    currentGame.bigMap.add(tile);
                }
            }
        }
        farm.setGreenHouse(greenHouse);
    }

    public static void buildHall() {
        Walkable walkable=new Walkable();

        for (int j = 30 ; j<60 ; j++) {
            Tile tile=new Tile(15 , j , walkable);
            currentGame.bigMap.add(tile);
        }
        for (int i=30 ; i<60 ; i++) {
            Tile tile=new Tile(i , 15 , walkable);
            currentGame.bigMap.add(tile);
        }
        for (int i = 30 ; i<60 ; i++) {
            Tile tile=new Tile(i , 75 , walkable);
            currentGame.bigMap.add(tile);
        }
        for (int j=30 ; j<60 ; j++) {
            Tile tile=new Tile(75 , j , walkable);
            currentGame.bigMap.add(tile);
        }
        for (int i=30 ; i<60 ; i++) {
            for (int j=0 ; j<30 ; j++) {
                if (i==45 && j >= 16) {
                    Tile tile=new Tile(i , j , walkable);
                    currentGame.bigMap.add(tile);
                }
                else if (j != 15) {
                    UnWalkable unWalkable=new UnWalkable();
                    Tile tile=new Tile(i , j , unWalkable);
                    currentGame.bigMap.add(tile);
                }
            }
        }
        for (int i=0 ; i<30 ; i++) {
            for (int j=30 ; j<60 ; j++) {

                if (j==45 && i>=16) {
                    Tile tile=new Tile(i , j , walkable);
                    currentGame.bigMap.add(tile);
                }
                else if (i!=15) {
                    UnWalkable unWalkable=new UnWalkable();
                    Tile tile=new Tile(i , j , unWalkable);
                    currentGame.bigMap.add(tile);
                }
            }
        }
        for (int i=30 ; i<60 ; i++) {
            for (int j=60 ; j<90 ; j++) {
                if (i==45 && j <= 74) {
                    Tile tile=new Tile(i , j , walkable);
                    currentGame.bigMap.add(tile);
                }
                else if (j!=75) {
                    UnWalkable unWalkable=new UnWalkable();
                    Tile tile=new Tile(i , j , unWalkable);
                    currentGame.bigMap.add(tile);
                }
            }
        }
        for (int i=60 ; i<90 ; i++) {
            for (int j=30 ; j<60 ; j++) {
                if (j == 45 && i <= 74) {
                    Tile tile=new Tile(i , j , walkable);
                    currentGame.bigMap.add(tile);
                }
                else if (i!=75) {
                    UnWalkable unWalkable=new UnWalkable();
                    Tile tile=new Tile(i , j , unWalkable);
                    currentGame.bigMap.add(tile);
                }
            }
        }
    }
    public static void buildNpcVillage() {
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
                        currentGame.bigMap.add(tile);
                    }
                    else {
                        Tile tile = new Tile(i, j, wall);
                        currentGame.bigMap.add(tile);
                    }
                }
                else if (Market.isInMarket(i , j) != null) {
                    MarketType marketType = Market.isInMarket(i, j);
                    Market market = new Market(marketType);
                    Tile tile=new Tile(i , j , market);
                    currentGame.bigMap.add(tile);
                }

                else if (NPC.wallOrDoor(i, j) != null) {
                    NPC npc = NPC.wallOrDoor(i, j);
                    if (i == npc.getTopLeftX() + npc.getWidth() -1 && j==npc.getTopLeftY() + 2) {
                        Tile tile=new Tile(i , j , dor);
                        currentGame.bigMap.add(tile);
                    }
                    else {
                        Tile tile=new Tile(i , j , wall);
                        currentGame.bigMap.add(tile);
                    }
                }
                else if (isInNpc(i , j) != null) {
                    NPC npc = isInNpc(i, j);
                    Walkable walkable=new Walkable();
                    walkable.setGrassOrFiber(npc.getName());
                    Tile tile=new Tile(i , j , walkable);
                    currentGame.bigMap.add(tile);
                }
                else {
                    Walkable walkable=new Walkable();
                    Tile tile=new Tile(i , j , walkable);
                    currentGame.bigMap.add(tile);
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
    public static void createInitialFarm(int id) {

        long seed=System.currentTimeMillis();
        Farm farm= currentGame.currentPlayer.getFarm();
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
                        createInitialMine(currentGame.currentPlayer.topLeftX, currentGame.currentPlayer.topLeftY, mine.getStartX(), mine.getStartY(), mine.getWidth(), mine.getHeight());
                        createMine = true;
                    }
                }
                else if(i >= lake.getTopLeftX() && i< lake.getTopLeftX() + lake.getWidth() && j>=lake.getTopLeftY() && j< lake.getTopLeftY() + lake.getHeight()) {
                    if (! createLake) {
                        createInitialLake(currentGame.currentPlayer.topLeftX, currentGame.currentPlayer.topLeftY, lake.getTopLeftX(), lake.getTopLeftY(), lake.getWidth(), lake.getHeight());
                        createLake = true;
                    }
                }
                else if(i>=greenHouse.getCoordinateX()  && i < greenHouse.getCoordinateX() + greenHouse.getWidth()
                    && j>=greenHouse.getCoordinateY() && j<greenHouse.getCoordinateY() + greenHouse.getLength() ) {

                    if (! createGreenHouse) {
                        createInitialGreenHouse(currentGame.currentPlayer.topLeftX, currentGame.currentPlayer.topLeftY, greenHouse.getCoordinateX(), greenHouse.getCoordinateY(),
                            greenHouse.getWidth(), greenHouse.getLength());

                        createGreenHouse = true;
                    }
                }
                else if (i >= home.getTopLeftX() && i<home.getTopLeftX() + home.getWidth() && j >= home.getTopLeftY() && j<home.getTopLeftY() + home.getLength() ) {
                    if (! createHome) {
                        createInitialHouse(currentGame.currentPlayer.topLeftX, currentGame.currentPlayer.topLeftY, home.getTopLeftX(), home.getTopLeftY(), home.getWidth(), home.getLength());
                        createHome = true;
                    }
                }
                else {
                    treeNumber+=MapGenerator(i,j,seed , treeNumber);
                }
            }
        }
        farm.setX(60 * currentGame.currentPlayer.topLeftX);
        farm.setY(60 * currentGame.currentPlayer.topLeftY);
        farm.setIndex(2 * currentGame.currentPlayer.topLeftY + currentGame.currentPlayer.topLeftX + 1);
        currentGame.farms.add(farm);
    }
    public static int MapGenerator(int i,int j,long seed , int treeNumber){
        if (i == 0 || i == 29 || j == 0 || j == 29) {
            if ((i == 15 && j == 29) || (i==15 && j == 0 ) ){
                door FarmDoor=new door();
                FarmDoor.setDoor(Door.Farm);
                FarmDoor.setCharactor('D');
                Tile tile=new Tile(i + 60 * currentGame.currentPlayer.topLeftX,j + 60 * currentGame.currentPlayer.topLeftY,FarmDoor);
                currentGame.currentPlayer.getFarm().Farm.add(tile);
                currentGame.bigMap.add(tile);
                return 0;
            }
            else if ((i==29 && j==15) || (i==0 && j==15)){
                door FarmDoor=new door();
                FarmDoor.setDoor(Door.Farm);
                FarmDoor.setCharactor('D');
                Tile tile=new Tile(i + 60 * currentGame.currentPlayer.topLeftX,j + 60 * currentGame.currentPlayer.topLeftY,FarmDoor);
                currentGame.currentPlayer.getFarm().Farm.add(tile);
                currentGame.bigMap.add(tile);
                return 0;
            }
            else {
                if (i == 0 || i == 29) {
                    Wall wall = new Wall(1);
                    Tile tile = new Tile(i + 60 * currentGame.currentPlayer.topLeftX, j + 60 * currentGame.currentPlayer.topLeftY, wall);
                    currentGame.currentPlayer.getFarm().Farm.add(tile);
                    currentGame.bigMap.add(tile);
                }
                else {
                    Wall wall = new Wall(0);
                    Tile tile = new Tile(i + 60 * currentGame.currentPlayer.topLeftX, j + 60 * currentGame.currentPlayer.topLeftY, wall);
                    currentGame.currentPlayer.getFarm().Farm.add(tile);
                    currentGame.bigMap.add(tile);
                }
                return 0;
            }

        }
        else {
            if (i ==15 && j == 20) {
                Walkable walkable=new Walkable();
                Tile tile = new Tile(i + 60 * currentGame.currentPlayer.topLeftX, j + 60 * currentGame.currentPlayer.topLeftY, walkable);
                currentGame.currentPlayer.getFarm().Farm.add(tile);
                currentGame.bigMap.add(tile);
                return 0;
            }
            PerlinNoise perlinNoise = new PerlinNoise(seed);

            double noise = perlinNoise.noise(i * 0.1, j * 0.1);
            if (-1.2 < noise && noise < -0.9 && treeNumber <30) {
                Tree tree = new Tree(TreeType.OakTree,currentGame.currentDate.clone());
                Tile tile = new Tile(i + 60 * currentGame.currentPlayer.topLeftX, j + 60 * currentGame.currentPlayer.topLeftY, tree);
                currentGame.currentPlayer.getFarm().Farm.add(tile);
                currentGame.bigMap.add(tile);
                return 1;
            }
            else if(noise > -0.9 && noise <-0.5 && treeNumber <30){
                Tree tree = new Tree(TreeType.MapleTree,currentGame.currentDate.clone());
                Tile tile = new Tile(i + 60 * currentGame.currentPlayer.topLeftX, j + 60 * currentGame.currentPlayer.topLeftY, tree);
                currentGame.currentPlayer.getFarm().Farm.add(tile);
                currentGame.bigMap.add(tile);
                return 1;
            }
            else if (noise > -0.5 && noise < - 0.2 && treeNumber <30){
                Tree tree = new Tree(TreeType.PineTree,currentGame.currentDate.clone());
                Tile tile = new Tile(i + 60 * currentGame.currentPlayer.topLeftX, j + 60 * currentGame.currentPlayer.topLeftY, tree);
                currentGame.currentPlayer.getFarm().Farm.add(tile);
                currentGame.bigMap.add(tile);
                return 1;
            }
            else if (-0.1 < noise && noise < 0.0) {
                BasicRock basicRock = new BasicRock();
                basicRock.setCharactor('S');
                Tile tile = new Tile(i + 60 * currentGame.currentPlayer.topLeftX, j + 60 * currentGame.currentPlayer.topLeftY, basicRock);
                currentGame.currentPlayer.getFarm().Farm.add(tile);
                currentGame.bigMap.add(tile);
                return 0;
            }
            else {
                Walkable walkable = new Walkable();
                walkable.setCharactor('.');
                Tile tile = new Tile(i + 60 * currentGame.currentPlayer.topLeftX, j + 60 * currentGame.currentPlayer.topLeftY, walkable);
                currentGame.currentPlayer.getFarm().Farm.add(tile);
                currentGame.bigMap.add(tile);
                return 0;
            }

        }

    }

    public static User getUserByLocation (int j, int i) {

        for (User user : currentGame.players)
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




    public static boolean checkCoordinateForFishing(){
//        int [] x={1,1,1,0,0,-1,-1,-1};
//        int [] y={1,0,-1,1,-1,-1,0,1};
//        for (int i=0;i<8;i++){
//            if (getTileByCoordinates(currentGame.currentPlayer.getPositionX() +x[i],currentGame.currentPlayer.getPositionY() +y[i]).
//                getGameObject() instanceof Lake){
//                return true;
//            }
//        }
        return false;
    }
    public static FishingPole isFishingPoleTypeExist(String name){
        Inventory inventory=currentGame.currentPlayer.getBackPack().inventory;
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
        if (x<60 * currentGame.currentPlayer.topLeftX || y< 60 * currentGame.currentPlayer.topLeftY) {
            return false;
        }
        if (x + width > 30 + 60 * currentGame.currentPlayer.topLeftX || y + height > 30 + 60 * currentGame.currentPlayer.topLeftY) {
            return false;
        }
        for (int i = x; i < x+width; i++) {
            for (int j = y; j < y+height; j++) {
                Tile tile = getTileByCoordinates(i , j);
                if (!(tile.getGameObject() instanceof Walkable)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static Animal getAnimalByName(String animalName) {
        for (BarnOrCage barnOrCage:currentGame.currentPlayer.BarnOrCages){
            for (Animal animal: barnOrCage.animals){
                if (animal.getName().equals(animalName)){
                    return animal;
                }
            }
        }
        return null;
    }
    public static boolean checkTileForAnimalWalking(int x, int y) {
        Tile tile = getTileByCoordinates(x , y );
        if (tile == null) {
            return false;
        }
        if (!(tile.getGameObject() instanceof Walkable) && !(tile.getGameObject() instanceof BarnOrCage)) {
            return false;
        }
        return true;
    }

    public static boolean animalIsOnBarnOrCage(Animal animal) {
        BarnOrCage barnOrCage=null;
        for (BarnOrCage barnOrCage1 : currentGame.currentPlayer.BarnOrCages) {
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
        for (User player : currentGame.players)
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
                    if (currentGame.currentDate.getDate() - animal.getLastProduceDay() == animal.getType().getPeriod()) {
                        animal.setLastProduceDay(currentGame.currentDate.clone().getDate());
                    }
                    else if (currentGame.currentDate.getDate() - animal.getLastProduceDay() == -28 + animal.getType().getPeriod()) {
                        animal.setLastProduceDay(currentGame.currentDate.clone().getDate());
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
        for (User player : currentGame.players) {
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
        return currentGame.currentDate.getDate() - animal.getLastProduceDay() == 0;
    }

    public static void checkSprinkler() {
        int domain=0;
        Tile Sprinkler=null;

        for (int dir =1 ; dir<9 ; dir++) {
            Tile tile1=getTileByDir(dir);
            if (tile1 != null) {
                if (tile1.getGameObject() instanceof CraftingItem) {
                    if (((CraftingItem) tile1.getGameObject()).getType().equals(CraftType.Sprinkler)) {
                        Sprinkler = tile1;
                        domain = 4;
                        break;
                    }
                    if (((CraftingItem) tile1.getGameObject()).getType().equals(CraftType.QualitySprinkler)) {
                        Sprinkler = tile1;
                        domain = 8;
                        break;
                    }
                    if (((CraftingItem) tile1.getGameObject()).getType().equals(CraftType.IridiumSprinkler)) {
                        Sprinkler = tile1;
                        domain = 24;
                        break;
                    }
                }
            }
        }

        if (Sprinkler != null) {
            for (int i=- domain /2 ; i < domain /2 ; i++) {
                for (int j=- domain /2 ; j < domain /2 ; j++) {
                    Tile tile1 = getTileByCoordinates(i + Sprinkler.getX(), j+Sprinkler.getY());
                    if (tile1.getGameObject() instanceof Tree ) {
                        ((Tree) tile1.getGameObject()).setLastWater(currentGame.currentDate.clone());
                    }
                    else if (tile1.getGameObject() instanceof ForagingSeeds) {
                        ((ForagingSeeds) tile1.getGameObject()).setLastWater(currentGame.currentDate.clone());
                    }
                    else if (tile1.getGameObject() instanceof GiantProduct) {
                        ((GiantProduct) tile1.getGameObject()).setLastWater(currentGame.currentDate.clone());
                    }
                }
            }
        }
    }
    public static CraftingItem isNeighborWithCrafting(String name) {
//        int [] dirx={0,0,1,1,1,-1,-1,-1};
//        int [] diry={1,-1,0,1,-1,0,1,-1};
//
//        for (int x = currentGame.currentPlayer.getPositionX(); x <currentGame.currentPlayer.getPositionX()+ dirx.length; x++) {
//            for (int y=currentGame.currentPlayer.getPositionY() ; y< currentGame.currentPlayer.getPositionY()+ diry.length; y++) {
//                Tile tile=getTileByCoordinates(x,y);
//                if (tile == null) {
//                    continue;
//                }
//                if (tile.getGameObject() instanceof CraftingItem) {
//                    if (((CraftingItem) tile.getGameObject()).getType().getName().equals(name)) {
//                        return (CraftingItem) tile.getGameObject();
//                    }
//                }
//            }
//        }
        return null;
    }
    public static void addArtisanToInventory(Items item) {
        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;
        for (Map.Entry <Items , Integer> entry : inventory.Items.entrySet()) {
            if (entry.getKey().equals(item)) {
                inventory.Items.put(item, entry.getValue() + 1);
                return;
            }
        }
        inventory.Items.put(item, 1);
    }



    // Ario


    public static void unloadAndReward() {
        for (User user : currentGame.players) {
            Farm farm = user.getFarm();
            for (ShippingBin shippingBin : farm.shippingBins) {
                for (Map.Entry<Items,Integer> entry : shippingBin.binContents.entrySet()) {
                    if (entry.getKey() instanceof Fish) {
                        Fish fish = (Fish) entry.getKey();
                        currentGame.currentPlayer.increaseMoney(fish.getSellPrice() * entry.getValue());
                    }
                    if (entry.getKey() instanceof Animalproduct) {
                        Animalproduct animalproduct = (Animalproduct) entry.getKey();
                        currentGame.currentPlayer.increaseMoney(animalproduct.getSellPrice() * entry.getValue());
                    }
                }
                shippingBin.binContents.clear();
            }
        }
    }


    public static void nextTurn () {
        User old = currentGame.currentPlayer;
        boolean done = false;
        boolean temp = false;
        int wrongAttempts = 0;

        while (wrongAttempts <= 5) {
            for (User user : currentGame.players) {
                if (temp) {

                    turnCounter++;
                    currentGame.currentPlayer = user;

                    AutomaticFunctionAfterOneTurn();

                    if (checkForDeath()) {
                        nextTurn();
                        return;
                    }
                    System.out.println(currentGame.currentPlayer.getNickname() + "'s turn.");

                    // Display Unseen Messages...
                    System.out.println("\nDisplaying Unseen Messages...");
                    for (List<MessageHandling> messages : currentGame.conversations.values()) {
                        for (MessageHandling m : messages) {
                            if (m.getReceiver().getUsername().equals(currentGame.currentPlayer.getUsername()) && !m.isSeen()) {
                                if (m.getText().contains("Proposal"))
                                    break;
                                m.print();
                                m.setSeen(true);

                                //بخش ریت کردن هدیه ها
                                if (m.getText().endsWith("Rate it out of 5!")) {
                                    HumanCommunications f = getFriendship(currentGame.currentPlayer, m.getSender());
                                    do {
                                        assert f != null;
                                    } while (!f.rateGifts().IsSuccess());
                                }
                            }
                        }
                    }
                    System.out.println(GREEN+"Unseen Messages Displayed.\n"+RESET);

                    // proposals
                    for (List<MessageHandling> messages : currentGame.conversations.values()) {
                        for (MessageHandling m : messages) {
                            if (m.getReceiver().getUsername().equals(currentGame.currentPlayer.getUsername()) && !m.isSeen() && m.getText().contains("Proposal")) {
                                m.print();
                                m.setSeen(true);
                                Result result;
                                do {
                                    result = Marriage.proposalResponse(m.getSender(), m.getReceiver());
                                    System.out.println(result.massage());
                                } while (!result.IsSuccess());
                            }
                        }
                    }

                    return;
                }
                if (Objects.equals(user.getUsername(), old.getUsername()))
                    temp = true;
                wrongAttempts++;
            }
        }
        System.out.println(RED+"Couldn't find the next Player!"+RESET);
    }
    public static void DisplayFriendships () {
        String targetName = currentGame.currentPlayer.getUsername();

        for (HumanCommunications f : currentGame.friendships) {
            if (f.getPlayer1().getUsername().equals(targetName) || f.getPlayer2().getUsername().equals(targetName))
                f.printInfo();
        }
    }
    public static void cheatAddXp (String input) {
        int xp = Integer.parseInt(GameMenuCommands.addXpCheat.getMatcher(input).group("xp"));
        String otherName = GameMenuCommands.addXpCheat.getMatcher(input).group("other");
        User other = findPlayerInGame(otherName);
        HumanCommunications f = getFriendship(currentGame.currentPlayer, other);
        f.addXP(xp);
    }
    public static void talking (String input) {
        String destinationUsername = GameMenuCommands.talking.getMatcher(input).group("username");
        String message = GameMenuCommands.talking.getMatcher(input).group("message");
        User destinationUser = null;
        boolean found = false;
        for (User player: currentGame.players) {
            if (player.getUsername().equals(destinationUsername)) {
                found = true;
                destinationUser = player;
                break;
            }
        }
        if (!found) {
            System.out.println(RED+"Username is Unavailable!"+RESET);
            return;
        }
        if (destinationUsername.equals(currentGame.currentPlayer.getUsername())) {
            System.out.println("You can't Talk to " + RED+"Yourself"+RESET + "!");
            return;
        }
        HumanCommunications f = getFriendship(currentGame.currentPlayer, destinationUser);
        if (f == null) {
            System.out.println("There's " + RED+"no Friendship"+RESET + " Among these Users");
            return;
        }
        Result result = f.talk(message);
        System.out.println(result);
    }
    public static void DisplayingTalkHistory (String input) {
        String username = GameMenuCommands.talkHistory.getMatcher(input).group("username");
        User destinationUser = null;
        boolean found = false;
        for (User player: currentGame.players) {
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
        if (username.equals(currentGame.currentPlayer.getUsername())) {
            System.out.println("You can't Talk to " + RED+"Yourself"+RESET + "!");
            return;
        }
        HumanCommunications f = getFriendship(currentGame.currentPlayer, destinationUser);
        if (f == null) {
            System.out.println("There's " + RED+"no Friendship"+RESET + " Among these Users");
            return;
        }
        Result result = f.talkingHistory();
        System.out.println(result);
    }
    public static void hug (String input) {
        String username = GameMenuCommands.hug.getMatcher(input).group("username");
        if (!currentGame.players.contains(findPlayerInGame(username))) {
            System.out.println(RED+"Username is Unavailable!"+RESET);
            return;
        }
        if (username.equals(currentGame.currentPlayer.getUsername())) {
            System.out.println("You can't Hug " + RED+"Yourself"+RESET + "!");
            return;
        }
        HumanCommunications f = getFriendship(currentGame.currentPlayer, findPlayerInGame(username));
        if (f == null) {
            System.out.println("There's " + RED+"no Friendship"+RESET + " Among these Users");
            return;
        }
        Result result = f.Hug();
        System.out.println(result);
    }

    public static void sendGifts (String input) {
        String username = GameMenuCommands.sendGift.getMatcher(input).group("username");
        String item = GameMenuCommands.sendGift.getMatcher(input).group("item");
        int amount = Integer.parseInt(GameMenuCommands.sendGift.getMatcher(input).group("amount"));
        if (username == null || item == null) {
            System.out.println("Invalid Command!");
            return;
        }
        if (!currentGame.players.contains(findPlayerInGame(username))) {
            System.out.println(RED+"Username '" + username + "' is Unavailable!"+RESET);
            return;
        }
        if (username.equals(currentGame.currentPlayer.getUsername())) {
            System.out.println("You can't Send Gifts to " + RED+"Yourself"+RESET + "!");
            return;
        }
        HumanCommunications f = getFriendship(currentGame.currentPlayer, findPlayerInGame(username));
        if (f == null) {
            System.out.println("There's " + RED+"no Friendship"+RESET + " Among these Users");
            return;
        }


        Result result = f.sendGifts(username, item, amount);
        System.out.println(result);
        if (result.IsSuccess()) {
            Set<User> key = new HashSet<>(Arrays.asList(currentGame.currentPlayer, findPlayerInGame(username)));
            currentGame.conversations.putIfAbsent(key, new ArrayList<>());
            currentGame.conversations.get(key).add(new MessageHandling(currentGame.currentPlayer, findPlayerInGame(username), currentGame.currentPlayer.getNickname() + " Sent you a GIFT. Rate it out of 5!"));
        }
    }
    public static void giveFlowers (String input) {
        String username = GameMenuCommands.giveFlower.getMatcher(input).group("username");
        if (!currentGame.players.contains(findPlayerInGame(username))) {
            System.out.println(RED+"Username is Unavailable!"+RESET);
            return;
        }
        if (username.equals(currentGame.currentPlayer.getUsername())) {
            System.out.println("You can't give Flower to " + RED+"Yourself"+RESET + "!");
            return;
        }
        HumanCommunications f = getFriendship(currentGame.currentPlayer, findPlayerInGame(username));
        if (f == null) {
            System.out.println("There's " + RED+"no Friendship"+RESET + " Among these Users");
            return;
        }
        Result result = f.buyFlowers();
        System.out.println(result);
    }
    public static void propose(String input) {
        String username = GameMenuCommands.propose.getMatcher(input).group("username");
        User wife = findPlayerInGame(username);
        if (wife == null) {
            System.out.println(RED+"Username is Unavailable!"+RESET);
            return;
        }
        if (username.equals(currentGame.currentPlayer.getUsername())) {
            System.out.println("You can't Propose to " + RED+"Yourself"+RESET + "!");
            return;
        }
        HumanCommunications f = getFriendship(currentGame.currentPlayer, wife);
        if (f == null) {
            System.out.println("There's " + RED+"no Friendship"+RESET + " Among these Users");
            return;
        }

        String ring = GameMenuCommands.propose.getMatcher(input).group("ring");
        if (!(ring.equalsIgnoreCase("ring") || ring.equalsIgnoreCase("wedding ring") || ring.equalsIgnoreCase("wedding"))) {
            System.out.println(RED+"Wrong Ring Name!"+RESET);
            System.out.println("'"+ring+"'");
            return;
        }

        Result result = f.propose();
        System.out.println(result);

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
    public static void eatFood (String input) {
        Matcher matcher = GameMenuCommands.eatFood.getMatcher(input);
        if (matcher == null) {
            System.out.println(RED+"Unknown Error!"+RESET);
            return;
        }
        String foodName = matcher.group("food");

        Recipe recipe = Recipe.findRecipeByName(foodName);
        if (recipe == null) {
            System.out.println(RED+"Food Name Unavailable!"+RESET);
            return;
        }

        FoodTypes type = recipe.getType();

        Inventory myInventory = currentGame.currentPlayer.getBackPack().inventory;
        Fridge fridge = currentGame.currentPlayer.getFarm().getHome().getFridge();
        Items i = new Food(type);
        if (checkAmountProductAvailable(i, 1)) {
            myInventory.Items.put(i, myInventory.Items.get(i) - 1);
            myInventory.Items.entrySet().removeIf(entry -> entry.getValue()==null || entry.getValue() <= 0);
        } else if (fridge.items.containsKey(i) && fridge.items.get(i) >= 1) {
            fridge.items.put(i, fridge.items.get(i) - 1);
            fridge.items.entrySet().removeIf(entry -> entry.getValue()==null || entry.getValue() <= 0);
        } else {
            System.out.println(RED+"None of This Item in Fridge or Inventory!"+RESET);
            return;
        }
        currentGame.currentPlayer.setHealth(currentGame.currentPlayer.getHealth() + recipe.getEnergy());
        recipe.applyEffect(currentGame.currentPlayer);

        System.out.println(GREEN+"Eaten Successfully."+RESET);
    }
    public static void exitGame () {
        if (currentGame.currentPlayer != currentUser) {
            System.out.println(RED+"Access Denied!"+RESET);
            return;
        }
        //TODO بیشترین امتیاز و... سیو بشه
        //TODO currently in game
        //TODO سیو کل بازی
    }
    public static void forceTerminate () {
        Scanner scanner = new Scanner(System.in);
        User terminator = currentGame.currentPlayer;
        for (User user: currentGame.players) {
            if (user == terminator)
                continue;
            currentGame.currentPlayer = user;
            System.out.println(currentGame.currentPlayer.getNickname() + ", Do You Agree With the Game Termination?[Y/N]");
            String choice = scanner.next();
            if (!choice.trim().toLowerCase().equals("y")) {
                System.out.println("Vote Failed! The Game won't be Terminated.");
                currentGame.currentPlayer = terminator;
                return;
            }
        }

        //TODO  کارهای ترمینیت کردن مثل پاک کردن فایل های سیو و ریست کردن همه دیتاهای بازیکنا بجز ماکسیمم امتیاز
        for (User user: currentGame.players) {
            user.setCurrently_in_game(false);
            user.setMax_point(user.getPoint());
        }
    }


    // Erfan


    public static void setPlayerLocation () {

        for (User user: currentGame.players) {
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

        User user1 = currentGame.currentPlayer;
        for (User user : currentGame.players) {
            user.increaseHealth(200);

            currentGame.currentPlayer = user;
            user.setFriendshipPoint(new HashMap<>(Map.of(
                NPC.Sebastian, 0,
                NPC.Lia, 0,
                NPC.Abigail, 0,
                NPC.Harvey, 0,
                NPC.Robin, 0)));

            for (NPC npc : NPC.values()) {
                user.setTodayTalking(npc, false);
                user.setTodayGifting(npc, false);
                user.setLevel3Date(npc, currentGame.currentDate);
            }
            advanceItem(new Scythe(), 1);
            advanceItem(new Hoe(HoeType.primaryHoe), 1);
            advanceItem(new Axe(AxeType.primaryAxe), 1);
            advanceItem(new PickAxe(PickAxeType.primaryPickAxe), 1);
            advanceItem(new WateringCan(WateringCanType.PrimaryWateringCan), 1);
            advanceItem(new TrashCan(TrashCanType.primaryTrashCan), 1);

            Home home = user.getFarm().getHome();
            user.setPositionX( 60 * user.topLeftX + home.getTopLeftX() + home.getWidth() / 2);
            user.setPositionY( 60 * user.topLeftY + home.getTopLeftY() + home.getLength());
            user.increaseMoney(1000000 - user.getMoney());
            //advanceItem(new Wood() , 100000);
            //advanceItem(new BasicRock() , 100000);
        }
        currentGame.currentPlayer = user1;
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
            if (currentGame.currentPlayer.Buff_maxEnergy_100_hoursLeft > 0) {
                currentGame.currentPlayer.setBuff_maxEnergy_100_hoursLeft(currentGame.currentPlayer.Buff_maxEnergy_100_hoursLeft - hour);
                if (currentGame.currentPlayer.Buff_maxEnergy_100_hoursLeft < 0)
                    currentGame.currentPlayer.setBuff_maxEnergy_100_hoursLeft(0);
            }
            if (currentGame.currentPlayer.Buff_maxEnergy_50_hoursLeft > 0) {
                currentGame.currentPlayer.setBuff_maxEnergy_50_hoursLeft(currentGame.currentPlayer.Buff_maxEnergy_50_hoursLeft - hour);
                if (currentGame.currentPlayer.Buff_maxEnergy_50_hoursLeft < 0)
                    currentGame.currentPlayer.setBuff_maxEnergy_50_hoursLeft(0);
            }
            if (currentGame.currentPlayer.Buff_farming_hoursLeft > 0) {
                currentGame.currentPlayer.setBuff_farming_hoursLeft(currentGame.currentPlayer.Buff_farming_hoursLeft - hour);
                if (currentGame.currentPlayer.Buff_farming_hoursLeft < 0)
                    currentGame.currentPlayer.setBuff_farming_hoursLeft(0);
            }
            if (currentGame.currentPlayer.Buff_foraging_hoursLeft > 0) {
                currentGame.currentPlayer.setBuff_foraging_hoursLeft(currentGame.currentPlayer.Buff_foraging_hoursLeft - hour);
                if (currentGame.currentPlayer.Buff_foraging_hoursLeft < 0)
                    currentGame.currentPlayer.setBuff_foraging_hoursLeft(0);
            }
            if (currentGame.currentPlayer.Buff_fishing_hoursLeft > 0) {
                currentGame.currentPlayer.setBuff_fishing_hoursLeft(currentGame.currentPlayer.Buff_fishing_hoursLeft - hour);
                if (currentGame.currentPlayer.Buff_fishing_hoursLeft < 0)
                    currentGame.currentPlayer.setBuff_fishing_hoursLeft(0);
            }
            if (currentGame.currentPlayer.Buff_mining_hoursLeft > 0) {
                currentGame.currentPlayer.setBuff_mining_hoursLeft(currentGame.currentPlayer.Buff_mining_hoursLeft - hour);
                if (currentGame.currentPlayer.Buff_mining_hoursLeft < 0)
                    currentGame.currentPlayer.setBuff_mining_hoursLeft(0);
            }




            // Buff implementation
            if (currentGame.currentPlayer.Buff_maxEnergy_100_hoursLeft == 0) currentGame.currentPlayer.setMAX_HEALTH(200);
            if (currentGame.currentPlayer.Buff_maxEnergy_50_hoursLeft == 0) currentGame.currentPlayer.setMAX_HEALTH(200);
            if (currentGame.currentPlayer.Buff_maxEnergy_100_hoursLeft > 0) {
                currentGame.currentPlayer.setMAX_HEALTH(currentGame.currentPlayer.getMAX_HEALTH() + 100);
                currentGame.currentPlayer.setHealth(currentGame.currentPlayer.getHealth() + 100);
                currentGame.currentPlayer.setBuff_maxEnergy_100_hoursLeft(currentGame.currentPlayer.Buff_maxEnergy_100_hoursLeft --);
            }
            if (currentGame.currentPlayer.Buff_maxEnergy_50_hoursLeft > 0) {
                currentGame.currentPlayer.setMAX_HEALTH(currentGame.currentPlayer.getMAX_HEALTH() + 50);
                currentGame.currentPlayer.setHealth(currentGame.currentPlayer.getHealth() + 50);
                currentGame.currentPlayer.setBuff_maxEnergy_50_hoursLeft(currentGame.currentPlayer.Buff_maxEnergy_50_hoursLeft --);
            }
            if (currentGame.currentPlayer.Buff_mining_hoursLeft > 0) currentGame.currentPlayer.setBuff_mining_hoursLeft(currentGame.currentPlayer.Buff_mining_hoursLeft --);
            if (currentGame.currentPlayer.Buff_fishing_hoursLeft > 0) currentGame.currentPlayer.setBuff_fishing_hoursLeft(currentGame.currentPlayer.Buff_fishing_hoursLeft --);
            if (currentGame.currentPlayer.Buff_farming_hoursLeft > 0) currentGame.currentPlayer.setBuff_farming_hoursLeft(currentGame.currentPlayer.Buff_farming_hoursLeft --);
            if (currentGame.currentPlayer.Buff_foraging_hoursLeft > 0) currentGame.currentPlayer.setBuff_foraging_hoursLeft(currentGame.currentPlayer.Buff_foraging_hoursLeft --);

        }
        else
            currentGame.currentPlayer.setBuff_maxEnergy_100_hoursLeft(0);

        DateHour dateHour = currentGame.currentDate.clone();

        dateHour.increaseHour(hour);
        dateHour.increaseDay(day);

        if (dateHour.getHour() > 22) {
            passedOfTime(getDayDifferent(dateHour, currentGame.currentDate), 24 - dateHour.getHour() + 9 + hour);
            return;
        }
        if (dateHour.getHour() < 9) {
            passedOfTime(getDayDifferent(dateHour, currentGame.currentDate), 9 - dateHour.getHour() + hour);
            return;
        }
        int number = getDayDifferent(currentGame.currentDate, dateHour);

        for (int i = 0 ; i < number ; i++) {
            currentGame.currentDate.increaseDay(1);
            fadeToNextDay();
        }
        currentGame.currentDate.increaseHour(dateHour.getHour() - currentGame.currentDate.getHour());
        updateDarknessLevel(currentGame.currentDate.getHour());
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

        for (User user : currentGame.players) {
            user.checkHealth();

            for (NPC npc : NPC.values())
                if (user.getFriendshipLevel(npc) == 3 && user.getLevel3Date(npc) == currentGame.currentDate)
                    user.setLevel3Date(npc, currentGame.currentDate.clone());
        }
        checkSprinkler();

//        if (checkForDeath()) {
//            currentGame.currentPlayer.setSleepTile(
//                getTileByCoordinates(currentGame.currentPlayer.getPositionX(),
//                    currentGame.currentPlayer.getPositionY()));
//            return new Result(false, BRIGHT_RED + "No energy left! It's the next player's turn" + RESET);
//        }

    }

    // energy & Date
    public static void setEnergyInMorning () {
        for (User user : currentGame.players) {
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

        currentGame.currentDate = new DateHour(Season.Spring, 1, 9, 1980);
        currentGame.currentWeather = Weather.Sunny;
        currentGame.tomorrowWeather = Weather.Sunny;

    }
    public static void doSeasonAutomaticTask () {

        currentGame.currentWeather = Weather.valueOf(currentGame.tomorrowWeather.toString());
        currentGame.tomorrowWeather = currentGame.currentDate.getSeason().getWeather();

    }
    public static void doWeatherTask () {

        if (currentGame.currentWeather.equals(Weather.Rainy) || currentGame.currentWeather.equals(Weather.Stormy))
            for (Tile tile : currentGame.bigMap) {
                GameObject object = tile.getGameObject();

                if (object instanceof Tree && !isInGreenHouse(tile))
                    ((Tree) object).setLastWater(currentGame.currentDate);
                if (object instanceof GiantProduct && !isInGreenHouse(tile))
                    ((GiantProduct) object).setLastWater(currentGame.currentDate);
                if (object instanceof ForagingSeeds && !isInGreenHouse(tile))
                    ((ForagingSeeds) object).setLastWater(currentGame.currentDate);
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
                                ((Tree) object).setLastFruit(currentGame.currentDate);

                            else if (object instanceof ForagingCrops && !((ForagingCrops) object).isProtected())
                                ((ForagingCrops) object).delete();

                            else if (object instanceof ForagingSeeds && !((ForagingSeeds) object).isProtected()) {
                                if (((ForagingSeeds) object).getType().isOneTimeUse())
                                    ((ForagingSeeds) object).delete();
                                else
                                    ((ForagingSeeds) object).setLastProduct(currentGame.currentDate);
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

        return (currentGame.currentPlayer.getHealth() <= 0 && !currentGame.currentPlayer.isHealthUnlimited());
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

        for (User user : currentGame.players)
            if (user.getFarm().isInFarm(x, y))
                return true;
        return false;
    }
    public static boolean canGrowGrass (Tile tile) {

        int x = tile.getX();
        int y = tile.getY();

        if (!checkInAllFarm(tile.getX(), tile.getY()))
            return false;

        for (User user : currentGame.players)
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
                        .filter(d -> d.getSeason().contains(currentGame.currentDate.getSeason()))
                        .toList();

                    ForagingSeedsType type = types.get(rand.nextInt(types.size()));

                    tile.setGameObject(new ForagingSeeds(type, currentGame.currentDate));
                } else {

                    List<ForagingCropsType> types = new ArrayList<>(Arrays.stream(ForagingCropsType.values())
                        .filter(d -> d.getSeason().contains(currentGame.currentDate.getSeason()))
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
        for (User user : currentGame.players) {
            int x1 = user.getFarm().getMine().getStartX() + 1;
            int y1 = user.getFarm().getMine().getStartY() + 1;

            List<int[]> positions = new ArrayList<>();
            for (int dx = 0; dx < 4; dx++) {
                for (int dy = 0; dy < 4; dy++) {
                    positions.add(new int[]{x1 + dx, y1 + dy});
                }
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
                    int[] pos = positions.get(posIndex++);
                    Tile tile = getTileByCoordinates(pos[0], pos[1]);

                    if (tile.getGameObject() instanceof Walkable && Math.random() < mineral.getProbability()) {
                        tile.setGameObject(new ForagingMinerals(mineral));
                        break; // بریم سراغ ماده بعدی
                    }
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
    public static Result plantTree (TreesSourceType type1, int dir) {

        if (!checkAmountProductAvailable(new TreeSource(type1), 1))
            return new Result(false, RED+"You don't have this tree source!"+RESET);

        Tile tile = getTileByDir(dir);

        if (!isInGreenHouse(tile))
            if (!type1.getSeason().contains(currentGame.currentDate.getSeason()))
                return new Result(false, RED+"You can't plant this tree in "
                    + currentGame.currentDate.getSeason());

        GameObject object = tile.getGameObject();
        if (object instanceof GreenHouse && !((GreenHouse) object).isCreated())
            return new Result(false, RED+"First you must create green House"+RESET);

        if ((tile.getGameObject() instanceof Walkable &&
            ((Walkable) tile.getGameObject()).getGrassOrFiber().equals("Plowed")) ||
            tile.getGameObject() instanceof GreenHouse) {

            Tree tree = new Tree(type1.getTreeType(), currentGame.currentDate);
            tile.setGameObject(tree);
            advanceItem(new TreeSource(type1), -1);
            return new Result(true, BLUE+"The tree begins its journey"+RESET);
        }
        else
            return new Result(false, RED+"First, you must plow the tile"+RESET);
    }
    public static Result plantMixedSeed (int dir) {

        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;
        MixedSeeds mixedSeeds = new MixedSeeds();

        if (inventory.Items.containsKey(mixedSeeds)) {

            ForagingSeedsType type = mixedSeeds.getSeeds(currentGame.currentDate.getSeason());
            advanceItem(mixedSeeds, -1);
            Tile tile = getTileByDir(dir);

            GameObject object = tile.getGameObject();
            if (object instanceof GreenHouse && !((GreenHouse) object).isCreated())
                return new Result(false, RED+"First you must create green House"+RESET);

            if ((tile.getGameObject() instanceof Walkable &&
                ((Walkable) tile.getGameObject()).getGrassOrFiber().equals("Plowed")) ||
                tile.getGameObject() instanceof GreenHouse) {

                tile.setGameObject(new ForagingSeeds(type, currentGame.currentDate));
                return new Result(true, BRIGHT_BLUE +
                    "The plant "+type.getDisplayName()+" has come to life! \uD83C\uDF31✨" + RESET);
            }
            else
                return new Result(false, RED+"First, you must plow the tile."+RESET);
        }
        return new Result(false, RED + "You don't have Mixed seed!" + RESET);
    }
    public static Result plantForagingSeed (ForagingSeedsType type, int dir) {

        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;

        for (Map.Entry<Items,Integer> entry: inventory.Items.entrySet())

            if (entry.getKey() instanceof ForagingSeeds && ((ForagingSeeds) entry.getKey()).getType().equals(type)) {
                if (entry.getValue() > 0) {

                    Tile tile = getTileByDir(dir);

                    if (!isInGreenHouse(tile))
                        if (!type.getSeason().contains(currentGame.currentDate.getSeason()))
                            return new Result(false, RED + "You can't plant this seed in "
                                + currentGame.currentDate.getSeason() + RESET);

                    GameObject object = tile.getGameObject();
                    if (object instanceof GreenHouse && !((GreenHouse) object).isCreated())
                        return new Result(false, RED+"First you must create green House"+RESET);

                    if (tile.getGameObject() instanceof Walkable && (!((Walkable) tile.getGameObject()).getGrassOrFiber().equals("Plowed")))
                        return new Result(false, RED+"First, you must plow the tile"+RESET);

                    if ((tile.getGameObject() instanceof Walkable &&
                        ((Walkable) tile.getGameObject()).getGrassOrFiber().equals("Plowed")) ||
                        tile.getGameObject() instanceof GreenHouse) {

                        tile.setGameObject(new ForagingSeeds(type, currentGame.currentDate));
                        inventory.Items.put(entry.getKey(), entry.getValue() - 1);
                        return new Result(true, BLUE+"The earth welcomes your seed"+RESET);

                    } else
                        return new Result(false, RED+"You can't plant in this tile"+RESET);
                }
                else
                    return new Result(false, RED + "You don't have this seed!" + RESET);
            }
        return new Result(false, RED + "You don't have this seed!" + RESET);
    }

    // Tools
    public static Result useHoe (int dir) {

        Tile tile = getTileByDir(dir);

        if (tile.getGameObject() instanceof Walkable &&
            ((Walkable) tile.getGameObject()).getGrassOrFiber().equals("Plowed"))
            return new Result(false, RED+"This tile is already plowed!"+RESET);
        if (!(tile.getGameObject() instanceof Walkable))
            return new Result(false, RED+"You can't plow this tile!"+RESET);

        ((Walkable) tile.getGameObject()).setGrassOrFiber("Plowed");
        return new Result(true, BLUE+"Tile("+tile.getX()+","+tile.getY()+") Plowed!"+RESET);
    }
    public static Result useWateringCan (int dir) {

        Tile tile = getTileByDir(dir);
        GameObject object = tile.getGameObject();


        if (object instanceof Lake || object instanceof Well) {
            ((WateringCan) currentGame.currentPlayer.currentTool).makeFullWater();
            return new Result(true, BLUE+"The Watering can is now full. Time to water" +
                " those plants!\uD83D\uDEB0"+RESET);
        }
        else if (object instanceof WaterTank) {

            int amount = ((WaterTank) object).getAmount();
            WateringCan wateringCan = (WateringCan) currentGame.currentPlayer.currentTool;

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

            currentGame.currentPlayer.increaseFarmingAbility(10);
            if (((Tree) object).isHaveFruit()) {

                TreeType type = ((Tree) object).getType();

                if (currentGame.currentPlayer.getBackPack().getType().getRemindCapacity() <= 0 &&
                    (!checkAmountProductAvailable(new TreesProdct(type.getProductType()), 0)))

                    return new Result(false, RED+"Inventory is full"+RESET);

                advanceItem(new TreesProdct(type.getProductType()), type.getHarvestYield());

                currentGame.currentPlayer.increaseFarmingAbility(5);
                ((Tree) object).setLastFruit(currentGame.currentDate.clone());
                return new Result(true, BLUE + "You got " + type.getHarvestYield()
                    + " " + type.getProductType().getDisplayName() + RESET);
            } else
                return new Result(true, RED + "This tree doesn't have fruit" + RESET);
        }
        if (object instanceof ForagingCrops) {

            currentGame.currentPlayer.increaseFarmingAbility(10);
            ForagingCropsType type = ((ForagingCrops) object).getType();

            if (currentGame.currentPlayer.getBackPack().getType().getRemindCapacity() <= 0 &&
                (!checkAmountProductAvailable(new ForagingCrops(type), 0)))


                return new Result(false, RED+"Inventory is full"+RESET);

            advanceItem(new ForagingCrops(((ForagingCrops) object).getType()), 1);
            ((ForagingCrops) object).delete();
            currentGame.currentPlayer.increaseFarmingAbility(5);

            return new Result(true, BLUE+"You got 1 of "+
                BRIGHT_PURPLE + type.getDisplayName()+RESET);
        }
        if (object instanceof ForagingSeeds) {

            currentGame.currentPlayer.increaseFarmingAbility(10);
            if (((ForagingSeeds) object).isHaveProduct()) {

                ForagingSeedsType type = ((ForagingSeeds) object).getType();

                if (currentGame.currentPlayer.getBackPack().getType().getRemindCapacity() <= 0 &&
                    (!checkAmountProductAvailable(new AllCrops(type.getProductType()), 0)))

                    return new Result(false, RED+"Inventory is full"+RESET);

                advanceItem(new AllCrops(type.getProductType()), 1);
                ((ForagingSeeds) object).harvest();
                currentGame.currentPlayer.increaseFarmingAbility(5);
                return new Result(true, BLUE + "You got 1 " + type.getProductType().getDisplayName() + RESET);
            } else
                return new Result(false, RED + "Still growing..." + RESET);
        }
        if (object instanceof GiantProduct) {

            currentGame.currentPlayer.increaseFarmingAbility(10);
            if (((GiantProduct) object).isHaveProduct()) {

                ForagingSeedsType type = ((GiantProduct) object).getType();

                if (currentGame.currentPlayer.getBackPack().getType().getRemindCapacity() <= 0 &&
                    (!checkAmountProductAvailable(new AllCrops(type.getProductType()), 0)))


                    return new Result(false, RED+"Inventory is full"+RESET);

                advanceItem(new AllCrops(type.getProductType()), 10);
                ((GiantProduct) object).harvest();
                currentGame.currentPlayer.increaseFarmingAbility(5);
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
                currentGame.currentPlayer.getBackPack().getType().getRemindCapacity() > 0) {

                if (((Tree) object).getType().equals(TreeType.MapleTree) ||
                    ((Tree) object).getType().equals(TreeType.MysticTree)) {

                    if (checkAmountProductAvailable(
                        new TreesProdct(((Tree) object).getType().getProductType()), 1) ||
                        currentGame.currentPlayer.getBackPack().getType().getRemindCapacity() > 1) {

                        advanceItem(new Wood(), 5);
                        advanceItem(new TreesProdct(((Tree) object).getType().getProductType()), 1);

                        TreesSourceType sourceType = TreesSourceType.fromDisplayName(((Tree) object).getType().getSourceName());
                        if (checkAmountProductAvailable(new TreeSource(sourceType), 1) ||
                            currentGame.currentPlayer.getBackPack().getType().getRemindCapacity() > 1) {
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
                        currentGame.currentPlayer.getBackPack().getType().getRemindCapacity() > 1) {
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

            if (currentGame.currentPlayer.currentTool.healthCost() > 0 && currentGame.currentPlayer.Buff_mining_hoursLeft > 0)
                currentGame.currentPlayer.increaseHealth(1);

            tile.setGameObject(new Walkable());
            currentGame.currentPlayer.increaseMiningAbility(10);

            if (((ForagingMinerals) object).getType().equals(COPPER)) {

                int x = 1;
                if (currentGame.currentPlayer.getLevelMining() >= 2)
                    x = 2;

                if (checkAmountProductAvailable(new BarsAndOres(BarsAndOreType.CopperOre), x) ||
                    currentGame.currentPlayer.getBackPack().getType().getRemindCapacity() > 0) {

                    advanceItem(new BarsAndOres(BarsAndOreType.CopperOre), x);
                    return new Result(false, BRIGHT_BLUE + x+" Cooper ore added" + RESET);
                }
                else
                    return new Result(false, RED+"Ops!!! you destroy Cooper ore" + RESET);
            }
            if (((ForagingMinerals) object).getType().equals(GOLD)) {

                int x = 1;
                if (currentGame.currentPlayer.getLevelMining() >= 2)
                    x = 2;

                if (checkAmountProductAvailable(new BarsAndOres(BarsAndOreType.GoldOre), x) ||
                    currentGame.currentPlayer.getBackPack().getType().getRemindCapacity() > 0) {
                    advanceItem(new BarsAndOres(BarsAndOreType.GoldOre), x);
                    return new Result(false, BRIGHT_BLUE + x + " Gold ore added" + RESET);
                }
                else
                    return new Result(false, RED+"Ops!!! you destroy Gold ore" + RESET);
            }
            if (((ForagingMinerals) object).getType().equals(IRIDIUM)) {

                int x = 1;
                if (currentGame.currentPlayer.getLevelMining() >= 2)
                    x = 2;

                if (checkAmountProductAvailable(new BarsAndOres(BarsAndOreType.IridiumOre), x) ||
                    currentGame.currentPlayer.getBackPack().getType().getRemindCapacity() > 0) {
                    advanceItem(new BarsAndOres(BarsAndOreType.IridiumOre), x);
                    return new Result(false, BRIGHT_BLUE + x + " Iridium ore added" + RESET);
                }
                else
                    return new Result(false, RED+"Ops!!! you destroy Iridium ore" + RESET);
            }
            if (((ForagingMinerals) object).getType().equals(IRON)) {

                int x = 1;
                if (currentGame.currentPlayer.getLevelMining() >= 2)
                    x = 2;

                if (checkAmountProductAvailable(new BarsAndOres(BarsAndOreType.IronOre), x) ||
                    currentGame.currentPlayer.getBackPack().getType().getRemindCapacity() > 0) {
                    advanceItem(new BarsAndOres(BarsAndOreType.IronOre), x);
                    return new Result(false, BRIGHT_BLUE + x + " Iron ore added" + RESET);
                }
                else
                    return new Result(false, RED+"Ops!!! you destroy Iron ore" + RESET);
            }
            else {

                int x = 1;
                if (currentGame.currentPlayer.getLevelMining() >= 2)
                    x = 2;

                if (checkAmountProductAvailable(new ForagingMinerals(((ForagingMinerals) object).getType()), x) ||
                    currentGame.currentPlayer.getBackPack().getType().getRemindCapacity() > 0) {
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

            if (currentGame.currentPlayer.currentTool.healthCost() > 0 && currentGame.currentPlayer.Buff_foraging_hoursLeft > 0)
                currentGame.currentPlayer.increaseHealth(1);

            currentGame.currentPlayer.increaseForagingAbility(10);

            int x = 1;
            if (currentGame.currentPlayer.getLevelForaging() >= 2)
                x = 2;
            tile.setGameObject(new Walkable());

            if (checkAmountProductAvailable(new BasicRock(), x) ||
                currentGame.currentPlayer.getBackPack().getType().getRemindCapacity() > 0) {

                advanceItem(new BasicRock(), x);
                return new Result(false, BRIGHT_BLUE + x + " Stone added" + RESET);
            }
            else
                return new Result(false, RED + "Ops!!! you destroy Stone" + RESET);
        }
        else if (object instanceof CraftingItem) {
            Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;
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

        User saveUser = currentGame.currentPlayer;

        for (User user : currentGame.players)
            for (NPC npc : NPC.values()) {

                currentGame.currentPlayer = user;
                user.setTodayTalking(npc, false);
                user.setTodayGifting(npc, false);

                if (user.getFriendshipLevel(npc) == 3 && Math.random() > 0.5)
                    if (user.getBackPack().getType().getRemindCapacity() > 0 ||
                        checkAmountProductAvailable(npc.getGiftItem(), 1))

                        advanceItem(npc.getGiftItem(), 1);
            }
        currentGame.currentPlayer = saveUser;
    }
    public static String padRight(String text, int length) {

        if (text.length() >= length)
            return text.substring(0, length);

        return text + " ".repeat(length - text.length());
    }
    public static String OneNPCQuestsList (NPC npc) {

        StringBuilder sb = new StringBuilder();

        int width = 100;
        String title = BRIGHT_BLUE + npc.getName() + RESET;
        String quest2;
        String quest3;
        ArrayList<String> requests = new ArrayList<>();
        ArrayList<Integer> numbers = new ArrayList<>(npc.getRequests().values());

        for (Items item : npc.getRequests().keySet())
            requests.add(item.getName());


        String str = BRIGHT_PURPLE + "|" + RESET;
        String check = BLUE + "|" + RESET;

        int padding = (width - 2 - title.length()) / 2;
        sb.append(str)
            .append(" ".repeat(padding + 3))
            .append(title)
            .append(" ".repeat(padding + 6))
            .append(str).append("\n");

        sb.append(str).append(" ".repeat(width - 2)).append(str).append("\n");
        sb.append(str).append(" ".repeat(width - 2)).append(str).append("\n");



        sb.append(str).append(" ").append(padRight(BRIGHT_GREEN+"Quest 1 "+RESET+":", width + 6)).append(str).append("\n");
        sb.append(str).append(" ".repeat(width - 2)).append(str).append("\n");

        String result = BRIGHT_CYAN + numbers.getFirst()+" "+requests.getFirst() + BRIGHT_BLACK + " ---> " + BRIGHT_YELLOW + npc.getReward(1);
        sb.append(str).append(" ".repeat(10)).append(padRight(result, width + 3)).append(str).append("\n");
        sb.append(str).append(" ".repeat(width - 2)).append(str).append("\n");



        if (currentGame.currentPlayer.getFriendshipLevel(npc) >= 1)
            quest2 = BRIGHT_GREEN+"Quest 2 "+RESET+":";
        else
            quest2 = BRIGHT_GREEN+"Quest 2 " + RESET + ": " + RED + "(unlock at friendship level 1)" + RESET;

        sb.append(str).append(" ").append(padRight(quest2, width + 15)).append(str).append("\n");
        sb.append(str).append(" ".repeat(width - 2)).append(str).append("\n");

        String result2 = BRIGHT_CYAN +numbers.get(1)+" "+requests.get(1) + BRIGHT_BLACK + " ---> " + BRIGHT_YELLOW + npc.getReward(2);
        sb.append(str).append(" ".repeat(10)).append(padRight(result2, width + 3)).append(str).append("\n");
        sb.append(str).append(" ".repeat(width - 2)).append(str).append("\n");




        int dif = getDayDifferent(currentGame.currentPlayer.getLevel3Date(npc), currentGame.currentDate);

        if (currentGame.currentPlayer.getFriendshipLevel(npc) >= 3) {
            if (dif > npc.getRequest3DayNeeded())
                quest3 = BRIGHT_GREEN+"Quest 3 "+RESET+":";
            else
                quest3 = BRIGHT_GREEN+"Quest 3 " + RESET + ": " + RED + "(unlock in " + (npc.getRequest3DayNeeded()-dif) + " days later)" + RESET;
        }
        else
            quest3 = BRIGHT_GREEN+"Quest 3 " + RESET + ": " + RED + "(unlock at friendship level 3)" + RESET;

        sb.append(str).append(" ").append(padRight(quest3, width + 15)).append(str).append("\n");
        sb.append(str).append(" ".repeat(width - 2)).append(str).append("\n");

        String result3 = BRIGHT_CYAN +numbers.get(2)+" "+requests.get(2) + BRIGHT_BLACK +" ---> " + BRIGHT_YELLOW +npc.getReward(3);
        sb.append(str).append(" ".repeat(10)).
            append(padRight(result3, width + 3)).append(str).append("\n");
        sb.append(str).append(" ".repeat(width - 2)).append(str).append("\n");

        return sb.toString();
    }
    public static String OneNPCFriendshipList (NPC npc) {

        String str = switch (npc) {
            case Sebastian -> "";
            case Abigail -> "  ";
            case Harvey -> "   ";
            case Lia -> "      ";
            default -> "    ";
        };
        int width = 60;

        String result = str + "Level : " + currentGame.currentPlayer.getFriendshipLevel(npc) +
            "       point : " + currentGame.currentPlayer.getFriendshipPoint(npc);

        return RED+"|" + " ".repeat(width - 2) + "|\n" +
            "| " +BRIGHT_BLUE + padRight(npc.getName() +RESET+ " : " + BRIGHT_GREEN +
            result, width + 6) + RED + "|\n" + RESET;
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
                    currentGame.currentPlayer.getBackPack().getType().getRemindCapacity() <= 0) {

                    int number = 2;
                    if (currentGame.currentPlayer.getFriendshipLevel(npc) > 1)
                        number *= 2;
                    advanceItem(key, -value);
                    advanceItem(new ForagingMinerals(DIAMOND), number);
                    return new Result(true, BRIGHT_BLUE+"You got "+number+" Diamond"+RESET);
                }
                return new Result(true, RED+"Inventory is full"+RESET);
            }
            case Abigail -> {

                currentGame.currentPlayer.increaseFriendshipPoint(NPC.Abigail, 200);
                return new Result(true, BRIGHT_BLUE+"Your friendship level with Abigail increased"+RESET);
            }
            case Harvey -> {

                int number = 750;
                if (currentGame.currentPlayer.getFriendshipLevel(npc) > 1)
                    number *= 2;
                currentGame.currentPlayer.increaseMoney(number);
                return new Result(true, "Your got +"+number+" money");
            }
            case Lia -> {
                int number = 500;
                if (currentGame.currentPlayer.getFriendshipLevel(npc) > 1)
                    number *= 2;
                currentGame.currentPlayer.increaseMoney(number);
                return new Result(true, "Your got +"+number+" money");
            }
            case Robin -> {

                int number = 2000;
                if (currentGame.currentPlayer.getFriendshipLevel(npc) > 1)
                    number *= 2;
                currentGame.currentPlayer.increaseMoney(number);
                return new Result(true, "Your got +"+number+" money");
            }
            default -> {
                return new Result(true, "");
            }
        }
    }
    public static Result doTask2 (NPC npc) {

        if (currentGame.currentPlayer.getFriendshipLevel(npc) < 1)
            return new Result(false, RED+"Your friendship with "+npc.getName()+" needs to grow"+RESET);

        Map.Entry<Items, Integer> entry = new ArrayList<>(npc.getRequests().entrySet()).get(1);
        Items key = entry.getKey();
        int value = entry.getValue();

        if (!checkAmountProductAvailable(key, value))
            return new Result(false, RED+"You don't have enough source"+RESET);

        switch (npc) {

            case Sebastian -> {
                int number = 5000;
                if (currentGame.currentPlayer.getFriendshipLevel(npc) > 1)
                    number *= 2;
                currentGame.currentPlayer.increaseMoney(number);
                return new Result(true, "Your got +"+number+" money");
            }
            case Abigail -> {
                int number = 500;
                if (currentGame.currentPlayer.getFriendshipLevel(npc) > 1)
                    number *= 2;
                // advanceItem(); // TODO ایتما باید کم بشن و تو قسمت نشون دادن اینا همشون داره یه عدد نشون میده
                currentGame.currentPlayer.increaseMoney(number);
                return new Result(true, "Your got +"+number+" money");
            }
            case Harvey -> {
                currentGame.currentPlayer.increaseFriendshipPoint(NPC.Abigail, 200);
                return new Result(true, BRIGHT_BLUE+"Your friendship level with Harvey increased"+RESET);
            }
            case Lia -> {

                if (checkAmountProductAvailable(new MarketItem(MarketItemType.PancakesRecipe), 1) ||
                    currentGame.currentPlayer.getBackPack().getType().getRemindCapacity() > 0) {

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
                if (currentGame.currentPlayer.getFriendshipLevel(npc) > 1)
                    number *= 2;
                currentGame.currentPlayer.increaseMoney(number);
                return new Result(true, "Your got +"+number+" money");
            }
            default -> {
                return new Result(true, "");
            }
        }
    }
    public static Result doTask3 (NPC npc) {

        int dif = getDayDifferent(currentGame.currentPlayer.getLevel3Date(npc), currentGame.currentDate);

        if (currentGame.currentPlayer.getFriendshipLevel(npc) >= 3) {
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
                    currentGame.currentPlayer.getBackPack().getType().getRemindCapacity() > 0) {
                    int number = 50;
                    if (currentGame.currentPlayer.getFriendshipLevel(npc) > 1)
                        number *= 2;
                    advanceItem(key, -value);
                    advanceItem(new ForagingMinerals(QUARTZ), number);
                    return new Result(true, BRIGHT_BLUE+"You got "+number+" Quartz"+RESET);
                }
                return new Result(true, RED+"Inventory is full"+RESET);
            }
            case Abigail -> {
                if (checkAmountProductAvailable(new CraftingItem(CraftType.IridiumSprinkler), 1) ||
                    currentGame.currentPlayer.getBackPack().getType().getRemindCapacity() > 0) {
                    int number = 1;
                    if (currentGame.currentPlayer.getFriendshipLevel(npc) > 1)
                        number *= 2;
                    advanceItem(key, -value);
                    advanceItem(new CraftingItem(CraftType.IridiumSprinkler), number);
                    return new Result(true, BRIGHT_BLUE+"You got "+number+" Iridium sprinkler"+RESET);
                }
                return new Result(true, RED+"Inventory is full"+RESET);
            }
            case Harvey -> {
                if (checkAmountProductAvailable(new MarketItem(MarketItemType.Salad), 1) ||
                    currentGame.currentPlayer.getBackPack().getType().getRemindCapacity() > 0) {
                    int number = 5;
                    if (currentGame.currentPlayer.getFriendshipLevel(npc) > 1)
                        number *= 2;
                    advanceItem(key, -value);
                    advanceItem(new MarketItem(MarketItemType.Salad), number);
                    return new Result(true, BRIGHT_BLUE+"You got "+number+" salad"+RESET);
                }
                return new Result(true, RED+"Inventory is full"+RESET);
            }
            case Lia -> {

                if (checkAmountProductAvailable(new CraftingItem(CraftType.DeluxeScarecrow), 1) ||
                    currentGame.currentPlayer.getBackPack().getType().getRemindCapacity() > 0) {
                    int number = 1;
                    if (currentGame.currentPlayer.getFriendshipLevel(npc) > 1)
                        number *= 2;
                    advanceItem(key, -value);
                    advanceItem(new CraftingItem(CraftType.DeluxeScarecrow), number);
                    return new Result(true, BRIGHT_BLUE+"You got "+number+" ⅾeⅼuxe sⅽareⅽrow"+RESET);
                }
                return new Result(true, RED+"Inventory is full"+RESET);
            }
            case Robin -> {
                int number = 1500;
                if (currentGame.currentPlayer.getFriendshipLevel(npc) > 1)
                    number *= 2;
                currentGame.currentPlayer.increaseMoney(number);
                return new Result(true, "Your got +"+number+" money");
            }
            default -> {
                return new Result(true, "");
            }
        }
    }
}
