package model;

import model.framework.GraphicalObject;
import view.framework.DrawTool;

import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class Map extends GraphicalObject {

    private int[][] tiles;
    private Rectangle2D.Double[][] boxes;
    private TTTClient client;


    public Map(TTTClient client){
        generateBoxes();
        this.client = client;
    }

    @Override
    public void draw(DrawTool drawTool){
        drawRectangles(drawTool);
    }

    @Override
    public void mouseReleased(MouseEvent e){
        if(client.isMyTurn()) {
            System.out.println(1);
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

    public void generateBoxes(){
        boxes = new Rectangle2D.Double[3][3];
        for(int i = 0; i <= 2; i++){
            for(int j = 0; j <= 2; j++){
                boxes[i][j] = new Rectangle2D.Double(90+i*200,80+j*200,200, 200);
            }
        }
        tiles = new int[3][3];
    }

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

    public void pickBox(int x, int y, int sign){
        tiles[x][y] = sign;
    }


}
