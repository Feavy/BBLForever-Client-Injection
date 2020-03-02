package fr.feavy.bbl.ui;

import javax.swing.*;

public class InformationPanel extends JTextArea {
    private String text = "";
    public InformationPanel() {

    }

    public void addText(String text) {
        this.setText(this.getText()+"\n"+text);
    }

    public void addErrorText(String text) {
        this.setText(this.getText()+"\n !! ERREUR : "+text);
    }
}
