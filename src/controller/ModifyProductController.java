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
    
    private Product selectedProduct;
    private static int index;
    
    /*
    These methods will handle buttons on Modify Product Screen
    */
    
     @FXML
    void onActionAddModifyProduct(ActionEvent event) {

    }

    @FXML
    void onActionDeleteModifyProduct(ActionEvent event) {

    }
    
    // This method will exit to the Main Screen
    @FXML
    void onActionExitModProdToMainScreen(ActionEvent event) throws IOException {
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show(); 

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
