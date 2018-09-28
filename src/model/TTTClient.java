package model;

import model.abitur.netz.Client;
import model.framework.Map;

public class TTTClient extends Client {

    private int tiles[][] = new int[3][3];
    private boolean myTurn;
    private Map map;


    public TTTClient(String pServerIP, int pServerPort) {
        super(pServerIP, pServerPort);
        myTurn = false;
        map = new Map();
        //send("CONNECT");
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
        tiles = new int[3][3];
    }


    /**
     * -MyTurn
     * überprüfen ob Feld frei
     **/
}
