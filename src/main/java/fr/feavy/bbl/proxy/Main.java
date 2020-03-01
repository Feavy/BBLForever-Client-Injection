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

    private final static String BASE = "https://www.blablaforever.com/";

    public static void main(String[] args) throws IOException {
        HTTPSClient client = new HTTPSClient(BASE);
        client.doPOST("login.php", "login=bilal12&password=bilal13");

        client.saveFile("chat.php");
        client.saveFile("chat/chat.swf?CACHE_VERSION=104");
        /*client.saveFile("scripts/chat/usersStats.php");
        client.saveFile("scripts/chat/keepAlive.php");
        client.saveFile("scripts/chat/dailyMessage.php");
        client.saveFile("scripts/chat/contactManager.php");
        client.saveFile("scripts/chat/getBBL.php");*/

        client.saveFile("data/fx/0/fx.swf?cacheVersion=10");
        client.saveFile("data/external.swf?cacheVersion=104");

        InetAddress inetAddress = InetAddress.getLocalHost();
        String localIP = inetAddress.getHostAddress();
        Process exec = Runtime.getRuntime().exec("netsh int ip add address \"Wi-Fi\" 51.75.125.199");
        // Wi-fi ou Ethernet
        // Necessite pas de DHCP --> config auto ?
        String response = new String(new InputStreamToByteArray(exec.getInputStream()).data());
        if(!response.contains("OK")) {
            System.err.println("Erreur : veuillez exécuter le programme en mode Aministrateur.");
            //System.exit(0);
        }


        //SSLContext ctx = new SimpleSSLContext().get();

        //InetSocketAddress addr = new InetSocketAddress(0);
        //HttpServer server = HttpServer.create(addr, 0);
        //server.setHttpsConfigurator(new Configurator(ctx));

        new BlablalandServer().start();
        System.out.println("Blablaland Server started");

        HttpServer httpServer = HttpServer.create(new InetSocketAddress(80), 0);
        HttpContext context = httpServer.createContext("/");
        context.setHandler(Main::handleRequest);
        httpServer.start();
        System.out.println("HTTP Server started");

    }

    private static void handleRequest(HttpExchange exchange) throws IOException {
        try {
        String path = exchange.getRequestURI().toString();
        System.out.println("Request: "+path);
        if(path.contains("?"))
            path = path.split("\\?")[0];
        byte[] data = getFileBytes("content" + path);

        OutputStream os = exchange.getResponseBody();

        Headers responseHeaders = exchange.getResponseHeaders();
        String type = "text/html; charset=UTF-8";
        if(path.contains(".")) {
            String extension = path.split("\\.")[1];
            if(types.containsKey(extension))
                type = types.get(extension);
        }
        responseHeaders.set("Content-Type", type);
        exchange.sendResponseHeaders(200, data.length);//response code and length

            os.write(data);
            os.close();
        }catch (FileNotFoundException e) {
        }catch (Exception e){
            e.printStackTrace();
        }
        exchange.close();
    }

    private static byte[] getFileBytes(String path) throws IOException {
        return new InputStreamToByteArray(new FileInputStream(new File(path))).data();
    }

    private static Map<String, String> types = new HashMap<>();
    static {
        types.put("css", "text/css; charset=UTF-8");
        types.put("js", "application/javascript; charset=UTF-8");
        types.put("swf", "application/x-shockwave-flash");
    }
}