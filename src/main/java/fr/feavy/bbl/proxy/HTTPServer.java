package fr.feavy.bbl.proxy;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import fr.feavy.bbl.ui.MainApplication;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HTTPServer {

    private final HttpServer httpServer;
    private boolean modifiedClient = false;

    private HTTPSClient httpsClient;

    public HTTPServer() throws IOException {
        this.httpServer = HttpServer.create(new InetSocketAddress(80), 0);
        HttpContext context = httpServer.createContext("/");
        context.setHandler(this::handleRequest);
    }

    public void start() {
        httpServer.start();
    }

    private void handleRequest(HttpExchange exchange) throws IOException {
        try {
            String path = exchange.getRequestURI().toString();
            path = path.replaceAll("//", "/");
            System.out.println("Request: "+path);
            if(path.contains("?"))
                path = path.split("\\?")[0];

            byte[] data;
            if(httpsClient != null && !isLocalMap(path)) {
                data = httpsClient.getFile(exchange.getRequestURI().toString(), exchange.getRequestHeaders());
            }else {
                if(isLocalMap(path)) {
                    System.out.println(path+" from local map !");
                }
                data = getFileBytes("content" + path);
            }

            OutputStream os = exchange.getResponseBody();

            Headers responseHeaders = exchange.getResponseHeaders();
            String type = "text/html; charset=UTF-8";
            if(path.contains(".")) {
                String extension = path.split("\\.")[1];
                if(types.containsKey(extension))
                    type = types.get(extension);
            }
            responseHeaders.set("Content-Type", type);
            responseHeaders.set("Cache-Control", "max-age=0, no-cache, no-store, must-revalidate");
            exchange.sendResponseHeaders(200, data.length);//response code and length

            os.write(data);
            os.close();

            if(path.contains("chat.swf") && modifiedClient) {
                exchange.close();
                Process exec = Runtime.getRuntime().exec("netsh int ip delete address \"Ethernet\" 51.75.125.199");
                String response = new String(new InputStreamToByteArray(exec.getInputStream()).data());
                System.out.println(response);
                response = new String(new InputStreamToByteArray(exec.getErrorStream()).data());
                System.out.println(response);
                System.out.println("Adress deleted");
                this.httpsClient = new HTTPSClient(MainApplication.BASE);
                return;
            }

        }catch (FileNotFoundException e) {
        }catch (Exception e){
            e.printStackTrace();
        }
        exchange.close();
    }

    public void setupBlablalandClient(String token) throws IOException {
        File swf = new File("custom_client/client.swf");
        String fileData = new String(new InputStreamToByteArray(new FileInputStream(swf)).data());
        int fileLength = fileData.length();
        int tokenIndex = fileData.indexOf("ae210560ea302afb");
        if(tokenIndex < 0) {
            System.err.println("Erreur: impossible de modifier le token du client.");
            System.exit(1);
        }

        FileInputStream fis = new FileInputStream(swf);
        byte[] data1 = new byte[fileLength];
        fis.read(data1);
        for(int i = 0; i < 16; i++) {
            data1[tokenIndex+i] = token.getBytes()[i];
        }
        fis.close();

        FileOutputStream fos = new FileOutputStream(new File("content/chat/chat.swf"));
        fos.write(data1);
        fos.flush();
        fos.close();

        System.out.println("chat.swf edited !!");
        modifiedClient = true;
    }

    private static byte[] getFileBytes(String path) throws IOException {
        return new InputStreamToByteArray(new FileInputStream(new File(path))).data();
    }

    private static List<String> mapLocal = new ArrayList<>();
    static {
        mapLocal.add("keepalive.php");
        mapLocal.add("SmileyPack.swf");
        //mapLocal.add("external.swf");
        mapLocal.add("getBBL.php");
    }

    private static boolean isLocalMap(String path) {
        for(String p : mapLocal) {
            if(path.contains(p))
                return true;
        }
        return false;
    }

    private static Map<String, String> types = new HashMap<>();
    static {
        types.put("css", "text/css; charset=UTF-8");
        types.put("js", "application/javascript; charset=UTF-8");
        types.put("swf", "application/x-shockwave-flash");
    }
}
