package com.Graphic.model.Enum;

import com.Graphic.View.*;
import com.Graphic.View.GameMenus.CraftMenu;
import com.Graphic.View.GameMenus.GameMenu;
import com.Graphic.View.GameMenus.HomeMenu;
import com.Graphic.View.GameMenus.MarketMenu;
import com.badlogic.gdx.Screen;

import java.io.IOException;
import java.util.Scanner;

public enum Menu {

    RegisterMenu(new RegisterMenu() , new RegisterMenu()),
    ProfileMenu(new ProfileMenu() , new ProfileMenu()),
    AvatarMenu(new AvatarMenu() , new AvatarMenu()),
    LoginMenu(new LoginMenu("", "") , new LoginMenu("", "")), // TODO   اینا باید درست بشع
    ExitMenu(new ExitMenu() , new ExitMenu()),
    MainMenu(new MainMenu() , new MainMenu()),
    HomeMenu(new HomeMenu() , new HomeMenu()),
    GameMenu(com.Graphic.View.GameMenus.GameMenu.gameMenu , com.Graphic.View.GameMenus.GameMenu.gameMenu),
    MarketMenu(com.Graphic.View.GameMenus.MarketMenu.getInstance() , com.Graphic.View.GameMenus.MarketMenu.getInstance() ),
    CraftMenu(new CraftMenu() , new CraftMenu()),
    PlayGameMenu(new PlayGameMenu() , new PlayGameMenu());


    private final AppMenu menu;
    private final Screen screen;

    public AppMenu getMenu() {
        return menu;
    }
    public Screen getScreen() {
        return screen;
    }

    Menu(AppMenu menu , Screen screen) {
        this.menu = menu;
        this.screen = screen;
    }

    public void checkCommand(Scanner scanner) throws IOException {
        //this.menu.check(scanner);
    }
}
