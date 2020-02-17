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
    private ButtonType mButtonType;
    private final ButtonType mOkButton;
    
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
    
    public OptionsLayoutController() throws IOException
    {
        super();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("OptionsLayout.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        getDialogPane().setContent(root);
        
        
        mOkButton = new ButtonType("Ok", ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().add(mOkButton);
        Button ok = (Button) getDialogPane().lookupButton(mOkButton);
        ok.addEventFilter(ActionEvent.ACTION, actionEvent -> onOkButton());
        
        ButtonType cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        getDialogPane().getButtonTypes().add(cancelButton);
        
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }

    @FXML
    public void onShowDate()
    {
        
    }
    
    private void onOkButton()
    {
        
    }
    
}
