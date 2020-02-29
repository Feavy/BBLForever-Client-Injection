package fr.feavy.bbl.proxy;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {
    public static void main(String[] args) throws IOException {
        CookieHandler.setDefault(new CookieManager());
        System.out.println(doPOST("https://blablaforever.com/login.php", "login=bilal12&password=bilal13"));
    }

    public static String doPOST(String url, String data) throws IOException {
        HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setInstanceFollowRedirects(true);
        HttpsURLConnection.setFollowRedirects(true);
        connection.setUseCaches(true);
        System.out.println(data.length());
        connection.setRequestProperty("Host", "www.blablaforever.com");
        connection.setRequestProperty("Connection", "keep-alive");
        connection.setRequestProperty("Content-Length", "30");
        connection.setRequestProperty("Pragma", "no-cache");
        connection.setRequestProperty("Cache-Control", "no-cache");
        connection.setRequestProperty("Origin", "https://www.blablaforever.com");
        connection.setRequestProperty("Upgrade-Insecure-Requests", "1");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.122 Safari/537.36");
        connection.setRequestProperty("Sec-Fetch-Dest", "document");
        connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        connection.setRequestProperty("Sec-Fetch-Site", "same-origin");
        connection.setRequestProperty("Sec-Fetch-Mode", "navigate");
        connection.setRequestProperty("Sec-Fetch-User", "?1");
        connection.setRequestProperty("Referer", "https://www.blablaforever.com/login.php");
        connection.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
        connection.setRequestProperty("Accept-Language", "fr-FR,fr;q=0.9,en-US;q=0.8,en;q=0.7");


        PrintWriter writer = new PrintWriter(connection.getOutputStream());
        writer.println(data);
        writer.flush();
        /*OutputStream outputStream = connection.getOutputStream();
        outputStream.write(data.getBytes());
        outputStream.flush();*/

        System.out.println(connection.getURL());
        System.out.println(connection.getResponseCode());
        System.out.println(connection.getResponseMessage());

        byte[] bytes = new byte[2048];
        int len;

        StringBuilder stringBuilder = new StringBuilder();
        InputStream inputStream = connection.getInputStream();

        while((len = inputStream.read(bytes)) != -1) {
            stringBuilder.append(new String(bytes, 0, len));
        }

        inputStream.close();
        writer.close();

        return stringBuilder.toString();
    }
}
