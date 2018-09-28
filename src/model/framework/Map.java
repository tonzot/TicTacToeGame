package model.framework;

import view.framework.DrawTool;

import java.awt.geom.Rectangle2D;

public class Map extends GraphicalObject{

    private Rectangle2D.Double[][] boxes;


    public Map(){
        generateBoxes();
    }

    @Override
    public void draw(DrawTool drawTool){
        drawRectangles(drawTool);
    }

    public void generateBoxes(){
        boxes = new Rectangle2D.Double[3][3];
        for(int i = 0; i <= 2; i++){
            for(int j = 0; j <= 2; j++){
                boxes[i][j] = new Rectangle2D.Double(90+i*200,80+j*200,200, 200);
            }
        }
    }

    public void drawRectangles(DrawTool drawTool){
        for(int i = 0; i <= 2; i++){
            for(int j = 0; j <= 2; j++) {
                drawTool.drawRectangle(boxes[i][j].x,boxes[i][j].y,boxes[i][j].width,boxes[i][j].height);
            }
        }
    }
}
