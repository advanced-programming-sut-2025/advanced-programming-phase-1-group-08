package View;

import Controller.GameController;
import model.Enum.Commands.GameMenuCommands;
import model.Result;

import java.util.Scanner;
import java.util.regex.Matcher;

import static model.App.currentPlayer;
import static model.Color_Eraser.*;

public class GameMenu implements AppMenu {


    int i=0;


    GameController controller = new GameController();
    Matcher matcher;

    @Override
    public void check(Scanner scanner) {

        String input = scanner.nextLine();

        if (GameMenuCommands.makeNewGame.getMather(input) != null)
            controller.startNewGame(input);

        else if (GameMenuCommands.nextTurn.getMather(input) != null)
            controller.nextTurn();

        else if (GameMenuCommands.friendships.getMather(input) != null)
            controller.DisplayFriendships();

        else if (GameMenuCommands.talking.getMather(input) != null)
            controller.talking(input);

        else if (GameMenuCommands.hug.getMather(input) != null)
            controller.hug(input);

        else if (GameMenuCommands.giveFlower.getMather(input) != null)
            controller.giveFlowers(input);

        else if (GameMenuCommands.talkHistory.getMather(input) != null)
            controller.DisplayingTalkHistory(input);

        else if (GameMenuCommands.showTime.getMather(input) != null)
            System.out.println(controller.showTime());

        else if (GameMenuCommands.showDate.getMather(input) != null)
            System.out.println(controller.showDate());

        else if (GameMenuCommands.showDateTime.getMather(input) != null)
            System.out.println(controller.showDateTime());

        else if (GameMenuCommands.showDayOfWeek.getMather(input) != null)
            System.out.println(controller.showDayOfWeek());

        else if (GameMenuCommands.showSeason.getMather(input) != null)
            System.out.println(controller.showSeason());

        else if ((matcher = GameMenuCommands.advanceTime.getMather(input)) != null)
            System.out.println(controller.increaseHour(matcher.group("hour").trim()));

        else if ((matcher = GameMenuCommands.advanceDate.getMather(input)) != null)
            System.out.println(controller.increaseDate(matcher.group("date").trim()));

        else if (GameMenuCommands.showWeather.getMather(input) != null)
            System.out.println(controller.showWeather(true));

        else if (GameMenuCommands.showTomorrowWeather.getMather(input) != null)
            System.out.println(controller.showWeather(false));

        else if ((matcher = GameMenuCommands.setWeather.getMather(input)) != null)
            System.out.println(controller.setWeather(matcher.group("Weather").trim()));

        else if (GameMenuCommands.showEnergy.getMather(input) != null)
            System.out.println(CYAN+"Your Energy : "+currentPlayer.getHealth()+ RESET);

        else if ((matcher = GameMenuCommands.setEnergy.getMather(input)) != null)
            System.out.println(controller.setEnergy(matcher.group("amount").trim()));

        else if (GameMenuCommands.energyUnlimit.getMather(input) != null)
            System.out.println(controller.EnergyUnlimited());

        else if ((matcher = GameMenuCommands.showFruitInfo.getMather(input)) != null)
            System.out.println(controller.showFruitInfo(matcher.group("name").trim()));

        else if (GameMenuCommands.buildGreenHouse.getMather(input) != null)
            System.out.println(controller.buildGreenHouse());

        else if ((matcher = GameMenuCommands.planting.getMather(input)) != null)
            System.out.println(controller.planting(matcher.group("seed").trim(),
                    matcher.group("direction").trim()));

        else if (GameMenuCommands.howMuchWater.getMather(input) != null)
            System.out.println(controller.howMuchWater());

        else if (GameMenuCommands.createThor.getMather(input) != null)
            System.out.println(controller.thor(matcher.group("x").trim(), matcher.group("y").trim()));

        else if (GameMenuCommands.showPlant.getMather(input) != null)
            System.out.println(controller.());








        else if (input.matches("\\s*exit\\s*game\\s*"))
            controller.exitGame();
        else if (input.matches("\\s*force\\s*terminate\\s*"))
            controller.forceTerminate();
        else
            System.out.println(RED+"Invalid Command, Try Again"+RESET);

    }
}
