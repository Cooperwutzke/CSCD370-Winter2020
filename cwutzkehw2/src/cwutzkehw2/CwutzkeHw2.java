/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cwutzkehw2;

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
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author Cooper Wutzke
 */
public class CwutzkeHw2 extends Application {
    
    Pendulum mPendulum;
    ProgressBar mProgressBar;
    
    
    @Override
    public void start(Stage primaryStage) {
        
        mPendulum = new Pendulum();
        BorderPane root = new BorderPane();
        root.setCenter(mPendulum);
        
        // add the menus
        root.setTop(buildMenuBar());
        
        mProgressBar = new ProgressBar();
        mProgressBar.progressProperty().bind(mPendulum.progressBindingProperty());
        ToolBar toolBar = new ToolBar(mProgressBar) ;
        root.setBottom(toolBar) ;
        
        Scene scene = new Scene(root);
        scene.setOnKeyPressed(keyEvent -> onKey(keyEvent));
        
        primaryStage.setTitle("Pendulum");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private static void onAbout()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION) ;
        alert.setTitle("About") ;
        alert.setHeaderText("Cooper R Wutzke, CSCD 370 Hw2, Wtr 2020") ;
        alert.showAndWait();
    }
    
    public static MenuBar buildMenuBar()
    {
        // build a menu bar
        MenuBar menuBar = new MenuBar() ;
        // File menu with just a quit item for now
        Menu fileMenu = new Menu("_File");
        MenuItem quitMenuItem = new MenuItem("_Quit");
        quitMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.Q,
                KeyCombination.CONTROL_DOWN));
        quitMenuItem.setOnAction(actionEvent -> Platform.exit());
        fileMenu.getItems().add(quitMenuItem);
        // Help menu with just an about item for now
        Menu helpMenu = new Menu("_Help");
        MenuItem aboutMenuItem = new MenuItem("_About");
        aboutMenuItem.setOnAction(actionEvent -> onAbout());
        helpMenu.getItems().add(aboutMenuItem);
        menuBar.getMenus().addAll(fileMenu, helpMenu);
        return menuBar;
    }
    
     private void onKey(KeyEvent event)
    {
        switch (event.getCode())
        {
            case UP:
                mPendulum.setRunning(true);
                break;
            case DOWN:
                mPendulum.setRunning(false);
                break;
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
