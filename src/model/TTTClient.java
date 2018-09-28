package model;

import control.framework.UIController;
import model.abitur.netz.Client;
import model.framework.Map;

public class TTTClient extends Client {

    private int tiles[][] = new int[3][3];
    private boolean myTurn;
    private Map map;


    public TTTClient(String pServerIP, int pServerPort, UIController uiController) {
        super(pServerIP, pServerPort);
        System.out.println(1);
        myTurn = false;
        map = new Map();
        uiController.drawObject(map);
        send("CONNECT");
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
