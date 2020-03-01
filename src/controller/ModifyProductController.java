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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

/**
 * FXML Controller class
 *
 * @author Vladan
 */
public class ModifyProductController implements Initializable {
    
    Stage stage;
    Parent scene;
    
    @FXML
    private TextField modifyProductIdTxt;

    @FXML
    private TextField modifyProductNameTxt;

    @FXML
    private TextField modifyProductInvTxt;

    @FXML
    private TextField modifyProductPriceTxt;

    @FXML
    private TextField modifyProductMaxTxt;

    @FXML
    private TextField modifyProductMinTxt;

    @FXML
    private TextField modifyProcuctSearchTxt;

    @FXML
    private TableView<Part> modifyProductAddTableView;

    @FXML
    private TableColumn<Part, Integer> partIdAddCol;

    @FXML
    private TableColumn<Part, String> partNameAddCol;

    @FXML
    private TableColumn<Part, Integer> inventoryLevelAddCol;

    @FXML
    private TableColumn<Part, Double> pricePerUnitAddCol;

    @FXML
    private TableView<Part> modifyProductDeleteTableView;

    @FXML
    private TableColumn<Part, Integer> partIdDeleteCol;

    @FXML
    private TableColumn<Part, String> partNameDelCol;

    @FXML
    private TableColumn<Part, Integer> inventoryLevelDeleteCol;

    @FXML
    private TableColumn<Part, Double> pricePerUnitDeleteCol;
    
    private static int index;
    private Product selectedProduct;
    private Part selectedPart;
    ObservableList<Part> temporaryPartList = FXCollections.observableArrayList();
    private static ObservableList<Part> partsToTransfer = FXCollections.observableArrayList();
    
    /*
    These methods will handle buttons on Modify Product Screen
    */
    
     @FXML
    void onActionAddModifyProduct(ActionEvent event) {
        
        selectedPart = modifyProductAddTableView.getSelectionModel().getSelectedItem();
        partsToTransfer.add(selectedPart);
        if(selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please select part to add.");
            alert.showAndWait();
        } else {
            modifyProductDeleteTableView.setItems(partsToTransfer);
            partIdDeleteCol.setCellValueFactory(new PropertyValueFactory<>("partId"));
            partNameDelCol.setCellValueFactory(new PropertyValueFactory<>("partName"));
            inventoryLevelDeleteCol.setCellValueFactory(new PropertyValueFactory<>("partPrice"));
            pricePerUnitDeleteCol.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        }
    }

    @FXML
    void onActionDeleteModifyProduct(ActionEvent event) {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to delete selected item?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            int selectedItem = modifyProductDeleteTableView.getSelectionModel().getSelectedIndex();
            modifyProductDeleteTableView.getItems().remove(selectedItem);
        }
    }
    
    // This method will exit to the Main Screen
    @FXML
    void onActionExitModProdToMainScreen(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to exit Modify Product Screen?");
        Optional<ButtonType> choice = alert.showAndWait();
        
        if(choice.get() == ButtonType.OK){
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show(); 
        }
        

    }
    
    // This method will save modification and exit to the Main Screen
    @FXML
    void onActionSaveAndExitModProduct(ActionEvent event) throws IOException {
        
        int id = Integer.parseInt(modifyProductIdTxt.getText());
        String name = modifyProductNameTxt.getText();
        int inventoryStock = Integer.parseInt(modifyProductInvTxt.getText());
        double price = Double.parseDouble(modifyProductPriceTxt.getText());
        int max = Integer.parseInt(modifyProductMaxTxt.getText());
        int min = Integer.parseInt(modifyProductMinTxt.getText());
        
        Product modifiedProduct = new Product(id, name, price, inventoryStock, max, min);
        
        Inventory.updateProduct(index, modifiedProduct);
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Please confirm to modify the part!");
        Optional<ButtonType> choice = alert.showAndWait();
        
        if(choice.get() == ButtonType.OK){
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }

    @FXML
    void onActionSearchModifyProduct(ActionEvent event) {
        
        String searchPartField = modifyProcuctSearchTxt.getText();
        ObservableList searchedPart = Inventory.lookupPart(searchPartField);
        if (searchedPart.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("No part was found matching your criteria!");
            alert.showAndWait();
        }
        else{
            modifyProductAddTableView.setItems(searchedPart);
            partIdAddCol.setCellValueFactory(new PropertyValueFactory<>("partId"));
            partNameAddCol.setCellValueFactory(new PropertyValueFactory<>("partName"));
            inventoryLevelAddCol.setCellValueFactory(new PropertyValueFactory<>("partStock"));
            pricePerUnitAddCol.setCellValueFactory(new PropertyValueFactory<>("partPrice"));

        }   
            
    }
    
    /*
     The fallowing methods accepts a Product to initialize a view 
     */
    public void initDataProduct(Product product){
        selectedProduct = product;
        modifyProductIdTxt.setText(Integer.toString(selectedProduct.getProductId()));
        modifyProductNameTxt.setText(selectedProduct.getProductName());
        modifyProductInvTxt.setText(Integer.toString(selectedProduct.getProductStock()));
        modifyProductPriceTxt.setText(Double.toString(selectedProduct.getProductPrice()));
        modifyProductMaxTxt.setText(Integer.toString(selectedProduct.getProductMax()));
        modifyProductMinTxt.setText(Integer.toString(selectedProduct.getProductMin()));
    }



    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
