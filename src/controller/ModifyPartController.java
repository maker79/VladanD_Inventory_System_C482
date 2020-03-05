/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static controller.MainScreenController.getSelectedPart;
import static controller.MainScreenController.getSelectedPartIndex;
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
public class ModifyPartController implements Initializable {
    
    Stage stage;
    Parent scene;
    
    private InHouse selectedInHousePart;
    private Outsourced selectedOutsourcedPart;
    
    @FXML
    private TextField modifyPartIdTxt;

    @FXML
    private TextField modifyPartNameTxt;

    @FXML
    private TextField modifyPartInvTxt;

    @FXML
    private TextField modifyPartPriceTxt;

    @FXML
    private TextField modifyPartMaxTxt;

    @FXML
    private TextField modifyPartMinTxt;

    @FXML
    private Label modifyMachIdCompNameLbl;

    @FXML
    private TextField modifyPartMachIdTxt;

    @FXML
    private RadioButton modifyPartInHouseRbtn;

    @FXML
    private RadioButton modifyPartOutsourcedRbtn;
    
    private static int index;
   
    
    
    /*
    The fallowing methods will handle Save and Cancel button
    */
    
    // This method will go back to the Main Screen
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
    
    // This method will save modification for a part and exit to Main Screen
    @FXML
    void onActionModifyPartAndExit(ActionEvent event) throws IOException {
        
        int id = Integer.parseInt(modifyPartIdTxt.getText());
        String name = modifyPartNameTxt.getText();
        int inventoryStock = Integer.parseInt(modifyPartInvTxt.getText());
        double price = Double.parseDouble(modifyPartPriceTxt.getText());
        int max = Integer.parseInt(modifyPartMaxTxt.getText());
        int min = Integer.parseInt(modifyPartMinTxt.getText());
        index = getSelectedPartIndex();
        
        if(modifyPartInHouseRbtn.isSelected()){
            inHouseModifiedButtonClicked();
            int machineId = Integer.parseInt(modifyPartMachIdTxt.getText());
            InHouse modifiedInHouse = new InHouse(id, name, price, inventoryStock, min, max, machineId);
            Inventory.updatePart(index, modifiedInHouse);
        } else {
            outsourcedButtonClicked();
            String companyName = modifyPartMachIdTxt.getText();
            Outsourced modifiedOutsourced = new Outsourced(id, name, price, inventoryStock, min, max, companyName); 
            Inventory.updatePart(index, modifiedOutsourced);
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Please confirm you want to modify the selected part!");
        Optional<ButtonType> choice = alert.showAndWait();
        
        if(choice.get() == ButtonType.OK){
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
    /*
     The fallowing methods accept a part to initialize a view 
     */
    public void initDataInHouse(InHouse part){
        selectedInHousePart = part;
        modifyPartIdTxt.setText(Integer.toString(selectedInHousePart.getPartId()));
        modifyPartNameTxt.setText(selectedInHousePart.getPartName());
        modifyPartInvTxt.setText(Integer.toString(selectedInHousePart.getPartStock()));
        modifyPartPriceTxt.setText(Double.toString(selectedInHousePart.getPartPrice()));
        modifyPartMaxTxt.setText(Integer.toString(selectedInHousePart.getPartMax()));
        modifyPartMinTxt.setText(Integer.toString(selectedInHousePart.getPartMin()));
        modifyPartMachIdTxt.setText(Integer.toString(selectedInHousePart.getMachineId()));
        modifyMachIdCompNameLbl.setText("Machine ID");
        modifyPartInHouseRbtn.setSelected(true);
        modifyPartIdTxt.setDisable(true);
    }
    
    public void initDataOutsourced(Outsourced part){
        selectedOutsourcedPart = part;
        modifyPartIdTxt.setText(Integer.toString(selectedOutsourcedPart.getPartId()));
        modifyPartNameTxt.setText(selectedOutsourcedPart.getPartName());
        modifyPartInvTxt.setText(Integer.toString(selectedOutsourcedPart.getPartStock()));
        modifyPartPriceTxt.setText(Double.toString(selectedOutsourcedPart.getPartPrice()));
        modifyPartMaxTxt.setText(Integer.toString(selectedOutsourcedPart.getPartMax()));
        modifyPartMinTxt.setText(Integer.toString(selectedOutsourcedPart.getPartMin()));
        modifyPartMachIdTxt.setText(selectedOutsourcedPart.getCompanyName());
        modifyMachIdCompNameLbl.setText("Company Name");
        modifyPartOutsourcedRbtn.setSelected(true);
        modifyPartIdTxt.setDisable(true);
        
    }
    
    public void inHouseModifiedButtonClicked(){
        modifyMachIdCompNameLbl.setText("Machine ID");
    }
    
    public void outsourcedButtonClicked(){
        modifyMachIdCompNameLbl.setText("Company Name");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
}
