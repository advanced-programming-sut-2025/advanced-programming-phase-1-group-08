package model.Enum;

import View.*;
import model.App;

import java.util.Scanner;

public enum Menu {

    RegisterMenu(new RegisterMenu()),
    ProfileMenu(new ProfileMenu()),
    AvatarMenu(new AvatarMenu()),
    MarketMenu(new MarketMenu()),
    LoginMenu(new LoginMenu()),
    ExitMenu(new ExitMenu()),
    MainMenu(new MainMenu()),
    HomeMenu(new HomeMenu()),
    GameMenu(new GameMenu());


    private final AppMenu menu;

    Menu(AppMenu menu) {
        this.menu = menu;
    }
    public void checkCommand(Scanner scanner) {
        this.menu.check(scanner);
    }
}
