/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cwutzkelab1;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
public class CwutzkeLab1 extends Application  implements ChangeListener<String>
{
    ImageView view;
    Label status;
    
    @Override
    public void start(Stage primaryStage) {
        view = new ImageView();
        Image center = new Image(getClass().getResourceAsStream("logo.png"));
        view.setImage(center);
        
        ListView<String> photoView = new ListView<String>();
        photoView.setPrefWidth(computeStringWidth("First Album"));
        
        BorderPane root = new BorderPane();
        root.setCenter(view);
        root.setLeft(photoView);
        
        ArrayList<String> descriptors = new ArrayList<String>();
        descriptors.add("First Album");
        descriptors.add("Cindy");
        descriptors.add("Fred");
        descriptors.add("Kate");
        descriptors.add("Keith");
        descriptors.add("Matt");
        descriptors.add("Ricky");
        
        photoView.setItems(FXCollections.observableArrayList(descriptors));
        
        photoView.getSelectionModel().selectedItemProperty().addListener(this);
        
        
        // add the menus
        root.setTop(buildMenuBar());
        
        
        status = new Label("Everything is Copacetic") ;
        ToolBar toolBar = new ToolBar(status) ;
        root.setBottom(toolBar) ;
        
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private static void onAbout()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION) ;
        alert.setTitle("About") ;
        alert.setHeaderText("Cooper R Wutzke, CSCD 370 Lab 1, Wtr 2020") ;
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
    
    public void setStatus(String newStatus)
    {
        this.status.setText(newStatus);
    }
    
    @Override
    public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) 
    {
        String imageName = arg2.toLowerCase();
        if (imageName.matches("cindy"))
        {
            Image image = new Image(getClass().getResourceAsStream("cindy.png"));
            view.setImage(image);
            status.setText("Cindy Wilson (Percussion since 1976)");
        }
        if (imageName.matches("fred"))
        {
            Image image = new Image(getClass().getResourceAsStream("fred.png"));
            view.setImage(image);
            status.setText("Fred Schneider (Vocals, Cowbell, since 1976)");
        }
        if (imageName.matches("kate"))
        {
            Image image = new Image(getClass().getResourceAsStream("kate.png"));
            view.setImage(image);
            status.setText("Kate Pierson (Organ since 1976)");
        }
        if (imageName.matches("keith"))
        {
            Image image = new Image(getClass().getResourceAsStream("keith.png"));
            view.setImage(image);
            status.setText("Keith Strickland (Drums, Guitar, since 1976)");
        }
        if (imageName.matches("first album"))
        {
            Image image = new Image(getClass().getResourceAsStream("logo.png"));
            view.setImage(image);
            status.setText("First Album, 1979");
        }
        if (imageName.matches("matt"))
        {
            Image image = new Image(getClass().getResourceAsStream("matt.png"));
            view.setImage(image);
            status.setText("Matt Flynn (Touring, Drums, prior to 2004");
        }
        if (imageName.matches("rickey"))
        {
            Image image = new Image(getClass().getResourceAsStream("rickey.png"));
            view.setImage(image);
            status.setText("Ricky Wilson, deceased (Bass, 1976-1985)");
        }            
    }
    
    private double computeStringWidth(String s)
    {
        Text text = new Text(s);
        return text.getLayoutBounds().getWidth();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
