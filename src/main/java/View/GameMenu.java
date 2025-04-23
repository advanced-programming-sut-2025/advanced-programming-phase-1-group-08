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

        if (i++ == 0)
            controller.startNewGame();

        String input = scanner.nextLine();

        if (GameMenuCommands.showTime.getMather(input) != null)
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


        else
            System.out.println(RED+"Sorry pls try again");

    }
}
