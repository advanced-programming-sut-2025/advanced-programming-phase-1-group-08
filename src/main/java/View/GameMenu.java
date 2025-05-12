package View;

import Controller.GameController;
import Controller.TradeController;
import model.Enum.Commands.GameMenuCommands;
import model.Enum.Menu;

import java.util.Scanner;
import java.util.regex.Matcher;

import static Controller.HomeController.NotInHome;
import static model.App.*;
import static model.Color_Eraser.*;

public class GameMenu implements AppMenu {

    GameController controller = new GameController();
    Matcher matcher;

    @Override
    public void check(Scanner scanner) {

        String input = scanner.nextLine();


        controller.AutomaticFunctionAfterAnyAct();

        if (GameMenuCommands.makeNewGame.getMatcher(input) != null)
            controller.startNewGame(input);

        else if (GameMenuCommands.nextTurn.getMatcher(input) != null)
            controller.nextTurn();

        else if (GameMenuCommands.openHomeMenu.getMatcher(input) != null) {
            if (!NotInHome(currentUser))
                currentMenu = Menu.HomeMenu;
            else
                System.out.println(RED+"You're Not in Your Home!"+RESET);
        }

        else if (GameMenuCommands.friendships.getMatcher(input) != null)
            controller.DisplayFriendships();

        else if (GameMenuCommands.talking.getMatcher(input) != null)
            controller.talking(input);

        else if (GameMenuCommands.hug.getMatcher(input) != null)
            controller.hug(input);

        else if (GameMenuCommands.sendGift.getMatcher(input) != null)
            controller.sendGifts(input);

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
            System.out.println(CYAN+"Your Energy : "+currentPlayer.getHealth()+ RESET);

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

        else if ((matcher = GameMenuCommands.toolsUpgrade.getMatcher(input)) != null)
            System.out.println(controller.upgradeTool(matcher.group("name").trim()));

        else if ((matcher = GameMenuCommands.toolsUse.getMatcher(input)) != null)
            System.out.println(controller.useTools(matcher.group("name").trim()));

        else if ((matcher = GameMenuCommands.wateringPlant.getMatcher(input)) != null)
            System.out.println(controller.WateringPlant(matcher.group("direction").trim()));



        else if (input.matches("\\s*exit\\s*game\\s*"))
            controller.exitGame();
        else if (input.matches("\\s*force\\s*terminate\\s*"))
            controller.forceTerminate();


        else if((matcher=GameMenuCommands.walk.getMatcher(input)) != null)
            System.out.println(controller.walk(Integer.parseInt(matcher.group(1).trim()) , Integer.parseInt(matcher.group(2).trim()) ));

        else if((matcher=GameMenuCommands.inventoryShow.getMatcher(input)) != null)
            System.out.println(controller.showInventory());

        else if((matcher=GameMenuCommands.removeItem.getMatcher(input)) != null)
            System.out.println(controller.removeItemToTrashcan(matcher.group(1).trim(), null));

        else if ((matcher=GameMenuCommands.removeItemFlagn.getMatcher(input)) != null)
            System.out.println(controller.removeItemToTrashcan(matcher.group(1).trim(), matcher.group(2).trim()) );

        else if ((matcher=GameMenuCommands.currentTool.getMatcher(input)) != null)
            System.out.println(controller.showCurrentTool());

        else if ((matcher=GameMenuCommands.availableTool.getMatcher(input)) != null)
            System.out.println(controller.availableTools());

        else if ((matcher=GameMenuCommands.fishing.getMatcher(input)) != null)
            System.out.println(matcher.group(1).trim());

        else if ((matcher=GameMenuCommands.pet.getMatcher(input)) != null)
            System.out.println(controller.pet(matcher.group(1).trim()));

        else if ((matcher=GameMenuCommands.animals.getMatcher(input)) != null)
            System.out.println(controller.animals());

        else if ((matcher=GameMenuCommands.shepherdAnimals.getMatcher(input)) != null) {
            System.out.println(controller.shepherdAnimals(matcher.group(2).trim() , matcher.group(3).trim() , matcher.group(1).trim()));
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

        else if ((matcher=GameMenuCommands.sell.getMather(input)) != null)
            System.out.println(controller.sell(matcher.group("name").trim() , -1));

        else if ((matcher=GameMenuCommands.sellByCount.getMather(input)) != null)
            System.out.println(controller.sell(matcher.group("name").trim() , Integer.parseInt(matcher.group(2).trim()) ));

        else if ((matcher=GameMenuCommands.cheatSetFriendship.getMather(input)) != null)
            System.out.println(controller.cheatSetFriendship(matcher.group(1).trim() , Integer.parseInt(matcher.group(2).trim()) ));

        else if ((matcher=GameMenuCommands.addDollar.getMather(input)) != null)
            System.out.println(controller.addDollar(Integer.parseInt(matcher.group(1).trim())));

        //TODO چیت کد اضافه کردن آیتم.
        else
            System.out.println(RED+"Invalid Command, Try Again"+RESET);

    }
}
