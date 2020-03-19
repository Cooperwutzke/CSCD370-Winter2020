/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cwutzkeAshman;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;

/**
 *
 * @author Cooper Wutzke
 */
public class Maze 
{    
    
    
    
    private final int [][] mazeTemplate = {
            {2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
            {2, 1, 2, 1, 1, 1, 2, 1, 2, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2},
            {2, 1, 2, 1, 2, 2, 2, 1, 2, 1, 2, 1, 2, 2, 2, 1, 2, 2, 2, 2},
            {2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 1, 2},
            {2, 1, 2, 1, 2, 1, 2, 2, 2, 1, 2, 1, 2, 1, 2, 2, 2, 1, 1, 2},
            {2, 2, 2, 2, 2, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 1, 2},
            {2, 1, 2, 1, 1, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 2, 2, 2},
            {2, 1, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2},
            {2, 1, 2, 1, 1, 1, 2, 1, 2, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1},
            {2, 1, 2, 1, 1, 1, 2, 1, 2, 1, 2, 1, 1, 1, 1, 1, 2, 2, 2, 2},
            {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1},
            {1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 2, 2, 1, 1, 1, 1, 1, 1},
            {2, 1, 2, 1, 2, 2, 2, 2, 1, 1, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2},
            {2, 1, 2, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 1, 2},
            {2, 1, 2, 1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2},
            {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 1, 1, 1, 1, 2},
            {2, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 2},
            {2, 1, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2},
            {2, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 2},
            {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}};
    
    private final int BLANK = 0;
    private final int WALL = 1;
    private final int CAKE = 2;
    private final int PLAYER = 3;
    private final int GHOST = 4;
        
    private long mPreviousTime = 0;
    private int mWorkStep = 0;
    private AnimationTimer mTimer = new AnimationTimer() 
    {
        @Override
        public void handle(long now) 
        {
            long elapsed = (now - mPreviousTime);
            if (elapsed > 1) 
            {
                if (redrawTimer()) 
                {
                    mTimer.stop();
                    mWorkStep = 0;
                }
                mPreviousTime = now;
            }
        }
    };
    
    protected int [][] mMaze;
    protected GamePiece[][] mGamePieces;
    protected Canvas mMazeCanvas;
    protected Canvas mGameCanvas;
    protected Player mPlayer;
    protected int mScore = 0;
    
    private int pieceSize = 25;
                        
    
    public Maze()
    {
        mMaze = mazeTemplate;
        mGamePieces = new GamePiece[20][20];
        mMazeCanvas = new Canvas(500, 500);
        mGameCanvas = new Canvas(500, 500);
        addGhosts(2, true);
        startGame();
    }
    
    private void startGame() 
    {
        for (int row = 0; row < mMaze.length; row++) 
        {
            for (int col = 0; col < mMaze.length; col++) 
            {
                if (mMaze[row][col] == WALL) 
                {
                    mGamePieces[row][col] = new Wall(this, row, col);
                    mGamePieces[row][col].draw(mMazeCanvas);
                } 
                
                else if (mMaze[row][col] == BLANK) 
                {
                    mGamePieces[row][col] = new Blank(this, row, col);
                    mGamePieces[row][col].draw(mMazeCanvas);
                }
                
                else if (mMaze[row][col] == CAKE) 
                {
                    mGamePieces[row][col] = new Cake(this, row, col);
                    mGamePieces[row][col].draw(mGameCanvas);                    
                }
                
                else if (mMaze[row][col] == GHOST) 
                {
                    mGamePieces[row][col] = new Ghost(this, row, col);
                    mGamePieces[row][col].draw(mMazeCanvas);                           
                }
                
                else if (mMaze[row][col] == PLAYER) 
                {
                    mPlayer = new Player(this, row, col);
                    mGamePieces[row][col] = mPlayer;
                    mGamePieces[row][col].draw(mGameCanvas);
                }
            }
        }
        mTimer.start();
    }
    
    private boolean redrawTimer() 
    {
        mWorkStep++;
        mGameCanvas.getGraphicsContext2D().clearRect(0, 0, 520, 520);
        redrawGamePieces();
        return false;
    }
    
    private void redrawGamePieces()
    {
        for (int row = 0; row < mMaze.length; row++) 
        {
            for (int col = 0; col < mMaze.length; col++)
            {
                if (mMaze[row][col] == PLAYER || mMaze[row][col] == CAKE)
                {
                    mGamePieces[row][col].draw(mGameCanvas);
                }
                else
                {
                    mGamePieces[row][col].draw(mMazeCanvas);
                }
            }
        }
    }
    
    public void addGhosts(int number, boolean addPlayer)
    {
        while (number >= 0) 
        {
            int randRow = (int) (Math.random() * 20);
            int randCol = (int) (Math.random() * 20);
            if (mMaze[randRow][randCol] == 2) 
            {
                if (number > 0) 
                {
                    mMaze[randRow][randCol] = 4;
                    number--;
                } 
                else if (addPlayer) 
                {
                    mMaze[randRow][randCol] = 3;
                    number--;
                }
            }
        }
    }
    
    public void onRestart()
    {
        mMaze = mazeTemplate;
        mGamePieces = new GamePiece[20][20];
        mMazeCanvas = new Canvas(500, 500);
        mGameCanvas = new Canvas(500, 500);
        addGhosts(2, true);
        startGame();
    }    
    
    public void onPause()
    {
        mTimer.stop();
    }
    
    public void onResume()
    {
        mTimer.start();
    }
}

