package model;

import model.framework.GraphicalObject;
import view.framework.DrawTool;

import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class Map extends GraphicalObject {

    private int[][] tiles;
    private Rectangle2D.Double[][] boxes;
    private TTTClient client;


    /**
     * Map(...) erstellt das Spielfeld
     * @param client ist eine Referent auf den Client
     */
    public Map(TTTClient client){
        generateBoxes();
        this.client = client;
    }

    /**
     * draw(...) zeichnet das Spielfeld
     * @param drawTool ist eine Referenz auf das drawTool
     */
    @Override
    public void draw(DrawTool drawTool){
        drawRectangles(drawTool);
    }

    /**
     * mouseReleased(...) lässt den Spieler, falls dieser dran ist, seinen Zug machen
     * @param e ist eine Referenz auf das MouseEvent
     */
    @Override
    public void mouseReleased(MouseEvent e){
        if(client.isMyTurn()) {
            for (int i = 0; i <= 2; i++) {
                for (int j = 0; j <= 2; j++) {
                    if (boxes[i][j].contains(e.getPoint())) {
                        if(tiles[i][j] == 0){
                            tiles[i][j] = client.getSign();
                            client.sendPick(i,j);
                        }
                    }
                }
            }
        }
    }

    /**
     * generateBoxes() erstellt ein 2-Dimensionales Array für die Rechtecke und gibt diesen die richtigen Koordinaten
     * außerdem wird ein 2-Dimensionales int Array erstellt, um sich den Inhalt der Rechtecke zu speichern
     */
    public void generateBoxes(){
        boxes = new Rectangle2D.Double[3][3];
        for(int i = 0; i <= 2; i++){
            for(int j = 0; j <= 2; j++){
                boxes[i][j] = new Rectangle2D.Double(90+i*200,80+j*200,200, 200);
            }
        }
        tiles = new int[3][3];
    }

    /**
     * drawRectangles(...) zeichnet die Recktecke und ihren Inhalt
     * @param drawTool ist eine Referenz auf das drawTool
     */
    public void drawRectangles(DrawTool drawTool){
        for(int i = 0; i <= 2; i++){
            for(int j = 0; j <= 2; j++) {
                drawTool.drawRectangle(boxes[i][j].x,boxes[i][j].y,boxes[i][j].width,boxes[i][j].height);
                if(tiles[i][j] == 1){
                    drawTool.drawLine(boxes[i][j].x+20,boxes[i][j].y+20,boxes[i][j].x+180,boxes[i][j].y+180);
                    drawTool.drawLine(boxes[i][j].x+180,boxes[i][j].y+20,boxes[i][j].x+20,boxes[i][j].y+180);
                }else if(tiles[i][j] == 2){
                    drawTool.drawCircle(boxes[i][j].x+20,boxes[i][j].y+20,160);
                }
            }
        }
    }

    /**
     * pickBox(...) belegt das übergebene Feld mit dem Übergebenem zeichen
     * @param x ist die x-Koordinate des Feldes, jedoch nur von 0 bis 2
     * @param y ist die y-Koordinate des Feldes, jedoch nur von 0 bis 2
     * @param sign ist das Zeichen das eingesetzt werden soll, 0 für nichts, 1 für Kreuz und 2 für Kreis
     */
    public void pickBox(int x, int y, int sign){
        tiles[x][y] = sign;
    }

    /**
     * checkOver() überprüft, ob das Spiel vorbei ist
     * @return gibt 0 bis 2 zurück
     * bei 0 ist das Spiel nicht vorbei
     * bei 1 ist das Spiel gewonnen
     * bei 2 ist das Spiel unentschieden
     */
    public int checkOver(){
        for(int i = 0; i <= 2; i++){
            if(tiles[i][0] == client.getSign() && tiles[i][1] == client.getSign() && tiles[i][2] == client.getSign()){
                return 1;
            }else if(tiles[0][i] == client.getSign() && tiles[1][i] == client.getSign() && tiles[2][i] == client.getSign()){
                return 1;
            }
        }
        if(tiles[0][0] == client.getSign() && tiles[1][1] == client.getSign() && tiles[2][2] == client.getSign()){
            return 1;
        }else if(tiles[0][2] == client.getSign() && tiles[1][1] == client.getSign() && tiles[2][0] == client.getSign()){
            return 1;
        }else{
            int a = 0;
            for(int i = 0; i <= 2; i++){
                for(int j = 0; j <= 2; j++) {
                    if(tiles[i][j] > 0){
                        a++;
                    }
                }
            }
            if(a == 9){
                return 2;
            }
        }
        return 0;
    }


}
