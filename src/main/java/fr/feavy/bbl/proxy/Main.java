package fr.feavy.bbl.proxy;

import com.sun.net.httpserver.*;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public final static String BASE = "https://www.blablaforever.com";

    private static boolean debug = false;
    private static HTTPServer httpServer;

    public static void main(String[] args) {
        try {
            HTTPSClient client = new HTTPSClient(BASE);

            if (!debug) {
                client.doPOST("/login.php", "login=bilal12&password=BilalLeB3st1");

                client.saveFile("/chat.php");
                client.saveFile("/chat/chat.swf?CACHE_VERSION=104");

                //client.saveFile("/data/fx/0/fx.swf?cacheVersion=10");
                //client.saveFile("/data/external.swf?cacheVersion=104");
                //client.saveFile("/scripts/chat/keepalive.php");
            }

            Process exec = Runtime.getRuntime().exec("netsh int ip add address \"Ethernet\" 51.75.125.199");

            // Wi-fi ou Ethernet
            // Necessite pas de DHCP --> config auto ?

            String response = new String(new InputStreamToByteArray(exec.getInputStream()).data());
            System.out.println(response);
            if (response.length() > 2) {
                System.err.println("Erreur : veuillez exécuter le programme en mode Aministrateur.");
            }

            new BlablalandServer().start();
            System.out.println("Blablaland Server started");

            httpServer = new HTTPServer();
            httpServer.start();
            System.out.println("HTTP Server started");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HTTPServer getHttpServer() {
        return httpServer;
    }
}