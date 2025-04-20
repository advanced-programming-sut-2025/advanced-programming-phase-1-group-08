package View;

import Controller.GameController;
import model.Enum.Commands.GameMenuCommand;

import java.util.Scanner;

public class GameMenu implements AppMenu {


    GameController controller = new GameController();


    @Override
    public void check(Scanner scanner) {

        String input = scanner.nextLine();

        if (GameMenuCommand.showTime.getMather(input) != null)
            System.out.println(controller.showTime());

        else if (GameMenuCommand.showDate.getMather(input) != null)
            System.out.println(controller.showDate());

        else if (GameMenuCommand.showDateTime.getMather(input) != null)
            System.out.println(controller.showDateTime());

        else if (GameMenuCommand.showDayOfWeek.getMather(input) != null)
            System.out.println(controller.showDayOfWeek());

        else if (GameMenuCommand.advanceTime.getMather(input) != null) // TODO
            System.out.println(controller.increaseHour());

        else if (GameMenuCommand.advanceDate.getMather(input) != null)
            System.out.println(controller.increaseDate());


    }
}
