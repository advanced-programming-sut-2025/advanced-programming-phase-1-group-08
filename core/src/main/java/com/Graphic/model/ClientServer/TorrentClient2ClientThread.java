package com.Graphic.model.ClientServer;

import com.Graphic.Main;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class TorrentClient2ClientThread implements Runnable {

    //پس از اینکه در کلاس Client2ClientThread ارتباط برقرار شد میایم اینجا و بازیکن از اینجا پیام های بازیکن دیگر را دریافت و جواب می دهد

    private Socket socket;
    private BufferedInputStream in;
    private PrintWriter out;

    public TorrentClient2ClientThread(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedInputStream(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void run() {
        while (Main.getClient().isWorkingWithOtherClient()) {
            try {

            }
            catch (Exception e) {
                try {
                    socket.close();
                    out.close();
                } catch (IOException ex) {

                }
            }
        }
        try {
            socket.close();
            out.close();
        }
        catch (IOException ex) {

        }
    }
}
