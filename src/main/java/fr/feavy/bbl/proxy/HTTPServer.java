package fr.feavy.bbl.proxy;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HTTPServer extends Thread {
    private final ServerSocket server;

    public HTTPServer() throws IOException {
        this.server = new ServerSocket(80);
    }

    @Override
    public void run() {
        while(true) {
            //Socket s = server.accept();
        }
        //super.run();
    }
}
