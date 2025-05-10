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
import model.Enum.ToolsType.FishingPoleType;
import model.Enum.WeatherTime.Season;
import model.Enum.ItemType.WallType;
import model.Enum.WeatherTime.Weather;
import model.MapThings.*;
import model.Places.*;
import model.Plants.*;
import model.ToolsPackage.*;

import java.util.*;

import static model.App.*;
import static model.App.tomorrowWeather;
import static model.Color_Eraser.*;
import static model.Enum.AllPlants.ForagingMineralsType.*;

import static model.Enum.AllPlants.ForagingMineralsType.RUBY;
import static model.SaveData.UserDataBase.findUserByUsername;


public class GameController {

    Random rand = new Random();

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
                return entry.getValue() > number;
            if (entry instanceof AllCrops && items instanceof AllCrops &&
                    ((AllCrops) entry).getType().equals(((AllCrops) items).getType()))
                return entry.getValue() > number;
            if (entry instanceof ForagingSeeds && items instanceof ForagingSeeds &&
                    ((ForagingSeeds) entry).getType().equals(((ForagingSeeds) items).getType()))
                return entry.getValue() > number;
            if (entry instanceof TreesProdct && items instanceof TreesProdct &&
                    ((TreesProdct) entry).getType().equals(((TreesProdct) items).getType()))
                return entry.getValue() > number;
            if (entry instanceof TreeSource && items instanceof TreeSource &&
                    ((TreeSource) entry).getType().equals(((TreeSource) items).getType()))
                return entry.getValue() > number;
            if (entry instanceof ForagingCrops && items instanceof ForagingCrops &&
                    ((ForagingCrops) entry).getType().equals(((ForagingCrops) items).getType()))
                return entry.getValue() > number;
            if (entry instanceof ForagingMinerals && items instanceof ForagingMinerals &&
                    ((ForagingMinerals) entry).getType().equals(((ForagingMinerals) items).getType()))
                return entry.getValue() > number;
            if (entry instanceof MarketItem && items instanceof MarketItem &&
                    ((MarketItem) entry).getType().equals(((MarketItem) items).getType()))
                return entry.getValue() > number;

        }
        return false;
    }
    private void advanceItem(Items items, int amount) { // برای کم کردن الو چک بشه اون تعداد داریم یا نه

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
    }


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
        for (Tile tile : bigMap) {
            if (tile.getX() == x && tile.getY() == y) {
                return tile;
            }
        }
        return null;
    }   ///        الان اینونتوری ساخته میشه از همه چی توش گذاشتی همون اول ؟


    public void createInitialMine(int id, int x, int y){
        Farm farm = currentPlayer.getFarm();
        if (id == 1) {
            Mine mine = new Mine(23, 2);
            door MineDoor = new door();
            MineDoor.setDoor(Door.Mine);
            MineDoor.setCharactor('D');
            mine.setCharactor('M');
            Walkable walkable = new Walkable();
            walkable.setCharactor('.');

            for (int i = 23; i < 29; i++) {
                for (int j = 2; j < 8; j++) {
                    if (i == 26 && j == 5) {
                        Tile tile = new Tile(i + 60 * x, j + 60 * y, MineDoor);
                        farm.Farm.add(tile);
                        bigMap.add(tile);
                    } else if (i == 23 || i == 28 || j == 2 || j == 7) {
                        Tile tile = new Tile(i + 60 * x, j + 60 * y, mine);
                        farm.Farm.add(tile);
                        bigMap.add(tile);
                    } else {
                        Tile tile = new Tile(i + 60 * x, j + 60 * y, walkable);
                        farm.Farm.add(tile);
                        bigMap.add(tile);
                    }
                }
            }
            farm.setMine(mine);
        } else if (id == 2) {

        }
    }
    public void createInitialLake(int id, int x, int y) {
        Farm farm = currentPlayer.getFarm();
        if (id == 1) {
            Lake lake = new Lake();
            lake.setCharactor('L');
            for (int i = 2; i < 7; i++) {
                for (int j = 23; j < 29; j++) {
                    Tile tile = new Tile(i + 60 * x, j + 60 * y, lake);
                    farm.Farm.add(tile);
                    bigMap.add(tile);
                }
            }
            farm.setLake(lake);
        } else if (id == 2) {

        }
    }
    public void createInitialHouse(int id, int x, int y) {
        Farm farm = currentPlayer.getFarm();
        Wall wall = new Wall();
        wall.setWallType(WallType.House);
        wall.setCharactor('#');
        door houseDoor = new door();
        houseDoor.setDoor(Door.House);
        houseDoor.setCharactor('D');

        for (int i = 12; i < 19; i++) {
            for (int j = 0; j < 7; j++) {
                if (i == 12 || i == 18) {
                    Tile tile = new Tile(i + 60 * x, j + 60 * y, wall);
                    farm.Farm.add(tile);
                    bigMap.add(tile);
                } else if (i == 15 && j == 6) {
                    Tile tile = new Tile(i + 60 * x, j + 60 * y, houseDoor);
                    farm.Farm.add(tile);
                    bigMap.add(tile);
                } else {
                    if (j == 0 || j == 6) {
                        Tile tile = new Tile(i + 60 * x, j + 60 * y, wall);
                        farm.Farm.add(tile);
                        bigMap.add(tile);
                    }
                }
            }
        }


        Fridge fridge = new Fridge(18 + 60 * x, 6 + 60 * y);
        fridge.setCharactor('F');
        Home home = new Home(13 + 60 * x, 1 + 60 * y, fridge);
        home.setCharactor('H');
        home.houseDoor = houseDoor;

        for (int i = 13; i < 18; i++) {
            for (int j = 1; j < 6; j++) {
                if (i == 18 && j == 5) {
                    Tile tile = new Tile(i + 60 * x, j + 60 * y, fridge);
                    farm.Farm.add(tile);
                    bigMap.add(tile);
                } else {
                    Tile tile = new Tile(i + 60 * x, j + 60 * y, home);
                    farm.Farm.add(tile);
                    bigMap.add(tile);
                }
            }
        }
        farm.setHome(home);
    }
    public void createInitialGreenHouse(int id, int x, int y) {
        Farm farm = currentPlayer.getFarm();
        Wall GreenWall = new Wall();
        GreenWall.setWallType(WallType.GreenHouse);
        GreenWall.setCharactor('#');
        door greenHouseDoor=new door();
        greenHouseDoor.setDoor(Door.GreenHouse);
        greenHouseDoor.setCharactor('D');

        for (int i=0;i<8;i++){
            for (int j=0;j<9;j++) {
                if (i==0 || i==7){
                    if (j==4 && i==7){
                        Tile tile = new Tile(i +60*x, j +60*y, greenHouseDoor);
                        farm.Farm.add(tile);
                        bigMap.add(tile);
                    }
                    else {
                        Tile tile = new Tile(i +60*x, j +60*y, GreenWall);
                        farm.Farm.add(tile);
                        bigMap.add(tile);
                    }
                }
                else {
                    if (j==0 || j==8){
                        Tile tile = new Tile(i +60*x, j+60*y, GreenWall);
                        farm.Farm.add(tile);
                        bigMap.add(tile);
                    }
                }
            }
        }

        GreenHouse greenHouse=new GreenHouse(1 + 60*x,1 + 60*y);
        greenHouse.setCharactor('G');
        WaterTank waterTank=new WaterTank(100);
        waterTank.setWaterTank('W'); // اینجا عدد میخواد چرا حروف دادی؟
        greenHouse.setWaterTank(waterTank);
        for (int i=1 ; i<7 ; i++){
            for (int j=1 ; j<8 ; j++) {
                if (i==1 && j==3){
                    Tile tile=new Tile(i + 60*x,j + 60*y,waterTank);
                    farm.Farm.add(tile);
                    bigMap.add(tile);
                }
                else {
                    Tile tile=new Tile(i + 60*x,j + 60*y,greenHouse);
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

        for (int i=0 ; i<30 ;i++){
            for (int j=0 ; j<30 ; j++) {
                if (i>=23 && i<29 && j>=2 && j<8){
                    createInitialMine(id,currentPlayer.topLeftX,currentPlayer.topLeftY);
                }
                else if(1<i && i<7 && j>22 && j<29){
                    createInitialLake(id,currentPlayer.topLeftX,currentPlayer.topLeftY);
                }
                else if(i>=12 && i<19 && j<7){
                    createInitialHouse(id,currentPlayer.topLeftX,currentPlayer.topLeftY);
                }
                else if (i<8 && j<9){
                    createInitialGreenHouse(id,currentPlayer.topLeftX,currentPlayer.topLeftY);
                }
                else {
                    MapGenerator(i,j,seed);
                }
            }
        }
        farms.add(farm);

        return farm;

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

        if (checkConditionsForWalk(goalX, goalY) !=null) {
            return checkConditionsForWalk(goalX, goalY);
        }
        if (currentPlayer.isHealthUnlimited()){
            currentPlayer.setPositionX(goalX);
            currentPlayer.setPositionY(goalY);
            return new Result(true,"now you are in "+goalX+","+goalY);
        }

        int [] dirx={0,0,0,1,1,1,-1,-1,-1};
        int [] diry={0,1,-1,0,1,-1,0,1,-1};
        HashMap<Tile,Integer> costEnergy=new HashMap<>();
        Queue<int []> queue=new LinkedList<>();

        for (int i=0 ; i<8 ; i++) {
            queue.add(new int[]{startX,startY,i,0,0});
        }

        while (!queue.isEmpty()) {
            int [] current=queue.poll();
            int x=current[0], y=current[1], dir=current[2], steps=current[3], turns=current[4];
            for (int i=0 ; i<8 ; i++) {
                int nx=x+dirx[i], ny=y+diry[i];

                Tile nextTile=getTileByCoordinates(nx, ny);
                if (nextTile==null || checkTile(nextTile)) continue;

                int newSteps=steps+1;
                int newTurn=turns+(i==dir ? 0:1);
                int cost=newSteps +10*newTurn;

                if (!costEnergy.containsKey(nextTile) || cost<costEnergy.get(nextTile)) {
                    costEnergy.put(nextTile, newSteps);
                    queue.add(new int[]{nextTile.getX(),nextTile.getY(),i,newSteps,newTurn});
                }

            }
        }

        if (!costEnergy.containsKey(endTile)) {
            return new Result(false,"you can't go to this coordinate because there no way");
        }
        else {
            int cost=costEnergy.get(endTile)/20;
            if (cost > currentPlayer.getHealth() /* TODO شاید بعدا از health controller استفاده کنیم! */){
                return new Result(false,"your Energy is not enough");
            }
            else {
                currentPlayer.increaseHealth(-cost);
                return new Result(true,"you are now in "+goalX+","+goalY);
            }
        }

    }
    private boolean checkTile(Tile tile){
        return tile.getGameObject() instanceof Home || tile.getGameObject() instanceof door
                || tile.getGameObject() instanceof Walkable || tile.getGameObject() instanceof GreenHouse;
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
        //TODO اگر NPC در اون مختصات باشه نمیتونیم اونجا بریم
        //TODO جاهایی که دونه کاشتیم
        return null;

    }

    public Result showInventory(Inventory inventory){
        String result="";
        for (Map.Entry <Items,Integer> entry: inventory.Items.entrySet()){
            if (entry instanceof BasicRock){
                result += "BasicRock: " + entry.getValue() + "\n";
            }
            else if (entry instanceof Wood){
                result += "Wood: " + entry.getValue() + "\n";
            }
            else if (entry instanceof ForagingMinerals){
                result += ((ForagingMinerals) entry).getType() +" "+ entry.getValue() + "\n";
            }
            else if (entry instanceof ForagingSeeds){
                result += ((ForagingSeeds) entry).getType() +" "+ entry.getValue() + "\n";
            }
            else if (entry instanceof AllCrops){
                result += ((AllCrops) entry).getType() +" "+ entry.getValue() + "\n";
            }
            else if (entry instanceof ForagingCrops) {
                result += ((ForagingCrops) entry).getType() +" "+ entry.getValue() + "\n";
            }
            else if (entry instanceof TreeSource){
                result += ((TreeSource) entry).getType() +" "+ entry.getValue() + "\n";
            }
            else if (entry instanceof Axe ){
                result += ((Axe) entry).getName() +" "+((Axe) entry).axeType + "\n";
            }
            else if (entry instanceof FishingPole){
                result += ((FishingPole) entry).getName() +" "+ ((FishingPole) entry).fishingPoleType + "\n";
            }
            else if (entry instanceof Hoe){
                result += ((Hoe) entry).getName() +" "+ ((Hoe) entry).getType() + "\n";
            }
            else if (entry instanceof PickAxe){
                result += ((PickAxe) entry).getName() +" "+ ((PickAxe) entry).pickAxeType +"\n";
            }
            else if (entry instanceof WateringCan){
                result += ((WateringCan) entry).getName() +" "+ ((WateringCan) entry).wateringCanType + "\n";
            }
            else if (entry instanceof TrashCan){
                result += ((TrashCan) entry).getName() +" "+((TrashCan) entry).Type + "\n";
            }
            else if (entry instanceof Tools){
                result+=((Tools) entry).getName() + "\n";
            }
        }

        return new Result(true,result);
    }

    private Result increaseMoney(Integer amount , int price , Items items,String name , Integer reminder) {
        int percent=0;
        for (Map.Entry<Items,Integer> entry: currentPlayer.getBackPack().inventory.Items.entrySet()) {
            if (entry instanceof TrashCan){
                percent= ((TrashCan) entry).Type.getPercent();
                break;
            }
        }
        if (amount ==null || amount == reminder) {
            int increase=(reminder * percent *price)/100;
            TrashCan.removeItem(increase,currentPlayer.getBackPack().inventory.Items, items, reminder);
            return new Result(true,name + "completely removed from your inventory");
        }
        if (amount > reminder) {
            return new Result(false,"not enough "+name+" "+"in your inventory for remove");
        }
        if (amount < reminder) {
            int increase=(reminder * percent *price)/100;
            TrashCan.removeItem(increase,currentPlayer.getBackPack().inventory.Items, items, reminder);
            return new Result(true , amount + " "+name+" "+"removed from your inventory");
        }

        return null;
    }



    public Result removeItemToTrashcan (String name, Integer number){
        Inventory inventory=currentPlayer.getBackPack().inventory;
        for (Map.Entry<Items,Integer> entry: inventory.Items.entrySet()){

            if (entry instanceof Wood){
                if (name.equals(Wood.name)) {
                    return increaseMoney(number, Wood.price, (Wood) entry.getKey(), name, entry.getValue());
                }
            }
            if (entry instanceof BasicRock){
                if (name.equals("BasicRock")) {
                    return increaseMoney(number, BasicRock.price, (BasicRock) entry.getKey(), name, entry.getValue());
                }
            }

            if (entry instanceof ForagingMinerals){
                if (((ForagingMinerals) entry).getType().getDisplayName().equals(name)){
                    return increaseMoney(number,( (ForagingMinerals) entry).getType().getPrice(),(ForagingMinerals) entry, name,entry.getValue());
                }
            }


            if (entry instanceof ForagingSeeds){
                if (((ForagingSeeds) entry).getType().getDisplayName().equals(name)){
                    //TODO قیمت foraging seeds رو باید از مارکتینگ در بیاریم
                    //TODO return increaseMoney(entry.getValue(),( (ForagingSeeds) entry).getType().getPrice(),(ForagingSeeds) entry, name,entry.getValue());
                }
            }
            if (entry instanceof AllCrops){
                if (((AllCrops) entry).getType().getDisplayName().equals(name)){
                    return increaseMoney(number,( (AllCrops) entry).getType().getPrice(),(AllCrops) entry, name,entry.getValue());
                }
            }
            if (entry instanceof TreeSource){
                if (((TreeSource) entry).getType().getDisplayName().equals(name)){
                    //TODO قیمت TreeSource رو باید از مارکتینگ دربیاریم
                    //TODO return increaseMoney(entry.getValue(),( (TreeSource) entry).getType().getPrice(),(TreeSource) entry, name,entry.getValue());
                }
            }
            if (entry instanceof ForagingCrops){
                if (((ForagingCrops) entry).getType().equals(name)){
                    return increaseMoney(entry.getValue(),( (ForagingCrops) entry).getType().getPrice(),(ForagingCrops) entry, name,entry.getValue());
                }
            }
            if (entry instanceof Tools){
                return new Result(false,"you can't remove "+name+"becuse it is a tool");
            }

            //TODO برای غذا و چیزهایی که در آینده ممکنه به اینونتوری اضافه بشه
        }
        return null;
    }

    public Result toolsEquip (String name){
        name=name.replaceAll("\\s+","");
        Inventory inventory=currentPlayer.getBackPack().inventory;

        for (Map.Entry<Items,Integer> entry: inventory.Items.entrySet()){
            if (entry instanceof Axe) {
                if (((Axe) entry).axeType.equals(name)){
                    currentPlayer.currentTool=(Tools) entry;
                    return new Result(true,"now current tool is "+name);
                }
            }
            else if (entry instanceof FishingPole){
                if (((FishingPole) entry).fishingPoleType.equals(name)){
                    currentPlayer.currentTool=(Tools) entry;
                    return new Result(true,"now current tool is "+name);
                }
            }
            else if (entry instanceof Hoe){
                if (((Hoe) entry).getType().equals(name)){
                    currentPlayer.currentTool=(Tools) entry;
                    return new Result(true,"now current tool is "+name);
                }
            }
            else if (entry instanceof PickAxe){
                if (((PickAxe) entry).pickAxeType.equals(name)){
                    currentPlayer.currentTool=(Tools) entry;
                    return new Result(true,"now current tool is "+name);
                }
            }
            else if (entry instanceof WateringCan){
                if (((WateringCan) entry).wateringCanType.equals(name)){
                    currentPlayer.currentTool=(Tools) entry;
                    return new Result(true,"now current tool is "+name);
                }
            }

            else if (entry instanceof Tools){
                if (((Tools) entry).getName().equals(name)){
                    currentPlayer.currentTool=(Tools) entry;
                    return new Result(true,"now current tool is "+name);
                }
            }
        }

        return new Result(false,"there is no such tool");
    }

    public Result showCurrentTool(){
        Tools currentTool=currentPlayer.currentTool;
        return switch (currentTool) {
            case null -> new Result(false, "there is no current tool in your hands");
            case Axe axe -> new Result(true, "current tool: " + axe.axeType);
            case FishingPole fishingPole -> new Result(true, "current tool: " + fishingPole.fishingPoleType);
            case Hoe hoe -> new Result(true, "current tool: " + hoe.getType());
            case WateringCan wateringCan -> new Result(true, "current tool: " + wateringCan.wateringCanType);
            case PickAxe pickAxe -> new Result(true, "current tool: " + pickAxe.pickAxeType);
            default -> new Result(true, "current tool: " + currentTool.getName());
        };
    }

    public Result availableTools() {
        Inventory inventory = currentPlayer.getBackPack().inventory;
        StringBuilder result = new StringBuilder();
        for (Map.Entry<Items, Integer> entry : inventory.Items.entrySet()) {
            if (entry instanceof Axe) {
                result.append(((Axe) entry).axeType).append("\n");
            } else if (entry instanceof FishingPole) {
                result.append(((FishingPole) entry).fishingPoleType).append("\n");
            } else if (entry instanceof Hoe) {
                result .append ( ((Hoe) entry).getType() ) .append("\n");
            } else if (entry instanceof WateringCan) {
                result.append(((WateringCan) entry).wateringCanType).append("\n");
            } else if (entry instanceof PickAxe) {
                result .append(((PickAxe) entry).pickAxeType) .append( "\n");
            } else if (entry instanceof Tools) {
                result.append(((Tools) entry).getName()).append("\n");
            }
        }
        return new Result(true, result.toString());
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
                if (((FishingPole) entry).fishingPoleType.getName().equals(name)){
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
        double random = Math.random();
        int x = (int) (random * currentWeather.getFishing() * (currentPlayer.getLevelFishing() + 2));
        int numberOfFish = Math.min(6, x);
        StringBuilder result = new StringBuilder("number of Fishes: " + numberOfFish + "\n");

        for (int i = 0; i < numberOfFish; i++) {

            //TODO بعد از هر if باید ماهی رو به inventory اضافه کنیم
            //TODO اضافه کردن مهارت ماهیگیری فراموش نشه
            double rand = Math.random();
            double quantity = (random * (currentPlayer.getLevelFishing() + 2) * fishingPole.fishingPoleType.getCoefficient()) / (7 - currentWeather.getFishing());
            Quantity fishQuantity = productQuantity(quantity);

            if (fishingPole.fishingPoleType.equals(FishingPoleType.TrainingRod)) {

                switch (currentDate.getSeason()) {
                    case Spring:
                        Fish springFish = new Fish(FishType.Herring, fishQuantity);
                        result.append(springFish.getFishType().getName()).append(springFish.getQuantity()).append("\n");
                    case Summer:
                        Fish summerFish = new Fish(FishType.Sunfish, fishQuantity);
                        result.append(summerFish.getFishType().getName()).append(summerFish.getQuantity()).append("\n");
                    case Fall:
                        Fish fallFish = new Fish(FishType.Sardine, fishQuantity);
                        result.append(fallFish.getFishType().getName()).append(fallFish.getQuantity()).append("\n");
                    case Winter:
                        Fish winterFish = new Fish(FishType.Perch, fishQuantity);
                        result.append(winterFish.getFishType().getName()).append(winterFish.getQuantity()).append("\n");
                    default:
                        break;
                }

            }

            else if (rand <= 0.2 || ( rand > 0.8 && rand <= 0.85 && currentPlayer.getLevelFishing()!=4) ){

                switch (currentDate.getSeason()) {
                    case Spring:
                        Fish springFish = new Fish(FishType.Flounder, fishQuantity);
                        result.append(springFish.getFishType().getName()).append(springFish.getQuantity()).append("\n");
                    case Summer:
                        Fish summerFish = new Fish(FishType.Tilapia, fishQuantity);
                        result.append(summerFish.getFishType().getName()).append(summerFish.getQuantity()).append("\n");
                    case Fall:
                        Fish fallFish = new Fish(FishType.Salmon, fishQuantity);
                        result.append(fallFish.getFishType().getName()).append(fallFish.getQuantity()).append("\n");
                    case Winter:
                        Fish winterFish = new Fish(FishType.Midnight_Carp, fishQuantity);
                        result.append(winterFish.getFishType().getName()).append(winterFish.getQuantity()).append("\n");
                    default:
                        break;
                }
            } else if (rand <= 0.4 || (rand > 0.85 && rand <= 0.9 && currentPlayer.getLevelFishing() != 4)) {

                switch (currentDate.getSeason()) {
                    case Spring:
                        Fish springFish = new Fish(FishType.Lionfish, fishQuantity);
                        result.append(springFish.getFishType().getName()).append(springFish.getQuantity()).append("\n");
                    case Summer:
                        Fish summerFish = new Fish(FishType.Dorado, fishQuantity);
                        result.append(summerFish.getFishType().getName()).append(summerFish.getQuantity()).append("\n");
                    case Fall:
                        Fish fallFish = new Fish(FishType.Sardine, fishQuantity);
                        result.append(fallFish.getFishType().getName()).append(fallFish.getQuantity()).append("\n");
                    case Winter:
                        Fish winterFish = new Fish(FishType.Squid, fishQuantity);
                        result.append(winterFish.getFishType().getName()).append(winterFish.getQuantity()).append("\n");
                    default:
                        break;
                }
            } else if (rand <= 0.6 || (rand > 0.9 && rand <= 0.95 && currentPlayer.getLevelFishing() != 4)) {

                switch (currentDate.getSeason()) {
                    case Spring:
                        Fish springFish = new Fish(FishType.Herring, fishQuantity);
                        result.append(springFish.getFishType().getName()).append(springFish.getQuantity()).append("\n");
                    case Summer:
                        Fish summerFish = new Fish(FishType.Sunfish, fishQuantity);
                        result.append(summerFish.getFishType().getName()).append(summerFish.getQuantity()).append("\n");
                    case Fall:
                        Fish fallFish = new Fish(FishType.Shad, fishQuantity);
                        result.append(fallFish.getFishType().getName()).append(fallFish.getQuantity()).append("\n");
                    case Winter:
                        Fish winterFish = new Fish(FishType.Tuna, fishQuantity);
                        result.append(winterFish.getFishType().getName()).append(winterFish.getQuantity()).append("\n");
                    default:
                        break;
                }

            }
            else if (rand <= 0.8 || (rand > 0.95 && currentPlayer.getLevelFishing() != 4)) {

                switch (currentDate.getSeason()) {
                    case Spring:
                        Fish springFish = new Fish(FishType.Ghostfish, fishQuantity);
                        result.append(springFish.getFishType().getName()).append(springFish.getQuantity()).append("\n");
                    case Summer:
                        Fish summerFish = new Fish(FishType.Rainbow_Trout, fishQuantity);
                        result.append(summerFish.getFishType().getName()).append(summerFish.getQuantity()).append("\n");
                    case Fall:
                        Fish fallFish = new Fish(FishType.Blue_Discus, fishQuantity);
                        result.append(fallFish.getFishType().getName()).append(fallFish.getQuantity()).append("\n");
                    case Winter:
                        Fish winterFish = new Fish(FishType.Perch, fishQuantity);
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
                            result.append(springFish.getFishType().getName()).append(springFish.getQuantity()).append("\n");
                        case Summer:
                            Fish summerFish= new Fish(FishType.Dorado,fishQuantity);
                            result.append(summerFish.getFishType().getName()).append(summerFish.getQuantity()).append("\n");
                        case Fall:
                            Fish fallFish= new Fish(FishType.Squid,fishQuantity);
                            result.append(fallFish.getFishType().getName()).append(fallFish.getQuantity()).append("\n");
                        case Winter:
                            Fish winterFish= new Fish(FishType.Tuna,fishQuantity);
                            result.append(winterFish.getFishType().getName()).append(winterFish.getQuantity()).append("\n");
                    }

                }
            }
        }

        //TODO اضافه کردن مهارت ماهیگیری

        return new Result(true, result.toString());
    }


    public Result Fishing(String fishingPoleType) {
        if (!checkCoordinateForFishing()) {
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

        return removeAnimal(animal);

    }

    public Result placeItem(String name, int direction) {
        Inventory inventory = currentPlayer.getBackPack().inventory;
        if (name.equals("Mystic Tree Seed")) {
            //TODO
        }
        if (name.equals("Grass Starter")){
           Items items=CraftingController.numberOfIngrediants(name);
           if (items == null) {
               return new Result(false , "Grass Starter not found");
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

        setTime(true);
        setWeather(true);
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
                            if (m.getReceiver().equals(currentPlayer) && !m.isSeen()) {
                                m.print();
                                m.setSeen(true);
                            }
                        }
                    }
                    System.out.println(GREEN+"Unseen Messages Displayed."+RESET);
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
    public void loadGame () {
        // TODO ذخیره جزییات بازی و لود بازی
        setTime(false);
        setWeather(false);
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

        for (Tile tile : bigMap)
            tile.getGameObject().startDayAutomaticTask();

        doWeatherTask();
        crowAttack(); // قبل محصول دادن درخت باید باشه
        checkForPlantProduct();
        // TODO بازیکنا برن خونشون , غش کردن
        // TODO محصول کاشته بشه و رشد محصولا یه روز بره بالاتر
        // TODO کانی تولید بشه شاپینگ بین خالی بشه و.  پول بیاد تو حساب فرد
    }
    public void AutomaticFunctionAfterOneTurn () {

        // محصول غول پیگر چک بشه

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




    private void setEnergyInMorning () {
        for (User user : players) {

            if (user.getHealth() > 0)
                user.setHealth(user.getMAX_HEALTH());
            else
                user.setHealth((user.getMAX_HEALTH()*3)/4);
        }
    }
    private void setTime (boolean gameIsNew) {

        if (gameIsNew)
            currentDate = new DateHour(Season.Spring, 1, 9, 1980);
        else {
            // TODO
        }
    }
    private void setWeather (boolean gameIsNew) {

        if (gameIsNew)
            currentWeather = Weather.Sunny;

        else {
            // TODO
        }
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

                if (object instanceof Tree)
                    ((Tree) object).setLastWater(currentDate);
                if (object instanceof GiantProduct)
                    ((GiantProduct) object).setLastWater(currentDate);
                if (object instanceof ForagingSeeds)
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

                        if (object instanceof Tree)
                            ((Tree) object).setLastFruit(currentDate);

                        if (object instanceof ForagingSeeds) {
                            if (((ForagingSeeds) object).getType().isOneTimeUse())
                                tile.setGameObject(new Walkable());
                            else
                                ((ForagingSeeds) object).setLastProduct(currentDate);
                        }
                        if (object instanceof GiantProduct) {
                            ArrayList<Tile> neighbors = ((GiantProduct) object).getNeighbors();
                            tile.setGameObject(new Walkable());

                            for (Tile tile1 : neighbors)
                                tile1.setGameObject(new Walkable());

                        }
                    }
                }
            }
        }
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
    private void checkForPlantProduct () {

    }
    private Result plantTree (TreeType type, int dir) {


        Inventory inventory=currentPlayer.getBackPack().inventory; // TODO

        for (Map.Entry<Items,Integer> entry: inventory.Items.entrySet())

            if (entry instanceof ForagingSeeds && ((ForagingSeeds) entry).getType().equals(type)) {
                if (inventory.Items.get(entry) > 0) {

                    inventory.Items.put(entry.getKey(), entry.getValue() - 1);
                    Tile tile = getTileByDir(dir);

                    if (tile.getGameObject() instanceof Walkable &&
                            ((Walkable) tile.getGameObject()).getGrassOrFiber().equals("Plowed")) {

                        tile.setGameObject(new ForagingSeeds(type, currentDate));

                    } else
                        return new Result(false, RED+"First, you must plow the tile."+RESET);
                }
                else
                    return new Result(false, RED + "You don't have this seed!" + RESET);
            }
        return new Result(false, RED + "You don't have this seed!" + RESET);
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

                    inventory.Items.put(entry.getKey(), entry.getValue() - 1);
                    Tile tile = getTileByDir(dir);

                    if (tile.getGameObject() instanceof Walkable &&
                            ((Walkable) tile.getGameObject()).getGrassOrFiber().equals("Plowed")) {

                        tile.setGameObject(new ForagingSeeds(type, currentDate));

                    } else
                        return new Result(false, RED+"First, you must plow the tile."+RESET);
                }
                else
                    return new Result(false, RED + "You don't have this seed!" + RESET);
            }
        return new Result(false, RED + "You don't have this seed!" + RESET);
    }
    private void checkForGiant () {

        for (int i = 0; i < 89 ; i++)
            for (int j = 0; j < 89 ; j++) {

                Tile tile1 = getTileByCoordinates(i, j);
                if (tile1.getGameObject() instanceof ForagingSeeds)
                    if (((ForagingSeeds) tile1.getGameObject()).getType().canGrowGiant()) {

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
    private void fertilizePlant (MarketItemType fertilizeType , Tile tile) {

        GameObject gameObject = tile.getGameObject();

        if (gameObject instanceof GiantProduct)
            ((GiantProduct) gameObject).setFertilize(fertilizeType);
        if (gameObject instanceof Tree)
            ((Tree) gameObject).setFertilize(fertilizeType);
        if (gameObject instanceof ForagingSeeds)
            ((ForagingSeeds) gameObject).setFertilize(fertilizeType);

    }
    private boolean checkTileForPlant (Tile tile) {

        GameObject object = tile.getGameObject();

        if (object instanceof Tree)
            return true;
        if (object instanceof GiantProduct)
            return true;

        return object instanceof ForagingSeeds;
    } // محصولای خودرو حساب نیستن
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

        if (!(currentPlayer.currentTool instanceof WateringCan))
            return new Result(false, RED+"Pick up your WateringCan"+RESET);

        if (tile.getGameObject() instanceof Lake || tile.getGameObject() instanceof WaterTank) {
            ((WateringCan) currentPlayer.currentTool).makeFullWater();
            return new Result(true, BLUE+"The tank is now full. Time to water" +
                    " those plants!\uD83D\uDEB0"+RESET);
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

                return new Result(true, BLUE + "You got 10 " + type.getProductType().getDisplayName() + RESET);
            } else
                return new Result(false, RED + "Still growing..." + RESET);
        }

        return new Result(false, RED+"There are no plant!"+RESET);
    }


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


    public Result showFruitInfo (String name) {

        TreesProductType type;

        try {
            type = TreesProductType.fromDisplayName(name);
            return new Result(true, TreesProductType.getInformation(type));

        } catch (Exception e) {

            CropsType cropType;
            try {
                cropType = CropsType.fromDisplayName(name);
                return new Result(false, CropsType.getInformation(cropType));

            } catch (Exception e1) {
                return new Result(false, "sorry, name is invalid!");
            }
        }
    }

    public Result buildGreenHouse () {

        for (Map.Entry <Items,Integer> entry: currentPlayer.getBackPack().inventory.Items.entrySet()) // این خیلی کیریه
            if (entry instanceof Wood)
                if (entry.getValue() < GreenHouse.requiredWood)
                    return new Result(false, RED+"You don't have enough wood!"+RESET);

        if (currentPlayer.getMoney() < GreenHouse.requiredCoins )
            return new Result(false, RED+"You don't have enough Coin!"+RESET);

        currentPlayer.increaseMoney(-1*GreenHouse.requiredCoins);

        Inventory inventory=currentPlayer.getBackPack().inventory;

        for (Map.Entry<Items,Integer> entry: inventory.Items.entrySet())
            if (entry instanceof Wood)
                inventory.Items.put(entry.getKey(), entry.getValue() - GreenHouse.requiredWood);

        currentPlayer.getFarm().getGreenHouse().setCreated(true);

        return new Result(true, BLUE+"The greenhouse has been built! \uD83C\uDF31"+RESET);
    }

    public Result useTools (String direction) {

        if (!currentPlayer.isHealthUnlimited())
            currentPlayer.increaseHealth(currentPlayer.currentTool.healthCost());

        if (checkDirection(direction)) {

        }
        int dir = Integer.parseInt(direction);

        return null; // TODO
    }

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

        return new Result(false, RED+"Hmm... that seed name doesn’t seem right!"+RESET);
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

    public Result thor (String x, String y) {

        int x1 = Integer.parseInt(x);
        int y1 = Integer.parseInt(y);

        if (!currentPlayer.getFarm().isInFarm(x1, y1))
            return new Result(false, RED+"Pick from your own farm!"+RESET);

        lightningStrike(getTileByCoordinates(x1, y1));
        return new Result(true, BLUE+"A lightning bolt hits!"+RESET);
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

    public Result howMuchWater () {

        Inventory inventory = currentPlayer.getBackPack().inventory;

        for (Map.Entry <Items,Integer> entry: inventory.Items.entrySet())
            if (entry instanceof WateringCan)
                return new Result(true, BLUE+"Water Remaining : "
                        +RESET+((WateringCan) entry).getReminderCapacity());

        return new Result(false, BLUE+"کدوم سطل سلطان"+RESET);
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
}