package com.Graphic.model.ClientServer;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.Graphic.Main;
import com.Graphic.model.User;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class KryoNetClient {
    private Client client;
    private String host = "127.0.0.1";
    private int port = 8080;
    private int timeoutMs = 5000;
    private volatile boolean connected = false;

    // optional callback that your UI/game can set to handle incoming Message objects
    private Consumer<Message> messageHandler;

    // small executor so connect() call (blocking) را بتوانیم خارج از UI thread اجرا کنیم در صورت نیاز
    private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
        Thread t = new Thread(r, "KryoNetClient-Connector");
        t.setDaemon(true);
        return t;
    });

    public KryoNetClient() {
        // default ctor
    }

    /**
     * مقداردهی اولیه مشخصات اتصال.
     * Main.create() تو کدت این رو صدا می‌زنه.
     */
    public void initFromArgs(String host, int port) {
        if (host != null && !host.isEmpty()) this.host = host;
        this.port = port;
    }

    /**
     * شروع کار با سرور.
     * این متد تلاش می‌کند به سرور وصل شود و listener را ثبت می‌کند.
     *
     * @throws IOException در صورت عدم توانایی در اتصال
     */
    public void startWorkWithServer() throws IOException {
        if (client != null && client.isConnected()) return; // already started

        client = new Client();
        // ثبت کلاس‌ها قبل از start
        Network.register(client);
        client.start();

        client.addListener(new Listener() {
            @Override
            public void connected(Connection connection) {
                connected = true;
                System.out.println("KryoNetClient connected. id=" + connection.getID());
            }

            @Override
            public void disconnected(Connection connection) {
                connected = false;
                System.out.println("KryoNetClient disconnected.");
            }

            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof Message) {
                    Message msg = (Message) object;
                    // اگر هِندلر ثبت شده، تحویل بده، در غیر این صورت لاگ بزن
                    if (messageHandler != null) {
                        try {
                            messageHandler.accept(msg);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        // default handling (موقت) — فقط لاگ
                        System.out.println("Received message: " + msg.commandType);
                    }
                }
            }
        });

        // connect می‌تواند بلاک‌کننده باشد؛ ما اینجا همان‌جا سعی می‌کنیم متصل شویم و IOException را پرتاب می‌کنیم.
        // اگر می‌خواهی اتصال در پس‌زمینه انجام شود می‌توانی این بلوک را با executor.submit اجرا کنی.
        try {
            // استفاده از همان پورت برای TCP و UDP (اگر می‌خواهی UDP متفاوت باشد، اینجا تغییر بده)
            client.connect(timeoutMs, host, port, port);
            System.out.println("Connected to " + host + ":" + port);
        } catch (IOException e) {
            // cleanup
            client.stop();
            client = null;
            System.out.println("Failed to connect to server: " + e.getMessage());
            throw e;
        }
    }

    /**
     * اگر ترجیح میدی اتصال را غیرقابل‌مسدود انجام دهی (مثلاً در یک ترد جدا) از این متد استفاده کن.
     * این متد خطا را پرتاب نمی‌کند بلکه آن را لاگ می‌کند.
     */
    public void startWorkWithServerAsync() {
        executor.submit(() -> {
            try {
                startWorkWithServer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public boolean isConnected() {
        return client != null && client.isConnected() && connected;
    }

    public void send(Message msg) {
        if (client != null && client.isConnected()) {
            client.sendTCP(msg);
        } else {
            System.err.println("Can't send message - not connected.");
        }
    }

    public void stop() {
        if (client != null) {
            client.stop();
            client = null;
        }
        executor.shutdownNow();
    }


    public void setMessageHandler(Consumer<Message> handler) {
        this.messageHandler = handler;
    }

    /**
     * تنظیم کردن timeout اتصال (میلی‌ثانیه) قبل از start
     */
    public void setTimeoutMs(int timeoutMs) {
        this.timeoutMs = timeoutMs;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
