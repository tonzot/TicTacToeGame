package model;

import control.framework.UIController;
import model.abitur.netz.Client;

public class TTTClient extends Client {

    private boolean myTurn;
    private Map map;
    private int sign;

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
        send("CONNECT");
        sign = 1;
    }

    /**
     * processMessage(...) verarbeitet die empfangene Nachricht.
     * @param pMessage ist die empfangene Nachricht.
     */
    @Override
    public void processMessage(String pMessage) {
        String[] splits = pMessage.split(";");

        if(splits[0].equals("OPPONENTPICK")){
            map.pickBox(Integer.parseInt(splits[1]),Integer.parseInt(splits[2]),2);
            myTurn = true;
        }else if(splits[0].equals("START")){
            myTurn = true;
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
     * setMyTurn(...) ist eine verändernde Methode.
     * @param myTurn ist der neue Wert den die Variable myTurn annehmen soll.
     */
    public void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;
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
