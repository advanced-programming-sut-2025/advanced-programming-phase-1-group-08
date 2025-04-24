package Controller;

import model.Result;

public class MainController {

    public Result logoutRes () {
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
