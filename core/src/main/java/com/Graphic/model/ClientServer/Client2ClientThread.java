package com.Graphic.model.ClientServer;

import com.Graphic.Main;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Client2ClientThread extends Thread {

    private final ServerSocket serverSocket;

    public Client2ClientThread(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    public void handleConnection(Socket socket) throws Exception {
        try {
            new TorrentClient2ClientThread(socket);
        }
        catch (IOException ex) {

        }
    }



    @Override
    public void run() {
        while (Main.getClient(null).isExit()) {
            try {
                Socket socket = serverSocket.accept();
                handleConnection(socket);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
