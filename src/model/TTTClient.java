package model;

import control.framework.UIController;
import model.abitur.netz.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TTTClient extends Client {

    private boolean myTurn;
    private Map map;
    private int sign, opponentSign;

    private JTextField jt;
    private JLabel jl;

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
        //Chat mit JTextfield
        jt = new JTextField(30);
        uiController.getActiveDrawingPanel().add(jt);
        jl = new JLabel();
        uiController.getActiveDrawingPanel().add(jl);
        jt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = jt.getText();
                send("CHAT;"+input);
                jl.setText("YOU: "+input);
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
                map.pickBox(Integer.parseInt(splits[1]),Integer.parseInt(splits[2]),opponentSign);
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
                jl.setText("OPPONENT: "+splits[1]);
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
        int i = map.checkOver();
        if(i == 1){
            send("WIN");
        }else if(i == 2){
            send("DRAW");
        }
    }
}
