package Controller;

import model.DateHour;
import model.Enum.WeatherTime.Season;
import model.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.beans.PropertyEditorSupport;
import java.util.Date;

import static model.App.currentDate;
import static org.junit.jupiter.api.Assertions.*;

public class GameControllerTest {

    private GameController gameController = new GameController();

    @BeforeEach
    void setUp() {
        gameController = new GameController();
    }

    @Test
    void testShowCurrentTime () {

        currentDate = new DateHour(Season.Spring, 1, 8, 2000);
        Result result1 = new Result(true, "08");
        Result result2 = gameController.showTime();

    }


}
