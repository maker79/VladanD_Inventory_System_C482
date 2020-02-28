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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import static model.Inventory.updatePart;
import model.Outsourced;
import model.Part;
import model.Product;

/**
 *
 * @author Vladan
 */
public class MainScreenController implements Initializable {
    
    Stage stage;
    Parent scene;
   
    
    private ObservableList<Part> searchPart = FXCollections.observableArrayList();
    private ObservableList<Product> searchProduct = FXCollections.observableArrayList();
    
    @FXML
    private TextField partsSearchTxt;

    @FXML
    private TableView<Part> partsTableView;

    @FXML
    private TableColumn<Part, Integer> partIDCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Integer> partsInventoryLevelCol;

    @FXML
    private TableColumn<Part, Double> partsPricePerUnitCol;

    @FXML
    private TextField productsSearchTxt;
    
    @FXML
    private TableView<Product> productsTableView;

    @FXML
    private TableColumn<Product, Integer> productIDCol;

    @FXML
    private TableColumn<Product, String> productNameCol;

    @FXML
    private TableColumn<Product, Integer> productInventoryLevelCol;

    @FXML
    private TableColumn<Product, Double> productPricePerUnitCol;
    
    
    /*
    * The fallowing methods will be handling when buutons are clicked on the main screen
    */
    
    // this method will switch to Add Part screen
    @FXML
    void onActionHandleAddPart(ActionEvent event) throws IOException {     
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    // This method will switch to Add Product Screen
    @FXML
    void onActionHandleAddProduct(ActionEvent event) throws IOException {
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void onActionHandleDeleteParts(ActionEvent event) {
        Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();
        
        if(selectedPart == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please select a part you want to delete");
            alert.showAndWait();
        } else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure you want to delete selected part");
            Optional<ButtonType> choice = alert.showAndWait();
            if(choice.get() == ButtonType.OK)
                Inventory.deletePart(selectedPart);
        }
            
        
    }

    @FXML
    void onActionHandleDeleteProduct(ActionEvent event) {
        Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();
        
        if(selectedProduct == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please select a product you want to delete");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure you want to delete selected product");
            Optional<ButtonType> choice = alert.showAndWait();
            if(choice.get() == ButtonType.OK)
                Inventory.deleteProduct(selectedProduct);
        }


    }
    
    // This method will exit the Main Screen and close the application
    @FXML
    void onActionHandleExitMainScreen(ActionEvent event) {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Please Click OK to close the application");
        Optional<ButtonType> choice = alert.showAndWait();
        if(choice.get() == ButtonType.OK)
            System.exit(0);
         
    }
    
    // This method will change to Modify parts screen and pass selected part
    @FXML
    void onActionHandleModifyParts(ActionEvent event) throws IOException {
            
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModifyPart.fxml"));
        Parent modifyPartPage = loader.load();
        Scene modifyPartScene = new Scene(modifyPartPage);
        
        // access the controller and call a method
        ModifyPartController controller = loader.getController();
        if((partsTableView.getSelectionModel().getSelectedItem()) instanceof InHouse) {
            controller.initDataInHouse((InHouse)(partsTableView.getSelectionModel().getSelectedItem()));
        } 
        else {
            controller.initDataOutsourced((Outsourced) partsTableView.getSelectionModel().getSelectedItem());
        }
        
        Stage applicationStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        applicationStage.setScene(modifyPartScene);
        applicationStage.show();
        
        
    }
    
    // This method will change to Modify Product screen
    @FXML
    void onActionHandleModifyProduct(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModifyProduct.fxml"));
        Parent modifyProductPage = loader.load();
        Scene modifyProductScene = new Scene(modifyProductPage);
        
        // access the controller and call a method
        ModifyProductController controller = loader.getController();
        if((productsTableView.getSelectionModel().getSelectedItem()) instanceof Product) 
            controller.initDataProduct((productsTableView.getSelectionModel().getSelectedItem()));
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please enter a Product to modify");
            Optional<ButtonType> choice = alert.showAndWait();
            
            if(choice.get() == ButtonType.OK) {
                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
        Stage applicationStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        applicationStage.setScene(modifyProductScene);
        applicationStage.show();
        
    }
    
    // This method will search for parts when criteria is entered in search box for parts
    @FXML
    void onActionHandleSearchParts(ActionEvent event) {
        
        String searchedPart = partsSearchTxt.getText();
        ObservableList<Part> foundParts = Inventory.lookupPart(searchedPart);
        
        if(foundParts.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please enter item in a search box!");
            alert.showAndWait();
        } else {
            partsTableView.setItems(foundParts);
        }
    
    }
    
    // This method will search for products when certain criteria is entered in product search box
    @FXML
    void onActionHandleSearchProducts(ActionEvent event) {
        
        String searchedProduct = productsSearchTxt.getText();
        ObservableList<Product> foundProducts = Inventory.lookupProduct(searchedProduct);
        
        if(foundProducts.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please enter item in a search box!");
            alert.showAndWait();
        } else {
            productsTableView.setItems(foundProducts);
        }
    }
    
//    public void setPartToModify(Part partToModify){
//        controller.MainScreenController.partToModify = partToModify;
//    }
    
   
    
    
    /**
     * Initializes the controller class
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // Parts table
        partsTableView.setItems(Inventory.getAllParts());
        // to set the value for a cell inside the perenteses we need to create PropValueFact object 
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("partId"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("partName"));
        partsInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        partsPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("partPrice"));
        
        // Products Table
        productsTableView.setItems(Inventory.getAllProducts());
        
        productIDCol.setCellValueFactory(new PropertyValueFactory<>("productId"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("productStock"));
        productPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
        
    }    
    
}
