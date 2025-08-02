package com.Graphic.model.ClientServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientWork implements Runnable {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public void connect(String ip, int port) {
        try {
            try {
                socket = new Socket(ip, port);
            } catch (IOException e) {

            }
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            System.out.println("Connected to server.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {
        while (true) {

        }
    }
}
