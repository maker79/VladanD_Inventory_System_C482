/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static controller.MainScreenController.getSelectedProduct;
import static controller.MainScreenController.getSelectedProducutIndex;
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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import static model.Inventory.getAllParts;
import static model.Inventory.lookupPart;
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

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private ObservableList<Part> partToTransfer = FXCollections.observableArrayList();
    Product product;

    /*
    These methods will handle buttons on Modify Product Screen
     */
    @FXML
    void onActionAddModifyProduct(ActionEvent event) {

        Part selectedPart = modifyProductAddTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please select product to add.");
            alert.showAndWait();
        } else {
            associatedParts.add(selectedPart);
            updateLowerTableView();
        }
    }

    @FXML
    void onActionDeleteModifyProduct(ActionEvent event) {
        int selectedItem = modifyProductDeleteTableView.getSelectionModel().getSelectedIndex();
        
        if (selectedItem >= 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure you want to delete selected item?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                modifyProductDeleteTableView.getItems().remove(selectedItem);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("No item was selected!");
            alert.showAndWait();
        }
        {

        }
    }

    // This method will exit to the Main Screen
    @FXML
    void onActionExitModProdToMainScreen(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to exit Modify Product Screen?");
        Optional<ButtonType> choice = alert.showAndWait();

        if (choice.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
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
        associatedParts = modifyProductDeleteTableView.getItems();

        if ((min > max) || (max > inventoryStock)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Min value cannot be higher than max!\nOr max value cannot be higher then inv!");
            alert.showAndWait();
        } else {
            Product modifiedProduct = new Product(id, name, price, inventoryStock, min, max);

            modifiedProduct.setAssociatedParts(associatedParts);
            Inventory.updateProduct(getSelectedProducutIndex(), modifiedProduct);

            Parent modifyProductSave = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            Scene scene = new Scene(modifyProductSave);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }

    }

    @FXML
    void onActionSearchModifyProduct(ActionEvent event) {

        String searchedPartField = modifyProcuctSearchTxt.getText();
        ObservableList<Part> searchedPart = Inventory.lookupPart(searchedPartField);

        if (searchedPart.size() == 0) {
            String id = modifyProcuctSearchTxt.getId();
            Part part = (Part) lookupPart(id);

            if (part != null) {
                searchedPart.add(part);
            }
        }
        modifyProductAddTableView.setItems(searchedPart);
    }

    public void updateUpperTableView() {
        modifyProductAddTableView.setItems(Inventory.getAllParts());
    }

    public void updateLowerTableView() {
        modifyProductDeleteTableView.setItems(associatedParts);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        Product selectedProduct = getSelectedProduct();

        modifyProductIdTxt.setText(Integer.toString(selectedProduct.getProductId()));
        modifyProductIdTxt.setDisable(true);
        modifyProductNameTxt.setText(selectedProduct.getProductName());
        modifyProductInvTxt.setText(Integer.toString(selectedProduct.getProductStock()));
        modifyProductPriceTxt.setText(Double.toString(selectedProduct.getProductPrice()));
        modifyProductMaxTxt.setText(Integer.toString(selectedProduct.getProductMax()));
        modifyProductMinTxt.setText(Integer.toString(selectedProduct.getProductMin()));

        associatedParts.addAll(selectedProduct.getAllAssociatedParts());

        partIdAddCol.setCellValueFactory(new PropertyValueFactory<>("partId"));
        partNameAddCol.setCellValueFactory(new PropertyValueFactory<>("partName"));
        inventoryLevelAddCol.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        pricePerUnitAddCol.setCellValueFactory(new PropertyValueFactory<>("partPrice"));
        updateUpperTableView();

        partIdDeleteCol.setCellValueFactory(new PropertyValueFactory<>("partId"));
        partNameDelCol.setCellValueFactory(new PropertyValueFactory<>("partName"));
        inventoryLevelDeleteCol.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        pricePerUnitDeleteCol.setCellValueFactory(new PropertyValueFactory<>("partPrice"));
        updateLowerTableView();
    }

}
