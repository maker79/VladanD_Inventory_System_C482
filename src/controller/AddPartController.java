/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

/**
 * FXML Controller class
 *
 * @author Vladan
 */
public class AddPartController implements Initializable {
    
    Stage stage;
    Parent scene;
    
    Part newPartId;
    
    @FXML
    private TextField addPartIdTxt;

    @FXML
    private TextField addPartNameTxt;

    @FXML
    private TextField addPartInvTxt;

    @FXML
    private TextField addpartPriceTxt;

    @FXML
    private TextField addPartMaxTxt;

    @FXML
    private TextField addPartMinTxt;

    @FXML
    private Label addPartMachIdCompNameLbl;

    @FXML
    private TextField addPartMachIdTxt;

    @FXML
    private RadioButton addPartInHouseRbtn;

    @FXML
    private RadioButton addPartoutsourcedRbtn;
    
    @FXML
    private ToggleGroup addPartTg;
    
    private int count;
    
    /*
    The fallowing methods will handle Save and Cancel buttons
    */
    
    // This method will switch back to the Main Screen, Cancel Button
     @FXML
    void onActionExitToMainScreen(ActionEvent event) throws IOException {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Please Click OK to go back to the Main Screen!");
        Optional<ButtonType> choice = alert.showAndWait();
        if(choice.get() == ButtonType.OK){
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
            
    }
    
    //This method will save info for a part that were added and than go back to the Main Screen
    @FXML
    void onActionSaveAndExit(ActionEvent event) throws IOException {
        
        String name = addPartNameTxt.getText();
        int inventoryStock = Integer.parseInt(addPartInvTxt.getText());
        double price = Double.parseDouble(addpartPriceTxt.getText());
        int max = Integer.parseInt(addPartMaxTxt.getText());
        int min = Integer.parseInt(addPartMinTxt.getText());
        
        if(addPartInHouseRbtn.isSelected()){
            int machineId = Integer.parseInt(addPartMachIdTxt.getText());
            InHouse newInHouse = new InHouse(count, name, price, inventoryStock, max, min, machineId);
            
            Inventory.addPart(newInHouse);
        } else {
            String companyName = addPartMachIdTxt.getText();
            Outsourced newOutsourced = new Outsourced(count, name, price, inventoryStock, max, min, companyName); 
            
            Inventory.addPart(newOutsourced);
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Please confirm that you want to add the part to Inventory!");
        Optional<ButtonType> choice = alert.showAndWait();
        
        if(choice.get() == ButtonType.OK){
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        

    }
    
    /*
    The fallowing methods will handle text displayed depending which radio button is checked
    */
     @FXML
    void onActionInHouseSelected(ActionEvent event) {
        addPartMachIdCompNameLbl.setText("Machine ID");
    }

    @FXML
    void onActionOutsourcedSelected(ActionEvent event) {
        addPartMachIdCompNameLbl.setText("Company Name");
    }


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        addPartIdTxt.setText("Auto Gen - Disabled");
        addPartIdTxt.setDisable(true);
        
        // setup auto generated Part ID
        count = Inventory.handlePartIdCount();
        addPartIdTxt.setText(Integer.toString(count));
        
        
    }    
    
}
