/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cwutzkeHw3;

import java.util.Optional;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *
 * @author Cooper Wutzke
 */
public class ColorPanel extends Region
{
    private static final DataFormat DATA_FORMAT = new DataFormat("cwutzkeHw3/ColorPanelData");
    
    private final Label mText = new Label();
    private final Rectangle mBackground = new Rectangle();
    
    private final ContextMenu mContext = new ContextMenu();
    
    public SimpleStringProperty mPasteSuccess = new SimpleStringProperty("");
    
    public ColorPanel(String text)
    {
        this.setPrefSize(700, 150);
        
        this.buildContextMenu();
        
        Font font = Font.font("Arial", FontWeight.BOLD, 20);
        mText.setText(text);
        mText.setAlignment(Pos.CENTER);
        mText.setFont(font);
        
        mBackground.setFill(randomColor());
        
        getChildren().addAll(mBackground, mText);
        addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> onMousePressed(mouseEvent));
    }
    
    private Color randomColor()
    {
        double opacity = Math.random();
        if (opacity < 0.5)
            opacity = 1.0 - opacity;
        
        return new Color(Math.random(), Math.random(), Math.random(), opacity);
    }
    
    public String getText() 
    { 
        return mText.getText(); 
    }
    
    public void setBackgroundColor(Color color) 
    { 
        mBackground.setFill(color); 
    }
    
    private void buildContextMenu()
    {
        MenuItem changeTextMenuItem = new MenuItem("_Change Text");
        changeTextMenuItem.setOnAction(actionEvent -> changeText());
        MenuItem changeColorMenuItem = new MenuItem("_Change Color");
        changeColorMenuItem.setOnAction(actionEvent -> changeColor());
        MenuItem copySettingsMenuItem = new MenuItem("_Copy Settings");
        copySettingsMenuItem.setOnAction(actionEvent -> copySettings());
        MenuItem pasteSettingsMenuItem = new MenuItem("_Paste Settings");
        pasteSettingsMenuItem.setOnAction(actionEvent -> pasteSettings());
        mContext.getItems().addAll(changeTextMenuItem, changeColorMenuItem, copySettingsMenuItem, pasteSettingsMenuItem);
    }
    
    private void onMousePressed(MouseEvent event)
    {
        if (event.isSecondaryButtonDown())
            mContext.show(this, event.getScreenX(), event.getScreenY());
    }
    
    private void changeText()
    {
        TextField text = new TextField();
        text.setPrefWidth(250);
        text.setText(mText.getText());
        text.selectAll();
        
        Alert alert = new Alert(AlertType.NONE);
        ButtonType buttonOK = ButtonType.OK;
        ButtonType buttonCancel = ButtonType.CANCEL;
        alert.getButtonTypes().addAll(buttonOK, buttonCancel);
        alert.setTitle("Change Text");
        alert.setHeaderText("Enter Text:");
        alert.setGraphic(text);
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get().equals(ButtonType.OK))
            mText.setText(text.getText());
    }
    
    private void changeColor() 
    { 
        mBackground.setFill(randomColor()); 
    }
    
    public Paint getColor() 
    { 
        return mBackground.getFill(); 
    }
    
    private void copySettings()
    {
        Color color = (Color) mBackground.getFill();
        ColorPanelData data = new ColorPanelData(mText.getText(), color.getRed(), color.getGreen(), color.getBlue(), color.getOpacity());
        
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.put(DATA_FORMAT, data);
        content.put(DataFormat.PLAIN_TEXT, data.toString());
        clipboard.setContent(content);
    }
    
    private void pasteSettings()
    {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        if (clipboard.hasContent(DATA_FORMAT))
        {
            ColorPanelData data = (ColorPanelData) clipboard.getContent(DATA_FORMAT);
            mText.setText(data.getText());
            mBackground.setFill(data.getColor());
            mPasteSuccess.set("Cooper Wutzke, CSCD 370");
        }
        else if (clipboard.hasString())
        {
            String string = clipboard.getString();
            string = string.trim();
            if (!string.equals(""))
            {
                mText.setText(string);
                mPasteSuccess.set("Cooper Wutzke, CSCD 370");
            }
            else
                mPasteSuccess.set("Paste Failure");
        }
        else
            mPasteSuccess.set("Paste Failure");
    }
    
    @Override
    protected void layoutChildren()
    {
        mBackground.setWidth(getWidth());
        mBackground.setHeight(getHeight());
        
        layoutInArea(mText, 0, 0, getWidth(), getHeight(), getBaselineOffset(), Insets.EMPTY, HPos.CENTER, VPos.CENTER);
    }
}