package fr.feavy.bbl.ui;

import fr.feavy.bbl.proxy.BlablalandServer;
import fr.feavy.bbl.proxy.HTTPSClient;
import fr.feavy.bbl.proxy.HTTPServer;
import fr.feavy.bbl.proxy.InputStreamToByteArray;

import javax.swing.*;
import java.awt.*;

public class MainApplication extends JFrame {
    public final static String BASE = "https://www.blablaforever.com";
    private static HTTPServer httpServer;

    private ConnectionPanel connectionPanel;
    private InformationPanel informationPanel = new InformationPanel();

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new MainApplication().setVisible(true);
    }

    public MainApplication() {
        super("BlablaForever Maxi Hack");
        JLabel titleLbl = new JLabel("<html><h1 style='text-align: center'>BlablaForever Maxi Hack</h1></html>");
        titleLbl.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLbl, BorderLayout.NORTH);
        this.connectionPanel = new ConnectionPanel(this::onLogged);
        add(connectionPanel, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(250, 400);
        setResizable(false);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        int x = (int) rect.getMaxX() - getWidth();
        int y = (int) rect.getMaxY() - getHeight()-50;
        setLocation(x, y);
    }

    private void onLogged(String theInterface, String login, String pass) {
        try {
            remove(connectionPanel);
            add(new JScrollPane(informationPanel), BorderLayout.CENTER);

            informationPanel.setText("");
            informationPanel.repaint();
            informationPanel.revalidate();

            getContentPane().revalidate();
            getContentPane().repaint();
            ((JPanel)getContentPane()).updateUI();

            System.out.println("wait");
            //Thread.sleep(10000);

            HTTPSClient client = new HTTPSClient(BASE);

            informationPanel.addText("Téléchargement du client...");
            System.out.println("Téléchargement du client");
            repaint();
            revalidate();

            client.doPOST("/login.php", "login=" + login + "&password=" + pass);
            client.saveFile("/chat.php");
            client.saveFile("/chat/chat.swf?CACHE_VERSION=104");

            informationPanel.addText("Fichiers téléchargés.");

            Process exec = Runtime.getRuntime().exec("netsh int ip add address \""+theInterface+"\" 51.75.125.199");

            String response = new String(new InputStreamToByteArray(exec.getInputStream()).data(), "UTF-8");
            if (response.length() > 2) {
                informationPanel.addErrorText("Erreur : "+response);
                return;
            }

            new BlablalandServer().start();
            informationPanel.addText("Serveur Blablaland lancé !");

            httpServer = new HTTPServer();
            httpServer.start();
            informationPanel.addText("Serveur HTTP lancé !");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HTTPServer getHttpServer() {
        return httpServer;
    }

    public InformationPanel getInformationPanel() {
        return informationPanel;
    }
}
