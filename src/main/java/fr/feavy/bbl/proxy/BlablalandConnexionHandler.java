package fr.feavy.bbl.proxy;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class BlablalandConnexionHandler extends Thread {
    private final Socket socket;
    private final InputStream inputStream;

    public BlablalandConnexionHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.inputStream = socket.getInputStream();
    }

    @Override
    public void run() {
        super.run();
        try {
            System.out.println(new String(new InputStreamToByteArray(inputStream).data()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
