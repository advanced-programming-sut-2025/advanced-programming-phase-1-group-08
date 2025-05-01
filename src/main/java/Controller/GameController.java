package Controller;

import model.*;
import model.Enum.AllPlants.CropsType;
import model.Enum.AllPlants.TreeType;
import model.Enum.AllPlants.TreesProductType;
import model.Enum.Door;
import model.Enum.WeatherTime.Season;
import model.Enum.ItemType.WallType;
import model.Enum.WeatherTime.Weather;
import model.Places.*;
import model.Plants.*;
import model.ToolsPackage.*;

import java.util.*;

import static model.App.*;
import static model.App.tomorrowWeather;
import static model.Color_Eraser.*;

public class GameController {

    public Tile getTileByCoordinates(int x, int y) {
        for (Tile tile:bigMap){
            if (tile.getX() == x && tile.getY() == y){
                return tile;
            }
        }
        return null;
    }

    public void creatInitialMine(int id , int x , int y){
        Farm farm= currentPlayer.getFarm();
        if (id==1){
            Mine mine = new Mine();
            mine.setCharactor('M');
            for (int i=23;i<29;i++){
                for (int j=2;j<8;j++) {
                    Tile tile = new Tile(i + 60*x, j + 60*y, mine);
                    farm.Farm.add(tile);
                    bigMap.add(tile);
                }
            }
            farm.setMine(mine);
        }
        else if (id==2){

        }
    }

    public void creatInitialLake(int id , int x , int y){
        Farm farm= currentPlayer.getFarm();
        if (id==1){
            Lake lake = new Lake();
            lake.setCharactor('L');
            for (int i=2;i<7;i++){
                for (int j=23;j<29;j++) {
                    Tile tile = new Tile(i +60*x, j+60*y, lake);
                    farm.Farm.add(tile);
                    bigMap.add(tile);
                }
            }
            farm.setLake(lake);
        }
        else if (id==2){

        }
    }

    public void creatInitialHouse(int id , int x , int y){
        Farm farm= currentPlayer.getFarm();
        Wall wall = new Wall();
        wall.setWallType(WallType.House);
        wall.setCharactor('#');
        door houseDoor=new door();
        houseDoor.setDoor(Door.House);
        houseDoor.setCharactor('D');

        for (int i=12;i<19;i++){
            for (int j=0;j<7;j++) {
                if (i==12 || i==18){
                    Tile tile = new Tile(i + 60*x, j + 60*y, wall);
                    farm.Farm.add(tile);
                    bigMap.add(tile);
                }
                else if (i==15 && j==6){
                    Tile tile = new Tile(i + 60*x, j + 60*y, houseDoor);
                    farm.Farm.add(tile);
                    bigMap.add(tile);
                }
                else {
                    if (j==0 || j==6){
                        Tile tile = new Tile(i + 60*x, j + 60*y, wall);
                        farm.Farm.add(tile);
                        bigMap.add(tile);
                    }
                }
            }
        }


        Fridge fridge=new Fridge(18 + 60*x,6 + 60*y);
        fridge.setCharactor('F');
        Home home=new Home(13 +60*x,1 + 60*y,fridge);
        home.setCharactor('H');
        home.houseDoor=houseDoor;

        for (int i=13 ; i<18 ; i++){
            for (int j=1 ; j<6 ; j++) {
                if (i==18 && j==5){
                    Tile tile = new Tile(i + 60*x, j +60*y, fridge);
                    farm.Farm.add(tile);
                    bigMap.add(tile);
                }
                else {
                    Tile tile = new Tile(i + 60*x, j + 60*y, home);
                    farm.Farm.add(tile);
                    bigMap.add(tile);
                }
            }
        }
        farm.setHome(home);
    }

    public void creatInitialGreenHouse(int id , int x , int y){
        Farm farm= currentPlayer.getFarm();
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
        waterTank.setWaterTank('W');
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

    public Farm creatInitialFarm(int id){
        long seed=System.currentTimeMillis();
        Farm farm= currentPlayer.getFarm();

        for (int i=0 ; i<30 ;i++){
            for (int j=0 ; j<30 ; j++) {
                if (i>=23 && i<29 && j>=2 && j<8){
                    creatInitialMine(id,currentPlayer.topLeftX,currentPlayer.topLeftY);
                }
                else if(1<i && i<7 && j>22 && j<29){
                    creatInitialLake(id,currentPlayer.topLeftX,currentPlayer.topLeftY);
                }
                else if(i>=12 && i<19 && j<7){
                    creatInitialHouse(id,currentPlayer.topLeftX,currentPlayer.topLeftY);
                }
                else if (i<8 && j<9){
                    creatInitialGreenHouse(id,currentPlayer.topLeftX,currentPlayer.topLeftY);
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

    public Result walk (int goalX, int goalY){
         int startX=currentPlayer.getPositionX();
         int startY=currentPlayer.getPositionY();
         Tile endTile=getTileByCoordinates(goalX, goalY);

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
        if (tile.getGameObject() instanceof Home || tile.getGameObject() instanceof door
                || tile.getGameObject() instanceof Walkable || tile.getGameObject() instanceof GreenHouse) {

            return true;
        }
        return false;
    }

    public Result checkConditionsForWalk(int goalX, int goalY){
        Tile tile = getTileByCoordinates(goalX, goalY);
        Farm farm = null;
        for (Farm farms : farms) {
            if (farms.Farm.contains(tile)) {
                farm = farms;
                break;
            }
        }
        for (User user : players) {
            if (user.getFarm().equals(farm)){
                if (!user.getMarried().equals(currentPlayer) && !user.equals(currentPlayer)){
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
                result += ((Hoe) entry).getName() +" "+ ((Hoe) entry).hoeType + "\n";
            }
            else if (entry instanceof PiⅽkAxe){
                result += ((PiⅽkAxe) entry).getName() +" "+ ((PiⅽkAxe) entry).pickAxeType +"\n";
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
        for (Map.Entry<Items,Integer> entry: currentPlayer.getBaⅽkPaⅽk().inventory.Items.entrySet()) {
            if (entry instanceof TrashCan){
                percent= ((TrashCan) entry).Type.getPercent();
                break;
            }
        }
        if (amount ==null || amount == reminder) {
            int increase=(reminder * percent *price)/100;
            TrashCan.removeItem(increase,currentPlayer.getBaⅽkPaⅽk().inventory.Items, items, reminder);
            return new Result(true,name + "completely removed from your inventory");
        }
        if (amount > reminder) {
            return new Result(false,"not enough "+name+" "+"in your inventory for remove");
        }
        if (amount < reminder) {
            int increase=(reminder * percent *price)/100;
            TrashCan.removeItem(increase,currentPlayer.getBaⅽkPaⅽk().inventory.Items, items, reminder);
            return new Result(true , amount + " "+name+" "+"removed from your inventory");
        }

        return null;
    }



    public Result removeItem (String name, Integer number){
        Inventory inventory=currentPlayer.getBaⅽkPaⅽk().inventory;
        for (Map.Entry<Items,Integer> entry: inventory.Items.entrySet()){

            if (entry instanceof Wood){
                if (name.equals(Wood.name)) {
                    return increaseMoney(entry.getValue(), Wood.price, (Wood) entry.getKey(), name, number);
                }
            }
            if (entry instanceof BasicRock){
                if (name.equals("BasicRock")) {
                    return increaseMoney(entry.getValue(), BasicRock.price, (BasicRock) entry.getKey(), name, number);
                }
            }

            if (entry instanceof ForagingMinerals){
                if (((ForagingMinerals) entry).getType().getDisplayName().equals(name)){
                     return increaseMoney(entry.getValue(),( (ForagingMinerals) entry).getType().getPrice(),(ForagingMinerals) entry, name,entry.getValue());
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
                    return increaseMoney(entry.getValue(),( (AllCrops) entry).getType().getPrice(),(AllCrops) entry, name,entry.getValue());
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
        Inventory inventory=currentPlayer.getBaⅽkPaⅽk().inventory;

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
                if (((Hoe) entry).hoeType.equals(name)){
                    currentPlayer.currentTool=(Tools) entry;
                    return new Result(true,"now current tool is "+name);
                }
            }
            else if (entry instanceof PiⅽkAxe){
                if (((PiⅽkAxe) entry).pickAxeType.equals(name)){
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
            case Hoe hoe -> new Result(true, "current tool: " + hoe.hoeType);
            case WateringCan wateringCan -> new Result(true, "current tool: " + wateringCan.wateringCanType);
            case PiⅽkAxe piⅽkAxe -> new Result(true, "current tool: " + piⅽkAxe.pickAxeType);
            default -> new Result(true, "current tool: " + currentTool.getName());
        };
    }

    public Result availableTools(){
        Inventory inventory=currentPlayer.getBaⅽkPaⅽk().inventory;
        String result="";
        for (Map.Entry<Items,Integer> entry: inventory.Items.entrySet()){
            if (entry instanceof Axe){
                result+=((Axe) entry).axeType+"\n";
            }
            else if (entry instanceof FishingPole){
                result+=((FishingPole) entry).fishingPoleType+"\n";
            }
            else if (entry instanceof Hoe){
                result+=((Hoe) entry).hoeType+"\n";
            }
            else if (entry instanceof WateringCan){
                result+=((WateringCan) entry).wateringCanType+"\n";
            }
            else if (entry instanceof PiⅽkAxe){
                result+=((PiⅽkAxe) entry).pickAxeType+"\n";
            }
            else if (entry instanceof Tools){
                result+=((Tools) entry).getName() +"\n";
            }
        }
        return new Result(true,result);
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
        Inventory inventory=currentPlayer.getBaⅽkPaⅽk().inventory;
        for (Map.Entry<Items,Integer> entry: inventory.Items.entrySet()){
            if (entry instanceof FishingPole){
                if (((FishingPole) entry).fishingPoleType.getName().equals(name)){
                    return (FishingPole) entry;
                }
            }
        }
        return null;
    }

    public void FishingInSpring(FishingPole fishingPole){
        double random=Math.random();

    }

    public Result Fishing(String fishingPoleType){
        if (!checkCoordinateForFishing()){
            return new Result(false, "you can't fishing because lake is not around you" );
        }
        if (isFishingPoleTypeExist(fishingPoleType)==null){
            return new Result(false, "No such fishing pole exist!" );
        }



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

    }
    private boolean checkForDeath () {

        return (currentPlayer.getHealth() <= 0 && !currentPlayer.isHealthUnlimited());
    }

    public void startNewGame () {

        setTime(true);
        setWeather(true);
    }
    public void loadGame () {
        setTime(false);
        setWeather(false);
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
        passedOfTime(0,(24 -currentDate.getHour()) + 9);
        setEnergyInMorning();

        for (Tile tile : bigMap)
            tile.getGameObject().startDayAutomaticTask();

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
            tile.getGameObject().startDayAutomaticTask();
    }
    public void AutomaticFunctionAfterAnyAct () {

        for (User user : players)
            user.checkHealth();

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

        for (Map.Entry <Items,Integer> entry: currentPlayer.getBaⅽkPaⅽk().inventory.Items.entrySet()) // این خیلی کیریه
            if (entry instanceof Wood)
                if (entry.getValue() < GreenHouse.requiredWood)
                    return new Result(false, RED+"You don't have enough wood!"+RESET);

        if (currentPlayer.getMoney() < GreenHouse.requiredCoins )
            return new Result(false, RED+"You don't have enough Coin!"+RESET);

        currentPlayer.increaseMoney(-1*GreenHouse.requiredCoins);
        removeItem(Wood.name, GreenHouse.requiredWood);
        currentPlayer.getFarm().getGreenHouse().setCreated(true);

        return new Result(true, BLUE+"The greenhouse has been built! \uD83C\uDF31"+RESET);
    }


}