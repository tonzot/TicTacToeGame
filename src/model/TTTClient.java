package model;

import control.framework.UIController;
import model.abitur.netz.Client;

public class TTTClient extends Client {

    private boolean myTurn;
    private Map map;
    private int sign;


    public TTTClient(String pServerIP, int pServerPort, UIController uiController) {
        super(pServerIP, pServerPort);
        myTurn = false;
        map = new Map(this);
        uiController.drawObject(map);
        send("CONNECT");
        sign = 1;
    }

    @Override
    public void processMessage(String pMessage) {
        String[] splits = pMessage.split(";");

        if(splits[0].equals("OPPONENTPICK")){
            map.pickBox(Integer.parseInt(splits[1]),Integer.parseInt(splits[2]),2);
            myTurn = true;
        }
    }

    public boolean isMyTurn() {
        return myTurn;
    }

    public void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }

    public int getSign() {
        return sign;
    }

    public void sendPick(int x, int y){
        send("PICK;" + x + ";" + y);
        myTurn = false;
    }
}
