package cwutzkelab5;

import com.sun.javaws.Main;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 *
 * @author Cooper Wutzke
 */
public class CwutzkeLab5 extends Application {
    
    private Label mStatus;
    private Canvas mCanvas, mSavedCanvas;
    private Stage mPrimaryStage;
    private Point2D mStartV, mEndV;
    
    private Color mCurColor;
    private double mCurLineWidth;
    
    private VBox mTopPos;
    private BorderPane mMainPane;
    
    private boolean mUnsaved;
    private List<Line> mLines = new ArrayList<Line>();
    private File mFile;
    
    private ToggleGroup mWidthGroup, mColorGroup;
    
    public enum buttonBarPos 
    {
        LEFT, TOP, RIGHT
    }
    buttonBarPos mButtonBarPos = buttonBarPos.LEFT;
    
    @Override
    public void start(Stage primaryStage) {
        
        mPrimaryStage = primaryStage;
        mCanvas = new Canvas(400, 400);
        mSavedCanvas = new Canvas(400, 400);
        mCurColor = Color.BLACK;
        mCurLineWidth = 1.0;
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
                drawLine(mCanvas, Paint.valueOf("black"), 1.0);
            }
        };
        
        EventHandler<MouseEvent> onMouseReleased = (MouseEvent e) -> 
        {
           mEndV = new Point2D(e.getX(), e.getY());

           GraphicsContext context = mCanvas.getGraphicsContext2D();
           context.setStroke(mCurColor);
           context.setLineWidth(mCurLineWidth);
           initCanvas(mCanvas, true);

           if (mSavedCanvas.contains(mEndV))
           {
               Line line = new Line(mStartV, mEndV, mCurLineWidth, mCurColor);
               mLines.add(line);
               line.draw(mSavedCanvas);

               if (!mUnsaved)
                   if (mFile != null)
                       mPrimaryStage.setTitle(mFile.getName() + "*");
                   else
                       mPrimaryStage.setTitle("(untitled)*");

               mUnsaved = true;
           }
        };
        
        mSavedCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, onMousePressed);
        mSavedCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, onMouseDragged);
        mSavedCanvas.addEventHandler(MouseEvent.MOUSE_RELEASED, onMouseReleased);
        
        ScrollPane scroll = new ScrollPane();
        scroll.setContent(pane);
        mMainPane = new BorderPane();
        
        mTopPos = new VBox();
        mTopPos.getChildren().add(buildMenuBar());
        
        mMainPane.setCenter(scroll);
        
        // add the menus
        mMainPane.setTop(mTopPos);
        
        mStatus = new Label("Main Drawing Screen") ;
        ToolBar statusBar = new ToolBar(mStatus) ;
        mMainPane.setBottom(statusBar) ;
       
        // add left button bar
        mMainPane.setLeft(buildButtonBar());
        mMainPane.setRight(null);
        
        Scene scene = new Scene(mMainPane);
        
        primaryStage.setTitle("Draw Lines");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void drawLine(Canvas canvas, Paint color, double lineWidth) 
    {
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.setStroke(color);
        context.setLineWidth(lineWidth);
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
        menuItemSave.setOnAction(actionEvent -> onSave(false));
        menuItemSaveAs.setOnAction(actionEvent -> onSave(true));
        menuItemExit.setOnAction(actionEvent -> onExit());
        
//Width menu
        
        Menu widthMenu = new Menu("_Width:");
        
        RadioMenuItem menuItem1Pixel = new RadioMenuItem("_1 Pixel");
        RadioMenuItem menuItem4Pixel = new RadioMenuItem("_4 Pixel");
        RadioMenuItem menuItem8Pixel = new RadioMenuItem("_8 Pixel");
        
        mWidthGroup = new ToggleGroup();
        
        menuItem1Pixel.setToggleGroup(mWidthGroup);
        menuItem1Pixel.setSelected(true); // 1 Pixel by default
        
        menuItem4Pixel.setToggleGroup(mWidthGroup);
        menuItem8Pixel.setToggleGroup(mWidthGroup);
        
        widthMenu.getItems().add(menuItem1Pixel);
        widthMenu.getItems().add(menuItem4Pixel);
        widthMenu.getItems().add(menuItem8Pixel);
        
        menuItem1Pixel.setOnAction(actionEvent -> onPixel1());
        menuItem4Pixel.setOnAction(actionEvent -> onPixel4());
        menuItem8Pixel.setOnAction(actionEvent -> onPixel8());
        
        widthMenu.setOnShowing(event -> onWidthShowing(true));
        
//Color menu
        Menu colorMenu = new Menu("_Color:");
        
        RadioMenuItem menuItemBlack = new RadioMenuItem("_Black");
        RadioMenuItem menuItemRed = new RadioMenuItem("_Red");
        RadioMenuItem menuItemGreen = new RadioMenuItem("_Green");
        RadioMenuItem menuItemBlue = new RadioMenuItem("_Blue");
        
        mColorGroup = new ToggleGroup();
        
        menuItemBlack.setToggleGroup(mColorGroup);
        menuItemBlack.setSelected(true); // Starting with Black by default
        
        menuItemRed.setToggleGroup(mColorGroup);
        menuItemGreen.setToggleGroup(mColorGroup);
        menuItemBlue.setToggleGroup(mColorGroup);
        
        colorMenu.getItems().add(menuItemBlack);
        colorMenu.getItems().add(menuItemRed);
        colorMenu.getItems().add(menuItemGreen);
        colorMenu.getItems().add(menuItemBlue);
        
        menuItemBlack.setOnAction(actionEvent -> onColorBlack());
        menuItemRed.setOnAction(actionEvent -> onColorRed());
        menuItemGreen.setOnAction(actionEvent -> onColorGreen());
        menuItemBlue.setOnAction(actionEvent -> onColorBlue());
        
        colorMenu.setOnShowing(event -> onColorShowing(true));
   
// Help menu
        Menu helpMenu = new Menu("_Help");
        
        MenuItem aboutMenuItem = new MenuItem("_About");
        
        aboutMenuItem.setOnAction(actionEvent -> onAbout());
        
        helpMenu.getItems().add(aboutMenuItem);
                
        
//MENU COMPILATION
        menuBar.getMenus().addAll(fileMenu, widthMenu, colorMenu, helpMenu);
        return menuBar;
    }
    
    public ToolBar buildButtonBar()
    {
        ToolBar toolbar = new ToolBar();
        toolbar.setOrientation(Orientation.VERTICAL);
        
        Button newButton = new Button();
        newButton.setTooltip(new Tooltip("Create New File"));
        Image newImage = new Image(getClass().getResourceAsStream("toolbarImages/New.png"));
        newButton.setGraphic(new ImageView(newImage));
        
        Button openButton = new Button();
        openButton.setTooltip(new Tooltip("Open File"));
        Image openImage = new Image(getClass().getResourceAsStream("toolbarImages/Open.png"));
        openButton.setGraphic(new ImageView(openImage));
        
        Button saveButton = new Button();
        saveButton.setTooltip(new Tooltip("Save File"));
        Image saveImage = new Image(getClass().getResourceAsStream("toolbarImages/Save.png"));
        saveButton.setGraphic(new ImageView(saveImage));
        
        Button widthButton = new Button();
        widthButton.setTooltip(new Tooltip("Cycle Width size"));
        Image widthImage = new Image(getClass().getResourceAsStream("toolbarImages/Width.png"));
        widthButton.setGraphic(new ImageView(widthImage));
        
        Button colorButton = new Button();
        colorButton.setTooltip(new Tooltip("Cycle Draw Color"));
        Image colorImage = new Image(getClass().getResourceAsStream("toolbarImages/Color.png"));
        colorButton.setGraphic(new ImageView(colorImage));
        
        Button moveButton = new Button();
        moveButton.setTooltip(new Tooltip("Move this Toolbar"));
        Image moveImage = new Image(getClass().getResourceAsStream("toolbarImages/Move.png"));
        moveButton.setGraphic(new ImageView(moveImage));
        
        Separator newSeparator = new Separator(Orientation.HORIZONTAL);
        Separator openSeparator = new Separator(Orientation.HORIZONTAL);
        Separator saveSeparator = new Separator(Orientation.HORIZONTAL);
        Separator widthSeparator = new Separator(Orientation.HORIZONTAL);
        Separator colorSeparator = new Separator(Orientation.HORIZONTAL);
        Separator moveSeparator = new Separator(Orientation.HORIZONTAL);
        
        toolbar.getItems().add(newButton);
        toolbar.getItems().add(newSeparator);
        newButton.setOnAction(actionEvent -> onNew());
        
        toolbar.getItems().add(openButton);
        toolbar.getItems().add(openSeparator);
        openButton.setOnAction(actionEvent -> onOpen());
        
        toolbar.getItems().add(saveButton);
        toolbar.getItems().add(saveSeparator);
        saveButton.setOnAction(actionEvent -> onSaveIcon());
        
        toolbar.getItems().add(widthButton);
        toolbar.getItems().add(widthSeparator);
        widthButton.setOnAction(actionEvent -> onNextPixel());
        
        toolbar.getItems().add(colorButton);
        toolbar.getItems().add(colorSeparator);
        colorButton.setOnAction(actionEvent -> onNextColor());
        
        toolbar.getItems().add(moveButton);
        toolbar.getItems().add(moveSeparator);
        moveButton.setOnAction(actionEvent -> onMove());
        
        return toolbar;
    }
    
    private static void onAbout()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION) ;
        alert.setTitle("About") ;
        alert.setHeaderText("Cooper R Wutzke, CSCD 370 Lab 5, Wtr 2020") ;
        alert.showAndWait();
    }
    
    private void onNew()
    {
         if (mUnsaved)
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Unsaved Changes");
            alert.setHeaderText("Discard unsaved changes?");
            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.get() == ButtonType.OK)
            {
                initCanvas(mSavedCanvas, false);
                resetFileVariables();
            }
        }
        else
        {
            initCanvas(mSavedCanvas, false);
            resetFileVariables();
        }
        setStatus("The 'onNew' Handler triggered!");
        //initCanvas(mCanvas, true);        If we want to also clear the temp canvas on new
    }
    
    private void onOpen()
    {
        boolean canceled = false;
        if (mUnsaved)
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Unsaved Changes");
            alert.setHeaderText("Save changes?");
            ButtonType buttonYes = new ButtonType("Yes");
            ButtonType buttonNo = new ButtonType("No");
            ButtonType buttonCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonYes, buttonNo, buttonCancel);
            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.get() == buttonYes)
                this.onSave(false);
            if (result.get() == buttonCancel)
                canceled = true;
        }
        
        if (!canceled)
        {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open File");
            fileChooser.getExtensionFilters().add(new ExtensionFilter("Draw File", "*.draw"));
            
            File file = fileChooser.showOpenDialog(mPrimaryStage);
            
            if (file != null)
            {
                try
                {
                    initCanvas(mSavedCanvas, false);
                    
                    FileInputStream fileIn = new FileInputStream(file);
                    
                    ObjectInputStream fileReader = new ObjectInputStream(fileIn);
                    mLines = (ArrayList<Line>) fileReader.readObject();
                    
                    mLines.forEach((rect) -> { rect.draw(mSavedCanvas); });
                    
                    mUnsaved = false;
                    mFile = file;
                    mPrimaryStage.setTitle(file.getName());
                }
                catch (Exception e)
                {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Open File Exception", e.getMessage());
                }
            }
        }
        setStatus("The 'onOpen' Handler triggered!");
    }
    
    private void onSave(boolean saveAs)
    {
        File selected = mFile;
        
        if (mFile == null || saveAs)
        {
            FileChooser fileChooser = new FileChooser();
            
            fileChooser.setTitle("Save File");
            fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Draw File","*.draw"), new ExtensionFilter("All Files","*.*"));
            
            if (mFile != null)
                fileChooser.setInitialFileName(mFile.getName());
            
            selected = fileChooser.showSaveDialog(mPrimaryStage);
        }
        
        if (selected != null)
        {
            try
            {
                FileOutputStream fileOut = new FileOutputStream(selected);
                
                ObjectOutputStream fileWriter = new ObjectOutputStream(fileOut);
                fileWriter.writeObject(mLines);
                
                
            } 
            catch (Exception e)
            {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Save File Exception", e.getMessage());
            }
            
            mFile = selected;
            mPrimaryStage.setTitle(mFile.getName());
            mUnsaved = false;
        }
        setStatus("The 'onSave' Handler triggered!");
    }
    
    private void onSaveIcon()
    {
        if (mFile == null)
            this.onSave(true);
        else
            this.onSave(false);
    }
    
    private void onExit()
    {
        boolean canceled = false;
        if (mUnsaved)
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Unsaved Changes");
            alert.setHeaderText("Save changes?");
            ButtonType buttonYes = new ButtonType("Yes");
            ButtonType buttonNo = new ButtonType("No");
            ButtonType buttonCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonYes, buttonNo, buttonCancel);
            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.get() == buttonYes)
                this.onSave(false);
            if (result.get() == buttonCancel)
                canceled = true;
        }
        
        System.out.println(canceled);
        if (!canceled)
            Platform.exit();
    }
    
    private void resetFileVariables()
    {
        mLines = new ArrayList<Line>();
        mUnsaved = false;
        mFile = null;
        mPrimaryStage.setTitle("(untitled)");
    }
    
    private void onPixel1()
    {
        setStatus("The 'onPixel1' Handler triggered!");
        mCurLineWidth = 1.0;
    }
    
    private void onPixel4()
    {
        setStatus("The 'onPixel4' Handler triggered!");
        mCurLineWidth = 4.0;
    }
    
    private void onPixel8()
    {
        setStatus("The 'onPixel8' Handler triggered!");
        mCurLineWidth = 8.0;
    }
    
    private void onNextPixel()
    {
        setStatus("The 'onNextPixel' Handler triggered!");
        if(mCurLineWidth == 1.0)
        {
            mCurLineWidth = 4.0;
        }
        
        else if (mCurLineWidth == 4.0)
        {
            mCurLineWidth = 8.0;
        }
        
        else if (mCurLineWidth == 8.0)
        {
            mCurLineWidth = 1.0;
        } 
    }
    
    private void onWidthShowing(boolean showing)
    {
        if (showing)
        {
            setStatus("The 'onWidthShowing' Handler triggered!");
            if (mCurLineWidth == 1.0)
            {
                Toggle toggle = mWidthGroup.getToggles().get(0);
                mWidthGroup.selectToggle(toggle);
            }
            
            else if (mCurLineWidth == 4.0)
            {
                Toggle toggle = mWidthGroup.getToggles().get(1);
                mWidthGroup.selectToggle(toggle);
            }
            
            else if (mCurLineWidth == 8.0)
            {
                Toggle toggle = mWidthGroup.getToggles().get(2);
                mWidthGroup.selectToggle(toggle);
            }
        }
    }
    
    private void onColorBlack()
    {
        setStatus("The 'onColorBlack' Handler triggered!");
        mCurColor = Color.BLACK;
    }
    
    private void onColorRed()
    {
        setStatus("The 'onColorRed' Handler triggered!");
        mCurColor = Color.RED;
    }
    
    private void onColorGreen()
    {
        setStatus("The 'onColorGreen' Handler triggered!");
        mCurColor = Color.GREEN;
    }
    
    private void onColorBlue()
    {
        setStatus("The 'onColorBlue' Handler triggered!");
        mCurColor = Color.BLUE;
    }
    
    private void onNextColor()
    {
        setStatus("The 'onNextColor' Handler triggered!");
        if (mCurColor.equals(Color.BLACK))
        {
            mCurColor = Color.RED;
        }
        
        else if (mCurColor.equals(Color.RED))
        {
            mCurColor = Color.GREEN;
        }
        
        else if (mCurColor.equals(Color.GREEN))
        {
            mCurColor = Color.BLUE;
        }
        
        else if (mCurColor.equals(Color.BLUE))
        {
            mCurColor = Color.BLACK;
        }
    }
    
    private void onColorShowing(boolean showing)
    {
        if (showing)
        {
            setStatus("The 'onColorShowing' Handler triggered!");
            if (mCurColor.equals(Color.BLACK))
            {
                Toggle toggle = mColorGroup.getToggles().get(0);
                mColorGroup.selectToggle(toggle);
            }
            
            else if (mCurColor.equals(Color.RED))
            {
                Toggle toggle = mColorGroup.getToggles().get(1);
                mColorGroup.selectToggle(toggle);
            }
            
            else if (mCurColor.equals(Color.GREEN))
            {
                Toggle toggle = mWidthGroup.getToggles().get(2);
                mColorGroup.selectToggle(toggle);
            }
            
            else if (mCurColor.equals(Color.BLUE))
            {
                Toggle toggle = mColorGroup.getToggles().get(3);
                mColorGroup.selectToggle(toggle);
            }
        }
    }
    
    public void onMove()
    {
        setStatus("The 'onMove' Handler triggered!");
        switch (mButtonBarPos)
        {
            case LEFT:
            {
                // Reset left 
                mMainPane.setLeft(null);

                // Clear then add top menu and button bar
                mTopPos.getChildren().clear();
                ToolBar buttonBar = buildButtonBar();
                buttonBar.setOrientation(Orientation.HORIZONTAL);
                mTopPos.getChildren().addAll(buildMenuBar(), buttonBar);
                mMainPane.setTop(mTopPos);
                mButtonBarPos = buttonBarPos.TOP;
                break;
            }
            case TOP:
            {
                // Redraw menu bar
                mMainPane.setTop(null);
                mMainPane.setTop(buildMenuBar());
                
                // Draw right button bar
                mMainPane.setRight(buildButtonBar());
                mButtonBarPos = buttonBarPos.RIGHT;
                break;
            }
            case RIGHT:
            {
                // Erase Right
                mMainPane.setRight(null);
                // Redraw top menu bar
                mMainPane.setTop(null);
                mMainPane.setTop(buildMenuBar());
                
                // Draw left button bar
                mMainPane.setLeft(buildButtonBar());
                mButtonBarPos = buttonBarPos.LEFT;
                break;
            }
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