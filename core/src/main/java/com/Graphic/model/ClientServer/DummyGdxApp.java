package com.Graphic.model.ClientServer;

import com.badlogic.gdx.ApplicationListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static com.Graphic.model.ClientServer.MultiGameServer.SERVER_PORT;
import static com.Graphic.model.ClientServer.MultiGameServer.handleNewClient;

public class DummyGdxApp implements ApplicationListener {
    @Override
    public void create() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(SERVER_PORT);
        } catch (IOException e) {

        }
        System.out.println("Waiting for clients...");

        while (true) {
            Socket clientSocket = null;
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                break;
            }

            handleNewClient(clientSocket);
        }
    }

    @Override
    public void resize(int i, int i1) {
    }

    @Override
    public void render() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}
