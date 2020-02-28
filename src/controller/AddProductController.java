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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    
    /*
    The fallowing methods will handle buttons on AddProduct screen
    */
    
    @FXML
    void onActionAddProduct(ActionEvent event) {

    }

    @FXML
    void onActionDeleteAddProduct(ActionEvent event) {

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
