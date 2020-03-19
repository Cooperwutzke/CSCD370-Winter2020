/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cwutzkeAshman;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

/**
 *
 * @author Cooper Wutzke
 */
public class Ghost extends GamePiece
{
    public Ghost(Maze targMaze, int row, int col)
    {
        super(targMaze, row, col, false);
        setPieceType(4);        
    }
    
    public void draw(Canvas targ)
    {
        move();
        GraphicsContext context = targ.getGraphicsContext2D();
        
        context.setFill(Paint.valueOf("Blue"));
        context.fillRect(x, y, pieceSize, pieceSize);      
    }
}
