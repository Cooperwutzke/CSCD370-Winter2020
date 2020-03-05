/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cwutzkelab8;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Cooper Wutzke
 */
public class CwutzkeLab8 extends Application {
    
    Label mStatus;
    
    Image mX = new Image("images/x.png");
    Image mO = new Image("images/o.png");
    
    @Override
    public void start(Stage primaryStage) {
        
        ImageView XImage = new ImageView(mX);
        XImage.setUserData("X");
        XImage.setOnDragDetected(mouseEvent -> onDragDetected(mouseEvent));
        
        ImageView OImage = new ImageView(mO);
        OImage.setUserData("O");
        OImage.setOnDragDetected(mouseEvent -> onDragDetected(mouseEvent));
        
        HBox xoSelector = new HBox();
        xoSelector.getChildren().addAll(XImage, OImage);
        xoSelector.setAlignment(Pos.CENTER);
        
        GridPane gamePane = buildGamePane();
        
        VBox playArea = new VBox();
        playArea.getChildren().addAll(xoSelector, gamePane);
        
        BorderPane root = new BorderPane();
        root.setCenter(playArea);
        
        // add the menus
        root.setTop(buildMenuBar());
        
        
        mStatus = new Label("Tic Tac Toe Simulator") ;
        ToolBar toolBar = new ToolBar(mStatus) ;
        root.setBottom(toolBar) ;
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Drag and Drop");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private static void onAbout()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION) ;
        alert.setTitle("About") ;
        alert.setHeaderText("Cooper R Wutzke, CSCD 370 Lab 8, Wtr 2020") ;
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
    
    private GridPane buildGamePane()
    {
        GridPane pane = new GridPane();
        
        for (int i = 0; i < 5; i++)
        {
            if (i % 2 == 0)
            {
                for (int j = 0; j < 5; j++)
                {
                    if (j % 2 == 0)
                    {
                        ImageView blank = new ImageView("images/blank.png");
                        blank.setUserData("BLANK");
                        blank.setOnDragOver(dragEvent -> onDragOver(dragEvent));
                        blank.setOnDragDropped(dragEvent -> onDragDropped(dragEvent));
                        pane.add(blank, j, i);
                    }
                    else
                        pane.add(new ImageView("images/vert.png"), j, i);
                }
            }
            else
            {
                for (int j = 0; j < 5; j += 2)
                    pane.add(new ImageView("images/horiz.png"), j, i);
            }
        }
        return pane;
    }
    
    private void onDragDetected(MouseEvent e)
    {
        ImageView piece = (ImageView) e.getSource();
        Dragboard db = piece.startDragAndDrop(TransferMode.COPY);
        db.setDragView(piece.getImage(), piece.getImage().getWidth() / 2, piece.getImage().getHeight() / 2);
        ClipboardContent clipboard = new ClipboardContent();
        clipboard.put(DataFormat.PLAIN_TEXT, piece.getUserData());
        db.setContent(clipboard);
    }
    
    private void onDragOver(DragEvent e)
    {
        ImageView blank = (ImageView) e.getTarget();
        if (blank.getUserData().equals("BLANK"))
        {
            Dragboard db = e.getDragboard();
            if (db.hasString() && db.getString().equals("X") || db.getString().equals("O"))
                e.acceptTransferModes(TransferMode.COPY);
        }
    }
    
    private void onDragDropped(DragEvent event)
    {
        ImageView blank = (ImageView) event.getTarget();
        Dragboard db = event.getDragboard();
        if (db.getString().equals("X"))
        {
            blank.setImage(mX);
            blank.setUserData("X");
        }
        else if (db.getString().equals("O"))
        {
            blank.setImage(mO);
            blank.setUserData("O");
        }
        event.setDropCompleted(true);
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
