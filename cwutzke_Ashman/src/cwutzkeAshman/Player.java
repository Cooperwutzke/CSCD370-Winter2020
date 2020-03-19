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
public class Player extends GamePiece
{
    public Player(Maze targMaze, int row, int col) 
    {        
        super(targMaze, row, col, true);
        setPieceType(3);
    }
    
    public void draw(Canvas targ)
    {
        move();
        GraphicsContext context = targ.getGraphicsContext2D();
        
        context.setFill(Paint.valueOf("Green"));
        context.fillOval(x, y, pieceSize, pieceSize);
    }
    
    public Player getPlayer()
    {
        return this;
    }
}
