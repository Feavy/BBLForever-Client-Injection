package fr.feavy.bbl.proxy;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;

public class HTTPSClient {
    private final String base;
    private String savedFilesPath;

    public HTTPSClient(String base) {
        this(base, "content/");
    }

    public HTTPSClient(String base, String savedFilesPath) {
        this.base = base;
        CookieHandler.setDefault(new CookieManager());
        this.savedFilesPath = savedFilesPath;
    }

    public String doGET(String path) throws IOException {
        HttpsURLConnection connection = newConnection(path);
        connection.setRequestMethod("GET");

        return new String(readInputStream(connection.getInputStream()));
    }

    public String doPOST(String path, String data) throws IOException {
        HttpsURLConnection connection = newConnection(path);
        connection.setRequestMethod("POST");

        OutputStream out = connection.getOutputStream();
        out.write(data.getBytes());
        out.flush();
        out.close();

        connection.getResponseCode();
        connection.getResponseMessage();

        return new String(readInputStream(connection.getInputStream()));
    }

    private HttpsURLConnection newConnection(String path) throws IOException {
        HttpsURLConnection connection = (HttpsURLConnection) new URL(base + path).openConnection();
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        return connection;
    }

    private byte[] readInputStream(InputStream inputStream) throws IOException {
        return new InputStreamToByteArray(inputStream).data();
    }

    public File saveFile(String path) throws IOException {
        File file = new File(savedFilesPath+path);
        file.getParentFile().mkdirs();
        HttpsURLConnection connection = newConnection(path);
        byte[] data = readInputStream(connection.getInputStream());
        FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.write(data);
        return file;
    }
}
