/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cwutzkeHw3;

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
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Cooper Wutzke
 */
public class CwutzkeHw3 extends Application {
    
    Label mStatus;
    Scene mScene;
    
    ToggleGroup mViewToggler;
    
    @Override
    public void start(Stage primaryStage) {
        
        BorderPane root = new BorderPane();
        
        ColorPanel top = new ColorPanel("Default Top");
        VBox.setVgrow(top, Priority.ALWAYS);
        top.mPasteSuccess.addListener((observable, oldValue, newValue) -> setStatus(newValue));
        
        ColorPanel bottom = new ColorPanel("Default Bottom");
        VBox.setVgrow(bottom, Priority.ALWAYS);
        bottom.mPasteSuccess.addListener((observable, oldValue, newValue) -> setStatus(newValue));
        
        VBox container = new VBox();
        container.getChildren().addAll(top, bottom);
        root.setCenter(container);
        
        root.setTop(buildMenuBar());
        
        mStatus = new Label("Cooper Wutzke, CSCD 370") ;
        ToolBar toolBar = new ToolBar(mStatus) ;
        root.setBottom(toolBar) ;
        
        mScene = new Scene(root);
        
        primaryStage.setTitle("System Clipboard");
        primaryStage.setScene(mScene);
        primaryStage.show();
    }
    
    private void onAbout()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Cooper Wutzke, CSCD 370 Homework 3, Winter 2020");
        alert.showAndWait();
    }
    
    public MenuBar buildMenuBar()
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
        
        //View menu with our css options
        mViewToggler = new ToggleGroup();
        Menu viewMenu = new Menu("_View");
        RadioMenuItem defaultMenuItem = new RadioMenuItem("_Default");
        defaultMenuItem.setToggleGroup(mViewToggler);
        defaultMenuItem.setSelected(true);
        defaultMenuItem.setOnAction(actionEvent -> changeTheme("modena"));
        RadioMenuItem darkMenuItem = new RadioMenuItem("_Darker");
        darkMenuItem.setToggleGroup(mViewToggler);
        darkMenuItem.setOnAction(actionEvent -> changeTheme("darker"));
        viewMenu.getItems().addAll(defaultMenuItem, darkMenuItem);
        
        // Help menu with just an about item for now
        Menu helpMenu = new Menu("_Help");
        MenuItem aboutMenuItem = new MenuItem("_About");
        aboutMenuItem.setOnAction(actionEvent -> onAbout());
        helpMenu.getItems().add(aboutMenuItem);
        
        // Add the menus
        menuBar.getMenus().addAll(fileMenu, viewMenu, helpMenu);
        return menuBar;
    }
    
    public void setStatus(String newStatus)
    {
        mStatus.setText(newStatus);
    }
    
    private void changeTheme(String style) 
    {
        if (style.equals("darker"))
        {
            String targ = getClass().getResource("style/darker.css").toExternalForm();
            mScene.getStylesheets().add(targ);
        }
        else if (style.equals("modena"))
        {
            mScene.getStylesheets().clear();
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
