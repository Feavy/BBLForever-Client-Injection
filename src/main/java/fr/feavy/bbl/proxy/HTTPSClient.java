package fr.feavy.bbl.proxy;

import com.sun.net.httpserver.Headers;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HTTPSClient {
    private final String base;
    private String savedFilesPath;

    public HTTPSClient(String base) {
        this(base, "content/");
    }

    public HTTPSClient(String base, String savedFilesPath) {
        this.base = base;
        if(CookieHandler.getDefault() == null)
            CookieHandler.setDefault(new CookieManager());
        this.savedFilesPath = savedFilesPath;
    }

    public byte[] getFile(String path, Headers headers) throws IOException {
        HttpsURLConnection connection = newConnection(path);
        connection.setRequestMethod("GET");

        /*Iterator<Map.Entry<String, List<String>>> iterator = headers.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry<String, List<String>> next = iterator.next();
            if(next.getKey().equals("Referer")) {

            }else
                connection.setRequestProperty(next.getKey(), String.join("; ", next.getValue()));
        }*/

        System.out.println(connection.getRequestProperties());
        connection.getResponseCode();
        System.out.println(connection.getResponseMessage());

        return readInputStream(connection.getInputStream());
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
        String filePath = path.split("\\?")[0];
        File file = new File(savedFilesPath+filePath);
        file.getParentFile().mkdirs();
        HttpsURLConnection connection = newConnection(path);
        byte[] data = readInputStream(connection.getInputStream());
        FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.write(data);
        return file;
    }
}
