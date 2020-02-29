package fr.feavy.bbl.proxy;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class InputStreamToByteArray {
    private final InputStream inputStream;
    private final int bufferSize;

    public InputStreamToByteArray(InputStream inputStream) {
        this(inputStream, 2048);
    }

    public InputStreamToByteArray(InputStream inputStream, int bufferSize) {
        this.inputStream = inputStream;
        this.bufferSize = bufferSize;
    }

    public byte[] data() throws IOException {
        byte[] bytes = new byte[bufferSize];
        int len;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        while ((len = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, len);
        }

        inputStream.close();

        return outputStream.toByteArray();
    }

    public void sendTo(OutputStream outputStream) throws IOException {
        byte[] bytes = new byte[bufferSize];
        int len;

        while ((len = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, len);
        }

        inputStream.close();
    }
}
