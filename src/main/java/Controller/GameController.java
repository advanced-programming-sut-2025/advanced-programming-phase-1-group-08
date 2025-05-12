package Controller;

import model.*;
import model.Animall.Animal;
import model.Animall.Animalproduct;
import model.Animall.BarnOrCage;
import model.Animall.Fish;
import model.Enum.AllPlants.*;
import model.Enum.Commands.GameMenuCommands;
import model.Enum.Door;
import model.Enum.ItemType.*;
import model.Enum.NPC;
import model.Enum.ToolsType.*;
import model.Enum.WeatherTime.Season;
import model.Enum.ItemType.WallType;
import model.Enum.WeatherTime.Weather;
import model.MapThings.*;
import model.OtherItem.*;
import model.Places.*;
import model.Plants.*;
import model.ToolsPackage.*;

import java.util.*;

import static model.App.*;
import static model.App.tomorrowWeather;
import static model.Color_Eraser.*;
import static model.DateHour.getDayDifferent;
import static model.Enum.AllPlants.ForagingMineralsType.*;

import static model.Enum.AllPlants.ForagingMineralsType.RUBY;
import static model.SaveData.UserDataBase.findUserByUsername;


public class GameController {

    Random rand = new Random();

    public Result addDollar(int amount) {
        currentPlayer.increaseMoney(amount - currentPlayer.getMoney());
        return new Result(true , "your money cheated successfully");
    }

    public Result addItem(int amount) {
        Inventory inventory= currentPlayer.getBackPack().inventory;

        if (currentPlayer.getBackPack().getType().getRemindCapacity() == 0) {
            return new Result(false , "you can't add an item to your inventory because there is no remind capacity");
        }
        for (ArtisanProduct artisanProduct )
    }

    public ArrayList<Tile> sortMap(ArrayList<Tile> Map) {
        Collections.sort(Map , (a,b) -> {
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

        for (User user : players) {

            GreenHouse greenHouse = user.getFarm().getGreenHouse();

            if (tile.getX() >= greenHouse.getCoordinateX() &&
                    tile.getY() >= greenHouse.getCoordinateY() &&
                    tile.getX() <= greenHouse.getCoordinateX() + greenHouse.getLength() &&
                    tile.getY() <= greenHouse.getCoordinateY() + greenHouse.getWidth())
                return true;
        }
        return false;
    }

    private boolean checkDirection (String dir) {

        try {
            int x = Integer.parseInt(dir);
            return x >= 1 && x <= 8;

        } catch (Exception e) {
            return false;
        }
    }
    private boolean checkAmountProductAvailable (Items items, int number) {

        Inventory inventory=currentPlayer.getBackPack().inventory;

        for (Map.Entry<Items,Integer> entry: inventory.Items.entrySet()) {

            if (entry instanceof MixedSeeds && items instanceof MixedSeeds)
                return entry.getValue() >= number;
            if (entry instanceof AllCrops && items instanceof AllCrops &&
                    ((AllCrops) entry).getType().equals(((AllCrops) items).getType()))
                return entry.getValue() >= number;
            if (entry instanceof ForagingSeeds && items instanceof ForagingSeeds &&
                    ((ForagingSeeds) entry).getType().equals(((ForagingSeeds) items).getType()))
                return entry.getValue() >= number;
            if (entry instanceof TreesProdct && items instanceof TreesProdct &&
                    ((TreesProdct) entry).getType().equals(((TreesProdct) items).getType()))
                return entry.getValue() >= number;
            if (entry instanceof TreeSource && items instanceof TreeSource &&
                    ((TreeSource) entry).getType().equals(((TreeSource) items).getType()))
                return entry.getValue() >= number;
            if (entry instanceof ForagingCrops && items instanceof ForagingCrops &&
                    ((ForagingCrops) entry).getType().equals(((ForagingCrops) items).getType()))
                return entry.getValue() >= number;
            if (entry instanceof ForagingMinerals && items instanceof ForagingMinerals &&
                    ((ForagingMinerals) entry).getType().equals(((ForagingMinerals) items).getType()))
                return entry.getValue() >= number;
            if (entry instanceof MarketItem && items instanceof MarketItem &&
                    ((MarketItem) entry).getType().equals(((MarketItem) items).getType()))
                return entry.getValue() >= number;

        }
        return false;
    } // اینجا مساوی هم گذاشتم
    private void advanceItem(Items items, int amount) {

        Inventory inventory = currentPlayer.getBackPack().inventory;

        for (Map.Entry<Items,Integer> entry: inventory.Items.entrySet()) {

            if (entry instanceof MixedSeeds && items instanceof MixedSeeds) {
                inventory.Items.put(entry.getKey(), entry.getValue() + amount);
                return;

            } else if (entry instanceof AllCrops && items instanceof AllCrops &&
                    ((AllCrops) entry).getType().equals(((AllCrops) items).getType())) {
                inventory.Items.put(entry.getKey(), entry.getValue() + amount);
                return;

            } else if (entry instanceof ForagingSeeds && items instanceof ForagingSeeds &&
                    ((ForagingSeeds) entry).getType().equals(((ForagingSeeds) items).getType())) {
                inventory.Items.put(entry.getKey(), entry.getValue() + amount);
                return;

            } else if (entry instanceof TreesProdct && items instanceof TreesProdct &&
                    ((TreesProdct) entry).getType().equals(((TreesProdct) items).getType())) {
                inventory.Items.put(entry.getKey(), entry.getValue() + amount);
                return;

            } else if (entry instanceof TreeSource && items instanceof TreeSource &&
                    ((TreeSource) entry).getType().equals(((TreeSource) items).getType())) {
                inventory.Items.put(entry.getKey(), entry.getValue() + amount);
                return;

            } else if (entry instanceof ForagingCrops && items instanceof ForagingCrops &&
                    ((ForagingCrops) entry).getType().equals(((ForagingCrops) items).getType())) {
                inventory.Items.put(entry.getKey(), entry.getValue() + amount);
                return;

            } else if (entry instanceof ForagingMinerals && items instanceof ForagingMinerals &&
                    ((ForagingMinerals) entry).getType().equals(((ForagingMinerals) items).getType())) {
                inventory.Items.put(entry.getKey(), entry.getValue() + amount);
                return;

            } else if (entry instanceof MarketItem && items instanceof MarketItem &&
                    ((MarketItem) entry).getType().equals(((MarketItem) items).getType())) {
                inventory.Items.put(entry.getKey(), entry.getValue() + amount);
                return;
            }
        }
        inventory.Items.put(items, amount);
    } // برای کم کردن الو چک بشه اون تعداد داریم یا نه


    public Tile getTileByDir (int dir) {

        int x = currentPlayer.getPositionX();
        int y = currentPlayer.getPositionY();

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
        Tile targetTile = bigMap.get(90 * y + x);
        return targetTile;
    }   ///        الان اینونتوری ساخته میشه از همه چی توش گذاشتی همون اول ؟


    public void createInitialMine( int x, int y , int topLeftX, int topLeftY, int width , int height) {
        Farm farm = currentPlayer.getFarm();

        Mine mine = new Mine(topLeftX + 60 *x , topLeftY + 60 * y ,width , height);
        door MineDoor = new door();
        MineDoor.setDoor(Door.Mine);
        MineDoor.setCharactor('D');
        mine.setCharactor('M');
        Walkable walkable = new Walkable();
        walkable.setCharactor('.');

        for (int i = topLeftX + 60 * x ; i < topLeftX + 60*x + width; i++) {
            for (int j = topLeftY + 60 * y; j < topLeftY + 60 * y + height; j++) {
                if (i == topLeftX + 60 * x + width / 2 && j == topLeftY + 60*y + height-1) {
                    Tile tile = new Tile(i, j, MineDoor);
                    farm.Farm.add(tile);
                    bigMap.add(tile);
                } else if (i == topLeftX + 6*x || i == topLeftX + 60*x + width-1 || j == topLeftY + 60*y || j == topLeftY + 60*y + height-1) {
                    Tile tile = new Tile(i, j, mine);
                    farm.Farm.add(tile);
                    bigMap.add(tile);
                } else {
                    Tile tile = new Tile(i, j, walkable);
                    farm.Farm.add(tile);
                    bigMap.add(tile);
                }
            }
        }
        farm.setMine(mine);
    }

    public void createInitialLake( int x, int y , int topLeftX , int topLeftY , int width , int height) {
        Farm farm = currentPlayer.getFarm();
        Lake lake = new Lake(topLeftX, topLeftY, width, height);
        lake.setCharactor('L');
        for (int i = topLeftX + 60 * x; i < topLeftX + 60 * x + width; i++) {
            for (int j = topLeftY + 60 * y; j < topLeftY + 60 * y + height; j++) {
                Tile tile = new Tile(i, j, lake);
                farm.Farm.add(tile);
                bigMap.add(tile);
            }
        }
        farm.setLake(lake);

    }
    public void createInitialHouse(int id, int x, int y , int topLeftX , int topLeftY , int width , int height) {
        Farm farm = currentPlayer.getFarm();
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
                    bigMap.add(tile);
                }
                if (i == topLeftX + 60 * x + width -2 && j == topLeftY + 60 * y + height-2) {
                    Tile tile = new Tile(i, j, fridge);
                    farm.Farm.add(tile);
                    bigMap.add(tile);
                }
                if (i == topLeftX || i == topLeftX + width -1 || j==topLeftY+60*y || j==topLeftY+60*y + height-1) {
                    Tile tile = new Tile(i, j, wall);
                    farm.Farm.add(tile);
                    bigMap.add(tile);
                }  else {
                    Tile tile = new Tile(i, j, home);
                    farm.Farm.add(tile);
                    bigMap.add(tile);
                }
            }
        }
        farm.setHome(home);
    }
    public void createInitialGreenHouse(int x, int y , int topLeftX , int topLeftY , int width , int height) {
        Farm farm = currentPlayer.getFarm();
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

        for (int i= topLeftX + 60 * x ; i< topLeftX + 60 * x + width; i++){
            for (int j=topLeftY + 60 * y ; j<topLeftY + 60 * y + height ; j++) {
                if (i==topLeftX + 60 * x + width/2 && j==topLeftY + 60 * y + height-1) {
                    Tile tile = new Tile(i, j, greenHouseDoor);
                    farm.Farm.add(tile);
                    bigMap.add(tile);
                }
                if (i== topLeftX + 60*x + width/2 && j==topLeftY + 60*y + 1) {
                    Tile tile = new Tile(i, j, waterTank);
                    farm.Farm.add(tile);
                    bigMap.add(tile);
                }
                if (i == topLeftX || i == topLeftX + width -1 || j==topLeftY+60*y || j==topLeftY+60*y + height-1) {
                    Tile tile = new Tile(i, j, GreenWall);
                    farm.Farm.add(tile);
                    bigMap.add(tile);
                }

                else {
                    Tile tile = new Tile(i, j, greenHouse);
                    farm.Farm.add(tile);
                    bigMap.add(tile);
                }
            }
        }
        farm.setGreenHouse(greenHouse);
    }
    public Farm createInitialFarm(int id){
        long seed=System.currentTimeMillis();
        Farm farm= currentPlayer.getFarm();
        Mine mine=null;
        Lake lake=null;
        Home home=null;
        GreenHouse greenHouse=null;
        Fridge fridge=null;

        if (id ==1 ) {
            mine = new Mine (23 , 2 , 6 , 6);
            lake = new Lake(2 , 23 , 5 , 6);
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

        for (int i=0 ; i<30 ;i++){
            for (int j=0 ; j<30 ; j++) {
                if (i >= mine.getStartX() && i<mine.getStartX() + mine.getWidth() && j>=mine.getStartY() && j< mine.getStartY() + mine.getHeight() ) {
                    if (! createMine) {
                        createInitialMine(currentPlayer.topLeftX, currentPlayer.topLeftY, mine.getStartX(), mine.getStartY(), mine.getWidth(), mine.getHeight());
                        createMine = true;
                    }
                }
                else if(i >= lake.getTopLeftX() && i< lake.getTopLeftX() + lake.getWidth() && j>=lake.getTopLeftY() && j< lake.getTopLeftY() + lake.getHeight()) {
                    if (! createLake) {
                        createInitialLake(currentPlayer.topLeftX, currentPlayer.topLeftY, lake.getTopLeftX(), lake.getTopLeftY(), lake.getWidth(), lake.getHeight());
                        createLake = true;
                    }
                }
                else if(i>=greenHouse.getCoordinateX()  && i < greenHouse.getCoordinateX() + greenHouse.getWidth()
                        && j>=greenHouse.getCoordinateY() && j<greenHouse.getCoordinateY() + greenHouse.getWidth() ) {

                    if (! createGreenHouse) {
                        createInitialGreenHouse(currentPlayer.topLeftX, currentPlayer.topLeftY, greenHouse.getCoordinateX(), greenHouse.getCoordinateY(),
                                greenHouse.getWidth(), greenHouse.getLength());

                        createGreenHouse = true;
                    }
                }
                else if (i >= home.getTopLeftX() && i<home.getTopLeftX() + home.getWidth() && j >= home.getTopLeftY() && j<home.getTopLeftY() + home.getLength() ) {
                    if (! createHome) {
                        createInitialGreenHouse(currentPlayer.topLeftX, currentPlayer.topLeftY, home.getTopLeftX(), home.getTopLeftY(), home.getWidth(), home.getLength());
                        createHome = true;
                    }
                }
                else {
                    MapGenerator(i,j,seed);
                }
            }
        }
        farms.add(farm);

        return farm;

    }

    public void buildHall() {
        Walkable walkable=new Walkable();
        UnWalkable unWalkable=new UnWalkable();
        for (int j = 30 ; j<60 ; j++) {
            Tile tile=new Tile(15 , j , walkable);
            bigMap.add(tile);
        }
        for (int i=30 ; i<60 ; i++) {
            Tile tile=new Tile(i , 15 , walkable);
            bigMap.add(tile);
        }
        for (int i = 30 ; i<60 ; i++) {
            Tile tile=new Tile(i , 75 , walkable);
            bigMap.add(tile);
        }
        for (int j=30 ; j<60 ; j++) {
            Tile tile=new Tile(75 , j , walkable);
            bigMap.add(tile);
        }
        for (int i=30 ; i<60 ; i++) {
            for (int j=0 ; j<30 ; j++) {
                if (j != 15) {
                    Tile tile=new Tile(i , j , unWalkable);
                    bigMap.add(tile);
                }
            }
        }
        for (int i=0 ; i<30 ; i++) {
            for (int j=30 ; j<60 ; j++) {
                if (i!=15) {
                    Tile tile=new Tile(i , j , unWalkable);
                    bigMap.add(tile);
                }
            }
        }
        for (int i=30 ; i<60 ; i++) {
            for (int j=60 ; j<90 ; j++) {
                if (j!=75) {
                    Tile tile=new Tile(i , j , unWalkable);
                    bigMap.add(tile);
                }
            }
        }
        for (int i=60 ; i<90 ; i++) {
            for (int j=30 ; j<60 ; j++) {
                if (i!=75) {
                    Tile tile=new Tile(i , j , unWalkable);
                    bigMap.add(tile);
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
        Walkable walkable=new Walkable();

        for (int i=30 ; i<60 ; i++) {
            for (int j=30 ; j<60 ; j++) {
                if (i== 30 || i==59 || j==30 || j==59) {
                    if ( (i==30 && j==45 ) || (i==45 && j==30) || (i==45 && j==59) || (i==59 && j==45) ) {
                        Tile tile=new Tile(i , j , dor);
                        bigMap.add(tile);
                    }
                    else {
                        Tile tile = new Tile(i, j, wall);
                        bigMap.add(tile);
                    }
                }
                else if (MarketType.isInMarket(i, j) != null) {
                    MarketType marketType = MarketType.isInMarket(i, j);
                    if (i== marketType.getTopleftx() + marketType.getWidth() - 1 && j==marketType.getToplefty() +2) {
                        Tile tile=new Tile(i , j , dor);
                        bigMap.add(tile);
                    }
                    else {
                        Tile tile=new Tile(i , j , wall);
                        bigMap.add(tile);
                    }
                }
                else if (NPC.wallOrDoor(i, j) != null) {
                    NPC npc = NPC.wallOrDoor(i, j);
                    if (i == npc.getTopLeftX() + npc.getWidth() -1 && j==npc.getTopLeftY() + 2) {
                        Tile tile=new Tile(i , j , dor);
                        bigMap.add(tile);
                    }
                    else {
                        Tile tile=new Tile(i , j , wall);
                        bigMap.add(tile);
                    }
                }
                else {
                    Tile tile=new Tile(i , j , walkable);
                    bigMap.add(tile);
                }
            }
        }
    }



    public void MapGenerator(int i,int j,long seed){
        if (i==0 || i==29 || j==0 || j==29){
            if (i==15 && j==29){
                door FarmDoor=new door();
                FarmDoor.setDoor(Door.Farm);
                FarmDoor.setCharactor('D');
                Tile tile=new Tile(i + 60*currentPlayer.topLeftX,j + 30*currentPlayer.topLeftY,FarmDoor);
                currentPlayer.getFarm().Farm.add(tile);
                bigMap.add(tile);
            }
            else if (i==29 && j==15){
                door FarmDoor=new door();
                FarmDoor.setDoor(Door.Farm);
                FarmDoor.setCharactor('D');
                Tile tile=new Tile(i + 30*currentPlayer.topLeftX,j + 60*currentPlayer.topLeftY,FarmDoor);
                currentPlayer.getFarm().Farm.add(tile);
                bigMap.add(tile);
            }
            else {
                Walkable walkable = new Walkable();
                walkable.setCharactor('.');
                Tile tile = new Tile(i + 60 * currentPlayer.topLeftX, j + 60 * currentPlayer.topLeftY, walkable);
                currentPlayer.getFarm().Farm.add(tile);
                bigMap.add(tile);
            }

        }
        else {

            PerlinNoise perlinNoise = new PerlinNoise(seed);

            double noise = perlinNoise.noise(i * 0.1, j * 0.1);
            if (-1.2 < noise && noise < -0.9) {
                Tree tree = new Tree(TreeType.OakTree,currentDate);
                Tile tile = new Tile(i + 60 * currentPlayer.topLeftX, j + 60 * currentPlayer.topLeftY, tree);
                currentPlayer.getFarm().Farm.add(tile);
                bigMap.add(tile);
            }
            else if(noise > -0.9 && noise <-0.5){
                Tree tree = new Tree(TreeType.MapleTree,currentDate);
                Tile tile = new Tile(i + 60 * currentPlayer.topLeftX, j + 60 * currentPlayer.topLeftY, tree);
                currentPlayer.getFarm().Farm.add(tile);
                bigMap.add(tile);
            }
            else if (noise > -0.5 && noise < - 0.2){
                Tree tree = new Tree(TreeType.PineTree,currentDate);
                Tile tile = new Tile(i + 60 * currentPlayer.topLeftX, j + 60 * currentPlayer.topLeftY, tree);
                currentPlayer.getFarm().Farm.add(tile);
                bigMap.add(tile);
            }
            else if (-0.1 < noise && noise < 0.0) {
                BasicRock basicRock = new BasicRock();
                basicRock.setCharactor('S');
                Tile tile = new Tile(i + 60 * currentPlayer.topLeftX, j + 60 * currentPlayer.topLeftY, basicRock);
                currentPlayer.getFarm().Farm.add(tile);
                bigMap.add(tile);
            } else {
                Walkable walkable = new Walkable();
                walkable.setCharactor('.');
                Tile tile = new Tile(i + 60 * currentPlayer.topLeftX, j + 60 * currentPlayer.topLeftY, walkable);
                currentPlayer.getFarm().Farm.add(tile);
                bigMap.add(tile);
            }

        }

    }

    public void print(Farm farm){
        int x=currentPlayer.topLeftX;
        int y=currentPlayer.topLeftY;

        for (int i=60 * x ; i<60 * x +30 ; i++) {
            for (int j = 60 * y; j < 60 * y + 30; j++) {

                Tile tile = getTileByCoordinates(j, i);

                if (tile.getGameObject() instanceof Walkable) {
                    System.out.print(WHITE + tile.getGameObject().getCharactor() + RESET+" ");
                } else if (tile.getGameObject() instanceof BasicRock) {
                    System.out.print(GRAY + tile.getGameObject().getCharactor() + RESET+" ");
                } else if (tile.getGameObject() instanceof Tree) {
                    System.out.print(GREEN + tile.getGameObject().getCharactor() + RESET+" ");
                } else if (tile.getGameObject() instanceof Lake) {
                    System.out.print(BLUE + tile.getGameObject().getCharactor() + RESET+" ");
                } else if (tile.getGameObject() instanceof Mine) {
                    System.out.print(RED + tile.getGameObject().getCharactor() + RESET+" ");
                } else if (tile.getGameObject() instanceof Wall) {
                    System.out.print(WHITE + tile.getGameObject().getCharactor() + RESET+" ");
                } else if (tile.getGameObject() instanceof Home) {
                    System.out.print(YELLOW + tile.getGameObject().getCharactor() + RESET+" ");
                } else if (tile.getGameObject() instanceof door) {
                    System.out.print(Brown + tile.getGameObject().getCharactor() + RESET+" ");
                } else if (tile.getGameObject() instanceof GreenHouse) {
                    System.out.print(GRAY + tile.getGameObject().getCharactor() + RESET+" ");
                } else if (tile.getGameObject() instanceof Fridge) {
                    System.out.print(WHITE + tile.getGameObject().getCharactor() + RESET+" ");
                } else if (tile.getGameObject() instanceof WaterTank) {
                    System.out.print(BLUE + tile.getGameObject().getCharactor() + RESET+" ");
                }
            }
            System.out.println();

        }

    }


    public Result walk(int goalX, int goalY) {
        int startX = currentPlayer.getPositionX();
        int startY = currentPlayer.getPositionY();
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
            if (checkConditionsForWalk(tile.getX() , tile.getY()) !=null) {
                State state=new State(startX , startY , dir , 0);
                break;
            }
        }

        int [] dirx={0,0,1,1,1,-1,-1,-1};
        int [] diry={1,-1,0,1,-1,0,1,-1};

        while (!queue.isEmpty()) {
            State current = queue.poll();
            if (current.x == goalX && current.y == goalY) {
                if (currentPlayer.isHealthUnlimited()){
                    currentPlayer.setPositionX(goalX);
                    currentPlayer.setPositionY(goalY);
                    return new Result(true,"now you are in "+goalX+","+goalY);
                }
                if (currentPlayer.getHealth() >= current.Energy ) {
                    currentPlayer.increaseHealth(- current.Energy);
                    currentPlayer.setPositionX(goalX);
                    currentPlayer.setPositionY(goalY);
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
                if (checkConditionsForWalk(x,y) ==null) {
                    continue;
                }
                int turnCost= (i+1==current.direction) ? 0:10;
                int totalEnergy=current.Energy;
                if (turnCost == 0) {
                    totalEnergy++;
                }
                else {
                    totalEnergy+=turnCost;
                }
                queue.add(new State(x , y , i+1 , totalEnergy));
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
        Tile tile = getTileByCoordinates(goalX, goalY);
        Farm farm = null;

        if (goalX <0 || goalX >90 || goalY <0 || goalY >90) {
            return new Result(false,"you can't walk out of bounds");
        }

        for (Farm farms : farms) {
            if (farms.Farm.contains(tile)) {
                farm = farms;
                break;
            }
        }
        for (User user : players) {
            if (user.getFarm().equals(farm)){
                if (!user.getSpouse().equals(currentPlayer) && !user.equals(currentPlayer)){
                    return new Result(false,"you can't go to this farm");
                }
            }
        }

        if (tile.getGameObject() instanceof GreenHouse) {
            if (!((GreenHouse) tile.getGameObject()).isCreated()){
                return new Result(false,"GreenHouse is not created yet");
            }
        }

        for (User user : users) {
            if (user.getPositionX()==goalX && user.getPositionY()==goalY){
                return new Result(true,"you can't go to this coordinate");
            }
        }
        if (!checkTile(tile)){
            return new Result(false,"you can't go to this coordinate");
        }

        for (Market market : markets) {
            if (goalX > market.getTopLeftX() && goalY > market.getTopLeftY()){
                if (goalX < market.getTopLeftX() + market.getWidth() && goalY < market.getTopLeftY() + market.getHeight()){
                    if (market.getMarketType().getStartHour() > currentDate.getHour() || market.getMarketType().getEndHour() < currentDate.getHour()){
                        return new Result(false , "you can't go to Market because it is not open");
                    }
                }
            }
        }
        return null;

    }

    public Result showInventory(Inventory inventory){
        StringBuilder output = new StringBuilder();
        output.append("Items:").append('\n');

        for (Map.Entry <Items,Integer> entry: inventory.Items.entrySet()){
            if (entry.getKey() instanceof BasicRock){
                output.append("Stone: ").append(entry.getValue()).append('\n');
            }
            else if (entry.getKey() instanceof Wood){
                output.append("Wood: ").append(entry.getValue()).append('\n');
            }
            else if (entry.getKey() instanceof ForagingMinerals){
                output.append(((ForagingMinerals) entry.getKey()).getType().getDisplayName()).append(": ").append(entry.getValue()).append('\n');
            }
            else if (entry.getKey() instanceof ForagingSeeds){
                output.append(((ForagingSeeds) entry.getKey()).getType().getDisplayName()).append(": ").append(entry.getValue()).append('\n');
            }
            else if (entry.getKey() instanceof AllCrops){
                output.append(((AllCrops) entry.getKey()).getType().getDisplayName()).append(": ").append(entry.getValue()).append('\n');
            }
            else if (entry.getKey() instanceof ForagingCrops) {
                output.append(((ForagingCrops) entry.getKey()).getType().getDisplayName()).append(": ").append(entry.getValue()).append('\n');
            }
            else if (entry.getKey() instanceof TreeSource){
                output.append(((TreeSource) entry.getKey()).getType().getDisplayName()).append(": ").append(entry.getValue()).append('\n');
            }
            else if (entry.getKey() instanceof Axe ){
                output.append(((Axe) entry.getKey()).getType().getDisplayName()).append('\n');
            }
            else if (entry.getKey() instanceof FishingPole){
                output.append(((FishingPole) entry.getKey()).type.name()).append('\n');
            }
            else if (entry.getKey() instanceof Hoe){
                output.append(((Hoe) entry.getKey()).getType().getDisplayName()).append('\n');
            }
            else if (entry.getKey() instanceof PickAxe){
                output.append(((PickAxe) entry.getKey()).getType().getDisplayName()).append('\n');
            }
            else if (entry.getKey() instanceof WateringCan){
                output.append(((WateringCan) entry.getKey()).getType().getDisplayName()).append('\n');
            }
            else if (entry.getKey() instanceof TrashCan){
                output.append(((TrashCan) entry.getKey()).type.name()).append('\n');
            }
            else if (entry.getKey() instanceof Tools){
                output.append(((Tools) entry.getKey()).getName()).append(": ").append(entry.getValue()).append('\n');
            }
            else if (entry.getKey() instanceof MarketItem) {
                output.append(((MarketItem) entry.getKey()).getType().getName()).append(": ").append(entry.getValue()).append('\n');
            }
            else if (entry.getKey() instanceof Fish) {
                output.append(((Fish) entry.getKey()).getFishType().getName()).append(": ") .append(((Fish) entry.getKey()).getQuantity().getName()).append('\n');
            }
            else if (entry.getKey() instanceof Animalproduct) {
                output.append(((Animalproduct) entry.getKey()).getAnimalProductType().getName()).append(": ").append(((Animalproduct) entry.getKey()).getQuantity().getName()).append('\n');
            }
            else if (entry.getKey() instanceof CraftingItem) {
                output.append(((CraftingItem) entry.getKey()).getCraftType().getName()).append(": ").append(entry.getValue()).append('\n');
            }
            else if (entry.getKey() instanceof ArtisanProduct) {
                output.append(((ArtisanProduct) entry.getKey()).getType().getName()).append(": ").append(entry.getValue()).append('\n');
            }
        }

        return new Result(true,output.toString());
    }

    private Result increaseMoney(Integer amount , int price , Items items,String name , Integer reminder) {
        int percent=0;
        for (Map.Entry<Items,Integer> entry: currentPlayer.getBackPack().inventory.Items.entrySet()) {
            if (entry.getKey() instanceof TrashCan){
                percent= ((TrashCan) entry.getKey()).type.getPercent();
                break;
            }
        }
        if (amount ==null || amount.equals(reminder)) {
            int increase=(reminder * percent *price)/100;
            TrashCan.removeItem(increase,currentPlayer.getBackPack().inventory.Items, items, reminder);
            return new Result(true,name + "completely removed from your inventory");
        }
        if (amount > reminder) {
            return new Result(false,"not enough "+name+" "+"in your inventory for remove");
        }
        int increase = (reminder * percent * price) / 100;
        TrashCan.removeItem(increase,currentPlayer.getBackPack().inventory.Items, items, reminder);
        return new Result(true , amount + " "+name+" "+"removed from your inventory");

    }



    public Result removeItemToTrashcan (String name, Integer number){
        Inventory inventory=currentPlayer.getBackPack().inventory;
        for (Map.Entry<Items,Integer> entry: inventory.Items.entrySet()){

            if (entry.getKey() instanceof Wood){
                if (name.equals(Wood.name)) {
                    return increaseMoney(number, Wood.price, (Wood) entry.getKey(), name, entry.getValue());
                }
            }
            if (entry.getKey() instanceof BasicRock){
                if (name.equals("Stone")) {
                    return increaseMoney(number, BasicRock.price, (BasicRock) entry.getKey(), name, entry.getValue());
                }
            }

            if (entry.getKey() instanceof ForagingMinerals){
                if (((ForagingMinerals) entry.getKey()).getType().getDisplayName().equals(name)){
                    return increaseMoney(number,((ForagingMinerals) entry.getKey()).getType().getPrice(),entry.getKey(), name,entry.getValue());
                }
            }


            if (entry.getKey() instanceof AllCrops){
                if (((AllCrops) entry.getKey()).getType().getDisplayName().equals(name)){
                    return increaseMoney(number, ((AllCrops) entry.getKey()).getType().getPrice(), entry.getKey(), name,entry.getValue());
                }
            }

            if (entry.getKey() instanceof ForagingCrops){
                if (((ForagingCrops) entry.getKey()).getType().getDisplayName().equals(name)){
                    return increaseMoney(entry.getValue(),((ForagingCrops) entry.getKey()).getType().getPrice(), entry.getKey(), name,entry.getValue());
                }
            }
            if (entry.getKey() instanceof Tools){
                return new Result(false,"you can't remove "+name+"because it is a tool");
            }

        }
        return null;
    }

    public boolean checkCoordinateForFishing(){
        int [] x={1,1,1,0,0,-1,-1,-1};
        int [] y={1,0,-1,1,-1,-1,0,1};
        for (int i=0;i<8;i++){
            if (getTileByCoordinates(currentPlayer.getPositionX() +x[i],currentPlayer.getPositionY() +y[i]).
                    getGameObject() instanceof Lake){
                return true;
            }
        }
        return false;
    }

    public FishingPole isFishingPoleTypeExist(String name){
        Inventory inventory=currentPlayer.getBackPack().inventory;
        for (Map.Entry<Items,Integer> entry: inventory.Items.entrySet()){
            if (entry instanceof FishingPole){
                if (((FishingPole) entry).type.getName().equals(name)){
                    return (FishingPole) entry;
                }
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
        Inventory inventory=currentPlayer.getBackPack().inventory;
        double random = Math.random();
        int x = (int) (random * currentWeather.getFishing() * (currentPlayer.getLevelFishing() + 2));
        int numberOfFish = Math.min(6, x);
        StringBuilder result = new StringBuilder("number of Fishes: " + numberOfFish + "\n");
        ArrayList<Fish> fishes = new ArrayList<>();

        for (int i = 0; i < numberOfFish; i++) {

            double rand = Math.random();
            double quantity = (random * (currentPlayer.getLevelFishing() + 2) * fishingPole.type.getCoefficient()) / (7 - currentWeather.getFishing());
            Quantity fishQuantity = productQuantity(quantity);

            if (fishingPole.type.equals(FishingPoleType.TrainingRod)) {

                switch (currentDate.getSeason()) {
                    case Spring:
                        Fish springFish = new Fish(FishType.Herring, fishQuantity);
                        fishes.add(springFish);
                        result.append(springFish.getFishType().getName()).append(springFish.getQuantity()).append("\n");
                    case Summer:
                        Fish summerFish = new Fish(FishType.Sunfish, fishQuantity);
                        fishes.add(summerFish);
                        result.append(summerFish.getFishType().getName()).append(summerFish.getQuantity()).append("\n");
                    case Fall:
                        Fish fallFish = new Fish(FishType.Sardine, fishQuantity);
                        fishes.add(fallFish);
                        result.append(fallFish.getFishType().getName()).append(fallFish.getQuantity()).append("\n");
                    case Winter:
                        Fish winterFish = new Fish(FishType.Perch, fishQuantity);
                        fishes.add(winterFish);
                        result.append(winterFish.getFishType().getName()).append(winterFish.getQuantity()).append("\n");
                    default:
                        break;
                }

            }

            else if (rand <= 0.2 || ( rand > 0.8 && rand <= 0.85 && currentPlayer.getLevelFishing()!=4) ){

                switch (currentDate.getSeason()) {
                    case Spring:
                        Fish springFish = new Fish(FishType.Flounder, fishQuantity);
                        fishes.add(springFish);
                        result.append(springFish.getFishType().getName()).append(springFish.getQuantity()).append("\n");
                    case Summer:
                        Fish summerFish = new Fish(FishType.Tilapia, fishQuantity);
                        fishes.add(summerFish);
                        result.append(summerFish.getFishType().getName()).append(summerFish.getQuantity()).append("\n");
                    case Fall:
                        Fish fallFish = new Fish(FishType.Salmon, fishQuantity);
                        fishes.add(fallFish);
                        result.append(fallFish.getFishType().getName()).append(fallFish.getQuantity()).append("\n");
                    case Winter:
                        Fish winterFish = new Fish(FishType.Midnight_Carp, fishQuantity);
                        fishes.add(winterFish);
                        result.append(winterFish.getFishType().getName()).append(winterFish.getQuantity()).append("\n");
                    default:
                        break;
                }
            } else if (rand <= 0.4 || (rand > 0.85 && rand <= 0.9 && currentPlayer.getLevelFishing() != 4)) {

                switch (currentDate.getSeason()) {
                    case Spring:
                        Fish springFish = new Fish(FishType.Lionfish, fishQuantity);
                        fishes.add(springFish);
                        result.append(springFish.getFishType().getName()).append(springFish.getQuantity()).append("\n");
                    case Summer:
                        Fish summerFish = new Fish(FishType.Dorado, fishQuantity);
                        fishes.add(summerFish);
                        result.append(summerFish.getFishType().getName()).append(summerFish.getQuantity()).append("\n");
                    case Fall:
                        Fish fallFish = new Fish(FishType.Sardine, fishQuantity);
                        fishes.add(fallFish);
                        result.append(fallFish.getFishType().getName()).append(fallFish.getQuantity()).append("\n");
                    case Winter:
                        Fish winterFish = new Fish(FishType.Squid, fishQuantity);
                        fishes.add(winterFish);
                        result.append(winterFish.getFishType().getName()).append(winterFish.getQuantity()).append("\n");
                    default:
                        break;
                }
            } else if (rand <= 0.6 || (rand > 0.9 && rand <= 0.95 && currentPlayer.getLevelFishing() != 4)) {

                switch (currentDate.getSeason()) {
                    case Spring:
                        Fish springFish = new Fish(FishType.Herring, fishQuantity);
                        fishes.add(springFish);
                        result.append(springFish.getFishType().getName()).append(springFish.getQuantity()).append("\n");
                    case Summer:
                        Fish summerFish = new Fish(FishType.Sunfish, fishQuantity);
                        fishes.add(summerFish);
                        result.append(summerFish.getFishType().getName()).append(summerFish.getQuantity()).append("\n");
                    case Fall:
                        Fish fallFish = new Fish(FishType.Shad, fishQuantity);
                        fishes.add(fallFish);
                        result.append(fallFish.getFishType().getName()).append(fallFish.getQuantity()).append("\n");
                    case Winter:
                        Fish winterFish = new Fish(FishType.Tuna, fishQuantity);
                        fishes.add(winterFish);
                        result.append(winterFish.getFishType().getName()).append(winterFish.getQuantity()).append("\n");
                    default:
                        break;
                }

            }
            else if (rand <= 0.8 || (rand > 0.95 && currentPlayer.getLevelFishing() != 4)) {

                switch (currentDate.getSeason()) {
                    case Spring:
                        Fish springFish = new Fish(FishType.Ghostfish, fishQuantity);
                        fishes.add(springFish);
                        result.append(springFish.getFishType().getName()).append(springFish.getQuantity()).append("\n");
                    case Summer:
                        Fish summerFish = new Fish(FishType.Rainbow_Trout, fishQuantity);
                        fishes.add(summerFish);
                        result.append(summerFish.getFishType().getName()).append(summerFish.getQuantity()).append("\n");
                    case Fall:
                        Fish fallFish = new Fish(FishType.Blue_Discus, fishQuantity);
                        fishes.add(fallFish);
                        result.append(fallFish.getFishType().getName()).append(fallFish.getQuantity()).append("\n");
                    case Winter:
                        Fish winterFish = new Fish(FishType.Perch, fishQuantity);
                        fishes.add(winterFish);
                        result.append(winterFish.getFishType().getName()).append(winterFish.getQuantity()).append("\n");
                    default:
                        break;
                }

            }
            else {
                if (currentPlayer.getLevelFishing() == 4){

                    switch (currentDate.getSeason()){
                        case Spring:
                            Fish springFish= new Fish(FishType.Legend,fishQuantity);
                            fishes.add(springFish);
                            result.append(springFish.getFishType().getName()).append(springFish.getQuantity()).append("\n");
                        case Summer:
                            Fish summerFish= new Fish(FishType.Dorado,fishQuantity);
                            fishes.add(summerFish);
                            result.append(summerFish.getFishType().getName()).append(summerFish.getQuantity()).append("\n");
                        case Fall:
                            Fish fallFish= new Fish(FishType.Squid,fishQuantity);
                            fishes.add(fallFish);
                            result.append(fallFish.getFishType().getName()).append(fallFish.getQuantity()).append("\n");
                        case Winter:
                            Fish winterFish= new Fish(FishType.Tuna,fishQuantity);
                            fishes.add(winterFish);
                            result.append(winterFish.getFishType().getName()).append(winterFish.getQuantity()).append("\n");
                    }

                }
            }
        }

        boolean top=currentPlayer.getLevelFishing() == 4;
        currentPlayer.increaseHealth(-Math.min ( ((FishingPole) currentPlayer.currentTool).type.costEnergy(top) , currentPlayer.getHealth()) ) ;
        currentPlayer.increaseFishingAbility(5);
        for (Fish fish : fishes) {
            if (currentPlayer.getBackPack().getType().getRemindCapacity() !=0) {
                inventory.Items.put(fish , 1);
            }
        }

        return new Result(true, result.toString());
    }


    public Result Fishing(String fishingPoleType) {
        if (!(currentPlayer.currentTool instanceof FishingPole)) {
            return new Result(false, "your current tool is not a FishingPole!");
        }

        if (!checkCoordinateForFishing()) {
            boolean top=currentPlayer.getLevelFishing() == 4;
            currentPlayer.increaseHealth(-Math.min ( ((FishingPole) currentPlayer.currentTool).type.costEnergy(top) , currentPlayer.getHealth()) ) ;
            return new Result(false, "you can't fishing because lake is not around you");
        }
        if (isFishingPoleTypeExist(fishingPoleType) == null) {
            return new Result(false, "No such fishing pole exist!");
        }

        return addFishToInventory(isFishingPoleTypeExist(fishingPoleType));
    }

    private boolean checkTilesForCreateBarnOrCage(int x, int y, int width, int height) {
        if (x<60 * currentPlayer.topLeftX || y< 60 * currentPlayer.topLeftY) {
            return false;
        }
        if (x + width > 30 + 60 * currentPlayer.topLeftX || y + height > 30 + 60 * currentPlayer.topLeftY) {
            return false;
        }
        for (int i = x; i < x+width; i++) {
            for (int j = y; j < y+height; j++) {
                Tile tile = getTileByCoordinates(i + 60 * currentPlayer.topLeftX, j + 60 * currentPlayer.topLeftY);
                if (!(tile.getGameObject() instanceof Walkable)) {
                    return false;
                }
            }
        }
        return true;
    }

    public Result createBarnOrCage(int topLeftX, int topLeftY, BarnORCageType barnORCageType) {
        Inventory inventory = currentPlayer.getBackPack().inventory;
        Marketing marketing = new Marketing();

        if (marketing.findEnteredShopType() != MarketType.MarnieRanch) {
            return new Result(false , "you can't create a barn or cage because you are not in Marnie's Ranch Market");
        }

        if (!checkTilesForCreateBarnOrCage(topLeftX, topLeftY, barnORCageType.getWidth(), barnORCageType.getHeight())) {
            return new Result(false, "you can't create barn or cage on this coordinate!");
        }

        int Wood = 0;
        int Stone= 0;
        for (Map.Entry <Items , Integer> entry:inventory.Items.entrySet()) {
            if (entry.getKey() instanceof Wood) {
                Wood=entry.getValue();
            }
            if (entry.getKey() instanceof BasicRock) {
                Stone=entry.getValue();
            }
        }

        if (barnORCageType.getWoodNeeded() > Wood) {
            return new Result(false , "you can't create barn or cage because you don't have enough wood!");
        }
        if (barnORCageType.getStoneNeeded() > Stone) {
            return new Result(false , "you can't create barn or cage because you don't have enough stone!");
        }
        if (barnORCageType.getPrice() > currentPlayer.getMoney() ) {
            return new Result(false , "you can't create barn or cage because you don't have enough money!");
        }

        BarnOrCage barnOrCage = new BarnOrCage(barnORCageType, topLeftX, topLeftY);

        for (Map.Entry <Items , Integer> entry:inventory.Items.entrySet()) {
            if (entry.getKey() instanceof Wood) {
                entry.setValue(entry.getValue() - Wood);
            }
            if (entry.getKey() instanceof BasicRock) {
                entry.setValue(entry.getValue() - Stone);
            }
        }


        currentPlayer.increaseMoney(- barnORCageType.getPrice());

        if (barnORCageType.equals(BarnORCageType.Barn) || barnORCageType.equals(BarnORCageType.BigBarn)
                 || barnORCageType.equals(BarnORCageType.DeluxeBarn)) {
            barnOrCage.setCharactor('b');
        }
        else {
            barnOrCage.setCharactor('c');
        }

        for (int i = topLeftX; i < topLeftX + barnORCageType.getWidth(); i++) {
            for (int j = topLeftY; j < topLeftY + barnORCageType.getHeight(); j++) {

                if (i == topLeftX || i == topLeftX + barnORCageType.getWidth() -1 || j == topLeftY || j == topLeftY + barnORCageType.getHeight() -1) {
                    Tile tile = getTileByCoordinates(i , j );
                    tile.setGameObject(barnOrCage);
                }
            }
        }

        return new Result(true, barnORCageType.getName() + "created successfully!");

    }



    public Result createWell(int topLeftX , int topLeftY) {
        Inventory inventory = currentPlayer.getBackPack().inventory;
        Marketing marketing = new Marketing();

        if (marketing.findEnteredShopType() != MarketType.MarnieRanch) {
            return new Result(false , "you can't create a Well because you are not in Marnie's Ranch Market");
        }

        if (!checkTilesForCreateBarnOrCage(topLeftX, topLeftY, Well.getWidth(), Well.getHeight())) {
            return new Result(false, "you can't create a Well on this coordinate!");
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

        if (Well.getNeededCoin() > currentPlayer.getMoney() ) {
            return new Result(false , "you can't create well because you don't have enough money!");
        }

        for (Map.Entry <Items , Integer> entry:inventory.Items.entrySet()) {
            if (entry.getKey() instanceof BasicRock) {
                entry.setValue(entry.getValue() - Stone);
                break;
            }
        }
        currentPlayer.increaseMoney(- Well.getNeededCoin());

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
        return new Result(true, "Well Created Successfully");

    }


    public Result createShippingBin(int topLeftX , int topLeftY) {
        Inventory inventory = currentPlayer.getBackPack().inventory;
        Marketing marketing = new Marketing();

        if (marketing.findEnteredShopType() != MarketType.MarnieRanch) {
            return new Result(false , "you can't create a Shipping Bin because you are not in Marnie's Ranch Market");
        }

        if (!checkTilesForCreateBarnOrCage(topLeftX, topLeftY, ShippingBin.getWidth(), ShippingBin.getHeight())) {
            return new Result(false, "you can't create a Shipping Bin on this coordinate!");
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

        if (ShippingBin.getCoinNeeded() > currentPlayer.getMoney() ) {
            return new Result(false , "you can't create Shipping Bin because you don't have enough money!");
        }

        for (Map.Entry <Items , Integer> entry:inventory.Items.entrySet()) {
            if (entry.getKey() instanceof Wood) {
                entry.setValue(entry.getValue() - Wood);
            }
        }
        currentPlayer.increaseMoney(- ShippingBin.getCoinNeeded());

        ShippingBin shippingBin = new ShippingBin(topLeftX, topLeftY);
        shippingBin.setCharactor('s');

        for (int i = topLeftX; i < topLeftX + ShippingBin.getWidth(); i++) {
            for (int j = topLeftY; j < topLeftY + ShippingBin.getHeight(); j++) {

                if (i == topLeftX || i == topLeftX + ShippingBin.getWidth() -1 || j == topLeftY || j == topLeftY + ShippingBin.getHeight() -1) {
                    Tile tile = getTileByCoordinates(i , j );
                    tile.setGameObject(shippingBin);
                }
            }
        }
        currentPlayer.getFarm().shippingBins.add(shippingBin);
        return new Result(true, "Shipping Bin Created Successfully");
    }




    public Animal getAnimalByName(String animalName) {
        for (BarnOrCage barnOrCage:currentPlayer.BarnOrCages){
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
            Tile tile = getTileByCoordinates(currentPlayer.getPositionX() + x[i], currentPlayer.getPositionY() + y[i]);
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
        for (BarnOrCage barnOrCage : currentPlayer.BarnOrCages) {
            for (Animal animal : barnOrCage.animals){
                result.append(animal.getName()).append(" Friendship: ").append(animal.getFriendShip()).append(" petToday: ")
                        .append(animal.isPetToday()).append("feedToday: ").append(animal.isFeedToday()).append("\n");
            }
        }
        return new Result(true, result.toString());
    }

    public Result shepherdAnimals(int goalX, int goalY, String name) {

        if (checkShepherdAnimals(goalX , goalY , name) != null) {
            return checkShepherdAnimals(goalX , goalY , name);
        }

        Animal animal = getAnimalByName(name);
        Walkable walkable=new Walkable();

        int [] x={1,1,1,0,0,-1,-1,-1};
        int [] y={1,0,-1,1,-1,-1,0,1};
        Queue<Tile> queue = new LinkedList<>();
        ArrayList<Tile> tiles = new ArrayList<>();
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
                return new Result(true, name + "shepherd successfully!");
            }

            for (int i = 0; i < 8; i++) {
                if (! checkTileForAnimalWalking(tile.getX() + x[i] , tile.getY() + y[i] ) ) {
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

    private Result checkShepherdAnimals(int goalX, int goalY, String name) {
        if (goalX < 0 || goalX >90 || goalY < 0 || goalY >90) {
            return new Result(false , "you can't shepherd animals out of bounds!");
        }
        Tile tile = getTileByCoordinates(goalX , goalY );
        if (!(tile.getGameObject() instanceof Walkable)) {
            return new Result(false , "yot can't shepherd animals on this coordinate!");
        }
        if (currentWeather.equals(Weather.Snowy) || currentWeather.equals(Weather.Rainy) || currentWeather.equals(Weather.Stormy) ) {
            return new Result(false , "The weather conditions isn't suitable");
        }

        Animal animal=null;

        for (BarnOrCage barnOrCage : currentPlayer.BarnOrCages) {
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
        Tile tile = getTileByCoordinates(x + 60 * currentPlayer.topLeftX, y + 60 * currentPlayer.topLeftY);
        if (tile == null) {
            return false;
        }
        if (!(tile.getGameObject() instanceof Walkable) || ! (tile.getGameObject() instanceof BarnOrCage)) {
            return false;
        }
        return true;
    }

    public Result feedHay(String name) {
        Animal animal=getAnimalByName(name);
        if (animal==null) {
            return new Result(false , "animal not found!");
        }
        animal.setFeedToday(true);
        return new Result(true, "you fed "+name+" successfully!");
    }

    public Result cheatSetFriendship( String name , Integer amount ) {
        Animal animal=getAnimalByName(name);
        if (animal==null) {
            return new Result(false , "animal not found!");
        }
        animal.increaseFriendShip(amount - animal.getFriendShip());
        return new Result(true, "friendship cheated successfully!");
    }

    public boolean animalIsOnBarnOrCage(Animal animal) {
        BarnOrCage barnOrCage=null;
        for (BarnOrCage barnOrCage1 : currentPlayer.BarnOrCages) {
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

    public void calculateAnimalsFriendship() {// آخر روز کال میشه
        for (BarnOrCage barnOrCage : currentPlayer.BarnOrCages) {
            for (Animal animal : barnOrCage.animals) {
                if (! animal.isFeedToday()){
                    animal.increaseFriendShip(- 20);
                }
                if (! animal.isPetToday()) {
                    animal.increaseFriendShip(- 10 * (animal.getFriendShip()/200));
                }
                if ( ! animalIsOnBarnOrCage(animal)) {
                    animal.increaseFriendShip(- 20);
                }
                animal.setFeedPreviousDay(animal.isFeedToday());
                animal.setFeedToday(false);
                if (currentDate.getDate() - animal.getLastProduceDay() == animal.getType().getPeriod()) {
                    animal.setLastProduceDay(currentDate.getDate());
                }
                else if (currentDate.getDate() - animal.getLastProduceDay() == -28 + animal.getType().getPeriod()) {
                    animal.setLastProduceDay(currentDate.getDate());
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

    public void checkAnimalProduct(Animal animal) {
        if (animal.getType().equals(AnimalType.dino)) {
            animal.setProductType(AnimalProductType.dinosaurEgg);
        }
        if (animal.getType().equals(AnimalType.sheep)) {
            animal.setProductType(AnimalProductType.sheeps_Wool);
        }
        //TODO truffle فراموش نشود
        if (animal.getFriendShip() < 100 || ! checkBigProduct(animal)) {
            switch (animal.getType()) {
                case hen -> { animal.setProductType(AnimalProductType.Egg); }
                case duck -> { animal.setProductType(AnimalProductType.duckEgg); }
                case rabbit -> { animal.setProductType(AnimalProductType.rabbits_Wool);}
                case cow -> { animal.setProductType(AnimalProductType.milk);}
                case goat -> { animal.setProductType(AnimalProductType.goatMilk);}
            }
        }
        else if (animal.getFriendShip() >= 100 && checkBigProduct(animal) ) {
            switch (animal.getType()) {
                case hen -> { animal.setProductType(AnimalProductType.bigEgg) ; }
                case duck -> { animal.setProductType(AnimalProductType.duckFeather) ; }
                case rabbit -> { animal.setProductType(AnimalProductType.rabbits_Foot) ;}
                case cow -> { animal.setProductType(AnimalProductType.bigMilk);}
                case goat -> { animal.setProductType(AnimalProductType.bigGoatMilk);}
            }
        }

    }

    public boolean checkPeriod(Animal animal) {
        return currentDate.getDate() - animal.getLastProduceDay() == animal.getType().getPeriod() || currentDate.getDate() - animal.getLastProduceDay() == -28 + animal.getType().getPeriod();
    }

    public Result getProductAnimals(String name) {
        Animal animal=getAnimalByName(name);

        if (! animal.isFeedPreviousDay()){
            return new Result(false , "No Product because you didn't feed " + animal.getName() + " in previous day");
        }
        if (! checkPeriod(animal)) {
            return new Result(false , "It's not time yet. This animal isn't ready to produce again");
        }
        if ( ! isNeighbor(currentPlayer.getPositionX() , currentPlayer.getPositionY() , animal.getPositionX() , animal.getPositionY())) {
            return new Result(false , "The animal is not in Neighbor Tile");
        }


        double Quantity=((double) animal.getFriendShip() / 1000) * (0.5 * (1 + animal.getRandomProduction()));
        Quantity quantity=productQuantity(Quantity);

        Animalproduct animalproduct = new Animalproduct(animal.getProductType(), quantity);
        Inventory inventory = currentPlayer.getBackPack().inventory;
        inventory.Items.put(animalproduct , 1);
        animal.setProductCollected(true);

        return new Result(true , "product "+ animal.getProductType().getName() + "collected successfully");
    }

    public Result produces() {
        StringBuilder result=new StringBuilder();
        result.append("Produces :\n");
        for (BarnOrCage barnOrCage : currentPlayer.BarnOrCages) {
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

    public Result removeAnimal(Animal animal) {
        for (BarnOrCage barnOrCage : currentPlayer.BarnOrCages) {
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
        //TODO وقتی مغازه زده میشه باید اینجا رو بزنیم. فعلا قیمت اولیه حیوان را نداریم
        if (animal == null) {
            return new Result(false , "Animal not found");
        }
        currentPlayer.increaseMoney(animal.getType().getPrice());
        return removeAnimal(animal);

    }

    private Result placeBomb(Tile tile , String name , Items items) {
        if (currentPlayer.getFarm().isInHome(currentPlayer.getPositionX(), currentPlayer.getPositionY())) {
            return new Result(false , "you can't place Bomb in your Home because it is dangerous");
        }

        Inventory inventory = currentPlayer.getBackPack().inventory;
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
                    ((Tree) target.getGameObject()).delete();
                }
                else if (target.getGameObject() instanceof ForagingCrops) {
                    ((ForagingCrops) target.getGameObject()).delete();
                }
                else if (target.getGameObject() instanceof GiantProduct) {
                    ((GiantProduct) target.getGameObject()).delete();
                }
                else if (target.getGameObject() instanceof ForagingSeeds) {
                    ((ForagingSeeds) target.getGameObject()).delete();
                }
            }
        }
        inventory.Items.compute(items , (k,v) -> v-1);
        inventory.Items.entrySet().removeIf(entry -> entry.getValue() == null || entry.getValue() ==0);

        return new Result(true , "Bomb successfully replaced and everything destroyed");
    }


    private Result placeScarecrow(Tile tile , String name, Items items) {

        Inventory inventory = currentPlayer.getBackPack().inventory;
        if (currentPlayer.getFarm().isInHome(currentPlayer.getPositionX(), currentPlayer.getPositionY())) {
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
        Inventory inventory = currentPlayer.getBackPack().inventory;

        if (items instanceof CraftingItem) {
            if (((CraftingItem) items).getCraftType().equals(CraftType.BeeHouse)) {

                if (currentPlayer.getFarm().isInHome(tile.getX(), tile.getY())) {
                    return new Result(false , "you should place Bee House in Farm!")
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
    }



    public Result placeItem(String name, int direction) {
        Inventory inventory = currentPlayer.getBackPack().inventory;
        Tile tile=getTileByDir(direction);
        if (tile == null) {
            return new Result(false , "you can't place Item on this Tile");
        }

        Farm farm=null;
        for (Farm farms : farms) {
            if (farms.Farm.contains(tile)) {
                farm = farms;
            }
        }

        for (User user: players) {
            if (user.getFarm().equals(farm)) {
                if (!user.equals(currentPlayer) && ! user.getSpouse().equals(currentPlayer) ) {
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
            case "Cherry Bomb", "Bomb", "Mega Bomb" -> {
                return placeBomb(tile, name, items);
            }
            case "Quality Sprinkler", "Sprinkler", "Iridium Sprinkler" -> {
                //TODO تابعی که عرفان زده کال میشود
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

        for (int x = currentPlayer.getPositionX(); x <currentPlayer.getPositionX()+ dirx.length; x++) {
            for (int y=currentPlayer.getPositionY() ; y< currentPlayer.getPositionY()+ diry.length; y++) {
                Tile tile=getTileByCoordinates(x,y);
                if (tile == null) {
                    continue;
                }
                if (tile.getGameObject() instanceof CraftingItem) {
                    if (((CraftingItem) tile.getGameObject()).getCraftType().getName().equals(name)) {
                        return (CraftingItem) tile.getGameObject();
                    }
                }
            }
        }
        return null;
    }



    public Result ArtisanUse(String artisanName , String first , String second) {
        Inventory inventory = currentPlayer.getBackPack().inventory;
        String newArtistName=artisanName.replace('_',' ');
        CraftingItem craftingItem=isNeighborWithCrafting(newArtistName.trim());

        if (craftingItem==null) {
            return new Result(false , "you can't use this Crafting because you are not close to it");
        }


        if (second !=null) {
            second=second.trim();
        }
        CraftType craftType=null;

        for (Map.Entry <Items , Integer> entry : inventory.Items.entrySet()) {
            if (entry.getKey() instanceof CraftingItem) {
                if (((CraftingItem) entry.getKey()).getCraftType().getName().equals(newArtistName)) {
                    craftType=((CraftingItem) entry.getKey()).getCraftType();
                }
            }
        }
        if (craftType==null) {
            return new Result(false , "you don't have Machine for craft this Item");
        }
        for (ArtisanType artisanType : ArtisanType.values()) {
            if (artisanType.getCraftType().equals(craftType)) {

                if (artisanType.checkIngredient(first.trim(), second)) {
                    artisanType.creatArtesian(first.trim(), craftingItem);
                    return new Result(true , "you use "+newArtistName+" successfully");
                }
            }
        }
        return new Result(false , "Not enough ingredient for use");

    }


    private void addArtisanToInventory(Items item) {
        Inventory inventory = currentPlayer.getBackPack().inventory;
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

        Inventory inventory= currentPlayer.getBackPack().inventory;
        if (currentPlayer.getBackPack().getType().getRemindCapacity() ==0) {
            return new Result(false , "you can't get product because your backpack is full");
        }
        Items items=null;

        for (int x =currentPlayer.getPositionX() ; x< currentPlayer.getPositionX()+ dirx.length; x++) {

            for (int y=currentPlayer.getPositionY() ; y<currentPlayer.getPositionY()+ diry.length; y++) {
                Tile tile=getTileByCoordinates(x,y);
                if (tile == null) {
                    continue;
                }

                if (tile.getGameObject() instanceof CraftingItem) {
                    HashMap<Items ,HashMap<DateHour , Integer>> temp=((CraftingItem) tile.getGameObject()).getBuffer();

                    for (Map.Entry<Items , HashMap<DateHour , Integer>> entry : temp.entrySet()) {

                        if (entry.getKey() instanceof ArtisanProduct) {
                            if (((ArtisanProduct) entry.getKey()).getType().getName().equals(name)) {
                                for (DateHour dateHour : entry.getValue().keySet()) {
                                    if (DateHour.getHourDiffrent(dateHour) >= entry.getValue().get(dateHour)) {
                                        items=entry.getKey();
                                        temp.remove(items);
                                        break;
                                    }
                                    else {
                                        return new Result(false , "you should wait");
                                    }
                                }
                            }
                        }

                        if (entry.getKey() instanceof MarketItem) {
                            if (((MarketItem) entry.getKey()).getType().getName().equals(name)) {
                                for (DateHour dateHour : entry.getValue().keySet()) {
                                    if (DateHour.getHourDiffrent(dateHour) >= entry.getValue().get(dateHour)) {
                                        items = entry.getKey();
                                        temp.remove(items);
                                        break;
                                    }
                                    else {
                                        return new Result(false , "you should wait");
                                    }
                                }
                            }
                        }

                        if (entry.getKey() instanceof ForagingMinerals) {
                            if (((ForagingMinerals) entry.getKey()).getType().getDisplayName().equals(name)) {
                                for (DateHour dateHour : entry.getValue().keySet()) {
                                    if (DateHour.getHourDiffrent(dateHour) >= entry.getValue().get(dateHour)) {
                                        items = entry.getKey();
                                        temp.remove(items);
                                        break;
                                    }
                                    else {
                                        return new Result(false , "you should wait");
                                    }
                                }
                            }
                        }

                        if (entry.getKey() instanceof BarsAndOres) {
                            if (((BarsAndOres) entry.getKey()).getType().getName().equals(name)) {
                                for (DateHour dateHour : entry.getValue().keySet()) {
                                    if (DateHour.getHourDiffrent(dateHour) >= entry.getValue().get(dateHour)) {
                                        items = entry.getKey();
                                        temp.remove(items);
                                        break;
                                    }
                                    else {
                                        return new Result(false , "you should wait");
                                    }
                                }
                            }
                        }

                        if (items !=null) {
                            break;
                        }
                    }
                }

                if (items != null) {
                    addArtisanToInventory(items);
                    return new Result(true , name + "successfully added to your inventory");
                }
            }
        }
        return new Result(false , name + " not found");
    }


    private Result sellFish(ArrayList<Fish> fishes , Integer amount,ShippingBin shippingBin) {
        Inventory inventory= currentPlayer.getBackPack().inventory;
        if (amount == -1) {
            amount = fishes.size();
        }
        int cursor=0;
        for (Fish fish : fishes ) {
            if (cursor == amount) {
                return new Result(false , "Products Successfully added to Shipping Bin") ;
            }
            shippingBin.binContents.add(fish);
            inventory.Items.remove(fish);
            cursor++;
        }
        return null;
    }

    private Result sellAnimalProduct(ArrayList<Animalproduct> animalproducts , Integer amount,ShippingBin shippingBin) {
        Inventory inventory= currentPlayer.getBackPack().inventory;
        if (amount == -1) {
            amount = animalproducts.size();
        }
        int cursor=0;
        for (Animalproduct animalproduct : animalproducts ) {
            if (cursor == amount) {
                return new Result(false , "Products Successfully added to Shipping Bin") ;
            }
            shippingBin.binContents.add(animalproduct);
            inventory.Items.remove(animalproduct);

            cursor++;
        }
        return null;
    }



    public Result sell(String name , Integer amount) {
        ShippingBin shippingBin=ShippingBin.isNearShippingBin();
        if (shippingBin == null ) {
            return new Result(false , "you are not near shipping bin");
        }

        Inventory inventory=currentPlayer.getBackPack().inventory;
        ArrayList<Fish> fishes=new ArrayList<>();
        ArrayList<Animalproduct> animalproducts=new ArrayList<>();

        for (Map.Entry <Items,Integer> entry : inventory.Items.entrySet() ) {
            if (entry.getKey() instanceof Fish) {
                if (((Fish) entry.getKey()).getFishType().getName().equals(name)) {
                    fishes.add((Fish) entry.getKey());
                }
            }
            if (entry.getKey() instanceof Animalproduct) {
                if (((Animalproduct) entry.getKey()).getAnimalProductType().getName().equals(name)) {
                    animalproducts.add((Animalproduct) entry.getKey());
                }
            }
        }
        if (fishes.isEmpty() && animalproducts.isEmpty()) {
            return new Result(false , name + " not found!");
        }
        if (!fishes.isEmpty()) {
           return sellFish(fishes , amount, shippingBin);
        }
        else {
           return sellAnimalProduct(animalproducts , amount, shippingBin);
        }
    }


    public void unloadAndReward() {
        for (User user : players) {
            Farm farm = user.getFarm();
            for (ShippingBin shippingBin : farm.shippingBins) {
                for (Items items : shippingBin.binContents) {
                    if (items instanceof Fish) {
                        user.increaseMoney((int) (((Fish) items).getFishType().getPrice() * ((Fish) items).getQuantity().getValue()));
                    }
                    if (items instanceof Animalproduct) {
                        user.increaseMoney((int) (((Animalproduct) items).getAnimalProductType().getInitialPrice() * ((Animalproduct) items).getQuantity().getValue()));
                    }
                }
            }
        }
    }

    public void startNewGame (String input) {

        String user1name = GameMenuCommands.makeNewGame.getMather(input).group("username1");
        String user2name = GameMenuCommands.makeNewGame.getMather(input).group("username2"); // could be null
        String user3name = GameMenuCommands.makeNewGame.getMather(input).group("username3"); // could be null
        if (findUserByUsername(user1name) == null){
            System.out.println("User Not Found!");
            return;
        }
        if (user2name != null) {
            if (findUserByUsername(user2name) == null) {
                System.out.println("User Not Found!");
                return;
            }
        }
        if (user3name != null) {
            if (findUserByUsername(user3name) == null) {
                System.out.println("User Not Found!");
                return;
            }
        }

        if (findUserByUsername(user1name).isCurrently_in_game()){
            System.out.println("User Currently in Game!");
            return;
        }

        if (user2name != null) {
            if (findUserByUsername(user2name).isCurrently_in_game()) {
                System.out.println("User Not Found!");
                return;
            }
        }
        if (user3name != null) {
            if (findUserByUsername(user3name).isCurrently_in_game()) {
                System.out.println("User Not Found!");
                return;
            }
        }
        players.add(currentUser);
        currentPlayer = currentUser;
        players.add(findUserByUsername(user1name));
        if (user2name != null) players.add(findUserByUsername(user2name));
        if (user3name != null) players.add(findUserByUsername(user3name));

        Scanner scanner = new Scanner(System.in);

        for (User user: players) {
            currentPlayer = user;
            while (true) {
                System.out.println(currentPlayer.getUsername() + "'s turn to choose map(1 or 2)");
                String choiceString = scanner.nextLine();
                String[] splitChoice = choiceString.trim().split("\\s+");
                int choice = Integer.parseInt(splitChoice[2]);
                if (choice != 1 && choice != 2) {
                    System.out.println("Choose between 1 and 2!");
                    continue;
                }
                createInitialFarm(choice);
                break;
            }
        }

        setTimeAndWeather();
        currentPlayer = currentUser;

        // Form Friendships
        for (int i = 0; i < players.size(); i++) {
            for (int j = i + 1; j < players.size(); j++) {
                HumanCommunications f = new HumanCommunications(players.get(i), players.get(j));
                friendships.add(f);
            }
        }

//        friendships.get(0).addXp(150);  // این باعث میشه لول بره بالا
//        friendships.get(0).printInfo();

        initializePlayer();
    }
    public void nextTurn () {
        User old = currentPlayer;
        boolean done = false;
        boolean temp = false;
        int wrongAttempts = 0;
        while (wrongAttempts <= 5) {
            for (User user : players) {
                if (temp) {
                    currentPlayer = user;
                    System.out.println(currentPlayer.getNickname() + "'s turn.");

                    // Display Unseen Messages...
                    System.out.println("Displaying Unseen Messages...");
                    for (List<MessageHandling> messages : conversations.values()) {
                        for (MessageHandling m : messages) {
                            if (m.getReceiver().getUsername().equals(currentPlayer.getUsername()) && !m.isSeen()) {
                                m.print();
                                m.setSeen(true);

                                //بخش ریت کردن هدیه ها
                                if (m.getText().endsWith("Rate it out of 5!")) {
                                    HumanCommunications f = getFriendship(currentPlayer, m.getSender());
                                    do {
                                        assert f != null;
                                    } while (!f.rateGifts().IsSuccess());
                                }
                            }
                        }
                    }
                    System.out.println(GREEN+"Unseen Messages Displayed."+RESET);

                    System.out.println("Displaying Trade Requests/Offers...");
                    for (List<Trade> tradeList: trades.values()) {
                        for (Trade t: tradeList) {
                            if (t.getReceiver().getUsername().equals(currentPlayer.getUsername()) && !t.isResponded()) {
                                t.print();

                                System.out.println("What's Your Response?");
                                Scanner scanner = new Scanner(System.in);
                                String respond;
                                Result result;
                                do {
                                    respond = scanner.nextLine();
                                    result = Trade.CheckTradeRespond(respond, t.getId());
                                    System.out.println(result.massage());
                                } while (!result.IsSuccess());
                                t.setResponded(true);
                            }
                        }
                    }
                    System.out.println(GREEN+"Trades Done!"+RESET);

                    // proposals
                    for (List<MessageHandling> messages : conversations.values()) {
                        for (MessageHandling m : messages) {
                            if (m.getReceiver().getUsername().equals(currentPlayer.getUsername()) && !m.isSeen() && m.getText().endsWith("Do You Accept to be his Wife?")) {
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
        String targetName = currentPlayer.getUsername();

        for (HumanCommunications f : friendships) {
            if (f.getPlayer1().getUsername().equals(targetName) || f.getPlayer2().getUsername().equals(targetName))
                f.printInfo();
        }
    }
    public void talking (String input) {
        String destinationUsername = GameMenuCommands.talking.getMather(input).group("username");
        String message = GameMenuCommands.talking.getMather(input).group("message");
        if (!players.contains(findUserByUsername(destinationUsername))) {
            System.out.println(RED+"Username is Unavailable!"+RESET);
            return;
        }
        if (destinationUsername.equals(currentPlayer.getUsername())) {
            System.out.println("You can't Talk to " + RED+"Yourself"+RESET + "!");
            return;
        }
        HumanCommunications f = getFriendship(currentPlayer, findUserByUsername(destinationUsername));
        if (f == null) {
            System.out.println("There's " + RED+"no Friendship"+RESET + " Among these Users");
            return;
        }
        Result result = f.talk(message);
        System.out.println(result);
    }
    public void DisplayingTalkHistory (String input) {
        String username = GameMenuCommands.talkHistory.getMather(input).group("username");
        if (!players.contains(findUserByUsername(username))) {
            System.out.println(RED+"Username is Unavailable!"+RESET);
            return;
        }
        if (username.equals(currentPlayer.getUsername())) {
            System.out.println("You can't Talk to " + RED+"Yourself"+RESET + "!");
            return;
        }
        HumanCommunications f = getFriendship(currentPlayer, findUserByUsername(username));
        if (f == null) {
            System.out.println("There's " + RED+"no Friendship"+RESET + " Among these Users");
            return;
        }
        Result result = f.talkingHistory();
        System.out.println(result);
    }
    public void hug (String input) {
        String username = GameMenuCommands.hug.getMather(input).group("username");
        if (!players.contains(findUserByUsername(username))) {
            System.out.println(RED+"Username is Unavailable!"+RESET);
            return;
        }
        if (username.equals(currentPlayer.getUsername())) {
            System.out.println("You can't Hug " + RED+"Yourself"+RESET + "!");
            return;
        }
        HumanCommunications f = getFriendship(currentPlayer, findUserByUsername(username));
        if (f == null) {
            System.out.println("There's " + RED+"no Friendship"+RESET + " Among these Users");
            return;
        }
        Result result = f.Hug();
        System.out.println(result);
    }

    public void sendGifts (String input) {
        String username = GameMenuCommands.sendGift.getMather(input).group("username");
        String item = GameMenuCommands.sendGift.getMather(input).group("item");
        int amount = Integer.parseInt(GameMenuCommands.sendGift.getMather(input).group("amount"));
        if (username == null || item == null) {
            System.out.println("Invalid Command!");
            return;
        }
        if (!players.contains(findUserByUsername(username))) {
            System.out.println(RED+"Username is Unavailable!"+RESET);
            return;
        }
        if (username.equals(currentPlayer.getUsername())) {
            System.out.println("You can't Send Gifts to " + RED+"Yourself"+RESET + "!");
            return;
        }
        HumanCommunications f = getFriendship(currentPlayer, findUserByUsername(username));
        if (f == null) {
            System.out.println("There's " + RED+"no Friendship"+RESET + " Among these Users");
            return;
        }


        Result result = f.sendGifts(username, item, amount);
        System.out.println(result);
        if (result.IsSuccess())
            new MessageHandling(currentPlayer, findUserByUsername(username), currentPlayer.getNickname() + " Sent you a GIFT. Rate it out of 5!");
    }

    public void giveFlowers (String input) {
        String username = GameMenuCommands.giveFlower.getMather(input).group("username");
        if (!players.contains(findUserByUsername(username))) {
            System.out.println(RED+"Username is Unavailable!"+RESET);
            return;
        }
        if (username.equals(currentPlayer.getUsername())) {
            System.out.println("You can't give Flower to " + RED+"Yourself"+RESET + "!");
            return;
        }
        HumanCommunications f = getFriendship(currentPlayer, findUserByUsername(username));
        if (f == null) {
            System.out.println("There's " + RED+"no Friendship"+RESET + " Among these Users");
            return;
        }
        Result result = f.buyFlowers();
        System.out.println(result);
    }
    public void propose(String input) {
        String username = GameMenuCommands.propose.getMather(input).group("username");
        User wife = findUserByUsername(username);
        if (!players.contains(wife)) {
            System.out.println(RED+"Username is Unavailable!"+RESET);
            return;
        }
        if (username.equals(currentPlayer.getUsername())) {
            System.out.println("You can't Propose to " + RED+"Yourself"+RESET + "!");
            return;
        }
        HumanCommunications f = getFriendship(currentPlayer, wife);
        if (f == null) {
            System.out.println("There's " + RED+"no Friendship"+RESET + " Among these Users");
            return;
        }

        String ring = GameMenuCommands.propose.getMather(input).group("ring");
        if (ring.equalsIgnoreCase("ring") || ring.equalsIgnoreCase("wedding ring") || ring.equalsIgnoreCase("wedding")) {
            System.out.println(RED+"Wrong Ring Name!"+RESET);
        }

        Result result = f.propose();
        System.out.println(result);

    }
    public void exitGame () {
        if (currentPlayer != currentUser) {
            System.out.println("Access Denied!");
            return;
        }

        //TODO سیو کل بازی
    }
    public void forceTerminate () {
        Scanner scanner = new Scanner(System.in);
        User terminator = currentPlayer;
        for (User user: players) {
            if (user == terminator)
                continue;
            currentPlayer = user;
            System.out.println(currentPlayer.getNickname() + ", Do You Agree With the Game Termination?[Y/N]");
            String choice = scanner.next();
            if (!choice.trim().toLowerCase().equals("y")) {
                System.out.println("Vote Failed! The Game won't be Terminated.");
                currentPlayer = terminator;
                return;
            }
        }

        //TODO  کارهای ترمینیت کردن مثل پاک کردن فایل های سیو و ریست کردن همه دیتاهای بازیکنا بجز ماکسیمم امتیاز
        for (User user: players) {
            user.setCurrently_in_game(false);
            user.setMax_point(user.getPoint());
        }
    }

// قسمت آقاتون اینجا تموم میشه




    private void initializePlayer () {

        for (User user : players) {
            user.setFriendshipPoint(new HashMap<>(Map.of(
                    NPC.Sebastian, 0,
                    NPC.Lia, 0,
                    NPC.Abigail, 0,
                    NPC.Harvey, 0,
                    NPC.Robin, 0)));

           for (NPC npc : NPC.values()) {
               user.setTodayTalking(npc, false);
               user.setTodayGifting(npc, false);
           }
           advanceItem(new Scythe(), 1);
           advanceItem(new Hoe(HoeType.primaryHoe), 1);
           advanceItem(new Axe(AxeType.primaryAxe), 1);
           advanceItem(new PickAxe(PickAxeType.primaryPickAxe), 1);
           advanceItem(new WateringCan(WateringCanType.PrimaryWateringCan), 1);
        }
    }

    public void passedOfTime (int day, int hour) {

        currentDate.increaseHour(hour);
        currentDate.increaseDay(day);
        // کارایی که اینجا زدی رو حواست باشه تو استارت دی هم نباشه
        //  محصولا رشد کنن     استجشون بررسی بشه ( تو خود کلاسشون میشه زد )
        // تغییر ایکون باید بدن

    } // TODO   باید کارایی که بعد افزایش زمان انجام میشن رو انجام بدی

    public void startDay () {

        doSeasonAutomaticTask();
        passedOfTime(0, (24 - currentDate.getHour()) + 9);
        setEnergyInMorning();
        createRandomForaging();
        createRandomMinerals();
        NPCAutomatTask();
        unloadAndReward();

        for (Tile tile : bigMap)
            tile.getGameObject().startDayAutomaticTask();

        doWeatherTask();
        setProtect();
        crowAttack(); // قبل محصول دادن درخت باید باشه
        // TODO بازیکنا برن خونشون , غش کردن
        // TODO محصول کاشته بشه و رشد محصولا یه روز بره بالاتر
        // TODO کانی تولید بشه شاپینگ بین خالی بشه و.  پول بیاد تو حساب فرد
    }
    public void AutomaticFunctionAfterOneTurn () {


        if (currentUser == currentPlayer)
            passedOfTime(0, 1);

        if (currentDate.getHour() > 22)
            startDay();

        for (Tile tile : bigMap)
            tile.getGameObject().turnByTurnAutomaticTask();
    }
    public void AutomaticFunctionAfterAnyAct () {

        checkForGiant();
        for (User user : players)
            user.checkHealth();

    }



                                                                // energy & Date
    private void setEnergyInMorning () {
        for (User user : players) {
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

        currentDate = new DateHour(Season.Spring, 1, 9, 1980);
        currentWeather = Weather.Sunny;

    }
    private void setAbilitiesLevel () {

    }
    private void doSeasonAutomaticTask () {

        currentWeather = tomorrowWeather;
        tomorrowWeather = currentDate.getSeason().getWeather();

    }
    private void doWeatherTask () {

        if (currentWeather.equals(Weather.Rainy) || currentWeather.equals(Weather.Stormy)) {
            for (Tile tile : bigMap) {
                GameObject object = tile.getGameObject();

                if (object instanceof Tree && !isInGreenHouse(tile))
                    ((Tree) object).setLastWater(currentDate);
                if (object instanceof GiantProduct && !isInGreenHouse(tile))
                    ((GiantProduct) object).setLastWater(currentDate);
                if (object instanceof ForagingSeeds && !isInGreenHouse(tile))
                    ((ForagingSeeds) object).setLastWater(currentDate);
            }
        }
        if (currentWeather.equals(Weather.Stormy)) {

            Random random1 = new Random();

            for (User user : players)
                if (random1.nextInt(2) == 1)
                    lightningStrike(selectTileForThor(user.getFarm()));
        }
    }


                                                                 // Automatic Plant task
    private void crowAttack () {

        for (Farm farm : farms) {

            int number = 0;
            for (Tile tile : farm.Farm) {

                if (tile.getGameObject() instanceof Tree ||
                        tile.getGameObject() instanceof ForagingSeeds ||
                        tile.getGameObject() instanceof GiantProduct)
                    number++;

                if (number % 16 == 0) {

                    double x = Math.random();
                    if (x <= 0.25) {

                        GameObject object = tile.getGameObject();
                        if (isInGreenHouse(tile)) {
                            continue;
                        }

                        else if (object instanceof Tree && !((Tree) object).isProtected())
                            ((Tree) object).setLastFruit(currentDate);

                        else if (object instanceof ForagingCrops && !((ForagingCrops) object).isProtected())
                            ((ForagingCrops) object).delete();

                        else if (object instanceof ForagingSeeds && !((ForagingSeeds) object).isProtected()) {
                            if (((ForagingSeeds) object).getType().isOneTimeUse())
                                tile.setGameObject(new Walkable());
                            else
                                ((ForagingSeeds) object).setLastProduct(currentDate);
                        }
                        else if (object instanceof GiantProduct && !((GiantProduct) object).isProtected()) {
                            ArrayList<Tile> neighbors = ((GiantProduct) object).getNeighbors();
                            ((GiantProduct) object).delete();

                        }
                    }
                }
            }
        }
    }
    private void checkForGiant () {

        for (int i = 0; i < 89 ; i++)
            for (int j = 0; j < 89 ; j++) {

                Tile tile1 = getTileByCoordinates(i, j);
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
    private boolean checkForDeath () {

        return (currentPlayer.getHealth() <= 0 && !currentPlayer.isHealthUnlimited());
    }

    private void lightningStrike (Tile selected) {

        GameObject object = selected.getGameObject();

        if (object instanceof Tree)
            selected.setGameObject(new ForagingMinerals(COAL));
        else if (object instanceof ForagingSeeds)
            selected.setGameObject(new Walkable());
        else if (object instanceof Animal)
            selected.setGameObject(new Walkable());

    }
    private Tile selectTileForThor (Farm farm) {

        List<Tile> matchingTiles = farm.Farm.stream()
                .filter(tile -> tile.getGameObject() instanceof Tree ||
                        tile.getGameObject() instanceof ForagingSeeds &&
                                !farm.isInGreenHouse(tile.getX(), tile.getY()))
                .toList();

        Random random = new Random();
        return matchingTiles.get(random.nextInt(matchingTiles.size()));
    }
    private void createRandomForaging () {

        for (Tile tile : bigMap) {
            if (tile.getGameObject() instanceof Walkable &&
                    ((Walkable) tile.getGameObject()).getGrassOrFiber().equals("Plowed") && Math.random() <= 0.01)
                if (Math.random() <= 0.5) {

                    List<ForagingSeedsType> types = Arrays.stream(ForagingSeedsType.values())
                            .filter(d -> d.getSeason().contains(currentDate.getSeason()))
                            .toList();

                    ForagingSeedsType type = types.get(rand.nextInt(types.size()));

                    tile.setGameObject(new ForagingSeeds(type, currentDate));
                }
                else {

                    List<ForagingCropsType> types = new ArrayList<>(Arrays.stream(ForagingCropsType.values())
                            .filter(d -> d.getSeason().contains(currentDate.getSeason()))
                            .toList());

                    types.remove(ForagingCropsType.Fiber);
                    ForagingCropsType type = types.get(rand.nextInt(types.size()));

                    ForagingCrops crop = new ForagingCrops(type);
                    tile.setGameObject(crop);
                }
            else if (tile.getGameObject() instanceof Walkable &&
                    ((Walkable) tile.getGameObject()).getGrassOrFiber().equals("Walk") &&
                    Math.random() <= 0.2) {

                if (Math.random() <= 0.5)
                    ((Walkable) tile.getGameObject()).setGrassOrFiber("Fiber");
                else
                    ((Walkable) tile.getGameObject()).setGrassOrFiber("Grass");
            }
        }
    }
    private void createRandomMinerals () {

        int x1 = currentPlayer.getFarm().getMine().getStartX()+1;
        int y1 = currentPlayer.getFarm().getMine().getStartY()+1;

        if (Math.random() < RUBY.getProbability())
            getTileByCoordinates(x1, y1).setGameObject(new ForagingMinerals(RUBY));
        if (Math.random() < COAL.getProbability())
            getTileByCoordinates(x1+1, y1).setGameObject(new ForagingMinerals(COAL));
        if (Math.random() < IRON.getProbability())
            getTileByCoordinates(x1+2, y1).setGameObject(new ForagingMinerals(IRON));
        if (Math.random() < TOPAZ.getProbability())
            getTileByCoordinates(x1+3, y1).setGameObject(new ForagingMinerals(TOPAZ));
        if (Math.random() < GOLD.getProbability())
            getTileByCoordinates(x1, y1+1).setGameObject(new ForagingMinerals(GOLD));
        if (Math.random() < JADE.getProbability())
            getTileByCoordinates(x1+1, y1+1).setGameObject(new ForagingMinerals(JADE));
        if (Math.random() < IRIDIUM.getProbability())
            getTileByCoordinates(x1+2, y1+1).setGameObject(new ForagingMinerals(IRIDIUM));
        if (Math.random() < QUARTZ.getProbability())
            getTileByCoordinates(x1+3, y1+1).setGameObject(new ForagingMinerals(QUARTZ));
        if (Math.random() < EMERALD.getProbability())
            getTileByCoordinates(x1, y1+2).setGameObject(new ForagingMinerals(EMERALD));
        if (Math.random() < COPPER.getProbability())
            getTileByCoordinates(x1+1, y1+2).setGameObject(new ForagingMinerals(COPPER));
        if (Math.random() < DIAMOND.getProbability())
            getTileByCoordinates(x1+2, y1+2).setGameObject(new ForagingMinerals(DIAMOND));
        if (Math.random() < AMETHYST.getProbability())
            getTileByCoordinates(x1+3, y1+2).setGameObject(new ForagingMinerals(AMETHYST));
        if (Math.random() < AQUAMARINE.getProbability())
            getTileByCoordinates(x1, y1+3).setGameObject(new ForagingMinerals(AQUAMARINE));
        if (Math.random() < FROZEN_TEAR.getProbability())
            getTileByCoordinates(x1+1, y1+3).setGameObject(new ForagingMinerals(FROZEN_TEAR));
        if (Math.random() < FIRE_QUARTZ.getProbability())
            getTileByCoordinates(x1+2, y1+3).setGameObject(new ForagingMinerals(FIRE_QUARTZ));
        if (Math.random() < PRISMATIC_SHARD.getProbability())
            getTileByCoordinates(x1+2, y1+3).setGameObject(new ForagingMinerals(PRISMATIC_SHARD));
        if (Math.random() < EARTH_CRYSTAL.getProbability())
            getTileByCoordinates(x1+3, y1+3).setGameObject(new ForagingMinerals(EARTH_CRYSTAL));


    }
    private void setProtect (int x, int y, int r) {

        for (int i = Math.min(x-(r/2), 1); i < x+r ; i++ )
            for (int j = Math.min(y-(r/2), 1); j < y+r ; j++ )
                if ((i-x)*(i-x) + (j-y)*(j-y) <= r*r) {

                    Tile tile = getTileByCoordinates(i , j);
                    GameObject object = tile.getGameObject();

                    if (object instanceof Tree)
                        ((Tree) object).setProtected(true);

                    if (object instanceof ForagingSeeds)
                        ((ForagingSeeds) object).setProtected(true);

                    if (object instanceof GiantProduct)
                        ((GiantProduct) object).setProtected(true);

                    if (object instanceof ForagingCrops)
                        ((ForagingCrops) object).setProtected(true);
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
    private void fertilizePlant (MarketItemType fertilizeType , Tile tile) {

        GameObject gameObject = tile.getGameObject();

        if (gameObject instanceof GiantProduct)
            ((GiantProduct) gameObject).setFertilize(fertilizeType);
        if (gameObject instanceof Tree)
            ((Tree) gameObject).setFertilize(fertilizeType);
        if (gameObject instanceof ForagingSeeds)
            ((ForagingSeeds) gameObject).setFertilize(fertilizeType);

    }
    private void greenHouse () {

        // check
        // TODO
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
            if (!type1.getSeason().contains(currentDate.getSeason()))
                return new Result(false, RED+"You can't plant this tree in "
                        + RESET + currentDate.getSeason());

        if (tile.getGameObject() instanceof Walkable &&
                ((Walkable) tile.getGameObject()).getGrassOrFiber().equals("Plowed")) {

            tile.setGameObject(new Tree(type1.getTreeType(), currentDate));
            advanceItem(new TreeSource(type1), 1);
            return new Result(true, BLUE+"The tree begins its journey"+RESET);
        }
        else
            return new Result(false, RED+"First, you must plow the tile"+RESET);

    }
    private Result plantMixedSeed (int dir) {

        Inventory inventory = currentPlayer.getBackPack().inventory;

        for (Map.Entry<Items,Integer> entry: inventory.Items.entrySet())

            if (entry instanceof MixedSeeds) {
                if (inventory.Items.get(entry) > 0) {

                    ForagingSeedsType type = ((MixedSeeds) entry).getSeeds(currentDate.getSeason());
                    inventory.Items.put(entry.getKey(), entry.getValue() - 1);
                    Tile tile = getTileByDir(dir);

                    if (tile.getGameObject() instanceof Walkable &&
                            ((Walkable) tile.getGameObject()).getGrassOrFiber().equals("Plowed")) {

                        tile.setGameObject(new ForagingSeeds(type, currentDate));

                    } else
                        return new Result(false, RED+"First, you must plow the tile."+RESET);

                }
                else
                    return new Result(false, RED + "You don't have Mixed seed!" + RESET);
            }
        return new Result(false, RED + "You don't have Mixed seed!" + RESET);
    }
    private Result plantForagingSeed (ForagingSeedsType type, int dir) {

        Inventory inventory=currentPlayer.getBackPack().inventory;

        for (Map.Entry<Items,Integer> entry: inventory.Items.entrySet())

            if (entry instanceof ForagingSeeds && ((ForagingSeeds) entry).getType().equals(type)) {
                if (inventory.Items.get(entry) > 0) {

                    Tile tile = getTileByDir(dir);

                    if (!isInGreenHouse(tile))
                        if (!type.getSeason().contains(currentDate.getSeason()))
                            return new Result(false, RED+"You can't plant this tree in "
                                    + RESET + currentDate.getSeason());

                    if (tile.getGameObject() instanceof Walkable &&
                            ((Walkable) tile.getGameObject()).getGrassOrFiber().equals("Plowed")) {

                        tile.setGameObject(new ForagingSeeds(type, currentDate));
                        inventory.Items.put(entry.getKey(), entry.getValue() - 1);
                        return new Result(true, BLUE+"The earth welcomes your seed"+RESET);

                    } else
                        return new Result(false, RED+"First, you must plow the tile"+RESET);
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
            ((WateringCan) currentPlayer.currentTool).makeFullWater();
            return new Result(true, BLUE+"The Watering can is now full. Time to water" +
                    " those plants!\uD83D\uDEB0"+RESET);
        }
        else if (object instanceof WaterTank) {

            int amount = ((WaterTank) object).getAmount();
            WateringCan wateringCan = (WateringCan) currentPlayer.currentTool;

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
            if (((Walkable) object).getGrassOrFiber().equals("Fibre"))
                advanceItem(new ForagingCrops(ForagingCropsType.Fiber), 1);

            ((Walkable) object).setGrassOrFiber("Walk");
        }
        if (object instanceof Tree) {

            if (((Tree) object).isHaveFruit()) {

                TreeType type = ((Tree) object).getType();

                if (currentPlayer.getBackPack().getType().getRemindCapacity() >
                        currentPlayer.getBackPack().inventory.Items.size() ||
                        checkAmountProductAvailable(new Tree(type, currentDate), 0))
                    return new Result(false, RED+"Inventory is full"+RESET);

                advanceItem(new TreesProdct(type.getProductType()), type.getHarvestYield());

                ((Tree) object).setLastFruit(currentDate);
                currentPlayer.increaseFarmingAbility(5);
                return new Result(true, BLUE + "You got " + type.getHarvestYield()
                        + type.getProductType().getDisplayName() + RESET);
            } else
                return new Result(true, RED + "This tree doesn't have fruit" + RESET);
        }
        if (object instanceof ForagingCrops) {

            ForagingCropsType type = ((ForagingCrops) object).getType();

            if (currentPlayer.getBackPack().getType().getRemindCapacity() >
                    currentPlayer.getBackPack().inventory.Items.size() ||
                    checkAmountProductAvailable(new ForagingCrops(type), 0))

                return new Result(false, RED+"Inventory is full"+RESET);

            advanceItem(new ForagingCrops(((ForagingCrops) object).getType()), 1);
            ((ForagingCrops) object).delete();
            currentPlayer.increaseFarmingAbility(5);

            return new Result(true, BLUE+"You got 1 of "+ RESET + type.getDisplayName());

        }
        if (object instanceof ForagingSeeds) {
            if (((ForagingSeeds) object).isHaveProduct()) {

                ForagingSeedsType type = ((ForagingSeeds) object).getType();
                if (currentPlayer.getBackPack().getType().getRemindCapacity() >
                        currentPlayer.getBackPack().inventory.Items.size() ||
                        checkAmountProductAvailable(new AllCrops(type.getProductType()), 0))
                    return new Result(false, RED+"Inventory is full"+RESET);

                advanceItem(new AllCrops(type.getProductType()), 1);
                ((ForagingSeeds) object).harvest();
                currentPlayer.increaseFarmingAbility(5);
                return new Result(true, BLUE + "You got 1 " + type.getProductType().getDisplayName() + RESET);
            } else
                return new Result(false, RED + "Still growing..." + RESET);
        }
        if (object instanceof GiantProduct) {
            if (((GiantProduct) object).isHaveProduct()) {

                ForagingSeedsType type = ((GiantProduct) object).getType();

                if (currentPlayer.getBackPack().getType().getRemindCapacity() >
                        currentPlayer.getBackPack().inventory.Items.size() ||
                        checkAmountProductAvailable(new AllCrops(type.getProductType()), 0))

                    return new Result(false, RED+"Inventory is full"+RESET);

                advanceItem(new AllCrops(type.getProductType()), 10);
                ((GiantProduct) object).harvest();
                currentPlayer.increaseFarmingAbility(5);
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
                    currentPlayer.getBackPack().getType().getRemindCapacity() > 0) {

                if (((Tree) object).getType().equals(TreeType.MapleTree) ||
                        ((Tree) object).getType().equals(TreeType.MysticTree))

                    if (checkAmountProductAvailable(
                            new TreesProdct(((Tree) object).getType().getProductType()), 1) ||
                            currentPlayer.getBackPack().getType().getRemindCapacity() > 1) {

                        advanceItem(new Wood(), 5);
                        advanceItem(new TreesProdct(((Tree) object).getType().getProductType()), 1);
                        return new Result(false, BRIGHT_BLUE + "+5 wood  +1 " +
                                ((Tree) object).getType().getProductType().getDisplayName()+RESET);
                    }

                advanceItem(new Wood(), 5);
                return new Result(false, BRIGHT_BLUE+"+5 wood"+RESET);
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

            if (((ForagingMinerals) object).getType().equals(COPPER)) {

                if (checkAmountProductAvailable(new BarsAndOres(BarsAndOreType.CopperOre), 1) ||
                        currentPlayer.getBackPack().getType().getRemindCapacity() > 0) {
                    advanceItem(new BarsAndOres(BarsAndOreType.CopperOre), 1);
                    return new Result(false, BRIGHT_BLUE + "+1 Cooper ore" + RESET);
                }
                else {
                    tile.setGameObject(new Walkable());
                    return new Result(false, RED+"Ops!!! you destroy Cooper ore" + RESET);
                }
            }
            if (((ForagingMinerals) object).getType().equals(GOLD)) {

                if (checkAmountProductAvailable(new BarsAndOres(BarsAndOreType.GoldOre), 1) ||
                        currentPlayer.getBackPack().getType().getRemindCapacity() > 0) {
                    advanceItem(new BarsAndOres(BarsAndOreType.GoldOre), 1);
                    return new Result(false, BRIGHT_BLUE + "+1 Gold ore" + RESET);
                }
                else {
                    tile.setGameObject(new Walkable());
                    return new Result(false, RED+"Ops!!! you destroy Gold ore" + RESET);
                }
            }
            if (((ForagingMinerals) object).getType().equals(IRIDIUM)) {

                if (checkAmountProductAvailable(new BarsAndOres(BarsAndOreType.IridiumOre), 1) ||
                        currentPlayer.getBackPack().getType().getRemindCapacity() > 0) {
                    advanceItem(new BarsAndOres(BarsAndOreType.IridiumOre), 1);
                    return new Result(false, BRIGHT_BLUE + "+1 Iridium ore" + RESET);
                }
                else {
                    tile.setGameObject(new Walkable());
                    return new Result(false, RED+"Ops!!! you destroy Iridium ore" + RESET);
                }
            }
            if (((ForagingMinerals) object).getType().equals(IRON)) {

                if (checkAmountProductAvailable(new BarsAndOres(BarsAndOreType.IronOre), 1) ||
                        currentPlayer.getBackPack().getType().getRemindCapacity() > 0) {
                    advanceItem(new BarsAndOres(BarsAndOreType.IronOre), 1);
                    return new Result(false, BRIGHT_BLUE + "+1 Iron ore" + RESET);
                }
                else {
                    tile.setGameObject(new Walkable());
                    return new Result(false, RED+"Ops!!! you destroy Iron ore" + RESET);
                }
            }
            else {
                if (checkAmountProductAvailable(new ForagingMinerals(((ForagingMinerals) object).getType()), 1) || // TODO همه سنگ معدنا قراره یه چا بگیرن ؟
                        currentPlayer.getBackPack().getType().getRemindCapacity() > 0) {
                     advanceItem(new ForagingMinerals(((ForagingMinerals) object).getType()), 1);
                     return new Result(false, BRIGHT_BLUE + "+1 " +
                             ((ForagingMinerals) object).getType().getDisplayName()+RESET);
                }
                else {
                    tile.setGameObject(new Walkable());
                    return new Result(false, RED+"Ops!!! you destroy "+
                            ((ForagingMinerals) object).getType().getDisplayName()+RESET);
                }
            }
        }
        else if (object instanceof Walkable && ((Walkable) object).getGrassOrFiber().equals("Plowed")) {
            tile.setGameObject(new Walkable());
            return new Result(true, BRIGHT_BLUE+"Oops! You accidentally removed a plowed tile"+RESET);
        }
        else if (object instanceof CraftingItem) {

            String str = ((CraftingItem) object).getCraftType().getName();
            tile.setGameObject(new Walkable());
            return new Result(false, RED+"You Destroy "+str+RESET);
        }
        return new Result(false, RED+"There are no plant!"+RESET);
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
    private void NPCAutomatTask () {

        User saveUser = currentPlayer;

        for (User user : players)
            for (NPC npc : NPC.values()) {

                currentPlayer = user;
                user.setTodayTalking(npc, false);
                user.setTodayGifting(npc, false);
                user.setLevel3Date(npc, currentDate);

                if (user.getFriendshipLevel(npc) == 3 && Math.random() > 0.5)
                    if (user.getBackPack().getType().getRemindCapacity() > 0 ||
                            checkAmountProductAvailable(npc.getGiftItem(), 1))

                        advanceItem(npc.getGiftItem(), 1);
            }
        currentPlayer = saveUser;
    }
    private String padRight(String text, int length) {
        if (text.length() >= length) return text.substring(0, length);
        return text + " ".repeat(length - text.length());
    }
    private String OneNPCQuestsList (NPC npc) {

        StringBuilder sb = new StringBuilder();

        int width = 120;
        String title = BRIGHT_BLUE + npc.getName() + RESET;
        String quest2;
        String quest3;
        ArrayList<String> requests = new ArrayList<>();
        ArrayList<Integer> numbers = new ArrayList<>(npc.getRequests().values());

        for (Items item : npc.getRequests().keySet())
            requests.add(item.toString());



        int padding = (width - 2 - title.length()) / 2;
        sb.append("|")
                .append(" ".repeat(padding))
                .append(title)
                .append(" ".repeat(width - 2 - padding - title.length()))
                .append("|\n");

        sb.append("|").append(" ".repeat(width - 2)).append("|\n");
        sb.append("|").append(" ".repeat(width - 2)).append("|\n");

        sb.append("| ").append(padRight("Quest 1 :", width - 3)).append("|\n\n");

        String result = numbers.getFirst()+" "+requests.getFirst() + " ---> " + npc.getReward(1);
        sb.append("|").append(" ".repeat(10)).append(padRight(result, width - 3)).append("|\n\n");


        if (currentPlayer.getFriendshipLevel(npc) >= 1)
            quest2 = "Quest 2 :";
        else
            quest2 = "Quest 2 : (unlock at friendship level 1)";

        sb.append("| ").append(padRight(quest2, width - 3)).append("|\n\n");

        String result2 = numbers.getFirst()+" "+requests.get(1) + " ---> " + npc.getReward(2);
        sb.append("|").append(" ".repeat(10)).append(padRight(result2, width - 3)).append("|\n\n");

        int dif = getDayDifferent(currentPlayer.getLevel3Date(npc), currentDate);

        if (currentPlayer.getFriendshipLevel(npc) >= 3) {
            if (dif > npc.getRequest3DayNeeded())
                quest3 = "Quest 3 :";
            else
                quest3 = "Quest 3 : (unlock in " + dif + " days later";
        }
        else
            quest3 = "Quest 3 : (unlock at friendship level 3)";

        sb.append("| ").append(padRight(quest3, width - 3)).append("|\n\n");

        String result3 = numbers.getFirst()+" "+requests.get(2) + " ---> " + npc.getReward(3);
        sb.append("|").append(" ".repeat(10)).
                append(padRight(result3, width - 3)).append("|\n\n");

        return sb.toString();
    }
    private String OneNPCFriendshipList (NPC npc) {

        int width = 60;

        return "|" + " ".repeat(width - 2) + "|\n" +
                "| " + padRight(npc.getName() + " : " +
                currentPlayer.getFriendshipLevel(npc), width - 3) + "|\n";
    }
    private Result doTask1 (NPC npc) {


        Map.Entry<Items, Integer> entry = new ArrayList<>(npc.getRequests().entrySet()).getFirst();
        Items key = entry.getKey();
        int value = entry.getValue();

        if (!checkAmountProductAvailable(key, value))
            return new Result(false, RED+"You don't have enough source"+RESET);

        switch (npc) {

            case Sebastian -> {

                if (checkAmountProductAvailable(new ForagingMinerals(DIAMOND), 1) ||
                    currentPlayer.getBackPack().getType().getRemindCapacity() > 0) {

                    int number = 2;
                    if (currentPlayer.getFriendshipLevel(npc) > 1)
                        number *= 2;
                    advanceItem(key, -value);
                    advanceItem(new ForagingMinerals(DIAMOND), number);
                    return new Result(true, BRIGHT_BLUE+"You got "+number+" Diamond"+RESET);
                }
                return new Result(true, RED+"Inventory is full"+RESET);
            }
            case Abigail -> {

                currentPlayer.increaseFriendshipPoint(NPC.Abigail, 200);
                return new Result(true, BRIGHT_BLUE+"Your friendship level with Abigail increased"+RESET);
            }
            case Harvey -> {

                int number = 750;
                if (currentPlayer.getFriendshipLevel(npc) > 1)
                    number *= 2;
                currentPlayer.increaseMoney(number);
                return new Result(true, "Your got +"+number+" money");
            }
            case Lia -> {
                int number = 500;
                if (currentPlayer.getFriendshipLevel(npc) > 1)
                    number *= 2;
                currentPlayer.increaseMoney(number);
                return new Result(true, "Your got +"+number+" money");
            }
            case Robin -> {

                int number = 2000;
                if (currentPlayer.getFriendshipLevel(npc) > 1)
                    number *= 2;
                currentPlayer.increaseMoney(number);
                return new Result(true, "Your got +"+number+" money");
            }
            default -> {
                return new Result(true, "");
            }
        }

    }
    private Result doTask2 (NPC npc) {

        if (currentPlayer.getFriendshipLevel(npc) < 1)
            return new Result(false, RED+"Your friendship with "+npc.getName()+" needs to grow"+RESET);

        Map.Entry<Items, Integer> entry = new ArrayList<>(npc.getRequests().entrySet()).get(1);
        Items key = entry.getKey();
        int value = entry.getValue();

        if (!checkAmountProductAvailable(key, value))
            return new Result(false, RED+"You don't have enough source"+RESET);

        switch (npc) {

            case Sebastian -> {
                int number = 5000;
                if (currentPlayer.getFriendshipLevel(npc) > 1)
                    number *= 2;
                currentPlayer.increaseMoney(number);
                return new Result(true, "Your got +"+number+" money");
            }
            case Abigail -> {
                int number = 500;
                if (currentPlayer.getFriendshipLevel(npc) > 1)
                    number *= 2;
                currentPlayer.increaseMoney(number);
                return new Result(true, "Your got +"+number+" money");
            }
            case Harvey -> {
                currentPlayer.increaseFriendshipPoint(NPC.Abigail, 200);
                return new Result(true, BRIGHT_BLUE+"Your friendship level with Harvey increased"+RESET);
            }
            case Lia -> {

                if (checkAmountProductAvailable(new MarketItem(MarketItemType.PancakesRecipe), 1) ||
                        currentPlayer.getBackPack().getType().getRemindCapacity() > 0) {

                    advanceItem(key, -value);
                    advanceItem(new MarketItem(MarketItemType.PancakesRecipe), 1);
                    return new Result(true, BRIGHT_BLUE+"You got 1 Pancakes Recipe"+RESET);
                }
                return new Result(true, RED+"Inventory is full"+RESET);
            }
            case Robin -> {
                int number = 1000;
                if (currentPlayer.getFriendshipLevel(npc) > 1)
                    number *= 2;
                currentPlayer.increaseMoney(number);
                return new Result(true, "Your got +"+number+" money");
            }
            default -> {
                return new Result(true, "");
            }
        }
    }
    private Result doTask3 (NPC npc) {

        int dif = getDayDifferent(currentPlayer.getLevel3Date(npc), currentDate);

        if (currentPlayer.getFriendshipLevel(npc) >= 3) {
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
                        currentPlayer.getBackPack().getType().getRemindCapacity() > 0) {
                    int number = 50;
                    if (currentPlayer.getFriendshipLevel(npc) > 1)
                        number *= 2;
                    advanceItem(key, -value);
                    advanceItem(new ForagingMinerals(QUARTZ), number);
                    return new Result(true, BRIGHT_BLUE+"You got "+number+" Quartz"+RESET);
                }
                return new Result(true, RED+"Inventory is full"+RESET);
            }
            case Abigail -> {
                if (checkAmountProductAvailable(new CraftingItem(CraftType.IridiumSprinkler), 1) ||
                        currentPlayer.getBackPack().getType().getRemindCapacity() > 0) {
                    int number = 1;
                    if (currentPlayer.getFriendshipLevel(npc) > 1)
                        number *= 2;
                    advanceItem(key, -value);
                    advanceItem(new CraftingItem(CraftType.IridiumSprinkler), number);
                    return new Result(true, BRIGHT_BLUE+"You got "+number+" Iridium sprinkler"+RESET);
                }
                return new Result(true, RED+"Inventory is full"+RESET);
            }
            case Harvey -> {
                if (checkAmountProductAvailable(new MarketItem(MarketItemType.Salad), 1) ||
                        currentPlayer.getBackPack().getType().getRemindCapacity() > 0) {
                    int number = 5;
                    if (currentPlayer.getFriendshipLevel(npc) > 1)
                        number *= 2;
                    advanceItem(key, -value);
                    advanceItem(new MarketItem(MarketItemType.Salad), number);
                    return new Result(true, BRIGHT_BLUE+"You got "+number+" salad"+RESET);
                }
                return new Result(true, RED+"Inventory is full"+RESET);
            }
            case Lia -> {

                if (checkAmountProductAvailable(new CraftingItem(CraftType.DeluxeScarecrow), 1) ||
                        currentPlayer.getBackPack().getType().getRemindCapacity() > 0) {
                    int number = 1;
                    if (currentPlayer.getFriendshipLevel(npc) > 1)
                        number *= 2;
                    advanceItem(key, -value);
                    advanceItem(new CraftingItem(CraftType.DeluxeScarecrow), number);
                    return new Result(true, BRIGHT_BLUE+"You got "+number+" ⅾeⅼuxe sⅽareⅽrow"+RESET);
                }
                return new Result(true, RED+"Inventory is full"+RESET);
            }
            case Robin -> {
                int number = 1500;
                if (currentPlayer.getFriendshipLevel(npc) > 1)
                    number *= 2;
                currentPlayer.increaseMoney(number);
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
                + currentDate.getHour()+ ":00");
    }
    public Result showDate () {
        return new Result(true, BLUE+"Date : "+RED+currentDate.getYear()+RESET+" "+currentDate.getNameSeason()+" "+currentDate.getDate());
    }
    public Result showSeason   () {

        return new Result(true, currentDate.getNameSeason());
    }
    public Result showWeather  (boolean isToday) {

        if (isToday)
            return new Result(true, currentWeather.getDisplayName());
        else
            return new Result(true, tomorrowWeather.getDisplayName());
    }
    public Result setWeather   (String type) {

        Weather weather;
        try {
            weather = Weather.valueOf(type);
        } catch (Exception e) {
            return new Result(false, RED+"Weather type is incorrect!"+RESET);
        }
        tomorrowWeather = weather;
        return new Result(true, BLUE+"Tomorrow weather change to "+RESET+currentWeather.getDisplayName());
    }
    public Result setEnergy    (String amount) {

        if (currentPlayer.isHealthUnlimited())
            return new Result(false, BLUE+"You're unstoppable! Energy level: ∞"+RESET);

        if (amount.charAt(0) == '-')
            return new Result(false, RED+"Energy must be a positive number!"+RESET);
        int amount2;
        try {
            amount2 = Integer.parseInt(amount);
        } catch (Exception e) {
            return new Result(false, RED+"Number is incorrect!"+RESET);
        }

        if (currentPlayer.getHealth() > amount2) {
            currentPlayer.setHealth(amount2);
            return new Result(true, BLUE+"Your Energy decrease to :" +RESET+amount2);
        }
        else if (currentPlayer.getHealth() < amount2) {
            currentPlayer.setHealth(amount2);
            return new Result(true, BLUE+"Your Energy increase to :" +RESET+amount2);
        } else
            return new Result(false, "Your energy level at this moment is this amount.");
    }
    public Result showDateTime () {
        return new Result(true, BLUE+"Time : "+RED+ currentDate.getHour()+ ":00" +
                BLUE+"\nData : "+RED+currentDate.getYear()+RESET+" "+currentDate.getNameSeason()+" "+currentDate.getDate());
    }
    public Result showDayOfWeek() {
        return new Result(true, BLUE+"Day of Week : "+RESET
                + currentDate.getDayOfTheWeek());
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
        return new Result(true, BLUE+"Time change to : "+GREEN+ currentDate.getHour()+":00"+RESET);
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
        return new Result(true, BLUE+"Date change to : "+RED+currentDate.getYear()+RESET+" "+currentDate.getNameSeason()+" "+currentDate.getDate());
    }
    public Result EnergyUnlimited () {

        currentPlayer.setHealthUnlimited();
        return new Result(true, BLUE+"Whoa! Infinite energy mode activated!"+RESET);
    }

                                                                   // input command plant
    public Result planting (String name, String direction) {

        if (!checkDirection(direction))
            return new Result(false, RED+"Direction is invalid"+RESET);

        int dir = Integer.parseInt(direction);

        if (name.matches("\\s*(?i)Mixed\\s*seed(s)?\\s*"))
            return plantMixedSeed(dir);

        try {
            ForagingSeedsType type = ForagingSeedsType.valueOf(name);
            return plantForagingSeed(type, dir);
        } catch (Exception e) {
            try {
                TreesSourceType type2 = TreesSourceType.valueOf(name);
                return plantTree(type2, dir);
            } catch (Exception e2) {
                return new Result(false, RED+"Hmm... that seed name doesn’t seem right!"+RESET);
            }
        }
    }
    public Result WateringPlant (String direction) {

        if (!checkDirection(direction))
            return new Result(false, RED+"Direction is invalid"+RESET);

        int dir = Integer.parseInt(direction);

        if (!(currentPlayer.currentTool instanceof WateringCan))
            return new Result(false, RED+"سطل اب رو بردار دوست من"+RESET);

        if (!currentPlayer.isHealthUnlimited())
            currentPlayer.increaseHealth(currentPlayer.currentTool.healthCost());

        Tile tile = getTileByDir(dir);
        GameObject object = tile.getGameObject();

        if (((WateringCan) currentPlayer.currentTool).getReminderCapacity() < 1)
            return new Result(false, RED+"ظرفت خالیه مشتی"+RESET);

        if (object instanceof ForagingSeeds) {

            ((WateringCan) currentPlayer.currentTool).decreaseWater(1);
            ((ForagingSeeds) object).setLastWater(currentDate);
            currentPlayer.increaseHealth(currentPlayer.currentTool.healthCost());
            return new Result(true, BLUE+"The plant has been watered!\uD83C\uDF31"+RESET);

        } else if (object instanceof Tree) {

            ((WateringCan) currentPlayer.currentTool).decreaseWater(1);
            ((Tree) object).setLastWater(currentDate);
            currentPlayer.increaseHealth(currentPlayer.currentTool.healthCost());
            return new Result(true, BLUE+"The plant has been watered!\uD83C\uDF31"+RESET);

        } else if (object instanceof GiantProduct) {

            ((WateringCan) currentPlayer.currentTool).decreaseWater(1);
            ((GiantProduct) object).setLastWater(currentDate);
            currentPlayer.increaseHealth(currentPlayer.currentTool.healthCost());
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

        if (!currentPlayer.getFarm().isInFarm(x1, y1))
            return new Result(false, RED+"Pick from your own farm!"+RESET);
        if (isInGreenHouse(getTileByCoordinates(x1, y1)))
            return new Result(false, RED+"Lightning can’t hit the greenhouse"+RESET);

        lightningStrike(getTileByCoordinates(x1, y1));
        return new Result(true, BLUE+"A lightning bolt hits!"+RESET);
    }
    public Result buildGreenHouse () {

        if (!checkAmountProductAvailable(new Wood(), GreenHouse.requiredWood))
            return new Result(false, RED+"You don't have enough wood!"+RESET);

        if (currentPlayer.getMoney() < GreenHouse.requiredCoins )
            return new Result(false, RED+"You don't have enough Coin!"+RESET);

        currentPlayer.increaseMoney(-GreenHouse.requiredCoins);
        advanceItem(new Wood(), GreenHouse.requiredWood);

        currentPlayer.getFarm().getGreenHouse().setCreated(true);

        return new Result(true, BLUE+"The greenhouse has been built! \uD83C\uDF31"+RESET);
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

        if (!currentPlayer.getFarm().isInFarm(x, y))
            return new Result(false, RED+"Pick from your own farm!"+RESET);

        Tile tile = getTileByCoordinates(x, y);

        if (tile.getGameObject() instanceof Tree)
            return new Result(true, showTree((Tree) tile.getGameObject()));
        if (tile.getGameObject() instanceof ForagingSeeds)
            return new Result(true, showForaging((ForagingSeeds) tile.getGameObject()));
        if (tile.getGameObject() instanceof GiantProduct)
            return new Result(true, showGiant((GiantProduct) tile.getGameObject()));

        return new Result(false, RED+"That tile don't have plant!"+RESET);

    }
    public Result fertilize (String fertilizeType, String direction) {

        if (!checkDirection(direction))
            return new Result(false, RED+"Direction is invalid"+RESET);

        int dir = Integer.parseInt(direction);
        Tile tile = getTileByDir(dir);

        if (!checkTileForPlant(tile))
            return new Result(false, RED+"This tile has no plants!"+RESET);

        MarketItemType type;

        try {
            type = MarketItemType.valueOf(fertilizeType);
        } catch (Exception e) {
            return new Result(false, RED+"Fertilize type is invalid"+RESET);
        }
        if (!checkAmountProductAvailable(new MarketItem(type), 1))
            return new Result(false, RED+"You don't have enough "+type.getName()+RESET);

        advanceItem(new MarketItem(type), -1);
        fertilizePlant(type, tile);
        return new Result(true, BLUE+"The plant has been fertilized! ✨"+RESET);
    }

                                                                   // input tools command
    public Result howMuchWater () {

        Inventory inventory = currentPlayer.getBackPack().inventory;

        for (Map.Entry <Items,Integer> entry: inventory.Items.entrySet())
            if (entry instanceof WateringCan)
                return new Result(true, BLUE+"Water Remaining : "
                        +RESET+((WateringCan) entry).getReminderCapacity());

        return new Result(false, BLUE+"کدوم سطل سلطان"+RESET);
    }
    public Result toolsEquip (String name) {

        Inventory inventory = currentPlayer.getBackPack().inventory;

        for (Map.Entry<Items,Integer> entry: inventory.Items.entrySet()) {

            if (entry instanceof Axe && name.equals("Axe")) {
                currentPlayer.currentTool = (Tools) entry.getKey();
                return new Result(true, BRIGHT_BLUE+"Axe successfully picked up"+RESET);
            }
            else if (entry instanceof FishingPole && name.equals("FishingPole")){
                currentPlayer.currentTool = (Tools) entry.getKey();
                return new Result(true, BRIGHT_BLUE+"FishingPole successfully picked up"+RESET);
            }
            else if (entry instanceof Hoe && name.equals("Hoe")){
                currentPlayer.currentTool = (Tools) entry.getKey();
                return new Result(true, BRIGHT_BLUE+"Hoe successfully picked up"+RESET);
            }
            else if (entry instanceof PickAxe && name.equals("PickAxe")){
                currentPlayer.currentTool = (Tools) entry.getKey();
                return new Result(true, BRIGHT_BLUE+"PickAxe successfully picked up"+RESET);
            }
            else if (entry instanceof WateringCan && name.equals("WateringCan")){
                currentPlayer.currentTool = (Tools) entry.getKey();
                return new Result(true, BRIGHT_BLUE+"WateringCan successfully picked up"+RESET);
            }
            else if (entry instanceof MilkPail && name.equals("MilkPail")){
                currentPlayer.currentTool = (Tools) entry.getKey();
                return new Result(true, BRIGHT_BLUE+"MilkPail successfully picked up"+RESET);
            }
            else if (entry instanceof Scythe && name.equals("Scythe")){
                currentPlayer.currentTool = (Tools) entry.getKey();
                return new Result(true, BRIGHT_BLUE+"Scythe successfully picked up"+RESET);
            }
            else if (entry instanceof Shear && name.equals("Shear")){
                currentPlayer.currentTool = (Tools) entry.getKey();
                return new Result(true, BRIGHT_BLUE+"Shear successfully picked up"+RESET);
            }
        }
        return new Result(false,"there is no such tool");
    }
    public Result showCurrentTool() {

        Tools currentTool = currentPlayer.currentTool;

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

        Inventory inventory = currentPlayer.getBackPack().inventory;
        StringBuilder result = new StringBuilder();

        for (Map.Entry<Items, Integer> entry : inventory.Items.entrySet()) {

            if (entry instanceof Axe)
                result.append(((Axe) entry).getType().getDisplayName()).append("\n");

            else if (entry instanceof FishingPole)
                result.append(((FishingPole) entry).type).append("\n");

            else if (entry instanceof Hoe)
                result.append(((Hoe) entry).getType().getDisplayName()).append("\n");

            else if (entry instanceof WateringCan)
                result.append(((WateringCan) entry).getType()).append("\n");

            else if (entry instanceof PickAxe)
                result.append(((PickAxe) entry).getType().getDisplayName()).append("\n");

            else if (entry instanceof Tools)
                result.append(((Tools) entry).getName()).append("\n");

        }
        return new Result(true, result.toString());
    }
    public Result upgradeTool (String name) {
         MarketType marketType=MarketType.isInMarket(currentPlayer.getPositionX() , currentPlayer.getPositionY());
         if (marketType!=MarketType.Blacksmith) {
             return new Result(false , "you are not in BlackSmith Market. please go there");
         }

         if (name.equals("Trash"))

    }
    public Result useTools (String direction) {

        if (!currentPlayer.isHealthUnlimited())
            currentPlayer.increaseHealth(currentPlayer.currentTool.healthCost());

        if (!checkDirection(direction))
            return new Result(false, RED+"Direction is invalid"+RESET);

        int dir = Integer.parseInt(direction);

        Tools tools = currentPlayer.currentTool;

        if (tools instanceof Axe)
            return useAxe(dir);
        else if (tools instanceof Hoe)
            return useHoe(dir);
        else if (tools instanceof MilkPail)
            return useMilkPail(dir);
        else if (tools instanceof Scythe)
            return useScythe(dir);
        else if (tools instanceof Shear)
            return useShear(dir);
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

        if (!npc.isInHisHome(currentPlayer.getPositionX(), currentPlayer.getPositionY()))
            return new Result(false, RED+"You should go to their place first"+RESET);

        if (!currentPlayer.getTodayTalking(npc)) {
            currentPlayer.setTodayTalking(npc,true);
            currentPlayer.increaseFriendshipPoint(npc, 20);
        }

        return new Result(true, BLUE +
                npc.getDialogue(currentPlayer.getFriendshipLevel(npc), currentWeather)+RESET);
    }
    public Result giftNPC (String name, String itemName) {

        NPC npc;
        try {
            npc = NPC.valueOf(name);
        } catch (Exception e) {
            return new Result(false, RED+"You're looking for someone who isn't real"+RESET);
        }

        if (!npc.isInHisHome(currentPlayer.getPositionX(), currentPlayer.getPositionY()))
            return new Result(false, RED+"You should go to their place first"+RESET);

        Items item = AllFromDisplayNames(itemName);

        if (item == null)
            return new Result(false, RED+"You can only gift items from the market, crops and fruit"+RESET);

        if (!checkAmountProductAvailable(item, 1))
            return new Result(false, RED+"You don't have this item"+RESET);

        advanceItem(item, -1);

        if (!currentPlayer.getTodayGifting(npc)) {

            if (npc.isItFavorite(item))
                currentPlayer.increaseFriendshipPoint(npc, 200);
            else
                currentPlayer.increaseFriendshipPoint(npc, 50);
        } else {

            if (npc.isItFavorite(item))
                currentPlayer.increaseFriendshipPoint(npc, 50);
            else
                currentPlayer.increaseFriendshipPoint(npc, 15);
        }
        return new Result(false, BRIGHT_BLUE+"Your gift successfully sent to "
                + BRIGHT_GREEN + npc.getName() + RESET);
    } // TODO check location
    public Result questsNPCList () {

        StringBuilder sb = new StringBuilder();

        sb.append("+").append("-".repeat(120 - 2)).append("+\n");

        for (NPC npc : NPC.values())
            sb.append(OneNPCQuestsList(npc));

        sb.append("+").append("-".repeat(120 - 2)).append("+");

        return new Result(true, sb.toString());
    }
    public Result friendshipNPCList () {

        StringBuilder sb = new StringBuilder();

        sb.append("+").append("-".repeat(100 - 2)).append("+\n");

        for (NPC npc : NPC.values())
            sb.append(OneNPCFriendshipList(npc));

        sb.append("+").append("-".repeat(100 - 2)).append("+");

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
        if (!npc.isInHisHome(currentPlayer.getPositionX(), currentPlayer.getPositionY()))
            return new Result(false, RED+"You should go to their place first"+RESET);

        return switch (ID) {
            case 1 -> doTask1(npc);
            case 2 -> doTask2(npc);
            case 3 -> doTask3(npc);
            default -> new Result(false, RED + "Index is invalid" + RESET);
        };
    }

}