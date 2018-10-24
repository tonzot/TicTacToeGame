package model;

import model.abitur.netz.Server;

public class TTTServer extends Server {

    //Attribute
    private boolean playerOnesTurn = true;
    private int maxPlayerAmount = 2;
    private int currentPlayerAmount = 0;
    private String[] playerIps = new String[2];
    private int[] playerPorts = new int[2];
    private int[][] tiles;
    private String tilesString;

    /**
     * TTTServer(...)
     * @param pPort ist der Port an dem der Server die Daten Empfängt.
     */
    public TTTServer(int pPort) {
        super(pPort);
        tiles = new int[3][3];
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
            case "PICK":
                int x = Integer.parseInt(nachrichtenTeil[1]);
                int y = Integer.parseInt(nachrichtenTeil[2]);
                if(playerOnesTurn){
                    tiles[x][y] = 1;
                }else{
                    tiles[x][y] = 2;
                }
                tilesString = buildTilesString();
                if(playerOnesTurn){
                    send(playerIps[1], playerPorts[1], "OPPONENTPICK;" + tilesString);
                }else{
                    send(playerIps[0], playerPorts[0], "OPPONENTPICK;" + tilesString);
                }
                int i = checkOver();
                switch (i){
                    case 1:
                        send(playerIps[0], playerPorts[0], "WIN");
                        send(playerIps[1], playerPorts[1], "LOSE");
                        close();
                        break;
                    case 2:
                        send(playerIps[1], playerPorts[1], "WIN");
                        send(playerIps[0], playerPorts[0], "LOSE");
                        close();
                        break;
                    case 3:
                        sendToAll("DRAW");
                        close();
                        break;
                }
                if(playerOnesTurn){
                    playerOnesTurn = false;
                }else{
                    playerOnesTurn = true;
                }
                break;
            case "LEAVE":
                processClosingConnection(pClientIP, pClientPort);
                break;
            case "CHAT":
                if (playerIps[0].equals(pClientIP) && playerPorts[0] == pClientPort) {
                    send(playerIps[1], playerPorts[1], "CHAT;"+nachrichtenTeil[1]);
                } else {
                    send(playerIps[0], playerPorts[0], "CHAT;"+nachrichtenTeil[1]);
                }
            default:
                send(pClientIP, pClientPort, "FALSECOMMAND");
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
    }

    public String buildTilesString(){
        String s = "";
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                s = s + tiles[i][j] + ";";
            }
        }
        return s;
    }



    /**
     * checkOver() überprüft, ob das Spiel vorbei ist
     * @return gibt 0 bis 2 zurück
     * bei 0 ist das Spiel nicht vorbei
     * bei 1 hat Spieler 1 das Spiel gewonnen
     * bei 2 hat Spieler 2 das Spiel gewonnen
     * bei 3 ist das Spiel unentschieden
     */
    public int checkOver(){
        int a = 0;
        for(int i = 0; i <= 2; i++){
            for(int j = 0; j <= 2; j++) {
                if(tiles[i][j] > 0){
                        a++;
                }
            }
        }
        if(a == 9){
            return 3;
        }
        for(int i =0; i < 3; i++){
            if(tiles[i][0] == tiles[i][1] && tiles[i][0] == tiles[i][2]){
                return tiles[i][0];
            }else if(tiles[0][i] == tiles[1][i] && tiles[0][i] == tiles[2][i]){
                return tiles[0][i];
            }
        }
        if(tiles[0][0] == tiles[1][1] && tiles[0][0] == tiles[2][2]){
            return tiles[0][0];
        }else if(tiles[2][0] == tiles[1][1] && tiles[2][0] == tiles[0][2]){
            return tiles[0][0];
        }
        return 0;
    }
}
