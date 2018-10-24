package model;

import model.framework.GraphicalObject;
import view.framework.DrawTool;

import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class Map extends GraphicalObject {

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
                        if(client.getTiles()[i][j] == 0){
                            client.setTile(i,j,client.getSign());
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
                boxes[i][j] = new Rectangle2D.Double(90+i*200,145+j*200,200, 200);
            }
        }
    }

    /**
     * drawRectangles(...) zeichnet die Recktecke und ihren Inhalt
     * @param drawTool ist eine Referenz auf das drawTool
     */
    public void drawRectangles(DrawTool drawTool){
        for(int i = 0; i <= 2; i++){
            for(int j = 0; j <= 2; j++) {
                drawTool.drawRectangle(boxes[i][j].x,boxes[i][j].y,boxes[i][j].width,boxes[i][j].height);
                if(client.getTiles()[i][j] == 1){
                    drawTool.drawLine(boxes[i][j].x+20,boxes[i][j].y+20,boxes[i][j].x+180,boxes[i][j].y+180);
                    drawTool.drawLine(boxes[i][j].x+180,boxes[i][j].y+20,boxes[i][j].x+20,boxes[i][j].y+180);
                }else if(client.getTiles()[i][j] == 2){
                    drawTool.drawCircle(boxes[i][j].x+20,boxes[i][j].y+20,160);
                }
            }
        }
    }


}
