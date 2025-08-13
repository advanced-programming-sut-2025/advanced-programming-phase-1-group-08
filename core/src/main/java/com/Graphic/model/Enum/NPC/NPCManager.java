package com.Graphic.model.Enum.NPC;

import com.Graphic.Controller.MainGame.InputGameController;
import com.Graphic.Main;
import com.Graphic.model.Enum.Direction;
import com.Graphic.model.MapThings.Walkable;
import com.Graphic.model.MapThings.door;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.Random;

//import static com.Graphic.Controller.MainGame.GameControllerLogic.getTileByCoordinates;
import static com.Graphic.Controller.MainGame.GameControllerLogic.getTileByCoordinates;
import static com.Graphic.Controller.MainGame.GameControllerLogic.sortMap;
import static com.Graphic.model.App.currentGame;
import static com.Graphic.model.Enum.NPC.NPC.Sebastian;
import static com.Graphic.model.Enum.NPC.NPC.wallOrDoor;
import static com.Graphic.model.HelpersClass.TextureManager.TEXTURE_SIZE;

public class NPCManager {

    public static void NPCWalk(float delta) {
        for (NPC npc : NPC.values())
            updateAutoWalk(npc, delta);
    }

    public NPCManager() {

    }

    private static void updateAutoWalk(NPC npc, float delta) {


        getSprite(npc).setSize(25, 40);

        npc.setElapsedTime(npc.getElapsedTime() + delta);

        if (npc.getElapsedTime() >= npc.getWaitDuration()) {
            Direction[] directions = Direction.values();
            Direction newDir = directions[new Random().nextInt(directions.length)];
            npc.setDirection(newDir);

            // finding a walkable direction
            int attempts = 0;
            while (!checkWalking(npc) && attempts < 5) {
                newDir = directions[new Random().nextInt(directions.length)];
                npc.setDirection(newDir);
                attempts++;
            }


            for (int i = 0; i < npc.getMoveDistance(); i++) {
                if (checkWalking(npc)) {
                    float x = getSprite(npc).getX();
                    float y = getSprite(npc).getY();
                    float newX = x + npc.getDirection().getX() * 50 * Gdx.graphics.getDeltaTime();
                    float newY = y - npc.getDirection().getY() * 50 * Gdx.graphics.getDeltaTime();
                    getSprite(npc).setPosition(newX, newY);
//                    NPCMoveAnimation(npc);

                    npc.setMoving(true);
                    npc.setPositionX(npc.getPositionX() + newDir.getX() * 5 * Gdx.graphics.getDeltaTime());
                    npc.setPositionY(npc.getPositionY() + newDir.getY() * 5 * Gdx.graphics.getDeltaTime());
                } else {
                    npc.setTimer(0);
                    getSprite(npc).setRegion(npc.getAnimation().getKeyFrame(0));
                }
            }
//            getSprite(npc).setRegion(npc.getAnimation().getKeyFrame(0));
//            getSprite(npc).draw(Main.getBatch());


            npc.setElapsedTime(0f);
            npc.setWaitDuration(10 + new Random().nextFloat() * 3);
            npc.setMoveDistance(8 + new Random().nextInt(16));
        }
    }




    public static void NPCMoveAnimation (NPC npc) {
        getSprite(npc).setRegion(npc.getAnimation().getKeyFrame(npc.getTimer()));

        if (! npc.getAnimation().isAnimationFinished(npc.getTimer())) {
            npc.setTimer(npc.getTimer() + Gdx.graphics.getDeltaTime());
        }
        else {
            npc.setTimer(0);
        }

        npc.getAnimation().setPlayMode(Animation.PlayMode.LOOP);
    }

    public static void updateNPC(NPC npc) {

        if (Gdx.input.isKeyPressed(Input.Keys.F) ) {
            npc.setDirection(Direction.Up);
            if (checkWalking(npc)) {
                float x = getSprite(npc).getX();
                float y = getSprite(npc).getY();
                float newX = x + npc.getDirection().getX() * 50 * Gdx.graphics.getDeltaTime();
                float newY = y - npc.getDirection().getY() * 50 * Gdx.graphics.getDeltaTime();
                getSprite(npc).setPosition(newX, newY);
                NPCMoveAnimation(npc);

                npc.setPositionY(npc.getPositionY() - 5 * Gdx.graphics.getDeltaTime());
            } else {
                npc.setTimer(0);
                getSprite(npc).setRegion(npc.getAnimation().getKeyFrame(0));
            }
            npc.setMoving(true);
            NPCMoveAnimation(npc);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.V) ) {
            npc.setDirection(Direction.Down);
            if (checkWalking(npc)) {
                float x = getSprite(npc).getX();
                float y = getSprite(npc).getY();
                float newX = x + npc.getDirection().getX() * 50 * Gdx.graphics.getDeltaTime();
                float newY = y - npc.getDirection().getY() * 50 * Gdx.graphics.getDeltaTime();
                getSprite(npc).setPosition(newX, newY);
                NPCMoveAnimation(npc);

                npc.setPositionY(npc.getPositionY() + 5 * Gdx.graphics.getDeltaTime());
            } else {
                npc.setTimer(0);
                getSprite(npc).setRegion(npc.getAnimation().getKeyFrame(0));
            }
            npc.setMoving(true);
            NPCMoveAnimation(npc);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.C) ) {
            npc.setDirection(Direction.Left);
            if (checkWalking(npc)) {
                float x = getSprite(npc).getX();
                float y = getSprite(npc).getY();
                float newX = x + npc.getDirection().getX() * 50 * Gdx.graphics.getDeltaTime();
                float newY = y - npc.getDirection().getY() * 50 * Gdx.graphics.getDeltaTime();
                getSprite(npc).setPosition(newX, newY);
                NPCMoveAnimation(npc);

                npc.setPositionX(npc.getPositionX() - 5 * Gdx.graphics.getDeltaTime());
            } else {
                npc.setTimer(0);
                getSprite(npc).setRegion(npc.getAnimation().getKeyFrame(0));
            }
            npc.setMoving(true);
            NPCMoveAnimation(npc);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.B) ) {
            npc.setDirection(Direction.Right);
            if (checkWalking(npc)) {
                float x = getSprite(npc).getX();
                float y = getSprite(npc).getY();
                float newX = x + npc.getDirection().getX() * 50 * Gdx.graphics.getDeltaTime();
                float newY = y - npc.getDirection().getY() * 50 * Gdx.graphics.getDeltaTime();
                getSprite(npc).setPosition(newX, newY);
                NPCMoveAnimation(npc);

                npc.setPositionX(npc.getPositionX() + 5 * Gdx.graphics.getDeltaTime());
            } else {
                npc.setTimer(0);
                getSprite(npc).setRegion(npc.getAnimation().getKeyFrame(0));
            }
            npc.setMoving(true);
            NPCMoveAnimation(npc);
        }

        else {
            npc.setMoving(false);
            npc.setTimer(0);
            getSprite(npc).setRegion(npc.getAnimation().getKeyFrame(0));

        }

        getSprite(npc).draw(Main.getBatch());
    }

    private static Sprite getSprite(NPC npc) {
        return npc.getSprite();
    }

    private static boolean checkWalking(NPC npc) {
        try {

//            int x = (int) (npc.getPositionX() + npc.getDirection().getX() *
//                5 * Gdx.graphics.getDeltaTime());
//            int y = (int) (npc.getPositionY() + npc.getDirection().getY() *
//                5 * Gdx.graphics.getDeltaTime());
//
//
//            if ((getTileByCoordinates(x, 90-y , Main.getClient().getLocalGameState()).getGameObject()) instanceof Walkable) {
//                return true;
//            }
            return true;
        }
        catch (Exception e) {
            return false;
        }

//        return false;
    }

}
