package com.Graphic;


import com.Graphic.View.LoginMenu;
import com.Graphic.model.ClientServer.ClientWork;
import com.Graphic.model.Enum.Menu;
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
    public static Skin newSkin;
    private static ClientWork client;

    static {
        System.setProperty("java.net.preferIPv4Stack", "true");
        System.setProperty("kryonet.log", "DEBUG");
    }

    public Main(ClientWork client) {
        Main.client = client;
    }

    @Override
    public void create() {

        main = this;
        batch = new SpriteBatch();

        skin = new Skin(Gdx.files.internal("Skin/craftacular-ui.json"));
        newSkin = new Skin(Gdx.files.internal("Mohamadreza/newSkin.json"));
        //client.startListening();
        client.setCurrentMenu(Menu.LoginMenu);
        main.setScreen(new LoginMenu());

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
    public static Skin getSkin() {
        return skin;
    }
    public static Main getMain() {
        return main;
    }
    public static void setMain(Main main) {
        Main.main = main;
    }

    public static synchronized ClientWork getClient() {
        return client;
    }
    public static Skin getNewSkin() {
        if (newSkin == null) {
            newSkin = new Skin(Gdx.files.internal("Mohamadreza/newSkin.json"));
        }
        return newSkin;
    }
}
