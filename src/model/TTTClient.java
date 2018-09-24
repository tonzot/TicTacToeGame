package model;

import model.abitur.netz.Client;

public class TTTClient extends Client {

    private int map[][] = new int[3][3];
    private boolean myTurn;


    public TTTClient(String pServerIP, int pServerPort) {
        super(pServerIP, pServerPort);
        myTurn = false;

    }

    @Override
    public void processMessage(String pMessage) {

        if (pMessage.equals("START")){
            createMap();
        }
//Keks
        //------------------------------------------------


            if (pMessage.equals("PICK")) {

            }

        //-------------------------------------
        if (pMessage.equals("LEAVE")){

        }
    }


    public void createMap(){
        map = new int[3][3];
    }


    /**
     * -MyTurn
     * überprüfen ob Feld frei
     **/
}
