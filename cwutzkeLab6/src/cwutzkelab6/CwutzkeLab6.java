package cwutzkeLab6;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Cooper Wutzke
 */
public class CwutzkeLab6 extends Application {
    
    Label mStatus;
    Text mPrompt;
    OptionsData mOptionsData;
    
    @Override
    public void start(Stage primaryStage) {
        mPrompt = new Text("Do File -> Options to change this");
        
        BorderPane root = new BorderPane();
        root.setCenter(mPrompt);
                
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
    
    public MenuBar buildMenuBar()
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
    
    private void onOptions()
    {
        try
        {
            mOptionsData = new OptionsData();
            OptionsLayoutController options = new OptionsLayoutController(mOptionsData);
            Optional<ButtonType> opt = options.showAndWait();
            if (opt.isPresent() && opt.get().getButtonData() == ButtonData.OK_DONE)
            {
                Font font;
                if (mOptionsData.bold && !mOptionsData.italics)
                {
                    font = Font.font("Monospaced", FontWeight.BOLD, mOptionsData.size);
                }
                else if (mOptionsData.italics && !mOptionsData.bold)
                {
                    font = Font.font("Monospaced", FontPosture.ITALIC, mOptionsData.size);
                }
                else if (mOptionsData.bold && mOptionsData.italics)
                {
                    font = Font.font("Monospaced", FontWeight.BOLD, FontPosture.ITALIC, mOptionsData.size);
                }
                else
                {
                    font = Font.font("Monospaced", mOptionsData.size);
                }
                
                mPrompt.setFont(font);
                if (mOptionsData.showString == true)
                    mPrompt.setText(mOptionsData.userString);
                else
                {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss z");
                    Date date = new Date();
                    String showDate = dateFormat.format(date);
                    mPrompt.setText(showDate);
                }
            }
        }
        catch (IOException e)
        {
            setStatus("IOException occured" + e.getMessage());
        }
    }
    
    public void setStatus(String newStatus)
    {
        mStatus.setText(newStatus);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        launch(args);
    }
}