package fr.feavy.bbl.proxy;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class BlablalandConnexionHandler extends Thread {
    private final Socket socket;
    private final InputStream inputStream;
    private final OutputStream outputStream;

    public BlablalandConnexionHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.inputStream = socket.getInputStream();
        this.outputStream = socket.getOutputStream();
    }

    @Override
    public void run() {
        super.run();
        try {
            int len = -1;
            byte[] data = new byte[32];
            System.out.println("Waiting for data...");
            while((len = inputStream.read(data)) != -1) {
                System.out.println("Data received !");
                System.out.println(Arrays.toString(data));
                handle(new String(data, 0, len));
                handleBinary(data, len);
                if(data[len-1] == 0) {
                    break;
                }
            }
            //System.out.println(new String(new InputStreamToByteArray(inputStream).data()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handle(String data) throws IOException {
        System.out.println(data);
        if(data.contains("policy-file-request")) {
            File f = new File("content/crossdomain.xml");
            outputStream.write(new InputStreamToByteArray(new FileInputStream(f)).data());
            outputStream.flush();
            System.out.println("Sended crossdomain.xml");
            outputStream.close();
        }
    }

    private void handleBinary(byte[] data, int length) throws IOException {
        if(data[0] == 1 && data[1] == 3) {
            System.out.println("Finding token...");
            BinaryData binaryData = new BinaryData(byteArrayToIntArray(data, length), 0, length);
            binaryData.bitReadUnsignedInt(5);
            binaryData.bitReadUnsignedInt(5);
            String token = binaryData.bitReadString().substring(3, 19);
            System.out.println("token = "+token);
            System.out.println(token.length());
            Main.getHttpServer().setupBlablalandClient(token);
        }
    }

    private static int[] byteArrayToIntArray(byte[] data, int length) {
        int[] newData = new int[length];
        for(int i = 0; i < length; i++)
            newData[i] = data[i];
        return newData;
    }
}
