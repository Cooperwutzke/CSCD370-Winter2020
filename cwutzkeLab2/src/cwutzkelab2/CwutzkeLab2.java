/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cwutzkelab2;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

/**
 *
 * @author Cooper Wutzke
 */
public class CwutzkeLab2 extends Application {
    
    Label mStatus;
    Canvas mCanvas, mSavedCanvas;
    Point2D mStartV, mEndV;
    
    @Override
    public void start(Stage primaryStage) {
        
        mCanvas = new Canvas(400, 400);
        mSavedCanvas = new Canvas(400, 400);
        initCanvas(mCanvas, true);
        
        StackPane pane = new StackPane();
        pane.getChildren().add(mCanvas);
        pane.getChildren().add(mSavedCanvas);
        
        EventHandler<MouseEvent> onMousePressed = (MouseEvent e) -> 
        {
            mStartV = new Point2D(e.getX(), e.getY());
            mEndV = new Point2D(e.getX(), e.getY());
            setStatus("Points set: X = " + mStartV.getX() + " Y = " + mStartV.getY());
        };
        
        EventHandler<MouseEvent> onMouseDragged = (MouseEvent e) -> 
        {
            mEndV = new Point2D(e.getX(), e.getY());
            setStatus("Start: X = " + mStartV.getX() + " Y = " + mStartV.getY() + 
                      " End: X = " + mEndV.getX() + " Y = " + mEndV.getY());
            initCanvas(mCanvas, true);
            if ((mEndV.getX() < 400 && mEndV.getX() > 0) && (mEndV.getY() < 400 && mEndV.getY() > 0))
            {
                drawLine(mCanvas, Paint.valueOf("black"));
            }
        };
        
        EventHandler<MouseEvent> onMouseReleased = (MouseEvent e) -> 
        {
            if ((mEndV.getX() < 400 && mEndV.getX() > 0) && (mEndV.getY() < 400 && mEndV.getY() > 0))
            {
                mEndV = new Point2D(e.getX(), e.getY());
                drawLine(mSavedCanvas, Paint.valueOf("red"));
                setStatus("Mouse click was released!\n Start: X = " + mStartV.getX() + " Y = " + mStartV.getY() + 
                          " End: X = " + mEndV.getX() + " Y = " + mEndV.getY());
            }
        };
        
        mSavedCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, onMousePressed);
        mSavedCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, onMouseDragged);
        mSavedCanvas.addEventHandler(MouseEvent.MOUSE_RELEASED, onMouseReleased);
        
        ScrollPane scroll = new ScrollPane();
        scroll.setContent(pane);
        BorderPane root = new BorderPane();
        root.setCenter(scroll);
        
        //btn.prefWidthProperty().bind(primaryStage.widthProperty().divide(2));
        // add the menus
        root.setTop(buildMenuBar());
        
        
        mStatus = new Label("Main Drawing Screen") ;
        ToolBar toolBar = new ToolBar(mStatus) ;
        root.setBottom(toolBar) ;
        
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("Draw Lines");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
     private void drawLine(Canvas canvas, Paint color) 
    {
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.setStroke(color);
        context.strokeLine(mStartV.getX(), mStartV.getY(), mEndV.getX(), mEndV.getY());
    }
    
    private static void initCanvas(Canvas targCanvas, boolean isTempCanvas)
    {
        if (isTempCanvas)
        {
            GraphicsContext context = targCanvas.getGraphicsContext2D();
            context.setFill(Paint.valueOf("white"));
            context.fillRect(targCanvas.getLayoutX(), targCanvas.getLayoutY(), targCanvas.getWidth(), targCanvas.getHeight());
        }
        else
        {
            GraphicsContext context = targCanvas.getGraphicsContext2D();
            context.clearRect(targCanvas.getLayoutX(), targCanvas.getLayoutY(), targCanvas.getWidth(), targCanvas.getHeight());
        }
    }
        
    public MenuBar buildMenuBar()
    {
        // build a menu bar
        MenuBar menuBar = new MenuBar() ;
        
// File menu
        Menu fileMenu = new Menu("_File");
        
        MenuItem menuItemNew = new MenuItem("_New");
        MenuItem menuItemOpen = new MenuItem("_Open");
        MenuItem menuItemSave = new MenuItem("_Save");
        MenuItem menuItemSaveAs = new MenuItem("Save _As");
        MenuItem menuItemExit = new MenuItem("_Exit");
        
        menuItemNew.setAccelerator(new KeyCodeCombination(KeyCode.N,
                KeyCombination.CONTROL_DOWN));
        menuItemOpen.setAccelerator(new KeyCodeCombination(KeyCode.O,
                KeyCombination.CONTROL_DOWN));
        menuItemSave.setAccelerator(new KeyCodeCombination(KeyCode.S,
                KeyCombination.CONTROL_DOWN));
        menuItemSaveAs.setAccelerator(new KeyCodeCombination(KeyCode.A,
                KeyCombination.CONTROL_DOWN));
        menuItemExit.setAccelerator(new KeyCodeCombination(KeyCode.X,
                KeyCombination.CONTROL_DOWN));
        
        SeparatorMenuItem dottedLine = new SeparatorMenuItem();
                
        fileMenu.getItems().add(menuItemNew);
        fileMenu.getItems().add(menuItemOpen);
        fileMenu.getItems().add(menuItemSave);
        fileMenu.getItems().add(menuItemSaveAs);
        fileMenu.getItems().add(dottedLine);
        fileMenu.getItems().add(menuItemExit);
        
        menuItemNew.setOnAction(actionEvent -> onNew());
        menuItemOpen.setOnAction(actionEvent -> onOpen());
        menuItemSave.setOnAction(actionEvent -> onSave());
        menuItemSaveAs.setOnAction(actionEvent -> onSave());
        menuItemExit.setOnAction(actionEvent -> Platform.exit());
        
//Width menu
        
        Menu widthMenu = new Menu("_Width:");
        
        RadioMenuItem menuItem1Pixel = new RadioMenuItem("_1 Pixel");
        RadioMenuItem menuItem4Pixel = new RadioMenuItem("_4 Pixel");
        RadioMenuItem menuItem8Pixel = new RadioMenuItem("_8 Pixel");
        
        widthMenu.getItems().add(menuItem1Pixel);
        widthMenu.getItems().add(menuItem4Pixel);
        widthMenu.getItems().add(menuItem8Pixel);
        
        menuItem1Pixel.setOnAction(actionEvent -> onPixel());
        menuItem4Pixel.setOnAction(actionEvent -> onPixel());
        menuItem8Pixel.setOnAction(actionEvent -> onPixel());
        
        
//Color menu
        Menu colorMenu = new Menu("_Color:");
        
        RadioMenuItem menuItemBlack = new RadioMenuItem("_Black");
        RadioMenuItem menuItemRed = new RadioMenuItem("_Red");
        RadioMenuItem menuItemGreen = new RadioMenuItem("_Green");
        RadioMenuItem menuItemBlue = new RadioMenuItem("_Blue");
        
        colorMenu.getItems().add(menuItemBlack);
        colorMenu.getItems().add(menuItemRed);
        colorMenu.getItems().add(menuItemGreen);
        colorMenu.getItems().add(menuItemBlue);
        
        menuItemBlack.setOnAction(actionEvent -> onColor());
        menuItemRed.setOnAction(actionEvent -> onColor());
        menuItemGreen.setOnAction(actionEvent -> onColor());
        menuItemBlue.setOnAction(actionEvent -> onColor());
   
// Help menu
        Menu helpMenu = new Menu("_Help");
        
        MenuItem aboutMenuItem = new MenuItem("_About");
        
        aboutMenuItem.setOnAction(actionEvent -> onAbout());
        
        helpMenu.getItems().add(aboutMenuItem);
                
        
//MENU COMPILATION
        menuBar.getMenus().addAll(fileMenu, widthMenu, colorMenu, helpMenu);
        return menuBar;
    }
    
    private static void onAbout()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION) ;
        alert.setTitle("About") ;
        alert.setHeaderText("Cooper R Wutzke, CSCD 370 Lab 2, Wtr 2020") ;
        alert.showAndWait();
    }
    
    private void onNew()
    {
        setStatus("The 'On New' Handler triggered!");
        //initCanvas(mCanvas, true);        If we want to also clear the temp canvas on new
        initCanvas(mSavedCanvas, false);
    }
    
    private void onOpen()
    {
        setStatus("The 'On Open' Handler triggered!");
    }
    
    private void onSave()
    {
        setStatus("The 'On Save' Handler triggered!");
    }
    
    private void onPixel()
    {
        setStatus("The 'On Pixel' Handler triggered!");
    }
    
    private void onColor()
    {
        setStatus("The 'On Color' Handler triggered!");
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
