package model;

import model.abitur.netz.Server;

public class TTTServer extends Server {

    //Attribute
    private boolean playerOnesTurn = true;
    private int maxPlayerAmount = 2;
    private int currentPlayerAmount = 0;
    private String[] playerIps = new String[2];
    private int[] playerPorts = new int[2];

    public TTTServer(int pPort) {
        super(pPort);
    }

    @Override
    public void processNewConnection(String pClientIP, int pClientPort) {
        if(currentPlayerAmount < maxPlayerAmount) {
            send(pClientIP, pClientPort, "WELCOME;Willkommen Bratan.");
            playerIps[currentPlayerAmount] = pClientIP;
            playerPorts[currentPlayerAmount] = pClientPort;
            currentPlayerAmount++;
            if (currentPlayerAmount == 2){
                send(playerIps[0], playerPorts[0],"START");
            }
        } else{
            send(pClientIP, pClientPort, "TOOMUCH;Es tut uns leid, es wurde bereits ein Spiel gestartet. /n Schauen Sie später wieder vorbei.");
            playerIps[currentPlayerAmount] = pClientIP;
            currentPlayerAmount++;
        }
    }

    @Override
    public void processMessage(String pClientIP, int pClientPort, String pMessage) {
        String[] nachrichtenTeil = pMessage.split(":");
        if(nachrichtenTeil[0].equals("CONNECT")){
            processNewConnection(pClientIP, pClientPort);

        } else if(nachrichtenTeil[0].equals("PICK")){
            int x = Integer.parseInt(nachrichtenTeil[1]);
            int y = Integer.parseInt(nachrichtenTeil[2]);
            if (playerOnesTurn) {
                send(playerIps[1], playerPorts[1], "OPPONENTPICK;" + x + ";" + y);
            }else{
                send(playerIps[0], playerPorts[0], "OPPONENTPICK;" + x + ";" + y);
            }

        } else if(nachrichtenTeil[0].equals("LEAVE")) {
            processClosingConnection(pClientIP, pClientPort);

        }else if(nachrichtenTeil[0].equals("WIN")) {
            if (playerIps[0].equals(pClientIP)) {
                send(playerIps[0], playerPorts[0], "WIN;Glückwunsch, du hast gewonnen!");
                send(playerIps[1], playerPorts[1], "LOSE;Du hast leider verloren...");
            } else {
                send(playerIps[1], playerPorts[1], "WIN;Glückwunsch, du hast gewonnen!");
                send(playerIps[0], playerPorts[0], "LOSE;Du hast leider verloren...");
            }
        }else if(nachrichtenTeil[0].equals("DRAW")){
            send(playerIps[0], playerPorts[0], "DRAW;Unentschieden");
            send(playerIps[1], playerPorts[1], "DRAW;Unentschieden");

        }else{
            send(pClientIP, pClientPort, "FALSECOMMAND;Geben Sie bitte einen richtigen Befehl ein.");
        }
    }

    @Override
    public void processClosingConnection(String pClientIP, int pClientPort) {
        send(pClientIP, pClientPort, "SIGNOUT;Hayde Ciao der Empfang geht weg.");
        closeConnection(pClientIP, pClientPort);
    }
}
