package Controller;

import ch.qos.logback.core.encoder.EchoEncoder;
import model.*;
import model.Animall.Animal;
import model.Enum.*;
import model.Plants.Animalproduct;
import model.Animall.BarnOrCage;
import model.Plants.Fish;
import model.Enum.AllPlants.*;
import model.Enum.Commands.GameMenuCommands;
import model.Enum.ItemType.*;
import model.Enum.ToolsType.*;
import model.Enum.WeatherTime.Season;
import model.Enum.ItemType.WallType;
import model.Enum.WeatherTime.Weather;
import model.MapThings.*;
import model.OtherItem.*;
import model.Places.*;
import model.Plants.*;
import model.SaveData.PasswordHashUtil;
import model.ToolsPackage.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.regex.Matcher;

import static model.App.*;
import static model.Color_Eraser.*;
import static model.DateHour.getDayDifferent;
import static model.Enum.AllPlants.ForagingMineralsType.*;

import static model.Enum.AllPlants.ForagingMineralsType.RUBY;
import static model.SaveData.UserDataBase.findUserByUsername;


public class GameController {

    int turnCounter = 0;
    Random rand = new Random();

    public Result addDollar(int amount) {
        currentGame.currentPlayer.increaseMoney(amount);
        return new Result(true , "your money cheated successfully");
    }
    public Result setDollar(int amount) {
        currentGame.currentPlayer.increaseMoney(amount - currentGame.currentPlayer.getMoney());
        return new Result(true , "your money cheated successfully");
    }

    public Result goToMarketMenu () {
        if (MarketType.isInMarket(currentGame.currentPlayer.getPositionX() , currentGame.currentPlayer.getPositionY()) == null) {
            return new Result(false , "you are not in market");
        }
        currentMenu=Menu.MarketMenu;
        return new Result(true , "Welcome to market menu");
    }

    public Result goToHomeMenu() {
        if ( !currentGame.currentPlayer.getFarm().isInHome(currentGame.currentPlayer.getPositionX() , currentGame.currentPlayer.getPositionY())) {
            return new Result(false , RED+"You're Not in Your Home!"+RESET);
        }
        currentMenu=Menu.HomeMenu;
        return new Result(true , BLUE+"Welcome to home menu"+RESET);
    }

    public Result cheatFishingAbility(int amount) {
        currentGame.currentPlayer.increaseFishingAbility(amount);
        return new Result(true , "you cheated fishing successfully");
    }



    public Result addItem(String name ,int amount) {

        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;
        ItemRegistry itemRegistry = new ItemRegistry();
        Items items = null;


        itemRegistry.scanItems("model.Plants");
        if ((items = itemRegistry.nameToItemMap.get(name)) != null) {
            if (inventory.Items.containsKey(items)) {
                inventory.Items.compute(items , (k,v) -> v+amount);
            }
            else if (currentGame.currentPlayer.getBackPack().getType().getRemindCapacity() == 0) {
                return new Result(false , "Not Enough Capacity!");
            }
            else {
                inventory.Items.put(items, amount);
            }
            return new Result(true , name + " Added Successfully");
        }

        itemRegistry.scanItems("model.Places");
        if ((items=itemRegistry.nameToItemMap.get(name)) != null) {
            if (inventory.Items.containsKey(items)) {
                inventory.Items.compute(items , (k,v) -> v+amount);
            }
            else if (currentGame.currentPlayer.getBackPack().getType().getRemindCapacity() == 0) {
                return new Result(false , "Not Enough Capacity!");
            }
            else {
                inventory.Items.put(items, amount);
            }
            return new Result(true , name + " Added Successfully");
        }

        itemRegistry.scanItems("model.ToolsPackage");
        if ((items=itemRegistry.nameToItemMap.get(name)) != null) {
            if (inventory.Items.containsKey(items)) {
                inventory.Items.compute(items , (k,v) -> v+amount);
            }
            else if (currentGame.currentPlayer.getBackPack().getType().getRemindCapacity() == 0) {
                return new Result(false , "Not Enough Capacity!");
            }
            else {
                inventory.Items.put(items, amount);
            }
            return new Result(true , name + " Added Successfully");
        }

        itemRegistry.scanItems("model.OtherItem");
        if ((items=itemRegistry.nameToItemMap.get(name)) != null) {
            if (inventory.Items.containsKey(items)) {
                inventory.Items.compute(items , (k,v) -> v+amount);
            }
            else if (currentGame.currentPlayer.getBackPack().getType().getRemindCapacity() == 0) {
                return new Result(false , "Not Enough Capacity!");
            }
            else {
                inventory.Items.put(items, amount);
            }
            return new Result(true , name + " Added Successfully");
        }

        return new Result(false , name + " not found!");
    }

    public ArrayList<Tile> sortMap(ArrayList<Tile> Map) {
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
    private boolean isInGreenHouse (Tile tile) {

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

    private boolean directionIncorrect(String dir) {

        try {
            int x = Integer.parseInt(dir);
            return x < 1 || x > 8;

        } catch (Exception e) {
            return true;
        }
    }
    public boolean checkAmountProductAvailable (Items items, int number) {

        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;

        if (inventory.Items.containsKey(items)) {
            int amount = inventory.Items.get(items);
            return amount >= number;
        }
        return false;
    }
    private void advanceItem(Items items, int amount) {

        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;

        if (inventory.Items.containsKey(items)) {
            inventory.Items.compute(items, (k, x) -> x + amount);
        }
        else
            inventory.Items.put(items, amount);
    } // برای کم کردن الو چک بشه اون تعداد داریم یا نه



    public Tile getTileByDir (int dir) {

        int x = currentGame.currentPlayer.getPositionX();
        int y = currentGame.currentPlayer.getPositionY();

        if (dir == 1)
            return getTileByCoordinates(x+1, y);
        else if (dir == 2)
            return getTileByCoordinates(x+1, y+1);
        else if (dir == 3)
            return getTileByCoordinates(x, y+1);
        else if (dir == 4)
            return getTileByCoordinates(x-1, y+1);
        else if (dir == 5)
            return getTileByCoordinates(x-1, y);
        else if (dir == 6)
            return getTileByCoordinates(x-1, y-1);
        else if (dir == 7)
            return getTileByCoordinates(x, y-1);
        else if (dir == 8)
            return getTileByCoordinates(x+1, y-1);
        else
            return null;
    }
    public Tile getTileByCoordinates(int x, int y) {
        if (x<0 || y<0 || x>=90 || y>=90) {
            return null;
        }
        Tile targetTile = currentGame.bigMap.get(90 * y + x);
        return targetTile;
    }

                                                    // create initial map
    public void createInitialMine( int x, int y , int topLeftX, int topLeftY, int width , int height) {
        Farm farm = currentGame.currentPlayer.getFarm();

        Mine mine = new Mine(topLeftX + 60 *x , topLeftY + 60 * y ,width , height);
        door MineDoor = new door();
        MineDoor.setDoor(Door.Mine);
        MineDoor.setCharactor('D');
        mine.setCharactor('M');
        Walkable walkable = new Walkable();
        walkable.setCharactor('.');

        for (int i = topLeftX + (60 * x) ; i < topLeftX + (60*x) + width; i++) {
            for (int j = topLeftY + (60 * y); j < topLeftY + (60 * y) + height; j++) {
                if (i == topLeftX + 60 * x + width / 2 && j == topLeftY + 60*y + height-1) {
                    Tile tile = new Tile(i, j, MineDoor);
                    farm.Farm.add(tile);
                    currentGame.bigMap.add(tile);
                } else if (i == topLeftX + 60*x || i == topLeftX + 60*x + width-1 || j == topLeftY + 60*y || j == topLeftY + 60*y + height-1) {
                    Tile tile = new Tile(i, j, mine);
                    farm.Farm.add(tile);
                    currentGame.bigMap.add(tile);
                } else {
                    Tile tile = new Tile(i, j, walkable);
                    farm.Farm.add(tile);
                    currentGame.bigMap.add(tile);
                }
            }
        }
        farm.setMine(mine);
    }
    public void createInitialLake( int x, int y , int topLeftX , int topLeftY , int width , int height) {
        Farm farm = currentGame.currentPlayer.getFarm();
        Lake lake = new Lake(topLeftX, topLeftY, width, height);
        lake.setCharactor('L');
        for (int i = topLeftX + 60 * x; i < topLeftX + 60 * x + width; i++) {
            for (int j = topLeftY + 60 * y; j < topLeftY + 60 * y + height; j++) {
                Tile tile = new Tile(i, j, lake);
                farm.Farm.add(tile);
                currentGame.bigMap.add(tile);
            }
        }
        farm.setLake(lake);

    }

    public void createInitialHouse(int x, int y , int topLeftX , int topLeftY , int width , int height) {
        Farm farm = currentGame.currentPlayer.getFarm();
        Wall wall = new Wall();
        wall.setWallType(WallType.House);
        wall.setCharactor('#');
        door houseDoor = new door();
        houseDoor.setDoor(Door.House);
        houseDoor.setCharactor('D');
        Fridge fridge= new Fridge(topLeftX + 60 * x + width -2 , topLeftY + 60 * y + height - 2);
        fridge.setCharactor('F');
        Home home = new Home(topLeftX + 60 * x, topLeftY + 60 * y,width,height, fridge);
        home.setCharactor('H');
        home.houseDoor = houseDoor;

        for (int i = topLeftX + 60 * x; i < topLeftX + 60 * x + width ; i++) {
            for (int j = topLeftY + 60 * y; j < topLeftY + 60 * y + height ; j++) {
                if (i == topLeftX + 60 * x + width / 2 && j == topLeftY + 60 * y + height-1) {
                    Tile tile = new Tile(i, j, houseDoor);
                    farm.Farm.add(tile);
                    currentGame.bigMap.add(tile);
                }
                else if (i == topLeftX + 60 * x + width -2 && j == topLeftY + 60 * y + height-2) {
                    Tile tile = new Tile(i, j, fridge);
                    farm.Farm.add(tile);
                    currentGame.bigMap.add(tile);
                }
                else if (i == topLeftX + 60 * x || i == topLeftX + 60 * x + width -1 || j==topLeftY+60*y || j==topLeftY+60*y + height-1) {
                    Tile tile = new Tile(i, j, wall);
                    farm.Farm.add(tile);
                    currentGame.bigMap.add(tile);
                }  else {
                    Tile tile = new Tile(i, j, home);
                    farm.Farm.add(tile);
                    currentGame.bigMap.add(tile);
                }
            }
        }
        farm.setHome(home);
    }

    public void createInitialGreenHouse(int x, int y , int topLeftX , int topLeftY , int width , int height) {
        Farm farm = currentGame.currentPlayer.getFarm();
        Wall GreenWall = new Wall();
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

    public void createInitialFarm(int id) {

        long seed=System.currentTimeMillis();
        Farm farm= currentGame.currentPlayer.getFarm();
        Mine mine=null;
        Lake lake=null;
        Home home=null;
        GreenHouse greenHouse=null;
        Fridge fridge=null;

        if (id ==1 ) {
            mine = new Mine (23 , 2 , 6 , 6);
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
        currentGame.farms.add(farm);
    }

    public void buildHall() {
        Walkable walkable=new Walkable();
        UnWalkable unWalkable=new UnWalkable();
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
                    Tile tile=new Tile(i , j , unWalkable);
                    currentGame.bigMap.add(tile);
                }
            }
        }
    }


    public void buildNpcVillage() {
        Wall wall=new Wall();
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
                else if (MarketType.wallOrDoor(i, j) != null) {
                    MarketType marketType = MarketType.wallOrDoor(i, j);
                    if (i== marketType.getTopleftx() + marketType.getWidth() - 1 && j==marketType.getToplefty() +2) {
                        Tile tile=new Tile(i , j , dor);
                        currentGame.bigMap.add(tile);
                    }
                    else {
                        Tile tile=new Tile(i , j , wall);
                        currentGame.bigMap.add(tile);
                    }
                }
                else if (Market.isInMarket(i , j) != null) {
                    Walkable walkable=new Walkable();
                    MarketType marketType = Market.isInMarket(i, j);
                    walkable.setGrassOrFiber(marketType.getName());
                    Tile tile=new Tile(i , j , walkable);
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

    public NPC isInNpc(int x , int y) {
        for (NPC npc : NPC.values()) {
            int tlx=npc.getTopLeftX();
            int tly=npc.getTopLeftY();
            if (x > tlx && x < tlx + npc.getWidth() - 1 && y > tly && y < tly + npc.getHeight() - 1 ) {
                return npc;
            }
        }
        return null;
    }
    public int MapGenerator(int i,int j,long seed , int treeNumber){
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
                Wall wall = new Wall();
                Tile tile = new Tile(i + 60 * currentGame.currentPlayer.topLeftX, j + 60 * currentGame.currentPlayer.topLeftY, wall);
                currentGame.currentPlayer.getFarm().Farm.add(tile);
                currentGame.bigMap.add(tile);
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
            if (-1.2 < noise && noise < -0.9 && treeNumber <16) {
                Tree tree = new Tree(TreeType.OakTree,currentGame.currentDate.clone());
                Tile tile = new Tile(i + 60 * currentGame.currentPlayer.topLeftX, j + 60 * currentGame.currentPlayer.topLeftY, tree);
                currentGame.currentPlayer.getFarm().Farm.add(tile);
                currentGame.bigMap.add(tile);
                return 1;
            }
            else if(noise > -0.9 && noise <-0.5 && treeNumber <16){
                Tree tree = new Tree(TreeType.MapleTree,currentGame.currentDate.clone());
                Tile tile = new Tile(i + 60 * currentGame.currentPlayer.topLeftX, j + 60 * currentGame.currentPlayer.topLeftY, tree);
                currentGame.currentPlayer.getFarm().Farm.add(tile);
                currentGame.bigMap.add(tile);
                return 1;
            }
            else if (noise > -0.5 && noise < - 0.2 && treeNumber <16){
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

    private User getUserByLocation (int j, int i) {

        for (User user : currentGame.players)
            if (j == user.getPositionX() && i == user.getPositionY())
                return user;
        return null;
    }

    public Result print(int startX , int startY , int size){

        User user;

        StringBuilder result = new StringBuilder();

        for (int i=startX ; i<startX + size ; i++) {
            for (int j = startY; j < startY + size; j++) {
                Tile tile = getTileByCoordinates(j, i);

                if ((user = getUserByLocation(j, i) )!= null)
                    result.append(user.getIcon()); // TODO

                else if (tile.getGameObject() instanceof Animal)
                    result.append(tile.getGameObject().getIcon());

                else {
                    if (tile.getGameObject() instanceof UnWalkable)
                        result.append(tile.getGameObject().getIcon()).append(RESET);
                    else
                        result.append(tile.getGameObject().getIcon()).append(RESET); // TODO
                }
            }
            result.append("\n");
        }
        return new Result(true , result.toString());
    }



    public Result walk(int goalX, int goalY) {
        int startX = currentGame.currentPlayer.getPositionX();
        int startY = currentGame.currentPlayer.getPositionY();
        Tile endTile = getTileByCoordinates(goalX, goalY);

        if (endTile==null) {
            return new Result(false,"you can't go to this coordinate");
        }

        PriorityQueue<State> queue = new PriorityQueue<>(Comparator.comparingInt(s -> s.Energy));
        Set<State> visited = new HashSet<>();


        if (checkConditionsForWalk(goalX, goalY) !=null) {
            return checkConditionsForWalk(goalX, goalY);
        }

        for (int dir =1 ; dir<9 ; dir++) {
            Tile tile = getTileByDir(dir);
            if (tile == null ) {
                continue;
            }
            if (checkConditionsForWalk(tile.getX() , tile.getY()) ==null) {
                State state=new State(startX , startY , -1, 0);
                queue.add(state);
            }
        }

        int [] dirx={0,0,1,1,1,-1,-1,-1};
        int [] diry={1,-1,0,1,-1,0,1,-1};

        while (!queue.isEmpty()) {
            State current = queue.poll();
            if (current.x == goalX && current.y == goalY) {
                if (currentGame.currentPlayer.isHealthUnlimited()){
                    currentGame.currentPlayer.setPositionX(goalX);
                    currentGame.currentPlayer.setPositionY(goalY);
                    return new Result(true,"now you are in "+goalX+","+goalY);
                }
                if (currentGame.currentPlayer.getHealth() >= current.Energy ) {
                    currentGame.currentPlayer.increaseHealth(- current.Energy);
                    currentGame.currentPlayer.setPositionX(goalX);
                    currentGame.currentPlayer.setPositionY(goalY);
                    return new Result(true, "you are now in " + goalX + "," + goalY);
                }
                return new Result(false , "your energy is not enough for go to this tile");
            }
            if (visited.contains(current)) {
                continue;
            }
            visited.add(current);
            for (int i=0 ; i<8 ; i++) {
                int x=dirx[i];
                int y=diry[i];
                if (checkConditionsForWalk(x + current.x,y + current.y) !=null) {
                    continue;
                }
                int turnCost= (i + 1 == current.direction || current.direction== - 1) ? 1:10;
                int totalEnergy=current.Energy + turnCost;
                queue.add(new State(x+current.x , y+current.y , i+1 , totalEnergy));
            }
        }

        return new Result(false , "No way to this coordinate");
    }
    private boolean checkTile(Tile tile){
        if (tile==null) {
            return false;
        }
        return tile.getGameObject() instanceof Home || tile.getGameObject() instanceof door
                || (tile.getGameObject() instanceof Walkable)
                || tile.getGameObject() instanceof GreenHouse;
    }

    public Result checkConditionsForWalk(int goalX, int goalY){
        if (goalX <0 || goalX >=90 || goalY <0 || goalY >=90) {
            return new Result(false,"you can't walk out of bounds");
        }

        Tile tile = getTileByCoordinates(goalX, goalY);
        Farm farm = null;


        for (Farm farms : currentGame.farms) {
            if (farms.Farm.contains(tile)) {
                farm = farms;
                break;
            }
        }
        for (User user : currentGame.players) {
            if (user.getFarm().equals(farm)){
                if (user.getSpouse() != null) {
                    if (!user.getSpouse().equals(currentGame.currentPlayer) && !user.equals(currentGame.currentPlayer)) {
                        return new Result(false, "you can't go to this farm");
                    }
                }
                else {
                    if (! user.equals(currentGame.currentPlayer)) {
                        return new Result(false, "you can't go to this farm");
                    }
                }
            }
        }

        if (tile.getGameObject() instanceof GreenHouse) {
            if (!((GreenHouse) tile.getGameObject()).isCreated()){
                return new Result(false,"GreenHouse is not created yet");
            }
        }

        for (User user : currentGame.players) {
            if (user.getPositionX()==goalX && user.getPositionY()==goalY){
                return new Result(true,"you can't go to this coordinate");
            }
        }
        if (!checkTile(tile)){
            return new Result(false,"you can't go to this coordinate");
        }

        for (Market market : currentGame.markets) {
            if (goalX > market.getTopLeftX() && goalY > market.getTopLeftY()){
                if (goalX < market.getTopLeftX() + market.getWidth() && goalY < market.getTopLeftY() + market.getHeight()){
                    if (market.getMarketType().getStartHour() > currentGame.currentDate.getHour() || market.getMarketType().getEndHour() < currentGame.currentDate.getHour()){
                        return new Result(false , "you can't go to Market because it is not open");
                    }
                }
            }
        }
        return null;

    }


    public Result showInventory() {

        Inventory inventory= currentGame.currentPlayer.getBackPack().inventory;
        StringBuilder output = new StringBuilder();
        output.append(BLUE+"\nItems"+RESET + " :").append("\n");

        for (Map.Entry <Items,Integer> entry: inventory.Items.entrySet()){
            if (entry.getKey() instanceof Food) {
                output.append("\t-> ").append(((Food) entry.getKey()).getType().getName()).append(": ").append(entry.getValue()).append('\n');
            }
            else if (entry.getKey() instanceof BasicRock){
                output.append("\t-> ").append("Stone: ").append(entry.getValue()).append('\n');
            }
            else if (entry.getKey() instanceof Wood){
                output.append("\t-> ").append("Wood: ").append(entry.getValue()).append('\n');
            }
            else if (entry.getKey() instanceof ForagingMinerals){
                output.append("\t-> ").append(((ForagingMinerals) entry.getKey()).getType().getDisplayName()).append(": ").append(entry.getValue()).append('\n');
            }
            else if (entry.getKey() instanceof ForagingSeeds){
                output.append("\t-> ").append(((ForagingSeeds) entry.getKey()).getType().getDisplayName()).append(": ").append(entry.getValue()).append('\n');
            }
            else if (entry.getKey() instanceof AllCrops){
                output.append("\t-> ").append(((AllCrops) entry.getKey()).getType().getDisplayName()).append(": ").append(entry.getValue()).append('\n');
            }
            else if (entry.getKey() instanceof ForagingCrops) {
                output.append("\t-> ").append(((ForagingCrops) entry.getKey()).getType().getDisplayName()).append(": ").append(entry.getValue()).append('\n');
            }
            else if (entry.getKey() instanceof TreeSource){
                output.append("\t-> ").append(((TreeSource) entry.getKey()).getType().getDisplayName()).append(": ").append(entry.getValue()).append('\n');
            }
            else if (entry.getKey() instanceof TreesProdct) {
                output.append("\t-> ").append(entry.getKey().getName()).append(": ").append(entry.getValue()).append('\n');
            }
            else if (entry.getKey() instanceof Axe ){
                output.append("\t-> ").append(((Axe) entry.getKey()).getType().getDisplayName()).append('\n');
            }
            else if (entry.getKey() instanceof FishingPole){
                output.append("\t-> ").append(((FishingPole) entry.getKey()).type.name()).append('\n');
            }
            else if (entry.getKey() instanceof Hoe){
                output.append("\t-> ").append(((Hoe) entry.getKey()).getType().getDisplayName()).append('\n');
            }
            else if (entry.getKey() instanceof PickAxe){
                output.append("\t-> ").append(((PickAxe) entry.getKey()).getType().getDisplayName()).append('\n');
            }
            else if (entry.getKey() instanceof WateringCan){
                output.append("\t-> ").append(((WateringCan) entry.getKey()).getType().getDisplayName()).append('\n');
            }
            else if (entry.getKey() instanceof TrashCan){
                output.append("\t-> ").append(((TrashCan) entry.getKey()).type.getDisplayName()).append('\n');
            }
            else if (entry.getKey() instanceof Tools){
                output.append("\t-> ").append(entry.getKey().getName()).append('\n');
            }
            else if (entry.getKey() instanceof MarketItem) {
                output.append("\t-> ").append(((MarketItem) entry.getKey()).getType().getName()).append(": ").append(entry.getValue()).append('\n');
            }
            else if (entry.getKey() instanceof Fish) {
                output.append("\t-> ").append(((Fish) entry.getKey()).getType().getName()).append(": ") .append(((Fish) entry.getKey()).getQuantity().getName()).append(" ").append(entry.getValue()).append('\n');
            }
            else if (entry.getKey() instanceof Animalproduct) {
                output.append("\t-> ").append(((Animalproduct) entry.getKey()).getType().getName()).append("(").append(((Animalproduct) entry.getKey()).getQuantity().getName()).append("): ").append(entry.getValue()).append('\n');
            }
            else if (entry.getKey() instanceof CraftingItem) {
                output.append("\t-> ").append(((CraftingItem) entry.getKey()).getType().getName()).append(": ").append(entry.getValue()).append('\n');
            }
            else if (entry.getKey() instanceof ArtisanProduct) {
                output.append("\t-> ").append(((ArtisanProduct) entry.getKey()).getType().getName()).append(": ").append(entry.getValue()).append('\n');
            }
            else if (entry.getKey() instanceof BarsAndOres) {
                output.append("\t-> ").append(((BarsAndOres) entry.getKey()).getType().getName()).append(": ").append(entry.getValue()).append('\n');
            }
            else if (entry.getKey() instanceof MixedSeeds) {
                output.append("Mixed Seeds : ").append(entry.getValue()).append("\n");
            }
        }

        return new Result(true,output.toString());
    }
    private Result increaseMoney(Integer amount , int price , Items items,String name , Integer reminder) {
        int percent=0;
        for (Map.Entry<Items,Integer> entry: currentGame.currentPlayer.getBackPack().inventory.Items.entrySet()) {
            if (entry.getKey() instanceof TrashCan){
                percent= ((TrashCan) entry.getKey()).type.getPercent();
                break;
            }
        }

        if (amount ==null || amount.equals(reminder)) {
            int increase=(reminder * percent *price)/100;
            TrashCan.removeItem(increase,currentGame.currentPlayer.getBackPack().inventory.Items, items, reminder);
            return new Result(true,name + " completely removed from your inventory");
        }
        if (amount > reminder) {
            return new Result(false,"not enough "+name+" "+"in your inventory for remove");
        }
        int increase = (reminder * percent * price) / 100;
        TrashCan.removeItem(increase,currentGame.currentPlayer.getBackPack().inventory.Items, items, reminder);
        return new Result(true , amount + " "+name+" "+"removed from your inventory");

    }



    public Result removeItemToTrashcan (String name, String amount){
        Integer number=null;
        if (amount != null) {
            number = Integer.parseInt(amount.trim());
        }

        Inventory inventory=currentGame.currentPlayer.getBackPack().inventory;
        for (Map.Entry<Items,Integer> entry: inventory.Items.entrySet()){

//            if (entry.getKey() instanceof Wood){
//                if (name.equals(Wood.name)) {
//                    return increaseMoney(number, Wood.price, (Wood) entry.getKey(), name, entry.getValue());
//                }
//            }
//            if (entry.getKey() instanceof BasicRock){
//                if (name.equals("Stone")) {
//                    return increaseMoney(number, BasicRock.price, (BasicRock) entry.getKey(), name, entry.getValue());
//                }
//            }
//
//            if (entry.getKey() instanceof ForagingMinerals){
//                if (((ForagingMinerals) entry.getKey()).getType().getDisplayName().equals(name)){
//                    return increaseMoney(number,((ForagingMinerals) entry.getKey()).getType().getPrice(),entry.getKey(), name,entry.getValue());
//                }
//            }
//
//
//            if (entry.getKey() instanceof AllCrops){
//                if (((AllCrops) entry.getKey()).getType().getDisplayName().equals(name)){
//                    return increaseMoney(number, ((AllCrops) entry.getKey()).getType().getPrice(), entry.getKey(), name,entry.getValue());
//                }
//            }
//
//            if (entry.getKey() instanceof ForagingCrops){
//                if (((ForagingCrops) entry.getKey()).getType().getDisplayName().equals(name)){
//                    return increaseMoney(entry.getValue(),((ForagingCrops) entry.getKey()).getType().getPrice(), entry.getKey(), name,entry.getValue());
//                }
//            }
//            if (entry.getKey() instanceof Tools){
//                return new Result(false,"you can't remove "+name+"because it is a tool");
//            }

            if (entry.getKey().getName().equals(name)) {
                if (entry.getKey().getSellPrice() == 0) {
                    continue;
                }
                return increaseMoney(number , entry.getKey().getSellPrice() , entry.getKey() , name , entry.getValue()) ;
            }

        }
        return new Result(false , name + " not found");
    }

                                                // Fish
    public boolean checkCoordinateForFishing(){
        int [] x={1,1,1,0,0,-1,-1,-1};
        int [] y={1,0,-1,1,-1,-1,0,1};
        for (int i=0;i<8;i++){
            if (getTileByCoordinates(currentGame.currentPlayer.getPositionX() +x[i],currentGame.currentPlayer.getPositionY() +y[i]).
                    getGameObject() instanceof Lake){
                return true;
            }
        }
        return false;
    }

    public FishingPole isFishingPoleTypeExist(String name){
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

    public Quantity productQuantity(double quantity){

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

    public Result addFishToInventory(FishingPole fishingPole) {
        Inventory inventory=currentGame.currentPlayer.getBackPack().inventory;
        double random = Math.random() + 0.3;
        int x = (int) (random * currentGame.currentWeather.getFishing() * (currentGame.currentPlayer.getLevelFishing() + 3));
        int numberOfFish = Math.min(6, x);
        StringBuilder result = new StringBuilder("number of Fishes: " + numberOfFish + "\n");
        ArrayList<Fish> fishes = new ArrayList<>();

        for (int i = 0; i < numberOfFish; i++) {

            double rand = Math.random() + 0.4;
            double quantity = (rand * (currentGame.currentPlayer.getLevelFishing() + 2) *
                    fishingPole.type.getCoefficient()) / (7 - 2*currentGame.currentWeather.getFishing());
            Quantity fishQuantity = productQuantity(quantity);

            if (fishingPole.type.equals(FishingPoleType.TrainingRod)) {

                switch (currentGame.currentDate.getSeason()) {
                    case Spring:
                        Fish springFish = new Fish(FishType.Herring, fishQuantity);
                        fishes.add(springFish);
                        result.append(springFish.getType().getName()).append(" ").append(springFish.getQuantity().getName()).append("\n");
                        break;
                    case Summer:
                        Fish summerFish = new Fish(FishType.Sunfish, fishQuantity);
                        fishes.add(summerFish);
                        result.append(summerFish.getType().getName()).append(" ").append(summerFish.getQuantity().getName()).append("\n");
                        break;
                    case Fall:
                        Fish fallFish = new Fish(FishType.Sardine, fishQuantity);
                        fishes.add(fallFish);
                        result.append(fallFish.getType().getName()).append(" ").append(fallFish.getQuantity().getName()).append("\n");
                        break;
                    case Winter:
                        Fish winterFish = new Fish(FishType.Perch, fishQuantity);
                        fishes.add(winterFish);
                        result.append(winterFish.getType().getName()).append(" ").append(winterFish.getQuantity().getName()).append("\n");
                        break;
                    default:
                        break;
                }

            }

            else if (rand <= 0.2 || ( rand > 0.8 && rand <= 0.85 && currentGame.currentPlayer.getLevelFishing()!=4) ){

                switch (currentGame.currentDate.getSeason()) {
                    case Spring:
                        Fish springFish = new Fish(FishType.Flounder, fishQuantity);
                        fishes.add(springFish);
                        result.append(springFish.getType().getName()).append(" ").append(springFish.getQuantity().getName()).append("\n");
                        break;
                    case Summer:
                        Fish summerFish = new Fish(FishType.Tilapia, fishQuantity);
                        fishes.add(summerFish);
                        result.append(summerFish.getType().getName()).append(" ").append(summerFish.getQuantity().getName()).append("\n");
                        break;
                    case Fall:
                        Fish fallFish = new Fish(FishType.Salmon, fishQuantity);
                        fishes.add(fallFish);
                        result.append(fallFish.getType().getName()).append(" ").append(fallFish.getQuantity().getName()).append("\n");
                        break;
                    case Winter:
                        Fish winterFish = new Fish(FishType.Midnight_Carp, fishQuantity);
                        fishes.add(winterFish);
                        result.append(winterFish.getType().getName()).append(" ").append(winterFish.getQuantity().getName()).append("\n");
                    default:
                        break;
                }
            }
            else if (rand <= 0.4 || (rand > 0.85 && rand <= 0.9 && currentGame.currentPlayer.getLevelFishing() != 4)) {

                switch (currentGame.currentDate.getSeason()) {
                    case Spring:
                        Fish springFish = new Fish(FishType.Lionfish, fishQuantity);
                        fishes.add(springFish);
                        result.append(springFish.getType().getName()).append(" ").append(springFish.getQuantity().getName()).append("\n");
                        break;
                    case Summer:
                        Fish summerFish = new Fish(FishType.Dorado, fishQuantity);
                        fishes.add(summerFish);
                        result.append(summerFish.getType().getName()).append(" ").append(summerFish.getQuantity().getName()).append("\n");
                        break;
                    case Fall:
                        Fish fallFish = new Fish(FishType.Sardine, fishQuantity);
                        fishes.add(fallFish);
                        result.append(fallFish.getType().getName()).append(" ").append(fallFish.getQuantity().getName()).append("\n");
                        break;
                    case Winter:
                        Fish winterFish = new Fish(FishType.Squid, fishQuantity);
                        fishes.add(winterFish);
                        result.append(winterFish.getType().getName()).append(" ").append(winterFish.getQuantity().getName()).append("\n");
                        break;
                    default:
                        break;
                }
            }
            else if (rand <= 0.6 || (rand > 0.9 && rand <= 0.95 && currentGame.currentPlayer.getLevelFishing() != 4)) {

                switch (currentGame.currentDate.getSeason()) {
                    case Spring:
                        Fish springFish = new Fish(FishType.Herring, fishQuantity);
                        fishes.add(springFish);
                        result.append(springFish.getType().getName()).append(" ").append(springFish.getQuantity().getName()).append("\n");
                        break;
                    case Summer:
                        Fish summerFish = new Fish(FishType.Sunfish, fishQuantity);
                        fishes.add(summerFish);
                        result.append(summerFish.getType().getName()).append(" ").append(summerFish.getQuantity().getName()).append("\n");
                        break;
                    case Fall:
                        Fish fallFish = new Fish(FishType.Shad, fishQuantity);
                        fishes.add(fallFish);
                        result.append(fallFish.getType().getName()).append(" ").append(fallFish.getQuantity().getName()).append("\n");
                        break;
                    case Winter:
                        Fish winterFish = new Fish(FishType.Tuna, fishQuantity);
                        fishes.add(winterFish);
                        result.append(winterFish.getType().getName()).append(" ").append(winterFish.getQuantity().getName()).append("\n");
                        break;
                    default:
                        break;
                }

            }
            else if (rand <= 0.8 || (rand > 0.95 && currentGame.currentPlayer.getLevelFishing() != 4)) {

                switch (currentGame.currentDate.getSeason()) {
                    case Spring:
                        Fish springFish = new Fish(FishType.Ghostfish, fishQuantity);
                        fishes.add(springFish);
                        result.append(springFish.getType().getName()).append(" ").append(springFish.getQuantity().getName()).append("\n");
                        break;
                    case Summer:
                        Fish summerFish = new Fish(FishType.Rainbow_Trout, fishQuantity);
                        fishes.add(summerFish);
                        result.append(summerFish.getType().getName()).append(" ").append(summerFish.getQuantity().getName()).append("\n");
                        break;
                    case Fall:
                        Fish fallFish = new Fish(FishType.Blue_Discus, fishQuantity);
                        fishes.add(fallFish);
                        result.append(fallFish.getType().getName()).append(" ").append(fallFish.getQuantity().getName()).append("\n");
                        break;
                    case Winter:
                        Fish winterFish = new Fish(FishType.Perch, fishQuantity);
                        fishes.add(winterFish);
                        result.append(winterFish.getType().getName()).append(" ").append(winterFish.getQuantity().getName()).append("\n");
                        break;
                    default:
                        break;
                }

            }
            else {
                if (currentGame.currentPlayer.getLevelFishing() == 4){

                    switch (currentGame.currentDate.getSeason()){
                        case Spring:
                            Fish springFish= new Fish(FishType.Legend,fishQuantity);
                            fishes.add(springFish);
                            result.append(springFish.getType().getName()).append(" ").append(springFish.getQuantity().getName()).append("\n");
                            break;
                        case Summer:
                            Fish summerFish= new Fish(FishType.Dorado,fishQuantity);
                            fishes.add(summerFish);
                            result.append(summerFish.getType().getName()).append(" ").append(summerFish.getQuantity().getName()).append("\n");
                            break;
                        case Fall:
                            Fish fallFish= new Fish(FishType.Squid,fishQuantity);
                            fishes.add(fallFish);
                            result.append(fallFish.getType().getName()).append(" ").append(fallFish.getQuantity().getName()).append("\n");
                            break;
                        case Winter:
                            Fish winterFish= new Fish(FishType.Tuna,fishQuantity);
                            fishes.add(winterFish);
                            result.append(winterFish.getType().getName()).append(" ").append(winterFish.getQuantity().getName()).append("\n");
                            break;
                    }

                }
            }
        }

        boolean top=currentGame.currentPlayer.getLevelFishing() == 4;
        currentGame.currentPlayer.increaseHealth(-Math.min ( ((FishingPole) currentGame.currentPlayer.currentTool).type.costEnergy(top) , currentGame.currentPlayer.getHealth()) ) ;
        currentGame.currentPlayer.increaseFishingAbility(5);
        for (Fish fish : fishes) {
            if (inventory.Items.containsKey(fish)) {
                inventory.Items.compute(fish , (k,v) -> v+1);
            }
            else if (currentGame.currentPlayer.getBackPack().getType().getRemindCapacity() !=0) {
                inventory.Items.put(fish , 1);
            }
        }

        return new Result(true, result.toString());
    }


    public Result Fishing(String fishingPoleType) {
        if (!(currentGame.currentPlayer.currentTool instanceof FishingPole)) {
            return new Result(false, "your current tool is not a FishingPole!");
        }

        if (!checkCoordinateForFishing()) {
            boolean top=currentGame.currentPlayer.getLevelFishing() == 4;
            currentGame.currentPlayer.increaseHealth(-Math.min ( ((FishingPole) currentGame.currentPlayer.currentTool).type.costEnergy(top) , currentGame.currentPlayer.getHealth()) ) ;
            return new Result(false, "you can't fishing because lake is not around you");
        }
        if (isFishingPoleTypeExist(fishingPoleType) == null) {
            return new Result(false, "No such fishing pole exist!");
        }

        return addFishToInventory(isFishingPoleTypeExist(fishingPoleType));
    }



    public boolean checkTilesForCreateBarnOrCage(int x, int y, int width, int height) {
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




                                                //
    public Animal getAnimalByName(String animalName) {
        for (BarnOrCage barnOrCage:currentGame.currentPlayer.BarnOrCages){
            for (Animal animal: barnOrCage.animals){
                if (animal.getName().equals(animalName)){
                    return animal;
                }
            }
        }
        return null;
    }

    public Result pet(String petName) {
        int [] x={1,1,1,0,0,-1,-1,-1};
        int [] y={1,0,-1,1,-1,-1,0,1};

        for (int i = 0; i < 8; i++) {
            Tile tile = getTileByCoordinates(currentGame.currentPlayer.getPositionX() + x[i], currentGame.currentPlayer.getPositionY() + y[i]);
            if (tile.getGameObject() instanceof Animal) {
                Animal animal = (Animal) tile.getGameObject();
                if (animal.getName().equals(petName)) {
                    animal.increaseFriendShip(15);
                    animal.setPetToday(true);
                    return new Result(true, petName + " petted successfully!");
                }
            }
        }
        return new Result(false,petName+"  doesn't exist!");
    }

    public Result animals() {
        StringBuilder result= new StringBuilder();
        for (BarnOrCage barnOrCage : currentGame.currentPlayer.BarnOrCages) {
            for (Animal animal : barnOrCage.animals){
                result.append(animal.getName())
                        .append(" Friendship: ").append(animal.getFriendShip()).append('\n')
                        .append(" petToday: ").append(animal.isPetToday()).append('\n')
                        .append("feedToday: ").append(animal.isFeedToday()).append("\n");
            }
        }
        return new Result(true, result.toString());
    }

    public Result shepherdAnimals(String x1, String y1, String name) {

        int goalX=Integer.parseInt(x1);
        int goalY=Integer.parseInt(y1);

        if (checkShepherdAnimals(goalX , goalY , name) != null) {
            return checkShepherdAnimals(goalX , goalY , name);
        }


        Animal animal = getAnimalByName(name);
        if (animal.getType().equals(AnimalType.pig) && currentGame.currentDate.getSeason().equals(Season.Winter)) {
            return new Result(false , "Pigs can't go out because we are in winter");
        }
        Walkable walkable=new Walkable();

        int [] x = {1,1,1,0,0,-1,-1,-1};
        int [] y = {1,0,-1,1,-1,-1,0,1};
        Queue<Tile> queue = new LinkedList<>();
        Set<Tile> tiles = new HashSet<>();
        for (int i = 0; i < 8; i++) {
            if (checkTileForAnimalWalking(animal.getPositionX() + x[i] , animal.getPositionY() + y[i] )) {
                queue.add(getTileByCoordinates(animal.getPositionX() + x[i] , animal.getPositionY() + y[i]));
            }
        }
        tiles.add(getTileByCoordinates(animal.getPositionX() , animal.getPositionY() ));

        while (!queue.isEmpty()) {
            Tile tile=queue.poll();
            tiles.add(tile);
            if (tile.getX() == goalX && tile.getY() == goalY) {
                tile.setGameObject(animal);
                getTileByCoordinates(animal.getPositionX(), animal.getPositionY() ).setGameObject(walkable);
                animal.setPositionX(goalX);
                animal.setPositionY(goalY);
                return eatFiberByAnimal(animal);
            }

            for (int i = 0; i < 8; i++) {
                if (! checkTileForAnimalWalking(tile.getX() + x[i] , tile.getY() + y[i] ) ) {
                    continue;
                }
                if (getTileByCoordinates(tile.getX() + x[i] , tile.getY() + y[i]) == null) {
                    continue;
                }
                if (tiles.contains(getTileByCoordinates(tile.getX() + x[i] , tile.getY() + y[i]))) {
                    continue;
                }
                queue.add(getTileByCoordinates(tile.getX() + x[i], tile.getY() + y[i]));
            }
        }
        return new Result(false , "there is no way for animal to go to this coordinate!");
    }

    public Result eatFiberByAnimal(Animal animal) {
        int [] x = {1,1,1,0,0,-1,-1,-1};
        int [] y = {1,0,-1,1,-1,-1,0,1};

        for (int i = 0; i < 8; i++) {
            Tile tile = getTileByCoordinates(animal.getPositionX() + x[i] , animal.getPositionY() + y[i]);
            if (tile == null) {
                continue;
            }
            if (tile.getGameObject() instanceof Walkable) {
                if (((Walkable) tile.getGameObject()).getGrassOrFiber().equals("Fiber") ||
                    ((Walkable) tile.getGameObject()).getGrassOrFiber().equals("Grass")) {
                    tile.setGameObject(new Walkable());
                    animal.setFeedToday(true);
                }
            }
        }
        if (animal.isFeedToday()) {
            return new Result(true , BLUE+animal.getName() + " is fed successfully"+RESET);
        }
        return new Result(true , RED+animal.getName() + " Shepherd successfully"+RESET);
    }

    private Result checkShepherdAnimals(int goalX, int goalY, String name) {
        if (goalX < 0 || goalX >90 || goalY < 0 || goalY >90) {
            return new Result(false , "you can't shepherd animals out of bounds!");
        }
        Tile tile = getTileByCoordinates(goalX , goalY );
        if (!(tile.getGameObject() instanceof Walkable)) {
            return new Result(false , "yot can't shepherd animals on this coordinate!");
        }
        if (currentGame.currentWeather.equals(Weather.Snowy) || currentGame.currentWeather.equals(Weather.Rainy) || currentGame.currentWeather.equals(Weather.Stormy) ) {
            return new Result(false , "The weather conditions isn't suitable");
        }

        Animal animal=null;

        for (BarnOrCage barnOrCage : currentGame.currentPlayer.BarnOrCages) {
            for (Animal animal1 : barnOrCage.animals) {
                if (animal1.getName().equals(name)) {
                    animal = animal1;
                    break;
                }
            }
        }

        if (animal == null) {
            return new Result(false , "animal not found!");
        }

        return null;

    }

    public boolean checkTileForAnimalWalking(int x, int y) {
        Tile tile = getTileByCoordinates(x , y );
        if (tile == null) {
            return false;
        }
        if (!(tile.getGameObject() instanceof Walkable) && !(tile.getGameObject() instanceof BarnOrCage)) {
            return false;
        }
        return true;
    }

    public Result feedHay(String name) {
        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;
        Animal animal=getAnimalByName(name);
        if (animal==null) {
            return new Result(false , "animal not found!");
        }
        MarketItem marketItem = new MarketItem(MarketItemType.Hay);
        if (inventory.Items.containsKey(marketItem)) {
            inventory.Items.compute(marketItem , (k,v) -> v-1);
            inventory.Items.entrySet().removeIf(entry -> entry.getValue()==0);
            animal.setFeedToday(true);
            return new Result(true, BLUE+"you fed "+name+" successfully!" + RESET);
        }

        return new Result(false , RED + "You don't have Hay in your inventiry!" + RESET);
    }
    public Result cheatSetFriendship( String name , Integer amount ) {
        Animal animal=getAnimalByName(name);
        if (animal==null) {
            return new Result(false , "animal not found!");
        }
        if (amount > 1000 || amount <0) {
            return new Result(false , RED+ "You should say a number between 1 and 1000" + RESET);
        }
        animal.increaseFriendShip(amount - animal.getFriendShip());
        return new Result(true, "friendship cheated successfully!");
    }
    public boolean animalIsOnBarnOrCage(Animal animal) {
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
    public void calculateAnimalsFriendship() {
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
    public boolean checkBigProduct(Animal animal) {
        double possibility=( animal.getFriendShip() + (150 * animal.getRandomProduction()) ) / 1500;
        return animal.getRandomChance() < possibility;
    }
    public void checkAnimalProduct() {
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
    public boolean checkPeriod(Animal animal) {
        return currentGame.currentDate.getDate() - animal.getLastProduceDay() == 0;
    }
    public Result getProductAnimals(String name) {
        Animal animal=getAnimalByName(name);

        if (! animal.isFeedPreviousDay()){
            return new Result(false , "No Product because you didn't feed " + animal.getName() + " in previous day");
        }
        if (animal.isProductCollected()) {
            return new Result(false , "Product was collected before");
        }
        if (! checkPeriod(animal)) {
            return new Result(false , "It's not time yet. This animal isn't ready to produce again");
        }
        if ( ! isNeighbor(currentGame.currentPlayer.getPositionX() , currentGame.currentPlayer.getPositionY() , animal.getPositionX() , animal.getPositionY())) {
            return new Result(false , "The animal is not in Neighbor Tile");
        }

        if (animal.getType().equals(AnimalType.cow) || animal.getType().equals(AnimalType.goat) || animal.getType().equals(AnimalType.sheep)) {
            if (sheepOrGoatOrCow(animal) == null) {
                return sheepOrGoatOrCow(animal);
            }
        }


        double Quantity=((double) animal.getFriendShip() / 1000) * (0.5 * (1 + animal.getRandomProduction()));
        Quantity quantity=productQuantity(Quantity);

        Animalproduct animalproduct = new Animalproduct(animal.getProductType(), quantity);
        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;
        if (inventory.Items.containsKey(animalproduct)) {
            inventory.Items.compute(animalproduct , (k,v) -> v+1);
        }
        else {
            inventory.Items.put(animalproduct, 1);
        }

        animal.setProductCollected(true);

        return new Result(true , "product "+ animal.getProductType().getName() + "collected successfully");
    }
    public Result produces() {
        StringBuilder result=new StringBuilder();
        result.append("Produces :\n");
        for (BarnOrCage barnOrCage : currentGame.currentPlayer.BarnOrCages) {
            for (Animal animal : barnOrCage.animals) {
                result.append(animal.getName()).append(",  Remaining Produces: ");
                if (animal.isFeedPreviousDay() && checkPeriod(animal) && ! animal.isProductCollected()) {
                    result.append(animal.getProductType().getName()).append(", ");

                    double Quantity=((double) animal.getFriendShip() / 1000) * (0.5 * (1 + animal.getRandomProduction()));
                    Quantity quantity=productQuantity(Quantity);

                    result.append("Quantity: ").append(quantity.getName());

                }
                result.append("\n");
            }
        }
        return new Result(true , result.toString());
    }
    public Result sheepOrGoatOrCow(Animal animal) {
        if (animal.getType().equals(AnimalType.sheep) ) {
            if (!(currentGame.currentPlayer.currentTool instanceof Shear)) {
                return new Result(false , "for collect wool you should use shear");
            }
            currentGame.currentPlayer.increaseMoney( (int) (4 * currentGame.currentWeather.getEnergyCostCoefficient()));
            animal.increaseFriendShip(5);
        }
        if (animal.getType().equals(AnimalType.goat)) {
            if (!(currentGame.currentPlayer.currentTool instanceof MilkPail)) {
                return new Result(false , "for collect milk you should use MilkPail");
            }
            currentGame.currentPlayer.increaseMoney((int) (4 * currentGame.currentWeather.getEnergyCostCoefficient()));
            animal.increaseFriendShip(5);
        }
        if (animal.getType().equals(AnimalType.cow)) {
            if (!(currentGame.currentPlayer.currentTool instanceof MilkPail)) {
                return new Result(false , "for collect milk you should use MilkPail");
            }
            currentGame.currentPlayer.increaseMoney((int) (4 * currentGame.currentWeather.getEnergyCostCoefficient()));
            animal.increaseFriendShip(5);
        }

        return null;
    }

    public Result removeAnimal(Animal animal) {
        for (BarnOrCage barnOrCage : currentGame.currentPlayer.BarnOrCages) {
            for (Animal animal2 : barnOrCage.animals) {
                if (animal.equals(animal2)) {
                    barnOrCage.getAnimals().remove(animal2);
                    return new Result(true , animal.getName() + " was sold successfully");
                }
            }
        }
        return null;
    }
    public Result sellAnimal(String name) {
        Animal animal=getAnimalByName(name);

        if (animal == null) {
            return new Result(false , "Animal not found");
        }
        for (Tile tile : currentGame.bigMap) {
            if (tile.getGameObject().equals(animal)) {
                tile.setGameObject(new Walkable());
                break;
            }
        }
        double x = animal.getFriendShip()/1000 + 0.3;
        currentGame.currentPlayer.increaseMoney((int) (animal.getType().getPrice() * x) );
        return removeAnimal(animal);

    }


    private Result placeBomb(Tile tile , String name , Items items) {
        if (currentGame.currentPlayer.getFarm().isInHome(tile.getX(), tile.getY())) {
            return new Result(false , "you can't place Bomb in your Home because it is dangerous");
        }

        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;
        int domain=0;
        CraftType Bomb=null;

        for (CraftType craftType:CraftType.values()) {
            if (craftType.name().equals(name)) {
                Bomb=craftType;
                break;
            }
        }

        assert Bomb != null;
        if (Bomb.equals(CraftType.CherryBomb)) {
            domain=3;
        }
        if (Bomb.equals(CraftType.Bomb)) {
            domain=5;
        }
        if (Bomb.equals(CraftType.MegaBomb)) {
            domain=7;
        }

        int x= tile.getX();
        int y= tile.getY();

        for (int i=x ; i < x + domain ; i++) {
            for (int j=y ; j < y + domain ; j++) {
                Tile target=getTileByCoordinates(i,j);
                if (target == null) {
                    continue;
                }
                if (target.getGameObject() instanceof Tree) {
                    target.setGameObject(new Walkable());
                }
                else if (target.getGameObject() instanceof ForagingCrops) {
                    target.setGameObject(new Walkable());
                }
                else if (target.getGameObject() instanceof GiantProduct) {
                    target.setGameObject(new Walkable());
                }
                else if (target.getGameObject() instanceof ForagingSeeds) {
                    target.setGameObject(new Walkable());
                }
            }
        }
        inventory.Items.compute(items , (k,v) -> v-1);
        inventory.Items.entrySet().removeIf(entry -> entry.getValue() == null || entry.getValue() ==0);

        return new Result(true , "Bomb successfully replaced and everything destroyed");
    }
    private Result placeScarecrow(Tile tile , String name, Items items) {

        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;
        if (currentGame.currentPlayer.getFarm().isInHome(currentGame.currentPlayer.getPositionX(), currentGame.currentPlayer.getPositionY())) {
            return new Result(false , "you can't place "+name+" in your Home because it is not for this place");
        }

        if (name.equals("Scarecrow")) {
            tile.setGameObject(items);
            inventory.Items.compute(items , (k,v) -> v-1);
            inventory.Items.entrySet().removeIf(entry -> entry.getValue() == null || entry.getValue() == 0);
            return new Result(true , "Scarecrow successfully placed");
        }
        else  {
            tile.setGameObject(items);
            inventory.Items.compute(items , (k,v) -> v-1);
            inventory.Items.entrySet().removeIf(entry -> entry.getValue() == null || entry.getValue() == 0);
            return new Result(true , "Deluxe Scarecrow successfully placed");
        }
    }
    private Result placeOther(Items items , Tile tile) {
        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;

        if (items instanceof CraftingItem) {
            if (((CraftingItem) items).getType().equals(CraftType.BeeHouse)) {

                if (currentGame.currentPlayer.getFarm().isInHome(tile.getX(), tile.getY())) {
                    return new Result(false , "you should place Bee House in Farm!");
                }
                tile.setGameObject(items);
                inventory.Items.compute(items , (k,v) -> v-1);
                if (inventory.Items.get(items) == 0) {
                    inventory.Items.remove(items);
                }
                return new Result(true , "Item placed Successfully");
            }

            tile.setGameObject(items);
            inventory.Items.compute(items , (k,v) -> v-1);
            if (inventory.Items.get(items) == 0) {
                inventory.Items.remove(items);
            }
            return new Result(true , "Item placed Successfully");

        }
        return new Result(false, RED + "Item not found" + RESET);
    }
    public void checkSprinkler() {
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



    public Result placeItem(String name, String dir) {
        int direction=Integer.parseInt(dir);
        if (direction < 1 || direction >8) {
            return new Result(false , "invalid direction");
        }

        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;
        Tile tile=getTileByDir(direction);
        if (tile == null) {
            return new Result(false , "you can't place Item on this Tile");
        }

        Farm farm=null;
        for (Farm farms : currentGame.farms) {
            if (farms.Farm.contains(tile)) {
                farm = farms;
            }
        }

        for (User user: currentGame.players) {
            if (user.getFarm().equals(farm)) {
                if (!user.equals(currentGame.currentPlayer) && ! user.getSpouse().equals(currentGame.currentPlayer) ) {
                    return new Result(false , "you can't place Item on this Tile");
                }
            }
        }

        if (farm == null) {
            return new Result(false , "you can't place Item on this Tile");
        }

        if (!(tile.getGameObject() instanceof Walkable) ){
            return new Result(false , "You can't place Item on this Tile");
        }

        if (tile.getGameObject() instanceof Walkable && !((Walkable) tile.getGameObject()).getGrassOrFiber().equals("Walk")) {
            return new Result(false , "You can't place Item on this Tile");
        }


        if (name.equals("Mystic Tree Seed")) {
            //TODO
        }

        Items items=CraftingController.numberOfIngrediants(name);
        if (items == null) {
            return new Result(false , name + "not found!");
        }
        switch (name) {
            case "Grass Starter" -> {
                ((Walkable) tile.getGameObject()).setGrassOrFiber("Grass");
                return new Result(true, "Grass Starter placed successfully");
            }
            case "Chery Bomb", "Bomb", "Mega Bomb" -> {
                return placeBomb(tile, name, items);
            }
            case "Quality Sprinkler", "Sprinkler", "Iridium Sprinkler" -> {
                return placeOther(items, tile);
            }
            case "Scarecrow","Deluxe Scarecrow" -> {
                return placeScarecrow(tile, name, items);
            }
            case "Furnace","Charcoal Klin","Bee House","Cheese Press","Keg","Loom","Mayonnaise Machine","Oil Maker","Preserves Jar","Dehydrator" ->{
                return placeOther(items, tile);
            }

        }
        return new Result(false , "Something went wrong");
    }

    private CraftingItem isNeighborWithCrafting(String name) {
        int [] dirx={0,0,1,1,1,-1,-1,-1};
        int [] diry={1,-1,0,1,-1,0,1,-1};

        for (int x = currentGame.currentPlayer.getPositionX(); x <currentGame.currentPlayer.getPositionX()+ dirx.length; x++) {
            for (int y=currentGame.currentPlayer.getPositionY() ; y< currentGame.currentPlayer.getPositionY()+ diry.length; y++) {
                Tile tile=getTileByCoordinates(x,y);
                if (tile == null) {
                    continue;
                }
                if (tile.getGameObject() instanceof CraftingItem) {
                    if (((CraftingItem) tile.getGameObject()).getType().getName().equals(name)) {
                        return (CraftingItem) tile.getGameObject();
                    }
                }
            }
        }
        return null;
    }



    public Result ArtisanUse(String artisanName , String first , String second) {
        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;
        String newArtistName=artisanName.replace('_',' ');

        boolean found=false;

        for (ArtisanType artisan : ArtisanType.values()) {
            if (artisan.getCraftType().getName().equals(newArtistName)) {
                found=true;
                break;
            }
        }
        if (! found) {
            return new Result(false , "No such type Of Crafting!");
        }
        CraftingItem craftingItem=isNeighborWithCrafting(newArtistName.trim());

        if (craftingItem==null) {
            return new Result(false , "you can't use this Crafting because you are not close to it");
        }


        if (second !=null) {
            second=second.trim();
        }

        for (ArtisanType artisanType : ArtisanType.values()) {
            if (artisanType.getCraftType().equals(craftingItem.getType())) {

                if (artisanType.checkIngredient(first.trim(), second)) {
                    artisanType.creatArtesian(first.trim(), craftingItem);
                    return new Result(true , "you use "+newArtistName+" successfully");
                }
            }
        }
        return new Result(false , "Not enough ingredient for use");

    }


    private void addArtisanToInventory(Items item) {
        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;
        for (Map.Entry <Items , Integer> entry : inventory.Items.entrySet()) {
            if (entry.getKey().equals(item)) {
                inventory.Items.put(item, entry.getValue() + 1);
                return;
            }
        }
        inventory.Items.put(item, 1);
    }

//  کد آقاتون از اینجا شروع میشه. دست نزنید
    public Result ArtisanGetProduct(String name) {
        int [] dirx={0,0,1,1,1,-1,-1,-1};
        int [] diry={1,-1,0,1,-1,0,1,-1};

        Inventory inventory= currentGame.currentPlayer.getBackPack().inventory;
        if (currentGame.currentPlayer.getBackPack().getType().getRemindCapacity() ==0) {
            return new Result(false , "you can't get product because your backpack is full");
        }
        Items items=null;
        StringBuilder result=new StringBuilder();
        result.append("Produces:").append('\n');

        for (int x = 0 ; x<dirx.length ; x++) {
            Tile tile=getTileByCoordinates(currentGame.currentPlayer.getPositionX() + dirx[x],currentGame.currentPlayer.getPositionY() + diry[x]);
                if (tile.getGameObject() instanceof CraftingItem) {
                    if (((CraftingItem) tile.getGameObject()).getName().equals(name)) {
                        Map <Items , DateHour> map=((CraftingItem) tile.getGameObject()).getBuffer();
                        for (Map.Entry <Items , DateHour> entry : map.entrySet()) {
                            for (ArtisanType artisan : ArtisanType.values()) {
                                if (artisan.getName().equals(entry.getKey().getName()) ) {
                                    if (DateHour.getHourDiffrent(entry.getValue()) >= artisan.getTakesTime()){
                                        entry.setValue(null);
                                        if (inventory.Items.containsKey(entry.getKey())) {
                                            inventory.Items.compute(entry.getKey(), (k, v) -> v + 1);
                                        }
                                        else {
                                            inventory.Items.put(entry.getKey(), 1);
                                        }
                                        result.append(BLUE + entry.getKey().getName() +RESET);
                                    }
                                }
                            }
                        }

                        map.entrySet().removeIf(entry -> entry.getValue() == null);
                    }
                }
            }

        return new Result(false , result.toString());
    }

    public Result sell(String name , Integer amount) {
        ShippingBin shippingBin=ShippingBin.isNearShippingBin();
        if (shippingBin == null ) {
            return new Result(false , "you are not near shipping bin");
        }

        Inventory inventory=currentGame.currentPlayer.getBackPack().inventory;
        ArrayList<Fish> fishes=new ArrayList<>();
        ArrayList<Animalproduct> animalproducts=new ArrayList<>();

        int cursor=0;

        for (Map.Entry <Items,Integer> entry : inventory.Items.entrySet() ) {
            if (entry.getKey() instanceof Fish) {
                if (((Fish) entry.getKey()).getType().getName().equals(name)) {
                    Fish fish=new Fish(((Fish) entry.getKey()).getType() , ((Fish) entry.getKey()).getQuantity());
                    fishes.add((Fish) entry.getKey());
                    if (amount == -1) {
                        shippingBin.binContents.put(entry.getKey(), entry.getValue());
                        inventory.Items.compute(fish, (k, v) -> 0);
                        continue;
                    }
                    if (cursor >= amount) {
                        break;
                    }
                    else if (cursor + entry.getValue() <= amount) {
                        shippingBin.binContents.put(entry.getKey(), entry.getValue());
                        cursor += entry.getValue();
                        inventory.Items.compute(fish, (k, v) -> 0);
                    }
                    else if (cursor + entry.getValue() > amount) {
                        shippingBin.binContents.put(entry.getKey(), entry.getValue() + cursor - amount);
                        int finalCursor = cursor;
                        inventory.Items.compute(fish, (k, v) -> v - amount + finalCursor);
                        cursor=amount;
                    }
                }
            }
            if (entry.getKey() instanceof Animalproduct) {
                if (((Animalproduct) entry.getKey()).getType().getName().equals(name)) {
                    animalproducts.add((Animalproduct) entry.getKey());
                    Animalproduct animalproduct = new Animalproduct(((Animalproduct) entry.getKey()).getType() , ((Animalproduct) entry.getKey()).getQuantity());
                    if (amount == -1) {
                        shippingBin.binContents.put(entry.getKey(), entry.getValue());
                        inventory.Items.compute(animalproduct, (k, v) -> 0);
                        continue;
                    }
                    if (cursor >= amount) {
                        break;
                    }
                    else if (cursor + entry.getValue() <= amount) {
                        inventory.Items.compute(animalproduct, (k, v) -> 0);
                        shippingBin.binContents.put(entry.getKey(), entry.getValue());
                        cursor += entry.getValue();
                    }
                    else if (cursor + entry.getValue() > amount) {
                        shippingBin.binContents.put(entry.getKey(), entry.getValue() + cursor - amount);
                        int finalCursor1 = cursor;
                        inventory.Items.compute(animalproduct , (k, v) -> v - amount + finalCursor1);
                        cursor=amount;
                    }
                }
            }
        }
        inventory.Items.entrySet().removeIf(entry -> entry.getValue() == 0);

        if (fishes.isEmpty() && animalproducts.isEmpty()) {
            return new Result(false , name + " not found!");
        }

        return new Result(true , BLUE +"your money will increase tomorrow" + RESET);

    }

    public void unloadAndReward() {
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


    public void nextTurn () {
        User old = currentGame.currentPlayer;
        boolean done = false;
        boolean temp = false;
        int wrongAttempts = 0;

        while (wrongAttempts <= 5) {
            for (User user : currentGame.players) {
                if (temp) {

                    turnCounter++;
                    currentGame.currentPlayer = user;

                    if (turnCounter % 4 == 0 && turnCounter != 0)
                        passedOfTime(0, 1);

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
    public void DisplayFriendships () {
        String targetName = currentGame.currentPlayer.getUsername();

        for (HumanCommunications f : currentGame.friendships) {
            if (f.getPlayer1().getUsername().equals(targetName) || f.getPlayer2().getUsername().equals(targetName))
                f.printInfo();
        }
    }
    public void cheatAddXp (String input) {
        int xp = Integer.parseInt(GameMenuCommands.addXpCheat.getMatcher(input).group("xp"));
        String otherName = GameMenuCommands.addXpCheat.getMatcher(input).group("other");
        User other = findPlayerInGame(otherName);
        HumanCommunications f = getFriendship(currentGame.currentPlayer, other);
        f.addXP(xp);
    }
    public void talking (String input) {
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
    public void DisplayingTalkHistory (String input) {
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
    public void hug (String input) {
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

    public void sendGifts (String input) {
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
//    public Result giftList () {
//        User me = currentGame.currentPlayer;
//        StringBuilder sb = new StringBuilder();
//        for (List<MessageHandling> messages: currentGame.conversations.values()) {
//            for (MessageHandling m: messages) {
//                if (m.getReceiver().getUsername().equals(currentGame.currentPlayer.getUsername())) {
//                    if (m.getText().endsWith("Rate it out of 5!")) {
//                        sb.append(m.getSender().getNickname()).append(" -> You: ").append(??????);
//                        // print
//                    }
//                }
//            }
//        }
//    }
    public void giveFlowers (String input) {
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
    public void propose(String input) {
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
    public void unlockRecipe(String input) {
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
    public void eatFood (String input) {
        Matcher matcher = GameMenuCommands.eatFood.getMatcher(input);
        if (matcher == null) {
            System.out.println(RED+"Unknown Error!"+RESET);
            return;
        }
        String foodName = matcher.group("food");

        // find recipe and it's type
        Recipe recipe = Recipe.findRecipeByName(foodName);
        if (recipe == null) {
            System.out.println(RED+"Food Name Unavailable!"+RESET);
            return;
        }

        FoodTypes type = recipe.getType();

        Inventory myInventory = currentGame.currentPlayer.getBackPack().inventory;
        Fridge fridge = currentGame.currentPlayer.getFarm().getHome().getFridge();
        // decrease from inventory or fridge
        GameController controller = new GameController();
        Items i = new Food(type);
        if (controller.checkAmountProductAvailable(i, 1)) {
            myInventory.Items.put(i, myInventory.Items.get(i) - 1);
            myInventory.Items.entrySet().removeIf(entry -> entry.getValue()==null || entry.getValue() <= 0);
        } else if (fridge.items.containsKey(i) && fridge.items.get(i) >= 1) {
            fridge.items.put(i, fridge.items.get(i) - 1);
            fridge.items.entrySet().removeIf(entry -> entry.getValue()==null || entry.getValue() <= 0);
        } else {
            System.out.println(RED+"None of This Item in Fridge or Inventory!"+RESET);
            return;
        }
        // implement food energy
        currentGame.currentPlayer.setHealth(currentGame.currentPlayer.getHealth() + recipe.getEnergy());
        // implement Buffs
        recipe.applyEffect(currentGame.currentPlayer);

        System.out.println(GREEN+"Eaten Successfully."+RESET);
    }
    public Result backToMainMenu () {
        if (App.currentUser.isCurrently_in_game())
            return new Result(false, RED+"You Are Currently in a Game!"+RESET);
        else {
            currentMenu = Menu.MainMenu;
            return new Result(true, GREEN+"Returned to Main Menu."+RESET);
        }
    }
    public void exitGame () {
        if (currentGame.currentPlayer != currentUser) {
            System.out.println(RED+"Access Denied!"+RESET);
            return;
        }
        //TODO بیشترین امتیاز و... سیو بشه
        //TODO currently in game
        //TODO سیو کل بازی
    }
    public void forceTerminate () {
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

// قسمت آقاتون اینجا تموم میشه


    private void setPlayerLocation () {

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
    private void initializePlayer () {

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
            user.increaseMoney(500 - user.getMoney());
        }
        currentGame.currentPlayer = user1;
    }


    public void passedOfTime (int day, int hour) {

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
            startDay();
        }
        currentGame.currentDate.increaseHour(dateHour.getHour() - currentGame.currentDate.getHour());
    }
    public void passedOfTimeOriginal (int day, int hour) {
//
//        if (day == 0) {
//            if (currentGame.currentPlayer.Buff_maxEnergy_100_hoursLeft > 0) {
//                currentGame.currentPlayer.setBuff_maxEnergy_100_hoursLeft(currentGame.currentPlayer.Buff_maxEnergy_100_hoursLeft - hour);
//                if (currentGame.currentPlayer.Buff_maxEnergy_100_hoursLeft < 0)
//                    currentGame.currentPlayer.setBuff_maxEnergy_100_hoursLeft(0);
//            }
//            if (currentGame.currentPlayer.Buff_maxEnergy_50_hoursLeft > 0) {
//                currentGame.currentPlayer.setBuff_maxEnergy_50_hoursLeft(currentGame.currentPlayer.Buff_maxEnergy_50_hoursLeft - hour);
//                if (currentGame.currentPlayer.Buff_maxEnergy_50_hoursLeft < 0)
//                    currentGame.currentPlayer.setBuff_maxEnergy_50_hoursLeft(0);
//            }
//            if (currentGame.currentPlayer.Buff_farming_hoursLeft > 0) {
//                currentGame.currentPlayer.setBuff_farming_hoursLeft(currentGame.currentPlayer.Buff_farming_hoursLeft - hour);
//                if (currentGame.currentPlayer.Buff_farming_hoursLeft < 0)
//                    currentGame.currentPlayer.setBuff_farming_hoursLeft(0);
//            }
//            if (currentGame.currentPlayer.Buff_foraging_hoursLeft > 0) {
//                currentGame.currentPlayer.setBuff_foraging_hoursLeft(currentGame.currentPlayer.Buff_foraging_hoursLeft - hour);
//                if (currentGame.currentPlayer.Buff_foraging_hoursLeft < 0)
//                    currentGame.currentPlayer.setBuff_foraging_hoursLeft(0);
//            }
//            if (currentGame.currentPlayer.Buff_fishing_hoursLeft > 0) {
//                currentGame.currentPlayer.setBuff_fishing_hoursLeft(currentGame.currentPlayer.Buff_fishing_hoursLeft - hour);
//                if (currentGame.currentPlayer.Buff_fishing_hoursLeft < 0)
//                    currentGame.currentPlayer.setBuff_fishing_hoursLeft(0);
//            }
//            if (currentGame.currentPlayer.Buff_mining_hoursLeft > 0) {
//                currentGame.currentPlayer.setBuff_mining_hoursLeft(currentGame.currentPlayer.Buff_mining_hoursLeft - hour);
//                if (currentGame.currentPlayer.Buff_mining_hoursLeft < 0)
//                    currentGame.currentPlayer.setBuff_mining_hoursLeft(0);
//            }
//
//
//
//
//            // Buff implementation
//            if (currentGame.currentPlayer.Buff_maxEnergy_100_hoursLeft == 0) currentGame.currentPlayer.setMAX_HEALTH(200);
//            if (currentGame.currentPlayer.Buff_maxEnergy_50_hoursLeft == 0) currentGame.currentPlayer.setMAX_HEALTH(200);
//            if (currentGame.currentPlayer.Buff_maxEnergy_100_hoursLeft > 0) {
//                currentGame.currentPlayer.setMAX_HEALTH(currentGame.currentPlayer.getMAX_HEALTH() + 100);
//                currentGame.currentPlayer.setHealth(currentGame.currentPlayer.getHealth() + 100);
//                currentGame.currentPlayer.setBuff_maxEnergy_100_hoursLeft(currentGame.currentPlayer.Buff_maxEnergy_100_hoursLeft --);
//            }
//            if (currentGame.currentPlayer.Buff_maxEnergy_50_hoursLeft > 0) {
//                currentGame.currentPlayer.setMAX_HEALTH(currentGame.currentPlayer.getMAX_HEALTH() + 50);
//                currentGame.currentPlayer.setHealth(currentGame.currentPlayer.getHealth() + 50);
//                currentGame.currentPlayer.setBuff_maxEnergy_50_hoursLeft(currentGame.currentPlayer.Buff_maxEnergy_50_hoursLeft --);
//            }
//            if (currentGame.currentPlayer.Buff_mining_hoursLeft > 0) currentGame.currentPlayer.setBuff_mining_hoursLeft(currentGame.currentPlayer.Buff_mining_hoursLeft --);
//            if (currentGame.currentPlayer.Buff_fishing_hoursLeft > 0) currentGame.currentPlayer.setBuff_fishing_hoursLeft(currentGame.currentPlayer.Buff_fishing_hoursLeft --);
//            if (currentGame.currentPlayer.Buff_farming_hoursLeft > 0) currentGame.currentPlayer.setBuff_farming_hoursLeft(currentGame.currentPlayer.Buff_farming_hoursLeft --);
//            if (currentGame.currentPlayer.Buff_foraging_hoursLeft > 0) currentGame.currentPlayer.setBuff_foraging_hoursLeft(currentGame.currentPlayer.Buff_foraging_hoursLeft --);
//
//        }
//        else
//            currentGame.currentPlayer.setBuff_maxEnergy_100_hoursLeft(0);
//
//        DateHour dateHour = currentGame.currentDate.clone();
//
//        currentGame.currentDate.increaseHour(hour);
//        currentGame.currentDate.increaseDay(day);
//
//        for (int i = 0 ; i < getDayDifferent(dateHour, currentGame.currentDate) ; i++)
//            startDay();
//
//        if (currentGame.currentDate.getHour() > 22)
//            passedOfTime(0, 24 - currentGame.currentDate.getHour() + 9);
//        if (currentGame.currentDate.getHour() < 9)
//            passedOfTime(0, 9 - currentGame.currentDate.getHour());

    }

    public void startNewGame (String input) {
        System.out.println(RED+"Starting New Game..."+RESET);

        currentGame = new Game();
        currentGame.currentMenu = currentMenu;


//        String user1name = GameMenuCommands.makeNewGame.getMatcher(input).group("username1");
//        String user2name = GameMenuCommands.makeNewGame.getMatcher(input).group("username2"); // could be null
//        String user3name = GameMenuCommands.makeNewGame.getMatcher(input).group("username3");// could be null
//
//        User user1 = findUserByUsername(user1name);
//        User user2 = findUserByUsername(user2name);
//        User user3 = findUserByUsername(user3name);
//
//        if (user1 == null){
//            System.out.println("User1 Not Found!");
//            return;
//        }
//        if (user2name != null) {
//            if (user2 == null) {
//                System.out.println("User2 Not Found! Try Again.");
//                return;
//            }
//        }
//        if (user3name != null) {
//            if (user3 == null) {
//                System.out.println("User3 Not Found! Try Again.");
//                return;
//            }
//        }
//        if (user1.isCurrently_in_game()){
//            System.out.println("User1 Currently in Game! Try Again.");
//            return;
//        }
//        else user1.setCurrently_in_game(true);
//
//        if (user2name != null) {
//            if (findUserByUsername(user2name).isCurrently_in_game()) {
//                System.out.println("User2 Currently in Game! Try Again.");
//                return;
//            }
//            else user2.setCurrently_in_game(true);
//        }
//        if (user3name != null) {
//            if (findUserByUsername(user3name).isCurrently_in_game()) {
//                System.out.println("User3 Currently in Game! Try Again.");
//                return;
//            }
//            else user3.setCurrently_in_game(true);
//        }
//
//        if (user1.getUsername().equals(currentUser.getUsername())) {
//            System.out.println(RED+"Invite Users Other than Yourself! Try Again."+RESET);
//            return;
//        }
//        if (user2 != null) {
//            if (user2.getUsername().equals(currentUser.getUsername())) {
//                System.out.println(RED+"Invite Users Other than Yourself! Try Again."+RESET);
//                return;
//            }
//        }
//        if (user3 != null) {
//            if (user3.getUsername().equals(currentUser.getUsername())) {
//                System.out.println(RED+"Invite Users Other than Yourself! Try Again."+RESET);
//                return;
//            }
//        }
//        currentGame.players.add(currentUser);
//        currentGame.currentPlayer = currentUser;
//        System.out.println(RED+"player selected"+RESET);
//        setTimeAndWeather();

//        currentGame.players.add(findUserByUsername(user1name));
//        if (user2name != null) currentGame.players.add(findUserByUsername(user2name));
//        if (user3name != null) currentGame.players.add(findUserByUsername(user3name));
        currentGame.players.add(new User("Ario", "Ario", "ario.ebr@gmail.com", "male", 0, 200, PasswordHashUtil.hashPassword("Ebrahim84?"), SecurityQuestions.FavoriteAnimal, "dog"));
        currentGame.players.add(new User("Erfan", "Erfan", "ario.ebr@gmail.com", "female", 0, 200, PasswordHashUtil.hashPassword("Ebrahim84?"), SecurityQuestions.FavoriteAnimal, "dog"));
        currentGame.players.add(new User("Mamali", "Mamali", "ario.ebr@gmail.com", "male", 0, 200, PasswordHashUtil.hashPassword("Ebrahim84?"), SecurityQuestions.FavoriteAnimal, "dog"));
        currentGame.players.add(new User("Ilia", "Ilia", "ario.ebr@gmail.com", "male", 0, 200, PasswordHashUtil.hashPassword("Ebrahim84?"), SecurityQuestions.FavoriteAnimal, "dog"));
        setTimeAndWeather();
        currentGame.currentPlayer = currentGame.players.getFirst();
        currentUser = currentGame.players.getFirst();
        // done


        Scanner scanner = new Scanner(System.in);

        int counter = 1;
        for (User user: currentGame.players) {

            currentGame.currentPlayer = user;
            while (true) {

//                System.out.println(currentPlayer.getUsername() + "'s turn to choose map(1 or 2)");
//                String choiceString = scanner.nextLine();
//                String[] splitChoice = choiceString.trim().split("\\s+");
//
//                int choice;
//                try {
//                    choice = Integer.parseInt(splitChoice[2]);
//                } catch (Exception e) {
//                    System.out.println("Please put a integer between 1 and 2!");
//                    continue;
//                }
//                if (choice != 1 && choice != 2) {
//                    System.out.println("Choose between 1 and 2!");
//                    continue;
//                }

                int choice = 1; // TODO باید پاک بشه

                if (counter == 1) {
                    user.setIcon(BRIGHT_CYAN + "∆ " + RESET);
                    user.topLeftX = 0;
                    user.topLeftY = 0;
                }
                else if (counter == 2) {
                    user.setIcon(BRIGHT_PURPLE + "∆ " + RESET);
                    user.topLeftX = 1;
                    user.topLeftY = 0;
                }
                else if (counter == 3) {
                    user.setIcon(BRIGHT_RED + "∆ " + RESET);
                    user.topLeftX = 0;
                    user.topLeftY = 1;
                }
                else if (counter == 4) {
                    user.setIcon(BRIGHT_YELLOW + "∆ " + RESET);
                    user.topLeftX = 1;
                    user.topLeftY = 1;
                }
                createInitialFarm(choice);
                counter++;
                break;
            }
        }
        currentGame.currentPlayer = currentGame.players.getFirst();

        // Form Friendships
        for (int i = 0; i < currentGame.players.size(); i++) {
            for (int j = i + 1; j < currentGame.players.size(); j++) {
                HumanCommunications f = new HumanCommunications(currentGame.players.get(i), currentGame.players.get(j));
                currentGame.friendships.add(f);
            }
        }
        // set initial Cooking Recipes from beginning
        for (User player: currentGame.players) {
            player.setRecipes(Recipe.createAllRecipes());
        }
        buildHall();
        buildNpcVillage();
        sortMap(currentGame.bigMap);
        initializePlayer();
        startDay();
    }
//    public void startNewGame (String input) throws IOException {
//        System.out.println(CYAN+"Starting New Game..."+RESET);
//
//        currentGame = new Game();
//        currentGame.currentPlayer = currentUser;
//        currentGame.currentMenu = currentMenu;
//
//
//        String user1name = GameMenuCommands.makeNewGame.getMatcher(input).group("username1");
//        String user2name = GameMenuCommands.makeNewGame.getMatcher(input).group("username2"); // could be null
//        String user3name = GameMenuCommands.makeNewGame.getMatcher(input).group("username3");// could be null
//
//        User user1 = findUserByUsername(user1name);
//        User user2 = findUserByUsername(user2name);
//        User user3 = findUserByUsername(user3name);
//
//        if (user1 == null){
//            System.out.println("User1 Not Found!");
//            return;
//        }
//        if (user2name != null) {
//            if (user2 == null) {
//                System.out.println("User2 Not Found!");
//                return;
//            }
//        }
//        if (user3name != null) {
//            if (user3 == null) {
//                System.out.println("User3 Not Found!");
//                return;
//            }
//        }
//        if (user1.isCurrently_in_game()){
//            System.out.println("User1 Currently in Game!");
//            return;
//        }
//        else user1.setCurrently_in_game(true);
//
//        if (user2name != null) {
//            if (findUserByUsername(user2name).isCurrently_in_game()) {
//                System.out.println("User2 Currently in Game!");
//                return;
//            }
//            else user2.setCurrently_in_game(true);
//        }
//        if (user3name != null) {
//            if (findUserByUsername(user3name).isCurrently_in_game()) {
//                System.out.println("User3 Currently in Game!");
//                return;
//            }
//            else user3.setCurrently_in_game(true);
//        }
//
//        if (user1.getUsername().equals(currentUser.getUsername())) {
//            System.out.println(RED+"Invite Users Other than Yourself!"+RESET);
//            return;
//        }
//        if (user2 != null) {
//            if (user2.getUsername().equals(currentUser.getUsername())) {
//                System.out.println(RED+"Invite Users Other than Yourself!"+RESET);
//                return;
//            }
//        }
//        if (user3 != null) {
//            if (user3.getUsername().equals(currentUser.getUsername())) {
//                System.out.println(RED+"Invite Users Other than Yourself!"+RESET);
//                return;
//            }
//        }
//
//
//        currentGame.players.add(currentUser);
//        currentGame.players.add(user1);
//        if (user2 != null) currentGame.players.add(user2);
//        if (user3 != null) currentGame.players.add(user2);
//        currentGame.currentPlayer = currentUser;
//        setTimeAndWeather();
//
//        Scanner scanner = new Scanner(System.in);
//
//        int counter = 1;
//        for (User user: currentGame.players) {
//
//            currentGame.currentPlayer = user;
//            while (true) {
//
//                System.out.println(currentGame.currentPlayer.getNickname() + "'s turn to choose map(1 or 2)");
//                String choiceString = scanner.nextLine();
//                String[] splitChoice = choiceString.trim().split("\\s+");
//
//                int choice;
//                try {
//                    choice = Integer.parseInt(splitChoice[2]);
//                } catch (Exception e) {
//                    System.out.println("Please Use an Integer between 1 and 2!");
//                    continue;
//                }
//                if (choice != 1 && choice != 2) {
//                    System.out.println("Choose between 1 and 2!");
//                    continue;
//                }
//
//
//                if (counter == 1) {
//                    user.setIcon(BRIGHT_CYAN + "∆ " + RESET);
//                    user.topLeftX = 0;
//                    user.topLeftY = 0;
//                }
//                else if (counter == 2) {
//                    user.setIcon(BRIGHT_PURPLE + "∆ " + RESET);
//                    user.topLeftX = 1;
//                    user.topLeftY = 0;
//                }
//                else if (counter == 3) {
//                    user.setIcon(BRIGHT_RED + "∆ " + RESET);
//                    user.topLeftX = 0;
//                    user.topLeftY = 1;
//                }
//                else if (counter == 4) {
//                    user.setIcon(BRIGHT_YELLOW + "∆ " + RESET);
//                    user.topLeftX = 1;
//                    user.topLeftY = 1;
//                }
//                createInitialFarm(choice);
//                counter++;
//                break;
//            }
//        }
//        currentGame.currentPlayer = currentGame.players.getFirst();
//
//        // Form Friendships
//        for (int i = 0; i < currentGame.players.size(); i++) {
//            for (int j = i + 1; j < currentGame.players.size(); j++) {
//                HumanCommunications f = new HumanCommunications(currentGame.players.get(i), currentGame.players.get(j));
//                currentGame.friendships.add(f);
//            }
//        }
//        // set initial Cooking Recipes from beginning
//        for (User player: currentGame.players) {
//            player.setRecipes(Recipe.createAllRecipes());
//        }
//        buildHall();
//        buildNpcVillage();
//        sortMap(currentGame.bigMap);
//        initializePlayer();
//        startDay();
//    }

    public void startDay () {

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

    public void AutomaticFunctionAfterOneTurn () {

        for (Tile tile : currentGame.bigMap)
            tile.getGameObject().turnByTurnAutomaticTask();
    }
    public Result AutomaticFunctionAfterAnyAct () {

        checkForGiant();
        checkForProtect();

        for (User user : currentGame.players) {
            user.checkHealth();

            for (NPC npc : NPC.values())
                if (user.getFriendshipLevel(npc) == 3 && user.getLevel3Date(npc) == currentGame.currentDate)
                    user.setLevel3Date(npc, currentGame.currentDate.clone());
        }
        checkSprinkler();

        if (checkForDeath()) {
            currentGame.currentPlayer.setSleepTile(
                    getTileByCoordinates(currentGame.currentPlayer.getPositionX(),
                            currentGame.currentPlayer.getPositionY()));
            return new Result(false, BRIGHT_RED + "No energy left! It's the next player's turn" + RESET);
        }
        return new Result(true, "");
    }

                                            // energy & Date
    private void setEnergyInMorning () {
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
    private void setTimeAndWeather () {

        currentGame.currentDate = new DateHour(Season.Spring, 1, 9, 1980);
        currentGame.tomorrowWeather = Weather.Sunny;

    }
    private void doSeasonAutomaticTask () {

        currentGame.currentWeather = Weather.valueOf(currentGame.tomorrowWeather.toString());
        currentGame.tomorrowWeather = currentGame.currentDate.getSeason().getWeather();

    }
    private void doWeatherTask () {

        if (currentGame.currentWeather.equals(Weather.Rainy) || currentGame.currentWeather.equals(Weather.Stormy)) {
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
        if (currentGame.currentWeather.equals(Weather.Stormy)) {

            Random random1 = new Random();

            for (User user : currentGame.players)
                if (random1.nextInt(2) == 1) {
                    Tile tile = selectTileForThor(user.getFarm());
                    if (tile != null)
                        lightningStrike(tile);
                }
        }
    }

                                            // Automatic Plant task
    private void    crowAttack () {

        for (Farm farm : currentGame.farms) {

            int number = 0;
            for (Tile tile : farm.Farm) {

                GameObject object = tile.getGameObject();

                if (object instanceof Tree ||
                        object instanceof ForagingSeeds ||
                        object instanceof GiantProduct ||
                        object instanceof ForagingCrops) {

                    number++;

                    if (number % 2 == 0) {

                        double x = Math.random();
                        if (x <= 0.25) {

                            if (isInGreenHouse(tile)) {
                                continue;
                            } else if (object instanceof Tree && !((Tree) object).isProtected())
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
    private void    checkForGiant () {

        for (Tile tile1 : currentGame.bigMap) {
            int i = tile1.getX();
            int j = tile1.getY();

            if (tile1.getGameObject() instanceof ForagingSeeds)
                if (((ForagingSeeds) tile1.getGameObject()).getType().canGrowGiant() && !isInGreenHouse(tile1)) {

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
    }
    private void    checkForProtect() {

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
    private boolean checkForDeath () {

        return (currentGame.currentPlayer.getHealth() <= 0 && !currentGame.currentPlayer.isHealthUnlimited());
    }
    private void    lightningStrike (Tile selected) {

        GameObject object = selected.getGameObject();

        if (object instanceof Tree)
            selected.setGameObject(new ForagingMinerals(COAL));
        else if (object instanceof ForagingSeeds)
            selected.setGameObject(new Walkable());
        else if (object instanceof Animal)
            selected.setGameObject(new Walkable());

    }
    private Tile    selectTileForThor (Farm farm) {

        List<Tile> matchingTiles = farm.Farm.stream()
                .filter(tile -> (tile.getGameObject() instanceof Tree ||
                                    tile.getGameObject() instanceof ForagingSeeds) &&
                                !farm.isInGreenHouse(tile.getX(), tile.getY())) .toList();

        if (matchingTiles.isEmpty())
            return null;

        Random random = new Random();
        return matchingTiles.get(random.nextInt(matchingTiles.size()));

    }
    private boolean checkInAllFarm (int x, int y) {

        for (User user : currentGame.players)
            if (user.getFarm().isInFarm(x, y))
                return true;
        return false;
    }
    private boolean canGrowGrass (Tile tile) {

        int x = tile.getX();
        int y = tile.getY();

        if (!checkInAllFarm(tile.getX(), tile.getY()))
            return false;

        for (User user : currentGame.players)
            if (user.getFarm().isInHome(x, y) || user.getFarm().isInMine(x, y) || user.getFarm().isInGreenHouse(x, y))
                return false;

        return true;
    }
    private void    createRandomForaging () {

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
    private void    createRandomMinerals () {
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
    private boolean checkTileForPlant (Tile tile) {

        GameObject object = tile.getGameObject();

        if (object instanceof Tree)
            return true;
        if (object instanceof GiantProduct)
            return true;

        return object instanceof ForagingSeeds;
    } // محصولای خودرو حساب نیستن
    private void    fertilizePlant (MarketItemType fertilizeType , Tile tile) {

        GameObject gameObject = tile.getGameObject();

        if (gameObject instanceof GiantProduct)
            ((GiantProduct) gameObject).setFertilize(fertilizeType);
        if (gameObject instanceof Tree)
            ((Tree) gameObject).setFertilize(fertilizeType);
        if (gameObject instanceof ForagingSeeds)
            ((ForagingSeeds) gameObject).setFertilize(fertilizeType);

    }

                                             // other plant task
    private String showTree (Tree tree) {


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
    private String showForaging (ForagingSeeds foragingSeeds) {

        return "name : " + foragingSeeds.getType().getDisplayName() +
                "\nLast Water : " + BLUE + "Date : " + RED + foragingSeeds.getLastWater().getYear() +
                RESET + " " + foragingSeeds.getLastWater().getNameSeason() +
                " " + foragingSeeds.getLastWater().getDate() +
                "\nToday fertilize :" + foragingSeeds.isTodayFertilize() +
                "\nStage :" + foragingSeeds.getStage() +
                "\nOne Time :" + foragingSeeds.getType().isOneTimeUse() +
                "\nCan grow giant :" + foragingSeeds.getType().canGrowGiant();

    }
    private String showGiant (GiantProduct giantProduct) {

        return "name : " + giantProduct.getType().getDisplayName() +
                "\nLast Water : " + BLUE + "Date : " + RED + giantProduct.getLastWater().getYear() +
                RESET + " " + giantProduct.getLastWater().getNameSeason() +
                " " + giantProduct.getLastWater().getDate() +
                "\nToday fertilize :" + giantProduct.isTodayFertilize() +
                "\nStage :" + giantProduct.getStage();
    }
    private Result plantTree (TreesSourceType type1, int dir) {

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
    private Result plantMixedSeed (int dir) {

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
    private Result plantForagingSeed (ForagingSeedsType type, int dir) {

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
    private Result useHoe (int dir) {

        Tile tile = getTileByDir(dir);

        if (tile.getGameObject() instanceof Walkable &&
                ((Walkable) tile.getGameObject()).getGrassOrFiber().equals("Plowed"))
            return new Result(false, RED+"This tile is already plowed!"+RESET);
        if (!(tile.getGameObject() instanceof Walkable))
            return new Result(false, RED+"You can't plow this tile!"+RESET);

        ((Walkable) tile.getGameObject()).setGrassOrFiber("Plowed");
        return new Result(true, BLUE+"Tile("+tile.getX()+","+tile.getY()+") Plowed!"+RESET);
    }
    private Result useWateringCan (int dir) {

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
    private Result useScythe (int dir) {


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

                ((Tree) object).setLastFruit(currentGame.currentDate);
                currentGame.currentPlayer.increaseFarmingAbility(5);
                ((Tree) object).setLastFruit(currentGame.currentDate);
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
    private Result useAxe (int dir) {

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
            }
            else {
                tile.setGameObject(new Wood());
                return new Result(false, RED+"Inventory is full"+RESET);
            }
        }
        return new Result(false, RED+"There are no Tree!"+RESET);
    }
    private Result usePickAxe (int dir) {


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
    private Result useShear (int dir) {


        Tile tile = getTileByDir(dir);
        GameObject object = tile.getGameObject();

        return new Result(false, RED+"There are no plant!"+RESET);
    }
    private Result useMilkPail (int dir) {


        Tile tile = getTileByDir(dir);
        GameObject object = tile.getGameObject();

        return new Result(false, RED+"There are no plant!"+RESET);
    }

                                                // NPC task
    private void NPAutomateTask() {

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
    private String padRight(String text, int length) {

        if (text.length() >= length)
            return text.substring(0, length);

        return text + " ".repeat(length - text.length());
    }
    private String OneNPCQuestsList (NPC npc) {

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
    private String OneNPCFriendshipList (NPC npc) {

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
    private Result doTask1 (NPC npc) {


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
    private Result doTask2 (NPC npc) {

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
    private Result doTask3 (NPC npc) {

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












                                                                  // input command Date
    public Result showTime () {
        return new Result(true, BLUE +"Time : "+RESET
                + currentGame.currentDate.getHour()+ ":00");
    }
    public Result showDate () {
        return new Result(true, BLUE+"Date : "+RED+currentGame.currentDate.getYear()+RESET+" "+currentGame.currentDate.getNameSeason()+" "+currentGame.currentDate.getDate());
    }
    public Result showSeason   () {

        return new Result(true, currentGame.currentDate.getNameSeason());
    }
    public Result showWeather  (boolean isToday) {

        if (isToday)
            return new Result(true, currentGame.currentWeather.getDisplayName());
        else
            return new Result(true, currentGame.tomorrowWeather.getDisplayName());
    }
    public Result setWeather   (String type) {

        Weather weather;
        try {
            weather = Weather.valueOf(type);
        } catch (Exception e) {
            return new Result(false, RED+"Weather type is incorrect!"+RESET);
        }
        currentGame.tomorrowWeather = weather;
        return new Result(true, BLUE+"Tomorrow weather change to "+RESET+currentGame.tomorrowWeather.getDisplayName());
    }
    public Result setEnergy    (String amount) {

        if (currentGame.currentPlayer.isHealthUnlimited())
            return new Result(false, BLUE+"You're unstoppable! Energy level: ∞"+RESET);

        if (amount.charAt(0) == '-')
            return new Result(false, RED+"Energy must be a positive number!"+RESET);
        int amount2;
        try {
            amount2 = Integer.parseInt(amount);
        } catch (Exception e) {
            return new Result(false, RED+"Number is incorrect!"+RESET);
        }

        if (currentGame.currentPlayer.getHealth() > amount2) {
            currentGame.currentPlayer.setHealth(amount2);
            return new Result(true, BLUE+"Your Energy decreased to : " +RESET+amount2);
        }
        else if (currentGame.currentPlayer.getHealth() < amount2) {
            currentGame.currentPlayer.setHealth(amount2);
            return new Result(true, BLUE+"Your Energy increased to : " +RESET+amount2);
        } else
            return new Result(false, "Your energy level at this moment is this amount.");
    }
    public Result showDateTime () {
        return new Result(true, BLUE+"Time : "+RED+ currentGame.currentDate.getHour()+ ":00" +
                BLUE+"\nData : "+RED+currentGame.currentDate.getYear()+RESET+" "+currentGame.currentDate.getNameSeason()+" "+currentGame.currentDate.getDate());
    }
    public Result showDayOfWeek() {
        return new Result(true, BLUE+"Day of Week : "+RESET
                + currentGame.currentDate.getDayOfTheWeek());
    }
    public Result increaseHour (String hour) {

        if (hour.charAt(0) == '-')
            return new Result(false, RED+"The time must be a positive number!"+RESET);
        int amount;
        try {
            amount = Integer.parseInt(hour);
        } catch (Exception e) {
            return new Result(false, RED+"Time is incorrect!"+RESET);
        }
        passedOfTime(0, amount);
        return new Result(true, BLUE+"Time change to : "+GREEN+ currentGame.currentDate.getHour()+":00"+RESET);
    }
    public Result increaseDate (String date) {

        if (date.charAt(0) == '-')
            return new Result(false, RED+"The time must be a positive number!"+RESET);
        int amount;
        try {
            amount = Integer.parseInt(date);
        } catch (Exception e) {
            return new Result(false, RED+"Time is incorrect!"+RESET);
        }
        passedOfTime(amount, 0);
        return new Result(true, BLUE+"Date change to : "+RED+currentGame.currentDate.getYear()+RESET+" "+currentGame.currentDate.getNameSeason()+" "+currentGame.currentDate.getDate());
    }
    public Result showEnergy () {

        if (currentGame.currentPlayer.isHealthUnlimited())
            return new Result(false, BRIGHT_BLUE + "Your energy is unlimited" + RESET);
        return new Result(true,BRIGHT_BLUE+"Your energy is : "+currentGame.currentPlayer.getHealth()+RESET);
    }
    public Result EnergyUnlimited () {

        currentGame.currentPlayer.setHealthUnlimited();
        return new Result(true, BLUE+"Whoa! Infinite energy mode activated!"+RESET);
    }

                                                                   // input command plant
    public Result planting (String name, String direction) {

        if (directionIncorrect(direction))
            return new Result(false, RED+"Direction is invalid"+RESET);

        int dir = Integer.parseInt(direction);

        Tile tile = getTileByDir(dir);

        if ((!currentGame.currentPlayer.getFarm().isInFarm(tile.getX(), tile.getY())) &&
                !currentGame.currentPlayer.getSpouse().getFarm().isInFarm(tile.getX(), tile.getY()))
            return new Result(false, RED+"You must select your tile"+RESET);

        if (name.matches("\\s*(?i)Mixed\\s*seed(s)?\\s*"))
            return plantMixedSeed(dir);

        try {
            ForagingSeedsType type = ForagingSeedsType.fromDisplayName(name.trim());
            return plantForagingSeed(type, dir);
        } catch (Exception e) {
            try {
                TreesSourceType type2 = TreesSourceType.fromDisplayName(name.trim());
                return plantTree(type2, dir);
            } catch (Exception e2) {
                return new Result(false, RED+"Hmm... that seed name doesn’t seem right!"+RESET);
            }
        }
    }
    public Result WateringPlant (String direction) {

        if (directionIncorrect(direction))
            return new Result(false, RED+"Direction is invalid"+RESET);

        int dir = Integer.parseInt(direction);

        if (!(currentGame.currentPlayer.currentTool instanceof WateringCan))
            return new Result(false, RED+"سطل اب رو بردار دوست من"+RESET);

        if (!currentGame.currentPlayer.isHealthUnlimited())
            currentGame.currentPlayer.increaseHealth(currentGame.currentPlayer.currentTool.healthCost());

        Tile tile = getTileByDir(dir);
        GameObject object = tile.getGameObject();

        if ((!currentGame.currentPlayer.getFarm().isInFarm(tile.getX(), tile.getY())) &&
                !currentGame.currentPlayer.getSpouse().getFarm().isInFarm(tile.getX(), tile.getY()))
            return new Result(false, RED+"You must select your tile"+RESET);

        if (((WateringCan) currentGame.currentPlayer.currentTool).getReminderCapacity() < 1)
            return new Result(false, RED+"ظرفت خالیه مشتی"+RESET);

        if (object instanceof ForagingSeeds) {

            ((WateringCan) currentGame.currentPlayer.currentTool).decreaseWater(1);
            ((ForagingSeeds) object).setLastWater(currentGame.currentDate);
            return new Result(true, BLUE+"The plant has been watered!\uD83C\uDF31"+RESET);

        }
        else if (object instanceof Tree) {

            ((WateringCan) currentGame.currentPlayer.currentTool).decreaseWater(1);
            ((Tree) object).setLastWater(currentGame.currentDate);
            return new Result(true, BLUE+"The plant has been watered!\uD83C\uDF31"+RESET);

        }
        else if (object instanceof GiantProduct) {

            ((WateringCan) currentGame.currentPlayer.currentTool).decreaseWater(1);
            ((GiantProduct) object).setLastWater(currentGame.currentDate);
            return new Result(true, BLUE+"The plant has been watered!\uD83C\uDF31"+RESET);
        }

        return new Result(false, RED+"No plant in here!"+RESET);
    }
    public Result showFruitInfo (String name) {

        TreesProductType type;

        try {
            type = TreesProductType.fromDisplayName(name);
            return new Result(true, TreesProductType.getInformation(type));

        } catch (Exception e) {

            CropsType cropType;
            try {
                cropType = CropsType.fromDisplayName(name);
                return new Result(true, CropsType.getInformation(cropType));

            } catch (Exception e1) {
                return new Result(false, RED+"sorry, name is invalid!"+RESET);
            }
        }
    }
    public Result thor (String x, String y) {

        int x1 = Integer.parseInt(x);
        int y1 = Integer.parseInt(y);

        if ((!currentGame.currentPlayer.getFarm().isInFarm(x1, y1)) && currentGame.currentPlayer.getSpouse() != null
                && !currentGame.currentPlayer.getSpouse().getFarm().isInFarm(x1, y1))
            return new Result(false, RED+"You must select your tile"+RESET);

        if (isInGreenHouse(getTileByCoordinates(x1, y1)))
            return new Result(false, RED+"Lightning can’t hit the greenhouse"+RESET);

        lightningStrike(getTileByCoordinates(x1, y1));
        return new Result(true, BLUE+"A lightning bolt hits!"+RESET);
    }
    public Result buildGreenHouse () {

        if (currentGame.currentPlayer.getFarm().getGreenHouse().isCreated())
            return new Result(false, BRIGHT_BLUE+"The greenhouse has been build!"+RESET);

        if (!checkAmountProductAvailable(new Wood(), GreenHouse.requiredWood))
            return new Result(false, RED+"You don't have enough wood!"+RESET);

        if (currentGame.currentPlayer.getMoney() < GreenHouse.requiredCoins )
            return new Result(false, RED+"You don't have enough Coin!"+RESET);

        currentGame.currentPlayer.increaseMoney(-GreenHouse.requiredCoins);
        advanceItem(new Wood(), -GreenHouse.requiredWood);

        currentGame.currentPlayer.getFarm().getGreenHouse().setCreated(true);

        return new Result(true, RED + "-500 wood  -1000 Coin"+BLUE+"\nThe greenhouse has been built! \uD83C\uDF31"+RESET);
    }
    public Result info (String name) {

        TreeType treeType;
        try {
            treeType = TreeType.fromDisplayName(name);
            return new Result(true, TreeType.getInformation(treeType));

        } catch (Exception e1) {

            ForagingCropsType foragingCropsType;
            try {
                foragingCropsType = ForagingCropsType.fromDisplayName(name);
                return new Result(true, ForagingCropsType.getInformation(foragingCropsType));
            } catch (Exception e2) {
                return new Result(false, RED+"Name is incorrect!"+RESET);
            }
        }
    }
    public Result showPlant (String xNumber, String yNumber) {

        int x = Integer.parseInt(xNumber);
        int y = Integer.parseInt(yNumber);

        if (!currentGame.currentPlayer.getFarm().isInFarm(x, y))
            return new Result(false, RED+"Pick from your own farm!"+RESET);

        Tile tile = getTileByCoordinates(x, y);

        if (tile.getGameObject() instanceof Tree)
            return new Result(true, showTree((Tree) tile.getGameObject()));
        if (tile.getGameObject() instanceof ForagingSeeds)
            return new Result(true, showForaging((ForagingSeeds) tile.getGameObject()));
        if (tile.getGameObject() instanceof GiantProduct)
            return new Result(true, showGiant((GiantProduct) tile.getGameObject()));

        if ((!currentGame.currentPlayer.getFarm().isInFarm(tile.getX(), tile.getY())) &&
                !currentGame.currentPlayer.getSpouse().getFarm().isInFarm(tile.getX(), tile.getY()))
            return new Result(false, RED+"You must select your tile"+RESET);

        return new Result(false, RED+"That tile don't have plant!"+RESET);

    }
    public Result fertilize (String fertilizeType, String direction) {

        if (directionIncorrect(direction))
            return new Result(false, RED+"Direction is invalid"+RESET);

        int dir = Integer.parseInt(direction);
        Tile tile = getTileByDir(dir);

        if (!checkTileForPlant(tile))
            return new Result(false, RED+"This tile has no plants!"+RESET);

        MarketItemType type;

        try {
            type = MarketItemType.fromDisplayName(fertilizeType);
        } catch (Exception e) {
            return new Result(false, RED+"Fertilize type is invalid"+RESET);
        }
        if (!checkAmountProductAvailable(new MarketItem(type), 1))
            return new Result(false, RED+"You don't have enough "+type.getName()+RESET);

        if ((!currentGame.currentPlayer.getFarm().isInFarm(tile.getX(), tile.getY())) &&
                !currentGame.currentPlayer.getSpouse().getFarm().isInFarm(tile.getX(), tile.getY()))
            return new Result(false, RED+"You must select your tile"+RESET);

        advanceItem(new MarketItem(type), -1);
        fertilizePlant(type, tile);
        return new Result(true, BLUE+"The plant has been fertilized! ✨"+RESET);
    }

                                                                   // input tools command
    public Result howMuchWater () {

        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;

        for (Map.Entry <Items,Integer> entry: inventory.Items.entrySet())
            if (entry instanceof WateringCan)
                return new Result(true, BLUE+"Water Remaining : "
                        +RESET+((WateringCan) entry).getReminderCapacity());

        return new Result(false, BLUE+"کدوم سطل سلطان"+RESET);
    }
    public Result toolsEquip (String name) {

        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;

        for (Map.Entry<Items,Integer> entry: inventory.Items.entrySet()) {

            if (entry.getKey() instanceof Axe && name.equals("Axe")) {
                currentGame.currentPlayer.currentTool = (Tools) entry.getKey();
                return new Result(true, BRIGHT_BLUE+"Axe successfully picked up"+RESET);
            }
            else if (entry.getKey() instanceof FishingPole && name.equals("FishingPole")){
                currentGame.currentPlayer.currentTool = (Tools) entry.getKey();
                return new Result(true, BRIGHT_BLUE+"FishingPole successfully picked up"+RESET);
            }
            else if (entry.getKey() instanceof Hoe && name.equals("Hoe")){
                currentGame.currentPlayer.currentTool = (Tools) entry.getKey();
                return new Result(true, BRIGHT_BLUE+"Hoe successfully picked up"+RESET);
            }
            else if (entry.getKey() instanceof PickAxe && name.equals("PickAxe")){
                currentGame.currentPlayer.currentTool = (Tools) entry.getKey();
                return new Result(true, BRIGHT_BLUE+"PickAxe successfully picked up"+RESET);
            }
            else if (entry.getKey() instanceof WateringCan && name.equals("WateringCan")){
                currentGame.currentPlayer.currentTool = (Tools) entry.getKey();
                return new Result(true, BRIGHT_BLUE+"WateringCan successfully picked up"+RESET);
            }
            else if (entry.getKey() instanceof MilkPail && name.equals("MilkPail")){
                currentGame.currentPlayer.currentTool = (Tools) entry.getKey();
                return new Result(true, BRIGHT_BLUE+"MilkPail successfully picked up"+RESET);
            }
            else if (entry.getKey() instanceof Scythe && name.equals("Scythe")){
                currentGame.currentPlayer.currentTool = (Tools) entry.getKey();
                return new Result(true, BRIGHT_BLUE+"Scythe successfully picked up"+RESET);
            }
            else if (entry.getKey() instanceof Shear && name.equals("Shear")){
                currentGame.currentPlayer.currentTool = (Tools) entry.getKey();
                return new Result(true, BRIGHT_BLUE+"Shear successfully picked up"+RESET);
            }
        }
        return new Result(false,"there is no such tool");
    }
    public Result showCurrentTool() {

        Tools currentTool = currentGame.currentPlayer.currentTool;

        return switch (currentTool) {

            case Axe axe -> new Result(true, "current tool: " + axe.getType().getDisplayName());
            case null    -> new Result(false, "there is no current tool in your hands");
            case Hoe hoe -> new Result(true, "current tool: " + hoe.getType().getDisplayName());
            case MilkPail milkPail -> new Result(true, "current tool: " + milkPail.getName());
            case Scythe scythe -> new Result(true, "current tool: " + scythe.getName());
            case Shear shear   -> new Result(true, "current tool: " + shear.getName());
            case PickAxe pickAxe -> new Result(true, "current tool: " +
                    pickAxe.getType().getDisplayName());
            case FishingPole fishingPole -> new Result(true, "current tool: " +
                    fishingPole.type.getName());
            case WateringCan wateringCan -> new Result(true, "current tool: " +
                    wateringCan.getType().getDisplayName());
            default -> new Result(true, "current tool: " + currentTool.getName());
        };
    }
    public Result availableTools() {

        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;
        StringBuilder result = new StringBuilder();

        for (Map.Entry<Items, Integer> entry : inventory.Items.entrySet()) {

            if (entry.getKey() instanceof Axe)
                 result.append(BRIGHT_BLUE).append(((Axe) entry.getKey()).getType().getDisplayName()).append(RESET).append("\n");

            else if (entry.getKey() instanceof FishingPole)
                result.append(BRIGHT_RED).append(((FishingPole) entry.getKey()).type).append(RESET).append("\n");

            else if (entry.getKey() instanceof Hoe)
                result.append(BRIGHT_PURPLE).append(((Hoe) entry.getKey()).getType().getDisplayName()).append(RESET).append("\n");

            else if (entry.getKey() instanceof WateringCan)
                result.append(BRIGHT_GREEN).append(((WateringCan) entry.getKey()).getType().getDisplayName()).append(RESET).append("\n");

            else if (entry.getKey() instanceof PickAxe)
                result.append(BRIGHT_CYAN).append(((PickAxe) entry.getKey()).getType().getDisplayName()).append(RESET).append("\n");

            else if (entry.getKey() instanceof Tools)
                result.append(BRIGHT_BROWN).append(((Tools) entry.getKey()).getName()).append(RESET).append("\n");
        }
        if (result.isEmpty())
            return new Result(false, RED + "You don't have any tools" + RESET);
        return new Result(true, result.toString());
    }
    public Result upgradeTool (String name) {
         MarketType marketType=MarketType.wallOrDoor(currentGame.currentPlayer.getPositionX() , currentGame.currentPlayer.getPositionY());
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
                    HoeType hoeType=HoeType.getNextType(((Hoe) entry.getKey()).getType());
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
                        return new Result(true , name + "updated successfully");
                    }
                    else {
                        return new Result(false , "Not enough ingredient or money");
                    }
                }
            }
        }

        return null;
    }
    public Result useTools (String direction) {

        if (!currentGame.currentPlayer.isHealthUnlimited()) {
            if (currentGame.currentPlayer.getHealth() < currentGame.currentPlayer.currentTool.healthCost())
                return new Result(false, RED+"you are not in your hand"+RESET);

            currentGame.currentPlayer.increaseHealth(currentGame.currentPlayer.currentTool.healthCost());
        }

        System.out.println("Energy : "+currentGame.currentPlayer.getHealth());

        if (directionIncorrect(direction))
            return new Result(false, RED+"Direction is invalid"+RESET);

        int dir = Integer.parseInt(direction);

        Tools tools = currentGame.currentPlayer.currentTool;

        if (currentGame.currentPlayer.currentTool == null)
            return new Result(false, RED + "please pick up a tools" + RESET);

        if (tools instanceof Axe)
            return useAxe(dir);
        else if (tools instanceof Hoe) {
            if (currentGame.currentPlayer.currentTool.healthCost() > 0 && currentGame.currentPlayer.Buff_farming_hoursLeft > 0) currentGame.currentPlayer.increaseHealth(1);
            return useHoe(dir);
        } else if (tools instanceof MilkPail) {
            if (currentGame.currentPlayer.currentTool.healthCost() > 0 && currentGame.currentPlayer.Buff_farming_hoursLeft > 0) currentGame.currentPlayer.increaseHealth(1);
            return useMilkPail(dir);
        } else if (tools instanceof Scythe) {
            if (currentGame.currentPlayer.currentTool.healthCost() > 0 && currentGame.currentPlayer.Buff_farming_hoursLeft > 0) currentGame.currentPlayer.increaseHealth(1);
            return useScythe(dir);
        }
        else if (tools instanceof Shear) {
            if (currentGame.currentPlayer.currentTool.healthCost() > 0 && currentGame.currentPlayer.Buff_farming_hoursLeft > 0) currentGame.currentPlayer.increaseHealth(1);
            return useShear(dir);
        }
        else if (tools instanceof WateringCan)
            return useWateringCan(dir);
        else if (tools instanceof PickAxe)
            return usePickAxe(dir);

        return new Result(false, RED + "please pick up a tools" + RESET);
    }

                                                                    // input NPC command
    public Result meetNPC (String name) {

        NPC npc;
        try {
            npc = NPC.valueOf(name);
        } catch (Exception e) {
            return new Result(false, RED+"You're looking for someone who isn't real"+RESET);
        }

        if (!npc.isInHisHome(currentGame.currentPlayer.getPositionX(), currentGame.currentPlayer.getPositionY()))
            return new Result(false, RED+"You should go to their place first"+RESET);

        if (!currentGame.currentPlayer.getTodayTalking(npc)) {
            currentGame.currentPlayer.setTodayTalking(npc,true);
            currentGame.currentPlayer.increaseFriendshipPoint(npc, 20);
        }

        return new Result(true, npc.getName() + " : " + BLUE +
                npc.getDialogue(currentGame.currentPlayer.getFriendshipLevel(npc), currentGame.currentWeather)+RESET);
    }
    public Result giftNPC (String name, String itemName) {

        NPC npc;
        try {
            npc = NPC.valueOf(name);
        } catch (Exception e) {
            return new Result(false, RED+"You're looking for someone who isn't real"+RESET);
        }

        if (!npc.isInHisHome(currentGame.currentPlayer.getPositionX(), currentGame.currentPlayer.getPositionY()))
            return new Result(false, RED+"You should go to their place first"+RESET);

        Items item = AllFromDisplayNames(itemName);

        if (item == null)
            return new Result(false, RED+"You can only gift items from the market, crops and fruit"+RESET);

        Inventory inventory = currentGame.currentPlayer.getBackPack().inventory;

        if (inventory.Items.containsKey(item))
            return new Result(false, RED+"You don't have this item"+RESET);

        if (item instanceof Tools)
            return new Result(false, RED + "you can't gift tools" + RESET);
        advanceItem(item, -1);

        if (!currentGame.currentPlayer.getTodayGifting(npc)) {

            if (npc.isItFavorite(item))
                currentGame.currentPlayer.increaseFriendshipPoint(npc, 200);
            else
                currentGame.currentPlayer.increaseFriendshipPoint(npc, 50);
        } else {

            if (npc.isItFavorite(item))
                currentGame.currentPlayer.increaseFriendshipPoint(npc, 50);
            else
                currentGame.currentPlayer.increaseFriendshipPoint(npc, 15);
        }
        currentGame.currentPlayer.setTodayGifting(npc, true);
        return new Result(false, BRIGHT_BLUE+"Your gift successfully sent to "
                + BRIGHT_GREEN + npc.getName() + RESET);
    }
    public Result questsNPCList () {

        StringBuilder sb = new StringBuilder();

        sb.append("+").append(BRIGHT_PURPLE).append("-".repeat(100 - 2)).append(RESET).append("+\n");

        for (NPC npc : NPC.values())
            sb.append(OneNPCQuestsList(npc));

        sb.append("+").append(BRIGHT_PURPLE).append("-".repeat(100 - 2)).append(RESET).append("+");

        return new Result(true, sb.toString());
    }
    public Result friendshipNPCList () {

        StringBuilder sb = new StringBuilder();

        sb.append("+").append(RED).append("-".repeat(60 - 2)).append(RESET).append("+\n");

        for (NPC npc : NPC.values())
            sb.append(OneNPCFriendshipList(npc));

        sb.append(RED + "|").append(" ".repeat(60 - 2)).append("|\n").
                append(RESET).append("+").append(RED).append("-".repeat(60 - 2)).append(RESET).append("+");

        return new Result(true, sb.toString());
    }
    public Result doQuest (String name, String index) {

        int ID;
        try {
            ID = Integer.parseInt(index);
        } catch (Exception e) {
            return new Result(false, RED+"Index is invalid"+RESET);
        }

        NPC npc;
        try {
            npc = NPC.valueOf(name);
        } catch (Exception e) {
            return new Result(false, RED+"You're looking for someone who isn't real"+RESET);
        }
        if (!npc.isInHisHome(currentGame.currentPlayer.getPositionX(), currentGame.currentPlayer.getPositionY()))
            return new Result(false, RED+"You should go to their place first"+RESET);

        return switch (ID) {
            case 1 -> doTask1(npc);
            case 2 -> doTask2(npc);
            case 3 -> doTask3(npc);
            default -> new Result(false, RED + "Index is invalid" + RESET);
        };
    }



    public Result getObject (String direction) {

        return new Result(true, PURPLE + getTileByDir(Integer.parseInt(direction)).getGameObject().toString() + RESET);
    }
    public Result getObject2 (String x, String y) {

        return new Result(true, PURPLE + getTileByCoordinates(Integer.parseInt(x), Integer.parseInt(y)).getGameObject().toString() + RESET);
    }
    public void remove (int x) {

        getTileByDir(x).setGameObject(new Walkable());
    }
    public void clear () {

        for (User user : currentGame.players)
            for (Tile tile : user.getFarm().Farm)
                if (tile.getGameObject() instanceof Walkable ||
                        tile.getGameObject() instanceof Tree || tile.getGameObject() instanceof BasicRock)
                    tile.setGameObject(new Walkable());
        Walkable walkable = new Walkable();
        walkable.setGrassOrFiber("Plowed");
        getTileByCoordinates(7, 10).setGameObject(walkable);
    }
    public void plantCreator () {


    }
}