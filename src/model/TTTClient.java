package model;

import control.RSA;
import control.framework.UIController;
import model.abitur.netz.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TTTClient extends Client {

    private boolean myTurn;
    private int sign, opponentSign;
    private int[][] tiles;

    //Referenzen
    private Map map;
    private JTextField jt;
    private JTextArea textArea;
    private JScrollPane sp;

    //RSA
    private RSA rsa;
    private String[] receiverPublicKey;

    /**
     * TTTClient(...) sendet dem Server eine Verbindungsanfrage und verbindet ihn dann.
     * @param pServerIP ist die IP des Servers.
     * @param pServerPort ist der Port an dem der Server die Daten empfängt.
     * @param uiController ist eine Referenz auf den UIController.
     */
    public TTTClient(String pServerIP, int pServerPort, UIController uiController) {
        super(pServerIP, pServerPort);

        myTurn = false;
        map = new Map(this);
        uiController.drawObject(map);
        sign = 2;
        opponentSign = 1;
        tiles = new int[3][3];

        //Chat mit JTextfield
        jt = new JTextField(41);
        textArea = new JTextArea(6,40);
        sp = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        uiController.getActiveDrawingPanel().add(jt);
        uiController.getActiveDrawingPanel().add(sp);

        jt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = jt.getText();
                send("CHAT;" + rsa.encrypt(input,receiverPublicKey));
                textArea.append("YOU: "+input+"\n");
            }
        });
    }

    /**
     * processMessage(...) verarbeitet die empfangene Nachricht.
     * @param pMessage ist die empfangene Nachricht.
     */
    @Override
    public void processMessage(String pMessage) {
        String[] splits = pMessage.split(";");

        switch(splits[0]){
            case "OPPONENTPICK":
                int h = 1;
                for(int i = 0; i < 3; i++){
                    for(int j = 0; j < 3; j++){
                        tiles[i][j] = Integer.parseInt(splits[h]);
                        h++;
                    }
                }
                myTurn = true;
                break;
            case "START":
                myTurn = true;
                System.out.println("start");
                sign = 1;
                opponentSign = 2;
                break;
            case "WELCOME":
                System.out.println("Willkommen Bratan.");
                break;
            case "TOOMUCH":
                System.out.println("Es tut uns leid, es wurde bereits ein Spiel gestartet. /n Schauen Sie später wieder vorbei.");
                break;
            case "WIN":
                System.out.println("Glückwunsch, du hast gewonnen!");
                break;
            case "LOSE":
                System.out.println("Du hast leider verloren...");
                break;
            case "DRAW":
                System.out.println("Unentschieden");
                break;
            case "SIGNOUT":
                System.out.println("Hayde Ciao der Empfang geht weg.");
                break;
            case "CHAT":
                textArea.append("OPPONENT: " + rsa.decrypt(splits[1]) + "\n");
                break;
            case "SENDKEYS":
                rsa = new RSA();
                receiverPublicKey = new String[2];
                send("KEYS;" + rsa.getPublicKey()[0] + ";" + rsa.getPublicKey()[1]);
                break;
            case "KEYS":
                receiverPublicKey[0] = splits[1];
                receiverPublicKey[1] = splits[2];
                break;
        }

    }

    /**
     * isMyTurn() ist eine sondierende Methode.
     * @return myturn wird zurückgegeben.
     */
    public boolean isMyTurn() {
        return myTurn;
    }

    /**
     * getSign() ist eine sondierende Methode.
     * @return sign. Der Wert von der Variable sign wird zurückgegeben.
     */
    public int getSign() {
        return sign;
    }

    /**
     * sendPick(...) sendet dem Server die X- und Y-Koordinate, an dem der Spieler sein Zeichen setzt.
     * @param x Koordinate des Zeichens.
     * @param y Koordinate des Zeichens.
     */
    public void sendPick(int x, int y){
        send("PICK;" + x + ";" + y);
        myTurn = false;
    }

    //////

    public int[][] getTiles() {
        return tiles;
    }

    public void setTile(int i, int j, int a) {
        tiles[i][j] = a;
    }
}
