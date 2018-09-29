package model;

import model.framework.GraphicalObject;
import view.framework.DrawTool;

import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class Map extends GraphicalObject {

    private int[][] tiles;
    private Rectangle2D.Double[][] boxes;
    private TTTClient client;
    private int sign;


    public Map(TTTClient client){
        generateBoxes();
        this.client = client;
        this.sign = client.getSign();
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

    //0 = nicht over
    //1 = gewonnen
    //2 = unentschieden
    public int checkOver(){
        for(int i = 0; i <= 2; i++){
            if(tiles[i][0] == sign && tiles[i][1] == sign && tiles[i][2] == sign){
                return 1;
            }else if(tiles[0][1] == sign && tiles[1][i] == sign && tiles[2][i] == sign){
                return 1;
            }
        }
        if(tiles[0][0] == sign && tiles[1][1] == sign && tiles[2][2] == sign){
            return 1;
        }else if(tiles[0][2] == sign && tiles[1][1] == sign && tiles[2][0] == sign){
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
