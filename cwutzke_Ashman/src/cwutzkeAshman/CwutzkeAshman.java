/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cwutzkeAshman;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Cooper Wutzke
 */
public class CwutzkeAshman extends Application {
    
    Label mStatus;
    Maze mMaze;
    
    @Override
    public void start(Stage primaryStage) {
        mMaze = new Maze(new Canvas(520,520), new Canvas(520, 520), 0);
        
        BorderPane root = new BorderPane();
        StackPane pane = new StackPane(mMaze.mBoardCanvas, mMaze.mGameCanvas);
        
        // add the menus
        root.setTop(buildMenuBar());
        
        
        mStatus = new Label("Ready to play ASHMAN");
        ToolBar toolBar = new ToolBar(mStatus) ;
        root.setBottom(toolBar) ;
        
        Scene scene = new Scene(root);
        
        scene.setOnKeyPressed(event ->
        {
            Direction direction = Direction.stop;
            switch (event.getCode()){
                case UP:
                    direction = Direction.down;
                    break;
                case DOWN:
                    direction = Direction.up;
                    break;
                case LEFT:
                    direction = Direction.left;
                    break;
                case RIGHT:
                    direction = Direction.right;
                    break;
            }
            mMaze.mElements.get(0).setDirection(direction);
        });
        
        primaryStage.setTitle("Ashman");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(true);
    }
    
    private static void onAbout()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION) ;
        alert.setTitle("About") ;
        alert.setHeaderText("Cooper R Wutzke, CSCD 370 Ashman Final, Wtr 2020") ;
        alert.showAndWait();
    }
    
    public static MenuBar buildMenuBar()
    {
        // build a menu bar
        MenuBar menuBar = new MenuBar();
        
        // File menu
        Menu fileMenu = new Menu("_File");
        MenuItem quitMenuItem = new MenuItem("_Quit");
        quitMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.Q,
                KeyCombination.CONTROL_DOWN));
        quitMenuItem.setOnAction(actionEvent -> Platform.exit());
        fileMenu.getItems().add(quitMenuItem);
        
        // Game menu
        Menu gameMenu = new Menu("_Game");
        MenuItem newMenuItem = new MenuItem("_New");
        newMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        newMenuItem.setOnAction(actionEvent -> {
            mMaze.reset();
        });
        MenuItem pauseMenuItem = new MenuItem("_Pause");
        pauseMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN));
        pauseMenuItem.setDisable(true);
        MenuItem goMenuItem = new MenuItem("_Go");
        goMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.G, KeyCombination.CONTROL_DOWN));
        pauseMenuItem.setOnAction(actionEvent -> {
            mMaze.pause();
            pauseMenuItem.setDisable(true);
            goMenuItem.setDisable(false);
        });
        goMenuItem.setOnAction(actionEvent -> {
            mMaze.resume();
            goMenuItem.setDisable(true);
            pauseMenuItem.setDisable(false);
        });
        
        // Help menu
        Menu helpMenu = new Menu("_Help");
        MenuItem aboutMenuItem = new MenuItem("_About");
        aboutMenuItem.setOnAction(actionEvent -> onAbout());
        helpMenu.getItems().add(aboutMenuItem);
        menuBar.getMenus().addAll(fileMenu, helpMenu);
        return menuBar;
    }
    
    public void setStatus(String newStatus)
    {
        mStatus.setText(newStatus);
    }
    
    private boolean doSomeWork() 
    {
        mWorkStep++;
        mGameCanvas.getGraphicsContext2D().clearRect(0, 0, 520, 520);
        mElements.forEach(element -> element.draw(mGameCanvas));
        return ghostCollision() || score == cakeCount;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
