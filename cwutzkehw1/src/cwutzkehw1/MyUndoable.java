/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cwutzkehw1;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javax.swing.undo.AbstractUndoableEdit;

/**
 *
 * @author Cooper Wutzke
 */
public class MyUndoable extends AbstractUndoableEdit
{
    private final Canvas mMainCanvas;
    private Canvas mUndoCanvas;
    private Canvas mRedoCanvas;
    public MyUndoable(Canvas currentCanvas)
    {
        mMainCanvas = currentCanvas;
        mUndoCanvas = copyCanvas(mMainCanvas);
    }
    
    @Override
    public void undo()
    {
        mRedoCanvas = copyCanvas(mMainCanvas);
        
        Canvas temp = copyCanvas(mMainCanvas);
        saveCanvas(mUndoCanvas, mMainCanvas);
        saveCanvas(temp, mUndoCanvas);
    }
    
    @Override
    public void redo()
    {
        mUndoCanvas = this.copyCanvas(mMainCanvas);
        
        Canvas temp = this.copyCanvas(mMainCanvas);
        this.saveCanvas(mRedoCanvas, mMainCanvas);
        this.saveCanvas(temp, mRedoCanvas);
    }
    
    public Canvas copyCanvas(Canvas targ)
    {
        Canvas temp = new Canvas(mMainCanvas.getWidth(), mMainCanvas.getHeight());
        temp.getGraphicsContext2D().drawImage(targ.snapshot(null, null), 0, 0);
        
        return temp;
    }
    
    public void saveCanvas(Canvas targ, Canvas save)
    {
        save.getGraphicsContext2D().drawImage(targ.snapshot(null, null), 0, 0);
    }
}
