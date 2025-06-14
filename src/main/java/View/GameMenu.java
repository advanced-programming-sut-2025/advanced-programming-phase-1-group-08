package View;

import Controller.GameController;
import Controller.TradeController;
import model.Animall.Animal;
import model.Animall.BarnOrCage;
import model.Enum.Commands.GameMenuCommands;
import model.Enum.ItemType.BarnORCageType;
import model.Enum.Menu;
import model.Result;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;

import static Controller.HomeController.NotInHome;
import static model.App.*;
import static model.Color_Eraser.*;

public class GameMenu implements AppMenu {

    GameController controller = new GameController();
    Matcher matcher;

    @Override
    public void check(Scanner scanner) throws IOException {

        String input = scanner.nextLine();


        if (GameMenuCommands.makeNewGame.getMatcher(input) != null)
            controller.startNewGame(input);

        else if (GameMenuCommands.back.getMatcher(input) != null)
            System.out.println(controller.backToMainMenu().massage());

        else if ((matcher = GameMenuCommands.printMap.getMatcher(input)) != null) {
            System.out.println(controller.print(
                    Integer.parseInt(matcher.group(1).trim()),
                    Integer.parseInt(matcher.group(2).trim()),
                    Integer.parseInt(matcher.group(3).trim())));
        }

        else if (GameMenuCommands.nextTurn.getMatcher(input) != null)
            controller.nextTurn();

        else if (GameMenuCommands.openHomeMenu.getMatcher(input) != null)
            System.out.println(controller.goToHomeMenu() );

        else if (GameMenuCommands.eatFood.getMatcher(input) != null)
            controller.eatFood(input);

        else if (GameMenuCommands.recipeUnlock.getMatcher(input) != null)
            controller.unlockRecipe(input);

        else if (GameMenuCommands.friendships.getMatcher(input) != null)
            controller.DisplayFriendships();

        else if (GameMenuCommands.addXpCheat.getMatcher(input) != null)
            controller.cheatAddXp(input);

        else if (GameMenuCommands.talking.getMatcher(input) != null)
            controller.talking(input);

        else if (GameMenuCommands.hug.getMatcher(input) != null)
            controller.hug(input);

        else if (GameMenuCommands.sendGift.getMatcher(input) != null)
            controller.sendGifts(input);

//        else if (GameMenuCommands.giftList.getMatcher(input) != null)  پاکش نکنین!
//            System.out.println(controller.giftList().massage());

        else if (GameMenuCommands.trade.getMatcher(input) != null) {
            TradeController tradeController = new TradeController();
            tradeController.tradeStart();
        }

        else if (GameMenuCommands.propose.getMatcher(input) != null)
            controller.propose(input);

        else if (GameMenuCommands.giveFlower.getMatcher(input) != null)
            controller.giveFlowers(input);

        else if (GameMenuCommands.talkHistory.getMatcher(input) != null)
            controller.DisplayingTalkHistory(input);

        else if (GameMenuCommands.showTime.getMatcher(input) != null)
            System.out.println(controller.showTime());

        else if (GameMenuCommands.showDate.getMatcher(input) != null)
            System.out.println(controller.showDate());

        else if (GameMenuCommands.showDateTime.getMatcher(input) != null)
            System.out.println(controller.showDateTime());

        else if (GameMenuCommands.showDayOfWeek.getMatcher(input) != null)
            System.out.println(controller.showDayOfWeek());

        else if (GameMenuCommands.showSeason.getMatcher(input) != null)
            System.out.println(controller.showSeason());

        else if ((matcher = GameMenuCommands.advanceTime.getMatcher(input)) != null)
            System.out.println(controller.increaseHour(matcher.group("hour").trim()));

        else if ((matcher = GameMenuCommands.advanceDate.getMatcher(input)) != null)
            System.out.println(controller.increaseDate(matcher.group("date").trim()));

        else if (GameMenuCommands.showWeather.getMatcher(input) != null)
            System.out.println(controller.showWeather(true));

        else if (GameMenuCommands.showTomorrowWeather.getMatcher(input) != null)
            System.out.println(controller.showWeather(false));

        else if ((matcher = GameMenuCommands.setWeather.getMatcher(input)) != null)
            System.out.println(controller.setWeather(matcher.group("Weather").trim()));

        else if (GameMenuCommands.showEnergy.getMatcher(input) != null)
            System.out.println(controller.showEnergy());

        else if ((matcher = GameMenuCommands.setEnergy.getMatcher(input)) != null)
            System.out.println(controller.setEnergy(matcher.group("amount").trim()));

        else if (GameMenuCommands.energyUnlimit.getMatcher(input) != null)
            System.out.println(controller.EnergyUnlimited());

        else if ((matcher = GameMenuCommands.showFruitInfo.getMatcher(input)) != null)
            System.out.println(controller.showFruitInfo(matcher.group("name").trim()));

        else if (GameMenuCommands.buildGreenHouse.getMatcher(input) != null)
            System.out.println(controller.buildGreenHouse());

        else if ((matcher = GameMenuCommands.planting.getMatcher(input)) != null)
            System.out.println(controller.planting(matcher.group("seed").trim(),
                    matcher.group("direction").trim()));

        else if (GameMenuCommands.howMuchWater.getMatcher(input) != null)
            System.out.println(controller.howMuchWater());

        else if ((matcher = GameMenuCommands.createThor.getMatcher(input)) != null)
            System.out.println(controller.thor(matcher.group("x").trim(),
                    matcher.group("y").trim()));

        else if ((matcher = GameMenuCommands.showPlant.getMatcher(input)) != null)
            System.out.println(controller.showPlant(matcher.group("x").trim(),
                    matcher.group("y").trim()));

        else if ((matcher = GameMenuCommands.fertilize.getMatcher(input)) != null)
            System.out.println(controller.fertilize(matcher.group("fertilizer")
                    .trim(), matcher.group("direction").trim()));

        else if ((matcher = GameMenuCommands.showTreeInfo.getMatcher(input)) != null)
            System.out.println(controller.info(matcher.group("name").trim()));

        else if (GameMenuCommands.questsList.getMatcher(input) != null)
            System.out.println(controller.questsNPCList());

        else if (GameMenuCommands.friendshipNPCList.getMatcher(input) != null)
            System.out.println(controller.friendshipNPCList());

        else if ((matcher = GameMenuCommands.meetNPC.getMatcher(input)) != null)
            System.out.println(controller.meetNPC(matcher.group("name").trim()));

        else if ((matcher = GameMenuCommands.questsFinish.getMatcher(input)) != null)
            System.out.println(controller.doQuest(matcher.group("name").trim(),
                    matcher.group("index").trim()));

        else if ((matcher = GameMenuCommands.giftNPC.getMatcher(input)) != null)
            System.out.println(controller.giftNPC(matcher.group("name").trim(),
                    matcher.group("item").trim()));

        else if (GameMenuCommands.showTool.getMatcher(input) != null)
            System.out.println(controller.showCurrentTool());

        else if (GameMenuCommands.toolsAvailable.getMatcher(input) != null)
            System.out.println(controller.availableTools());

        else if ((matcher = GameMenuCommands.toolsEquip.getMatcher(input)) != null)
            System.out.println(controller.toolsEquip(matcher.group("name").trim()));


        else if ((matcher = GameMenuCommands.toolsUse.getMatcher(input)) != null)
            System.out.println(controller.useTools(matcher.group("direction").trim()));

        else if ((matcher = GameMenuCommands.wateringPlant.getMatcher(input)) != null)
            System.out.println(controller.WateringPlant(matcher.group("direction").trim()));


        else if (input.matches("\\s*show\\s*current\\s*menu\\s*"))
            System.out.println("Game Menu");
        else if (input.matches("\\s*menu\\s*exit")) {
            currentMenu = Menu.MainMenu;
            System.out.println(GREEN+"Done!"+RESET);
        }
        else if (input.matches("\\s*exit\\s*game\\s*"))
            controller.exitGame();
        else if (input.matches("\\s*force\\s*terminate\\s*"))
            controller.forceTerminate();


        else if((matcher=GameMenuCommands.walk.getMatcher(input)) != null)
            System.out.println(controller.walk(Integer.parseInt(matcher.group(1).trim())
                    , Integer.parseInt(matcher.group(2).trim()) ));

        else if((matcher=GameMenuCommands.inventoryShow.getMatcher(input)) != null)
            System.out.println(controller.showInventory());

        else if((matcher=GameMenuCommands.removeItem.getMatcher(input)) != null)
            System.out.println(controller.removeItemToTrashcan(matcher.group(1).trim(), null));

        else if ((matcher=GameMenuCommands.removeItemFlags.getMatcher(input)) != null)
            System.out.println(controller.removeItemToTrashcan(matcher.group(1).trim(), matcher.group(2).trim()) );

        else if ((matcher=GameMenuCommands.fishing.getMatcher(input)) != null)
            System.out.println(controller.Fishing(matcher.group(1).trim()));

        else if ((matcher=GameMenuCommands.pet.getMatcher(input)) != null)
            System.out.println(controller.pet(matcher.group(1).trim()));

        else if ((matcher=GameMenuCommands.animals.getMatcher(input)) != null)
            System.out.println(controller.animals());

        else if ((matcher=GameMenuCommands.shepherdAnimals.getMatcher(input)) != null) {
            System.out.println(controller.shepherdAnimals(matcher.group(2).trim()
                    , matcher.group(3).trim() , matcher.group(1).trim()));
        }

        else if ((matcher=GameMenuCommands.feedHay.getMatcher(input)) != null)
            System.out.println(controller.feedHay(matcher.group("name").trim()));

        else if ((matcher=GameMenuCommands.produces.getMatcher(input)) != null)
            System.out.println(controller.produces());

        else if ((matcher=GameMenuCommands.collectProduct.getMatcher(input)) != null)
            System.out.println(controller.getProductAnimals(matcher.group("name").trim()));

        else if ((matcher=GameMenuCommands.sellAnimal.getMatcher(input)) != null)
            System.out.println(controller.sellAnimal(matcher.group("name").trim()));

        else if ((matcher=GameMenuCommands.placeItem.getMatcher(input)) != null)
            System.out.println(controller.placeItem(matcher.group("name").trim() , matcher.group(2).trim()));

        else if ((matcher=GameMenuCommands.artisan.getMatcher(input)) != null)
            System.out.println(controller.ArtisanUse(matcher.group(1).trim() , matcher.group(2).trim() , null));

        else if ((matcher=GameMenuCommands.artisanUse.getMatcher(input)) != null)
            System.out.println(controller.ArtisanUse(matcher.group(1).trim() , matcher.group(2).trim() , matcher.group(3).trim() ));

        else if ((matcher=GameMenuCommands.artisanGet.getMatcher(input)) != null)
            System.out.println(controller.ArtisanGetProduct(matcher.group(1).trim()));

        else if ((matcher=GameMenuCommands.sellByCount.getMatcher(input)) != null)
            System.out.println(controller.sell(matcher.group("name").trim() , Integer.parseInt(matcher.group(2).trim()) ));

        else if ((matcher=GameMenuCommands.sell.getMatcher(input)) != null)
            System.out.println(controller.sell(matcher.group("name").trim() , -1));

        else if ((matcher=GameMenuCommands.cheatSetFriendship.getMatcher(input)) != null)
            System.out.println(controller.cheatSetFriendship(matcher.group(1).trim() , Integer.parseInt(matcher.group(2).trim()) ));

        else if ((matcher=GameMenuCommands.addDollar.getMatcher(input)) != null)
            System.out.println(controller.addDollar(Integer.parseInt(matcher.group(1).trim())));

        else if ((matcher=GameMenuCommands.setDollar.getMatcher(input)) != null)
            System.out.println(controller.setDollar(Integer.parseInt(matcher.group(1).trim())));

        else if ((matcher=GameMenuCommands.addItem.getMatcher(input)) != null)
            System.out.println(controller.addItem(matcher.group(1) , Integer.parseInt(matcher.group(2).trim())));

        else if ((matcher=GameMenuCommands.MarketMenu.getMatcher(input)) != null)
            System.out.println(controller.goToMarketMenu());

        else if (input.matches("\\s*plant\\s*"))
            controller.plantCreator();


//        else if (input.matches("\\s*exit\\s*game\\s*"))
//            controller.exitGame();
//        else if (input.matches("\\s*force\\s*terminate\\s*"))
//            controller.forceTerminate();


        else if ((matcher = GameMenuCommands.getGameObject.getMatcher(input)) != null)
            System.out.println(controller.getObject(matcher.group("dir").trim()));
        else if ((matcher = GameMenuCommands.getGameObject2.getMatcher(input)) != null)
            System.out.println(controller.getObject2(matcher.group("x").trim(), matcher.group("y").trim()));

        else if (input.matches("(i?)\\s*print\\s*"))
            System.out.println(controller.print(0, 0, 30));

        else if (input.matches("(i?)\\s*print\\s*all\\s*"))
            System.out.println(controller.print(0, 0, 90));

        else if ((matcher = GameMenuCommands.remove.getMatcher(input)) != null)
            controller.remove(Integer.parseInt(matcher.group(1)));

        else if (input.matches("(?i)\\s*add\\s*money\\s*"))
            currentGame.currentPlayer.increaseMoney(10000);

        else if (input.matches("\\s*clear\\s*"))
            controller.clear();

        //TODO چیت کد اضافه کردن آیتم.
        else
            System.out.println(RED+"Invalid Command, Try Again"+RESET);

        Result result = controller.AutomaticFunctionAfterAnyAct();

        System.out.println("-----------------");
        System.out.println("-> " + currentGame.currentPlayer.getNickname() + GREEN+"     " + currentGame.currentPlayer.getMoney() + "$"+RESET);
        System.out.println("(" + currentGame.currentPlayer.getPositionX()+ ", " + currentGame.currentPlayer.getPositionY() + ")");
        System.out.println("-----------------");

        if (!result.IsSuccess()) {
            System.out.println(result);
            controller.nextTurn();
        }
    }
}
