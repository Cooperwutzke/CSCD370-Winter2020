/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cwutzkeHw3;

import java.io.Serializable;
import javafx.scene.paint.Color;

/**
 *
 * @author Cooper Wutzke
 */
class ColorPanelData implements Serializable
{
    private String mText;
    private double mRed;
    private double mGreen;
    private double mBlue;
    private double mOpacity;
    
    ColorPanelData(String text, double red, double green, double blue, double opacity)
    {
        mText = text;
        mRed = red;
        mGreen = green;
        mBlue = blue;
        mOpacity = opacity;
    }
    
    public String getText() 
    { 
        return mText; 
    }
    
    public Color getColor() 
    { 
        return new Color(mRed, mGreen, mBlue, mOpacity); 
    }
    
    @Override
    public String toString() 
    { 
        return "Panel Data:\n" + mText + "\nRed: " + (int) (mRed * 255) + "\nGreen: " + (int) (mGreen * 255) + "\nBlue: " + (int) (mBlue * 255) + "\nOpacity: " + mOpacity; 
    }
}    
