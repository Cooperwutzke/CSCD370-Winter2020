/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cwutzkeAshman;

/**
 *
 * @author Cooper Wutzke
 */
public abstract class GamePiece 
{
    int pieceSize = 25;
    int x, y;
    Maze maze;
        
    private boolean player;
    
    GamePiece(Maze m, int row, int col, boolean isPlayer)
    {
        maze = m;
        x = row * pieceSize;
        y = col * pieceSize;
        player = isPlayer;
        if(!isPlayer)
            direction = Direction.values()[(int)(Math.random() * 4)];
            
        else
            direction = Direction.stop;

    }

    public int getXIndex() {
        return x;
    }

    public int getYIndex() {
        return y;
    }
}
