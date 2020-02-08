/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cwutzkehw1;

import javafx.scene.canvas.Canvas;
import javax.swing.undo.AbstractUndoableEdit;

/**
 *
 * @author Cooper Wutzke
 */
public class UndoableScribble extends AbstractUndoableEdit
{
    private final Canvas mMainCanvas;
    private Canvas mUndoCanvas;
    private Canvas mRedoCanvas;
    
    public UndoableScribble(Canvas canvas)
    {
        mMainCanvas = canvas;
        mUndoCanvas = this.copyCanvas(canvas);
    }
    
    @Override
    public void undo()
    {
        mRedoCanvas = this.copyCanvas(mMainCanvas);
        
        Canvas temp = this.copyCanvas(mMainCanvas);
        this.saveCanvas(mUndoCanvas, mMainCanvas);
        this.saveCanvas(temp, mUndoCanvas);
    }
    
    @Override
    public void redo()
    {
        mUndoCanvas = this.copyCanvas(mMainCanvas);
        
        Canvas temp = this.copyCanvas(mMainCanvas);
        this.saveCanvas(mRedoCanvas, mMainCanvas);
        this.saveCanvas(temp, mRedoCanvas);
    }
    
    @Override
    public String getPresentationName() 
    { 
        return "Scribble"; 
    }
    
    private Canvas copyCanvas(Canvas canvas)
    {
        Canvas temp = new Canvas(mMainCanvas.getWidth(), mMainCanvas.getHeight());
        temp.getGraphicsContext2D().drawImage(canvas.snapshot(null, null), 0, 0);
        
        return temp;
    }
    
    private void saveCanvas(Canvas targ, Canvas save) 
    { 
        save.getGraphicsContext2D().drawImage(targ.snapshot(null, null), 0, 0); 
    }
    
 
}