package com.Graphic.model.Enum;

import com.Graphic.View.*;
import com.Graphic.View.GameMenus.CraftMenu;
import com.Graphic.View.GameMenus.HomeMenu;
import com.Graphic.View.GameMenus.MarketMenu;

import java.io.IOException;
import java.util.Scanner;

public enum Menu {

    RegisterMenu(new RegisterMenu()),
    ProfileMenu(new ProfileMenu()),
    AvatarMenu(new AvatarMenu()),
    LoginMenu(new LoginMenu()),
    ExitMenu(new ExitMenu()),
    MainMenu(new MainMenu()),
    HomeMenu(new HomeMenu()),
    CraftMenu(new CraftMenu());


    private final AppMenu menu;

    Menu(AppMenu menu) {
        this.menu = menu;
    }
    public void checkCommand(Scanner scanner) throws IOException {
        this.menu.check(scanner);
    }
}
