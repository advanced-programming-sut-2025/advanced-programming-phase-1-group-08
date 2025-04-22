package org.example;

import Controller.GameController;
import View.AppView;
import model.App;
import model.User;

public class Main {

    public static void main(String[] args)  {
        User user=new User("j","ds","jfk","msjk",1,0);
        App.currentPlayer=user;
        GameController gameController=new GameController();
        gameController.creatInitialFarm(1);
        gameController.print(user.getFarm());
        (new AppView()).run();
    }
}