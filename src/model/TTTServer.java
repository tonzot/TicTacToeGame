package model;

import model.abitur.netz.Server;

public class TTTServer extends Server {

    //Attribute
    private boolean playerOnesTurn = true;
    private int maxPlayerAmount = 2;
    private int currentPlayerAmount = 0;
    private String[] playerIps = new String[2];
    private int[] playerPorts = new int[2];

    /**
     * TTTServer(...)
     * @param pPort ist der Port an dem der Server die Daten Empfängt.
     */
    public TTTServer(int pPort) {
        super(pPort);
    }


    /**
     * processNewConnection(...) verarbeitet eine neue Verbindung.
     * @param pClientIP ist die Ip des Clients.
     * @param pClientPort ist der Port an dem der Client die Daten empfängt.
     */
    @Override
    public void processNewConnection(String pClientIP, int pClientPort) {
        if(currentPlayerAmount < maxPlayerAmount) {
            send(pClientIP, pClientPort, "WELCOME");
            playerIps[currentPlayerAmount] = pClientIP;
            playerPorts[currentPlayerAmount] = pClientPort;
            currentPlayerAmount++;
            if (currentPlayerAmount == 2){
                send(playerIps[0], playerPorts[0],"START");
            }
        } else{
            send(pClientIP, pClientPort, "TOOMUCH");
            playerIps[currentPlayerAmount] = pClientIP;
            currentPlayerAmount++;
        }
    }

    /**
     * processMessage(...) verarbeitet die empfangene Nachricht.
     * @param pClientIP ist die Ip des Clients.
     * @param pClientPort ist der Port an dem der Client die Daten empfängt.
     * @param pMessage ist die Nachricht die der nun vom Server verarbeitet wird.
     */
    @Override
    public void processMessage(String pClientIP, int pClientPort, String pMessage) {
        String[] nachrichtenTeil = pMessage.split(";");
        switch(nachrichtenTeil[0]){
            case "CONNECT":
                processNewConnection(pClientIP, pClientPort);
                break;
            case "PICK":
                int x = Integer.parseInt(nachrichtenTeil[1]);
                int y = Integer.parseInt(nachrichtenTeil[2]);
                if (playerOnesTurn) {
                    send(playerIps[1], playerPorts[1], "OPPONENTPICK;" + x + ";" + y);
                    playerOnesTurn = false;
                }else{
                    send(playerIps[0], playerPorts[0], "OPPONENTPICK;" + x + ";" + y);
                    playerOnesTurn = true;
                }
                break;
            case "LEAVE":
                processClosingConnection(pClientIP, pClientPort);
                break;
            case "WIN":
                if (playerIps[0].equals(pClientIP)) {
                    send(playerIps[0], playerPorts[0], "WIN");
                    send(playerIps[1], playerPorts[1], "LOSE");
                } else {
                    send(playerIps[1], playerPorts[1], "WIN;Glückwunsch, du hast gewonnen!");
                    send(playerIps[0], playerPorts[0], "LOSE;Du hast leider verloren...");
                }
                break;
            case "DRAW":
                send(playerIps[0], playerPorts[0], "DRAW");
                send(playerIps[1], playerPorts[1], "DRAW");
                break;
            default:
                send(pClientIP, pClientPort, "FALSECOMMAND;Geben Sie bitte einen richtigen Befehl ein.");
                break;
        }
    }

    /**
     * processClosingConnection(...) beendet die Verbindung zu einem Client.
     * @param pClientIP  ist die Ip des Clients.
     * @param pClientPort ist der Port an dem der Client die Daten empfängt.
     */
    @Override
    public void processClosingConnection(String pClientIP, int pClientPort) {
        send(pClientIP, pClientPort, "SIGNOUT");
        closeConnection(pClientIP, pClientPort);
    }
}
