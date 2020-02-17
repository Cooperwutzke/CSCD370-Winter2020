/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cwutzkeLab6;

import java.io.IOException;
import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Cooper Wutzke
 */
public class CwutzkeLab6 extends Application {
    
    Label mStatus;
    
    @Override
    public void start(Stage primaryStage) {
        Text prompt = new Text("Do File -> Options to change this");
        
        BorderPane root = new BorderPane();
        root.setCenter(prompt);
                
        // add the menus
        root.setTop(buildMenuBar());
        
        
        mStatus = new Label("Everything is Copacetic") ;
        ToolBar toolBar = new ToolBar(mStatus) ;
        root.setBottom(toolBar) ;
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("FXML Layout");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static MenuBar buildMenuBar()
    {
        // build a menu bar
        MenuBar menuBar = new MenuBar() ;
        
        // File menu
        Menu fileMenu = new Menu("_File");
        MenuItem quitMenuItem = new MenuItem("_Quit");
        quitMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.Q,
                KeyCombination.CONTROL_DOWN));
        quitMenuItem.setOnAction(actionEvent -> Platform.exit());
        
        MenuItem optionsMenuItem = new MenuItem("_Options");
        optionsMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.O,
                KeyCombination.CONTROL_DOWN));
        optionsMenuItem.setOnAction(actionEvent -> onOptions());
        
        fileMenu.getItems().addAll(optionsMenuItem, quitMenuItem);
        
        // Help menu with just an about item for now
        Menu helpMenu = new Menu("_Help");
        MenuItem aboutMenuItem = new MenuItem("_About");
        aboutMenuItem.setOnAction(actionEvent -> onAbout());
        helpMenu.getItems().add(aboutMenuItem);
        
        // Add menus to menubar
        menuBar.getMenus().addAll(fileMenu, helpMenu);
        
        return menuBar;
    }
    
    private static void onAbout()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION) ;
        alert.setTitle("About") ;
        alert.setHeaderText("Cooper R Wutzke, CSCD 370 Lab 6, Wtr 2020") ;
        alert.showAndWait();
    }
    
    private static void onOptions()
    {
        try
        {
            OptionsLayoutController options = new OptionsLayoutController();
            Optional<ButtonType> opt = options.showAndWait();
            if (opt.isPresent())
            {
                ButtonData result = opt.get().getButtonData();
                if (result.equals(ButtonData.OK_DONE))
                { 
                    AlertType alert = AlertType.WARNING;
                    Alert exceptionAlert = new Alert(alert, "Hit Ok");
                    exceptionAlert.showAndWait();
                }
            }
        }
        catch (IOException e)
        {
            AlertType alert = AlertType.WARNING;
            Alert exceptionAlert = new Alert(alert, e.getMessage());
            exceptionAlert.showAndWait();
        }
    }
    
    public void setStatus(String newStatus)
    {
        mStatus.setText(newStatus);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
