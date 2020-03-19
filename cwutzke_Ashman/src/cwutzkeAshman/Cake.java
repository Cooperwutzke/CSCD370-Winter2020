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
public class Cake extends GamePiece
{
    public Cake(Maze targMaze, int row, int col)
    {
        super(targMaze, row, col, false);
        setPieceType(2);
    }
    
    @Override
    public void draw(Canvas targ)
    {
        GraphicsContext context = targ.getGraphicsContext2D();
        
        context.setFill(Paint.valueOf("Yellow"));
        context.fillOval(x, y, pieceSize - 2, pieceSize - 2);
    }
}
