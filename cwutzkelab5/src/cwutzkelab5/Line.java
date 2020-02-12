/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cwutzkelab5;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author Cooper Wutzke
 */
public class Line implements Serializable
{
    private transient Point2D mStartV;
    private transient Point2D mEndV;
    private double mCurLineWidth;
    private transient Color mCurColor;
    
    public Line(Point2D start, Point2D end, double width, Color color)
    {
        mStartV = start;
        mEndV = end;
        mCurLineWidth = width;
        mCurColor = color;
    }
    
    public void draw(Canvas canvas)
    {
        GraphicsContext context = canvas.getGraphicsContext2D();
        
        context.setStroke(mCurColor);
        context.setLineWidth(mCurLineWidth);
        context.strokeLine(mStartV.getX(), mStartV.getY(), mEndV.getX(), mEndV.getY());
//        if (mEndV.getX() > mStartV.getX() && mEndV.getY() > mStartV.getY())
//            context.strokeRect(mStartV.getX(), mStartV.getY(), mEndV.getX() - mStartV.getX(), mEndV.getY() - mStartV.getY());
//        else if (mEndV.getX() < mStartV.getX() && mEndV.getY() > mStartV.getY())
//            context.strokeRect(mEndV.getX(), mStartV.getY(), mStartV.getX() - mEndV.getX(), mEndV.getY() - mStartV.getY());
//        else if (mEndV.getX() < mStartV.getX() && mEndV.getY() < mStartV.getY())
//            context.strokeRect(mEndV.getX(), mEndV.getY(), mStartV.getX() - mEndV.getX(), mStartV.getY() - mEndV.getY());
//        else if (mEndV.getX() > mStartV.getX() && mEndV.getY() < mStartV.getY())
//            context.strokeRect(mStartV.getX(), mEndV.getY(), mEndV.getX() - mStartV.getX(), mStartV.getY() - mEndV.getY());
    }
    
    private void readObject(ObjectInputStream in)
    {
        try
        {
            in.defaultReadObject();
            
            mStartV = new Point2D(in.readDouble(), in.readDouble());
            mEndV = new Point2D(in.readDouble(), in.readDouble());
            
            mCurColor = new Color(in.readDouble(), in.readDouble(), in.readDouble(), 1);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    private void writeObject(ObjectOutputStream out)
    {
        try
        {
            out.defaultWriteObject();
            
            out.writeDouble(mStartV.getX());
            out.writeDouble(mStartV.getY());
            out.writeDouble(mEndV.getX());
            out.writeDouble(mEndV.getY());
            
            out.writeDouble(mCurColor.getRed());
            out.writeDouble(mCurColor.getGreen());
            out.writeDouble(mCurColor.getBlue());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
