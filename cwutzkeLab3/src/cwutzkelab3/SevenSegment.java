/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cwutzkelab3;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 *
 * @author Cooper Wutzke
 */
public class SevenSegment extends Region
{
    Canvas mCanvas;
    int mValue = 8;
    boolean[] mOnOff = new boolean[7];
    
    static final int WIDTH = 20;
    static final int HEIGHT = 34;
    
    static final double ASPECT_RATIO =  WIDTH / HEIGHT;
    
    static final int MARGIN_SIZE = 2;
    
    static final Color ON_COLOR = Color.RED;
    static final Color OFF_COLOR = Color.color(1, 0, 0, 0.1);
    
    static final double[] X_COORDINATES = {0.0, 1.0, 13.0, 14.0, 13.0, 1.0};
    static final double[] Y_COORDINATES = {0.0, 1.0, 1.0, 0.0, -1.0, -1.0};
    
    public SevenSegment()
    {
        mValue = 10;
        for (boolean b : mOnOff)
        {
            b = false;
        }
        mCanvas = new Canvas(WIDTH * 10, HEIGHT * 10);
        getChildren().add(mCanvas);
    }
    
    public SevenSegment(int value)
    {
        this();
        if (value > 0 && value <= 10)
            mValue = value;
    }
    
    public int getValue()
    {
        return mValue;
    }
    
    public void setValue(int value)
    {
        if (value > 0 && value <= 10)
        {
            mValue = value;
        }
        else
        {
            mValue = 10;
        }
    }
    
    
    public void draw()
    {
        GraphicsContext context = mCanvas.getGraphicsContext2D();
        context.save();
        context.translate(MARGIN_SIZE, MARGIN_SIZE); // Andrew: is context coordinate system?
        
        double widthDimension = mCanvas.getWidth() / WIDTH;
        double heightDimension = mCanvas.getHeight() / HEIGHT;
        context.scale(widthDimension, heightDimension);
        
        //context.setFill(Paint.valueOf("white"));
        //context.fillRect(mCanvas.getLayoutX(), mCanvas.getLayoutY(), mCanvas.getWidth(), mCanvas.getHeight());
        
        // Segment 0 : TOP MIDDLE
        context.setFill(!mOnOff[0] ? ON_COLOR : OFF_COLOR);
        context.translate(MARGIN_SIZE, MARGIN_SIZE);
        context.fillPolygon(X_COORDINATES, Y_COORDINATES, 6);
        
        // Segment 1 : TOP RIGHT
        context.save();
        context.translate(14, 0);
        context.rotate(90);
        context.setFill(!mOnOff[1] ? ON_COLOR : OFF_COLOR);
        context.fillPolygon(X_COORDINATES, Y_COORDINATES, 6);
        context.restore();
        
        // Segment 2 : TOP LEFT
        context.save();
        context.rotate(90);
        context.setFill(!mOnOff[2] ? ON_COLOR : OFF_COLOR);
        context.fillPolygon(X_COORDINATES, Y_COORDINATES, 6);
        context.restore();
        
        // Segment 3 : MIDDLE
        context.save();
        context.translate(0, 14);
        context.setFill(!mOnOff[3] ? ON_COLOR : OFF_COLOR);
        context.fillPolygon(X_COORDINATES, Y_COORDINATES, 6);
        context.restore();
        
        // Segment 4 : LOWER RIGHT
        context.save();
        context.translate(14, 14);
        context.rotate(90);
        context.setFill(!mOnOff[4] ? ON_COLOR : OFF_COLOR);
        context.fillPolygon(X_COORDINATES, Y_COORDINATES, 6);
        context.restore();
        
        // Segment 5 : LOWER LEFT
        context.save();
        context.translate(0, 14);
        context.rotate(90);
        context.setFill(!mOnOff[5] ? ON_COLOR : OFF_COLOR);
        context.fillPolygon(X_COORDINATES, Y_COORDINATES, 6);
        context.restore();
        
        // Segment 6 : LOWER MIDDLE
        context.save();
        context.translate(0, 28);
        context.setFill(!mOnOff[6] ? ON_COLOR : OFF_COLOR);
        context.fillPolygon(X_COORDINATES, Y_COORDINATES, 6);
        context.restore();

    }
    
    @Override
    public void layoutChildren()
    {
        double curWidth = getWidth();
        double curHeight = getHeight();
        
        double widthRatio = curWidth / WIDTH;
        double heightRatio = curHeight / HEIGHT;
        
        layoutInArea(this, 0, 0, curWidth, curHeight, 0, HPos.LEFT, VPos.TOP);
    }

}
