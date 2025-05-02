package Controller;

import model.Result;
import model.SaveData.SessionManager;
import model.SaveData.UserDataBase;

public class MainController {

    public Result logoutRes () {
        SessionManager.logout();
        return new Result(true, "You Logged Out Successfully.");
    }

    public Result goToAvatar () {
        return new Result(true, "You Are Now in Avatar Menu.");
    }

    public Result goToGame () {
        return new Result(true, "You Are Now in Game Menu.");
    }

    public Result goToProfile () {
        return new Result(true, "You Are Now in Profile Menu.");
    }
}
