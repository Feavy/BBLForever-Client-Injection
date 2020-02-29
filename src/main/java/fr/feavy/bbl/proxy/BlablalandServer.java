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
            try {
                Socket s = server.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
