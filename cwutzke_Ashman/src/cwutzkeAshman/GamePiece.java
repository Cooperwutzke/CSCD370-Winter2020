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
public abstract class GamePiece 
{
    int pieceType;
    int pieceSize = 25;
    int x, y;
    Maze mMaze;
    MoveDirection moveDir;
        
    private boolean player;
    
    GamePiece(Maze m, int row, int col, boolean isPlayer)
    {
        mMaze = m;
        x = row * pieceSize;
        y = col * pieceSize;
        player = isPlayer;
        
        if(!isPlayer())
        {
            moveDir = MoveDirection.values()[(int)(Math.random() * 4)];
        }            
        else
        {
            moveDir = MoveDirection.STOP;
        }
    }

    public int getXIndex() 
    {
        return x;
    }

    public int getYIndex() 
    {
        return y;
    }
    
    public void setDirection(MoveDirection dir)
    {
        if(isPlayer())
        {
            moveDir = dir;
        }
    }
    public abstract void draw(Canvas targ);
    
    public boolean isPlayer()
    {
        return player;
    }

    public void move(){
        int X = x;
        int Y = y;
        switch (moveDir){
            case UP:
                Y++;
                break;
            case DOWN:
                Y--;
                break;
            case LEFT:
                X--;
                break;
            case RIGHT:
                X++;
                break;
            case STOP:
                break;
        }
        
        else if (!player)
        {
            moveDir =  MoveDirection.values()[(int)(Math.random() * 4)];
        }
        else
        {
            moveDir = MoveDirection.STOP;
        }
    }
    
    public void setPieceType(int type)
    {
        pieceType = type;
    }
}
