package com.Graphic;

import com.Graphic.Controller.MainGame.InputGameController;
import com.Graphic.View.GameMenus.GameMenu;
import com.Graphic.View.GameMenus.MarketMenu;
import com.Graphic.View.GameMenus.TransitionScreen;
import com.Graphic.View.LoginMenu;
import com.Graphic.View.MainMenu;
import com.Graphic.View.PlayGameMenu;
import com.Graphic.View.ProfileMenu;
import com.Graphic.model.App;
import com.Graphic.model.ClientServer.Client;
import com.Graphic.model.ClientServer.ClientWork;
import com.Graphic.model.Enum.Menu;
import com.Graphic.model.HelpersClass.SFXManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

//import static com.Graphic.model.ClientServer.Client.SERVER_IP;
//import static com.Graphic.model.ClientServer.MultiGameServer.SERVER_PORT;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

    private static Main main;
    private static Batch batch;
    private static Skin skin;
    private static ClientWork client;

    public Main(ClientWork client) {
        System.out.println("Main Constructor");
        Main.client = client;
        if (client == null) {
            System.out.println("Client is null");
        }
    }
    //private static InputGameController x;

    @Override
    public void create() {

        main = this;
        batch = new SpriteBatch();
        //InputGameController x = InputGameController.getInstance();
        //x.startNewGame("a");


//        Main.getMain().setScreen(
//            new TransitionScreen(Main.getMain(),
//                this,
//                new HomeMenu(),
//                1f
//            )
//        );
        skin = new Skin(Gdx.files.internal("Skin/craftacular-ui.json"));
        client.initFromArgs("127.0.0.1" , 8080);
        try {
            client.startWorkWithServer();
        } catch (Exception e) {

        }
        //client.startListening();
        main.setScreen(new LoginMenu());
        //main.setScreen(new PlayGameMenu());
//        skin = new Skin(Gdx.files.internal("Skin/craftacular-ui.json"));
//        main.setScreen(new ProfileMenu());

    }

    public void render() {
        super.render();
    }
    public void dispose() {
        batch.dispose();
        skin.dispose();
    }
    public static Batch getBatch() {
        return batch;
    }
    public static Skin getSkin() {return skin;}
    public static Main getMain() {
        return main;
    }
    public static void setMain(Main main) {
        Main.main = main;
    }

    public static synchronized ClientWork getClient(ClientWork c) {
        return client;
    }
}
