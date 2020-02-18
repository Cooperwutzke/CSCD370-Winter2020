/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cwutzkeLab6;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author cooper.wutzke
 */
public class OptionsLayoutController extends Dialog<ButtonType> implements Initializable
{
    private final ButtonType mOkButton;
    private static OptionsData mOptionsDataController;
    
    @FXML
    private RadioButton dateTimeRadio;
    @FXML
    private RadioButton showStringRadio;
    @FXML
    private CheckBox italicCheckbox;
    @FXML
    private CheckBox boldCheckbox;
    @FXML
    private TextField inputTextfield;
    @FXML
    private TextField inputSizeTextfield;
    
    public OptionsLayoutController(OptionsData options) throws IOException
    {
        super();
        mOptionsDataController = options;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("OptionsLayout.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        getDialogPane().setContent(root);
        
        
        mOkButton = new ButtonType("Ok", ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().add(mOkButton);
        Button ok = (Button) getDialogPane().lookupButton(mOkButton);
        ok.addEventFilter(ActionEvent.ACTION, actionEvent -> onOkButton(actionEvent));
        
        ButtonType cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        getDialogPane().getButtonTypes().add(cancelButton);
        
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        boldCheckbox.setSelected(mOptionsDataController.bold);
        italicCheckbox.setSelected(mOptionsDataController.italics);
        dateTimeRadio.setSelected(mOptionsDataController.dateTime);
        showStringRadio.setSelected(mOptionsDataController.showString);
        if (dateTimeRadio.isSelected())
            inputTextfield.setDisable(true);
        inputTextfield.setText(mOptionsDataController.userString);
        inputSizeTextfield.setText(((Integer)mOptionsDataController.size).toString());
    }

    @FXML
    public void onShowDate()
    {
        inputTextfield.setDisable(true);
    }
    @FXML
    private void onShowString()
    {
        inputTextfield.setDisable(false);
    }
    
    private void onOkButton(ActionEvent event)
    {
        try
        {
            int textSize = Integer.parseInt(inputSizeTextfield.getText());
            if (textSize < 8 || textSize > 40)
            {
                showInvalidSizeDialog(textSize + " is out of range. Re-enter.");
                event.consume();
            }
            else
            {
                mOptionsDataController.bold = boldCheckbox.isSelected();
                mOptionsDataController.italics = italicCheckbox.isSelected();
                mOptionsDataController.dateTime = dateTimeRadio.isSelected();
                mOptionsDataController.showString = showStringRadio.isSelected();
                mOptionsDataController.userString = inputTextfield.getText();
                mOptionsDataController.size = textSize;
            }
        }
        catch (NumberFormatException e)
        {
            showInvalidSizeDialog(e.getMessage());
            event.consume();
        }
    }
    
    private void showInvalidSizeDialog(String error)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Invalid Text Size");
        alert.setContentText(error);
        alert.setHeaderText("Text size must be between 8 and 40.");
        alert.showAndWait();
        
        inputSizeTextfield.selectAll();
    }
    
}