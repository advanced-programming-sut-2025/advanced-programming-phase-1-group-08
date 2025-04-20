package View;

import Controller.GameController;
import model.Enum.Commands.GameMenuCommands;

import java.util.Scanner;

public class GameMenu implements AppMenu {


    GameController controller = new GameController();


    @Override
    public void check(Scanner scanner) {

        String input = scanner.nextLine();

        if (GameMenuCommands.showTime.getMather(input) != null)
            System.out.println(controller.showTime());

        else if (GameMenuCommands.showDate.getMather(input) != null)
            System.out.println(controller.showDate());

        else if (GameMenuCommands.showDateTime.getMather(input) != null)
            System.out.println(controller.showDateTime());

        else if (GameMenuCommands.showDayOfWeek.getMather(input) != null)
            System.out.println(controller.showDayOfWeek());

        else if (GameMenuCommand.advanceTime.getMather(input) != null) // TODO
            System.out.println(controller.increaseHour());

        else if (GameMenuCommand.advanceDate.getMather(input) != null)
            System.out.println(controller.increaseDate());


    }
}
