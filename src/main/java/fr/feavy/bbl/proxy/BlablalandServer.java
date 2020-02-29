package fr.feavy.bbl.proxy;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BlablalandServer extends Thread{
    private final int port = 12301;
    private final ServerSocket server;

    public BlablalandServer() throws IOException {
        this.server = new ServerSocket(port);
    }

    @Override
    public void run() {
        super.run();
        while(true) {
            System.out.println("Blablaland server waiting...");
            try {
                Socket s = server.accept();
                System.out.println("New connexion");
                new BlablalandConnexionHandler(s).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
