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
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;
import model.Product;

/**
 * FXML Controller class
 *
 * @author Vladan
 */
public class AddProductController implements Initializable {
    
    Stage stage;
    Parent scene;
    
    @FXML
    private TextField addProductIdTxt;

    @FXML
    private TextField addProductNameTxt;

    @FXML
    private TextField addProductInvTxt;

    @FXML
    private TextField addProductPriceTxt;

    @FXML
    private TextField addProductMaxTxt;

    @FXML
    private TextField addProductMinTxt;

    @FXML
    private TextField addProductSearchTxt;

    @FXML
    private TableView<Part> addTableView;

    @FXML
    private TableColumn<Part, Integer> addPartIdCol;

    @FXML
    private TableColumn<Part, String> addPartNameCol;

    @FXML
    private TableColumn<Part, Integer> addInventoryLevelCol;

    @FXML
    private TableColumn<Part, Double> addPricePerUnitCol;

    @FXML
    private TableView<Part> deleteTableView;

    @FXML
    private TableColumn<Part, Integer> delPartIdCol;

    @FXML
    private TableColumn<Part, String> delPartNameCol;

    @FXML
    private TableColumn<Part, Integer> delInventroyLevelCol;

    @FXML
    private TableColumn<Part, Double> delPricePerUnitCol;
    
    private int count;
    private Part selectedPart;
    Product newProduct;
    private static ObservableList<Part> partsToTransfer = FXCollections.observableArrayList();
    
    
    /*
    The fallowing methods will handle buttons on AddProduct screen
    */
    
    @FXML
    void onActionAddProduct(ActionEvent event) {
   
        selectedPart = addTableView.getSelectionModel().getSelectedItem();
        partsToTransfer.add(selectedPart);
        if(selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please select part to add.");
            alert.showAndWait();
        } else {
            deleteTableView.setItems(partsToTransfer);
            delPartIdCol.setCellValueFactory(new PropertyValueFactory<>("partId"));
            delPartNameCol.setCellValueFactory(new PropertyValueFactory<>("partName"));
            delInventroyLevelCol.setCellValueFactory(new PropertyValueFactory<>("partPrice"));
            delPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        }
       
    }

    @FXML
    void onActionDeleteAddProduct(ActionEvent event) {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to delete selected item?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            int selectedItem = deleteTableView.getSelectionModel().getSelectedIndex();
            deleteTableView.getItems().remove(selectedItem);
        }
    }
    
    // This method will go back to the main screen
    @FXML
    void onActionExitToMainScreen(ActionEvent event) throws IOException {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Please confirm to exit to the main screen!");
        Optional<ButtonType> choice = alert.showAndWait();
        
        if(choice.get() == ButtonType.OK) {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        
    }
    
    // This method will save modification and exit to the Main Screen
    @FXML
    void onActionSaveAndExit(ActionEvent event) throws IOException {
        
        String name = addProductNameTxt.getText();
        int inventoryStock = Integer.parseInt(addProductInvTxt.getText());
        double price = Double.parseDouble(addProductPriceTxt.getText());
        int max = Integer.parseInt(addProductMaxTxt.getText());
        int min = Integer.parseInt(addProductMinTxt.getText());
        
        Product newProduct = new Product(count, name, price, inventoryStock, max, min); 
        Inventory.addProduct(newProduct);
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Please confirm that you want to add the Product to Inventory!");
        Optional<ButtonType> choice = alert.showAndWait();
        
        if(choice.get() == ButtonType.OK){
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }
    }
        

    @FXML
    void onActionSearchAddProduct(ActionEvent event) {
        String searchPartField = addProductSearchTxt.getText();
        ObservableList searchedPart = Inventory.lookupPart(searchPartField);
        if (searchedPart.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("No part was found matching your criteria!");
            alert.showAndWait();
        }
        else{
            addTableView.setItems(searchedPart);
            addPartIdCol.setCellValueFactory(new PropertyValueFactory<>("partId"));
            addPartNameCol.setCellValueFactory(new PropertyValueFactory<>("partName"));
            addInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("partStock"));
            addPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("partPrice"));

        } 
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        addProductIdTxt.setText("Auto Gen - Disabled");
        addProductIdTxt.setDisable(true);
        
        // setup auto generate Product ID
        
        count = Inventory.handleProductIdCount();
        addProductIdTxt.setText(Integer.toString(count));
    }    
    
}
