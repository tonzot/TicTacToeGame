package model.framework;

import view.framework.DrawTool;

public class Map extends GraphicalObject{



    public Map(){

    }

    @Override
    public void draw(DrawTool drawTool){
        drawLines(drawTool);
    }

    public void drawLines(DrawTool drawTool){
        drawTool.drawLine(200,50,200,750);
    }
}
